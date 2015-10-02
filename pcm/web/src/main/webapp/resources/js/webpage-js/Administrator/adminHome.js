//Code to execute on document.ready event for Administrator/adminHome.html page
$(document).ready(function(){
	initDOBYearField();
	setDOBfromTxt();
	$('#ssn1, #ssn2, #ssn3').autotab_magic().autotab_filter('numeric');
	
	$( "#searchTextBox" )
    .autocomplete({source: function( request, response ) {
        $.getJSON( "patientlookup/query", {
          token:request.term
        }, function (data) {
        	var dateRegEx='((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3}))[-:\\/.](?:[0]?[1-9]|[1][012])[-:\\/.](?:(?:[0-2]?\\d{1})|(?:[3][01]{1})))(?![\\d])';
        	var p = new RegExp(dateRegEx,["i"]);
               response($.map(data, function (item) {
            	   var dob=p.exec(item.birthDay);
                   return {
                	   id:item.id,
                       label: item.firstName
                       		+" "+item.lastName
                       		+", "+dob[0]
                       		+", ***-**-"+item.socialSecurityNumber,
                       value:	item.firstName+" "+item.lastName,
                   };
                  
               }));
           } );
      },
      search: function() {
        var term = this.value;
        if ( term.length < 2 ) {
          return false;
        }
      },
      
      select: function( event, ui ) {
      	document.location.href="adminPatientView.html?id="+ui.item.id;
      }
    });
	
	//Bind hidden.bs.modal event handler to clear create patient account modal fields & error messages
	$('#createPatient').on('hidden.bs.modal', function(){
		clearCreatePatientAccountModal();
	});
	
	$("form#admin_create_patient_acct_form").submit(function(evt){
		var isFormValid = false;
		
		//Disable create account button
		$("form#admin_create_patient_acct_form button#adminCreatePatientAccountFormSubmitButton").prop("disabled", true);
		
		isFormValid = validateAdminCreatePatientAccount('month', 'day', 'year', 'date');
		
		if(isFormValid === true){
			//On validation success, return true to proceed with form submission
			return true;
		}else{
			//On validation failure, prevent form submission
			evt.preventDefault();
			
			//Re-enable create account button
			$("form#admin_create_patient_acct_form button#adminCreatePatientAccountFormSubmitButton").prop("disabled", false);
			
			return false;
		}
	});
	
});

/**
 * Clear create patient account modal input field values & error messages
 */
function clearCreatePatientAccountModal(){
	
	/*	Issue #490 Fix Start
	 The function missed email and SSN 
	 Also extract the function as named clearCreatePatientAccountModalCommon(information1, errormessage) 
	$('input#first_name').val("");
	$('#fname_client_error_text').text("");
	$('#fname_client_error_text').attr('style', "display: none;");
	
	$('input#last_name').val("");
	$('#lname_client_error_text').text("");
	$('#lname_client_error_text').attr('style', "display: none;");
	
	$('select#gender').val("");
	$('#gender_client_error_text').text("");
	$('#gender_client_error_text').attr('style', "display: none;");
	
	$('input#date').val("");
	$('select#month').val("  ");
	$('select#day').val("  ");
	$('select#year').val("    ");
	$('#dob_client_error_text').text("");
	$('#dob_client_error_text').attr('style', "display: none;");
	*/
	
	
	clearCreatePatientAccountModalCommon(first_name,fname_client_error_text);
	clearCreatePatientAccountModalCommon(last_name,lname_client_error_text);
	clearCreatePatientAccountModalCommon(gender,gender_client_error_text);
	
	$('input#date').val("");
	$('select#month').val("  ");
	$('select#day').val("  ");
	$('select#year').val("    ");
	$('#dob_client_error_text').text("");
	$('#dob_client_error_text').attr('style', "display: none;");
	
	
	clearCreatePatientAccountModalCommon(ssn1,ssn_client_error_text);
	clearCreatePatientAccountModalCommon(ssn2,ssn_client_error_text);
	clearCreatePatientAccountModalCommon(ssn3,ssn_client_error_text);
	clearCreatePatientAccountModalCommon(email,email_client_error_text);

	
	
}


function clearCreatePatientAccountModalCommon(information1, errormessage){
	
	
	$(information1).val("");
	$(errormessage).text("");
	$(errormessage).attr('style', "display: none;");
}

//Issue #490 Fix End

$( ".ssn" ).change(function() {
	var ssnStr = $('#ssn1').val() + $('#ssn2').val() + $('#ssn3').val() ;
	$('#socialsecurity').attr('value', ssnStr);
});
