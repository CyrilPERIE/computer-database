<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="../../cdb/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../../cdb/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../../cdb/css/main.css" rel="stylesheet" media="screen">

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
                    <div class="label label-default pull-right">
                        id: ${editComputerFormInput.companyId}
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="editComputer" method="POST">
                        <input name="computerId" type="hidden" value="${editComputerFormInput.companyId}" id="id"/> 
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" name="computerName" id="computerName" placeholder="Computer name" value="${editComputerFormInput.computerName}" required>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" name="introducedDate" id="introduced" placeholder="Introduced date" name="introducedDate" value ="${editComputerFormInput.introducedDate}"
                                onload ="limitMinDate(this.value)" onchange=limitMinDate(this.value)>
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" name="discontinuedDate" id="discontinued" placeholder="Discontinued date" name="discontinuedDate" value ="${editComputerFormInput.discontinuedDate}"
                                 onload ="limitMaxDate(this.value)" onchange = limitMaxDate(this.value)>
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select name="companyId" class="form-control" id="companyId" >
                                	<option selected="selected" value="${editComputerFormInput.companyId}"><c:out value="${editComputerFormInput.companyName}"/></option>
                                    <c:forEach items="${companies}" var="company">
                                    	<option value="${company.id}"><c:out value="${company.name}"/></option>
                                    </c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="/cdb/dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="../../cdb/js/addComputer.js"></script>
</body>
</html>