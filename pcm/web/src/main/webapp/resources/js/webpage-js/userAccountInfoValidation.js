/**
 * Functions involved in validating input for user account fields
 */


function validateSignupForm(selMonthID, selDayID, selYearID, txtOutDateID){				
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

	isFormValid = validateCommonFields(isFormValid, txtOutDateID);
	
	var isValidUname = chkUname($('#user_name').val());

	if(isValidUname !== true){
		isFormValid = false;
		$('#username_client_error_text').html(isValidUname);
		$('#username_client_error_text').attr('style', "");
	}else{
		$('#username_client_error_text').text("");
		$('#username_client_error_text').attr('style', "display: none;");
	}
	
	var isValidPword = chkPword($('#password').val(), $('#user_name').val());

	if(isValidPword !== true){
		isFormValid = false;
		$('#password_client_error_text').html(isValidPword);
		$('#password_client_error_text').attr('style', "");
	}else{
		$('#password_client_error_text').text("");
		$('#password_client_error_text').attr('style', "display: none;");
	}
	
	var ssnStr = $('#ssn1').val() + $('#ssn2').val() + $('#ssn3').val() ;
	
	var isValidSSN = chkSSN(ssnStr);

	if(isValidSSN !== true){
		isFormValid = false;
		$('#ssn_client_error_text').html(isValidSSN);
		$('#ssn_client_error_text').attr('style', "");
	}else{
		$('#ssn_client_error_text').text("");
		$('#ssn_client_error_text').attr('style', "display: none;");
	}	
	
	
	return isFormValid;
}


function validateProfileUpdateForm(selMonthID, selDayID, selYearID, txtOutDateID){
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
	
	var flagUserPassInvalid = false;
	
	$('#updatedMessage').attr('style', "display: none;");
	
	isFormValid = validateCommonFields(isFormValid, txtOutDateID);
	
	var isValidUname = chkUname($('#user_name').val());
	
	if(isValidUname !== true){
		isFormValid = false;
		flagUserPassInvalid = true;
		$('#username_client_error_text').html(isValidUname);
		$('#username_client_error_text').attr('style', "");
	}else{
		$('#username_client_error_text').text("");
		$('#username_client_error_text').attr('style', "display: none;");
	}
	
	var isValidPword = chkPword($('#password').val(), "");
	
	if(isValidPword !== true){
		isFormValid = false;
		flagUserPassInvalid = true;
		$('#pwd_client_error_text').html("Invalid Password");
		$('#pwd_client_error_text').attr('style', "");
	}else{
		$('#pwd_client_error_text').text("");
		$('#pwd_client_error_text').attr('style', "display: none;");
	}
	
	var isValidPhone = chkPhone($('#phone').val());

	if(isValidPhone !== true){
		isFormValid = false;
		$('#phone_client_error_text').html(isValidPhone);
		$('#phone_client_error_text').attr('style', "");
	}else{
		$('#phone_client_error_text').text("");
		$('#phone_client_error_text').attr('style', "display: none;");
	}
	
	var isValidZip = chkZip($('#zip').val());

	if(isValidZip !== true){
		isFormValid = false;
		$('#zip_client_error_text').html(isValidZip);
		$('#zip_client_error_text').attr('style', "");
	}else{
		$('#zip_client_error_text').text("");
		$('#zip_client_error_text').attr('style', "display: none;");
	}
	
	var ssnStr = $('#ssn1').val() + $('#ssn2').val() + $('#ssn3').val() ;
	var isValidSSN = chkSSN(ssnStr);

	if(isValidSSN !== true){
		isFormValid = false;
		$('#ssn_client_error_text').html(isValidSSN);
		$('#ssn_client_error_text').attr('style', "");
	}else{
		$('#ssn_client_error_text').text("");
		$('#ssn_client_error_text').attr('style', "display: none;");
	}	

	
	/*var isValidMrn = chkMrn($('#medical_record_number').val());

	if(isValidMrn !== true){
		isFormValid = false;
		$('#mrn_client_error_text').html(isValidMrn);
		$('#mrn_client_error_text').attr('style', "");
	}else{
		$('#mrn_client_error_text').text("");
		$('#mrn_client_error_text').attr('style', "display: none;");
	}		*/
	
	if(isFormValid !== true){
		if(flagUserPassInvalid === false){
			$('#username_password_modal').modal('hide');
		}
		return false;
	}else{
		return true;
	}
}


function validateAdminEditPatientProfileForm(selMonthID, selDayID, selYearID, txtOutDateID){
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
	
	isFormValid = validateCommonFields(isFormValid, txtOutDateID);
	
	var isValidPhone = chkPhone($('#phone').val());

	if(isValidPhone !== true){
		isFormValid = false;
		$('#phone_client_error_text').html(isValidPhone);
		$('#phone_client_error_text').attr('style', "");
	}else{
		$('#phone_client_error_text').text("");
		$('#phone_client_error_text').attr('style', "display: none;");
	}
	
	var isValidZip = chkZip($('#zip').val());

	if(isValidZip !== true){
		isFormValid = false;
		$('#zip_client_error_text').html(isValidZip);
		$('#zip_client_error_text').attr('style', "");
	}else{
		$('#zip_client_error_text').text("");
		$('#zip_client_error_text').attr('style', "display: none;");
	}
			
	
	return isFormValid;
}

/**
 * Validate input fields common to multiple forms
 * NOTE: DO NOT CALL THIS FUNCTION DIRECTLY;
 * Only call this function from other validation functions
 * 
 * @param {Boolean} inFormValid - variable from other validation function indicating validation status so far
 * @param {String} txtOutDateID
 * @returns {Boolean} isFormValidSub - true if validation successful so far; false if validation fails
 */
function validateCommonFields(inFormValid, txtOutDateID){
	var isFormValidSub = inFormValid;
	
	var isValidDate = isDate($('#' + txtOutDateID).val());

	if(isValidDate !== true){
		isFormValidSub = false;
		$('#dob_client_error_text').html(isValidDate);
		$('#dob_client_error_text').attr('style', "");
	}else{
		$('#dob_client_error_text').text("");
		$('#dob_client_error_text').attr('style', "display: none;");
	}

	var isValidFname = chkFname($('#first_name').val());

	if(isValidFname !== true){
		isFormValidSub = false;
		$('#fname_client_error_text').html(isValidFname);
		$('#fname_client_error_text').attr('style', "");
	}else{
		$('#fname_client_error_text').text("");
		$('#fname_client_error_text').attr('style', "display: none;");
	}

	var isValidLname = chkLname($('#last_name').val());

	if(isValidLname !== true){
		isFormValidSub = false;
		$('#lname_client_error_text').html(isValidLname);
		$('#lname_client_error_text').attr('style', "");
	}else{
		$('#lname_client_error_text').text("");
		$('#lname_client_error_text').attr('style', "display: none;");
	}

	var isValidGender = chkGender($('#gender').val());

	if(isValidGender !== true){
		isFormValidSub = false;
		$('#gender_client_error_text').html(isValidGender);
		$('#gender_client_error_text').attr('style', "");
	}else{
		$('#gender_client_error_text').text("");
		$('#gender_client_error_text').attr('style', "display: none;");
	}
	
	
	var isValidEmail = chkEmail($('#email').val());

	if(isValidEmail !== true){
		isFormValidSub = false;
		$('#email_client_error_text').html(isValidEmail);
		$('#email_client_error_text').attr('style', "");
	}else{
		$('#email_client_error_text').text("");
		$('#email_client_error_text').attr('style', "display: none;");
	}
	
	return isFormValidSub;
}


/**
 * Validate Create Patient Account form on Administrator/adminHome.html page
 * 
 * @param {String} selMonthID
 * @param {String} selDayID
 * @param {String} selYearID
 * @param {String} txtOutDateID
 * @returns {Boolean} isFormValid - true if form validates successfully; false if validation fails
 */
function validateAdminCreatePatientAccount(selMonthID, selDayID, selYearID, txtOutDateID){
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

	var isValidGender = chkGender($('#gender').val());

	if(isValidGender !== true){
		isFormValid = false;
		$('#gender_client_error_text').html(isValidGender);
		$('#gender_client_error_text').attr('style', "");
	}else{
		$('#gender_client_error_text').text("");
		$('#gender_client_error_text').attr('style', "display: none;");
	}
	
	var isValidSSN = chkSSN($('#socialsecurity').val());

	if(isValidSSN !== true){
		isFormValid = false;
		$('#ssn_client_error_text').html(isValidSSN);
		$('#ssn_client_error_text').attr('style', "");
	}else{
		$('#ssn_client_error_text').text("");
		$('#ssn_client_error_text').attr('style', "display: none;");
	}
	
	
	var isValidEmail = chkEmail($('#email').val());

	if(isValidEmail !== true){
		isFormValid= false;
		$('#email_client_error_text').html(isValidEmail);
		$('#email_client_error_text').attr('style', "");
	}else{
		$('#email_client_error_text').text("");
		$('#email_client_error_text').attr('style', "display: none;");
	}
	
	return isFormValid;
}

/**
 * Validate update admin profile for on registationLinkToPatient.html page
 * 
 * @returns Boolean isFormValid - true if validation succeeds; false if it fails
 */

function validateSignupLinkToPatientForm(selMonthID, selDayID, selYearID, txtOutDateID){
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
		/* Issue#503 fix start
		 * This function should use parameter 'isFormValid' instead of 'isFormValidSub', otherwise it would not verify the date is valid.
		 */
		//isFormValidSub = false;	
		isFormValid = false;
		//Issue #503 Fix End
		
		$('#dob_client_error_text').html(isValidDate);
		$('#dob_client_error_text').attr('style', "");
	}else{
		$('#dob_client_error_text').text("");
		$('#dob_client_error_text').attr('style', "display: none;");
	}

	
	var isValidVerificationCode = chkVerificationCode($('#verificationCode').val());

	if(isValidVerificationCode !== true){
		isFormValid = false;
		$('#verificationCode_client_error_text').html(isValidVerificationCode);
		$('#verificationCode_client_error_text').attr('style', "");
	}else{
		$('#verificationCode_client_error_text').text("");
		$('#verificationCode_client_error_text').attr('style', "display: none;");
	}

	return isFormValid;
	
	}

function validateSignupUsernamePassword(){
	var isFormValid = true;
	var isValidUname = chkUname($('#user_name').val());
	
	if(isValidUname !== true){
		isFormValid = false;
		flagUserPassInvalid = true;
		$('#username_client_error_text').html(isValidUname);
		$('#username_client_error_text').attr('style', "");
	}else{
		$('#username_client_error_text').text("");
		$('#username_client_error_text').attr('style', "display: none;");
	}
	
	
	var isValidPword = chkPword($('#password').val(), "");
	
	if(isValidPword !== true){
		isFormValid = false;
		flagUserPassInvalid = true;
		$('#pwd_client_error_text').html(isValidPword);
		$('#pwd_client_error_text').attr('style', "");
	}else{
		$('#pwd_client_error_text').text("");
		$('#pwd_client_error_text').attr('style', "display: none;");
	}
	
var isValidPword = chkPword($('#reenterPassword').val(), "");
	
	if(isValidPword !== true){
		isFormValid = false;
		flagUserPassInvalid = true;
		$('#password_client_error_text2').html(isValidPword);
		$('#password_client_error_text2').attr('style', "");
	}else{
		$('#password_client_error_text2').text("");
		$('#password_client_error_text2').attr('style', "display: none;");
	}

	return isFormValid;
	
	}




/* Function to validate update admin profile form
 * on Administrator/editAdminProfile.html page */
function validateUpdateAdminProfile(){

	var isFormValid = true;
	var flagUserPassInvalid = false;

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

	var isValidGender = chkGender($('#gender').val());

	if(isValidGender !== true){
		isFormValid = false;
		$('#gender_client_error_text').html(isValidGender);
		$('#gender_client_error_text').attr('style', "");
	}else{
		$('#gender_client_error_text').text("");
		$('#gender_client_error_text').attr('style', "display: none;");
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
	
	var isValidUname = chkUname($('#user_name').val());
	
	if(isValidUname !== true){
		isFormValid = false;
		flagUserPassInvalid = true;
		$('#username_client_error_text').html(isValidUname);
		$('#username_client_error_text').attr('style', "");
	}else{
		$('#username_client_error_text').text("");
		$('#username_client_error_text').attr('style', "display: none;");
	}
	
	/*
	var isValidPword = chkPword($('#password').val(), "");
	
	if(isValidPword !== true){
		isFormValid = false;
		flagUserPassInvalid = true;
		$('#pwd_client_error_text').html("Invalid Password");
		$('#pwd_client_error_text').attr('style', "");
	}else{
		$('#pwd_client_error_text').text("");
		$('#pwd_client_error_text').attr('style', "display: none;");
	}
	
	*/
	
	if(isFormValid !== true){
		if(flagUserPassInvalid === false){
			$('#username_password_modal').modal('hide');
		}
		return false;
	}else{
		return true;
	}
	
}

/*
 * A function to validate the contact form 
 * on contact.html
 */

function validateContactForm(){				
	var isFormValid = true;
		
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
	
	var isValidEmail = chkEmail($('#emailConfirmation').val());

	if(isValidEmail !== true){
		isFormValid = false;
		$('#email_client_error_text2').html(isValidEmail);
		$('#email_client_error_text2').attr('style', "");
	}else{
		$('#email_client_error_text2').text("");
		$('#email_client_error_text2').attr('style', "display: none;");
	}
	
	
	var isValidEmail = compareStrings($('#email').val(), $('#emailConfirmation').val());
	
	if(isValidEmail !== true){
		isFormValid = false;
		$('#email_client_error_text3').html(isValidEmail);
		$('#email_client_error_text3').attr('style', "");
	}else{
		$('#email_client_error_text3').text("");
		$('#email_client_error_text3').attr('style', "display: none;");
	}
	
	var isValidPhone = chkPhone($('#phone').val());

	if(isValidPhone !== true){
		isFormValid = false;
		$('#phone_client_error_text').html(isValidPhone);
		$('#phone_client_error_text').attr('style', "");
	}else{
		$('#phone_client_error_text').text("");
		$('#phone_client_error_text').attr('style', "display: none;");
	}
	
	if(isFormValid !== true){
		return false;
	}
}
