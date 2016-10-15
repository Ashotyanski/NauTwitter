<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base>
    <div class="main-container">
        <blockquote>
            <p>
                ${message.body}
            </p>
            <footer>Written by <cite title="Source Title">${message.author}</cite></footer>

        </blockquote>
        <p>
            <c:if test="${pageContext.request.userPrincipal.name == message.author}">
                <a href="/message/delete?id=${message.id}" role="button" class="btn btn-danger btn-sm">Delete</a>
            </c:if>
        </p>
    </div>
</t:base>