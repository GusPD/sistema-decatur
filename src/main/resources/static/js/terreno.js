$(document).ready(function() { 
    var idProyecto = $('#proyectoId').data('id');
    var table = $('#terrenoTable').DataTable({
        ajax: {
            url: '/terrenos/data/' + idProyecto,
            dataSrc: 'data'
        },
        order: [[0, 'asc'],[1, 'asc'],[2, 'asc']],
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
                  columns: [0, 1, 2, 3] // Índices de las columnas que se copiarán
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                title: 'Terrenos del proyecto', // Título del reporte en Excel
                filename: 'Terrenos ' + getCurrentDateTime(), // Nombre del archivo Excel
                exportOptions: {
                  columns: [0, 1, 2, 3] // Índices de las columnas que se exportarán
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Terrenos del proyecto', // Título del reporte en PDF
                filename: 'Terrenos ' + getCurrentDateTime(), // Nombre del archivo PDF
                exportOptions: {
                  columns: [0, 1, 2, 3] // Índices de las columnas que se exportarán
                },
                customize: function (doc) {
                  doc.content[1].table.widths = Array(doc.content[1].table.body[0].length + 1).join('*').split('');
                }
            }
        ],
        columns: [
            { data: 'poligono', width: '10%' },
            { data: 'lote', width: '10%' },
            { data: 'matricula', width: '30%', sortable: false, searchable: false,},
            {
                data: 'areaMetros',
                width: '15%',
                sortable: false,
                searchable: false,
                render: function(data, type, row) {
                    if (type === 'display' || type === 'filter') {
                        return parseFloat(data).toFixed(2);
                    }
                    return data;
                }
            },
            {
                data: 'areaVaras',
                width: '15%',
                sortable: false,
                searchable: false,
                render: function(data, type, row) {
                    if (type === 'display' || type === 'filter') {
                        return parseFloat(data).toFixed(2);
                    }
                    return data;
                }
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
                    
                    if(hasPrivilegeVerTerreno === true){
                        actionsHtml = '<a type="button" class="btn btn-outline-secondary btn-sm" href="/Ventas/' + row.idTerreno + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
                    }
                    
                    if(hasPrivilegeEditarTerreno === true){
                        actionsHtml += '<button type="button" class="btn btn-outline-primary abrirModal-btn btn-sm" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idTerreno + '" data-modo="actualizar">';
                        actionsHtml += '<i class="far fa-edit"></i></button>';
                    }
                    
                    if(hasPrivilegeEliminarTerreno === true){
                    actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn btn-sm" data-id="' + row.idTerreno + '" ';
                    actionsHtml += 'data-cod="' + row.idTerreno + '">';
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
    
    // Obtén la referencia al DataTable
    var table = $('#terrenoTable').DataTable();

    // Agrega un evento al filtro de búsqueda
    $('#terrenoTable_filter input').on('keyup', function () {
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
    document.getElementById("areaMetros").addEventListener("input", function() {
        const metros = parseFloat(document.getElementById('areaMetros').value);
        if (!isNaN(metros)) {
            const varas = metros * 1.4233213046;
            document.getElementById('areaVaras').value = varas.toFixed(2);
        } else {
            document.getElementById('areaVaras').value = '';
        }
    });
    
    $.validator.addMethod(
        "validarPoligono",
        function(value, element) {
            return this.optional(element) || /^[A-Za-z\s,]+$/.test(value);
        },
        "No se aceptan números ni caracteres especiales"
    );
    $.validator.addMethod(
        "validarNumero",
        function(value, element) {
            return this.optional(element) || /^\d+$/.test(value);
        },
        "Solo se aceptan números"
    );
    $.validator.addMethod(
        "validarSeccion",
        function(value, element) {
            return this.optional(element) || /^[A-Za-z\s,]+$/.test(value);
        },
        "No se aceptan números ni caracteres especiales"
    );
    $.validator.addMethod(
        "validarArea",
        function(value, element) {
            return this.optional(element) || /^\d+(\.\d+)?$/.test(value);
        },
        "Ingrese un número válido"
    );
     var formGuardar = $('#formGuardar'); // Almacenar referencia al formulario
     var validator = $('#formGuardar').validate({
         
        rules: {
           matricula: {
               required: true,
               maxlength: 18
           },
           poligono: {
               required: true,
               validarPoligono: true,
               maxlength: 1
           },
           numero: {
               required: true,
               validarNumero: true,
               maxlength: 3
           },
           seccion:{
               validarSeccion: true,
               maxlength: 1
           },
           areaMetros: {
               required: true,
               validarArea: true,
               maxlength: 20
           },
           areaVaras:{
               maxlength: 20
           }          
        },
        
        messages:{
            matricula:{
                required: 'Este campo es requerido'
            },
            poligono:{
                required: 'Este campo es requerido'
            },
            numero:{
                required: 'Este campo es requerido'
            },
            seccion:{
                required: 'Este campo es requerido'
            },
            areaMetros:{
                required: 'Este campo es requerido'
            },
            areaVaras:{
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
            if (element.attr("name") === "matricula" || element.attr("name") === "poligono" || element.attr("name") === "numero" || element.attr("name") === "seccion" || element.attr("name") === "areaMetros" || element.attr("name") === "areaVaras") {
                error.insertAfter(element);
            }        
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
               event.preventDefault();//detiene el evento del envio del form 
            var idTerreno = $('#idTerreno').val();//tomo la id
            var idProyecto = $('#proyecto').val();
            
            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            console.log(formDataArray);
            var url;//valido el tipo de url si editar o crear
            if (idTerreno) {
                url = '/ActualizarTerreno/'+idProyecto;
                //meto la id en el campo de envio
                formDataArray.push({name: 'idTerreno', value: idTerreno});
            } else {
                url = '/AgregarTerreno/'+idProyecto;
            }

            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');  // Cierra el modal
                    var table = $('#terrenoTable').DataTable();
                    table.ajax.reload(null, false);
                    mostrarMensaje(response, 'success');
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar el terreno.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });

    // metodo para mostrar el modal segun sea si editar o nuevo registro
        $(document).on('click', '.abrirModal-btn', function () {
            var idTerreno = $(this).data('id');
            var idProyecto = $("#proyecto").val();
            var modal = $('#crearModal');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            var btnSumit = document.getElementById('btnSumit');
            validator.resetForm();  // Restablecer la validación
            formGuardar.find('.is-invalid').removeClass('is-invalid');

            if (idTerreno) {
                tituloModal.text('Editar Terreno');//titulo del modal
                
                $.ajax({//utilizo ajax para obtener los datos
                    url: '/ObtenerTerreno/' + idTerreno,
                    type: 'GET',
                    success: function (response) {
                       
                        var checkboxes = document.querySelectorAll(".checkClean");

                        for (var i = 0; i < checkboxes.length; i++) {
                            checkboxes[i].checked = false;
                        }
                        $('#matricula').val(response.matricula);
                        $('#poligono').val(response.poligono);
                        $('#numero').val(response.numero);
                        $('#seccion').val(response.seccion);
                        $('#areaMetros').val(response.areaMetros);
                        $('#areaVaras').val(response.areaVaras);
                        $('#proyecto').val(response.proyecto.idProyecto);
                        $('#idTerreno').val(response.idTerreno);

                    },
                    error: function () {
                        alert('Error al obtener los datos del terreno.');
                    }
                });
            } else {
                var checkboxes = document.querySelectorAll(".checkClean");
                
                // en caso de presionar el boton de nuevo solo se abrira el modal
                tituloModal.text('Agregar Terreno');
                form.attr('action', '/AgregarTerreno/'+idProyecto);
                $('#matricula').val('');
                $('#poligono').val('');
                $('#numero').val('');
                $('#seccion').val('');
                $('#areaMetros').val('');
                $('#areaVaras').val('');
                $('#idTerreno').val('');
            }
            modal.modal('show');
   });
   
   
   // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idTerreno = $(this).data('id');

        var modal = $('#confirmarEliminarModal');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarTerrenoBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar el terreno seleccionado?</strong><br>Ten en cuenta que se eliminarán \n\
        los datos relacionados al terreno');

        // Actualizar el atributo href del botón de eliminación con el idCohorte
        eliminarBtn.data('id', idTerreno);

        modal.modal('show');
    });
   
   
   //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarTerrenoBtn', function () {
        
        var idTerreno = $(this).data('id');
        // Actualizar la acción del formulario
        $('#eliminarTerrenoForm').attr('action', '/EliminarTerreno/' + idTerreno);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarTerrenoForm').attr('action'),
            type: 'POST',
            data: $('#eliminarTerrenoForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
              $('#confirmarEliminarModal').modal('hide');
              // Recargar el DataTable
              $('#terrenoTable').DataTable().ajax.reload();
              // Mostrar el mensaje de éxito del controlador
               mostrarMensaje(response, 'success');
            },
            error: function () {
              $('#confirmarEliminarModal').modal('hide');
              // Mostrar mensaje de error en caso de que la solicitud falle
              mostrarMensaje('Error al eliminar el terreno.', 'danger');
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

