//Code to execute on document.ready event for connectionProviderAdd.html page
$(document).ready(function(){
	
	$("div#consent_session_expired_modal.modal").on("show.bs.modal", function(){
		$("div.modal").modal("hide");
	});
	
	$('#phone1, #phone2, #phone3').autotab_magic().autotab_filter('numeric');
	cityEnableDisable();
	zipEnableDisable();

	/* Builds list of providers already added so that results in the search results
	 * modal can be disabled if that provider has already been added. */
	$('.npi-list-input').each(function(){
		var in_NPI = $(this).val();
		npiLists.push(in_NPI);
	});


	//Binds an event handler to the change event for the state_name element
	$('#state_name').change(function(e){
		e.stopPropagation();

		cityEnableDisable();
		zipEnableDisable();
	});            

	//Binds an event handler to the change event for the zip_code element
	$('#zip_code').on("propertychange keyup input", function(e){
		e.stopPropagation();

		city_stateEnableDisable();
	});
	
	$('div#org_prov_search_group').on("propertychange keyup input", "input.form-control", function(e){
		e.stopPropagation();

		lnameEnableDisable();
	});
	
	
	$('div#indv_prov_search_group').on("propertychange keyup input", "input.form-control", function(e){
		e.stopPropagation();

		facilitynameEnableDisable();
	});
	
	$('div#indv_prov_search_group').on("change", "select.form-control", function(e){
		e.stopPropagation();

		facilitynameEnableDisable();
	});
	
	//Binds an event handler to clear search by location panel
	$('button#btn_provSearchClearLocation').click(function(e){
		clearLocation();
	});
	
	//Binds an event handler to clear search by location panel
	$('button#btn_provSearchClearNameAndOthers').click(function(e){
		clearNameAndOthers();
	});
	
});
