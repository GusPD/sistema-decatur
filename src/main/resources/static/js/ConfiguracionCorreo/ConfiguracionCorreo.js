$(document).ready(function() {
    //Tabla
    var table = $('#correoTable').DataTable({
        ajax: '/configuracionCorreo/data',
        processing: true,
        serverSide: true,
        order: [[1, 'asc'],[3, 'asc']],
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
                title: 'Configuración de Correo del sistema',
                filename: 'Configuracion correo ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Configuración de Correo del sistema',
                filename: 'Configuracion correo ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3]
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
            { data: 'name', title:'Nombre', width: '28%' },
            { data: 'username', title:'Correo', width: '30%' },
            {
                data: 'servidor', 
                width: '20%', 
                title: 'Servidor Correos',
                sortable: false,
                searchable: false,
                render: function(data, type, row) {
                    if (type === 'display' || type === 'filter') {
                        if(row.servidor.toString()==="true"){
                            return "Seleccionado";
                        }else{
                            return "No seleccionado";
                        }
                    }
                    return data;
                }
            },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '15%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    if(hasPrivilegeEditarConfiguracion === true){
                        actionsHtml += '<button title="Editar" type="button" class="btn font-size-small btn-outline-primary abrirModal-btn btn-sm" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idConfiguracion + '" data-modo="actualizar">';
                        actionsHtml += '<i class="far fa-edit"></i></button>';
                    }
                    if(hasPrivilegeEliminarConfiguracion === true){
                        actionsHtml += '<button title="Eliminar" type="button" class="btn font-size-small btn-outline-danger eliminarModal-btn btn-sm" data-id="' + row.idConfiguracion + '" ';
                        actionsHtml += 'data-cod="' + row.idConfiguracion + '">';
                        actionsHtml += '<i class="far fa-trash-alt"></i></button>';
                    }
                    if(hasPrivilegeEditarConfiguracion === true){
                        var classButton = "";
                        if(data.verificado.toString()==="true"){
                            classButton = "btn-outline-success";
                        }else{
                            classButton = "btn-outline-dark";
                        }
                        actionsHtml += '<button title="Verificar" type="button" class="btn font-size-small ' + classButton + ' btn-verificar btn-sm"';
                        actionsHtml += 'data-tipo="verificar" data-id="' + row.idConfiguracion + '" data-modo="verificar">';
                        actionsHtml += '<i class="fa-regular fa-circle-check"></i></button>';
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
    $.validator.addMethod(
        "validarCorreo",
        function(value, element) {
            return this.optional(element) || /^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,}$/.test(value);
        },
        "Ingrese un correo electrónico válido"
    );
    $.validator.addMethod(
        "validarHost",
        function(value, element) {
            return this.optional(element) || /^[a-zA-Z0-9.-]+$/.test(value);
        },
        "Ingrese un host válido"
    );
    $.validator.addMethod(
        "validarPort",
        function(value, element) {
            return this.optional(element) || /^\d+$/.test(value);
        },
        "Ingrese un puerto válido"
    );
    $.validator.addMethod(
        "validarProtocol",
        function(value, element) {
            return this.optional(element) || /^(smtp|SMTP|ssl|SSL|tls|TLS)$/.test(value);
        },
        "Ingrese un protocolo válido"
    );
    var formGuardar = $('#formGuardar');
    var validator = $('#formGuardar').validate({
        rules: {
            name: {
                required: true,
                maxlength: 100
            },
            host: {
                required: true,
                validarHost: true,
                maxlength: 50
            },
            port: {
                required: true,
                validarPort: true,
                maxlength: 20
            },
            protocol: {
                required: true,
                validarProtocol: true,
                maxlength: 4
            },
            username: {
                required: true,
                validarCorreo: true,
                maxlength: 100
            },
            password: {
                required: true,
                maxlength: 100
            },
            smtp_auth: {
                required: true
            },
            start_tls: {
                required: true
            },
            servidor: {
                required: true
            }
        },
        messages:{
            name:{
                required: 'Este campo es requerido'
            },
            host:{
                required: 'Este campo es requerido'
            },
            port:{
                required: 'Este campo es requerido'
            },
            protocol:{
                required: 'Este campo es requerido'
            },
            username:{
                required: 'Este campo es requerido'
            },
            password:{
                required: 'Este campo es requerido'
            },
            smtp_auth:{
                required: 'Este campo es requerido'
            },
            start_tls:{
                required: 'Este campo es requerido'
            },
            servidor:{
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
            if (element.attr("name") === "name" || element.attr("name") === "host" || element.attr("name") === "port" || element.attr("name") === "protocol" || element.attr("name") === "username" || element.attr("name") === "password" || element.attr("name") === "start_tls" || element.attr("name") === "smtp_auth" || element.attr("name") === "servidor") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var idConfiguracion = $('#idConfiguracion').val();
            var verificado = $('#verificado').val();
            var formDataArray = formGuardar.serializeArray();
            var url;
            if (idConfiguracion) {
                url = '/ActualizarConfiguracionCorreo';
                formDataArray.push({name: 'idConfiguracion', value: idConfiguracion}, {name: 'verificado', value: verificado});
            } else {
                url = '/AgregarConfiguracionCorreo';
                formDataArray.push({name: 'verificado', value: verificado});
            }
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');
                    table.ajax.reload(null, false);
                    toastr.success(response);
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al actualizar la configuración de correo.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    // Método para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModal-btn', function () {
        var idConfiguracion = $(this).data('id');
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        validator.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        if (idConfiguracion) {
            tituloModal.text('Editar Configuración de Correo');
            $.ajax({
                url: '/ObtenerConfiguracionCorreo/' + idConfiguracion,
                type: 'GET',
                success: function (response) {
                    $('#name').val(response.name);
                    $('#host').val(response.host);
                    $('#port').val(response.port);
                    $('#protocol').val(response.protocol);
                    $('#username').val(response.username);
                    $('#password').val(response.password);
                    $('#servidor').val(response.servidor.toString());
                    $('#verificado').val("false");
                    $('#smtp_auth').val(response.smtp_auth.toString());
                    $('#start_tls').val(response.start_tls.toString());
                    $('#idConfiguracion').val(response.idConfiguracion);
                },
                error: function () {
                    alert('Error al obtener los datos de la empresa.');
                }
            });
        } else {
            tituloModal.text('Agregar Configuración Correo');
            form.attr('action', '/AgregarConfiguracionCorreo');
            $('.form-control').val('');
            $('.form-select').val('');
        }
        modal.modal('show');
    });
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idConfiguracion = $(this).data('id');
        var modal = $('#confirmarEliminarModal');
        var eliminarBtn = modal.find('#eliminarBtn');
        eliminarBtn.data('id', idConfiguracion);
        modal.modal('show');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarBtn', function () {
        var idConfiguracion = $(this).data('id');
        $('#eliminarForm').attr('action', '/EliminarConfiguracionCorreo/' + idConfiguracion);
        $.ajax({
            url: $('#eliminarForm').attr('action'),
            type: 'POST',
            data: $('#eliminarForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModal').modal('hide');
                table.ajax.reload();
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                $('#confirmarEliminarModal').modal('hide');
                var errorMessage = xhr.responseText || 'Error al eliminar la configuración de correo.';
                toastr.error(errorMessage);
            }
        });
    });
    //Método para verificar configuración de correo
    $(document).on('click', '.btn-verificar', function () {
        var idConfiguracion = $(this).data('id');
        $.ajax({
            url: "/VerificarConfiguracionCorreo",
            type: 'GET',
            data: {
                idConfiguracion: idConfiguracion,
            },
            success: function (response) {
                table.ajax.reload();
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                var errorMessage = xhr.responseText || 'Error en la verificación de la configuración del recibo.';
                toastr.error(errorMessage);
            }
        });
    });    
});

