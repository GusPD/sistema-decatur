<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationProyecto.jspf"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <div id="terrenoId" class="hidden" data-id="${terreno.idTerreno}"></div>
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="titulo-Perfil">
                        <div class="container container-titulo">
                            <h1>Proyecto ${proyecto.nombre} - Ventas del Terreno ${terreno.numero}${terreno.seccion}-${terreno.poligono}</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section class="content">
        <div class="container">
            <section class="content pb-5">
                <div>
                    <c:if test="${not empty mensaje}">
                        <div class="alert alert-success d-flex align-items-center alert-dismissible fade show" role="alert">
                            <strong><i class="bi bi-check-circle"></i> Éxito!</strong> ${mensaje}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger d-flex align-items-center alert-dismissible fade show" role="alert">
                            <strong><i class="bi bi-exclamation-triangle"></i> Error!</strong> ${error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <div class="alert alert-success d-flex align-items-center alert-dismissible fade d-none" role="alert">
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        <strong><i class="bi bi-check-circle"></i> Éxito!&nbsp;</strong>
                    </div>
                    <div class="alert alert-danger d-flex align-items-center alert-dismissible fade d-none" role="alert">
                        <strong><i class="bi bi-exclamation-triangle"></i> Error!&nbsp;</strong>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <div class="col-sm-12 d-flex justify-content-end">
                        <sec:authorize access="hasAuthority('EXPORTAR_VENTA_PRIVILAGE')"> 
                            <button id="export-copy" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><span>Copiar  </span><i class="fa-regular fa-copy"></i></button> 
                            <button id="export-excel" class="btn btn-sm btn-outline-success buttons-excel" type="button"><span>Exportar </span><i class="fa-solid fa-file-csv"></i></button> 
                            <button id="export-pdf" class="btn btn-sm btn-outline-danger buttons-pdf" type="button"><span>Exportar </span><i class="fa-regular fa-file-pdf"></i></button> 
                        </sec:authorize>
                        <sec:authorize access="hasAuthority('AGREGAR_VENTA_PRIVILAGE')"> 
                            <button type="button" class="btn-add btn abrirModal-btn btn-sm" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar">Agregar</button>
                        </sec:authorize>
                    </div>
                    <div>
                        <div class="table-responsive-md table-container">
                            <table id="ventaTable" class="table table-striped custom-fixed-header">
                                <thead class="table-light">
                                    <tr>
                                        <th class="text-center">Nombre</th>
                                        <th class="text-center">Estado</th>
                                        <th class="text-center">Fecha</th>
                                        <th class="text-center">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div><!-- /.container-fluid -->
            </section>

            <!-- Modal para ventas -->
            <div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="crearModalLabel">Agregar Venta</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form id='formGuardar' accept-charset="UTF-8">
                                <div  class="overflow-auto">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                    <input type="hidden" id="idVenta">
                                    <input type="hidden" id="idTerreno" value="${terreno.getIdTerreno()}">
                                    <input type="hidden" id="estado" value="Activo">
                                    <input type="hidden" id="idListDocumento">
                                    <div class="form-group">
                                        <label for="nombre" class="form-label">Nombre: </label>
                                        <input type="text" class="form-control" id="nombre" name="nombre" maxlength="200" placeholder="Ingrese el nombre de la venta" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="fecha" class="form-label">Fecha: </label>
                                        <input type="date" class="form-control" id="fecha" name="fecha" maxlength="10" placeholder="Ingrese la fecha de la venta">
                                    </div>
                                    <div class="form-group">
                                        <label for="" class="form-label">Precio: </label>
                                        <input type="text" class="form-control" id="precio" name="precio" maxlength="9" placeholder="Ingrese el precio de la venta">
                                    </div>
                                    <div class="form-group">
                                        <label for="descuento" class="form-label">Descuento: </label>
                                        <input type="text" class="form-control" id="descuento" name="descuento" maxlength="9" placeholder="Ingrese el descuento de la venta">
                                    </div>
                                    <div class="form-group">
                                        <label for="prima" class="form-label">Prima: </label>
                                        <input type="text" class="form-control" id="prima" name="prima" placeholder="0.00" data-prima="${valorPrima}" readonly>
                                    </div>
                                    <div class="form-group">
                                        <label for="monto" class="form-label">Monto: </label>
                                        <input type="text" class="form-control" id="monto" name="monto" placeholder="0.00" readonly>
                                    </div>
                                    <div class="form-group">
                                        <label for="plazo" class="form-label">Plazo: </label>
                                        <input type="text" class="form-control" id="plazo" name="plazo" maxlength="3" placeholder="Ingrese el plazo del financiamiento">
                                    </div>
                                    <div class="form-group">
                                        <label for="tasa" class="form-label">Tasa: </label>
                                        <input type="text" class="form-control" id="tasa" name="tasa" maxlength="6" placeholder="Ingrese la tasa del financiamiento">
                                    </div>
                                    <div class="form-group">
                                        <label for="cuotaKi" class="form-label">Cuota KI: </label>
                                        <input type="text" class="form-control" id="cuotaKi" name="cuotaKi" placeholder="0.00" readonly>
                                    </div>
                                    <div class="form-group">
                                        <label for="cuotaMantenimiento" class="form-label">Cuota Mantenimiento: </label>
                                        <input type="text" class="form-control" id="cuotaMantenimiento" name="cuotaMantenimiento" maxlength="9" placeholder="Ingrese la cuota de mantenimiento">
                                    </div>
                                    <div class="form-group">
                                        <label for="multaMantenimiento" class="form-label">Multa Mantenimiento: </label>
                                        <input type="text" class="form-control" id="multaMantenimiento" name="multaMantenimiento" maxlength="9" placeholder="Ingrese la multa de mantenimiento">
                                    </div>
                                    <div class="form-group">
                                        <label for="multaFinanciamiento" class="form-label">Multa Financiamiento: </label>
                                        <input type="text" class="form-control" id="multaFinanciamiento" name="multaFinanciamiento" maxlength="9" placeholder="Ingrese la multa de financiamientos">
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
            <div class="modal fade" id="confirmarEliminarModal" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminación</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <strong>¿Estás seguro de eliminar la venta seleccionada?</strong>
                            <p>Ten en cuenta que se eliminarán los datos relacionados a la venta <span id="venta"></span>-<span id="numero"></span><span id="seccion"></span>.</p>
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
    </section>
</div>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('VER_VENTA_PRIVILAGE')" var="hasPrivilegeVerVenta"></sec:authorize>
<script>var hasPrivilegeVerVenta = ${hasPrivilegeVerVenta};</script>    
        
<sec:authorize access="hasAuthority('EDITAR_VENTA_PRIVILAGE')" var="hasPrivilegeEditarVenta"></sec:authorize>
<script>var hasPrivilegeEditarVenta = ${hasPrivilegeEditarVenta};</script>

<sec:authorize access="hasAuthority('ELIMINAR_VENTA_PRIVILAGE')" var="hasPrivilegeEliminarVenta"></sec:authorize>
<script>var hasPrivilegeEliminarVenta = ${hasPrivilegeEliminarVenta};</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/ventas.js"></script>