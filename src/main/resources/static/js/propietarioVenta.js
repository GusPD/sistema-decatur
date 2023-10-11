$(document).ready(function() {
    $.validator.addMethod(
        "validarDui",
        function(value, element) {
            return this.optional(element) || /^\d{9}$/.test(value);
        },
        "Ingrese un número válido, verifique que deben de ser nueve dígitos"
    );
    
    var formGuardar = $('#formGuardarPropietario'); // Almacenar referencia al formulario
    var validator = $('#formGuardarPropietario').validate({
         
        rules: {
           dui: {
               required: true,
               validarDui: true,
               maxlength: 9
           },
           nombreP: {
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
           },
           estadoP:{
               required: true
           } 
        },
        
        messages:{
            dui:{
                required: 'Este campo es requerido'
            },
            nombreP:{
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
            },
            estadoP:{
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
            if (element.attr("name") === "dui" || element.attr("name") === "nombreP" || element.attr("name") === "apellido" || element.attr("name") === "profesion" || element.attr("name") === "direccionCasa" || element.attr("name") === "lugarTrabajo" || element.attr("name") === "direccionTrabajo" || element.attr("name") === "estadoP") {
                error.insertAfter(element);
            }        
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
               event.preventDefault();//detiene el evento del envio del form 
            var idVenta = $('#idVenta').val();//tomo la id
            var idDocumento = $('#idDocumento').val();
            var idPersona = $('#idPersona').val();
            var idPropietario = $('#idPropietario').val();
            var idAsignacion = $('#idAsignacion').val();
            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            console.log(formDataArray);
            if (idPersona) {
                url = '/ActualizarPropietarioVenta';
                formDataArray.push({name: 'idPersona', value: idPersona}, {name: 'idPropietario', value: idPropietario}, {name: 'idDocumento', value: idDocumento},{name: 'idVenta', value: idVenta},{name: 'idAsignacion', value: idAsignacion});
            } else {
                var url = '/AgregarPropietarioVenta';
                formDataArray.push({name: 'idVenta', value: idVenta});
            }

            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModalPropietario').modal('hide');  // Cierra el modal
                    mostrarMensaje(response, 'success');
                    $('.form-control').val('');
                    $.ajax({
                        url: "/Venta/"+idVenta,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable = $(nuevosDatos).find('#tabla-propietarios');
                            $('#tabla-propietarios').html(elementoActualizable.html());
                            var elementoActualizable2 = $(nuevosDatos).find('#formGuardarPropietario');
                            $('#formGuardarPropietario').html(elementoActualizable2.html());
                            $( '#propietarios' ).select2( {
                                theme: "bootstrap-5",
                                width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
                                placeholder: $( this ).data( 'placeholder' ),
                                closeOnSelect: false,
                            } );
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#crearModalPropietario').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al agregar el propietario.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });
    
    // metodo para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModalPropietario-btn', function () {
        var idAsignacion = $(this).data('id');
        var idPersona = $('#idPersona').val();
        var modal = $('#crearModalPropietario');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        var btnSumit = document.getElementById('btnSumit');
        validator.resetForm();  // Restablecer la validación
        formGuardar.find('.is-invalid').removeClass('is-invalid');

        if (idAsignacion) {
            tituloModal.text('Editar Propietario');//titulo del modal

            $.ajax({//utilizo ajax para obtener los datos
                url: '/ObtenerPropietarioProyecto/' + idAsignacion,
                type: 'GET',
                success: function (response) {

                    $('#idPersona').val(response.asignacion.propietario.persona.idPersona);
                    $('#dui').val(response.asignacion.propietario.persona.dui);
                    $('#nombreP').val(response.asignacion.propietario.persona.nombre);
                    $('#apellido').val(response.asignacion.propietario.persona.apellido);
                    $('#idPropietario').val(response.asignacion.propietario.idPropietario);
                    $('#profesion').val(response.asignacion.propietario.profesion);
                    $('#direccionCasa').val(response.asignacion.propietario.direccionCasa);
                    $('#lugarTrabajo').val(response.asignacion.propietario.lugarTrabajo);
                    $('#direccionTrabajo').val(response.asignacion.propietario.direccionTrabajo);
                    $('#idDocumento').val(response.asignacion.propietario.idDocumento);
                    $('#estadoP').val(response.asignacion.estado);
                    $('#idAsignacion').val(response.asignacion.idAsignacion);
                },
                error: function () {
                    alert('Error al obtener los datos del propietario.');
                }
            });
        } else {
            var checkboxes = document.querySelectorAll(".checkClean");

            // en caso de presionar el boton de nuevo solo se abrira el modal
            tituloModal.text('Agregar Propietario');
            form.attr('action', '/AgregarPropietarioVenta');
            $('.form-control').val('');
        }
        modal.modal('show');
    });
    
    $('#seleccionarModalPropietario').on('shown.bs.modal', function () {
        var select2Elements = $(this).find('.select2');
        select2Elements.each(function () {
            $(this).on('click', function () {
                var select2ChoiceElement = document.querySelector('.select2-selection__choice');
                if (select2ChoiceElement) {
                    $("#span-propietarios-error").addClass('d-none');
                    $('#propietarios').removeClass('is-invalid');
                }else{
                    $("#span-propietarios-error").removeClass('d-none');
                    $('#propietarios').addClass('is-invalid');
                }
            });
        });
    });
    
    var formSeleccionarGuardar = $('#formSeleccionarPropietario'); // Almacenar referencia al formulario
    var validator = $('#formSeleccionarPropietario').validate({
        
        rules: {
            propietarios:{
                required: true
            },
            estadoS:{
                required: true
            } 
        },
        
        messages:{
            propietarios:{
                required: 'Este campo es requerido'
            },
            estadoS:{
                required: 'Este campo es requerido'
            }
        },
        
        highlight: function( element) {
            $(element).addClass('is-invalid');
            var select2ChoiceElement = document.querySelector('.select2-selection__choice');
            if (!select2ChoiceElement) {
                $("#span-propietarios-error").removeClass('d-none');
                $('#propietarios').addClass('is-invalid');
            }
        },
        
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
            var select2ChoiceElement = document.querySelector('.select2-selection__choice');
            if (select2ChoiceElement) {
                $("#span-propietarios-error").addClass('d-none');
                $('#propietarios').removeClass('is-invalid');
            }
        },
        
        errorPlacement: function(error, element) {
            if (element.attr("name") === "estadoS") {
                error.insertAfter(element);
            }      
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
            event.preventDefault();
            var formDataArray = formSeleccionarGuardar.serializeArray();//tomo los datos del array
            var idVenta = $('#idVenta').val();
            var url = '/SeleccionarPropietariosVenta';
            formDataArray.push({name: 'idVenta', value: idVenta});
            
            console.log(formDataArray);
            
            $.ajax({
                type: "POST",
                url: url,
                data: formDataArray,
                success: function(response) {
                    $('#seleccionarModalPropietario').modal('hide');
                    mostrarMensaje(response, 'success');
                    $.ajax({
                        url: "/Venta/"+idVenta,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable = $(nuevosDatos).find('#tabla-propietarios');
                            $('#tabla-propietarios').html(elementoActualizable.html());
                            var elementoActualizable2 = $(nuevosDatos).find('#formSeleccionarPropietario');
                            $('#formSeleccionarPropietario').html(elementoActualizable2.html());
                            $( '#propietarios' ).select2( {
                                theme: "bootstrap-5",
                                width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
                                placeholder: $( this ).data( 'placeholder' ),
                                closeOnSelect: false,
                            } );
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#seleccionarModalPropietario').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al agregar el propietario.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });
    
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModalPropietario-btn', function () {
        var idPersona = $(this).data('id');

        var modal = $('#confirmarEliminarModalPropietario');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarPropietarioBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar el propietario seleccionado?</strong><br>Ten en cuenta que se eliminarán \n\
        los datos relacionados al propietario');

        // Actualizar el atributo href del botón de eliminación con el idCohorte
        eliminarBtn.data('id', idPersona);

        modal.modal('show');
    });
   
   
   //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarPropietarioBtn', function () {
        var idVenta = $('#idVenta').val();
        var idPersona = $(this).data('id');
        // Actualizar la acción del formulario
        $('#eliminarPropietarioForm').attr('action', '/EliminarPropietarioVenta/' + idPersona);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarPropietarioForm').attr('action'),
            type: 'POST',
            data: $('#eliminarPropietarioForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
                $('#confirmarEliminarModalPropietario').modal('hide');
                // Recargar el DataTable
                $.ajax({
                    url: "/Venta/"+idVenta,
                    type: 'GET',
                    success: function (nuevosDatos) {
                        var elementoActualizable = $(nuevosDatos).find('#tabla-propietarios');
                        $('#tabla-propietarios').html(elementoActualizable.html());
                        var elementoActualizable2 = $(nuevosDatos).find('#formGuardarPropietario');
                        $('#formGuardarPropietario').html(elementoActualizable2.html());
                        var elementoActualizable2 = $(nuevosDatos).find('#formSeleccionarPropietario');
                        $('#formSeleccionarPropietario').html(elementoActualizable2.html());
                        $( '#propietarios' ).select2( {
                            theme: "bootstrap-5",
                            width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
                            placeholder: $( this ).data( 'placeholder' ),
                            closeOnSelect: false,
                        } );
                    },
                    error: function () {
                        alert('Error al cargar la tabla.');
                    }
                });
               mostrarMensaje(response, 'success');
            },
            error: function (xhr, status, error) {
              $('#confirmarEliminarModalPropietario').modal('hide');
              var errorMessage = xhr.responseText || 'Error al eliminar el propietario.';
              mostrarMensaje(errorMessage, 'danger');
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
    
    $( '#propietarios' ).select2( {
        theme: "bootstrap-5",
        width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
        placeholder: $( this ).data( 'placeholder' ),
        closeOnSelect: false,
    } );
    
    $( '#multiple-select-field' ).select2( {
        theme: "bootstrap-5",
        width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
        placeholder: $( this ).data( 'placeholder' ),
        closeOnSelect: false,
    } );
}); 
