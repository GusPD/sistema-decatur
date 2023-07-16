<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <div id="proyectoId" class="hidden" data-id="${proyecto.idProyecto}"></div>
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container">
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
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                            <a class="nav-link active menu-proyecto" aria-current="page" href="#" data-action="Proyecto/${proyecto.idProyecto}">Terrenos</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link menu-proyecto" href="#" data-action="Usuarios">Propietarios</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </section>
    <section class="content">
        <div  class="overflow-auto">
            <div class="container">
                <div id="PageContenido"></div>
            </div>
        </div>
    </section>
</div>

<!-- Script de la página -->
<sec:authorize access="hasAuthority('VER_TERRENO_PRIVILAGE')" var="hasPrivilegeVerTerreno"></sec:authorize>
<script>var hasPrivilegeVerTerreno = ${hasPrivilegeVerTerreno};</script>    
        
<sec:authorize access="hasAuthority('EDITAR_TERRENO_PRIVILAGE')" var="hasPrivilegeEditarTerreno"></sec:authorize>
<script>var hasPrivilegeEditarTerreno = ${hasPrivilegeEditarTerreno};</script>

<sec:authorize access="hasAuthority('ELIMINAR_TERRENO_PRIVILAGE')" var="hasPrivilegeEliminarTerreno"></sec:authorize>
<script>var hasPrivilegeEliminarTerreno = ${hasPrivilegeEliminarTerreno};</script>

<%@ include file="../common/footer.jspf"%>

<script>
    $(document).ready(function() {
        var idProyecto = $('#proyectoId').data('id');
        var url = 'Proyecto/'+idProyecto;
        $.ajax({
            url: url,
            type: 'GET',
            success: function(response) {
                $("#PageContenido").html(response);
                var script = document.createElement('script');
                script.src = "${pageContext.request.contextPath}/js/terreno.js";
                document.body.appendChild(script);
            }
        });
    });
    $('.menu-proyecto').on('click', function() {
        event.preventDefault();
        $('.menu-proyecto').removeClass('active');
        $(this).addClass('active');
        var action = $(this).data('action');
        $.ajax({
            url: action,
            type: 'GET',
            success: function(response) {
                $("#PageContenido").html(response);
            }
        });
        if(action==="Proyecto"){
            var script = document.createElement('script');
            script.src = "${pageContext.request.contextPath}/js/terreno.js";
            document.body.appendChild(script);
        }
    });
</script>

