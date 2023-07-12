<!-- Main content -->
<section class="content pb-5">
    <div>
        <c:if test="${not empty mensaje}">
            <div class="alert alert-success d-flex align-items-center alert-dismissible fade show" role="alert">
                <strong><i class="bi bi-check-circle"></i> �xito!</strong> ${mensaje}
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
            <strong><i class="bi bi-check-circle"></i> �xito!&nbsp;</strong>
        </div>
        <div class="alert alert-danger d-flex align-items-center alert-dismissible fade d-none" role="alert">
            <strong><i class="bi bi-exclamation-triangle"></i> Error!&nbsp;</strong>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <div class="row col-sm-12 margenBoton">
            <div class="col-sm-4 botonExportar"></div>
            <div class="col-sm-7"></div>
            <sec:authorize access="hasAuthority('AGREGAR_PROYECTO_PRIVILAGE')"> 
                <button type="button" class="btn-add btn abrirModal-btn col-sm-1 btn-sm" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar">Agregar</button>
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
                        <label for="nombre" class="form-label">Nombre del proyecto: </label>
                        <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Proyecto" required>
                    </div>
                    <div class="form-group">
                        <label for="empresa" class="form-label">Nombre de la empresa: </label>
                        <input type="text" class="form-control" id="empresa" name="empresa" placeholder="Empresa" required>
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
                <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminaci�n</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <strong>�Est�s seguro de eliminar al proyecto seleccionado?</strong>
                <p>Ten en cuenta que se eliminar�n los datos relacionados al proyecto <span id="nombre"></span>.</p>
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

<!-- Script de la p�gina -->
<sec:authorize access="hasAuthority('VER_PROYECTO_PRIVILAGE')" var="hasPrivilegeVerProyecto"></sec:authorize>
<script>var hasPrivilegeVerProyecto = ${hasPrivilegeVerProyecto};</script>    
        
<sec:authorize access="hasAuthority('EDITAR_PROYECTO_PRIVILAGE')" var="hasPrivilegeEditarProyecto"></sec:authorize>
<script>var hasPrivilegeEditarProyecto = ${hasPrivilegeEditarProyecto};</script>

<sec:authorize access="hasAuthority('ELIMINAR_PROYECTO_PRIVILAGE')" var="hasPrivilegeEliminarProyecto"></sec:authorize>
<script>var hasPrivilegeEliminarProyecto = ${hasPrivilegeEliminarProyecto};</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/proyecto.js"></script>

