/**
 * 
 */
$(document).ready(function() {
	function getQueryParam(pram) {
		let urlParams = new URLSearchParams(window.location.search);
		return urlParams.get(pram);
	}

	let hotel_id = getQueryParam("hotel"); console.log(hotel_id);
	if (hotel_id) {
		// $("#hotel_name_display").html("<h2>Welcome to " + decodeURIComponent(hotelName) + "</h2>");
		fetchhotel(hotel_id);
	} else {
		$("#hotel_name_display").html("<h2>No Hotel Selected</h2>");
	}
});

function fetchhotel(hotel_id) {
	//$("#hotel_name_display").html(hotel_id);
	$.ajax({
		url: "Cgethotelname",
		method: "POST",
		data: {
			hotel_id: hotel_id
		},
		success: function(response) {
			console.log(response);
			var hotelname = response;
			fetchMenu(hotelname, hotel_id)

		},
		error: function(error, status, xhr) {
			console.log("Error Details:", status, error, xhr.responseText);
			$("#message").html("<p style='color: red;'>Error: " + xhr.responseText + "</p>");
		}
	})
}
function fetchMenu(hotelname, hotel_id) {

	console.log(" hotelname:", hotelname);
	$("#hotel_name_display").html('<h2>hotel ' + hotelname + '</h2>');
	$.ajax({
		url: "chotelmenu",
		method: "GET",
		data: { hotel_id: hotel_id },

		success: function(response) {
			console.log(" received:", response);
			if (!response || $.isEmptyObject(response)) {
				$("#foodstore").html("<p style='color: red;'>No food items</p>");
			}
			response.foodMenus.forEach(function(item) {
				let listhotel = `<div class='fooditem' id="food_${item.foodid}">
																			<span class='food-name'>${item.food}</span> - 
																			<span class='food-price'>${item.price}</span> 
																			<label>quantiy:</label>
																			<input type='number' name='quantity' class='quantity-input' placeholder='Enter quantity' min='1'>
																			<button class='remove-btn' onclick="addcart(${item.foodid})">add to cart</button>
																		</div>`;
				$("#foodstore").append(listhotel);
				$("#cartSection").hide();
				$("#cartContent").hide();
				

			});

		},
		error: function(xhr, status, error) {
			console.log("Error Details:", status, error, xhr.responseText);
			$("#message").html("<p style='color: red;'>Error: " + xhr.responseText + "</p>");
		}
	});
}
function addcart(foodid) {
	var food_id = foodid;
	var quantity = $("#food_" + foodid).find("input[name='quantity']").val();
	console.log(food_id, quantity);
	if (quantity > 0) {
		$.ajax({
			url: 'checkfirst',
			method: 'POST',
			data: { food_id: food_id },
			success: function(response) {
				console.log(response)
				if (response.trim() === "success") {
					console.log(response);
					addtocart(food_id, quantity);
				}
				else if (response.trim() === "failure") {
					var answer = response;

					$.ajax({
						url: 'cartaddd',
						method: 'POST',

						success: function(response) {
							if (response = "success") {
								console.log(response);
								var answer = response;
								addtocart(food_id, quantity);

							}
						},
						error: function(xhr, status, error) {
							console.log("Error Details:", status, error, xhr.responseText);
							$("#message").html("<p style='color: red;'>Error: " + xhr.responseText + "</p>");
						}
					});

				}
				else {
					console.error("Error fetching menu:", error);
					$("#message").html("<p>Failed to load menu.</p>");
				}
			},
			error: function(xhr, status, error) {
				console.log("Error Details:", status, error, xhr.responseText);
				$("#message").html("<p style='color: red;'>Error: " + xhr.responseText + "</p>");
			}
		});

	}
	else {
		$("#message").html("<p>enter quantity.....</p>");
	}
}
function placeorder() {
	console.log("order");
	$.ajax({
		url: "checkforcart",
		method: "POST",

		success: function(response) {
			if (response == "success") {
				console.log(response)
				$.ajax({
					url: "orderplaced",
					method: "PUT",

					success: function(response) {
						if (response == "success") {
							console.log(" received:", response);
							window.location.href = "placeorderview.html";
							//$("#message").html("<p>food ordered....</p>");
						}
						else {
							$("#message").html("<p>not order");
						}
					},
					error: function(xhr, status, error) {
						console.log("Error Details:", status, error, xhr.responseText);
						$("#message").html("<p style='color: red;'>Error: " + xhr.responseText + "</p>");
	7
				}
				});
			}

			else {
				$("#message").html("<p>add to cart</p>");
			}
		},
	});
}
function logout() {
	$.ajax({
		url: "logoutexist",
		method: "POST",

		success: function(response) {

			var action = response;
			console.log(action)
			if (action == "success") {
				window.location.href = "loginpage.html";
			}
			else {
				alert("Logout failed. Please try again.");
			}
b
		},
		error: function(xhr, status, error) {
			console.log("Error Details:", status, error, xhr.responseText);
			$("#message").html("<p style='color: red;'>Error: " + xhr.responseText + "</p>");
		}
	});
}
function viewcart() {
	$("#menuSection").hide();
	$("#cartSection").show();
	$("#cartContent").show();
	$("#message").hide(); // Show cart section

	$.ajax({
		url: "displaycart",
		method: "POST",
		dataType: "json",
		success: function(response) {
			console.log("Response:", response);


			var total = response.total;



			var tableHtml = "<table border='1'><tr><th>Food Name</th><th>Price</th><th>Quantity</th><th>Total</th><th>Action</th></tr>";

			response.allmenu.forEach(function(menu) {
				tableHtml += "<tr>" +
					"<td>" + menu.food + "</td>" +
					"<td>" + menu.price + "</td>" +
					"<td>" + menu.quantity + "</td>" +
					"<td>" + menu.total + "</td>" +
					"<td><button onclick='removeFromCart(" + menu.foodid + ")'>Remove</button></td>" +
					"</tr>";
			});

			tableHtml += "<tr><td colspan='3'>Final Total:</td><td>" + total + "</td></tr>";
			tableHtml += "</table>";

			$("#cartContent").html(tableHtml);
		},
		error: function(xhr, status, error) {
			console.log("Error Details:", status, error, xhr.responseText);
			$("#message").html("<p style='color: red;'>  add food items to view cart   </p>");
			
		}
	});

}
function backtomenu() {
	$("#cartContent").hide();
	$("#cartSection").hide();  // Hide cart section
	$("#menuSection").show();
	$("#message").show(); // Show menu section
}

function removeFromCart(foodId) {
	$.ajax({
		url: "removeFromCarttable",
		method: "POST",
		data: { food_id: foodId },
		success: function(response) {
			console.log(response);

			viewcart();
		},
		error: function(xhr, status, error) {
			console.log("Error Details:", status, error, xhr.responseText);
			$("#message").html("<p style='color: red;'>Error: " + xhr.responseText + "</p>");
		}
	});
}
function addtocart(food_id, quantity) {
	console.log(food_id);

	$.ajax({
		url: "addtocarts",
		method: "POST",
		data: {
			food_id: food_id,
			quantity: quantity
		},
		success: function(response) {
			console.log(" received:", response);
			if (response == "success") {
				console.log(" received:", response);
				var answer = response;
				$("#message").html("<p>food added....</p>");
			}
			else {
				$("#message").html("<p>no repeat food add other food");
			}
		},
		error: function(xhr, status, error) {
			console.log("Error Details:", status, error, xhr.responseText);
			$("#message").html("<p style='color: red;'>Error: " + xhr.responseText + "</p>");
		}
	});
}