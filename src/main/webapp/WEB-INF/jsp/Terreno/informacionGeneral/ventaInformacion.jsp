<%@ include file="../venta-header.jspf"%>
<div class="row">
    <div class="subtitulo-page"><h3 class="mt-0">Venta
            <sec:authorize access="hasAuthority('EDITAR_VENTA_PRIVILAGE')">
                <span title="Editar Información" id="EditarInformacion" class="btn abrirModal-btn text-info puntero pull-right btn-sm" data-bs-toggle="modal" data-bs-target="#crearModal" data-tipo="editar" data-id="${venta.idVenta}" data-modo="actualizar" style="cursor: pointer;">
                    <i class="far fa-edit"></i>
                </span>
            </sec:authorize>
        </h3>
    </div>
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
                    <td class="encabezado-tabla font-weight-bold">Prima</td>
                    <td><c:if test="${not empty valorPrima}">$ <c:out value="${String.format('%.2f', valorPrima)}"/></c:if></td>
                </tr>
                <tr>
                    <td class="encabezado-tabla font-weight-bold">Monto</td>
                    <td><c:if test="${not empty venta.monto}">$ <c:out value="${String.format('%.2f', venta.monto)}"/></c:if></td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="subtitulo-page mt-4"><h3>Financiamiento
            <sec:authorize access="hasAuthority('EDITAR_INFORMACION_FINANCIAMIENTO_PRIVILAGE')">
                <span title="Editar Información" id="EditarInformacion" class="btn abrirModal-btn text-info puntero pull-right btn-sm" data-bs-toggle="modal" data-bs-target="#crearModal" data-tipo="editar" data-id="${venta.idVenta}" data-modo="actualizar" style="cursor: pointer;">
                    <i class="far fa-edit"></i>
                </span>
            </sec:authorize>
        </h3>
    </div>
    <div class="tarjeta-container">
        <div class="tarjeta-venta-izquierda border p-3 rounded">
            <table class="table small table-borderless" id="tabla-informacion-financiamiento">
                <tbody>
                    <tr>
                        <td class="font-weight-bold" width="10%">Fecha Aplicación</td>
                        <td><c:if test="${not empty venta}"><fmt:formatDate value="" pattern="dd/MM/yyyy" /></c:if></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Monto</td>
                        <td><c:if test="${not empty venta}">$ <c:out value=""/></c:if></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Plazo</td>
                        <td><c:if test="${not empty venta}"></c:if></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Tasa</td>
                        <td><c:if test="${not empty venta}"><c:out value=""/> %</c:if></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Cuota</td>
                        <td><c:if test="${not empty venta}">$ <c:out value=""/></c:if></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Multa</td>
                        <td><c:if test="${not empty venta}">$ <c:out value=""/></c:if></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="tarjeta-venta-derecha border p-3 rounded">
            <h6>Financiamientos Anteriores</h6>
            <table class="table table-bordered custom-fixed-header small" id="tabla-financiamientos">
                <thead>
                    <tr class="encabezado-tabla">
                        <th scope="col">N°</th>
                        <th scope="col">Fecha Aplicación</th>
                        <th scope="col">Monto</th>
                        <th scope="col">Plazo</th>
                        <th scope="col">Tasa</th>
                        <th scope="col">Cuota</th>
                        <th scope="col">Multa</th>
                        <th scope="col">Opciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty financiamientos}">
                        <tr>
                            <td colspan="8">No hay registros</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty financiamientos}">
                        <c:forEach items="${financiamientos}" var="eAsignacion" varStatus="numeroAsignacion">
                            <tr>
                                <td>${numeroAsignacion.index+1}</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>
                                    <sec:authorize access="hasAuthority('ELIMINAR_INFORMACION_FINANCIAMIENTO_PRIVILAGE')">
                                    <button type="button" class="btn btn-outline-danger eliminarModalFinanciamiento-btn btn-sm" data-id="${eAsignacion.idFinanciamiento}" 'data-cod="${eAsignacion.idFinanciamiento}"><i class="far fa-trash-alt"></i></button>
                                    </sec:authorize>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="subtitulo-page mt-4"><h3>Mantenimiento
            <sec:authorize access="hasAuthority('EDITAR_INFORMACION_MANTENIMIENTO_PRIVILAGE')">
                <span title="Editar Información" id="EditarInformacion" class="btn abrirModal-btn text-info puntero pull-right btn-sm" data-bs-toggle="modal" data-bs-target="#crearModal" data-tipo="editar" data-id="${venta.idVenta}" data-modo="actualizar" style="cursor: pointer;">
                    <i class="far fa-edit"></i>
                </span>
            </sec:authorize>
        </h3>
    </div>
    <div class="tarjeta-container mb-3">
        <div class="tarjeta-venta-izquierda border p-3 rounded">
            <table class="table small table-borderless" id="tabla-informacion-mantenimiento">
                <tbody>
                    <tr>
                        <td class="font-weight-bold" width="10%">Fecha Aplicación</td>
                        <td><c:if test="${not empty venta}"><fmt:formatDate value="" pattern="dd/MM/yyyy" /></c:if></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Cuota</td>
                        <td><c:if test="${not empty venta}">$ <c:out value=""/></c:if></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Multa</td>
                        <td><c:if test="${not empty venta}">$ <c:out value=""/></c:if></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="tarjeta-venta-derecha border p-3 rounded">
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
                    <c:if test="${empty matenimientos}">
                        <tr>
                            <td colspan="5">No hay registros</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty matenimientos}">
                        <c:forEach items="${matenimientos}" var="eAsignacion" varStatus="numeroAsignacion">
                            <tr>
                                <td>${numeroAsignacion.index+1}</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>
                                    <sec:authorize access="hasAuthority('ELIMINAR_INFORMACION_FINANCIAMIENTO_PRIVILAGE')">
                                    <button type="button" class="btn btn-outline-danger eliminarModalMantenimiento-btn btn-sm" data-id="${eAsignacion.idMantenimiento}" 'data-cod="${eAsignacion.idMantenimiento}"><i class="far fa-trash-alt"></i></button>
                                    </sec:authorize>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
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
<div class="modal fade" id="crearModalFinanciamiento" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Editar Información Financiamiento</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formGuardar' accept-charset="UTF-8">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idFinanciamiento" value="">
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
<div class="modal fade" id="crearModalMantenimiento" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Editar Información Mantenimiento</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formGuardar' accept-charset="UTF-8">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idMantenimiento" value="">
                        <div class="form-group">
                            <label for="cuotaMantenimiento" class="form-label">Cuota Mantenimiento: </label>
                            <input type="text" class="form-control" id="cuotaMantenimiento" name="cuotaMantenimiento" maxlength="9" placeholder="Ingrese la cuota de mantenimiento">
                        </div>
                        <div class="form-group">
                            <label for="multaMantenimiento" class="form-label">Multa Mantenimiento: </label>
                            <input type="text" class="form-control" id="multaMantenimiento" name="multaMantenimiento" maxlength="9" placeholder="Ingrese la multa de mantenimiento">
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
<%@ include file="../venta-footer.jspf"%>
<script src="${pageContext.request.contextPath}/js/informacionVenta.js"></script>


      
