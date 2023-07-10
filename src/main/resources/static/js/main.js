(function($) {
	"use strict";
	var fullHeight = function() {

		$('.js-fullheight').css('height', $(window).height());
		$(window).resize(function(){
			$('.js-fullheight').css('height', $(window).height());
		});
	};
	fullHeight();
	$('#sidebarCollapse').on('click', function () {
      $('#sidebar').toggleClass('active');
  });
})(jQuery);
//Funcion para el logout  
function logout() {
    // Crear un formulario dinámicamente
    var form = document.createElement('form');
    form.method = 'POST';
    form.action = '/logout';  
    // Agregar el token CSRF como campo oculto en el formulario
    var csrfTokenInput = document.createElement('input');
    csrfTokenInput.type = 'hidden';
    csrfTokenInput.name = '_csrf';
    csrfTokenInput.value = csrfToken;
    form.appendChild(csrfTokenInput);

    // Agregar el formulario al cuerpo del documento
    document.body.appendChild(form);

    // Enviar el formulario
    form.submit();
}

$(document).ready(function() {   
    var basePath = window.location.origin;
    // Realizar la petición AJAX al endpoint "/ObtenerUsuarioMenu"
    $.ajax({
        url: "/ObtenerUsuarioMenu",
        method: "GET",
        success: function(response) {
            // Actualizar el nombre de usuario
            $("#userPanel .info a").text(response.username);
        },
        error: function() {
            // Manejar el error en caso de que ocurra
            console.log("Error al obtener los datos del usuario");
        }
    });
});

