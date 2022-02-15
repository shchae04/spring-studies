package com.spring.mvc.board.commons.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//인터셉터 클래스를 만들려면 HandlerInterceptorAdapter 인터페이스를 구현합니다. 5.3버전 이상.
public class BoardInterceptor implements HandlerInterceptor {

	//PreHandle은 컨트롤러로 들어가기 전 처리해야 할 로직을 작성.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("게시판 인터셉터 발동!");
		
		HttpSession session = request.getSession(); //request를 보고 session을 얻어올 생각을해야함.
		
		if(session.getAttribute("login") == null) {
			System.out.println("회원 인증 실패");
			response.sendRedirect("/board/list");
			return false; //false --> 작성 해줘야 컨트롤러에 들어가지않음.
		}
		System.out.println("회원 인증 성공!");
		return true;  //--> true를 return하면 아무 일도 일어나지 않고 요청이 전달됩니다.
	}

	//PostHandle은 컨트롤러를 나갈 때 공통 처리해야 할 내용을 작성.
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		System.out.println("게시판 인터셉터의 postHandle발동");
		System.out.println("모델 객체의 내부 :" + modelAndView.getModel());
		
		Object data =  modelAndView.getModel().get("article");
		System.out.println("article이라는 이름의 데이터:" + data);
//		modelAndView.setViewName("/"); 특정 요청에 따라서 다른곳으로 옮겨버릴 수 있다.
		
		/*
		 컨트롤러에서 로직을 처리하고 나가는 흐름을 붙잡아서
		 모델 데이터가 제대로 전송이 되는지 확인하고, 추가할 내용이나 수정할 내용이 있다면
		 모델 객체를 받아와서 추가 수정도 가능합니다.
		 기타 특징을 이용하여 흐름을 제어할 수도 있습니다. (sendRedirect, viewName을 수정)
		 */
	}

	//afterCompletion은 응답확인용 log찍어보는 용도로 사용한다 잘 사용하지 않음.
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
}
