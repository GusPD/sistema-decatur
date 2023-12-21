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
                            <h1>Trabajadores</h1>
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
                            <h3 class="card-title d-flex justify-content-between">
                                <div class="d-flex justify-content-estart">
                                    <sec:authorize access="hasAuthority('EXPORTAR_TRABAJADOR_PRIVILAGE')">
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
                                <div class="col-sm-12 table-responsive pt-1" style="height: 60vh; padding:4px;">
                                    <table id="trabajadorTable" class="table table-bordered table-striped text-center dataTable dtr-inline mt-1"></table>
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
<sec:authorize access="hasAuthority('GESTIONAR_INFORMACION_TRABAJADOR_PRIVILAGE') || hasAuthority('GESTIONAR_DOCUMENTO_TRABAJADOR_PRIVILAGE') || hasAuthority('GESTIONAR_TERRENOS_TRABAJADOR_PRIVILAGE')" var="hasPrivilegeVerTrabajadorProyecto"></sec:authorize>
<script>var hasPrivilegeVerTrabajadorProyecto = <c:out value='${hasPrivilegeVerTrabajadorProyecto}' />;</script>    

<c:set var="url" value="" /> 
<sec:authorize access="hasAuthority('GESTIONAR_TERRENOS_TRABAJADOR_PRIVILAGE')">
    <c:set var="url" value="'/TerrenosTrabajador/'" />
</sec:authorize>
<sec:authorize access="hasAuthority('GESTIONAR_DOCUMENTO_TRABAJADOR_PRIVILAGE')">
    <c:set var="url" value="'/DocumentosTrabajador/'" />
</sec:authorize> 
<sec:authorize access="hasAuthority('GESTIONAR_INFORMACION_TRABAJADOR_PRIVILAGE')">
    <c:set var="url" value="'/InformacionTrabajador/'" />
</sec:authorize>
<script>var urlVerTrabajador = <c:out value='${url}' escapeXml="false"/>;</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Proyecto/Trabajador.js"></script>