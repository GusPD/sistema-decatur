<%@ include file="../venta-header.jspf"%>
<div class="row pb-3">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <!-- Subtitulo de la página -->
                <div class="subtitulo-page">
                    <h3 class="mt-0 mb-0">Facturación</h3>
                </div>
            </div>
            <!-- Datos -->
            <div class="card-body pb-0">
                <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                    <div class="col-sm-12 d-flex aling-items-center justify-content-center pt-1 page-scroll" style="height: 57.5vh; padding:4px;">
                        <div class="row col-md-12 pb-3 h-100">
                            <!-- Columna izquierda -->
                            <div class="col-md-6 border p-3 rounded">
                                <h6 class="text-center font-weight-bold">
                                    Consumidor Final
                                    <sec:authorize access="hasAuthority('EDITAR_FACTURACION_PRIVILAGE')">
                                        <span title="Editar Información" id="EditarConsumidorFinal" class="btn abrirModalConsumidorFinal-btn text-info puntero float-end text-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalConsumidorFinal" data-tipo="editar" data-id="${venta.idVenta}" data-modo="actualizar" style="cursor: pointer;">
                                            <i class="far fa-edit"></i>
                                        </span>
                                    </sec:authorize>
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
                            <div class="col-md-6 border p-3 ml-1 rounded columna-derecha">
                                <h6 class="text-center font-weight-bold">
                                    Crédito Fiscal
                                    <sec:authorize access="hasAuthority('EDITAR_FACTURACION_PRIVILAGE')">
                                        <span title="Editar Información" id="EditarCreditoFiscal" class="btn abrirModalCreditoFiscal-btn text-info puntero float-end text-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalCreditoFiscal" data-tipo="editar" data-id="${facturacion.idFacturacion}" data-modo="actualizar" style="cursor: pointer;">
                                            <i class="far fa-edit"></i>
                                        </span>
                                        <c:if test="${not empty facturacion}">
                                            <span title="Eliminar Información" id="EliminarCreditoFiscal" class="btn eliominarModalCreditoFiscal-btn text-info puntero float-end text-blue btn-sm" data-bs-toggle="modal" data-bs-target="#eliminarModalCreditoFiscal" data-tipo="eliminar" data-id="${facturacion.idFacturacion}" data-modo="eliminar" style="cursor: pointer;">
                                                <i class="far fa-trash-alt"></i>
                                            </span>
                                        </c:if>
                                    </sec:authorize>
                                </h6>
                                <table class="table table-borderless small" id="tabla-creditoFiscal">
                                    <tbody>
                                        <tr>
                                            <td width="25%" class="font-weight-bold" scope="col">N° Registro:</td>
                                            <td><c:if test="${not empty facturacion.registro}">${facturacion.registro}</c:if></td>
                                        </tr>
                                        <c:if test="${not empty facturacion.nit}">
                                            <tr>
                                                <td class="font-weight-bold" scope="col">NIT:</td>
                                                <td><c:if test="${not empty facturacion.nit}">${facturacion.nit}</c:if></td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${not empty facturacion.dui}">
                                            <tr>
                                                <td class="font-weight-bold" scope="col">DUI:</td>
                                                <td><c:if test="${not empty facturacion.dui}">${facturacion.dui}</c:if></td>
                                            </tr>
                                        </c:if>
                                        <tr>
                                            <td class="font-weight-bold" scope="col">Nombre:</td>
                                            <td><c:if test="${not empty facturacion.nombre}">${facturacion.nombre}</c:if></td>
                                        </tr>
                                        <tr>
                                            <td class="font-weight-bold" scope="col">Dirección:</td>
                                            <td><c:if test="${not empty facturacion.direccion}">${facturacion.direccion}</c:if></td>
                                        </tr>
                                        <tr>
                                            <td class="font-weight-bold" scope="col">Giro:</td>
                                            <td><c:if test="${not empty facturacion.giro}">${facturacion.giro}</c:if></td>
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
            <form id='formSeleccionarPropietario' accept-charset="UTF-8">
                <div class="modal-body">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <div class="form-group">
                            <label for="propietarios">Seleccione los propietarios:<strong class="text-danger"> *</strong></label>
                            <select class="form-select form-select-sm" id="propietarios" name="propietarios" placeholder="Seleccione un propietario" data-live-search="true" multiple>
                                <c:if test="${not empty propietariosAsignados}">
                                    <c:forEach items="${propietariosAsignados}" var="ePropietario">
                                        <option value="${ePropietario.propietario.idPropietario}">${ePropietario.propietario.persona.numero} ${ePropietario.propietario.persona.nombre} ${ePropietario.propietario.persona.apellido}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                            <div id="span-propietarios-error" class="mensaje-error d-none"><span>Este campo es requerido</span></div>
                        </div>
                        <div class="form-group">
                            <label for="estadoS" class="form-label">Impresión Comprobante:<strong class="text-danger"> *</strong></label>
                            <select class="form-select form-select-sm" id="estado" name="estado" required>
                                <option value="">Seleccione el estado</option>
                                <option value="Seleccionado">Seleccionado</option>
                                <option value="No seleccionado">No seleccionado</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer d-flex justify-content-between">
                    <label for="monto" class="form-label text-danger mensaje-obligatorios">(*) Campos Obligatorios</label>
                    <div>
                        <button type="submit" id="btnGuardarSeleccionarPropietario" class="btn btn-outline-success btn-sm">Guardar</button>
                        <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Modal de agregar crédito fiscal -->
<div class="modal fade" id="crearModalCreditoFiscal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header justify-content-left">
                <h5 class="modal-title" id="crearModalLabel">Crédito Fiscal</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="formGuardar" accept-charset="UTF-8" method="post" action="/AgregarFacturacionVenta">
                <div class="modal-body">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idVenta" value="${venta.getIdVenta()}">
                        <input type="hidden" id="idTerreno" value="${terreno.getIdTerreno()}">
                        <input type="hidden" id="idFacturacion" value="${facturacion.getIdFacturacion()}">
                        <div class="form-group">
                            <label for="registro" class="form-label">N° Registro:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="registro" name="registro" maxlength="12" placeholder="Ingrese el registro" required>
                        </div>
                        <div class="form-group">
                            <label for="fiscal" class="form-label">Personería Jurídica:<strong class="text-danger"> *</strong></label>
                            <select class="form-select form-select-sm" id="fiscal" name="fiscal" required>
                                <option value="">Seleccione una opción</option>
                                <option value="0">Persona</option>
                                <option value="1">Empresa</option>
                            </select>
                        </div>
                        <div id="input-nit" class="form-group" style="display:none;">
                            <label for="nit" class="form-label">NIT:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="nit" name="nit" maxlength="14" placeholder="Ingrese el NIT">
                        </div>
                        <div id="input-dui" class="form-group" style="display:none;">
                            <label for="" class="form-label">DUI:<strong class="text-danger"> *</strong></label>
                            <input type="dui" class="form-control form-control-sm" id="dui" name="dui" maxlength="9" placeholder="Ingrese el DUI">
                        </div>
                        <div class="form-group">
                            <label for="nombre" class="form-label">Nombre:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="nombre" name="nombre" maxlength="200" placeholder="Ingrese el nombre" required>
                        </div>
                        <div class="form-group">
                            <label for="direccion" class="form-label">Dirección:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="direccion" name="direccion" maxlength="300" placeholder="Ingrese la dirección" required>
                        </div>
                        <div class="form-group">
                            <label for="giro" class="form-label">Giro:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="giro" name="giro" maxlength="200" placeholder="Ingrese el giro" required>
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
<!-- Modal de eliminar crédito fiscal -->
<div class="modal fade" id="confirmarEliminarModal" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminación</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <strong>¿Estás seguro de eliminar la información?</strong>
                <p>Ten en cuenta que se eliminará la información de la facturación del crédito fiscal de la venta.</p>
            </div>
            <div class="modal-footer">
              <button id="eliminarFacturacionBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
              <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>
<form id="eliminarFacturacionForm" method="post" action="/EliminarFacturacionVenta/{idFacturacion}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form> 
                        
<!-- Script de la página -->
<%@ include file="../venta-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Venta/Facturacion.js"></script>


      
