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
                            <h1>Asignaturas Inscritas en ${cohorte.nombreCohorte}</h1>
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
                <strong><i class="bi bi-check-circle"></i> Éxito!</strong> &nbsp; ${mensaje}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
              </div>
            </c:if>
            <c:if test="${not empty error}">
              <div class="alert alert-danger d-flex align-items-center alert-dismissible fade show" role="alert">
                <strong><i class="bi bi-exclamation-triangle"></i> Error!</strong> &nbsp;${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
              </div>
            </c:if>
            <div class="pt-4">
                <div class="table-responsive">
                    <table id="asignaturasInscritasTable" class="table table-striped" >
                        <thead class="thead-light">
                            <tr>
                                <th class="text-center">Nº Correlativo</th> 
                                <th class="text-center">Codigo</th>
                                <th class="text-center">Nombre</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${not empty asignaturas}">
                                <c:forEach items="${asignaturas}" var="asignatura" varStatus="status">
                                    <tr>
                                      <td class="text-center">${asignatura.numeroCorrelativo}</td>
                                      <td class="text-center">${asignatura.codAsignatura}</td>
                                      <td class="text-center">${asignatura.nombreMateria}</td>
                                      <td class="text-center">
                                        <button type="button" class="btn btn-outline-danger eliminarInscripcion-btn" data-id="${asignatura.idAsignatura}" data-cohorte="${cohorte.idCohorte}" data-nombre="${asignatura.nombreMateria}">
                                            <i class="bi bi-trash"></i>
                                        </button>
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
    
    <div class="modal fade" id="confirmarEliminarModal" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p id="confirmarEliminarMensaje"></p>
                    <form id="eliminarInscripcionAsignaturaForm" method="post" action="#">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    </form>
                </div>
                <div class="modal-footer">
                  <button id="eliminarInscripcionBtn" class="btn btn-danger">Eliminar</button>
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    
    
</div>

<%@ include file="../common/footer1.jspf"%>
<script>
  $(document).ready(function() {
    $(".eliminarInscripcion-btn").click(function() {
      var idCohorte = $(this).data("cohorte");
      var idAsignatura = $(this).data("id");
      var nombreAsignatura = $(this).data("nombre");
      
      // Asignar dinámicamente la URL del formulario al botón de eliminar dentro del modal
      $("#eliminarInscripcionBtn").attr("data-url", "/EliminarInscripcionAsignatura/" + idCohorte + "/" + idAsignatura);


      // Modificar el mensaje en el cuerpo del modal
      $("#confirmarEliminarMensaje").text("¿Estás seguro de que deseas eliminar la inscripción de la asignatura "+ nombreAsignatura +" ?");

      // Abrir el modal
      $("#confirmarEliminarModal").modal("show");
    });

    // Manejar el evento de confirmación de eliminación
    $("#eliminarInscripcionBtn").click(function() {
      var url = $(this).attr("data-url");

      // Asignar la URL del formulario y enviarlo
      $("#eliminarInscripcionAsignaturaForm").attr("action", url);
      $("#eliminarInscripcionAsignaturaForm").submit();
    });
  });
</script>


