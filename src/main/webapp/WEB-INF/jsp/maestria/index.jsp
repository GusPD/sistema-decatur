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
                    <h1>Maestrias</h1>
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
                    <sec:authorize access="hasAuthority('AGREGAR_MAESTRIA_PRIVILAGE')">
                    <button type="button" class="btn-add btn abrirModal-btn" 
                            data-bs-toggle="modal" data-bs-target="#crearModal" 
                            data-action="agregar">Agregar
                    </button>
                    </sec:authorize>
                </div>
            </div>

            <div class="pt-4">
                <div class="table-responsive-md">
                    <table id="maestriasTable" class="table table-striped" style="width:100%">
                        <thead class="table-light">
                            <tr>
                                <th class="text-center">Nombre</th>
                                <th class="text-center">Escuela de Posgrado</th>
                                <th class="text-center">Facultad</th>
                                <th class="text-center">Acciones</th>
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

    <!-- Modal de agregar y editar -->
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
                        <input type="hidden" id="idMaestria">
                        <div class="form-group">
                            <label for="nombreMaestria" class="form-label">Nombre de la maestria</label>
                            <input type="text" class="form-control" id="nombreMaestria" name="nombreMaestria" required>
                        </div>
                        <div class="form-group">
                            <label for="idPostgrado" class="form-label">Escuela de Posgrado</label>
                              <select class="form-select form-select-sm" id="idPostgrado" name="idPostgrado" required>
                                  <option value="">Seleccione una escuela de postgrado</option>
                                  <c:forEach items="${escuelas}" var="elementoEscuela" varStatus="status">
                                      <option value="${elementoEscuela.idPostgrado}">${elementoEscuela.nombrePostgrado}</option>
                                  </c:forEach>
                              </select>
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
                    <strong>¿Estás seguro de eliminar la maestría seleccionada?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados a la maestría de <span id="nombreMaestriaEliminar"></span>.</p>
                    
                </div>
                <div class="modal-footer">
                  <button id="eliminarMaestriaBtn" class="btn btn-danger">Eliminar</button>
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    
    <form id="eliminarMaestriaForm" method="post" action="/EliminarMaestria/{idMaestria}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
    <!-- /.Modal de eliminar -->
</div>
<!-- /.content-wrapper -->
  
<sec:authorize access="hasAuthority('ACCEDER_MAESTRIA_PRIVILAGE')" var="hasPrivilegeAccederMaestria"></sec:authorize>
<sec:authorize access="hasAuthority('ELIMINAR_MAESTRIA_PRIVILAGE')" var="hasPrivilegeEliminarMaestria"></sec:authorize>
<sec:authorize access="hasAuthority('EDITAR_MAESTRIA_PRIVILAGE')" var="hasPrivilegeEditarMaestria"></sec:authorize>
<script>
    var hasPrivilegeAccederMaestria = ${hasPrivilegeAccederMaestria};
    var hasPrivilegeEliminarMaestria = ${hasPrivilegeEliminarMaestria};
    var hasPrivilegeEditarMaestria = ${hasPrivilegeEditarMaestria};
</script>
<%@ include file="../common/footer1.jspf"%>
<script src="${pageContext.request.contextPath}/js/maestria.js"></script>

