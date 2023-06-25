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
                    <h1>Cohortes</h1>
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
                    <sec:authorize access="hasAuthority('AGREGAR_COHORTE_PRIVILAGE')"> 
                    <button type="button" class="btn-add btn abrirModal-btn" 
                            data-bs-toggle="modal" data-bs-target="#crearModal" 
                            data-action="agregar">Agregar
                    </button>
                    </sec:authorize>
                </div>
            </div>

            <div class="pt-4">
              <div class="table-responsive-md">
                  <table id="cohorteTable" class="table table-striped" style="width:100%">
                      <thead class="table-light">
                          <tr>
                              <th class="text-center">Nombre Cohorte</th>
                              <th class="text-center">Fecha apertura</th>
                              <th class="text-center">Estado Cohorte</th>
                              <th class="text-center">Acciones</th>
                          </tr>
                      </thead>
                      <tbody class="text-center">
                      </tbody>
                  </table>
              </div>
            </div>
        </div><!-- /.container-fluid -->
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
                        
                        <input type="hidden" id="idCohorte">
                        <input type="hidden" id="idMaestria">
                        
                        <div class="form-group">
                            <label for="nombreCohorte" class="form-label">Nombre Cohorte: </label>
                            <input type="text" class="form-control" id="nombreCohorte" name="nombreCohorte" placeholder="nombre cohorte" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="fechaApertura" class="form-label">Fecha Apertura: </label>
                            <input type="text" class="form-control" id="fechaApertura" name="fechaApertura" placeholder="dd-mm-yyyy" required>
                        </div>
                        
                         <div class="form-group oculto" hidden>
                             <label for="estadoCohorte" class="form-label">Estado Cohorte: </label>
                            <select class="form-control" id="estadoCohorte" name="estadoCohorte" required> 
                                <option value="1">Activo</option>
                                <option value="0">Inactivo</option>
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
                    <strong>¿Estás seguro de eliminar la cohorte seleccionado?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados a la cohorte <span id="nombreCohorte"></span>.</p>
                    
                </div>
                <div class="modal-footer">
                  <button id="eliminarCohorteBtn" class="btn btn-danger">Eliminar</button>
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    
    <form id="eliminarCohorteForm" method="post" action="/EliminarCohorte/{idCohorte}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>                    
    
    <!-- /.Modal de eliminar -->
    
</div>
  <!-- /.content-wrapper -->
  
<script>
    var idMaestria = "${idMaestria}";
</script>

<sec:authorize access="hasAuthority('EDITAR_COHORTE_PRIVILAGE')" var="hasPrivilegeEditarCohorte"></sec:authorize>
<script>
    var hasPrivilegeEditarCohorte = ${hasPrivilegeEditarCohorte};
</script>

<sec:authorize access="hasAuthority('ELIMINAR_COHORTE_PRIVILAGE')" var="hasPrivilegeEliminarCohorte"></sec:authorize>
<script>
    var hasPrivilegeEliminarCohorte = ${hasPrivilegeEliminarCohorte};
</script>


<%@ include file="../common/footer1.jspf"%>
<script src="https://cdn.jsdelivr.net/npm/inputmask/dist/jquery.inputmask.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/cohorte.js"></script>
