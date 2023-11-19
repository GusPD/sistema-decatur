$(document).ready(function() {
    //Tabla
    var idPropietario = $('#idPropietario').val();
    var table = $('#telefonoTable').DataTable({
        ajax: '/telefonoPropietario/data/'+idPropietario,
        processing: true,
        serverSide: true,
        order: [[1, 'asc']],
        dom: "<'row w-100'<'col-sm-12 mb-4'B>>" +
             "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        lengthMenu: [[5, 25, 50, 100, -1], [5, 25, 50, 100, 'Todos']],
        pageLength: 5,
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
            { data: 'tipo', title: "Tipo", width: '20%' },
            { data: 'telefono', title: "Telefono", width: '40%' },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '30%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    if(hasPrivilegeEliminarTelefono === true){
                        actionsHtml += '<button  title="Eliminar" type="button" class="btn btn-outline-danger eliminarModalTelefono-btn btn-sm" data-id="' + row.idTelefono + '" ';
                        actionsHtml += 'data-cod="' + row.idTelefono + '">';
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
            }
        },
        search: {
            return: true
        }
    });
    //Formulario de agregar
    $.validator.addMethod(
        "validarTelefono",
        function(value, element) {
           return this.optional(element) || /^\d{8,}$/.test(value);
        },
        "Ingrese un número de teléfono válido"
    );
    var formGuardar = $('#formGuardarTelefono');
    var validator = $('#formGuardarTelefono').validate({
        rules: {
            tipo: {
                required: true,
                maxlength: 20
            },
            telefono: {
                required: true,
                validarTelefono: true,
                maxlength: 12
            }      
        },
        messages:{
            tipo:{
                required: 'Este campo es requerido'
            },
            telefono:{
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
            if (element.attr("name") === "tipo" || element.attr("name") === "telefono") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var idTelefono = $('#idTelefono').val();
            var idPropietario = $('#idPropietario').val();
            var formDataArray = formGuardar.serializeArray();
            var url = '/AgregarTelefono';
            formDataArray.push({name: 'idTelefono', value: idTelefono}, {name: 'idPropietario', value: idPropietario});
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModalTelefono').modal('hide');
                    toastr.success(response);
                    table.ajax.reload();
                },
                error: function (xhr, status, error) {
                    $('#crearModalTelefono').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al actualizar el telefono.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    //Método para mostrar el modal
    $(document).on('click', '#AgregarTelefono', function() {
        var modal = $('#crearModalTelefono');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        validator.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        tituloModal.text('Agregar Telefono');
        form.attr('action', '/AgregarTelefono');
        $('.form-control').val('');
        modal.modal('show');
    });
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModalTelefono-btn', function () {
        var idTelefono = $(this).data('id');
        var modal = $('#confirmarEliminarModalTelefono');
        var eliminarBtn = modal.find('#eliminarTelefonoBtn');
        eliminarBtn.data('id', idTelefono);
        modal.modal('show');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarTelefonoBtn', function () {
        var idTelefono = $(this).data('id');
        $('#eliminarTelefonoForm').attr('action', '/EliminarTelefono/' + idTelefono);
        $.ajax({
            url: $('#eliminarTelefonoForm').attr('action'),
            type: 'POST',
            data: $('#eliminarTelefonoForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModalTelefono').modal('hide');
                toastr.success(response);
                table.ajax.reload();
            },
            error: function (xhr, status, error) {
                $('#confirmarEliminarModalTelefono').modal('hide');
                var errorMessage = xhr.responseText || 'Error al eliminar el telefono.';
                toastr.error(errorMessage);
            }
        });
    });
}); 

