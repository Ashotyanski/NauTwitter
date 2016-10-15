<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <div class="navigation">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <a class="navbar-brand" href="/">NauTwitter</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <c:if test="${pageContext.request.userPrincipal.name!=null}">
                            <li>
                                <p class="navbar-text">Signed in as ${pageContext.request.userPrincipal.name}</p>
                            </li>
                            <li>
                                <form action="/logout" method="post" class="navbar-form">
                                    <div class="form-group">
                                        <input type="hidden" class="form-control">
                                    </div>
                                    <button type="submit" class="btn btn-default">logout</button>
                                </form>
                            </li>
                        </c:if>
                        <li><a href="/register">Sign up</a></li>
                    </ul>
                </div>
            </div><!-- /.container-fluid -->
        </nav>
    </div>
</header>
