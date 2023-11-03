<%@ include file="../trabajador-header.jspf"%>
<div class="row pb-3">
    <!-- Subtitulo de la página y funciones de los datos -->
    <div class="subtitulo-page">
        <h3 class="mt-0">
            <div class="d-flex">
                <div class="col-sm-6">
                    Información General
                </div>
                <div class="col-sm-6 d-flex justify-content-end">
                    <sec:authorize access="hasAuthority('EDITAR_TRABAJADOR_PRIVILAGE')">
                        <button title="Editar Información" id="EditarInformacion" class="btn btn-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModal" data-modo="editar" data-id="${persona.idPersona}" data-modo="actualizar"><i class="far fa-edit"></i></button>
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
                    <td class="encabezado-tabla">Especialidad</td>
                    <td><c:if test="${not empty trabajador.empleador}">${trabajador.empleador}</c:if></td>
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
                <h5 class="modal-title" id="crearModalLabel">Agregar Trabajador</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formGuardar' accept-charset="UTF-8">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idPersona" value="${persona.getIdPersona()}">
                        <input type="hidden" id="idVisitante" value="${visitante.getIdVisitante()}">
                        <input type="hidden" id="idDocumento" value="${visitante.getIdDocumento()}">
                        <input type="hidden" id="rol" value="${visitante.getRol()}">
                        <div class="form-group">
                            <label for="dui" class="form-label">DUI:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="dui" name="dui" maxlength="9" placeholder="Ingrese el número de DUI sin guiones" required>
                        </div>
                        <div class="form-group">
                            <label for="nombre" class="form-label">Nombre:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="nombre" name="nombre" maxlength="200" placeholder="Ingrese el nombre" required>
                        </div>
                        <div class="form-group">
                            <label for="apellido" class="form-label">Apellido:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="apellido" name="apellido" maxlength="200" placeholder="Ingrese el apellido" required>
                        </div>
                        <div class="form-group">
                            <label for="apellido" class="form-label">Especialidad:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="empleador" name="empleador" maxlength="200" placeholder="Ingrese la especialidad" required>
                        </div>
                    </div>
                    <div class="modal-footer d-flex justify-content-between">
                        <label for="monto" class="form-label text-danger">(*) Campos Obligatorios</label>
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

<!-- Script de la página -->
<%@ include file="../trabajador-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/informacionTrabajador.js"></script>


      
