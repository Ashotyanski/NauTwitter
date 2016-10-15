<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="ru.urfu.user.model.Role" %>
<t:base>
    <%--
        <p>
            <form:label path="username">Username</form:label>
            <form:input path="username"/>
        </p>
        <p>
            <form:label path="password">Password</form:label>
            <form:password path="password"/>
        </p>
        <form:select path="role" items="${roles}"/>
        &lt;%&ndash;
                <input type="hidden"
                       name="${_csrf.parameterName}" value="${_csrf.token}" />

        &ndash;%&gt;
    --%>


    <div class="form-container">
        <h2> Register form</h2>
        <div class="form-wrapper">
            <form:form modelAttribute="user" action="register" method="post" class="form-horizontal">
                <spring:bind path="username">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label class="col-sm-2 control-label">Username</label>
                        <div class="col-sm-10">
                            <form:input type="text" path="username" class="form-control" placeholder="Username"
                                        autofocus="true"></form:input>
                            <form:errors path="username"></form:errors>
                        </div>
                    </div>
                </spring:bind>

                <spring:bind path="password">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label class="col-sm-2 control-label">Password</label>
                        <div class="col-sm-10">

                            <form:input type="password" path="password" class="form-control"
                                        placeholder="Password"></form:input>
                            <form:errors path="password"></form:errors>
                        </div>

                    </div>
                </spring:bind>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-3">
                        <form:select path="role" class="form-control" items="<%= Role.values() %>"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">Sign up</button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>

</t:base>