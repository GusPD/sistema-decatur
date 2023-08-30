<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Iniciar sesión</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/recursosOnline/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    ${_csrfMetaTags}
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-sm-12 col-md-6 offset-md-3">
                <div class="login-form">
                    <h3 class="text-center">Iniciar sesión Decatur</h3>
        
                    <!-- Mostrar mensaje de error si está presente en la sesión -->
                    <c:if test="${not empty sessionScope.errorMessage}">
                        <div id="errorMessageContainer" class="alert alert-danger mt-3">
                        ${sessionScope.errorMessage}
                    </div>

                    <c:remove var="errorMessage" scope="session" />
                    </c:if>

                    <form action="/authenticate" method="POST">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <div class="mb-3">
                            <label for="username" class="form-label">Usuario</label>
                            <input type="text" name="username" id="username" class="form-control" placeholder= "Ingrese nombre de usuario" required autofocus>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Contraseña</label>
                        <div class="input-group">
                            <input type="password" name="password" id="password" class="form-control" placeholder= "Ingrese contraseña" required>
                                <button class="btn btn-outline-secondary" type="button" id="password-toggle" onclick="togglePasswordVisibility()">
                                <i id="eye-icon" class="far fa-eye"></i>
                            </button>
                        </div>
                        </div>

                        <div class="d-flex justify-content-end">
                            <button type="submit" class="btn btn-primary">Iniciar sesión</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/recursosOnline/js/kit.fontawesome.com.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/recursosOnline/js/jquery-3.7.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/recursosOnline/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
</body>
</html>

