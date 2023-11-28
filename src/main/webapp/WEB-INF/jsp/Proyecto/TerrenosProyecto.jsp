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
                            <h1>Terrenos</h1>
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
                                    <sec:authorize access="hasAuthority('EXPORTAR_TERRENO_PRIVILAGE')">
                                        <button id="export-copy" title="Copiar" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><i class="fa-regular fa-copy"></i></button> 
                                        <button id="export-excel" title="Exportar Excel" class="btn btn-sm btn-outline-success buttons-excel ml-2" type="button"><i class="fa-solid fa-file-excel"></i></button> 
                                        <button id="export-pdf" title="Exportar PDF" class="btn btn-sm btn-outline-danger buttons-pdf ml-2" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                    </sec:authorize>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <sec:authorize access="hasAuthority('AGREGAR_TERRENO_PRIVILAGE')"> 
                                        <button type="button" title="Agregar Terreno" class="btn-blue btn-sm btn abrirModal-btn ml-2" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar"><i class="fa-solid fa-file-pen"></i></button>
                                    </sec:authorize> 
                                </div>
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 60vh; padding:4px;">
                                    <table id="terrenoTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Modal de agregar -->
    <div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel">Agregar Terreno</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form id='formGuardar' accept-charset="UTF-8">
                    <div class="modal-body">
                        <div  class="overflow-auto">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="hidden" id="idTerreno">
                            <input type="hidden" id="proyecto" value="${proyecto.getIdProyecto()}">
                            <div class="form-group">
                                <label for="matricula" class="form-label">Matrícula:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control form-control-sm" id="matricula" name="matricula" maxlength="18" placeholder="Ingrese la matrícula del terreno" required>
                            </div>
                            <div class="form-group">
                                <label for="poligono" class="form-label">Polígono:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control form-control-sm" id="poligono" name="poligono" maxlength="1" placeholder="Ingrese el polígono del terreno" required>
                            </div>
                            <div class="form-group">
                                <label for="numero" class="form-label">Número de terreno:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control form-control-sm" id="numero" name="numero" maxlength="3" placeholder="Ingrese el número del terreno" required>
                            </div>
                            <div class="form-group">
                                <label for="seccion" class="form-label">Sección del terreno:</label>
                                <input type="text" class="form-control form-control-sm" id="seccion" name="seccion" maxlength="1" placeholder="Ingrese la sección del terreno">
                            </div>
                            <div class="form-group">
                                <label for="areaMetros" class="form-label">Área (m²):<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control form-control-sm" id="areaMetros" name="areaMetros" maxlength="18" placeholder="Ingrese el área en metros cuadrados del terreno" required>
                            </div>
                            <div class="form-group">
                                <label for="areaVaras" class="form-label">Área (v²):</label>
                                <input type="text" class="form-control form-control-sm" id="areaVaras" name="areaVaras" placeholder="Ingrese el área en varas cuadradas del terreno" readonly>
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
    <div class="modal fade" id="confirmarEliminarModal" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <strong>¿Estás seguro de eliminar el terreno seleccionado?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados al terreno.</p>
                </div>
                <div class="modal-footer">
                  <button id="eliminarTerrenoBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
                  <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    <form id="eliminarTerrenoForm" method="post" action="/EliminarTerreno/{idTerreno}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
</div>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('VER_TERRENO_PRIVILAGE')" var="hasPrivilegeVerTerreno"></sec:authorize>
<script>var hasPrivilegeVerTerreno = <c:out value=' ${hasPrivilegeVerTerreno}' />;</script>    
        
<sec:authorize access="hasAuthority('EDITAR_TERRENO_PRIVILAGE')" var="hasPrivilegeEditarTerreno"></sec:authorize>
<script>var hasPrivilegeEditarTerreno = <c:out value=' ${hasPrivilegeEditarTerreno}' />;</script>

<sec:authorize access="hasAuthority('ELIMINAR_TERRENO_PRIVILAGE')" var="hasPrivilegeEliminarTerreno"></sec:authorize>
<script>var hasPrivilegeEliminarTerreno = <c:out value=' ${hasPrivilegeEliminarTerreno}' />;</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Proyecto/Terreno.js"></script>