<%@ include file="../venta-header.jspf"%>
<div class="row">
    <div class="col-12 pb-0">
        <div class="card">
            <input type="hidden" id="idVenta" value="${venta.getIdVenta()}">
            <!-- Menú de los estados de cuenta -->
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a class="nav-link text-dark" aria-current="page" href="/PagosVenta/${venta.idVenta}">Pagos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-dark" aria-current="page" href="/PrimaVenta/${venta.idVenta}">Prima</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-dark active" aria-current="page" href="/MantenimientoVenta/${venta.idVenta}">Matenimiento</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-dark" aria-current="page" href="/FinanciamientoVenta/${venta.idVenta}">Financiamiento</a>
                </li>
            </ul>
            <div class="card-header">
                <!-- Subtitulo de la página y funciones de los datos -->
                <div class="subtitulo-page">
                    <h3 class="mt-0 mb-0">
                        <div class="d-flex">
                            <div class="col-sm-6">
                                Mantenimiento
                            </div>
                            <div class="col-sm-6 d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('EXPORTAR_PAGO_PRIVILAGE')">
                                    <button id="export-copy" title="Copiar" class="btn btn-outline-secondary buttons-copy" type="button"><i class="fa-regular fa-copy"></i></button> 
                                    <button id="export-excel" title="Exportar Excel" class="btn btn-outline-success buttons-excel ml-2" type="button"><i class="fa-solid fa-file-csv"></i></button> 
                                    <button id="export-pdf" title="Exportar PDF" class="btn btn-outline-danger buttons-pdf ml-2" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                </sec:authorize>
                                <sec:authorize access="hasAuthority('AGREGAR_PAGO_PRIVILAGE')">
                                    <button title="Cargar Mantenimiento" id="SubirEstado" class="btn btn-blue" data-bs-toggle="modal" data-bs-target="#crearModal" data-tipo="subir"><i class="fa-solid fa-file-arrow-up"></i></button>
                                    <button title="Eliminar Mantenimiento" type="button" class="btn btn-outline-danger eliminarModal-btn" data-bs-toggle="modal" data-bs-target="#confirmarEliminarModal" data-id="${venta.getIdVenta()}"><i class="far fa-trash-alt"></i></button>
                                </sec:authorize>
                            </div>
                        </div>
                    </h3>
                </div>
            </div>
            <!-- Datos -->
            <div class="card-body">
                <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                    <div class="col-sm-12 table-responsive pt-1" style="height: 48vh; padding:4px;">
                        <table id="mantenimientoTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal de agregar -->
<div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Cargar Estado de Cuenta</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formGuardar' accept-charset="UTF-8" enctype="multipart/form-data">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idVenta" value="${venta.getIdVenta()}">
                        <div class="form-group">
                            <label for="documento" class="form-label">Documento:<strong class="text-danger"> *</strong></label>
                            <input type="file" class="form-control" id="documento" name="documento" aria-hidden="true" accept=".csv" required>
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
<div class="modal fade" id="confirmarEliminarModal" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Confirmar eliminación</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <strong class="text-justify">¿Estás seguro de eliminar el estado de cuenta?</strong>
                <p>Ten en cuenta que se eliminarán los pagos y cuotas relacionadas al estado de cuenta de mantenimiento.</p>
            </div>
            <div class="modal-footer">
              <button id="eliminarBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
              <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>
<form id="eliminarForm" method="post" action="/EliminarInformeMantenimientoVenta/{venta.getIdVenta}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>
<!-- Modal de mensaje de errores -->
<div class="modal fade" id="mensajeErrores" tabindex="-1" aria-labelledby="mensajeErrores" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Errores en el archivo .csv</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div  class="overflow-auto">
                    <p id="listaMensajesError"></p>
                </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<!-- Script de la página -->  

<%@ include file="../venta-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Venta/Mantenimiento.js"></script>


      
