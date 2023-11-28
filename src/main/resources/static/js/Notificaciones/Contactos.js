$(document).ready(function() {
    //Tabla
    var idProyecto = $('#idProyecto').val();
    var table = $('#contactosTable').DataTable({
        ajax: '/correosProyecto/data/'+idProyecto,
        processing: true,
        serverSide: true,
        order: [[1, 'asc']],
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
                  columns: [0, 1, 2, 3, 4]
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                title: 'Contactos del Proyecto',
                filename: 'Contactos del Proyecto ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3, 4]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Contactos del Proyecto',
                filename: 'Contactos del Proyecto ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3, 4]
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
                width: '6%'
            },
            { data: 'propietario.persona.nombre', title: "Nombre", width: '17%' },
            { data: 'propietario.persona.apellido', title: "Apellido", width: '17%' },
            { data: 'tipo', title: "Tipo", width: '15%' },
            { data: 'correo', title: "Correo", width: '30%' },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '15%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    if(hasPrivilegeEliminarCorreo === true){
                        actionsHtml += '<button title="Eliminar" type="button" class="btn font-size-small btn-outline-danger eliminarModalCorreo-btn btn-sm" data-id="' + row.idCorreo + '" ';
                        actionsHtml += 'data-cod="' + row.idCorreo + '">';
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
    //Formulario para agregar
    $.validator.addMethod(
        "validarCorreo",
        function(value, element) {
            return this.optional(element) || /^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,}$/.test(value);
        },
        "Ingrese un correo electrónico válido"
    );
    var formGuardar = $('#formGuardar');
    var validator = $('#formGuardar').validate({
        rules: {
            idPropietario: {
                required: true
            },
            tipo: {
                required: true,
                maxlength: 20
            },
            correo: {
                required: true,
                validarCorreo: true,
                maxlength: 150
            }      
        },
        messages:{
            idPropietario:{
                required: 'Este campo es requerido'
            },
            tipo:{
                required: 'Este campo es requerido'
            },
            correo:{
                required: 'Este campo es requerido'
            }    
        },
        highlight: function(element) {
            $(element).addClass('is-invalid');
            var select2ChoiceElement = document.querySelector('#idPropietario');
            if (select2ChoiceElement.classList.contains('is-invalid')) {
                $("#span-propietarios-error").removeClass('d-none');
                $('#idPropietario').addClass('is-invalid');
                $("#idPropietario-error").addClass('d-none');
            }
        },
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
            var select2ChoiceElement = document.querySelector('#idPropietario');
            if (!select2ChoiceElement.classList.contains('is-invalid')) {
                $("#span-propietarios-error").addClass('d-none');
                $('#idPropietario').removeClass('is-invalid');
            }
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") === "tipo" || element.attr("name") === "correo") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var idCorreo = $('#idCorreo').val();
            var formDataArray = formGuardar.serializeArray();
            var url = '/AgregarCorreo';
            formDataArray.push({name: 'idCorreo', value: idCorreo});
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');
                    toastr.success(response);
                    table.ajax.reload();
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al actualizar el correo.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    //Método para mostrar el modal de agregar
    $(document).on('click', '#AgregarCorreo', function() {
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        validator.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        tituloModal.text('Agregar Correo');
        form.attr('action', '/AgregarCorreo');
        $('.form-control').val('');
        modal.modal('show');
    });
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModalCorreo-btn', function () {
        var idCorreo = $(this).data('id');
        var modal = $('#confirmarEliminarModal');
        var eliminarBtn = modal.find('#eliminarCorreoBtn');
        eliminarBtn.data('id', idCorreo);
        modal.modal('show');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarCorreoBtn', function () {
        var idCorreo = $(this).data('id');
        $('#eliminarCorreoForm').attr('action', '/EliminarCorreo/' + idCorreo);
        $.ajax({
            url: $('#eliminarCorreoForm').attr('action'),
            type: 'POST',
            data: $('#eliminarCorreoForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModal').modal('hide');               
                toastr.success(response);
                table.ajax.reload();
            },
            error: function (xhr, status, error) {
                $('#confirmarEliminarModal').modal('hide');
                var errorMessage = xhr.responseText || 'Error al eliminar el correo.';
                toastr.error(errorMessage);
            }
        });
    });
    //Función para definir el uso de la libreria selec2
    var $select = $( '#idPropietario' ).select2( {
        theme: "bootstrap-5",
        width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
        placeholder: $( this ).data( 'placeholder' ),
        dropdownParent: $('#crearModal .modal-body'),
        closeOnSelect: false
    } );
    $select.on('change', function() {
        $(this).trigger('blur');
    });
}); 

