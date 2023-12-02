<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationAdministracion.jspf"%>
<div class="content-wrapper">
    <!-- Título de la página -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1>Usuarios</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section class="content">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <!-- Funciones de los datos -->
                        <div class="card-header">
                            <h3 class="card-title d-flex justify-content-between">
                                <div class="d-flex justify-content-estart">
                                    <sec:authorize access="hasAuthority('EXPORTAR_USUARIO_PRIVILAGE')">
                                        <button id="export-copy" title="Copiar" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><i class="fa-regular fa-copy"></i></button> 
                                        <button id="export-excel" title="Exportar Excel" class="btn btn-sm btn-outline-success buttons-excel ml-2" type="button"><i class="fa-solid fa-file-excel"></i></button> 
                                        <button id="export-pdf" title="Exportar PDF" class="btn btn-sm btn-outline-danger buttons-pdf ml-2" type="button"><i class="fa-regular fa-file-pdf"></i></button>
                                    </sec:authorize>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <sec:authorize access="hasAuthority('AGREGAR_USUARIO_PRIVILAGE')"> 
                                        <button type="button" title="Agregar Usuario" class="btn-blue btn-sm btn abrirModal-btn ml-2" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar"><i class="fa-solid fa-file-pen"></i></button>
                                    </sec:authorize> 
                                </div>
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 60vh; padding:4px;">
                                    <table id="usuarioTable" class="table table-bordered table-striped text-center dataTable dtr-inline mt-1"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
   <!-- Modal de agregar -->
    <div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel">Agregar Usuario</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form id='formGuardar' accept-charset="UTF-8">
                    <div class="modal-body">
                        <div  class="overflow-auto">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="hidden" id="UsuarioId">
                            <div class="form-group">
                                <label for="nombre" class="form-label">Nombre:<strong class="text-danger"> *</strong></label>
                               <input type="text" class="form-control form-control-sm" id="nombre" name="nombre" placeholder="Nombre" required>
                           </div>
                            <div class="form-group">
                                 <label for="username" class="form-label">Usuario:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control form-control-sm" id="username" name="username" placeholder="Usuario" required>
                            </div>
                            <div class="form-group">
                                <label for="email" class="form-label">Correo Electrónico:<strong class="text-danger"> *</strong></label>
                                <input type="text" class="form-control form-control-sm" id="email" name="email" placeholder="Correo" required>
                            </div>
                            <div class="form-group">
                                <label for="password" class="form-label">Contraseña:<strong class="text-danger"> *</strong></label>
                                <input type="password" class="form-control form-control-sm" id="password" name="password" placeholder="Contraseña" required>
                            </div>
                            <input type="hidden" name="numerointentos" value="0">
                            <div class="form-group oculto" hidden>
                                <label for="habilitado" class="form-label">Usuario:<strong class="text-danger"> *</strong></label>
                                <select class="form-select form-select-sm" id="habilitado" name="habilitado" required>
                                    <option value="1">Habilitado</option>
                                    <option value="0">Deshabilitado</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="roles" class="form-label">Seleccione los roles:<strong class="text-danger"> *</strong></label>
                                <select class="form-select form-select-sm" id="roles" name="roles" placeholder="Seleccione una opción" required>
                                    <option value="">Seleccione una opción</option>
                                    <c:if test="${not empty roles}">
                                        <c:forEach items="${roles}" var="eRol">
                                            <option value="${eRol.idRol}">${eRol.nombre}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="proyectos" class="form-label">Seleccione los proyectos: </label>
                                <div id="proyectos-error" name="proyectos-error" class="error-message mt-0 mb-1"></div>
                                <c:forEach items="${proyectos}" var="elementoProyecto" varStatus="status" >
                                    <div>
                                        <li>
                                            <input class="checkClean" type="checkbox" id="proyecto${elementoProyecto.idProyecto}" name="proyectos[]" value="${elementoProyecto.idProyecto}">
                                            <label for="proyecto${elementoProyecto.idProyecto}">${elementoProyecto.nombre}</label>
                                        </li>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="form-group">
                                <label for="empresas" class="form-label">Seleccione las empresas: </label>
                                <div id="empresas-error" name="empresas-error" class="error-message mt-0 mb-1"></div>
                                <c:forEach items="${empresas}" var="elementoEmpresa" varStatus="status" >
                                    <div>
                                        <li>
                                            <input class="checkClean" type="checkbox" id="empresa${elementoEmpresa.idEmpresa}" name="empresas[]" value="${elementoEmpresa.idEmpresa}">
                                            <label for="empresa${elementoEmpresa.idEmpresa}">${elementoEmpresa.nombre}</label>
                                        </li>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer d-flex justify-content-between">
                        <label for="monto" class="form-label text-danger mensaje-obligatorios">(*) Campos Obligatorios</label>
                        <div>
                            <button type="submit" class="btn btn-outline-success btn-sm">Guardar</button>
                            <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </form>
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
                    <p>Ten en cuenta que se eliminarán los datos relacionados al usuario.</p>
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
</div>
        
<!-- Script de la página -->
<sec:authorize access="hasAuthority('EDITAR_USUARIO_PRIVILAGE')" var="hasPrivilegeEditarUsuario"></sec:authorize>
<script>var hasPrivilegeEditarUsuario = <c:out value='${hasPrivilegeEditarUsuario}' />;</script>

<sec:authorize access="hasAuthority('ELIMINAR_USUARIO_PRIVILAGE')" var="hasPrivilegeEliminarUsuario"></sec:authorize>
<script>var hasPrivilegeEliminarUsuario = <c:out value='${hasPrivilegeEliminarUsuario}' />;</script>

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/Usuario/Usuarios.js"></script>

