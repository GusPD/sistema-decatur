$(document).ready(function() {
    //Tabla
    var idVenta=$("#idVenta").val();
    var table = $('#propietariosTable').DataTable({
        ajax: '/propietarioVenta/data/'+idVenta,
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
            { data: 'propietario.persona.nombre', title: 'Nombre', width: '35%' },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '30%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    if(hasPrivilegeVerPropietario === true){
                        actionsHtml = '<a type="button" class="btn btn-outline-secondary btn-sm" href="/InformacionPropietario/' + row.venta.terreno.proyecto.idProyecto +'/'+ row.propietario.persona.idPersona + '' + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
                    }
                    if(hasPrivilegeEliminarPropietario === true){
                        actionsHtml += '<button type="button" class="btn btn-outline-danger eliminarModalPropietario-btn btn-sm" data-id="' + row.idAsignacion + '" ';
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
    //Formulario de agregar
    var formGuardar = $('#formGuardarPropietario');
    var validator = $('#formGuardarPropietario').validate({
        rules: {
            dui: {
                required: true,
                validarDui: true,
                maxlength: 9
            },
            nombreP: {
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
            },
            estadoP:{
                required: true
            } 
        },
        messages:{
            dui:{
                required: 'Este campo es requerido'
            },
            nombreP:{
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
            },
            estadoP:{
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
            if (element.attr("name") === "dui" || element.attr("name") === "nombreP" || element.attr("name") === "apellido" || element.attr("name") === "profesion" || element.attr("name") === "direccionCasa" || element.attr("name") === "lugarTrabajo" || element.attr("name") === "direccionTrabajo" || element.attr("name") === "estadoP") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var idVenta = $('#idVenta').val();
            var idDocumento = $('#idDocumento').val();
            var idPersona = $('#idPersona').val();
            var idPropietario = $('#idPropietario').val();
            var idAsignacion = $('#idAsignacion').val();
            var formDataArray = formGuardar.serializeArray();
            if (idPersona) {
                url = '/ActualizarPropietarioVenta';
                formDataArray.push({name: 'idPersona', value: idPersona}, {name: 'idPropietario', value: idPropietario}, {name: 'idDocumento', value: idDocumento},{name: 'idVenta', value: idVenta},{name: 'idAsignacion', value: idAsignacion});
            } else {
                var url = '/AgregarPropietarioVenta';
                formDataArray.push({name: 'idVenta', value: idVenta});
            }
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModalPropietario').modal('hide');
                    toastr.success(response);
                    $('.form-control').val('');
                    $.ajax({
                        url: "/PropietariosVenta/"+idVenta,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            table.ajax.reload();
                            var elementoActualizable2 = $(nuevosDatos).find('#formGuardarPropietario');
                            $('#formGuardarPropietario').html(elementoActualizable2.html());
                            $( '#propietarios' ).select2( {
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
                    $('#crearModalPropietario').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al agregar el propietario.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    // Método para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModalPropietario-btn', function () {
        var idAsignacion = $(this).data('id');
        var modal = $('#crearModalPropietario');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        validator.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        if (idAsignacion) {
            tituloModal.text('Editar Propietario');
            $.ajax({
                url: '/ObtenerPropietarioProyecto/' + idAsignacion,
                type: 'GET',
                success: function (response) {
                    $('#idPersona').val(response.asignacion.propietario.persona.idPersona);
                    $('#dui').val(response.asignacion.propietario.persona.dui);
                    $('#nombreP').val(response.asignacion.propietario.persona.nombre);
                    $('#apellido').val(response.asignacion.propietario.persona.apellido);
                    $('#idPropietario').val(response.asignacion.propietario.idPropietario);
                    $('#profesion').val(response.asignacion.propietario.profesion);
                    $('#direccionCasa').val(response.asignacion.propietario.direccionCasa);
                    $('#lugarTrabajo').val(response.asignacion.propietario.lugarTrabajo);
                    $('#direccionTrabajo').val(response.asignacion.propietario.direccionTrabajo);
                    $('#idDocumento').val(response.asignacion.propietario.idDocumento);
                    $('#estadoP').val(response.asignacion.estado);
                    $('#idAsignacion').val(response.asignacion.idAsignacion);
                },
                error: function () {
                    alert('Error al obtener los datos del propietario.');
                }
            });
        } else {
            tituloModal.text('Agregar Propietario');
            form.attr('action', '/AgregarPropietarioVenta');
            $('.form-control').val('');
        }
        modal.modal('show');
    });
    //Formulario de seleccionar
    $('#seleccionarModalPropietario').on('shown.bs.modal', function () {
        var select2Elements = $(this).find('.select2');
        select2Elements.each(function () {
            $(this).on('click', function () {
                var select2ChoiceElement = document.querySelector('.select2-selection__choice');
                if (select2ChoiceElement) {
                    $("#span-propietarios-error").addClass('d-none');
                    $('#propietarios').removeClass('is-invalid');
                }else{
                    $("#span-propietarios-error").removeClass('d-none');
                    $('#propietarios').addClass('is-invalid');
                }
            });
        });
    });
    var formSeleccionarGuardar = $('#formSeleccionarPropietario');
    var validator = $('#formSeleccionarPropietario').validate({
        rules: {
            propietarios:{
                required: true
            },
            estadoS:{
                required: true
            } 
        },
        messages:{
            propietarios:{
                required: 'Este campo es requerido'
            },
            estadoS:{
                required: 'Este campo es requerido'
            }
        },
        highlight: function( element) {
            $(element).addClass('is-invalid');
            var select2ChoiceElement = document.querySelector('.select2-selection__choice');
            if (!select2ChoiceElement) {
                $("#span-propietarios-error").removeClass('d-none');
                $('#propietarios').addClass('is-invalid');
            }
        },
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
            var select2ChoiceElement = document.querySelector('.select2-selection__choice');
            if (select2ChoiceElement) {
                $("#span-propietarios-error").addClass('d-none');
                $('#propietarios').removeClass('is-invalid');
            }
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") === "estadoS") {
                error.insertAfter(element);
            }      
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var formDataArray = formSeleccionarGuardar.serializeArray();
            var idVenta = $('#idVenta').val();
            var url = '/SeleccionarPropietariosVenta';
            formDataArray.push({name: 'idVenta', value: idVenta});
            $.ajax({
                type: "POST",
                url: url,
                data: formDataArray,
                success: function(response) {
                    $('#seleccionarModalPropietario').modal('hide');
                    toastr.success(response);
                    $.ajax({
                        url: "/PropietariosVenta/"+idVenta,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            table.ajax.reload();
                            var elementoActualizable2 = $(nuevosDatos).find('#formSeleccionarPropietario');
                            $('#formSeleccionarPropietario').html(elementoActualizable2.html());
                            $( '#propietarios' ).select2( {
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
                    $('#seleccionarModalPropietario').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al agregar el propietario.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    //Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModalPropietario-btn', function () {
        var idPersona = $(this).data('id');
        var modal = $('#confirmarEliminarModalPropietario');
        var eliminarBtn = modal.find('#eliminarPropietarioBtn');
        eliminarBtn.data('id', idPersona);
        modal.modal('show');
    });
   //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarPropietarioBtn', function () {
        var idVenta = $('#idVenta').val();
        var idPersona = $(this).data('id');
        $('#eliminarPropietarioForm').attr('action', '/EliminarPropietarioVenta/' + idPersona);
        $.ajax({
            url: $('#eliminarPropietarioForm').attr('action'),
            type: 'POST',
            data: $('#eliminarPropietarioForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModalPropietario').modal('hide');
                table.ajax.reload();
                $.ajax({
                    url: "/PropietariosVenta/"+idVenta,
                    type: 'GET',
                    success: function (nuevosDatos) {
                        var elementoActualizable2 = $(nuevosDatos).find('#formGuardarPropietario');
                        $('#formGuardarPropietario').html(elementoActualizable2.html());
                        var elementoActualizable2 = $(nuevosDatos).find('#formSeleccionarPropietario');
                        $('#formSeleccionarPropietario').html(elementoActualizable2.html());
                        $( '#propietarios' ).select2( {
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
              $('#confirmarEliminarModalPropietario').modal('hide');
              var errorMessage = xhr.responseText || 'Error al eliminar el propietario.';
              toastr.error(errorMessage);
            }
        });
    });
    //Función para definir el uso de la libreria selec2
    $( '#propietarios' ).select2( {
        theme: "bootstrap-5",
        width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
        placeholder: $( this ).data( 'placeholder' ),
        closeOnSelect: false,
    } );
    //Función para definir el uso de la libreria select2
    $( '#multiple-select-field' ).select2( {
        theme: "bootstrap-5",
        width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
        placeholder: $( this ).data( 'placeholder' ),
        closeOnSelect: false,
    } );
}); 
