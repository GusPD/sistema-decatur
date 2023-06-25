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
            { data: 'nombreMaestria', width: '30%' },
            { 
                data: 'idPostgrado.nombrePostgrado',
                render: function(data, type, row) {
                    return data || '';
                }, width: '25%'
            },
            { 
                data: 'idPostgrado.idFacultad.nombreFacultad',
                render: function(data, type, row) {
                    return data || '';
                }, width: '25%'
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
                    if (hasPrivilegeInscribirMaestria) {
                    actionsHtml += '<button type="button" class="btn btn-outline-success abrirModal-btn" ';
                    actionsHtml += 'data-bs-target="#crearModal" data-id="' + row.idMaestria + '">';
                    actionsHtml += 'Inscripción</button>';
                    }
                    if (hasPrivilegeEstudianteMaestria) {
                    actionsHtml += '<button type="button" class="btn btn-outline-warning abrirModalGestionEstudiantes-btn" ';
                    actionsHtml += 'data-id="' + row.idMaestria + '">Estudiantes</button>';
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
    
    // metodo para mostrar el modal segun sea si editar o nuevo registro
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
                        var enlace = $('<a>').addClass('btn btn-primary').attr('href', '/InscripcionEstudiantes/' + cohorte.idCohorte).text('Inscribir');
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
    
    // metodo para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModalGestionEstudiantes-btn', function () {
        var idMaestria = $(this).data('id');
        var modal = $('#gestionEstudiantesModal');
        var modalBody = modal.find('.modal-body');
        var form = modal.find('form');

        $.ajax({
            url: '/ObtenerCohortes/' + idMaestria,
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
                        var enlace = $('<a>').addClass('btn btn-primary').attr('href', '/GestionarEstudiantesCohorte/' + cohorte.idCohorte).text('Gestionar');
                        // Agregar el enlace al final del elemento li
                        li.append(enlace);
                        // Agregar el elemento li a la lista ul
                        ul.append(li);
                    });
                    // Agregar la lista ul al modal body
                    modalBody.append(ul);
                } else {
                    // Si no hay cohortes activas, mostrar un mensaje en el modal body
                    modalBody.text('No hay cohortes creadas en la maestría seleccionada.');
                }
            },
            error: function () {
                alert('Error al obtener las cohortes de la maestría.');
            }
        });
        modal.modal('show');
    });
    
});