//Code to execute on document.ready event for registrationLinkToPatient.html page
$(document).ready(function() {
	var currentYear = (new Date).getFullYear();
	var minYear = currentYear - 125;

	if(1900 > minYear){
		minYear = 1900;
	}

	for (var i=currentYear; i >= minYear; i--){
		$('#year').append($("<option/>", { value: i, text: i}));
	}

	if(1 >= $('#date').val().length){
		preprocessInput();
	}
	
	// Submit event handler for signupForm form
	$("form#signupForm").submit(function(evt){
		var isFormValid = false;
		
		//Disable continue button
		$("form#signupForm button#signupFormSubmitButton").prop("disabled", true);
		
		isFormValid = validateSignupLinkToPatientForm();
		
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

function clearField(){
	//clear state_name value
	clearVerificationCode();
	//trigger change event handler for state_name
	$("#verificationCode").triggerHandler("change");

	//clear city_name value
	clearDOB();
	//trigger change event handler for city_name
	$("#dob").triggerHandler("change");

}

function clearVerificationCode(){
	$("#verificationCode").val('');
	$('#verificationCode_client_error_text').attr('style', "display: none;");
}

function clearDOB(){
	$("#month").val('');
	$("#day").val('');
	$("#year").val('');
	$('#dob_client_error_text').attr('style', "display: none;");
}
	
/* Issue#503 fix start
 * After page loaded, it needs to be cleaned data.
 */
window.onload = function(){
	clearField();
};
//Issue #503 Fix End
