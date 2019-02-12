<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
	<div class="container">
		<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-success">
				<div class="panel-heading">
					<div class="panel-title">로그인</div>
				</div>
				<div class="panel-body">
					<form id="login-form" method="post" action="/login_processing">
						<div>
							<input type="text" class="form-control" name="userId"
								placeholder="Username" autofocus>
						</div>
						<div>
							<input type="password" class="form-control" name="password"
								placeholder="Password">
						</div>
						<div>
							<button type="submit" class="form-control btn btn-primary">로그인</button>
						</div>
						<div>
							<a data-target="#myModal" data-toggle="modal">
								<button id="signup" type="button"
									class="form-control btn btn-info">회원가입</button>
							</a>

						</div>
					</form>
				</div>

				<!-- Modal -->
				<div class="modal fade" id="myModal" role="dialog">
					<div class="modal-dialog">

						<!-- Modal content-->
						<div class="modal-content">
							<div class="modal-header">

								<h4 class="modal-title">회원가입</h4>
								<button type="button" class="close" data-dismiss="modal">×</button>
							</div>
							<div class="modal-body">
								<form>
									<div class="form-group">
										<label for="exampleInputEmail1">아이디</label> <input
											type="text" class="form-control" id="inputId"
											placeholder="아이디를 입력하세요">
									</div>
									<div class="form-group">
										<label for="exampleInputPassword1">암호 - 숫자와 문자를 포함하여 6~12자리</label> <input
											type="password" class="form-control"
											id="inputPassword" placeholder="암호">
									</div>
									<button type="button" id="button1" class="btn btn-default">가입하기</button>
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Close</button>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
	$(document).ready(function(){
		function checkVal(){
			
			var idPattern = /^[A-Za-z]{1}[A-Za-z0-9]{3,19}$/; //아이디 4~20 자리, 첫글자 숫자 불가
			var passPattern = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/; //비밀번호 숫자와 문자 포함하여 6~12자리
			
	 		if($('#inputId').val()==''){
	 			alert("아이디를 입력해주세요.");
	 			return false;	
	 		}else if($('#inputPassword').val()==''){
	 			alert("비밀번호를 입력해주세요.");
	 			return false;	
	 		}else if(!idPattern.test($('#inputId').val())){
	 			alert("옳지 않은 아이디입니다.");
	 			return false;
	 		}else if(!passPattern.test($('#inputPassword').val())){
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
			
			var sendData;
			sendData = JSON.stringify({
	            "userId":			$('#inputId').val(),
	            "password": 			$('#inputPassword').val()
	        });		
			
			$.ajax({
    			url: "signup",
    			method: "POST",
    			data: sendData,
    			dataType: "text",
    			contentType: "application/json;charset=UTF-8",
    			beforeSend: function(){
    				if(confirm('회원가입 하시겠습니까?')){	
						return true;
					}
					else{
						return false;
					}    				
    			},
    			success:function(data){
    				if(data){
    					alert(data+"가입에 성공하셨습니다.");
    					location.reload();
    				}else{
    					alert("에러1");
    				}
				},
				error: function(jqXHR, textStatus, errorThrown){
            		alert(jqXHR+"\n"+jqXHR.status+"\n"+jqXHR.statusText+"\n"+jqXHR.responseText+"\n"+jqXHR.readyState);
         	   }
			});
		});
	});
	</script>

</body>
</html>