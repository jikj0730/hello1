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
					<div class="panel-title">환영합니다!</div>
				</div>
				<div class="panel-body">
					<form id="login-form" method="post">
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
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>