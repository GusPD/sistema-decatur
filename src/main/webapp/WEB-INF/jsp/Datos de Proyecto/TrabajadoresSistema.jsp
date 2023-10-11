<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationAdministracion.jspf"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <div id="proyectoId" class="hidden" data-id="${proyecto.idProyecto}"></div>
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="titulo-Perfil">
                        <div class="container container-titulo">
                            <h1>Trabajadores del Sistema</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section class="content">
        <div class="container">
            <section class="content pb-5">
                <div>
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
                        <sec:authorize access="hasAuthority('EXPORTAR_TRABAJADORES_SISTEMA_PRIVILAGE')"> 
                            <button id="export-copy" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><span>Copiar  </span><i class="fa-regular fa-copy"></i></button> 
                            <button id="export-excel" class="btn btn-sm btn-outline-success buttons-excel" type="button"><span>Exportar </span><i class="fa-solid fa-file-csv"></i></button> 
                            <button id="export-pdf" class="btn btn-sm btn-outline-danger buttons-pdf" type="button"><span>Exportar </span><i class="fa-regular fa-file-pdf"></i></button> 
                        </sec:authorize>
                        <sec:authorize access="hasAuthority('AGREGAR_TRABAJADOR_PRIVILAGE')"> 
                            <button type="button" class="btn-blue btn abrirModal-btn btn-sm" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar">Agregar</button>
                        </sec:authorize>
                    </div>
                    <div>
                        <div class="table-responsive-md table-container">
                            <table id="trabajadorTable" class="table table-striped custom-fixed-header">
                                <thead class="table-light">
                                    <tr>
                                        <th class="text-center">DUI</th>
                                        <th class="text-center">Nombre</th>
                                        <th class="text-center">Apellido</th>
                                        <th class="text-center">Especialidad</th>
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

            <!-- Modal para Trabajadores -->
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
                                    <input type="hidden" id="idVisitante">
                                    <input type="hidden" id="idPersona">
                                    <input type="hidden" id="idDocumento">
                                    <input type="hidden" id="rol" value="TRABAJADOR">
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
                                        <label for="empleador" class="form-label">Especialidad: </label>
                                        <input type="text" class="form-control" id="empleador" name="empleador" maxlength="200" placeholder="Ingrese la especialidad" required>
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
            <div class="modal fade" id="confirmarEliminarModal" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminación</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <strong>¿Estás seguro de eliminar el trabajador seleccionado?</strong>
                            <p>Ten en cuenta que se eliminarán los datos relacionados al trabajador <span id="nombre"></span> <span id="apellido"></span>.</p>
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
    </section>
</div>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('VER_TRABAJADOR_PRIVILAGE')" var="hasPrivilegeVerTrabajador"></sec:authorize>
<script>var hasPrivilegeVerTrabajador = ${hasPrivilegeVerTrabajador};</script>    
        
<sec:authorize access="hasAuthority('EDITAR_TRABAJADOR_PRIVILAGE')" var="hasPrivilegeEditarTrabajador"></sec:authorize>
<script>var hasPrivilegeEditarTrabajador = ${hasPrivilegeEditarTrabajador};</script>

<sec:authorize access="hasAuthority('ELIMINAR_TRABAJADOR_PRIVILAGE')" var="hasPrivilegeEliminarTrabajador"></sec:authorize>
<script>var hasPrivilegeEliminarTrabajador = ${hasPrivilegeEliminarTrabajador};</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/trabajador.js"></script>