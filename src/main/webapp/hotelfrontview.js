/**
 * 
 */
$(document).ready(function() {
	function getQueryParam(param) {
		let urlParams = new URLSearchParams(window.location.search);
		return urlParams.get(param);
	}

	let hotelName = getQueryParam("hotel");

	if (hotelName) {
		// $("#hotel_name_display").html("<h2>Welcome to " + decodeURIComponent(hotelName) + "</h2>");


		fetchMenu(hotelName);
	} else {
		$("#hotel_name_display").html("<h2>No Hotel Selected</h2>");
	}
});
function fetchMenu(hotelname) {
	console.log(" hotelname:", hotelname);
	$("#hotel_name_display").html('<h2>hotel ' + hotelname + '</h2>');
	$.ajax({
		url: "Mmenufodd",
		method: "GET",
		data: { hotel_name: hotelname },

		success: function(response) {
			console.log(" received:", response);
			$("#foodList").empty();
			if (response.foodMenu && response.foodMenu.length > 0) {

				response.foodMenu.forEach(function(item) {
					let listItem = `<div class='food-item' id="food_${item.foodid}">
													<span class='food-name'>${item.food}</span> - 
													<span class='food-price'>${item.price}</span> 
													<button class='remove-btn' onclick="removeFood(${item.foodid})">Remove</button>
												</div>`;
					$("#foodList").append(listItem);
				});
			} else {
				$("#message").html("<p>No food items available.</p>");
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching menu:", error);
			$("#message").html("<p>Failed to load menu.</p>");
		}
	});
}
function removeFood(foodId) {
	console.log(" food item:", foodId);
var id=foodId;
	$.ajax({
		url: "MremoveFood",
		method: "POST",
		data: { food_id: foodId },
		success: function(response) {

			var action = response.trim();
			console.log(action)
			if (action == "success") {
				let element = $("#food_" + id); 
				               console.log("Element to remove:", element);

				               if (element.length) {
				                   element.remove(); // Remove the item from the DOM
				                   console.log("Food item removed:", id);
				               } else {
				                   console.warn("Element not found for ID:", id);
				               }
			}
			else {
				console.log(response)
			}

		},
		error: function(xhr, status, error) {
			console.error(" Error removing food:", error);
			alert("Failed to remove food item. .");
		}
	});
}
function add() {

	var foodName = $("#food").val();
	var foodPrice = $("#price").val();
	console.log(foodName);
	console.log(foodPrice);
	if (!(foodName && foodPrice > 1)) {
		alert("Enter valid food name and price!");
		return;
	}
	$.ajax({



		url: "MaddFood",
		method: "POST",
		data: {
			food_name: foodName,
			food_price: foodPrice,

			//  hotel_name: hotelName
		},

		success: function(response) {
			console.log(" added:", response);
			var food_id = response;
			console.log(food_id);
			// Append the new item to the food list
			$("#foodList").append(`
				<div id="food_${food_id}" class='food-item'>
				           <span class='food-name'>${foodName}</span>
				           - <span class='food-price'>${foodPrice}</span>
				           <button class='remove-btn' onclick='removeFood(${food_id})'>Remove</button>
				       </div>
	            `);

			$("#food").val("");
			$("#price").val("");

			// Hide the input form after adding
			$("#showaddmenu").hide();
			$("#message").html("<p> food added</p>");
		},
		error: function(xhr, status, error) {
			console.error("Error adding food:", xhr.responseText);
			alert("Failed to add food: " + xhr.responseText);
		}
	});
}
function showadd() {
	console.log("show");
	$("#showaddmenu").html(`
                <label>Food Name:</label> 
                <input type="text" name="food" id="food" placeholder="Enter food name">
                
                <label>Price:</label> 
                <input type="number" name="price" id="price" placeholder="Enter price" min="1">

                <button type="button" class="butt" onclick="add()">Submit</button>
            `).show();
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

		},
		error: function(xhr, status, error) {
			console.error("Error removing food:", error);
			alert("Failed to remove food item. Try again.");
		}
	});
}