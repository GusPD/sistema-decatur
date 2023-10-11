<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationProyecto.jspf"%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="titulo-Perfil">
                        <div class="container container-titulo">
                            <h1 id="NombreVenta">Proyecto ${proyecto.nombre} - ${venta.nombre}</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="container">
            <section class="content pb-5">
                <div>
                    <c:if test="${not empty mensaje}">
                        <div class="alert alert-success d-flex align-items-center alert-dismissible fade show" role="alert">
                            <strong><i class="bi bi-check-circle"></i> Éxito!</strong> ${mensaje}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger d-flex align-items-center alert-dismissible fade show" role="alert">
                            <strong><i class="bi bi-exclamation-triangle"></i> Error!</strong> ${error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <div class="alert alert-success d-flex align-items-center alert-dismissible fade d-none" role="alert">
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        <strong><i class="bi bi-check-circle"></i> Éxito!&nbsp;</strong>
                    </div>
                    <div class="alert alert-danger d-flex align-items-center alert-dismissible fade d-none" role="alert">
                        <strong><i class="bi bi-exclamation-triangle"></i> Error!&nbsp;</strong>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="list-group info">
                                        <div class="border p-3 rounded">
                                            <div class="row align-items-center mt-1 mb-1">
                                                <h3 id="numeroTerrenoVenta" class="text-center mt-1 mb-1">${terreno.numero}${terreno.seccion}</h3>
                                            </div>
                                            <hr>
                                            <div class="row align-items-center mt-1 mb-1">
                                                <p id="poligonoTerrenoVenta" class="text-center mt-1 mb-1">Polígono ${terreno.poligono}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row mt-3">
                                <div class="col-sm-12">
                                    <div class="list-group info">
                                        <button class="tab-perfil list-group-item list-group-item-info btn-info active" id="tab_informacion" data-url="#" title="Datos generales" type="button" onclick="setActiveButton('tab_informacion')">
                                            Información
                                        </button>
                                        <button class="tab-perfil list-group-item list-group-item-info btn-info" id="tab_propietarios" data-url="#" title="Propietarios" type="button" onclick="setActiveButton('tab_propietarios')">
                                            Propietarios
                                        </button>
                                        <button class="tab-perfil list-group-item list-group-item-info btn-info" id="tab_trabajadores" data-url="#" title="Trabajadores" type="button" onclick="setActiveButton('tab_trabajadores')">
                                            Trabajadores
                                        </button>
                                        <button class="tab-perfil list-group-item list-group-item-info btn-info" id="tab_documentos" data-url="#" title="Documentos" type="button" onclick="setActiveButton('tab_documentos')">
                                            Documentos
                                        </button>
                                        <button class="tab-perfil list-group-item list-group-item-info btn-info" id="tab_facturacion" data-url="#" title="Facturación" type="button" onclick="setActiveButton('tab_facturacion')">
                                            Facturación
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-10">
                            <div class="row">
                                <div class="col-sm-12">
                                    <div id="contenido-perfil" class="contenido">
                                        <div id="content_tab_informacion" class="row content_tab tab_informacion">
                                            <%@ include file="informacionGeneral/ventaInformacion.jspf"%>
                                        </div>
                                        <div id="content_tab_documentos" class="row d-none content_tab tab_documentos">
                                            <%@ include file="informacionGeneral/ventaDocumentos.jspf"%>
                                        </div>
                                        <div id="content_tab_propietarios" class="row d-none content_tab tab_propietarios">
                                            <%@ include file="informacionGeneral/ventaPropietarios.jspf"%>
                                        </div>
                                        <div id="content_tab_trabajadores" class="row d-none content_tab tab_trabajadores">
                                            <%@ include file="informacionGeneral/ventaTrabajadores.jspf"%>
                                        </div>
                                        <div id="content_tab_facturacion" class="row d-none content_tab tab_facturacion">
                                            <%@ include file="informacionGeneral/ventaFacturacion.jspf"%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div><!-- /.container-->
    </section>
</div>
<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/mostrarVenta.js"></script>
<script src="${pageContext.request.contextPath}/js/propietarioVenta.js"></script>
<script src="${pageContext.request.contextPath}/js/trabajadorVenta.js"></script>
<script src="${pageContext.request.contextPath}/js/informacionVenta.js"></script>
<script src="${pageContext.request.contextPath}/js/documentoVenta.js"></script>

