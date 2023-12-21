document.addEventListener('DOMContentLoaded', function() {
    $(document).ready(function() {
        actualizarEventos();
        function actualizarEventos() {
            //Función para seleccionar la fecha de aplicación para los mantenimientos
            $('#fechaAplicacionM').change(function () {
                var valorFecha = $(this).val();
                if (valorFecha) {
                    var fecha = new Date(valorFecha);
                    fecha.setDate(0);
                    var nuevoValorFecha = fecha.toISOString().slice(0, 10);
                    $(this).val(nuevoValorFecha);
                }
            });
            //Función para seleccionar la fecha de aplicación para los financiamientos
            $('#fechaAplicacionF').change(function () {
                var valorFecha = $(this).val();
                if (valorFecha) {
                    var fecha = new Date(valorFecha);
                    fecha.setDate(0);
                    var nuevoValorFecha = fecha.toISOString().slice(0, 10);
                    $(this).val(nuevoValorFecha);
                }
            });
        }
        //Formulario de editar
        $.validator.addMethod(
            "validarNumero",
            function(value, element) {
                return this.optional(element) || /^\d+(\.\d+)?$/.test(value);
            },
            "Ingrese un número válido"
        );
        $.validator.addMethod(
            "fechaMayorMantenimiento", 
            function(value, element) {
                var fechaIngresada = new Date(value);
                var fechaActual = new Date();
                var ultimoDiaMesActual = new Date(fechaActual.getFullYear(), fechaActual.getMonth() + 1, 0);
                return fechaIngresada > ultimoDiaMesActual;
            }, 
            "La fecha debe ser mayor al mes actual."
        );
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
                    validarNumero: true,
                    maxlength: 9
                },
                multaMantenimiento: {
                    required: true,
                    validarNumero: true,
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
                var idProyecto = $('#idProyecto').val();
                var cuota = $('#cuotaMantenimiento').val();
                var multa = $('#multaMantenimiento').val();
                var formDataArray = formGuardarMantenimiento.serializeArray();
                var url = '/AgregarMantenimientoProyecto';
                formDataArray.push({name: 'idProyecto', value: idProyecto},{name: 'fechaAplicacion', value: formattedDate},{name: 'cuota', value: cuota},{name: 'multa', value: multa},{name: 'idAsignacion', value: ''});
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
                    },
                    error: function (xhr, status, error) {
                        $('#crearModalMantenimiento').modal('hide');
                        var errorMessage = xhr.responseText || 'Error al actualizar la información del mantenimiento.';
                        toastr.error(errorMessage);
                    }
                });
            }
        });
        // Método para mostrar el mantenimiento
        $(document).on('click', '#EditarInformacionMantenimiento', function () {
            $(".form-control").val("");
            validatorMantenimiento.resetForm();
            $(".form-control").removeClass("is-invalid");
        });
    });
});

