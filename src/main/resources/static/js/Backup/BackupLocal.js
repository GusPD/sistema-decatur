$(document).ready(function() {      

    var options = {
        ajax: "/backuplocal/data",
        processing: true,
        serverSide: true,
        paging: false,
        searching: false,
        ordering: false,
        dom: "<'row w-100'<'col-sm-12 mb-4'B>>" +
             "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
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
            { data: 'nombre', title:'Nombre', width: '40%', sortable: false, searchable: false},
            { data: 'tamano', title:'Tamaño', width: '30%', sortable: false, searchable: false},
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false, 
                width: '20%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    if(hasPrivilegeRestaurarBackup === true){
                        if(row.exportado === "false" && row.ubicacion === "nube"){
                            actionsHtml += '<button title="Descargar" type="button" class="btn font-size-small btn-outline-dark descargarBackup-btn btn-sm" ';
                            actionsHtml += ' data-nombre="' + row.nombre + '">';
                            actionsHtml += '<i class="fa-solid fa-cloud-arrow-down"></i></button>';
                            actionsHtml += '<button title="Exportar" type="button" class="btn font-size-small btn-success btn-sm"';
                            actionsHtml += ' data-nombre="' + row.nombre + '">';
                            actionsHtml += '<i class="fa-solid fa-cloud"></i></button>';
                        }else{
                            actionsHtml += '<button title="Restaurar" type="button" class="btn font-size-small btn-outline-primary restaurarBackup-btn btn-sm" ';
                            actionsHtml += 'data-nombre="' + row.nombre + '">';
                            actionsHtml += '<i class="fa-solid fa-gear"></i></button>';
                        }
                    }
                    if(hasPrivilegeExportarBackup === true){
                        if (row.exportado === "true") {
                            actionsHtml += '<button title="Exportar" type="button" class="btn font-size-small btn-success btn-sm"';
                            actionsHtml += ' data-nombre="' + row.nombre + '">';
                            actionsHtml += '<i class="fa-solid fa-cloud"></i></button>';
                        } else if(row.exportado === "false" && row.ubicacion === "local"){
                            actionsHtml += '<button title="Exportar" type="button" class="btn font-size-small btn-outline-dark exportarBackup-btn btn-sm"';
                            actionsHtml += ' data-nombre="' + row.nombre + '">';
                            actionsHtml += '<i class="fa-solid fa-cloud-arrow-up"></i></button>';
                        }
                    }
                    if(hasPrivilegeEliminarBackup === true){
                        if(row.exportado === "false" && row.ubicacion === "local"){
                            actionsHtml += '<button title="Eliminar" type="button" class="btn font-size-small btn-outline-danger eliminarModal-btn btn-sm" data-nombre="' + row.nombre + '">';
                            actionsHtml += '<i class="far fa-trash-alt"></i></button>';
                        }else if(row.exportado === "true" && row.ubicacion === "local"){
                            actionsHtml += '<button title="Eliminar" type="button" class="btn font-size-small btn-outline-danger eliminarModal-btn btn-sm" data-nombre="' + row.nombre + '">';
                            actionsHtml += '<i class="far fa-trash-alt"></i></button>';
                        }
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
            }
        },
        search: true
    }
    var table = $('#backupTable').DataTable(options);
    table.columns.adjust();
    // Método para crear la copia de seguridad
    $(document).on('click', '.crearBackup-btn', function () {
        var formDataArray = $("#formGuardar").serializeArray();
        var url = '/CrearBackup';
        document.getElementById("contenido-pagina-carga").innerHTML = "Generando backup...";
        $("#loadingOverlay").show();
        $.ajax({
            url: url,
            type: 'POST',
            data: formDataArray,
            success: function (response) {
                $("#loadingOverlay").hide();
                table.destroy();
                table = $('#backupTable').DataTable(options);
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                $("#loadingOverlay").hide();
                var errorMessage = xhr.responseText || 'Error al actualizar la empresa.';
                toastr.error(errorMessage);
            }
        });
    });
    //Método para restaurar la copia de seguridad
    $(document).on('click', '.restaurarBackup-btn', function () {
        var nombreArchivo = $(this).data('nombre');
        $('#formRestaurar').attr('action', '/RestaurarBackup?nombre=' + nombreArchivo);
        document.getElementById("contenido-pagina-carga").innerHTML = "Restaurando backup...";
        $("#loadingOverlay").show();
        $.ajax({
            url: $('#formRestaurar').attr('action'),
            type: 'POST',
            data: $('#formRestaurar').serialize(),
            success: function (response) {
                $("#loadingOverlay").hide();
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                $("#loadingOverlay").hide();
                var errorMessage = xhr.responseText || 'Error al restaurar la copia de seguridad.';
                toastr.error(errorMessage);
            }
        });
    });
    //Método para descargar la copia de seguridad
    $(document).on('click', '.descargarBackup-btn', function () {
        var nombreArchivo = $(this).data('nombre');
        $('#formDescargar').attr('action', '/DescargarBackup?nombre='+nombreArchivo);
        document.getElementById("contenido-pagina-carga").innerHTML = "Descargando backup...";
        $("#loadingOverlay").show();
        $.ajax({
            url: $('#formDescargar').attr('action'),
            type: 'POST',
            data: $('#formDescargar').serialize(),
            success: function (response) {
                $("#loadingOverlay").hide();
                table.destroy();
                table = $('#backupTable').DataTable(options);
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                $("#loadingOverlay").hide();
                var errorMessage = xhr.responseText || 'Error al descargar la copia de seguridad.';
                toastr.error(errorMessage);
            }
        });
    });
    //Método para exportar la copia de seguridad
    $(document).on('click', '.exportarBackup-btn', function () {
        var nombreArchivo = $(this).data('nombre');
        $('#formExportar').attr('action', '/ExportarBackup?nombre=' + nombreArchivo);
        document.getElementById("contenido-pagina-carga").innerHTML = "Exportando backup...";
        $("#loadingOverlay").show();
        $.ajax({
            url: $('#formExportar').attr('action'),
            type: 'POST',
            data: $('#formExportar').serialize(),
            success: function (response) {
                $("#loadingOverlay").hide();
                table.destroy();
                table = $('#backupTable').DataTable(options);
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                $("#loadingOverlay").hide();
                var errorMessage = xhr.responseText || 'Error al exportar la copia de seguridad.';
                toastr.error(errorMessage);
            }
        });
    });
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var nombreArchivo = $(this).data('nombre');
        var modal = $('#confirmarEliminarModal');
        var eliminarBtn = modal.find('#eliminarBackupBtn');
        eliminarBtn.data('nombre', nombreArchivo);
        modal.modal('show');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarBackupBtn', function () {
        var nombreArchivo = $(this).data('nombre');
        $('#eliminarBackupForm').attr('action', '/EliminarBackup?nombre=' + nombreArchivo);
        $.ajax({
            url: $('#eliminarBackupForm').attr('action'),
            type: 'POST',
            data: $('#eliminarBackupForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModal').modal('hide');
                table.destroy();
                table = $('#backupTable').DataTable(options);
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                $('#confirmarEliminarModal').modal('hide');
                var errorMessage = xhr.responseText || 'Error al eliminar la copia de seguridad.';
                toastr.error(errorMessage);
            }
        });
    });    
});

