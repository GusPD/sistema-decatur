$(document).ready(function() {
    //Formulario de editar
    $.validator.addMethod(
        "validarDocumento",
        function(value, element) {
            var mascara = $("#tipoDocumento option:selected").data("mascara");
            if (mascara) {
                var regex = new RegExp(mascara);
                return this.optional(element) || regex.test(value);
            } else {
                return true;
            }
        },
        "Ingrese un número válido para el tipo de documento"
    );
    var formGuardar = $('#formGuardar');
    var validator = $('#formGuardar').validate({
        rules: {
            tipoDocumento: {
                required: true,
            },
            numero: {
                required: true,
                validarDocumento: true,
            },
            nombre: {
                required: true,
                maxlength: 200
            },
            apellido: {
                required: true,
                maxlength: 200
            },
            empleador: {
                required: true,
                maxlength: 200
            } 
        },
        messages:{
            tipoDocumento:{
                required: 'Este campo es requerido'
            },
            numero:{
                required: 'Este campo es requerido'
            },
            nombre:{
                required: 'Este campo es requerido'
            },
            apellido:{
                required: 'Este campo es requerido'
            },
            empleador:{
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
            if (element.attr("name") === "tipoDocumento" || element.attr("name") === "numero" || element.attr("name") === "nombre" || element.attr("name") === "apellido" || element.attr("name") === "empleador") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault(); 
            var idPersona = $('#idPersona').val();
            var idDocumento = $('#idDocumento').val();
            var idTrabajador = $('#idVisitante').val();
            var formDataArray = formGuardar.serializeArray();
            var idProyecto = $("#idProyecto").val();
            if(idProyecto===""){
                idProyecto=0;
            }
            var url;
            if (idPersona) {
                url = '/ActualizarTrabajador';
                formDataArray.push({name: 'idPersona', value: idPersona}, {name: 'idVisitante', value: idTrabajador}, {name: 'idDocumento', value: idDocumento}, {name: 'rol', value: "TRABAJADOR"});
            } else {
                url = '/AgregarTrabajador';
                formDataArray.push( {name: 'rol', value: "TRABAJADOR"});
            }
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');
                    toastr.success(response);
                    $.ajax({
                        url: "/InformacionTrabajador/"+idProyecto+"/"+idPersona,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable = $(nuevosDatos).find('#tabla-informacion');
                            $('#tabla-informacion').html(elementoActualizable.html());
                            var elementoActualizable2 = $(nuevosDatos).find('#NombreTrabajador');
                            $('#NombreTrabajador').html(elementoActualizable2.html());
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al actualizar el trabajador.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    // Método para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '#EditarInformacion', function() {
       var idPersona = $(this).data('id');
       var modal = $('#crearModal');
       var tituloModal = modal.find('.modal-title');
       var form = modal.find('form');
       validator.resetForm();
       formGuardar.find('.is-invalid').removeClass('is-invalid');
        if (idPersona) {
            tituloModal.text('Editar Trabajador');
            $.ajax({
                url: '/ObtenerTrabajador/' + idPersona,
                type: 'GET',
                success: function (response) {
                    $('#idPersona').val(response.persona.idPersona);
                    $('#tipoDocumento').val(response.persona.tipoDocumento.idTipoDocumento);
                    $('#numero').val(response.persona.numero);
                    $('#nombre').val(response.persona.nombre);
                    $('#apellido').val(response.persona.apellido);
                    $('#idVisitante').val(response.trabajador.idVisitante);
                    $('#idDocumento').val(response.trabajador.idDocumento);
                    $('#rol').val(response.trabajador.rol);
                    $('#empleador').val(response.trabajador.empleador);
                },
                error: function () {
                    alert('Error al obtener los datos del trabajador.');
                }
            });
        } else {
            tituloModal.text('Agregar Trabajador');
            form.attr('action', '/AgregarTrabajador');
            $('.form-control').val('');
            $('#idPersona').val('');
            $('#idVisitante').val('');
        }
        modal.modal('show');
    });
}); 

