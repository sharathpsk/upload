$(document).ready(function () {

    $("#login-form").submit(function (event) {

       
        event.preventDefault();
        event.stopPropagation();
        fire_ajax_submit();

    });

});

function fire_ajax_submit() {

    var search = {}
    search["customerEmail"] = $("#customerEmail").val();
    search["customerPassword"] = $("#customerPassword").val();
   
    $("#btn-search").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/signin",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h2>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            if(data.message == "login success"){
            	location.href = "http://localhost:9090/homepage.html"
            }else{
            	location.href = "http://localhost:9090/signup.html"
            }
            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h2>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });

}



$(document).ready(function () {

    $("#home-form").submit(function (event) {

       
        event.preventDefault();

        fire_ajax_submit_home();

    });

});

function fire_ajax_submit_home() {

    var search = {}
    search["name"] = $("#name").val();
  
    console.log(search.name);
    console.log("console");
    $("#btn-search-home").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/frndsname/"+search.name,
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

        	
        	 if(data.message == "success"){
        		 var json = "<h2>Friends list</h4>"
                     + JSON.stringify(data.friends, null, 4) ;
        		 console.log(data.friends[0].name)
        		 
        		 for (var i = 0; i < data.friends.length; i++) {
                     $("#feedback'").html("<li>"+data.friends[i].name+"</li>");
                     var div_data = "<div ><a href='"+data.friends[i].name+"</a></div>";
                     $(div_data).appendTo("#feedback");
                  }   
        		 
        		 $('#feedback').html(json);
             }
            

           
            console.log("SUCCESS : ", data);
            $("#btn-search-home").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h2>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search-home").prop("disabled", false);

        }
    });

}






