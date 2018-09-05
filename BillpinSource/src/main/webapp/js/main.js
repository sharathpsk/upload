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
			var htmlText ="";
		/*	var json = "<h2>Ajax Response</h4><pre>"
				+ JSON.stringify(data, null, 4) + "</pre>";
			$('#feedback').html(json);*/

			if(data.message == "login success"){
				sessionStorage.setItem("username", data.userId);
				location.href = "http://localhost:9090/homepage.html"
					htmlText += '<h1>'+data.message+'</h1>'
					$('#feedback').html(htmlText);
			}else if(data.message == "Enter password"){
				
				htmlText +=  '<h1>Please Enter Correct Password</h1>'
					$('#feedback').html(htmlText);
			}else{
				location.href = "http://localhost:9090/signup.html"
					htmlText += '<h1>'+data.message+'</h1>'
					$('#feedback').html(htmlText);
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


			var htmlText = '';


				
					htmlText +=  '<h1>Friends List</h1>'
						htmlText +=  '<h1>Click to add</h1>'
						if(data.message == "success"){


							for ( var key in data.friends ) {
								htmlText += '<button class="input pass" onclick="addFriend(this)">'+data.friends[key].name+'</button>'


							}

							$('#result').html(htmlText);


						}else{

							htmlText += '<button class="input pass">'+data.message+'</button>' 		                    
							$('#result').html(htmlText);
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



function addFriend(name) {

	console.log(name.innerHTML);
	console.log(sessionStorage.getItem("username"));


	var search = {}
	search["username"] = sessionStorage.getItem("username");
	search["frndsname"] = name.innerHTML;

	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: "/addingFriends/",
		data: JSON.stringify(search),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data) {

			var htmlText ="";
			htmlText +=  '<h1>'+data.message+'</h1>'
				$('#feedback').html(htmlText);
			console.log(data.message)
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



function transactions(){
	
	console.log(sessionStorage.getItem("username"));


	var search = {}
	search["userid"] = sessionStorage.getItem("username");
	
	console.log(search.userid);
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: "/userOwnTransactions/"+search.userid,
		data: JSON.stringify(search),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data) {


			console.log(data)
			
			
			var htmlText = '';


			htmlText +=  '<div class="col-md-6 col-md-offset-4">'
				htmlText +=	'<div id="logbox" style="border-radius: 10px;">'
					htmlText +=  '<h1>Transactions List</h1>'
						htmlText += '<table>'
							htmlText +='<tr>'
								htmlText +='<th>FriendName</th>'
								htmlText +='<th>totalAmount</th>'
								htmlText +='<th>youOwe</th>'
								htmlText +='</tr>'
						if(data.message == "success"){


							for ( var key in data.txns_list ) {
								
								htmlText +='<tr>'
								htmlText +='<td>'+data.txns_list[key].friendName +'</td>'
								htmlText +='<td>'+ data.txns_list[key].totalAmount +'</td>'
								htmlText +='<td>'+data.txns_list[key].youOwe +'</td>'
								htmlText +='</tr>'
							}

							$('#mytxns').html(htmlText);


						}else{

							htmlText += '<button class="input pass">'+data.message+'</button>' 		                    
							$('#mytxns').html(htmlText);
						}
			htmlText += '</table>'
			htmlText +=	'  </div> 	</div>';
			
			
			//$('#mytxns').html(json);
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
	

$(document).ready(function () {

	$("#frnds_txns-form").submit(function (event) {


		event.preventDefault();
		event.stopPropagation();
		friendstransactions();

	});

});

function friendstransactions(){
	
	
	var search = {}
	search["frndsname"] = $("#frndsname").val();
	
	console.log(search.frndsname);
	
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: "/userInvolvetransactions/"+search.frndsname,
		data: JSON.stringify(search),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data) {


			console.log(data)
			
			
			var htmlText = '';


			htmlText +=  '<div class="col-md-6">'
				htmlText +=	'<div id="logbox" style="border-radius: 10px;">'
					htmlText +=  '<h1>Transactions List</h1>'
						
						if(data.message == "success"){

							htmlText += '<table>'
								htmlText +='<tr>'
									htmlText +='<th>FriendName</th>'
									htmlText +='<th>totalAmount</th>'
									htmlText +='<th>UserOwe</th>'
									htmlText +='</tr>'
							for ( var key in data.txns_list ) {
								
								htmlText +='<tr>'
								htmlText +='<td>'+data.txns_list[key].friendName +'</td>'
								htmlText +='<td>'+ data.txns_list[key].totalAmount +'</td>'
								htmlText +='<td>'+data.txns_list[key].youOwe +'</td>'
								htmlText +='</tr>'
							}

							$('#frndtxns_result').html(htmlText);


						}else{

							htmlText += '<button class="input pass">'+data.message+'</button>' 		                    
							$('#frndtxns_result').html(htmlText);
						}
			htmlText += '</table>'
			htmlText +=	'  </div> </div>';
			
			
			
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
	

function friendList(){
	
	var search = {}
	search["userid"] = sessionStorage.getItem("username");
	console.log(search);
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: "/friendslist/"+search.userid,
		data: JSON.stringify(search),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data) {
		var htmlText = '';
			if(data.message == 'success'){
				htmlText += '<h2>List of Friends To add</h2>';
				htmlText +='<select id="selectfrndsList" multiple="multiple" style="border-radius: 10px;">';
				for ( var key in data.friendsList ) {
					
					//htmlText += '<button class="input pass" onclick="addFriend(this)">'+data.friendsList[key]+'</button>'
					htmlText += '<option value="'+data.friendsList[key]+'">'+data.friendsList[key]+'</option>';
					
				}
				$('#friendslist').html(htmlText);
				htmlText +='</select>';
			}
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


$(document).ready(function () {

	$("#expenses-form").submit(function (event) {


		event.preventDefault();

		Expenses_func();

	});

});

function Expenses_func(){
	
	
	var search = {}
	search["amount"] = $("#amount").val();
	search["frndsIDs"]	 = $("#selectfrndsList").val();
	search["userid"] = sessionStorage.getItem("username");
	
	console.log(search.FrndsIDs);
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: "/Addexpenses/",
		data: JSON.stringify(search),
		dataType: 'json',
		
		cache: false,
		timeout: 600000,
		success: function (data) {
			
		var htmlText = '';
			if(data.message == 'success'){
				var htmlText ="";
				htmlText +=  '<h1>'+data.message+'</h1>'
					$('#expenseResponse').html(htmlText);
				console.log(data.message)
			}else{
				var htmlText ="";
				htmlText +=  '<h1>'+data.message+'</h1>'
					$('#expenseResponse').html(htmlText);
				console.log(data.message)
			}
		},
		error: function (e) {

			var json = "<h2>Ajax Response</h4><pre>"
				+ e.responseText + "</pre>";
			$('#expenseResponse').html(json);

			console.log("ERROR : ", e);
			$("#btn-search-home").prop("disabled", false);

		}
	});

	
}


