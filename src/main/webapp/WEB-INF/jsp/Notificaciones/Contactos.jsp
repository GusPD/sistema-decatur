<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationProyecto.jspf"%>

<div class="content-wrapper">
    <input type="hidden" id="idProyecto" name="idProyecto" value="${proyecto.idProyecto}">
    <!-- Título de la página -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1>Contactos</h1>
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
                            <h3 class="card-title d-flex justify-content-between">
                                <div class="d-flex justify-content-estart">
                                    <sec:authorize access="hasAuthority('EXPORTAR_CONTACTO_PRIVILAGE')">
                                        <button id="export-copy" title="Copiar" class="btn btn-outline-secondary buttons-copy btn-sm" type="button"><i class="fa-regular fa-copy"></i></button> 
                                        <button id="export-excel" title="Exportar Excel" class="btn btn-outline-success buttons-excel ml-2 btn-sm" type="button"><i class="fa-solid fa-file-excel"></i></button> 
                                        <button id="export-pdf" title="Exportar PDF" class="btn btn-outline-danger buttons-pdf ml-2 btn-sm" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                    </sec:authorize>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <sec:authorize access="hasAuthority('AGREGAR_CONTACTO_PRIVILAGE')"> 
                                        <button type="button" title="Agregar Pago" class="btn-blue btn abrirModal-btn ml-2 btn-sm" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar"><i class="fa-solid fa-file-pen"></i></button>
                                    </sec:authorize>
                                </div>
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 60vh; padding:4px;">
                                    <table id="contactosTable" class="table table-bordered table-striped text-center dataTable dtr-inline mt-1"></table>
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
                    <h5 class="modal-title" id="crearModalLabel">Agregar Correo</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form id='formGuardar' accept-charset="UTF-8">
                    <div class="modal-body">
                        <div  class="overflow-auto">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="hidden" id="idCorreo">
                            <div class="form-group">
                                <label for="idPropietario" class="form-label">Propietario:<strong class="text-danger"> *</strong></label>
                                <select type="text" class="form-select form-select-sm" id="idPropietario" name="idPropietario" required>
                                    <option value="">Seleccione una opción</option>
                                    <c:forEach items="${propietarios}" var="ePropietario" varStatus="status">
                                        <option value="${ePropietario.idPropietario}">${ePropietario.persona.numero} ${ePropietario.persona.nombre} ${ePropietario.persona.apellido}</option>
                                    </c:forEach>
                                </select>
                                <div id="span-propietarios-error" class="mensaje-error d-none"><span>Este campo es requerido</span></div>
                            </div>
                            <div class="form-group">
                                <label for="tipo" class="form-label">Tipo de Correo:<strong class="text-danger"> *</strong></label>
                                <select type="text" class="form-select form-select-sm" id="tipo" name="tipo" required>
                                    <option value="">Seleccione una opción</option>
                                    <c:forEach items="${tiposCorreo}" var="eTipoCorreo" varStatus="status">
                                        <option value="${eTipoCorreo}">${eTipoCorreo}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="correo" class="form-label">Correo:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control form-control-sm" id="correo" name="correo" maxlength="150" placeholder="Ingrese el correo electrónico" required>
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
                    <strong>¿Estás seguro de eliminar el correo seleccionado?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados al pago, y los pagos realizados a continuación a este.</p>
                </div>
                <div class="modal-footer">
                  <button id="eliminarCorreoBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
                  <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    <form id="eliminarCorreoForm" method="post" action="/EliminarCorreo/{idCorreo}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>                    
</div>

<!-- Script de la página -->

<sec:authorize access="hasAuthority('ELIMINAR_CONTACTO_PRIVILAGE')" var="hasPrivilegeEliminarCorreo"></sec:authorize>
<script>var hasPrivilegeEliminarCorreo = <c:out value='${hasPrivilegeEliminarCorreo}'/>;</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Notificaciones/Contactos.js"></script>

