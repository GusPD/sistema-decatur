$(document).ready(function() {
    //Tabla
    var idProyecto = $('#idProyecto').val();
    var table = $('#mantenimientoTable').DataTable({
        ajax: '/InformeMantenimiento/data/'+idProyecto,
        processing: true,
        serverSide: true,
        order: [[1, 'asc']],
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
                  columns: [0, 1, 2, 3, 4, 5, 6, 7]
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                title: 'Monitoreo de Mantenimiento',
                filename: 'Monitoreo de mantenimiento ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3, 4, 5, 6, 7]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Monitoreo de Mantenimiento',
                filename: 'Monitoreo de mantenimiento ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3, 4, 5, 6, 7]
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
                width: '6%'
            },
            { data: 'lote', title: 'Lote', width: '10%'},
            {
                data: 'fechaPago', 
                width: '10.5%', 
                title: 'Fecha Pago',
                searchable: false,
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
                data: 'fechaCuota', 
                width: '10.5%', 
                title: 'Fecha Cuota',
                searchable: false,
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
            { data: 'estado', title: "Estado", width: '15%' },
            {
                data: 'cuota',
                title: 'Cuota',
                width: '10.5%',
                sortable: false,
                searchable: false,
                render: function (data, type, row) {
                  if (type === 'display' || type === 'filter') {
                    return '$' + parseFloat(data).toFixed(2);
                  }
                  return data;
                }
            },
            {
                data: 'multa',
                title: 'Recargo',
                width: '10.5%',
                sortable: false,
                searchable: false,
                render: function (data, type, row) {
                  if (type === 'display' || type === 'filter') {
                    return '$' + parseFloat(data).toFixed(2);
                  }
                  return data;
                }
            },
            {
                data: null,
                title: 'Total',
                width: '10.5%',
                sortable: false,
                searchable: false,
                render: function (data, type, row) {
                  if (type === 'display' || type === 'filter') {
                    return '$' + parseFloat(row.cuota + row.multa).toFixed(2);
                  }
                  return data;
                }
            },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '10.5%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    if(hasPrivilegeVerVenta === true){
                        actionsHtml = '<a  title="Ver" type="button" class="btn font-size-small btn-outline-secondary btn-sm" href="/MantenimientoVenta/' + row.venta.idVenta + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
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
            "sInfoFiltered": "",
            "sInfoPostFix": "",
            "sSearch": "Buscar por estado o lote:",
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

