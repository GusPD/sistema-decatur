$(document).ready(function() {
    $.validator.addMethod(
        "validarDocumento",
        function(value, element) {
            return this.optional(element) || /\.pdf$/i.test(value);
        },
        "Ingrese un documento en formato .pdf"
    );
    
    var formGuardar = $('#formGuardarDocumento'); // Almacenar referencia al formulario
    var validator = $('#formGuardarDocumento').validate({
         
        rules: {
           nombre: {
               required: true,
               maxlength: 200
           },
           documento: {
               required: true,
               validarDocumento: true
           }      
        },
        
        messages:{
            nombre:{
                required: 'Este campo es requerido'
            },
            documento:{
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
            if (element.attr("name") === "nombre" || element.attr("name") === "documento") {
                error.insertAfter(element);
            }        
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
               event.preventDefault();//detiene el evento del envio del form
            var idPersona = $('#idPersona').val();
            var idDocumento = $('#idDocumento').val();//tomo la id
            var idVisitante = $('#idVisitante').val();
            var formDataArray = new FormData(formGuardar[0]);//tomo los datos del array

            console.log(formDataArray);
            var url = '/AgregarDocumentoTrabajador';
            formDataArray.append('idDocumento', idDocumento);
            formDataArray.append('idVisitante', idVisitante);

            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                processData: false, // No procesar los datos
                contentType: false,
                success: function (response) {
                    $('#crearModalDocumento').modal('hide');  // Cierra el modal
                    mostrarMensaje(response, 'success');

                    $.ajax({
                        url: "/MostrarTrabajador/"+idPersona,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable = $(nuevosDatos).find('#tabla-documentos');
                            $('#tabla-documentos').html(elementoActualizable.html());
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#crearModalDocumento').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar el documento.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });

    // metodo para mostrar el modal
         $(document).on('click', '#AgregarDocumento', function() {
            var idVisitante = $('#idVisitante').data('id');
            var modal = $('#crearModalDocumento');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            validator.resetForm();  // Restablecer la validación
            formGuardar.find('.is-invalid').removeClass('is-invalid');

            var checkboxes = document.querySelectorAll(".checkClean");

            // en caso de presionar el boton de nuevo solo se abrira el modal
            tituloModal.text('Agregar Documento');
            form.attr('action', '/AgregarDocumentoTrabajador');
            $('.form-control').val('');

            modal.modal('show');
   });
   
   // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModalDocumento-btn', function () {
        var idDocumento = $(this).data('id');

        var modal = $('#confirmarEliminarModalDocumento');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarDocumentoBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar el documento seleccionado?</strong><br>Ten en cuenta que se eliminarán \n\
        los datos relacionados al documento');

        // Actualizar el atributo href del botón de eliminación con el idCohorte
        eliminarBtn.data('id', idDocumento);

        modal.modal('show');
    });
   
   
   //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarDocumentoBtn', function () {
        var idPersona = $('#idPersona').val();
        var idDocumento = $(this).data('id');
        // Actualizar la acción del formulario
        $('#eliminarDocumentoForm').attr('action', '/EliminarDocumentoTrabajador/' + idDocumento);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarDocumentoForm').attr('action'),
            type: 'POST',
            data: $('#eliminarDocumentoForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
                $('#confirmarEliminarModalDocumento').modal('hide');
                // Recargar el DataTable
                $.ajax({
                    url: "/MostrarTrabajador/"+idPersona,
                    type: 'GET',
                    success: function (nuevosDatos) {
                        var elementoActualizable = $(nuevosDatos).find('#tabla-documentos');
                        $('#tabla-documentos').html(elementoActualizable.html());
                    },
                    error: function () {
                        alert('Error al cargar la tabla.');
                    }
                });
               mostrarMensaje(response, 'success');
            },
            error: function (xhr, status, error) {
              $('#confirmarEliminarModalDocumento').modal('hide');
              var errorMessage = xhr.responseText || 'Error al eliminar el documento.';
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

