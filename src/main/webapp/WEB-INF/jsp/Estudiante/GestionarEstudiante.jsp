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
                            <h1>Estudiantes</h1>
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
          
            <div class="alert alert-success d-flex align-items-center alert-dismissible fade d-none" role="alert">
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                <strong><i class="bi bi-check-circle"></i> Éxito!&nbsp;</strong>
            </div>
            <div class="alert alert-danger d-flex align-items-center alert-dismissible fade d-none" role="alert">
                <strong><i class="bi bi-exclamation-triangle"></i> Error!&nbsp;</strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <div class="row col-sm-12 d-flex justify-content-end">
                <div class="col-sm-1">
                     <sec:authorize access="hasAuthority('AGREGAR_ESTUDIANTE_PRIVILAGE')"> 
                    <button type="button" class="btn-add btn abrirModal-btn" 
                            data-bs-toggle="modal" data-bs-target="#crearModal" 
                            data-action="agregar" onclick="cargarSelect()" >Agregar</button>
                    </sec:authorize>
                </div>
            </div>

            <div class="pt-4">
                <div class="table-responsive-md">
                    <table id="estudianteTable" class="table table-striped" style="width:100%">
                        <thead class="table-light">
                            <tr>
                                <th class="text-center">Carnet</th>
                                <th class="text-center">Nombre</th>
                                <th class="text-center">Apellidos</th>
                                <th class="text-center">Correo</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->

    <!-- Modal de agregar y editar -->
    <div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true" data-tipo="" data-modo=''>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel"></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id='formGuardar' accept-charset="UTF-8" >

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">


                        <input type="hidden" id="estudianteId">

                        <div class="form-group">
                            <label for="carnetE" class="form-label">Carnet:</label>
                            <input type="text" class="form-control" id="carnetE" name="carnetE" maxlength="5" placeholder="Carnet" required>
                        </div>

                        <div class="form-group">
                            <label for="nombresE" class="form-label">Nombres:</label>
                            <input type="text" class="form-control" id="nombresE" name="nombresE" placeholder="Nombres Estudiante" required>
                        </div>


                        <div class="form-group">
                            <label for="apellidosE" class="form-label">Apellidos:</label>
                            <input type="text" class="form-control" id="apellidosE" name="apellidosE" placeholder="Apellidos Estudiante" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="correoE" class="form-label">Correo: </label>
                            <input type="text" class="form-control" id="correoE" name="correoE" placeholder="Correo" required>
                        </div>

                        <div class="form-group">
                            <label for="sexoE" class="form-label">Sexo:</label>
                            <select type="text" class="form-select" id="sexoE" name="sexoE" required>
                                <option value="">Seleccione una opción</option>
                                <c:forEach items="${sexos}" var="eSexo" varStatus="status">
                                    <option value="${eSexo}">${eSexo}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="fechaNacE" class="form-label">Fecha Nacimiento:</label>
                            <input type="text" class="form-control" id="fechaNacE" name="fechaNacE" placeholder="dd-mm-yyyy" maxlength="10"/>
                        </div>

                        <div class="form-group">
                            <label for="nacionalidadE" class="form-label">Nacionalidad:</label>
                            <select class="form-select" id="nacionalidadE" name="nacionalidadE" required>
                                <option value="">Seleccione una opción</option>
                                <c:forEach items="${nacionalidades}" var="eNacionalidad">
                                    <option value="${eNacionalidad}">${eNacionalidad}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="" class="form-label">Pais:</label>
                            <select class="form-control" id="idPais" name="idPais" onchange="mostrarOcultarSelect()" required>
                                <option value="">Seleccione una opción</option>
                                <c:forEach items="${pais}" var="elementoPais">
                                    <option value="${elementoPais.idPais}">${elementoPais.nombrePais}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div id="group-duiE" class="form-group">
                            <label for="duiE" class="form-label">DUI:</label>
                            <input type="text" class="form-control" id="duiE" name="duiE" maxlength="10" placeholder="########-#" >
                        </div>


                        <div id="group-nitE" class="form-group">
                            <label for="nitE" class="form-label">NIT:</label>
                            <input type="text" class="form-control" id="nitE" name="nitE" maxlength="17" placeholder="#### - #### - ### - #" >
                        </div>

                        <div id="group-nupE" class="form-group">
                            <label for="nupE" class="form-label">NUP:</label>
                            <input type="text" class="form-control" id="nupE" name="nupE" maxlength="14" placeholder="############">
                        </div>

                        <div id="group-pasaporteE" class="form-group">
                            <label for="pasaporteE" class="form-label">Pasaporte:</label>
                            <input type="text" class="form-control" id="pasaporteE" name="pasaporteE" maxlength="9" placeholder="*********" >
                        </div>

                        <div id="group-docPersonalE" class="form-group">
                            <label for="docPersonalE" class="form-label">Documento Personal:</label>
                            <input type="text" class="form-control" id="docPersonalE" name="docPersonalE" maxlength="20" placeholder="####################">
                        </div>

                        <br>
                        <div class="modal-footer">
                            <button id='btnSumit' type="submit" class="btn btn-outline-success guardar-btn">Guardar</button>
                            <button type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">Cancelar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>

<!-- Modal de eliminar -->
    <div class="modal fade" id="confirmarEliminarModal" tabindex="-1" aria-labelledby="confirmarEliminarLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmarEliminarLabel">Confirmar eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <strong>¿Estás seguro de eliminar al estudiante seleccionado?</strong>
                    <p>Ten en cuenta que se eliminarán los datos relacionados al estudiante <span id="nombreE"></span>.</p>
                    
                </div>
                <div class="modal-footer">
                  <button id="eliminarEstudianteBtn" class="btn btn-danger">Eliminar</button>
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    
    <form id="eliminarEstudianteForm" method="post" action="/EliminarEstudiante/{idEstudiante}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>                                            

<sec:authorize access="hasAuthority('EDITAR_ESTUDIANTE_PRIVILAGE')" var="hasPrivilegeEditarEstudiante"></sec:authorize>
<script>
    var hasPrivilegeEditarEstudiante = ${hasPrivilegeEditarEstudiante};
</script>

<sec:authorize access="hasAuthority('ELIMINAR_ESTUDIANTE_PRIVILAGE')" var="hasPrivilegeEliminarEstudiante"></sec:authorize>
<script>
    var hasPrivilegeEliminarEstudiante = ${hasPrivilegeEliminarEstudiante};
</script>    
    

<%@ include file="../common/footer1.jspf"%>

<script src="${pageContext.request.contextPath}/js/estudiantes.js"></script>

<script src="https://cdn.jsdelivr.net/npm/inputmask/dist/jquery.inputmask.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/estudianteSelect.js"></script>

<script>
    $(document).ready(function () {
    
    //Expresiones regulares para validar campos con formatos especificos
    
    //Correo
    $.validator.addMethod(
    "validarCorreo",
    function(value, element) {
        return this.optional(element) || /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(value);
    },
        "Ingresa una dirección de correo electrónico válida"
    );
    
    //Carnet
    $.validator.addMethod(
    "validarCarnet",
    function(value, element) {
        return this.optional(element) || /^[a-zA-Z0-9]{5}$/.test(value);
    },
        "Ingresa un carnet valido"
    );
    
    //Carnet
    $.validator.addMethod(
    "validarDui",
    function(value, element) {
        return this.optional(element) || /^0\d{7}-\d$/.test(value);
    },
        "Ingresa un dui valido"
    );
    
    //nit
    $.validator.addMethod(
    "validarNit",
    function(value, element) {
        return this.optional(element) || /^(\d+-){3}\d+$/.test(value);
    },
        "Ingresa un nit valido"
    );
    
    //fecha
    $.validator.addMethod(
    "validarFecha",
    function(value, element) {
        return this.optional(element) || /^(0[1-9]|1\d|2\d|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/.test(value);
    },
    "Ingresa una fecha en formato DD/MM/AAAA"
    );
    
    //fecha
    $.validator.addMethod(
    "validarNup",
    function(value, element) {
        return this.optional(element) || /^\d{12}$/.test(value);
    },
        "Ingresa un nup valido"
    );
    
        //Validacion
        $('#formGuardar').validate({
        rules: {
            carnetE: {
                required: true,
                validarCarnet: true
            },
            nombresE: {
                required: true
            },
            apellidosE : {
               required: true
            },
            correoE: {
                required: true,
                validarCorreo: true
            },
            sexoE :{
               required: true   
            },
            fechaNacE:{
               required: true,
               validarFecha: true
            },
            nacionalidadE:{
              required: true
            },
            idPais:{
              required: true
            },
            duiE:{
              validarDui: true
            },
            nitE: {
              validarNit: true
            },
            nupE :{
              validarNup: true
            }
           
        },
        messages: {
            carnetE: {
                required: 'Este campo es requerido',
                validarCarnet: 'Ingresa un carnet valido'
            },
            nombresE: {
                required: 'Este campo es requerido'
            },
            apellidosE : {
               required: 'Este campo es requerido'
            },
            correoE: {
                required: 'Este campo es requerido',
                validarCorreo: 'Ingresa una dirección de correo electrónico válida'
            },
            sexoE :{
               required: 'Este campo es requerido'   
            },
            fechaNacE:{
               required: 'Este campo es requerido',
               validarFecha: 'Ingrese una fecha valida en formato dd/mm/yyyy'
            },
            nacionalidadE:{
              required: 'Este campo es requerido'
            },
            idPais:{
              required: 'Este campo es requerido'
            },
            duiE:{
               required: 'Este campo es requerido',
               validarDui: 'Ingresa un DUI valido'
            },
            nitE: {
               required: 'Este campo es requerido',
               validarNit: 'Ingrese un NIT valido'
            },
            nupE :{
              required: 'Este campo es requerido',
              validarNup: 'Ingrese un NUP valido'
            },
            pasaporteE:{
                required: 'Este campo es requerido'
            },
            docPersonalE:{
                required: 'Este campo es requerido'
            }
            
            
        },
        
        highlight: function(element) {
            $(element).addClass('is-invalid');
        },
        
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
        },
        
        errorPlacement: function(error, element) {
            if (element.attr("name") === "carnetE" || 
                element.attr("name") === "nombresE" ||
                element.attr("name") === "apellidosE" ||
                element.attr("name") === "correoE" ||
                element.attr("name") === "sexoE" ||
                element.attr("name") === "fechaNacE" ||
                element.attr("name") === "nacionalidadE" ||
                element.attr("name") === "idPais" ||
                element.attr("name") === "duiE" ||
                element.attr("name") === "nitE" ||
                element.attr("name") === "nupE" ||
                element.attr("name") === "pasaporteE" ||
                element.attr("name") === "docPersonalE") {
                error.insertAfter(element);
              }
         },
        
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        
        
        submitHandler: function (form) {
            event.preventDefault();
            var idEstudiante = $('#estudianteId').val();
            var formDataArray = $(form).serializeArray();
            var url;

            if (idEstudiante) {
                url = '/ActualizarEstudiante';
                formDataArray.push({ name: 'idEstudiante', value: idEstudiante });
            } else {
                url = '/AgregarEstudiante';
            }

            var formData = {};
            $.map(formDataArray, function (n, i) {
                formData[n['name']] = n['value'];
            });
            
            // Eliminar guion del valor de duiE
            formData.duiE = formData.duiE.replace("-", "");
             // Eliminar guion del valor de nitE
            formData.nitE = formData.nitE.replace(/-/g, "");
            
            //console.log(formData.nitE);

            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                success: function (response) {
                    $('#crearModal').modal('hide');
                    var table = $('#estudianteTable').DataTable();
                    table.ajax.reload(null, false);
                    mostrarMensaje(response, 'success');
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar el estudiante.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });
       
       // metodo para mostrar el modal segun sea si editar o nuevo registro
       $(document).on('click', '.abrirModal-btn', function () {
        var idEstudiante = $(this).data('id');
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        var btnSumit = document.getElementById('btnSumit');
        
        // Restablecer las validaciones
        form.validate().resetForm();

        // Restablecer los estilos de error
        form.find('.is-invalid').removeClass('is-invalid');

        var docPersonal = document.getElementById("group-docPersonalE");
        var pasaporte = document.getElementById("group-pasaporteE");
        var dui = document.getElementById("group-duiE");
        var nit = document.getElementById("group-nitE");
        var nup = document.getElementById("group-nupE");


        if (idEstudiante) {
            tituloModal.text('Editar Estudiante');//titulo del modal
            $.ajax({//utilizo ajax para obtener los datos
                url: '/ObtenerEstudiante/' + idEstudiante,
                type: 'GET',
            success: function (response) {
                $('#carnetE').val(response.carnetE);
                $('#nombresE').val(response.nombresE);
                $('#apellidosE').val(response.apellidosE);
                $('#correoE').val(response.correoE);
                $('#sexoE').val(response.sexoE);
                //$('#fechaNacE').val(response.fechaNacE);
                var fechaNacE = response.fechaNacE;
                var fechaFormateada = formatearFecha(fechaNacE);
                $('#fechaNacE').val(fechaFormateada);
                $('#nacionalidadE').val(response.nacionalidadE);
                $('#idPais').val(response.idPais.idPais);
                $('#duiE').val(response.duiE);
                $('#nitE').val(response.nitE);
                $('#nupE').val(response.nupE);
                $('#pasaporteE').val(response.pasaporteE);
                $('#docPersonalE').val(response.docPersonalE);
                $('#estudianteId').val(idEstudiante);
                
                //console.log(response.idPais.nombrePais);
                console.log(response.fechaNacE);

                docPersonal.classList.remove("d-none");
                pasaporte.classList.remove("d-none");
                dui.classList.remove("d-none");
                nit.classList.remove("d-none");
                nup.classList.remove("d-none");

        if (response.idPais.nombrePais === "El Salvador") {
            //console.log("entro en 1");
            docPersonal.classList.add("d-none");
            pasaporte.classList.add("d-none");
        } else {
            dui.classList.add("d-none");
            nit.classList.add("d-none");
            nup.classList.add("d-none");
            //console.log("entro en 2");
        }

        },
           error: function () {
            alert('Error al obtener los datos del estudiante.');
        }
          });
        } else {
            // en caso de presionar el boton de nuevo solo se abrira el modal
            tituloModal.text('Agregar Estudiante');
            form.attr('action', '/AgregarEstudiante');

            docPersonal.classList.add("d-none");
            pasaporte.classList.add("d-none");
            dui.classList.add("d-none");
            nit.classList.add("d-none");
            nup.classList.add("d-none");

            $('#carnetE').val('');
            $('#nombresE').val('');
            $('#apellidosE').val('');
            $('#correoE').val('');
            $('#sexoE').val('');
            $('#fechaNacE').val('');
            $('#nacionalidadE').val('');
            $('#idPais').val('');
            $('#duiE').val('');
            $('#nitE').val('');
            $('#nupE').val('');
            $('#pasaporteE').val('');
            $('#docPersonalE').val('');
            $('#estudianteId').val('');
    }
        modal.modal('show');
    });
});
    
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idEstudiante = $(this).data('id');
        //var codPlan = $(this).data('cod');

        var modal = $('#confirmarEliminarModal');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarEstudianteBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar al estudiante seleccionado?</strong><br>Ten en cuenta que se eliminarán \n\
        los datos relacionados al estudiante');

        // Actualizar el atributo href del botón de eliminación con el idCohorte
        eliminarBtn.data('id', idEstudiante);

        modal.modal('show');
    });
    
      //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarEstudianteBtn', function () {
        
       var idEstudiante = $(this).data('id');
        // Actualizar la acción del formulario con el idMaestria
        $('#eliminarEstudianteForm').attr('action', '/EliminarEstudiante/' + idEstudiante);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarEstudianteForm').attr('action'),
            type: 'POST',
            data: $('#eliminarEstudianteForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
              $('#confirmarEliminarModal').modal('hide');
              // Recargar el DataTable
              $('#estudianteTable').DataTable().ajax.reload();
              // Mostrar el mensaje de éxito del controlador
               mostrarMensaje(response, 'success');
            },
            error: function () {
              $('#confirmarEliminarModal').modal('hide');
              // Mostrar mensaje de error en caso de que la solicitud falle
              mostrarMensaje('Error al eliminar al estudiante.', 'danger');
            }
        });
        
    });
    
    function mostrarMensaje(mensaje, tipo) {
        var alertElement = $('.alert-' + tipo);
        alertElement.text(mensaje).addClass('show').removeClass('d-none');
        setTimeout(function() {
          alertElement.removeClass('show').addClass('d-none');
        }, 5000); // Ocultar el mensaje después de 3 segundos (ajusta el valor según tus necesidades)
   }
   
    function formatearFecha(fecha) {
        //Especifico UTCDate para tomar la fecha del sistema
        var fechaObjeto = new Date(fecha);
        var dia = fechaObjeto.getUTCDate();
        var mes = fechaObjeto.getUTCMonth() + 1;
        var anio = fechaObjeto.getUTCFullYear();

        //var fechaObjeto = new Date(fecha);
        //var dia = fechaObjeto.getDate();
        //var mes = fechaObjeto.getMonth() + 1;
        //var anio = fechaObjeto.getFullYear();

        if (dia < 10) {
            dia = '0' + dia;
        }
        if (mes < 10) {
            mes = '0' + mes;
        }

        return dia + '/' + mes + '/' + anio;
    }
    
</script>
