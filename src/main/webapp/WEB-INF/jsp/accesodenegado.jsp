<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Acceso Denegado</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="${pageContext.request.contextPath}/images/img/favicon-logo.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/RecursosOnline/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/images/iconos/fontawesome-free-6.4.2-web/css/all.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/RecursosOnline/adminlte.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <!-- Contenido del sitio -->
        <div class="content-wrapper d-flex justify-content-center align-items-center">
            <section class="content pb-5">
                <div class="container">
                    <br>
                    <h1 class="text-dark font-small d-flex justify-content-center align-items-center">Acceso Denegado</h1>
                    <i class="fa-solid fa-triangle-exclamation text-danger d-flex justify-content-center align-items-center display-4 mb-3 mt-3"></i>
                    <p class="text-secondary d-flex justify-content-center align-items-center">Lo siento, no tienes permiso para acceder a esta página.</p>                
                    <a href="/" class="text-primary text-decoration-none d-flex justify-content-center align-items-center">Regresar a la página de inicio</a>
                    <br>
                </div>
            </section>
        </div>
    </body>
</html>


