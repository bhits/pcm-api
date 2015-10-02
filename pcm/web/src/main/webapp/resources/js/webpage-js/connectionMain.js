$(document).ready(function(){
	$('.prov-phone-input').each(function(){
		var provNPI = $(this).data('npi');
		var rawphonenum = $(this).val();
		var formatphonenum = phoneNumberParser(rawphonenum);

		$('span#prov-phone-display-NPI' + provNPI).text(formatphonenum);
	});

	$('.prov-address-input').each(function(){
		var provNPI = $(this).data('npi');
		var streetaddress = $(this).data('streetaddress');
		var city = $(this).data('city');
		var state = $(this).data('state');
		//Zip is retrieved via .attr instead of .data to overcome a type conversion issue when zip starts with 0
		var zip = $(this).attr('data-zip');

		var fulladdress = addressParserZip5(streetaddress, city, state, zip);

		$('span#prov-address-display-NPI' + provNPI).text(fulladdress);
	});


	$('[data-toggle=popover]').popover();

	$('html').on('mouseup', function(e) {
		if((!$(e.target).closest('.popover').length)) {
			if((!$(e.target).closest('[data-toggle=popover]').length)){
				$('.popover').each(function(){
					$(this.previousSibling).popover('hide');
				});
			}
		}
	});

	$('.datepicker').datepicker();

	$("button[id*=edit_legal_submit]").bind('click', function() {
		var id = $(this).attr("id").split('_')[3];
		var pageUrl = window.location.pathname;
		pageUrl = pageUrl.replace(".html", "")+'/editLegalRepresenttive/'+id;

		$("span[id*=_edit_error]").text("");

		var formData = {	"firstName" : $("#firstName_edit_field-" + id).val() ,
				"lastName" : $("#lastName_edit_field-" + id).val() ,
				"administrativeGenderCode" : $("#administrativeGenderCode_edit_field-" + id).val() ,
				"birthDate" : $("#birthDate_edit_field-" + id).val() , 
				"email" : $("#email_edit_field-" + id).val() ,
				"telephoneTelephone" : $("#telephoneTelephone_edit_field-" + id).val() ,
				"addressStreetAddressLine" : $("#addressStreetAddressLine_edit_field-" + id).val() ,
				"addressCity" : $("#addressCity_edit_field-" + id).val() ,
				"addressStateCode" : $("#addressStateCode_edit_field-" + id).val() ,
				"addressPostalCode" : $("#addressPostalCode_edit_field-" + id).val() ,
				"legalRepresentativeTypeCode" : $("#legalRepresentativeTypeCode_edit_field-" + id).val() ,
				"relationshipStartDate" : $("#relationshipStartDate_edit_field-" + id).val() ,
				"relationshipEndDate" : $("#relationshipEndDate_edit_field-" + id).val() ,
		};

		$.ajax({
			type: "POST",
			url: pageUrl, 
			data: formData, 
			success: function(response, status, xhr){ 
				var ct = xhr.getResponseHeader("content-type") || "";
				if (ct.indexOf('html') > -1) {
					document.location.reload(true);
				}
				if (ct.indexOf('json') > -1) {
					for(var property in response) {
						$("#" + property + "_edit_error-" + id).text(response[property]);
					}
					$("#edit-legal-form-" + id).find("p").remove();
					$("#edit-legal-form-" + id).prepend("<p>Please check the error fields below.</p>");
					$("p:contains('Please check the error fields below.')").addClass('alert');
					$("#edit-legal-form-" + id).animate({scrollTop:0},800);
				} 
			},
		});
	});
});

function validate_add_legal_form() {
	var pageUrl = window.location.pathname;

	$("span[id*=_add_error]").text("");

	var formData = {	"firstName" : $("#firstName_add_field").val() ,
			"lastName" : $("#lastName_add_field").val() ,
			"administrativeGenderCode" : $("#administrativeGenderCode_add_field").val() ,
			"birthDate" : $("#birthDate_add_field").val() , 
			"email" : $("#email_add_field").val() ,
			"telephoneTelephone" : $("#telephoneTelephone_add_field").val() ,
			"addressStreetAddressLine" : $("#addressStreetAddressLine_add_field").val() ,
			"addressCity" : $("#addressCity_add_field").val() ,
			"addressStateCode" : $("#addressStateCode_add_field").val() ,
			"addressPostalCode" : $("#addressPostalCode_add_field").val() ,
			"legalRepresentativeTypeCode" : $("#legalRepresentativeTypeCode_add_field").val() ,
			"relationshipStartDate" : $("#relationshipStartDate_add_field").val() ,
			"relationshipEndDate" : $("#relationshipEndDate_add_field").val() ,
	};

	$.ajax({
		type: "POST",
		url: pageUrl, 
		data: formData, 
		success: function(response, status, xhr){ 
			var ct = xhr.getResponseHeader("content-type") || "";
			if (ct.indexOf('html') > -1) {
				document.location.reload(true);
			}
			if (ct.indexOf('json') > -1) {
				for(var property in response) {
					$("#" + property + "_add_error").text(response[property]);
				}
				$("#add-legal-form").animate({scrollTop:0},800);
			} 
		},
	});
}
