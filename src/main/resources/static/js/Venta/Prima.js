$(document).ready(function() {
    //Tabla
    var idVenta = $('#idVenta').val();
    var table = $('#primaTable').DataTable({
        ajax: {
            url: '/primaVenta/data/' + idVenta,
            dataSrc: 'data'
        },
        order: [[2, 'desc'],[1, 'desc']],
        processing: true,
        serverSide: true,
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
                  columns: [0, 2, 3, 4, 5, 6, 7]
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                title: 'Primas de la venta',
                filename: 'Prima ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 2, 3, 4, 5, 6, 7]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Primas de la venta',
                filename: 'Prima ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 2, 3, 4, 5, 6, 7]
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
                width: '7%'
            },
            {
                data: 'fechaRegistro',
                width: '10%',
                title: 'Fecha Registro',
                class: 'd-none',
                searchable: false
            },
            {
                data: 'fecha', 
                width: '10%', 
                title: 'Fecha',
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
                data: 'recibo',
                title: 'Recibo',
                width: '10%',
                render: function (data, type, row) {
                    var claseCSS = '';
                    if(row.estado === false){
                        claseCSS = 'badge bg-danger';
                    }else if(row.comprobante=="Factura"){
                        claseCSS = 'badge bg-azul';
                    }else{
                        claseCSS = 'badge bg-verde';
                    }
                    return '<span class="badge-recibo ' + claseCSS + '">' + data + '</span>';
                }
            },
            {data: 'tipo', title: 'Tipo', width: '15%', searchable: false},
            { data: 'cuentaBancaria.nombre', title:'Tipo Pago', width: '15%', searchable: false},
            {
                data: 'monto',
                title: 'Monto',
                width: '15%',
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
                title: 'Lote',
                sortable: false,
                searchable: false,
                width: '10%',
                render: function (data, type, row) {            
                    return row.venta.terreno.poligono + '-' + row.venta.terreno.numero + row.venta.terreno.seccion;
                }
            },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '28%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    if(hasPrivilegeVerPago === true){
                        actionsHtml = '<a title="Ver" type="button" class="btn btn-outline-secondary btn-sm" href="/Recibo/' + row.idPago + '">';
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
        },
        ordering: {
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