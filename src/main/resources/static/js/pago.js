$(document).ready(function() {
    //Tabla
    var fechaInicio = $("#b_fecha_inicio").length > 0 ? $("#b_fecha_inicio").val() : '';
    var fechaFin = $("#b_fecha_fin").length > 0 ? $("#b_fecha_fin").val() : '';
    var comprobante = $("#b_comprobante").length > 0 ? $("#b_comprobante").val() : '';
    var estado = $("#b_estado").length > 0 ? $("#b_estado").val() : '';
    var tipoPago = $("#b_tipo_pago").length > 0 ? $("#b_tipo_pago").val() : '0';
    var lote = $("#b_lote").length > 0 ? $("#b_lote").val() : '0';
    var idProyecto = $('#proyectoId').data('id');
    var table = $('#pagoTable').DataTable({
        ajax: {
            url: '/pagos/data/' + idProyecto + '?fecha_inicio=' + fechaInicio + '&fecha_fin=' + fechaFin + '&comprobante=' + comprobante + '&estado=' + estado + '&tipo_pago=' + tipoPago + '&lote=' + lote,
            dataSrc: 'data'
        },
        order: [[1, 'desc'],[2, 'asc']],
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
                  columns: [0, 1, 2, 3, 4, 5, 6]
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                title: 'Pagos del proyecto',
                filename: 'Pagos ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3, 4, 5, 6]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Pagos del proyecto',
                filename: 'Pagos ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3, 4, 5, 6]
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
                searchable: false,
                render: function (data, type, row) {
                    var claseCSS = '';
                    
                    if(row.estado){
                        if(row.tipo === "Prima"){
                            claseCSS = 'badge bg-secondary';
                        }else if(row.tipo === "Mantenimiento"){
                            claseCSS = 'badge bg-info';
                        }if(row.tipo === "Financiamiento"){
                            claseCSS = 'badge bg-warning';
                        }
                    }else{
                        claseCSS = 'badge bg-danger';
                    }
            
                    return '<span class="badge-recibo ' + claseCSS + '">' + data + '</span>';
                }
            },
            { data: 'tipo', title:'Tipo', width: '15%', searchable: false},
            { data: 'cuentaBancaria.nombre', title:'Tipo Pago', width: '15%', searchable: false},
            { data: 'monto', title:'Monto', width: '15%', sortable: false, searchable: false,},
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
                        actionsHtml = '<a type="button" class="btn btn-outline-secondary btn-sm" href="/Recibo/' + row.idPago + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
                    }
                    if(hasPrivilegeEditarPago === true){
                        actionsHtml += '<button type="button" class="btn btn-outline-primary abrirModal-btn btn-sm" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModalGuardar" data-tipo="editar" data-id="' + row.idPago + '" data-modo="actualizar">';
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
        searching: false,
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
    //Método para filtrar la tabla de pagos
    $(document).on('click', '#b_buscar', function () {
        var fechaInicio = $("#b_fecha_inicio").val();
        var fechaFin = $("#b_fecha_fin").val();
        var comprobante = $("#b_comprobante").val();
        var estado = $("#b_estado").val();
        var tipoPago = $("#b_tipo_pago").val();
        var lote = $("#b_lote").val();
        var url = '/pagos/data/' + idProyecto + '?fecha_inicio=' + fechaInicio + '&fecha_fin=' + fechaFin + '&comprobante=' + comprobante + '&estado=' + estado + '&tipo_pago=' + tipoPago + '&lote=' + lote;
        console.log(url);
        table.ajax.url(url).load();
    });
    //Método para eliminar los filtros la tabla de pagos
    $(document).on('click', '#b_clear', function () {
        $("#b_fecha_inicio").val("");
        $("#b_fecha_fin").val("");
        $("#b_comprobante").val("");
        $("#b_estado").val("");
        $("#b_tipo_pago").val("0");
        $("#b_lote").val("0");
        var fechaInicio = "";
        var fechaFin = "";
        var comprobante = "";
        var estado = "";
        var tipoPago = "0";
        var lote = "0";
        var url = '/pagos/data/' + idProyecto + '?fecha_inicio=' + fechaInicio + '&fecha_fin=' + fechaFin + '&comprobante=' + comprobante + '&estado=' + estado + '&tipo_pago=' + tipoPago + '&lote=' + lote;
        console.log(url);
        table.ajax.url(url).load();
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
    $.validator.addMethod(
        "otrosMenorQueMonto",
        function(value, element, params) {
            var montoValue = parseFloat($("#monto").val()) || 0;
            var descuentoValue = parseFloat($("#descuento").val()) || 0;
            var valorPago = montoValue + descuentoValue;
            return parseFloat(value) >= 0 && parseFloat(value) <= valorPago;
        },
        "El valor en 'Otros' debe ser menor o igual que el valor en 'Monto'"
    );
    $.validator.addMethod(
        "descuentoMenorCalculado",
        function(value, element, params) {
            var descuentoIngresado = parseFloat(value) || 0;
            var descuentoCalculado = 0;
            var isValid = false;
            $.ajax({
                url: '/ObtenerDescuento/' + $("#venta").val() + '?monto=' + $("#monto").val() + '&otros=' + $("#otros").val() + '&fecha=' + $("#fecha").val(),
                method: 'GET',
                dataType: 'json',
                async: false,
                success: function(response) {
                    descuentoCalculado = parseFloat(response.descuentoCalculado) || 0;
                    isValid = descuentoIngresado <= descuentoCalculado;
                },
                error: function() {
                    isValid = descuentoIngresado == 0;
                }
            });
            $.validator.messages.descuentoMenorCalculado = "El valor en 'Descuento' debe ser menor o igual a " + descuentoCalculado;
            return isValid;
        },
        ""
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
            cuenta: {
                required: true
            },
            fecha: {
                required: true,
                maxlength: 10
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
                descuentoMenorCalculado: true,
                maxlength: 10
            },
            otros: {
                validarMonto: true,
                otrosMenorQueMonto: true,
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
            cuenta:{
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
            var select2ChoiceElement = document.querySelector('#venta');
            if (select2ChoiceElement.classList.contains('is-invalid')) {
                $("#span-lotes-error").removeClass('d-none');
                $('#venta').addClass('is-invalid');
                $("#venta-error").addClass('d-none');
            }
        },
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
            var select2ChoiceElement = document.querySelector('#venta');
            if (!select2ChoiceElement.classList.contains('is-invalid')) {
                $("#span-lotes-error").addClass('d-none');
                $('#venta').removeClass('is-invalid');
            }
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") === "comprobante" || element.attr("name") === "cuenta" || element.attr("name") === "fecha" || element.attr("name") === "recibo" || element.attr("name") === "monto" || element.attr("name") === "descuento" || element.attr("name") === "otros") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            validator.resetForm();
            var idPago = $('#idPago').val();
            var tipo = $('#tipo').val();
            const fechaInputValue = $('#fecha').val();
            const cuenta = $('#cuenta').val();
            const fechaInput = new Date(fechaInputValue);
            const fechaLocal = new Date(fechaInput.getTime() + fechaInput.getTimezoneOffset() * 60000);

            function addLeadingZero(number) {
                return number < 10 ? `0${number}` : number;
            }
            const day = addLeadingZero(fechaLocal.getDate());
            const month = addLeadingZero(fechaLocal.getMonth() + 1);
            const year = fechaLocal.getFullYear();
            const formattedDate = `${day}/${month}/${year}`;
            var formDataArray = formGuardar.serializeArray();
            formDataArray = formDataArray.filter(item => item.name !== 'fecha');
            formDataArray = formDataArray.filter(item => item.name !== 'cuenta');
            var url;
            if (idPago) {
                url = '/ActualizarPago';
                formDataArray.push({name: 'tipo', value: tipo}, {name: 'fecha', value: formattedDate}, {name: 'cuentaBancaria', value: cuenta}, {name: 'idPago', value: idPago});
            } else {
                url = '/AgregarPago';
                formDataArray.push({name: 'tipo', value: tipo}, {name: 'fecha', value: formattedDate}, {name: 'cuentaBancaria', value: cuenta});
            }
            //console.log(formDataArray);
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModalGuardar').modal('hide');
                    table.ajax.reload(null, false);
                    toastr.success(response);
                },
                error: function (xhr, status, error) {
                    $('#crearModalGuardar').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al actualizar el pago.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    //Método para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModal-btn', function () {
        validator.resetForm();
        var idPago = $(this).data('id');
        var tipo = '';
        if (idPago) {
            formGuardar.find('.is-invalid').removeClass('is-invalid');
            $.ajax({
                url: '/ObtenerPago/' + idPago,
                type: 'GET',
                success: function (response) {
                    $('#comprobante').val(response.comprobante);
                    $('#tipo').val(response.tipo);
                    tipo =  response.tipo;
                    if(response.tipo==='Prima'){
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
                                        text: data[i].terreno.poligono + '-' + data[i].terreno.numero + data[i].terreno.seccion,
                                        selected: data[i].idVenta === response.venta.idVenta
                                    }));
                                }
                            }
                        });
                    }else if(response.tipo==='Mantenimiento'){
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
                                        text: data[i].terreno.poligono + '-' + data[i].terreno.numero + data[i].terreno.seccion,
                                        selected: data[i].idVenta === response.venta.idVenta
                                    }));
                                }
                            }
                        });
                    }else if(response.tipo==='Financiamiento'){
                        $.ajax({
                            url: "/obtenerVentasFinanciamiento?proyectoId=" + idProyecto,
                            type: "GET",
                            success: function (data) {
                                var selectElement = $("#venta");
                                selectElement.empty();
                                selectElement.append($("<option>", {value: '', text: 'Seleccione una opción'}));
                                for (var i = 0; i < data.length; i++) {
                                    selectElement.append($("<option>", {
                                        value: data[i].idVenta,
                                        text: data[i].terreno.poligono + '-' + data[i].terreno.numero + data[i].terreno.seccion,
                                        selected: data[i].idVenta === response.venta.idVenta
                                    }));
                                }
                            }
                        });
                    }
                    $('#fecha').val(response.fecha);
                    $('#recibo').val(response.recibo);
                    $('#estado').val(response.estado);
                    $('#monto').val(response.monto);
                    $('#otros').val(response.otros);
                    $('#descuento').val(response.descuento);
                    $('#observaciones').val(response.observaciones);
                    $('#cuenta').val(response.cuentaBancaria.idCuenta);
                    $('#idPago').val(response.idPago);
                    $("#span-lotes-error").addClass('d-none');
                },
                error: function () {
                    alert('Error al obtener los datos del pago.');
                }
            });
            $('#crearModalGuardar').find('.modal-title').text('Editar '+tipo);
            $('#crearModalGuardar').modal('show');
        } else {
            $('#crearModal').find('.modal-title').text('Agregar Pago');
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
            $("#span-lotes-error").addClass('d-none');
            $('#crearModal').modal('show');
        }
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
        validator.resetForm();
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
        validator.resetForm();
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
        var cantidadDeOpciones = selectElement.options.length;
        if (cantidadDeOpciones < 2) {
            var newOption = document.createElement("option");
            newOption.text = "Crédito Fiscal";
            newOption.value = "Crédito Fiscal";
            selectElement.appendChild(newOption);
        }
        document.getElementById("group-otros").style.display = "block";
        document.getElementById("group-descuento").style.display = "block";
        document.getElementById("otros").removeAttribute("required");
        document.getElementById("descuento").removeAttribute("required");
        modal.modal('hide');
        modalGuardar.modal('show');
    });
    //Función para inicializar la libreria de select2
    var $select = $('#venta').select2({
        theme: "bootstrap-5",
        width: $(this).data('width') ? $(this).data('width') : $(this).hasClass('w-100') ? '100%' : 'style',
        placeholder: $(this).data('placeholder'),
        dropdownParent: $('#crearModalGuardar .modal-body'),
        closeOnSelect: false
    });
    //Función para definir el uso de la libreria selec2
    var $select = $( '#b_lote' ).select2( {
        theme: "bootstrap-5",
        width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
        placeholder: $( this ).data( 'placeholder' ),
        closeOnSelect: false
    } );
    $select.on('change', function() {
        $(this).trigger('blur');
    });
}); 

