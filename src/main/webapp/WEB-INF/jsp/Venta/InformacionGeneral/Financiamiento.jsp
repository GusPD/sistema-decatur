<%@ include file="../venta-header.jspf"%>
<div class="row">
    <div class="col-12 pb-3">
        <div class="card">
            <input type="hidden" id="idVenta" value="${venta.getIdVenta()}">
            <!-- Menú de los estados de cuenta -->
            <ul class="nav nav-tabs">
                <sec:authorize access="hasAuthority('GESTIONAR_PAGOS_VENTA_PRIVILAGE')">
                    <li class="nav-item">
                        <a class="nav-link text-dark" aria-current="page" href="/PagosVenta/${venta.idVenta}">Pagos</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('GESTIONAR_PRIMA_VENTA_PRIVILAGE')">
                    <li class="nav-item">
                        <a class="nav-link text-dark" aria-current="page" href="/PrimaVenta/${venta.idVenta}">Prima</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('GESTIONAR_MANTENIMIENTO_VENTA_PRIVILAGE')">
                    <li class="nav-item">
                        <a class="nav-link text-dark" aria-current="page" href="/MantenimientoVenta/${venta.idVenta}">Matenimiento</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('GESTIONAR_FINANCIAMIENTO_VENTA_PRIVILAGE')">
                    <li class="nav-item">
                        <a class="nav-link text-dark active" aria-current="page" href="/FinanciamientoVenta/${venta.idVenta}">Financiamiento</a>
                    </li>
                </sec:authorize>
            </ul>
            <div class="card-header">
                <!-- Subtitulo de la página y funciones de los datos -->
                <div class="subtitulo-page">
                    <h3 class="mt-0 mb-0">
                        <div class="d-flex">
                            <div class="col-sm-6">
                                Financiamiento
                            </div>
                            <div class="col-sm-6 d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('SELECCIONAR_PROPIETARIO_PRIVILAGE')">
                                    <button id="export-copy" title="Copiar" class="btn btn-outline-secondary buttons-copy" type="button"><i class="fa-regular fa-copy"></i></button> 
                                    <button id="export-excel" title="Exportar Excel" class="btn btn-outline-success buttons-excel ml-2" type="button"><i class="fa-solid fa-file-csv"></i></button> 
                                    <button id="export-pdf" title="Exportar PDF" class="btn btn-outline-danger buttons-pdf ml-2" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                </sec:authorize>
                                <sec:authorize access="hasAuthority('AGREGAR_PROPIETARIO_PRIVILAGE')">
                                    <button title="Cargar Financiamiento" id="SubirEstado" class="btn btn-blue" data-bs-toggle="modal" data-bs-target="#crearModal" data-tipo="subir"><i class="fa-solid fa-file-arrow-up"></i></button>
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
                        <table id="financiamientoTable" class="table table-bordered table-striped text-center dataTable dtr-inline mt-1"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Script de la página -->  

<%@ include file="../venta-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Venta/Financiamiento.js"></script>


      
