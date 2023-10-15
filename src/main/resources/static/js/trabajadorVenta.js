$(document).ready(function() {
    var idVenta=$("#idVenta").val();
     var table = $('#trabajadoresTable').DataTable({
        ajax: '/trabajadorVenta/data/'+idVenta,
        processing: true,
        serverSide: true,
        order: [[0, 'asc']],
        dom: "<'row w-100'<'col-sm-12 mb-4'B>>" +
             "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        lengthMenu: [[5, 25, 50, 100, -1], [5, 25, 50, 100, 'Todos']],
        pageLength: 5,
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
            { data: 'visitante.persona.nombre', title: 'Nombre', width: '35%' },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '30%',
                render: function (data, type, row) {
                    
                    var actionsHtml = '';
                    
                    if(hasPrivilegeVerTrabajador === true){
                        actionsHtml = '<a type="button" class="btn btn-outline-secondary btn-sm" href="/Trabajador/' + row.venta.terreno.proyecto.idProyecto +'/'+ row.visitante.persona.idPersona + '' + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
                    }
                    
                    if(hasPrivilegeEliminarTrabajador === true){
                    actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModalTrabajador-btn btn-sm" data-id="' + row.idAsignacion + '" ';
                    actionsHtml += 'data-cod="' + row.idAsignacion + '">';
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
        "validarDui",
        function(value, element) {
            return this.optional(element) || /^\d{9}$/.test(value);
        },
        "Ingrese un número válido, verifique que deben de ser nueve dígitos"
    );
    
    var formGuardar = $('#formGuardarTrabajador'); // Almacenar referencia al formulario
    var validator = $('#formGuardarTrabajador').validate({
         
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
           empleador: {
               required: true,
               maxlength: 200
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
            empleador:{
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
            if (element.attr("name") === "dui" || element.attr("name") === "nombre" || element.attr("name") === "apellido" || element.attr("name") === "empleador") {
                error.insertAfter(element);
            }        
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
               event.preventDefault();//detiene el evento del envio del form 
            var idVenta = $('#idVenta').val();//tomo la id
            var idDocumento = $('#idDocumento').val();
            var idTrabajador = $('#idVisitante').val();
            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            console.log(formDataArray);
            var url = '/AgregarTrabajadorVenta';
            formDataArray.push({name: 'idVenta', value: idVenta}, {name: 'rol', value: "TRABAJADOR"});
            
            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModalTrabajador').modal('hide');  // Cierra el modal
                    toastr.success(response);
                    $('.form-control').val('');
                    $.ajax({
                        url: "/TrabajadoresVenta/"+idVenta,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable2 = $(nuevosDatos).find('#formGuardarTrabajador');
                            $('#formGuardarTrabajador').html(elementoActualizable2.html());
                            $( '#trabajadores' ).select2( {
                                theme: "bootstrap-5",
                                width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
                                placeholder: $( this ).data( 'placeholder' ),
                                closeOnSelect: false,
                            } );
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#crearModalTrabajador').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al agregar el trabajador.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    
    $('#seleccionarModalTrabajador').on('shown.bs.modal', function () {
        var select2Elements = $(this).find('.select2');
        select2Elements.each(function () {
            $(this).on('click', function () {
                var select2ChoiceElement = document.querySelector('.select2-selection__choice');
                if (select2ChoiceElement) {
                    $("#span-trabajadores-error").addClass('d-none');
                    $('#trabajadores').removeClass('is-invalid');
                }else{
                    $("#span-trabajadores-error").removeClass('d-none');
                    $('#trabajadores').addClass('is-invalid');
                }
            });
        });
    });
    
    var formSeleccionarGuardar = $('#formSeleccionarTrabajador'); // Almacenar referencia al formulario
    var validator = $('#formSeleccionarTrabajador').validate({
        
        rules: {
           trabajadores:{
               required: true
           }          
        },
        
        messages:{
            trabajadores:{
                required: 'Este campo es requerido'
            }        
        },
        
        highlight: function(element) {
            $(element).addClass('is-invalid');
            var select2ChoiceElement = document.querySelector('.select2-selection__choice');
            if (!select2ChoiceElement) {
                $("#span-trabajadores-error").removeClass('d-none');
                $('#trabajadores').addClass('is-invalid');
            }
        },
        
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
            var select2ChoiceElement = document.querySelector('.select2-selection__choice');
            if (select2ChoiceElement) {
                $("#span-trabajadores-error").addClass('d-none');
                $('#trabajadores').removeClass('is-invalid');
            }
        },
        
        errorPlacement: function(error, element) {
            if (element.attr("name") !== "trabajadores") {
                error.insertAfter(element);
            }        
        },
         
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        
        submitHandler: function(form) {
            event.preventDefault();
            var formDataArray = formSeleccionarGuardar.serializeArray();//tomo los datos del array
            var idVenta = $('#idVenta').val();
            var url = '/SeleccionarTrabajadoresVenta';
            formDataArray.push({name: 'idVenta', value: idVenta});
            
            console.log(formDataArray);
            
            $.ajax({
                type: "POST",
                url: url,
                data: formDataArray,
                success: function(response) {
                    $('#seleccionarModalTrabajador').modal('hide');
                    table.ajax.reload();
                    toastr.success(response);
                    $.ajax({
                        url: "/TrabajadoresVenta/"+idVenta,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable2 = $(nuevosDatos).find('#formSeleccionarTrabajador');
                            $('#formSeleccionarTrabajador').html(elementoActualizable2.html());
                            $( '#trabajadores' ).select2( {
                                theme: "bootstrap-5",
                                width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
                                placeholder: $( this ).data( 'placeholder' ),
                                closeOnSelect: false,
                            } );
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#seleccionarModalTrabajador').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al agregar el trabajador.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    
    // Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModalTrabajador-btn', function () {
        var idPersona = $(this).data('id');
        var modal = $('#confirmarEliminarModalTrabajador');
        var eliminarBtn = modal.find('#eliminarTrabajadorBtn');
        eliminarBtn.data('id', idPersona);
        modal.modal('show');
    });
   
   
   //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarTrabajadorBtn', function () {
        var idVenta = $('#idVenta').val();
        var idPersona = $(this).data('id');
        // Actualizar la acción del formulario
        $('#eliminarTrabajadorForm').attr('action', '/EliminarTrabajadorVenta/' + idPersona);

        // Realizar la solicitud POST al método de eliminación
        $.ajax({
            url: $('#eliminarTrabajadorForm').attr('action'),
            type: 'POST',
            data: $('#eliminarTrabajadorForm').serialize(), // Incluir los datos del formulario en la solicitud
            success: function (response) {
                $('#confirmarEliminarModalTrabajador').modal('hide');
                table.ajax.reload();
                $.ajax({
                    url: "/TrabajadoresVenta/"+idVenta,
                    type: 'GET',
                    success: function (nuevosDatos) {
                        var elementoActualizable2 = $(nuevosDatos).find('#formGuardarTrabajador');
                        $('#formGuardarTrabajador').html(elementoActualizable2.html());
                        var elementoActualizable2 = $(nuevosDatos).find('#formSeleccionarTrabajador');
                        $('#formSeleccionarTrabajador').html(elementoActualizable2.html());
                        $( '#trabajadores' ).select2( {
                            theme: "bootstrap-5",
                            width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
                            placeholder: $( this ).data( 'placeholder' ),
                            closeOnSelect: false,
                        } );
                    },
                    error: function () {
                        alert('Error al cargar la tabla.');
                    }
                });
               toastr.success(response);
            },
            error: function (xhr, status, error) {
              $('#confirmarEliminarModalTrabajador').modal('hide');
              var errorMessage = xhr.responseText || 'Error al eliminar el trabajador.';
              toastr.error(errorMessage);
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
    
    $( '#trabajadores' ).select2( {
        theme: "bootstrap-5",
        width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
        placeholder: $( this ).data( 'placeholder' ),
        closeOnSelect: false,
    } );
    
    $( '#multiple-select-field' ).select2( {
        theme: "bootstrap-5",
        width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
        placeholder: $( this ).data( 'placeholder' ),
        closeOnSelect: false,
    } );
}); 
