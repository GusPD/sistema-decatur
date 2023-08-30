$(document).ready(function() {
    
    document.getElementById("precio").addEventListener("input", function() {
        const precio = parseFloat(document.getElementById('precio').value);
        const descuento = parseFloat(document.getElementById('descuento').value);
        const primaElement = document.getElementById('prima');
        const prima = parseFloat(primaElement.getAttribute('data-prima'));
        if (!isNaN(precio) && !isNaN(descuento)) {
            const monto = precio - descuento - prima;
            document.getElementById('monto').value = monto.toFixed(2);
        } else if(!isNaN(precio)){
            const monto = precio - prima;
            document.getElementById('monto').value = monto.toFixed(2);
        }else if(!isNaN(descuento)){
            const monto = 0 - (descuento + prima);
            document.getElementById('monto').value = monto.toFixed(2);
        }else{
            document.getElementById('monto').value = '0.00';
        }
    });
    
    document.getElementById("descuento").addEventListener("input", function() {
        const precio = parseFloat(document.getElementById('precio').value);
        const descuento = parseFloat(document.getElementById('descuento').value);
        const primaElement = document.getElementById('prima');
        const prima = parseFloat(primaElement.getAttribute('data-prima'));
        if (!isNaN(precio) && !isNaN(descuento)) {
            const monto = precio - descuento - prima;
            document.getElementById('monto').value = monto.toFixed(2);
        } else if(!isNaN(precio)){
            const monto = precio - prima;
            document.getElementById('monto').value = monto.toFixed(2);
        }else if(!isNaN(descuento)){
            const monto = 0 - (descuento + prima);
            document.getElementById('monto').value = monto.toFixed(2);
        }else{
            document.getElementById('monto').value = '0.00';
        }
    });
    
    document.getElementById("plazo").addEventListener("input", function() {
        const tasa = parseFloat(document.getElementById('tasa').value);
        const plazo = parseFloat(document.getElementById('plazo').value);
        const monto = parseFloat(document.getElementById('monto').value);
        if (!isNaN(tasa) && !isNaN(plazo) && !isNaN(monto) ){
            const valorCuota = calcularCuotaKi(tasa, plazo, monto);
            document.getElementById('cuotaKi').value = valorCuota.toFixed(2);
        }else{
            document.getElementById('cuotaKi').value = "0.00";
        }
    });
    
    document.getElementById("tasa").addEventListener("input", function() {
        const tasa = parseFloat(document.getElementById('tasa').value);
        const plazo = parseFloat(document.getElementById('plazo').value);
        const monto = parseFloat(document.getElementById('monto').value);
        if (!isNaN(tasa) && !isNaN(plazo) && !isNaN(monto) ){
            const valorCuota = calcularCuotaKi(tasa, plazo, monto);
            document.getElementById('cuotaKi').value = valorCuota.toFixed(2);
        }else{
            document.getElementById('cuotaKi').value = "0.00";
        }
    });
    
    function calcularCuotaKi(tasa, nper, va) {
        tasa = (tasa / 100)/12;
        const cuota = (va * tasa) / (1 - Math.pow(1 + tasa, -nper));
        return cuota;
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
                    mostrarMensaje(response, 'success');
                    
                    $.ajax({
                        url: "/Venta/"+idVenta,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable = $(nuevosDatos).find('#tabla-informacion');
                            $('#tabla-informacion').html(elementoActualizable.html());
                            var elementoActualizable2 = $(nuevosDatos).find('#NombreVenta');
                            $('#NombreVenta').html(elementoActualizable2.html());
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
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
        $(document).on('click', '#EditarInformacion', function () {
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
                var checkboxes = document.querySelectorAll(".checkClean");
                
                // en caso de presionar el boton de nuevo solo se abrira el modal
                tituloModal.text('Agregar Venta');
                form.attr('action', '/AgregarVenta/'+idTerreno);
                $('.form-control').val('');
            }
            modal.modal('show');
    });
    
    function mostrarMensaje(mensaje, tipo) {
        var alertElement = $('.alert-' + tipo);
        alertElement.text(mensaje).addClass('show').removeClass('d-none');
        setTimeout(function() {
          alertElement.removeClass('show').addClass('d-none');
        }, 5000); // Ocultar el mensaje después de 3 segundos (ajusta el valor según tus necesidades)
    }
}); 

