<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationAdministracion.jspf"%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="titulo-Perfil">
                        <div class="container container-titulo">
                            <h1 id="NombreTrabajador">Perfil del Trabajador ${persona.nombre} ${persona.apellido}</h1>
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
                        <div class="col-sm-2 ">
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="list-group info">
                                        <div class="border p-3 rounded">
                                            <div class="row align-items-center mt-1 mb-1">
                                                <i id="imagenPerfil" class="fa-solid fa-user text-center"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row mt-3">
                              <div class="col-sm-12 ">
                                    <div class="list-group info ">
                                        <button class="tab-perfil list-group-item list-group-item-info btn-info active" id="tab_informacion" data-url="#" title="Datos generales" type="button" onclick="setActiveButton('tab_informacion')">
                                            Información
                                        </button>
                                        <button class="tab-perfil list-group-item list-group-item-info btn-info" id="tab_documentos" data-url="#" title="Documentos" type="button" onclick="setActiveButton('tab_documentos')">
                                            Documentos
                                        </button>
                                        <button class="tab-perfil list-group-item list-group-item-info btn-info" id="tab_terrenos" data-url="#" title="Terrenos" type="button" onclick="setActiveButton('tab_terrenos')">
                                            Terrenos
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
                                            <%@ include file="informacionGeneral/trabajadorInformacion.jspf"%>
                                        </div>
                                        <div id="content_tab_documentos" class="row d-none content_tab tab_documentos">
                                            <%@ include file="informacionGeneral/trabajadorDocumentos.jspf"%>
                                        </div>
                                        <div id="content_tab_terrenos" class="row d-none content_tab tab_terrenos">
                                            <%@ include file="informacionGeneral/trabajadorTerrenos.jspf"%>
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

<script src="${pageContext.request.contextPath}/js/mostrarTrabajador.js"></script>
<script src="${pageContext.request.contextPath}/js/informacionTrabajador.js"></script>
<script src="${pageContext.request.contextPath}/js/documentoTrabajador.js"></script>

