<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2018-04-07
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>系统主页</title>
</head>
<body>
<h2>我是服务器：${pageContext.request.localPort}</h2>
<h2>当前sessionId：${pageContext.session.id}</h2>
<a href="${pageContext.request.contextPath}/add">
    <button type="button" class="btn btn-info">添加</button>
</a>
<br/>

<table border="1">
    <tr>
        <th>id</th>
        <th>name</th>
        <th>address</th>
        <th>phone</th>
    </tr>
    <c:forEach var="resume" items="${resume}">
    <tr>
        <td>${resume.id}</td>
        <td>${resume.name}</td>
        <td>${resume.address}</td>
        <td>${resume.phone}</td>
        <td>
            <a href="${pageContext.request.contextPath}/delete?id=${resume.id}">
                <button type="button" class="btn btn-primary btn-xs">删除</button>
            </a>
            <a href="${pageContext.request.contextPath}/edit/${resume.id}">
                <button type="button" class="btn btn-primary btn-xs">编辑</button>
            </a>
        </td>
    </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/logout">退出</a>
</body>
</html>