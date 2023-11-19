<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationAdministracion.jspf"%>
<div id="idProyecto" class="hidden" value="${proyecto.idProyecto}"></div>
<div class="content-wrapper">
    <!-- Título de la página -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1 id="NombrePropietario">Perfil del Usuario</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section class="content">
        <div class="container">
            <section class="content">
                    <div class="row">
                        <div class="col-sm-2">
                            <!-- Imágen del Perfil -->
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="list-group info">
                                        <div class="border p-3 rounded bg-white">
                                            <div class="row align-items-center mt-1 mb-1">
                                                <i id="imagenPerfil" class="fa-solid fa-user-tie text-center"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Menú del Perfil -->
                        </div>
                        <!-- Datos -->
                        <div class="col-sm-10">
                            <div class="row pb-4" >
                                <div class="col-12 table-responsive page-scroll" style="height: 76vh;">
                                    <div class="card">
                                        <div class="card-body pb-0">
                                            <div class="col-sm-12">
                                                <div class="row pb-3">
                                                    <!-- Subtitulo de la página y funciones de los datos -->
                                                    <div class="subtitulo-page">
                                                        <h3 class="mt-0">
                                                            <div class="d-flex">
                                                                <div class="col-sm-6">
                                                                    Información General
                                                                </div>
                                                                <div class="col-sm-6 d-flex justify-content-end">
                                                                    <sec:authorize access="hasAuthority('EDITAR_USUARIO_PRIVILAGE')">
                                                                        <button title="Editar Información" id="EditarInformacion" class="btn btn-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModal" data-tipo="editar" data-id="${usuario.idUsuario}" data-modo="actualizar"><i class="far fa-edit"></i></button>
                                                                    </sec:authorize>
                                                                </div>
                                                            </div>
                                                        </h3>
                                                    </div>
                                                    <!-- Datos -->
                                                    <div class="tarjeta-container">
                                                        <table class="table small table-bordered m-0" id="tabla-informacion">
                                                            <tbody>
                                                                <tr>
                                                                    <td width="20%" class="encabezado-tabla">Nombre</td>
                                                                    <td><c:if test="${not empty usuario.nombre}">${usuario.nombre}</c:if></td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="encabezado-tabla">Usuario</td>
                                                                    <td><c:if test="${not empty usuario.username}">${usuario.username}</c:if></td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="encabezado-tabla">Correo</td>
                                                                    <td><c:if test="${not empty usuario.email}">${usuario.email}</c:if></td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="encabezado-tabla">Rol</td>
                                                                    <td><c:if test="${not empty roles}">${roles}</c:if></td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="encabezado-tabla">Empresas</td>
                                                                    <td><c:if test="${not empty empresas}">${empresas}</c:if></td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="encabezado-tabla">Proyectos</td>
                                                                    <td><c:if test="${not empty proyectos}">${proyectos}</c:if></td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                                <!-- Modal de editar -->
                                                <div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title" id="crearModalLabel">Editar Perfil</h5>
                                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <form id='formGuardar' accept-charset="UTF-8">
                                                                    <div  class="overflow-auto">
                                                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                                                        <input type="hidden" id="UsuarioId">
                                                                        <div class="form-group">
                                                                            <label for="nombre" class="form-label">Nombre:<strong class="text-danger"> *</strong></label>
                                                                           <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre" required>
                                                                       </div>
                                                                        <div class="form-group">
                                                                             <label for="username" class="form-label">Usuario:<strong class="text-danger"> *</strong></label>
                                                                            <input type="text" class="form-control" id="username" name="username" placeholder="Usuario" required>
                                                                        </div>
                                                                        <div class="form-group">
                                                                            <label for="email" class="form-label">Correo Electrónico:<strong class="text-danger"> *</strong></label>
                                                                            <input type="text" class="form-control" id="email" name="email" placeholder="Correo" required>
                                                                        </div>
                                                                        <div class="form-group">
                                                                            <label for="password" class="form-label">Contraseña:<strong class="text-danger"> *</strong></label>
                                                                            <input type="password" class="form-control" id="password" name="password" placeholder="Contraseña" required>
                                                                        </div>
                                                                    </div>
                                                                    <div class="modal-footer d-flex justify-content-between">
                                                                        <label for="monto" class="form-label text-danger mensaje-obligatorios">(*) Campos Obligatorios</label>
                                                                        <div>
                                                                            <button type="submit" class="btn btn-outline-success btn-sm">Guardar</button>
                                                                            <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                                                                        </div>
                                                                    </div>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </section>
</div>

<!-- Script de la página -->
<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Usuario/PerfilUsuario.js"></script>


      
