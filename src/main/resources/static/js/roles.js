$(document).ready(function () {
    $('#rolesTable').DataTable({
        ajax: '/roles/data',
        processing: true,
        serverSide: true,
        dom: "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
                "<'row w-100'<'col-sm-12 my-4'tr>>" +
                "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        columns: [
            {data: 'nombre', width: '20%'},
            {
                data: 'permisos',
                width: '40%',
                render: function (data) {
                    var nombresPermisos = data.map(function (permiso) {
                        return permiso.nombre;
                    });
                    return nombresPermisos.join(', ');
                }},
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '40%',
                render: function (data, type, row) {
                    // Aquí puedes construir el HTML para las acciones según tus necesidades
//                    var actionsHtml = '<a type="button" class="btn btn-outline-secondary" href="/DetalleMaestria/' + row.idMaestria + '">';
//                    actionsHtml += '<i class="bi bi-eye"></i></a>';
                
                 var actionsHtml = '';

                    if(hasPrivilegeEditarRol === true){
                    actionsHtml = '<button type="button" class="btn btn-outline-primary abrirModal-btn" data-bs-toggle="modal" ';
                    actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idRol + '" data-modo="actualizar">';
                    actionsHtml += '<i class="bi bi-pencil-square"></i></button>';
                    }

                    if(hasPrivilegeEliminarRol === true){
                    actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn" data-id="' + row.idRol + '" ';
                    actionsHtml += 'data-cod="' + row.idRol + '">';
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

    $.validator.addMethod(
    "validarnombre",
    function (value, element) {
       return this.optional(element) || /^ROLE_[A-Z_]+$/.test(value);
    },
    "El valor debe comenzar con 'ROLE_' y contener solo letras mayúsculas y guiones bajos después"
    );
    
    var formGuardar = $('#formGuardar'); // Almacenar referencia al formulario
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
            event.preventDefault();//detiene el evento del envio del form 
            var idRol = $('#rolId').val();//tomo la id

            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            console.log(formDataArray);
            var url;//valido el tipo de url si editar o crear
            if (idRol) {
                url = '/ActualizarRol';
                //meto la id en el campo de envio
                formDataArray.push({name: 'idRol', value: idRol});
            } else {
                url = '/AgregarRol';
            }
            
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');  // Cierra el modal
                    var table = $('#rolesTable').DataTable();
                    table.ajax.reload(null, false);
                    mostrarMensaje(response, 'success');
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar el rol.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
         }
        
    });
    
           // metodo para mostrar el modal segun sea si editar o nuevo registro
        $(document).on('click', '.abrirModal-btn', function () {
            var idRol = $(this).data('id');
            var modal = $('#crearModal');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            var btnSumit = document.getElementById('btnSumit');
            validator.resetForm();  // Restablecer la validación
            formGuardar.find('.is-invalid').removeClass('is-invalid');

            if (idRol) {
                tituloModal.text('Editar Rol');//titulo del modal
                $.ajax({//utilizo ajax para obtener los datos
                    url: '/ObtenerRol/' + idRol,
                    type: 'GET',
                    success: function (response) {

                        var checkboxes = document.querySelectorAll(".checkClean");

                        for (var i = 0; i < checkboxes.length; i++) {
                            checkboxes[i].checked = false;
                        }
                        $('#nombre').val(response.nombre);
                        //$('#permisos').val(response.permisos);
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
                var checkboxes = document.querySelectorAll(".checkClean");

                for (var i = 0; i < checkboxes.length; i++) {
                    checkboxes[i].checked = false;
                }
                // en caso de presionar el boton de nuevo solo se abrira el modal
                tituloModal.text('Agregar Rol');
                form.attr('action', '/AgregarRol');
                $('#nombre').val('');
                $('#permisos').val('');
                $('#rolId').val('');
            }
            modal.modal('show');
        });
    
  
   // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idRol = $(this).data('id');
        
        var modal = $('#confirmarEliminarModal');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarRolBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar al rol seleccionado?</strong><br>Ten en cuenta que se eliminarán \n\
        los datos relacionados al rol');

        // Actualizar el atributo href del botón de eliminación con el idCohorte
        eliminarBtn.data('id', idRol);

        modal.modal('show');
    });
    
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarRolBtn', function () {
        
        var idRol = $(this).data('id');
        // Actualizar la acción del formulario con el idMaestria
        $('#eliminarRolForm').attr('action', '/EliminarRol/' + idRol);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarRolForm').attr('action'),
            type: 'POST',
            data: $('#eliminarRolForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
              $('#confirmarEliminarModal').modal('hide');
              // Recargar el DataTable
              $('#rolesTable').DataTable().ajax.reload();
              // Mostrar el mensaje de éxito del controlador
               mostrarMensaje(response, 'success');
            },
            error: function () {
              $('#confirmarEliminarModal').modal('hide');
              // Mostrar mensaje de error en caso de que la solicitud falle
              mostrarMensaje('Error al eliminar el rol, esta asociado a un usuario.', 'danger');
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

