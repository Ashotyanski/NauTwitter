<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
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
        <h2>Create message</h2>
        <div class="form-wrapper">
            <form:form modelAttribute="message" action="create" method="post" class="form-horizontal">
                <spring:bind path="body">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label class="col-sm-2 control-label">Message</label>

                        <div class="col-sm-10">
                            <form:textarea class="form-control" rows="3" placeholder="Your message" autofocus="true"
                                           path="body"></form:textarea>
                            <form:errors path="body"></form:errors>
                        </div>
                    </div>
                </spring:bind>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Create</button>
                </div>
            </form:form>
        </div>
    </div>

</t:base>