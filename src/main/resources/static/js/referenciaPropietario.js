$(document).ready(function() {
    var idPropietario = $('#idPropietario').val();
    var table = $('#referenciaTable').DataTable({
        ajax: '/referenciaPropietario/data/'+idPropietario,
        processing: true,
        serverSide: true,
        order: [[1, 'asc']],
        dom: "<'row w-100'<'col-sm-12 mb-4'B>>" +
             "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        lengthMenu: [[5, 25, 50, 100, -1], [5, 25, 50, 100, 'Todos']], // Opciones de selección para mostrar registros por página
        pageLength: 5, // Cantidad de registros por página por defecto
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
            { data: 'nombre', title: "Nombre", width: '15%' },
            { data: 'apellido', title: "Apellido", width: '15%' },
            { data: 'telefono', title: "Teléfono", width: '15%' },
            { data: 'correo', title: "Correo", width: '15%' },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '30%',
                render: function (data, type, row) {
                    
                    var actionsHtml = '';
                    
                    if(hasPrivilegeEliminarReferencia === true){
                        actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModalReferencia-btn btn-sm" data-id="' + row.idReferencia + '" ';
                        actionsHtml += 'data-cod="' + row.idReferencia + '">';
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
            "sInfoFiltered": "", //"(filtrado de un total de _MAX_ registros)",
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
    $.validator.addMethod(
        "validarTelefono",
        function(value, element) {
           return this.optional(element) || /^\d{8,}$/.test(value);
        },
        "Ingrese un número de teléfono válido"
    );
    
    $.validator.addMethod(
        "validarCorreo",
        function(value, element) {
           return this.optional(element) || /^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,}$/.test(value);
        },
        "Ingrese un correo electrónico válido"
    );
    
    var formGuardar = $('#formGuardarReferencia'); // Almacenar referencia al formulario
    var validator = $('#formGuardarReferencia').validate({
         
        rules: {
           nombre: {
               required: true,
               maxlength: 200
           },
           apellido: {
               required: true,
               maxlength: 200
           },
           telefono: {
               required: true,
               validarTelefono: true,
               maxlength: 12
           },
           correo: {
               required: true,
               validarCorreo: true,
               maxlength: 150
           } 
        },
        
        messages:{
            nombre:{
                required: 'Este campo es requerido'
            },
            apellido:{
                required: 'Este campo es requerido'
            },
            telefono:{
                required: 'Este campo es requerido'
            },
            correo:{
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
            if (element.attr("name") === "nombre" || element.attr("name") === "apellido" || element.attr("name") === "telefono" || element.attr("name") === "correo") {
                error.insertAfter(element);
            }        
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
               event.preventDefault();//detiene el evento del envio del form
            var idPersona = $('#idPersona').val();
            var idReferencia = $('#idReferencia').val();//tomo la id
            var idPropietario = $('#idPropietario').val();
            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            console.log(formDataArray);
            var url = '/AgregarReferencia';
            formDataArray.push({name: 'idReferencia', value: idReferencia}, {name: 'idPropietario', value: idPropietario});

            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModalReferencia').modal('hide');  // Cierra el modal
                    mostrarMensaje(response, 'success');
                    table.ajax.reload();
                },
                error: function (xhr, status, error) {
                    $('#crearModalReferencia').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar la referencia.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });

    // metodo para mostrar el modal
         $(document).on('click', '#AgregarReferencia', function() {
            var idPropietario = $('#idPropietario').data('id');
            var modal = $('#crearModalReferencia');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            validator.resetForm();  // Restablecer la validación
            formGuardar.find('.is-invalid').removeClass('is-invalid');

            var checkboxes = document.querySelectorAll(".checkClean");

            // en caso de presionar el boton de nuevo solo se abrira el modal
            tituloModal.text('Agregar Referencia');
            form.attr('action', '/AgregarReferencia');
            $('.form-control').val('');

            modal.modal('show');
   });
   
   // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModalReferencia-btn', function () {
        var idReferencia = $(this).data('id');
        var modal = $('#confirmarEliminarModalReferencia');
        var eliminarBtn = modal.find('#eliminarReferenciaBtn');
        eliminarBtn.data('id', idReferencia);
        modal.modal('show');
    });
   
   
   //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarReferenciaBtn', function () {
        var idPersona = $('#idPersona').val();
        var idReferencia = $(this).data('id');
        // Actualizar la acción del formulario
        $('#eliminarReferenciaForm').attr('action', '/EliminarReferencia/' + idReferencia);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarReferenciaForm').attr('action'),
            type: 'POST',
            data: $('#eliminarReferenciaForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
                $('#confirmarEliminarModalReferencia').modal('hide');
                mostrarMensaje(response, 'success');
                table.ajax.reload();
            },
            error: function (xhr, status, error) {
              $('#confirmarEliminarModalReferencia').modal('hide');
              var errorMessage = xhr.responseText || 'Error al eliminar la referencia.';
              mostrarMensaje(errorMessage, 'danger');
            }
        });
        
    });
    
    function mostrarMensaje(mensaje, tipo) {
        var alertElement = $('.alert-' + tipo);
        alertElement.text(mensaje).addClass('show').removeClass('d-none');
        setTimeout(function() {
          alertElement.removeClass('show').addClass('d-none');
        }, 5000);
    }
}); 

