$(document).ready(function() {
    var idPropietario = $('#idPropietario').val();
    var table = $('#correoTable').DataTable({
        ajax: '/correoPropietario/data/'+idPropietario,
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
            { data: 'tipo', title: "Tipo", width: '20%' },
            { data: 'correo', title: "Correo", width: '40%' },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '30%',
                render: function (data, type, row) {
                    
                    var actionsHtml = '';
                    
                    if(hasPrivilegeEliminarCorreo === true){
                        actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModalCorreo-btn btn-sm" data-id="' + row.idCorreo + '" ';
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
        "validarCorreo",
        function(value, element) {
            return this.optional(element) || /^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,}$/.test(value);
        },
        "Ingrese un correo electrónico válido"
    );
    
    var formGuardar = $('#formGuardarCorreo'); // Almacenar referencia al formulario
    var validator = $('#formGuardarCorreo').validate({
         
        rules: {
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
            tipo:{
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
            if (element.attr("name") === "tipo" || element.attr("name") === "correo") {
                error.insertAfter(element);
            }        
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
               event.preventDefault();//detiene el evento del envio del form
            var idPersona = $('#idPersona').val();
            var idCorreo = $('#idCorreo').val();//tomo la id
            var idPropietario = $('#idPropietario').val();
            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            console.log(formDataArray);
            var url = '/AgregarCorreo';
            formDataArray.push({name: 'idCorreo', value: idCorreo}, {name: 'idPropietario', value: idPropietario});

            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModalCorreo').modal('hide');  // Cierra el modal
                    mostrarMensaje(response, 'success');
                    table.ajax.reload();
                },
                error: function (xhr, status, error) {
                    $('#crearModalCorreo').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar el correo.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });

    // metodo para mostrar el modal
         $(document).on('click', '#AgregarCorreo', function() {
            var idPropietario = $('#idPropietario').data('id');
            var modal = $('#crearModalCorreo');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            validator.resetForm();  // Restablecer la validación
            formGuardar.find('.is-invalid').removeClass('is-invalid');

            var checkboxes = document.querySelectorAll(".checkClean");

            // en caso de presionar el boton de nuevo solo se abrira el modal
            tituloModal.text('Agregar Correo');
            form.attr('action', '/AgregarCorreo');
            $('.form-control').val('');

            modal.modal('show');
   });
   
   // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModalCorreo-btn', function () {
        var idCorreo = $(this).data('id');
        var modal = $('#confirmarEliminarModalCorreo');
        var eliminarBtn = modal.find('#eliminarCorreoBtn');
        eliminarBtn.data('id', idCorreo);
        modal.modal('show');
    });
   
   
   //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarCorreoBtn', function () {
        var idPersona = $('#idPersona').val();
        var idCorreo = $(this).data('id');
        // Actualizar la acción del formulario
        $('#eliminarCorreoForm').attr('action', '/EliminarCorreo/' + idCorreo);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarCorreoForm').attr('action'),
            type: 'POST',
            data: $('#eliminarCorreoForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
                $('#confirmarEliminarModalCorreo').modal('hide');               
                mostrarMensaje(response, 'success');
                table.ajax.reload();
            },
            error: function (xhr, status, error) {
              $('#confirmarEliminarModalCorreo').modal('hide');
              var errorMessage = xhr.responseText || 'Error al eliminar el correo.';
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

