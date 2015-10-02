/**
 *  providerSearchCriteriaValidation.js
 *  Created date: 09/18/2013
 *  Created by: Andy Lee
 */
 
function validProviderSearchForm()
{
	var isFormValid = true;
	
	var facilitynameStr = $('#facility_name').val();
	var lnameStr = $('#last_name').val();
	var fnameStr = $('#first_name').val();
	var cityStr = $('#city_name').val();
	var stateStr = $('#state_name').val();
	var phoneStr = $('#phone1').val() + $('#phone2').val() + $('#phone3').val();
	var zipStr = $('#zip_code').val();
	
	isFormValid = validCommonProviderSearch(facilitynameStr, lnameStr, fnameStr, cityStr, stateStr, phoneStr, zipStr);
	
	return isFormValid;
}


function validDashboardProviderSearchForm()
{
	var isFormValid = true;
	
	var facilitynameStr = $('#search_facility_name').val();
	var lnameStr = $('#search_last_name').val();
	var fnameStr = $('#search_first_name').val();
	var cityStr = $('#search_city_name').val();
	var stateStr = $('#search_state_name').val();
	var phoneStr = $('#search_phone1').val() + $('#search_phone2').val() + $('#search_phone3').val();
	var zipStr = $('#search_zip_code').val();
	
	isFormValid = validCommonProviderSearch(facilitynameStr, lnameStr, fnameStr, cityStr, stateStr, phoneStr, zipStr);
		
	return isFormValid;
}


/** VALIDATE PROVIDER SEARCH FORM DATA VALUES
 * 
 * @param {String} facilitynameStr
 * @param {String} lnameStr
 * @param {String} fnameStr
 * @param {String} cityStr
 * @param {String} stateStr
 * @param {String} phoneStr
 * @param {String} zipStr
 * @returns {Boolean} isFormValid
 */
function validCommonProviderSearch(facilitynameStr, lnameStr, fnameStr, cityStr, stateStr, phoneStr, zipStr){
	
	var isFormValid = true;
	
	clearSharedErrorMessage();
	
	var isValidState = chkStateOptional(stateStr);
	if(isValidState !== true){
		isFormValid = false;
		showStateError();
		showSharedErrorMessage();
	}else{
		clearStateError();
	}
	
	var isValidCity = chkCityOptional(cityStr);
	if(isValidCity !== true){
		isFormValid = false;
		showCityError();
		showSharedErrorMessage();
	}else{
		clearCityError();
	}
	
	var isValidZip = chkZip(zipStr);
	if( isValidZip !== true ){
		isFormValid = false;
		showZipError();
		showSharedErrorMessage();
	}else{
		clearZipError();
		
	}
	
	var isStateAndCityOrZip = chkStateAndCityOrZip(stateStr, cityStr, zipStr);
	if(isStateAndCityOrZip !== true){
		isFormValid = false;
		
		showStateAndCityOrZipErrorMessages();
		showSharedErrorMessage();
	}else{
		clearStateAndCityOrZipErrorMessages();
	}
	
	
	var isValidLname = chkLnameOptional(lnameStr);
	if(isValidLname !== true){
		isFormValid = false;
		showLastNameError();
	}else{
		clearLastNameError();
	}
	
	
	var isValidFname = chkFnameOptional(fnameStr);
	if(isValidFname !== true){
		isFormValid = false;
		showFirstNameError();
	}else{
		clearFirstNameError();
	}
	
	var isValidFacilityName = chkFacilityName(facilitynameStr);
	if(isValidFacilityName !== true){
		isFormValid = false;
		showFacilityNameError();
	}else{
		clearFacilityNameError();
	}

	var isValidPhone = chkPhone( phoneStr );
	if( isValidPhone !== true ){
		isFormValid = false;
		showPhoneError();
	}else{
		clearPhoneError();
	}

	var isFacilityNameOrLastName = chkFacilityNameOrLastName(facilitynameStr, lnameStr);
	if(isFacilityNameOrLastName !== true){
		isFormValid = false;
		showFacilityLastNameRequiredErrorMessages();
		showSharedErrorMessage();
	}else{
		clearFacilityLastNameRequiredErrorMessages();
	}
	
	$("#searchButton").attr("href", isFormValid == true ? "#provider_search_modal" : "" );
	
	return isFormValid;
}