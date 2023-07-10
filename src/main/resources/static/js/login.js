//Funcionalidad de ver o no la contraseña
function togglePasswordVisibility() {
    var passwordInput = document.getElementById("password");
    var eyeIcon = document.getElementById("eye-icon");

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        eyeIcon.classList.remove("bi-eye");
        eyeIcon.classList.add("bi-eye-slash");
    } else {
        passwordInput.type = "password";
        eyeIcon.classList.remove("bi-eye-slash");
        eyeIcon.classList.add("bi-eye");
    }
}
// Esperar 5 segundos y ocultar el mensaje de error
setTimeout(function() {
    var errorMessageContainer = document.getElementById("errorMessageContainer");
    if (errorMessageContainer) {
        errorMessageContainer.style.display = "none";
    }
}, 5000);
