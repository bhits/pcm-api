var result4ajaxJSON;


//kill Ajax call after 20s
var ajaxCallTimeout = 20000;

npiLists=new Array();


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

$(function(){
	
	$("#provider_search_modal #resultList").on("click", "button.addProviderButton", function(evt){
		var clickedButton = $(this);
		
		if($(clickedButton).hasClass("addIndividualProviderButton")){
			//Disable button to prevent multiple clicks
			$(clickedButton).prop("disabled", true);
			
			var entryId=$(clickedButton).attr("id").substr(27,$(clickedButton).attr("id").length-27);
			var serializedQueryResult=JSON.stringify(result4ajaxJSON["providers"][entryId]);
			$.ajax({
				  url: "connectionProviderAdd.html",
				  type: "POST",
				  data: {npi:result4ajaxJSON["providers"][entryId]["npi"]},
				  success:function() {
					  window.location.replace("connectionMain.html?notify=add");
				  },
				  error:function(jqXHR) {
					  //Re-enable button
					  $(clickedButton).prop("disabled", false);
					  
					  window.alert("ERROR: " + jqXHR.responseText);
				  }
				});
		}else if($(clickedButton).hasClass("addOrganizationalProviderButton")){
			//Disable button to prevent multiple clicks
			$(clickedButton).prop("disabled", true);
			
			var entryId=$(clickedButton).attr("id").substr(31,$(clickedButton).attr("id").length-31);
			var serializedQueryResult=JSON.stringify(result4ajaxJSON["providers"][entryId]);
			$.ajax({
				  url: "connectionProviderAdd.html",
				  type: "POST",
				  data: {npi:result4ajaxJSON["providers"][entryId]["npi"]},
				  success:function() {
				       window.location.replace("connectionMain.html?notify=add");
				  },
				  error:function(jqXHR) {
					//Re-enable button
					  $(clickedButton).prop("disabled", false);
					  
					  window.alert("ERROR: " + jqXHR.responseText);
				  }
				});
		}
		
	});
	
	
	
	
});


jQuery.fn.buildPagingBar = function( arrHtmlStr, items_per_page, func2showPage, totalNumberOfProviders, totalPages )
{
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

			//alert("Page link clicked: " + currentPage);
			buildPagingLinks();
			// add pagination
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

function lookup (){
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

		if ($("input#city_name").val() != ""){
			providerSearchForm+="&city="+$("#city_name").val();
		}
		if ($("#state_name").val() != ""){
			providerSearchForm+="&usstate="+$("#state_name").val();
		}
		if ($("input#zip_code").val() != ""){
			providerSearchForm+="&zipcode="+$("#zip_code").val();
		}
		if ($("select#gender").val() != ""){
			var gender=null;
			if ($("select#gender").val()=="M"){
				gender='MALE';
			}else if($("select#gender").val()=="F"){
				gender="FEMALE";
			}else{
				throw new RangeError("Invalid value detected in gender select control");
			}
			providerSearchForm+="&gender="+gender;
		}
		if ($("input#phone1").val() != ""){
			providerSearchForm+="&phone="+$("input#phone1").val()+$("input#phone2").val()+$("input#phone3").val();
		}
		if ($("input#first_name").val() != ""){
			providerSearchForm+="&firstname="+$("input#first_name").val();
		}
		
		if ($("input#last_name").val() != ""){
			providerSearchForm+="&lastname="+$("input#last_name").val();
		}
		
		if ($("input#facility_name").val() != ""){
			providerSearchForm+="&facilityname="+$("input#facility_name").val();
		}
		
		$("#provider_search_modal .search-loading").show();
	    //window.alert( providerSearchForm );
		
		providerSearchForm+="&pageNumber=0";
		
		setTimeout( killAjaxCall, ajaxCallTimeout); 

		var myAjaxCall= $.ajax({
        	dataType: "json",
			url: "providerSearch.html",
			type:"GET",
			async:true, 
			data: providerSearchForm,
			success: function (queryResult) { 
				ajaxFinishedFlag++;
				if(queryResult==null) 
				{
					showResult( queryResult, items_per_page, totalNumberOfProviders, totalPages);
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
				
				showResult( arrProviderHtmStr, items_per_page, totalNumberOfProviders, totalPages );
			},
			error: function (queryResult) {
				//If response HTTP Status Code is 440 (Session has expired)
	        	if(queryResult.status == 440){
	        		$('div#consent_session_expired_modal').modal();
	        		
	        		$('.redirectToLogin').click(function()
    				{
    					window.location.reload();
    				});
	        	}
	        	else{
	        		window.alert("ERROR: " + queryResult.responseText);
	        	}
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
	    var providerSearchForm = "";
	    var ajaxFinishedFlag = 0;
	    var pageNumber = 0;
	    var totalNumberOfProviders = 0;
	    var totalPages = 0;

        var arrProviderHtmStr = new Array();
    	var items_per_page = 10;
    	
		//$("#Pagination").empty();
	    
		$("#resultList").hide();
		$("#noResult").hide();
		$("#noResponse").hide();
		$("#resultList").empty();

		if ($("#city_name").val() != ""){
			providerSearchForm+="&city="+$("#city_name").val();
		}
		if ($("#state_name").val() != ""){
			providerSearchForm+="&usstate="+$("#state_name").val();
		}
		if ($("#zip_code").val() != ""){
			providerSearchForm+="&zipcode="+$("#zip_code").val();
		}
		if ($("#gender").val() != ""){
			var gender=null;
			if ($("#gender").val()=="M")
				gender='MALE';
			if ($("#gender").val()=="F")
				gender="FEMALE";
			providerSearchForm+="&gender="+gender;
		}
		if ($("#phone1").val() != ""){
			providerSearchForm+="&phone="+$("#phone1").val()+$("#phone2").val()+$("#phone3").val();
		}
		if ($("#first_name").val() != ""){
			providerSearchForm+="&firstname="+$("#first_name").val();
		}
		
		if ($("#last_name").val() != ""){
			providerSearchForm+="&lastname="+$("#last_name").val();
		}
		
		if ($("#facility_name").val() != ""){
			providerSearchForm+="&facilityname="+$("#facility_name").val();
		}
		
		$("#provider_search_modal .search-loading").show();
	    //window.alert( providerSearchForm );
		
		pageNumber = $(".currentpage").text() - 1;
		
		providerSearchForm+="&pageNumber="+pageNumber;
		setTimeout( killAjaxCall, ajaxCallTimeout); 

		var myAjaxCall= $.ajax({
        	dataType: "json",
			url: "providerSearch.html",
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
			},
			error:function(jqXHR) {
				window.alert("ERROR: " + jqXHR.responseText);
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
	setTimeout( function() { $("#provider_search_modal .search-loading").fadeOut({ duration: 400}); }, 200 );

    if( arrHtmlStr != null && arrHtmlStr.length > 0)
    {
		$("#Pagination").buildPagingBar( arrHtmlStr, items_per_page, showCurrentPage, totalNumberOfProviders, totalPages ); 
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
	$("#state_state_city_zip_client_error_text").css("display", "none");
	$("#city_state_city_zip_client_error_text").css("display", "none");
	$("#zip_state_city_zip_client_error_text").css("display", "none");
}

function showStateAndCityOrZipErrorMessages(){
	$("#state_state_city_zip_client_error_text").css("display", "");
	$("#city_state_city_zip_client_error_text").css("display", "");
	$("#zip_state_city_zip_client_error_text").css("display", "");
}


function clearLastName(){
	$("#last_name").val('');
	clearLastNameError();
	clearFacilityLastNameRequiredErrorMessages();
	//trigger change event handler for last_name
	$("#last_name").trigger("propertychange");
}

function showLastNameError(){
	$('#lname_client_error_text').css("display", "");
}

function clearLastNameError(){
	$('#lname_client_error_text').css("display", "none");
}


function clearFirstName(){
	$("#first_name").val('');
	clearFirstNameError();
	//trigger change event handler for first_name
	$("#first_name").trigger("propertychange");
}

function showFirstNameError(){
	$('#fname_client_error_text').css("display", "");
}

function clearFirstNameError(){
	$('#fname_client_error_text').css("display", "none");
}


function clearFacilityName(){
	$("#facility_name").val('');
	clearFacilityNameError();
	clearFacilityLastNameRequiredErrorMessages();
	//trigger change event handler for facility_name
	$("#facility_name").trigger("propertychange");
}

function showFacilityNameError(){
	$('#facilityname_client_error_text').css("display", "");
}

function clearFacilityNameError(){
	$('#facilityname_client_error_text').css("display", "none");
}


function clearGender(){
	$("#gender").val('');
	clearGenderError();
	//trigger change event handler for gender
	$("#gender").trigger("change");
}

function showGenderError(){
	//Currently no gender error field(s)
}

function clearGenderError(){
	//Currently no gender error field(s)
}


function clearPhone(){
	$("#phone1").val('');
	//trigger change event handler for phone1
	$("#phone1").trigger("propertychange");
	
	$("#phone2").val('');
	//trigger change event handler for phone2
	$("#phone2").trigger("propertychange");
	
	$("#phone3").val('');
	//trigger change event handler for phone3
	$("#phone3").trigger("propertychange");
	
	clearPhoneError();
	
}

function showPhoneError(){
	$('#phone_client_error_text').css("display", "");
}

function clearPhoneError(){
	$('#phone_client_error_text').css("display", "none");
}


function clearAll(){
	clearLocation();
	clearNameAndOthers();
	clearSharedErrorMessage();
}

function clearCity(){
	$("#city_name").val('');
	clearCityError();
	clearStateAndCityOrZipErrorMessages();
	//trigger change event handler for city_name
	$("#city_name").trigger("change");
}

function showCityError(){
	$('#city_client_error_text').css("display", "");
}

function clearCityError(){
	$('#city_client_error_text').css("display", "none");
}


function clearState(){
	$("#state_name").val('');
	clearStateError();
	clearStateAndCityOrZipErrorMessages();
	//trigger change event handler for state_name
	$("#state_name").trigger("change");
}

function showStateError(){
	$('#state_client_error_text').css("display", "");
}

function clearStateError(){
	$('#state_client_error_text').css("display", "none");
}


function clearZip(){
	$("#zip_code").val('');
	clearZipError();
	clearStateAndCityOrZipErrorMessages();
	//trigger change event handler for zip_code
	$("#zip_code").trigger("propertychange");
}

function showZipError(){
	$('#zip_client_error_text').css("display", "");
}

function clearZipError(){
	$('#zip_client_error_text').css("display", "none");
}


function clearSharedErrorMessage(){
	$('#empty_client_error_text').css("display", "none");
}

function showSharedErrorMessage(){
	$('#empty_client_error_text').css("display", "");
}

//enables or disables the state_name & city_name input boxes based on the current value of zip_code input box
function city_stateEnableDisable(){
	if($('#zip_code').val().length <= 0){
		//if zip_code has no value entered, then re-enable the state_name input box
		$('#state_name').prop('disabled', false);
	}else{
		//if zip_code has a value entered, then clear & disable state_name & city_name input boxes
		clearState();
		clearCity();
		$('#state_name').prop('disabled', true);
		$('#city_name').prop('disabled', true);
	}
}


//enables or disables the city_name input box based on the current selected value of state_name
function cityEnableDisable(){
	if($('#state_name').val().length <= 0){
		//if state_name does not have a valid state selected, then clear & disable city_name input box
		clearCity();
		$('#city_name').prop('disabled', true);
	}else{
		//if state_name has valid state selected, then enable city_name input box
		$('#city_name').prop('disabled', false);
	}
}

//enables or disables the zip_code input box based on the current selected value of state_name & city_name
function zipEnableDisable(){
	if(($('#state_name').val().length <= "") && ($('#city_name').val() <= "")){
		//if state_name & city name both do not have values, then enable zip_code input box
		$('#zip_code').prop('disabled', false);
	}else{
		//if state_name or city name have a valid value, then clear and disable zip_code input box
		clearZip();
		$('#zip_code').prop('disabled', true);
	}
}

//enables or disables the last_name input box based on the current value of facility_name input box
function lnameEnableDisable(){
	if($('#facility_name').val().length <= 0){
		//if facility_name has no value entered, then re-enable the last_name, last_name, & gender input/select boxes
		$('#last_name').prop('disabled', false);
		$('#first_name').prop('disabled', false);
		$('#gender').prop('disabled', false);
	}else{
		//if facility_name has a value entered, then clear & disable last_name, first_name, & gender input/select boxes
		clearLastName();
		clearFirstName();
		clearGender();
		$('#last_name').prop('disabled', true);
		$('#first_name').prop('disabled', true);
		$('#gender').prop('disabled', true);
	}
}

//enables or disables the facility_name input box based on the current value of last_name input box
function facilitynameEnableDisable(){
	if(($('#last_name').val().length <= 0) && ($('#first_name').val().length <= 0) && ($('#gender').val().length <= 0) ){
		//if last_name has no value entered, then re-enable the facility_name input box
		$('#facility_name').prop('disabled', false);
	}else{
		//if last_name has a value entered, then clear & disable the facility_name input box
		clearFacilityName();
		$('#facility_name').prop('disabled', true);
	}
}