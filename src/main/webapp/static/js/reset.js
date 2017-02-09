$(document).ready(function () {

    VerificationManager.getVerificationFromSession(function (verification) {
        if (verification == null) {
            location.href = "/overtime.html";
            return;
        }

        $("#reset-message").fillText({
            "name": verification.user.name,
            "createAt": new Date(verification.createAt * 1000).toLocaleString()
        });
    });

    $("#reset-submit").click(function () {
        var password = $("#reset-password").val();
        if (password == null || password == "") {
            $("#reset-password").parent().addClass("error");
            return;
        }
        $(this).addClass("disabled");
        UserManager.resetPassword(password, function (success) {
            if (success) {
                location.href = "success.html";
            } else {
                location.href = "fail.html";
            }
        });
    });
});