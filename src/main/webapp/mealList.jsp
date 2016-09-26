<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }

        dt {
            display: inline-block;
            margin-left: 30px;
            width: 100px;
        }

        dd {
            display: inline-block;
            vertical-align: top;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h3>Meal list</h3>
    <a href="meals?action=create">Add Meal</a>
    <hr>
    <form method="post" action="meals?action=filter">
        <dl>
            <dt>Date start:</dt>
            <dd><input type="date" name="dateStart"></dd>
            <dt>Time start:</dt>
            <dd><input type="time" name="timeStart"></dd>
        </dl>
        <dl>
            <dt>Date end:</dt>
            <dd><input type="date" name="dateEnd"></dd>
            <dt>Time end:</dt>
            <dd><input type="time" name="timeEnd"></dd>
        </dl>
        <dl>
            <dd>
                <button type="submit">Filter my meal!</button>
            </dd>
        </dl>
    </form>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>${meal.id}</td>
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>