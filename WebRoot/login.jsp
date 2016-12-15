<!DOCTYPE html>
<html lang="en">
<head>
<title>Login Page</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="css/customStyle.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="mainDiv">
		<img src="images/GE_bg.png" />
		<div class="loginpage">
			<form name="loginForm" method="POST" action="j_security_check">
				<table>
					<tr>
						<td><label>User Name</label></td>
						<td><input type="text" name="j_username" size="20" /></td>
					</tr>
					<tr>
						<td><label>Password</label></td>
						<td><input type="password" size="20" name="j_password" /></td>
					</tr>
				</table>
				<input type="submit" value="Submit" />
			</form>
		</div>
	</div>
</body>
</html>
