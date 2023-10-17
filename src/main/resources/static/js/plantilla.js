//Función para el redimensionamiento de pantalla
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
    var form = document.createElement('form');
    form.method = 'POST';
    form.action = '/logout';  
    var csrfTokenInput = document.createElement('input');
    csrfTokenInput.type = 'hidden';
    csrfTokenInput.name = '_csrf';
    csrfTokenInput.value = csrfToken;
    form.appendChild(csrfTokenInput);
    document.body.appendChild(form);
    form.submit();
}
//Función para mostrar el nombre de usuario en el header
$(document).ready(function() {   
    var basePath = window.location.origin;
    $.ajax({
        url: "/ObtenerUsuarioMenu",
        method: "GET",
        success: function(response) {
            $("#userPanel .info a").text(response.username);
        },
        error: function() {
            console.log("Error al obtener los datos del usuario");
        }
    });
});
// Función para mostrar la hora del sistema
function actualizarHora() {
    var elementoHora = document.getElementById("hora");
    var fechaHoraActual = new Date();
    var horas = fechaHoraActual.getHours();
    var minutos = fechaHoraActual.getMinutes();
    var segundos = fechaHoraActual.getSeconds();
    var periodo = horas >= 12 ? "PM" : "AM";
    horas = horas % 12;
    horas = horas ? horas : 12;
    var horasFormateadas = horas < 10 ? "0" + horas : horas;
    var minutosFormateados = minutos < 10 ? "0" + minutos : minutos;
    var segundosFormateados = segundos < 10 ? "0" + segundos : segundos;
    var horaFormateada = horasFormateadas + ":" + minutosFormateados + ":" + segundosFormateados + " " + periodo;
    elementoHora.textContent = horaFormateada;
}
//Función que actualiza la hora cada segundo
setInterval(actualizarHora, 1000);
//Función que agrega el logo del menú de acuerdo al dispositivo
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
//Librería para la impresión de las confirmaciones de mensajes
toastr.options = {
    closeButton: true,
    progressBar: true,
    positionClass: "toast-top-right"
};
