<%@ include file="../propietario-header.jspf"%>
<input type="hidden" id="idPersona" value="${persona.getIdPersona()}">
<div class="row">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <!-- Subtitulo de la página y funciones de los datos-->
                <div class="subtitulo-page">
                    <h3 class="m-0">Correos Eléctronicos
                        <sec:authorize access="hasAuthority('AGREGAR_CORREO_PROPIETARIO_PRIVILAGE')">
                            <span id="AgregarCorreo" class="btn abrirModal-btn text-info puntero pull-right btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalCorreo" data-action="agregar" style="cursor: pointer;">
                                <i class="fa-solid fa-envelope"></i>
                            </span>
                        </sec:authorize>
                    </h3>
                </div>
            </div>
            <!-- Datos -->
            <div class="card-body">
                <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                    <div class="col-sm-12 table-responsive pt-1" style="height: 55vh; padding:4px;">
                        <table id="correoTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal de agregar -->
<div class="modal fade" id="crearModalCorreo" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Agregar Correo</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formGuardarCorreo' accept-charset="UTF-8">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idPropietario" value="${propietario.getIdPropietario()}">
                        <input type="hidden" id="idCorreo">
                        <div class="form-group">
                            <label for="tipo" class="form-label">Tipo de Correo:</label>
                            <select type="text" class="form-select" id="tipo" name="tipo" required>
                                <option value="">Seleccione una opción</option>
                                <c:forEach items="${tiposCorreo}" var="eTipoCorreo" varStatus="status">
                                    <option value="${eTipoCorreo}">${eTipoCorreo}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="correo" class="form-label">Correo:</label>
                            <input type="text" class="form-control" id="correo" name="correo" maxlength="150" placeholder="Ingrese el correo electrónico" required>
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
<!-- Modal de eliminar -->
<div class="modal fade" id="confirmarEliminarModalCorreo" tabindex="-1" aria-labelledby="confirmarEliminarLabelCorreo" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmarEliminarLabelCorreo">Confirmar eliminación</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <strong>¿Estás seguro de eliminar el correo seleccionado?</strong>
                <p>Ten en cuenta que se eliminarán los datos relacionados al correo.</p>
            </div>
            <div class="modal-footer">
              <button id="eliminarCorreoBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
              <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>
<form id="eliminarCorreoForm" method="post" action="/EliminarCorreo/{idCorreo}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>   

<!-- Script de la página -->
<sec:authorize access="hasAuthority('ELIMINAR_CORREO_PROPIETARIO_PRIVILAGE')" var="hasPrivilegeEliminarCorreo"></sec:authorize>
<script>var hasPrivilegeEliminarCorreo = ${hasPrivilegeEliminarCorreo};</script>

<%@ include file="../propietario-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/correoPropietario.js"></script>


      
