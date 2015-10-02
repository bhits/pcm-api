	/**
 * Pre-process input from select elements for selecting a date
 * 
 *    Assembles a single string representing the selected date
 *    using the element id's from the input parameters, then puts
 *    the result in the specified text box.
 * 
 * @param {String} selMonthID
 * @param {String} selDayID
 * @param {String} selYearID
 * @param {String} txtOutDateID
 */
function preprocessDateInput(selMonthID, selDayID, selYearID, txtOutDateID){
	
	var selMonth = $('#' + selMonthID).val();
	var selDay = $('#' + selDayID).val();
	var selYear = $('#' + selYearID).val();
	
	var txtOutDateVal = $('#' + txtOutDateID).val();
	
	if(selMonth === undefined){
		throw new ReferenceError("selMonthID referes to invalid/undefined element");
	}else if(selDay === undefined){
		throw new ReferenceError("selDayID referes to invalid/undefined element");
	}else if (selYear === undefined){
		throw new ReferenceError("selYearID referes to invalid/undefined element");
	}else if (txtOutDateVal === undefined){
		throw new ReferenceError("txtOutDateID referes to invalid/undefined element");
	}else{
		if ($.isNumeric(selMonth) === false){
			selMonth = "  ";
		}
	
		if ($.isNumeric(selDay) === false){
			selDay = "  ";
		}
	
		if ($.isNumeric(selYear) === false){
			selYear = "    ";
		}
		
		$('#' + txtOutDateID).attr('value', selMonth + "/" + selDay + "/" + selYear);
	}
}


function chkVerificationCode(inVerificationCode){
	var returnVal = null;
	
	if(0 >= inVerificationCode.length){
		returnVal = "Verification Code is required";
	}else{
		returnVal = true;
	}
	
	return returnVal;
}


function chkFname(inFname){
	var inFname = inFname.trim();
	if(2 > inFname.length || inFname.length > 30){
		if(0 >= inFname.length){
			return "First Name is required";
		}else{
			return "First Name must be between 2 and 30 characters long";
		}
	}else{
		return true;
	}
}

/** FUNCTION CHECKS FOR FIRST NAME WHEN FIELD IS OPTIONAL
 * NOTE: As first name is not a required field when checked
 * by this function, an input value with a length of zero
 * is considered valid input.
 * 
 * @param {String} inFname
 * @returns {Boolean} true (on successful validation)
 * @returns {String} errorString (on failed validation)
 */
function chkFnameOptional(inFname){	
	var fnameLen = inFname.length;
	
	if(fnameLen > 0){
		if(fnameLen >= 2 && fnameLen < 30){
			return true;
		}else{
			return "First Name must be between 2 and 30 characters long";
		}
	}else if(fnameLen == 0){
		return true;
	}else{
		return "First Name validation failed.";
	}
}

function chkLname(inLname){
	var inLname = inLname.trim();
	
	if(2 > inLname.length || inLname.length > 30){
		if(0 >= inLname.length){
			return "Last Name is required";
		}else{
			return "Last Name must be between 2 and 30 characters long";
		}
	}else{
		return true;
	}
}

/** FUNCTION CHECKS FOR LAST NAME WHEN FIELD IS OPTIONAL
 * NOTE: As last name is not a required field when checked
 * by this function, an input value with a length of zero
 * is considered valid input.
 * 
 * @param {String} inLname
 * @returns {Boolean} true (on successful validation)
 * @returns {String} errorString (on failed validation)
 */
function chkLnameOptional(inLname){
	var lnameLen = inLname.length;
	
	if(lnameLen > 0){
		if(lnameLen >= 2 && lnameLen < 30){
			return true;
		}else{
			return "First Name must be between 2 and 30 characters long";
		}
	}else if(lnameLen == 0){
		return true;
	}else{
		return "First Name validation failed.";
	}
}


function chkMrn(inMrn){
	var errorString = "MRN must be between 0 and 30 characters long";
	if(0 > inMrn.length || inMrn.length > 30){
		return errorString;
	}else{
		return true;
	}
}

function chkGender(inGender){
	if(0 >= inGender.length){
		return "Gender is required";
	}else{
		return true;
	}
}

function chkEmail(inEmail){
	var emailRegEx = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	var isPass = emailRegEx.test(inEmail);
	
	if(0 >= inEmail.length){
		return "Email is required";
	}else{
		if(isPass !== true){	
			return "Please provide a valid email address";
		}else{
			return true;
		}
	}
}

function chkUname(inUname){
	if(3 > inUname.length || inUname.length > 30){
		if(0 >= inUname.length){
			return "Username is required";
		}else{
			return "Username must be between 3 and 30 characters long";
		}
	}else{
		return true;
	}
}

function chkPword(inPword, inUname){
	var strErr = "";
	
	var numRegEx = /.*\d.*/;
	var lcaseRegEx = /.*[a-z].*/;
	var ucaseRegEx = /.*[A-Z].*/;
	var pwordSpecCharRegEx = /.*[,~,!,@,#,$,%,^,&,*,(,),\-,_,=,+,[,{,\],},|,;,:,<,>,\/,?].*/;
	
	if(8 > inPword.length || inPword.length > 30){
		if(0 >= inPword.length){
			strErr = strErr + "Password is required";
		}else{
			strErr = strErr + "Password must be between 8 and 30 characters long";
		}
	}
	
	if(numRegEx.test(inPword) !== true){
		if(strErr.length > 0){
			strErr = strErr + "<br />";
		}
		strErr = strErr + "Password must contain at least one number";
	}
	
	if(lcaseRegEx.test(inPword) !== true){
		if(strErr.length > 0){
			strErr = strErr + "<br />";
		}
		strErr = strErr + "Password must contain at least one lowercase alphabetic character";
	}
	
	if(ucaseRegEx.test(inPword) !== true){
		if(strErr.length > 0){
			strErr = strErr + "<br />";
		}
		strErr = strErr + "Password must contain at least one uppercase alphabetic character";
	}
	
	if(pwordSpecCharRegEx.test(inPword) !== true){
		if(strErr.length > 0){
			strErr = strErr + "<br />";
		}
		strErr = strErr + "Password must contain at least one special character";
	}
	
	if(inPword == inUname){
		if(strErr.length > 0){
			strErr = strErr + "<br />";
		}
		strErr = strErr + "Password can not be username";
	}
	
	if(strErr.length > 0){
		return strErr;
	}else{
		return true;
	}
	
}

//TODO (MH): Fix function to return consistant data type
/** FUNCTION CHECKS FOR PHONE NUMBER
 * Uses regular expression to check for valid
 * phone number value and length.
 * 
 * NOTE: As phone number is not a required field, an input
 * value with a length of zero is considered valid input.
 * 
 * @param {String} inPhone
 * @returns {Boolean} true (on successful validation)
 * @returns {String} errorString (on failed validation)
 */
function chkPhone(inPhone){
	var phoneRegEx = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
	var errorString = "Please provide a valid phone number. For example 123-456-7890.";
	
	if(inPhone.length == 0){
		return true;
	}else{
		if(phoneRegEx.test(inPhone) !== true){
			return errorString;
		}else{
			return true;
		}
	}
}

//TODO (MH): Fix function to return consistant data type
/** FUNCTION CHECKS FOR VALID ZIP CODE
 * Uses regular expression to check for valid
 * zip code value and length. Accepts both 5 digit
 * zip codes & full 5+4 zip codes (5 digits plus
 * four digits with a hyphen after the first 5 digits).
 * 
 * NOTE: As zip code is not a required field, an input
 * value with a length of zero is considered valid input.
 * 
 * @param {String} inZip
 * @returns {Boolean} true (on successful validation)
 * @returns {String} errorString (on failed validation)
 */
function chkZip(inZip){
	var zipRegEx = /^\d{5}$|^\d{5}-\d{4}$/;
	var errorString = "Please provide a valid zip code";
	
	if(inZip.length == 0){
		return true;
	}else{
		if(zipRegEx.test(inZip) !== true){
			return errorString;
		}else{
			return true;
		}
	}
}


//TODO (MH): Fix function to return consistant data type
/** FUNCTION CHECKS FOR SSN
 * Uses regular expression to check for valid
 * SSN value and length.
 * 
 * NOTE: As SSN is not a required field, an input
 * value with a length of zero is considered valid input.
 * 
 * @param {String} inSSN
 * @returns {Boolean} true (on successful validation)
 * @returns {String} errorString (on failed validation)
 */
function chkSSN(inSSN){
	var ssnRegEx = /^\d{3}-?\d{2}-?\d{4}$/;
	var errorString = "Please provide a valid social security number. For example 123-45-6789.";
	
	if(inSSN.length == 9){
		return true;
	}else{
		if(ssnRegEx.test(inSSN) !== true){
			return errorString;
		}else{
			return true;
		}
	}
}

/** FUNCTION CHECKS FOR CITY
 * 
 * @param {String} inCity
 * @returns {Boolean} true (on successful validation)
 * @returns {String} errorString (on failed validation)
 */
function chkCity(inCity){
	var cityLen = inCity.length;
	
	if(cityLen == 0){
		return "City is required";
	}else if(cityLen < 2 || cityLen > 30){
		return "City must be between 2 and 30 characters long";
	}else if(cityLen >= 2 && cityLen <= 30){
		return true;
	}else{
		return "City validation failed";
	}
}

/** FUNCTION CHECKS FOR CITY WHEN FIELD OPTIONAL
 * 
 * @param {String} inCity
 * @returns {Boolean} true (on successful validation)
 * @returns {String} errorString (on failed validation)
 */
function chkCityOptional(inCity){
	var cityLen = inCity.length;
	
	if(cityLen == 0){
		return true;
	}else if(cityLen < 2 || cityLen > 30){
		return "City must be between 2 and 30 characters long";
	}else if(cityLen >= 2 && cityLen <= 30){
		return true;
	}else{
		return "City validation failed";
	}
}


/** FUNCTION CHECKS FOR VALID STATE ABBREVIATION
 * 
 * @param {String} inState
 * @returns {Boolean}
 */
function chkState(inState){
	var stateLen = inState.length;
	
	if(stateLen == 2){
		return true;
	}else{
		return false;
	}
}


/** FUNCTION CHECKS FOR VALID STATE ABBREVIATION WHEN FIELD OPTIONAL
 * 
 * @param {String} inState
 * @returns {Boolean}
 */
function chkStateOptional(inState){
	var stateLen = inState.length;
	
	if(stateLen == 2){
		return true;
	}else if(stateLen == 0){
		return true;
	}else{
		return false;
	}
}


/** FUNCTION CHECKS FOR FACILITY NAME:
 * 
 * NOTE: As Facility Name is not a required field when this
 * function is called, an input value with a length of zero
 * is considered valid input.
 * 
 * @param {String} inFacilityName
 * @returns {Boolean} true (on successful validation)
 * @returns {String} errorString (on failed validation)
 */
function chkFacilityName(inFacilityName){
	var facilityLen = inFacilityName.length;
	
	if(facilityLen > 0){
		if(facilityLen < 2 || facilityLen > 30){
			return "City must be between 2 and 30 characters long";
		}else{
			return true;
		}
	}else if(facilityLen == 0){
		return true;
	}else{
		return "City validation failed.";
	}
}


/** FUNCTION CHECKS FOR ONLY FACILITY NAME OR LAST NAME:
 * 
 * Checks to make sure that either last name or facility
 * name is specifed, but not both and not neither.
 * 
 * @param {String} inFacilityName
 * @param {String} inLname
 * @returns {Boolean} (true on successful validation, false on failure)
 */
function chkFacilityNameOrLastName(inFacilityName, inLname){
	var facilityLen = inFacilityName.length;
	var lnameLen = inLname.length;
	
	if(facilityLen == 0 && lnameLen == 0){
		return false;
	}else if(facilityLen > 0 && lnameLen > 0){
		return false;
	}else if(facilityLen == 0 && lnameLen > 0){
		return true;
	}else if(facilityLen > 0 && lnameLen == 0){
		return true;
	}else{
		return false;
	}
}

/** FUNCTION CHECKS FOR ONLY STATE AND CITY, OR ONLY LAST NAME:
 * 
 * Checks to make sure that either state and city are
 * specifed or zip is specified, but not both and not neither.
 * 
 * @param {String} inState
 * @param {String} inCity
 * @param {String} inZip
 * @returns {Boolean} (true on successful validation, false on failure)
 */
function chkStateAndCityOrZip(inState, inCity, inZip){
	var stateLen = inState.length;
	var cityLen = inCity.length;
	
	var zipLen = inZip.length;
	
	if(stateLen > 0){
		if(cityLen > 0){
			if(zipLen == 0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}else{
		if(cityLen == 0){
			if(zipLen > 0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
}

/**
 * FUNCTION: CASE SENSITIVE COMPARE TWO STRINGs BASED ON CURRENT LOCAL
 * 
 * @param {String} str1
 * 					The first string to compare.
 * @param {String} str2
 * 					The second string to compare.
 * 
 * @returns {Boolean} (true if the strings are the same, error message otherwise)
 */

function compareStrings(str1, str2){
	var errorStr = "Please email addresses should be the same.";
	if((str1 && (typeof str1 !== "undefined") && (str1.length >0) ) && (str2 && (typeof str2 !== "undefined") && (str2.length >0))){
		return (str1.localeCompare(str2) === 0 )? true: errorStr;
	}else{
		return "";
	}
}

function nullOrUndefinedOrEmpty(value){
	return (value === null || (typeof value === "undefined") || (value.length === 0) );
}

function addZero(n){
    return n < 10 ? '0' + n : '' + n;
 }

//Formats d to MM/dd/yyyy format
function formatDate(d){
	  return addZero(d.getMonth()+1)+"/"+ addZero(d.getDate()) + "/" + d.getFullYear();
}

function isValidDate(dateStr) {
	var result = isDate(dateStr);
	if((typeof result ) === 'boolean')
	{
		return result;
	}else{
		return false;
	}
} 

function compareDate(startDate, endDate){
	return (new Date(startDate)> new Date(endDate));
}



/**
 * Determines if a checkbox is checked
 * 
 * @param {String} elementId - element ID
 * @returns {Boolean}  true if it is checked and false otherwise
 */
function isChecked(elementId){
	return $(elementId).is(':checked');
}


/**
 * Show error message if invalid and hide it otherwise
 * 
 * @param {String} fieldId - element id
 * @param {Boolean} isFieldValid - true if it contains error
 * @param {String} errorMsg - the error message to be display
 */
function toggleErrorMessage(fieldId,isFieldValid ,errorMsg ){
	if(isFieldValid){
		//$(fieldId).text("");
		$(fieldId).attr('style', "display: none;");
	}else{
		$(fieldId).html(errorMsg);
		$(fieldId).attr('style', "");
	}
}


/**
 * Disables the datepicker
 * 
 * @param {String} datePickerId - datepicker id
 */
function disableField(datePickerId){
	$(datePickerId).prop('disabled', true);
}


/**
 * Enables the datepicker
 * 
 * @param {String} datePickerId - datepicker id
 */
function enableField(datePickerId){
	$(datePickerId).prop('disabled', false);
}


/**
 * Resets the datepicker
 * 
 * @param {String} datePickerId - datepicker id
 */
function resetDatePicker(datePickerId){
	$(datePickerId).val("");
}


/**
 * Resets select
 * 
 * @param {String} selectId - select id
 */
function resetSelect(selectId){
	//Setting the default select
	var selectOptionId = selectId + " option:first";	
	$(selectId).val($(selectOptionId).val());
}


/**
 * Toggles checkbox 
 * 
 * @param {String} fieldId - element id
 * @param {boolean} isChecked - determines if the element should be checked
 */
function toggleIcheckBox(fieldId, isChecked){
	if(isChecked && ! $(fieldId).attr('checked')){
		$(fieldId).iCheck('check');
	}else if(!isChecked &&  $(fieldId).attr('checked')){
		$(fieldId).iCheck('uncheck');
	}
}



