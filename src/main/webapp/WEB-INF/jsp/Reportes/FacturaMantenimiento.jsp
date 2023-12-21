<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Recibo de Mantenimiento</title>
    <style>
        .contenedor-fecha-pago{
            display: flex;
            margin: 0;
            padding: 0;
        }
        .contenedor-mantenimiento{
            display: flex;
            margin: 0;
            padding: 0;
        }
        .contenedor-recargo{
            display: flex;
            margin: 0;
            padding: 0;
        }
        .contenedor-descuento{
            display: flex;
            margin: 0;
            padding: 0;
        }
        .contenedor-otros{
            display: flex;
            margin: 0;
            padding: 0;
        }
        .parrafo{
            padding: 0;
            margin: 0;
        }
        .oculto{
            display: none;
        }
        @page {
            size: letter;
            margin: 0;
        }
        @media print {
            body {
                font-family: 'Arial', sans-serif;
                font-size: 13px;
                line-height: 1;
                margin: 0;
            }
            .vista-impresion{
                display: none;
            }
            .factura {
                width: 20.2cm;
                height: 24.8cm;
                margin: 0 auto;
            }
            .parte-superior-factura{
                display: flex;
                width: 20.2cm;
                height: 7.3cm;
                margin: 0;
                padding: 0;
            }
            .parte-inferior-factura{
                width: 20.2cm;
                height: 17.5cm;
                margin: 0;
                padding: 0;
            }
            .datos-cliente{
                width: 13.4cm;
                height: 7.3cm;
                margin: 0;
                padding: 0;
            }
            .propietario{
                overflow: hidden;
                margin-top: 5cm;
                margin-bottom: 0.0cm;
                margin-left: 2.2cm;
                margin-right: 0.0cm;
                width: 10.8cm;
                height: 13px;
            }
            .direccion{
                overflow: hidden;
                margin-top: 0.5cm;
                margin-bottom: 0.0cm;
                margin-left: 2.5cm;
                margin-right: 0.0cm;
                width: 10.5cm;
                height: 13px;
            }
            .contenedor-municipio-departamento{
                display: flex;
                margin-top: 0.6cm;
                margin-bottom: 0.0cm;
                margin-left: 0.0cm;
                margin-right: 0.0cm;
                padding: 0;
            }
            .municipio{
                margin-top: 0.0cm;
                margin-bottom: 0.0cm;
                margin-left: 2.5cm;
                margin-right: 0.0cm;
                width: 3.8cm;
                height: 13px;
            }
            .departamento{
                margin-top: 0.0cm;
                margin-bottom: 0.0cm;
                margin-left: 1.7cm;
                margin-right: 0.0cm;
                width: 5.2cm;
                height: 13px;
            }
            .fecha-factura{
                width: 6.4cm;
                height: 7.3cm;
                margin: 0;
                padding: 0;
            }
            .contenedor-fecha-pago{
                display: flex;
                margin-top: 4.85cm;
                margin-bottom: 0.0cm;
                margin-left: 0.0cm;
                margin-right: 0.0cm;
                padding: 0;
            }
            .dia-pago{
                margin-top: 0.0cm;
                margin-bottom: 0.0cm;
                margin-left: 0.6cm;
                margin-right: 0.0cm;
                width: 0.4cm;
                height: 13px;
            }
            .mes-pago{
                margin-top: 0.0cm;
                margin-bottom: 0.0cm;
                margin-left: 1.5cm;
                margin-right: 0.0cm;
                width: 2cm;
                height: 13px;
            }
            .anio-pago{
                margin-top: 0.0cm;
                margin-bottom: 0.0cm;
                margin-left: 0.6cm;
                margin-right: 0.0cm;
                width: 1cm;
                height: 13px;
            }
            .numero-factura{
                margin-top: 1.45cm;
                margin-bottom: 0.0cm;
                margin-left: 4.5cm;
                margin-right: 0.0cm;
                height: 13px;
            }
            .contenedor-registros{
                overflow: hidden;
                margin: 0;
                padding: 0;
                width: 20.2cm;
                height: 4cm;
            }
            .contenedor-mantenimiento{
                display: flex;
                margin-top: 1.2cm;
                margin-bottom: 0.0cm;
                margin-left: 0.0cm;
                margin-right: 0.0cm;
                padding: 0;
            }
            .contenedor-recargo{
                display: flex;
                margin-top: 0.1cm;
                margin-bottom: 0.0cm;
                margin-left: 0.0cm;
                margin-right: 0.0cm;
                padding: 0;
            }
            .contenedor-descuento{
                display: flex;
                margin-top: 0.1cm;
                margin-bottom: 0.0cm;
                margin-left: 0.0cm;
                margin-right: 0.0cm;
                padding: 0;
            }
            .contenedor-otros{
                display: flex;
                margin-top: 0.1cm;
                margin-bottom: 0.0cm;
                margin-left: 0.0cm;
                margin-right: 0.0cm;
                padding: 0;
            }
            .concepto-registro{
                font-family: 'Arial Narrow', sans-serif;
                margin-top: 0.0cm;
                margin-bottom: 0.0cm;
                margin-left: 4.7cm;
                margin-right: 0.0cm;
                width: 7.6cm;
            }
            .valor-registro{
                font-family: 'Arial Narrow', sans-serif;
                text-align: right;
                margin-top: 0.0cm;
                margin-bottom: 0.0cm;
                margin-left: 5.9cm;
                margin-right: 0.0cm;
                width: 1.5cm;
            }
            .contenedor-observaciones{
                overflow: hidden;
                font-family: 'Arial Narrow', sans-serif;
                margin-top: 0.0cm;
                margin-bottom: 0.0cm;
                margin-left: 0.0cm;
                margin-right: 0.0cm;
                padding: 0;
                width: 20.2cm;
                height: 4.5cm;
            }
            .parrafo{
                padding: 0;
                margin: 0;
            }
            .texto-observaciones{
                overflow: hidden;
                margin-top: 0.2cm;
                margin-bottom: 0.0cm;
                margin-left: 4.7cm;
                margin-right: 0.0cm;
                padding: 0;
                width: 7.6cm;
            }
            .oculto{
                display: block;
            }
            .valor-suma{
                font-family: 'Arial Narrow', sans-serif;
                text-align: right;
                margin-top: 0.0cm;
                margin-bottom: 0.0cm;
                margin-left: 18.2cm;
                margin-right: 0.0cm;
                padding: 0;
                width: 1.5cm;
            }
            .valor-total{
                font-family: 'Arial Narrow', sans-serif;
                text-align: right;
                margin-top: 2.2cm;
                margin-bottom: 0.0cm;
                margin-left: 18.2cm;
                margin-right: 0.0cm;
                padding: 0;
                width: 1.5cm;
            }
        }
    </style>
</head>
<body>
    <div class="factura p-0">
        <div class="parte-superior-factura">
            <div class="datos-cliente">
                <p class="propietario"><strong class="vista-impresion">Cliente: </strong>${facturacion.nombre} ${pago.venta.terreno.poligono}-${pago.venta.terreno.numero}${pago.venta.terreno.seccion}</p>
                <p class="direccion"><strong class="vista-impresion">Dirección: </strong>${facturacion.direccion}</p>
                <div class="contenedor-municipio-departamento">
                    <p class="municipio"><strong class="vista-impresion">Municipio: </strong>${facturacion.municipio.nombre}</p><p class="departamento"><strong class="vista-impresion">Departamento: </strong>${facturacion.municipio.departamento.nombre}</p>
                </div>
            </div>
            <div class="fecha-factura">
                <div class="contenedor-fecha-pago">
                    <p class="dia-pago"><strong class="vista-impresion">Fecha pago: </strong><fmt:formatDate value="${pago.fecha}" pattern="dd" /></p><p class="mes-pago p-0">&nbsp<fmt:formatDate value="${pago.fecha}" pattern="MMMM"/>&nbsp</p><p class="anio-pago p-0"><fmt:formatDate value="${pago.fecha}" pattern="yyyy" /></p>
                </div>
                <p class="numero-factura"><strong class="vista-impresion">Factura: </strong>${pago.recibo}</p>             
            </div>
        </div>
        <div class="parte-inferior-factura">
            <div class="contenedor-registros">
                <div class="contenedor-mantenimiento">
                    <p class="concepto-registro"><c:if test="${montoMantenimiento>0}"><strong>Mtto: </strong>${cuota}</c:if></p><p class="valor-registro"><c:if test="${montoMantenimiento>0}"><strong class="vista-impresion">&nbsp;&nbsp;$ </strong><c:out value="${String.format('%.2f', montoMantenimiento)}"/></c:if></p>
                </div>
                <c:if test="${montoRecargo>0}">
                    <div class="contenedor-recargo">
                        <p class="concepto-registro"><strong>Recargo: </strong>${recargo}</p><p class="valor-registro"><strong class="vista-impresion">&nbsp;&nbsp;$ </strong><c:out value="${String.format('%.2f', montoRecargo)}"/></p>
                    </div>
                </c:if>
                <c:if test="${pago.descuento>0}">
                    <div class="contenedor-descuento">
                        <p class="concepto-registro"><strong>Descuento de recargo</strong></p><p class="valor-registro"><strong class="vista-impresion">&nbsp;&nbsp;$ </strong>-<c:out value="${String.format('%.2f', pago.descuento)}"/></p>
                    </div>
                </c:if>
                <c:if test="${pago.otros>0}">
                    <div class="contenedor-otros">
                        <p class="concepto-registro"><strong>Otros</strong></p><p class="valor-registro"><strong class="vista-impresion">&nbsp;&nbsp;$ </strong><c:out value="${String.format('%.2f', pago.otros)}"/></p>
                    </div>
                </c:if>
            </div>
            <div class="contenedor-observaciones">
                <div class="texto-observaciones">
                    <strong>Observaciones: </strong>
                    <p class="parrafo"><c:if test="${(montoMantenimiento+montoRecargo)>0}">Fecha de corte <fmt:formatDate value="${pago.venta.fechaCorteMantenimiento}" pattern="dd" /> de cada mes</c:if></p>
                    <p class="parrafo"><c:if test="${not empty estado}">${estado}<br></c:if><c:if test="${montoPendiente>0 && (montoMantenimiento+montoRecargo)>0}">${pendiente}<br></c:if>${pago.observaciones}</p>
                </div>
            </div>
            <p class="valor-suma oculto"><strong class="vista-impresion">Sumas: </strong><c:out value="${String.format('%.2f', pago.monto)}"/></p>
            <p class="valor-total"><strong class="vista-impresion">Total: </strong><c:out value="${String.format('%.2f', pago.monto)}"/></p>
        </div>
    </div>
</body>
</html>