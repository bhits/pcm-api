/**
 * document.ready function for secureClinicalDocuments.html page
 */

$(document).ready(function(){
	
	// Submit event handler for documentUploadForm form
	$("form#documentUploadForm").submit(function(evt){
		//Disable submit ("Upload") button
		$("form#documentUploadForm button#documentUploadFormSubmitButton").prop("disabled", true);
		
		//FIXME (MH): Remove "required" attribute from HTML form input elements and implement JavaScript based client-side validation
		
		//Proceed with form submission process
		return true;
	});
});
