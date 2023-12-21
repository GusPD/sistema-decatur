$(document).ready(function() {
    //Tabla
    var idPago = $('#idPago').val();
    var table = $('#cuotaMantenimientoTable').DataTable({
        ajax: {
            url: '/cuotaMantenimientoPago/data/' + idPago,
            dataSrc: 'data'
        },
        order: [[1, 'asc']],
        processing: true,
        serverSide: true,
        dom: "<'row w-100'<'col-sm-12 mb-4'B>>" +
             "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        lengthChange: false,
        pageLength: -1,
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
                title: 'Fecha Registro',
                class: 'd-none',
                searchable: false
            },
            {
                data: 'fechaCuota', 
                title: 'Fecha Cuota',
                width: '10.3%',
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
                data: 'cuota',
                title: 'Cuota',
                width: '10.3%',
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
                data: 'recargo',
                title: 'Recargo',
                width: '10.3%',
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
                data: 'descuento',
                title: 'Descuento',
                width: '10.3%',
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
                data: 'otros',
                title: 'Otros',
                width: '10.3%',
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
                title: 'Abono',
                width: '10.3%',
                sortable: false,
                searchable: false,
                render: function (data, type, row) {
                  if (type === 'display' || type === 'filter') {
                    return '$' + parseFloat(row.cuota+row.recargo-row.descuento).toFixed(2);
                  }
                  return data;
                }
            },
            {
                data: null,
                title: 'Pendiente',
                width: '10.3%',
                sortable: false,
                searchable: false,
                render: function (data, type, row) {
                  if (type === 'display' || type === 'filter') {
                    return '$' + parseFloat(row.saldoCuota+row.saldoRecargo).toFixed(2);
                  }
                  return data;
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
        paging: false,
        info: false,
        searching: false,
        search: true,
        ordering: true
    });
    table.columns.adjust();
    //Función para mostrar la vista de impresión del comprobante
    document.getElementById('btn-imprimir').addEventListener('click', function () {
      $("#loadingOverlay").show();
      $.ajax({
          url: '/ComprobantePago/' + idPago,
          method: 'GET',
          success: function (data) {
              document.getElementById('contenedorDePagina').innerHTML=data;
              $("#loadingOverlay").hide();
              $("#reporteModal").modal('show');
          },
          error: function () {
              console.error('Error al cargar la página.');
          }
      });
    });
    //Función para imprimir el estado de cuenta
    document.getElementById('btnImprimir').addEventListener('click', function () {
      var divParaImprimir = $("#contenedorDePagina");
      divParaImprimir.printThis();
    });
});