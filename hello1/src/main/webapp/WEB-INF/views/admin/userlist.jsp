<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<sec:authorize access="authenticated">
		<a href="/logout_processing" class="btn btn-success pull-right mb5">
			로그아웃 </a>

		<div class="container">
			<h1>게시글 목록</h1>
			<table class="table table-bordered mt5 table-hover">
				<thead>
					<tr>
						<th style="width: 17%">회원번호</th>
						<th style="width: 49%">아이디</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="list" items="${ List }">
						<tr data-url="/board/article?no=${ list.no }">
							<td>${ list.no }</td>
							<td>${ list.title }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</sec:authorize>
</body>
</html>