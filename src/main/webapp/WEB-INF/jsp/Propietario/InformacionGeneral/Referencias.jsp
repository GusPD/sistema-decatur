<%@ include file="../propietario-header.jspf"%>
<input type="hidden" id="idPersona" value="${persona.getIdPersona()}">
<div class="row pb-3">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <!-- Subtitulo de la página y funciones de los datos -->
                <div class="subtitulo-page">
                    <h3 class="m-0">
                        <div class="d-flex">
                            <div class="col-sm-6">
                                Referencias
                            </div>
                            <div class="col-sm-6 d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('AGREGAR_REFERENCIA_PROPIETARIO_PRIVILAGE')">
                                    <button title="Agregar Referencia" id="AgregarReferencia" class="btn btn-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalReferencia" data-modo="agregar"><i class="fas fa-user-plus"></i></button>
                                </sec:authorize>
                            </div>
                        </div>
                    </h3>
                </div>
            </div>
            <!-- Datos -->
            <div class="card-body">
                <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                    <div class="col-sm-12 table-responsive pt-1" style="height: 55vh; padding:4px;">
                        <table id="referenciaTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal de agregar -->
<div class="modal fade" id="crearModalReferencia" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Agregar Referencia</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id='formGuardarReferencia' accept-charset="UTF-8">
                <div class="modal-body">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idPropietario" value="${propietario.getIdPropietario()}">
                        <input type="hidden" id="idReferencia">
                        <div class="form-group">
                            <label for="nombre" class="form-label">Nombre:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="nombre" name="nombre" maxlength="200" placeholder="Ingrese el nombre de la persona" required>
                        </div>
                        <div class="form-group">
                            <label for="apellido" class="form-label">Apellido:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="apellido" name="apellido" maxlength="200" placeholder="Ingrese el apellido de la persona" required>
                        </div>
                        <div class="form-group">
                            <label for="telefono" class="form-label">Teléfono:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="telefono" name="telefono" maxlength="12" placeholder="Ingrese el número de teléfono" required>
                        </div>
                        <div class="form-group">
                            <label for="correo" class="form-label">Correo Eléctronico:</label>
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
<div class="modal fade" id="confirmarEliminarModalReferencia" tabindex="-1" aria-labelledby="confirmarEliminarLabelReferencia" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmarEliminarLabelReferencia">Confirmar eliminación</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <strong>¿Estás seguro de eliminar la referencia seleccionada?</strong>
                <p>Ten en cuenta que se eliminarán los datos relacionados a la referencia.</p>
            </div>
            <div class="modal-footer">
              <button id="eliminarReferenciaBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
              <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>
<form id="eliminarReferenciaForm" method="post" action="/EliminarReferencia/{idReferencia}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form> 

<!-- Script de la página -->
<sec:authorize access="hasAuthority('ELIMINAR_REFERENCIA_PROPIETARIO_PRIVILAGE')" var="hasPrivilegeEliminarReferencia"></sec:authorize>
<script>var hasPrivilegeEliminarReferencia = <c:out value='${hasPrivilegeEliminarReferencia}' />;</script>

<%@ include file="../propietario-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Propietario/Referencia.js"></script>


      
