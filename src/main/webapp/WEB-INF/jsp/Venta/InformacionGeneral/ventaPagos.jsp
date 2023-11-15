<%@ include file="../venta-header.jspf"%>
<div class="row">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <!-- Subtitulo de la página y funciones de los datos -->
                <div class="subtitulo-page">
                    <h3 class="mt-0 mb-0">
                        <div class="d-flex">
                            <div class="col-sm-6">
                                Pagos
                            </div>
                            <div class="col-sm-6 d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('SELECCIONAR_PROPIETARIO_PRIVILAGE')">
                                    <button title="Seleccionar Propietario" id="SeleccionarPropietario" class="btn btn-blue btn-sm" data-bs-toggle="modal" data-bs-target="#seleccionarModalPropietario" data-tipo="seleccionar"><i class="fas fa-user-check"></i></button>
                                </sec:authorize>
                                <sec:authorize access="hasAuthority('AGREGAR_PROPIETARIO_PRIVILAGE')">
                                    <button title="Agregar Propietario" id="AgregarPropietario" class="btn btn-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalPropietario" data-tipo="agregar"><i class="fas fa-user-plus"></i></button>
                                </sec:authorize>
                            </div>
                        </div>
                    </h3>
                </div>
            </div>
            <!-- Datos -->
            <div class="card-body">
                <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                    <div class="col-sm-12 table-responsive pt-1" style="height: 55vh; padding:4px;">
                        <table id="pagoTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('VER_PROPIETARIO_PRIVILAGE')" var="hasPrivilegeVerPropietario"></sec:authorize>
<script>var hasPrivilegeVerPropietario = <c:out value='${hasPrivilegeVerPropietario}' />;</script>    

<%@ include file="../venta-footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/pago.js"></script>


      
