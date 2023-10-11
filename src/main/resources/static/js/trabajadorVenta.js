$(document).ready(function() {
    $.validator.addMethod(
        "validarDui",
        function(value, element) {
            return this.optional(element) || /^\d{9}$/.test(value);
        },
        "Ingrese un número válido, verifique que deben de ser nueve dígitos"
    );
    
    var formGuardar = $('#formGuardarTrabajador'); // Almacenar referencia al formulario
    var validator = $('#formGuardarTrabajador').validate({
         
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
           empleador: {
               required: true,
               maxlength: 200
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
            if (element.attr("name") === "dui" || element.attr("name") === "nombre" || element.attr("name") === "apellido" || element.attr("name") === "empleador") {
                error.insertAfter(element);
            }        
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
               event.preventDefault();//detiene el evento del envio del form 
            var idVenta = $('#idVenta').val();//tomo la id
            var idDocumento = $('#idDocumento').val();
            var idTrabajador = $('#idVisitante').val();
            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            console.log(formDataArray);
            var url = '/AgregarTrabajadorVenta';
            formDataArray.push({name: 'idVenta', value: idVenta}, {name: 'rol', value: "TRABAJADOR"});
            
            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModalTrabajador').modal('hide');  // Cierra el modal
                    mostrarMensaje(response, 'success');
                    $('.form-control').val('');
                    $.ajax({
                        url: "/Venta/"+idVenta,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable = $(nuevosDatos).find('#tabla-trabajadores');
                            $('#tabla-trabajadores').html(elementoActualizable.html());
                            var elementoActualizable2 = $(nuevosDatos).find('#formGuardarTrabajador');
                            $('#formGuardarTrabajador').html(elementoActualizable2.html());
                            $( '#trabajadores' ).select2( {
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
                    $('#crearModalTrabajador').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al agregar el trabajador.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });
    
    $('#seleccionarModalTrabajador').on('shown.bs.modal', function () {
        var select2Elements = $(this).find('.select2');
        select2Elements.each(function () {
            $(this).on('click', function () {
                var select2ChoiceElement = document.querySelector('.select2-selection__choice');
                if (select2ChoiceElement) {
                    $("#span-trabajadores-error").addClass('d-none');
                    $('#trabajadores').removeClass('is-invalid');
                }else{
                    $("#span-trabajadores-error").removeClass('d-none');
                    $('#trabajadores').addClass('is-invalid');
                }
            });
        });
    });
    
    var formSeleccionarGuardar = $('#formSeleccionarTrabajador'); // Almacenar referencia al formulario
    var validator = $('#formSeleccionarTrabajador').validate({
        
        rules: {
           trabajadores:{
               required: true
           }          
        },
        
        messages:{
            trabajadores:{
                required: 'Este campo es requerido'
            }        
        },
        
        highlight: function(element) {
            $(element).addClass('is-invalid');
            var select2ChoiceElement = document.querySelector('.select2-selection__choice');
            if (!select2ChoiceElement) {
                $("#span-trabajadores-error").removeClass('d-none');
                $('#trabajadores').addClass('is-invalid');
            }
        },
        
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
            var select2ChoiceElement = document.querySelector('.select2-selection__choice');
            if (select2ChoiceElement) {
                $("#span-trabajadores-error").addClass('d-none');
                $('#trabajadores').removeClass('is-invalid');
            }
        },
        
        errorPlacement: function(error, element) {
            if (element.attr("name") !== "trabajadores") {
                error.insertAfter(element);
            }        
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
            event.preventDefault();
            var formDataArray = formSeleccionarGuardar.serializeArray();//tomo los datos del array
            var idVenta = $('#idVenta').val();
            var url = '/SeleccionarTrabajadoresVenta';
            formDataArray.push({name: 'idVenta', value: idVenta});
            
            console.log(formDataArray);
            
            $.ajax({
                type: "POST",
                url: url,
                data: formDataArray,
                success: function(response) {
                    $('#seleccionarModalTrabajador').modal('hide');
                    mostrarMensaje(response, 'success');
                    $.ajax({
                        url: "/Venta/"+idVenta,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable = $(nuevosDatos).find('#tabla-trabajadores');
                            $('#tabla-trabajadores').html(elementoActualizable.html());
                            var elementoActualizable2 = $(nuevosDatos).find('#formSeleccionarTrabajador');
                            $('#formSeleccionarTrabajador').html(elementoActualizable2.html());
                            $( '#trabajadores' ).select2( {
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
                    $('#seleccionarModalTrabajador').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al agregar el trabajador.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });
    
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModalTrabajador-btn', function () {
        var idPersona = $(this).data('id');

        var modal = $('#confirmarEliminarModalTrabajador');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarTrabajadorBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar el trabajador seleccionado?</strong><br>Ten en cuenta que se eliminarán \n\
        los datos relacionados al trabajador');

        // Actualizar el atributo href del botón de eliminación con el idCohorte
        eliminarBtn.data('id', idPersona);

        modal.modal('show');
    });
   
   
   //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarTrabajadorBtn', function () {
        var idVenta = $('#idVenta').val();
        var idPersona = $(this).data('id');
        // Actualizar la acción del formulario
        $('#eliminarTrabajadorForm').attr('action', '/EliminarTrabajadorVenta/' + idPersona);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarTrabajadorForm').attr('action'),
            type: 'POST',
            data: $('#eliminarTrabajadorForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
                $('#confirmarEliminarModalTrabajador').modal('hide');
                // Recargar el DataTable
                $.ajax({
                    url: "/Venta/"+idVenta,
                    type: 'GET',
                    success: function (nuevosDatos) {
                        var elementoActualizable = $(nuevosDatos).find('#tabla-trabajadores');
                        $('#tabla-trabajadores').html(elementoActualizable.html());
                        var elementoActualizable2 = $(nuevosDatos).find('#formGuardarTrabajador');
                        $('#formGuardarTrabajador').html(elementoActualizable2.html());
                        var elementoActualizable2 = $(nuevosDatos).find('#formSeleccionarTrabajador');
                        $('#formSeleccionarTrabajador').html(elementoActualizable2.html());
                        $( '#trabajadores' ).select2( {
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
              $('#confirmarEliminarModalTrabajador').modal('hide');
              var errorMessage = xhr.responseText || 'Error al eliminar el trabajador.';
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
    
    $( '#trabajadores' ).select2( {
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
