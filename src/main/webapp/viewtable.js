/**
 * 
 */
/*$(document).ready(function() {
	cartview()
});
function cartview() {
	$.ajax({
		url: "viewcart",
		method: "POST",
		datatype: "json",
		success: function(response) {
			console.log(response)
			var cartItems = response.allmenu; // ✅ Corrected Key
			var total = response.total;
			var tableHtml = "<table border='1'><tr><th>Food Name</th><th>Price</th><th>Quantity</th><th>Total</th></tr>";

			cartItems.forEach(function(menu) {
				tableHtml += "<tr>" +
					"<td>" + menu.food + "</td>" +
					"<td>" + menu.price + "</td>" +
					"<td>" + menu.quantity + "</td>" +
					"<td>" + menu.total + "</td>" +
					"<td><button class='remove-btn' data-foodid='" + menu.food_id + "'>Remove</button></td>" +
					"</tr>";
			});
			tableHtml += "<tr><td colspan='3'>Final Total:</td><td>" + total + "</td></tr>";
			tableHtml += "</table>"

			$("#cartTable").html(tableHtml)

		},
		error: function(xhr, status, error) {
			//console.error("Error removing food:", error);
			alert(" Try again.");
		}
	});
}
$(document).on("click", ".remove-btn", function() {
    var foodId = $(this).data("foodid");

    $.ajax({
        url: "removeFromCart",
        method: "POST",
        data: { food_id: foodId },
        success: function(response) {
            alert("Item removed successfully!");
            cartview(); // Refresh cart view after deletion
        },
        error: function(xhr, status, error) {
            alert("Failed to remove item. Try again.");
        }
    });
});*/
function back(){
	window.Location.href="chotelmenu.html";
}