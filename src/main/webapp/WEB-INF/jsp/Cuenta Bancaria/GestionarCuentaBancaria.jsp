<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationEmpresa.jspf"%>
<div class="content-wrapper">
    <div id="empresaId" class="hidden" data-id="${empresa.idEmpresa}"></div>
    <!-- Título de la página -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1>Cuentas Bancarias</h1>
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
                                    <sec:authorize access="hasAuthority('EXPORTAR_CUENTA_BANCARIA_PRIVILAGE')">
                                        <button id="export-copy" title="Copiar" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><i class="fa-regular fa-copy"></i></button> 
                                        <button id="export-excel" title="Exportar Excel" class="btn btn-sm btn-outline-success buttons-excel ml-2" type="button"><i class="fa-solid fa-file-excel"></i></button> 
                                        <button id="export-pdf" title="Exportar PDF" class="btn btn-sm btn-outline-danger buttons-pdf ml-2" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                    </sec:authorize>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <sec:authorize access="hasAuthority('AGREGAR_CUENTA_BANCARIA_PRIVILAGE')"> 
                                        <button type="button" title="Agregar Cuenta" class="btn-blue btn-sm btn abrirModal-btn ml-2" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar"><i class="fa-solid fa-file-pen"></i></button>
                                    </sec:authorize> 
                                </div>
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 60vh; padding:4px;">
                                    <table id="cuentaTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
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
                    <h5 class="modal-title" id="crearModalLabel">Agregar Cuenta</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id='formGuardar' accept-charset="UTF-8">
                        <div  class="overflow-auto">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="hidden" class="form-control" id="idCuenta">
                            <div class="form-group">
                                <label for="nombre" class="form-label">Nombre:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ingrese el nombre" maxlength="200" required>
                            </div>
                            <div class="form-group">
                                <label for="titular" class="form-label">Titular:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control" id="titular" name="titular" placeholder="Ingrese el titular" maxlength="200" required>
                            </div>
                            <div class="form-group">
                                <label for="banco" class="form-label">Banco:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control" id="banco" name="banco" placeholder="Ingrese el banco" maxlength="200" required>
                            </div>
                            <div class="form-group">
                                <label for="tipo" class="form-label">Tipo:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control" id="tipo" name="tipo" placeholder="Ingrese el tipo" maxlength="20" required>
                            </div>
                            <div class="form-group">
                                <label for="cuenta" class="form-label">Cuenta:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control" id="cuenta" name="cuenta" placeholder="Ingrese número de cuenta" maxlength="20" required>
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
    <div class="modal fade" id="confirmarEliminarModal" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <strong>¿Estás seguro de eliminar la cuenta seleccionada?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados a la cuenta.</p>
                </div>
                <div class="modal-footer">
                  <button id="eliminarCuentaBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
                  <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    <form id="eliminarCuentaForm" method="post" action="/EliminarCuenta/{idCuenta}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>                    
</div>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('EDITAR_CUENTA_BANCARIA_PRIVILAGE')" var="hasPrivilegeEditarCuenta"></sec:authorize>
<script>var hasPrivilegeEditarCuenta = <c:out value='${hasPrivilegeEditarCuenta}' />;</script>

<sec:authorize access="hasAuthority('ELIMINAR_CUENTA_BANCARIA_PRIVILAGE')" var="hasPrivilegeEliminarCuenta"></sec:authorize>
<script>var hasPrivilegeEliminarCuenta = <c:out value='${hasPrivilegeEliminarCuenta}' />;</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Cuenta Bancaria/CuentaBancaria.js"></script>

