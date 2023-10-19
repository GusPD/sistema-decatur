$(document).ready(function () {
    //Tabla
    var table = $('#rolesTable').DataTable({
        ajax: '/roles/data',
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
                  columns: [0, 1]
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                title: 'Roles del sistema',
                filename: 'Roles ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Roles del sistema',
                filename: 'Roles ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1]
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
            {data: 'nombre', title:'Nombre', width: '65%'},
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '25%',
                render: function (data, type, row) {
                 var actionsHtml = '';
                    if(hasPrivilegeEditarRol === true){
                        actionsHtml = '<button type="button" class="btn btn-outline-primary abrirModal-btn btn-sm" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idRol + '" data-modo="actualizar">';
                        actionsHtml += '<i class="far fa-edit"></i></button>';
                    }
                    if(hasPrivilegeEliminarRol === true){
                        actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn btn-sm" data-id="' + row.idRol + '" ';
                        actionsHtml += 'data-cod="' + row.idRol + '">';
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
    $.validator.addMethod( "validarnombre", function (value, element) {
        return this.optional(element) || /^ROLE_[A-Z_]+$/.test(value);
    },
        "El valor debe comenzar con 'ROLE_' y contener solo letras mayúsculas y guiones bajos después"
    );
    var formGuardar = $('#formGuardar');
    var validator = $('#formGuardar').validate({
        rules: {
            nombre:{
              required: true,
              validarnombre: true
            },
            "permisos[]":{
             required: true,
             minlength: 1
            }
        },
        messages:{
            nombre:{
                required: 'Este campo es requerido',
                validarnombre: 'El valor debe comenzar con  ROLE_ y contener solo letras mayúsculas y guiones bajos después'
            },
            "permisos[]":{
                required: "Selecciona al menos un permiso"
            }
        },
        highlight: function(element) {
            $(element).addClass('is-invalid');
        },
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") === "nombre") {
                error.insertAfter(element);
            }
            if (element.attr("name") === "permisos[]") {
                error.appendTo("#permisos-error");
            } else {
                error.insertAfter(element);
            }
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault(); 
            var idRol = $('#rolId').val();
            var formDataArray = formGuardar.serializeArray();
            var url;
            if (idRol) {
                url = '/ActualizarRol';
                formDataArray.push({name: 'idRol', value: idRol});
            } else {
                url = '/AgregarRol';
            }
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');
                    var table = $('#rolesTable').DataTable();
                    table.ajax.reload(null, false);
                    toastr.success(response);
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al actualizar el rol.';
                    toastr.error(errorMessage);
                }
            });
         }
        
    });
    //Método para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModal-btn', function () {
        var idRol = $(this).data('id');
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        validator.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        if (idRol) {
            tituloModal.text('Editar Rol');
            $.ajax({
                url: '/ObtenerRol/' + idRol,
                type: 'GET',
                success: function (response) {
                    $('#selectAll').prop('checked', false);
                    var checkboxes = document.querySelectorAll(".checkClean");
                    for (var i = 0; i < checkboxes.length; i++) {
                        checkboxes[i].checked = false;
                    }
                    $('#nombre').val(response.nombre);
                    $.each(response.permisos, function (index, valor) {
                        var miCheckbox = document.getElementById('permiso' + valor.idPermiso);
                        if (miCheckbox !== null) {
                            miCheckbox.checked = true;
                        } else {
                            console.log("El checkbox no se encontró en el documento.");
                        }
                    });
                    $('#rolId').val(idRol);
                },
                error: function () {
                    alert('Error al obtener los datos del rol.');
                }
            });
        } else {
            $('#selectAll').prop('checked', false);
            var checkboxes = document.querySelectorAll(".checkClean");
            for (var i = 0; i < checkboxes.length; i++) {
                checkboxes[i].checked = false;
            }
            tituloModal.text('Agregar Rol');
            form.attr('action', '/AgregarRol');
            $('#nombre').val('');
            $('#permisos').val('');
            $('#rolId').val('');
        }
        modal.modal('show');
    });
    //Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idRol = $(this).data('id');
        var modal = $('#confirmarEliminarModal');
        var eliminarBtn = modal.find('#eliminarRolBtn');
        eliminarBtn.data('id', idRol);
        modal.modal('show');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarRolBtn', function () {
        var idRol = $(this).data('id');
        $('#eliminarRolForm').attr('action', '/EliminarRol/' + idRol);
        $.ajax({
            url: $('#eliminarRolForm').attr('action'),
            type: 'POST',
            data: $('#eliminarRolForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModal').modal('hide');
                table.ajax.reload();
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                $('#confirmarEliminarModal').modal('hide');
                var errorMessage = xhr.responseText || 'Error al eliminar el rol.';
                toastr.error(errorMessage);
            }
        });
    });
    //Método para seleccionar los permisos
    $(document).on('click', '#selectAll', function() {
        var isChecked = $(this).is(':checked');
        $('.checkClean').prop('checked', isChecked);
    });
});

