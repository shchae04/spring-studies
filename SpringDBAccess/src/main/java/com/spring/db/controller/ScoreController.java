package com.spring.db.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.db.model.ScoreVO;
import com.spring.db.service.IScoreService;

@Controller
@RequestMapping("/score")
public class ScoreController {
	
	//빈 등록된 Service 객체를 직접 생성하는 것이 아닌 자동 주입으로 사용 
	@Autowired
	private IScoreService service;

	//점수 등록 화면을 열어주는 요청 메서드
	@GetMapping("/register")
	public String register() {
		System.out.println("/score/register: GET");
		return "score/write-form";
	}
	
	//점수 등록 요청을 처리할 메서드
	@PostMapping("/register")
	public String register(ScoreVO vo) {
		System.out.println("/score/register: POST");
		System.out.println("param:" + vo);
		//service를 부르는 로직!
		service.insertScore(vo);
		return "score/write-result";
	}
	
	//점수 전체 조회를 처리하는 메서드
	@GetMapping("/list")
	public void list(Model model) {
		System.out.println("/score/list: GET");
		
//		List<ScoreVO> list =  service.selectAllScores();
//		model.addAttribute("sList",list);
		
		model.addAttribute("sList",service.selectAllScores());
	}
	
	
	//점수 삭제 요청 처리 메서드
	@GetMapping("/delete")
	public String delete(@RequestParam("stuNum") int stuNum, RedirectAttributes ra) {
	
	//삭제 처리를 완료하신 후 list 요청이 다시 컨트롤러로 들어갈 수 있도록 처리해 보세요.
	//list요청이 다시 들어가서 list.jsp로 갔을 때, 삭제 이후에 간 것이 판단된다면
	//브라우저에 '삭제가 완료되었습니다.' 문구를 빨간색으로 띄워보세요.
	//(RedirectAttributes 사용, 경고창으로 띄워도 좋아요.)
		System.out.println("/delete 요청! 학번" + stuNum);
		service.deleteScore(stuNum);
		ra.addFlashAttribute("msg","delSuccess");
		
		return "redirect:/score/list";
	}
	
	//점수 개별 조회 화면요청 처리 메서드
	@GetMapping("/search")
	public void selectOne() {
		System.out.println("/score/search: GET");
	}
	
	//점수 개별 조회 처리 메서드
	@GetMapping("/selectOne")
	public String searchOne(@RequestParam("stuNum") int stuNum,
							RedirectAttributes ra,
							Model model) {
		System.out.println("/score/selectone: GET");
		ScoreVO vo = service.selectOne(stuNum);
		if(vo == null) {
			ra.addFlashAttribute("msg","검색 결과가 없습니다.");
			return "redirect:/score/search";
		}
		
		model.addAttribute("stu",vo);
		return "score/search-result";
		
		
		
	}
	
}











