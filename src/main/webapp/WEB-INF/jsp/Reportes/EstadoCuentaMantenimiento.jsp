<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Estado de Cuenta de Mantenimiento</title>
    <style>
        #estadoCuentaTable_wrapper > .row {
            margin-left: 0 !important;
            margin-right: 0 !important;
        }
        @page {
            size: letter;
            margin: 1cm;
        }
        @media print {
            body {
                font-family: Arial, sans-serif;
                line-height: 1.5;
                margin: 0;
            }
            table {
                margin-top: 0;
            }
            #estadoCuentaTable_wrapper > .row {
                margin-right: calc(var(--bs-gutter-x) * -.5) !important;
                margin-left: calc(var(--bs-gutter-x) * -.5) !important;
            }
        }
    </style>
</head>
<body class="bg-white">
    <div class="text-center mb-3 w-100">
        <h6 class="fw-normal p-0 m-0 w-100" style="text-transform: uppercase;">PROYECTO ${venta.terreno.proyecto.nombre}</h6>
        <h6 class="fw-normal p-0 m-0 w-100">ESTADO DE CUENTA DE MANTENIMIENTO</h6>
        <div class="border-bottom border-2" style="margin-left:20%; width: 60%;"></div>
        <h7 class="fw-normal pt-0 m-0 w-100" style="text-transform: uppercase;">LOTE ${venta.terreno.numero}${venta.terreno.seccion} POLIGONO ${venta.terreno.poligono}</h7>
    </div>
    <div class="text-center">
        <p class="p-0 m-0 mb-3">
            Propietarios: 
            <br>
            <c:forEach items="${propietarios}" var="asignacion" varStatus="status">
                <c:if test="${status.index > 0}">,</c:if>
                ${asignacion.propietario.persona.nombre} ${asignacion.propietario.persona.apellido}
            </c:forEach>
        </p>
    </div>  
    <table id="estadoCuentaTable" class="table datatable table-sm table-bordered text-center w-100">
        <thead style="top:0px !important;">
            <tr>
                <th style="width: 13%;">F. Pago</th>
                <th style="width: 8%;">Recibo</th>
                <th style="width: 13%;">F. Cuota</th>
                <th style="width: 11%;">Cuota</th>
                <th style="width: 11%;">Recargo</th>
                <th style="width: 11%;">Descuento</th>
                <th style="width: 11%;">Otros</th>
                <th style="width: 11%;">Abono</th>
                <th style="width: 11%;">Pendiente</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${cuotas}" var="cuota" varStatus="status">
                <tr>
                    <td><fmt:formatDate value="${cuota.pago.fecha}" pattern="dd/MM/yyyy" /></td>
                    <td>${cuota.pago.recibo}</td>
                    <td><fmt:formatDate value="${cuota.fechaCuota}" pattern="dd/MM/yyyy"/></td>
                    <td><c:out value="${String.format('%.2f', cuota.cuota)}"/></td>
                    <td><c:out value="${String.format('%.2f', cuota.recargo)}"/></td>
                    <td><c:out value="${String.format('%.2f', cuota.descuento)}"/></td>
                    <td><c:out value="${String.format('%.2f', cuota.otros)}"/></td>
                    <td><c:out value="${String.format('%.2f', cuota.cuota + cuota.recargo - cuota.descuento)}"/></td>
                    <td><c:out value="${String.format('%.2f', cuota.saldoCuota + cuota.saldoRecargo)}"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
