document.addEventListener('DOMContentLoaded', function() {
    $(document).ready(function() {
        actualizarEventos();
        function actualizarEventos() {
            //Método que permite calcular el monto si se digita el precio
            document.getElementById("precio").addEventListener("input", function() {
                const precio = parseFloat(document.getElementById('precio').value);
                const descuento = parseFloat(document.getElementById('descuento').value);
                if (!isNaN(precio) && !isNaN(descuento)) {
                    const monto = precio - descuento;
                    document.getElementById('monto').value = monto.toFixed(2);
                } else if(!isNaN(precio)){
                    const monto = precio;
                    document.getElementById('monto').value = monto.toFixed(2);
                }else if(!isNaN(descuento)){
                    const monto = 0 - descuento;
                    document.getElementById('monto').value = monto.toFixed(2);
                }else{
                    document.getElementById('monto').value = '0.00';
                }
            });
            //Método que permite calcular el monto si se digita el descuento
            document.getElementById("descuento").addEventListener("input", function() {
                const precio = parseFloat(document.getElementById('precio').value);
                const descuento = parseFloat(document.getElementById('descuento').value);
                if (!isNaN(precio) && !isNaN(descuento)) {
                    const monto = precio - descuento;
                    document.getElementById('monto').value = monto.toFixed(2);
                } else if(!isNaN(precio)){
                    const monto = precio;
                    document.getElementById('monto').value = monto.toFixed(2);
                }else if(!isNaN(descuento)){
                    const monto = 0 - descuento;
                    document.getElementById('monto').value = monto.toFixed(2);
                }else{
                    document.getElementById('monto').value = '0.00';
                }
            });
            //Método que permite calcular la cuota Ki si se digita el plazo
            document.getElementById("plazo").addEventListener("input", function() {
                const tasa = parseFloat(document.getElementById('tasa').value);
                const plazo = parseFloat(document.getElementById('plazo').value);
                const monto = parseFloat(document.getElementById('montoF').value);
                if (!isNaN(tasa) && !isNaN(plazo) && !isNaN(monto) ){
                    const valorCuota = calcularCuotaKi(tasa, plazo, monto);
                    document.getElementById('cuotaKi').value = valorCuota.toFixed(2);
                }else{
                    document.getElementById('cuotaKi').value = "0.00";
                }
            });
            //Método que permite calcular la cuota Ki si se digita la tasa
            document.getElementById("tasa").addEventListener("input", function() {
                const tasa = parseFloat(document.getElementById('tasa').value);
                const plazo = parseFloat(document.getElementById('plazo').value);
                const monto = parseFloat(document.getElementById('montoF').value);
                if (!isNaN(tasa) && !isNaN(plazo) && !isNaN(monto) ){
                    const valorCuota = calcularCuotaKi(tasa, plazo, monto);
                    document.getElementById('cuotaKi').value = valorCuota.toFixed(2);
                }else{
                    document.getElementById('cuotaKi').value = "0.00";
                }
            });
            //Método que calcula la cuota Ki
            function calcularCuotaKi(tasa, nper, va) {
                tasa = (tasa / 100)/12;
                const cuota = (va * tasa) / (1 - Math.pow(1 + tasa, -nper));
                return cuota;
            }
        }
        //Formulario de editar
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
        $.validator.addMethod(
            "validarMonto",
            function(value, element) {
                return this.optional(element) || /^\d+(\.\d+)?$/.test(value);
            },
            "Ingrese un número válido"
        );
        $.validator.addMethod(
            "validarPlazo",
            function(value, element) {
                return this.optional(element) || /^\d+$/.test(value);
            },
            "Solo se aceptan números"
        );
        $.validator.addMethod(
            "validarTasa",
            function(value, element) {
                return this.optional(element) || /^(100(\.0+)?|[0-9]?[0-9](\.\d+)?)$/.test(value);
            },
            "Ingrese un número válido"
        );
        $.validator.addMethod(
            "validarCuotaKi",
            function(value, element) {
                return this.optional(element) || /^\d+(\.\d+)?$/.test(value);
            },
            "Ingrese un número válido"
        );
        $.validator.addMethod(
            "validarCuotaMantenimiento",
            function(value, element) {
                return this.optional(element) || /^\d+(\.\d+)?$/.test(value);
            },
            "Ingrese un número válido"
        );
        $.validator.addMethod(
            "validarMultaMantenimiento",
            function(value, element) {
                return this.optional(element) || /^\d+(\.\d+)?$/.test(value);
            },
            "Ingrese un número válido"
        );
        $.validator.addMethod(
            "validarMultaFinanciamiento",
            function(value, element) {
                return this.optional(element) || /^\d+(\.\d+)?$/.test(value);
            },
            "Ingrese un número válido"
        );
        $.validator.addMethod(
            "fechaMayorFinanciamiento", 
            function(value, element) {
                var fechaIngresada = new Date(value);
                var fechaFinanciamiento = new Date($("#fechaAplicacionFinanciamiento").val());
                return fechaIngresada > fechaFinanciamiento;
            }, 
            "La fecha debe ser mayor que la fecha anterior."
        );
        $.validator.addMethod(
            "fechaMayorMantenimiento", 
            function(value, element) {
                var fechaIngresada = new Date(value);
                var fechaMantenimiento = new Date($("#fechaAplicacionMantenimiento").val());
                return fechaIngresada > fechaMantenimiento;
            }, 
            "La fecha debe ser mayor que la fecha anterior."
        );
        var formGuardar = $('#formGuardar');
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
               },
               monto:{
                   validarMonto: true
               },
               plazo:{
                   required: true,
                   validarPlazo: true,
                   maxlength: 3
               },
               tasa: {
                   required: true,
                   validarTasa: true,
                   maxlength: 6
               },
               cuotaKi:{
                   validarMonto: true
               },
               cuotaMantenimiento: {
                   required: true,
                   validarCuotaMantenimiento: true,
                   maxlength: 9
               },
               multaMantenimiento: {
                   required: true,
                   validarMultaMantenimiento: true,
                   maxlength: 9
               },
               multaFinanciamiento: {
                   required: true,
                   validarMultaFinanciamiento: true,
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
                },
                plazo:{
                    required: 'Este campo es requerido'
                },
                tasa: {
                    required: 'Este campo es requerido'
                },
                cuotaMantenimiento: {
                    required: 'Este campo es requerido'
                },
                multaMantenimiento: {
                    required: 'Este campo es requerido'
                },
                multaFinanciamiento: {
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
                if (element.attr("name") === "nombre" || element.attr("name") === "fecha" || element.attr("name") === "precio" || element.attr("name") === "descuento" || element.attr("name") === "monto" || element.attr("name") === "plazo" || element.attr("name") === "tasa" || element.attr("name") === "cuotaKi" || element.attr("name") === "cuotaMantenimiento" || element.attr("name") === "multaFinanciamiento" || element.attr("name") === "multaMantenimiento") {
                    error.insertAfter(element);
                }        
            },
            errorElement: 'div',
            errorClass: 'invalid-feedback',
            submitHandler: function(form) {
                event.preventDefault();
                const fechaInputValue = $('#fecha').val();
                const fechaInput = new Date(fechaInputValue);
                const fechaLocal = new Date(fechaInput.getTime() + fechaInput.getTimezoneOffset() * 60000);

                function addLeadingZero(number) {
                    return number < 10 ? `0${number}` : number;
                }
                const day = addLeadingZero(fechaLocal.getDate());
                const month = addLeadingZero(fechaLocal.getMonth() + 1);
                const year = fechaLocal.getFullYear();
                const formattedDate = `${day}/${month}/${year}`;
                var idVenta = $('#idVenta').val();
                var idListDocumento = $('#idListDocumento').val();
                var estado = $('#estado').val();
                var idTerreno = $('#idTerreno').val();
                var formDataArray = formGuardar.serializeArray();
                var fechaIndex = formDataArray.findIndex(item => item.name === 'fecha');
                formDataArray[fechaIndex].value = formattedDate;
                var url;
                if (idVenta) {
                    url = '/ActualizarVenta/'+idTerreno;
                    formDataArray.push({name: 'idVenta', value: idVenta},{name: 'estado', value: estado},{name: 'idListDocumento', value: idListDocumento});
                } else {
                    url = '/AgregarVenta/'+idTerreno;
                    formDataArray.push({name: 'estado', value: estado});
                }
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: formDataArray,
                    success: function (response) {
                        $('#crearModal').modal('hide');
                        toastr.success(response);
                        $.ajax({
                            url: "/InformacionVenta/"+idVenta,
                            type: 'GET',
                            success: function (nuevosDatos) {
                                var elementoActualizable = $(nuevosDatos).find('#page-informacion');
                                $('#page-informacion').html(elementoActualizable.html());
                                var elementoActualizable2 = $(nuevosDatos).find('#formGuardar');
                                $('#formGuardar').html(elementoActualizable2.html());
                                actualizarEventos();
                            },
                            error: function () {
                                alert('Error al cargar la tabla.');
                            }
                        });
                    },
                    error: function (xhr, status, error) {
                        $('#crearModal').modal('hide');
                        var errorMessage = xhr.responseText || 'Error al actualizar la venta.';
                        toastr.error(errorMessage);
                    }
                });
            }
        });
        var formGuardarFinanciamiento = $('#formGuardarFinanciamiento');
        var validatorFinanciamiento = $('#formGuardarFinanciamiento').validate({
            rules: {
                montoF: {
                    required: true
                },
                prima: {
                    required: true
                },
                financiamiento: {
                    required: true
                },
                fechaAplicacionF: {
                   required: true,
                   fechaMayorFinanciamiento: true,
                   maxlength: 10
                },
                plazo:{
                    required: true,
                    validarPlazo: true,
                    maxlength: 3
                },
                tasa: {
                    required: true,
                    validarTasa: true,
                    maxlength: 6
                },
                cuotaKi:{
                    validarMonto: true
                },
                multaFinanciamiento: {
                    required: true,
                    validarMultaFinanciamiento: true,
                    maxlength: 9
                }
            },
            messages:{
                montoF: {
                    required: 'Este campo es requerido'
                },
                prima: {
                    required: 'Este campo es requerido'
                },
                financiamiento: {
                    required: 'Este campo es requerido'
                },
                fechaAplicacionF:{
                    required: 'Este campo es requerido'
                },
                plazo:{
                    required: 'Este campo es requerido'
                },
                tasa: {
                    required: 'Este campo es requerido'
                },
                multaFinanciamiento: {
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
                if (element.attr("name") === "montoF" || element.attr("name") === "prima" || element.attr("name") === "financiamiento" || element.attr("name") === "fechaAplicacionF" || element.attr("name") === "plazo" || element.attr("name") === "tasa" || element.attr("name") === "cuotaKi" || element.attr("name") === "multaFinanciamiento") {
                    error.insertAfter(element);
                }        
            },
            errorElement: 'div',
            errorClass: 'invalid-feedback',
            submitHandler: function(form) {
                event.preventDefault();
                const fechaInputValue = $('#fechaAplicacionF').val();
                const fechaInput = new Date(fechaInputValue);
                const fechaLocal = new Date(fechaInput.getTime() + fechaInput.getTimezoneOffset() * 60000);

                function addLeadingZero(number) {
                    return number < 10 ? `0${number}` : number;
                }
                const day = addLeadingZero(fechaLocal.getDate());
                const month = addLeadingZero(fechaLocal.getMonth() + 1);
                const year = fechaLocal.getFullYear();
                const formattedDate = `${day}/${month}/${year}`;
                var idVenta = $('#idVenta').val();
                var monto = $('#financiamiento').val();
                var cuota = $('#cuotaKi').val();
                var multa = $('#multaFinanciamiento').val();
                var formDataArray = formGuardarFinanciamiento.serializeArray();
                var url = '/AgregarFinanciamientoVenta';
                formDataArray.push({name: 'idVenta', value: idVenta},{name: 'fechaAplicacion', value: formattedDate},{name: 'monto', value: monto},{name: 'cuota', value: cuota},{name: 'multa', value: multa},{name: 'idAsignacion', value: ''});
                formDataArray = formDataArray.filter(item => item.name !== 'montoF');
                formDataArray = formDataArray.filter(item => item.name !== 'financiamiento');
                formDataArray = formDataArray.filter(item => item.name !== 'fechaAplicacionF');
                formDataArray = formDataArray.filter(item => item.name !== 'cuotaKi');
                formDataArray = formDataArray.filter(item => item.name !== 'multaFinanciamiento');
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: formDataArray,
                    success: function (response) {
                        $('#crearModalFinanciamiento').modal('hide');
                        toastr.success(response);
                        $.ajax({
                            url: "/InformacionVenta/"+idVenta,
                            type: 'GET',
                            success: function (nuevosDatos) {
                                var elementoActualizable = $(nuevosDatos).find('#page-informacion');
                                $('#page-informacion').html(elementoActualizable.html());
                                var elementoActualizable2 = $(nuevosDatos).find('#formGuardarFinanciamiento');
                                $('#formGuardarFinanciamiento').html(elementoActualizable2.html());
                                actualizarEventos();
                            },
                            error: function () {
                                alert('Error al cargar la tabla.');
                            }
                        });
                    },
                    error: function (xhr, status, error) {
                        $('#crearModal').modal('hide');
                        var errorMessage = xhr.responseText || 'Error al actualizar la información del financiamiento.';
                        toastr.error(errorMessage);
                    }
                });
            }
        });
        var formGuardarMantenimiento = $('#formGuardarMantenimiento');
        var validatorMantenimiento = $('#formGuardarMantenimiento').validate({
            rules: {
                fechaAplicacionM: {
                   required: true,
                   fechaMayorMantenimiento: true,
                   maxlength: 10
                },
                cuotaMantenimiento:{
                    required: true,
                    validarCuotaMantenimiento: true,
                    maxlength: 9
                },
                multaMantenimiento: {
                    required: true,
                    validarMultaMantenimiento: true,
                    maxlength: 9
                }
            },
            messages:{
                fechaAplicacionM:{
                    required: 'Este campo es requerido'
                },
                cuotaMantenimiento: {
                    required: 'Este campo es requerido'
                },
                multaMantenimiento: {
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
                if (element.attr("name") === "fechaAplicacionM" || element.attr("name") === "cuotaMantenimiento" || element.attr("name") === "multaMantenimiento") {
                    error.insertAfter(element);
                }        
            },
            errorElement: 'div',
            errorClass: 'invalid-feedback',
            submitHandler: function(form) {
                event.preventDefault();
                const fechaInputValue = $('#fechaAplicacionM').val();
                const fechaInput = new Date(fechaInputValue);
                const fechaLocal = new Date(fechaInput.getTime() + fechaInput.getTimezoneOffset() * 60000);

                function addLeadingZero(number) {
                    return number < 10 ? `0${number}` : number;
                }
                const day = addLeadingZero(fechaLocal.getDate());
                const month = addLeadingZero(fechaLocal.getMonth() + 1);
                const year = fechaLocal.getFullYear();
                const formattedDate = `${day}/${month}/${year}`;
                var idVenta = $('#idVenta').val();
                var cuota = $('#cuotaMantenimiento').val();
                var multa = $('#multaMantenimiento').val();
                var formDataArray = formGuardarMantenimiento.serializeArray();
                var url = '/AgregarMantenimientoVenta';
                formDataArray.push({name: 'idVenta', value: idVenta},{name: 'fechaAplicacion', value: formattedDate},{name: 'cuota', value: cuota},{name: 'multa', value: multa},{name: 'idAsignacion', value: ''});
                formDataArray = formDataArray.filter(item => item.name !== 'cuotaMantenimiento');
                formDataArray = formDataArray.filter(item => item.name !== 'multaMantenimiento');
                formDataArray = formDataArray.filter(item => item.name !== 'fechaAplicacionM');
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: formDataArray,
                    success: function (response) {
                        $('#crearModalMantenimiento').modal('hide');
                        toastr.success(response);
                        $.ajax({
                            url: "/InformacionVenta/"+idVenta,
                            type: 'GET',
                            success: function (nuevosDatos) {
                                var elementoActualizable = $(nuevosDatos).find('#page-informacion');
                                $('#page-informacion').html(elementoActualizable.html());
                                var elementoActualizable2 = $(nuevosDatos).find('#formGuardarMantenimiento');
                                $('#formGuardarMantenimiento').html(elementoActualizable2.html());
                                actualizarEventos();
                            },
                            error: function () {
                                alert('Error al cargar la tabla.');
                            }
                        });
                    },
                    error: function (xhr, status, error) {
                        $('#crearModal').modal('hide');
                        var errorMessage = xhr.responseText || 'Error al actualizar la información del mantenimiento.';
                        toastr.error(errorMessage);
                    }
                });
            }
        });
        // Método para mostrar el modal segun sea si editar o nuevo registro
        $(document).on('click', '#EditarInformacion', function () {
            var idVenta = $(this).data('id');
            var idTerreno = $("#idTerreno").val();
            var modal = $('#crearModal');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            validator.resetForm();
            formGuardar.find('.is-invalid').removeClass('is-invalid');
            if (idVenta) {
                tituloModal.text('Editar Venta');
                $.ajax({
                    url: '/ObtenerVenta/' + idVenta,
                    type: 'GET',
                    success: function (response) {
                        $('#nombre').val(response.nombre);
                        $('#fecha').val(response.fecha);
                        $('#precio').val(response.precio);
                        $('#descuento').val(response.descuento);
                        $('#monto').val(response.monto);
                        $('#plazo').val(response.plazo);
                        $('#tasa').val(response.tasa);
                        $('#cuotaKi').val(response.cuotaKi);
                        $('#cuotaMantenimiento').val(response.cuotaMantenimiento);
                        $('#multaMantenimiento').val(response.multaMantenimiento);
                        $('#multaFinanciamiento').val(response.multaFinanciamiento);
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
                tituloModal.text('Agregar Venta');
                form.attr('action', '/AgregarVenta/'+idTerreno);
                $('.form-control').val('');
            }
            modal.modal('show');
        });
        // Método para mostrar el financiamiento
        $(document).on('click', '.mostrarFinanciamiento-btn', function () {
            var idFinanciamiento = $(this).data('id');
            $.ajax({
                url: '/ObtenerFinanciamientoVenta/' + idFinanciamiento,
                type: 'GET',
                success: function (response) {
                    var fechaAplicacion = new Date(response.financiamiento.fechaAplicacion);
                    var opcionesFormato = { 
                        day: '2-digit', 
                        month: '2-digit', 
                        year: 'numeric',
                        timeZone: 'UTC', 
                        locale: 'es-ES' 
                    };
                    var fechaFormateada = fechaAplicacion.toLocaleDateString(undefined, opcionesFormato);
                    $('#fecha-aplicacion-financiamiento').html(fechaFormateada);
                    $('#monto-financiamiento').html('$ '+response.financiamiento.monto.toFixed(2));
                    $('#plazo-financiamiento').html(response.financiamiento.plazo);
                    $('#tasa-financiamiento').html(response.financiamiento.tasa.toFixed(2)+' %');
                    $('#cuota-financiamiento').html('$ '+response.financiamiento.cuota.toFixed(2));
                    $('#multa-financiamiento').html('$ '+response.financiamiento.multa.toFixed(2));
                },
                error: function () {
                    alert('Error al obtener los datos del financimaiento de la venta.');
                }
            });
        });
        // Método para mostrar el mantenimiento
        $(document).on('click', '.mostrarMantenimiento-btn', function () {
            var idMantenimiento = $(this).data('id');
            $.ajax({
                url: '/ObtenerMantenimientoVenta/' + idMantenimiento,
                type: 'GET',
                success: function (response) {
                    var fechaAplicacion = new Date(response.mantenimiento.fechaAplicacion);
                    var opcionesFormato = { 
                        day: '2-digit', 
                        month: '2-digit', 
                        year: 'numeric',
                        timeZone: 'UTC', 
                        locale: 'es-ES' 
                    };
                    var fechaFormateada = fechaAplicacion.toLocaleDateString(undefined, opcionesFormato);
                    $('#fecha-aplicacion-mantenimiento').html(fechaFormateada);
                    $('#cuota-mantenimiento').html('$ '+response.mantenimiento.cuota.toFixed(2));
                    $('#multa-mantenimiento').html('$ '+response.mantenimiento.multa.toFixed(2));
                },
                error: function () {
                    alert('Error al obtener los datos del mantenimiento de la venta.');
                }
            });
        });
        // Método para mostrar el modal de eliminación de financiamiento
        $(document).on('click', '.eliminarModalFinanciamiento-btn', function () {
            var idFinanciamiento = $(this).data('id');
            var modal = $('#confirmarEliminarModalFinanciamiento');
            var eliminarBtn = modal.find('#eliminarFinanciamientoBtn');
            eliminarBtn.data('id', idFinanciamiento);
            modal.modal('show');
        });
        //Método para enviar la solicitud de eliminar de financiamiento
        $(document).on('click', '#eliminarFinanciamientoBtn', function () {
            var idFinanciamiento = $(this).data('id');
            var idVenta = $('#idVenta').val();
            $('#eliminarFinanciamientoForm').attr('action', '/EliminarFinanciamientoVenta/' + idFinanciamiento);
            $.ajax({
                url: $('#eliminarFinanciamientoForm').attr('action'),
                type: 'POST',
                data: $('#eliminarFinanciamientoForm').serialize(),
                success: function (response) {
                    $('#confirmarEliminarModalFinanciamiento').modal('hide');
                    toastr.success(response);
                    $.ajax({
                        url: "/InformacionVenta/"+idVenta,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable = $(nuevosDatos).find('#page-informacion');
                            $('#page-informacion').html(elementoActualizable.html());
                            var elementoActualizable2 = $(nuevosDatos).find('#formGuardarFinanciamiento');
                            $('#formGuardarFinanciamiento').html(elementoActualizable2.html());
                            actualizarEventos();
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#confirmarEliminarModalFinanciamiento').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al eliminar información del financiamiento de la venta.';
                    toastr.error(errorMessage);
                }
            });
        });
        // Método para mostrar el modal de eliminación de mantenimiento
        $(document).on('click', '.eliminarModalMantenimiento-btn', function () {
            var idMantenimiento = $(this).data('id');
            var modal = $('#confirmarEliminarModalMantenimiento');
            var eliminarBtn = modal.find('#eliminarMantenimientoBtn');
            eliminarBtn.data('id', idMantenimiento);
            modal.modal('show');
        });
        //Método para enviar la solicitud de eliminar de mantenimiento
        $(document).on('click', '#eliminarMantenimientoBtn', function () {
            var idMantenimiento = $(this).data('id');
            var idVenta = $('#idVenta').val();
            $('#eliminarMantenimientoForm').attr('action', '/EliminarMantenimientoVenta/' + idMantenimiento);
            $.ajax({
                url: $('#eliminarMantenimientoForm').attr('action'),
                type: 'POST',
                data: $('#eliminarMantenimientoForm').serialize(),
                success: function (response) {
                    $('#confirmarEliminarModalMantenimiento').modal('hide');
                    toastr.success(response);
                    $.ajax({
                        url: "/InformacionVenta/"+idVenta,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable = $(nuevosDatos).find('#page-informacion');
                            $('#page-informacion').html(elementoActualizable.html());
                            var elementoActualizable2 = $(nuevosDatos).find('#formGuardarMantenimiento');
                            $('#formGuardarMantenimiento').html(elementoActualizable2.html());
                            actualizarEventos();
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#confirmarEliminarModalMantenimiento').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al eliminar información del mantenimiento de la venta.';
                    toastr.error(errorMessage);
                }
            });
        });
    });
});

