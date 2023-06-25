//total horas
var semanas = $('#duracionSemanas');
var horasT = $('#horasTeoricoSemana');
var horasP = $('#horasPracticaSemana');
var horaCiclo = $('#horasCiclo');

// Detectar cambios en los inputs
semanas.on('change', sumarValores);
horasT.on('change', sumarValores);
horasP.on('change', sumarValores);

// Función para sumar los valores y asignar el resultado
function sumarValores() {
    var valor1 = parseInt(horasT.val()) || 0; // Obtener valor del input 1, convertir a entero
    var valor2 = parseInt(horasP.val()) || 0; // Obtener valor del input 2, convertir a entero
    var valor3 = parseInt(semanas.val()) || 0;
    var suma = (valor1 + valor2) * valor3; // Sumar los valores

    horaCiclo.val(suma); // Asignar el resultado al input de resultado
}

$.validator.addMethod("soloUnoODos", function (value, element) {
    return this.optional(element) || value === "1" || value === "2";
}, "Solo se permiten los números 1 y 2.");

$.validator.addMethod('positiveInteger', function (value, element) {
    return this.optional(element) || /^[1-9]\d*$/.test(value);
}, 'Ingrese un valor positivo entero válido.');
var validator = $('#formGuardar').validate({
    rules: {// reglas
        duracionSemanas: {
            required: true,
            positiveInteger: true,
            maxlength: 4
        },
        horasTeoricoSemana: {
            required: true,
            positiveInteger: true,
            maxlength: 4
        },
        horasPracticaSemana: {
            required: true,
            positiveInteger: true,
            maxlength: 4
        },
        horasCiclo: {
            required: true,
            positiveInteger: true,
            maxlength: 4
        },
        introduccion: {
            required: true
        },
        descripcionPrograma: {
            required: true
        },
        objetivos: {
            required: true
        },
        metodologiaEnsenanza: {
            required: true
        },
        sistemaEvaluacion: {
            required: true
        },
        bibliografia: {
            required: true
        }
    },
    messages: {// mensajes
        duracionSemanas: {
            required: 'Campo Requerido',
            maxlength: 'Maximo 4 Digitos'
        },
        horasTeoricoSemana: {
            required: 'Campo Requerido',
            maxlength: 'Maximo 4 Digitos'
        },
        horasPracticaSemana: {
            required: 'Campo Requerido',
            maxlength: 'Maximo 4 Digitos'
        },
        horasCiclo: {
            required: 'Campo Requerido',
            maxlength: 'Maximo 4 Digitos'
        },
        introduccion: {
            required: 'Campo Requerido'
        },
        descripcionPrograma: {
            required: 'Campo Requerido'
        },
        objetivos: {
            required: 'Campo Requerido'
        },
        metodologiaEnsenanza: {
            required: 'Campo Requerido'
        },
        sistemaEvaluacion: {
            required: 'Campo Requerido'
        },
        bibliografia: {
            required: 'Campo Requerido'
        },
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
        var id = $('#idPrograma').val();//tomo la id
        var formData = $('#formGuardar').serializeArray();//tomo los datos del array
        formData.push({name: 'idProgramAsignatura', value: id});
        var url = '/ActualizarPrograma';
        $.ajax({
            url: url,
            type: 'POST',
            data: formData,
            success: function (response) {
                $('#crearModal').modal('hide');  // Cierra el modal
                location.reload();
                mostrarMensaje(response, 'success');
            },
            error: function (xhr, status, error) {
                $('#crearModal').modal('hide');  // Cierra el modal
                console.log(error);
                var errorMessage = xhr.responseText || 'Error al actualizar la Asignatura.';
                mostrarMensaje(errorMessage, 'danger');
            }
        });
    }
});

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
        tituloModal.text('Editar Asignatura');//titulo del modal
        $.ajax({//utilizo ajax para obtener los datos
            url: '/ObtenerPrograma/' + id,
            type: 'GET',
            success: function (response) {
                $('#duracionSemanas').val(response.duracionSemanas);
                $('#horasTeoricoSemana').val(response.horasTeoricoSemana);
                $('#horasPracticaSemana').val(response.horasPracticaSemana);
                $('#horasCiclo').val(response.horasCiclo);
                $('#introduccion').val(response.introduccion);
                $('#descripcionPrograma').val(response.descripcionPrograma);
                $('#objetivos').val(response.objetivos);
                $('#metodologiaEnsenanza').val(response.metodologiaEnsenanza);
                $('#sistemaEvaluacion').val(response.sistemaEvaluacion);
                $('#bibliografia').val(response.bibliografia);
                $('#idPrograma').val(id);
            },
            error: function () {
                alert('Error al obtener los datos del Programa de la Asignatura.');
            }
        });
    }
    modal.modal('show');
});

function mostrarMensaje(mensaje, tipo) {
    var alertElement = $('.alert-' + tipo);
    alertElement.text(mensaje).addClass('show').removeClass('d-none');
    setTimeout(function () {
        alertElement.removeClass('show').addClass('d-none');
    }, 5000); // Ocultar el mensaje después de 3 segundos (ajusta el valor según tus necesidades)
}
