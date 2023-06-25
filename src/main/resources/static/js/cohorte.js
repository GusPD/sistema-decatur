$(document).ready(function() {
    
    var fechaApertura = document.getElementById("fechaApertura");
        
        $(fechaApertura).inputmask('99/99/9999', {
            showMaskOnFocus: false,
            showMaskOnHover: false
        });
    
    $('#cohorteTable').DataTable({
        ajax: '/cohorte/data?idMaestria=' + idMaestria,
        processing: true,
        serverSide: true,
        order: [[2, 'desc']],
        dom: "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        columns: [
            { 
                data: 'nombreCohorte',width: '22%'
            },
            { data: 'fechaApertura', width: '22%'
                ,

            render: function (data, type, row) {
            // Separa la fecha en año, mes y día
            var parts = data.split("-");
            var year = parts[0];
            var month = parts[1];
            var day = parts[2];
        
            // Construye la fecha en formato dd/mm/yyyy
            var formattedDate = day + '/' + month + '/' + year;
        
            // Devuelve la fecha formateada
            return formattedDate;
    }
            
            },
            { 
                data: 'estadoCohorte',width: '22%',
                render: function (data, type, row) {
                    var estado = (data === 1) ? 'Activo' : 'Inactivo';
                    return estado;         
                }
            },
            {
                data: null,
                title: 'Acciones',
                class:'text-center',
                sortable: false,
                searchable: false,
                width: '34%',
                render: function (data, type, row) {
                    // Aquí puedes construir el HTML para las acciones según tus necesidades
//                    var actionsHtml = '<a type="button" class="btn btn-outline-secondary" href="/DetallePlanEstudio/' + row.idPlanEstudio + '">';
//                    actionsHtml += '<i class="bi bi-eye"></i></a>';
                    
                    var actionsHtml = '';
                    
                    actionsHtml = '<a class="btn btn-outline-secondary" href="/DetalleCohorte/' + row.idCohorte + '">';
                    actionsHtml += '<i class="bi bi-gear-fill"></i></a>';
                    
                    if(hasPrivilegeEditarCohorte === true){
                    actionsHtml += '<button type="button" class="btn btn-outline-warning abrirModal-btn" data-bs-toggle="modal" ';
                    actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idCohorte + '" data-modo="actualizar">';
                    actionsHtml += '<i class="bi bi-pencil-square"></i></button>';
                    }
                    
                    if(hasPrivilegeEliminarCohorte === true){
                    actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModal-btn" data-id="' + row.idCohorte + '" ';
                    actionsHtml += 'data-cod="' + row.idCohorte + '">';
                    actionsHtml += '<i class="bi bi-trash"></i></button>';
                    }
                    
                    //Boton para habilitar/deshabilitar
                    //actionsHtml += '<button type="button" class="btn btn-outline-secondary" ">';
                    //actionsHtml += '<i class="bi bi-check"></i></button>';
                    
                    //Boton para aspirantes
//                    actionsHtml += '<a href="/PostuladosCohorte/' + row.idCohorte + '" type="button" class="btn btn-outline-success" ">';
////                    actionsHtml += 'data-cod="' + row.idCohorte + '">';
//                    actionsHtml += '<i class="bi bi-person-fill-add"></i></a>';
                    
//                    actionsHtml += '<button type="button" class="btn btn-outline-primary inscribirMateriaModal-btn" data-id="' + row.idCohorte + '" ';
//                    actionsHtml += 'data-maestria="' + row.idMaestria.idMaestria + '">';
//                    actionsHtml += '<i class="bi bi-calendar-plus"></i></button>';
                    
//                    actionsHtml += '<a href="/ProfesorCohorte/' + row.idCohorte + '" type="button" class="btn btn-outline-dark" ">';
////                    actionsHtml += 'data-cod="' + row.idCohorte + '">';
//                    actionsHtml += '<i class="bi bi-building-check"></i></i></a>';
                    
                    return actionsHtml;
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
    
     //fecha
    $.validator.addMethod(
    "validarFecha",
    function(value, element) {
        return this.optional(element) || /^(0[1-9]|1\d|2\d|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/.test(value);
    },
    "Ingresa una fecha en formato DD/MM/AAAA"
    );
    
    var formGuardar = $('#formGuardar'); // Almacenar referencia al formulario
    var validator = $('#formGuardar').validate({
       
       rules: {
           
           nombreCohorte: {
               required: true
           },
           
           fechaApertura:{
               required: true,
               validarFecha: true
           }
       },
       
       messages: {
           nombreCohorte: {
               required: 'Este campo es requerido'
           },
           
           fechaApertura: {
               required: 'Este campo es requerido',
               validarFecha: 'Ingrese una fecha valida en formato dd/mm/yyyy'
           } 
       },
       
       highlight: function(element) {
            $(element).addClass('is-invalid');
       },
       
       unhighlight: function(element) {
            $(element).removeClass('is-invalid');
        },
        
        errorPlacement: function(error, element) {
            if (element.attr("name") === "nombreCohorte" || element.attr("name") === "fechaApertura" ) {
                error.insertAfter(element);
              }
         },
       
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
            event.preventDefault(); // detiene el evento del envío del formulario
            var idCohorte = $('#idCohorte').val(); // tomo la id
            
            var formDataArray = formGuardar.serializeArray(); // tomo los datos del array
            var url; // valido el tipo de URL si editar o crear
            
            if(idCohorte){
                url = '/ActualizarCohorte';
                formDataArray.push({ name: 'idCohorte', value: idCohorte });
                formDataArray.push({ name: 'idMaestria', value: idMaestria });
            }else{
                url = '/AgregarCohorte';
                formDataArray.push({ name: 'idMaestria', value: idMaestria });
            }
            
            // Convertir el arreglo en un objeto
            var formData = {};
            $.map(formDataArray, function(n, i) {
                formData[n['name']] = n['value'];
            });
            
             // realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                success: function(response) {
                    $('#crearModal').modal('hide'); // Cierra el modal
                    var table = $('#cohorteTable').DataTable();
                    table.ajax.reload(null, false); // Recargar sin reiniciar la paginación
                    mostrarMensaje(response, 'success');
                },
                error: function(xhr, status, error) {
                    $('#crearModal').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar la cohorte.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });
    
     // metodo para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModal-btn', function () {
        var idCohorte = $(this).data('id');
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        var btnSumit = document.getElementById('btnSumit');
        validator.resetForm();  // Restablecer la validación
        formGuardar.find('.is-invalid').removeClass('is-invalid');

        if (idCohorte) {
            tituloModal.text('Editar Cohorte');//titulo del modal
            
            $('.oculto').removeAttr('hidden');

            
            $.ajax({//utilizo ajax para obtener los datos
                url: '/ObtenerCoherte/' + idCohorte,
                type: 'GET',
                success: function (response) {
                    $('#idCohorte').val(response.idCohorte);
                    $('#idMaestria').val(response.idMaestria);
                    $('#nombreCohorte').val(response.nombreCohorte);
                    
                    var fechaApertura = response.fechaApertura;
                    var fechaFormateada = formatearFecha(fechaApertura);
                    $('#fechaApertura').val(fechaFormateada);
                    $('#estadoCohorte').val(response.estadoCohorte);
   
                },
                error: function () {
                    alert('Error al obtener los de la cohorte');
                }
            });
        } else {
            // en caso de presionar el boton de nuevo solo se abrira el modal
            $('.oculto').attr('hidden', true);
            tituloModal.text('Agregar Cohorte');
            form.attr('action', '/AgregarCohorte');
            $('#idCohorte').val('');
            $('#idMaestria').val('');
            $('#nombreCohorte').val('');
            $('#fechaApertura').val('');
            //$('#estadoCohorte').val('');
        }
        modal.modal('show');
    });
    
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idCohorte = $(this).data('id');
//        var codPlan = $(this).data('cod');

        var modal = $('#confirmarEliminarModal');
        var tituloModal = modal.find('.modal-title');
        var cuerpoModal = modal.find('.modal-body');
        var eliminarBtn = modal.find('#eliminarCohorteBtn');

        // Actualizar el contenido del modal con los parámetros recibidos
        tituloModal.text('Confirmar eliminación');
        cuerpoModal.html('<strong>¿Estás seguro de eliminar la cohorte seleccionada?</strong><br>Ten en cuenta que se eliminarán \n\
        los datos relacionados a la cohorte ');

        // Actualizar el atributo href del botón de eliminación con el idCohorte
        eliminarBtn.data('id', idCohorte);

        modal.modal('show');
    });
    
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarCohorteBtn', function () {
        
        var idCohorte = $(this).data('id');
        // Actualizar la acción del formulario con el idMaestria
        $('#eliminarCohorteForm').attr('action', '/EliminarCohorte/' + idCohorte);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarCohorteForm').attr('action'),
            type: 'POST',
            data: $('#eliminarCohorteForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
              $('#confirmarEliminarModal').modal('hide');
              // Recargar el DataTable
              $('#cohorteTable').DataTable().ajax.reload();
              // Mostrar el mensaje de éxito del controlador
               mostrarMensaje(response, 'success');
            },
            error: function () {
              $('#confirmarEliminarModal').modal('hide');
              // Mostrar mensaje de error en caso de que la solicitud falle
              mostrarMensaje('Error al eliminar la cohorte.', 'danger');
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
    
    function formatearFecha(fecha) {
    //Especifico UTCDate para tomar la fecha del sistema
        var fechaObjeto = new Date(fecha);
        var dia = fechaObjeto.getUTCDate();
        var mes = fechaObjeto.getUTCMonth() + 1;
        var anio = fechaObjeto.getUTCFullYear();

        if (dia < 10) {
            dia = '0' + dia;
        }
        if (mes < 10) {
            mes = '0' + mes;
        }

        return dia + '/' + mes + '/' + anio;
}

    
    
});

    

