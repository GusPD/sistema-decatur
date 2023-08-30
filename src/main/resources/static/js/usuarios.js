$(document).ready(function() { 
    var table = $('#usuarioTable').DataTable({
        ajax: '/usuarios/data',
        processing: true,
        serverSide: true,
        order: [[0, 'asc']],
        dom: "<'row w-100'<'col-sm-12 mb-4'B>>" +
             "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        lengthMenu: [[5, 25, 50, 100, -1], [5, 25, 50, 100, 'Todos']], // Opciones de selección para mostrar registros por página
        pageLength: 5, // Cantidad de registros por página por defecto
        buttons: [
            {
                extend: 'copy',
                text: 'Copiar',
                class: 'btn-sm',
                exportOptions: {
                  columns: [0, 1, 2, 3] // Índices de las columnas que se copiarán
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                class: 'btn-sm',
                title: 'Usuarios del sistema', // Título del reporte en Excel
                filename: 'Usuarios ' + getCurrentDateTime(), // Nombre del archivo Excel
                exportOptions: {
                  columns: [0, 1, 2, 3] // Índices de las columnas que se exportarán
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                class: 'btn-sm',
                title: 'Usuarios del sistema', // Título del reporte en PDF
                filename: 'Usuarios ' + getCurrentDateTime(), // Nombre del archivo PDF
                exportOptions: {
                  columns: [0, 1, 2, 3] // Índices de las columnas que se exportarán
                },
                customize: function (doc) {
                  doc.content[1].table.widths = Array(doc.content[1].table.body[0].length + 1).join('*').split('');
                }
            }
        ],
        columns: [
            { data: 'username', width: '20%' },
            { data: 'email', width: '20%' },
            { data: 'habilitado',
                render: function(data, type, row) {
                    var estado = (data === true) ? 'Si' : 'No';
                    return estado;
                }, width: '20%'
            },
            { data: 'bloqueado',
                render: function(data, type, row) {
                    var estado = (data === 0) ? 'No' : 'Si';
                    return estado;
                }, width: '20%'
            },
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
                    
                    if(hasPrivilegeEditarUsuario === true){
                        actionsHtml = '<button type="button" class="btn btn-outline-primary abrirModal-btn btn-sm" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idUsuario + '" data-modo="actualizar">';
                        actionsHtml += '<i class="far fa-edit"></i></button>';
                    }
                    
                    if(hasPrivilegeEliminarUsuario === true){
                    actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn btn-sm" data-id="' + row.idUsuario + '" ';
                    actionsHtml += 'data-cod="' + row.idUsuario + '">';
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
    table.buttons().container().appendTo('.botonExportar');
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
    
    //validacion de contraseña con expresión
    $.validator.addMethod(
    "validarPassword",
    function(value, element) {
      return this.optional(element) || 
              /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{};:,<.>])[A-Za-z\d!@#$%^&*()\-_=+{};:,<.>]{8,}$/.test(value);
    },
    "La contraseña debe contener al menos una mayúscula, una minúscula, un número, un carácter especial y tener un mínimo de 8 caracteres"
    );
    
    $.validator.addMethod(
    "validarCorreo",
    function(value, element) {
        return this.optional(element) || /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(value);
    },
        "Ingresa una dirección de correo electrónico válida"
    );

            

     var formGuardar = $('#formGuardar'); // Almacenar referencia al formulario
     var validator = $('#formGuardar').validate({
         
        rules: {
           username: {
               required: true
           },
           
           email:{
               required: true,
               validarCorreo: true
           }
           ,          
           password: {
           required: function(element) {
            return !$('#UsuarioId').val(); // La contraseña es requerida si no se está editando un usuario existente
            },
            validarPassword: true
            },
            
            "roles[]": {
                required: true,
                minlength: 1
            }
            
           
        },
        
        messages:{
            username:{
                required: 'Este campo es requerido'
            },
            
            email:{
                required: 'Este campo es requerido'
            }
            ,
            password:{
                required: 'Este campo es requerido',
                validarPassword: 'La contraseña debe contener al menos una mayúscula, una minúscula, un número, un carácter especial y tener un mínimo de 8 caracteres'
            },
            
            "roles[]": {
                required: "Selecciona al menos un rol"
            }
        
        },
        
        highlight: function(element) {
            $(element).addClass('is-invalid');
        },
        
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
        },
        
        errorPlacement: function(error, element) {
            if (element.attr("name") === "username" || element.attr("name") === "email" || element.attr("name") === "password" ) {
                error.insertAfter(element);
              }
           
           if (element.attr("name") === "roles[]") {
              error.appendTo("#roles-error");
            } else {
              error.insertAfter(element);
            }
           
            
         },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
               event.preventDefault();//detiene el evento del envio del form 
            var idUsuario = $('#UsuarioId').val();//tomo la id

            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            console.log(formDataArray);
            var url;//valido el tipo de url si editar o crear
            if (idUsuario) {
                url = '/ActualizarUsuario';
                //meto la id en el campo de envio
                formDataArray.push({name: 'idUsuario', value: idUsuario});
            } else {
                url = '/AgregarUsuario';
            }

            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');  // Cierra el modal
                    var table = $('#usuarioTable').DataTable();
                    table.ajax.reload(null, false);
                    mostrarMensaje(response, 'success');
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar el usuario.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
     
    });
    
    // metodo para mostrar el modal segun sea si editar o nuevo registro
        $(document).on('click', '.abrirModal-btn', function () {
            var idUsuario = $(this).data('id');
            var modal = $('#crearModal');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            var btnSumit = document.getElementById('btnSumit');
            validator.resetForm();  // Restablecer la validación
            formGuardar.find('.is-invalid').removeClass('is-invalid');

            if (idUsuario) {
                tituloModal.text('Editar Usuario');//titulo del modal
                //con esto quito los campos de oculto para que muestre para habilitar/deshabilitar
                // y bloquear/desbloquear
                $('.oculto').removeAttr('hidden');
                 $('#password').removeAttr('required');
                
                $.ajax({//utilizo ajax para obtener los datos
                    url: '/ObtenerUsuario/' + idUsuario,
                    type: 'GET',
                    success: function (response) {
                       
                        var checkboxes = document.querySelectorAll(".checkClean");

                        for (var i = 0; i < checkboxes.length; i++) {
                            checkboxes[i].checked = false;
                        }
                        $('#username').val(response.username);
                        $('#email').val(response.email);
                        $('#password').val('');

                        $.each(response.roles, function (index, valor) {
                            var miCheckbox = document.getElementById('rol' + valor.idRol);
                            if (miCheckbox !== null) {
                                miCheckbox.checked = true;
                            } else {
                                console.log("El checkbox no se encontró en el documento.");
                            }
                        });
                       
                       //esto es para habilitado/desabilitado
                       if(response.habilitado === true){
                           $('#habilitado').val(1);
                       }else{
                           $('#habilitado').val(0);
                       }
                       
                       //console.log(response.enabled);
                       $('#bloqueado').val(response.bloqueado);
                       //console.log(response.usuarioBloqueado);
                       $('#UsuarioId').val(idUsuario);

                    },
                    error: function () {
                        alert('Error al obtener los datos del usuario.');
                    }
                });
            } else {
                var checkboxes = document.querySelectorAll(".checkClean");
                $('.oculto').attr('hidden', true);
                
                //Aqui la contraseña ya no sera requerida
                $('#password').attr('required', true);

                for (var i = 0; i < checkboxes.length; i++) {
                    checkboxes[i].checked = false;
                }
                // en caso de presionar el boton de nuevo solo se abrira el modal
                tituloModal.text('Agregar Usuario');
                form.attr('action', '/AgregarUsuario');
                $('#username').val('');
                $('#email').val('');
                $('#password').val('');
                $('#UsuarioId').val('');

            }
            modal.modal('show');
   });
   
   
   // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idUsuario = $(this).data('id');
//        var codPlan = $(this).data('cod');

        var modal = $('#confirmarEliminarModal');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarUsuarioBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar al usuario seleccionado?</strong><br>Ten en cuenta que se eliminarán \n\
        los datos relacionados al usuario');

        // Actualizar el atributo href del botón de eliminación con el idCohorte
        eliminarBtn.data('id', idUsuario);

        modal.modal('show');
    });
   
   
   //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarUsuarioBtn', function () {
        
        var idUsuario = $(this).data('id');
        // Actualizar la acción del formulario con el idMaestria
        $('#eliminarUsuarioForm').attr('action', '/EliminarUsuario/' + idUsuario);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarUsuarioForm').attr('action'),
            type: 'POST',
            data: $('#eliminarUsuarioForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
              $('#confirmarEliminarModal').modal('hide');
              // Recargar el DataTable
              $('#usuarioTable').DataTable().ajax.reload();
              // Mostrar el mensaje de éxito del controlador
               mostrarMensaje(response, 'success');
            },
            error: function () {
              $('#confirmarEliminarModal').modal('hide');
              // Mostrar mensaje de error en caso de que la solicitud falle
              mostrarMensaje('Error al eliminar al usuario.', 'danger');
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

