<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h2>시험 점수 등록</h2>
	<form method="post"> <!-- 직전 요청 url을 재활용 하겠다. -->
		<p>
		<!--커맨드 객체 사용  -->
			#이름: <input type="text" name="stuName">
			#국어: <input type="text" name="kor">
			#영어: <input type="text" name="eng">
			#수학: <input type="text" name="math">
			<input type="submit" value="확인"> 
		</p>
	
	
	</form>

</body>
</html>