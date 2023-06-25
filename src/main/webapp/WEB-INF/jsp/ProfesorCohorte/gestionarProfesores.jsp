<%@ include file="../common/header1.jspf"%>
<%@ include file="../common/navigation1.jspf"%>


<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12">
                    <div class="titulo-Perfil">
                        <div class="container">
                            <h1>Profesores</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Main content -->
    <section class="content pb-5">
        <div class="container">
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

            <br>

            <div class="pt-4">
                <div class="table-responsive">
                    <table id="asignaturaTable" class="table table-striped" >
                        <thead class="thead-light">
                            <tr>
                                <th class="text-center">N</th> 
                                <th class="text-center">Nombres</th> 
                                <th class="text-center">Pago Por Hora</th>
                                <th class="text-center">Horas a Impartir</th>
                                <th class="text-center">Total Por Servicios</th>
                                <th class="text-center">Fecha Contratacion</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${empty profesorCohorte}">
                                <tr>
                                    <td colspan="7">No hay registros</td>
                                </tr>
                            </c:if>
                            <c:if test="${not empty profesorCohorte}">
                                <c:forEach items="${profesorCohorte}" var="elemento" varStatus="status">
                                    <tr>
                                        <td width="20%">${status.index + 1}</td>
                                        <td>${elemento.idAspiranteProfesor.nombresAp}</td>
                                        <td>${elemento.montoPagarHora}</td>
                                        <td>${elemento.unidadHorasImpartir}</td>
                                        <td>${elemento.montoTotalServicios}</td>
                                        <td>${elemento.fechaContratacion}</td>
                                        <td class="text-center">
                                            <a href="/PerfilAspiranteProfesor/${elemento.idAspiranteProfesor.idAspiranteProfesor}" type="button" title="Perfil" class="btn btn-outline-primary">
                                                <i class="bi bi-person"></i>
                                            </a> 
                                            </button> 
                                            <button type="button" title="Eliminar" class="btn btn-outline-danger eliminarModal-btn" data-id="${elemento.idProfesor}" data-nombre="${elemento.idAspiranteProfesor.nombresAp}" >
                                                <i class="bi bi-trash"></i></button>
                                            <button type="button" class="btn btn-outline-primary abrirModalEdit-btn" data-bs-toggle="modal" data-bs-target="#contratar" data-tipo="editar" data-id="${elemento.idProfesor}" data-modo="actualizar"><i class="bi bi-pencil-square"></i></button>
                                           <button type="button" class="btn btn-outline-success modalMaterias-btn" data-id="${elemento.idProfesor}"><i class="bi bi-journal-bookmark"></i></button>

                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>
    <!-- Modal de eliminar -->
    <div class="modal fade" id="confirmarEliminarModal" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="modal-body">

                    </div>
                </div>
                <div class="modal-footer">
                    <button id="eliminarProfesorBtn" class="btn btn-danger">Eliminar</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>

    <form id="eliminarForm" method="post" action="/EliminarProfesor/{idProfesorCohorte}">

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
    <div class="modal fade" id="contratar" tabindex="-1" aria-labelledby="contratarModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel">Contratar Postulante</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id='formGuardar' accept-charset="UTF-8">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idList" value="${idPrograma}">
                        <input type="hidden" id="idActividad">
                        <div class="row">
                            <div class="form-group mb-3 col-md-6 col-sm-6">
                                <label>Monto a Pagar por Hora</label>
                                <input type="number" maxlength="9" class="form-control" id="montoPagarHora" name="montoPagarHora">  
                            </div>
                            <div class="form-group mb-3 col-md-6 col-sm-6">
                                <label>Unidad de Horas Impartir</label>
                                <input type="number" maxlength="9"  class="form-control" id="unidadHorasImpartir" name="unidadHorasImpartir">  
                            </div>
                            <div class="form-group mb-3 col-md-6 col-sm-6">
                                <label>Monto Total de Servicios</label>
                                <input disabled type="number" maxlength="9"  class="form-control" id="total">  
                                <input type="hidden" maxlength="9"  class="form-control" id="montoTotalServicios" name="montoTotalServicios">  
                            </div>
                            <div class="form-group mb-3 col-md-6 col-sm-6">
                                <label>Fecha de Contratacion</label>
                                <input type="text" class="form-control" id="fechaContratacion" name="fechaContratacion" placeholder="dd/mm/yyyy">
                            </div><!--
                            <div class="form-grup mb-3 col-md-6 col-sm-6">
                                <label for="idAreaC">Asignatura</label>
                                <select class="form-select form-select-sm" id="idAsignatura" name="idAsignatura">
                                    <option value="">Seleccione una Asignatura</option>
                            <c:forEach items="${asignaturas}" var="asignatura" varStatus="status">
                                <option value="${asignatura.idAsignatura}">${asignatura.nombreMateria}</option>
                            </c:forEach>
                        </select>
                    </div>-->
                            <input type="hidden" class="form-control"  id="idProfesor"> 
                            <input type="hidden" class="form-control"  id="idAspiranteProfesor" name="idAspiranteProfesor">  

                            <input type="hidden" class="form-control" value="${idCohorte}" id="idCohorte" name="idCohorte">  

                        </div>

                        <div class="modal-footer">
                            <button id='btnSumit' type="submit" class="btn btn-outline-success guardar-btn">Guardar</button>
                            <button type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">Cancelar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal pequeño -->
    <div class="modal fade" id="modalMaterias" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <!-- Contenido del modal -->
                <div class="modal-header">
                    <h5 class="modal-title" id="myModalLabel">Título del modal</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    
                </div>
            </div>
        </div>
    </div>

</div>
<%@ include file="../common/footer1.jspf"%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.1.4/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.1.4/dist/sweetalert2.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/inputmask/dist/jquery.inputmask.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/profesorCohorte.js"></script>

