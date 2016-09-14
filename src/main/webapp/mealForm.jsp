<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Add meal / Edit meal</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<form method="POST" name="frmAddMeal">
    <br> Date : <input type="datetime-local" name="date" value="<c:out value="${meal.dateTime}" />" > <br />

    <br> Description : <input type="text" name="description" value="<c:out value="${meal.description}" />" > <br />

    <br> Calories : <input type="number" name="calories" value="<c:out value="${meal.calories}" />"> <br />

    <br> <input type="submit" value="Save">
</form>
</body>
</html>