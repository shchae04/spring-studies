<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%--
		-/selectOne 요청을 통해 해당 학번을 가진 학생의 성적 정보를
		조회하여 search-result.jsp에 한 학생의 모든 정보를 적절하게 
		출력하세요.( 디자인은 안하셔도 됩니다.)
	 --%>
	<h2>${stu.stuName}학생 성적 정보 조회</h2>
	 
	 <p>
	이름: ${stu.stuName } <br>
	국어: ${stu.kor } <br>
	수학: ${stu.math } <br>
	영어: ${stu.eng } <br>
	총점: ${stu.total } <br>
	평균: ${stu.average} <br>
	</p>
	
	<a href="/db/score/search">점수 개별 조회</a>


</body>
</html>