$(document).ready(function() { 
    $('#planEstudioTable').DataTable({
        ajax: '/planEstudio/data?idMaestria=' + idMaestria,
        processing: true,
        serverSide: true,
        order: [[2, 'desc']],
        dom: "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        columns: [
            { data: 'codPlan', width: '35%' },
            { 
                data: 'anio',width: '20%'
            },
            { 
                data: 'planEstado',
                width: '25%',
                render: function (data, type, row) {
                    var estado = (data === 1) ? 'Activo' : 'Inactivo';
                    return estado;
                }
            },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '20%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    // Aquí puedes construir el HTML para las acciones según tus necesidades
                    if (hasPrivilegeAccederPlan) {
                    actionsHtml += '<a type="button" class="btn btn-outline-secondary" href="/DetallePlanEstudio/' + row.idPlanEstudio + '">';
                    actionsHtml += '<i class="bi bi-eye"></i></a>';
                    }
                    if (hasPrivilegeEditarPlan) {
                    actionsHtml += '<button type="button" class="btn btn-outline-warning abrirModal-btn" data-bs-toggle="modal" ';
                    actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idPlanEstudio + '" data-modo="actualizar">';
                    actionsHtml += '<i class="bi bi-pencil-square"></i></button>';
                    }
                    if (hasPrivilegeEliminarPlan) {
                    actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn" data-id="' + row.idPlanEstudio + '" ';
                    actionsHtml += 'data-cod="' + row.codPlan + '">';
                    actionsHtml += '<i class="bi bi-trash"></i></button>';
                    }
              
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
            "sInfoFiltered": "",
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
    
    // Agregar regla personalizada para validar el campo nombreMaestria con una expresión regular
    $.validator.addMethod(
        "validarCodPlan",
        function(value, element) {
          return this.optional(element) || /^[A-Za-z0-9]{1,6}$/.test(value);
        },
        "El campo debe contener como máximo 6 caracteres y solo puede incluir letras y números"
      );
      
    $.validator.addMethod(
        "maxTwoDecimals",
        function(value, element) {
          return this.optional(element) || /^\d+(\.\d{1,2})?$/.test(value);
        },
        "Por favor, ingresa un número con máximo 2 decimales"
      );
      
    $.validator.addMethod(
        "validarTituloOtorgar",
        function(value, element) {
          return this.optional(element) || /^[A-Za-zÁÉÍÓÚáéíóú\s]+$/.test(value);
        },
        "No se aceptan números ni caracteres especiales"
      );
      
    $.validator.addMethod(
        "validarMaximoAnioActual",
        function(value, element) {
            var anioActual = new Date().getFullYear();
            return this.optional(element) || (parseInt(value) <= anioActual);
        },
        "El año debe ser igual o anterior al año actual"
    );
    
    var formGuardar = $('#formGuardar'); // Almacenar referencia al formulario
    var validator = $('#formGuardar').validate({
        rules: {// reglas
            codPlan: {
                required: true,
                validarCodPlan: true
            },
            cumMinimo: {
                maxTwoDecimals: true
            },
            notaMinimaAprobacion: {
                maxTwoDecimals: true
            },
            totalAsignaturas: {
                required: true
              },
            totalUv: {
                required: true
              },
            duracionCarrera: {
                required: true
              },
            tituloOrtorgar: {
                required: true,
                validarTituloOtorgar: true,
                maxlength: 100
            },
            anio: {
                required: true,
                validarMaximoAnioActual: true
            }
        },
        messages: {// mensajes
            codPlan: {
                required: 'Este campo es requerido'
            },
            modalidad: {
                required: 'Este campo es requerido'
            },
            cumMinimo: {
                required: 'Este campo es requerido',
                min: 'Por favor, ingresa un valor mayor o igual a 0',
                max: 'Por favor, ingresa un valor menor o igual a 10',
                maxTwoDecimals: 'Por favor, ingresa un número con máximo 2 decimales',
                number: 'Por favor, ingresa un número válido'
             },
            notaMinimaAprobacion: {
                required: 'Este campo es requerido',
                min: 'Por favor, ingresa un valor mayor o igual a 0',
                max: 'Por favor, ingresa un valor menor o igual a 10',
                maxTwoDecimals: 'Por favor, ingresa un número con máximo 2 decimales',
                number: 'Por favor, ingresa un número válido'
            },
            totalAsignaturas: {
                required: 'Este campo es requerido',
                min: 'Por favor, ingresa un valor mayor o igual a 0',
                step: 'Por favor, ingresa un número entero',
                number: 'Por favor, ingresa un número válido',
                max: 'Por favor, ingresa un valor menor'
              },
            totalUv: {
                required: 'Este campo es requerido',
                min: 'Por favor, ingresa un valor mayor o igual a 0',
                step: 'Por favor, ingresa un número entero',
                number: 'Por favor, ingresa un número válido',
                max: 'Por favor, ingresa un valor menor'
              },
            duracionCarrera: {
                required: 'Este campo es requerido',
                min: 'Por favor, ingresa un valor mayor o igual a 10',
                step: 'Por favor, ingresa un número entero',
                number: 'Por favor, ingresa un número válido',
                max: 'Por favor, ingresa un valor menor'
              },
            tituloOrtorgar: {
                required: 'Este campo es requerido',
                maxlength: "El número máximo de caracteres es 100"
            },
            anio: {
                required: 'Este campo es requerido',
                step: 'Por favor, ingresa un número entero',
                number: 'Por favor, ingresa un número válido',
            }
        },
        highlight: function(element) {
            $(element).addClass('is-invalid');
        },
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") === "codPlan" || element.attr("name") === "modalidad" || element.attr("name") === "cumMinimo"|| element.attr("name") === "notaMinimaAprobacion"|| element.attr("name") === "totalAsignaturas"|| element.attr("name") === "totalUv"|| element.attr("name") === "duracionCarrera"|| element.attr("name") === "tituloOrtorgar"|| element.attr("name") === "anio") {
                error.insertAfter(element);
              }
         },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault(); // detiene el evento del envío del formulario
            var idPlanEstudio = $('#idPlanEstudio').val(); // tomo la id

            var formDataArray = formGuardar.serializeArray(); // tomo los datos del array

            var url; // valido el tipo de URL si editar o crear
            if (idPlanEstudio) {
                url = '/ActualizarPlanEstudio';
                formDataArray.push({ name: 'idPlanEstudio', value: idPlanEstudio });
                formDataArray.push({ name: 'idMaestria', value: idMaestria });
            } else {
                url = '/AgregarPlanEstudio';
                formDataArray.push({ name: 'idMaestria', value: idMaestria });
            }
            // Convertir el arreglo en un objeto
            var formData = {};
            $.map(formDataArray, function(n, i) {
                formData[n['name']] = n['value'];
            });
            // realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                success: function(response) {
                    $('#crearModal').modal('hide'); // Cierra el modal
                    var table = $('#planEstudioTable').DataTable();
                    table.ajax.reload(null, false); // Recargar sin reiniciar la paginación
                    mostrarMensaje(response, 'success');
                },
                error: function(xhr, status, error) {
                    $('#crearModal').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar el plan de estudio.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });
    
    // metodo para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModal-btn', function () {
        var idPlanEstudio = $(this).data('id');
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        var btnSumit = document.getElementById('btnSumit');
        validator.resetForm();  // Restablecer la validación
        formGuardar.find('.is-invalid').removeClass('is-invalid');

        if (idPlanEstudio) {
            tituloModal.text('Editar Plan de Estudio');//titulo del modal
            $.ajax({//utilizo ajax para obtener los datos
                url: '/ObtenerPlanEstudio/' + idPlanEstudio,
                type: 'GET',
                success: function (response) {
                    $('#idPlanEstudio').val(response.idPlanEstudio);
                    $('#idMaestria').val(response.idMaestria);
                    $('#codPlan').val(response.codPlan);
                    $('#modalidad').val(response.modalidad);
                    $('#cumMinimo').val(response.cumMinimo);
                    $('#notaMinimaAprobacion').val(response.notaMinimaAprobacion);
                    $('#totalAsignaturas').val(response.totalAsignaturas);
                    $('#totalUv').val(response.totalUv);
                    $('#duracionCarrera').val(response.duracionCarrera);
                    $('#tituloOrtorgar').val(response.tituloOrtorgar);
                    $('#anio').val(response.anio);
                },
                error: function () {
                    alert('Error al obtener los datos de la maestria.');
                }
            });
        } else {
            // en caso de presionar el boton de nuevo solo se abrira el modal
            tituloModal.text('Agregar Plan de Estudio');
            form.attr('action', '/AgregarPlanEstudio');
            $('#idPlanEstudio').val('');
            $('#idMaestria').val('');
            $('#codPlan').val('');
            $('#modalidad').val('');
            $('#cumMinimo').val('');
            $('#notaMinimaAprobacion').val('');
            $('#totalAsignaturas').val('');
            $('#totalUv').val('');
            $('#duracionCarrera').val('');
            $('#tituloOrtorgar').val('');
            $('#anio').val('');
        }
        modal.modal('show');
    });
    
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idPlanEstudio = $(this).data('id');
        var codPlan = $(this).data('cod');

        var modal = $('#confirmarEliminarModal');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarPlanEstudioBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar el plan de estudios seleccionado?</strong><br>Ten en cuenta que se eliminarán los datos relacionados al plan de estudios ' + codPlan + '.');

        // Actualizar el atributo href del botón de eliminación con el idMaestria
        eliminarBtn.data('id', idPlanEstudio);

        modal.modal('show');
    });
    
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarPlanEstudioBtn', function () {
        
        var idPlanEstudio = $(this).data('id');
        // Actualizar la acción del formulario con el idMaestria
        $('#eliminarPlanEstudioForm').attr('action', '/EliminarPlanEstudio/' + idPlanEstudio);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarPlanEstudioForm').attr('action'),
            type: 'POST',
            data: $('#eliminarPlanEstudioForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
              $('#confirmarEliminarModal').modal('hide');
              // Recargar el DataTable
              $('#maestriasTable').DataTable().ajax.reload();
              // Mostrar el mensaje de éxito del controlador
               mostrarMensaje(response, 'success');
            },
            error: function () {
              $('#confirmarEliminarModal').modal('hide');
              // Mostrar mensaje de error en caso de que la solicitud falle
              mostrarMensaje('Error al eliminar el plan de estudio.', 'danger');
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

    

