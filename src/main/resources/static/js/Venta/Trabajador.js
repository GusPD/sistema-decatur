$(document).ready(function() {
    //Tabla
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
                        actionsHtml = '<a title="Ver" title="Ver" type="button" class="btn font-size-small btn-outline-secondary btn-sm" href="/InformacionTrabajador/' + row.venta.terreno.proyecto.idProyecto +'/'+ row.visitante.persona.idPersona + '' + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
                    }
                    if(hasPrivilegeEliminarTrabajador === true){
                        actionsHtml += '<button title="Eliminar" title="Eliminar" type="button" class="btn font-size-small btn-outline-danger eliminarModalTrabajador-btn btn-sm" data-id="' + row.idAsignacion + '" ';
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
            }
        },
        search: true
    });
    //Formulario de agregar
    $.validator.addMethod(
        "validarDocumento",
        function(value, element) {
            var mascara = $("#tipoDocumento option:selected").data("mascara");
            if (mascara) {
                var regex = new RegExp(mascara);
                return this.optional(element) || regex.test(value);
            } else {
                return true;
            }
        },
        "Ingrese un número válido para el tipo de documento"
    );
    var formGuardar = $('#formGuardarTrabajador');
    var validatorAgregar = $('#formGuardarTrabajador').validate({
        rules: {
            tipoDocumento: {
                required: true,
            },
            numero: {
                required: true,
                validarDocumento: true,
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
            tipoDocumento:{
                required: 'Este campo es requerido'
            },
            numero:{
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
            if (element.attr("name") === "tipoDocumento" || element.attr("name") === "numero" || element.attr("name") === "nombre" || element.attr("name") === "apellido" || element.attr("name") === "empleador") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault(); 
            var idVenta = $('#idVenta').val();
            var formDataArray = formGuardar.serializeArray();
            var url = '/AgregarTrabajadorVenta';
            formDataArray.push({name: 'idVenta', value: idVenta}, {name: 'rol', value: "TRABAJADOR"});
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModalTrabajador').modal('hide');
                    toastr.success(response);
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
                                closeOnSelect: false
                            } );
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#crearModalTrabajador').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al agregar el trabajador.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    var formSeleccionar = $('#formSeleccionarTrabajador');
    var validatorSeleccionar = $('#formSeleccionarTrabajador').validate({
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
            var select2ChoiceElement = document.querySelector('#trabajadores');
            if (select2ChoiceElement.classList.contains('is-invalid')) {
                $("#span-trabajadores-error").removeClass('d-none');
                $('#trabajadores').addClass('is-invalid');
                $("#trabajadores-error").addClass('d-none');
            }
        },
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
            var select2ChoiceElement = document.querySelector('#trabajadores');
            if (!select2ChoiceElement.classList.contains('is-invalid')) {
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
            var formDataArray = formSeleccionar.serializeArray();
            var idVenta = $('#idVenta').val();
            var url = '/SeleccionarTrabajadoresVenta';
            formDataArray.push({name: 'idVenta', value: idVenta});
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
                                closeOnSelect: false
                            } );
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#seleccionarModalTrabajador').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al agregar el trabajador.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    // Método para mostrar el modal agregar trabajador
    $(document).on('click', '#AgregarTrabajador', function () {
        validatorAgregar.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        form.attr('action', '/AgregarTrabajadorVenta');
        $('.form-control').val('');
    });
    // Método para mostrar el modal seleccionar trabajador
    $(document).on('click', '#SeleccionarTrabajador', function () {
        validatorSeleccionar.resetForm();
        formSeleccionar.find('.is-invalid').removeClass('is-invalid');
        $('.form-control').val('');
        $("#span-trabajadores-error").addClass('d-none');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarTrabajadorBtn', function () {
        var idVenta = $('#idVenta').val();
        var idPersona = $(this).data('id');
        $('#eliminarTrabajadorForm').attr('action', '/EliminarTrabajadorVenta/' + idPersona);
        $.ajax({
            url: $('#eliminarTrabajadorForm').attr('action'),
            type: 'POST',
            data: $('#eliminarTrabajadorForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModalTrabajador').modal('hide');
                table.ajax.reload();
                toastr.success(response);
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
                            closeOnSelect: false
                        } );
                    },
                    error: function () {
                        alert('Error al cargar la tabla.');
                    }
                });
            },
            error: function (xhr, status, error) {
              $('#confirmarEliminarModalTrabajador').modal('hide');
              var errorMessage = xhr.responseText || 'Error al eliminar el trabajador.';
              toastr.error(errorMessage);
            }
        });
        
    });
    //Función para inicializar la libreria de select2
     var $select = $( '#trabajadores' ).select2( {
        theme: "bootstrap-5",
        width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
        placeholder: $( this ).data( 'placeholder' ),
        dropdownParent: $('#seleccionarModalTrabajador .modal-body'),
        closeOnSelect: false
    } );
    $select.on('change', function() {
        $(this).trigger('blur');
    });
}); 
