//Code to execute on document.ready event for registration.html page
$(document).ready(function() {
	
	$('#ssn1, #ssn2, #ssn3').autotab_magic().autotab_filter('numeric');
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

	setDOBfromTxt();
	
	$('#password').pstrength();
	
	// Submit event handler for signupForm form
	$("form#signupForm").submit(function(evt){
		var isFormValid = false;
		
		//Disable save button
		$("form#signupForm button#signupFormSubmitButton").prop("disabled", true);
		
		isFormValid = validateSignupForm();
		
		if(isFormValid === true){
			//On validation success, return true to proceed with form submission
			return true;
		}else{
			//On validation failure, prevent form submission
			evt.preventDefault();
			
			//Re-enable save button
			$("form#signupForm button#signupFormSubmitButton").prop("disabled", false);
			
			return false;
		}
	});
	
});

$( ".ssn" ).change(function() {
	var ssnStr = $('#ssn1').val() + $('#ssn2').val() + $('#ssn3').val() ;
	$('#socialsecurity').attr('value', ssnStr);
});
