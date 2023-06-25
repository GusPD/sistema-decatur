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
                            <h1>Gestionar Aspirantes a Profesor</h1>
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
                    <strong><i class="bi bi-check-circle"></i> Éxito!&nbsp;</strong>${mensaje}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger d-flex align-items-center alert-dismissible fade show" role="alert">
                    <strong><i class="bi bi-exclamation-triangle"></i> Error!&nbsp;</strong>${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <br>
            <sec:authorize access="hasAuthority('AGREGAR_ASPIRANTES_PRIVILAGE')"> 
            <div class="row col-sm-12 d-flex justify-content-end">
                <div class="col-sm-1">
                    <button type="button" class="btn btn-outline-primary btn-sm" data-bs-toggle="modal" data-bs-target="#crearModal">Agregar</button>
                </div>
            </div>
            </sec:authorize>
            <br>
            <div class="row col-sm-12">
                <div class="pt-4">
                    <div class="table-responsive-md">
                        <table id="aspirantesTable" class="table table-striped" style="width:100%">
                            <thead class="table-light">
                                <tr>
                                    <!--<th class="text-center">N°</th>-->
                                    <th class="text-center">Carné</th>
                                    <th class="text-center">Nombre</th>
                                    <th class="text-center">Apellido</th>
                                    <th class="text-center">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div><!-- /.container-->
    </section>
    <!-- Modal para crear aspirantes -->
    <div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel">Agregar Aspirante a Profesor</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="/AgregarAspiranteProfesor" method="post" onsubmit="return validarCorreo()" accept-charset="UTF-8">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <div id="MensajeErrorCorreo" class="d-none">
                            <div class="alert alert-danger d-flex align-items-center alert-dismissible fade show" role="alert">
                                <strong><i class="bi bi-exclamation-triangle"></i> Error!&nbsp;</strong>El formato del correo no es válido.
                                <button id="btnAlertaCorreo" type="button" class="btn-close" aria-label="Close"></button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="codAp" class="form-label">Carné:</label>
                            <input type="text" class="form-control" id="codAp" name="codAp" maxlength="5" required>
                        </div>
                        <div class="form-group">
                            <label for="nombresAp" class="form-label">Nombre:</label>
                            <input type="text" class="form-control" id="nombresAp" name="nombresAp" required>
                        </div>
                        <div class="form-group">
                            <label for="apellidosAp" class="form-label">Apellido:</label>
                            <input type="text" class="form-control" id="apellidosAp" name="apellidosAp" required>
                        </div>
                        <div class="form-group">
                            <label for="correo" class="form-label">Correo:</label>
                            <input type="text" class="form-control" id="correo" name="correo" required>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-outline-success">Guardar</button>
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
                    <strong>¿Estás seguro de eliminar el aspirante seleccionado?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados al aspirante <span id="nombreAspiranteEliminar"></span>.</p>
                    
                </div>
                <div class="modal-footer">
                  <button id="eliminarAspiranteBtn" class="btn btn-danger">Eliminar</button>
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    <form id="eliminarAspiranteForm" method="post" action="/EliminarAspiranteProfesor/{idAspiranteProfesor}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
</div>
<sec:authorize access="hasAuthority('ELIMINAR_ASPIRANTES_PRIVILAGE')" var="hasPrivilegeEliminarApirantes"></sec:authorize>
<script>
    var hasPrivilegeEliminarApirantes = ${hasPrivilegeEliminarApirantes};
</script>
<sec:authorize access="hasAuthority('GESTIONAR_ASPIRANTES_PRIVILAGE')" var="hasPrivilegeGestionarApirantes"></sec:authorize>
<script>
    var hasPrivilegeGestionarApirantes = ${hasPrivilegeGestionarApirantes};
</script>
<%@ include file="../common/footer1.jspf"%>
<script src="${pageContext.request.contextPath}/js/aspiranteProfesor.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        //Alerta de Numero
        var mensajeErrorCorreo = document.getElementById("MensajeErrorCorreo");
        var botonAlertaCorreo=document.getElementById("btnAlertaCorreo");
        botonAlertaCorreo.addEventListener("click", function() {
          mensajeErrorCorreo.classList.add("d-none");
        });
    });
    function validarCorreo() {
        var correoInput = document.getElementById("correo").value;
        var regex = /^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,}$/;
        
        if (!regex.test(correoInput)) {
            var mensajeErrorCorreo = document.getElementById("MensajeErrorCorreo");
            mensajeErrorCorreo.classList.remove("d-none");
            return false;
        }
        return true;
    }
</script>