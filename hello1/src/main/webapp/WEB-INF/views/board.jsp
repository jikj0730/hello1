<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="R" value="/" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="${R}res/common.js"></script>
<link rel="stylesheet" href="${R}res/common.css">
</head>
<body>
	<div class="container">
		<h1>게시글 목록</h1>
		<div class="pull-right mb5">
			<a href="create" class="btn btn-info"> 
				<span class="glyphicon glyphicon-user"></span> 게시글 등록
			</a>
		</div>
		<table class="table table-bordered mt5">
			<thead>
				<tr>
					<th style="width: 17%">id</th>
					<th style="width: 49%">제목</th>
					<th style="width: 17%">작성자</th>
					<th style="width: 17%">날짜</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${ List }">
					<tr data-url="article?id=${ list.id }">
						<td>${ list.id }</td>
						<td>${ list.title }</td>
						<td>${ list.writer }</td>
						<td>${ list.date}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>