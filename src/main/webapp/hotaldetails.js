/**
 * 
 */
/* function registerfun(){
	var hotel_name=$("#hotel_name").val();
	var address = $("#hotel_address").val();
	var ph_num=$("#phone_number").val();
	
	console.log(hotel_name);
	console.log(address);
	console.log(ph_num);
	if(hotel_name&&address)
		{
			if(ph_num.length==10){
				$.ajax({
					url:'hoteldetailback',
					method:'POST',
					data:{
						hotel_name:hotel_name,
						address:address,
						ph_num:ph_num
					},
					success:function(response){
						if(response=="success")
							{
								// ✅ Load `hoteldetails.html` inside `#content`
																	                                $("#context").load("hotelpage.html", function(response, status, xhr) {
																	                                    if (status === "error") {
																	                                        console.error("❌ Error loading hoteldetails.html:", xhr.status, xhr.statusText);
																	                                    } else {
																	                                        console.log("✅ hoteldetails.html loaded successfully.");
																	                                    }
																	                                });
							}
							else{
								$('#hotelresult').html("<p>An error occurred. Please try again later.</p>");
																           }
							},
						error: function(xhr, status, error) {
									               $('#hotelresult').html("<p>An error occurred. Please try again later.</p>");
									           }
					
				});
			}
			
			else{
				$("#hotelresult").html("<p>Phone number must be 10 digits</p>");
			}
		
		}
	
	else{
		$("#hotelresult").html("<p>Enter details.</p>");
	}
	
	
	
 }*/