$(document).ready(function() {
    //Cargar dataTable
    $('#coordinadoresTable').DataTable({
        ajax: '/coordinadores/data',
        processing: true,
        serverSide: true,
        dom: "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        columns: [
            { data: 'codCa', width: '25%' },
            { 
                data: 'nombresCa',
                render: function(data, type, row) {
                    return data || '';
                }, width: '33%'
            },
            { 
                data: 'apellidosCa',
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
                    // Aquí puedes construir el HTML para las acciones según tus necesidades
                    var actionsHtml = '';
                    
                    if(hasPrivilegeGestionarCoordinadores === true){
                        var actionsHtml = '<a type="button" class="btn btn-outline-secondary" href="/perfilCoordinadorAcademico/' + row.idCoorAca + '">';
                        actionsHtml += '<i class="bi bi-eye"></i></a>';
                    }
                    
                    if(hasPrivilegeEliminarCoordinadores === true){
                      actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn" data-id="' + row.idCoorAca + '" ';
                      actionsHtml += 'data-nombre="' + row.nombresCa + ' ' + row.apellidosCa +'">';
                      actionsHtml += '<i class="bi bi-trash"></i></button>';
                    }
                    
                    return actionsHtml || '';
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
    
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idCoordinador = $(this).data('id');
        var nombreCoordinador = $(this).data('nombre');

        var modal = $('#confirmarEliminarModal');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarCoordinadorBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar este coordinador?</strong><br>Ten en cuenta que se eliminarán todos los datos relacionados a ' + nombreCoordinador + '.');

        // Actualizar el atributo href del botón de eliminación con el idCoodrinador
        eliminarBtn.data('id', idCoordinador);

        modal.modal('show');
    });
    
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarCoordinadorBtn', function () {        
        var idCoordinador = $(this).data('id');
        // Actualizar la acción del formulario con el idCoordinador
        $('#eliminarCoordinadorForm').attr('action', '/eliminarCoordinadorAcademico/' + idCoordinador);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarCoordinadorForm').attr('action'),
            type: 'POST',
            data: $('#eliminarCoordinadorForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
              $('#confirmarEliminarModal').modal('hide');
              // Recargar el DataTable
              $('#coordinadoresTable').DataTable().ajax.reload();
              // Mostrar el mensaje de éxito del controlador
               mostrarMensaje(response, 'success');
            },
            error: function () {
              $('#confirmarEliminarModal').modal('hide');
              // Mostrar mensaje de error en caso de que la solicitud falle
              mostrarMensaje('Error al eliminar el coordinador.', 'danger');
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
    