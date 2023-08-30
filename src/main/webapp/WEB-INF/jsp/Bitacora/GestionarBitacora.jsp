<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationAdministracion.jspf"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="titulo-Perfil">
                        <div class="container container-titulo">
                            <h1>Bitácora</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section class="content">
        <!-- Main content -->
        <section class="content">
            <div class="container">
                <div class="col-sm-12 d-flex justify-content-end">
                    <div class="botonExportar"></div>
                </div>
                <div class="table-responsive-md table-container">
                    <table id="bitacoraTable" class="table table-striped custom-fixed-header">
                        <thead class="table-light">
                            <tr>
                                <th class="text-center">Nombre de Usuario</th>
                                <th class="text-center">Evento</th>
                                <th class="text-center">Fecha</th>
                                <th class="text-center">Hora</th>
                                <th class="text-center">Ip Equipo</th>
                            </tr>
                        </thead>
                        <tbody >
                        </tbody>
                    </table>
                </div>
            </div><!-- /.container-fluid -->
        </section>
    </section>
<%@ include file="../common/footer.jspf"%>
<script src="${pageContext.request.contextPath}/js/bitacora.js"></script>

