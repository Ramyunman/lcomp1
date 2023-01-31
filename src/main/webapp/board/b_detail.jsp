<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세정보</title>
</head>
<style>
	table {
		border-collapse:collapse;
	}
	table tr th {
		font-weight:700;
	}
	table tr td, table tr th {
		border:1px solid #818181;
		width:200px;
		text-align:center;
	}
	a {
		text-decoration:none;
		color:#000;
		font-weight:700;
		border:none;
		cursor:pointer;
		padding:10px;
		display:inline-block;
	}
</style>
<body>
	<h1>상세페이지</h1>

	<table>
	<tr>
		<td>번호</td>
		<td>${board.b_idx }</td>
	</tr>
		<tr>
		<td>내용</td>
		<td>${board.b_content }</td>
	</tr>
		<tr>
		<td>조회수</td>
		<td>${board.b_views }</td>
	</tr>
		<tr>
		<td>작성자</td>
		<td>${board.b_writer }</td>
	</tr>
		<tr>
		<td>작성일자</td>
		<td>${board.b_date }</td>
	</tr>
	<tr style="height:50px;">
			<td style="border:none;">
				<a href="/lcomp1/board-update.do?b_idx=${board.b_idx}" style="width:70%;font-weight:700;background-color:#818181;color:#fff;">수정</a>
			</td>
			<td style="border:none;">
				<a href="/lcomp1/board-delete.do?b_idx=${board.b_idx}" style="width:70%;font-weight:700;background-color:red;color:#fff;">삭제</a>
			</td>
		</tr>
	</table>
	<a href="/lcomp1/board-reply.do?b_group=${board.b_group}&b_order=${board.b_order}&b_depth=${board.b_depth}">답글 등록</a>
</body>
</html>