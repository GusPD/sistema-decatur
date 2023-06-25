$(document).ready(function() {
    //Cargar dataTable
    $('#aspirantesTable').DataTable({
        ajax: '/aspirante/data',
        processing: true,
        serverSide: true,
        dom: "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        columns: [
            { data: 'codAp', width: '25%' },
            { data: 'nombresAp', width: '25%' },
            { data: 'apellidosAp', width: '25%' },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '12%',
                render: function (data, type, row) {
                    // Aquí puedes construir el HTML para las acciones según tus necesidades
                    
                    var actionsHtml = '';
                    if(hasPrivilegeGestionarApirantes === true){
                        actionsHtml = '<a type="button" class="btn btn-outline-secondary" href="/PerfilAspiranteProfesor/' + row.idAspiranteProfesor + '">';
                        actionsHtml += '<i class="bi bi-eye"></i></a>';
                    }
                    if(hasPrivilegeEliminarApirantes === true){
                        actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn" data-id="' + row.idAspiranteProfesor + '" ';
                        actionsHtml += 'data-nombre="' + row.nombresAp +" "+row.apellidosAp+ '">';
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
        var idAspirante = $(this).data('id');
        var nombreAspirante = $(this).data('nombre');

        var modal = $('#confirmarEliminarModal');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarAspiranteBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar el aspirante seleccionado?</strong><br>Ten en cuenta que se eliminarán los datos relacionados al aspirante ' + nombreAspirante + '.');

        // Actualizar el atributo href del botón de eliminación con el idAspirante
        eliminarBtn.data('id', idAspirante);

        modal.modal('show');
    });
    
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarAspiranteBtn', function () {
        
        var idAspirante = $(this).data('id');
        // Actualizar la acción del formulario con el idAspirante
        $('#eliminarAspiranteForm').attr('action', '/EliminarAspiranteProfesor/' + idAspirante);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarAspiranteForm').attr('action'),
            type: 'POST',
            data: $('#eliminarAspiranteForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
              $('#confirmarEliminarModal').modal('hide');
              // Recargar el DataTable
              $('#aspirantesTable').DataTable().ajax.reload();
              // Mostrar el mensaje de éxito del controlador
               mostrarMensaje(response, 'success');
            },
            error: function () {
              $('#confirmarEliminarModal').modal('hide');
              // Mostrar mensaje de error en caso de que la solicitud falle
              mostrarMensaje('Error al eliminar el aspirante.', 'danger');
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



