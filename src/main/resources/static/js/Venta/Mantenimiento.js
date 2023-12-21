$(document).ready(function() {
    //Tabla
    var idVenta = $('#idVenta').val();
    var table = $('#mantenimientoTable').DataTable({
        ajax: {
            url: '/mantenimientoVenta/data/' + idVenta,
            dataSrc: 'data'
        },
        order: [[1, 'asc'],[4, 'asc']],
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
                  columns: [0, 2, 3, 4, 5, 6, 7, 8, 9, 10]
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                title: 'Mantenimiento de la venta',
                filename: 'Mantenimiento ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 2, 3, 4, 5, 6, 7, 8, 9, 10]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Mantenimiento de la venta',
                filename: 'Mantenimiento ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 2, 3, 4, 5, 6, 7, 8, 9, 10]
                },
                customize: function (doc) {
                  doc.pageOrientation = 'landscape';
                  doc.content[1].table.widths = Array(doc.content[1].table.body[0].length + 1).join('*').split('').reverse();
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
                title: 'Fecha Registro',
                class: 'd-none',
                searchable: false
            },
            {
                data: 'pago.fecha', 
                width: '10.3%', 
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
                data: 'pago.recibo',
                title: 'Recibo',
                width: '10.3%',
                render: function (data, type, row) {
                    var claseCSS = '';
                    var claseCSS = '';
                    if(row.estado === false){
                        claseCSS = 'badge bg-danger';
                    }else if(row.comprobante=="Recibo"){
                        claseCSS = 'badge bg-azul';
                    }else if(row.comprobante=="Factura"){
                        claseCSS = 'badge bg-amarillo';
                    }else{
                        claseCSS = 'badge bg-verde';
                    }
                    return '<span class="badge-recibo ' + claseCSS + '">' + data + '</span>';
                }
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
            "sSearch": "Buscar recibo:",
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
        search: true,
        ordering: true
    });
    table.columns.adjust();
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
    //Formulario de agregar
    $.validator.addMethod(
        "validarDocumento",
        function(value, element) {
            return this.optional(element) || /\.csv$/i.test(value);
        },
        "Ingrese un documento en formato .csv"
    );
    var formGuardar = $('#formGuardar');
    var validator = $('#formGuardar').validate({
        rules: {
           documento: {
               required: true,
               validarDocumento: true
           }      
        },
        messages:{
            documento:{
                required: 'Este campo es requerido'
            }    
        },
        highlight: function(element) {
            $(element).addClass('is-invalid');
        },
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") === "documento") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var idVenta = $('#idVenta').val();
            var formDataArray = new FormData(formGuardar[0]);
            var url = '/AgregarInformeMantenimientoVenta';
            formDataArray.append('idVenta', idVenta);
            $('#crearModal').modal('hide');
            document.getElementById("contenido-pagina-carga").innerHTML = "Agregando cuotas...";
            $("#loadingOverlay").show();
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                processData: false,
                contentType: false,
                success: function (response) {
                    $("#loadingOverlay").hide();
                    $(".form-control").val("");
                    toastr.success(response);
                    table.ajax.reload();
                },
                error: function (xhr, status, error) {
                    $("#loadingOverlay").hide();
                    var errorMessage = xhr.responseText || 'Error al agregar el estado de cuenta.';
                    if (errorMessage.includes('Errores en los registros:')) {
                        errorMessage = errorMessage.replace("Errores en los registros:", "");
                        var lineas = errorMessage.trim().split('\n');
                        var lista = document.createElement("ul");
                        for (var i = 0; i < lineas.length; i++) {
                            var li = document.createElement("li");
                            li.textContent = lineas[i].trim();
                            lista.appendChild(li);
                        }
                        var listaMensajesErrorElement = document.getElementById("listaMensajesError");
                        listaMensajesErrorElement.innerHTML = "";
                        listaMensajesErrorElement.appendChild(lista);
                        $("#mensajeErrores").modal('show');
                    }else{
                        toastr.error(errorMessage);
                    }
                    $(".form-control").val("");
                }
            });
        }
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarBtn', function () {
        var idVenta = $("#idVenta").val();
        $('#eliminarForm').attr('action', '/EliminarInformeMantenimientoVenta/' + idVenta);
        $.ajax({
            url: $('#eliminarForm').attr('action'),
            type: 'POST',
            data: $('#eliminarForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModal').modal('hide');
                toastr.success(response);
                table.ajax.reload();
            },
            error: function (xhr, status, error) {
                $('#confirmarEliminarModal').modal('hide');
                var errorMessage = xhr.responseText || 'Error al eliminar el documento.';
                toastr.error(errorMessage);
            }
        });
    });
    //Función para mostrar la vista de impresión del estado de cuenta
    document.getElementById('btn-imprimir').addEventListener('click', function () {
        document.getElementById("contenido-pagina-carga").innerHTML = "Generando reporte...";
        $("#loadingOverlay").show();
        $.ajax({
            url: '/EstadoCuentaMantenimiento/' + idVenta,
            method: 'GET',
            success: function (data) {
                document.getElementById('contenedorDePagina').innerHTML=data;
                var table = $('#estadoCuentaTable').DataTable({
                    pageLength: -1,
                    paging: false,
                    lengthChange: false,
                    info: false,
                    searching: false,
                    ordering: false,
                    columnDefs: [
                        {
                            targets: 'center',
                            className: 'dt-center'
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
                    }
                });
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