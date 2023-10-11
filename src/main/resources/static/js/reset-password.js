//Funcionalidad de ver o no la contraseña
function togglePasswordVisibilityNew() {
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
function togglePasswordVisibilityConfirm() {
    var passwordInput = document.getElementById("passwordconfirm");
    var eyeIcon = document.getElementById("icono-ver-confirm");

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

var form = $('#form-reset-password'); // Almacenar referencia al formulario
var validator = $('#form-reset-password').validate({
    rules: {        
        password: {
            required: true
        },
        passwordconfirm: {
            required: true,
            equalTo: "#password"
        }
    },
    messages:{
        password:{
            required: 'Este campo es requerido'
        },
        passwordconfirm:{
            required: 'Este campo es requerido',
            equalTo: 'Las contraseñas no coinciden'
        }
    },
    highlight: function(element) {
        $(element).addClass('is-invalid');
    },
    unhighlight: function(element) {
        $(element).removeClass('is-invalid');
    },
    errorPlacement: function(error, element) {
        if (element.attr("name") === "password"){
            error.insertAfter("#group-password-new");
        }         
        if (element.attr("name") === "passwordconfirm")
        {
            error.insertAfter("#group-password-confirm");
        }
    },
    errorElement: 'div',
    errorClass: 'invalid-feedback',
    submitHandler: function(form) {
        form.trigger('submit');
    }
});
