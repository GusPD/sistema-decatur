$(document).ready(function() {
    
    $.validator.addMethod(
        "validarDui",
        function(value, element) {
            return this.optional(element) || /^\d{9}$/.test(value);
        },
        "Ingrese un número válido, verifique que deben de ser nueve dígitos"
    );
    
    var formGuardar = $('#formGuardar'); // Almacenar referencia al formulario
    var validator = $('#formGuardar').validate({
         
        rules: {
           dui: {
               required: true,
               validarDui: true,
               maxlength: 9
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
               required: true,
               maxlength: 200
           },
           direccionTrabajo:{
               required: true,
               maxlength: 300
           }          
        },
        
        messages:{
            dui:{
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
            if (element.attr("name") === "dui" || element.attr("name") === "nombre" || element.attr("name") === "apellido" || element.attr("name") === "profesion" || element.attr("name") === "direccionCasa" || element.attr("name") === "lugarTrabajo" || element.attr("name") === "direccionTrabajo") {
                error.insertAfter(element);
            }        
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
               event.preventDefault();//detiene el evento del envio del form 
            var idPersona = $('#idPersona').val();//tomo la id
            var idDocumento = $('#idDocumento').val();
            var idPropietario = $('#idPropietario').val();
            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            console.log(formDataArray);
            var url;//valido el tipo de url si editar o crear
            if (idPersona) {
                url = '/ActualizarPropietario';
                //meto la id en el campo de envio
                formDataArray.push({name: 'idPersona', value: idPersona}, {name: 'idPropietario', value: idPropietario}, {name: 'idDocumento', value: idDocumento});
            } else {
                url = '/AgregarPropietario';
            }

            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');  // Cierra el modal
                    mostrarMensaje(response, 'success');

                    $.ajax({
                        url: "/InformacionPropietario/"+idPersona,
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
                    $('#crearModal').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar el propietario.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });

    // metodo para mostrar el modal segun sea si editar o nuevo registro
         $(document).on('click', '#EditarInformacion', function() {
            var idPersona = $(this).data('id');
            var modal = $('#crearModal');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            var btnSumit = document.getElementById('btnSumit');
            validator.resetForm();  // Restablecer la validación
            formGuardar.find('.is-invalid').removeClass('is-invalid');

            if (idPersona) {
                tituloModal.text('Editar Propietario');//titulo del modal
                
                $.ajax({//utilizo ajax para obtener los datos
                    url: '/ObtenerPropietario/' + idPersona,
                    type: 'GET',
                    success: function (response) {
                       
                        var checkboxes = document.querySelectorAll(".checkClean");

                        for (var i = 0; i < checkboxes.length; i++) {
                            checkboxes[i].checked = false;
                        }
                        $('#idPersona').val(response.persona.idPersona);
                        $('#dui').val(response.persona.dui);
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
                var checkboxes = document.querySelectorAll(".checkClean");
                
                // en caso de presionar el boton de nuevo solo se abrira el modal
                tituloModal.text('Agregar Propietario');
                form.attr('action', '/AgregarPropietario');
                $('.form-control').val('');
                $('#idPersona').val('');
                $('#idPropietario').val('');
            }
            modal.modal('show');
   });
    
    function mostrarMensaje(mensaje, tipo) {
        var alertElement = $('.alert-' + tipo);
        alertElement.text(mensaje).addClass('show').removeClass('d-none');
        setTimeout(function() {
          alertElement.removeClass('show').addClass('d-none');
        }, 5000); // Ocultar el mensaje después de 3 segundos (ajusta el valor según tus necesidades)
    }
}); 

