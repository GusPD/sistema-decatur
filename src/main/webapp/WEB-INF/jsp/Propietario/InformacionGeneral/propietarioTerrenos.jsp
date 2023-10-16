<%@ include file="../propietario-header.jspf"%>
<input type="hidden" id="idPersona" value="${persona.getIdPersona()}">
<div class="row mb-3">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <div class="subtitulo-page">
                    <h3 class="m-0">Terrenos</h3>
                </div>
            </div>
            <div class="card-body">
                <div id="table_wrapper" class="dataTables_wrapper dt-bootstrap4">
                    <div class="col-sm-12 table-responsive pt-1" style="height: 55vh; padding:4px;">
                        <table id="terrenoTable" class="table table-bordered table-striped dataTable dtr-inline mt-1"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../propietario-footer.jspf"%>
<script src="${pageContext.request.contextPath}/js/terrenoPropietario.js"></script>


      
