var currentHour = new Date().getHours();

// Definir las horas para cada opción
var dawnStart = 7; // 7 AM
var dayStart = 8;  // 8 AM
var duskStart = 15; // 3 PM
var nightStart = 18; // 6 PM

// Determinar la opción activa
var activeOption;
if (currentHour >= dawnStart && currentHour < dayStart) {
  activeOption = "dusk";
} else if (currentHour >= dayStart && currentHour < duskStart) {
  activeOption = "day";
} else if (currentHour >= duskStart && currentHour < nightStart) {
  activeOption = "sunset";
} else {
  activeOption = "night";
}

// Activar la opción correspondiente
$(".option").removeClass("active");
$(".option[data-option='" + activeOption + "']").addClass("active");
var type = $(".option[data-option='" + activeOption + "']").data("option");
setTimeout(function() {
  if (type === "day") {
    $(".time").attr('class', 'time day');
  } else if (type === "night") {
    $(".time").attr('class', 'time night');
  } else if (type === "dusk") {
    $(".time").attr('class', 'time dusk');
  } else if (type === "sunset") {
    $(".time").attr('class', 'time sunset');
  } 
}, 200);

// Ejecutar la función al hacer clic en una opción
$(".option").on("click", function() {
  $(".option").removeClass("active");
  $(this).addClass("active");
  var type = $(this).data("option");
  setTimeout(function() {
    if (type === "day") {
      $(".time").attr('class', 'time day');
    } else if (type === "night") {
      $(".time").attr('class', 'time night');
    } else if (type === "dusk") {
      $(".time").attr('class', 'time dusk');
    } else if (type === "sunset") {
      $(".time").attr('class', 'time sunset');
    } 
  }, 500);
});