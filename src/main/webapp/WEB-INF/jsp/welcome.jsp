<%@ include file="common/header1.jspf"%>
<%@ include file="common/navigation1.jspf"%>
<div class="content-wrapper">
    <!-- Main content -->
    <section class="content pb-5">
        <div class="container">
            <!--Esto lo estoy manejando en el indexController -->
            <br>
            <h1>Bienvenido ${username}</h1>

            <br>
            <c:if test="${not empty mensaje}">
              <div class="alert alert-success d-flex align-items-center alert-dismissible fade show" role="alert">
                <strong><i class="bi bi-check-circle"></i> ${mensaje}</strong> 
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
              </div>
            </c:if>
            <c:if test="${not empty error}">
              <div class="alert alert-danger d-flex align-items-center alert-dismissible fade show" role="alert">
                <strong><i class="bi bi-exclamation-triangle"></i> Error!</strong> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
              </div>
            </c:if>
            
            
            <c:if test="${not empty maestrias}">
                <div class="card card-index">
                    <h2 class="card-title">Mis Maestrías</h2>
                    <div class="maestrias-grid">
                        <c:forEach var="maestria" items="${maestrias}">
                            <div class="card-index maestria-card text-center">
                                <p class="h-100 m-0">${maestria.nombreMaestria}</p>
                                <div class="button-container">
                                    <a href="/GestionarPlanEstudio/${maestria.idMaestria}" class="btn btn-outline-primary">Asignaturas</a>
                                    <a href="/GestionarCohorte/${maestria.idMaestria}" class="btn btn-outline-primary">Gestión de Cohorte</a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
<%@ include file="common/footer1.jspf"%>