$(document).ready(function() {
    //Tabla
    var idPropietario = $('#idPropietario').val();
    var table = $('#terrenoTable').DataTable({
        ajax: '/terrenosPropietario/data/'+idPropietario,
        processing: true,
        serverSide: true,
        order: [[1, 'asc']],
        dom: "<'row w-100'<'col-sm-12 mb-4'B>>" +
             "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        lengthMenu: [[5, 25, 50, 100, -1], [5, 25, 50, 100, 'Todos']],
        pageLength: 5,
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
            { data: 'venta.terreno.proyecto.nombre', title: "Proyecto", width: '20%' },
            { data: 'venta.terreno.poligono', title: "Poligono", width: '10%' },
            { data: 'venta.terreno.numero', title: "Número", width: '10%' },
            { data: 'venta.terreno.seccion', title: "Sección", width: '10%' },
            { data: 'venta.terreno.matricula', title: "Matrícula", width: '20%' },
            { data: 'venta.terreno.areaMetros', title: "Área (m²)", width: '10%' },
            { data: 'venta.terreno.areaVaras', title: "Área (v²)", width: '10%' }
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
            }
        },
        search: {
            return: true
        }
    });
}); 

