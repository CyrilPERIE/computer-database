<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<style><%@include file="../css/bootstrap.min.css"%></style>
<style><%@include file="../css/font-awesome.css"%></style>
<style><%@include file="../css/main.css"%></style>

<script><%@include file="../js/addComputer.js"%></script>
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <form action="addComputer" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" name ="computerName" class="form-control" id="computerName" placeholder="Computer name" required>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" name="introducedDate" class="form-control" id="introduced" placeholder="Introduced date"
                                onchange = limitMinDate(this.value) value ="">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" name="discontinuedDate" class="form-control" id="discontinued" placeholder="Discontinued date" 
                                onchange = limitMaxDate(this.value) value ="">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select name="companyId" class="form-control" id="companyId" >
                                    <c:forEach items="${companies}" var="company">
                                    	<option value="${company.id}"><c:out value="${company.name}"/></option>
                                    </c:forEach>
                                </select>
                                <span class = "error">${errorsForRequest["companyNameField"]}</span>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                        	<span class = "error">${errorsForRequest["global"]}</span>
                            <input type="submit" name = "addComputer" value="Add" class="btn btn-primary">
                            or
                            <a href="/cdb/dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>