<%@ include file="../venta-header.jspf"%>
<div class="row">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <!-- Subtitulo de la página y funciones de los datos -->
                <div class="subtitulo-page">
                    <h3 class="mt-0 mb-0">
                        <div class="d-flex">
                            <div class="col-sm-6">
                                Propietarios
                            </div>
                            <div class="col-sm-6 d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('SELECCIONAR_PROPIETARIO_PRIVILAGE')">
                                    <button title="Seleccionar Propietario" id="SeleccionarPropietario" class="btn btn-blue btn-sm" data-bs-toggle="modal" data-bs-target="#seleccionarModalPropietario" data-tipo="seleccionar"><i class="fas fa-user-check"></i></button>
                                </sec:authorize>
                                <sec:authorize access="hasAuthority('AGREGAR_PROPIETARIO_PRIVILAGE')">
                                    <button title="Agregar Propietario" id="AgregarPropietario" class="btn btn-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalPropietario" data-tipo="agregar"><i class="fas fa-user-plus"></i></button>
                                </sec:authorize>
                            </div>
                        </div>
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
                            <label for="tipoDocumento" class="form-label">Tipo documento:<strong class="text-danger"> *</strong></label>
                            <select class="form-select" id="tipoDocumento" name="tipoDocumento" placeholder="Seleccione una opción" required>
                                <c:if test="${not empty tiposDocumento}">
                                    <c:forEach items="${tiposDocumento}" var="eTipo">
                                        <option value="${eTipo.idTipoDocumento}" data-mascara="${eTipo.mascara}">${eTipo.nombre}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="numero" class="form-label">Número:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="numero" name="numero" maxlength="50" placeholder="Ingrese el número de documento" required>
                        </div>
                        <div class="form-group">
                            <label for="nombreP" class="form-label">Nombre:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="nombreP" name="nombreP" maxlength="200" placeholder="Ingrese el nombre" required>
                        </div>
                        <div class="form-group">
                            <label for="apellido" class="form-label">Apellido:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="apellido" name="apellido" maxlength="200" placeholder="Ingrese el apellido" required>
                        </div>
                        <div class="form-group">
                            <label for="profesion" class="form-label">Profesión:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="profesion" name="profesion" maxlength="200" placeholder="Ingrese la profesión" required>
                        </div>
                        <div class="form-group">
                            <label for="direccionCasa" class="form-label">Dirección Casa:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="direccionCasa" name="direccionCasa" maxlength="300" placeholder="Ingrese la dirección de casa" required>
                        </div>
                        <div class="form-group">
                            <label for="lugarTrabajo" class="form-label">Lugar de Trabajo:</label>
                            <input type="text" class="form-control" id="lugarTrabajo" name="lugarTrabajo" placeholder="Ingrese el lugar de trabajo">
                        </div>
                        <div class="form-group">
                            <label for="direccionTrabajo" class="form-label">Dirección de Trabajo:</label>
                            <input type="text" class="form-control" id="direccionTrabajo" name="direccionTrabajo" placeholder="Ingrese la dirección del trabajo">
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
                            <label for="propietarios">Seleccione los propietarios:<strong class="text-danger"> *</strong></label>
                            <select class="form-select" id="propietarios" name="propietarios" placeholder="Seleccione un propietario" data-live-search="true" multiple>
                                <c:if test="${not empty propietariosNoVenta}">
                                    <c:forEach items="${propietariosNoVenta}" var="ePropietario">
                                        <option value="${ePropietario.idPropietario}">${ePropietario.persona.numero} ${ePropietario.persona.nombre} ${ePropietario.persona.apellido}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                            <div id="span-propietarios-error" class="mensaje-error d-none"><span>Este campo es requerido</span></div>
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
<!-- Modal de eliminar -->
<div class="modal fade" id="confirmarEliminarModalPropietario" tabindex="-1" aria-labelledby="confirmarEliminarLabelPropietario" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmarEliminarLabelPropietario">Confirmar eliminación</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <strong>¿Estás seguro de eliminar la propietario seleccionada?</strong>
                <p>Ten en cuenta que se eliminarán los datos relacionados a la propietario.</p>
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

<!-- Script de la página -->
<sec:authorize access="hasAuthority('VER_PROPIETARIO_PRIVILAGE')" var="hasPrivilegeVerPropietario"></sec:authorize>
<script>var hasPrivilegeVerPropietario = <c:out value='${hasPrivilegeVerPropietario}' />;</script>    

<sec:authorize access="hasAuthority('ELIMINAR_PROPIETARIO_PRIVILAGE')" var="hasPrivilegeEliminarPropietario"></sec:authorize>
<script>var hasPrivilegeEliminarPropietario = <c:out value='${hasPrivilegeEliminarPropietario}' />;</script>

<%@ include file="../venta-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Venta/Propietario.js"></script>


      
