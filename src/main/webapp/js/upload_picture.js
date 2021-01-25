$('form input[type=file]').change(function () {
    if ($('form input[type=file]').val() != '') {
        $("#upload_submit").css("display", "block");
        $("#select_file_label").css("background-color", "var(--accept-btn-color)");

    } else {
        $("#upload_submit").css("display", "none");
        $("#select_file_label").css("background-color", "var(--alert-text-color)");
    }
});