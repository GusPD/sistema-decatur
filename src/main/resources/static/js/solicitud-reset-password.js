//Función para mostrar los mensajes de error
function mostrarMensaje(mensaje, tipo) {
    var alertElement = $('.alert-' + tipo);
    alertElement.text(mensaje).addClass('show').removeClass('d-none');
    setTimeout(function() {
      alertElement.removeClass('show').addClass('d-none');
    }, 5000);
}
//Función para validar el formulario de la solicitud
var formReset = $('#form-reset-password');
var validator = $('#form-reset-password').validate({
    rules: {
        username: {
            required: true
        }
    },
    messages:{
        username:{
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
        if (element.attr("name") === "username"){
            error.insertAfter("#group-username");
        }         
    },
    errorElement: 'div',
    errorClass: 'invalid-feedback',
    submitHandler: function(form) {
        event.preventDefault();
        var formDataArray = formReset.serializeArray();
        $("#animacion-loading").show();
        $.ajax({
            url: '/RestablecerPassword',
            type: 'POST',
            data: formDataArray,
            success: function (response) {
                $("#animacion-loading").hide();
                mostrarMensaje(response, 'success');
                $("#username").val("");
            },
            error: function (xhr, status, error) {
                $("#animacion-loading").hide();
                var errorMessage = xhr.responseText || 'Error al enviar la solicitud para restablecer la contraseña.';
                mostrarMensaje(errorMessage, 'danger');
            }
        });
    }
});
