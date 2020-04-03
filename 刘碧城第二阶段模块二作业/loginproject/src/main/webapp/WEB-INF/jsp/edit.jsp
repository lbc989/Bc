<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>编辑用户</title>
</head>
<body>


        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <br>
            <form class="form-horizontal" id="addUserForm" method="post"
                  action="${pageContext.request.contextPath}/editUser">
                <input type="hidden" name="id" id="id" value="${resumeDTO.id}">
                <div class="form-group">
                    <label for="address" class="col-sm-2 control-label">address</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="address" id="address" placeholder="address"
                               value="${resumeDTO.address}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">name</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="name" id="name" placeholder="name"
                               value="${resumeDTO.name}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="phone" class="col-sm-2 control-label">phone</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="phone" id="phone" placeholder="phone"
                               value="${resumeDTO.phone}">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary">保存</button>
                    </div>
                </div>
            </form>
        </div>

</body>
</html>