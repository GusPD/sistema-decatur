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
                            <h1>${cohorte.nombreCohorte}</h1>
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
            <div class="card px-4 py-4">
                <div class="row">
                    <div class="col-md-4 text-center">
                        <h5>Fecha de apertura: </h5>
                        <p class="m-0">${cohorte.fechaApertura}</p>
                    </div>
                    <div class="col-md-4 text-center">
                    </div>
                    <div class="col-md-4 text-center">
                        <h5>Estado: </h5>
                        <c:if test="${cohorte.estadoCohorte == '1'}">
                        <p class="m-0">Activa</p>
                        </c:if>
                        <c:if test="${cohorte.estadoCohorte == '0'}">
                        <p class="m-0">Inactiva</p>
                        </c:if>
                    </div>
                </div>
            </div>
            
            <div class="row mt-5">
                <div class="col-md-6">
                    <button class="card links-cohorte w-100 p-3 inscribirMateriaModal-btn" data-id="${cohorte.idCohorte}">
                        <p class="text-center m-0">Inscripción de Materias</p>
                    </button>
                </div>
                <div class="col-md-6">
                    <a href="/AsignaturasInscripcionCohorte/${cohorte.idCohorte}" class="card links-cohorte p-3">
                        <p class="text-center m-0">Gestionar Inscripciones de Materias</p>
                    </a>
                </div>
            </div>
                    
            <div class="row mt-5">
                <div class="col-md-6">
                    <a href="/PostuladosCohorte/${cohorte.idCohorte}" class="card links-cohorte p-3">
                        <p class="text-center m-0">Postulados a Profesor</p>
                    </a>
                </div>
                <div class="col-md-6">
                    <a href="/ProfesorCohorte/${cohorte.idCohorte}" class="card links-cohorte p-3">
                        <p class="text-center m-0">Profesores</p>
                    </a>
                </div>
            </div>
                    
            <div class="row mt-5">
                <div class="col-md-6 offset-3">
                    <a href="/GestionarEstudiantesCohorte/${cohorte.idCohorte}" class="card links-cohorte p-3">
                        <p class="text-center m-0">Estudiantes</p>
                    </a>
                </div>
            </div>
        </div>
    </section>
                        
    <div class="modal fade" id="inscribirMateriaModal" tabindex="-1" aria-labelledby="inscribirMateriaModalLabel" aria-hidden="true" data-tipo="" data-modo=''>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Malla Curricular de la Maestría</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                    <form id="inscribirMateriaForm" action="/InscribirMateria/{idCohorte}" method="post">
                        <div class="modal-body">
                        <input id="csrfToken" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <select id="materias" name="materias[]" class="form-control" size="10" multiple="multiple" required></select>
                        </div>
                        <div class="modal-footer">
                        <button id="inscribirMateriaBtn" class="btn btn-success">Inscribir</button>
                        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancelar</button>
                      </div>
                    </form>
            </div>
        </div>
    </div>
</div>

<script>
var idMaestria = "${cohorte.idMaestria.idMaestria}";
</script>
<%@ include file="../common/footer1.jspf"%>
<script src="${pageContext.request.contextPath}/js/detalleCohorte.js"></script>