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

// Función para actualizar la hora cada segundo
function actualizarHora() {
    // Obtener el elemento HTML donde se mostrará la hora
    var elementoHora = document.getElementById("hora");

    // Obtener la fecha y hora actual
    var fechaHoraActual = new Date();

    // Obtener las componentes de la hora actual
    var horas = fechaHoraActual.getHours();
    var minutos = fechaHoraActual.getMinutes();
    var segundos = fechaHoraActual.getSeconds();

    // Determinar si es AM o PM
    var periodo = horas >= 12 ? "PM" : "AM";

    // Convertir el formato de 24 horas a 12 horas
    horas = horas % 12;
    horas = horas ? horas : 12; // Ajustar 0 a 12 en lugar de 0

    // Formatear las componentes de la hora
    var horasFormateadas = horas < 10 ? "0" + horas : horas;
    var minutosFormateados = minutos < 10 ? "0" + minutos : minutos;
    var segundosFormateados = segundos < 10 ? "0" + segundos : segundos;

    // Construir la cadena de la hora formateada
    var horaFormateada = horasFormateadas + ":" + minutosFormateados + ":" + segundosFormateados + " " + periodo;

    // Actualizar el contenido del elemento HTML con la hora actual
    elementoHora.textContent = horaFormateada;
}
// Llamar a la función actualizarHora cada segundo
setInterval(actualizarHora, 1000);

$(document).ready(function() {
    $("#boton-menu").click(function() {
        var windowWidth = window.innerWidth;
        var navIconoLogo = $("#nav-icono-logo");
        var navLogo = $("#nav-logo");
        if (windowWidth > 768) {
            if (navIconoLogo.length > 0) {
                if (isSidebarCollapsed()) {
                    navIconoLogo.hide();
                    navLogo.show();
                } else {
                    navIconoLogo.show();
                    navLogo.hide();
                }
            }
        }else{
            if (navIconoLogo.length > 0) {
                navIconoLogo.hide();
                navLogo.show();
            }
        }
    });
});

function isSidebarCollapsed() {
  var sidebar = document.querySelector('body');
  return sidebar.classList.contains('sidebar-collapse');
}

toastr.options = {
    closeButton: true,
    progressBar: true,
    positionClass: "toast-top-right"
};
