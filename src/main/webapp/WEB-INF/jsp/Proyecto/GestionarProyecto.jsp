<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationAdministracion.jspf"%>
<div class="content-wrapper">
    <!-- Título de la página -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1>Proyectos</h1>
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
                                    <sec:authorize access="hasAuthority('EXPORTAR_PROYECTO_PRIVILAGE')">
                                        <button id="export-copy" title="Copiar" class="btn btn-outline-secondary buttons-copy" type="button"><i class="fa-regular fa-copy"></i></button> 
                                        <button id="export-excel" title="Exportar Excel" class="btn btn-outline-success buttons-excel ml-2" type="button"><i class="fa-solid fa-file-csv"></i></button> 
                                        <button id="export-pdf" title="Exportar PDF" class="btn btn-outline-danger buttons-pdf ml-2" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                    </sec:authorize>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <sec:authorize access="hasAuthority('AGREGAR_PROYECTO_PRIVILAGE')"> 
                                        <button type="button" title="Agregar Proyecto" class="btn-blue btn abrirModal-btn ml-2" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar"><i class="fa-solid fa-file-pen"></i></button>
                                    </sec:authorize> 
                                </div>
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 60vh; padding: 4px;">
                                    <table id="proyectoTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
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
                    <h5 class="modal-title" id="crearModalLabel">Agregar Proyecto</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id='formGuardar' accept-charset="UTF-8">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idProyecto">
                        <div class="form-group">
                            <label for="nombre" class="form-label">Proyecto:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ingrese el nombre del proyecto" required>
                        </div>
                        <div class="form-group">
                            <label for="empresa" class="form-label">Empresa:<strong class="text-danger"> *</strong></label> 
                            <select class="form-control" id="empresa" name="empresa" placeholder="Seleccione una empresa" required>
                                <option value="">Seleccione una opción</option>
                                <c:if test="${not empty empresas}">
                                    <c:forEach items="${empresas}" var="eEmpresa">
                                        <option value="${eEmpresa.idEmpresa}">${eEmpresa.nombre}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
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
    <div class="modal fade" id="confirmarEliminarModal" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <strong>¿Estás seguro de eliminar al proyecto seleccionado?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados al proyecto.</p>
                </div>
                <div class="modal-footer">
                  <button id="eliminarProyectoBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
                  <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    <form id="eliminarProyectoForm" method="post" action="/EliminarProyecto/{idProyecto}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>                    
</div>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('VER_PROYECTO_PRIVILAGE')" var="hasPrivilegeVerProyecto"></sec:authorize>
<script>var hasPrivilegeVerProyecto = <c:out value='${hasPrivilegeVerProyecto}' />;</script>    
        
<sec:authorize access="hasAuthority('EDITAR_PROYECTO_PRIVILAGE')" var="hasPrivilegeEditarProyecto"></sec:authorize>
<script>var hasPrivilegeEditarProyecto = <c:out value='${hasPrivilegeEditarProyecto}' />;</script>

<sec:authorize access="hasAuthority('ELIMINAR_PROYECTO_PRIVILAGE')" var="hasPrivilegeEliminarProyecto"></sec:authorize>
<script>var hasPrivilegeEliminarProyecto = <c:out value='${hasPrivilegeEliminarProyecto}' />;</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Proyecto/Proyecto.js"></script>