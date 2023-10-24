<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationProyecto.jspf"%>
<div class="content-wrapper">
    <div id="proyectoId" class="hidden" data-id="${proyecto.idProyecto}"></div>
    <!-- T�tulo de la p�gina -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1>Registro de Pagos</h1>
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
                        <!-- Funciones de la p�gina -->
                        <div class="card-header">
                            <h3 class="card-title d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('EXPORTAR_PAGO_PRIVILAGE')"> 
                                    <button id="export-copy" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><span>Copiar  </span><i class="fa-regular fa-copy"></i></button> 
                                    <button id="export-excel" class="btn btn-sm btn-outline-success buttons-excel ml-2" type="button"><span>Exportar </span><i class="fa-solid fa-file-csv"></i></button> 
                                    <button id="export-pdf" class="btn btn-sm btn-outline-danger buttons-pdf ml-2" type="button"><span>Exportar </span><i class="fa-regular fa-file-pdf"></i></button> 
                                </sec:authorize>
                                <sec:authorize access="hasAuthority('AGREGAR_PAGO_PRIVILAGE')"> 
                                    <button type="button" class="btn-blue btn abrirModal-btn btn-sm ml-2" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar"><span>Agregar </span><i class="fa-solid fa-file-pen"></i></button>
                                </sec:authorize> 
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 60vh; padding:4px;">
                                    <table id="pagoTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
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
                    <h5 class="modal-title" id="crearModalLabel">Agregar Pago</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="modal-footer">
                        <label for="seleccionar-pago" class="form-label">Seleccione un pago: </label>
                        <button id="btn-prima" class="btn btn-outline-success btn-sm">Prima</button>
                        <button type="btn-mantenimiento" class="btn btn-outline-success btn-sm">Mantenimiento</button>
                        <button type="btn-financiamiento" class="btn btn-outline-success btn-sm">Financiamiento</button>
                        <button type="button" class="btn btn-outline-danger btn-sm" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal de agregar prima-->
    <div class="modal fade" id="crearModalPrima" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel">Agregar Prima</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id='formGuardar' accept-charset="UTF-8">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idCuenta">
                        <div class="form-group">
                            <label for="nombre" class="form-label">Nombre: </label>
                            <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ingrese el nombre" required>
                        </div>
                        <div class="form-group">
                            <label for="titular" class="form-label">Titular: </label>
                            <input type="text" class="form-control" id="titular" name="titular" placeholder="Ingrese el titular" required>
                        </div>
                        <div class="form-group">
                            <label for="banco" class="form-label">Banco: </label>
                            <input type="text" class="form-control" id="banco" name="banco" placeholder="Ingrese el banco" required>
                        </div>
                        <div class="form-group">
                            <label for="tipo" class="form-label">Tipo: </label>
                            <input type="text" class="form-control" id="tipo" name="tipo" placeholder="Ingrese el tipo" required>
                        </div>
                        <div class="form-group">
                            <label for="cuenta" class="form-label">Cuenta: </label>
                            <input type="text" class="form-control" id="cuenta" name="cuenta" placeholder="Ingrese n�mero de cuenta" required>
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
                    <strong>�Est�s seguro de eliminar el pago seleccionado?</strong>
                    <p>Ten en cuenta que se eliminar�n los datos relacionados al pago.</p>
                </div>
                <div class="modal-footer">
                  <button id="eliminarPagoBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
                  <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    <form id="eliminarPagoForm" method="post" action="/EliminarPago/{idPago}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>                    
</div>

<!-- Script de la p�gina -->
<sec:authorize access="hasAuthority('EDITAR_CUENTA_BANCARIA_PRIVILAGE')" var="hasPrivilegeEditarCuenta"></sec:authorize>
<script>var hasPrivilegeEditarCuenta = ${hasPrivilegeEditarCuenta};</script>

<sec:authorize access="hasAuthority('ELIMINAR_CUENTA_BANCARIA_PRIVILAGE')" var="hasPrivilegeEliminarCuenta"></sec:authorize>
<script>var hasPrivilegeEliminarCuenta = ${hasPrivilegeEliminarCuenta};</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/cuentaBancaria.js"></script>

