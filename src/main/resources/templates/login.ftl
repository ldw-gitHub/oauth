<!DOCTYPE html> 
<html lang="en"> 
<head> 
	<title>SpringBoot + Freemarker</title> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<link rel="stylesheet" href="" />
	<script th:src="@{/js/jquery-3.4.1.min.js}" type="text/javascript"></script>
</head> 
<body onload="document.f.username.focus();"> 
	<h1>welcome login</h1><br>
	<form action="/login" method="POST">
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
					<td colspan="2"><input name="submit" type="submit" value="登入"></td>
				</tr>
			</tbody>
		</table>
	</form>
</body> 
</html>
