<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationProyecto.jspf"%>
<div class="content-wrapper">
    <div id="proyectoId" class="hidden" data-id="${proyecto.idProyecto}"></div>
    <!-- Título de la página -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1>Proyecto ${proyecto.nombre} - Propietarios</h1>
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
                            <h3 class="card-title d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('EXPORTAR_PROPIETARIO_PRIVILAGE')"> 
                                    <button id="export-copy" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><span>Copiar  </span><i class="fa-regular fa-copy"></i></button> 
                                    <button id="export-excel" class="btn btn-sm btn-outline-success buttons-excel ml-2" type="button"><span>Exportar </span><i class="fa-solid fa-file-csv"></i></button> 
                                    <button id="export-pdf" class="btn btn-sm btn-outline-danger buttons-pdf ml-2" type="button"><span>Exportar </span><i class="fa-regular fa-file-pdf"></i></button> 
                                </sec:authorize>
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 60vh; padding:4px;">
                                <!--Sección de tabla-->
                                    <table id="propietarioTable" class="table table-bordered table-striped dataTable dtr-inline mt-1 table-head-fixed text-nowrap"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('VER_PROPIETARIO_PRIVILAGE')" var="hasPrivilegeVerPropietarioProyecto"></sec:authorize>
<script>var hasPrivilegeVerPropietarioProyecto = ${hasPrivilegeVerPropietarioProyecto};</script>    

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/propietarioProyecto.js"></script>