<%@ include file="../venta-header.jspf"%>
<div class="row">
    <div class="col-12 pb-3">
        <div class="card">
            <div class="card-header">
                <!-- Subtitulo de la página -->
                <div class="subtitulo-page">
                    <h3 class="mt-0 mb-0">Facturacion</h3>
                </div>
            </div>
            <!-- Datos -->
            <div class="card-body pb-0">
                <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                    <div class="col-sm-12 table-responsive pt-1" style="height: 55vh; padding:4px;">
                        <div class="tarjeta-container pb-3 h-100">
                            <!-- Columna izquierda -->
                            <div class="tarjeta-facturacion-izquierda border p-3 rounded">
                                <h6 class="text-center font-weight-bold">Consumidor Final
                                    <span title="Editar Información" id="EditarConsumidorFinal" class="btn abrirModalConsumidorFinal-btn text-info puntero pull-right text-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalConsumidorFinal" data-tipo="editar" data-id="${venta.idVenta}" data-modo="actualizar" style="cursor: pointer;">
                                        <i class="far fa-edit"></i>
                                    </span>
                                </h6>
                                <table class="table table-borderless small" id="tabla-consumidorFinal">
                                    <thead>
                                        <tr>
                                            <th scope="col" width="10%">N°</th>
                                            <th scope="col" width="90%">Nombre</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:if test="${empty consumidorFinal}">
                                            <tr>
                                                <td colspan="3">No hay registros</td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${not empty consumidorFinal}">
                                            <c:forEach items="${consumidorFinal}" var="eElemento" varStatus="i">
                                                <tr>
                                                    <td>${i.index+1}</td>
                                                    <td>${eElemento.propietario.persona.nombre} ${eElemento.propietario.persona.apellido}</td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                            <!-- Columna derecha -->
                            <div class="tarjeta-facturacion-derecha border p-3 rounded">
                                <h6 class="text-center font-weight-bold">Crédito Fiscal
                                    <span title="Editar Información" id="EditarCreditoFiscal" class="btn abrirModalCreditoFiscal-btn text-info puntero pull-right text-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalCreditoFiscal" data-tipo="editar" data-id="${venta.idVenta}" data-modo="actualizar" style="cursor: pointer;">
                                        <i class="far fa-edit"></i>
                                    </span>
                                </h6>
                                <table class="table table-borderless small" id="tabla-creditoFiscal">
                                    <tbody>
                                        <tr>
                                            <td width="25%" class="font-weight-bold" scope="col">N° Registro:</td>
                                            <td><c:if test="${not empty venta.estado}"></c:if></td>
                                        </tr>
                                        <tr>
                                            <td class="font-weight-bold" scope="col">NIT:</td>
                                            <td><c:if test="${not empty venta.nombre}"></c:if></td>
                                        </tr>
                                        <tr>
                                            <td class="font-weight-bold" scope="col">DUI:</td>
                                            <td><c:if test="${not empty venta.nombre}"></c:if></td>
                                        </tr>
                                        <tr>
                                            <td class="font-weight-bold" scope="col">Nombre:</td>
                                            <td><c:if test="${not empty venta.fecha}"></c:if></td>
                                        </tr>
                                        <tr>
                                            <td class="font-weight-bold" scope="col">Dirección:</td>
                                            <td><c:if test="${not empty venta.precio}"></c:if></td>
                                        </tr>
                                        <tr>
                                            <td class="font-weight-bold" scope="col">Giro:</td>
                                            <td><c:if test="${not empty venta.descuento}"></c:if></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal de agregar factura consumidor final -->
<div class="modal fade" id="crearModalConsumidorFinal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Consumidor Final</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formSeleccionarPropietario' accept-charset="UTF-8">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <div class="form-group">
                            <label for="propietarios">Seleccione los propietarios:</label>
                            <select class="form-select" id="propietarios" name="propietarios" data-live-search="true" multiple>
                                <c:if test="${not empty propietariosAsignados}">
                                    <c:forEach items="${propietariosAsignados}" var="ePropietario">
                                        <option value="${ePropietario.propietario.idPropietario}">${ePropietario.propietario.persona.dui} ${ePropietario.propietario.persona.nombre} ${ePropietario.propietario.persona.apellido}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                            <div id="span-propietarios-error" class="mensaje-error d-none" style=""><span>Este campo es requerido</span></div>
                        </div>
                        <div class="form-group">
                            <label for="estadoS" class="form-label">Impresión Comprobante: </label>
                            <select class="form-control" id="estadoS" name="estadoS" required>
                                <option value="">Seleccione el estado</option>
                                <option value="Seleccionado">Seleccionado</option>
                                <option value="No seleccionado">No seleccionado</option>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button id="btnGuardarSeleccionarPropietario" type="submit" class="btn btn-outline-success btn-sm">Guardar</button>
                        <button type="button" class="btn btn-outline-danger btn-sm" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Modal de agregar crédito fiscal -->
<div class="modal fade" id="crearModalCreditoFiscal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Crédito Fiscal</h5>
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
                            <label for="nombre" class="form-label">N° Registro: </label>
                            <input type="text" class="form-control" id="nombre" name="nombre" maxlength="200" placeholder="Ingrese el nombre de la venta" required>
                        </div>
                        <div class="form-group">
                            <label for="fecha" class="form-label">NIT: </label>
                            <input type="date" class="form-control" id="fecha" name="fecha" maxlength="10" placeholder="Ingrese la fecha de la venta">
                        </div>
                        <div class="form-group">
                            <label for="" class="form-label">DUI: </label>
                            <input type="text" class="form-control" id="precio" name="precio" maxlength="9" placeholder="Ingrese el precio de la venta">
                        </div>
                        <div class="form-group">
                            <label for="descuento" class="form-label">Nombre: </label>
                            <input type="text" class="form-control" id="descuento" name="descuento" maxlength="9" placeholder="Ingrese el descuento de la venta">
                        </div>
                        <div class="form-group">
                            <label for="prima" class="form-label">Dirección: </label>
                            <input type="text" class="form-control" id="prima" name="prima" placeholder="0.00" data-prima="${valorPrima}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="monto" class="form-label">Giro: </label>
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

<!-- Script de la página -->
<%@ include file="../venta-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/informacionVenta.js"></script>


      
