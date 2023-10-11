//Funcionalidad de ver o no la contraseña
function togglePasswordVisibility() {
    var passwordInput = document.getElementById("password");
    var eyeIcon = document.getElementById("icono-ver");

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        eyeIcon.classList.remove("fa-eye");
        eyeIcon.classList.add("fa-eye-slash");
    } else {
        passwordInput.type = "password";
        eyeIcon.classList.remove("fa-eye-slash");
        eyeIcon.classList.add("fa-eye");
    }
}
// Esperar 5 segundos y ocultar el mensaje de error
setTimeout(function() {
    var errorMessageContainer = document.getElementById("errorMessageContainer");
    if (errorMessageContainer) {
        errorMessageContainer.style.display = "none";
    }
}, 5000);

setTimeout(function() {
    var successMessageContainer = document.getElementById("successMessageContainer");
    if (successMessageContainer) {
        successMessageContainer.style.display = "none";
    }
}, 5000);


var form = $('#form-login'); // Almacenar referencia al formulario
var validator = $('#form-login').validate({
    rules: {
        username: {
            required: true
        },          
        password: {
            required: true
        }
    },
    messages:{
        username:{
            required: 'Este campo es requerido'
        },
        password:{
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
        if (element.attr("name") === "password")
        {
            error.insertAfter("#group-password");
        }
    },
    errorElement: 'div',
    errorClass: 'invalid-feedback',
    submitHandler: function(form) {
        form.trigger('submit');
    }
});
