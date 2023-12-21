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
                            <h3 class="card-title d-flex justify-content-between">
                                <div class="d-flex justify-content-estart">
                                    <sec:authorize access="hasAuthority('EXPORTAR_PAGO_PRIVILAGE')">
                                        <button id="export-copy" title="Copiar" class="btn btn-outline-secondary buttons-copy btn-sm" type="button"><i class="fa-regular fa-copy"></i></button> 
                                        <button id="export-excel" title="Exportar Excel" class="btn btn-outline-success buttons-excel ml-2 btn-sm" type="button"><i class="fa-solid fa-file-excel"></i></button> 
                                        <button id="export-pdf" title="Exportar PDF" class="btn btn-outline-danger buttons-pdf ml-2 btn-sm" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                    </sec:authorize>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <sec:authorize access="hasAuthority('EXPORTAR_PAGO_PRIVILAGE')">
                                        <button id="b_clear" title="Eliminar Filtros" class="btn btn-outline-dark btn-sm" type="button"><i class="fa-solid fa-filter-circle-xmark"></i></button>
                                        <button id="b_buscar" title="Filtrar Registros" class="btn btn-outline-dark btn-sm" type="button"><i class="fa-solid fa-filter"></i></button>
                                    </sec:authorize>
                                    <sec:authorize access="hasAuthority('AGREGAR_PAGO_PRIVILAGE')"> 
                                        <button type="button" title="Agregar Pago" class="btn-blue btn abrirModal-btn ml-2 btn-sm" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar"><i class="fa-solid fa-file-pen"></i></button>
                                    </sec:authorize>
                                </div>
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 d-flex display-alineacion border-bottom mb-3" style="padding:4px;">
                                    <div class="col-sm-2 form-group" style="padding-left: 0%!Important; padding-right: 1%!Important;">
                                        <label for="b_fecha_inicio" class="form-label">Fecha Inicio:</label>
                                        <input type="date" class="form-control form-control-sm" id="b_fecha_inicio" name="b_fecha_inicio" maxlength="10">
                                    </div>
                                    <div class="col-sm-2 form-group" style="padding-left: 0%!Important; padding-right: 1%!Important;">
                                        <label for="b_fecha_fin" class="form-label">Fecha Fin:</label>
                                        <input type="date" class="form-control form-control-sm" id="b_fecha_fin" name="b_fecha_fin" maxlength="10">
                                    </div>
                                    <div class="col-sm-2 form-group" style="padding-left: 0%!Important; padding-right: 1%!Important;">
                                        <label for="b_tipo" class="form-label">Tipo:</label>
                                        <select class="form-select form-select-sm" id="b_tipo" name="b_tipo" placeholder="Seleccione una opción">
                                            <option value="">Seleccione una opción</option>
                                            <option value="Prima">Prima</option>
                                            <option value="Mantenimiento">Mantenimiento</option>
                                            <option value="Financiamiento">Financiamiento</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 form-group" style="padding-left: 0%!Important; padding-right: 1%!Important;">
                                        <label for="b_estado" class="form-label">Estado:</label>
                                        <select class="form-select form-select-sm" id="b_estado" name="b_estado" placeholder="Seleccione una opción">
                                            <option value="">Seleccione una opción</option>
                                            <option value="false">Anulado</option>
                                            <option value="true">No anulado</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 form-group" style="padding-left: 0%!Important; padding-right: 1%!Important;">
                                        <label for="b_cuenta" class="form-label">Cuenta:</label>
                                        <select class="form-select form-select-sm" id="b_cuenta" name="b_cuenta" placeholder="Seleccione una opción">
                                            <option value="0">Seleccione una opción</option>
                                            <c:if test="${not empty cuentas}">
                                                <c:forEach items="${cuentas}" var="eCuenta">
                                                    <option value="${eCuenta.idCuenta}">${eCuenta.nombre}</option>
                                                </c:forEach>
                                            </c:if>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 form-group" style="padding-left: 0%!Important; padding-right: 1%!Important;">
                                        <label for="b_lote" class="form-label">Comprobante:</label>
                                        <select class="form-select form-select-sm" id="b_comprobante" name="b_comprobante" placeholder="Seleccione una opción">
                                            <option value="">Seleccione una opción</option>
                                            <option value="Ticket">Ticket</option>
                                            <option value="Recibo">Recibo</option>
                                            <option value="Factura">Factura</option>
                                            <option value="Crédito Fiscal">Crédito Fiscal</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-12 table-responsive pt-1" style="height: 43vh; padding:4px;">
                                    <table id="pagoTable" class="table table-bordered table-striped text-center dataTable dtr-inline mt-1"></table>
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
                <div class="modal-body pb-0">
                    <div class="form-group row display-alineacion">
                        <label for="seleccionar-pago" class="form-label">Seleccione un pago: </label>
                        <div class="col">
                            <button id="btn-prima" class="btn btn-outline-dark btn-sm btn-block" style="margin: 0px !important;">Prima</button>
                        </div>
                        <div class="col">
                            <button id="btn-mantenimiento" class="btn btn-outline-dark btn-sm btn-block" style="margin: 0px !important;">Mantenimiento</button>
                        </div>
                        <div class="col">
                            <button id="btn-financiamiento" class="btn btn-outline-dark btn-sm btn-block" style="margin: 0px !important;">Financiamiento</button>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal de agregar pago-->
    <div class="modal fade" id="crearModalGuardar" tabindex="-1" aria-labelledby="crearModalLabelPago" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabelPago">Agregar</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form id='formGuardar' accept-charset="UTF-8">
                    <div class="modal-body">
                        <div  class="overflow-auto">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="hidden" id="idPago">
                            <input type="hidden" id="fechaRegistro">
                            <input type="hidden" id="tipo" value="">
                            <input type="checkbox" class="form-check-input d-none" id="estado" name="estado" checked>
                            <div class="form-group">
                                <label for="cuenta" class="form-label">Lote:<strong class="text-danger"> *</strong></label>
                                <select class="form-select form-select-sm" id="venta" name="venta" placeholder="Seleccione una opción" data-live-search="true" required></select>
                                <div id="span-lotes-error" class="mensaje-error d-none"><span>Este campo es requerido</span></div>
                            </div>
                            <div class="form-group">
                                <label for="comprobante" class="form-label">Comprobante:<strong class="text-danger"> *</strong></label>
                                <select class="form-select form-select-sm" id="comprobante" name="comprobante" placeholder="Seleccione una opción" required>
                                    <option value="">Seleccione una opción</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="cuenta" class="form-label">Cuenta:<strong class="text-danger"> *</strong></label>
                                <select class="form-select form-select-sm" id="cuenta" name="cuenta" placeholder="Seleccione una opción" required>
                                    <option value="">Seleccione una opción</option>
                                    <c:if test="${not empty cuentas}">
                                        <c:forEach items="${cuentas}" var="eCuenta">
                                            <option value="${eCuenta.idCuenta}">${eCuenta.nombre}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="referencia" class="form-label">Referencia:</label>
                                <input type="text" class="form-control form-control-sm" id="referencia" name="referencia" maxlength="20" placeholder="Ingrese la referencia del abono">
                            </div>
                            <div class="form-group">
                                <label for="fecha" class="form-label">Fecha Pago:<strong class="text-danger"> *</strong></label>
                                <input type="date" class="form-control form-control-sm" id="fecha" name="fecha" maxlength="10" required>
                            </div>
                            <div class="form-group">
                                <label for="recibo" class="form-label">N° Comprobante:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control form-control-sm" id="recibo" name="recibo" maxlength="5" placeholder="Ingrese el número de recibo" required>
                            </div>
                            <div class="form-group">
                                <label for="monto" class="form-label">Monto:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control form-control-sm" id="monto" name="monto" maxlength="10" placeholder="Ingrese el monto" required>
                            </div>
                            <div id="group-otros" class="form-group" style="display: none">
                                <label for="otros" class="form-label">Otros:</label>
                                <input type="text" class="form-control form-control-sm" id="otros" name="otros" value="0.00" maxlength="10" placeholder="Ingrese el monto en concepto de otros" required>
                            </div>
                            <div id="group-descuento" class="form-group" style="display: none">
                                <label for="descuento" class="form-label">Descuento:</label>
                                <input type="text" class="form-control form-control-sm" id="descuento" name="descuento" value="0.00"  maxlength="10" placeholder="Ingrese el monto en concepto de descuento" required>
                            </div>
                            <div class="form-group">
                                <label for="observaciones" class="form-label">Observaciones:</label>
                                <textarea class="form-control form-control-sm" id="observaciones" name="observaciones" maxlength="500" placeholder="Ingrese las observaciones"></textarea>
                            </div>
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
                    <strong>¿Estás seguro de eliminar el pago seleccionado?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados al pago, y los pagos realizados a continuación a este.</p>
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

<!-- Modal Reporte Impresión -->
<div class="modal fade" id="reporteModal" tabindex="-1" aria-labelledby="reporteModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="reporteModalLabel">Vista Previa de Impresión</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="overflow-auto">
                    <div id="contenedorDePagina" class="bg-white m-0 p-0"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="btnImprimir" type="button" class="btn btn-sm btn-outline-dark" data-bs-dismiss="modal">Imprimir</button>
            </div>
        </div>
    </div>
</div>

<!-- Pantalla de carga --> 
<div id="loadingOverlay">
    <div class="loading-spinner">
        <div class="spinner-border" role="status">
            <span class="sr-only">Cargando...</span>
        </div>
        <p>Generando reporte...</p>
    </div>
</div>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('VER_PAGO_PRIVILAGE')" var="hasPrivilegeVerPago"></sec:authorize>
<script>var hasPrivilegeVerPago = <c:out value='${hasPrivilegeVerPago}'/>;</script>

<sec:authorize access="hasAuthority('EDITAR_PAGO_PRIVILAGE')" var="hasPrivilegeEditarPago"></sec:authorize>
<script>var hasPrivilegeEditarPago = <c:out value='${hasPrivilegeEditarPago}'/>;</script>

<sec:authorize access="hasAuthority('ELIMINAR_PAGO_PRIVILAGE')" var="hasPrivilegeEliminarPago"></sec:authorize>
<script>var hasPrivilegeEliminarPago = <c:out value='${hasPrivilegeEliminarPago}'/>;</script>

<sec:authorize access="hasAuthority('EXPORTAR_PAGO_PRIVILAGE')" var="hasPrivilegeImprimirPago"></sec:authorize>
<script>var hasPrivilegeImprimirPago = <c:out value='${hasPrivilegeImprimirPago}'/>;</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Pago/Pago.js"></script>

