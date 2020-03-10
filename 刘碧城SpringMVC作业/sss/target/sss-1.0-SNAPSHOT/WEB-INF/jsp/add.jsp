<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="./common/header.jsp"%>
<body>
<%@include file="./common/nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div id="myBreadcrumb">
                <ul class="breadcrumb">
                    <li class="active">添加用户</li>
                </ul>
            </div>

            <br>
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
    </div>
</div>
<%@include file="./common/footer.jsp"%>