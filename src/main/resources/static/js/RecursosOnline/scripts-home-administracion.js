$(".option").on("click", function() {
  $(".person-type").removeClass("active");
  $(".option").removeClass("active");
  $(this).addClass("active");
  var type = $(this).data("option");
  setTimeout(function() {
    if (type === "home") {
      $(".home-body").addClass("active");
      $("#mensaje-home").hide();
      $("#mensaje-outdoor").show();
    } else if (type === "outdoor") {
      $(".outdoor-person").addClass("active");
      $("#mensaje-outdoor").hide();
      $("#mensaje-home").show();
    }
  }, 500);
});