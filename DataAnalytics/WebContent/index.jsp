<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>Data Analytics</title>
<link rel="stylesheet" href="CSS/index.css">
 <link rel = "icon" href ="img/logo.png"  type = "image/x-icon">
</head>
<body>
  <div class="login">
  <a class ="login-txt"><b>LOGIN</b></a>
    <input type="text" placeholder="Username" id="username" name="uid">  
  <input type="password" placeholder="Password" id="password" name="pass">  
  <div class ="floatleft">
  <input type="checkbox" id="remember-me" name="remember-me" value="remember-me" name="remember">
   <label for="remember-me"> Remember me</label><br>
   </div>
  <input type="submit" id ="btn-submit" value="Sign In" class ="floatleft">
</div>
<div class="shadow"></div>

<script src="js/jquery.js"></script>
<script src="js/cookies.js"></script>  
<script src="js/login.js"></script>
</body>

</html>
