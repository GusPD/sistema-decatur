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
                            <h1>Programa de la Asingatura</h1>
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
                    <table id="actividadTable" class="table table-striped" >
                        <thead class="thead-light">
                            <tr>
                                <th class="text-center">Duracion Semanas</th>
                                <th class="text-center">Horas Ciclo</th>
                                <th class="text-center">Horas Teorico Semana</th>
                                <th class="text-center">Horas Practica Semana</th>
                                <th class="text-center">Introduccion</th>
                                <th class="text-center">Descripcion Programa</th>
                                <th class="text-center">Objetivos</th>
                                <th class="text-center">Metodologia Ensenanza</th>
                                <th class="text-center">Sistema Evaluacion</th>
                                <th class="text-center">Bibliografia</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${empty programa}">
                                <tr>
                                    <td colspan="5">No hay registros</td>
                                </tr>
                            </c:if>
                            <c:if test="${not empty programa}">

                                <tr>
                                    <td>${programa.duracionSemanas}</td>
                                    <td>${programa.horasCiclo}</td>
                                    <td>${programa.horasTeoricoSemana}</td>
                                    <td>${programa.horasPracticaSemana}</td>
                                    <td>${programa.introduccion}</td>
                                    <td>${programa.descripcionPrograma}</td>
                                    <td>${programa.objetivos}</td>
                                    <td>${programa.metodologiaEnsenanza}</td>
                                    <td>${programa.sistemaEvaluacion}</td>
                                    <td>${programa.bibliografia}</td>

                                    <td class="d-flex justify-content-around">
                                        <button type="button" title="Actualizar" class="btn btn-outline-primary abrirModal-btn" data-bs-toggle="modal" 
                                                data-bs-target="#crearModal" data-tipo="editar" data-id="${programa.idProgramAsignatura}" 
                                                data-modo="actualizar">
                                            <i class="bi bi-pencil-square"></i>
                                        </button> 

                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
    <div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel"></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id='formGuardar' accept-charset="UTF-8">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idPrograma">
                        <div class="row">
                            <div class="form-grup mb-3 col-md-6 col-sm-6">
                                <label for="duracion">Duraciòn en Semanas</label>
                                <input type="number" class="form-control" id="duracionSemanas" name="duracionSemanas">
                            </div>
                            <div class="form-grup mb-3 col-md-6 col-sm-6">
                                <label for="horasT">Horas Teorico Semanas</label>
                                <input type="number" class="form-control" id="horasTeoricoSemana" name="horasTeoricoSemana" >
                            </div>
                            <div class="form-grup mb-3 col-md-6 col-sm-6">
                                <label for="horasP">Horas Practica Semanas</label>
                                <input type="number" class="form-control" id="horasPracticaSemana" name="horasPracticaSemana">
                            </div>
                            <div class="form-grup mb-3 col-md-6 col-sm-6">
                                <label for="horaCiclo">Horas Ciclo</label>
                                <input type="number" class="form-control" id="horasCiclo" name="horasCiclo" readonly>
                            </div>
                            <div class="form-grup mb-3 col-md-6 col-sm-6">
                                <label for="introduccion">Introduccion</label>
                                <textarea type="text" class="form-control" id="introduccion" name="introduccion" rows="2"></textarea>
                            </div>
                            <div class="form-grup mb-3 col-md-6 col-sm-6">
                                <label for="descipcionPrograma">Descriòn del Programa</label>
                                <textarea type="text" class="form-control" id="descripcionPrograma" name="descripcionPrograma" rows="2"></textarea>
                            </div>
                            <div class="form-grup mb-3 col-md-6 col-sm-6">
                                <label for="objetivo">Objetivo</label>
                                <textarea type="text" class="form-control" id="objetivos" name="objetivos" rows="2"></textarea>
                            </div>
                            <div class="form-grup mb-3 col-md-6 col-sm-6">
                                <label for="metodologia">Metodologia de Enseñanza</label>
                                <textarea type="text" class="form-control" id="metodologiaEnsenanza" name="metodologiaEnsenanza" rows="2"></textarea>
                            </div>
                            <div class="form-grup mb-3 col-md-6 col-sm-6">
                                <label for="sistemaEvaluacion">Sistema de Evaluaciòn</label>
                                <textarea type="text" class="form-control" id="sistemaEvaluacion" name="sistemaEvaluacion" rows="2"></textarea>
                            </div>
                            <div class="form-grup mb-3 col-md-6 col-sm-6">
                                <label for="bibliografia">Bibliografia</label>
                                <textarea type="text" class="form-control" id="bibliografia" name="bibliografia" rows="2"></textarea>                           
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
</div>
<%@ include file="../common/footer1.jspf"%>
<script src="${pageContext.request.contextPath}/js/programaAsignatura.js"></script>
