/**
 /function signupfun(){
	var username = $("#signusername").val();
		           var password = $("#signpassword").val();
				   var role=$('#role').val();
		console.log(username);
		console.log(password);
		console.log(role);
		
		if (username && password) {
		       $.ajax({
		           url: 'signup', // Ensure this is the correct URL for your servlet
		           method: 'POST',
		           data: {
		               username: username,
		               password: password,
					   role:role
		           },
				 
		           success: function(response) {
		               console.log("ress"+response); // Log the response to the console
		               if (response == "success") {
		                   $('#signupresult').html("<p>Signup successful! Please login</p>");
		               } else {
		                   $('#signupresult').html("<p>Signup failed. Username might already exist.</p>");
		               }
		           },
		           error: function(xhr, status, error) {
		               $('#signupresult').html("<p>An error occurred. Please try again later.</p>");
		           }
		       });
		   } else {
		       $('#signupresult').html("<p>Enter details.</p>");
		   }
}
function loginfun(){
	var name =$("#username").val();
	var pass=$("#password").val();
	var role=$('#role').val();
	console.log(name);
	console.log(pass);
	console.log(role);
	if(name&& pass)
		{
			$.ajax({
				           url: 'login', // Ensure this is the correct URL for your servlet
				           method: 'POST',
				           data: {
				               username: name,
				               password: pass,
							   role:role
				           },
	           success:function(response)
			   {
				
				if(response=="success")
					{
						console.log(response)
						$.ajax({
							url:'managercheck',
							method:'POST',
							
							
							success:function(response){
								if(response=="newuser")
									{console.log(response);
                                          hoteldetails();
									}
								else {
									console.log(response);
								}
								
							},
							error:function(error,status,xhr)
							{
								$('#signupresult').html("<p>An error occurred. Please try again later.</p>");
							}
							
							
							
						});
						
			
						
					}
					else{
						$('#loginresult').html("<p>login failed pls signup</p>");
					}
			   },
			   error:function(url,error,status)
			   {
				$('#loginresult').html("<p>An error occurred. Please try again later.</p>");
			   }
			});
		}
		else{
			$('#loginresult').html("<p>Enter details..</p>");
		}
	
	
}
function hoteldetails()
{
	console.lod("hotel");
	$("body").load("hoteldetails.html");
}*/