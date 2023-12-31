$(document).ready(function() {
    //Tabla
    var table = $('#propietarioTable').DataTable({
        ajax: {
            url: '/propietarios/data',
            dataSrc: 'data'
        },
        order: [[1, 'asc'],[2, 'asc']],
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
                title: 'Propietarios del sistema',
                filename: 'Propietarios ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Propietarios del sistema',
                filename: 'Propietarios ' + getCurrentDateTime(),
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
                width: '7%'
            },
            { data: 'persona.numero', title:'N° Documento', width: '13%' },
            { data: 'persona.nombre', title:'Nombre', width: '30%' },
            { data: 'persona.apellido', title:'Apellido', width: '30%' },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '20%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    if(hasPrivilegeVerPropietario === true){
                        actionsHtml = '<a title="Ver" type="button" class="btn btn-outline-secondary btn-sm" href="/InformacionPropietario/0/' + row.persona.idPersona + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
                    }
                    if(hasPrivilegeEditarPropietario === true){
                        actionsHtml += '<button  title="Editar" type="button" class="btn btn-outline-primary abrirModal-btn btn-sm" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.persona.idPersona + '" data-modo="actualizar">';
                        actionsHtml += '<i class="far fa-edit"></i></button>';
                    }
                    if(hasPrivilegeEliminarPropietario === true){
                        actionsHtml += '<button  title="Eliminar" type="button" class="btn btn-outline-danger eliminarModal-btn btn-sm" data-id="' + row.persona.idPersona + '" ';
                        actionsHtml += 'data-cod="' + row.persona.idPersona + '">';
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
    //Formulario de agregar
    $.validator.addMethod(
        "validarDocumento",
        function(value, element) {
            var mascara = $("#tipoDocumento option:selected").data("mascara");
            if (mascara) {
                var regex = new RegExp(mascara);
                return this.optional(element) || regex.test(value);
            } else {
                return true;
            }
        },
        "Ingrese un número válido para el tipo de documento"
    );
    var formGuardar = $('#formGuardar');
    var validator = $('#formGuardar').validate({
        rules: {
            tipoDocumento: {
                required: true,
            },
            numero: {
                required: true,
                validarDocumento: true,
            },
            nombre: {
                required: true,
                maxlength: 200
            },
            apellido: {
                required: true,
                maxlength: 200
            },
            profesion:{
                required: true,
                maxlength: 200
            },
            direccionCasa: {
                required: true,
                maxlength: 300
            },
            lugarTrabajo: {
                maxlength: 200
            },
            direccionTrabajo:{
                maxlength: 300
            }          
        },
        messages:{
            tipoDocumento:{
                required: 'Este campo es requerido'
            },
            numero:{
                required: 'Este campo es requerido'
            },
            nombre:{
                required: 'Este campo es requerido'
            },
            apellido:{
                required: 'Este campo es requerido'
            },
            profesion:{
                required: 'Este campo es requerido'
            },
            direccionCasa:{
                required: 'Este campo es requerido'
            },
            lugarTrabajo:{
                required: 'Este campo es requerido'
            },
            direccionTrabajo:{
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
            if (element.attr("name") === "tipoDocumento" || element.attr("name") === "numero" || element.attr("name") === "nombre" || element.attr("name") === "apellido" || element.attr("name") === "profesion" || element.attr("name") === "direccionCasa" || element.attr("name") === "lugarTrabajo" || element.attr("name") === "direccionTrabajo") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var idPersona = $('#idPersona').val();
            var idDocumento = $('#idDocumento').val();
            var idPropietario = $('#idPropietario').val();
            var formDataArray = formGuardar.serializeArray();
            var url;
            if (idPersona) {
                url = '/ActualizarPropietario';
                formDataArray.push({name: 'idPersona', value: idPersona}, {name: 'idPropietario', value: idPropietario}, {name: 'idDocumento', value: idDocumento});
            } else {
                url = '/AgregarPropietario';
            }
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');
                    var table = $('#propietarioTable').DataTable();
                    table.ajax.reload(null, false);
                    toastr.success(response);
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al actualizar el propietario.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    // Método para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModal-btn', function () {
        var idPersona = $(this).data('id');
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        validator.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        if (idPersona) {
            tituloModal.text('Editar Propietario');
            $.ajax({
                url: '/ObtenerPropietario/' + idPersona,
                type: 'GET',
                success: function (response) {
                    $('#idPersona').val(response.persona.idPersona);
                    $('#tipoDocumento').val(response.persona.tipoDocumento.idTipoDocumento);
                    $('#numero').val(response.persona.numero);
                    $('#nombre').val(response.persona.nombre);
                    $('#apellido').val(response.persona.apellido);
                    $('#idPropietario').val(response.propietario.idPropietario);
                    $('#profesion').val(response.propietario.profesion);
                    $('#direccionCasa').val(response.propietario.direccionCasa);
                    $('#lugarTrabajo').val(response.propietario.lugarTrabajo);
                    $('#direccionTrabajo').val(response.propietario.direccionTrabajo);
                    $('#idDocumento').val(response.propietario.idDocumento);
                },
                error: function () {
                    alert('Error al obtener los datos del propietario.');
                }
            });
        } else {
            tituloModal.text('Agregar Propietario');
            form.attr('action', '/AgregarPropietario');
            $('.form-control').val('');
        }
        modal.modal('show');
    });
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idPersona = $(this).data('id');
        var modal = $('#confirmarEliminarModal');
        var eliminarBtn = modal.find('#eliminarPropietarioBtn');
        eliminarBtn.data('id', idPersona);
        modal.modal('show');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarPropietarioBtn', function () {
        var idPersona = $(this).data('id');
        $('#eliminarPropietarioForm').attr('action', '/EliminarPropietario/' + idPersona);
        $.ajax({
            url: $('#eliminarPropietarioForm').attr('action'),
            type: 'POST',
            data: $('#eliminarPropietarioForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModal').modal('hide');
                table.ajax.reload();
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                $('#confirmarEliminarModal').modal('hide');
                var errorMessage = xhr.responseText || 'Error al eliminar el propietario.';
                toastr.error(errorMessage);
            }
        });
    });
}); 

