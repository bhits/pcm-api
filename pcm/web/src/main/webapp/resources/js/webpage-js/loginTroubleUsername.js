//Code to execute on document.ready event for loginTroubleUsername.html page
$(document).ready(function(){
	initAllDOBFields();
});


/**
 * Validates form fields on the loginTroubleUsername.html page.
 * First performs pre-processing on DOB date field.
 * 
 * @param {String} selMonthID - the DOM element ID of the month select element
 * @param {String} selDayID - the DOM element ID of the day select element
 * @param {String} selYearID - the DOM element ID of the year select element
 * @param {String} txtOutDateID - the DOM element ID of the hidden input element that holds the resulting date string
 * @returns {Boolean} Returns true if form is valid; otherwise returns false
 */
function validateForgotUsernameForm(selMonthID, selDayID, selYearID, txtOutDateID){
	var isFormValid = true;
	
	try{
		preprocessDateInput(selMonthID, selDayID, selYearID, txtOutDateID);
	}catch(e){
		if(e instanceof ReferenceError){
			isFormValid = false;
			return isFormValid;
		}else{
			throw e;
		}
	}
	
	var isValidDate = isDate($('#' + txtOutDateID).val());

	if(isValidDate !== true){
		isFormValid = false;
		$('#dob_client_error_text').html(isValidDate);
		$('#dob_client_error_text').attr('style', "");
	}else{
		$('#dob_client_error_text').text("");
		$('#dob_client_error_text').attr('style', "display: none;");
	}

	var isValidFname = chkFname($('#first_name').val());

	if(isValidFname !== true){
		isFormValid = false;
		$('#fname_client_error_text').html(isValidFname);
		$('#fname_client_error_text').attr('style', "");
	}else{
		$('#fname_client_error_text').text("");
		$('#fname_client_error_text').attr('style', "display: none;");
	}

	var isValidLname = chkLname($('#last_name').val());

	if(isValidLname !== true){
		isFormValid = false;
		$('#lname_client_error_text').html(isValidLname);
		$('#lname_client_error_text').attr('style', "");
	}else{
		$('#lname_client_error_text').text("");
		$('#lname_client_error_text').attr('style', "display: none;");
	}
	
	var isValidEmail = chkEmail($('#email').val());

	if(isValidEmail !== true){
		isFormValid = false;
		$('#email_client_error_text').html(isValidEmail);
		$('#email_client_error_text').attr('style', "");
	}else{
		$('#email_client_error_text').text("");
		$('#email_client_error_text').attr('style', "display: none;");
	}
	
	return isFormValid;
}