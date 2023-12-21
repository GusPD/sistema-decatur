<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationAdministracion.jspf"%>
<div class="content-wrapper">
    <!-- Título de la página -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1>Bitácora</h1>
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
                        <!-- Funciones con los datos -->
                        <div class="card-header">
                            <h3 class="card-title d-flex justify-content-between">
                                <div class="d-flex justify-content-estart">
                                    <sec:authorize access="hasAuthority('EXPORTAR_BITACORA_PRIVILAGE')"> 
                                        <button id="export-copy" title="Copiar" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><i class="fa-regular fa-copy"></i></button> 
                                        <button id="export-excel" title="Exportar Excel" class="btn btn-sm btn-outline-success buttons-excel ml-2" type="button"><i class="fa-solid fa-file-excel"></i></button> 
                                        <button id="export-pdf" title="Exportar PDF" class="btn btn-sm btn-outline-danger buttons-pdf ml-2" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                    </sec:authorize>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <sec:authorize access="hasAuthority('EXPORTAR_BITACORA_PRIVILAGE')">
                                        <button id="b_clear" title="Eliminar Filtros" class="btn btn-sm btn-outline-dark" type="button"><i class="fa-solid fa-filter-circle-xmark"></i></button>
                                        <button id="b_buscar" title="Filtrar Registros" class="btn btn-sm btn-outline-dark" type="button"><i class="fa-solid fa-filter"></i></button>
                                    </sec:authorize>
                                </div>
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 d-flex display-alineacion border-bottom mb-3" style="padding:4px;">
                                    <div class="col-sm-2 form-group" style="padding-left: 0%!Important; padding-right: 1%!Important;">
                                        <label for="b_fecha_inicio" class="form-label">Fecha Inicio:</label>
                                        <input type="date" class="form-control form-control-sm" id="b_fecha_inicio" name="b_fecha_inicio" maxlength="10">
                                    </div>
                                    <div class="col-sm-2 form-group" style="padding-left: 0%!Important; padding-right: 1%!Important;">
                                        <label for="b_fecha_fin" class="form-label">Fecha Fin:</label>
                                        <input type="date" class="form-control form-control-sm" id="b_fecha_fin" name="b_fecha_fin" maxlength="10">
                                    </div>
                                    <div class="col-sm-2 form-group" style="padding-left: 0%!Important; padding-right: 1%!Important;">
                                        <label for="b_usuario" class="form-label">Usuario:</label>
                                        <select class="form-select form-select-sm" id="b_usuario" name="b_usuario" placeholder="Seleccione una opción">
                                            <option value="">Seleccione una opción</option>
                                            <c:if test="${not empty usuarios}">
                                                <c:forEach items="${usuarios}" var="eUsuario">
                                                    <option value="${eUsuario.username}">${eUsuario.nombre}</option>
                                                </c:forEach>
                                            </c:if>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-12 table-responsive pt-1" style="height: 43vh; padding:4px;">
                                    <table id="bitacoraTable" class="table table-bordered table-striped text-center dataTable dtr-inline mt-1"></table>
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
<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Bitacora/Bitacora.js"></script>

