<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationAdministracion.jspf"%>
<div class="content-wrapper">
    <!-- T�tulo de la p�gina -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1>Roles</h1>
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
                            <h3 class="card-title d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('EXPORTAR_ROL_PRIVILAGE')"> 
                                    <button id="export-copy" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><span>Copiar  </span><i class="fa-regular fa-copy"></i></button> 
                                    <button id="export-excel" class="btn btn-sm btn-outline-success buttons-excel ml-2" type="button"><span>Exportar </span><i class="fa-solid fa-file-csv"></i></button> 
                                    <button id="export-pdf" class="btn btn-sm btn-outline-danger buttons-pdf ml-2" type="button"><span>Exportar </span><i class="fa-regular fa-file-pdf"></i></button> 
                                </sec:authorize>
                                <sec:authorize access="hasAuthority('AGREGAR_ROL_PRIVILAGE')"> 
                                    <button type="button" class="btn-blue btn abrirModal-btn btn-sm ml-2" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar"><span>Agregar </span><i class="fa-solid fa-file-pen"></i></button>
                                </sec:authorize> 
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 60vh; padding:4px;">
                                    <table id="rolesTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
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
                    <h5 class="modal-title" id="crearModalLabel">Agregar Rol</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id='formGuardar' accept-charset="UTF-8">
                        <div  class="overflow-auto">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="hidden" id="rolId">
                            <div class="form-group">
                                <label for="nombre" class="form-label">Nombre de rol: </label>
                                <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre" required>
                            </div>
                            <div id="permisos-error" class="error-message"></div>
                            <div class="form-group">
                                <label for="permisos" class="form-label">Permisos: </label>
                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                    <div>
                                        <li>
                                            <input class="checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                            <label for="permiso${elementoPermiso.idPermiso}">${elementoPermiso.nombre}</label>
                                        </li>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button id='btnSumit' type="submit" class="btn btn-outline-success btn-sm">Guardar</button>
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
                    <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminaci�n</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <strong>�Est�s seguro de eliminar al rol seleccionado?</strong>
                    <p>Ten en cuenta que se eliminar�n los datos relacionados al rol.</p>
                </div>
                <div class="modal-footer">
                  <button id="eliminarRolBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
                  <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    <form id="eliminarRolForm" method="post" action="/EliminarRol/{idRol}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
</div>

<!-- Script de la p�gina -->
<sec:authorize access="hasAuthority('EDITAR_ROL_PRIVILAGE')" var="hasPrivilegeEditarRol"></sec:authorize>
<script>var hasPrivilegeEditarRol = ${hasPrivilegeEditarRol};</script>

<sec:authorize access="hasAuthority('ELIMINAR_ROL_PRIVILAGE')" var="hasPrivilegeEliminarRol"></sec:authorize>
<script>var hasPrivilegeEliminarRol = ${hasPrivilegeEliminarRol};</script>    

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/roles.js"></script>

