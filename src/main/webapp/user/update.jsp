<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>update</title>
</head>
<body>

<h2>회원 정보 수정</h2>
<form action="user-update-process.do" name="user" method="post">
	<p> 아이디 : <input type="text" name="id" value = ${user.u_id }></p>
	<p> 비밀번호 : <input type="password" name="password" value = ${user.u_pw }></p>
	<p> 이름 : <input type="text" name="name" value = ${user.u_name }></p>
	<p> 연락처 : <input type="text" maxlength="4" size="4" name="tel1" > -
			   <input type="text" maxlength="4" size="4" name="tel2" > -
			   <input type="text" maxlength="4" size="4" name="tel3" >
	</p>
	<p> 나이 : <input type="text" name="age" value = ${user.u_age }></p>
	<p> <input type="submit" value="수정하기"></p>
</form>

</body>
</html>