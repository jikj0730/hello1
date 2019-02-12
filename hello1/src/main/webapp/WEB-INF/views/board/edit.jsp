<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/latest/js/bootstrap.min.js"></script>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/latest/css/bootstrap.min.css"
	rel="stylesheet">
<style>
body {
	background: #f8f8f8;
	padding: 60px 0;
}

#login-form>div {
	margin: 15px 0;
}
</style>
</head>
<body>
<sec:authentication property='user.userId' var="writer" />
<a href="/logout_processing" class="btn btn-success pull-right mb5"> 
		로그아웃
	</a>
	<h1>수정</h1>
	<div class="container">
		<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<form action="/board/edit" method="post">
				<div class="form-group">
					<label for="exampleInputEmail1">아이디</label> <input type="text"
						value="${writer }" class="form-control" id="userId"
						readonly="readonly">
				</div>
				<div class="form-group">
					<label for="ddd">제목</label> <input type="text" class="form-control"
						name="title" value="${article.title }">
				</div>
				<div class="form-group">
					<label for="aaa">내용</label>
					<textarea class="form-control" rows="3" name="content">${article.content}</textarea>
				</div>
				<input type="hidden" name="no" value="${article.no }"/>
				<button type="submit" id="button1" class="btn btn-default">작성</button>

			</form>
			<a href="/board">
				<button id="button1" class="btn btn-danger">취소</button>
			</a>
		</div>
	</div>

</body>
</html>