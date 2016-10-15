<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base>
    <div class="form-container">
        <h2>Login form</h2>
        <div class="form-wrapper">
            <c:if test="${error!=null}">
                <div class="alert alert-danger" role="alert">${error}</div>
            </c:if>
            <c:if test="${logout!=null}">
                <div class="alert alert-info" role="alert">${logout}</div>
            </c:if>
            <form class="form-horizontal" method="POST" action="/login">
                <div class="form-group">
                    <label class="col-sm-2 control-label">Username</label>
                    <div class="col-sm-10">
                        <input type="username" name="username" class="form-control" placeholder="Username">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">Password</label>
                    <div class="col-sm-10">
                        <input type="password" name="password" class="form-control" placeholder="Password">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">Sign in</button>
                    </div>
                </div>

            </form>
        </div>
    </div>
</t:base>