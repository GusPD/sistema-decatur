<%@ include file="../venta-header.jspf"%>
<div class="row pb-3">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <!-- Subtitulo de la página y funciones de los datos -->
                <div class="subtitulo-page">
                    <h3 class="mt-0 mb-0">
                        <div class="d-flex">
                            <div class="col-sm-6">
                                Trabajadores
                            </div>
                            <div class="col-sm-6 d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('SELECCIONAR_TRABAJADOR_PRIVILAGE')">
                                    <button title="Seleccionar Trabajador" id="SeleccionarTrabajador" class="btn btn-blue btn-sm" data-bs-toggle="modal" data-bs-target="#seleccionarModalTrabajador" data-tipo="seleccionar"><i class="fas fa-user-check"></i></button>
                                </sec:authorize>
                                <sec:authorize access="hasAuthority('AGREGAR_TRABAJADOR_PRIVILAGE')">
                                    <button title="Agregar Trabajador" id="AgregarTrabajador" class="btn btn-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalTrabajador" data-tipo="agregar"><i class="fas fa-user-plus"></i></button>
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
                        <table id="trabajadoresTable" class="table table-bordered table-striped text-center dataTable dtr-inline mt-1"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal de agregar -->
<div class="modal fade" id="crearModalTrabajador" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Agregar Trabajador</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id='formGuardarTrabajador' accept-charset="UTF-8">
                <div class="modal-body">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idVisitante">
                        <input type="hidden" id="idPersona">
                        <input type="hidden" id="rol">
                        <input type="hidden" id="idVenta" value="${venta.getIdVenta()}">
                        <div class="form-group">
                            <label for="tipoDocumento" class="form-label">Tipo documento:<strong class="text-danger"> *</strong></label>
                            <select class="form-select form-select-sm" id="tipoDocumento" name="tipoDocumento" placeholder="Seleccione una opción" required>
                                <option value="" data-mascara="">Seleccione una opción</option>
                                <c:if test="${not empty tiposDocumento}">
                                    <c:forEach items="${tiposDocumento}" var="eTipo">
                                        <option value="${eTipo.idTipoDocumento}" data-mascara="${eTipo.mascara}">${eTipo.nombre}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="numero" class="form-label">Número:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="numero" name="numero" maxlength="50" placeholder="Ingrese el número de documento" required>
                        </div>
                        <div class="form-group">
                            <label for="nombre" class="form-label">Nombre:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="nombre" name="nombre" maxlength="200" placeholder="Ingrese el nombre" required>
                        </div>
                        <div class="form-group">
                            <label for="apellido" class="form-label">Apellido:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="apellido" name="apellido" maxlength="200" placeholder="Ingrese el apellido" required>
                        </div>
                        <div class="form-group">
                            <label for="apellido" class="form-label">Especialidad:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="empleador" name="empleador" maxlength="200" placeholder="Ingrese la especialidad" required>
                        </div>
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
<!-- Modal de seleccionar -->
<div class="modal fade" id="seleccionarModalTrabajador" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Seleccionar Trabajador</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id='formSeleccionarTrabajador' accept-charset="UTF-8">
                <div class="modal-body">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <div class="form-group">
                            <label for="trabajadores">Seleccione los trabajadores:<strong class="text-danger"> *</strong></label>
                            <select class="form-select form-select-sm" id="trabajadores" name="trabajadores" placeholder="Seleccione un trabajador" data-live-search="true" multiple>
                                <c:if test="${not empty trabajadoresNoVenta}">
                                    <c:forEach items="${trabajadoresNoVenta}" var="eTrabajador">
                                        <option value="${eTrabajador.idVisitante}">${eTrabajador.persona.numero} ${eTrabajador.persona.nombre} ${eTrabajador.persona.apellido}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                            <div id="span-trabajadores-error" class="mensaje-error d-none"><span>Este campo es requerido</span></div>
                        </div>
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
<!-- Modal de eliminar -->
<div class="modal fade" id="confirmarEliminarModalTrabajador" tabindex="-1" aria-labelledby="confirmarEliminarLabelTrabajador" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmarEliminarLabelTrabajador">Confirmar eliminación</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <strong>¿Estás seguro de eliminar la trabajador seleccionada?</strong>
                <p>Ten en cuenta que se eliminarán los datos relacionados al trabajador.</p>
            </div>
            <div class="modal-footer">
              <button id="eliminarTrabajadorBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
              <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>
<form id="eliminarTrabajadorForm" method="post" action="/EliminarTrabajadorVenta/{idPersona}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('VER_TRABAJADOR_PRIVILAGE')" var="hasPrivilegeVerTrabajador"></sec:authorize>
<script>var hasPrivilegeVerTrabajador = <c:out value='${hasPrivilegeVerTrabajador}' />;</script>

<sec:authorize access="hasAuthority('ELIMINAR_TRABAJADOR_PRIVILAGE')" var="hasPrivilegeEliminarTrabajador"></sec:authorize>
<script>var hasPrivilegeEliminarTrabajador = <c:out value='${hasPrivilegeEliminarTrabajador}' />;</script>

<%@ include file="../venta-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Venta/Trabajador.js"></script>


      
