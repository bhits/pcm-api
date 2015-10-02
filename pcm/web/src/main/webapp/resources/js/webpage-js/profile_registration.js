/**
 * FUNCTIONS USED ON PROFILE.HTML PAGE & REGISTRATION.HTML PAGE
 */

//Initialize date of birth fields

function initDOBYearField(){
	var currentYear = (new Date).getFullYear();
	var minYear = currentYear - 125;

	if(minYear < 1900){
		minYear = 1900;
	}

	for (var i=currentYear; i >= minYear; i--){
		$('#year').append($("<option/>", { value: i, text: i}));
	}
}

function setDOBfromTxt(){
	var inString = $('#date').attr('value');
	
	if(inString.length > 0){
		var inMonth = inString.substring(0, 2);
		var inDay = inString.substring(3, 5);
		var inYear = inString.substring(6, 10);
	
		setDOBMonth(inMonth);
		setDOBDay(inDay);
		setDOBYear(inYear);
	}else{
		setDOBMonth("  ");
		setDOBDay("  ");
		setDOBYear("    ");
	}
}

function setDOBMonth(inMonth){
	$('#month').attr('value', inMonth);
}

function setDOBDay(inDay){
	$('#day').attr('value', inDay);
}

function setDOBYear(inYear){
	$('#year').attr('value', inYear);
}

function initAllDOBFields() {
	initDOBYearField();
	setDOBfromTxt();
}

function initUserPassModal(){
	
	//Bind hidden.bs.modal event handler to clear modal fields & error messages
	$('#username_password_modal').on('hidden.bs.modal', function(){
		clearPassModal();
	});
	
};

/*  Clear password modal input field value, and clear error messages
 *  for both password & username inputs. */ 
function clearPassModal(){
	//Clear password value
	$('#password').val("");
	
	//Clear user_name error message box value
	$('#username_client_error_text').text("");
	//Set user_name error message box to display: none
	$('#username_client_error_text').attr('style', "display: none;");
	
	//Clear password error message box value
	$('#pwd_client_error_text').text("");
	//Set password error message box to display: none
	$('#pwd_client_error_text').attr('style', "display: none;");
}

function initAllSSNFields(){
	ssnString= $('#socialsecurity').attr('value');
	$('#ssn1').attr('value', ssnString.substring(0,3));
	$('#ssn2').attr('value', ssnString.substring(3,5));
	$('#ssn3').attr('value', ssnString.substring(5,9));
	
}