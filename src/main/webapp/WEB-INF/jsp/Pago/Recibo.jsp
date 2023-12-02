<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationProyecto.jspf"%>
<div class="content-wrapper">
    <!-- Título de la página -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
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
                    <div class="card mb-3">
                        <!-- Funciones de la página -->
                        <div class="card-header">
                            <h3 class="m-0">
                                <div class="d-flex">
                                    <div class="col-sm-6">
                                        Recibo de ${pago.tipo} N° ${pago.recibo}
                                    </div>
                                    <div class="col-sm-6 d-flex justify-content-end">
                                        <sec:authorize access="hasAuthority('EXPORTAR_PAGO_PRIVILAGE')"> 
                                            <button id="btn-imprimir" title="Imprimir Recibo" type="button" class="btn btn-sm btn-outline-dark" data-bs-toggle="modal"><i class="fa-solid fa-print"></i></button>
                                        </sec:authorize>
                                    </div>
                                </div>
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="padding:4px;">
                                    <table id="tabla-informacion" class="table small table-bordered">
                                        <input type="hidden" id="idPago" value="${pago.getIdPago()}">
                                        <tbody>
                                            <tr>
                                                <td width="20%" class="encabezado-tabla font-weight-bold">Lote</td>
                                                <td><c:if test="${not empty pago.venta.terreno.poligono}">${pago.venta.terreno.poligono}-${pago.venta.terreno.numero}${pago.venta.terreno.seccion}</c:if></td>
                                            </tr>
                                            <tr>
                                                <td class="encabezado-tabla font-weight-bold">Fecha Pago</td>
                                                <td><c:if test="${not empty pago.fecha}"><fmt:formatDate value="${pago.fecha}" pattern="dd/MM/yyyy" /></c:if></td>
                                            </tr>
                                            <tr>
                                                <td class="encabezado-tabla font-weight-bold">Tipo Pago</td>
                                                <td><c:if test="${not empty pago.cuentaBancaria.nombre}">${pago.cuentaBancaria.nombre}</c:if></td>
                                            </tr>
                                            <tr>
                                                <td class="encabezado-tabla font-weight-bold">Monto</td>
                                                <td><c:if test="${not empty pago.monto}">$ <c:out value="${String.format('%.2f', pago.monto)}"/></c:if></td>
                                            </tr>
                                            <c:if test="${pago.tipo != 'Prima'}">
                                                <tr>
                                                    <td class="encabezado-tabla font-weight-bold">Descuento</td>
                                                    <td><c:if test="${not empty pago.descuento}">$ <c:out value="${String.format('%.2f', pago.descuento)}"/></c:if></td>
                                                </tr>
                                                <tr>
                                                    <td class="encabezado-tabla font-weight-bold">Otros</td>
                                                    <td><c:if test="${not empty pago.otros}">$ <c:out value="${String.format('%.2f', pago.otros)}"/></c:if></td>
                                                </tr>
                                            </c:if>
                                            <tr>
                                                <td class="encabezado-tabla font-weight-bold">Observaciones</td>
                                                <td><c:if test="${not empty pago.observaciones}">${pago.observaciones}</c:if></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <c:if test="${not empty listaCuotaMantenimientos or not empty listaCuotaFinanciamientos}">
                                <div class="col-sm-12" style="padding:4px;">
                                    <h5 class="p-0 m-0">Cuotas Canceladas</h5>
                                </div>
                                </c:if>
                                <c:if test="${not empty listaCuotaMantenimientos}">
                                    <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                        <div class="col-sm-12 table-responsive" style="height: 48vh; padding:4px;">
                                            <table id="cuotaMantenimientoTable" class="table table-bordered table-striped text-center dataTable dtr-inline"></table>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>                   
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
<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Pago/Recibo.js"></script>

