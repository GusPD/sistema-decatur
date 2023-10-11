// Función que se ejecuta cuando se hace clic en un botón
function setActiveButton(buttonId) {
    // Obtener todos los botones
    var buttons = document.getElementsByClassName("tab-perfil");
    var content_tab = document.getElementsByClassName('content_tab');

    // Recorrer todos los botones y quitar la clase "active"
    for (var i = 0; i < buttons.length; i++) {
      buttons[i].classList.remove("active");
    }

    // Agregar la clase "active" al botón actual
    var currentButton = document.getElementById(buttonId);
    currentButton.classList.add("active");

    for (var i = 0; i < content_tab.length; i++) {
        content_tab[i].classList.add('d-none');
    }

    var currentContentTab = document.getElementById("content_"+buttonId);
    currentContentTab.classList.remove('d-none');
}
