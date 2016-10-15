<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="header" fragment="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Twitter</title>
    <link rel="stylesheet" href="/css/twitter.css">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <script src="/js/bootstrap.min.js"></script>
</head>
<body>
<main>
    <header>
        <jsp:include page="../includes/header.jsp"/>
    </header>

    <jsp:doBody/>

</main>
</body>
</html>