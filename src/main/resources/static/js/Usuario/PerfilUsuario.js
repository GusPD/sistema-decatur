$(document).ready(function() {
    //Formulario de agregar
    $.validator.addMethod("validarPassword",
        function(value, element) {
            return this.optional(element) || 
                    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{};:,<.>])[A-Za-z\d!@#$%^&*()\-_=+{};:,<.>]{8,}$/.test(value);
        },
        "La contraseña debe contener al menos una mayúscula, una minúscula, un número, un carácter especial y tener un mínimo de 8 caracteres"
    );
    $.validator.addMethod("validarCorreo", 
        function(value, element) {
            return this.optional(element) || /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(value);
        },
        "Ingresa una dirección de correo electrónico válida"
    );
    $.validator.addMethod("noSpacios", function(value, element) {
        return this.optional(element) || /^\S+$/.test(value);
    }, "El nombre de usuario no puede contener espacios en blanco");
    var formGuardar = $('#formGuardar');
    var validator = $('#formGuardar').validate({
        rules: {
            nombre: {
                required: true
            },
            username: {
                required: true,
                noSpacios: true
            },
            email:{
                required: true,
                validarCorreo: true
            },          
            password: {
                required: function(element) {
                    return !$('#UsuarioId').val();
                },
                validarPassword: true
            },
        },
        messages:{
            nombre:{
                required: 'Este campo es requerido'
            },
            username:{
                required: 'Este campo es requerido'
            },
            email:{
                required: 'Este campo es requerido'
            },
            password:{
                required: 'Este campo es requerido',
                validarPassword: 'La contraseña debe contener al menos una mayúscula, una minúscula, un número, un carácter especial y tener un mínimo de 8 caracteres'
            },
        },
        highlight: function(element) {
            $(element).addClass('is-invalid');
        },
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") === "nombre" || element.attr("name") === "username" || element.attr("name") === "email" || element.attr("name") === "password") {
                error.insertAfter(element);
            }
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var idUsuario = $('#UsuarioId').val();
            var formDataArray = formGuardar.serializeArray();
            var url = '/ActualizarPerfilUsuario';
            formDataArray.push({name: 'idUsuario', value: idUsuario});
            console.log(formDataArray);
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');
                    $.ajax({
                        url: "/PerfilUsuario/"+idUsuario,
                        type: 'GET',
                        success: function (nuevosDatos) {
                            var elementoActualizable = $(nuevosDatos).find('#tabla-informacion');
                            $('#tabla-informacion').html(elementoActualizable.html());
                        },
                        error: function () {
                            alert('Error al cargar la tabla.');
                        }
                    });
                    toastr.success(response);
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al actualizar el usuario.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    //Método para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '#EditarInformacion', function () {
        var idUsuario = $(this).data('id');
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        validator.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        if (idUsuario) {
            tituloModal.text('Editar Perfil');
            $('.oculto').removeAttr('hidden');
            $('#password').removeAttr('required');
            $.ajax({
                url: '/ObtenerUsuario/' + idUsuario,
                type: 'GET',
                success: function (response) {
                    $('#nombre').val(response.nombre);
                    $('#username').val(response.username);
                    $('#email').val(response.email);
                    $('#password').val('');
                    $('#UsuarioId').val(idUsuario);
                },
                error: function () {
                    alert('Error al obtener los datos del usuario.');
                }
            });
        }
        modal.modal('show');
    });
});

