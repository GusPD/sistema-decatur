$(document).ready(function() {
    var idProyecto = $('#proyectoId').data('id');
    var table = $('#propietarioTable').DataTable({
        ajax: {
            url: '/propietariosProyecto/data/' + idProyecto,
            dataSrc: 'data'
        },
        order: [[1, 'asc'],[2, 'asc']],
        processing: true,
        serverSide: true,
        dom: "<'row w-100'<'col-sm-12 mb-4'B>>" +
             "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        lengthMenu: [[5, 25, 50, 100, -1], [5, 25, 50, 100, 'Todos']], // Opciones de selección para mostrar registros por página
        pageLength: 5, // Cantidad de registros por página por defecto
        buttons: [
            {
                extend: 'copy',
                text: 'Copiar',
                class: 'btn-sm',
                exportOptions: {
                  columns: [0, 1, 2] // Índices de las columnas que se copiarán
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                class: 'btn-sm',
                title: 'Propietarios del proyecto', // Título del reporte en Excel
                filename: 'Propietarios ' + getCurrentDateTime(), // Nombre del archivo Excel
                exportOptions: {
                  columns: [0, 1, 2] // Índices de las columnas que se exportarán
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                class: 'btn-sm',
                title: 'Propietarios del proyecto', // Título del reporte en PDF
                filename: 'Propietarios ' + getCurrentDateTime(), // Nombre del archivo PDF
                exportOptions: {
                  columns: [0, 1, 2] // Índices de las columnas que se exportarán
                },
                customize: function (doc) {
                  doc.content[1].table.widths = Array(doc.content[1].table.body[0].length + 1).join('*').split('');
                }
            }
        ],
        columns: [
            { data: 'propietario.persona.dui', width: '15%' },
            { data: 'propietario.persona.nombre', width: '25%' },
            { data: 'propietario.persona.apellido', width: '25%' },
            { 
                data: null,
                sortable: false,
                searchable: false,
                width: '15%' ,
                render: function (data, type, row) {
                    var terreno = '';
                    terreno += row.venta.terreno.poligono + '-' + row.venta.terreno.numero + row.venta.terreno.seccion;
                    return terreno;
                }
            },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '20%',
                render: function (data, type, row) {
                    // Aquí puedes construir el HTML para las acciones según tus necesidades
//                    var actionsHtml = '<a type="button" class="btn btn-outline-secondary" href="/DetalleMaestria/' + row.idMaestria + '">';
//                    actionsHtml += '<i class="bi bi-eye"></i></a>';
                    
                    var actionsHtml = '';
                    
                    if(hasPrivilegeVerPropietarioProyecto === true){
                        actionsHtml = '<a type="button" class="btn btn-outline-secondary btn-sm" href="/VerPropietarioVenta/'+ row.venta.terreno.proyecto.idProyecto + '/' + row.propietario.persona.idPersona + '">';
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
            "sInfoFiltered": "",//(filtrado de un total de _MAX_ registros)
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
    table.buttons().container().appendTo('.botonExportar');
    
    // Obtén la referencia al DataTable
    var table = $('#propietarioTable').DataTable();

    // Agrega un evento al filtro de búsqueda
    $('#propietarioTable_filter input').on('keyup', function () {
        // Obtén el valor del filtro de búsqueda
        var searchTerm = $(this).val().trim();

        // Verifica si el valor no está vacío
        if (searchTerm !== '') {
            // Aplica el filtro personalizado en la columna "Dui"
            table.column[0,2].search('^' + searchTerm + '$', true, false).draw();
        } else {
            // Si el valor está vacío, muestra todos los registros
            table.column[0,2].search('').draw();
        }
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

