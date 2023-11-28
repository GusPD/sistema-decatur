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
                            <h1>Empresas</h1>
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
                        <!-- Funciones de la página -->
                        <div class="card-header">
                            <h3 class="card-title d-flex justify-content-between">
                                <div class="d-flex justify-content-estart">
                                    <sec:authorize access="hasAuthority('EXPORTAR_EMPRESA_PRIVILAGE')">
                                        <button id="export-copy" title="Copiar" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><i class="fa-regular fa-copy"></i></button> 
                                        <button id="export-excel" title="Exportar Excel" class="btn btn-sm btn-outline-success buttons-excel ml-2" type="button"><i class="fa-solid fa-file-excel"></i></button> 
                                        <button id="export-pdf" title="Exportar PDF" class="btn btn-sm btn-outline-danger buttons-pdf ml-2" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                    </sec:authorize>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <sec:authorize access="hasAuthority('AGREGAR_EMPRESA_PRIVILAGE')"> 
                                        <button type="button" title="Agregar Empresa" class="btn-blue btn-sm btn abrirModal-btn ml-2" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar"><i class="fa-solid fa-file-pen"></i></button>
                                    </sec:authorize> 
                                </div>
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 60vh; padding:4px;">
                                    <table id="empresaTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
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
                    <h5 class="modal-title" id="crearModalLabel">Agregar Empresa</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form id='formGuardar' accept-charset="UTF-8">
                    <div class="modal-body">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idEmpresa">
                        <div class="form-group">
                            <label for="nombre" class="form-label">Nombre:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="nombre" name="nombre" placeholder="Empresa" required>
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
                    <strong>¿Estás seguro de eliminar la empresa seleccionado?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados a la empresa.</p>
                </div>
                <div class="modal-footer">
                  <button id="eliminarEmpresaBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
                  <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    <form id="eliminarEmpresaForm" method="post" action="/EliminarEmpresa/{idEmpresa}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>                    
</div>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('VER_EMPRESA_PRIVILAGE')" var="hasPrivilegeVerEmpresa"></sec:authorize>
<script>var hasPrivilegeVerEmpresa = <c:out value='${hasPrivilegeVerEmpresa}' />;</script>    
        
<sec:authorize access="hasAuthority('EDITAR_EMPRESA_PRIVILAGE')" var="hasPrivilegeEditarEmpresa"></sec:authorize>
<script>var hasPrivilegeEditarEmpresa = <c:out value='${hasPrivilegeEditarEmpresa}' />;</script>

<sec:authorize access="hasAuthority('ELIMINAR_EMPRESA_PRIVILAGE')" var="hasPrivilegeEliminarEmpresa"></sec:authorize>
<script>var hasPrivilegeEliminarEmpresa = <c:out value='${hasPrivilegeEliminarEmpresa}' />;</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Empresa/Empresa.js"></script>

