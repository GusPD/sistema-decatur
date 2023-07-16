<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- Main content -->
<div id="proyectoId" class="hidden" data-id="${proyecto.idProyecto}"></div>
<section class="content pb-5">
    <div>
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
            <sec:authorize access="hasAuthority('AGREGAR_TERRENO_PRIVILAGE')"> 
                <button type="button" class="btn-add btn abrirModal-btn col-sm-1 btn-sm" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar">Agregar</button>
            </sec:authorize>
        </div>
        <div>
            <div class="table-responsive-md">
                <table id="terrenoTable" class="table table-striped">
                    <thead class="table-light">
                        <tr>
                            <th class="text-center">Polígono</th>
                            <th class="text-center">Lote</th>
                            <th class="text-center">Matrícula</th>
                            <th class="text-center">Área (m²)</th>
                            <th class="text-center">Área (v²)</th>
                            <th class="text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div><!-- /.container-fluid -->
</section>

<!-- Modal para Usuarios -->
<div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Agregar Terreno</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formGuardar' accept-charset="UTF-8">
                    <div  class="overflow-auto">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idTerreno">
                        <input type="hidden" id="idProyecto" value="${proyecto.getIdProyecto()}">
                        <div class="form-group">
                            <label for="matricula" class="form-label">Matrícula: </label>
                            <input type="text" class="form-control" id="matricula" name="matricula" placeholder="Ingrese la matrícula del terreno" required>
                        </div>
                        <div class="form-group">
                            <label for="poligono" class="form-label">Polígono: </label>
                            <input type="text" class="form-control" id="poligono" name="poligono" placeholder="Ingrese el polígono del terreno" required>
                        </div>
                        <div class="form-group">
                            <label for="numero" class="form-label">Número de terreno: </label>
                            <input type="text" class="form-control" id="numero" name="numero" placeholder="Ingrese el número del terreno" required>
                        </div>
                        <div class="form-group">
                            <label for="seccion" class="form-label">Sección del terreno: </label>
                            <input type="text" class="form-control" id="seccion" name="seccion" placeholder="Ingrese la sección del terreno">
                        </div>
                        <div class="form-group">
                            <label for="areaMetros" class="form-label">Área (m²): </label>
                            <input type="text" class="form-control" id="areaMetros" name="areaMetros" placeholder="Ingrese el área en metros cuadrados del terreno">
                        </div>
                        <div class="form-group">
                            <label for="areaVaras" class="form-label">Área (v²): </label>
                            <input type="text" class="form-control" id="areaVaras" name="areaVaras" placeholder="Ingrese el área en varas cuadradas del terreno">
                        </div>
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
                <strong>¿Estás seguro de eliminar el terreno seleccionado?</strong>
                <p>Ten en cuenta que se eliminarán los datos relacionados al terreno <span id="poligono"></span>-<span id="numero"></span><span id="seccion"></span>.</p>
            </div>
            <div class="modal-footer">
              <button id="eliminarTerrenoBtn" class="btn btn-outline-danger btn-sm">Eliminar</button>
              <button type="button" class="btn btn-outline-dark btn-sm" data-bs-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>

<form id="eliminarTerrenoForm" method="post" action="/EliminarTerreno/{idTerreno}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>
<!-- /.Modal de eliminar -->