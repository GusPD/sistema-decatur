<%@ include file="../common/header1.jspf"%>
<%@ include file="../common/navigation1.jspf"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12">
                    <div class="titulo-Perfil">
                        <div class="container">
                            <h1>Postulados</h1>
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

            <br>

            <div class="pt-4">
                <div class="table-responsive">
                    <table id="asignaturaTable" class="table table-striped" >
                        <thead class="thead-light">
                            <tr>
                                <th class="text-center">N</th> 
                                <th class="text-center">Nombres</th> 
                                <th class="text-center">Apellidos</th>
                                <th class="text-center">Dui</th>
                                <th class="text-center">Nacionalidad</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${empty postulados}">
                                <tr>
                                    <td colspan="6">No hay registros</td>
                                </tr>
                            </c:if>
                            <c:if test="${not empty postulados}">
                                <c:forEach items="${postulados}" var="elemento" varStatus="status">
                                    <tr>
                                        <td width="20%">${status.index + 1}</td>
                                        <td>${elemento.nombresAp}</td>
                                        <td>${elemento.apellidosAp}</td>
                                        <td>${elemento.duiAp}</td>
                                        <td>${elemento.nacionalidadAp}</td>
                                        <td class="text-center">
                                            <a href="/PerfilAspiranteProfesor/${elemento.idAspiranteProfesor}" type="button" title="Perfil" class="btn btn-outline-primary">
                                                <i class="bi bi-person"></i>
                                            </a> 
                                            <c:if test="${fn:length(postulados) >= 3}">
                                                <button type="button" title="Contratar" class="btn btn-outline-success abrirModal-btn" data-bs-toggle="modal" 
                                                        data-bs-target="#contratar" data-tipo="editar" data-id="${elemento.idAspiranteProfesor}" 
                                                        data-modo="actualizar">
                                                    <i class="bi bi-person-rolodex"></i>
                                                </button> 
                                            </c:if>
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

    <div class="modal fade" id="contratar" tabindex="-1" aria-labelledby="contratarModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel">Contratar Postulante</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id='formGuardar' accept-charset="UTF-8">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="idList" value="${idPrograma}">
                        <input type="hidden" id="idActividad">
                        <div class="row">
                            <div class="form-group mb-3 col-md-6 col-sm-6">
                                <label>Monto a Pagar por Hora</label>
                                <input type="number" maxlength="9" class="form-control" id="montoPagarHora" name="montoPagarHora">  
                            </div>
                            <div class="form-group mb-3 col-md-6 col-sm-6">
                                <label>Unidad de Horas Impartir</label>
                                <input type="number" maxlength="9"  class="form-control" id="unidadHorasImpartir" name="unidadHorasImpartir">  
                            </div>
                            <div class="form-group mb-3 col-md-6 col-sm-6">
                                <label>Monto Total de Servicios</label>
                                <input disabled type="number" maxlength="9"  class="form-control" id="total">  
                                <input type="hidden" maxlength="9"  class="form-control" id="montoTotalServicios" name="montoTotalServicios">  
                            </div>
                            <div class="form-group mb-3 col-md-6 col-sm-6">
                                <label>Fecha de Contratacion</label>
                                <input type="text" class="form-control" id="fechaContratacion" name="fechaContratacion" placeholder="dd/mm/yyyy">
                            </div>
                            <div class="form-grup mb-3 col-md-6 col-sm-6">
                                <label for="idAreaC">Asignatura</label>
                                <select class="form-select" id="idAsignatura" name="idAsignatura" multiple>
                                    <c:forEach items="${asignaturas}" var="asignatura" varStatus="status">
                                        <option value="${asignatura.idAsignatura}">${asignatura.nombreMateria}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <input type="hidden" class="form-control"  id="idAspiranteProfesor" name="idAspiranteProfesor">  

                            <input type="hidden" class="form-control" value="${idCohorte}" id="idCohorte" name="idCohorte">  

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
</div>
<%@ include file="../common/footer1.jspf"%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.1.4/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.1.4/dist/sweetalert2.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/inputmask/dist/jquery.inputmask.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/profesorCohorte.js"></script>
<script>
    $(document).ready(function() {
        $( '#idAsignatura' ).select2( {
        theme: 'bootstrap-5',
        dropdownParent: $('#contratar')
        } );
    });
</script>
