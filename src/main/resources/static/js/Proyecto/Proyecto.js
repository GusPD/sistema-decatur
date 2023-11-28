$(document).ready(function() {
    //Tabla
    var table = $('#proyectoTable').DataTable({
        ajax: '/proyectos/data',
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
                  columns: [0, 1, 2]
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                title: 'Proyectos del sistema',
                filename: 'Proyectos ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Proyectos del sistema',
                filename: 'Proyectos ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2]
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
            { data: 'nombre', title: 'Nombre', width: '35%' },
            { data: 'empresa.nombre', title: 'Empresa', width: '35%' },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '20%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    if(hasPrivilegeVerProyecto === true){
                        actionsHtml = '<a title="Ver" type="button" class="btn font-size-small btn-outline-secondary btn-sm" href="/VentasActivas/' + row.idProyecto + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
                    }
                    if(hasPrivilegeEditarProyecto === true){
                        actionsHtml += '<button title="Editar" type="button" class="btn font-size-small btn-outline-primary abrirModal-btn btn-sm" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idProyecto + '" data-modo="actualizar">';
                        actionsHtml += '<i class="far fa-edit"></i></button>';
                    }
                    if(hasPrivilegeEliminarProyecto === true){
                        actionsHtml += '<button  title="Eliminar" type="button" class="btn font-size-small btn-outline-danger eliminarModal-btn btn-sm" data-id="' + row.idProyecto + '" ';
                        actionsHtml += 'data-cod="' + row.idProyecto + '">';
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
    var formGuardar = $('#formGuardar');
    var validator = $('#formGuardar').validate({
        rules: {
            nombre: {
                required: true
            },

            empresa:{
                required: true
            }          
        },
        messages:{
            nombre:{
                required: 'Este campo es requerido'
            },
            empresa:{
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
            if (element.attr("name") === "nombre" || element.attr("name") === "empresa" ) {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var idProyecto = $('#idProyecto').val();
            var formDataArray = formGuardar.serializeArray();
            var url;
            if (idProyecto) {
                url = '/ActualizarProyecto';
                formDataArray.push({name: 'idProyecto', value: idProyecto});
            } else {
                url = '/AgregarProyecto';
            }
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');
                    var table = $('#proyectoTable').DataTable();
                    table.ajax.reload(null, false);
                    toastr.success(response);
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al actualizar el proyecto.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    // Método para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModal-btn', function () {
        var idProyecto = $(this).data('id');
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        validator.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        if (idProyecto) {
            tituloModal.text('Editar Proyecto');
            $.ajax({
                url: '/ObtenerProyecto/' + idProyecto,
                type: 'GET',
                success: function (response) {
                    $('#nombre').val(response.nombre);
                    $('#empresa').val(response.empresa.idEmpresa);
                    $('#idProyecto').val(response.idProyecto);
                },
                error: function () {
                    alert('Error al obtener los datos del proyecto.');
                }
            });
        } else {
            tituloModal.text('Agregar Proyecto');
            form.attr('action', '/AgregarProyecto');
            $('#nombre').val('');
            $('#empresa').val('');
            $('#idProyecto').val('');
        }
        modal.modal('show');
    });
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idProyecto = $(this).data('id');
        var modal = $('#confirmarEliminarModal');
        var eliminarBtn = modal.find('#eliminarProyectoBtn');
        eliminarBtn.data('id', idProyecto);
        modal.modal('show');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarProyectoBtn', function () {
        var idProyecto = $(this).data('id');
        $('#eliminarProyectoForm').attr('action', '/EliminarProyecto/' + idProyecto);
        $.ajax({
            url: $('#eliminarProyectoForm').attr('action'),
            type: 'POST',
            data: $('#eliminarProyectoForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModal').modal('hide');
                table.ajax.reload();
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                $('#confirmarEliminarModal').modal('hide');
                var errorMessage = xhr.responseText || 'Error al eliminar el proyecto.';
                toastr.error(errorMessage);
            }
        });
    }); 
});

