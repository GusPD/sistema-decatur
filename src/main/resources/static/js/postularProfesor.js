$(document).ready(function () {
//Cargar dataTable
    $('#maestriasTable').DataTable({
        ajax: '/maestria/data',
        processing: true,
        serverSide: true,
        dom: "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
                "<'row w-100'<'col-sm-12 my-4'tr>>" +
                "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        columns: [
            {data: 'nombreMaestria', width: '25%'},
            {
                data: 'idPostgrado.nombrePostgrado',
                render: function (data, type, row) {
                    return data || '';
                }, width: '33%'
            },
            {
                data: 'idPostgrado.idFacultad.nombreFacultad',
                render: function (data, type, row) {
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
                    actionsHtml += '<button type="button" title="Postularse" class="btn btn-outline-success abrirModal-btn"';
                    actionsHtml += 'data-bs-target="#crearModal" data-id="' + row.idMaestria + '">';
                    actionsHtml += '<i class="bi bi-check-square"></i></button>';

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

    $(document).on('click', '.abrirModal-btn', function () {
        var idMaestria = $(this).data('id');
        var modal = $('#crearModal');
        var modalBody = modal.find('.modal-body');
        var form = modal.find('form');

        $.ajax({
            url: '/ObtenerCohortesActivas/' + idMaestria,
            type: 'GET',
            success: function (response) {
                // Limpiar el contenido anterior del modal
                modalBody.empty();
                // Verificar si se obtuvieron cohortes activas
                if (response && response.length > 0) {
                    // Crear una lista ul para mostrar las cohortes
                    var ul = $('<ul>').addClass('list-group');
                    // Recorrer las cohortes y agregar cada una como un elemento de la lista
                    response.forEach(function (cohorte) {
                        // Crear un elemento li para la cohorte
                        var li = $('<li>').addClass('list-group-item d-flex justify-content-between align-items-center');
                        // Agregar el nombre de la cohorte
                        li.text(cohorte.nombreCohorte);
                        // Crear un enlace al formulario de inscripción de la cohorte
                        var enlace = $('<a>')
                                .addClass('btn btn-primary confirmar-btn')
                                .text('Postular')
                                .attr('data-idCohorte', cohorte.idCohorte)
                                .attr('id', cohorte.idCohorte);
                        // Agregar el enlace al final del elemento li
                        li.append(enlace);
                        // Agregar el elemento li a la lista ul
                        ul.append(li);
                    });
                    // Agregar la lista ul al modal body
                    modalBody.append(ul);
                } else {
                    // Si no hay cohortes activas, mostrar un mensaje en el modal body
                    modalBody.text('No hay cohortes activas disponibles para inscripción.');
                }
            },
            error: function () {
                alert('Error al obtener las cohortes de la maestría.');
            }
        });
        modal.modal('show');
    });


    $(document).on('click', '.confirmar-btn', function () {
        var fechaActual = new Date();
        var url = '/AgregarPostulado';
        var formDataArray = $('#formGuardar').serializeArray();
        formDataArray.push({name: 'idAspiranteProfesor', value: idAspiranteProfesor});
        formDataArray.push({name: 'idCohorte', value: $(this).attr('data-idCohorte')});
        formDataArray.push({name: 'fechaPostulacion', value: fechaActual});
        var formData = {};
        $.map(formDataArray, function (n, i) {
            formData[n['name']] = n['value'];
        });
        $.ajax({
            url: url,
            type: 'POST',
            data: formData,
            success: function (response) {
                $('#postularModal').modal('hide');  // Cierra el modal
                $('#crearModal').modal('hide');
                mostrarMensaje(response, 'success');
            },
            error: function (xhr, status, error) {
                $('#crearModal').modal('hide');  // Cierra el modal
                var errorMessage = xhr.responseText || 'Error al actualizar la Postulacion.';
                mostrarMensaje(errorMessage, 'danger');
                
            }
        });
    });
    function mostrarMensaje(mensaje, tipo) {
        var alertElement = $('.alert-' + tipo);
        alertElement.text(mensaje).addClass('show').removeClass('d-none');
        setTimeout(function () {
            alertElement.removeClass('show').addClass('d-none');
        }, 5000);
    }
});


