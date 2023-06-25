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
                            <h1>Actividades</h1>
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
            <div class="row col-sm-12 d-flex justify-content-end">
                <div class="col-sm-1">
                    <button type="button" class="btn btn-outline-primary btn-sm abrirModal-btn" 
                            data-bs-toggle="modal" data-bs-target="#crearModal" 
                            data-action="agregar">Agregar</button>
                </div>
            </div>
            <br>
            <div class="pt-4">
                <div class="table-responsive">
                    <table id="actividadTable" class="table table-striped" >
                        <thead class="thead-light">
                            <tr>
                                <th class="text-center">N°</th>
                                <th class="text-center">Actividad</th>
                                <th class="text-center">Ponderacion</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${empty actividad}">
                                <tr>
                                    <td colspan="5">No hay registros</td>
                                </tr>
                            </c:if>
                            <c:if test="${not empty actividad}">
                                <c:forEach items="${actividad}" var="elemento" varStatus="status">
                                    <tr>
                                        <td width="20%">${status.index + 1}</td>
                                        <td>${elemento.nombreActividad}</td>
                                        <td>${elemento.ponderacion} %</td>


                                        <td class="d-flex justify-content-around">
                                            <button type="button" title="Actualizar" class="btn btn-outline-primary abrirModal-btn" data-bs-toggle="modal" 
                                                    data-bs-target="#crearModal" data-tipo="editar" data-id="${elemento.idActividad}" 
                                                    data-modo="actualizar">
                                                <i class="bi bi-pencil-square"></i>
                                            </button> 
                                            <button type="button" title="Eliminar" class="btn btn-outline-danger eliminarModal-btn" data-id="${elemento.idActividad}" data-nombre="${elemento.nombreActividad}" >
                                                <i class="bi bi-trash"></i></button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
    <div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true" data-tipo="" data-modo=''>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel"></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id='formGuardar' accept-charset="UTF-8">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idList" value="${idPrograma}">
                        <input type="hidden" id="idActividad">
                        <div class="row">
                            <div class="form-group mb-3 col-md-6 col-sm-6">
                                <input type="text" class="form-control" id="actividad" name="nombreActividad" placeholder="Nombre de la Actividad">  
                            </div>

                            <div class="form-group mb-3 col-md-6 col-sm-6">
                                <input type="number" class="form-control" id="ponderacion" name="ponderacion" placeholder="Ponderaciòn en %">
                            </div>
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
                    <button id="eliminarActividadBtn" class="btn btn-danger">Eliminar</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>

    <form id="eliminarForm" method="post" action="/EliminarActividad/{idActividad}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
</div>
<%@ include file="../common/footer1.jspf"%>
<script src="${pageContext.request.contextPath}/js/actividad.js"></script>
