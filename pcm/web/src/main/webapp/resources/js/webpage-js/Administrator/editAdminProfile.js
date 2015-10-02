/**
 * profile_registration.js must be included in the HTML
 * before this script file is included in order to function properly.
 */

//Code to execute on document.ready event for editAdminProfile.html page
$(document).ready(function() {
	initUserPassModal();
	
	$("form#adminProfileForm").submit(function(evt){
		var isFormValid = false;
		
		//Disable save button
		$("form#adminProfileForm button#adminProfileFormSubmitButton").prop("disabled", true);
		
		isFormValid = validateUpdateAdminProfile();
		
		if(isFormValid === true){
			//On validation success, return true to proceed with form submission
			return true;
		}else{
			//On validation failure, prevent form submission
			evt.preventDefault();
			
			//Re-enable save button
			$("form#adminProfileForm button#adminProfileFormSubmitButton").prop("disabled", false);
			
			return false;
		}
	});
});
