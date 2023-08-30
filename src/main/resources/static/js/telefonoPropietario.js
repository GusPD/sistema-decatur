$(document).ready(function() {
    $.validator.addMethod(
        "validarTelefono",
        function(value, element) {
           return this.optional(element) || /^\d{8,}$/.test(value);
        },
        "Ingrese un número de teléfono válido"
    );
    
    var formGuardar = $('#formGuardarTelefono'); // Almacenar referencia al formulario
    var validator = $('#formGuardarTelefono').validate({
         
        rules: {
           tipo: {
               required: true,
               maxlength: 20
           },
           telefono: {
               required: true,
               validarTelefono: true,
               maxlength: 12
           }      
        },
        
        messages:{
            tipo:{
                required: 'Este campo es requerido'
            },
            telefono:{
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
            if (element.attr("name") === "tipo" || element.attr("name") === "telefono") {
                error.insertAfter(element);
            }        
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
               event.preventDefault();//detiene el evento del envio del form
            var idPersona = $('#idPersona').val();
            var idTelefono = $('#idTelefono').val();//tomo la id
            var idPropietario = $('#idPropietario').val();
            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            console.log(formDataArray);
            var url = '/AgregarTelefono';
            formDataArray.push({name: 'idTelefono', value: idTelefono}, {name: 'idPropietario', value: idPropietario});

            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModalTelefono').modal('hide');  // Cierra el modal
                    mostrarMensaje(response, 'success');

                    $.ajax({
                        url: "/MostrarPropietario/"+idPersona,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable = $(nuevosDatos).find('#tabla-telefonos');
                            $('#tabla-telefonos').html(elementoActualizable.html());
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#crearModalTelefono').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar el telefono.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });

    // metodo para mostrar el modal
         $(document).on('click', '#AgregarTelefono', function() {
            var idPropietario = $('#idPropietario').data('id');
            var modal = $('#crearModalTelefono');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            validator.resetForm();  // Restablecer la validación
            formGuardar.find('.is-invalid').removeClass('is-invalid');

            var checkboxes = document.querySelectorAll(".checkClean");

            // en caso de presionar el boton de nuevo solo se abrira el modal
            tituloModal.text('Agregar Telefono');
            form.attr('action', '/AgregarTelefono');
            $('.form-control').val('');

            modal.modal('show');
   });
   
   // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModalTelefono-btn', function () {
        var idTelefono = $(this).data('id');

        var modal = $('#confirmarEliminarModalTelefono');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarTelefonoBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar el teléfono seleccionado?</strong><br>Ten en cuenta que se eliminarán \n\
        los datos relacionados al teléfono');

        // Actualizar el atributo href del botón de eliminación con el idCohorte
        eliminarBtn.data('id', idTelefono);

        modal.modal('show');
    });
   
   
   //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarTelefonoBtn', function () {
        var idPersona = $('#idPersona').val();
        var idTelefono = $(this).data('id');
        // Actualizar la acción del formulario
        $('#eliminarTelefonoForm').attr('action', '/EliminarTelefono/' + idTelefono);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarTelefonoForm').attr('action'),
            type: 'POST',
            data: $('#eliminarTelefonoForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
                $('#confirmarEliminarModalTelefono').modal('hide');
                // Recargar el DataTable
                $.ajax({
                    url: "/MostrarPropietario/"+idPersona,
                    type: 'GET',
                    success: function (nuevosDatos) {
                        var elementoActualizable = $(nuevosDatos).find('#tabla-telefonos');
                        $('#tabla-telefonos').html(elementoActualizable.html());
                    },
                    error: function () {
                        alert('Error al cargar la tabla.');
                    }
                });
               mostrarMensaje(response, 'success');
            },
            error: function (xhr, status, error) {
              $('#confirmarEliminarModalTelefono').modal('hide');
              var errorMessage = xhr.responseText || 'Error al eliminar el telefono.';
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
}); 

