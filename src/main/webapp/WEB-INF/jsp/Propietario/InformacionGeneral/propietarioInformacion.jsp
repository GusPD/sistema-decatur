<%@ include file="../propietario-header.jspf"%>
<div class="row pb-3">
    <!-- Subtitulo de la página y funciones de los datos -->
    <div class="subtitulo-page"><h3 class="mt-0">Información General
            <sec:authorize access="hasAuthority('EDITAR_PROPIETARIO_PRIVILAGE')">
                <span title="Editar Información" id="EditarInformacion" class="btn abrirModal-btn text-info puntero pull-right btn-sm" data-bs-toggle="modal" data-bs-target="#crearModal" data-tipo="editar" data-id="${persona.idPersona}" data-modo="actualizar" style="cursor: pointer;">
                    <i class="far fa-edit"></i>
                </span>
            </sec:authorize>
        </h3>
    </div>
    <!-- Datos -->
    <div class="tarjeta-container">
        <table class="table small table-bordered m-0" id="tabla-informacion">
            <tbody>
                <tr>
                    <td width="20%" class="encabezado-tabla">DUI</td>
                    <td><c:if test="${not empty persona.dui}">${persona.dui}</c:if></td>
                </tr>
                <tr>
                    <td class="encabezado-tabla">Nombre</td>
                    <td><c:if test="${not empty persona.nombre}">${persona.nombre}</c:if></td>
                </tr>
                <tr>
                    <td class="encabezado-tabla">Apellido</td>
                    <td><c:if test="${not empty persona.apellido}">${persona.apellido}</c:if></td>
                </tr>
                <tr>
                    <td class="encabezado-tabla">Profesión</td>
                    <td><c:if test="${not empty propietario.profesion}">${propietario.profesion}</c:if></td>
                </tr>
                <tr>
                    <td class="encabezado-tabla">Dirección de Casa</td>
                    <td><c:if test="${not empty propietario.direccionCasa}">${propietario.direccionCasa}</c:if></td>
                </tr>
                <tr>
                    <td class="encabezado-tabla">Lugar de Trabajo</td>
                    <td><c:if test="${not empty propietario.lugarTrabajo}">${propietario.lugarTrabajo}</c:if></td>
                </tr>
                <tr>
                    <td class="encabezado-tabla">Dirección de Trabajo</td>
                    <td><c:if test="${not empty propietario.direccionTrabajo}">${propietario.direccionTrabajo}</c:if></td>
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
                <h5 class="modal-title" id="crearModalLabel">Agregar Propietario</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formGuardar' accept-charset="UTF-8">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idPersona" value="${persona.getIdPersona()}">
                        <input type="hidden" id="idPropietario" value="${propietario.getIdPropietario()}">
                        <input type="hidden" id="idDocumento" value="${propietario.getIdDocumento()}">
                        <div class="form-group">
                            <label for="dui" class="form-label">DUI: </label>
                            <input type="text" class="form-control" id="dui" name="dui" maxlength="9" placeholder="Ingrese el número de DUI sin guiones" required>
                        </div>
                        <div class="form-group">
                            <label for="nombre" class="form-label">Nombre: </label>
                            <input type="text" class="form-control" id="nombre" name="nombre" maxlength="200" placeholder="Ingrese el nombre" required>
                        </div>
                        <div class="form-group">
                            <label for="apellido" class="form-label">Apellido: </label>
                            <input type="text" class="form-control" id="apellido" name="apellido" maxlength="200" placeholder="Ingrese el apellido" required>
                        </div>
                        <div class="form-group">
                            <label for="profesion" class="form-label">Profesión: </label>
                            <input type="text" class="form-control" id="profesion" name="profesion" maxlength="200" placeholder="Ingrese la profesión" required>
                        </div>
                        <div class="form-group">
                            <label for="direccionCasa" class="form-label">Dirección Casa: </label>
                            <input type="text" class="form-control" id="direccionCasa" name="direccionCasa" maxlength="300" placeholder="Ingrese la dirección de casa" required>
                        </div>
                        <div class="form-group">
                            <label for="lugarTrabajo" class="form-label">Lugar de Trabajo: </label>
                            <input type="text" class="form-control" id="lugarTrabajo" name="lugarTrabajo" placeholder="Ingrese el lugar de trabajo " required>
                        </div>
                        <div class="form-group">
                            <label for="direccionTrabajo" class="form-label">Dirección de Trabajo: </label>
                            <input type="text" class="form-control" id="direccionTrabajo" name="direccionTrabajo" placeholder="Ingrese la dirección del trabajo" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-outline-success btn-sm">Guardar</button>
                        <button type="button" class="btn btn-outline-danger btn-sm" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
                        
<!-- Script de la página -->
<%@ include file="../propietario-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/informacionPropietario.js"></script>


      
