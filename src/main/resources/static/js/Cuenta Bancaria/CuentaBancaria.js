$(document).ready(function() {
    //Tabla
    var idEmpresa = $('#empresaId').data('id');
    var table = $('#cuentaTable').DataTable({
        ajax: '/cuentas/data/'+idEmpresa,
        processing: true,
        serverSide: true,
        order: [[0, 'asc']],
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
                title: 'Cuentas bancarias de la empresa',
                filename: 'Cuentas Bancarias ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3, 4, 5]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Cuentas bancarias de la empresa',
                filename: 'Cuentas Bancarias ' + getCurrentDateTime(),
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
                width: '8%'
            },
            { data: 'nombre', title:'Nombre', width: '%19' },
            { data: 'titular', title:'Titular', width: '19%' },
            { data: 'banco', title:'Banco', width: '19%' },
            { data: 'tipo', title:'Tipo', width: '11%' },
            { data: 'cuenta', title:'Número', width: '11%' },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '16%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    if(hasPrivilegeEditarCuenta === true){
                        actionsHtml += '<button title="Editar" type="button" class="btn font-size-small btn-outline-primary abrirModal-btn btn-sm" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idCuenta + '" data-modo="actualizar">';
                        actionsHtml += '<i class="far fa-edit"></i></button>';
                    }
                    if(hasPrivilegeEliminarCuenta === true){
                        actionsHtml += '<button title="Eliminar" type="button" class="btn font-size-small btn-outline-danger eliminarModal-btn btn-sm" data-id="' + row.idCuenta + '" ';
                        actionsHtml += 'data-cod="' + row.idCuenta + '">';
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
        search: true
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
    //Formulario de agregar
    var formGuardar = $('#formGuardar');
    var validator = $('#formGuardar').validate({
        rules: {
            nombre: {
                required: true,
                maxlength: 200
            },
            titular: {
                required: true,
                maxlength: 200
            },
            banco: {
                required: true,
                maxlength: 200
            },
            tipo: {
                required: true,
                maxlength: 20
            },
            cuenta: {
                required: true,
                maxlength: 20
            } 
        },
        messages:{
            nombre:{
                required: 'Este campo es requerido'
            },
            titular:{
                required: 'Este campo es requerido'
            },
            banco:{
                required: 'Este campo es requerido'
            },
            tipo:{
                required: 'Este campo es requerido'
            },
            cuenta:{
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
            if (element.attr("name") === "nombre" || element.attr("name") === "titular" || element.attr("name") === "banco" || element.attr("name") === "tipo" || element.attr("name") === "cuenta") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var idCuenta = $('#idCuenta').val();
            var idEmpresa = $('#empresaId').data('id');
            var formDataArray = formGuardar.serializeArray();
            var url;
            if (idCuenta) {
                url = '/ActualizarCuenta';
                formDataArray.push({name: 'idCuenta', value: idCuenta}, {name: 'idEmpresa', value: idEmpresa});
            } else {
                url = '/AgregarCuenta';
                formDataArray.push({name: 'idEmpresa', value: idEmpresa});
            }
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');
                    var table = $('#cuentaTable').DataTable();
                    table.ajax.reload(null, false);
                    toastr.success(response);
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al actualizar la cuenta bancaria.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    // Método para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModal-btn', function () {
        var idCuenta = $(this).data('id');
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        validator.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        if (idCuenta) {
            tituloModal.text('Editar Cuenta Bancaria');
            $.ajax({
                url: '/ObtenerCuenta/' + idCuenta,
                type: 'GET',
                success: function (response) {
                    $('#nombre').val(response.nombre);
                    $('#titular').val(response.titular);
                    $('#banco').val(response.banco);
                    $('#tipo').val(response.tipo);
                    $('#cuenta').val(response.cuenta);
                    $('#idCuenta').val(response.idCuenta);

                },
                error: function () {
                    alert('Error al obtener los datos de la cuenta bancaria.');
                }
            });
        } else {
            tituloModal.text('Agregar Cuenta Bancaria');
            form.attr('action', '/AgregarCuenta');
            $('.form-control').val('');
        }
        modal.modal('show');
    });
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idCuenta = $(this).data('id');
        var modal = $('#confirmarEliminarModal');
        var eliminarBtn = modal.find('#eliminarCuentaBtn');
        eliminarBtn.data('id', idCuenta);
        modal.modal('show');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarCuentaBtn', function () {
        var idCuenta = $(this).data('id');
        $('#eliminarCuentaForm').attr('action', '/EliminarCuenta/' + idCuenta);
        $.ajax({
            url: $('#eliminarCuentaForm').attr('action'),
            type: 'POST',
            data: $('#eliminarCuentaForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModal').modal('hide');
                table.ajax.reload();
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                $('#confirmarEliminarModal').modal('hide');
                var errorMessage = xhr.responseText || 'Error al eliminar la cuenta.';
                toastr.error(errorMessage);
            }
        });
    });    
});

