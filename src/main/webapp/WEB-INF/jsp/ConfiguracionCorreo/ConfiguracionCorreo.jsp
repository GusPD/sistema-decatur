<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationAdministracion.jspf"%>
<div class="content-wrapper">
    <!-- Título de la página -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1>Configuración Correo</h1>
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
                        <!-- Funciones de los datos -->
                        <div class="card-header">
                            <h3 class="card-title d-flex justify-content-between">
                                <div class="d-flex justify-content-estart">
                                    <sec:authorize access="hasAuthority('EXPORTAR_CONFIGURACION_CORREO_PRIVILAGE')">
                                        <button id="export-copy" title="Copiar" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><i class="fa-regular fa-copy"></i></button> 
                                        <button id="export-excel" title="Exportar Excel" class="btn btn-sm btn-outline-success buttons-excel ml-2" type="button"><i class="fa-solid fa-file-excel"></i></button> 
                                        <button id="export-pdf" title="Exportar PDF" class="btn btn-sm btn-outline-danger buttons-pdf ml-2" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                    </sec:authorize>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <sec:authorize access="hasAuthority('AGREGAR_CONFIGURACION_CORREO_PRIVILAGE')"> 
                                        <button type="button" title="Agregar Correo" class="btn-blue btn-sm btn abrirModal-btn ml-2" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar"><i class="fa-solid fa-file-pen"></i></button>
                                    </sec:authorize> 
                                </div>
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 60vh; padding:4px;">
                                    <table id="correoTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Modal de agregar -->
    <div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel">Agregar Configuración de Correo</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form id='formGuardar' accept-charset="UTF-8">
                    <div class="modal-body">
                        <div  class="overflow-auto">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="hidden" id="idConfiguracion" name="idConfiguracion">
                            <input type="hidden" id="verificado" name="verificado" value="false">
                            <div class="form-group">
                                <label for="name" class="form-label">Name:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control form-control-sm" id="name" name="name" maxlength="100" placeholder="Ingrese el nombre del remitente" required>
                            </div>
                            <div class="form-group">
                                <label for="host" class="form-label">Host:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control form-control-sm" id="host" name="host" maxlength="50" placeholder="Ingrese el host del servidor" required>
                            </div>
                            <div class="form-group">
                                <label for="port" class="form-label">Port:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control form-control-sm" id="port" name="port" maxlength="20" placeholder="Ingrese el puerto del servidor" required>
                            </div>
                            <div class="form-group">
                                <label for="protocol" class="form-label">Protocol:</label>
                                <input type="text" class="form-control form-control-sm" id="protocol" name="protocol" maxlength="4" placeholder="Ingrese el protocolo del servidor">
                            </div>
                            <div class="form-group">
                                <label for="username" class="form-label">Username:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control form-control-sm" id="username" name="username" maxlength="100" placeholder="Ingrese el usuario" required>
                            </div>
                            <div class="form-group">
                                <label for="password" class="form-label">Password:</label>
                                <input type="text" class="form-control form-control-sm" id="password" name="password" maxlength="100" placeholder="Ingrese la contraseña" required>
                            </div>
                            <div class="form-group">
                                <label for="smtp_auth" class="form-label">Smtp Auth:</label>
                                <select class="form-select form-select-sm" id="smtp_auth" name="smtp_auth" placeholder="Seleccione una opción" required>
                                    <option value="">Seleccione una opción</option>
                                    <option value="true">Activado</option>
                                    <option value="false">Desactivado</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="start_tls" class="form-label">Tls:</label>
                                <select class="form-select form-select-sm" id="start_tls" name="start_tls" placeholder="Seleccione una opción" required>
                                    <option value="">Seleccione una opción</option>
                                    <option value="true">Activado</option>
                                    <option value="false">Desactivado</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="servidor" class="form-label">Servidor:</label>
                                <select class="form-select form-select-sm" id="servidor" name="servidor" placeholder="Seleccione una opción" required>
                                    <option value="">Seleccione una opción</option>
                                    <option value="true">Activado</option>
                                    <option value="false">Desactivado</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer d-flex justify-content-between">
                        <label for="monto" class="form-label text-danger mensaje-obligatorios">(*) Campos Obligatorios</label>
                        <div>
                            <button type="submit" class="btn btn-outline-success btn-sm">Guardar</button>
                            <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- Modal de eliminar -->
    <div class="modal fade" id="confirmarEliminarModal" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <strong>¿Estás seguro de eliminar la configuración de correo seleccionada?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados a la configuración de correo.</p>
                </div>
                <div class="modal-footer">
                  <button id="eliminarBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
                  <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    <form id="eliminarForm" method="post" action="/EliminarConfiguracionCorreo/{idConfiguracion}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
</div>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('EDITAR_CONFIGURACION_CORREO_PRIVILAGE')" var="hasPrivilegeEditarConfiguracion"></sec:authorize>
<script>var hasPrivilegeEditarConfiguracion = <c:out value=' ${hasPrivilegeEditarConfiguracion}' />;</script>

<sec:authorize access="hasAuthority('ELIMINAR_CONFIGURACION_CORREO_PRIVILAGE')" var="hasPrivilegeEliminarConfiguracion"></sec:authorize>
<script>var hasPrivilegeEliminarConfiguracion = <c:out value=' ${hasPrivilegeEliminarConfiguracion}' />;</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/ConfiguracionCorreo/ConfiguracionCorreo.js"></script>