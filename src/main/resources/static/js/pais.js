$(document).ready(function() {
    //Cargar dataTable
    $('#paisesTable').DataTable({
        ajax: '/pais/data',
        processing: true,
        serverSide: true,
        dom: "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        columns: [
            { data: 'codigoPais', width: '25%' },
            { data: 'nombrePais', width: '25%' },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '12%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    // Aquí puedes construir el HTML para las acciones según tus necesidades
                    if (hasPrivilegeEditarPaises) {
                    actionsHtml += '<button type="button" class="btn btn-outline-warning abrirModal-btn" data-bs-toggle="modal" ';
                    actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idPais + '" data-modo="actualizar">';
                    actionsHtml += '<i class="bi bi-pencil-square"></i></button>';
                    }
                    if (hasPrivilegeEliminarPaises) {
                    actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn" data-id="' + row.idPais + '" ';
                    actionsHtml += 'data-nombre="' + row.nombrePais + '">';
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
    
    var formGuardar = $('#formGuardar'); // Almacenar referencia al formulario
    var validator = $('#formGuardar').validate({
        rules: {// reglas
            codigoPais: {
                required: true,
                maxlength: 4
            },
            nombrePais:{
                required: true,
                maxlength: 50
            }
        },
        messages: {// mensajes
            codigoPais: {
                required: 'Este campo es requerido',
                maxlength: "El número máximo de caracteres es 4"
            },
            nombrePais: {
                required: 'Este campo es requerido',
                maxlength: "El número máximo de caracteres es 50"
            }
        },
        highlight: function(element) {
            $(element).addClass('is-invalid');
        },
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") === "codigoPais") {
                error.insertAfter(element);
            } else {
                if (element.attr("name") === "nombrePais") {
                    error.insertAfter(element);
                }
            }
         },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function (form) {
            event.preventDefault();//detiene el evento del envio del form 
            var idPais = $('#idPais').val();//tomo la id

            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            var url;//valido el tipo de url si editar o crear
            if (idPais) {
                url = '/ActualizarPais';
                //meto la id en el campo de envio
                formDataArray.push({name: 'idPais', value: idPais});
            } else {
                url = '/AgregarPais';
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
                    var table = $('#paisesTable').DataTable();
                    table.ajax.reload(null, false); // Recargar sin reiniciar la paginación
                    mostrarMensaje(response, 'success');
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide');  // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar el país.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });
    
    // metodo para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModal-btn', function () {
        var idPais = $(this).data('id');
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        var btnSumit = document.getElementById('btnSumit');
        formGuardar.find('.is-invalid').removeClass('is-invalid');

        if (idPais) {
            tituloModal.text('Editar Pais');//titulo del modal
            $.ajax({//utilizo ajax para obtener los datos
                url: '/ObtenerPais/' + idPais,
                type: 'GET',
                success: function (response) {
                    $('#idPais').val(idPais);
                    $('#codigoPais').val(response.codigoPais);
                    $('#nombrePais').val(response.nombrePais);
                },
                error: function () {
                    alert('Error al obtener los datos del pais.');
                }
            });
        } else {
            // en caso de presionar el boton de nuevo solo se abrira el modal
            tituloModal.text('Agregar Pais');
            form.attr('action', '/AgregarPais');
            $('#idPais').val('');
            $('#codigoPais').val('');
            $('#nombrePais').val('');
        }
        modal.modal('show');
    });
    
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idPais= $(this).data('id');
        var nombrePais = $(this).data('nombre');

        var modal = $('#confirmarEliminarModal');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarPaisBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar el país seleccionado?</strong><br>Ten en cuenta que se eliminarán los datos relacionados al país ' + nombrePais + '.');

        // Actualizar el atributo href del botón de eliminación con el idMaestria
        eliminarBtn.data('id', idPais);

        modal.modal('show');
    });
    
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarPaisBtn', function () {
        
        var idPais = $(this).data('id');
        // Actualizar la acción del formulario con el idPais
        $('#eliminarPaisForm').attr('action', '/EliminarPais/' + idPais);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarPaisForm').attr('action'),
            type: 'POST',
            data: $('#eliminarPaisForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
              $('#confirmarEliminarModal').modal('hide');
              // Recargar el DataTable
              $('#paisesTable').DataTable().ajax.reload();
              // Mostrar el mensaje de éxito del controlador
               mostrarMensaje(response, 'success');
            },
            error: function () {
              $('#confirmarEliminarModal').modal('hide');
              // Mostrar mensaje de error en caso de que la solicitud falle
              mostrarMensaje('Error al eliminar el pais.', 'danger');
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



