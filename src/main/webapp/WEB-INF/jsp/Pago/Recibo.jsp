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
                    <div class="card">
                        <!-- Funciones de la página -->
                        <div class="card-header">
                            <h3 class="card-title d-flex justify-content-end"><h3>Recibo ${pago.tipo} ${pago.recibo}</h3>
                                <sec:authorize access="hasAuthority('IMPRIMIR_PAGO_PRIVILAGE')"> 
                                    <button type="button" title="Imprimir Pago" class=" btn btn-outline-dark abrirModal-btn ml-2"><i class="fa-solid fa-print"></i></button>
                                </sec:authorize> 
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 40vh; padding:4px;">
                                    <table id="tabla-informacion" class="table small table-bordered">
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
                                    <table id="mantenimientoTable" class="table table-bordered table-striped dataTable dtr-inline mt-1">
                                        <thead>
                                            <tr>
                                                <th>N°</th>
                                                <th>Pago Cuota</th>
                                                <th>Mantenimiento</th>
                                                <th>Saldo Mantenimiento</th>
                                                <th>Recargo</th>
                                                <th>Saldo Recargo</th>
                                            </tr>
                                        </thead>
                                        <body>
                                            
                                        </body>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>                   
</div>

<!-- Script de la página -->
<%@ include file="../common/footer.jspf"%>

