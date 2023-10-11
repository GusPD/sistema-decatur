<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Iniciar sesión</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/recursosOnline/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/recursosOnline/css/adminlte.css">
    ${_csrfMetaTags}
</head>
<body>
    <div class="container background-login">
        <div class="row col-xs-12 col-sm-12 col-md-12 d-flex align-items-center justify-content-center vh-100">
            <div class="login-box">    
                <div class="login-logo">
                    <b>Admin</b>LTE
                </div>
                <!-- Mostrar mensaje de error si está presente en la sesión -->
                <c:choose>
                    <c:when test="${not empty error}">
                        <div id="errorMessageContainer" class="alert alert-danger mt-3">
                            ${error}
                        </div>
                        <c:remove var="errorMessage" />
                    </c:when>
                    <c:otherwise>
                        <div class="card card-outline card-primary">        
                            <div class="card-header d-flex align-items-center justify-content-center">
                                <h3 class="card-title text-blue">Restablecer Contraseña</h3>
                            </div>
                            <div class="card-body login-card-body ">
                                <form id="form-reset-password" action="/reset-password-new" method="POST">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                    <input type="hidden" name="token" id="token" value="${token}">
                                    <div class="input-group mb-3">
                                        <input type="password" name="password" id="password" class="form-control" placeholder= "Ingrese la nueva contraseña" required>
                                        <div class="input-group-append" id="password-toggle-new" onclick="togglePasswordVisibilityNew()">
                                            <div class="input-group-text">
                                                <span id="icono-candado" class="fas fa-lock"></span>
                                                <span id="icono-ver" class="fas fa-eye"></span>
                                            </div>
                                        </div>
                                        <div id="group-password-new" class="d-none"></div>
                                    </div>
                                    <div class="input-group mb-3">
                                        <input type="password" name="passwordconfirm" id="passwordconfirm" class="form-control" placeholder= "Confirme la nueva contraseña" required>
                                        <div class="input-group-append" id="password-toggle-confirm" onclick="togglePasswordVisibilityConfirm()">
                                            <div class="input-group-text">
                                                <span id="icono-candado" class="fas fa-lock"></span>
                                                <span id="icono-ver-confirm" class="fas fa-eye"></span>
                                            </div>
                                        </div>
                                        <div id="group-password-confirm" class="d-none"></div>
                                    </div>
                                    <div class="row">
                                        <div class="col-7">
                                        </div>
                                        <div class="col-5">
                                            <button type="submit" class="btn btn-block btn-flat btn-blue">
                                                <span class="fa-solid fa-key"></span>
                                                Aceptar
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="card-footer "></div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/recursosOnline/js/jquery-3.7.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/recursosOnline/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/recursosOnline/js/kit.fontawesome.com.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/recursosOnline/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/reset-password.js"></script>
</body>
</html>

