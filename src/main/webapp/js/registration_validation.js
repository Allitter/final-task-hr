password_repeat.oninput = checkPass;

function checkPass() {
    var pass_value = password.value;
    var pass_repeat_value = password_repeat.value;

    if (pass_value === pass_repeat_value) {
        passwords_dont_match_message.style.display = "none";

    }
    else {
        passwords_dont_match_message.style.display = "block";
    }
}