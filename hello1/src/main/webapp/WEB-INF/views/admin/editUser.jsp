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
		<sec:authentication property="user.userId" />
		<sec:authorize access="hasRole('ROLE_ADMIN')">

		</sec:authorize>
		<div class="container">
			<h1>회원 정보</h1>

			<div class="pull-right mb5">
				<!-- <a href="/board/create" class="btn btn-info"> 
				<span class="glyphicon glyphicon-user"></span> 게시글 등록
			</a> -->
			</div>
			<table class="table table-bordered mt5 table-hover">
				<tr>
					<th class="success" style="width: 15%">회원 번호</th>
					<td>${user.no }</td>
				</tr>
				<tr>
					<th class="success" style="width: 15%">아이디</th>
					<td>${user.userId}</td>
				</tr>
				<tr>
					<th class="success" style="width: 15%">유저 타입</th>
					<td><select id="selectbox">
							<option value="1"
								${user.userType == "ROLE_USER" ? 'selected' : ''}>유저</option>
							<option value="2"
								${user.userType == "ROLE_TEST" ? 'selected' : ''}>테스트</option>
							<option value="3"
								${user.userType == "ROLE_ADMIN" ? 'selected' :'' }>관리자</option>
					</select></td>
				</tr>
			</table>
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<button id="editbutton" class="btn btn-warning">수정하기 </button> 
					<button id="resetbutton" class="btn btn-danger"> 비밀번호 초기화 </button>
				</div>
				<button id="deletebutton" class="btn btn-danger pull-rignt">회원 삭제 </button>
			</div>
		</div>
	</sec:authorize>
	<sec:authorize access="not authenticated">
		<div class="container">
			<h1>로그인 하세요</h1>
		</div>
	</sec:authorize>
	
<script type="text/javascript">
$(document).ready(function(){
	
	$("#resetbutton").click(function(){
		
		if(confirm("비밀번호를 초기화하시겠습니까?")){
			
			$.ajax({
				url: "/admin/userpassreset",
				method: "GET",
				data: {"no" : '<c:out value="${user.no}"/>'},
				dataType: "text",
				contentType: "application/json;charset=UTF-8",
				success:function(data){
					if(data){
						alert(data);
						location.reload();
					}else{
						alert("에러");
					}
				},
				error: function(jqXHR, textStatus, errorThrown){
            		alert(jqXHR+"\n"+jqXHR.status+"\n"+jqXHR.statusText+"\n"+jqXHR.responseText+"\n"+jqXHR.readyState);
	     	    }
			});
		}
		else{
			alert("취소");
		}
		
	});
	
	
	$("#editbutton").click(function(){
		if(confirm("정보를 변경하시겠습니까?")){
			
			var data = $("#selectbox option:selected").val();
			alert(data);
			$.ajax({
				url: "/admin/usertypereset",
				method: "GET",
				data: {"no" : '<c:out value="${user.no}"/>',
					"select" : data},
				dataType: "text",
				contentType: "application/json;charset=UTF-8",
				success:function(data){
					if(data){
						alert(data);
						location.reload();
						//$(location).attr('href', "/admin/userlist");

					}else{
						alert("에러");
					}
				},
				error: function(jqXHR, textStatus, errorThrown){
            		alert(jqXHR+"\n"+jqXHR.status+"\n"+jqXHR.statusText+"\n"+jqXHR.responseText+"\n"+jqXHR.readyState);
	     	    }
			});
		}
		else{
			alert("취소");
		}
	});
	
	$("#deletebutton").click(function(){
		if(confirm("삭제하시겠습니까?")){
			
			//var data = $("#selectbox option:selected").val();
			//alert(data);
			$.ajax({
				url: "/admin/deleteuser",
				method: "GET",
				data: {"no" : '<c:out value="${user.no}"/>'},
				dataType: "text",
				contentType: "application/json;charset=UTF-8",
				success:function(data){
					if(data){
						alert(data);
						$(location).attr('href', "/admin/userlist");
					}else{
						alert("에러");
					}
				},
				error: function(jqXHR, textStatus, errorThrown){
            		alert(jqXHR+"\n"+jqXHR.status+"\n"+jqXHR.statusText+"\n"+jqXHR.responseText+"\n"+jqXHR.readyState);
	     	    }
			});
		}
		else{
			alert("취소");
		}
	});
});
</script>

</body>
</html>