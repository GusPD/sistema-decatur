<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationProyecto.jspf"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/RecursosOnline/style-home-administracion.css">

<div class="content-wrapper">
    <!-- Título de la página -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1>Administración del Proyecto</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section class="content">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <!-- Datos -->
                        <div class="card-body">
                            <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="col-sm-12" style="height: 70vh;">
                                    <div class="row w-100 h-100 p-2">
                                        <div class="col-sm-8">
                                        </div>
                                        <div class="col-sm-4 bg-secondary rounded m-0 p-0">
                                            <div class="main-content">
                                                <div class="illustration">
                                                    <div class="boy"> <img src="${pageContext.request.contextPath}/images/svg/boy.svg" width="90px"/></div>
                                                    <div class="person-type-wrapper">
                                                        <div class="person-type active home-body">
                                                            <div class="scenery"><img src="${pageContext.request.contextPath}/images/svg/indoor.svg" height="250px"/></div>
                                                            <div class="background-items"><img src="${pageContext.request.contextPath}/images/svg/furniture.svg" width="270px"/></div>
                                                            <div class="foreground-items"><img src="${pageContext.request.contextPath}/images/svg/playstation.svg" width="65px"/></div>
                                                        </div>
                                                        <div class="person-type outdoor-person">
                                                            <div class="scenery"><img src="${pageContext.request.contextPath}/images/svg/outdoor.svg" height="250px"/></div>
                                                            <div class="background-items"><img src="${pageContext.request.contextPath}/images/svg/sky.svg" width="220px"/></div>
                                                            <div class="foreground-items"><img src="${pageContext.request.contextPath}/images/svg/dogtree.svg" width="200px"/></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="option-wrapper text-white p-0">
                                                    <div id="mensaje-home" class="option active" data-option="home" style="display: none;">Ahora sí,<br>¡Podemos retomar el trabajo!</div>
                                                    <div id="mensaje-outdoor" class="option" data-option="outdoor">Es necesesario,<br>¡Salir a distraernos un rato!</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>                  
</div>

<!-- Script de la página -->

<%@ include file="../common/footer.jspf"%>

<script src="${pageContext.request.contextPath}/js/RecursosOnline/scripts-home-administracion.js"></script>