$(document).ready(function() {
    //Tabla
    var idVenta = $('#idVenta').val();
    var table = $('#pagoTable').DataTable({
        ajax: {
            url: '/pagosVenta/data/' + idVenta,
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
                title: 'Pagos de la venta',
                filename: 'Pagos ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 2, 3, 4, 5, 6, 7]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Pagos de la venta',
                filename: 'Pagos ' + getCurrentDateTime(),
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
                    }else if(row.comprobante=="Recibo"){
                        claseCSS = 'badge bg-azul';
                    }else if(row.comprobante=="Factura"){
                        claseCSS = 'badge bg-amarillo';
                    }else{
                        claseCSS = 'badge bg-verde';
                    }
                    var valor = '';
                    if(row.comprobante === 'Ticket'){
                        valor += 'T '+data;
                    }else if(row.comprobante === 'Recibo'){
                        valor += 'R '+data;
                    }else if(row.comprobante === 'Factura'){
                        valor += 'F '+data;
                    }else if(row.comprobante === 'Crédito Fiscal'){
                        valor += 'CF '+data;
                    }
                    return '<span class="badge-recibo ' + claseCSS + '">' + valor + '</span>';
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
                        actionsHtml = '<a  title="Ver" type="button" class="btn font-size-small btn-outline-secondary btn-sm" href="/Recibo/' + row.idPago + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
                    }
                    if(hasPrivilegeEditarPago === true){
                        actionsHtml += '<button  title="Editar" type="button" class="btn font-size-small btn-outline-primary abrirModal-btn btn-sm" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModalGuardar" data-tipo="editar" data-id="' + row.idPago + '" data-modo="actualizar">';
                        actionsHtml += '<i class="far fa-edit"></i></button>';
                    }
                    if(hasPrivilegeEliminarPago === true){
                        actionsHtml += '<button  title="Eliminar" type="button" class="btn font-size-small btn-outline-danger eliminarModal-btn btn-sm" data-id="' + row.idPago + '" ';
                        actionsHtml += 'data-cod="' + row.idTerreno + '">';
                        actionsHtml += '<i class="far fa-trash-alt"></i></button>';
                    }
                    if(row.estado === true){
                        if(hasPrivilegeEditarPago === true){
                            actionsHtml += '<a  title="Anular" type="button" class="btn font-size-small btn-outline-dark btn-sm btn-anular" data-id="' + row.idPago + '" data-estado="false">';
                            actionsHtml += '<i class="fa-solid fa-ban"></i></a>';
                        }
                    }else{
                        if(hasPrivilegeEditarPago === true){
                            actionsHtml += '<a  title="Desanular" type="button" class="btn font-size-small btn-outline-success btn-sm btn-anular" data-id="' + row.idPago + '" data-estado="true">';
                            actionsHtml += '<i class="fa-solid fa-check"></i></a>';
                        }
                    }
                    if(hasPrivilegeImprimirPago == true){
                        actionsHtml += '<button title="Imprimir Recibo" type="button" class="btn btn-sm font-size-small btn-outline-dark btn-imprimir" data-bs-toggle="modal" data-id="' + row.idPago + '"><i class="fa-solid fa-print"></i></button>'
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
    //Método para anular recibos
    $(document).on('click', '.btn-anular', function () {
        var idPago = $(this).data('id');
        var estado = $(this).data('estado');
        $.ajax({
            url: "/AnularRecibo",
            type: 'GET',
            data: {
                idPago: idPago,
                estado: estado
            },
            success: function (response) {
                table.ajax.reload();
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                var errorMessage = xhr.responseText || 'Error en la anulación del pago.';
                toastr.error(errorMessage);
            }
        });
    });
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
            var otros = parseFloat(value);
            var montoValue = parseFloat($("#monto").val()) || 0;
            var descuentoValue = parseFloat($("#descuento").val()) || 0;
            var valorPago = montoValue + descuentoValue;
            return otros >= 0 && otros <= valorPago;
        },
        "El valor en 'Otros' debe ser menor o igual que el valor en 'Monto'"
    );
    $.validator.addMethod(
        "encontrarRecibo",
        function(value, element, params) {
            var existe = false;
            if ($("#recibo").val() != "" && $("#comprobante").val() != "" && $("#tipo").val() != "" && $("#venta").val() != "") {
                $.ajax({
                    url: '/VerificarPago?recibo=' + $("#recibo").val() + '&comprobante=' + $("#comprobante").val() + '&tipo=' + $("#tipo").val() + '&idVenta=' + $("#venta").val() + '&idPago=' + $("#idPago").val(),
                    method: 'GET',
                    dataType: 'json',
                    async: false,
                    success: function(response) {
                        if(response.existe){
                            existe = false;
                        }else{
                            existe = true;
                        }
                    },
                    error: function() {
                        existe = false;
                    }
                });
            } else {
                existe = true;
            }
            $.validator.messages.encontrarRecibo = "El número de comprobante ya se encuentra registrado.";
            return existe;
        },
        ""
    );
    $.validator.addMethod(
        "descuentoMenorCalculado",
        function(value, element, params) {
            var descuentoIngresado = parseFloat(value);
            var descuentoCalculado = 0;
            var isValid = false;
            if($("#venta").val() != "" && $("#monto").val() != "" && $("#otros").val() != "" && $("#fecha").val() != ""){
                $.ajax({
                    url: '/ObtenerDescuento/' + $("#venta").val() + '?monto=' + $("#monto").val() + '&otros=' + $("#otros").val() + '&fecha=' + $("#fecha").val(),
                    method: 'GET',
                    dataType: 'json',
                    async: false,
                    success: function(response) {
                        descuentoCalculado = parseFloat(response.descuentoCalculado) || 0;
                        isValid = descuentoIngresado >= 0 && descuentoIngresado <= descuentoCalculado;
                    },
                    error: function() {
                        isValid = descuentoIngresado == 0;
                    }
                });
            }else{
                isValid = true;
            }
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
            referencia: {
                validarNumero: true,
                maxlength: 20
            },
            fecha: {
                required: true,
                maxlength: 10
            },
            recibo: {
                required: true,
                validarNumero: true,
                encontrarRecibo: true,
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
            },
            observaciones: {
                maxlength: 500
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
            referencia:{
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
            },
            observaciones:{
                maxlength: 'Supera el límite de caracteres'
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
            if (element.attr("name") === "comprobante" || element.attr("name") === "cuenta" || element.attr("name") === "referencia" || element.attr("name") === "fecha" || element.attr("name") === "recibo" || element.attr("name") === "monto" || element.attr("name") === "descuento" || element.attr("name") === "otros" || element.attr("name") === "observaciones") {
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
            var venta = $('#venta').val();
            var monto = $('#monto').val();
            var estado = $('#estado').val();
            const fechaInputValue = $('#fecha').val();
            const cuenta = $('#cuenta').val();
            const otros = $('#otros').val();
            if(otros===""){
                otros="0.0";
            }
            const descuento = $('#descuento').val();
            if(descuento===""){
                descuento="0.0";
            }
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
            formDataArray = formDataArray.filter(item => item.name !== 'descuento');
            formDataArray = formDataArray.filter(item => item.name !== 'otros');
            formDataArray = formDataArray.filter(item => item.name !== 'monto');
            formDataArray = formDataArray.filter(item => item.name !== 'estado');
            console.log(formDataArray);
            var url;
            if (idPago) {
                url = '/ActualizarPago';
                formDataArray.push({name: 'tipo', value: tipo}, {name: 'fecha', value: formattedDate}, {name: 'cuentaBancaria', value: cuenta}, {name: 'idPago', value: idPago}, {name: 'monto', value: monto}, {name: 'descuento', value: descuento}, {name: 'otros', value: otros}, {name: 'venta', value: venta}, {name: 'estado', value: estado});
            } else {
                url = '/AgregarPago';
                formDataArray.push({name: 'tipo', value: tipo}, {name: 'fecha', value: formattedDate}, {name: 'cuentaBancaria', value: cuenta}, {name: 'monto', value: monto}, {name: 'descuento', value: descuento}, {name: 'otros', value: otros}, {name: 'venta', value: venta}, {name: 'estado', value: estado});
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
                    $('#tipo').val(response.tipo);
                    tipo =  response.tipo;
                    if(response.tipo==='Prima'){
                        var selectElement = document.getElementById("comprobante");
                        selectElement.innerHTML = '';
                        //Opciones del select prima
                        var newOption = document.createElement("option");
                        newOption.text = "Seleccione una opción";
                        newOption.value = "";
                        selectElement.appendChild(newOption);
                        newOption = document.createElement("option");
                        newOption.text = "Recibo";
                        newOption.value = "Recibo";
                        selectElement.appendChild(newOption);
                        $('#comprobante').val(response.comprobante);
                    }else if(response.tipo==='Mantenimiento'){
                        var selectElement = document.getElementById("comprobante");
                        selectElement.innerHTML = '';
                        //Opciones del select de mantenimiento
                        var newOption = document.createElement("option");
                        newOption.text = "Seleccione una opción";
                        newOption.value = "";
                        selectElement.appendChild(newOption);
                        newOption = document.createElement("option");
                        newOption.text = "Ticket";
                        newOption.value = "Ticket";
                        selectElement.appendChild(newOption);
                        newOption = document.createElement("option");
                        newOption.text = "Recibo";
                        newOption.value = "Recibo";
                        selectElement.appendChild(newOption);
                        newOption = document.createElement("option");
                        newOption.text = "Factura";
                        newOption.value = "Factura";
                        selectElement.appendChild(newOption);
                        newOption = document.createElement("option");
                        newOption.text = "Crédito Fiscal";
                        newOption.value = "Crédito Fiscal";
                        selectElement.appendChild(newOption);
                        $('#comprobante').val(response.comprobante);
                    }else if(response.tipo==='Financiamiento'){
                        var selectElement = document.getElementById("comprobante");
                        selectElement.innerHTML = '';
                        //Opciones del select financiamiento
                        var newOption = document.createElement("option");
                        newOption.text = "Seleccione una opción";
                        newOption.value = "";
                        selectElement.appendChild(newOption);
                        newOption = document.createElement("option");
                        newOption.text = "Recibo";
                        newOption.value = "Recibo";
                        selectElement.appendChild(newOption);
                        $('#comprobante').val(response.comprobante);
                    }
                    $('#fecha').val(response.fecha);
                    $('#recibo').val(response.recibo);
                    $('#referencia').val(response.referencia);
                    $('#estado').val(response.estado);
                    $('#monto').val(parseFloat(response.monto).toFixed(2));
                    $('#otros').val(parseFloat(response.otros).toFixed(2));
                    $('#descuento').val(parseFloat(response.descuento).toFixed(2));
                    $('#observaciones').val(response.observaciones);
                    $('#cuenta').val(response.cuentaBancaria.idCuenta);
                    $('#idPago').val(response.idPago);
                    $("#span-lotes-error").addClass('d-none');
                    //Desabilitar inputs si no es un pago de prima
                    if(response.tipo==='Prima'){
                        $('#fecha').prop('disabled', false);
                        $('#recibo').prop('disabled', false);
                        $('#comprobante').prop('disabled', false);
                        $('#estado').prop('disabled', false);
                        $('#monto').prop('disabled', false);
                        $('#otros').prop('disabled', false);
                        $('#descuento').prop('disabled', false);
                        $('#observaciones').prop('disabled', false);
                        $('#cuenta').prop('disabled', false);
                        $('#group-otros').hide();
                        $('#group-descuento').hide();
                    }else{
                        $('#fecha').prop('disabled', true);
                        $('#recibo').prop('disabled', false);
                        $('#comprobante').prop('disabled', false);
                        $('#estado').prop('disabled', true);
                        $('#monto').prop('disabled', true);
                        $('#otros').prop('disabled', true);
                        $('#descuento').prop('disabled', true);
                        $('#observaciones').prop('disabled', false);
                        $('#cuenta').prop('disabled', false);
                        $('#group-otros').show();
                        $('#group-descuento').show();
                    }
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
            $('#referencia').val('');
            $('#monto').val('');
            $('#comprobante').val('');
            $('#otros').val('0.00');
            $('#descuento').val('0.00');
            $('#observaciones').val('');
            $('#cuenta').val('');
            $('#idPago').val('');
            $("#span-lotes-error").addClass('d-none');
            $('#fecha').prop('disabled', false);
            $('#recibo').prop('disabled', false);
            $('#comprobante').prop('disabled', false);
            $('#estado').prop('disabled', false);
            $('#monto').prop('disabled', false);
            $('#otros').prop('disabled', false);
            $('#descuento').prop('disabled', false);
            $('#observaciones').prop('disabled', false);
            $('#cuenta').prop('disabled', false);
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
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        var modal = $('#crearModal');
        var modalGuardar = $('#crearModalGuardar');
        $("#crearModalLabelPago").text("Agregar Prima");
        $("#tipo").val("Prima");
        var selectElement = document.getElementById("comprobante");
        selectElement.innerHTML = '';
        //Opciones del select prima
        var newOption = document.createElement("option");
        newOption.text = "Seleccione una opción";
        newOption.value = "";
        selectElement.appendChild(newOption);
        newOption = document.createElement("option");
        newOption.text = "Recibo";
        newOption.value = "Recibo";
        selectElement.appendChild(newOption);
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
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        var modal = $('#crearModal');
        var modalGuardar = $('#crearModalGuardar');
        $("#crearModalLabelPago").text("Agregar Mantenimiento");
        $("#tipo").val("Mantenimiento");
        var selectElement = document.getElementById("comprobante");
        selectElement.innerHTML = '';
        //Opciones del select de mantenimiento
        var newOption = document.createElement("option");
        newOption.text = "Seleccione una opción";
        newOption.value = "";
        selectElement.appendChild(newOption);
        newOption = document.createElement("option");
        newOption.text = "Ticket";
        newOption.value = "Ticket";
        selectElement.appendChild(newOption);
        newOption = document.createElement("option");
        newOption.text = "Recibo";
        newOption.value = "Recibo";
        selectElement.appendChild(newOption);
        newOption = document.createElement("option");
        newOption.text = "Factura";
        newOption.value = "Factura";
        selectElement.appendChild(newOption);
        newOption = document.createElement("option");
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
    //Función para mostrar la vista de impresión del comprobante
    $(document).on('change', '#comprobante', function () {
        var valor = $(this).val();
        if(valor === "Ticket"){
            document.getElementById("group-otros").style.display = "none";
            document.getElementById("group-descuento").style.display = "none";
        }else{
            document.getElementById("group-otros").style.display = "block";
            document.getElementById("group-descuento").style.display = "block";
        }
    });
    //Función para admitir los saltos de linea en las observaciones
    const textarea = document.getElementById('observaciones');
    textarea.addEventListener('keydown', function (e) {
        if (e.key === 'Enter') {
        e.preventDefault();
        const currentText = textarea.value;
        textarea.value = currentText + '\n';
        }
    });
    //Función para mostrar la vista de impresión del comprobante
    $(document).on('click', '.btn-imprimir', function () {
        var idPago = $(this).data('id');
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