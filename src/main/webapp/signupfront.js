/**
 * 
 */function signupfun() {
	var username = $("#signusername").val();
	var password = $("#signpassword").val();
	var role = $('#role').val();
	console.log(username);
	console.log(password);
	console.log(role);


	$.ajax({
		url: 'namecheks',
		method: 'POST',
		data: {
			username: username,
			password: password,
			role: role
		},
		success: function(response) {
			console.log("result" + response);
			if (response == "success") {
				$.ajax({
					url: 'signupbac',
					method: 'POST',
					data: {
						username: username,
						password: password,
						role: role
					},
					success: function(response) {
						console.log("ress" + response);
						if (response == "success") {
							$('#signupresult').html("<p>Signup successful! Please login</p>");
						} else {
							$('#signupresult').html("<p>" + response + "</p>");
						}
					},
					error: function(xhr, status, error) {
						$('#signupresult').html("<p>An error occurred. Please try again later.</p>");
					}
				});
			} else if(response=="failure")
				{
					$('#signupresult').html("<p style='color: red;'> userexists /tryagain </p>")
				}
			else {
				$('#signupresult').html("<p style='color: red;'>"+ response+"</p>")
			}
		},
		error: function(xhr, status, error) {
			$('#signupresult').html("<p>An error occurred. Please try again later.</p>");
		}
	});


}
function loginfun() {
	var name = $("#username").val();
	var pass = $("#password").val();

	console.log(name);
	console.log(pass);


	$.ajax({
		url: 'loginstruts',
		method: 'POST',
		data: {
			username: name,
			password: pass,
		},
		dataType: "text",
		success: function(response) {
			console.log(response)
			if (response =="Manager"|| response =="User") {
				console.log(response)
				var name = response
				checkmanageror(name);
			}

			else if(response =="failure") {
				$('#loginresult').html("<p>login failed pls signup/enter crt userdetails</p>");
			}
			else{
				$('#loginresult').html("<p>"+response+"</p>")
			}
		},
		error: function(url, error, status) {
			console.log("Error:", error);
			$('#loginresult').html("<p>An error occurred. Please try again later.</p>");
		}
	});



}
function registerfun() {
	var hotel_name = $("#hotel_name").val();
	var address = $("#hotel_address").val();
	var ph_num = $("#phone_number").val();

	console.log(hotel_name);
	console.log(address);
	console.log(ph_num);


	$.ajax({
		url: "hoteldetail",
		method: 'POST',
		data: {
				hotel_name: hotel_name,
				address: address,
				ph_num: ph_num
			},
			dataType: 'json',
		success: function(response) {
			console.log(response);
			if (response.status === "success") {
				console.log(hotel_name);
				// ✅ Load `hoteldetails.html` inside `#content`

				window.location.href = "hotelsfrontview.html?hotel=" + encodeURIComponent(hotel_name);
			}
			else {
				$('#hotelresult').html("<p>" + response + "</p>");
			}
		},
		error: function(xhr, status, error) {

			$('#hotelresult').html("<p>An error occurred. Please try again later.</p>");
		}

	});
}

/*function fetchMenu(hotelname) {
	console.log(" hotelname:", hotelname);
	$("#hotel_name_display").html('<h2>Welcome to ' + hotelname + '</h2>');
	$.ajax({
		url: "fetchMenu",
		method: "POST",
		data: { hotel_name: hotelname },

		success: function(response) {
			console.log(" received:", response);
			var answer = response;
			$("#foodList").html(answer);

		},
		error: function(xhr, status, error) {
			console.error("❌ Error fetching menu:", error);
			$("#foodList").html("<p>Failed to load menu. Please try again.</p>");
		}
	});
}*/
function checkmanageror(name) {
	console.log(name);
	if (name == "Manager") {
		$.ajax({
			url: 'managerchecking',
			method: 'POST',
			success: function(response) {
				if ($.trim(response) == "newuser") {
					console.log("Loading hoteldetails.html...");
					console.log("ok" + response);
					window.location.href = "hoteldetails.html";
				}
				else {
					console.log(response);
					var hotelname = response;
					window.location.href = "hotelsfrontview.html?hotel=" + encodeURIComponent(hotelname);
				}
			},
			error: function(error, status, xhr) {
				$('#signupresult').html("<p>An error occurred. Please try again later.</p>");
			}
		});
	}
	else {
		window.location.href = "c.hoteldisplay.html";
	}
}