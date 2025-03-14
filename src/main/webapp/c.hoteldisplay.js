/**
 * 
 */
$(document).ready(function() {
	loadHotels();
});
function loadHotels() {

	$.ajax({
		url: 'Chotelfetch',
		method: 'POST',
		success: function(response) {
			console.log(response);
			response.hotelname.forEach(function(detail) {
								let listhotel = `<div class='hotel-item' id="hotel_${detail.id}">
																<span class='hotel-name'>${detail.name}</span> - 
																<span class='hotel-price'>${detail.address}</span> 
																<button class='remove-btn' onclick="viewfood(${detail.id})">view</button>
															</div>`;
								$("#hotellist").append(listhotel);
								});
		},
		error: function(xhr, status, error) {
			console.log("Error Details:", status, error, xhr.responseText);
						$("#message").html("<p style='color: red;'>Error: " + xhr.responseText + "</p>");
		}
	});
}
function viewfood(hot_id) {
	console.log(hot_id, typeof (hot_id));
	if(hot_id>0)
		{
			
		
				window.location.href = "chotelmenu.html?hotel=" + encodeURIComponent(hot_id);
			}
		else{
			console.error("Error fetching menu:", error);
						$("#hotellist").html("<p>Failed to load menu.</p>");
					
		}
	
}