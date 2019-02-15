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
		<sec:authentication property='user.userId' var="userId" />
		<sec:authorize access="hasRole('ROLE_ADMIN')">

		</sec:authorize>
		<div class="container">
			<h1>회원 아이디 - ${userId }</h1> 

			<div class="pull-right mb5">
				<!-- <a href="/board/create" class="btn btn-info"> 
				<span class="glyphicon glyphicon-user"></span> 게시글 등록
			</a> -->
			</div>
			<form>
				<div class="form-group">
					<label for="exampleInputPassword1">현재 비밀번호</label>
					<input type="password" class="form-control" id="inputPassword1" placeholder="암호">
					<label for="exampleInputPassword1">바꿀 비밀번호 - 숫자와 문자를 포함하여 6~12자리</label>
					<input type="password" class="form-control" id="inputPassword2" placeholder="암호">
				</div>
				<button type="button" id="button1" class="btn btn-default">수정하기</button>
			</form>
		</div>
	</sec:authorize>
	<sec:authorize access="not authenticated">
		<div class="container">
			<h1>로그인 하세요</h1>
		</div>
	</sec:authorize>

	<script type="text/javascript">
$(document).ready(function(){
	
	function checkVal(){
		
		var passPattern = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/; //비밀번호 숫자와 문자 포함하여 6~12자리
		
 		if($('#inputPassword1').val()==''){
 			alert("비밀번호를 입력해주세요.");
 			return false;	
 		}else if($('#inputPassword2').val()==''){
 			alert("바꿀 비밀번호를 입력해주세요.");
 			return false;	
 		}else if(!passPattern.test($('#inputPassword2').val())){
 			alert("옳지 않은 비밀번호입니다.");
 			return false;
 		}
 		return true;				
 	};
	
	$("#button1").click(function(e){
		e.preventDefault();
	    	
		//입력폼 값 검사
		if(!checkVal()){
			return false;
		}
		
		var pass1 = $('#inputPassword1').val();
		var pass2 = $('#inputPassword2').val();
		
		var form = $('<form></form>');
		form.attr('action', '/board/changepassword');
		form.attr('method', 'post');
		form.appendTo('body');
		     
		var pass1_ = $("<input type='hidden' value="+pass1+" name='pass1'>");
		var pass2_ = $("<input type='hidden' value="+pass2+" name='pass2'>");
		
		form.append(pass1_);
		form.append(pass2_);
		form.submit();
	});
});
</script>

</body>
</html>