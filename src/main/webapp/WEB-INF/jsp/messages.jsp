<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:base>
    <div class="table-container">
        <div class="table-wrapper">
            <p>
                <a href="/message/create" role="button" class="btn btn-primary btn-sm">Create message</a>
            </p>
            <table class="table">
                <thead>
                <tr>
                        <%--<th>#</th>--%>
                    <th>Username</th>
                    <th>Message</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${messages}" var="message">
                    <tr>
                        <td>${message.author}</td>
                        <td>${message.body}</td>
                        <td>
                            <a href="/message/${message.id}" role="button" class="btn btn-success btn-xs">View</a>
                            <c:if test="${pageContext.request.userPrincipal.name == message.author || isAdmin==true}">
                                <a href="/message/delete?id=${message.id}" role="button" class="btn btn-danger btn-xs">Delete</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    </main>
</t:base>