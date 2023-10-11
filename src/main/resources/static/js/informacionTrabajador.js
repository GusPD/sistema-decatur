$(document).ready(function() {
    
    $.validator.addMethod(
        "validarDui",
        function(value, element) {
            return this.optional(element) || /^\d{9}$/.test(value);
        },
        "Ingrese un número válido, verifique que deben de ser nueve dígitos"
    );
    
    var formGuardar = $('#formGuardar'); // Almacenar referencia al formulario
    var validator = $('#formGuardar').validate({
         
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
            var idPersona = $('#idPersona').val();//tomo la id
            var idDocumento = $('#idDocumento').val();
            var idTrabajador = $('#idVisitante').val();
            var formDataArray = formGuardar.serializeArray();//tomo los datos del array

            console.log(formDataArray);
            var url;//valido el tipo de url si editar o crear
            if (idPersona) {
                url = '/ActualizarTrabajador';
                //meto la id en el campo de envio
                formDataArray.push({name: 'idPersona', value: idPersona}, {name: 'idVisitante', value: idTrabajador}, {name: 'idDocumento', value: idDocumento}, {name: 'rol', value: "TRABAJADOR"});
            } else {
                url = '/AgregarTrabajador';
                formDataArray.push( {name: 'rol', value: "TRABAJADOR"});
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
                        url: "/MostrarTrabajador/"+idPersona,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable = $(nuevosDatos).find('#tabla-informacion');
                            $('#tabla-informacion').html(elementoActualizable.html());
                            var elementoActualizable2 = $(nuevosDatos).find('#NombreTrabajador');
                            $('#NombreTrabajador').html(elementoActualizable2.html());
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide'); // Cierra el modal
                    var errorMessage = xhr.responseText || 'Error al actualizar el trabajador.';
                    mostrarMensaje(errorMessage, 'danger');
                }
            });
        }
    });

    // metodo para mostrar el modal segun sea si editar o nuevo registro
         $(document).on('click', '#EditarInformacion', function() {
            var idPersona = $(this).data('id');
            var modal = $('#crearModal');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            var btnSumit = document.getElementById('btnSumit');
            validator.resetForm();  // Restablecer la validación
            formGuardar.find('.is-invalid').removeClass('is-invalid');

            if (idPersona) {
                tituloModal.text('Editar Trabajador');//titulo del modal
                
                $.ajax({//utilizo ajax para obtener los datos
                    url: '/ObtenerTrabajador/' + idPersona,
                    type: 'GET',
                    success: function (response) {
                       
                        var checkboxes = document.querySelectorAll(".checkClean");

                        for (var i = 0; i < checkboxes.length; i++) {
                            checkboxes[i].checked = false;
                        }
                        $('#idPersona').val(response.persona.idPersona);
                        $('#dui').val(response.persona.dui);
                        $('#nombre').val(response.persona.nombre);
                        $('#apellido').val(response.persona.apellido);
                        $('#idVisitante').val(response.trabajador.idVisitante);
                        $('#idDocumento').val(response.trabajador.idDocumento);
                        $('#rol').val(response.trabajador.rol);
                        $('#empleador').val(response.trabajador.empleador);
                    },
                    error: function () {
                        alert('Error al obtener los datos del trabajador.');
                    }
                });
            } else {
                var checkboxes = document.querySelectorAll(".checkClean");
                
                // en caso de presionar el boton de nuevo solo se abrira el modal
                tituloModal.text('Agregar Trabajador');
                form.attr('action', '/AgregarTrabajador');
                $('.form-control').val('');
                $('#idPersona').val('');
                $('#idVisitante').val('');
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

