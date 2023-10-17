<%@ include file="../venta-header.jspf"%>
<div class="row">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <!-- Subtitulo de la página y funciones de los datos -->
                <div class="subtitulo-page">
                    <h3 class="m-0">Documentos
                        <sec:authorize access="hasAuthority('AGREGAR_DOCUMENTO_VENTA_PRIVILAGE')"> 
                            <span id="AgregarDocumento" class="btn abrirModal-btn text-info puntero pull-right btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalDocumento" data-action="agregar" style="cursor: pointer;">
                                <i class="fa-solid fa-file-pen"></i>
                            </span>
                        </sec:authorize>
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
            <div class="modal-body">
                <form id='formGuardarDocumento' accept-charset="UTF-8" enctype="multipart/form-data">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idVenta" value="${venta.getIdVenta()}">
                        <input type="hidden" id="idList" value="${venta.getIdListDocumento()}">
                        <input type="hidden" id="idDocumento">
                        <div class="form-group">
                            <label for="nombre" class="form-label">Nombre:</label>
                            <input type="text" class="form-control" id="nombre" name="nombre" maxlength="200" placeholder="Ingrese el nombre del documento" required>
                        </div>
                        <div class="form-group">
                            <label for="documento" class="form-label">Documento:</label>
                            <input type="file" class="form-control" id="documento" name="documento" aria-hidden="true" accept=".pdf" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-outline-success btn-sm">Guardar</button>
                        <button type="button" class="btn btn-outline-danger btn-sm" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </form>
            </div>
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
<sec:authorize access="hasAuthority('VER_DOCUMENTO_VENTA_PRIVILAGE')" var="hasPrivilegeVerDocumento"></sec:authorize>
<script>var hasPrivilegeVerDocumento = ${hasPrivilegeVerDocumento};</script>    

<sec:authorize access="hasAuthority('ELIMINAR_DOCUMENTO_VENTA_PRIVILAGE')" var="hasPrivilegeEliminarDocumento"></sec:authorize>
<script>var hasPrivilegeEliminarDocumento = ${hasPrivilegeEliminarDocumento};</script>

<%@ include file="../venta-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/documentoVenta.js"></script>


      
