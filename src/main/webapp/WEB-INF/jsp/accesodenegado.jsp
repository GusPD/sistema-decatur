<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Acceso Denegado</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${pageContext.request.contextPath}/images/favicon-logo.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/recursosOnline/css/all.min.css">
</head>
<body>
    <div class="content-wrapper" style="display: flex; justify-content: center; align-items: center;">
        <!-- Main content -->
        <section class="content pb-5">
            <div class="container">
                <!-- Esto lo estoy manejando en el indexController -->
                <br>
                <h1 style="color: #333; font-size: 28px; text-align: center;">Acceso Denegado</h1>
                <i class="fas fa-exclamation-triangle" style="color: #f00; font-size: 48px; text-align: center; display: block; margin: 20px auto;"></i>
                <p style="color: #555555; font-size: 16px; text-align: center;">Lo siento, no tienes permiso para acceder a esta página.</p>                
                <a href="/" style="color: #007bff; text-decoration: none; font-size: 16px; text-align: center; display: block; margin-top: 20px;">Regresar a la página de inicio</a>
                <br>
            </div>
        </section>
    </div>
</body>
</html>


