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
                            <h1>Mis Postulaciones a Cohorte</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Main content -->
    <section class="content pb-5">
        <div class="container">
            <div class="alert alert-success d-flex align-items-center alert-dismissible fade d-none" role="alert">
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                <strong><i class="bi bi-check-circle"></i> Éxito!&nbsp;</strong>
            </div>
            <div class="alert alert-danger d-flex align-items-center alert-dismissible fade d-none" role="alert">
                <strong><i class="bi bi-exclamation-triangle"></i> Error!&nbsp;</strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <div class="pt-4">
                <div class="table-responsive-md">
                    <table id="maestriasTable" class="table table-striped" style="width:100%">
                        <thead class="table-light">
                            <tr>
                                <th class="text-center">Maestria</th>
                                <th class="text-center">Nombre Cohorte</th>
                                <th class="text-center">Escuela Posgrado</th>
                                <th class="text-center">Facultad</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${postulados}" var="elemento" varStatus="status">
                                <tr> 
                                    <td class="text-center">${elemento.idCohorte.idMaestria.nombreMaestria}</td>
                                    <td class="text-center">${elemento.idCohorte.nombreCohorte}</td>
                                    <td class="text-center">${elemento.idCohorte.idMaestria.idPostgrado.nombrePostgrado}</td>
                                    <td class="text-center">${elemento.idCohorte.idMaestria.idPostgrado.idFacultad.nombreFacultad}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div><!-- /.container-->
    </section>
    <!-- /.Main content -->
</div>
<%@ include file="../common/footer1.jspf"%>