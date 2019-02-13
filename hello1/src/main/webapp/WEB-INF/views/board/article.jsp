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
<style type="text/css">
.box {
	width: 94%;
	margin: 1%;
	display: inline-block;
	font-size: 0.96em;
	background-color: #DEF3FF;
	padding: 8px 8px;
	box-sizing: border-box;
	border-radius: 8px;
	color: #21637D;
	border: 1px solid #A8DCFF;
}
</style>
</head>
<body>
	<sec:authentication property='user.userId' var="userId" />
	<sec:authentication property='user.no' var="no" />
	로그인 정보 : ${userId }
	<br>
	<a href="/logout_processing" class="btn btn-success pull-right mb5">
		로그아웃 </a>
	<div class="container">
		<h1>게시글 보기</h1>
		<div class="pull-right mb5">
			<a href="/board" class="btn btn-info"> <span
				class="glyphicon glyphicon-user"></span> 목록으로
			</a>
		</div>
		<table class="table table-bordered mt5 table-hover">
			<tr>
				<th class="success" style="width: 15%">글번호</th>
				<td>${article.no }</td>
				<th class="success" style="width: 15%">작성자</th>
				<td>${article.writer }</td>
				<th class="success" style="width: 15%">시간</th>
				<td>${article.date }</td>
			</tr>
			<tr>
				<th class="success" style="width: 15%">제목</th>
				<td colspan="5">${article.title }</td>
			</tr>
			<tr>
				<th class="success" style="width: 15%">내용</th>
				<td colspan="5">${article.content }</td>
			</tr>
		</table>
		<c:choose>
			<c:when test="${no == article.userNo}">
				<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<a href="/board/edit?no=${article.no}" class="btn btn-warning">
							수정하기 </a>
						<a href="/board/delete?no=${article.no}"
							onclick="confirm('삭제하시겠습니까?')" class="btn btn-danger"> 삭제하기 </a>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<button class="btn btn-warning" disabled>수정하기</button>
						<button onclick="confirm('삭제하시겠습니까?')" class="btn btn-danger"
							disabled>삭제하기</button>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="row">
			<h3>댓글달기</h3>
			<div class="col-md-10">
				<input type="text" class="form-control" id="reply">
			</div>
			<div class="col-md-2">
				<button class="btn btn-default" id="replybutton">댓글달기</button>
			</div>
		</div>
		<div class="row">
			<c:forEach var="list" items="${ ReplyList }">
				<div class="box">
					<p>
						${ list.writer } / ${ list.date } 
						<c:choose>
							<c:when test="${no == list.userNo}">
								<span class="pull-right mb5">
									<a href="/board/reply/delete?no=${list.no}&ano=${article.no }">삭제</a>
								</span>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</p>
					<span> ${ list.content } </span>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	
	//var temp = '<c:out value="${userId}"/>'
	//alert(temp);
	
	function checkVal(){
 		if($('#reply').val()==''){
 			alert("댓글을 입력해주세요.");
 			return false;	
 		}
 		return true;				
 	};
 	
	$("#replybutton").click(function(){
		
		if(!checkVal()){
			return false;
		}
		
		var sendData  = JSON.stringify({
			"content" : $("#reply").val(),
			"articleNo" : '<c:out value="${article.no}"/>'
		});
		
		$.ajax({
			url: "/board/reply",
			method: "POST",
			data: sendData,
			dataType: "json",
			contentType: "application/json;charset=UTF-8",
			success:function(data){
				if(data){
					//alert(data);
					location.reload();
				}else{
					alert("에러");
				}
			},
			error: function(){
        		alert("에러");
     	    }
		});
	});
});
</script>
</html>
