<%@ include file="../propietario-header.jspf"%>
<input type="hidden" id="idPersona" value="${persona.getIdPersona()}">
<div class="row">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <!-- Subtitulo de la página -->
                <div class="subtitulo-page">
                    <h3 class="m-0">
                        <div class="d-flex">
                            <div class="col-sm-6">
                                Teléfonos
                            </div>
                            <div class="col-sm-6 d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('AGREGAR_TELEFONO_PROPIETARIO_PRIVILAGE')">
                                    <button title="Agregar Teléfono" id="AgregarTelefono" class="btn btn-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalTelefono" data-modo="agregar"><i class="fa-solid fa-phone"></i></button>
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
                        <table id="telefonoTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal de agregar -->
<div class="modal fade" id="crearModalTelefono" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Agregar Teléfono</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formGuardarTelefono' accept-charset="UTF-8">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idPropietario" value="${propietario.getIdPropietario()}">
                        <input type="hidden" id="idTelefono">
                        <div class="form-group">
                            <label for="tipo" class="form-label">Tipo de Teléfono:<strong class="text-danger"> *</strong></label>
                            <select type="text" class="form-select" id="tipo" name="tipo" required>
                                <option value="">Seleccione una opción</option>
                                <c:forEach items="${tiposTelefonos}" var="eTipoTelefono" varStatus="status">
                                    <option value="${eTipoTelefono}">${eTipoTelefono}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="telefono" class="form-label">Teléfono:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="telefono" name="telefono" maxlength="12" placeholder="Ingrese el número de teléfono" required>
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
</div>
<!-- Modal de eliminar -->
<div class="modal fade" id="confirmarEliminarModalTelefono" tabindex="-1" aria-labelledby="confirmarEliminarLabelTelefono" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmarEliminarLabelTelefono">Confirmar eliminación</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <strong>¿Estás seguro de eliminar el teléfono seleccionado?</strong>
                <p>Ten en cuenta que se eliminarán los datos relacionados al teléfono.</p>
            </div>
            <div class="modal-footer">
              <button id="eliminarTelefonoBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
              <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>
<form id="eliminarTelefonoForm" method="post" action="/EliminarTelefono/{idTelefono}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form> 

<!-- Script de la página -->
<sec:authorize access="hasAuthority('ELIMINAR_TELEFONO_PROPIETARIO_PRIVILAGE')" var="hasPrivilegeEliminarTelefono"></sec:authorize>
<script>var hasPrivilegeEliminarTelefono = <c:out value='${hasPrivilegeEliminarTelefono}' />;</script>

<%@ include file="../propietario-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/telefonoPropietario.js"></script>


      
