<%@ include file="../venta-header.jspf"%>
<div id="page-informacion" class="row">
    <input type="hidden" id="fechaAplicacionFinanciamiento" value="<c:choose><c:when test="${empty financiamiento}">${venta.fecha}</c:when><c:otherwise>${financiamiento.fechaAplicacion}</c:otherwise></c:choose>">
    <input type="hidden" id="fechaAplicacionMantenimiento" value="<c:choose><c:when test="${empty mantenimiento}">${venta.fecha}</c:when><c:otherwise>${mantenimiento.fechaAplicacion}</c:otherwise></c:choose>">
    <!-- Subtitulo de la pï¿½gina y funciones de los datos -->
    <div class="subtitulo-page">
        <h3 class="mt-0">
            <div class="d-flex">
                <div class="col-sm-6">
                    Venta
                </div>
                <div class="col-sm-6 d-flex justify-content-end">
                    <c:if test="${empty financiamientos and empty mantenimientos}">
                        <sec:authorize access="hasAuthority('EDITAR_VENTA_PRIVILAGE')">
                            <button title="Editar Venta" id="EditarInformacion" class="btn btn-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModal" data-tipo="editar" data-id="${venta.idVenta}" data-modo="actualizar"><i class="far fa-edit"></i></button>
                        </sec:authorize>
                    </c:if>
                </div>
            </div>  
        </h3>
    </div>
    <!-- Datos -->
    <div class="tarjeta-container">
        <table class="table small table-bordered m-0" id="tabla-informacion">
            <tbody>
                <tr>
                    <td width="20%" class="encabezado-tabla font-weight-bold">Estado</td>
                    <td><c:if test="${not empty venta.estado}">${venta.estado}</c:if></td>
                </tr>
                <tr>
                    <td class="encabezado-tabla font-weight-bold">Nombre</td>
                    <td><c:if test="${not empty venta.nombre}">${venta.nombre}</c:if></td>
                </tr>
                <tr>
                    <td class="encabezado-tabla font-weight-bold">Fecha Venta</td>
                    <td><c:if test="${not empty venta.fecha}"><fmt:formatDate value="${venta.fecha}" pattern="dd/MM/yyyy" /></c:if></td>
                </tr>
                <tr>
                    <td class="encabezado-tabla font-weight-bold">Precio</td>
                    <td><c:if test="${not empty venta.precio}">$ <c:out value="${String.format('%.2f', venta.precio)}"/></c:if></td>
                </tr>
                <tr>
                    <td class="encabezado-tabla font-weight-bold">Descuento</td>
                    <td><c:if test="${not empty venta.descuento}">$ <c:out value="${String.format('%.2f', venta.descuento)}"/></c:if></td>
                </tr>
                <tr>
                    <td class="encabezado-tabla font-weight-bold">Monto</td>
                    <td><c:if test="${not empty venta.monto}">$ <c:out value="${String.format('%.2f', venta.monto)}"/></c:if></td>
                </tr>
                <tr>
                    <td class="encabezado-tabla font-weight-bold">Prima</td>
                    <td><c:if test="${not empty valorPrima}">$ <c:out value="${String.format('%.2f', valorPrima)}"/></c:if></td>
                </tr>
            </tbody>
        </table>
    </div>
    <!-- Subtitulo de la página y funciones de los datos -->
    <div class="subtitulo-page mt-4">
        <h3>
            <div class="d-flex">
                <div class="col-sm-6">
                    Financiamiento
                </div>
                <div class="col-sm-6 d-flex justify-content-end">
                    <sec:authorize access="hasAuthority('EDITAR_INFORMACION_FINANCIAMIENTO_PRIVILAGE')">
                        <button title="Editar Financiamiento" id="EditarInformacionFinanciamiento" class="btn btn-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalFinanciamiento" data-tipo="editar" data-id="${financiamiento.idAsignacion}" data-modo="actualizar"><i class="far fa-edit"></i></button>
                    </sec:authorize>
                </div>
            </div> 
        </h3>
    </div>
    <!-- Datos -->
    <div class="tarjeta-container d-flex">
        <div class="col-md-4 border p-3 rounded">
            <table class="table small table-borderless" id="tabla-informacion-financiamiento">
                <tbody>
                    <tr>
                        <td class="font-weight-bold" width="10%">Fecha Aplicación</td>
                        <td id="fecha-aplicacion-financiamiento"><c:if test="${not empty financiamiento.fechaAplicacion}"><fmt:formatDate value="${financiamiento.fechaAplicacion}" pattern="dd/MM/yyyy" /></c:if></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Monto</td>
                        <td id="monto-financiamiento"><c:if test="${not empty financiamiento.monto}">$ <c:out value="${String.format('%.2f',financiamiento.monto)}"/></c:if></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Plazo</td>
                        <td id="plazo-financiamiento"><c:if test="${not empty financiamiento.plazo}">${financiamiento.plazo}</c:if></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Tasa</td>
                        <td id="tasa-financiamiento"><c:if test="${not empty financiamiento.tasa}"><c:out value="${String.format('%.2f',financiamiento.tasa)}"/> %</c:if></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Cuota</td>
                        <td id="cuota-financiamiento"><c:if test="${not empty financiamiento.cuota}">$ <c:out value="${String.format('%.2f',financiamiento.cuota)}"/></c:if></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Multa</td>
                        <td id="multa-financiamiento"><c:if test="${not empty financiamiento.multa}">$ <c:out value="${String.format('%.2f',financiamiento.multa)}"/></c:if></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="col-md-8 border p-3 rounded table-responsive columna-derecha page-scroll" style="height: 50vh;">
            <h6>Financiamientos Anteriores</h6>
            <table class="table table-bordered custom-fixed-header small" id="tabla-financiamientos">
                <thead>
                    <tr class="encabezado-tabla">
                        <th scope="col">N°</th>
                        <th scope="col">Fecha Aplicación</th>
                        <th scope="col">Cuota</th>
                        <th scope="col">Multa</th>
                        <th scope="col">Opciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty financiamientos}">
                        <tr>
                            <td colspan="5">No hay registros</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty financiamientos}">
                        <c:forEach items="${financiamientos}" var="eAsignacion" varStatus="numeroAsignacion">
                            <tr>
                                <td>${numeroAsignacion.index+1}</td>
                                <td><fmt:formatDate value="${eAsignacion.fechaAplicacion}" pattern="dd/MM/yyyy" /></td>
                                <td>$ <c:out value="${String.format('%.2f', eAsignacion.cuota)}"/></td>
                                <td>$ <c:out value="${String.format('%.2f', eAsignacion.multa)}"/></td>
                                <td>
                                    <sec:authorize access="hasAuthority('ELIMINAR_INFORMACION_FINANCIAMIENTO_PRIVILAGE')">
                                    </sec:authorize>
                                    <button type="button" class="btn btn-outline-secondary mostrarFinanciamiento-btn btn-sm" data-id="${eAsignacion.idAsignacion}" 'data-cod="${eAsignacion.idAsignacion}"><i class="far fa-eye"></i></button>
                                    <c:if test="${numeroAsignacion.index < 1}">
                                        <button type="button" class="btn btn-outline-danger eliminarModalFinanciamiento-btn btn-sm" data-id="${eAsignacion.idAsignacion}" 'data-cod="${eAsignacion.idAsignacion}"><i class="far fa-trash-alt"></i></button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
    <!-- Subtitulo de la página y funciones de los datos -->
    <div class="subtitulo-page mt-4">
        <h3>
            <div class="d-flex">
                <div class="col-sm-6">
                    Mantenimiento
                </div>
                <div class="col-sm-6 d-flex justify-content-end">
                    <sec:authorize access="hasAuthority('EDITAR_INFORMACION_MANTENIMIENTO_PRIVILAGE')">
                        <button title="Editar Mantenimiento" id="EditarInformacionMantenimiento" class="btn btn-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalMantenimiento" data-tipo="editar" data-id="${mantenimiento.idAsignacion}" data-modo="actualizar"><i class="far fa-edit"></i></button>
                    </sec:authorize>
                </div>
            </div>
        </h3>
    </div>
    <!-- Datos -->
    <div class="tarjeta-container d-flex">
        <div class="col-md-4 border p-3 rounded">
            <table class="table small table-borderless" id="tabla-informacion-mantenimiento">
                <tbody>
                    <tr>
                        <td class="font-weight-bold" width="10%">Fecha Aplicación</td>
                        <td id="fecha-aplicacion-mantenimiento"><c:if test="${not empty mantenimiento.fechaAplicacion}"><fmt:formatDate value="${mantenimiento.fechaAplicacion}" pattern="dd/MM/yyyy" /></c:if></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Cuota</td>
                        <td id="cuota-mantenimiento"><c:if test="${not empty mantenimiento.cuota}">$ <c:out value="${String.format('%.2f',mantenimiento.cuota)}"/></c:if></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Multa</td>
                        <td id="multa-mantenimiento"><c:if test="${not empty mantenimiento.multa}">$ <c:out value="${String.format('%.2f',mantenimiento.multa)}"/></c:if></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="col-md-8 border p-3 rounded table-responsive columna-derecha page-scroll" style="height: 45vh;">
            <h6>Mantenimientos Anteriores</h6>
            <table class="table table-bordered custom-fixed-header small" id="tabla-mantenimientos">
                <thead>
                    <tr class="encabezado-tabla">
                        <th scope="col">N°</th>
                        <th scope="col">Fecha Aplicación</th>
                        <th scope="col">Cuota</th>
                        <th scope="col">Multa</th>
                        <th scope="col">Opciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty mantenimientos}">
                        <tr>
                            <td colspan="5">No hay registros</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty mantenimientos}">
                        <c:forEach items="${mantenimientos}" var="eAsignacion" varStatus="numeroAsignacion">
                            <tr>
                                <td>${numeroAsignacion.index+1}</td>
                                <td><fmt:formatDate value="${eAsignacion.fechaAplicacion}" pattern="dd/MM/yyyy" /></td>
                                <td>$ <c:out value="${String.format('%.2f', eAsignacion.cuota)}"/></td>
                                <td>$ <c:out value="${String.format('%.2f', eAsignacion.multa)}"/></td>
                                <td>
                                    <sec:authorize access="hasAuthority('ELIMINAR_INFORMACION_FINANCIAMIENTO_PRIVILAGE')">
                                    </sec:authorize>
                                    <button type="button" class="btn btn-outline-secondary mostrarMantenimiento-btn btn-sm" data-id="${eAsignacion.idAsignacion}" 'data-cod="${eAsignacion.idAsignacion}"><i class="far fa-eye"></i></button>
                                    <c:if test="${numeroAsignacion.index < 1}">
                                        <button type="button" class="btn btn-outline-danger eliminarModalMantenimiento-btn btn-sm" data-id="${eAsignacion.idAsignacion}" 'data-cod="${eAsignacion.idAsignacion}"><i class="far fa-trash-alt"></i></button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
<!-- Modal de editar -->
<div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Editar Venta</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formGuardar' accept-charset="UTF-8">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idVenta" value="${venta.getIdVenta()}">
                        <input type="hidden" id="idTerreno" value="${terreno.getIdTerreno()}">
                        <input type="hidden" id="estado" value="Activo">
                        <input type="hidden" id="idListDocumento" value="${venta.getIdListDocumento()}">
                        <div class="form-group">
                            <label for="nombre" class="form-label">Nombre:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="nombre" name="nombre" maxlength="200" placeholder="Ingrese el nombre de la venta" required>
                        </div>
                        <div class="form-group">
                            <label for="fecha" class="form-label">Fecha:<strong class="text-danger"> *</strong></label>
                            <input type="date" class="form-control" id="fecha" name="fecha" maxlength="10" placeholder="Ingrese la fecha de la venta">
                        </div>
                        <div class="form-group">
                            <label for="" class="form-label">Precio:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="precio" name="precio" maxlength="9" placeholder="Ingrese el precio de la venta">
                        </div>
                        <div class="form-group">
                            <label for="descuento" class="form-label">Descuento:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="descuento" name="descuento" maxlength="9" placeholder="Ingrese el descuento de la venta">
                        </div>
                        <div class="form-group">
                            <label for="monto" class="form-label">Monto:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="monto" name="monto" placeholder="0.00" readonly>
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
<!-- Modal de editar financiamiento -->
<div class="modal fade" id="crearModalFinanciamiento" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Editar Financiamiento</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formGuardarFinanciamiento' accept-charset="UTF-8">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idFinanciamiento" value="">
                        <div class="form-group">
                            <label for="montoF" class="form-label">Monto:</label>
                            <input type="text" class="form-control" id="montoF" name="montoF" placeholder="0.00" value="${venta.getMonto()}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="prima" class="form-label">Prima:</label>
                            <input type="text" class="form-control" id="prima" name="prima" placeholder="0.00" value="${valorPrima}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="financiamiento" class="form-label">Financiamiento:</label>
                            <input type="text" class="form-control" id="financiamiento" name="financiamiento" placeholder="0.00"  value="${venta.getMonto() - valorPrima}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="fecha" class="form-label">Fecha Aplicación:<strong class="text-danger"> *</strong></label>
                            <input type="date" class="form-control" id="fechaAplicacionF" name="fechaAplicacionF" maxlength="10" placeholder="Ingrese la fecha de aplicaciï¿½n">
                        </div>
                        <div class="form-group">
                            <label for="plazo" class="form-label">Plazo:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="plazo" name="plazo" maxlength="3" placeholder="Ingrese el plazo del financiamiento">
                        </div>
                        <div class="form-group">
                            <label for="tasa" class="form-label">Tasa:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="tasa" name="tasa" maxlength="6" placeholder="Ingrese la tasa del financiamiento">
                        </div>
                        <div class="form-group">
                            <label for="cuotaKi" class="form-label">Cuota Financiamiento:</label>
                            <input type="text" class="form-control" id="cuotaKi" name="cuotaKi" placeholder="0.00" readonly>
                        </div>
                        <div class="form-group">
                            <label for="multaFinanciamiento" class="form-label">Multa Financiamiento:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="multaFinanciamiento" name="multaFinanciamiento" maxlength="9" placeholder="Ingrese la multa de financiamientos">
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
<!-- Modal de editar mantenimiento -->
<div class="modal fade" id="crearModalMantenimiento" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Editar Mantenimiento</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formGuardarMantenimiento' accept-charset="UTF-8">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idMantenimiento" value="">
                        <div class="form-group">
                            <label for="fecha" class="form-label">Fecha Aplicación:<strong class="text-danger"> *</strong></label>
                            <input type="date" class="form-control" id="fechaAplicacionM" name="fechaAplicacionM" maxlength="10" placeholder="Ingrese la fecha de aplicaciï¿½n">
                        </div>
                        <div class="form-group">
                            <label for="cuotaMantenimiento" class="form-label">Cuota Mantenimiento:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="cuotaMantenimiento" name="cuotaMantenimiento" maxlength="9" placeholder="Ingrese la cuota de mantenimiento">
                        </div>
                        <div class="form-group">
                            <label for="multaMantenimiento" class="form-label">Multa Mantenimiento:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control" id="multaMantenimiento" name="multaMantenimiento" maxlength="9" placeholder="Ingrese la multa de mantenimiento">
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
<!-- Modal de eliminar financiamiento -->
<div class="modal fade" id="confirmarEliminarModalFinanciamiento" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminaciï¿½n</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <strong>¿Estás seguro de eliminar la información?</strong>
                <p>Ten en cuenta que se eliminará la información del financiamiento de la venta, y los pagos realizados que se encuentren relacionados a esta.</p>
            </div>
            <div class="modal-footer">
              <button id="eliminarFinanciamientoBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
              <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>
<form id="eliminarFinanciamientoForm" method="post" action="/EliminarFinanciamientoVenta/{idAsignacion}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>
<!-- Modal de eliminar mantenimiento -->
<div class="modal fade" id="confirmarEliminarModalMantenimiento" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminaciï¿½n</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <strong>¿Estás seguro de eliminar la información?</strong>
                <p>Ten en cuenta que se eliminará la información del mantenimiento de la venta, y los pagos realizados que se encuentren relacionados a esta.</p>
            </div>
            <div class="modal-footer">
              <button id="eliminarMantenimientoBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
              <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>
<form id="eliminarMantenimientoForm" method="post" action="/EliminarMantenimientoVenta/{idAsignacion}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>
        
<!-- Script de la pï¿½gina -->
<%@ include file="../venta-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Venta/Informacion.js"></script>


      
