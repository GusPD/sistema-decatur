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
                    <h1>Coordinadores Académicos</h1>
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
                  <sec:authorize access="hasAuthority('AGREGAR_COORDINADORES_PRIVILAGE')">
                    <button type="button" class="btn-add btn" data-bs-toggle="modal" data-bs-target="#crearModal">Agregar</button>
                  </sec:authorize>
              </div>
            </div>

            <div class="pt-4">
                <div class="table-responsive-md">
                    <table id="coordinadoresTable" class="table table-striped" style="width:100%">
                        <thead class="table-light">
                            <tr>
                              <th>Número de identificación</th>
                              <th>Nombres</th>
                              <th>Apellidos</th>
                              <th>Opciones</th>
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
    
        <!-- Modal para crear coordinadores -->
   <div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="crearModalLabel">Agregar Coordinador Académico</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form action="/guardarCA" method="post" accept-charset="UTF-8">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
              <div class="form-group">
                <label for="codCa">Carnet de identificación:</label>
                <input type="text" class="form-control" id="codCa" name="codCa" maxlength="6" required>
              </div>
              <div class="form-group">
                <label for="nombresCa">Nombre:</label>
                <input type="text" class="form-control" id="nombresCa" name="nombresCa" required>
              </div>
              <div class="form-group">
                <label for="apellidosCa">Apellido:</label>
                <input type="text" class="form-control" id="apellidosCa" name="apellidosCa" required>
              </div>
              <div class="form-group">
                <label for="email">Correo:</label>
                <input type="text" class="form-control" id="email" name="email" required>
              </div>              
              <div class="modal-footer">
                <button type="submit" class="btn btn-outline-success">Guardar</button>
                <button type="submit" class="btn btn-outline-danger" data-bs-dismiss="modal">Cancelar</button>
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
                    <strong>¿Estás seguro de eliminar este coordinador?</strong>
                    <p>Ten en cuenta que se eliminarán todos los datos relacionados a <span id="nombresCaEliminar"></span>.</p>
                    
                </div>
                <div class="modal-footer">
                  <button id="eliminarCoordinadorBtn" class="btn btn-danger">Eliminar</button>
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    
    <form id="eliminarCoordinadorForm" method="post" action="/eliminarCoordinadorAcademico/{idCoorAca}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
    <!-- /.Modal de eliminar -->
</div>
<sec:authorize access="hasAuthority('GESTIONAR_COORDINADORES_PRIVILAGE')" var="hasPrivilegeGestionarCoordinadores"></sec:authorize>
<script>
    var hasPrivilegeGestionarCoordinadores = ${hasPrivilegeGestionarCoordinadores};
</script>
<sec:authorize access="hasAuthority('ELIMINAR_COORDINADORES_PRIVILAGE')" var="hasPrivilegeEliminarCoordinadores"></sec:authorize>
<script>
    var hasPrivilegeEliminarCoordinadores = ${hasPrivilegeEliminarCoordinadores};
</script>
<!-- /.content-wrapper -->
<%@ include file="../common/footer1.jspf"%>
<script src="${pageContext.request.contextPath}/js/coordinador.js"></script>





