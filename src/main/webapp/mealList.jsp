<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="resources/css/mealList.css"/>

<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<table>
    <thead>
    <tr>
        <h3>
            <th>ID</th>
        </h3>
        <h3>
            <th>Date</th>
        </h3>
        <h3>
            <th>Description</th>
        </h3>
        <h3>
            <th>Calories</th>
        </h3>
        <h3>
            <th colspan="2" class="colAction">Action</th>
        </h3>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mealsWithExceeded}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
            <th class="colId">${meal.id}</th>
            <th class="colDate"><%=TimeUtil.FormattedDate(meal.getDateTime())%>
            <th class="colCalories">${meal.calories}</th>
            <th class="editButton"><a href="meals?action=edit&id=${meal.id}">Edit</a></th>
            <th class="deleteButton"><a href="meals?action=delete&id=${meal.id}">Delete</a></th>

                <%--<td><a href="meals?action=edit&userId=<c:out value="${meal.id}"/>">Update</a></td>--%>
                <%--<td><a href="meals?action=delete&userId=<c:out value="${meal.id}"/>">Delete</a></td>--%>
        </tr>
    </c:forEach>
    <%--<th><a href="meals?action=insert">Add Meal</a></th>--%>
    <th class="editButton"><a href="meals?action=insert&id=">Add Meal</a></th>
    </tbody>
</table>
</body>
</html>
