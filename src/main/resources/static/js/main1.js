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
            // Actualizar la imagen de perfil
            var userImage = response.fotografia ? "data:" + response.contentType + ";base64," + response.fotografia : "${pageContext.request.contextPath}/images/usuario.png";
            $("#userPanel .image img").attr("src", userImage);

            // Actualizar el nombre de usuario
            $("#userPanel .info a").text(response.username);

            // Evaluar el tipo de usuario y agregar el enlace correspondiente
            var tipoUsuario = response.tipoUsuario;
            var perfilLink = "";
            if (tipoUsuario === "Coordinador Académico") {
                perfilLink = basePath + "/perfilCoordinadorAcademico/" + response.idPerfil;
            } else if (tipoUsuario === "Aspirante a Profesor") {
                perfilLink = basePath + "/PerfilAspiranteProfesor/" + response.idPerfil;
            }

            // Actualizar el enlace del perfil en el menú de la barra lateral
            if (perfilLink !== "") {
                $("#link-perfil a").attr("href", perfilLink);
                $("#link-perfil").removeClass("d-none");
            } else {
                $("#link-perfil a").removeAttr("href");
            }
        },
        error: function() {
            // Manejar el error en caso de que ocurra
            console.log("Error al obtener los datos del usuario");
        }
    });

//Obtener maestrias relacionadas al coordinador
$.ajax({
    url: '/ObtenerMaestriasCoordinador',
    type: 'GET',
    success: function(response) {
        var currentPath = window.location.pathname;

        if (response && response.length > 0) {
            // Genera dinámicamente el menú de maestrías utilizando la respuesta del servidor
            var maestriasMenu = $('<li>').addClass('nav-item')
                .addClass(currentPath.match('^/GestionarPlanEstudio/\\d+$') || currentPath.match('^/GestionarCohorte/\\d+$') ? 'menu-open' : '')
                .append($('<a>').attr('href', '#').addClass('nav-link').addClass(currentPath.match('^/GestionarPlanEstudio/\\d+$') || currentPath.match('^/GestionarCohorte/\\d+$') ? 'active' : '')
                    .append($('<i>').addClass('nav-icon fas fa-building-columns'))
                    .append($('<p>').text('Mis Maestrías').append($('<i>').addClass('right fas fa-angle-left')))
                )
                .append($('<ul>').addClass('nav nav-treeview mis-maestrias'));

            // Genera el elemento del encabezado 'MAESTRÍAS'
            var headerElement = $('<li>').addClass('nav-header').text('MAESTRÍAS');

            // Agrega el elemento del encabezado al contenedor principal del menú
            $('.nav-sidebar').append(headerElement);

            // Genera dinámicamente los elementos del menú para cada maestría
            $.each(response, function(index, maestria) {
                var regexGestionarPlanEstudio = new RegExp('^/GestionarPlanEstudio/' + maestria.idMaestria + '$');
                var regexGestionarCohorte = new RegExp('^/GestionarCohorte/' + maestria.idMaestria + '$');

                // Genera el elemento del menú de la maestría con las clases y enlaces correspondientes
                var menuElement = $('<li>').addClass('nav-item')
                    .addClass(currentPath.match(regexGestionarPlanEstudio) || currentPath.match(regexGestionarCohorte) ? 'menu-open' : '')
                    .append($('<a>').attr('href', '#').addClass('nav-link').addClass(currentPath.match(regexGestionarPlanEstudio) || currentPath.match(regexGestionarCohorte) ? 'active' : '')
                        .append($('<i>').addClass('nav-icon fas fa-building-columns text-secondary'))
                        .append($('<p>').text(maestria.nombreMaestria).append($('<i>').addClass('right fas fa-angle-left')))
                    )
                    .append($('<ul>').addClass('nav nav-treeview')
                        .append($('<li>').addClass('nav-item')
                            .append($('<a>').attr('href', '/GestionarPlanEstudio/' + maestria.idMaestria).addClass('nav-link').addClass(currentPath.match(regexGestionarPlanEstudio) ? 'active' : '')
                                .append($('<i>').addClass('fa-solid fa-book nav-icon text-warning'))
                                .append($('<p>').text('Asignaturas'))
                            )
                        )
                        .append($('<li>').addClass('nav-item')
                            .append($('<a>').attr('href', '/GestionarCohorte/' + maestria.idMaestria).addClass('nav-link').addClass(currentPath.match(regexGestionarCohorte) ? 'active' : '')
                                .append($('<i>').addClass('fas fa-graduation-cap nav-icon text-warning'))
                                .append($('<p>').text('Gestión de Cohorte'))
                            )
                        )
                    );

                // Agrega el elemento del menú de la maestría al contenedor del menú
                maestriasMenu.find('.mis-maestrias').append(menuElement);
            });

            // Agrega el elemento padre al contenedor principal del menú
            $('.nav-sidebar').append(maestriasMenu);
        }
    },
    error: function() {
        // Manejo de errores en caso de que la solicitud falle
    }
});

});

