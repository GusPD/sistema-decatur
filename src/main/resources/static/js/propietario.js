$(document).ready(function() {
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
        lengthMenu: [[5, 25, 50, 100, -1], [5, 25, 50, 100, 'Todos']], // Opciones de selección para mostrar registros por página
        pageLength: 5, // Cantidad de registros por página por defecto
        buttons: [
            {
                extend: 'copy',
                text: 'Copiar',
                exportOptions: {
                  columns: [0, 1, 2] // Índices de las columnas que se copiarán
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                title: 'Propietarios del sistema', // Título del reporte en Excel
                filename: 'Propietarios ' + getCurrentDateTime(), // Nombre del archivo Excel
                exportOptions: {
                  columns: [0, 1, 2] // Índices de las columnas que se exportarán
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Propietarios del sistema', // Título del reporte en PDF
                filename: 'Propietarios ' + getCurrentDateTime(), // Nombre del archivo PDF
                exportOptions: {
                  columns: [0, 1, 2] // Índices de las columnas que se exportarán
                },
                customize: function (doc) {
                  doc.content[1].table.widths = Array(doc.content[1].table.body[0].length + 1).join('*').split('');
                }
            }
        ],
        columns: [
            { data: 'persona.dui', width: '20%' },
            { data: 'persona.nombre', width: '30%' },
            { data: 'persona.apellido', width: '30%' },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '20%',
                render: function (data, type, row) {
                    
                    var actionsHtml = '';
                    
                    if(hasPrivilegeVerPropietario === true){
                        actionsHtml = '<a type="button" class="btn btn-outline-secondary btn-sm" href="/InformacionPropietario/0/' + row.persona.idPersona + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
                    }
                    
                    if(hasPrivilegeEditarPropietario === true){
                        actionsHtml += '<button type="button" class="btn btn-outline-primary abrirModal-btn btn-sm" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.persona.idPersona + '" data-modo="actualizar">';
                        actionsHtml += '<i class="far fa-edit"></i></button>';
                    }
                    
                    if(hasPrivilegeEliminarPropietario === true){
                    actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn btn-sm" data-id="' + row.persona.idPersona + '" ';
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
            "sInfoFiltered": "",//(filtrado de un total de _MAX_ registros)
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
    
    $.validator.addMethod(
        "validarDui",
        function(value, element) {
            return this.optional(element) || /^\d{9}$/.test(value);
        },
        "Ingrese un número válido, verifique que deben de ser nueve dígitos"
    );
    
     var formGuardar = $('#formGuardar'); // Almacenar referencia al formulario
     var validator = $('#formGuardar').validate({
         
        rules: {
           dui: {
               required: true,
               validarDui: true,
               maxlength: 9
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
               required: true,
               maxlength: 200
           },
           direccionTrabajo:{
               required: true,
               maxlength: 300
           }          
        },
        
        messages:{
            dui:{
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
            if (element.attr("name") === "dui" || element.attr("name") === "nombre" || element.attr("name") === "apellido" || element.attr("name") === "profesion" || element.attr("name") === "direccionCasa" || element.attr("name") === "lugarTrabajo" || element.attr("name") === "direccionTrabajo") {
                error.insertAfter(element);
            }        
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
               event.preventDefault();//detiene el evento del envio del form 
            var idPersona = $('#idPersona').val();//tomo la id
            var idDocumento = $('#idDocumento').val();
            var idPropietario = $('#idPropietario').val();
            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            console.log(formDataArray);
            var url;//valido el tipo de url si editar o crear
            if (idPersona) {
                url = '/ActualizarPropietario';
                //meto la id en el campo de envio
                formDataArray.push({name: 'idPersona', value: idPersona}, {name: 'idPropietario', value: idPropietario}, {name: 'idDocumento', value: idDocumento});
            } else {
                url = '/AgregarPropietario';
            }

            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');  // Cierra el modal
                    var table = $('#propietarioTable').DataTable();
                    table.ajax.reload(null, false);
                    mostrarMensaje(response, 'success');
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar el propietario.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });

    // metodo para mostrar el modal segun sea si editar o nuevo registro
        $(document).on('click', '.abrirModal-btn', function () {
            var idPersona = $(this).data('id');
            var modal = $('#crearModal');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            var btnSumit = document.getElementById('btnSumit');
            validator.resetForm();  // Restablecer la validación
            formGuardar.find('.is-invalid').removeClass('is-invalid');

            if (idPersona) {
                tituloModal.text('Editar Propietario');//titulo del modal
                
                $.ajax({//utilizo ajax para obtener los datos
                    url: '/ObtenerPropietario/' + idPersona,
                    type: 'GET',
                    success: function (response) {
                       
                        var checkboxes = document.querySelectorAll(".checkClean");

                        for (var i = 0; i < checkboxes.length; i++) {
                            checkboxes[i].checked = false;
                        }
                        $('#idPersona').val(response.persona.idPersona);
                        $('#dui').val(response.persona.dui);
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
                var checkboxes = document.querySelectorAll(".checkClean");
                
                // en caso de presionar el boton de nuevo solo se abrira el modal
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
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarPropietarioBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar el propietario seleccionado?</strong><br>Ten en cuenta que se eliminarán \n\
        los datos relacionados al propietario');

        // Actualizar el atributo href del botón de eliminación
        eliminarBtn.data('id', idPersona);

        modal.modal('show');
    });
   
   
   //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarPropietarioBtn', function () {
        
        var idPersona = $(this).data('id');
        // Actualizar la acción del formulario
        $('#eliminarPropietarioForm').attr('action', '/EliminarPropietario/' + idPersona);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarPropietarioForm').attr('action'),
            type: 'POST',
            data: $('#eliminarPropietarioForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
              $('#confirmarEliminarModal').modal('hide');
              // Recargar el DataTable
              $('#propietarioTable').DataTable().ajax.reload();
              // Mostrar el mensaje de éxito del controlador
               mostrarMensaje(response, 'success');
            },
            error: function () {
              $('#confirmarEliminarModal').modal('hide');
              // Mostrar mensaje de error en caso de que la solicitud falle
              mostrarMensaje('Error al eliminar el propietario.', 'danger');
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

