<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationProyecto.jspf"%>
<div class="content-wrapper">
    <div id="proyectoId" class="hidden" data-id="${proyecto.idProyecto}"></div>
    <!-- Título de la página -->
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
                        <!-- Funciones de la página -->
                        <div class="card-header">
                            <h3 class="card-title d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('EXPORTAR_PAGO_PRIVILAGE')"> 
                                    <button id="export-copy" title="Copiar" class="btn btn-outline-secondary buttons-copy" type="button"><i class="fa-regular fa-copy"></i></button> 
                                    <button id="export-excel" title="Exportar Excel" class="btn btn-outline-success buttons-excel ml-2" type="button"><i class="fa-solid fa-file-csv"></i></button> 
                                    <button id="export-pdf" title="Exportar PDF" class="btn btn-outline-danger buttons-pdf ml-2" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                </sec:authorize>
                                <sec:authorize access="hasAuthority('AGREGAR_PAGO_PRIVILAGE')"> 
                                    <button type="button" title="Agregar Pago" class="btn-blue btn abrirModal-btn ml-2" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar"><i class="fa-solid fa-file-pen"></i></button>
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
                    <div class="form-group row">
                        <label for="seleccionar-pago" class="form-label">Seleccione un pago: </label>
                        <div class="col">
                            <button id="btn-prima" class="btn btn-outline-dark btn-sm btn-block">Prima</button>
                        </div>
                        <div class="col">
                            <button id="btn-mantenimiento" class="btn btn-outline-dark btn-sm btn-block">Mantenimiento</button>
                        </div>
                        <div class="col">
                            <button id="btn-financiamiento" class="btn btn-outline-dark btn-sm btn-block">Financiamiento</button>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal de agregar prima-->
    <div class="modal fade" id="crearModalGuardar" tabindex="-1" aria-labelledby="crearModalLabelPago" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabelPago">Agregar</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body ">
                    <form id='formGuardar' accept-charset="UTF-8">
                        <div  class="overflow-auto">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="hidden" id="idPago">
                            <input type="hidden" id="tipo" value="">
                            <input type="checkbox" class="form-check-input d-none" id="estado" name="estado" checked>
                            <div class="form-group">
                                <label for="cuenta" class="form-label">Lote:<strong class="text-danger"> *</strong></label>
                                <select class="form-select" id="venta" name="venta" placeholder="Seleccione una opción" data-live-search="true" required></select>
                                <div id="span-lotes-error" class="mensaje-error d-none" style=""><span>Este campo es requerido</span></div>
                            </div>
                            <div class="form-group">
                                <label for="comprobante" class="form-label">Comprobante:<strong class="text-danger"> *</strong></label>
                                <select class="form-select" id="comprobante" name="comprobante" placeholder="Seleccione una opción" required>
                                    <option value="Factura">Factura</option>
                                    <option value="Crédito Fiscal">Crédito Fiscal</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="cuenta" class="form-label">Tipo pago:<strong class="text-danger"> *</strong></label>
                                <select class="form-select" id="cuenta" name="cuenta" placeholder="Seleccione una opción" required>
                                    <c:if test="${not empty cuentas}">
                                        <c:forEach items="${cuentas}" var="eCuenta">
                                            <option value="${eCuenta.idCuenta}">${eCuenta.nombre}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="fecha" class="form-label">Fecha Pago:<strong class="text-danger"> *</strong></label>
                                <input type="date" class="form-control" id="fecha" name="fecha" maxlength="10" required>
                            </div>
                            <div class="form-group">
                                <label for="recibo" class="form-label">Recibo:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control" id="recibo" name="recibo" maxlength="5" placeholder="Ingrese el número de recibo" required>
                            </div>
                            <div class="form-group">
                                <label for="monto" class="form-label">Monto:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control" id="monto" name="monto" maxlength="10" placeholder="Ingrese el monto" required>
                            </div>
                            <div id="group-otros" class="form-group" style="display: none">
                                <label for="otros" class="form-label">Otros:</label>
                                <input type="text" class="form-control" id="otros" name="otros" value="0.00" maxlength="10" placeholder="Ingrese el monto en concepto de otros" required>
                            </div>
                            <div id="group-descuento" class="form-group" style="display: none">
                                <label for="descuento" class="form-label">Descuento:</label>
                                <input type="text" class="form-control" id="descuento" name="descuento" value="0.00"  maxlength="10" placeholder="Ingrese el monto en concepto de descuento" required>
                            </div>
                            <div class="form-group">
                                <label for="observaciones" class="form-label">Observaciones:</label>
                                <textarea class="form-control" id="observaciones" name="observaciones" placeholder="Ingrese las observaciones"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer d-flex justify-content-between">
                            <label for="monto" class="form-label text-danger">(*) Campos Obligatorios</label>
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
                    <strong>¿Estás seguro de eliminar el pago seleccionado?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados al pago.</p>
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

<!-- Script de la página -->
<sec:authorize access="hasAuthority('VER_PAGO_PRIVILAGE')" var="hasPrivilegeVerPago"></sec:authorize>
<script>var hasPrivilegeVerPago = ${hasPrivilegeVerPago};</script>

<sec:authorize access="hasAuthority('EDITAR_PAGO_PRIVILAGE')" var="hasPrivilegeEditarPago"></sec:authorize>
<script>var hasPrivilegeEditarPago = ${hasPrivilegeEditarPago};</script>

<sec:authorize access="hasAuthority('ELIMINAR_PAGO_PRIVILAGE')" var="hasPrivilegeEliminarPago"></sec:authorize>
<script>var hasPrivilegeEliminarPago = ${hasPrivilegeEliminarPago};</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/pago.js"></script>

