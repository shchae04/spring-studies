package com.spring.mvc.user.controller;



import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.spring.mvc.user.model.UserVO;
import com.spring.mvc.user.service.IUserService;

@RestController //@RestController로 쓴다 -> 뷰리졸버에 연계되는게 아니라 클라이언트로 던진다!
@RequestMapping("/user") 
public class UserController {

	@Autowired
	private IUserService service;

	@PostMapping("/checkId")
	public String checkId(@RequestBody String account) { //@requestbody를 통해 json데이터를 가지고옴!
		System.out.println("/user/checkId: POST");
		System.out.println("param:"+ account);
		
		int checkNum = service.checkId(account); //0 or 1 온다.
		
		if(checkNum == 1) {
			System.out.println("아이디가 중복됨!");
			return "duplicated";
		} else {
			System.out.println("아이디 사용 가능!");
			return "available";
		}
		
		
	}
	
	//회원 가입 요청 처리
	@PostMapping("/")
	public String register(@RequestBody UserVO vo) {
		
		System.out.println("/user/: POST");
		service.register(vo);
		return "joinSuccess";
	}
	
	//로그인 요청 처리
	@PostMapping("/loginCheck")
	public String login(@RequestBody UserVO vo,
						/*HttpServletRequest request*/HttpServletResponse response,
						HttpSession session) {
		System.out.println("/user/loginCheck: POST");
		System.out.println("param:" + vo);
		
		//서버에서 세션 객체를 얻는 방법
		//1.HTTPservletRequest 객체 사용
//		HttpSession session =  request.getSession();
		
		//2.매개값으로 HttpSession 객체 받아서 사용
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		UserVO dbData = service.selectOne(vo.getAccount());
		//Mybatis는 조회된 값이없으면 null 이온다.
		if(dbData != null) { //raw 비밀번호랑 비교한다; 암호화된것이랑.
			if(encoder.matches(vo.getPassword(),dbData.getPassword())) {
				//로그인 성공 회원을 대상으로 세션 정보를 생성.
				session.setAttribute("login", dbData);
				
				long limitTime = 60 * 60 * 24 * 90;
				
				//자동 로그인 체크 시 처리해야 할 내용
				if(vo.isAutoLogin()) {
					//자동 로그인을 희망하는 경우
					//쿠키를 이용하여 자동 로그인 정보를 저장.
					System.out.println("자동 로그인 쿠키 생성 중...");
					//세션 아이디를 가지고 와서 쿠키에 저장(고유한 값이 필요합니다)
					Cookie loginCookie = new Cookie("loginCookie",session.getId());
					loginCookie.setPath("/"); //쿠키가 동작할 수 있는 유효한 url
					loginCookie.setMaxAge((int)limitTime);
					response.addCookie(loginCookie);
					
					//자동 로그인 유지 시간을 날짜 객체로 변환.(DB에 삽입하기 위해, 밀리초.)
					long expiredDate = System.currentTimeMillis() + (limitTime * 1000);
					//Date 객체의 생성자에 매개값으로 밀리초의 정수를 전달하면 날짜형태로 변경해 줍니다.
					Date date = new Date(expiredDate);
					
					System.out.println("자동 로그인 만료시간:" + date);
					
					service.keepLogin(session.getId(), date, vo.getAccount());
				}
				
				return "loginSuccess";
			} else {
				return "pwFail";
			}
		} else {
			return "idFail";
		}
		
		
		/*
		int result = service.checkId(vo.getAccount());
		
		if(result == 0) { //아이디가 없다면
			return "idFail";
		} else  { //비밀번호가 틀렸다면
			if(vo.getPassword() != (service.selectOne(vo.getAccount())).getPassword())
			return"pwFail";
			else
				return "loginSuccess"; //성공했다면
		} 
		*/
		
		
	
	}
	
	//로그아웃 처리
	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session, RedirectAttributes ra
			,HttpServletRequest request,HttpServletResponse response) {
		System.out.println("/user/logout: GET");
		
//		session.invalidate();
		
		//session에서 아이디를 미리 꺼내놓기
		UserVO user = (UserVO) session.getAttribute("login");
			
		session.removeAttribute("login");
		
		ra.addFlashAttribute("msg","logout");
		
		/*
		 자동 로그인 쿠키가 있는지를 확인(없는 사람도 있으니깐)
		 쿠키가 존재한다면 쿠키의 수명을 0
		 쿠키를 지우실 때도 setPath를 동일하게 지정해 주어야 합니다.
		 DB의 내용도 바꿔 주셔야 합니다.
		 세션ID: 'none', 만료시간: 지금 이 시간. -> Date. 
		 */
		
		Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
	
		if(loginCookie != null) {
			loginCookie.setMaxAge(0);
			loginCookie.setPath("/");
			response.addCookie(loginCookie); //쿠키는 클라이언트 쪽에 
			service.keepLogin("none", new Date(), user.getAccount());
		}
		//자동 로그인 해제 방법.
		
	
			
			
		
		
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("name","value");
//		mv.setViewName("redirect:/");
		
	///===> same ; 전달할 데이터 없다.
		//RestController를 사용하고있기 때문에 클라이언트로 던져서
		//요청을 다시 보낼땐 리턴타입을 modelAndView
		//viewName을 강제시킴
		
		return new ModelAndView("redirect:/");
		
	}
	
	
	
}













