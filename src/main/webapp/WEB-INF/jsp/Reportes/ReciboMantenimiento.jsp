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
        .lote{
            display: flex;
        }
        .contenedor-fecha-pago{
            display: flex;
        }
        .texto-observaciones{
            text-align: justify;
            overflow: hidden;
            line-height: 1.4;
            padding: 0;
            max-height: 7cm;
            max-width: 6.4cm;
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
            .recibo {
                width: 21cm;
                height: 16.2cm;
                margin: 0 auto;
            }
            .parte-superior-recibo{
                display: flex;
                width: 21cm;
                height: 12cm;
                margin: 0;
                padding: 0;
            }
            .parte-inferior-recibo{
                width: 21cm;
                height: 4.2cm;
                margin: 0;
                padding: 0;
            }
            .datos-recibo{
                width: 13.7cm;
                height: 12cm;
                margin: 0;
                padding: 0;
            }
            .contenedor-monto{
                display: flex;
                margin-top: 3.5cm;
                margin-left: 0;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                height: 13px;
            }
            .monto-recibo{
                margin-top: 0;
                margin-left: 3.5cm;
                margin-right: 0;
                margin-bottom: 0;
                width: 2.5cm;
                height: 13px;
            }
            .numero-recibo{
                margin-top: 0;
                margin-left: 12cm;
                margin-right: 0;
                margin-bottom: 0;
                height: 13px;
            }
            .propietario{
                margin-top: 0.4cm;
                margin-left: 4.6cm;
                margin-right: 0;
                margin-bottom: 0;
                height: 13px;
            }
            .lote{
                margin-top: 0.1cm;
                display: flex;
                margin-left: 0;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                height: 13px;
            }
            .seccion{
                margin-left: 7cm;
                padding: 0px;
                margin-top: 0;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                width: 0.5cm;
                height: 13px;
            }
            .poligono{
                margin-left: 1.5cm;
                padding: 0px;
                margin-top: 0;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                width: 0.5cm;
                height: 13px;
            }
            .valor-mantenimiento{
                text-align: right;
                margin-top: 1.5cm;
                margin-left: 11.7cm;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                width: 1.5cm;
                height: 13px;
            }
            .concepto-mantenimiento{
                font-family: 'Arial Narrow', sans-serif;
                margin-top: 0.1cm;
                margin-left: 2.2cm;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                height: 13px;
            }
            .contenedor-recargo{
                margin-top: 0.2cm;
                display: flex;
                margin-left: 0;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                height: 13px;
            }
            .concepto-recargo{
                font-family: 'Arial Narrow', sans-serif;
                margin-left: 3.7cm;
                margin-top: 0;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                height: 13px;
                width: 7.2cm;
            }
            .valor-recargo{
                text-align: right;
                margin-left: 0.8cm;
                margin-top: 0;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                width: 1.5cm;
                height: 13px;
            }
            .valor-otros{
                text-align: right;
                margin-top: 0.2cm;
                margin-left: 11.7cm;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                width: 1.5cm;
                height: 13px;
            }
            .valor-total{
                text-align: right;
                margin-top: 1.3cm;
                margin-left: 11.7cm;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                width: 1.5cm;
                height: 13px;
            }
            .contenedor-fecha-pago{
                margin-top: 1.3cm;
                margin-left: 0;
                margin-right: 0;
                margin-bottom: 0;
                display: flex;
                padding: 0;
                height: 13px;
            }
            .dia-pago{
                margin-left: 5cm;
                margin-top: 0;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                height: 13px;
                width: 0.6cm;
            }
            .mes-pago{
                margin-left: 2.4cm;
                margin-top: 0;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                height: 13px;
                width: 2.5cm;
            }
            .anio-pago{
                margin-left: 2.3cm;
                margin-top: 0;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                height: 13px;
                width: 0.6cm;
            }
            .observaciones-recibo{
                width: 7.3cm;
                height: 12cm;
                margin: 0;
                padding: 0;
            }
            .fecha-corte{
                margin-left: 1.3cm;
                margin-top: 6.4cm;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                height: 13px;
                width: 5.3cm;
            }
            .texto-observaciones{
                text-align: justify;
                overflow: hidden;
                line-height: 1.4;
                margin-left: 0.2cm;
                margin-top: 0.2cm;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                max-height: 7cm;
                width: 6.4cm;
            }
            .usuario{
                text-align: center;
                margin-left: 2cm;
                margin-top: 2cm;
                margin-right: 0;
                margin-bottom: 0;
                padding: 0;
                height: 13px;
                width: 5.5cm;
            }
        }
    </style>
</head>
<body>
    <div class="recibo p-0">
        <div class="parte-superior-recibo">
            <div class="datos-recibo">
                <div class="contenedor-monto p-0">
                    <p class="monto-recibo"><strong class="vista-impresion">Por: </strong>${pago.monto}</p>
                    <p class="numero-recibo"><strong class="vista-impresion">Recibo: </strong>${pago.recibo}</p>
                </div>
                <p class="propietario"><strong class="vista-impresion">Recibimos de: </strong>${propietario}</p>
                <div class="lote p-0 m-0">
                    <p class="seccion"><strong class="vista-impresion">Lote: </strong>${pago.venta.terreno.numero}${pago.venta.terreno.seccion}</p><p class="poligono p-0">${pago.venta.terreno.poligono}</p>
                </div>
                <p class="valor-mantenimiento"><c:if test="${montoMantenimiento>0}"><strong class="vista-impresion">Monto mantenimiento: </strong><c:out value="${String.format('%.2f', montoMantenimiento)}"/></c:if></p>
                <p class="concepto-mantenimiento"><c:if test="${montoMantenimiento>0}"><strong class="vista-impresion">Concepto mantenimiento: </strong>${cuota}</c:if></p>
                <div class="contenedor-recargo p-0">
                    <p class="concepto-recargo"><c:if test="${montoRecargo>0}"><strong class="vista-impresion">Concepto recargo: </strong>${recargo} <c:if test="${pago.descuento>0}">(<c:out value="${String.format('%.2f', pago.descuento)}"/>)</c:if></c:if></p><p class="valor-recargo p-0"><c:if test="${montoRecargo>0}"><strong class="vista-impresion">Monto Recargo: </strong><c:out value="${String.format('%.2f', montoRecargo - pago.descuento)}"/></c:if></p>
                </div>
                <p class="valor-otros"><c:if test="${pago.otros>0}"><strong class="vista-impresion">Monto otros: </strong><c:out value="${String.format('%.2f', pago.otros)}"/></c:if></p>
                <p class="valor-total"><strong class="vista-impresion">Total: </strong><c:out value="${String.format('%.2f', pago.monto)}"/></p>
                <div class="contenedor-fecha-pago">
                    <p class="dia-pago"><strong class="vista-impresion">Fecha pago: </strong><fmt:formatDate value="${pago.fecha}" pattern="dd" /></p><p class="mes-pago p-0">&nbsp<fmt:formatDate value="${pago.fecha}" pattern="MMMM"/>&nbsp</p><p class="anio-pago p-0"><fmt:formatDate value="${pago.fecha}" pattern="yy" /></p>
                </div>
            </div>
            <div class="observaciones-recibo">
                <strong class="vista-impresion">Observaciones: </strong>
                <p class="fecha-corte"><c:if test="${(montoMantenimiento+montoRecargo)>0}">Fecha de corte <fmt:formatDate value="${pago.venta.fechaCorteMantenimiento}" pattern="dd" /> de cada mes</c:if></p>
                <p class="texto-observaciones"><c:if test="${not empty estado}">${estado}<br></c:if><c:if test="${montoPendiente>0 && (montoMantenimiento+montoRecargo)>0}">${pendiente}<br></c:if>${pago.observaciones}</p>
            </div>
        </div>
        <p class="usuario"><strong class="vista-impresion">Impreso por: </strong>${usuario.nombre}</p>
    </div>
</body>
</html>