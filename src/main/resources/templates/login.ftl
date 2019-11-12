<!DOCTYPE html> 
<html lang="en"> 
<head> 
	<title>SpringBoot + Freemarker</title> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<link rel="stylesheet" href="" />
	<script th:src="@{/js/jquery-1.11.1.min.js}" type="text/javascript"></script>
</head> 
<body onload="document.f.username.focus();"> 
	<h1>welcome login</h1><br>
	<form name="f" action="/login" method="POST">
		<table>
			<tbody>
				<tr>
					<td>User:</td>
					<td><input type="text" name="username" value=""></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr>
					<td colspan="2"><input name="submit" type="submit"
						value="Login"></td>
				</tr>
			</tbody>
		</table>
	</form>
	<p>当前时间：${.now?string("yyyy-MM-dd HH:mm:ss.sss")}</p>
</body> 
</html>
