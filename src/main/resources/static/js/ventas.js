$(document).ready(function() {
    //Tabla
    var idTerreno = $('#terrenoId').data('id');
    var table = $('#ventaTable').DataTable({
        ajax: {
            url: '/ventas/data/' + idTerreno,
            dataSrc: 'data'
        },
        order: [[1, 'asc'],[0, 'asc']],
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
                  columns: [0, 1, 2, 3]
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                title: 'Ventas del terreno',
                filename: 'Ventas ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Ventas del terreno',
                filename: 'Ventas ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3]
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
                width: '10%'
            },
            { data: 'nombre',title: 'Nombre'},
            { data: 'estado',title: 'Estado', width: '15%' },
            { data: 'fecha',title: 'Fecha', width: '15%' },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '20%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    if(hasPrivilegeVerVenta === true){
                        actionsHtml = '<a type="button" class="btn btn-outline-secondary btn-sm" href="/InformacionVenta/' + row.idVenta + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
                    }
                    if(hasPrivilegeEditarVenta === true){
                        actionsHtml += '<button type="button" class="btn btn-outline-primary abrirModal-btn btn-sm" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idVenta + '" data-modo="actualizar">';
                        actionsHtml += '<i class="far fa-edit"></i></button>';
                    }
                    if(hasPrivilegeEliminarVenta === true){
                        actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn btn-sm" data-id="' + row.idVenta + '" ';
                        actionsHtml += 'data-cod="' + row.idVenta + '">';
                        actionsHtml += '<i class="far fa-trash-alt"></i></button>';
                    }
                    return actionsHtml || '';
                }
            }
        ],
        columnDefs: [
            {
                targets: [3],
                render: function(data, type, row) {
                    if (type === 'display' || type === 'filter') {
                        var date = new Date(data);
                        var day = date.getDate();
                        var month = date.getMonth() + 1;
                        var year = date.getFullYear();
                        return day + '/' + month + '/' + year;
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
    //Funcion que calcula el monto cuando se digita el precio
    document.getElementById("precio").addEventListener("input", function() {
        const precio = parseFloat(document.getElementById('precio').value);
        const descuento = parseFloat(document.getElementById('descuento').value);
        if (!isNaN(precio) && !isNaN(descuento)) {
            const monto = precio - descuento;
            document.getElementById('monto').value = monto.toFixed(2);
        } else if(!isNaN(precio)){
            const monto = precio;
            document.getElementById('monto').value = monto.toFixed(2);
        }else if(!isNaN(descuento)){
            const monto = 0 - descuento;
            document.getElementById('monto').value = monto.toFixed(2);
        }else{
            document.getElementById('monto').value = '0.00';
        }
    });
    //Funcion que calcula el monto cuando se digita el descuento
    document.getElementById("descuento").addEventListener("input", function() {
        const precio = parseFloat(document.getElementById('precio').value);
        const descuento = parseFloat(document.getElementById('descuento').value);
        if (!isNaN(precio) && !isNaN(descuento)) {
            const monto = precio - descuento;
            document.getElementById('monto').value = monto.toFixed(2);
        } else if(!isNaN(precio)){
            const monto = precio;
            document.getElementById('monto').value = monto.toFixed(2);
        }else if(!isNaN(descuento)){
            const monto = 0 - descuento;
            document.getElementById('monto').value = monto.toFixed(2);
        }else{
            document.getElementById('monto').value = '0.00';
        }
    });
    //Formulario de agregar
    $.validator.addMethod(
        "validarPrecio",
        function(value, element) {
            return this.optional(element) || /^\d+(\.\d+)?$/.test(value);
        },
        "Ingrese un número válido"
    );
    $.validator.addMethod(
        "validarDescuento",
        function(value, element) {
            return this.optional(element) || /^\d+(\.\d+)?$/.test(value);
        },
        "Ingrese un número válido"
    );
    var formGuardar = $('#formGuardar');
    var validator = $('#formGuardar').validate({
        rules: {
            nombre: {
                required: true,
                maxlength: 200
            },
            fecha: {
                required: true,
                maxlength: 10
            },
            precio: {
                required: true,
                validarPrecio: true,
                maxlength: 9
            },
            descuento:{
                validarDescuento: true,
                maxlength: 9
            }
        },
        messages:{
            nombre: {
                required: 'Este campo es requerido'
            },
            fecha: {
                required: 'Este campo es requerido'
            },
            precio: {
                required: 'Este campo es requerido'
            },
            descuento:{
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
            if (element.attr("name") === "nombre" || element.attr("name") === "fecha" || element.attr("name") === "precio" || element.attr("name") === "descuento") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            const fechaInputValue = $('#fecha').val();
            const fechaInput = new Date(fechaInputValue);
            const fechaLocal = new Date(fechaInput.getTime() + fechaInput.getTimezoneOffset() * 60000);
            function addLeadingZero(number) {
                return number < 10 ? `0${number}` : number;
            }
            const day = addLeadingZero(fechaLocal.getDate());
            const month = addLeadingZero(fechaLocal.getMonth() + 1);
            const year = fechaLocal.getFullYear();
            const formattedDate = `${day}/${month}/${year}`;
            var idVenta = $('#idVenta').val();
            var estado = $('#estado').val();
            var idTerreno = $('#idTerreno').val();
            var descuento = $('#descuento').val();
            var idListDocumento = $('#idListDocumento').val();
            var formDataArray = formGuardar.serializeArray();
            formDataArray = formDataArray.filter(item => item.name !== 'fecha');
            formDataArray = formDataArray.filter(item => item.name !== 'descuento');
            var url;
            if(descuento===''){
                descuento='0.00';
            }
            if (idVenta) {
                url = '/ActualizarVenta/'+idTerreno;
                formDataArray.push({name: 'idVenta', value: idVenta},{name: 'estado', value: estado},{name: 'fecha', value: formattedDate},{name: 'descuento', value: descuento}, {name: 'idListDocumento', value: idListDocumento});
            } else {
                url = '/AgregarVenta/'+idTerreno;
                formDataArray.push({name: 'estado', value: estado},{name: 'fecha', value: formattedDate},{name: 'descuento', value: descuento});
            }
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');
                    var table = $('#ventaTable').DataTable();
                    table.ajax.reload(null, false);
                    toastr.success(response);
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al actualizar la venta.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    //Método para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModal-btn', function () {
        var idVenta = $(this).data('id');
        var idTerreno = $("#idTerreno").val();
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        validator.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        var habilitarEdicion = false;
        if (idVenta) {
            tituloModal.text('Editar Venta');
            $.ajax({
                url: '/ObtenerVenta/' + idVenta,
                type: 'GET',
                success: function (response) {
                    var checkboxes = document.querySelectorAll(".checkClean");
                    for (var i = 0; i < checkboxes.length; i++) {
                        checkboxes[i].checked = false;
                    }
                    $('#nombre').val(response.nombre);
                    $('#fecha').val(response.fecha);
                    $('#precio').val(response.precio);
                    $('#monto').val(response.monto);
                    $('#descuento').val(response.descuento);
                    $('#idListDocumento').val(response.idListDocumento);
                    $('#estado').val(response.estado);
                    $('#terreno').val(response.terreno.idTerreno);
                    $('#idVenta').val(response.idVenta);
                },
                error: function () {
                    alert('Error al obtener los datos de la venta.');
                }
            });
        } else {
            var checkboxes = document.querySelectorAll(".checkClean");
            tituloModal.text('Agregar Venta');
            form.attr('action', '/AgregarVenta/'+idTerreno);
            $('.form-control').val('');
            $('#descuento').val('0.00');
        }
        //Habilitar la edición solo si esta disponible en la venta
        $.ajax({
            url: '/HabilitarActualizarVenta/' + idVenta,
            type: 'GET',
            success: function (response) {
                habilitarEdicion = response.habilitarEdicion;
                if(habilitarEdicion){
                    $('#nombre').prop('disabled', false);
                    $('#fecha').prop('disabled', false);
                    $('#precio').prop('disabled', false);
                    $('#monto').prop('disabled', true);
                    $('#descuento').prop('disabled', false);
                    $('#idListDocumento').prop('disabled', false);
                    $('#estado').prop('disabled', false);
                    $('#terreno').prop('disabled', false);
                    $('#idVenta').prop('disabled', false);
                }else{
                    $('#nombre').prop('disabled', true);
                    $('#fecha').prop('disabled', true);
                    $('#precio').prop('disabled', true);
                    $('#monto').prop('disabled', true);
                    $('#descuento').prop('disabled', true);
                    $('#idListDocumento').prop('disabled', true);
                    $('#estado').prop('disabled', true);
                    $('#terreno').prop('disabled', true);
                    $('#idVenta').prop('disabled', true);
                }
            },
            error: function () {
                alert('Error al obtener el permiso de la edición.');
            }
        });
        modal.modal('show');
    });
    //Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idVenta = $(this).data('id');
        var modal = $('#confirmarEliminarModal');
        var eliminarBtn = modal.find('#eliminarVentaBtn');
        eliminarBtn.data('id', idVenta);
        modal.modal('show');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarVentaBtn', function () {
        var idVenta = $(this).data('id');
        $('#eliminarVentaForm').attr('action', '/EliminarVenta/' + idVenta);
        $.ajax({
            url: $('#eliminarVentaForm').attr('action'),
            type: 'POST',
            data: $('#eliminarVentaForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModal').modal('hide');
                var table = $('#ventaTable').DataTable();
                table.ajax.reload(null, false);
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                $('#confirmarEliminarModal').modal('hide');
                var errorMessage = xhr.responseText || 'Error al eliminar la venta.';
                toastr.error(errorMessage);
            }
        });
    });
}); 

