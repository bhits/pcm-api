var result4ajaxJSON;

npiLists=new Array();

var providerSearchRedirectString = "../consents/addConsent.html";

//Control the triangle in the expandable tags
$(function(){
	
	//Append csrf token to ajax call
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
	
	$("#expand1").click(function(){
		
	if ($("#expandTriangle1").text()=="\u25BC")
		$("#expandTriangle1").text("\u25B6");
		
	else
		$("#expandTriangle1").text("\u25BC");
		$("#expandTriangle2").text("\u25B6");
		
});
	
	$("#expand2").click(function(){
		
		if ($("#expandTriangle2").text()=="\u25BC")
			$("#expandTriangle2").text("\u25B6");
		else
			$("#expandTriangle2").text("\u25BC");
			$("#expandTriangle1").text("\u25B6");
	});
});

/*Fix issue #493 Start
This is only a problem found in Internet Explorer browser. 
After it was fixed, user could press "Space" Key to select.*/

function radioOnClick() { 
	var SPACE_KEY = 32;
	if (event.keyCode == SPACE_KEY) { 
	var button = document.getElementById("saveauthorizer"); //
	button.click(); 
	} 
} 

function butOnClick() { 
	var SPACE_KEY = 32;
	if (event.keyCode == SPACE_KEY) { 
	var button = document.getElementById("saveconsentmadeto"); //
	button.click(); 
	} 
} 
/*Fix issue #493 End*/

$(function(){
	$("#provider_search_modal #resultList").on("click", "button.addProviderButton", function(evt){
		var clickedButton = $(this);
		
		if($(clickedButton).hasClass("addIndividualProviderButton")){
			//Disable button to prevent multiple clicks
			$(clickedButton).prop("disabled", true);
			
			var entryId=$(clickedButton).attr("id").substr(27,$(clickedButton).attr("id").length-27);
			var npiToAdd=result4ajaxJSON["providers"][entryId]["npi"];
			
			var in_patientId = $('input#patientId').val();
			var in_patientUsername = $('input#patientUsername').val();
			
			$.ajax({
				  url: "../patients/connectionProviderAdd_AJAX.html",
				  type: "POST",
				  data: {npi: npiToAdd,
					  	 patientId: in_patientId,
					  	 patientusername: in_patientUsername},
				  success:function() {
					  $('input.isMadeToList').iCheck('destroy');
					  $('input.toDiscloseList').iCheck('destroy');
					  
					  $('div#disclose-list-container').append("<div>" +
					  			"<label for='to" + result4ajaxJSON["providers"][entryId].npi + "'>" +
					  					"<input class='isMadeToList' id='to" + result4ajaxJSON["providers"][entryId].npi + "' " +
					  						"value='" + result4ajaxJSON["providers"][entryId].npi + "' " +
					  						"type='checkbox' name='providersDisclosureIsMadeTo' style='float: none;' />" +
					  					"<input type='hidden' name='_providersDisclosureIsMadeTo' value='on' />" +
					  					"<span>" + result4ajaxJSON["providers"][entryId].providerLastName + ", " +
					  						result4ajaxJSON["providers"][entryId].providerFirstName + "</span>" +
					  			"</label></div>");
					  
					  $('div#authorize-list-container').append("<div>" +
							  	"<label for='from" + result4ajaxJSON["providers"][entryId].npi + "'>" +
			  						"<input class='toDiscloseList' id='from" + result4ajaxJSON["providers"][entryId].npi + "' " +
			  							"value='" + result4ajaxJSON["providers"][entryId].npi + "' " +
			  							"type='checkbox' name='providersPermittedToDisclose' style='float: none;' />" +
			  							"<input type='hidden' name='_providersPermittedToDisclose' value='on' />" +
			  							"<span>" + result4ajaxJSON["providers"][entryId].providerLastName + ", " +
			  								result4ajaxJSON["providers"][entryId].providerFirstName + "</span>" +
			  					"</label></div>");
					  
					  $('input.isMadeToList').iCheck({
						    checkboxClass: 'icheckbox_square-blue',
						    radioClass: 'iradio_square-blue',
						    increaseArea: '20%',
					  });
					  
					  $('input.toDiscloseList').iCheck({
						    checkboxClass: 'icheckbox_square-blue',
						    radioClass: 'iradio_square-blue',
						    increaseArea: '20%',
					  });
					  
					  loadAllProviders();
					  
					  $('#provider_search_modal').modal('hide');
					  $('#disclose-modal').modal();
				  },
				  error: function(e){
					  //Re-enable button
					  $(clickedButton).prop("disabled", false);
					  
					  window.alert("ERROR: " + e.responseText);
				  }
				});
		}else if($(clickedButton).hasClass("addOrganizationalProviderButton")){
			//Disable button to prevent multiple clicks
			$(clickedButton).prop("disabled", true);
			
			var entryId=$(clickedButton).attr("id").substr(31,$(clickedButton).attr("id").length-31);
			var npiToAdd=result4ajaxJSON["providers"][entryId]["npi"];
			
			var in_patientId = $('input#patientId').val();
			var in_patientUsername = $('input#patientUsername').val();
			
			$.ajax({
				  url: "../patients/connectionProviderAdd_AJAX.html",
				  type: "POST",
				  data: {npi: npiToAdd,
					  	 patientId: in_patientId,
					  	 patientusername: in_patientUsername},
				  success:function() {
					  $('input.isMadeToList').iCheck('destroy');
					  $('input.toDiscloseList').iCheck('destroy');
					  
					  $('div#disclose-list-container').append("<div>" +
					  			"<label for='to" + result4ajaxJSON["providers"][entryId].npi + "'>" +
					  					"<input class='isMadeToList' id='to" + result4ajaxJSON["providers"][entryId].npi + "' " +
					  						"value='" + result4ajaxJSON["providers"][entryId].npi + "' " +
					  						"type='checkbox' name='organizationalProvidersDisclosureIsMadeTo' style='float: none;' />" +
					  					"<input type='hidden' name='_organizationalProvidersDisclosureIsMadeTo' value='on' />" +
					  					"<span>" + result4ajaxJSON["providers"][entryId].providerOrganizationName + "</span>" +
					  			"</label></div>");
					  
					  $('div#authorize-list-container').append("<div>" +
					  			"<label for='from" + result4ajaxJSON["providers"][entryId].npi + "'>" +
					  					"<input class='toDiscloseList' id='from" + result4ajaxJSON["providers"][entryId].npi + "' " +
					  						"value='" + result4ajaxJSON["providers"][entryId].npi + "' " +
					  						"type='checkbox' name='organizationalProvidersPermittedToDisclose' style='float: none;' />" +
					  					"<input type='hidden' name='_organizationalProvidersPermittedToDisclose' value='on' />" +
					  					"<span>" + result4ajaxJSON["providers"][entryId].providerOrganizationName + "</span>" +
					  			"</label></div>");
					  
					  $('input.isMadeToList').iCheck({
						    checkboxClass: 'icheckbox_square-blue',
						    radioClass: 'iradio_square-blue',
						    increaseArea: '20%',
					  });
					  
					  $('input.toDiscloseList').iCheck({
						    checkboxClass: 'icheckbox_square-blue',
						    radioClass: 'iradio_square-blue',
						    increaseArea: '20%',
					  });
					  
					  loadAllProviders();
					  
					  $('#provider_search_modal').modal('hide');
					  $('#disclose-modal').modal();
				  },
				  error: function(e){
					  //Re-enable button
					  $(clickedButton).prop("disabled", false);
					  
					  window.alert("ERROR: " + e.responseText);
				  }
				});
		}
		
	});
	
});


jQuery.fn.buildPagingBar = function( arrHtmlStr, items_per_page, func2showPage, totalNumberOfProviders, totalPages  )
{
	//window.alert( 'items_per_page = '+ items_per_page );
	var lnkCnt4most = 8 ;
	var lnkCnt4short = 2 ;
	var currentPage = 0 ;
	var txt_prev = "Prev" ;
	var txt_next = "Next" ;
	var ellipsis = "<span>......</span>" ;
	
	return this.each( function() 
	{
		var pageLinksBar = jQuery(this);	
		
		function pageLinkClicked( pageNo, evt ) // event handler for the pagination links
		{
			currentPage = pageNo;
			buildPagingLinks();
			clickedPagelookup();
			func2showPage( arrHtmlStr, pageNo, items_per_page );
		}
		
		function buildPagingLinks() {	
			pageLinksBar.empty();
			//var pageCnt = Math.ceil( arrHtmlStr.length / items_per_page );
			var pageCnt = totalPages;
			var halfLinkCnt = Math.ceil(lnkCnt4most/2);
			var startPageLink = currentPage > halfLinkCnt ? Math.max( Math.min( currentPage-halfLinkCnt, pageCnt-lnkCnt4most), 0) : 0 ;
			var endPageLink = currentPage > halfLinkCnt ? Math.min(currentPage+halfLinkCnt, pageCnt) : Math.min( lnkCnt4most, pageCnt );
			
			var pgClickHandler = function( pageNo ) {	return function( evt ) { return pageLinkClicked( pageNo, evt ); };	};
			var addPageLink = function( pageNo, appendopts )
			{
				pageNo = pageNo<0 ? 0 : (pageNo<pageCnt?pageNo:pageCnt-1) ; 
				appendopts = jQuery.extend( {text:pageNo+1, classes:""}, appendopts||{} );
				
				var lnk = ( pageNo == currentPage )
					? jQuery("<span class='currentpage'>"+(appendopts.text)+"</span>")
					: jQuery("<a>"+(appendopts.text)+"</a>").bind("click", pgClickHandler(pageNo) ).attr('href', "#".replace(/__id__/,pageNo)) ; 
				
				if( appendopts.classes)
					lnk.addClass( appendopts.classes );
					
				pageLinksBar.append( lnk );
			};
			
			if( txt_prev && (currentPage > 0 ) )				// add "Previous" link
				addPageLink( currentPage-1, {text:txt_prev, classes:"prev"} );
			
			if (startPageLink > 0 && lnkCnt4short > 0 )			// add starting link
			{
				var end = Math.min( lnkCnt4short, startPageLink );
				for(var i=0; i<end; i++) 
					addPageLink(i);
				
				if( lnkCnt4short < startPageLink )				// add ellipsis
					jQuery( ellipsis ).appendTo( pageLinksBar );
			}
			
			for( var i=startPageLink; i<endPageLink; i++) 		// add interval links
				addPageLink(i);
			
			if (endPageLink < pageCnt && lnkCnt4short > 0)		// add ending link
			{
				if( pageCnt-lnkCnt4short > endPageLink )		// add ellipsis
					jQuery( ellipsis ).appendTo( pageLinksBar );
				
				var begin = Math.max( pageCnt-lnkCnt4short, endPageLink );
				
				for(var i=begin; i<pageCnt; i++) 
					addPageLink(i);
			}
			
			if( txt_next && (currentPage < pageCnt-1 ))			// add "Next" link
				addPageLink( currentPage+1, {text:txt_next, classes:"next"} );
		}
		
		buildPagingLinks();
        func2showPage( arrHtmlStr, currentPage, items_per_page );
	});
};

function dashboardTriggerLookup(){
	$('#providerSearchSelect-modal').modal('hide');
	providerLookup();
}

function providerLookup(){
    var providerSearchForm="";
    var ajaxFinishedFlag=0;
    var totalNumberOfProviders = 0;
    var totalPages = 0;

    var arrProviderHtmStr = new Array();
	var items_per_page = 10;
	$("#Pagination").empty();
    
	$("#resultList").hide();
	$("#noResult").hide();
	$("#noResponse").hide();
	$("#resultList").empty();

	if ($("#search_city_name").val() != ""){
		providerSearchForm+="&city="+$("#search_city_name").val();
	}
	if ($("#search_state_name").val() != ""){
		providerSearchForm+="&usstate="+$("#search_state_name").val();
	}
	if ($("#search_zip_code").val() != ""){
		providerSearchForm+="&zipcode="+$("#search_zip_code").val();
	}
	if ($("#search_gender").val() != ""){
		var gender=null;
		if ($("#search_gender").val()=="M")
			gender='MALE';
		if ($("#search_gender").val()=="F")
			gender="FEMALE";
		providerSearchForm+="&gender="+gender;
	}
	if ($("#search_phone1").val() != ""){
		providerSearchForm+="&phone="+$("#search_phone1").val()+$("#search_phone2").val()+$("#search_phone3").val();
	}
	if ($("#search_first_name").val() != ""){
		providerSearchForm+="&firstname="+$("#search_first_name").val();
	}
	
	if ($("#search_facility_name").val() != ""){
		providerSearchForm+="&facilityname="+$("#search_facility_name").val();
	}
	
	if ($("#search_last_name").val() != ""){
		providerSearchForm+="&lastname="+$("#search_last_name").val();
	}
	$("#provider_search_modal .search-loading").show();
	
	providerSearchForm+="&pageNumber=0";
	
	setTimeout( killAjaxCall, 10000); 
    
	var myAjaxCall= $.ajax({
    	dataType: "json",
		url: "../patients/providerSearch.html",
		type:"GET",
		async:true, 
		data: providerSearchForm,
		success: function (queryResult) { 
			ajaxFinishedFlag++;
			if(queryResult==null) 
			{
				showResult( queryResult, items_per_page, totalNumberOfProviders, totalPages  );
				return;
			}
			
			result4ajaxJSON=queryResult;
			
			totalNumberOfProviders = queryResult["totalNumberOfProviders"];
			totalPages = queryResult["totalPages"];
			
			for (var i=0;i<queryResult["providers"].length;i++) {
				addable="true";
				for(var j=0;j<npiLists.length;j++)
					{

					if(queryResult["providers"][i]["npi"]==npiLists[j])
						addable="false";
					}
				arrProviderHtmStr[i] = getResultRowHtmStr( i, queryResult["providers"][i], addable );
			}
			
			showResult( arrProviderHtmStr, items_per_page, totalNumberOfProviders, totalPages  );
		}
    
		});
	
	function killAjaxCall(){  // if no response, abort both getJson requests
	    if(ajaxFinishedFlag==0){
	    myAjaxCall.abort();
	    setTimeout( function() { $("#provider_search_modal .search-loading").fadeOut({ duration: 400}); }, 200 );
	    $("#noResponse").show();
	    }
	
}


}

function clickedPagelookup (){
    var providerSearchForm="";
    var ajaxFinishedFlag=0;
    var pageNumber = 0;
    var totalNumberOfProviders = 0;
    var totalPages = 0;

    var arrProviderHtmStr = new Array();
	var items_per_page = 10 ;
	//$("#Pagination").empty();
    
	$("#resultList").hide();
	$("#noResult").hide();
	$("#noResponse").hide();
	$("#resultList").empty();

	if ($("#search_city_name").val() != ""){
		providerSearchForm+="&city="+$("#search_city_name").val();
	}
	if ($("#search_state_name").val() != ""){
		providerSearchForm+="&usstate="+$("#search_state_name").val();
	}
	if ($("#search_zip_code").val() != ""){
		providerSearchForm+="&zipcode="+$("#search_zip_code").val();
	}
	if ($("#search_gender").val() != ""){
		var gender=null;
		if ($("#search_gender").val()=="M")
			gender='MALE';
		if ($("#search_gender").val()=="F")
			gender="FEMALE";
		providerSearchForm+="&gender="+gender;
	}
	if ($("#search_phone1").val() != ""){
		providerSearchForm+="&phone="+$("#search_phone1").val()+$("#search_phone2").val()+$("#search_phone3").val();
	}
	if ($("#search_first_name").val() != ""){
		providerSearchForm+="&firstname="+$("#search_first_name").val();
	}
	
	if ($("#search_facility_name").val() != ""){
		providerSearchForm+="&facilityname="+$("#search_facility_name").val();
	}
	
	if ($("#search_last_name").val() != ""){
		providerSearchForm+="&lastname="+$("#search_last_name").val();
	}
	
	$("#provider_search_modal .search-loading").show();
	
	pageNumber = $(".currentpage").text() - 1;
	
	providerSearchForm+="&pageNumber="+pageNumber;
	setTimeout( killAjaxCall, 10000); 

	var myAjaxCall= $.ajax({
    	dataType: "json",
		url: "../patients/providerSearch.html",
		type:"GET",
		async:true, 
		data: providerSearchForm,
		success: function (queryResult) { 
			ajaxFinishedFlag++;
			if(queryResult==null) 
			{
				showResultPaged( queryResult, items_per_page, totalNumberOfProviders, totalPages);
				return;
			}
			
			result4ajaxJSON=queryResult;
			
			totalNumberOfProviders = queryResult["totalNumberOfProviders"];
			totalPages = queryResult["totalPages"];
			
			
			for (var i=0;i<queryResult["providers"].length;i++) {
				addable="true";
				for(var j=0;j<npiLists.length;j++)
					{
					if(queryResult["providers"][i]["npi"]==npiLists[j])
						addable="false";
					}
				arrProviderHtmStr[i] = getResultRowHtmStr( i, queryResult["providers"][i], addable );
			}
			
			showResultPaged( arrProviderHtmStr, items_per_page, totalNumberOfProviders, totalPages );
		}
    
		});
	
	function killAjaxCall(){  // if no response, abort both getJson requests
	    if(ajaxFinishedFlag==0){
	    myAjaxCall.abort();
	    setTimeout( function() { $("#provider_search_modal .search-loading").fadeOut({ duration: 400}); }, 200 );
	    $("#noResponse").show();
	    }
	
}


}

function getResultRowHtmStr( i, rs, addable )
{
	var showOrg = ( rs["entityType"] == "Organization" );
	var ret = 
	"<div class='provider_record_space'><div class='provider_record_box'><div class='provider_record_header'><p class='result_row'><span class='result_field provider_name_field'>"+
	( showOrg== true  ? rs["providerOrganizationName"] : rs["providerLastName"] +", "+ rs["providerFirstName"] ) +
	'</span><span class="result_field">[NPI:' + rs["npi"] + ']</span></p>'+
	(
		addable=="true" ?	
			(
				showOrg== true  ?	
					'<p class="result_row add_button_space"><span class="result_field"><button class="addProviderButton addOrganizationalProviderButton btn btn-xs btn-success" id="addOrganizationalProviderButton'+i+'"><span class="fa fa-plus"></span></button></span> Add this provider.</p></div>'
				:
					'<p class="result_row add_button_space"><span class="result_field"><button class="addProviderButton addIndividualProviderButton btn btn-xs btn-success" id="addIndividualProviderButton'+i+'"><span class="fa fa-plus"></span></button></span> Add this provider.</p></div>'  
			)
		:
					'<p class="result_row add_button_space" style="color: black;"><span class="result_field"><button class="btn btn-xs" disabled="true"><span class="fa fa-plus"></span></button></span> Provider already added.</p></div>'  
	) +	
	'<p class="result_row"><span class="result_field provider_specialty_field">' + rs["healthcareProviderTaxonomy_1"] + '</span></p>' + 
	'<p class="result_row"><span class="result_field">' + rs["providerFirstLineBusinessPracticeLocationAddress"] + ', ' +
		( rs["providerSecondLineBusinessPracticeLocationAddress"]==""?( rs["providerSecondLineBusinessPracticeLocationAddress"]):"") + 
		rs["providerBusinessPracticeLocationAddressCityName"] + ", " +
		rs["providerBusinessPracticeLocationAddressStateName"] + ", " + 
		zipCodeParser( rs["providerBusinessPracticeLocationAddressPostalCode"]) +'</span></p>'+
	'<p class="result_row"><span class="result_field">' + phoneNumberParser( rs["providerBusinessPracticeLocationAddressTelephoneNumber"]) + "</span></p></div></div>" ;
	return ret ;
}

function showResult( arrHtmlStr, items_per_page , totalNumberOfProviders, totalPages)
{
	//if( arrHtmlStr ) window.alert( 'arrHtmlStr.length = '+ arrHtmlStr.length );
	setTimeout( function() { $("#provider_search_modal .search-loading").fadeOut({ duration: 400}); }, 200 );

    if( arrHtmlStr != null && arrHtmlStr.length > 0)
    {
		$("#Pagination").buildPagingBar( arrHtmlStr, items_per_page, showCurrentPage, totalNumberOfProviders, totalPages  ); 
		//window.alert( 'b4 Pagination.show/hide' );
		( totalNumberOfProviders > items_per_page ) ? $("#Pagination").show() : $("#Pagination").hide() ; 
    	$("#resultList").show();
	}
	else{
		$("#Pagination").hide(); 
		$("#noResult").show();
	}
}

function showResultPaged( arrHtmlStr, items_per_page , totalNumberOfProviders, totalPages)
{
	setTimeout( function() { $("#provider_search_modal .search-loading").fadeOut({ duration: 400}); }, 200 );

    if( arrHtmlStr != null && arrHtmlStr.length > 0)
    {
		( totalNumberOfProviders > items_per_page ) ? $("#Pagination").show() : $("#Pagination").hide() ; 
    	showCurrentPageWithPagination(arrHtmlStr, 3, items_per_page);
    	$("#Pagination").show();
    	$("#resultList").show();
	}
	else{
		$("#Pagination").hide(); 
		$("#noResult").show();
	}
}

function showCurrentPage( arrHtmlStr, page_index, items_per_page )
{
	//window.alert( 'page_index = '+ page_index );
    var max_elem = Math.min((page_index+1) * items_per_page, arrHtmlStr.length );
    var newcontent = '';
 
    for(var i=page_index*items_per_page; i<max_elem; i++)
        newcontent += ( arrHtmlStr[i] ) ;
 
    $('#resultList').html( newcontent );
}

function showCurrentPageWithPagination( arrHtmlStr, page_index, items_per_page )
{
    var max_elem = Math.min((page_index+1) * items_per_page, arrHtmlStr.length );
    var newcontent = '';
 
    for(var i=0; i<max_elem; i++)
        newcontent += ( arrHtmlStr[i] ) ;
    
    $('#resultList').html( newcontent );
}

	
function clearLocation(){
	//clear state_name value
	clearState();

	//clear city_name value
	clearCity();

	//clear zip_code value
	clearZip();
	
	clearSharedErrorMessage();
}

function clearNameAndOthers(){
	clearName();
	clearGender();
	clearPhone();
	
	clearSharedErrorMessage();
}

function clearName(){
	clearLastName();
	clearFirstName();
	clearFacilityName();
	
	clearSharedErrorMessage();
}

function clearFacilityLastNameRequiredErrorMessages(){
	$("#facilityname_facility_lname_client_error_text").css("display", "none");
	$("#lname_facility_lname_client_error_text").css("display", "none");
}

function showFacilityLastNameRequiredErrorMessages(){
	$("#facilityname_facility_lname_client_error_text").css("display", "");
	$("#lname_facility_lname_client_error_text").css("display", "");
}


function clearStateAndCityOrZipErrorMessages(){
	$("#search_state_state_city_zip_client_error_text").css("display", "none");
	$("#search_city_state_city_zip_client_error_text").css("display", "none");
	$("#search_zip_state_city_zip_client_error_text").css("display", "none");
}

function showStateAndCityOrZipErrorMessages(){
	$("#search_state_state_city_zip_client_error_text").css("display", "");
	$("#search_city_state_city_zip_client_error_text").css("display", "");
	$("#search_zip_state_city_zip_client_error_text").css("display", "");
}


function clearLastName(){
	$("#search_last_name").val('');
	clearLastNameError();
	clearFacilityLastNameRequiredErrorMessages();
	//trigger change event handler for last_name
	$("#search_last_name").trigger("propertychange");
}

function showLastNameError(){
	$('#search_lname_client_error_text').css("display", "");
}

function clearLastNameError(){
	$('#search_lname_client_error_text').css("display", "none");
}


function clearFirstName(){
	$("#search_first_name").val('');
	clearFirstNameError();
	//trigger change event handler for first_name
	$("#search_first_name").trigger("propertychange");
}

function showFirstNameError(){
	$('#search_fname_client_error_text').css("display", "");
}

function clearFirstNameError(){
	$('#search_fname_client_error_text').css("display", "none");
}


function clearFacilityName(){
	$("#search_facility_name").val('');
	clearFacilityNameError();
	clearFacilityLastNameRequiredErrorMessages();
	//trigger change event handler for facility_name
	$("#search_facility_name").trigger("propertychange");
}

function showFacilityNameError(){
	$('#search_facilityname_client_error_text').css("display", "");
}

function clearFacilityNameError(){
	$('#search_facilityname_client_error_text').css("display", "none");
}


function clearGender(){
	$("#search_gender").val('');
	clearGenderError();
	//trigger change event handler for gender
	$("#search_gender").trigger("change");
}

function showGenderError(){
	//Currently no gender error field(s)
	return;
}

function clearGenderError(){
	//Currently no gender error field(s)
	return;
}


function clearPhone(){
	$("#search_phone1").val('');
	//trigger change event handler for search_phone1
	$("#search_phone1").trigger("propertychange");
	
	$("#search_phone2").val('');
	//trigger change event handler for search_phone2
	$("#search_phone2").trigger("propertychange");
	
	$("#search_phone3").val('');
	//trigger change event handler for search_phone3
	$("#search_phone3").trigger("propertychange");
	
	clearPhoneError();
}

function showPhoneError(){
	$('#search_phone_client_error_text').css("display", "");
}

function clearPhoneError(){
	$('#search_phone_client_error_text').css("display", "none");
}


function clearAll(){
	clearLocation();
	clearNameAndOthers();
	clearSharedErrorMessage();
}

function clearCity(){
	$("#search_city_name").val('');
	clearCityError();
	clearStateAndCityOrZipErrorMessages();
	//trigger change event handler for city_name
	$("#search_city_name").trigger("change");
}

function showCityError(){
	$('#search_city_client_error_text').css("display", "");
}

function clearCityError(){
	$('#search_city_client_error_text').css("display", "none");
}


function clearState(){
	$("#search_state_name").val('');
	clearStateError();
	clearStateAndCityOrZipErrorMessages();
	//trigger change event handler for state_name
	$("#search_state_name").trigger("change");
}

function showStateError(){
	$('#search_state_client_error_text').css("display", "");
}

function clearStateError(){
	$('#search_state_client_error_text').css("display", "none");
}

function clearZip(){
	$("#search_zip_code").val('');
	clearZipError();
	clearStateAndCityOrZipErrorMessages();
	//trigger change event handler for zip_code
	$("#search_zip_code").trigger("propertychange");
}

function showZipError(){
	$('#search_zip_client_error_text').css("display", "");
}

function clearZipError(){
	$('#search_zip_client_error_text').css("display", "none");
}


function clearSharedErrorMessage(){
	$('#empty_client_error_text').css("display", "none");
}

function showSharedErrorMessage(){
	$('#empty_client_error_text').css("display", "");
}

//enables or disables the state_name & city_name input boxes based on the current value of zip_code input box
function city_stateEnableDisable(){
	if($('#search_zip_code').val().length <= 0){
		//if zip_code has no value entered, then re-enable the state_name input box
		$('#search_state_name').prop('disabled', false);
	}else{
		//if zip_code does not have any value entered, then clear & disable state_name & city_name input boxes
		clearState();
		clearCity();
		$('#search_state_name').prop('disabled', true);
		$('#search_city_name').prop('disabled', true);
	}
}


//enables or disables the city_name input box based on the current selected value of state_name
function cityEnableDisable(){
	if($('#search_state_name').val().length <= 0){
		//if state_name does not have a valid state selected, then clear & disable city_name input box
		clearCity();
		$('#search_city_name').prop('disabled', true);
	}else{
		//if state_name has valid state selected, then enable city_name input box
		$('#search_city_name').prop('disabled', false);
	}
}

//enables or disables the zip_code input box based on the current selected value of state_name & city_name
function zipEnableDisable(){
	if(($('#search_state_name').val().length <= "") && ($('#search_city_name').val() <= "")){
		//if state_name & city name both do not have values, then enable zip_code input box
		$('#search_zip_code').prop('disabled', false);
	}else{
		//if state_name or city name have a valid value, then clear and disable zip_code input box
		clearZip();
		$('#search_zip_code').prop('disabled', true);
	}
}

//enables or disables the last_name input box based on the current value of search_facility_name input box
function lnameEnableDisable(){
	if($('#search_facility_name').val().length <= 0){
		//if search_facility_name has no value entered, then re-enable the search_last_name, search_first_name, & search_gender input/select boxes
		$('#search_last_name').prop('disabled', false);
		$('#search_first_name').prop('disabled', false);
		$('#search_gender').prop('disabled', false);
	}else{
		//if search_facility_name has a value entered, then clear & disable search_last_name, search_first_name, & search_gender input/select boxes
		clearLastName();
		clearFirstName();
		clearGender();
		$('#search_last_name').prop('disabled', true);
		$('#search_first_name').prop('disabled', true);
		$('#search_gender').prop('disabled', true);
	}
}

//enables or disables the search_facility_name input box based on the current value of search_last_name, search_first_name, & search_gender input/select boxes
function facilitynameEnableDisable(){
	if(($('#search_last_name').val().length <= 0) && ($('#search_first_name').val().length <= 0) && ($('#search_gender').val().length <= 0)){
		//if search_last_name has no value entered, then re-enable the search_facility_name input box
		$('#search_facility_name').prop('disabled', false);
	}else{
		//if search_last_name has a value entered, then clear & disable the search_facility_name input box
		clearFacilityName();
		$('#search_facility_name').prop('disabled', true);
	}
}
