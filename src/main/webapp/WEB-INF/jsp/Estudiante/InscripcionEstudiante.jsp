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
                            <h1>Seleccione los estudiantes a inscribir</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Main content -->
    <section class="content pb-5">
        <div class="container">
            <form action="/guardarInscripcion" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="hidden" name="idCohorte" value="${cohorte.idCohorte}">
                <div class="form-group">
                    <select name="estudiantes" id="estudiantes" class="form-control" multiple>
                        <c:forEach items="${estudiantes}" var="estudiante">
                            <option value="${estudiante.idEstudiante}">${estudiante.nombresE} ${estudiante.apellidosE}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Guardar</button>
                <a href="/InscribirMaestria" class="btn btn-danger">Cancelar</a>
            </form>
        </div><!-- /.container-fluid -->
    </section>
</div>

<%@ include file="../common/footer1.jspf"%>
<script>
    $(document).ready(function() {
        $( '#estudiantes' ).select2( {
        theme: 'bootstrap-5'
        } );
    });
</script>