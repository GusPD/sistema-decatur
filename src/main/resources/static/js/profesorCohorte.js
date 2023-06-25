$(document).ready(function () {
    var montoPagarHora = $('#montoPagarHora');
    var unidadHorasImpartir = $('#unidadHorasImpartir');
    var montoTotalServicios = $('#montoTotalServicios');
    var mostrarTotal = $('#total');
    montoPagarHora.on('change', sumarValores);
    unidadHorasImpartir.on('change', sumarValores);

    // Función para sumar
    function sumarValores() {
        var valor1 = parseInt(montoPagarHora.val()) || 0;
        var valor2 = parseInt(unidadHorasImpartir.val()) || 0;
        var suma = (valor1 * valor2);

        montoTotalServicios.val(suma);
        mostrarTotal.val(suma);
    }

    var fechaContratacion = document.getElementById("fechaContratacion");

    $(fechaContratacion).inputmask('99/99/9999', {
        showMaskOnFocus: false,
        showMaskOnHover: false
    });
    $.validator.addMethod("numeroValido", function (value, element) {
        return this.optional(element) || /^\d{0,7}(\.\d{0,2})?$/.test(value);
    }, "Ingresa un número válido con un máximo de 7 dígitos y 2 decimales.");
    $.validator.addMethod("integerOnly", function (value, element) {
        return this.optional(element) || /^\d+$/.test(value);
    }, "Ingresa solo números enteros.");
    $.validator.addMethod("positivos", function (value, element) {
        return this.optional(element) || /^[1-9]\d*$/.test(value);
    }, "Ingresa solo números enteros positivos.");

    $.validator.addMethod("validarFecha", function (value, element) {
        return this.optional(element) || /^(0[1-9]|1\d|2\d|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/.test(value);
    }, "Ingresa una fecha en formato DD/MM/AAAA");

    $(document).on('click', '.abrirModal-btn', function () {
        var id = $(this).data('id');
        validator.resetForm();  // Restablecer la validación
        $('#formGuardar').find('.is-invalid').removeClass('is-invalid');
        $('#idAspiranteProfesor').val(id);
    });

    var formGuardar = $('#formGuardar');
    var validator = $('#formGuardar').validate({
        rules: {
            montoPagarHora: {
                required: true,
                numeroValido: true,
                positivos: true
            },
            unidadHorasImpartir: {
                required: true,
                integerOnly: true,
                positivos: true
            },
            montoTotalServicios: {
                required: true,
                numeroValido: true
            },
            fechaContratacion: {
                required: true,
                validarFecha: true
            },
            idAsignatura: {
                required: true,
            }
        },

        messages: {
            montoPagarHora: {
                required: 'Este campo es requerido'
            },

            unidadHorasImpartir: {
                required: 'Este campo es requerido',
            },
            montoTotalServicios: {
                required: 'Este campo es requerido'
            },

            fechaContratacion: {
                required: 'Este campo es requerido',
            },
            idAsignatura: {
                required: 'Este campo es requerido',
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
            event.preventDefault(); // detiene el evento del envío del formulario
            var idCohorte = $('#idCohorte').val();
            var formDataArray = formGuardar.serializeArray();
            var id = $('#idProfesor').val();
            console.log(formDataArray);
            var url;//valido el tipo de url si editar o crear
            if (id) {
                url = '/ActualizarProfesorCohorte';
                formDataArray.push({name: 'idProfesor', value: id});
            } else {
                url = '/ContratarAspirante';
            }
            var formData = {};
            $.map(formDataArray, function (n, i) {
                formData[n['name']] = n['value'];
            });
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#contratar').modal('hide');
                    mostrarMensaje(response, 'success');
                },
                error: function (xhr, status, error) {
                    $('#contratar').modal('hide');
                    mostrarMensaje(error, 'danger');
                }
            });
        }
    });
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var id = $(this).data('id');
        var nombre = $(this).data('nombre');

        var modal = $('#confirmarEliminarModal');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarProfesorBtn');

        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar El Profesor seleccionada?</strong><br>Ten en cuenta que se eliminarán los datos relacionados a el Profesor ' + nombre + '.');

        eliminarBtn.data('id', id);

        modal.modal('show');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarProfesorBtn', function () {
        var idProfesorCohorte = $(this).data('id');
        console.log(idProfesorCohorte);
        // Actualizar la acción del formulario con el idMaestria
        $('#eliminarForm').attr('action', '/EliminarProfesor/' + idProfesorCohorte);

        var formData = $('#eliminarForm').serializeArray();
        formData.push({name: 'idProfesor', value: idProfesorCohorte});
        console.log(formData);
        $.ajax({
            url: $('#eliminarForm').attr('action'),
            type: 'POST',
            data: formData, // Incluir los datos del formulario en la solicitud
            success: function (response) {
                $('#confirmarEliminarModal').modal('hide');
                mostrarMensaje('Se elimino el profesor con exito.', 'success');
                //location.reload();
            },
            error: function (error) {
                $('#confirmarEliminarModal').modal('hide');
                // Mostrar mensaje de error en caso de que la solicitud falle
                mostrarMensaje('Se Cancelo el contrato con el profesor con exito.', 'success');
            }
        });

    });
    $(document).on('click', '.abrirModalEdit-btn', function () {
        var id = $(this).data('id');
        var modal = $('#contratar');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        validator.resetForm();  // Restablecer la validación
        $('#formGuardar').find('.is-invalid').removeClass('is-invalid');

        $('#formGuardar').validate().resetForm();
        tituloModal.text('Editar Profesor');//titulo del modal
        $.ajax({//utilizo ajax para obtener los datos
            url: '/ObtenerProfesor/' + id,
            type: 'GET',
            success: function (response) {
                $('#montoPagarHora').val(response.montoPagarHora);
                $('#unidadHorasImpartir').val(response.unidadHorasImpartir);
                $('#montoTotalServicios').val(response.montoTotalServicios);
                $('#total').val(response.montoTotalServicios);
                var fecha = new Date(response.fechaContratacion);
                var dia = fecha.getUTCDate().toString().padStart(2, '0');
                var mes = (fecha.getUTCMonth() + 1).toString().padStart(2, '0');
                var año = fecha.getUTCFullYear();
                var fechaFormateada = dia + '/' + mes + '/' + año;
                $('#fechaContratacion').val(fechaFormateada);
                $('#idAspiranteProfesor').val(response.idAspiranteProfesor.idAspiranteProfesor);
                $('#idProfesor').val(id);
            },
            error: function () {
                alert('Error al obtener los datos del Profesor.');
            }
        });

        modal.modal('show');
    });

    function mostrarMensaje(mensaje, tipo) {
        Swal.fire({
            icon: tipo,
            title: mensaje,
            showConfirmButton: true,
            allowOutsideClick: false,
            didClose: function () {
                window.location.reload();
            }
        });
    }

    $(document).on('click', '.modalMaterias-btn', function () {
        var id = $(this).data('id');

        var modal = $('#modalMaterias');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        tituloModal.text('Materias Asignadas');
        cuerpoModal.html(id);
        $.ajax({//utilizo ajax para obtener los datos
            url: '/ObtenerProfesorAsignatura/' + id,
            type: 'GET',
            success: function (response) {
                cuerpoModal.empty(); // Limpiar el contenido anterior del modal

                if (response.length > 0) {
                    response.forEach(function (profesorAsignatura) {
                        cuerpoModal.append(profesorAsignatura.idAsignatura.nombreMateria + "<br>");
                    });
                } else {
                    cuerpoModal.append("No hay datos disponibles.");
                }
            },
            error: function () {
                alert('Error al obtener los datos del Profesor.');
            }
        });



        modal.modal('show');
    });

});


