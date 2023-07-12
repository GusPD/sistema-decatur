<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12">
                    <div class="titulo-Perfil">
                        <div class="container container-titulo">
                            <h1>Proyecto ${proyecto.nombre} - ${proyecto.empresa}</h1>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link active" id="terrenos-tab" data-bs-toggle="tab" data-bs-target="#terrenos" data-page="TerrenoProyecto" type="button" role="tab" aria-controls="terrenos" aria-selected="true">Terrenos</button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" id="propietarios-tab" data-bs-toggle="tab" data-bs-target="#propietarios" data-page="Uusarios/GestionarUsuarios.jsp" type="button" role="tab" aria-controls="propietarios" aria-selected="false">Propietarios</button>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </section>
    <div>
    <div  class="overflow-auto">
        <section class="content pb-5">
            <div class="container">
                <div class="tab-content" id="myTabContent">
                    <div class="tab-pane fade show active" id="terrenos" role="tabpanel" aria-labelledby="terrenos-tab">
                        <!-- Contenido de la página JSP se cargará aquí -->
                    </div>
                    <div class="tab-pane fade" id="propietarios" role="tabpanel" aria-labelledby="propietarios-tab">
                        <!-- Contenido de la página JSP se cargará aquí -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jspf"%>
<script>
    $(document).ready(function() {
        $('.nav-link').click(function() {
            var target = $(this).attr('data-bs-target');
            var page = $(this).attr('data-page');
            $.ajax({
                url: page,
                method: 'GET',
                success: function(response) {
                    $(target).html(response);
                },
                error: function() {
                    $(target).html('<p>Error al cargar la página.</p>');
                }
            });
        });
    });
</script>

