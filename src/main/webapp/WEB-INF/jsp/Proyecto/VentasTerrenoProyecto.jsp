<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationProyecto.jspf"%>
<div class="content-wrapper">
    <div id="terrenoId" class="hidden" data-id="${terreno.idTerreno}"></div>
    <!-- Título de la página -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1>Ventas del Terreno ${terreno.numero}${terreno.seccion} Polígono ${terreno.poligono}</h1>
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
                                    <sec:authorize access="hasAuthority('EXPORTAR_VENTA_PRIVILAGE')">
                                        <button id="export-copy" title="Copiar" class="btn btn-outline-secondary buttons-copy" type="button"><i class="fa-regular fa-copy"></i></button> 
                                        <button id="export-excel" title="Exportar Excel" class="btn btn-outline-success buttons-excel ml-2" type="button"><i class="fa-solid fa-file-csv"></i></button> 
                                        <button id="export-pdf" title="Exportar PDF" class="btn btn-outline-danger buttons-pdf ml-2" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                    </sec:authorize>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <sec:authorize access="hasAuthority('AGREGAR_VENTA_PRIVILAGE')"> 
                                        <button type="button" title="Agregar Venta" class="btn-blue btn abrirModal-btn ml-2" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar"><i class="fa-solid fa-file-pen"></i></button>
                                    </sec:authorize> 
                                </div>
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 60vh; padding:4px;">
                                    <table id="ventaTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
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
                <div class="modal-body">
                    <form id='formGuardar' accept-charset="UTF-8">
                        <div  class="overflow-auto">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="hidden" id="idVenta">
                            <input type="hidden" id="idTerreno" value="${terreno.getIdTerreno()}">
                            <input type="hidden" id="idListDocumento">
                            <input type="hidden" id="estado" value="Activo">
                            <div class="form-group">
                                <label for="nombre" class="form-label">Nombre:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control" id="nombre" name="nombre" maxlength="200" placeholder="Ingrese el nombre de la venta" required>
                            </div>
                            <div class="form-group">
                                <label for="fecha" class="form-label">Fecha:<strong class="text-danger"> *</strong></label>
                                <input type="date" class="form-control" id="fecha" name="fecha" maxlength="10" placeholder="Ingrese la fecha de la venta" required>
                            </div>
                            <div class="form-group">
                                <label for="precio" class="form-label">Precio:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control" id="precio" name="precio" maxlength="10" placeholder="Ingrese el precio" required>
                            </div>
                            <div class="form-group">
                                <label for="descuento" class="form-label">Descuento:</label>
                                <input type="text" class="form-control" id="descuento" name="descuento" maxlength="10" value="0.00">
                            </div>
                            <div class="form-group">
                                <label for="monto" class="form-label">Monto:</label>
                                <input type="text" class="form-control" id="monto" name="monto" maxlength="10" placeholder="0.00" readonly>
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
                    <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <strong>¿Estás seguro de eliminar la venta seleccionada?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados a la venta.</p>
                </div>
                <div class="modal-footer">
                  <button id="eliminarVentaBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
                  <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    <form id="eliminarVentaForm" method="post" action="/EliminarVenta/{idVenta}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
</div>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('VER_VENTA_PRIVILAGE')" var="hasPrivilegeVerVenta"></sec:authorize>
<script>var hasPrivilegeVerVenta = <c:out value='${hasPrivilegeVerVenta}' />;</script>    
        
<sec:authorize access="hasAuthority('EDITAR_VENTA_PRIVILAGE')" var="hasPrivilegeEditarVenta"></sec:authorize>
<script>var hasPrivilegeEditarVenta = <c:out value='${hasPrivilegeEditarVenta}' />;</script>

<sec:authorize access="hasAuthority('ELIMINAR_VENTA_PRIVILAGE')" var="hasPrivilegeEliminarVenta"></sec:authorize>
<script>var hasPrivilegeEliminarVenta = <c:out value='${hasPrivilegeEliminarVenta}' />;</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Proyecto/VentasTerreno.js"></script>