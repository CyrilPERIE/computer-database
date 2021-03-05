<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="../../cdb/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../../cdb/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../../cdb/css/main.css" rel="stylesheet" media="screen">

</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.html"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${numberOfComputers} Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> 
						<input type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="/cdb/addComputer">Add
						Computer</a> 
					<a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><a href = "dashboard?orderBy=computer.name">Computer name</a></th>
						<th><a href = "dashboard?orderBy=computer.introduced">Introduced date</a></th>
						<!-- Table header for Discontinued Date -->
						<th><a href = "dashboard?orderBy=computer.discontinued">Discontinued date</a></th>
						<!-- Table header for Company -->
						<th><a href = "dashboard?orderBy=company.name">Company</a></th>

					</tr>
					<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers}" var="computer">

						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a href="<c:url value="editComputer">
							<c:param name="id" value="${computer.id}"/></c:url>">${computer.name}</a>
							</td>
							<td>${computer.introducedDate}</td>
							<td>${computer.discontinuedDate}</td>
							<td>${computer.manufacturer.name}</td>

						</tr>

					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><a
					href="dashboard?go=previous" aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				
				<c:forEach items="${indexPage}" var="index">
					<li><a href="dashboard?page=${index}">${index}</a></li>
				</c:forEach>
				
				<li><a href="dashboard?go=next" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>


			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href="dashboard?limit=10" class="btn btn-default">10</a>
				<a href="dashboard?limit=50" class="btn btn-default">50</a>
				<a href="dashboard?limit=100" class="btn btn-default">100</a>
			</div>
		</div>

	</footer>
	
	<script src="../../cdb/js/jquery.min.js"></script>
	<script src="../../cdb/js/bootstrap.min.js"></script>
	<script src="../../cdb/js/dashboard.js"></script>

</body>
</html>