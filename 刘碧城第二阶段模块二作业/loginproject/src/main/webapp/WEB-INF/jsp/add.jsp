<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>添加用户</title>
</head>
<body>
<div class="container-fluid">
            <form class="form-horizontal" id="addResumeForm" method="post" action="${pageContext.request.contextPath}/addResume" >
                <div class="form-group">
                    <label for="address" class="col-sm-2 control-label">address <span class="text-danger">*</span></label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="address" id="address" placeholder="address" >
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">name <span class="text-danger">*</span></label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="name" id="name" placeholder="name" >
                    </div>
                </div>
                <div class="form-group">
                    <label for="phone" class="col-sm-2 control-label">phone</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="phone" id="phone" placeholder="phone">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary">添加</button>
                    </div>
                </div>
            </form>
        </div>
</body>
</html>