<%@ page import="java.util.Arrays" %>
<%@ page import="ir.maktabsharif.cinemawatchingsystem.enums.Genre" %>
<%@ page import="ir.maktabsharif.cinemawatchingsystem.model.Movie" %>
<%@ page import="java.time.format.DateTimeFormatterBuilder" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Add Movie</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">


<nav class="navbar navbar-dark bg-dark">
	<div class="container-fluid">
		<a class="navbar-brand mx-auto" href="#">Movies</a>
	</div>
</nav>

<%
	Movie movie = (Movie) request.getAttribute("movie");
	pageContext.setAttribute("movie", movie);
	boolean hasMovie = movie != null;
	pageContext.setAttribute("hasMovie", hasMovie);
	if (movie != null) {
		pageContext.setAttribute("releaseDate", movie.getReleaseDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
	}
%>

<div class="container my-5">
	<div class="row justify-content-center">
		<div class="col-md-8 col-lg-6">
			<div class="card shadow-sm p-4">
				<h2 class="mb-4 text-center">Add Movie</h2>

				<c:if test="${not empty message}">
					<div class="alert alert-info">${message}</div>
				</c:if>

				<form action="${pageContext.request.contextPath}${hasMovie? '/app/movie/edit':'/app/movie/add'}" method="post"
				      enctype="multipart/form-data">
					<div class="mb-3">
						<label class="form-label">Title</label>
						<input name="title" type="text" class="form-control" placeholder="Enter movie title" required
						       value="${movie != null? movie.title: ''}"
						       oninvalid="this.setCustomValidity('Title is required!')"
						       oninput="this.setCustomValidity('')">
					</div>

					<div class="mb-3">
						<label class="form-label">Description</label>
						<textarea name="description" class="form-control" rows="3"
						          placeholder="Enter movie description">
							${movie != null? movie.description: ""}
						</textarea>
					</div>

					<div class="mb-3">
						<label class="form-label">Release Date</label>
						<input value="${movie==null?'':releaseDate}" name="releaseDate" type="date" class="form-control"
						       required>
					</div>

					<div class="mb-3">
						<label class="form-label">Genre</label>
						<select name="genre" class="form-select">
							<c:forEach var="genre"
							           items="<%=Arrays.stream(Genre.values()).map(
                                               g->{
                                                   String str = g.toString();
                                                   return str.substring(0,1).toUpperCase().concat(str.substring(1).toLowerCase());}).toList()%>">
								<option <c:out
										value="${movie!=null and Genre.valueOf(genre.toUpperCase())== movie.genre ? 'selected':''}"/>
										value="${fn:toUpperCase(pageScope.genre)}">
										${genre}
								</option>
							</c:forEach>
						</select>
					</div>
					<c:if test="${hasMovie}">
						<input type="hidden" value="${movie.id}" name="movie-id">
					</c:if>
					<div class="mb-3">
						<label class="form-label">Poster Image</label>
						<input name="image" type="file" class="form-control" id="posterInput" accept="image/*">
						<div class="mt-2 text-danger" id="posterError"></div>

						<div class="mt-3 text-center">
							<img id="posterPreview"
							     src="${hasMovie?'data:image/png;base64,'.concat(movie.picture) : 'movie.jpeg'}"
							     alt="Poster Preview"
							     class="img-fluid rounded" style="max-height: 250px;">
						</div>
					</div>

					<div class="d-flex justify-content-start">
						<button type="submit" class="btn btn-primary me-2">${movie==null? 'Create':'Edite'}</button>
						<a href="${pageContext.request.contextPath}/user/index" class="btn btn-secondary">Cancel</a>
					</div>
				</form>

			</div>
		</div>
	</div>
</div>

<script>
    const posterInput = document.getElementById('posterInput');
    const posterPreview = document.getElementById('posterPreview');
    const posterError = document.getElementById('posterError');

    posterInput.addEventListener('change', function () {
        const file = this.files[0];
        posterError.textContent = '';

        if (file) {
            if (!file.type.startsWith('image/')) {
                posterError.textContent = 'Please select a valid image file.';
                posterPreview.src = 'movie.jpeg';
                return;
            }

            const reader = new FileReader();
            reader.onload = function (e) {
                posterPreview.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });
</script>

</body>
</html>

