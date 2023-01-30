<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글 등록하기</title>
</head>
<style>

</style>
<body>
	<h2>답글 등록</h2>
	<form action="board-reply-process.do" name="board" method="post">
	<p> 내용 : </p>
	<p> <textarea cols="60" rows="10" placeholder="내용을 입력하세요."></textarea></p>
	<p> 작성자 : <input type="text" name="writer"></p>
	<p> 작성일자 : <input type="text" maxlength="4" size="4" name="year"> -
			   <input type="text" maxlength="4" size="4" name="month"> -
			   <input type="text" maxlength="4" size="4" name="day">
	</p>
	
	<p> <input type="submit" value="등록하기"></p>
</form>
</body>
</html>