$(document).ready(function() {
    //Cargar dataTable
    $('#reporteProfesoresContratadosAsignaturaTable').DataTable({
        ajax: '/ProfesoresContratadosAsignatura/data',
        processing: true,
        serverSide: true,
        dom: "<'row w-100'<'col-sm-12 mb-4'B>>" +
             "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        buttons: [
            {
              extend: 'copy',
              text: 'Copiar',
              exportOptions: {
                columns: [0, 1, 2, 3] // Índices de las columnas que se copiarán
              }
            },
            {
              extend: 'excel',
              text: 'Exportar a Excel',
              title: 'Número de estudiantes inscritos por cohorte', // Título del reporte en Excel
              filename: 'Reporte del número de estudiantes inscritos por cohorte ' + getCurrentDateTime(), // Nombre del archivo Excel
              exportOptions: {
                columns: [0, 1, 2, 3] // Índices de las columnas que se exportarán
              }
            },
            {
              extend: 'pdf',
              text: 'Exportar a PDF',
              title: 'Número de estudiantes inscritos por cohorte', // Título del reporte en PDF
              filename: 'Reporte del número de estudiantes inscritos por cohorte ' + getCurrentDateTime(), // Nombre del archivo PDF
              exportOptions: {
                columns: [0, 1, 2, 3] // Índices de las columnas que se exportarán
              },
              customize: function (doc) {
                doc.content[1].table.widths = Array(doc.content[1].table.body[0].length + 1).join('*').split('');
              }
            }
          ],
        columns: [
            { data: 'nombres', width: '20%', class: 'text-center' },
            { 
                data: 'apellidos', width: '20%', class: 'text-center'
            },
            { 
                data: 'nombreMaestria', width: '20%', class: 'text-center'
            },
            { 
                data: 'nombreCohorte', width: '20%', class: 'text-center'
            },
            { 
                data: 'nombreMateria', width: '20%', class: 'text-center'
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

