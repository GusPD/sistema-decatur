<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationProyecto.jspf"%>
<div class="content-wrapper">
    <!-- T�tulo de la p�gina -->
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
                        <!-- Funciones de la p�gina -->
                        <div class="card-header">
                            <h3 class="m-0">
                                <div class="d-flex">
                                    <div class="col-sm-6">
                                        Recibo de ${pago.tipo} N� ${pago.recibo}
                                    </div>
                                    <sec:authorize access="hasAuthority('IMPRIMIR_PAGO_PRIVILAGE')"> 
                                        <button type="button" title="Imprimir Pago" class=" btn btn-outline-dark abrirModal-btn ml-2"><i class="fa-solid fa-print"></i></button>
                                    </sec:authorize>
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
                                                <td width="20%" class="encabezado-tabla font-weight-bold">Fecha Pago</td>
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
                                            <table id="cuotaMantenimientoTable" class="table table-bordered table-striped dataTable dtr-inline"></table>
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

<!-- Script de la p�gina -->
<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Pago/Recibo.js"></script>

