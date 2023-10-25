$(document).ready(function() {
    //Tabla
    var idProyecto = $('#proyectoId').data('id');
    var table = $('#pagoTable').DataTable({
        ajax: {
            url: '/pagos/data/' + idProyecto,
            dataSrc: 'data'
        },
        order: [[0, 'asc'],[1, 'asc'],[2, 'asc']],
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
                  columns: [0, 1, 2, 3, 4, 5]
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                title: 'Pagos del proyecto',
                filename: 'Pagos ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3, 4, 5]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Pagos del proyecto',
                filename: 'Pagos ' + getCurrentDateTime(),
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
                width: '7%'
            },
            { data: 'fecha', title:'Fecha', width: '10%' },
            { data: 'recibo', title:'Recibo', width: '8%' },
            { data: 'estado', title:'Estado', width: '15%'},
            { data: 'tipo', title:'Tipo', width: '10%'},
            { data: 'cuenta.banco', title:'Banco', width: '20%'},
            { data: 'monto', title:'Monto', width: '10%'},
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '20%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    if(hasPrivilegeVerPago === true){
                        actionsHtml = '<a type="button" class="btn btn-outline-secondary btn-sm" href="/Recibo/' + row.idPago + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
                    }
                    if(hasPrivilegeEditarPago === true){
                        actionsHtml += '<button type="button" class="btn btn-outline-primary abrirModal-btn btn-sm" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idPago + '" data-modo="actualizar">';
                        actionsHtml += '<i class="far fa-edit"></i></button>';
                    }
                    if(hasPrivilegeEliminarPago === true){
                        actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn btn-sm" data-id="' + row.idPago + '" ';
                        actionsHtml += 'data-cod="' + row.idTerreno + '">';
                        actionsHtml += '<i class="far fa-trash-alt"></i></button>';
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
    $.validator.addMethod(
        "validarNumero",
        function(value, element) {
            return this.optional(element) || /^\d+$/.test(value);
        },
        "Solo se aceptan números"
    );
    $.validator.addMethod(
        "validarMonto",
        function(value, element) {
            return this.optional(element) || /^\d+(\.\d+)?$/.test(value);
        },
        "Ingrese un número válido"
    );
    var formGuardar = $('#formGuardar');
    var validator = $('#formGuardar').validate({
        rules: {
            venta: {
                required: true
            },
            comprobante: {
                required: true
            },
            fecha: {
                required: true
            },
            recibo: {
                required: true,
                validarNumero: true,
                maxlength: 5
            },
            monto: {
                required: true,
                validarMonto: true,
                maxlength: 10
            },
            descuento: {
                validarMonto: true,
                maxlength: 10
            },
            otros: {
                validarMonto: true,
                maxlength: 10
            }  
        },
        messages:{
            venta:{
                required: 'Este campo es requerido'
            },
            comprobante:{
                required: 'Este campo es requerido'
            },
            fecha:{
                required: 'Este campo es requerido'
            },
            recibo:{
                required: 'Este campo es requerido'
            },
            monto:{
                required: 'Este campo es requerido'
            },
            descuento:{
                required: 'Este campo es requerido'
            },
            otros:{
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
            if (element.attr("name") === "venta" || element.attr("name") === "comprobante" || element.attr("name") === "fecha" || element.attr("name") === "recibo" || element.attr("name") === "monto" || element.attr("name") === "descuento" || element.attr("name") === "otros") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var idTerreno = $('#idTerreno').val();
            var idProyecto = $('#proyecto').val();
            var formDataArray = formGuardar.serializeArray();
            var url;
            if (idTerreno) {
                url = '/ActualizarTerreno/'+idProyecto;
                formDataArray.push({name: 'idTerreno', value: idTerreno});
            } else {
                url = '/AgregarTerreno/'+idProyecto;
            }
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');
                    var table = $('#terrenoTable').DataTable();
                    table.ajax.reload(null, false);
                    toastr.success(response);
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al actualizar el terreno.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    //Método para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModal-btn', function () {
        var idPago = $(this).data('id');
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        validator.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        if (idPago) {
            tituloModal.text('Editar Pago');
            $.ajax({
                url: '/ObtenerPago/' + idPago,
                type: 'GET',
                success: function (response) {
                    $('#comprobante').val(response.comprobante);
                    $('#tipo').val(response.tipo);
                    $('#fecha').val(response.fecha);
                    $('#recibo').val(response.recibo);
                    $('#estado').val(response.estado);
                    $('#monto').val(response.monto);
                    $('#otros').val(response.otros);
                    $('#descuento').val(response.descuento);
                    $('#observaciones').val(response.observaciones);
                    $('#cuenta').val(response.cuenta);
                    $('#venta').val(response.venta);
                    $('#idPago').val(response.idPago);
                },
                error: function () {
                    alert('Error al obtener los datos del pago.');
                }
            });
        } else {
            tituloModal.text('Agregar Pago');
            form.attr('action', '/AgregarPago');
            $('#tipo').val('');
            $('#fecha').val('');
            $('#recibo').val('');
            $('#monto').val('');
            $('#otros').val('0.00');
            $('#descuento').val('0.00');
            $('#observaciones').val('');
            $('#cuenta').val('');
            $('#idPago').val('');
            $('#venta').val('');
        }
        modal.modal('show');
    });
    //Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idPago = $(this).data('id');
        var modal = $('#confirmarEliminarModal');
        var eliminarBtn = modal.find('#eliminarPagoBtn');
        eliminarBtn.data('id', idPago);
        modal.modal('show');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarPagoBtn', function () {
        var idPago = $(this).data('id');
        $('#eliminarPagoForm').attr('action', '/EliminarPago/' + idPago);
        $.ajax({
            url: $('#eliminarPagoForm').attr('action'),
            type: 'POST',
            data: $('#eliminarPagoForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModal').modal('hide');
                table.ajax.reload();
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                $('#confirmarEliminarModal').modal('hide');
                var errorMessage = xhr.responseText || 'Error al eliminar el pago.';
                toastr.error(errorMessage);
            }
        });
    });
    //Método para abrir modal de prima
    $(document).on('click', '#btn-prima', function () {
        var modal = $('#crearModal');
        var modalGuardar = $('#crearModalGuardar');
        $("#crearModalLabelPago").text("Agregar Prima");
        $("#tipo").val("Prima");
        $.ajax({
            url: "/obtenerVentasPrima?proyectoId=" + idProyecto,
            type: "GET",
            success: function (data) {
                var selectElement = $("#venta");
                selectElement.empty();
                selectElement.append($("<option>", {value: '', text: 'Seleccione una opción'}));
                for (var i = 0; i < data.length; i++) {
                    selectElement.append($("<option>", {
                        value: data[i].idVenta,
                        text: data[i].terreno.poligono + '-' + data[i].terreno.numero + data[i].terreno.seccion
                    }));
                }
            }
        });
        var selectElement = document.getElementById("comprobante");
        var indexToRemove = 1;
        selectElement.remove(indexToRemove);
        document.getElementById("group-otros").style.display = "none";
        document.getElementById("group-descuento").style.display = "none";
        document.getElementById("otros").removeAttribute("required");
        document.getElementById("descuento").removeAttribute("required");
        modal.modal('hide');
        modalGuardar.modal('show');
    });
    //Método para abrir modal de mantenimiento
    $(document).on('click', '#btn-mantenimiento', function () {
        var modal = $('#crearModal');
        var modalGuardar = $('#crearModalGuardar');
        $("#crearModalLabelPago").text("Agregar Mantenimiento");
        $("#tipo").val("Mantenimiento");
        $.ajax({
            url: "/obtenerVentasMantenimiento?proyectoId=" + idProyecto,
            type: "GET",
            success: function (data) {
                var selectElement = $("#venta");
                selectElement.empty();
                selectElement.append($("<option>", {value: '', text: 'Seleccione una opción'}));
                for (var i = 0; i < data.length; i++) {
                    selectElement.append($("<option>", {
                        value: data[i].idVenta,
                        text: data[i].terreno.poligono + '-' + data[i].terreno.numero + data[i].terreno.seccion
                    }));
                }
            }
        });
        var selectElement = document.getElementById("comprobante");
        var newOption = document.createElement("option");
        newOption.text = "Crédito Fiscal";
        newOption.value = "Crédito Fiscal";
        selectElement.appendChild(newOption);
        document.getElementById("group-otros").style.display = "block";
        document.getElementById("group-descuento").style.display = "block";
        document.getElementById("otros").removeAttribute("required");
        document.getElementById("descuento").removeAttribute("required");
        modal.modal('hide');
        modalGuardar.modal('show');
    });
    //Función para inicializar la libreria de select2
    $( '#venta' ).select2( {
        theme: "bootstrap-5",
        width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
        placeholder: $( this ).data( 'placeholder' ),
        closeOnSelect: false,
    } );
}); 

