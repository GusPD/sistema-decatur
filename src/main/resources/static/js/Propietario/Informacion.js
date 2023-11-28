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
            profesion:{
                required: true,
                maxlength: 200
            },
            direccionCasa: {
                required: true,
                maxlength: 300
            },
            lugarTrabajo: {
                maxlength: 200
            },
            direccionTrabajo:{
                maxlength: 300
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
            profesion:{
                required: 'Este campo es requerido'
            },
            direccionCasa:{
                required: 'Este campo es requerido'
            },
            lugarTrabajo:{
                required: 'Este campo es requerido'
            },
            direccionTrabajo:{
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
            if (element.attr("name") === "tipoDocumento" || element.attr("name") === "numero" || element.attr("name") === "nombre" || element.attr("name") === "apellido" || element.attr("name") === "profesion" || element.attr("name") === "direccionCasa" || element.attr("name") === "lugarTrabajo" || element.attr("name") === "direccionTrabajo") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var idPersona = $('#idPersona').val();
            var idProyecto = $('#idProyecto').val();
            var idDocumento = $('#idDocumento').val();
            var idPropietario = $('#idPropietario').val();
            var formDataArray = formGuardar.serializeArray();
            var idProyecto = $("#idProyecto").val();
            if(idProyecto===""){
                idProyecto=0;
            }
            var url;
            if (idPersona) {
                url = '/ActualizarPropietario';
                formDataArray.push({name: 'idPersona', value: idPersona}, {name: 'idPropietario', value: idPropietario}, {name: 'idDocumento', value: idDocumento});
            } else {
                url = '/AgregarPropietario';
            }
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');
                    toastr.success(response);
                    $.ajax({
                        url: "/InformacionPropietario/"+idProyecto+"/"+idPersona,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable = $(nuevosDatos).find('#tabla-informacion');
                            $('#tabla-informacion').html(elementoActualizable.html());
                            var elementoActualizable2 = $(nuevosDatos).find('#NombrePropietario');
                            $('#NombrePropietario').html(elementoActualizable2.html());
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al actualizar el propietario.';
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
           tituloModal.text('Editar Propietario');
            $.ajax({
                url: '/ObtenerPropietario/' + idPersona,
                type: 'GET',
                success: function (response) {
                    $('#idPersona').val(response.persona.idPersona);
                    $('#tipoDocumento').val(response.persona.tipoDocumento.idTipoDocumento);
                    $('#numero').val(response.persona.numero);
                    $('#nombre').val(response.persona.nombre);
                    $('#apellido').val(response.persona.apellido);
                    $('#idPropietario').val(response.propietario.idPropietario);
                    $('#profesion').val(response.propietario.profesion);
                    $('#direccionCasa').val(response.propietario.direccionCasa);
                    $('#lugarTrabajo').val(response.propietario.lugarTrabajo);
                    $('#direccionTrabajo').val(response.propietario.direccionTrabajo);
                    $('#idDocumento').val(response.propietario.idDocumento);
                },
                error: function () {
                    alert('Error al obtener los datos del propietario.');
                }
            });
       } else {
           tituloModal.text('Agregar Propietario');
           form.attr('action', '/AgregarPropietario');
           $('.form-control').val('');
           $('#idPersona').val('');
           $('#idPropietario').val('');
       }
       modal.modal('show');
    });
}); 

