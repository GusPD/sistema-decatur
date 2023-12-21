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
                            <h1>Enviar Notificación</h1>
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
                        <!-- Datos -->
                        <form id="formEnviar" accept-charset="UTF-8" enctype="multipart/form-data">
                            <div class="card-body pb-0">
                                <div class="row col-sm-12 m-0 p-0 justify-content-center">
                                    <div class="col-sm-4 m-0 p-0">
                                        <div  class="overflow-auto-email">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                            <input type="hidden" id="idProyecto" name="idProyecto" value="${proyecto.getIdProyecto()}">
                                            <div class="form-group form-group-left">
                                                <label for="remitente">Remitente:<strong class="text-danger"> *</strong></label>
                                                <select type="text" class="form-select form-select-sm" id="remitente" name="remitente" required>
                                                    <option value="">Seleccione una opción</option>
                                                    <c:if test="${not empty remitentes}">
                                                        <c:forEach items="${remitentes}" var="eRemitente">
                                                            <option value="${eRemitente.idConfiguracion}">${eRemitente.name}</option>
                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                            </div>
                                            <div class="form-group form-group-left">
                                                <label for="tipoEnvio">Tipo de envío:<strong class="text-danger"> *</strong></label>
                                                <select type="text" class="form-select form-select-sm" id="tipoEnvio" name="tipoEnvio" required>
                                                    <option value="">Seleccione una opción</option>
                                                    <option value="Unico">Envío único</option>
                                                    <option value="Masivo">Envío masivo</option>
                                                </select>
                                            </div>
                                            <div class="form-group form-group-left">
                                                <label for="destino">Destinatarios:<strong class="text-danger"> *</strong></label>
                                                <select type="text" class="form-select form-select-sm" id="destino" name="destino" required>
                                                    <option value="">Seleccione una opción</option>
                                                    <option value="Individual">Individual</option>
                                                    <option value="Propietarios">Propietarios (Ventas Activas)</option>
                                                    <option value="SeleccionarPropietarios">Seleccionar Propietarios</option>
                                                    <option value="PropietariosIndividual">Propietarios (Ventas Activas) e Individual</option>
                                                    <option value="SeleccionarPropietariosIndividual">Seleccionar Propietarios e Individual</option>
                                                </select>
                                            </div>
                                            <div id="form-group-seleccionar" class="form-group form-group-left" style="display: none;">
                                                <label for="destinatario">Seleccione los Propietarios:</label>
                                                <select type="text" class="form-select form-select-sm" id="propietarioIndividual" name="propietarioIndividual" multiple>
                                                    <c:if test="${not empty listaCorreosProyecto}">
                                                        <c:forEach items="${listaCorreosProyecto}" var="eCorreo">
                                                            <option value="${eCorreo.correo}">${eCorreo.propietario.persona.nombre} ${eCorreo.propietario.persona.apellido} (${eCorreo.correo})</option>
                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                                <div id="span-propietarios-error" class="mensaje-error d-none"><span>Este campo es requerido</span></div>
                                            </div>
                                            <div id="form-group-destinatario" class="form-group form-group-left" style="display: none;">
                                                <label for="destinatario">Destinatario:</label>
                                                <input type="text" class="form-control form-control-sm" id="destinatario" name="destinatario">
                                            </div>
                                            <div class="form-group form-group-left">
                                                <label for="cco">CC:</label>
                                                <input type="text" class="form-control form-control-sm" id="cc" name="cc">
                                            </div>
                                            <div class="form-group form-group-left">
                                                <label for="cco">CCO:</label>
                                                <input type="text" class="form-control form-control-sm" id="cco" name="cco">
                                            </div>  
                                            <div class="form-group form-group-left">
                                                <label for="asunto">Asunto:<strong class="text-danger"> *</strong></label>
                                                <input type="text" class="form-control form-control-sm" id="asunto" name="asunto" maxlength="100" required>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-8 m-0 p-0">
                                        <div class="form-group form-group-right">
                                            <label for="mensaje">Mensaje:</label>
                                            <textarea class="form-control" id="mensaje" name="mensaje" rows="7" required></textarea>
                                        </div> 
                                    </div> 
                                </div>
                                <div class="row col-sm-12 m-0 p-0 justify-content-center">
                                    <div class="form-group form-group-right form-group-left mb-0">
                                        <label for="adjuntos" class="form-label">Adjuntar archivos:</label>
                                        <input type="file" class="filepond" id="adjuntos" name="adjuntos" aria-hidden="true" data-allow-reorder="true" data-max-file-size="20MB" multiple>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer d-flex justify-content-between mb-2">
                                <label for="monto" class="form-label text-danger mensaje-obligatorios">(*) Campos Obligatorios</label>
                                <sec:authorize access="hasAuthority('ENVIAR_CORREO_ELECTRONICO_PRIVILAGE')">
                                    <button type="submit" title="Enviar correo" class="btn btn-outline-success btn-sm">Enviar</button>
                                </sec:authorize>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>                   
</div>
<!-- Pantalla de carga -->
<div id="loadingOverlay">
    <div class="loading-spinner">
        <div class="spinner-border" role="status">
            <span class="sr-only">Cargando...</span>
        </div>
        <p>Enviando Correo...</p>
    </div>
</div>
<!-- Script de la página -->
<%@ include file="../common/footer.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/RecursosOnline/filepond/dist/filepond.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/RecursosOnline/filepond/locale/es-es.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/RecursosOnline/filepond/plugins/filepond-plugin-file-validate-size.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/RecursosOnline/tinymce/tinymce.min.js"></script>
<script src="${pageContext.request.contextPath}/js/Notificaciones/Notificacion.js"></script>

