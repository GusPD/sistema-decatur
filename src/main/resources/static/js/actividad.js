// metodo para mostrar el modal segun sea si editar o nuevo registro 
$(document).on('click', '.abrirModal-btn', function () {
    var id = $(this).data('id');
    var modal = $('#crearModal');
    var tituloModal = modal.find('.modal-title');
    var form = modal.find('form');
    validator.resetForm();  // Restablecer la validación
    $('#formGuardar').find('.is-invalid').removeClass('is-invalid');

    $('#formGuardar').validate().resetForm();//quita los mensajes de error 
    if (id) {
        tituloModal.text('Editar Area de Conocimiento');//titulo del modal
        $.ajax({//utilizo ajax para obtener los datos
            url: '/ObtenerActividad/' + id,
            type: 'GET',
            success: function (response) {
                $('#actividad').val(response.nombreActividad);
                $('#ponderacion').val(response.ponderacion);
                $('#idActividad').val(id);
            },
            error: function () {
                alert('Error al obtener los datos del Ã¡rea de conocimiento.');
            }
        });
    } else {
        // en caso de presionar el boton de nuevo solo se abrira el modal
        tituloModal.text('Agregar Actividad');
        form.attr('action', '/AgregarActividad');
        $('#actividad').val();
        $('#ponderacion').val();
        $('#idActividad').val();
    }
    modal.modal('show');
});
var validator = $('#formGuardar').validate({
    rules: {// reglas
        nombreActividad: {
            required: true
        },
        ponderacion: {
            required: true
        }
    },
    messages: {// mensajes
        nombreActividad: {
            required: 'Este campo es requerido'
        },
        ponderacion: {
            required: 'Este campo es requerido'
        }
    },
    highlight: function (element) {
        $(element).addClass('is-invalid');
    },
    unhighlight: function (element) {
        $(element).removeClass('is-invalid');
    },
    errorPlacement: function (error, element) {

        error.insertAfter(element);

    },
    errorElement: 'div',
    errorClass: 'invalid-feedback',
    submitHandler: function (form) {
        event.preventDefault();
        var id = $('#idActividad').val();
        var idListAe = $('#idList').val();
        var formDataArray = $('#formGuardar').serializeArray();//tomo los datos del array        
        //formDataArray.push({name: 'idListAe', value: idListAe});
        var url = '';
        if (id) {
            url = '/ActualizarActividad';
            //meto la id en el campo de envio
            formDataArray.push({name: 'idActividad', value: id});
        } else {
            url = '/AgregarActividad';
            formDataArray.push({name: 'idListAe', value: idListAe});
        }
        
        // Convertir el arreglo en un objeto
        var formData = {};
        $.map(formDataArray, function (n, i) {
            formData[n['name']] = n['value'];
        });
        $.ajax({
            url: url,
            type: 'POST',
            data: formData,
            success: function (response) {
                $('#crearModal').modal('hide');  // Cierra el modal
                //location.reload();
                mostrarMensaje(response, 'success');
            },
            error: function (xhr, status, error) {
                $('#crearModal').modal('hide');  // Cierra el modal
                var errorMessage = xhr.responseText || 'Error al actualizar la maestría.';
                mostrarMensaje(errorMessage, 'danger');
                console.log(errorMessage);
            }
        });
    }
});

// Método para mostrar el modal de eliminación
$(document).on('click', '.eliminarModal-btn', function () {
    var idA = $(this).data('id');
    var nombreA = $(this).data('nombre');

    var modal = $('#confirmarEliminarModal');
    var tituloModal = modal.find('.modal-title');
    var cuerpoModal = modal.find('.modal-body');
    var eliminarBtn = modal.find('#eliminarActividadBtn');

    // Actualizar el contenido del modal con los parámetros recibidos
    tituloModal.text('Confirmar eliminación');
    cuerpoModal.html('<strong>¿Estás seguro de eliminar la Àrea de Conocimiento seleccionada?</strong><br>Ten en cuenta que se eliminarán los datos relacionados a la Àrea de Conocimiento ' + nombreA + '.');

    // Actualizar el atributo href del botón de eliminación con el idMaestria
    eliminarBtn.data('id', idA);

    modal.modal('show');
});
//Método para enviar la solicitud de eliminar
$(document).on('click', '#eliminarActividadBtn', function () {
    var idActividad = $(this).data('id');
    var idPrograma = $('#idList').val();
    // Actualizar la acción del formulario con el idMaestria
    $('#eliminarAsignaturaForm').attr('action', '/EliminarAsignatura/' + idActividad);

    var formData = $('#eliminarActividadForm').serializeArray();//tomo los datos del array
    formData.push({name: 'idPrograma', value: idPrograma});
    formData.push({name: 'idActividad', value: idActividad});
    $.ajax({
        url: $('#eliminarActividadForm').attr('action'),
        type: 'POST',
        data: formData, // Incluir los datos del formulario en la solicitud
        success: function (response) {
            $('#confirmarEliminarModal').modal('hide');
            location.reload();
            //mostrarMensaje(response, 'success');
        },
        error: function () {
            $('#confirmarEliminarModal').modal('hide');
            // Mostrar mensaje de error en caso de que la solicitud falle
            mostrarMensaje('Error al eliminar la Asignatura.', 'danger');
        }
    });

});

function mostrarMensaje(mensaje, tipo) {
    var alertElement = $('.alert-' + tipo);
    alertElement.text(mensaje).addClass('show').removeClass('d-none');
    setTimeout(function () {
        alertElement.removeClass('show').addClass('d-none');
    }, 5000); // Ocultar el mensaje después de 3 segundos (ajusta el valor según tus necesidades)
}


