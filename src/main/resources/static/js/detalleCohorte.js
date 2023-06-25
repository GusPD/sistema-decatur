$(document).ready(function() {

$(document).on('click', '.inscribirMateriaModal-btn', function () {
        var idCohorte = $(this).data('id');
        var modal = $('#inscribirMateriaModal');
        var modalBody = modal.find('.modal-body');
        var modalBtn = modal.find('#inscribirMateriaBtn');
        modalBtn.data('id', idCohorte);
        var select = modal.find('select');
        validatorIM.resetForm();  // Restablecer la validación
        formGuardarIM.find('.is-invalid').removeClass('is-invalid');
        modalBtn.removeClass('d-none');

        $.ajax({
            url: '/ObtenerMateriasMaestriaCohorte/' + idMaestria + '/'+ idCohorte,
            type: 'GET',
            success: function (response) {
                // Limpiar las opciones anteriores del select
                select.empty();
                // Verificar si se obtuvieron materias
                if (response && response.length > 0) {
                    // Recorrer las materias y agregarlas como opciones en el select
                    response.forEach(function (materia) {
                        var option = $('<option>').attr('value', materia.idAsignatura).text(materia.nombreMateria);
                        select.append(option);
                    });
                } else {
                    // Si no hay materias, mostrar un mensaje en el modal body
                    modalBody.text('La maestría no tiene materias disponibles para inscribir.');
                    modalBtn.addClass('d-none');
                }
            },
            error: function () {
                alert('Error al obtener las materias de la maestría.');
            }
        });
        modal.modal('show');
    });
    
    var formGuardarIM = $('#inscribirMateriaForm'); // Almacenar referencia al formulario
    var validatorIM = $('#inscribirMateriaForm').validate({
        rules: {// reglas
            materias: {
                required: true
            }
        },
        messages: {// mensajes
            materias: {
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
            if (element.attr("name") === "materias") {
                error.insertAfter(element);
            }
         },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function (form) {
            event.preventDefault();//detiene el evento del envio del form 
            var idCohorte = $('#inscribirMateriaBtn').data('id');

            var url;//valido el tipo de url si editar o crear
                url = '/InscribirMateria/'+idCohorte;
            // Convertir el arreglo en un objeto
            var materiasIds = $('#materias').val();
            var formData = {};
            formData['materias'] = materiasIds; 
            formData['_csrf'] = $('#csrfToken').val(); // Agregar el valor del token CSRF al objeto formData
            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                success: function (response) {
                    $('#inscribirMateriaModal').modal('hide');  // Cierra el modal
                    var table = $('#cohorteTable').DataTable();
                    table.ajax.reload(null, false); // Recargar sin reiniciar la paginación
                    mostrarMensaje(response, 'success');
                },
                error: function (xhr, status, error) {
                    $('#inscribirMateriaModal').modal('hide');  // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al inscribir la cohorte a las materias seleccionadas.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });


    
    function mostrarMensaje(mensaje, tipo) {
        var alertElement = $('.alert-' + tipo);
        alertElement.text(mensaje).addClass('show').removeClass('d-none');
        setTimeout(function() {
          alertElement.removeClass('show').addClass('d-none');
        }, 5000); // Ocultar el mensaje después de 3 segundos (ajusta el valor según tus necesidades)
    }
    
});