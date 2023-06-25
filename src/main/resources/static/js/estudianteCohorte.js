$(document).ready(function() {
    //Cargar dataTable
    $('#estudiantesCohorteTable').DataTable({
        ajax: '/estudianteCohorte/list?idCohorte=' + idCohorte,
        processing: true,
        serverSide: true,
        dom: "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        columns: [
            { data: 'idEstudiante.carnetE', width: '20%' },
            { data: 'idEstudiante.nombresE', width: '30%' },
            { data: 'idEstudiante.apellidosE', width: '30%' },
            {
                data: null,
                class:'text-center',
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '20%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    // Aquí puedes construir el HTML para las acciones según tus necesidades
                    actionsHtml += '<a class="btn btn-outline-success" href="/DetalleEstudiante/' + row.idEstudiante.idEstudiante + '">';
                    actionsHtml += 'Perfil</a>';
                    if (hasPrivilegeEliminarEstudianteCohorte) {
                    actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn" data-id="' + row.idEstudiante.idEstudiante + '" ';
                    actionsHtml += 'data-carnet="' + row.idEstudiante.carnetE + '">';
                    actionsHtml += 'Eliminar</button>';
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
    
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idEstudiante = $(this).data('id');
        var carnet = $(this).data('carnet');

        var modal = $('#confirmarEliminarModal');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar el estudiante seleccionado de la cohorte?</strong><br>Ten en cuenta que se eliminarán los datos relacionados al estudiante de carnet ' + carnet + ' de la cohorte.');

        // Actualizar el atributo href del botón de eliminación con el idMaestria
        eliminarBtn.data('id', idEstudiante);

        modal.modal('show');
    });
    
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarBtn', function () {
        
        var idEstudiante = $(this).data('id');
        // Actualizar la acción del formulario con el idMaestria
        $('#eliminarEstudianteCohorteForm').attr('action', '/EliminarEstudianteCohorte/' + idCohorte +'/'+ idEstudiante);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarEstudianteCohorteForm').attr('action'),
            type: 'POST',
            data: $('#eliminarEstudianteCohorteForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
              $('#confirmarEliminarModal').modal('hide');
              // Recargar el DataTable
              $('#estudiantesCohorteTable').DataTable().ajax.reload();
              // Mostrar el mensaje de éxito del controlador
               mostrarMensaje(response, 'success');
            },
            error: function () {
              $('#confirmarEliminarModal').modal('hide');
              // Mostrar mensaje de error en caso de que la solicitud falle
              mostrarMensaje('Error al eliminar el estudiante de la cohorte.', 'danger');
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

