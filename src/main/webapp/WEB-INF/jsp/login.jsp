<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Iniciar sesión</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="${pageContext.request.contextPath}/images/img/favicon-logo.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/RecursosOnline/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/images/iconos/fontawesome-free-6.4.2-web/css/all.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/RecursosOnline/adminlte.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
        ${_csrfMetaTags}
    </head>
    <body>
        <!-- Contenido del sistio -->
        <div class="container background-login">
            <div class="row col-xs-12 col-sm-12 col-md-12 d-flex align-items-center justify-content-center vh-100">
                <!-- Modal de login -->
                <div class="login-box"> 
                    <!-- Logo -->
                    <div class="login-logo">
                         <img id="login-logo" src="${pageContext.request.contextPath}/images/img/logo.png" alt="Logo Decatur">
                    </div>
                    <!-- Mensaje de error -->
                    <c:if test="${not empty sessionScope.errorMessage or not empty error}">
                        <div id="errorMessageContainer" class="alert alert-danger mt-3">
                            ${sessionScope.errorMessage}${error}
                        </div>
                    <c:remove var="errorMessage" scope="session" />
                    </c:if>
                    <!-- Mensaje de success -->
                    <c:if test="${not empty mensaje}">
                        <div id="successMessageContainer" class="alert alert-success mt-3">
                            ${mensaje}
                        </div>
                    <c:remove var="succesMessage" />
                    </c:if>
                    <div class="card card-outline card-primary">        
                        <div class="card-header header-login d-flex align-items-center justify-content-center">
                            <h3 class="card-title text-blue">Iniciar Sesión</h3>
                        </div>
                        <div class="card-body login-card-body ">
                            <form id="form-login" action="/authenticate" method="POST">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                <div class="input-group mb-3">
                                    <input type="text" name="username" id="username" class="form-control" placeholder= "Ingrese el nombre de usuario" required autofocus autocomplete="username">
                                    <div class="input-group-append">
                                        <div class="input-group-text">
                                            <span class="fa-solid fa-user"></span>
                                        </div>
                                    </div>
                                    <div id="group-username" class="d-none"></div>
                                </div>
                                <div class="input-group mb-3">
                                    <input type="password" name="password" id="password" class="form-control" placeholder= "Ingrese la contraseña" required autocomplete="current-password">
                                    <div class="input-group-append" id="password-toggle" onclick="togglePasswordVisibility()">
                                        <div class="input-group-text">
                                            <span id="icono-candado" class="fas fa-lock"></span>
                                            <span id="icono-ver" class="fas fa-eye"></span>
                                        </div>
                                    </div>
                                    <div id="group-password" class="d-none"></div>
                                </div>
                                <div class="row">
                                    <div class="col-7">
                                        <div class="icheck-primary" title="Keep me authenticated indefinitely or until I manually logout">
                                            <input type="checkbox" name="remember" id="remember">
                                            <label for="remember">Recordarme</label>
                                        </div>
                                    </div>
                                    <div class="col-5">
                                        <button type="submit" class="btn btn-block btn-flat btn-blue">
                                            <span class="fas fa-sign-in-alt"></span>
                                            Ingresar
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer ">       
                            <a class="link-reset-password" href="${pageContext.request.contextPath}/password/reset">Olvidé mi contraseña</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Script de la página -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/RecursosOnline/jquery-3.7.0.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/RecursosOnline/jquery.validate.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/RecursosOnline/bootstrap.bundle.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/images/iconos/fontawesome-free-6.4.2-web/js/all.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
    </body>
</html>

