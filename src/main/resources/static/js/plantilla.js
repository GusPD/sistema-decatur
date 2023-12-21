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
$("#btn-logout").click(function() {
    var formData = new FormData();
    formData.append('_csrf', csrfToken);
    $.ajax({
        url: '/logout',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false, 
        success: function (response) {
            location.reload(true);
        },
        error: function (xhr, status, error) {
            location.reload(true);
        }
    });
});
//Función para mostrar el nombre de usuario en el header
//Index Controler
$(document).ready(function() {   
    var basePath = window.location.origin;
    $.ajax({
        url: "/ObtenerUsuarioMenu",
        method: "GET",
        success: function(response) {
            var rolFormateado = response.rol.charAt(0).toUpperCase() + response.rol.slice(1).toLowerCase();
            $("#userPanel .info a").html(response.nombre + "<br>" + rolFormateado);
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

var socket = new SockJS('ws:/localhost:8080/notificacion');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    stompClient.subscribe('/topic/notificaciones', function (notification) {
        var message = JSON.parse(notification.body);
        console.log('Notificación: ' + message);
    });
});

