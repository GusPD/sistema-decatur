$(document).ready(function() {
    //Tabla
    var table = $('#usuarioTable').DataTable({
        ajax: '/usuarios/data',
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
                  columns: [0, 1, 2, 3, 4, 5, 6]
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                title: 'Usuarios del sistema',
                filename: 'Usuarios ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3, 4, 5, 6]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Usuarios del sistema',
                filename: 'Usuarios ' + getCurrentDateTime(),
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
                width: '5%'
            },
            { data: 'nombre', title: 'Nombre', width: '20%' },
            { data: 'username', title: 'Usuario', width: '10%' },
            {
                data: 'proyectos',
                title: 'Proyectos',
                width: '15%',
                sortable: false,
                searchable: false,
                render: function(data, type, row) {
                    var proyectosHtml = '';
                    if (Array.isArray(data)) {
                        data.forEach(function(proyecto, index) {
                            proyectosHtml += proyecto.nombre;
                            if (index < data.length - 1) {
                                proyectosHtml += '<br>';
                            }
                        });
                    }
                    return proyectosHtml;
                }
            },
            {
                data: 'empresas',
                title: 'Empresas',
                width: '15%',
                sortable: false,
                searchable: false,
                render: function(data, type, row) {
                    var empresasHtml = '';
                    if (Array.isArray(data)) {
                        data.forEach(function(empresa, index) {
                            empresasHtml += empresa.nombre;
                            if (index < data.length - 1) {
                                empresasHtml += '<br>';
                            }
                        });
                    }
                    return empresasHtml;
                }
            },
            { data: 'habilitado', title: 'Habilitado',
                render: function(data, type, row) {
                    var estado = (data === true) ? 'Si' : 'No';
                    return estado;
                }, width: '10%'
            },
            { data: 'bloqueado', title: 'Bloqueado',
                render: function(data, type, row) {
                    var estado = (data === 0) ? 'No' : 'Si';
                    return estado;
                }, width: '10%'
            },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '15%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    if(hasPrivilegeEditarUsuario === true){
                        actionsHtml = '<button title="Editar" type="button" class="btn font-size-small btn-outline-primary abrirModal-btn btn-sm" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idUsuario + '" data-modo="actualizar">';
                        actionsHtml += '<i class="far fa-edit"></i></button>';
                    }
                    if(hasPrivilegeEliminarUsuario === true){
                        actionsHtml += '<button title="Eliminar" type="button" class="btn font-size-small btn-outline-danger eliminarModal-btn btn-sm" data-id="' + row.idUsuario + '" ';
                        actionsHtml += 'data-cod="' + row.idUsuario + '">';
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
    //Formulario de agregar
    $.validator.addMethod("validarPassword",
        function(value, element) {
            return this.optional(element) || 
                    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{};:,<.>])[A-Za-z\d!@#$%^&*()\-_=+{};:,<.>]{8,}$/.test(value);
        },
        "La contraseña debe contener al menos una mayúscula, una minúscula, un número, un carácter especial y tener un mínimo de 8 caracteres"
    );
    $.validator.addMethod("validarCorreo", 
        function(value, element) {
            return this.optional(element) || /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(value);
        },
        "Ingresa una dirección de correo electrónico válida"
    );
    $.validator.addMethod("noSpacios", function(value, element) {
        return this.optional(element) || /^\S+$/.test(value);
    }, "El nombre de usuario no puede contener espacios en blanco");
    var formGuardar = $('#formGuardar');
    var validator = $('#formGuardar').validate({
        rules: {
            nombre: {
                required: true
            },
            username: {
                required: true,
                noSpacios: true
            },
            email:{
                required: true,
                validarCorreo: true
            },          
            password: {
                required: function(element) {
                    return !$('#UsuarioId').val();
                },
                validarPassword: true
            },
            roles: {
                required: true,
            },
            "proyectos[]": {
                minlength: 1
            },
            "empresas[]": {
                minlength: 1
            }
        },
        messages:{
            nombre:{
                required: 'Este campo es requerido'
            },
            username:{
                required: 'Este campo es requerido'
            },
            email:{
                required: 'Este campo es requerido'
            },
            password:{
                required: 'Este campo es requerido',
                validarPassword: 'La contraseña debe contener al menos una mayúscula, una minúscula, un número, un carácter especial y tener un mínimo de 8 caracteres'
            },
            roles: {
                required: "Seleccione un rol"
            },
            "proyectos[]": {
                required: "Seleccione al menos un proyecto"
            },
            "empresas[]": {
                required: "Seleccione al menos una empresa"
            }
        },
        highlight: function(element) {
            $(element).addClass('is-invalid');
        },
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") === "nombre" || element.attr("name") === "username" || element.attr("name") === "email" || element.attr("name") === "password" || element.attr("name") === "roles") {
                error.insertAfter(element);
            }
            if (element.attr("name") === "empresas[]") {
                error.appendTo("#empresas-error");
            }
            if (element.attr("name") === "proyectos[]") {
                error.appendTo("#proyectos-error");
            }
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var idUsuario = $('#UsuarioId').val();
            var formDataArray = formGuardar.serializeArray();
            var url;
            if (idUsuario) {
                url = '/ActualizarUsuario';
                formDataArray.push({name: 'idUsuario', value: idUsuario});
            } else {
                url = '/AgregarUsuario';
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
                    var errorMessage = xhr.responseText || 'Error al actualizar el usuario.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    //Método para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModal-btn', function () {
        var idUsuario = $(this).data('id');
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        validator.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        if (idUsuario) {
            tituloModal.text('Editar Usuario');
            $('.oculto').removeAttr('hidden');
            $('#password').removeAttr('required');
            $.ajax({
                url: '/ObtenerUsuario/' + idUsuario,
                type: 'GET',
                success: function (response) {
                    var checkboxes = document.querySelectorAll(".checkClean");
                    for (var i = 0; i < checkboxes.length; i++) {
                        checkboxes[i].checked = false;
                    }
                    $('#nombre').val(response.nombre);
                    $('#username').val(response.username);
                    $('#email').val(response.email);
                    $('#password').val('');
                    $.each(response.roles, function (index, valor) {
                        $('#roles').val(valor.idRol);
                    });
                    $.each(response.proyectos, function (index, valor) {
                        var miCheckbox = document.getElementById('proyecto' + valor.idProyecto);
                        if (miCheckbox !== null) {
                            miCheckbox.checked = true;
                        } else {
                            console.log("El checkbox no se encontró en el documento.");
                        }
                    });
                    $.each(response.empresas, function (index, valor) {
                        var miCheckbox = document.getElementById('empresa' + valor.idEmpresa);
                        if (miCheckbox !== null) {
                            miCheckbox.checked = true;
                        } else {
                            console.log("El checkbox no se encontró en el documento.");
                        }
                    });
                    if(response.habilitado === true){
                        $('#habilitado').val(1);
                    }else{
                        $('#habilitado').val(0);
                    }
                    $('#bloqueado').val(response.bloqueado);
                    $('#UsuarioId').val(idUsuario);
                },
                error: function () {
                    alert('Error al obtener los datos del usuario.');
                }
            });
        } else {
            var checkboxes = document.querySelectorAll(".checkClean");
            $('.oculto').attr('hidden', true);
            $('#password').attr('required', true);
            for (var i = 0; i < checkboxes.length; i++) {
                checkboxes[i].checked = false;
            }
            tituloModal.text('Agregar Usuario');
            form.attr('action', '/AgregarUsuario');
            $('#nombre').val('');
            $('#roles').val('');
            $('#username').val('');
            $('#email').val('');
            $('#password').val('');
            $('#UsuarioId').val('');
        }
        modal.modal('show');
    });
    //Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idUsuario = $(this).data('id');
        var modal = $('#confirmarEliminarModal');
        var eliminarBtn = modal.find('#eliminarUsuarioBtn');
        eliminarBtn.data('id', idUsuario);
        modal.modal('show');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarUsuarioBtn', function () {
        var idUsuario = $(this).data('id');
        $('#eliminarUsuarioForm').attr('action', '/EliminarUsuario/' + idUsuario);
        $.ajax({
            url: $('#eliminarUsuarioForm').attr('action'),
            type: 'POST',
            data: $('#eliminarUsuarioForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModal').modal('hide');
                table.ajax.reload();
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                $('#confirmarEliminarModal').modal('hide');
                var errorMessage = xhr.responseText || 'Error al eliminar el usuario.';
                toastr.error(errorMessage);
            }
        });
    });
});

