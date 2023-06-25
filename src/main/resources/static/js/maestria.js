$(document).ready(function() {
    //Cargar dataTable
    $('#maestriasTable').DataTable({
        ajax: '/maestria/data',
        processing: true,
        serverSide: true,
        dom: "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        columns: [
            { data: 'nombreMaestria', width: '25%' },
            { 
                data: 'idPostgrado.nombrePostgrado',
                render: function(data, type, row) {
                    return data || '';
                }, width: '33%'
            },
            { 
                data: 'idPostgrado.idFacultad.nombreFacultad',
                render: function(data, type, row) {
                    return data || '';
                }, width: '30%'
            },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '12%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    // Aquí puedes construir el HTML para las acciones según tus necesidades
                    if (hasPrivilegeAccederMaestria) {
                    actionsHtml = '<a type="button" class="btn btn-outline-secondary" href="/DetalleMaestria/' + row.idMaestria + '">';
                    actionsHtml += '<i class="bi bi-eye"></i></a>';
                    }
                    if (hasPrivilegeEditarMaestria) {
                    actionsHtml += '<button type="button" class="btn btn-outline-warning abrirModal-btn" data-bs-toggle="modal" ';
                    actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idMaestria + '" data-modo="actualizar">';
                    actionsHtml += '<i class="bi bi-pencil-square"></i></button>';
                    }
                    if (hasPrivilegeEliminarMaestria) {
                    actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn" data-id="' + row.idMaestria + '" ';
                    actionsHtml += 'data-nombre="' + row.nombreMaestria + '">';
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
    
    // Agregar regla personalizada para validar el campo nombreMaestria con una expresión regular
    $.validator.addMethod(
        "validarNombreMaestria",
        function(value, element) {
          return this.optional(element) || /^[A-Za-zÁÉÍÓÚáéíóú\s,]+$/.test(value);
        },
        "No se aceptan números ni caracteres especiales"
      );
    
    var formGuardar = $('#formGuardar'); // Almacenar referencia al formulario
    var validator = $('#formGuardar').validate({
        rules: {// reglas
            nombreMaestria: {
                required: true,
                validarNombreMaestria: true,
                maxlength: 100
            },
            idPostgrado:{
                required: true
            }
        },
        messages: {// mensajes
            nombreMaestria: {
                required: 'Este campo es requerido',
                maxlength: "El número máximo de caracteres es 100"
            },
            idPostgrado: {
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
            if (element.attr("name") === "nombreMaestria") {
                error.insertAfter(element);
            } else {
                if (element.attr("name") === "idPostgrado") {
                    error.insertAfter(element);
                }
            }
         },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function (form) {
            event.preventDefault();//detiene el evento del envio del form 
            var idMaestria = $('#idMaestria').val();//tomo la id

            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            var url;//valido el tipo de url si editar o crear
            if (idMaestria) {
                url = '/ActualizarMaestria';
                //meto la id en el campo de envio
                formDataArray.push({name: 'idMaestria', value: idMaestria});
            } else {
                url = '/AgregarMaestria';
            }
            // Convertir el arreglo en un objeto
            var formData = {};
            $.map(formDataArray, function (n, i) {
                formData[n['name']] = n['value'];
            });
            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                success: function (response) {
                    $('#crearModal').modal('hide');  // Cierra el modal
                    var table = $('#maestriasTable').DataTable();
                    table.ajax.reload(null, false); // Recargar sin reiniciar la paginación
                    mostrarMensaje(response, 'success');
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide');  // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar la maestría.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });
    
    // metodo para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModal-btn', function () {
        var idMaestria = $(this).data('id');
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        var btnSumit = document.getElementById('btnSumit');
        validator.resetForm();  // Restablecer la validación
        formGuardar.find('.is-invalid').removeClass('is-invalid');

        if (idMaestria) {
            tituloModal.text('Editar Maestria');//titulo del modal
            $.ajax({//utilizo ajax para obtener los datos
                url: '/ObtenerMaestria/' + idMaestria,
                type: 'GET',
                success: function (response) {
                    $('#nombreMaestria').val(response.nombreMaestria);
                    $('#idMaestria').val(idMaestria);
                    $('#idPostgrado').val(response.idPostgrado.idPostgrado);
                },
                error: function () {
                    alert('Error al obtener los datos de la maestria.');
                }
            });
        } else {
            // en caso de presionar el boton de nuevo solo se abrira el modal
            tituloModal.text('Agregar Maestria');
            form.attr('action', '/AgregarMaestria');
            $('#idMaestria').val('');
            $('#nombreMaestria').val('');
            $('#idPostgrado').val('');
        }
        modal.modal('show');
    });
    
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idMaestria = $(this).data('id');
        var nombreMaestria = $(this).data('nombre');

        var modal = $('#confirmarEliminarModal');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarMaestriaBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar la maestría seleccionada?</strong><br>Ten en cuenta que se eliminarán los datos relacionados a la maestría de ' + nombreMaestria + '.');

        // Actualizar el atributo href del botón de eliminación con el idMaestria
        eliminarBtn.data('id', idMaestria);

        modal.modal('show');
    });
    
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarMaestriaBtn', function () {
        
        var idMaestria = $(this).data('id');
        // Actualizar la acción del formulario con el idMaestria
        $('#eliminarMaestriaForm').attr('action', '/EliminarMaestria/' + idMaestria);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarMaestriaForm').attr('action'),
            type: 'POST',
            data: $('#eliminarMaestriaForm').serialize(), // Incluir los datos del formulario en la solicitud
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
              mostrarMensaje('Error al eliminar la maestría.', 'danger');
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



