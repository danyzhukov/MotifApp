// order counter
$(document).ready(function() {
  $('.minus').click(function () {
    var $input = $(this).parent().find('input');
    var count = parseInt($input.val()) - 1;
    count = count < 0 ? 0 : count;
    $input.val(count);
    $input.change();
    return false;
  });
  $('.minus-1').click(function () {
    var $input = $(this).parent().find('input');
    var count = parseInt($input.val()) - 1;
    count = count < -1 ? -1 : count;
    $input.val(count);
    $input.change();
    return false;
  });
  $('.minus01').click(function () {
    var $input = $(this).parent().find('input');
    var num = parseFloat($input.val()) - 0.1;
    var count = Math.floor(num * 10) / 10;
    count = count < 0.1 ? 0.1 : count;
    $input.val(count);
    $input.change();
    return false;
  });
  $('.minus05').click(function () {
    var $input = $(this).parent().find('input');
    var num = parseFloat($input.val()) - 0.5;
    var count = Math.floor(num * 10) / 10;
    count = count < 0.5 ? 0.5 : count;
    $input.val(count);
    $input.change();
    return false;
  });
  $('.minus1').click(function () {
    var $input = $(this).parent().find('input');
    var count = parseInt($input.val()) - 1;
    count = count < 1 ? 1 : count;
    $input.val(count);
    $input.change();
    return false;
  });
  $('.minus3').click(function () {
    var $input = $(this).parent().find('input');
    var count = parseInt($input.val()) - 1;
    count = count < 3 ? 3 : count;
    $input.val(count);
    $input.change();
    return false;
  });
  $('.minus10000').click(function () {
    var $input = $(this).parent().find('input');
    var count = parseInt($input.val()) - 10;
    count = count < 10000 ? 10000 : count;
    $input.val(count);
    $input.change();
    return false;
  });
  $('.plus').click(function () {
    var $input = $(this).parent().find('input');
    $input.val(parseInt($input.val()) + 1);
    $input.change();
    return false;
  });
  $('.plus4').click(function () {
    var $input = $(this).parent().find('input');
    var count = parseInt($input.val()) + 1;
    count = count > 4 ? 4 : count;
    $input.val(count);
    $input.change();
    return false;
  });
  $('.plus5').click(function () {
    var $input = $(this).parent().find('input');
    var count = parseInt($input.val()) + 1;
    count = count > 5 ? 5 : count;
    $input.val(count);
    $input.change();
    return false;
  });
  $('.plus100').click(function () {
    var $input = $(this).parent().find('input');
    var num = parseFloat($input.val()) + 0.1;
    var count = Math.floor(num * 10) / 10;
    count = count > 100 ? 100 : count;
    $input.val(count);
    $input.change();
    return false;
  });
  $('.plus5000').click(function () {
    var $input = $(this).parent().find('input');
    var num = parseFloat($input.val()) + 0.5;
    var count = Math.floor(num * 10) / 10;
    count = count > 5000 ? 5000 : count;
    $input.val(count);
    $input.change();
    return false;
  });
  $('.plus100000').click(function () {
    var $input = $(this).parent().find('input');
    var count = parseInt($input.val()) + 1;
    count = count > 100000 ? 100000 : count;
    $input.val(count);
    $input.change();
    return false;
  });
  $('.plus99999999').click(function () {
    var $input = $(this).parent().find('input');
    var count = parseInt($input.val()) + 10;
    count = count > 99999999 ? 99999999 : count;
    $input.val(count);
    $input.change();
    return false;
  });
});

// scroll
$(document).ready(function() {
  $(".tutorial__links").on("click","a", function (event) {
    event.preventDefault();
    var id  = $(this).attr('href'),
        top = $(id).offset().top;
    $('body,html').animate({scrollTop: top}, 1000);
  });
});

// mob menu
$(".header label").on("click", function() {
  $(".header .main__menu").toggleClass("open", 1000);
  if ($(".header .main__menu").hasClass("open")) {
    $("body").addClass('no-scroll');
  } else {
    $("body").removeClass('no-scroll');
  }
});
$(".footer label").on("click", function() {
  $(".footer .main__menu").toggleClass("open", 1000);
  if ($(".footer .main__menu").hasClass("open")) {
    $("body").addClass('no-scroll');
  } else {
    $("body").removeClass('no-scroll');
  }
});

//show file name after choose file
$(fileNet).on("change", function() {
  $(fileName).val(this.files[0].name);
});

//load result to server
$(fileLoad).on("change", function() {
  $(loadResult).click();
});

//filter update
$(".filter-update").on("click", function() {
  $(filterResult).click();
});