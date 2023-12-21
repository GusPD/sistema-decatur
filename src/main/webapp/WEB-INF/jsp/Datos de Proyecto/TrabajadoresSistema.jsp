<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationAdministracion.jspf"%>
<div class="content-wrapper">
    <div id="proyectoId" class="hidden" data-id="${proyecto.idProyecto}"></div>
    <!-- Título de la página -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1>Trabajadores de Proyectos</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section class="content">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <!-- Funciones de los datos -->
                        <div class="card-header">
                            <h3 class="card-title d-flex justify-content-between">
                                <div class="d-flex justify-content-estart">
                                    <sec:authorize access="hasAuthority('EXPORTAR_TRABAJADOR_PRIVILAGE')">
                                        <button id="export-copy" title="Copiar" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><i class="fa-regular fa-copy"></i></button> 
                                        <button id="export-excel" title="Exportar Excel" class="btn btn-sm btn-outline-success buttons-excel ml-2" type="button"><i class="fa-solid fa-file-excel"></i></button> 
                                        <button id="export-pdf" title="Exportar PDF" class="btn btn-sm btn-outline-danger buttons-pdf ml-2" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                    </sec:authorize>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <sec:authorize access="hasAuthority('AGREGAR_TRABAJADOR_PRIVILAGE')"> 
                                        <button type="button" title="Agregar Trabajador" class="btn-blue btn-sm btn abrirModal-btn ml-2" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar"><i class="fa-solid fa-file-pen"></i></button>
                                    </sec:authorize> 
                                </div>
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 60vh; padding:4px;">
                                    <table id="trabajadorTable" class="table table-bordered table-striped text-center dataTable dtr-inline mt-1"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Modal de agregar -->
    <div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel">Agregar Trabajador</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form id='formGuardar' accept-charset="UTF-8">
                    <div class="modal-body">
                        <div  class="overflow-auto">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="hidden" id="idVisitante">
                            <input type="hidden" id="idPersona">
                            <input type="hidden" id="idDocumento">
                            <input type="hidden" id="rol" value="TRABAJADOR">
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
                                <label for="empleador" class="form-label">Especialidad:<strong class="text-danger"> *</strong></label>
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
    <!-- Modal de eliminar -->
    <div class="modal fade" id="confirmarEliminarModal" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <strong>¿Estás seguro de eliminar el trabajador seleccionado?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados al trabajador.</p>
                </div>
                <div class="modal-footer">
                  <button id="eliminarTrabajadorBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
                  <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    <form id="eliminarTrabajadorForm" method="post" action="/EliminarTrabajador/{idPersona}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
</div>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('GESTIONAR_INFORMACION_TRABAJADOR_PRIVILAGE') || hasAuthority('GESTIONAR_DOCUMENTO_TRABAJADOR_PRIVILAGE') || hasAuthority('GESTIONAR_TERRENOS_TRABAJADOR_PRIVILAGE')" var="hasPrivilegeVerTrabajador"></sec:authorize>
<script>var hasPrivilegeVerTrabajador = <c:out value='${hasPrivilegeVerTrabajador}' />;</script>    

<c:set var="url" value="" /> 
<sec:authorize access="hasAuthority('GESTIONAR_TERRENOS_TRABAJADOR_PRIVILAGE')">
    <c:set var="url" value="'/TerrenosTrabajador/0/'" />
</sec:authorize>
<sec:authorize access="hasAuthority('GESTIONAR_DOCUMENTO_TRABAJADOR_PRIVILAGE')">
    <c:set var="url" value="'/DocumentosTrabajador/0/'" />
</sec:authorize> 
<sec:authorize access="hasAuthority('GESTIONAR_INFORMACION_TRABAJADOR_PRIVILAGE')">
    <c:set var="url" value="'/InformacionTrabajador/0/'" />
</sec:authorize>
<script>var urlVerTrabajador = <c:out value='${url}' escapeXml="false"/>;</script>

<sec:authorize access="hasAuthority('EDITAR_TRABAJADOR_PRIVILAGE')" var="hasPrivilegeEditarTrabajador"></sec:authorize>
<script>var hasPrivilegeEditarTrabajador = <c:out value='${hasPrivilegeEditarTrabajador}' />;</script>

<sec:authorize access="hasAuthority('ELIMINAR_TRABAJADOR_PRIVILAGE')" var="hasPrivilegeEliminarTrabajador"></sec:authorize>
<script>var hasPrivilegeEliminarTrabajador = <c:out value='${hasPrivilegeEliminarTrabajador}' />;</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Trabajador/Trabajador.js"></script>