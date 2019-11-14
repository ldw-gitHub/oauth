<!DOCTYPE html>
<html lang="en">
<head>
<title>SpringBoot + Freemarker</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="" />
<script th:src="@{/js/jquery-3.4.1.min.js}" type="text/javascript"></script>

<style>
body {
	text-align: center
}

#loginStyle {
	margin: 0 auto;
	border: 1px solid #000;
	width: 300px;
	height: 100px
}
</style>
<script type="text/javascript">
	function login() {
		var username = $("input[name='username']").val();
		var password = $("input[name='password']").val();

		$.ajax({
			url : "/login",
			data : {
				'username' : username,
				'password' : password
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				console.log(data);
				data = jQuery.parseJSON(data); //dataType指明了返回数据为json类型，故不需要再反序列化
			}
		});
	}
</script>


</head>
<body onload="document.f.username.focus();">
	<div id="loginStyle">
		<h1>登入授权页</h1>
		<br>
		<form action="/login" method="POST">
			<table>
				<tbody>
					<tr>
						<td>username :</td>
						<td><input type="text" name="username" value=""></td>
					</tr>
					<tr>
						<td>password :</td>
						<td><input type="password" name="password"></td>
					</tr>
					<tr>
						<td colspan="2"><button onclick="login()">登入</button></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>
