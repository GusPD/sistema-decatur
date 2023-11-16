<%@ include file="../venta-header.jspf"%>
<div class="row">
    <div class="col-12">
        <div class="card">
            <input type="hidden" id="idVenta" value="${venta.getIdVenta()}">
            <!-- Menú de los estados de cuenta -->
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a class="nav-link text-dark" aria-current="page" href="/PagosVenta/${venta.idVenta}">Pagos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-dark active" aria-current="page" href="/PrimaVenta/${venta.idVenta}">Prima</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-dark" aria-current="page" href="/MantenimientoVenta/${venta.idVenta}">Matenimiento</a>
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
                                Prima
                            </div>
                            <div class="col-sm-6 d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('EXPORTAR_PAGO_PRIVILAGE')">
                                    <button id="export-copy" title="Copiar" class="btn btn-outline-secondary buttons-copy" type="button"><i class="fa-regular fa-copy"></i></button> 
                                    <button id="export-excel" title="Exportar Excel" class="btn btn-outline-success buttons-excel ml-2" type="button"><i class="fa-solid fa-file-csv"></i></button> 
                                    <button id="export-pdf" title="Exportar PDF" class="btn btn-outline-danger buttons-pdf ml-2" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                </sec:authorize>
                            </div>
                        </div>
                    </h3>
                </div>
            </div>
            <!-- Datos -->
            <div class="card-body">
                <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4 mb-5">
                    <div class="col-sm-12 table-responsive pt-1" style="height: 48vh; padding:4px;">
                        <table id="primaTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Script de la página -->  
<sec:authorize access="hasAuthority('VER_PAGO_PRIVILAGE')" var="hasPrivilegeVerPago"></sec:authorize>
<script>var hasPrivilegeVerPago = <c:out value='${hasPrivilegeVerPago}'/>;</script>

<%@ include file="../venta-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/primaVenta.js"></script>


      
