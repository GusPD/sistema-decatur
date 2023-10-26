$(document).ready(function() {
    $.validator.addMethod(
        "validarRegistro",
        function(value, element) {
            return this.optional(element) || /^[0-9]{3,12}$/.test(value);
        },
        "Ingrese un número válido, no debe ser mayor a 12 dígitos"
    );
    $.validator.addMethod(
        "validarNit",
        function(value, element) {
            return this.optional(element) || /^\d{14}$/.test(value);
        },
        "Ingrese un número válido, verifique que sea un número de 14 dígitos"
    );
    $.validator.addMethod(
        "validarDui",
        function(value, element) {
            return this.optional(element) || /^\d{9}$/.test(value);
        },
        "Ingrese un número válido, verifique que sea un número de 9 dígitos"
    );
    //Formulario de agregar credito fiscal
    var formGuardar = $('#formGuardar');
    var validator = $('#formGuardar').validate({
        rules: {
            registro: {
                required: true,
                validarRegistro: true,
                maxlength: 12
            },
            nit: {
                validarNit: true,
                maxlength: 14
            },
            dui: {
                validarDui: true,
                maxlength: 9
            },
            nombre: {
                required: true,
                maxlength: 200
            },
            dirección: {
                required: true,
                maxlength: 300
            },
            giro: {
                required: true,
                maxlength: 200
            },
            fiscal: {
                required: true
            }
        },
        messages:{
            registro:{
                required: 'Este campo es requerido'
            },
            nit:{
                required: 'Este campo es requerido'
            },
            dui:{
                required: 'Este campo es requerido'
            },
            nombre:{
                required: 'Este campo es requerido'
            },
            direccion:{
                required: 'Este campo es requerido'
            },
            giro:{
                required: 'Este campo es requerido'
            },
            fiscal:{
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
            if (element.attr("name") === "registro" || element.attr("name") === "nit" || element.attr("name") === "dui" || element.attr("name") === "nombre" || element.attr("name") === "direccion" || element.attr("name") === "giro" || element.attr("name") === "fiscal") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var idVenta = $('#idVenta').val();
            var idFacturacion = $('#idFacturacion').val();
            var formDataArray = formGuardar.serializeArray();
            var url;
            if (idFacturacion) {
                url = '/ActualizarFacturacionVenta';
                formDataArray.push({name: 'idFacturacion', value: idFacturacion}, {name: 'idVenta', value: idVenta});
                formDataArray = formDataArray.filter(item => item.name !== 'fiscal');
            } else {
                url = '/AgregarFacturacionVenta';
                formDataArray.push({name: 'idFacturacion', value: idFacturacion}, {name: 'idVenta', value: idVenta});
                formDataArray = formDataArray.filter(item => item.name !== 'fiscal');
            }
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModalCreditoFiscal').modal('hide');
                    toastr.success(response);
                    $.ajax({
                        url: "/FacturacionVenta/"+idVenta,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable1 = $(nuevosDatos).find('#tabla-creditoFiscal');
                            $('#tabla-creditoFiscal').html(elementoActualizable1.html());
                            $('.form-control').val("");
                            var nitField = document.getElementById("input-nit");
                            var duiField = document.getElementById("input-dui");
                            nitField.style.display = "none";
                            duiField.style.display = "none";
                            var elementoActualizable3 = $(nuevosDatos).find('#columna-derecha');
                            $('#columna-derecha').html(elementoActualizable3.html());
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#crearModalCreditoFiscal').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al agregar la información de la facturación de crédito fiscal.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    //Función que valida el tipo de persona jurída
    document.getElementById("fiscal").addEventListener("change", function () {
        var selectedValue = this.value;
        var nitField = document.getElementById("input-nit");
        var duiField = document.getElementById("input-dui");
        nitField.style.display = "none";
        duiField.style.display = "none";
        if (selectedValue === "1") {
            nitField.style.display = "block";
            document.getElementById("nit").setAttribute("required", "required");
            document.getElementById("dui").removeAttribute("required");
            document.getElementById("dui").value="";
        } else if (selectedValue === "0") {
            duiField.style.display = "block";
            document.getElementById("dui").setAttribute("required", "required");
            document.getElementById("nit").removeAttribute("required");
            document.getElementById("nit").value="";
        }
    });
    // Método para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '#EditarCreditoFiscal', function () {
        var idFacturacion = $(this).data('id');
        var modal = $('#crearModalCreditoFiscal');
        var form = modal.find('form');
        validator.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        if (idFacturacion) {
            $.ajax({
                url: '/ObtenerFacturacion/' + idFacturacion,
                type: 'GET',
                success: function (response) {
                    $('#idFacturacion').val(response.facturacion.idFacturacion);
                    $('#registro').val(response.facturacion.registro);
                    var nitField = document.getElementById("input-nit");
                    var duiField = document.getElementById("input-dui");
                    if (response.facturacion.dui !== null) {
                        duiField.style.display = "block";
                        document.getElementById("dui").setAttribute("required", "required");
                        document.getElementById("nit").removeAttribute("required");
                        document.getElementById("fiscal").value="0";
                    } else{
                        nitField.style.display = "block";
                        document.getElementById("nit").setAttribute("required", "required");
                        document.getElementById("dui").removeAttribute("required");
                        document.getElementById("fiscal").value="1";
                    }
                    $('#nit').val(response.facturacion.nit);
                    $('#dui').val(response.facturacion.dui);
                    $('#nombre').val(response.facturacion.nombre);
                    $('#direccion').val(response.facturacion.direccion);
                    $('#giro').val(response.facturacion.giro);
                },
                error: function () {
                    alert('Error al obtener los datos de la facturacion.');
                }
            });
        } else {
            form.attr('action', '/AgregarFacturacionVenta');
            $('.form-control').val('');
        }
        modal.modal('show');
    });
    var formSeleccionarGuardar = $('#formSeleccionarPropietario');
    var validator = $('#formSeleccionarPropietario').validate({
        rules: {
            propietarios:{
                required: true
            },
            estado:{
                required: true
            } 
        },
        messages:{
            propietarios:{
                required: 'Este campo es requerido'
            },
            estado:{
                required: 'Este campo es requerido'
            }
        },
        highlight: function( element) {
            $(element).addClass('is-invalid');
            var select2ChoiceElement = document.querySelector('#propietarios');
            if (select2ChoiceElement.classList.contains('is-invalid')) {
                $("#span-propietarios-error").removeClass('d-none');
                $('#propietarios').addClass('is-invalid');
                $("#propietarios-error").addClass('d-none');
            }
        },
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
            var select2ChoiceElement = document.querySelector('#propietarios');
            if (!select2ChoiceElement.classList.contains('is-invalid')) {
                $("#span-propietarios-error").addClass('d-none');
                $('#propietarios').removeClass('is-invalid');
            }
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") === "estado") {
                error.insertAfter(element);
            }      
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var formDataArray = formSeleccionarGuardar.serializeArray();
            var idVenta = $('#idVenta').val();
            var url = '/SeleccionarPropietariosFactuacionVenta';
            formDataArray.push({name: 'idVenta', value: idVenta});
            $.ajax({
                type: "POST",
                url: url,
                data: formDataArray,
                success: function(response) {
                    $('#crearModalConsumidorFinal').modal('hide');
                    toastr.success(response);
                    $.ajax({
                        url: "/FacturacionVenta/"+idVenta,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable1 = $(nuevosDatos).find('#tabla-consumidorFinal');
                            $('#tabla-consumidorFinal').html(elementoActualizable1.html());
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
                    $('#crearModalConsumidorFinal').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al seleccionar el propietario para la factura de consumidor final.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    // Método para mostrar el modal de eliminación
    $(document).on('click', '#EliminarCreditoFiscal', function () {
        var idFacturacion = $(this).data('id');
        var modal = $('#confirmarEliminarModal');
        var eliminarBtn = modal.find('#eliminarFacturacionBtn');
        eliminarBtn.data('id', idFacturacion);
        modal.modal('show');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarFacturacionBtn', function () {
        var idFacturacion = $(this).data('id');
        var idVenta = $('#idVenta').val();
        $('#eliminarFacturacionForm').attr('action', '/EliminarFacturacionVenta/' + idFacturacion);
        $.ajax({
            url: $('#eliminarFacturacionForm').attr('action'),
            type: 'POST',
            data: $('#eliminarFacturacionForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModal').modal('hide');
                toastr.success(response);
                $.ajax({
                    url: "/FacturacionVenta/"+idVenta,
                    type: 'GET',
                    success: function (nuevosDatos) {
                        var elementoActualizable1 = $(nuevosDatos).find('#tabla-creditoFiscal');
                        $('#tabla-creditoFiscal').html(elementoActualizable1.html());
                        $('.form-control').val("");
                        var nitField = document.getElementById("input-nit");
                        var duiField = document.getElementById("input-dui");
                        nitField.style.display = "none";
                        duiField.style.display = "none";
                        var elementoActualizable3 = $(nuevosDatos).find('#columna-derecha');
                        $('#columna-derecha').html(elementoActualizable3.html());
                    },
                    error: function () {
                        alert('Error al cargar la tabla.');
                    }
                });
            },
            error: function (xhr, status, error) {
                $('#confirmarEliminarModal').modal('hide');
                var errorMessage = xhr.responseText || 'Error al eliminar la facturación del crédito fiscal de la venta.';
                toastr.error(errorMessage);
            }
        });
    }); 
    //Función para definir el uso de la libreria selec2
    var $select = $( '#propietarios' ).select2( {
        theme: "bootstrap-5",
        width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
        placeholder: $( this ).data( 'placeholder' ),
        dropdownParent: $('#crearModalConsumidorFinal .modal-body'),
        closeOnSelect: false
    } );
    $select.on('change', function() {
        $(this).trigger('blur');
    });
}); 
