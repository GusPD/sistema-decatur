<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationProyecto.jspf"%>
<div class="content-wrapper">
    <input type="hidden" id="idProyecto" value="${proyecto.idProyecto}">
    <!-- Título de la página -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1>Monitoreo de Pagos</h1>
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
                        <!-- Menú del monitoreo de pagos -->
                        <ul class="nav nav-tabs">
                            <li class="nav-item">
                                <a class="nav-link text-dark active" aria-current="page" href="/InformeMantenimiento/${proyecto.idProyecto}">Matenimiento</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link text-dark" aria-current="page" href="#">Financiamiento</a>
                            </li>
                        </ul>
                        <!-- Funciones de la página -->
                        <div class="card-header">
                            <h3 class="card-title d-flex justify-content-between">
                                <div class="d-flex justify-content-estart">
                                    <sec:authorize access="hasAuthority('EXPORTAR_MONITOREO_MANTENIMIENTO_PRIVILAGE')">
                                        <button id="export-copy" title="Copiar" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><i class="fa-regular fa-copy"></i></button> 
                                        <button id="export-excel" title="Exportar Excel" class="btn btn-sm btn-outline-success buttons-excel ml-2" type="button"><i class="fa-solid fa-file-excel"></i></button> 
                                        <button id="export-pdf" title="Exportar PDF" class="btn btn-sm btn-outline-danger buttons-pdf ml-2" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                    </sec:authorize>
                                </div>
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 54vh; padding:4px;">
                                    <table id="mantenimientoTable" class="table table-bordered table-striped text-center dataTable dtr-inline mt-1"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>                      
</div>

<%@ include file="../common/footer.jspf"%>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('VER_VENTA_PRIVILAGE')" var="hasPrivilegeVerVenta"></sec:authorize>
<script>var hasPrivilegeVerVenta = <c:out value='${hasPrivilegeVerVenta}'/>;</script>

<script src="${pageContext.request.contextPath}/js/Informes/InformeMantenimiento.js"></script>


      
