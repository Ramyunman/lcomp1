<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세정보</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
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
	<a href="/lcomp1/board-reply-insert.do?b_group=${board.b_group}&b_order=${board.b_order}&b_depth=${board.b_depth}">답글 등록</a>
			
	<h4> >> 댓글 등록 </h4>
	<form action="comment-insert-process.do" name="comment" method="post">
		<input type = "hidden" name = "b_idx" value = "${board.b_idx }">
		<p> 내용 : <input type="text" name = content></p>
		<p> 작성자 : <input type="text" name="writer"></p>	
		<p> <input type="submit" value="등록하기"></p>	
	</form>
	
	
	<h3>댓글 목록</h3>
		<table>
			<tr>
				<th>No</th>
				<th>내용</th>
				<th>작성자</th>
				<th>작성일자</th>		
			</tr>
			
			<c:forEach items="${commentList}" var="comment" varStatus="status">
				<tr>
					<td>${comment.c_idx}</td>
					<td>${comment.c_content }</td>
					<td>${comment.c_writer }</td>
					<td>${comment.c_date }</td>
					<td>
						<button type="button" class="btnCommentForm">댓글</button>
					</td>			
				</tr>
				<tr style="display: none;">
					<td>
						<textarea rows="1" cols="80"></textarea>
						<button type="button" class="btnComment" c_group="${comment.c_group }" c_order="${comment.c_order }" c_depth="${comment.c_depth }">등록</button>
					</td>
				</tr>
			</c:forEach>
		</table>
<script>
$(document).on('click', '.btnCommentForm', function () {
	console.log('asdfasdf');
	$(this).parent().parent().next().css('display', '');	//btnCommentForm에서 시작해서 바깥으로 나가면서 찾아감.
});

$(document).on('click', '.btnComment', function () {
	console.log('asdfasdf2222');
	
/*	let cgroup = $(this).attr('cgroup');
	
	$.ajax({
		  method: "POST",
		  url: "commentReply.do",
		  data: { c_group:cgorup , c_order: , c_depth: }
	})
	.done(function( msg ) {
	   	alert( "Data Saved: " + msg );
	});
*/

});
</script>
</body>
</html>