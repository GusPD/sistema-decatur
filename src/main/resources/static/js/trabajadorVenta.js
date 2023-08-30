$(document).ready(function() {
    $.validator.addMethod(
        "validarCorreo",
        function(value, element) {
            return this.optional(element) || /^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,}$/.test(value);
        },
        "Ingrese un correo electrónico válido"
    );
    
    var formGuardar = $('#formGuardarCorreo'); // Almacenar referencia al formulario
    var validator = $('#formGuardarCorreo').validate({
         
        rules: {
           tipo: {
               required: true,
               maxlength: 20
           },
           correo: {
               required: true,
               validarCorreo: true,
               maxlength: 150
           }      
        },
        
        messages:{
            tipo:{
                required: 'Este campo es requerido'
            },
            correo:{
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
            if (element.attr("name") === "tipo" || element.attr("name") === "correo") {
                error.insertAfter(element);
            }        
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
               event.preventDefault();//detiene el evento del envio del form
            var idPersona = $('#idPersona').val();
            var idCorreo = $('#idCorreo').val();//tomo la id
            var idPropietario = $('#idPropietario').val();
            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            console.log(formDataArray);
            var url = '/AgregarCorreo';
            formDataArray.push({name: 'idCorreo', value: idCorreo}, {name: 'idPropietario', value: idPropietario});

            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModalCorreo').modal('hide');  // Cierra el modal
                    mostrarMensaje(response, 'success');

                    $.ajax({
                        url: "/MostrarPropietario/"+idPersona,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable = $(nuevosDatos).find('#tabla-correos');
                            $('#tabla-correos').html(elementoActualizable.html());
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#crearModalCorreo').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar el correo.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });

    // metodo para mostrar el modal
         $(document).on('click', '#AgregarCorreo', function() {
            var idPropietario = $('#idPropietario').data('id');
            var modal = $('#crearModalCorreo');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            validator.resetForm();  // Restablecer la validación
            formGuardar.find('.is-invalid').removeClass('is-invalid');

            var checkboxes = document.querySelectorAll(".checkClean");

            // en caso de presionar el boton de nuevo solo se abrira el modal
            tituloModal.text('Agregar Correo');
            form.attr('action', '/AgregarCorreo');
            $('.form-control').val('');

            modal.modal('show');
   });
   
   // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModalCorreo-btn', function () {
        var idCorreo = $(this).data('id');

        var modal = $('#confirmarEliminarModalCorreo');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarCorreoBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar el correo seleccionado?</strong><br>Ten en cuenta que se eliminarán \n\
        los datos relacionados al correo');

        // Actualizar el atributo href del botón de eliminación con el idCohorte
        eliminarBtn.data('id', idCorreo);

        modal.modal('show');
    });
   
   
   //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarCorreoBtn', function () {
        var idPersona = $('#idPersona').val();
        var idCorreo = $(this).data('id');
        // Actualizar la acción del formulario
        $('#eliminarCorreoForm').attr('action', '/EliminarCorreo/' + idCorreo);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarCorreoForm').attr('action'),
            type: 'POST',
            data: $('#eliminarCorreoForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
                $('#confirmarEliminarModalCorreo').modal('hide');
                // Recargar el DataTable
                $.ajax({
                    url: "/MostrarPropietario/"+idPersona,
                    type: 'GET',
                    success: function (nuevosDatos) {
                        var elementoActualizable = $(nuevosDatos).find('#tabla-correos');
                        $('#tabla-correos').html(elementoActualizable.html());
                    },
                    error: function () {
                        alert('Error al cargar la tabla.');
                    }
                });
               mostrarMensaje(response, 'success');
            },
            error: function (xhr, status, error) {
              $('#confirmarEliminarModalCorreo').modal('hide');
              var errorMessage = xhr.responseText || 'Error al eliminar el correo.';
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

