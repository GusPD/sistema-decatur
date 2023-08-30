$(document).ready(function() {
    $.validator.addMethod(
        "validarTelefono",
        function(value, element) {
           return this.optional(element) || /^\d{8,}$/.test(value);
        },
        "Ingrese un número de teléfono válido"
    );
    
    $.validator.addMethod(
        "validarCorreo",
        function(value, element) {
           return this.optional(element) || /^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,}$/.test(value);
        },
        "Ingrese un correo electrónico válido"
    );
    
    var formGuardar = $('#formGuardarReferencia'); // Almacenar referencia al formulario
    var validator = $('#formGuardarReferencia').validate({
         
        rules: {
           nombre: {
               required: true,
               maxlength: 200
           },
           apellido: {
               required: true,
               maxlength: 200
           },
           telefono: {
               required: true,
               validarTelefono: true,
               maxlength: 12
           },
           correo: {
               required: true,
               validarCorreo: true,
               maxlength: 150
           } 
        },
        
        messages:{
            nombre:{
                required: 'Este campo es requerido'
            },
            apellido:{
                required: 'Este campo es requerido'
            },
            telefono:{
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
            if (element.attr("name") === "nombre" || element.attr("name") === "apellido" || element.attr("name") === "telefono" || element.attr("name") === "correo") {
                error.insertAfter(element);
            }        
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
               event.preventDefault();//detiene el evento del envio del form
            var idPersona = $('#idPersona').val();
            var idReferencia = $('#idReferencia').val();//tomo la id
            var idPropietario = $('#idPropietario').val();
            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            console.log(formDataArray);
            var url = '/AgregarReferencia';
            formDataArray.push({name: 'idReferencia', value: idReferencia}, {name: 'idPropietario', value: idPropietario});

            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModalReferencia').modal('hide');  // Cierra el modal
                    mostrarMensaje(response, 'success');

                    $.ajax({
                        url: "/MostrarPropietario/"+idPersona,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable = $(nuevosDatos).find('#tabla-referencias');
                            $('#tabla-referencias').html(elementoActualizable.html());
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#crearModalReferencia').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar la referencia.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });

    // metodo para mostrar el modal
         $(document).on('click', '#AgregarReferencia', function() {
            var idPropietario = $('#idPropietario').data('id');
            var modal = $('#crearModalReferencia');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            validator.resetForm();  // Restablecer la validación
            formGuardar.find('.is-invalid').removeClass('is-invalid');

            var checkboxes = document.querySelectorAll(".checkClean");

            // en caso de presionar el boton de nuevo solo se abrira el modal
            tituloModal.text('Agregar Referencia');
            form.attr('action', '/AgregarReferencia');
            $('.form-control').val('');

            modal.modal('show');
   });
   
   // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModalReferencia-btn', function () {
        var idReferencia = $(this).data('id');

        var modal = $('#confirmarEliminarModalReferencia');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarReferenciaBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar la referencia seleccionada?</strong><br>Ten en cuenta que se eliminarán \n\
        los datos relacionados a la referencia');

        // Actualizar el atributo href del botón de eliminación con el idCohorte
        eliminarBtn.data('id', idReferencia);

        modal.modal('show');
    });
   
   
   //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarReferenciaBtn', function () {
        var idPersona = $('#idPersona').val();
        var idReferencia = $(this).data('id');
        // Actualizar la acción del formulario
        $('#eliminarReferenciaForm').attr('action', '/EliminarReferencia/' + idReferencia);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarReferenciaForm').attr('action'),
            type: 'POST',
            data: $('#eliminarReferenciaForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
                $('#confirmarEliminarModalReferencia').modal('hide');
                // Recargar el DataTable
                $.ajax({
                    url: "/MostrarPropietario/"+idPersona,
                    type: 'GET',
                    success: function (nuevosDatos) {
                        var elementoActualizable = $(nuevosDatos).find('#tabla-referencias');
                        $('#tabla-referencias').html(elementoActualizable.html());
                    },
                    error: function () {
                        alert('Error al cargar la tabla.');
                    }
                });
               mostrarMensaje(response, 'success');
            },
            error: function (xhr, status, error) {
              $('#confirmarEliminarModalReferencia').modal('hide');
              var errorMessage = xhr.responseText || 'Error al eliminar la referencia.';
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

