<%@ include file="common/header.jspf"%>
<%@ include file="common/navigationAdministracion.jspf"%>
<div class="content-wrapper">
    <!-- Título de la página -->
    <section class="content-header">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="titulo-page">
                        <div class="container">
                            <h1>Inicio</h1>
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
                        <!-- Funciones de los datos -->
                        <div class="card-header">
                            <!-- <h3 class="card-title d-flex justify-content-end">
                                <sec:authorize access="hasAuthority('EXPORTAR_PROYECTO_PRIVILAGE')"> 
                                    <button id="export-copy" class="btn btn-sm btn-outline-secondary buttons-copy" type="button"><span>Copiar  </span><i class="fa-regular fa-copy"></i></button> 
                                    <button id="export-excel" class="btn btn-sm btn-outline-success buttons-excel ml-2" type="button"><span>Exportar </span><i class="fa-solid fa-file-csv"></i></button> 
                                    <button id="export-pdf" class="btn btn-sm btn-outline-danger buttons-pdf ml-2" type="button"><span>Exportar </span><i class="fa-regular fa-file-pdf"></i></button> 
                                </sec:authorize>
                                <sec:authorize access="hasAuthority('AGREGAR_PROYECTO_PRIVILAGE')"> 
                                    <button type="button" class="btn-blue btn abrirModal-btn btn-sm ml-2" data-bs-toggle="modal" data-bs-target="#crearModal" data-action="agregar"><span>Agregar </span><i class="fa-solid fa-file-pen"></i></button>
                                </sec:authorize> 
                            </h3> -->
                        </div>
                        <!-- Datos -->
                        <div class="card-body">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>                  
</div>
  
<!-- Script de la página -->

<%@ include file="common/footer.jspf"%>