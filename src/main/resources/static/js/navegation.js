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
