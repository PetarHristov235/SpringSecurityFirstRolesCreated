<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <title>Thats the home page of my System Project</title>
</head>

<body>
<h2>Creator: Petar Hristov</h2>
<hr>

<p>
Feel more than welcome to see it! :)
</p>

<hr>

<!-- display username and role -->

<p>
    User: <security:authentication property="principal.username" />
    <br><br>
    Role(s): <security:authentication property="principal.authorities" />
</p>

<hr>

<!-- Add a link to point to /leaders ... this is for the managers -->

<%--If you want to make the hyperlink visible when an user is connected with a right role--%>
<%--paste the tag below--%>

<%--<securty:authorize access="hasRole('MANAGER')">--%>

    <p>
    <a href="${pageContext.request.contextPath}/leaders">Leadership Meeting</a>
    (Only for Manager peeps)
</p>
<%--</securty:authorize>--%>


<!-- Add a link to point to /systems ... this is for the admins -->
<p>
    <a href="${pageContext.request.contextPath}/systems">IT Systems Meeting</a>
    (Only for Admin peeps)
</p>


<hr>


<!-- Add a logout button -->
<form:form action="${pageContext.request.contextPath}/logout"
           method="POST">

    <input type="submit" value="Logout" />

</form:form>

</body>

</html>









