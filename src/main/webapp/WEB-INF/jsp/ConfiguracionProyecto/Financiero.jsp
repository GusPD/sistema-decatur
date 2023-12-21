<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationProyecto.jspf"%>
<div class="content-wrapper">
    <!-- Título de la página -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1>Configuración Financiera</h1>
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
                    <div class="card p-1">
                        <!-- Funciones de la página -->
                        <div class="card-header">
                            <h4 class="card-title d-flex justify-content-between">
                                Aplicación de nueva cuota de mantenimiento
                                <div class="d-flex justify-content-end">
                                    <sec:authorize access="hasAuthority('AGREGAR_INFORMACION_MANTENIMIENTO_PRIVILAGE')">
                                        <button title="Editar Mantenimiento" id="EditarInformacionMantenimiento" class="btn btn-blue btn-sm" data-bs-toggle="modal" data-bs-target="#crearModalMantenimiento" data-tipo="editar" data-id="${mantenimiento.idAsignacion}" data-modo="actualizar"><i class="far fa-edit"></i></button>
                                    </sec:authorize>
                                </div>
                            </h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>               
</div>

<!-- Modal de editar mantenimiento -->
<div class="modal fade" id="crearModalMantenimiento" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Editar Mantenimiento</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id='formGuardarMantenimiento' accept-charset="UTF-8">
                <div class="modal-body">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idProyecto" value="${proyecto.idProyecto}">
                        <input type="hidden" id="idMantenimiento" value="">
                        <div class="form-group">
                            <label for="fecha" class="form-label">Fecha Aplicación:<strong class="text-danger"> *</strong></label>
                            <input type="date" class="form-control form-control-sm" id="fechaAplicacionM" name="fechaAplicacionM" maxlength="10" placeholder="Ingrese la fecha de aplicación">
                        </div>
                        <div class="form-group">
                            <label for="cuotaMantenimiento" class="form-label">Cuota Mantenimiento:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="cuotaMantenimiento" name="cuotaMantenimiento" maxlength="9" placeholder="Ingrese la cuota de mantenimiento">
                        </div>
                        <div class="form-group">
                            <label for="multaMantenimiento" class="form-label">Multa Mantenimiento:<strong class="text-danger"> *</strong></label>
                            <input type="text" class="form-control form-control-sm" id="multaMantenimiento" name="multaMantenimiento" maxlength="9" placeholder="Ingrese la multa de mantenimiento">
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

<!-- Script de la página -->

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/ConfiguracionProyecto/Financiero.js"></script>

