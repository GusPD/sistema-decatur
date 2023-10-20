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
                            <h1>Roles</h1>
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
                            <h3 class="card-title d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('EXPORTAR_ROL_PRIVILAGE')"> 
                                    <button id="export-copy" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><span>Copiar  </span><i class="fa-regular fa-copy"></i></button> 
                                    <button id="export-excel" class="btn btn-sm btn-outline-success buttons-excel ml-2" type="button"><span>Exportar </span><i class="fa-solid fa-file-csv"></i></button> 
                                    <button id="export-pdf" class="btn btn-sm btn-outline-danger buttons-pdf ml-2" type="button"><span>Exportar </span><i class="fa-regular fa-file-pdf"></i></button> 
                                </sec:authorize>
                                <sec:authorize access="hasAuthority('AGREGAR_ROL_PRIVILAGE')"> 
                                    <button type="button" class="btn-blue btn abrirModal-btn btn-sm ml-2" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar"><span>Agregar </span><i class="fa-solid fa-file-pen"></i></button>
                                </sec:authorize> 
                            </h3>
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12 table-responsive pt-1" style="height: 60vh; padding:4px;">
                                    <table id="rolesTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
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
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel">Agregar Rol</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id='formGuardar' accept-charset="UTF-8">
                        <div  class="overflow-auto">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="hidden" id="rolId">
                            <div class="form-group">
                                <label for="nombre" class="form-label">Nombre de rol: </label>
                                <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre" required>
                            </div>
                            <div class="form-group">
                                <label for="permisos" class="form-label mb-0">Permisos: </label>
                                <div id="permisos-error" class="error-message mt-0 mb-1"></div>
                                <div class="table-responsive">
                                    <table class="table table-flush-spacing" style="font-size: 13px">
                                        <tbody>
                                            <tr>
                                                <td class="w-65">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        Administrador
                                                    </div>
                                                </td>
                                                <td colspan="7">
                                                    <div class="form-check d-flex align-items-center" style="margin-left: 3.5%;">
                                                        <input class="form-check-input mt-1 mb-0 p-0" type="checkbox" id="selectAll">
                                                        <label class="form-check-label mt-1 mb-0 p-0" for="selectAll">
                                                            Seleccionar Todos
                                                        </label>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="w-65">
                                                    <div class="font-weight-bold">
                                                        Datos
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Mostrar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Seleccionar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Agregar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Actualizar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Eliminar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Exportar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Gestionar
                                                    </div>
                                                </td>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Proyecto"/>
                                            <c:set var="permisoEvaluado1" value="${'VER_PROYECTO'}"/>
                                            <c:set var="permisoEvaluado2" value="${'SELECCIONAR_PROYECTO'}"/>
                                            <c:set var="permisoEvaluado3" value="${'AGREGAR_PROYECTO'}"/>
                                            <c:set var="permisoEvaluado4" value="${'EDITAR_PROYECTO'}"/>
                                            <c:set var="permisoEvaluado5" value="${'ELIMINAR_PROYECTO'}"/>
                                            <c:set var="permisoEvaluado6" value="${'GESTIONAR_PROYECTO'}"/>
                                            <c:set var="permisoEvaluado7" value="${'EXPORTAR_PROYECTO'}"/>
                                            <tr>
                                                <td class="w-70">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado2)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado3)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado4)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado5)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center checkClean">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado6)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado7)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Empresa"/>
                                            <c:set var="permisoEvaluado1" value="${'VER_EMPRESA'}"/>
                                            <c:set var="permisoEvaluado2" value="${'SELECCIONAR_EMPRESA'}"/>
                                            <c:set var="permisoEvaluado3" value="${'AGREGAR_EMPRESA'}"/>
                                            <c:set var="permisoEvaluado4" value="${'EDITAR_EMPRESA'}"/>
                                            <c:set var="permisoEvaluado5" value="${'ELIMINAR_EMPRESA'}"/>
                                            <c:set var="permisoEvaluado6" value="${'GESTIONAR_EMPRESA'}"/>
                                            <c:set var="permisoEvaluado7" value="${'EXPORTAR_EMPRESA'}"/>
                                            <tr>
                                                <td class="w-70">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado2)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado3)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado4)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado5)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center checkClean">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado6)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado7)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Terreno"/>
                                            <c:set var="permisoEvaluado1" value="${'VER_TERRENO'}"/>
                                            <c:set var="permisoEvaluado2" value="${'SELECCIONAR_TERRENO'}"/>
                                            <c:set var="permisoEvaluado3" value="${'AGREGAR_TERRENO'}"/>
                                            <c:set var="permisoEvaluado4" value="${'EDITAR_TERRENO'}"/>
                                            <c:set var="permisoEvaluado5" value="${'ELIMINAR_TERRENO'}"/>
                                            <c:set var="permisoEvaluado6" value="${'GESTIONAR_TERRENO'}"/>
                                            <c:set var="permisoEvaluado7" value="${'EXPORTAR_TERRENO'}"/>
                                            <tr>
                                                <td class="w-70">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado2)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado3)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado4)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado5)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center checkClean">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado6)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado7)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Venta"/>
                                            <c:set var="permisoEvaluado1" value="${'VER_VENTA'}"/>
                                            <c:set var="permisoEvaluado2" value="${'SELECCIONAR_VENTA'}"/>
                                            <c:set var="permisoEvaluado3" value="${'AGREGAR_VENTA'}"/>
                                            <c:set var="permisoEvaluado4" value="${'EDITAR_VENTA'}"/>
                                            <c:set var="permisoEvaluado5" value="${'ELIMINAR_VENTA'}"/>
                                            <c:set var="permisoEvaluado6" value="${'GESTIONAR_VENTA'}"/>
                                            <c:set var="permisoEvaluado7" value="${'EXPORTAR_VENTA'}"/>
                                            <tr>
                                                <td class="w-70">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado2)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado3)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado4)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado5)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center checkClean">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado6)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado7)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Propietario"/>
                                            <c:set var="permisoEvaluado1" value="${'VER_PROPIETARIO'}"/>
                                            <c:set var="permisoEvaluado2" value="${'SELECCIONAR_PROPIETARIO'}"/>
                                            <c:set var="permisoEvaluado3" value="${'AGREGAR_PROPIETARIO'}"/>
                                            <c:set var="permisoEvaluado4" value="${'EDITAR_PROPIETARIO'}"/>
                                            <c:set var="permisoEvaluado5" value="${'ELIMINAR_PROPIETARIO'}"/>
                                            <c:set var="permisoEvaluado6" value="${'GESTIONAR_PROPIETARIO'}"/>
                                            <c:set var="permisoEvaluado7" value="${'EXPORTAR_PROPIETARIO'}"/>
                                            <tr>
                                                <td class="w-70">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado2)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado3)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado4)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado5)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center checkClean">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado6)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado7)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Trabajador"/>
                                            <c:set var="permisoEvaluado1" value="${'VER_TRABAJADOR'}"/>
                                            <c:set var="permisoEvaluado2" value="${'SELECCIONAR_TRABAJADOR'}"/>
                                            <c:set var="permisoEvaluado3" value="${'AGREGAR_TRABAJADOR'}"/>
                                            <c:set var="permisoEvaluado4" value="${'EDITAR_TRABAJADOR'}"/>
                                            <c:set var="permisoEvaluado5" value="${'ELIMINAR_TRABAJADOR'}"/>
                                            <c:set var="permisoEvaluado6" value="${'GESTIONAR_TRABAJADOR'}"/>
                                            <c:set var="permisoEvaluado7" value="${'EXPORTAR_TRABAJADOR'}"/>
                                            <tr>
                                                <td class="w-70">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado2)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado3)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado4)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado5)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center checkClean">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado6)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado7)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </tr>
                                            <tr><td colspan="8"></td><tr>
                                            <tr>
                                                <td colspan="4">
                                                    <div class="font-weight-bold">
                                                        Administración Venta
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Mostrar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Agregar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Editar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Eliminar
                                                    </div>
                                                </td>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Documentos"/>
                                            <c:set var="permisoEvaluado1" value="${'VER_DOCUMENTO_VENTA'}"/>
                                            <c:set var="permisoEvaluado2" value="${'AGREGAR_DOCUMENTO_VENTA'}"/>
                                            <c:set var="permisoEvaluado3" value="${'ELIMINAR_DOCUMENTO_VENTA'}"/>
                                            <tr>
                                                <td colspan="4">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado2)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado3)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Financiamiento"/>
                                            <c:set var="permisoEvaluado1" value="${'EDITAR_INFORMACION_FINANCIAMIENTO'}"/>
                                            <tr>
                                                <td colspan="4">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Mantenimiento"/>
                                            <c:set var="permisoEvaluado1" value="${'EDITAR_INFORMACION_MANTENIMIENTO'}"/>
                                            <tr>
                                                <td colspan="4">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Facturación"/>
                                            <c:set var="permisoEvaluado1" value="${'EDITAR_FACTURACION'}"/>
                                            <tr>
                                                <td colspan="4">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                            </tr>
                                            <tr><td colspan="8"></td><tr>
                                            <tr>
                                                <td colspan="5">
                                                    <div class="font-weight-bold">
                                                        Administración Propietario
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Mostrar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Agregar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Eliminar
                                                    </div>
                                                </td>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Correos"/>
                                            <c:set var="permisoEvaluado1" value="${'AGREGAR_CORREO_PROPIETARIO'}"/>
                                            <c:set var="permisoEvaluado2" value="${'ELIMINAR_CORREO_PROPIETARIO'}"/>
                                            <tr>
                                                <td colspan="6">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado2)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Teléfonos"/>
                                            <c:set var="permisoEvaluado1" value="${'AGREGAR_TELEFONO_PROPIETARIO'}"/>
                                            <c:set var="permisoEvaluado2" value="${'ELIMINAR_TELEFONO_PROPIETARIO'}"/>
                                            <tr>
                                                <td colspan="6">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado2)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Referencias"/>
                                            <c:set var="permisoEvaluado1" value="${'AGREGAR_REFERENCIA_PROPIETARIO'}"/>
                                            <c:set var="permisoEvaluado2" value="${'ELIMINAR_REFERENCIA_PROPIETARIO'}"/>
                                            <tr>
                                                <td colspan="6">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado2)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Documentos"/>
                                            <c:set var="permisoEvaluado1" value="${'VER_DOCUMENTO_PROPIETARIO'}"/>
                                            <c:set var="permisoEvaluado2" value="${'AGREGAR_DOCUMENTO_PROPIETARIO'}"/>
                                            <c:set var="permisoEvaluado3" value="${'ELIMINAR_DOCUMENTO_PROPIETARIO'}"/>
                                            <tr>
                                                <td colspan="5">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado2)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado3)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </tr>
                                            <tr><td colspan="8"></td><tr>
                                            <tr>
                                                <td colspan="5">
                                                    <div class="font-weight-bold">
                                                        Administración Trabajador
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Mostrar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Agregar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Eliminar
                                                    </div>
                                                </td>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Documentos"/>
                                            <c:set var="permisoEvaluado1" value="${'VER_DOCUMENTO_TRABAJADOR'}"/>
                                            <c:set var="permisoEvaluado2" value="${'AGREGAR_DOCUMENTO_TRABAJADOR'}"/>
                                            <c:set var="permisoEvaluado3" value="${'ELIMINAR_DOCUMENTO_TRABAJADOR'}"/>
                                            <tr>
                                                <td colspan="5">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado2)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado3)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </tr>
                                            <tr><td colspan="8"></td><tr>
                                            <tr>
                                                <td colspan="7" class="w-70">
                                                    <div class="font-weight-bold">
                                                        Administración Datos Proyectos
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Mostrar
                                                    </div>
                                                </td>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Propietarios, Trabajadores"/>
                                            <c:set var="permisoEvaluado1" value="${'GESTIONAR_DATOS_PROYECTO'}"/>
                                            <tr>
                                                <td colspan="7">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </td>
                                            <tr><td colspan="8"></td><tr>
                                            <tr>
                                                <td colspan="2" class="w-70">
                                                    <div class="font-weight-bold">
                                                        Seguridad
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Mostrar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Agregar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Actualizar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Eliminar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Exportar
                                                    </div>
                                                </td>
                                                <td class="w-5">
                                                    <div class="d-flex justify-content-center">
                                                        Gestionar
                                                    </div>
                                                </td>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Usuario"/>
                                            <c:set var="permisoEvaluado1" value="${'VER_USUARIO'}"/>
                                            <c:set var="permisoEvaluado2" value="${'AGREGAR_USUARIO'}"/>
                                            <c:set var="permisoEvaluado3" value="${'EDITAR_USUARIO'}"/>
                                            <c:set var="permisoEvaluado4" value="${'ELIMINAR_USUARIO'}"/>
                                            <c:set var="permisoEvaluado5" value="${'GESTIONAR_USUARIO'}"/>
                                            <c:set var="permisoEvaluado6" value="${'EXPORTAR_USUARIO'}"/>
                                            <tr>
                                                <td colspan="2" class="w-70">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado2)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado3)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado4)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado5)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center checkClean">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado6)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Rol"/>
                                            <c:set var="permisoEvaluado1" value="${'AGREGAR_ROL'}"/>
                                            <c:set var="permisoEvaluado2" value="${'EDITAR_ROL'}"/>
                                            <c:set var="permisoEvaluado3" value="${'ELIMINAR_ROL'}"/>
                                            <c:set var="permisoEvaluado4" value="${'GESTIONAR_ROL'}"/>
                                            <c:set var="permisoEvaluado5" value="${'EXPORTAR_ROL'}"/>
                                            <tr>
                                                <td colspan="3" class="w-75">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado2)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado3)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado4)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado5)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center checkClean">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </tr>
                                            <c:set var="nombrePermisoLista" value="Bitácora"/>
                                            <c:set var="permisoEvaluado1" value="${'GESTIONAR_BITACORA'}"/>
                                            <c:set var="permisoEvaluado2" value="${'EXPORTAR_BITACORA'}"/>
                                            <tr>
                                                <td colspan="6" class="w-90">
                                                    <div class="mt-1 mb-0 p-0 d-flex align-items-center">
                                                        <c:out value="${nombrePermisoLista}"/>
                                                    </div>
                                                </td>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado1)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                                <c:set var="permisoEncontrado" value="0" />
                                                <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                                                    <c:if test="${fn:containsIgnoreCase(elementoPermiso.nombre, permisoEvaluado2)}">
                                                        <td class="w-5">
                                                            <div class="d-flex justify-content-center">
                                                                <div class="form-check">
                                                                    <input class="form-check-input checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <c:set var="permisoEncontrado" value="1" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${permisoEncontrado==0}">
                                                    <td class="w-5"><div class="d-flex justify-content-center"><div class="form-check"></div></div></td>
                                                </c:if>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button id='btnSumit' type="submit" class="btn btn-outline-success btn-sm">Guardar</button>
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
                    <strong>¿Estás seguro de eliminar al rol seleccionado?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados al rol.</p>
                </div>
                <div class="modal-footer">
                  <button id="eliminarRolBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
                  <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    <form id="eliminarRolForm" method="post" action="/EliminarRol/{idRol}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
</div>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('EDITAR_ROL_PRIVILAGE')" var="hasPrivilegeEditarRol"></sec:authorize>
<script>var hasPrivilegeEditarRol = ${hasPrivilegeEditarRol};</script>

<sec:authorize access="hasAuthority('ELIMINAR_ROL_PRIVILAGE')" var="hasPrivilegeEliminarRol"></sec:authorize>
<script>var hasPrivilegeEliminarRol = ${hasPrivilegeEliminarRol};</script>    

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/roles.js"></script>

