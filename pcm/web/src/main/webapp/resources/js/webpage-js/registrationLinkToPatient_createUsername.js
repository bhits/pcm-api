/**
 * Code to execute on document.ready event for registrationLinkToPatient_createUsername.html page
 */

$(document).ready(function() {
	$('#password').pstrength();
	
	// Submit event handler for signupForm form
	$("form#signupForm").submit(function(evt){
		var isFormValid = false;
		
		//Disable continue button
		$("form#signupForm button#signupFormSubmitButton").prop("disabled", true);
		
		isFormValid = validateSignupUsernamePassword();
		
		if(isFormValid === true){
			//On validation success, return true to proceed with form submission
			return true;
		}else{
			//On validation failure, prevent form submission
			evt.preventDefault();
			
			//Re-enable continue button
			$("form#signupForm button#signupFormSubmitButton").prop("disabled", false);
			
			return false;
		}
	});
	
});
