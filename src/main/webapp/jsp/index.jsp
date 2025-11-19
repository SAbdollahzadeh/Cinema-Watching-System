
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


<nav class="navbar navbar-dark navbar-custom py-2">
	<div class="container-fluid d-flex justify-content-between align-items-center">

		<span class="navbar-brand h5 m-0">🎬 Movie Library</span>

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
			<a href="${pageContext.request.contextPath}/app/user/login"
			   class="btn btn-primary btn-sm d-flex align-items-center gap-2 px-3">
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
				     class="bi bi-box-arrow-in-right" viewBox="0 0 16 16">
					<path fill-rule="evenodd"
					      d="M6 3.5a.5.5 0 0 1 .5-.5h5A1.5 1.5 0 0 1 13 4.5v7a1.5 1.5 0 0 1-1.5 1.5h-5a.5.5 0 0 1 0-1h5a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.5-.5h-5A.5.5 0 0 1 6 3.5z"/>
					<path fill-rule="evenodd"
					      d="M.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L1.707 7.5H10a.5.5 0 0 1 0 1H1.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z"/>
				</svg>

				Login
			</a>
		</div>
	</div>
</nav>

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
						<p class="card-text text-muted small mb-2">
							📅 ${movie.releaseDate.format(DateTimeFormatter.ISO_LOCAL_DATE)}</p>
						<p class="mb-2">
							<span class="text-warning fs-5">⭐</span>
							<span class="fw-bold">${movie.rating}</span>
						</p>
						<p class="small text-dark flex-grow-1">${movie.description}</p>
					</div>
				</div>
			</div>
		</c:forEach>

	</div>
</div>
</body>
</html>
