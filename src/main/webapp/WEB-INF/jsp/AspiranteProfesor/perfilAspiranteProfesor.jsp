<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header1.jspf"%>
<%@ include file="../common/navigation1.jspf"%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12">
                    <div class="titulo-Perfil">
                        <div class="container">
                            <h1>Perfil del Aspirante a Profesor</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Main content -->
    <section class="content pb-5">
        <div class="container">
            <c:if test="${not empty mensaje}">
                <div class="alert alert-success d-flex align-items-center alert-dismissible fade show" role="alert">
                    <strong><i class="bi bi-check-circle"></i> Éxito!&nbsp;</strong>${mensaje}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger d-flex align-items-center alert-dismissible fade show" role="alert">
                    <strong><i class="bi bi-exclamation-triangle"></i> Error!&nbsp;</strong>${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <div class="row">
                <div class="col-sm-2 ">
                    <sec:authorize access="hasAuthority('AGREGAR_FOTOGRAFIA_PERFIL_ASPIRANTE_PRIVILAGE')">
                    <div class="row">
                        
                        <c:if test="${empty imagenBase64}">
                            <div class="col-sm-12" style="text-align: center; cursor: pointer;">
                                <i style="font-size: 8rem;" class="bi bi-person-add" id="tab_fotografia" title="Click para subir foto" onerror="" onclick="setActiveButton('tab_fotografia')"></i>
                            </div>
                        </c:if>
                        <c:if test="${not empty imagenBase64}">
                            <div class="col-sm-12" style="text-align: center; cursor: pointer;">
                                <img style="width:128px;cursor:pointer;" src="data:image;base64,${imagenBase64}" id="tab_fotografia" title="Click para subir foto" onclick="setActiveButton('tab_fotografia')">
                            </div>
                        </c:if>
                    </div>
                    </sec:authorize>
                    <br>
                    <br>
                    <div class="row">
                      <div class="col-sm-12 ">
                            <div class="list-group info ">
                                <button class="tab-perfil list-group-item list-group-item-info btn-info active" id="tab_generales" data-url="#" title="Datos generales" type="button" onclick="setActiveButton('tab_generales')">
                                    Información general
                                </button>
                                <button class="tab-perfil list-group-item list-group-item-info btn-info" id="tab_documentos_personales" data-url="#" title="Educación" type="button" onclick="setActiveButton('tab_documentos_personales')">
                                    Documentos
                                </button>
                                <button class="tab-perfil list-group-item list-group-item-info btn-info" id="tab_titulos_academicos" data-url="#" title="Documentos" type="button" onclick="setActiveButton('tab_titulos_academicos')">
                                    Títulos
                                </button>
                                <button class="tab-perfil list-group-item list-group-item-info btn-info" id="tab_telefonos" data-url="#" title="Teléfonos" type="button" onclick="setActiveButton('tab_telefonos')">
                                    Teléfonos
                                </button>
                                <button class="tab-perfil list-group-item list-group-item-info btn-info" id="tab_correos" data-url="#" title="Correos" type="button" onclick="setActiveButton('tab_correos')">
                                    Correos
                                </button>
                                <button class="tab-perfil list-group-item list-group-item-info btn-info" id="tab_redes_sociales" data-url="#" title="Correos" type="button" onclick="setActiveButton('tab_redes_sociales')">
                                    Redes Sociales
                                </button>
                                <button class="tab-perfil list-group-item list-group-item-info btn-info" id="tab_experiencia_laboral" data-url="#" title="Correos" type="button" onclick="setActiveButton('tab_experiencia_laboral')">
                                    Experiencia Laboral
                                </button>
                            </div>
                      </div>
                    </div>
                </div>
                <div class="col-sm-10">
                    <div class="col-sm-12">
                        <div id="contenido-perfil" class="contenido">
                            <div id="content_tab_fotografia" class="row d-none content_tab tab_fotografia">
                                <%@ include file="informacionGeneral/aspiranteProfesorFotografia.jspf"%>
                            </div>
                            <div id="content_tab_generales" class="row content_tab tab_generales">
                                <%@ include file="informacionGeneral/aspiranteProfesorGenerales.jspf"%>
                            </div>
                            <div id="content_tab_documentos_personales" class="row d-none content_tab tab_documentos_personales">
                                <%@ include file="informacionGeneral/aspiranteProfesorDocumentos.jspf"%>
                            </div>
                            <div id="content_tab_titulos_academicos" class="row d-none content_tab tab_titulos_academicos">
                                <%@ include file="informacionGeneral/aspiranteProfesorTitulos.jspf"%>
                            </div>
                            <div id="content_tab_telefonos" class="row d-none content_tab tab_telefonos">
                                <%@ include file="informacionGeneral/aspiranteProfesorTelefonos.jspf"%>
                            </div>
                            <div id="content_tab_correos" class="row d-none content_tab tab_correos">
                                <%@ include file="informacionGeneral/aspiranteProfesorCorreos.jspf"%>
                            </div>
                            <div id="content_tab_redes_sociales" class="row d-none content_tab tab_redes_sociales">
                                <%@ include file="informacionGeneral/aspiranteProfesorRedes.jspf"%>
                            </div>
                            <div id="content_tab_experiencia_laboral" class="row d-none content_tab tab_experiencia_laboral">
                                <%@ include file="informacionGeneral/aspiranteProfesorExperiencia.jspf"%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- /.container-->
    </section>
</div>
<%@ include file="../common/footer1.jspf"%>
<script src="https://cdn.jsdelivr.net/npm/jquery/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/inputmask/dist/jquery.inputmask.bundle.min.js"></script>
<script>
    // Función que se ejecuta cuando se hace clic en un botón
    function setActiveButton(buttonId) {
        // Obtener todos los botones
        var buttons = document.getElementsByClassName("tab-perfil");
        var content_tab = document.getElementsByClassName('content_tab');

        // Recorrer todos los botones y quitar la clase "active"
        for (var i = 0; i < buttons.length; i++) {
          buttons[i].classList.remove("active");
        }

        // Agregar la clase "active" al botón actual
        var currentButton = document.getElementById(buttonId);
        currentButton.classList.add("active");

        for (var i = 0; i < content_tab.length; i++) {
            content_tab[i].classList.add('d-none');
        }

        var currentContentTab = document.getElementById("content_"+buttonId);
        currentContentTab.classList.remove('d-none');
    }
</script>