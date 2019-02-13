<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
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
	<sec:authorize access="authenticated">
		<a href="/logout_processing" class="btn btn-success pull-right mb5">
			로그아웃 </a>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<div class="container">
				<h1>유저 목록</h1>
				<h1>${contextPath}</h1>
				<table class="table table-bordered mt5 table-hover">
					<thead>
						<tr>
							<th style="width: 17%">회원번호</th>
							<th style="width: 49%">아이디</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="list" items="${ userList }">
							<tr data-url="/admin/useredit?no=${ list.no }">
								<td>${ list.no }</td>
								<td>${ list.userId }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</sec:authorize>
	</sec:authorize>
	<sec:authorize access="not authenticated">
		<div class="container">
			<h1>로그인 하세요</h1>
		</div>
	</sec:authorize>
</body>
</html>