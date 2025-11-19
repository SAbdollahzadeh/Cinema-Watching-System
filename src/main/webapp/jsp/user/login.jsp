<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Simple Login Form</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container">
	<div class="row justify-content-center align-items-center vh-100">
		<div class="col-md-4">
			<div class="card shadow-sm">
				<div class="card-body p-4">
					<h3 class="card-title text-center mb-4">Login</h3>

					<div class="message-box text-center mb-3 overflow-auto" style="height: 60px;">
						<% String message = (String) request.getAttribute("message"); %>
						<% String messageType = (String) request.getAttribute("messageType"); %>

						<% if (message != null) { %>
						<div class="alert <%= "success".equals(messageType) ? "alert-success" : "alert-danger" %> py-2 px-3 mb-0">
							<%= message %>
						</div>
						<% } %>
					</div>

					<form method="post" action="${pageContext.request.contextPath}/app/user/login">
						<div class="mb-3">
							<label for="username" class="form-label">Username</label>
							<input type="text" class="form-control" id="username" name="username" placeholder="Enter your username" required>
						</div>

						<div class="mb-3">
							<label for="password" class="form-label">Password</label>
							<input type="password" class="form-control" id="password" name="password" placeholder="Enter your password" required>
						</div>

						<div class="mb-3 form-check">
							<input type="checkbox" class="form-check-input" name="remember-me" id="remember-me">
							<label class="form-check-label" for="remember-me">Remember me</label>
						</div>

						<div class="d-grid">
							<button type="submit" class="btn btn-primary">Login</button>
						</div>
					</form>

					<div class="mt-3 text-center">
						<a href="<%= request.getContextPath()%>/app/user/signup">Forgot password?</a>
					</div>

					<div class="mt-2 text-center">
						<span>
							Don't have an account?
							<a href="<%=request.getContextPath()%>/app/user/signup">
								Sign up
							</a>
						</span>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>
