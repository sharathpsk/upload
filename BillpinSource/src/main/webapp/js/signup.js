$(document).ready(function () {

    $("#sign-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});

function fire_ajax_submit() {

    var search = {}
    search["customerEmail"] = $("#customerEmail").val();
    search["customerPassword"] = $("#customerPassword").val();
    search["customerName"] = $("#customerName").val();
    search["customerPassword2"] = $("#customerPassword2").val();
    
    console.log(customerEmail);
    $("#bth-sign").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/signup",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
        	var htmlText ="";
           /* var json = "<h2>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback_signup').html(json);*/

        	
            if(data.message == "success"){
            	location.href = "http://localhost:9090/thanks.html"
            		htmlText += '<h1>'+data.message+'</h1>'
					$('#feedback_signup').html(htmlText);
            }else{
            	htmlText += '<h1>'+data.message+'</h1>'
				$('#feedback_signup').html(htmlText);
            }
            console.log("SUCCESS : ", data);
            $("#bth-sign").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h2>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback_signup').html(json);

            console.log("ERROR : ", e);
            $("#bth-sign").prop("disabled", false);

        }
    });

}