<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>comment 등록</title>
</head>
<body>
	<h2>대댓글 등록</h2>
	<form action="comment-commentInComments-process.do" name="comment" method="post">
	<p> 내용 : <input type="text" name="content"></p>
	<p> <input type = "hidden" name = "b_idx" value = "${comment.b_idx }"> </p>
	<p> <input type = "hidden" name = "c_order" value = "${comment.c_order }"> </p>
	<p> <input type = "hidden" name = "c_depth" value = "${comment.c_depth }"> </p>

	<p> <input type="submit" value="등록하기"></p>
</form>
</body>
</html>