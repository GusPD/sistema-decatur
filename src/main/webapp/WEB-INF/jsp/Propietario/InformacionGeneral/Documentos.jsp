<%@ include file="../propietario-header.jspf"%>
<input type="hidden" id="idDocumento" value="${propietario.getIdDocumento()}">
<div class="row pb-3">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <!-- Subitulo de la página y funciones de los datos -->
                <div class="subtitulo-page">
                    <h3 class="m-0">
                        <div class="d-flex">
                            <div class="col-sm-6">
                                Documentos
                            </div>
                            <div class="col-sm-6 d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('AGREGAR_DOCUMENTO_PROPIETARIO_PRIVILAGE')"> 
                                    <button title="Agregar Documento" id="AgregarDocumento" class="btn btn-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalDocumento" data-modo="agregar"><i class="fa-solid fa-file-pen"></i></button>
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
                        <table id="documentoTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal de agregar -->
<div class="modal fade" id="crearModalDocumento" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Agregar Documento</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id='formGuardarDocumento' accept-charset="UTF-8" enctype="multipart/form-data">
                <div class="modal-body">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idPropietario" value="${propietario.getIdPropietario()}">
                        <input type="hidden" id="idDocumento">
                        <div class="form-group">
                            <label for="nombre" class="form-label">Nombre:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="nombre" name="nombre" maxlength="200" placeholder="Ingrese el nombre del documento" required>
                        </div>
                        <div class="form-group">
                            <label for="documento" class="form-label">Documento:<strong class="text-danger"> *</strong></label>
                            <input type="file" class="form-control form-control-sm" id="documento" name="documento" aria-hidden="true" accept=".pdf" required>
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
<div class="modal fade" id="confirmarEliminarModalDocumento" tabindex="-1" aria-labelledby="confirmarEliminarLabelDocumento" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Confirmar eliminación</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <strong>¿Estás seguro de eliminar el documento seleccionado?</strong>
                <p>Ten en cuenta que se eliminarán los datos relacionados al documento.</p>
            </div>
            <div class="modal-footer">
              <button id="eliminarDocumentoBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
              <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>
<form id="eliminarDocumentoForm" method="post" action="/EliminarDocumento/{idDocumento}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('VER_DOCUMENTO_PROPIETARIO_PRIVILAGE')" var="hasPrivilegeVerDocumento"></sec:authorize>
<script>var hasPrivilegeVerDocumento = <c:out value='${hasPrivilegeVerDocumento}' />;</script>    

<sec:authorize access="hasAuthority('ELIMINAR_DOCUMENTO_PROPIETARIO_PRIVILAGE')" var="hasPrivilegeEliminarDocumento"></sec:authorize>
<script>var hasPrivilegeEliminarDocumento = <c:out value='${hasPrivilegeEliminarDocumento}' />;</script>

<%@ include file="../propietario-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Propietario/Documento.js"></script>


      
