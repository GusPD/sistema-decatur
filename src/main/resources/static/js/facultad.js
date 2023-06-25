$(document).ready(function() { 
    $('#facultadTable').DataTable({
        ajax: '/facultades/data',
        processing: true,
        serverSide: true,
        dom: "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        columns: [
            { data: 'nombreFacultad', width: '80%' }, 
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '20%',
                render: function (data, type, row) {
                    // Aquí puedes construir el HTML para las acciones según tus necesidades
//                    var actionsHtml = '<a type="button" class="btn btn-outline-secondary" href="/DetalleMaestria/' + row.idMaestria + '">';
//                    actionsHtml += '<i class="bi bi-eye"></i></a>';
                    
                     var actionsHtml = '';
                    
                    if(hasPrivilegeEditarFacultad === true){
                        actionsHtml = '<button type="button" class="btn btn-outline-primary abrirModal-btn" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idFacultad + '" data-modo="actualizar">';
                        actionsHtml += '<i class="bi bi-pencil-square"></i></button>';
                    }
                   
                     if(hasPrivilegeEliminarFacultad === true){
                    actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn" data-id="' + row.idFacultad + '" ';
                    actionsHtml += 'data-cod="' + row.idFacultad + '">';
                    actionsHtml += '<i class="bi bi-trash"></i></button>';
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
            "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
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
                "colvis": "Visibilidad"
            }
        },
        search: {
            return: true
        }
    });
    
    var formGuardar = $('#formGuardar'); // Almacenar referencia al formulario
    var validator = $('#formGuardar').validate({
        
        rules:{
           nombreFacultad:{
               required: true
           } 
        },
        
        messages:{
            nombreFacultad:{
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
            if (element.attr("name") === "nombreFacultad") {
                error.insertAfter(element);
              }
         },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
           event.preventDefault();//detiene el evento del envio del form
           var idFacultad = $('#facultadId').val();//tomo la id
           
           var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            var url;//valido el tipo de url si editar o crear
            if (idFacultad) {
                url = '/ActualizarFacultad';
                //meto la id en el campo de envio
                formDataArray.push({name: 'idFacultad', value: idFacultad});
            } else {
                url = '/AgregarFacultad';
            }
            // Convertir el arreglo en un objeto
            var formData = {};
            $.map(formDataArray, function (n, i) {
                formData[n['name']] = n['value'];
            });
            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                success: function (response) {
                    $('#crearModal').modal('hide');  // Cierra el modal
                    var table = $('#facultadTable').DataTable();
                    table.ajax.reload(null, false);
                    mostrarMensaje(response, 'success');
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar la facultad.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
           
        }
        
    });

    // metodo para mostrar el modal segun sea si editar o nuevo registro
        $(document).on('click', '.abrirModal-btn', function () {
            var idFacultad = $(this).data('id');
            var modal = $('#crearModal');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            var btnSumit = document.getElementById('btnSumit');
            validator.resetForm();  // Restablecer la validación
            formGuardar.find('.is-invalid').removeClass('is-invalid');

            if (idFacultad) {
                tituloModal.text('Editar Facultad');//titulo del modal
                $.ajax({//utilizo ajax para obtener los datos
                    url: '/ObtenerFacultad/' + idFacultad,
                    type: 'GET',
                    success: function (response) {
                        $('#nombreFacultad').val(response.nombreFacultad);
                        $('#facultadId').val(idFacultad);
                    },
                    error: function () {
                        alert('Error al obtener los datos de la facultad.');
                    }
                });
            } else {
                // en caso de presionar el boton de nuevo solo se abrira el modal
                tituloModal.text('Agregar Facultad');
                form.attr('action', '/AgregarFacultad');
                $('#nombreFacultad').val('');
                $('#facultadId').val('');
            }
            modal.modal('show');
        });
        
        // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idFacultad = $(this).data('id');
        
        var modal = $('#confirmarEliminarModal');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarFacultadBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar la facultad seleccionada?</strong><br>Ten en cuenta que se eliminarán \n\
        los datos relacionados a la facultad');

        // Actualizar el atributo href del botón de eliminación con el idCohorte
        eliminarBtn.data('id', idFacultad);

        modal.modal('show');
    });
    
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarFacultadBtn', function () {
        
        var idFacultad = $(this).data('id');
        // Actualizar la acción del formulario con el idMaestria
        $('#eliminarFacultadForm').attr('action', '/EliminarFacultad/' + idFacultad);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarFacultadForm').attr('action'),
            type: 'POST',
            data: $('#eliminarFacultadForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
              $('#confirmarEliminarModal').modal('hide');
              // Recargar el DataTable
              $('#facultadTable').DataTable().ajax.reload();
              // Mostrar el mensaje de éxito del controlador
               mostrarMensaje(response, 'success');
            },
            error: function () {
              $('#confirmarEliminarModal').modal('hide');
              // Mostrar mensaje de error en caso de que la solicitud falle
              mostrarMensaje('Error al eliminar la facultad, esta esta asociada a una Escuela.', 'danger');
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

