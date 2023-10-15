$(document).ready(function() {
    var idTerreno = $('#terrenoId').data('id');
    var table = $('#ventaTable').DataTable({
        ajax: {
            url: '/ventas/data/' + idTerreno,
            dataSrc: 'data'
        },
        order: [[1, 'asc'],[0, 'asc']],
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
                  columns: [0, 1] // Índices de las columnas que se copiarán
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                title: 'Ventas del terreno', // Título del reporte en Excel
                filename: 'Ventas ' + getCurrentDateTime(), // Nombre del archivo Excel
                exportOptions: {
                  columns: [0, 1,2] // Índices de las columnas que se exportarán
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Ventas del terreno', // Título del reporte en PDF
                filename: 'Ventas ' + getCurrentDateTime(), // Nombre del archivo PDF
                exportOptions: {
                  columns: [0, 1,2] // Índices de las columnas que se exportarán
                },
                customize: function (doc) {
                  doc.content[1].table.widths = Array(doc.content[1].table.body[0].length + 1).join('*').split('');
                }
            }
        ],
        columns: [
            { data: 'nombre'},
            { data: 'estado', width: '15%' },
            { data: 'fecha', width: '15%' },
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
                    
                    if(hasPrivilegeVerVenta === true){
                        actionsHtml = '<a type="button" class="btn btn-outline-secondary btn-sm" href="/InformacionVenta/' + row.idVenta + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
                    }
                    
                    if(hasPrivilegeEditarVenta === true){
                        actionsHtml += '<button type="button" class="btn btn-outline-primary abrirModal-btn btn-sm" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idVenta + '" data-modo="actualizar">';
                        actionsHtml += '<i class="far fa-edit"></i></button>';
                    }
                    
                    if(hasPrivilegeEliminarVenta === true){
                    actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn btn-sm" data-id="' + row.idVenta + '" ';
                    actionsHtml += 'data-cod="' + row.idVenta + '">';
                    actionsHtml += '<i class="far fa-trash-alt"></i></button>';
                   }
                    
                    return actionsHtml || '';
                }
            }
        ],
        columnDefs: [
            {
                targets: [2],
                render: function(data, type, row) {
                    if (type === 'display' || type === 'filter') {
                        var date = new Date(data);
                        var day = date.getDate();
                        var month = date.getMonth() + 1;
                        var year = date.getFullYear();
                        return day + '/' + month + '/' + year;
                    }
                    return data;
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
    
    // Obtén la referencia al DataTable
    var table = $('#ventaTable').DataTable();

    // Agrega un evento al filtro de búsqueda
    $('#ventaTable_filter input').on('keyup', function () {
        // Obtén el valor del filtro de búsqueda
        var searchTerm = $(this).val().trim();

        // Verifica si el valor no está vacío
        if (searchTerm !== '') {
            // Aplica el filtro personalizado en la columna "Lote"
            table.column[0,1].search('^' + searchTerm + '$', true, false).draw();
        } else {
            // Si el valor está vacío, muestra todos los registros
            table.column[0,1].search('').draw();
        }
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
        "validarPrecio",
        function(value, element) {
            return this.optional(element) || /^\d+(\.\d+)?$/.test(value);
        },
        "Ingrese un número válido"
    );
    $.validator.addMethod(
        "validarDescuento",
        function(value, element) {
            return this.optional(element) || /^\d+(\.\d+)?$/.test(value);
        },
        "Ingrese un número válido"
    );
     var formGuardar = $('#formGuardar'); // Almacenar referencia al formulario
     var validator = $('#formGuardar').validate({
         
        rules: {
           nombre: {
               required: true,
               maxlength: 200
           },
           fecha: {
               required: true,
               maxlength: 10
           },
           precio: {
               required: true,
               validarPrecio: true,
               maxlength: 9
           },
           descuento:{
               required: true,
               validarDescuento: true,
               maxlength: 9
           }
        },
        
        messages:{
            nombre: {
                required: 'Este campo es requerido'
            },
            fecha: {
                required: 'Este campo es requerido'
            },
            precio: {
                required: 'Este campo es requerido'
            },
            descuento:{
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
            if (element.attr("name") === "nombre" || element.attr("name") === "fecha" || element.attr("name") === "precio" || element.attr("name") === "descuento") {
                error.insertAfter(element);
            }        
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
               event.preventDefault();//detiene el evento del envio del form

            const fechaInputValue = $('#fecha').val();
            const fechaInput = new Date(fechaInputValue);
            function addLeadingZero(number) {
              return number < 10 ? `0${number}` : number;
            }
            const day = addLeadingZero(fechaInput.getDate());
            const month = addLeadingZero(fechaInput.getMonth() + 1);
            const year = fechaInput.getFullYear();
            const formattedDate = `${day}/${month}/${year}`;
            
            var idVenta = $('#idVenta').val();//tomo la id
            var estado = $('#estado').val();
            var idTerreno = $('#idTerreno').val();
            var formDataArray = formGuardar.serializeArray();//tomo los datos del array
            var fechaIndex = formDataArray.findIndex(item => item.name === 'fecha');
            formDataArray[fechaIndex].value = formattedDate;
            console.log(formDataArray);
            var url;//valido el tipo de url si editar o crear
            if (idVenta) {
                url = '/ActualizarVenta/'+idTerreno;
                //meto la id en el campo de envio
                formDataArray.push({name: 'idVenta', value: idVenta},{name: 'estado', value: estado});
            } else {
                url = '/AgregarVenta/'+idTerreno;
                formDataArray.push({name: 'estado', value: estado});
            }

            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');  // Cierra el modal
                    var table = $('#ventaTable').DataTable();
                    table.ajax.reload(null, false);
                    mostrarMensaje(response, 'success');
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar la venta.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });

    // metodo para mostrar el modal segun sea si editar o nuevo registro
        $(document).on('click', '.abrirModal-btn', function () {
            var idVenta = $(this).data('id');
            var idTerreno = $("#idTerreno").val();
            var modal = $('#crearModal');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            var btnSumit = document.getElementById('btnSumit');
            validator.resetForm();  // Restablecer la validación
            formGuardar.find('.is-invalid').removeClass('is-invalid');

            if (idVenta) {
                tituloModal.text('Editar Venta');//titulo del modal
                
                $.ajax({//utilizo ajax para obtener los datos
                    url: '/ObtenerVenta/' + idVenta,
                    type: 'GET',
                    success: function (response) {
                       
                        var checkboxes = document.querySelectorAll(".checkClean");

                        for (var i = 0; i < checkboxes.length; i++) {
                            checkboxes[i].checked = false;
                        }
                        $('#nombre').val(response.nombre);
                        $('#fecha').val(response.fecha);
                        $('#precio').val(response.precio);
                        $('#descuento').val(response.descuento);
                        $('#idListDocumento').val(response.idListDocumento);
                        $('#estado').val(response.estado);
                        $('#terreno').val(response.terreno.idTerreno);
                        $('#idVenta').val(response.idVenta);

                    },
                    error: function () {
                        alert('Error al obtener los datos de la venta.');
                    }
                });
            } else {
                var checkboxes = document.querySelectorAll(".checkClean");
                
                // en caso de presionar el boton de nuevo solo se abrira el modal
                tituloModal.text('Agregar Venta');
                form.attr('action', '/AgregarVenta/'+idTerreno);
                $('.form-control').val('');
            }
            modal.modal('show');
   });
   
   
   // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idVenta = $(this).data('id');

        var modal = $('#confirmarEliminarModal');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarVentaBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar la venta seleccionada?</strong><br>Ten en cuenta que se eliminarán \n\
        los datos relacionados a la venta');

        // Actualizar el atributo href del botón de eliminación con el idCohorte
        eliminarBtn.data('id', idVenta);

        modal.modal('show');
    });
   
   
   //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarVentaBtn', function () {
        
        var idVenta = $(this).data('id');
        // Actualizar la acción del formulario
        $('#eliminarVentaForm').attr('action', '/EliminarVenta/' + idVenta);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarVentaForm').attr('action'),
            type: 'POST',
            data: $('#eliminarVentaForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
              $('#confirmarEliminarModal').modal('hide');
              // Recargar el DataTable
              $('#ventaTable').DataTable().ajax.reload();
              // Mostrar el mensaje de éxito del controlador
               mostrarMensaje(response, 'success');
            },
            error: function () {
              $('#confirmarEliminarModal').modal('hide');
              // Mostrar mensaje de error en caso de que la solicitud falle
              mostrarMensaje('Error al eliminar la venta.', 'danger');
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

