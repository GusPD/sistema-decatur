<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="titulo-Perfil">
                        <div class="container container-titulo">
                            <h1>Usuarios</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section class="content">
        <div  class="overflow-auto">
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
                <div class="row col-sm-12 margenBoton">
                    <div class="col-sm-4 botonExportar"></div>
                    <div class="col-sm-7"></div>
                    <sec:authorize access="hasAuthority('AGREGAR_USUARIO_PRIVILAGE')"> 
                        <button type="button" class="btn-add btn abrirModal-btn col-sm-1 btn-sm" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar">Agregar</button>
                    </sec:authorize>
                </div>
                <div>
                    <div class="table-responsive-md">
                        <table id="usuarioTable" class="table table-striped">
                            <thead class="table-light">
                                <tr>
                                    <th class="text-center">Nombre de Usuario</th>
                                    <th class="text-center">Correo</th>
                                    <th class="text-center">Habilitado</th>
                                    <th class="text-center">Bloqueado</th>
                                    <th class="text-center">Opciones</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div><!-- /.container-fluid -->
        </section>
    </div>
    </section>
    
   <!-- Modal para Usuarios -->
    <div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel">Agregar Usuario</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id='formGuardar' accept-charset="UTF-8">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="UsuarioId">
                        <div class="form-group">
                             <label for="username" class="form-label">Nombre de usuario: </label>
                            <input type="text" class="form-control" id="username" name="username" placeholder="Nombre de Usuario" required>
                        </div>
                        <div class="form-group">
                            <label for="email" class="form-label">Correo Electronico: </label>
                            <input type="text" class="form-control" id="email" name="email" placeholder="Correo" required>
                        </div>
                        <div class="form-group">
                            <label for="password" class="form-label">Contraseña: </label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="Contraseña" required>
                        </div>
                        <input type="hidden" name="numerointentos" value="0">
                        <div class="form-group oculto" hidden>
                            <label for="habilitado" class="form-label">Usuario: </label>
                            <select class="form-control" id="habilitado" name="habilitado" required>
                                <option value="1">Habilitado</option>
                                <option value="0">Deshabilitado</option>
                            </select>
                        </div>
                        <div class="form-group oculto" hidden>
                            <label for="bloqueado" class="form-label">Usuario: </label>
                            <select class="form-control" id="bloqueado" name="bloqueado" required>
                                <option value="0" >Desbloqueado</option>
                                <option value="1" >Bloqueado</option>
                            </select>
                        </div>
                        <div id="roles-error" class="error-message"></div>
                        <div class="form-group">
                            <label for="roles" class="form-label">Selecciona roles: </label>
                            <c:forEach items="${roles}" var="elementoRol" varStatus="status" >
                                <div>
                                    <li>
                                        <input class="checkClean" type="checkbox" id="rol${elementoRol.idRol}" name="roles[]" value="${elementoRol.idRol}">
                                        <label for="rol${elementoRol.idRol}">${elementoRol.nombre}</label>
                                    </li>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-outline-success btn-sm">Guardar</button>
                            <button type="button" class="btn btn-outline-danger btn-sm" data-bs-dismiss="modal">Cancelar</button>
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
                    <strong>¿Estás seguro de eliminar al usuario seleccionado?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados al usuario <span id="username"></span>.</p>
                </div>
                <div class="modal-footer">
                  <button id="eliminarUsuarioBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
                  <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    
    <form id="eliminarUsuarioForm" method="post" action="/EliminarUsuario/{idUsuario}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>                    
    <!-- /.Modal de eliminar -->
</div>
<sec:authorize access="hasAuthority('EDITAR_USUARIO_PRIVILAGE')" var="hasPrivilegeEditarUsuario"></sec:authorize>
<script>var hasPrivilegeEditarUsuario = ${hasPrivilegeEditarUsuario};</script>

<sec:authorize access="hasAuthority('ELIMINAR_USUARIO_PRIVILAGE')" var="hasPrivilegeEliminarUsuario"></sec:authorize>
<script>var hasPrivilegeEliminarUsuario = ${hasPrivilegeEliminarUsuario};</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/usuarios.js"></script>

