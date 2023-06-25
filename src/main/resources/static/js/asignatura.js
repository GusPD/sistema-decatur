$(document).ready(function () {
    $('#asignaturaTable').DataTable({
        ajax: '/Asignatura/data?idMallaCurricular=' + idMallaCurricular,
        processing: true,
        serverSide: true,
        dom: "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
                "<'row w-100'<'col-sm-12 my-5'tr>>" +
                "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        columns: [
            {data: 'numeroCorrelativo'},
            {data: 'codAsignatura'},
            {data: 'nombreMateria'},
            {data: 'unidadesValorativas'},
            {data: 'ciclo'},
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                className: 'd-flex justify-content-around',
                render: function (data, type, row) {
                    // Aquí puedes construir el HTML para las acciones según tus necesidades
                    var actionsHtml = '';
                    actionsHtml += '<a type="button" title="Programa" class="btn btn-outline-secondary"';
                    actionsHtml += 'title="Programa" href="/viewPrograma/' + row.idProgramAsignatura.idProgramAsignatura + '">';
                    actionsHtml += '<i class="bi bi-arrow-up-right-square"></i></a>';

                    actionsHtml += '<a type="button" title="Actividades" class="btn btn-outline-secondary"';
                    actionsHtml += 'title="Actividades" href="/viewActividad/' + row.idProgramAsignatura.idProgramAsignatura + '">';
                    actionsHtml += '<i class="bi bi-card-checklist"></i></a>';

                    //if(hasPrivilegeAdmin == true){
                    actionsHtml += '<button title="Actualizar" type="button" class="btn btn-outline-primary abrirModal-btn" data-bs-toggle="modal" ';
                    actionsHtml += 'data-bs-target="#editarModal" data-tipo="editar" data-id="' + row.idAsignatura + '" data-modo="actualizar">';
                    actionsHtml += '<i class="bi bi-pencil-square"></i></button>';
                    //}

                    actionsHtml += '<button type="button" title="Eliminar" class="btn btn-outline-danger eliminarModal-btn" data-id="' + row.idAsignatura + '" ';
                    actionsHtml += 'data-nombre="' + row.nombreMateria + '">';
                    actionsHtml += '<i class="bi bi-trash"></i></button>';

                    return actionsHtml;
                }
            }
        ],
        language: {
            "sProcessing": "Procesando...",
            "sLengthMenu": "Mostrar _MENU_ registros",
            "sZeroRecords": "No se encontraron resultados",
            "sEmptyTable": "Ningún dato disponible en esta tabla",
            "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
            "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
            "sInfoPostFix": "",
            "sSearch": "Buscar:",
            "sUrl": "",
            "sInfoThousands": ",",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst": "Primero",
                "sLast": "Último",
                "sNext": "Siguiente",
                "sPrevious": "Anterior"
            },
            "oAria": {
                "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
            },
            "buttons": {
                "copy": "Copiar",
                "colvis": "Visibilidad"
            }
        },
        search: {
            return: true
        }
    });
    //total horas
    var semanas = $('#duracion');
    var horasT = $('#horasT');
    var horasP = $('#horasP');
    var horaCiclo = $('#horaCiclo');

    // Detectar cambios en los inputs
    semanas.on('change', sumarValores)
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
    $.validator.addMethod("maxValue", function (value, element, params) {
        return this.optional(element) || parseInt(value) <= params;
    }, "Por favor, ingrese un número menor o igual a 100.");
    var validator = $('#formGuardar').validate({
        rules: {// reglas
            codigoAsignatura: {
                required: true
            },
            nombreAsignatura: {
                required: true
            },
            uv: {
                required: true,
                positiveInteger: true,
                maxlength:3
            },
            numeroCorrelativo: {
                required: true,
                positiveInteger: true,
                maxlength:3
            },
            ciclo: {
                required: true,
                positiveInteger: true,
            },
            idAreaC: {
                required: true
            },
            duracion: {
                required: true,
                positiveInteger: true,
                maxlength:4
            },
            horasT: {
                required: true,
                positiveInteger: true,
                maxlength:4
            },
            horasP: {
                required: true,
                positiveInteger: true,
                maxlength:4
            },
            horaCiclo: {
                required: true,
                positiveInteger: true,
                maxlength:4
            },
            introduccion: {
                required: true
            },
            descipcionPrograma: {
                required: true
            },
            objetivo: {
                required: true
            },
            metodologia: {
                required: true
            },
            sistemaEvaluacion: {
                required: true
            },
            bibliografia: {
                required: true
            },
            "actividad[]": {
                required: true,
                minlength: 1
            },
            "ponderacion[]": {
                required: true,
                minlength: 1,
                maxValue: 100,
                positiveInteger: true
            }
        },
        messages: {// mensajes
            codigoAsignatura: {
                required: 'Campo Requerido'
            },
            nombreAsignatura: {
                required: 'Campo Requerido'
            },
            uv: {
                required: 'Campo Requerido',
                maxlength:'Maximo 3 Digitos'
            },
            numeroCorrelativo: {
                required: 'Campo Requerido',
                maxlength:'Maximo 3 Digitos'
            },
            ciclo: {
                required: 'Campo Requerido'
            },
            idAreaC: {
                required: 'Campo Requerido'
            },
            duracion: {
                required: 'Campo Requerido',
                maxlength:'Maximo 4 Digitos'
            },
            horasT: {
                required: 'Campo Requerido',
                maxlength:'Maximo 4 Digitos'
            },
            horasP: {
                required: 'Campo Requerido',
                maxlength:'Maximo 4 Digitos'
            },
            horaCiclo: {
                required: 'Campo Requerido',
                maxlength:'Maximo 4 Digitos'
            },
            introduccion: {
                required: 'Campo Requerido'
            },
            descipcionPrograma: {
                required: 'Campo Requerido'
            },
            objetivo: {
                required: 'Campo Requerido'
            },
            metodologia: {
                required: 'Campo Requerido'
            },
            sistemaEvaluacion: {
                required: 'Campo Requerido'
            },
            bibliografia: {
                required: 'Campo Requerido'
            },
            actividad: {
                required: 'Campo Requerido'
            },
            ponderacion: {
                required: 'Campo Requerido'
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
            var ponderaciones = $('.ponderaciones-class').map(function () {
                return parseFloat($(this).val());
            }).get();

            var sumaPonderaciones = ponderaciones.reduce(function (a, b) {
                return a + b;
            }, 0);

            if (sumaPonderaciones <= 100 && sumaPonderaciones >= 100) {
                var idAsignatura = $('#asignaturaId').val();
                var mallaId = $('#mallaId').val();
                var formData = $('#formGuardar').serializeArray();
                formData.push({name: 'mallaId', value: mallaId});

                if (idAsignatura) {
                    url = '/ActualizarAsignatura';
                    formData.push({name: 'idAsignatura', value: idAsignatura});
                } else {
                    url = '/AgregarAsignatura';
                }

                $.ajax({
                    url: url,
                    type: 'POST',
                    data: formData,
                    success: function (response) {
                        $('#crearModal').modal('hide');
                        var table = $('#asignaturaTable').DataTable();
                        table.ajax.reload(null, false);
                        mostrarMensaje(response, 'success');
                    },
                    error: function (xhr, status, error) {
                        $('#crearModal').modal('hide');
                        console.log(error);
                        var errorMessage = xhr.responseText || 'Error al guardar la Asignatura.';
                        mostrarMensaje(errorMessage, 'danger');
                    }
                });
            } else {
                mostrarMensaje('La suma de las ponderaciones no puede exceder 100.', 'danger');
            }
        }

    });

    var validatorEditar = $('#formGuardarEditar').validate({
        rules: {// reglas
            codAsignatura: {
                required: true
            },
            nombreMateria: {
                required: true
            },
            unidadesValorativas: {
                required: true,
                positiveInteger: true,
                maxlength:3
            },
            numeroCorrelativo: {
                required: true,
                positiveInteger: true,
                maxlength:3
            },
            ciclo: {
                required: true,
                positiveInteger: true,
            },
            idAreaConocimiento: {
                required: true
            }
        },
        messages: {// mensajes
            codAsignatura: {
                required: 'Campo Requerido'
            },
            nombreMateria: {
                required: 'Campo Requerido'
            },
            unidadesValorativas: {
                required: 'Campo Requerido',
                maxlength:'Maximo 3 Digitos'
            },
            numeroCorrelativo: {
                required: 'Campo Requerido',
                maxlength:'Maximo 3 Digitos'
            },
            ciclo: {
                required: 'Campo Requerido'
            },
            idAreaConocimiento: {
                required: 'Campo Requerido'
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
            var id = $('#asignaturaIdEditar').val();//tomo la id
            var formData = $('#formGuardarEditar').serializeArray();//tomo los datos del array
            var url;//valido el tipo de url si editar o crear
            url = '/ActualizarAsignatura';
            //meto la id en el campo de envio
            formData.push({name: 'idAsignatura', value: id});

            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                success: function (response) {
                    $('#editarModal').modal('hide');  // Cierra el modal
                    var table = $('#asignaturaTable').DataTable();
                    table.ajax.reload(null, false); // Recargar sin reiniciar la paginación
                    mostrarMensaje(response, 'success');
                },
                error: function (xhr, status, error) {
                    $('#editarModal').modal('hide');  // Cierra el modal
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

        if (id) {
            validatorEditar.resetForm();  // Restablecer la validación
            $('#formGuardarEditar').find('.is-invalid').removeClass('is-invalid');
            $('#formGuardarEditar').validate().resetForm();//quita los mensajes de error 
            var modal = $('#editarModal');
            var tituloModal = modal.find('.modal-title');
            tituloModal.text('Editar Asignatura');//titulo del modal
            $.ajax({//utilizo ajax para obtener los datos
                url: '/ObtenerAsignatura/' + id,
                type: 'GET',
                success: function (response) {
                    $('#formGuardarEditar #codAsignatura').val(response.codAsignatura);
                    $('#formGuardarEditar #nombreMateria').val(response.nombreMateria);
                    $('#formGuardarEditar #unidadesValorativas').val(response.unidadesValorativas);
                    $('#formGuardarEditar #numeroCorrelativo').val(response.numeroCorrelativo);
                    $('#formGuardarEditar #ciclo').val(response.ciclo);
                    $('#formGuardarEditar #idAreaConocimiento').val(response.idAreaConocimiento.idAreaConocimiento);
                    $('#formGuardarEditar #asignaturaIdEditar').val(id);
                },
                error: function () {
                    alert('Error al obtener los datos de la Asignatura.');
                }
            });
        } else {
            validator.resetForm();  // Restablecer la validación
            $('#formGuardar').find('.is-invalid').removeClass('is-invalid');
            $('#formGuardar').validate().resetForm();//quita los mensajes de error 
            // en caso de presionar el boton de nuevo solo se abrira el modal
            var modal = $('#crearModal');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            tituloModal.text('Agregar Asignatura');
            form.attr('action', '/AgregarAsignatura');
            $('#codigoAsignatura').val('');
            $('#nombreAsignatura').val('');
            $('#uv').val('');
            $('#numeroCorrelativo').val('');
            $('#ciclo').val('');
            $('#idAreaC').val('');
            $('#duracion').val('');
            $('#horasT').val('');
            $('#horasP').val('');
            $('#horaCiclo').val('');
            $('#introduccion').val('');
            $('#descipcionPrograma').val('');
            $('#objetivo').val('');
            $('#metodologia').val('');
            $('#sistemaEvaluacion').val('');
            $('#bibliografia').val('');
            $('#actividad').val('');
            $('#ponderacion').val('');
            $('#actividades-extra').empty();
        }
        modal.modal('show');
    });

    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idAsignatura = $(this).data('id');
        var nombreAsignatura = $(this).data('nombre');

        var modal = $('#confirmarEliminarModal');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarAsignaturaBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar la Asignatura seleccionada?</strong><br>Ten en cuenta que se eliminarán los datos relacionados a la Asignatura ' + nombreAsignatura + '.');

        // Actualizar el atributo href del botón de eliminación con el idMaestria
        eliminarBtn.data('id', idAsignatura);

        modal.modal('show');
    });

    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarAsignaturaBtn', function () {
        var idAsignatura = $(this).data('id');
        // Actualizar la acción del formulario con el idMaestria
        $('#eliminarAsignaturaForm').attr('action', '/EliminarAsignatura/' + idAsignatura);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarAsignaturaForm').attr('action'),
            type: 'POST',
            data: $('#eliminarAsignaturaForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
                $('#confirmarEliminarModal').modal('hide');
                // Recargar el DataTable
                $('#asignaturaTable').DataTable().ajax.reload();
                // Mostrar el mensaje de éxito del controlador
                mostrarMensaje(response, 'success');
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
    // Función para añadir un nuevo campo de entrada de actividad
    function agregarActividad() {
        var container = $("#actividades-extra");

        var divA = $("<div>").addClass("form-group col-6");
        var divP = $("<div>").addClass("form-group col-6");
        //"Nombre de la Actividad""Ponderaciòn de la Actividad"
        var actividad = $("<input>").attr({
            type: "text",
            class: "form-control",
            name: "actividad[]",
            id: "actividad",
            placeholder: "Nombre de la Actividad"
        });
        var ponderacion = $("<input>").attr({
            type: "number",
            id: "ponderacion",
            class: "form-control ponderaciones-class",
            name: "ponderacion[]",
            placeholder: "Ponderaciòn de la Actividad"
        });

        divA.append(actividad);
        divP.append(ponderacion);
        container.append(divA);
        container.append(divP);
    }

    // Evento de clic para añadir actividad
    $("#btnAgregarActividad").on('click', function () {
        var suma = 0;
        $('.ponderaciones-class').each(function () {
            suma += parseInt($(this).val());
        });
        if (suma === 100) {
            $(this).prop('disabled', true);
        } else {
            $(this).prop('disabled', false);
            agregarActividad();
        }



    });

    $('.ponderaciones-class').on('input', function () {
        var suma = 0;
        $(this).each(function () {
            suma += parseInt($(this).val());
        });
        if (suma === 100) {
            $("#btnAgregarActividad").prop('disabled', true);
        } else {
            $("#btnAgregarActividad").prop('disabled', false);
        }
    });





});





