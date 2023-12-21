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
                            <h1>Copias de Seguridad</h1>
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
                        <!-- Menú -->
                        <ul class="nav nav-tabs">
                            <li class="nav-item">
                                <a class="nav-link text-dark" aria-current="page" href="/BackupLocal">Local</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link text-dark active" aria-current="page" href="/BackupNube">Nube</a>
                            </li>
                        </ul>
                        <!-- Funciones con los datos -->
                        <div class="card-header">
                            <h3 class="card-title d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('AGREGAR_BACKUP_PRIVILAGE')">
                                    <button id="crearBackup-btn" type="button" title="Crear Backup" class="btn-blue btn-sm btn crearBackup-btn ml-2"><i class="fa-solid fa-cloud"></i></button>
                                </sec:authorize>
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 54vh; padding:4px;">
                                    <table id="backupTable" class="table table-bordered table-striped text-center dataTable dtr-inline mt-1"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Formulario crear backup-->
    <form id="formGuardar" method="post" action="/CrearBackup">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
    <!-- Formulario restaurar backup-->
    <form id="formRestaurar" method="post" action="/RestaurarBackup">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
    <!-- Formulario exportar backup-->
    <form id="formExportar" method="post" action="/ExportarBackup">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
    <!-- Formulario descargar backup-->
    <form id="formDescargar" method="post" action="/DescargarBackup">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
    <!-- Modal de eliminar -->
    <div class="modal fade" id="confirmarEliminarModal" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <strong>¿Estás seguro de eliminar la copia de seguridad seleccionada?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados a todo el sistema.</p>
                </div>
                <div class="modal-footer">
                  <button id="eliminarBackupBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
                  <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    <form id="eliminarBackupForm" method="post" action="/EliminarBackup">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
</div>

<!-- Pantalla de carga --> 
<div id="loadingOverlay">
    <div class="loading-spinner">
        <div class="spinner-border" role="status">
            <span class="sr-only">Cargando...</span>
        </div>
        <p id="contenido-pagina-carga">Generando reporte...</p>
    </div>
</div>
    
<!-- Script de la página -->
<%@ include file="../common/footer.jspf"%>

<sec:authorize access="hasAuthority('EDITAR_BACKUP_PRIVILAGE')" var="hasPrivilegeRestaurarBackup"></sec:authorize>
<script>var hasPrivilegeRestaurarBackup = <c:out value='${hasPrivilegeRestaurarBackup}' />;</script>    

<sec:authorize access="hasAuthority('EXPORTAR_EMPRESA_PRIVILAGE')" var="hasPrivilegeExportarBackup"></sec:authorize>
<script>var hasPrivilegeExportarBackup = <c:out value='${hasPrivilegeExportarBackup}' />;</script>

<sec:authorize access="hasAuthority('ELIMINAR_EMPRESA_PRIVILAGE')" var="hasPrivilegeEliminarBackup"></sec:authorize>
<script>var hasPrivilegeEliminarBackup = <c:out value='${hasPrivilegeEliminarBackup}' />;</script>

<script src="${pageContext.request.contextPath}/js/Backup/BackupNube.js"></script>

