<%@ include file="../common/header1.jspf"%>
<%@ include file="../common/navigation1.jspf"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <div class="container-fluid">
        <div class="row">
          <div class="col-sm-12">
            <div class="titulo-Perfil">
                <div class="container">
                    <h1>Docentes Contratados</h1>
                </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!--<section class="content pb-5">
      <form action="/obtenerProfesoresContratados" method="post" accept-charset="UTF-8">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <div class="form-group">
          <label for="destino">Destinatarios:</label>
          <select type="text" class="form-select" id="destino" name="destino" required>
              <option value="">Seleccione una opción</option>
              <option value="Todos">Todos</option>
              <option value="Estudiantes">Estudiantes</option>
              <option value="Aspirantes a Profesores">Aspirantes a Profesores</option>
              <option value="Profesores">Profesores</option>
              <option value="Coordinadores Académicos">Coordinadores Académicos</option>
          </select>
        </div>  
        <div class="form-group">
          <label for="asunto">Asunto:</label>
          <input type="text" class="form-control" id="asunto" name="asunto" maxlength="100" required>
        </div>
        <div class="form-group">
          <label for="mensaje">Mensaje:</label>
          <textarea class="form-control" id="mensaje" name="mensaje" rows="10" required></textarea>
        </div>        
        <div class="modal-footer">
          <button type="submit" class="btn btn-outline-success">Guardar</button>
          <button class="btn btn-outline-danger" data-bs-dismiss="modal">Cancelar</button>
        </div>
      </form>
    </section>-->

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

            <div class="pt-4">
                <div class="table-responsive-md">
                    <table id="reporteProfesoresContratadosAsignaturaTable" class="table table-striped" style="width:100%">
                        <thead class="table-light">
                            <tr>
                                <th class="text-center">Nombres</th>
                                <th class="text-center">Apellidos</th>
                                <th class="text-center">Maestría</th>
                                <th class="text-center">Cohorte</th>
                                <th class="text-center">Materia</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div><!-- /.container-->
    </section>
    <!-- /.Main content -->
</div>
<!-- /.content-wrapper -->
  

<%@ include file="../common/footer1.jspf"%>
<script src="${pageContext.request.contextPath}/js/reporteProfesoresContratadosAsignatura.js"></script>

