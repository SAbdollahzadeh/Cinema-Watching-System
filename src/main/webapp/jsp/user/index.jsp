<%@ page import="ir.maktabsharif.cinemawatchingsystem.model.RegularUser" %>
<%@ page import="javax.print.attribute.standard.RequestingUserName" %>
<%@ page import="ir.maktabsharif.cinemawatchingsystem.model.User" %>
<%@ page import="ir.maktabsharif.cinemawatchingsystem.model.Admin" %>
<%@ page import="ir.maktabsharif.cinemawatchingsystem.model.Movie" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="ir.maktabsharif.cinemawatchingsystem.enums.Genre" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Movie Library - User View</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<style>
        body {
            background: #f3f5f9;
        }

        .navbar-custom {
            background: linear-gradient(90deg, #1f1f1f, #343434);
        }

        .movie-card {
            border-radius: 14px !important;
            overflow: hidden;
            background: #ffffff;
            border: 1px solid #e2e6ea;
        }

        .movie-card:hover {
            transform: translateY(-3px);
            transition: 0.2s ease;
            box-shadow: 0 4px 14px rgba(0, 0, 0, 0.15);
        }

        .alert-info {
            background: #d9ecff;
            border-color: #b8dbff;
            color: #0b3c68;
        }

        .input-group-text, .form-control, .form-select {
            background: #ffffff;
            border: 1px solid #ced4da;
            color: #212529;
        }

        .btn-primary {
            background: #0d6efd;
            border-color: #0d6efd;
        }

        .btn-primary:hover {
            background: #0b5ed7;
            border-color: #0a58ca;
        }

        .user-info img {
            width: 38px;
            height: 38px;
            object-fit: cover;
            border-radius: 50%;
            border: 1px solid #fff;
        }

        .user-info span {
            color: #fff;
            font-weight: 600;
        }
	</style>
</head>
<body>

<%
	User user = (User) request.getSession().getAttribute("user");
	boolean hasUser = user != null ? true : false;
	boolean isAdmin = user instanceof Admin;
	pageContext.setAttribute("isAdmin", isAdmin);
	String role = user instanceof RegularUser ? "User-" + ((RegularUser) user).getUserLevel() : "Admin-" + ((Admin) user).getAdminLevel();
	pageContext.setAttribute("role", role);
%>

<!-- Navbar -->
<nav class="navbar navbar-dark navbar-custom py-2">
	<div class="container-fluid d-flex justify-content-between align-items-center">

		<!-- Logo -->
		<span class="navbar-brand h5 m-0">🎬 Movie Library</span>

		<!-- Search Form -->
		<form class="d-flex align-items-center gap-2">
			<div class="input-group input-group-sm">
				<span class="input-group-text">🔍</span>
				<input type="text" class="form-control" placeholder="Title" name="title">
			</div>
			<select class="form-select form-select-sm" style="min-width: 110px;" name="genre">
				<option value="">Genre</option>
				<c:forEach var="genre" items="<%=Arrays.stream(Genre.values()).map(g->{
                    String str = g.toString(); return str.substring(0,1).toUpperCase().concat(str.substring(1).toLowerCase());
                }).toList()%>">
					<option value="${fn:toUpperCase(pageScope.genre)}">${genre}</option>
				</c:forEach>
			</select>
			<button class="btn btn-primary btn-sm" type="submit">Search</button>
		</form>

		<div class="d-flex align-items-center gap-2">
			<c:choose>
				<c:when test="${isAdmin}">
					<a href="${pageContext.request.contextPath}/app/movie/add" class="btn btn-success btn-sm">
						➕ Add Movie
					</a>
				</c:when>
				<c:otherwise>
					<a href="${pageContext.request.contextPath}/app/user/watchlist" class="btn btn-success btn-sm">
						🎬 Watchlist
					</a>
				</c:otherwise>
			</c:choose>


			<a href="${pageContext.request.contextPath}/app/user/edit" class="btn btn-warning btn-sm">📝 Edit Profile</a>

			<a href="${pageContext.request.contextPath}/app/user/logout" class="btn btn-dark btn-sm">↩️ Logout</a>

			<div class="d-flex align-items-center gap-2 ms-2">
				<img src="data:image/png;base64,${empty user.profilePicture ? '' : user.profilePicture}"
				     alt="Profile Image" class="rounded-circle border"
				     style="width:38px; height:38px; object-fit:cover;">
				<span class="text-white fw-bold"><c:out value="${user.username}"/></span>
			</div>

		</div>
	</div>
</nav>

<div class="alert alert-info py-1 mb-3" role="alert">
	Logged in as <strong><c:out value="${user.username}"/></strong> (Role: <c:out value="${pageScope.role}"/>)
</div>
<c:if test="${not empty requestScope.message}">
	<div class="alert
        <c:choose>
            <c:when test='${requestScope.messageType == "success"}'>alert-success</c:when>
            <c:when test='${requestScope.messageType == "error"}'>alert-danger</c:when>
            <c:otherwise>alert-info</c:otherwise>
        </c:choose>
        py-1 mb-3" role="alert">

		<c:out value="${requestScope.message}"/>
	</div>
</c:if>

<div class="container my-4">
	<div class="row g-4">

		<c:forEach var="movie" items="${requestScope.movies}">
			<div class="col-lg-3 col-md-6 col-sm-12">
				<div class="card movie-card shadow-sm border-0 h-100 d-flex flex-column">
					<div class="ratio ratio-4x3">
						<img src="data:image/png;base64,${movie.picture}" class="img-fluid" alt="${movie.title}">
					</div>
					<div class="card-body d-flex flex-column">
						<h5 class="card-title fw-bold">${movie.title}</h5>
						<p class="card-text text-muted small mb-1">${movie.genre}</p>
						<%
							String releaseDate = ((Movie) pageContext.getAttribute("movie")).getReleaseDate()
									.format(DateTimeFormatter.ISO_LOCAL_DATE);
							pageContext.setAttribute("releaseDate", releaseDate);
						%>
						<p class="card-text text-muted small mb-2">📅 ${releaseDate}</p>
						<p class="mb-2">
							<span class="text-warning fs-5">⭐</span>
							<span class="fw-bold">${movie.rating}</span>
						</p>
						<p class="small text-dark flex-grow-1">${movie.description}</p>
						<c:choose>
							<c:when test="${isAdmin}">
								<div class="d-flex gap-2 mt-auto">
									<a class="btn btn-outline-warning btn-sm w-50 d-flex align-items-center justify-content-center"
									   href="${pageContext.request.contextPath}/app/movie/edit?id=${movie.id}">
										✏️ Edit
									</a>
									<a class="btn btn-outline-danger btn-sm w-50 d-flex align-items-center justify-content-center"
									   href="${pageContext.request.contextPath}/app/movie/delete?id=${movie.id}">
										🗑️ Delete
									</a>
								</div>
							</c:when>
							<c:otherwise>
								<a class="btn btn-outline-success btn-sm w-100 mt-auto"
								   href="${pageContext.request.contextPath}/app/user/add-watchlist-movie?id=${movie.id}">
									💚 Add to Watchlist
								</a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</c:forEach>

	</div>
</div>
</body>
</html>
