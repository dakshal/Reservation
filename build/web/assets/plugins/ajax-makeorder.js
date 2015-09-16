// Content Contact Form
$(function () {
    $('#makeorder .error').hide();

    $("#makeorder .form-button").click(function () {
        // validate and process form
        // first hide any error messages
        $('#makeorder .error').hide();

        var datefrom = $("#makeorder input#datefrom").val();
        var matches = /^(\d{4})[-\/](\d{2})[-\/](\d{2})$/.exec(datefrom);
        if (matches == null) {
            $("#makeorder #datefrom_error").show();
            return false;
        }

        var dateto = $("#makeorder input#dateto").val();
        var matches = /^(\d{4})[-\/](\d{2})[-\/](\d{2})$/.exec(dateto);
        if (matches == null) {
            $("#makeorder #dateto_error").show();
            return false;
        }

        var name = $("#makeorder input#name").val();
        if (name == "" || name == "Name") {
            $("#makeorder label#name_error").show();
            $("#makeorder input#name").focus();
            return false;
        }
        var email = $("#makeorder input#email").val();
        var filter = /^[a-zA-Z0-9]+[a-zA-Z0-9_.-]+[a-zA-Z0-9_-]+@[a-zA-Z0-9]+[a-zA-Z0-9.-]+[a-zA-Z0-9]+.[a-z]{2,4}$/;
        console.log(filter.test(email));
        if (!filter.test(email)) {
            $("#makeorder label#email_error").show();
            $("#makeorder input#email").focus();
            return false;
        }
        var message = $("#makeorder #input-message").val();
        if (message == "") {
            $("#makeorder label#message_error").show();
            $("#makeorder #input-message").focus();
            return false;
        }

        var dataString = 'name=' + name + '&email=' + email + '&message=' + message + '&datefrom =' + datefrom + '&dateto =' + dateto;
        //alert (dataString);return false;

        $.ajax({
            type:"POST",
            url:"assets/php/contact-form.php",
            data:dataString,
            success:function () {
                $('#makeorder').prepend("<div class=\"alert alert-success fade in\"><button class=\"close\" data-dismiss=\"alert\" type=\"button\">&times;</button><strong>Query submited.</strong> We will be in touch soon.</div>");
                $('#makeorder')[0].reset();
            }
        });
        return false;
    });
});