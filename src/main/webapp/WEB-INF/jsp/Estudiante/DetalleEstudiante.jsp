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
                            <h1>Detalle del Estudiante</h1>
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
          
            <div class="card">
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <h5>Carnet:</h5>
                            <p>${estudiante.carnetE}</p>
                            
                            <h5>Nombres:</h5>
                            <p>${estudiante.nombresE}</p>
                            
                            <h5>Apellidos:</h5>
                            <p>${estudiante.apellidosE}</p>
                            
                            <h5>Sexo:</h5>
                            <p>${estudiante.sexoE}</p>
                            
                            <h5>Fecha de Nacimiento:</h5>
                            <p>${estudiante.fechaNacE}</p>
                            
                            <h5>Nacionalidad:</h5>
                            <p>${estudiante.nacionalidadE}</p>
                        </div>
                        <div class="col-md-6">
                            <h5>
                                <c:choose>
                                    <c:when test="${estudiante.idPais.nombrePais == 'El Salvador'}">
                                        DUI:
                                    </c:when>
                                    <c:otherwise>
                                        Documento Personal:
                                    </c:otherwise>
                                </c:choose>
                            </h5>
                            <p>
                                <c:choose>
                                    <c:when test="${estudiante.idPais.nombrePais == 'El Salvador'}">
                                        ${estudiante.duiE}
                                    </c:when>
                                    <c:otherwise>
                                        ${estudiante.docPersonalE}
                                    </c:otherwise>
                                </c:choose>
                            </p>
                            
                            <c:if test="${estudiante.idPais.nombrePais == 'El Salvador'}">
                                <h5>NIT:</h5>
                                <p>
                                    <c:if test="${empty estudiante.nitE}">
                                        No posee documento registrado
                                    </c:if>
                                    <c:if test="${not empty estudiante.nitE}">
                                        ${estudiante.nitE}
                                    </c:if>
                                </p>

                                <h5>NUP:</h5>
                                <p>
                                    <c:if test="${empty estudiante.nupE}">
                                        No posee documento registrado
                                    </c:if>
                                    <c:if test="${not empty estudiante.nupE}">
                                        ${estudiante.nupE}
                                    </c:if>
                                </p>
                            </c:if>

                            <h5>Correo:</h5>
                            <p>${estudiante.correoE}</p>

                            <h5>País:</h5>
                            <p>${estudiante.idPais.nombrePais}</p>
                            
                            <h5>Pasaporte:</h5>
                            <p>
                                <c:if test="${empty estudiante.pasaporteE}">
                                    No posee documento registrado
                                </c:if>
                                <c:if test="${not empty estudiante.pasaporteE}">
                                    ${estudiante.pasaporteE}
                                </c:if>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<%@ include file="../common/footer1.jspf"%>

