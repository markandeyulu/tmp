<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" contentType="text/html; charset=UTF-8" %>

<html>
	<head>
		<title>IAUT Resource Application Login</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta charset="utf-8" />
		<script src="<c:url value="/js/jquery112.js" />" type="text/javascript"></script>
		<script src="<c:url value="/js/cryptoJS.js" />" type="text/javascript"></script>
		<link href="<c:url value="/css/sb-admin.css" />" rel="stylesheet"/>
		<link href="<c:url value="/css/font-awesome.min.css" />" rel="stylesheet"/>
		<link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet"/>
		
		
		<script>
			$(document).ready(function () {
				var errorMessage = ${errorMsg};
				var logoutMessage = ${message};
				if (undefined != errorMessage) {
					if(errorMessage==true){
						$("#errorMsg").text("Incorrect login user name or password. Please try again");
					}else if(errorMessage==false){
						$("#errorMsg").text("");
					}else if(logoutMessage==true){
						$("#errorMsg").text("You have successfully logged off from application !");
					}
				}
				
				$('#submit').click(function (){
					var text = $("#password").val();
					var secret = "TMP SECRET";
					var encrypted = CryptoJS.AES.encrypt(text, secret);
					encrypted = encrypted.toString();
					$("#password").val(encrypted);
					console.log(text + " Cipher text: " + encrypted);
				});
			});
		</script>
	
	</head>
	
	<body class="bg-dark">
	
		<div class="container">
			<div class="card card-login mx-auto mt-5">
				<div class="card-header"
					style="background-color: #b30000; color: white;">
					<Strong>Login</Strong>
				</div>
				<div class="card-body">
					<spring:form  name='loginForm' id='loginForm' action="login" method='POST' modelAttribute="login">
					<div id="errorMsg" style="display: inline-block; margin-left:10%; color:#ff0000; ">
						</div>
						<div class="form-group">
							<spring:label path="name">User Name</spring:label> 
							<spring:input class="form-control" errorMessage="User Name is required" required="required" path="name" id="username" type="text" aria-describedby="emailHelp" placeholder="Enter User Name"/>
						</div>
						<div class="form-group">
							<spring:label path="password">Password</spring:label> 
							<spring:input class="form-control" errorMessage="Password is required"  required="required" path="password" id="password" type="password" placeholder="Password"/>
						</div>
						<td><Button id="submit" name="submit" class="btn btn-md button1" style="background-color:#b30000;color:white;" type="submit">Submit</Button></td>
					</spring:form>
				</div>
			</div>
		</div>
	</body>
</html>