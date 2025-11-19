<%--
  Created by IntelliJ IDEA.
  User: SHS-WS
  Date: 11/3/2025
  Time: 1:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Signup Form</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container">
	<div class="row justify-content-center align-items-center vh-100">
		<div class="col-md-4">
			<div class="card shadow-sm">
				<div class="card-body p-4">
					<h3 class="card-title text-center mb-4">Sign Up</h3>

					<div class="message-box text-center mb-3 overflow-auto" style="height: 60px;">
						<% String message = (String) request.getAttribute("message"); %>
						<% String messageType = (String) request.getAttribute("messageType"); %>

						<% if (message != null) { %>
						<div class="alert <%= "success".equals(messageType) ? "alert-success" : "alert-danger" %> py-2 px-3 mb-0">
							<%= message %>
						</div>
						<% } %>
					</div>

					<form method="post"
					      action="${pageContext.request.contextPath}/app/user/signup">

						<div class="mb-3">
							<label for="username" class="form-label">Username</label>
							<input type="text" class="form-control" id="username" placeholder="Enter your username"
							       name="username" required>
						</div>

						<div class="mb-3">
							<label for="email" class="form-label">Email</label>
							<input type="email" class="form-control" id="email" placeholder="Enter your email"
							       name="email" required>
						</div>

						<div class="mb-3">
							<label for="password" class="form-label">Password</label>
							<input type="password" class="form-control" id="password" placeholder="Enter your password"
							       name="password" required>
						</div>

						<div class="d-grid">
							<button type="submit" class="btn btn-primary">Sign Up</button>
						</div>
					</form>

					<div class="mt-3 text-center">
						<span>Already have an account?
							<a href="${pageContext.request.contextPath}/app/user/login">Login</a>
						</span>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
