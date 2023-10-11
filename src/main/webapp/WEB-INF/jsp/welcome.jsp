<%@ include file="common/header.jspf"%>
<%@ include file="common/navigationAdministracion.jspf"%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="titulo-Perfil">
                        <div class="container container-titulo">
                            <h1>Proyectos</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section class="content">
        <!-- Main content -->
        <section class="content pb-5">
            <div class="container">
                <c:if test="${not empty mensaje}">
                    <div class="alert alert-success d-flex align-items-center alert-dismissible fade show" role="alert">
                        <strong><i class="bi bi-check-circle"></i> Éxito!</strong> ${mensaje}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger d-flex align-items-center alert-dismissible fade show" role="alert">
                        <strong><i class="bi bi-exclamation-triangle"></i> Error!</strong> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <div class="alert alert-success d-flex align-items-center alert-dismissible fade d-none" role="alert">
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    <strong><i class="bi bi-check-circle"></i> Éxito!&nbsp;</strong>
                </div>
                <div class="alert alert-danger d-flex align-items-center alert-dismissible fade d-none" role="alert">
                    <strong><i class="bi bi-exclamation-triangle"></i> Error!&nbsp;</strong>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <div class="col-sm-12 d-flex justify-content-end">
                    <sec:authorize access="hasAuthority('EXPORTAR_PROYECTO_PRIVILAGE')"> 
                        <button id="export-copy" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><span>Copiar  </span><i class="fa-regular fa-copy"></i></button> 
                        <button id="export-excel" class="btn btn-sm btn-outline-success buttons-excel ml-2" type="button"><span>Exportar </span><i class="fa-solid fa-file-csv"></i></button> 
                        <button id="export-pdf" class="btn btn-sm btn-outline-danger buttons-pdf ml-2" type="button"><span>Exportar </span><i class="fa-regular fa-file-pdf"></i></button> 
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('AGREGAR_PROYECTO_PRIVILAGE')"> 
                        <button type="button" class="btn-blue btn abrirModal-btn btn-sm ml-2" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar">Agregar</button>
                    </sec:authorize>
                </div>
                <div>
                    <div class="table-responsive-md">
                        <table id="proyectoTable" class="table table-striped">
                            <thead class="table-light">
                                <tr>
                                    <th class="text-center">Nombre del Proyecto</th>
                                    <th class="text-center">Empresa</th>
                                    <th class="text-center">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div><!-- /.container-fluid -->
        </section>
    </section>
    
   <!-- Modal para Usuarios -->
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
                            <label for="nombre" class="form-label">Proyecto: </label>
                            <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ingrese el nombre del proyecto" required>
                        </div>
                        <div class="form-group">
                            <label for="empresa" class="form-label">Empresa: </label>
                            
                            <select class="form-select" id="empresa" name="empresa" placeholder="Seleccione una empresa" required>
                                <c:if test="${not empty empresas}">
                                    <c:forEach items="${empresas}" var="eEmpresa">
                                        <option value="${eEmpresa.idEmpresa}">${eEmpresa.nombre}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
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
    <div class="modal fade" id="confirmarEliminarModal" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <strong>¿Estás seguro de eliminar al proyecto seleccionado?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados al proyecto <span id="nombre"></span>.</p>
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
    <!-- /.Modal de eliminar -->
</div>

<sec:authorize access="hasAuthority('VER_PROYECTO_PRIVILAGE')" var="hasPrivilegeVerProyecto"></sec:authorize>
<script>var hasPrivilegeVerProyecto = ${hasPrivilegeVerProyecto};</script>    
        
<sec:authorize access="hasAuthority('EDITAR_PROYECTO_PRIVILAGE')" var="hasPrivilegeEditarProyecto"></sec:authorize>
<script>var hasPrivilegeEditarProyecto = ${hasPrivilegeEditarProyecto};</script>

<sec:authorize access="hasAuthority('ELIMINAR_PROYECTO_PRIVILAGE')" var="hasPrivilegeEliminarProyecto"></sec:authorize>
<script>var hasPrivilegeEliminarProyecto = ${hasPrivilegeEliminarProyecto};</script>

<%@ include file="common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/proyecto.js"></script>