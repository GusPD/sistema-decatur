<%@ include file="../venta-header.jspf"%>
<div class="row">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <!-- Subtitulo de la p�gina y funciones de los datos -->
                <div class="subtitulo-page">
                    <h3 class="mt-0 mb-0">Propietarios
                        <sec:authorize access="hasAuthority('SELECCIONAR_PROPIETARIO_PRIVILAGE')">
                            <span id="SeleccionarPropietario" class="btn abrirModal-btn text-info puntero pull-right btn-sm" data-bs-toggle="modal" data-bs-target="#seleccionarModalPropietario" data-action="seleccionar" style="cursor: pointer;">
                                <i class="fas fa-user-check"></i>
                            </span>
                        </sec:authorize>
                        <sec:authorize access="hasAuthority('AGREGAR_PROPIETARIO_PRIVILAGE')">
                            <span id="AgregarPropietario" class="btn abrirModalPropietario-btn text-info puntero pull-right btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalPropietario" data-action="agregar" style="cursor: pointer;">
                                <i class="fas fa-user-plus"></i>
                            </span>
                        </sec:authorize>
                    </h3>
                </div>
            </div>
            <!-- Datos -->
            <div class="card-body">
                <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                    <div class="col-sm-12 table-responsive pt-1" style="height: 55vh; padding:4px;">
                        <table id="propietariosTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal de agregar -->
<div class="modal fade" id="crearModalPropietario" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Agregar Propietario</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formGuardarPropietario' accept-charset="UTF-8">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idVenta" value="${venta.getIdVenta()}">
                        <input type="hidden" id="idPropietario">
                        <input type="hidden" id="idPersona">
                        <input type="hidden" id="idDocumento">
                        <input type="hidden" id="idAsignacion">
                        <div class="form-group">
                            <label for="dui" class="form-label">DUI: </label>
                            <input type="text" class="form-control" id="dui" name="dui" maxlength="9" placeholder="Ingrese el n�mero de DUI sin guiones" required>
                        </div>
                        <div class="form-group">
                            <label for="nombreP" class="form-label">Nombre: </label>
                            <input type="text" class="form-control" id="nombreP" name="nombreP" maxlength="200" placeholder="Ingrese el nombre" required>
                        </div>
                        <div class="form-group">
                            <label for="apellido" class="form-label">Apellido: </label>
                            <input type="text" class="form-control" id="apellido" name="apellido" maxlength="200" placeholder="Ingrese el apellido" required>
                        </div>
                        <div class="form-group">
                            <label for="profesion" class="form-label">Profesi�n: </label>
                            <input type="text" class="form-control" id="profesion" name="profesion" maxlength="200" placeholder="Ingrese la profesi�n" required>
                        </div>
                        <div class="form-group">
                            <label for="direccionCasa" class="form-label">Direcci�n Casa: </label>
                            <input type="text" class="form-control" id="direccionCasa" name="direccionCasa" maxlength="300" placeholder="Ingrese la direcci�n de casa" required>
                        </div>
                        <div class="form-group">
                            <label for="lugarTrabajo" class="form-label">Lugar de Trabajo: </label>
                            <input type="text" class="form-control" id="lugarTrabajo" name="lugarTrabajo" placeholder="Ingrese el lugar de trabajo " required>
                        </div>
                        <div class="form-group">
                            <label for="direccionTrabajo" class="form-label">Direcci�n de Trabajo: </label>
                            <input type="text" class="form-control" id="direccionTrabajo" name="direccionTrabajo" placeholder="Ingrese la direcci�n del trabajo" required>
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
<!-- Modal de seleccionar -->
<div class="modal fade" id="seleccionarModalPropietario" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Seleccionar Propietario</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formSeleccionarPropietario' accept-charset="UTF-8">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <div class="form-group">
                            <label for="propietarios">Seleccione los propietarios:</label>
                            <select class="form-select" id="propietarios" name="propietarios" data-live-search="true" multiple>
                                <c:if test="${not empty propietariosNoVenta}">
                                    <c:forEach items="${propietariosNoVenta}" var="ePropietario">
                                        <option value="${ePropietario.idPropietario}">${ePropietario.persona.dui} ${ePropietario.persona.nombre} ${ePropietario.persona.apellido}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                            <div id="span-propietarios-error" class="mensaje-error d-none" style=""><span>Este campo es requerido</span></div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button id="btnGuardarSeleccionarPropietario" type="submit" class="btn btn-outline-success btn-sm">Guardar</button>
                        <button type="button" class="btn btn-outline-danger btn-sm" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Modal de eliminar -->
<div class="modal fade" id="confirmarEliminarModalPropietario" tabindex="-1" aria-labelledby="confirmarEliminarLabelPropietario" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmarEliminarLabelPropietario">Confirmar eliminaci�n</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <strong>�Est�s seguro de eliminar la propietario seleccionada?</strong>
                <p>Ten en cuenta que se eliminar�n los datos relacionados a la propietario.</p>
            </div>
            <div class="modal-footer">
              <button id="eliminarPropietarioBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
              <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>
<form id="eliminarPropietarioForm" method="post" action="/EliminarPropietarioVenta/{idPersona}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>

<!-- Script de la p�gina -->
<sec:authorize access="hasAuthority('VER_PROPIETARIO_PRIVILAGE')" var="hasPrivilegeVerPropietario"></sec:authorize>
<script>var hasPrivilegeVerPropietario = ${hasPrivilegeVerPropietario};</script>    

<sec:authorize access="hasAuthority('ELIMINAR_PROPIETARIO_PRIVILAGE')" var="hasPrivilegeEliminarPropietario"></sec:authorize>
<script>var hasPrivilegeEliminarPropietario = ${hasPrivilegeEliminarPropietario};</script>

<%@ include file="../venta-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/propietarioVenta.js"></script>


      
