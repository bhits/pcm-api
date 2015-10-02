$(document).ready(function () {

	$('#reportModal').on('hidden.bs.modal', function () {
    	resetReportModal();
	});
    
    $('.js-modal-reset').on('click', function () { 
    	$("#accountCreatedPeriodRadio").iCheck('check');
    	
	});
    
    $('input').iCheck({
	    checkboxClass: 'icheckbox_square-blue',
	    radioClass: 'iradio_square-blue',
	    increaseArea: '20%' // optional
    });
    
    //Initialise datepicker
    $('#date-picker-start').datepicker({});    
    $('#date-picker-end').datepicker({});
    
    $('#accountCreatedPeriodRadio').on('ifChecked', function(event){
    	disableReportDateRangeFields();
	});
    
    $('#accountCreatedRangeRadio').on('ifChecked', function(event){
    	disableReportDatePeriod();
	});
   
    // If user interact with the account create period dropdown, disable the date range
    $('#selectRange1Value').on('change', function(event){
    	toggleIcheckBox('#accountCreatedPeriodRadio', true);
    	disableReportDateRangeFields();
    });
    
    //Trigger after start date change
    $('#date-picker-start').on('changeDate', function(ev){
    	// If user interact with the date picker, disable the date period
    	toggleIcheckBox('#accountCreatedRangeRadio', true);
    	disableReportDatePeriod();
    	//hide date picker
       $(this).datepicker('hide');
       
       //If end date is empty then set focus to end date 
    	if(($("#date-picker-end").val()).length === 0){
    		$('#date-picker-end').focus();
    	}else{
    		//set focus to next element
    		$('#date-picker-start').focusout();
    	}
    });
    
    //Trigger after end date change
    $('#date-picker-end').on('changeDate', function(ev){
    	// If user interact with the date picker, disable the date period
    	toggleIcheckBox('#accountCreatedRangeRadio', true);
    	disableReportDatePeriod();
    	//hide date picker
        $(this).datepicker('hide');
        
        //If start date is empty set focus to end date
    	if(($("#date-picker-start").val()).length === 0){
    		$('#date-picker-start').focus();
    	}else{
    		//set focus to next element
    		$('#date-picker-end').focusout();
    	}
    });
    
    
    $("#generateReportBtn").on('click',function(evt){
    	var isFormValid = false;
    	var reportFormat = $("#managerReportFormatSelect").val();
    	var accountCreatedPeriod = $('#selectRange1Value').val();
    	var accountCreatedStartDate = $("#date-picker-start").val();
    	var accountCreatedEndDate = $("#date-picker-end").val();
    	
    	var isFormValid = validateReport(isChecked("#accountCreatedPeriodRadio"),isChecked("#accountCreatedRangeRadio"), accountCreatedPeriod, accountCreatedStartDate, accountCreatedEndDate, reportFormat);
    	
    	if(isFormValid === true){
    		$('#reportModal').modal('hide');
    		//On validation success, return true to proceed with form submission
    		var url = composeReportUrl(isChecked("#accountCreatedPeriodRadio"),isChecked("#accountCreatedRangeRadio"),accountCreatedPeriod, accountCreatedStartDate, accountCreatedEndDate, isChecked("#noCatagoryDescription"), isChecked("#noAccountInformation"), reportFormat) ;			
    		var win = window.open(url);
    	}else{
    		evt.preventDefault();
    		return false;
    	}
    });
    
});

/*
 * Resets modal, selecting the date period 
 */
function resetReportModal(){
	
	toggleErrorMessage("#report_filter_criteria", true, "");
	
	$('#account_created_period_error_text').attr('style', "display: none;");
	$('#account_created_range_error_text').attr('style', "display: none;");
	$('#report_format_error_text').attr('style', "display: none;");
	
	//Unchecking the account created radio button period
	toggleIcheckBox('#accountCreatedPeriodRadio', false);
	
	//Setting the default select
	resetSelect("#selectRange1Value");
	disableField("#selectRange1Value");
	
	//Unchecking the account created radio button range
	toggleIcheckBox('#accountCreatedRangeRadio', false);
	
	//Reset Datepicker
	resetDatePicker("#date-picker-start");
	resetDatePicker("#date-picker-end");
	disableField("#date-picker-start");
	disableField("#date-picker-end");
	
	//Unchecking the no account information and no category description
	toggleIcheckBox('#noAccountInformation', false);
	toggleIcheckBox('#noCatagoryDescription', false);

	//Setting the default select
	resetSelect("#managerReportFormatSelect");
}

/*
 * Composes the report URL
 * 
 *  @param {Boolean} isAccountCreatedPeriod - determines if account created period radio button is checked 
 *  @param {Boolean} isAccountCreatedRange - determines if account created range radio button is checked
 *  @param {Boolean} accountCreatedPeriod - holds the account created period select value
 *  @param {Boolean} accountCreatedStartDate - Account created start date
 *  @param {Boolean} accountCreatedEndDate - Account created end date
 *  @param {Boolean} isNoCatagoryDescription - determines if category description is checked
 *  @param {Boolean} isNoAccountInformation - determines if account information is checked
 *  @param {String} reportFormat -  report format
 *  
 *  @return {String} url - report url
 * 
 */
function composeReportUrl(isAccountCreatedPeriod, isAccountCreatedRange ,accountCreatedPeriod, accountCreatedStartDate, accountCreatedEndDate, isNoCatagoryDescription, isNoAccountInformation, reportFormat){
	var url;
	var startDate;
	var endDate;
	
	if(isAccountCreatedPeriod){
		var day = new Date();
		day.setDate(day.getDate()-parseInt(accountCreatedPeriod));
		endDate = formatDate(new Date());
		startDate = formatDate(day);
	}else if(isAccountCreatedRange){
		startDate = accountCreatedStartDate;
		endDate = accountCreatedEndDate;
	}
	
	if(startDate !== null && endDate !== null){
		url =  'reports/managerReport?format=' + reportFormat + "&startDate=" + startDate+ "&endDate=" + endDate + "&noCatagoryDescription=" + isNoCatagoryDescription + "&noAccountInformation=" + isNoAccountInformation;
	}	
	
	return url;
}

/*
 * Validates report form
 * 
 *  
 * @param {Boolean} isAccountCreatedPeriod - determines if account created period radio button is checked 
 * @param {Boolean} isAccountCreatedRange - determines if account created range radio button is checked
 * @param {Boolean} accountCreatedPeriod - holds the account created period select value
 * @param {Boolean} accountCreatedStartDate - Account created start date
 * @param {Boolean} accountCreatedEndDate - Account created end date
 * @param {String} format -  report format
 * 
 * 
 */
function validateReport(isAccountCreatedPeriod, isAccountCreatedRange, accountCreatedPeriod, accountCreatedStartDate, accountCreatedEndDate, format){
	var isValidReportPeriod = false;
	var isValidFormat = false;
	
		
	if(isAccountCreatedPeriod){
		if(nullOrUndefinedOrEmpty(accountCreatedPeriod) ){
			isValidReportPeriod = false;
			toggleErrorMessage("#account_created_period_error_text", isValidReportPeriod, "Account created period is required.");
		}else{
			isValidReportPeriod = true;
			toggleErrorMessage("#account_created_period_error_text", isValidReportPeriod, "");
		}
	}else if( isAccountCreatedRange ){
		if(nullOrUndefinedOrEmpty(accountCreatedStartDate) || nullOrUndefinedOrEmpty(accountCreatedEndDate) || !isValidDate(accountCreatedStartDate) || !isValidDate(accountCreatedEndDate)){
			isValidReportPeriod = false;
			toggleErrorMessage("#account_created_range_error_text", isValidReportPeriod, "You must provide the date range.");
		}else{
			
			if(compareDate(accountCreatedStartDate, accountCreatedEndDate)){
				isValidReportPeriod = false;
				toggleErrorMessage("#account_created_range_error_text", isValidReportPeriod, "Start date must occur before the end date.");
			}else{
				isValidReportPeriod = true;
				toggleErrorMessage("#account_created_range_error_text", isValidReportPeriod, "");
			}
		}
	}		
		
	if(nullOrUndefinedOrEmpty(format) ){
		isValidFormat = false;
		toggleErrorMessage("#report_format_error_text", isValidFormat, "You must select the format type of the output.");
	}else{
		isValidFormat = true;
		toggleErrorMessage("#report_format_error_text", isValidFormat, "");
	}
	
	return (isValidReportPeriod && isValidFormat);
}


/**
 * Disable the date range field
 */
function disableReportDateRangeFields(){
	toggleErrorMessage("#account_created_range_error_text", true, "");
	
	enableField("#selectRange1Value");
	
	//Reset datepicker
	resetDatePicker("#date-picker-start");
	resetDatePicker("#date-picker-end");
	
	//Disable datepicker
	disableField("#date-picker-start");
	disableField("#date-picker-end");
}


/**
 * Disable the report date period
 * 
 */
function disableReportDatePeriod(){
	toggleErrorMessage("#account_created_period_error_text", true, "");
	
	disableField("#selectRange1Value");
	
	//Clear create account period
	resetSelect("#selectRange1Value");
	
	//Reset datePicker
	enableField("#date-picker-start");
	enableField("#date-picker-end");
}


