$(document).ready(function() { 
    //Tabla
    var table = $('#bitacoraTable').DataTable({
        ajax: '/bitacora/data',
        processing: true,
        serverSide: true,
        order: [[3, 'desc']],
        dom: "<'row w-100'<'col-sm-12 mb-4'B>>" +
             "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        lengthMenu: [[5, 25, 50, 100, -1], [5, 25, 50, 100, 'Todos']],
        pageLength: 5,
        buttons: [
            {
                extend: 'copy',
                text: 'Copiar',
                exportOptions: {
                  columns: [0, 1, 2, 3, 4, 5]
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                title: 'Registro de cambios en el sistema',
                filename: 'Bitacora ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3, 4, 5]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Registro de cambios en el sistema',
                filename: 'Bitacora ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3, 4, 5]
                },
                customize: function (doc) {
                  doc.content[1].table.widths = Array(doc.content[1].table.body[0].length + 1).join('*').split('');
                }
            }
        ],
        columns: [
            {
                data: null,
                title: "N°",
                sortable: false,
                searchable: false,
                render: function (data, type, row, meta) {
                    return meta.row + 1;
                },
                width: '10%'
            },
            { data: 'username', title: 'Usuario', width: '20%', class: 'text-center' },
            { 
                data: 'evento', title: 'Evento', width: '30%', class: 'text-center'
            },
            {
                data: 'hora',
                width: '10%',
                title: 'Fecha',
                class: 'text-center',
                render: function(data, type, row) {
                    if (type === 'display' || type === 'filter') {
                        var date = new Date(data);
                        var formattedDate = new Date(date.toLocaleString('en-US', { timeZone: 'UTC' }));
                        var day = formattedDate.getDate();
                        var month = formattedDate.getMonth() + 1;
                        var year = formattedDate.getFullYear();
                        var formattedDateString = day + "/" + month + "/" + year;
                        return formattedDateString;
                    }
                    return data;
                }
            },
            {
                data: 'hora',
                title: 'Hora',
                width: '10%',
                class: 'text-center',
                render: function(data, type, row) {
                  if (type === 'display' || type === 'filter') {
                    var date = new Date(data);
                    var hours = date.getHours();
                    var minutes = date.getMinutes();
                    var ampm = hours >= 12 ? 'PM' : 'AM';
                    hours = hours % 12;
                    hours = hours ? hours : 12;
                    var formattedTime = hours + ':' + (minutes < 10 ? '0' + minutes : minutes) + ' ' + ampm;
                    return formattedTime;
                  }
                  return data;
                }
            },
            { 
                data: 'ipEquipo', title: 'IP Equipo', width: '15%', class: 'text-center'
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
                "copyTitle": "Copiar al portapapeles",
                copySuccess: {
                  _: "%d filas copiadas al portapapeles",
                  1: "1 fila copiada al portapapeles"
                }
            }
        },
        search: {
            return: true
        }
    });
    table.columns.adjust();    
    $('#export-pdf').on('click', function() {
        table.button('.buttons-pdf').trigger();
    });
    $('#export-excel').on('click', function() {
        table.button('.buttons-excel').trigger();
    });
    $('#export-copy').on('click', function() {
        table.button('.buttons-copy').trigger();
    });
    // Función para obtener la fecha y hora actual en formato deseado
    function getCurrentDateTime() {
        var date = new Date();
        var year = date.getFullYear();
        var month = String(date.getMonth() + 1).padStart(2, '0');
        var day = String(date.getDate()).padStart(2, '0');
        var hours = String(date.getHours()).padStart(2, '0');
        var minutes = String(date.getMinutes()).padStart(2, '0');
        var seconds = String(date.getSeconds()).padStart(2, '0');
        return year + month + day + '_' + hours + minutes + seconds;
    }
});
    

