<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Restablecer</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${pageContext.request.contextPath}/images/favicon-logo.ico" type="image/x-icon">
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
                     <img id="login-logo" src="${pageContext.request.contextPath}/images/logo.png" alt="Logo Decatur">
                </div>
                <div id="animacion-loading" class="animacion-loading">
                    <div class="loader">
                        <span class="dot"></span>
                        <span class="dot"></span>
                        <span class="dot"></span>
                    </div>
                </div>               
                <c:if test="${not empty mensaje}">
                    <div class="alert alert-success d-flex align-items-center alert-dismissible fade show" role="alert">
                        <strong><i class="bi bi-check-circle"></i> Éxito!</strong> ${mensaje}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger d-flex align-items-center alert-dismissible fade show" role="alert">
                        <strong><i class="bi bi-exclamation-triangle"></i> Error!</strong> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <div class="alert alert-success d-flex align-items-center alert-dismissible fade d-none" role="alert">
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    <strong><i class="bi bi-check-circle"></i> Éxito!&nbsp;</strong>
                </div>
                <div class="alert alert-danger d-flex align-items-center alert-dismissible fade d-none" role="alert">
                    <strong><i class="bi bi-exclamation-triangle"></i> Error!&nbsp;</strong>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <div class="card card-outline card-primary">  
                    <div class="card-header d-flex align-items-center justify-content-center">
                        <h3 class="card-title text-blue">Restablecer Contraseña</h3>
                    </div>
                    <div class="card-body login-card-body ">
                        <div>
                            <p class="text-align-justify">Por favor, introduzca el nombre de usuario de su cuenta, para proceder con el restablecimiento de la contraseña.</p>
                        </div>
                        <form id="form-reset-password">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <div class="input-group mb-3">
                                <input type="text" name="username" id="username" class="form-control" placeholder= "Ingrese el nombre de usuario" maxlength="150" required autofocus>
                                <div class="input-group-append">
                                    <div class="input-group-text">
                                        <span class="fa-solid fa-user"></span>
                                    </div>
                                </div>
                                <div id="group-username" class="d-none"></div>
                            </div>
                            <div class="row">
                                <div class="col-7"></div>
                                <div class="col-5">
                                    <button type="submit" class="btn btn-block btn-flat btn-blue">
                                        <span class="fa-solid fa-paper-plane"></span>
                                        Enviar
                                    </button>   
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/recursosOnline/js/jquery-3.7.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/recursosOnline/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/recursosOnline/js/kit.fontawesome.com.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/recursosOnline/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/solicitud-reset-password.js"></script>
</body>
</html>

