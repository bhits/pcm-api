/**
 * document.ready function for loginTroublePassword.html page
 */

$(document).ready(function(){
	
	//Submit event handler for forgot-password form
	$("form#forgot-password").submit(function(evt){
		//Disable submit button
		$("form#forgot-password button#forgotPasswordFormSubmitButton").prop("disabled", true);
		
		//TODO: Implement client-side validation
		
		//Proceed with form submission process
		return true;
	});
	
});
