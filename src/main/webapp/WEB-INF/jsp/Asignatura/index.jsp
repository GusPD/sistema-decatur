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
                            <h1>Asignaturas Del Plan de Estudio</h1>
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
                    <table id="asignaturaTable" class="table table-striped" >
                        <thead class="thead-light">
                            <tr>
                                <th class="text-center">Nº Correlativo</th> 
                                <th class="text-center">Codigo</th>
                                <th class="text-center">Nombre</th>
                                <th class="text-center">UV</th>
                                <th class="text-center">Ciclo</th>
                                <th class="text-center"></th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>

    <!-- Modal para agregar -->
    <div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true" data-tipo="" data-modo=''>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel"></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">

                    <form class="form-floating" id='formGuardar' accept-charset="UTF-8">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="asignaturaId">
                        <input type="hidden" id="mallaId" name="idMalla" value="${idMallaC}">
                        <div class="row">
                            <div class="row">
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="codigoAsignatura">Código Asignatura</label>
                                    <input type="text" maxlength="10" class="form-control" id="codigoAsignatura" name="codigoAsignatura">
                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="nombreAsignatura">Nombre de la Asignatura</label>
                                    <input type="text" maxlength="100" class="form-control" id="nombreAsignatura" name="nombreAsignatura">

                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="uv">Unidades Valorativas</label>
                                    <input type="number" maxlength="3" class="form-control" id="uv" name="uv">

                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="numeroCorrelativo">Nùmero Correlativo</label>
                                    <input type="number" maxlength="3" class="form-control" id="numeroCorrelativo" name="numeroCorrelativo">

                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="ciclo">Ciclo</label>
                                    <select class="form-control" id="ciclo" name="ciclo">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                    </select>
                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="idAreaC">Àrea de Conocimiento</label>
                                    <select class="form-select form-select-sm" id="idAreaC" name="idAreaC">
                                        <option value="">Seleccione una Área de conocimiento</option>
                                        <c:forEach items="${areaC}" var="elementoArea" varStatus="status">
                                            <option value="${elementoArea.idAreaConocimiento}">${elementoArea.nombreArea}</option>
                                        </c:forEach>
                                    </select>

                                </div>
                            </div>
                            <h6>Programa de la Asignatura</h6>
                            <div class="row">
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="duracion">Duración en Semanas</label>
                                    <input type="number" maxlength="4" class="form-control" id="duracion" name="duracion">

                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="horasT">Horas Teorico Semanas</label>
                                    <input type="number" maxlength="4" class="form-control" id="horasT" name="horasT" >

                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="horasP">Horas Practica Semanas</label>
                                    <input type="number" maxlength="4" class="form-control" id="horasP" name="horasP">

                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="horaCiclo">Horas Ciclo</label>
                                    <input type="number" maxlength="4" class="form-control" id="horaCiclo" name="horaCiclo" readonly>

                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="introduccion">Introduccion</label>
                                    <textarea rows="2" maxlength="300" type="text" class="form-control" id="introduccion" name="introduccion"></textarea>
                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="descipcionPrograma">Descripción del Programa</label>
                                    <textarea rows="2" maxlength="500" type="text" class="form-control" id="descipcionPrograma" name="descipcionPrograma"></textarea>
                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="objetivo">Objetivo</label>
                                    <textarea rows="2" type="text" class="form-control" id="objetivo" name="objetivo"></textarea>
                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="metodologia">Metodologia de Enseñanza</label>
                                    <textarea rows="2" maxlength="300" type="text" class="form-control" id="metodologia" name="metodologia"></textarea>
                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="sistemaEvaluacion">Sistema de Evaluación</label>
                                    <textarea rows="2" maxlength="500" type="text" class="form-control" id="sistemaEvaluacion" name="sistemaEvaluacion"></textarea>
                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="bibliografia">Bibliografia</label>
                                    <textarea rows="2" maxlength="300" type="text" class="form-control" id="bibliografia" name="bibliografia"></textarea>                              
                                </div> 
                            </div>

                            <h6>Actividad de la Asignatura</h6>
                            <div class="row">
                                <div class="alert alert-danger d-flex align-items-center alert-dismissible fade d-none" role="alert">
                                    <strong><i class="bi bi-exclamation-triangle"></i> Error!&nbsp;</strong>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                                <div class="form-group mb-3 col-md-6 col-sm-6">
                                    <input type="text" maxlength="100" class="form-control" id="actividad" name="actividad[]" placeholder="Nombre de la Actividad">  
                                </div>

                                <div class="form-group mb-3 col-md-6 col-sm-6">
                                    <input type="number" maxlength="3" class="form-control ponderaciones-class" id="ponderacion" name="ponderacion[]" placeholder="Ponderación de la Actividad en %">
                                </div>
                            </div>
                            <div id="actividades-extra" class="row">
                            </div> 
                            <div class="form-group col-4">
                                <button type="button" class="btn btn-secondary" id="btnAgregarActividad">Añadir actividad</button>
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

    <div class="modal fade" id="editarModal" tabindex="-1" aria-labelledby="editarModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel"></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form class="form" id='formGuardarEditar' accept-charset="UTF-8">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="asignaturaIdEditar">
                        <div class="row">
                            <div class="row">
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="codigoAsignatura">Código Asignatura</label>
                                    <input type="text" class="form-control" id="codAsignatura" name="codAsignatura">
                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="nombreAsignatura">Nombre de la Asignatura</label>
                                    <input type="text" class="form-control" id="nombreMateria" name="nombreMateria">
                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="uv">Unidades Valorativas</label>
                                    <input type="number" class="form-control" id="unidadesValorativas" name="unidadesValorativas">
                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="numeroCorrelativo">Nùmero Correlativo</label>
                                    <input type="number" class="form-control" id="numeroCorrelativo" name="numeroCorrelativo">
                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="ciclo">Ciclo</label>
                                    <select class="form-control" id="ciclo" name="ciclo">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                    </select>
                                </div>
                                <div class="form-grup mb-3 col-md-6 col-sm-6">
                                    <label for="idAreaC">Àrea de Conocimiento</label>
                                    <select class="form-select form-select-sm" id="idAreaConocimiento" name="idAreaConocimiento">
                                        <option value="">Seleccione una Área de conocimiento</option>
                                        <c:forEach items="${areaC}" var="elementoArea" varStatus="status">
                                            <option value="${elementoArea.idAreaConocimiento}">${elementoArea.nombreArea}</option>
                                        </c:forEach>
                                    </select>

                                </div>
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

                </div>
                <div class="modal-footer">
                    <button id="eliminarAsignaturaBtn" class="btn btn-danger">Eliminar</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>

    <form id="eliminarAsignaturaForm" method="post" action="/EliminarAsignatura/{idAsignatura}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
</div>
<script>
    var idMallaCurricular = "${idMallaC}";
</script>
<%@ include file="../common/footer1.jspf"%>
<script src="${pageContext.request.contextPath}/js/asignatura.js"></script>
<style>
    .modal-dialog {
        max-width: 800px; /* Ajusta el valor según el ancho deseado */
    }
    .modal-content {
        width: 100%; /* Opcional: haz que el contenido del modal ocupe todo el ancho disponible */
    }
</style>

