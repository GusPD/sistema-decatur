$(document).ready(function() {
    var idDocumento = $('#idDocumento').val();
    var table = $('#documentoTable').DataTable({
        ajax: '/documentoPropietario/data/'+idDocumento,
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
                        actionsHtml = '<a type="button" class="btn btn-outline-secondary btn-sm" href="/DocumentoPropietario/' + row.idDocumento + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
                    }
                    
                    if(hasPrivilegeEliminarDocumento === true){
                        actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModalDocumento-btn btn-sm" data-id="' + row.idDocumento + '" ';
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
        "validarDocumento",
        function(value, element) {
            return this.optional(element) || /\.pdf$/i.test(value);
        },
        "Ingrese un documento en formato .pdf"
    );
    
    var formGuardar = $('#formGuardarDocumento'); // Almacenar referencia al formulario
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
               event.preventDefault();//detiene el evento del envio del form
            var idPersona = $('#idPersona').val();
            var idDocumento = $('#idDocumento').val();//tomo la id
            var idPropietario = $('#idPropietario').val();
            var formDataArray = new FormData(formGuardar[0]);//tomo los datos del array

            console.log(formDataArray);
            var url = '/AgregarDocumentoPropietario';
            formDataArray.append('idDocumento', idDocumento);
            formDataArray.append('idPropietario', idPropietario);

            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                processData: false, // No procesar los datos
                contentType: false,
                success: function (response) {
                    $('#crearModalDocumento').modal('hide');  // Cierra el modal
                    mostrarMensaje(response, 'success');
                    table.ajax.reload();
                },
                error: function (xhr, status, error) {
                    $('#crearModalDocumento').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar el documento.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });

    // metodo para mostrar el modal
         $(document).on('click', '#AgregarDocumento', function() {
            var idPropietario = $('#idPropietario').data('id');
            var modal = $('#crearModalDocumento');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            validator.resetForm();  // Restablecer la validación
            formGuardar.find('.is-invalid').removeClass('is-invalid');

            var checkboxes = document.querySelectorAll(".checkClean");

            // en caso de presionar el boton de nuevo solo se abrira el modal
            tituloModal.text('Agregar Documento');
            form.attr('action', '/AgregarDocumentoPropietario');
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
        var idPersona = $('#idPersona').val();
        var idDocumento = $(this).data('id');
        // Actualizar la acción del formulario
        $('#eliminarDocumentoForm').attr('action', '/EliminarDocumentoPropietario/' + idDocumento);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarDocumentoForm').attr('action'),
            type: 'POST',
            data: $('#eliminarDocumentoForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
                $('#confirmarEliminarModalDocumento').modal('hide');
                table.ajax.reload();
                mostrarMensaje(response, 'success');
            },
            error: function (xhr, status, error) {
              $('#confirmarEliminarModalDocumento').modal('hide');
              var errorMessage = xhr.responseText || 'Error al eliminar el documento.';
              mostrarMensaje(errorMessage, 'danger');
            }
        });
        
    });
    
    function mostrarMensaje(mensaje, tipo) {
        var alertElement = $('.alert-' + tipo);
        alertElement.text(mensaje).addClass('show').removeClass('d-none');
        setTimeout(function() {
          alertElement.removeClass('show').addClass('d-none');
        }, 5000); // Ocultar el mensaje después de 3 segundos (ajusta el valor según tus necesidades)
    }
}); 

