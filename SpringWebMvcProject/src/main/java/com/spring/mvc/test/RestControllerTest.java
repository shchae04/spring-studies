package com.spring.mvc.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class RestControllerTest {

	
	/*
	 
	 JSON ajax 비동기 통신에 사용한다.
	 
	 @ResponseBody 
	 - 리턴 데이터를 viewResolver에게 전달하지 않고
	 클라이언트에게 해당 데이터를 바로 응답하게 합니다.
	 비동기 통신에서 주로 많이 사용합니다.
	 ex) 아이디 중복확인:
	 
	 @RestController를 사용하면 모든 메서드에
	 @responseBody를 붙인 결과와 같습니다.
	 */
	
	@GetMapping("/hello")
//	@ResponseBody
	public String hello() {
		return "hello world";
	}
	
	@GetMapping("/hobby")
//	@ResponseBody
	public List<String> hobby() {
		List<String> hobby = Arrays.asList("축구","수영","영화감상");
		return hobby;
	}
	
	@GetMapping("/study")
	public Map<String, Object> study() {
		Map<String,Object> subject = new HashMap<String, Object>();
		subject.put("java", "자바");
		subject.put("jsp", "java server pages");
		subject.put("spring", "spring frameworks");
		
		return subject;
	}
	
	@GetMapping("/person")
	public Person person() {
		Person p = new Person();
		p.setName("김철수");
		p.setAge(30);
		p.setHobbies(Arrays.asList("수영","독서","축구"));
		
		return p;
	}
	
	@GetMapping(value="/getText", produces="text/plain")
	public String getText() {
		System.out.println("/getText요청이 들어옴");
		return "Hello World!";
	}
	
	@PostMapping("/getObject")
	public Person getObject(@RequestBody Person person) {
		System.out.println("/getObject 요청이 들어옴");
		System.out.println("이름:"+ person.getName());
		System.out.println("나이:"+ person.getAge());
		System.out.println("취미:" + person.getHobbies());
		person.setAge(40);
		
		return person;
		
		/* yarc payload
		 {
		 "name":"김철수",
		 "age": 24,
		 "hobbies": ["수면","식사","놀기"]
		 }
		 */
	}
	
	@GetMapping("/getPath/{id}/{cpp}/{page}")
	public Map<String,Integer> getPath(@PathVariable int id,@PathVariable int cpp,
										@PathVariable int page) {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		map.put("cpp", cpp);
		map.put("page", page);
		
		return map;
	}
	
	
	
	
	
	
}
