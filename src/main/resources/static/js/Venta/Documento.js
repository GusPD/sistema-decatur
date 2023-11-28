$(document).ready(function() {
    //Tabla
    var idListDocumento = $('#idList').val();
    var table = $('#documentoTable').DataTable({
        ajax: '/documentoVenta/data/'+idListDocumento,
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
            { data: 'nombre', title: "Nombre", width: '60%' },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '30%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    if(hasPrivilegeVerDocumento === true){
                        actionsHtml = '<a  title="Ver" type="button" class="btn font-size-small btn-outline-secondary btn-sm" href="/DocumentoVenta/' + row.idDocumento + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
                    }
                    if(hasPrivilegeEliminarDocumento === true){
                        actionsHtml += '<button title="Eliminar" type="button" class="btn font-size-small btn-outline-danger eliminarModalDocumento-btn btn-sm" data-id="' + row.idDocumento + '" ';
                        actionsHtml += 'data-cod="' + row.idDocumento + '">';
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
        "validarDocumento",
        function(value, element) {
            return this.optional(element) || /\.pdf$/i.test(value);
        },
        "Ingrese un documento en formato .pdf"
    );
    var formGuardar = $('#formGuardarDocumento');
    var validator = $('#formGuardarDocumento').validate({
        rules: {
           nombre: {
               required: true,
               maxlength: 200
           },
           documento: {
               required: true,
               validarDocumento: true
           }      
        },
        messages:{
            nombre:{
                required: 'Este campo es requerido'
            },
            documento:{
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
            if (element.attr("name") === "nombre" || element.attr("name") === "documento") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var idDocumento = $('#idDocumento').val();
            var idVenta = $('#idVenta').val();
            var formDataArray = new FormData(formGuardar[0]);
            var url = '/AgregarDocumentoVenta';
            formDataArray.append('idDocumento', idDocumento);
            formDataArray.append('idVenta', idVenta);
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                processData: false,
                contentType: false,
                success: function (response) {
                    $('#crearModalDocumento').modal('hide');
                    toastr.success(response);
                    table.ajax.reload();
                },
                error: function (xhr, status, error) {
                    $('#crearModalDocumento').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al actualizar el documento.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    //Método para mostrar el modal de agregar
    $(document).on('click', '#AgregarDocumento', function() {
        var modal = $('#crearModalDocumento');
        var form = modal.find('form');
        validator.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        form.attr('action', '/AgregarDocumentoVenta');
        $('.form-control').val('');
        modal.modal('show');
    });
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModalDocumento-btn', function () {
        var idDocumento = $(this).data('id');
        var modal = $('#confirmarEliminarModalDocumento');
        var eliminarBtn = modal.find('#eliminarDocumentoBtn');
        eliminarBtn.data('id', idDocumento);
        modal.modal('show');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarDocumentoBtn', function () {
        var idDocumento = $(this).data('id');
        $('#eliminarDocumentoForm').attr('action', '/EliminarDocumentoVenta/' + idDocumento);
        $.ajax({
            url: $('#eliminarDocumentoForm').attr('action'),
            type: 'POST',
            data: $('#eliminarDocumentoForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModalDocumento').modal('hide');
                toastr.success(response);
                table.ajax.reload();
            },
            error: function (xhr, status, error) {
                $('#confirmarEliminarModalDocumento').modal('hide');
                var errorMessage = xhr.responseText || 'Error al eliminar el documento.';
                toastr.error(errorMessage);
            }
        });
    });
}); 

