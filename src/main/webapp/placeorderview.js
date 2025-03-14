/**
 * 
 */
$(document).ready(function() {
	fetchfoods()
});

function fetchfoods() {
	//$("#hotel_name_display").html(hotel_id);
	$.ajax({
		url: "viewplacedorder",
		method: "POST",
		
		success: function(response) {

			        var total = response.total;

			       

			        var tableHtml = "<table border='1'><tr><th>Food Name</th><th>Price</th><th>Quantity</th><th>Total</th><th>Action</th></tr>";
			        
			        response.allmenu.forEach(function(menu) {
			            tableHtml += "<tr>" +
			                "<td>" + menu.food + "</td>" +
			                "<td>" + menu.price + "</td>" +
			                "<td>" + menu.quantity + "</td>" +
			                "<td>" + menu.total + "</td>" +  
			                "</tr>";
			        });

			        tableHtml += "<tr><td colspan='3'>Final Total:</td><td>" + total + "</td></tr>";
			        tableHtml += "</table>";

			       
			$("#cartItems").html(tableHtml)
		},
		error: function(error, status, xhr) {
			console.log("Error Details:", status, error, xhr.responseText);
						$("#showmenu").html("<p style='color: red;'>Error: " + xhr.responseText + "</p>");;
		}
	})
}function logout() {
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
			//console.error("Error removing food:", error);
			alert(" Try again.");
		}
	});
}