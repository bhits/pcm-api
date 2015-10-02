//Code to execute on document.ready event for changePassword.html & createPassword.html pages
$(document).ready(function() {
	$('#new-password').pstrength();
	
	// Submit event handler for frm_new_password form
	$("form#frm_new_password").submit(function(evt){
		//Disable save button
		$("form#frm_new_password button#frmNewPasswordSubmitButton").prop("disabled", true);
		
		//FIXME: Implement JavaScript based client-side validation
		
		//Proceed with form submission process
		return true;
	});
	
});
