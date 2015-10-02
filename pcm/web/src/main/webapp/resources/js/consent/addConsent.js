var lastProviderState={};


/**********************************************************************************
 * DOCUMENT.READY FUNCTION
**********************************************************************************/
$(document).ready(function() {
	var addConsent_tmp = null;
	var isProviderAdminUser_tmp = null;
	
	var in_addConsent_tmp = $('input#input_isAddConsent').val();
	var in_isProviderAdminUser_tmp = $('input#input_isProviderAdminUser').val();


	//Convert string input for in_addConsent_tmp to boolean type
	if(in_addConsent_tmp === "true"){
		addConsent_tmp = true;
	}else if(in_addConsent_tmp === "false"){
		addConsent_tmp = false;
	}else{
		addConsent_tmp = null;
	}

	//Check if addConsent is a valid boolean value
	if((addConsent_tmp !== true) && (addConsent_tmp !== false)){
		addConsent_tmp = null;
		throw new ReferenceError("addConsent_tmp variable is not a valid boolean value");
	}
	
	
	//Convert string input for in_isProviderAdminUser_tmp to boolean type
	if(in_isProviderAdminUser_tmp === "true"){
		isProviderAdminUser_tmp = true;
	}else if(in_isProviderAdminUser_tmp === "false"){
		isProviderAdminUser_tmp = false;
	}else{
		isProviderAdminUser_tmp = false;
	}

	//Check if isProviderAdminUser_tmp is a valid boolean value
	if((isProviderAdminUser_tmp !== true) && (isProviderAdminUser_tmp !== false)){
		isProviderAdminUser_tmp = null;
		throw new ReferenceError("isProviderAdminUser_tmp variable is not a valid boolean value");
	}
	
	
	var specMedSetObj_tmp = null;

	if(addConsent_tmp === false){
		specMedSetObj_tmp = new Array();

		$('.specmedinfo-input').each(function(){
			var str_code = $(this).attr('id');
			var str_codesys = $(this).data('codesys');
			var str_dispname = $(this).data('dispname');

			var newEntry = createSpecMedInfoObj(str_code, str_codesys, str_dispname);
			specMedSetObj_tmp.push(newEntry);
			newEntry = null;
		});
	}

	setupPage(addConsent_tmp, isProviderAdminUser_tmp, specMedSetObj_tmp);
	
	//Setup search for provider modal event handlers
	$('#search_phone1, #search_phone2, #search_phone3').autotab_magic().autotab_filter('numeric');
	cityEnableDisable();
	zipEnableDisable();

	/* Builds list of providers already added so that results in the search results
	 * modal can be disabled if that provider has already been added. */
	$('.npi-list-input').each(function(){
		var in_NPI = $(this).val();
		npiLists.push(in_NPI);
	});


	//Binds an event handler to the change event for the state_name element
	$('#search_state_name').change(function(e){
		e.stopPropagation();

		cityEnableDisable();
		zipEnableDisable();
	});

	//Binds an event handler to the change event for the state_name element
	$('#search_zip_code').bind("propertychange keyup input", function(e){
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

/***********************************************************************************
 * START OF setupPage
************************************************************************************/
function setupPage(addConsent_tmp, isProviderAdminUser_tmp, specMedSetObj_tmp) {
	var addConsent = addConsent_tmp;
	var isProviderAdminUser = isProviderAdminUser_tmp;
    
	//Check if addConsent is a valid boolean value
	if((addConsent !== true) && (addConsent !== false)){
		addConsent = null;
	    throw new ReferenceError("addConsent variable is not a valid boolean value");
    }
	
	//Check if isProviderAdminUser is a valid boolean value
	if((isProviderAdminUser !== true) && (isProviderAdminUser !== false)){
		isProviderAdminUser = null;
	    throw new ReferenceError("isProviderAdminUser variable is not a valid boolean value");
    }
	
	//Set popover 'a' element data attributes based on dynamic hidden form values prior to popup initialization
	$('.input_i-text').each(function(){
		var linkID = $(this).data("linkid");
		var in_content = $(this).attr("value");
		var in_title = $(this).data("intitle");
		
		document.getElementById(linkID).setAttribute("data-content", in_content);
		document.getElementById(linkID).setAttribute("data-title", in_title);
	});
	
	var specMedSetObj = null;
	var specMedSet = null;
	
	if(addConsent === false){
	    specMedSetObj = specMedSetObj_tmp;
	    
	    try{
	    	specMedSet = specMedSetObj;
    	}catch(e){
		    if(e.name == "TypeError"){
		    	specMedSet = null;
			}else{
				throw e;
			}
		}
	}
	
	
	// set providers in consent being edited to be checked
	$('.prov-npi-checked-input').each(function(){
		var prov = $(this).val();
		document.getElementById(prov).checked = true;
	});
	
	// set sensitivity policy codes in consent being edited to be checked
	$('.sensitivity-policy-code-checked-input').each(function(){
		var sens_code = $(this).val();
		document.getElementById(sens_code).checked = true;
	});
	
	// set clinical document section type codes in consent being edited to be checked
	$('.doc-sec-type-code-checked-input').each(function(){
		var docsectyp_code = $(this).val();
		document.getElementById(docsectyp_code).checked = true;
	});
	
	// set clinical document section codes in consent being edited to be checked
	$('.doc-sec-code-checked-input').each(function(){
		var docsec_code = $(this).val();
		document.getElementById(docsec_code).checked = true;
	});
	
	// set purpose of use codes in consent being edited to be checked
	$('.purpose-use-code-checked-input').each(function(){
		var puruse_code = $(this).val();
		document.getElementById(puruse_code).checked = true;
	});
	
	
	
	$('input').iCheck({
		    checkboxClass: 'icheckbox_square-blue',
		    radioClass: 'iradio_square-blue',
		    increaseArea: '20%' // optional
	});
	
	//Initialize page for adding/editing consent
	initAddConsent(addConsent, isProviderAdminUser, specMedSet);

	//Initialize popovers
	$('[data-toggle=popover]').popover();
	
	//Close all currently showing popovers when user clicks the page outside of a popover
	$('html').on('mouseup', function(e) {
	    if((!$(e.target).closest('.popover').length)) {
	    	if((!$(e.target).closest('[data-toggle=popover]').length)){
	        	$('.popover-showing').each(function(){
	            	$(this).popover('toggle');
	        	});
			}
	    }
	});
	
	
	/* When show.bs.popover event is thrown, close any other visible popovers, then flag triggered
	 * popover element with popover-showing class.
	 * 		show.bs.popover event is thrown immediately when show instance method is called.
	 *   	It will not wait for the popover's CSS transitions to complete first.   */
	$('[data-toggle=popover]').on('show.bs.popover', function(e){
		$('[data-toggle=popover].popover-showing').not(this).popover('toggle'); //all but this
		$(this).addClass('popover-showing');
	});
	
	/* When hide.bs.popover event is thrown, remove popover-showing class from popover element.
	 * 		hide.bs.popover event is thrown immediately when hide instance method is called.
	 *   	It will not wait for the popover's CSS transitions to complete first.   */
	$('[data-toggle=popover]').on('hide.bs.popover', function(e){
		$(this).removeClass('popover-showing');
	});
	
	
	// populate for edit consent page
	$("#authorizers").empty();
	$("#authorize-list input").each(function() {
		if($(this).attr("checked")=="checked"){
			$("#authorizers").append('<li class="uneditable-input"><span class="fa fa-user"></span>'
					+$(this).parent().parent().children("span").text()
					+'</li>');
		}
	});
	$("#consentmadetodisplay").empty();
	$("#disclose-list input").each(function() {
		if($(this).attr("checked")=="checked"){
			$("#consentmadetodisplay").append('<li class="uneditable-input" ><span class="fa fa-user"></span>'
					+$(this).parent().parent().children("span").text()
					+'</li>');
		}
	});

	
	$("body").removeClass('fouc_loading');
	
	if(!isProviderAdminUser){
		$("ul#pulldown_menu").sidebar({
			position:"top",
			open:"click",
			close:"click",
			//labelText:"GUIDE",
			callback: {
	        	item : {
	            	enter : function(){},
	                leave : function(){}
	        	},
	        	sidebar : {
	            	open : function(){},
	                close : function(){}
	        	}	
	    	},
	    	inject : $("<div><span>GUIDE</span></div>")
		});


		$("div.sidebar-container").addClass("guide-pulldown-tab");
		$("div.sidebar-inject.top > span").addClass("sidebar-label");
		
		$('#tourtest').joyride({
		    tipContainer: '#consent-add-main',
		    heightPadding: $('footer').outerHeight() + 10,
		    mode: 'focus',
		    autoStart: true,
		    'preStepCallback': function() {
			    var int_next_index = 0;
			    try{
			    	int_next_index = $('#tourtest').joyride('get_next_index');
			    }catch(e){
				    int_next_index = 0;
				}
			    
			    var li_field_id = $('#tourtest li').get(int_next_index);
			    var isShowDelayedNext = $(li_field_id).hasClass('show-delayed-next');
			    var str_field_id = $(li_field_id).data("id");
			    var tempVarIconUser = $('div#' + str_field_id).find('.fa-user');
			    var tempVarBadge = $('div#' + str_field_id).find('.badge');
			    var isDataPopulated = false;
	
			    if(tempVarIconUser.length > 0 || tempVarBadge.length > 0){
				    isDataPopulated = true;
			    }else{
			    	isDataPopulated = false;
				}
	
			    var obj_next_tip = $('#tourtest').joyride('get_next_tip');
			    var btn_next_button = $(obj_next_tip).find('.joyride-next-tip');
	
			    $(btn_next_button).addClass('hidden');
			    
			    if(isDataPopulated || isShowDelayedNext){					    	
			    	setTimeout(function(){
				    	$(btn_next_button).removeClass('hidden');
					}, 500);
			    }
			},
			'postRideCallback': function() {
				setGuideButtonStateOff();
			}
		});
	
		if(jQuery.storage.getItem('guideStatus', 'sessionStorage') == 'on'){
			turnGuideOn();
		}else if(jQuery.storage.getItem('guideStatus', 'sessionStorage') == 'off'){
			turnGuideOff();
		}else{
			if($('#btn_guide_OnOff').hasClass('guide-off-flag') === false){
				turnGuideOn();
			}else{
				turnGuideOff();
			}
		}
		
		
		$('#btn_guide_OnOff').click(function() {
			if($('#btn_guide_OnOff').hasClass('guide-off-flag') === true){
				turnGuideOn();
			}else{
				turnGuideOff();
			}
	    });
	    
		$('#saveauthorizer').click(function() {
			ifGuideOnGoNext();
	    });
	
		$('#saveconsentmadeto').click(function() {
			ifGuideOnGoNext();
	    });
	
	    $('#btn_close_share_settings').click(function() {
	    	ifGuideOnGoNext();
		});
	
	    $('#btn_save_selected_purposes').click(function() {
	    	ifGuideOnGoNext();
		});
	
		$('#selectInfo').change(function() {
		    if($('#selectInfo').prop('checked') == true){
		    	ifGuideOnGoNext();
			}
		});
	
	    $('#selectPurposesToggle').change(function() {
		    if($('#selectPurposesToggle').prop('checked') == true){
		    	ifGuideOnGoNext();
			}
		});
	    
		$("a[id*=i-icon]").bind('click', function() {
			var id = $(this).attr("id").split('_')[1];
			$("#message-block_"+id).css("display","block");
	
		});
		
		$("body").on("closeJoyrideClick", function(e){
			e.stopImmediatePropagation();
			
			$('#tourtest').joyride('end');
			turnGuideOff();
		});
	
	}
	
	function ifGuideOnGoNext(){
		if(!isProviderAdminUser){
			if(jQuery.storage.getItem('guideStatus', 'sessionStorage') == 'on'){
				$('#tourtest').joyride("go_next");
			}
		}
	}
	
	//Turn Joyride Guide Off
	function turnGuideOff(){
		if(!isProviderAdminUser){
			$('#tourtest').joyride('end');
			setGuideButtonStateOff();
		}
	}

	//Set Joyride Guide Button State Off
	function setGuideButtonStateOff(){
		if(!isProviderAdminUser){
			$('#btn_guide_OnOff').addClass('guide-off-flag');
			
			jQuery.storage.setItem('guideStatus', 'off', 'sessionStorage');
			
			$('#btn_guide_OnOff').text("Guide On");
		}
	}

	//Turn Joyride Guide On
	function turnGuideOn(){
		if(!isProviderAdminUser){
			$('#tourtest').joyride('end');
			$('#tourtest').joyride();
			setGuideButtonStateOn();
		}
	}

	//Set Joyride Guide Button State On
	function setGuideButtonStateOn(){
		if(!isProviderAdminUser){
			if($('#btn_guide_OnOff').hasClass('guide-off-flag') === true){
				$('#btn_guide_OnOff').removeClass('guide-off-flag');
			}
			
			jQuery.storage.setItem('guideStatus', 'on', 'sessionStorage');
			
			$('#btn_guide_OnOff').text("Guide Off");
		}
	}
	
	
}
/**********************************************************************************
 * END OF setupPage
***********************************************************************************/


/***********************************************************************************
 * FUNCTION TO INITIALIZE PAGE WHEN ADDING/EDITING A CONSENT
***********************************************************************************/

/*Fix issue #493 Start
This is only a problem found in Internet Explorer browser. 
After it was fixed, admin could press "Space" Key to select.*/

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

function initAddConsent(addConsent, isProviderAdminUser, specMedSet) {
				
				//Check if addConsent is a valid boolean value
				if((addConsent !== true) && (addConsent !== false)){
					throw new ReferenceError("addConsent variable is not a valid boolean value");
				}
				
				//Check if isProviderAdminUser is a valid boolean value
				if((isProviderAdminUser !== true) && (isProviderAdminUser !== false)){
				    throw new ReferenceError("isProviderAdminUser variable is not a valid boolean value");
			    }
				
				
				/*******************************************************************************************
				 * MAIN CODE
				*******************************************************************************************/
	
				var specmedinfoary = new Array();
				var specmedinfoid=0;
				var specmedinfoary_final = new Array();
				var lastInfoState={};
				var lastSpecificMedicalInfoState={};
				var lastPurposeState={};
				var isSaveButtonClicked=false;
				
				lastProviderState={};
				
				/********* Initialize Date Picker **********/
				var dateToday = new Date();
				
				var startDate = new Date();
				var endDate = new Date();
				
				if (addConsent === true) {				
					dateToday = new Date();
			        startDate = new Date(dateToday.getFullYear(), dateToday.getMonth(), dateToday.getDate(), 0, 0, 0, 0);
			        endDate = new Date(dateToday.getFullYear()+1, dateToday.getMonth(), dateToday.getDate(), 0, 0, 0, 0);

			        // set end date as a day minus 1 year
			        endDate.setDate(endDate.getDate() - 1);
				} else {
					dateToday = new Date($('#date-picker-start').attr('value'));
					startDate = new Date(dateToday.getFullYear(), dateToday.getMonth(), dateToday.getDate(), 0, 0, 0, 0);
					dateToday = new Date($('#date-picker-end').attr('value'));
					endDate = new Date(dateToday.getFullYear(), dateToday.getMonth(), dateToday.getDate(), 0, 0, 0, 0);

					var intAryLength = $(specMedSet).length;

					for(var i = 0; i < intAryLength; i++){
						initSpecMedInfoArray(specMedSet[i]);
					}
				}
				
				var nowTemp = new Date();
				var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
				
				$('#date-picker-start').datepicker({
					onRender: function(date) {
						return date.valueOf() < now.valueOf() ? 'disabled' : '';
					}
				});
				
				$('#date-picker-end').datepicker({
					onRender: function(date) {
						return date.valueOf() < now.valueOf() ? 'disabled' : '';
					}
				});
				
		        $('#date-picker-start').datepicker('setValue',startDate);
		        $('#date-picker-start').attr('value',$('#date-picker-start').attr('value'));
		        $('#date-picker-start').attr('data-date-format',startDate);
			    $('#date-picker-end').datepicker('setValue',endDate);
			    $('#date-picker-end').attr('value',$('#date-picker-end').attr('value'));
			    
			    
			    /********* End Inititialize Date Picker **********/
				
			    
			  //Check if adding or editing consent
			    if (addConsent === true) {
			    	//ADDING CONSENT:
			    	$("#allInfo").iCheck("check");
			    	$("#edit1").hide();
				}else{
					//EDITING CONSENT:
					
					loadAllSharePreferences();
					loadAllPurposeofshareform();
					loadAllLastSpecificMedicalInfoState();
			    
					// disable providers in made to list that are checked in to disclose list
					$(".isMadeToList").each(function(){
						var providerId=$(this).attr("id").substr(2,$(this).attr("id").length-2);
						if($("#from"+providerId).prop('checked')==true){
							toggleToProviderDisabled(providerId);
						}
					}); 
				 
					// disable providers in to disclose list that are checked in to made list
					$(".toDiscloseList").each(function(){
						var providerId=$(this).attr("id").substr(4,$(this).attr("id").length-4);
						if($("#to"+providerId).prop('checked')==true){
							toggleFromProviderDisabled(providerId);
						}
					});
					
					if (areAllInfoUnSelected()) {
						$("#allInfo").iCheck("check");
						$("#edit1").hide();
					}else{
						$("#selectInfo").iCheck("check");
						
						$("#sensitivityinfo input").each(function(){
							if ($(this).prop('checked') == true) {
								var toAppendMain='<div id="TagMain'+ 
								$(this).attr('id')+ 
								'" class="badge">'+
								$(this).parent().text()+
								"</div>";
								$("#notsharedmainpage").append(toAppendMain);
							}
						});
						
						$("#medicalinfo input").each(function(){
							if ($(this).prop('checked') == true) {
								var toAppendMain='<div id="TagMain'+ 
								$(this).attr('id')+ 
								'" class="badge">'+
								$(this).parent().text()+
								"</div>";
								$("#notsharedmainpage").append(toAppendMain);
							}
						});
						
						$("#clinicalDocumentType input").each(function(){
							if ($(this).prop('checked') == true) {
								var toAppendMain='<div id="TagMain'+ 
								$(this).attr('id')+ 
								'" class="badge">'+
								$(this).parent().text()+
								"</div>";
								$("#notsharedmainpage").append(toAppendMain);
							}
						});
							
						$("#purposeOfSharingInputs input").each(function(){
							if ($(this).prop('checked') == true) {
								var toAppendMain='<div id="TagMain'+ 
								$(this).attr('id')+ 
								'" class="badge">'+
								$(this).parent().text()+
								"</div>";
								$("#sharedpurpose").append(toAppendMain);
							}
						});
					}
					
					//FIXME (MH): REMOVE THE FOLLOWING IF/ELSE CODE BLOCK AS IT IS OBSOLETE
					if (areAllPurposesUnselected()) {
						$("#allPurposes").iCheck("check");
					}
					else {
						$("#selectPurposes").iCheck("check");
					}
				}
			    
				// check all only if page is add consent (not edit)
				if (addConsent === true) {
					uncheckAllSharePreferences(loadAllSharePreferences);
					checkRecommendedPurposeofsharing(loadAllPurposeofshareform);
				}else{
					loadAllSharePreferences();
					loadAllPurposeofshareform();
				}
				
				loadAllProviders();
				
				reAppendMediInfoBadges();
				reAppendPurposeOfUse();

					
				/*******************************************************************************************
				 * EVENT HANDLERS
				*******************************************************************************************/
				
				$(".removeEntry").on("click",function(){
						var entryId=$(this).attr("id").substr(11,$(this).attr("id").length-11);
						delete specmedinfoary[entryId];
						$("#TagSpec"+entryId).remove();
						$("#entry"+entryId).remove();
					});
				
				$("div#disclose-list-container").on("ifToggled", "input.isMadeToList", function(){
					var providerId=$(this).attr("id").substr(2,$(this).attr("id").length-2);
					toggleFromProviderDisabled(providerId);
				});
				
				$("div#authorize-list-container").on("ifToggled", "input.toDiscloseList", function(){
					var providerId=$(this).attr("id").substr(4,$(this).attr("id").length-4);
					toggleToProviderDisabled(providerId);
				});
				
				
				$("div#disclose-list-container").on('ifChecked', "input.indv-prov", function(event) {
					$("div#disclose-list-container input.org-prov").iCheck("uncheck");
				});
				
				$("div#disclose-list-container").on('ifChecked', "input.org-prov", function(event) {
					$("div#disclose-list-container input.indv-prov").iCheck("uncheck");
				});
				
				
				$("div#authorize-list-container").on('ifChecked', "input.indv-prov", function(event) {
					$("div#authorize-list-container input.org-prov").iCheck("uncheck");
				});
				
				$("div#authorize-list-container").on('ifChecked', "input.org-prov", function(event) {
					$("div#authorize-list-container input.indv-prov").iCheck("uncheck");
				});
				
				
				$("button#consent-add-save").click(function(){
					$('div.validation-alert').empty();
					
					if(areAnyProvidersSelected() === true && areDateCorrected() === true){
						$(".inputformPerson input").each(function(){
							if($(this).prop("checked")==true){
								$(this).not(':submit').clone().hide().appendTo('#formToBeSubmitted');
							}
						});
						
						$(".purposeofshareform input").each(function(){
							if($(this).prop("checked")==true){
								$(this).not(':submit').clone().hide().appendTo('#formToBeSubmitted');
							}
						});
						
						$(".inputformDate").each(function(){
							$(this).find("input").not(':submit').clone().hide().appendTo('#formToBeSubmitted');
						});
						
						$(".inputform input").each(function(){
							if($(this).prop("checked")==true){
								$(this).not(':submit').clone().hide().appendTo('#formToBeSubmitted');	
							}
						});
						
						
						for (var i=0;i<specmedinfoary.length;i++){
							if(specmedinfoary[i]!=undefined){
								specmedinfoary_final.push(specmedinfoary[i]);
							}
						}
						
						
						for (var i=0;i<specmedinfoary_final.length;i++){
							if(specmedinfoary_final[i]!=undefined){
								var tempStr = '<input type="text" name="' + specmedinfoary_final[i].codeSystem + '" value="' + specmedinfoary_final[i].code + ";" + specmedinfoary_final[i].description + '" />';
								$('#formToBeSubmitted').append(tempStr.replace(/,/g,"^^^"));
							}
						}
						
						$("div#sharedpurpose div").each(function(){		
								var sharedPurposeOfUseName = $(this).text();
								$("<input type='hidden' value='" + sharedPurposeOfUseName + "' />" ).attr("name","sharedPurposeNames").appendTo('#formToBeSubmitted');
						});
						
						$("ul#authorizers li").each(function(){		
							var authorizersName = $(this).text();
							$("<input type='hidden' value='" + authorizersName + "' />" ).attr("name","authorizerNames").appendTo('#formToBeSubmitted');
						});
						
						$("ul#consentmadetodisplay li").each(function(){		
							var madeToName = $(this).text();
							$("<input type='hidden' value='" + madeToName + "' />" ).attr("name","madeToNames").appendTo('#formToBeSubmitted');
						});
						
						//Disable save button to prevent multiple submits
						$('button#consent-add-save').prop("disabled", true);
						
						//Submit form
						$('#formToBeSubmitted').submit();
						
					}else{ if(areAnyProvidersSelected() === false)
						$('div.navbar-inner-header').after("<div class='validation-alert'><span><div class='alert alert-danger rounded'><button type='button' class='close' data-dismiss='alert'>&times;</button>You must add provider(s).</div></span></div>");
					
					    if(areDateCorrected() === false)
						$('div.navbar-inner-header').after("<div class='validation-alert'><span><div class='alert alert-danger rounded'><button type='button' class='close' data-dismiss='alert'>&times;</button>You must select correct dates.</div></span></div>");
									
					} 

				});
				
	
				//callback handler for form submit
				var frm = $('#formToBeSubmitted');
				$("#formToBeSubmitted").submit(function(e)
				{
				    $.ajax(
				    {
						url: frm.attr('action'),
						type: frm.attr('method'),
						data: frm.serialize(),
				        success:function(data, textStatus, jqXHR) 
				        {
				            //data: return data from server
				        	var jsonData = JSON.parse(data);
				        	if(jsonData["isSuccess"] === true){				        		
				        		if(jsonData["isAdmin"] === true){
				        			// staff is creating a consent on behalf of patient
				        			if(jsonData["isAdd"] === false){
				        				window.location.href = "adminPatientView.html?notify=editpatientconsent&status=success&id="+ jsonData["patientId"] ; 
				        			} else {
				        				window.location.href = "adminPatientView.html?notify=createpatientconsent&status=success&id="+ jsonData["patientId"];
				        			}
				        		} else{
				        			if($('input#input_isAddConsent').val()==="true"){
				        			//patient creating a consent
				        			window.location.href = "listConsents.html?notify=add"; 
				        			} else {
				        				window.location.href = "listConsents.html";
				        			}
				        		}
				        	}				        	
				        },
				        error: function(jqXHR, textStatus, errorThrown) 
				        {
				        	//Re-enable "save" button
				        	$('button#consent-add-save').prop("disabled", false);
				        	
				        	//TODO (MH): Handle different error types from controller
				        	//If response HTTP Status Code is 440 (Session has expired)
				        	if(jqXHR.status == 440){
				        		$('div#consent_session_expired_modal').modal();
				        		
				        		$('.redirectToLogin').click(function()
		        				{
		        					window.location.reload();
		        				});
				        	}
				        	
				        	//If response HTTP Status Code is 409 (Conflict)
				        	else if(jqXHR.status == 409){
				        		resetFormToBeSubmitted();
					        	populateModalData(jqXHR.responseText, isProviderAdminUser);
					        	$('div#consent_validation_modal').modal();
				        	}else{
				        		window.alert("ERROR: " + jqXHR.responseText);
				        	}
				        }
				    });
				    e.preventDefault(); //STOP default action
				});
				
				
				
				$("#addspecmedi").click(function(){
					updateSpecMedInfo();
				});
				
				
				$("#allInfo").on("ifChecked",function(){
						uncheckAllSharePreferences();
						clearAllSpecificMedicalInfo();
						$("#notsharedmainpage").empty();
						$('#edit1').hide();
						loadAllSharePreferences();
				});
				
				/*Fix issue #448 Start
				 * After resolving issue, the datepicker is closed immediately when a date is selected. 
				 * And also add TAB keyup function to close datepicker if no date need to be selected.
				*/
				
				$('input.datepicker').bind('keyup', function(e) {
					var TAB_KEY = 9;
					
					var keyCode = e.keyCode;
					if (keyCode == TAB_KEY) {
						$('.datepicker.dropdown-menu').hide();
						e.preventDefault();
					}
				});

				$("#date-picker-start").datepicker().on('changeDate', function(ev){
					$('#date-picker-start').attr('value',ev.target.value);
					 $('#date-picker-start').datepicker({minDate: 0}) ;
					 $('.datepicker.dropdown-menu').hide();
		        });

				$("#date-picker-end").datepicker().on('changeDate', function(ev){
					$('#date-picker-end').attr('value',ev.target.value);
					    $('#date-picker-end').datepicker({minDate: 0}) ;
					    $('.datepicker.dropdown-menu').hide();
		        });				
				
				//Fix issue #448 end
				
				$("#selectInfo").on("ifChecked",function(){
					$('#edit1').show();
					showShareSettingsModal();
					loadAllSharePreferences();
					uncheckAllMedicalInfo();
				});

				
				$("#btn_save_selected_purposes").click(function(){
					isSaveButtonClicked=true;
					handleLastStoredStates(reAppendPurposeOfUse);
				});
				
				$("#btn_save_selected_medinfo").click(function(){
					isSaveButtonClicked=true;
					handleLastStoredStates(reAppendMediInfoBadges);
					if (areAllInfoUnSelected()==true)
						$("#allInfo").iCheck("check");
				});
								
				$("#share-settings-modal").on('hidden.bs.modal', function(){
					setTimeout(function(){
						handleLastStoredStates();
						isSaveButtonClicked=false;
						if (areAllInfoUnSelected()==true){
							$("#allInfo").iCheck("check");
						}
						$('#condition').val("");
					},300);
				});
				
				$("#selected-purposes-modal").on('hide.bs.modal',function(event){
					if(event.target.id == "selected-purposes-modal"){
						setTimeout(function(){
							handleLastStoredStates();
							isSaveButtonClicked=false;
							if (areAllPurposesUnselected()==true){
								$("#allPurposes").iCheck("check");
							}
						},300);
					}
				});
				
				$("#authorize-modal,#disclose-modal").on('hide.bs.modal',function(event){
					if((event.target.id == "authorize-modal") || (event.target.id == "disclose-modal")){
						setTimeout(function(){
							handleLastStoredStates();
							isSaveButtonClicked=false;
						},300);
					}
				});
				
				$("#saveauthorizer").click(function(){
					isSaveButtonClicked=true;
					handleLastStoredStates();
					$("#authorizers").empty();
					$("#authorize-list input").each(function() {
						if($(this).attr("checked")=="checked"){
							$("#authorizers").append('<li class="uneditable-input"><span class="fa fa-user"></span>'
		 							+$(this).parent().parent().children("span").text()
		 							+'</li>');
						}
					});
				});
				
				$("#saveconsentmadeto").click(function(){
					isSaveButtonClicked=true;
					handleLastStoredStates();
					$("#consentmadetodisplay").empty();
					$("#disclose-list input").each(function() {
						if($(this).attr("checked")=="checked"){
							$("#consentmadetodisplay").append('<li class="uneditable-input"><span class="fa fa-user"></span>'
		 							+$(this).parent().parent().children("span").text()
		 							+'</li>');
						}
					});
				});
				
				
				$('button.btn-sel-all-tab').click(function(e){
					var $target = $(e.target);
					
					switch($target.attr('id')){
						case "btn_sensitivity_select_all":
							$('input[name=doNotShareSensitivityPolicyCodes]').iCheck('check');
							break;
						case "btn_med_info_select_all":
							$('input[name=doNotShareClinicalDocumentSectionTypeCodes]').iCheck('check');
							break;
						case "btn_clinical_doc_select_all":
							$('input[name=doNotShareClinicalDocumentTypeCodes]').iCheck('check');
							break;
						case "btn_share_purposes_select_all":
							$('input[name=shareForPurposeOfUseCodes]').iCheck('check');
							break;
						default:
							break;
					}
					
				});
				
				$('button.btn-desel-all-tab').click(function(e){
					var $target = $(e.target);
					
					switch($target.attr('id')){
						case "btn_sensitivity_deselect_all":
							$('input[name=doNotShareSensitivityPolicyCodes]').iCheck('uncheck');
							break;
						case "btn_med_info_deselect_all":
							$('input[name=doNotShareClinicalDocumentSectionTypeCodes]').iCheck('uncheck');
							break;
						case "btn_clinical_doc_deselect_all":
							$('input[name=doNotShareClinicalDocumentTypeCodes]').iCheck('uncheck');
							break;
						case "btn_share_purposes_deselect_all":
							$('input[name=shareForPurposeOfUseCodes]').iCheck('uncheck');
							break;
						default:
							break;
					}
					
				});
				
				$('#consent_validation_modal button#btn_continue_editing').click(function(e){
					$('#consent_validation_modal').modal('hide');
				});
				
				//Show search for providers modal when add provider button is clicked
				$('#btn_add_provider_search').click(function(e){
					$('#disclose-modal').modal('hide');
					$('#providerSearchSelect-modal').modal();
				});
				
				
				/******************************************************************************************
				 * FUNCTION DEFINITIONS
				*******************************************************************************************/
				
				$(function() {
				    $( "#condition" )
				      .autocomplete({appendTo:"#autocomplete"},
				      {source: function( request, response ) {
				          $.getJSON( "callHippaSpace.html", {
				            q: request.term,
				            domain:"icd9",
				            rt:"json"
				          }, function (data) {
				                 response($.map(data.ICD9, function (item) {
				                     return {
				                    	 codeSystem:"ICD9",
				                    	 description:getDescriptionString(item.Description).replace(/,/g,"^^^"),
				                    	 code:item.Code,
				                         label: getDescriptionString(item.Description),
				                         value:	getDescriptionString(item.Description),
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
				        	var isThisEntryAlreadyEntered=false;
				        	for(key in specmedinfoary){
				        		if (specmedinfoary[key].code==ui.item.code){
				        			isThisEntryAlreadyEntered=true;
				        			return;
				        		}
				        	}
				        	if(isThisEntryAlreadyEntered==false){
				        		addSpecMedInfoToArray(ui.item);
				        	}
				        	
				        }
				      });
				  });
				
				function areAnyProvidersSelected(){
					var flag=0;
					$(".isMadeToList").each(function(){
						if($(this).prop("checked")==true){
							flag++;
							return false;
						}
						return true;
					});
					$(".toDiscloseList").each(function(){
						if($(this).prop("checked")==true){
							flag++;
							return false;
						}
						return true;
					});
					if (flag>1)
						return true;
					else
						return false;
				}
				
				
				
				function getDescriptionString(rawString){
					var fatalErrorFlag = false;
					
					if (rawString.indexOf("(")!=-1 && rawString.substr(rawString.length-1)==")"){
						var num_open_paren = 0;
						var num_close_paren = 0;
						
						var isEqualNum = false;
						
						var strTemp = rawString;
						var subResult = "";
						
						var endPar = 0;
						var openPar = 0;
						
						try{
							endPar = strTemp.lastIndexOf(")");
							openPar = strTemp.lastIndexOf("(", endPar + 1);
							
							subResult = strTemp.substring(openPar, endPar + 1);
						}catch(e){
							fatalErrorFlag = true;
							endPar = 0;
							openPar = 0;
							subResult = "";
							throw e;
						}
						
						num_open_paren = countOpenParen(subResult);
						num_close_paren = countCloseParen(subResult);
						
						if(num_open_paren === num_close_paren){
							isEqualNum = true;
						}
						
						while((num_open_paren !== num_close_paren) && (isEqualNum === false) && (openPar > 0) && (fatalErrorFlag === false)){
							try{
								openPar = strTemp.lastIndexOf("(", openPar - 1);
								subResult = strTemp.substring(openPar, endPar + 1);
							}catch(e){
								fatalErrorFlag = true;
								endPar = 0;
								openPar = 0;
								subResult = "";
								throw e;
							}
							
							num_open_paren = countOpenParen(subResult);
							num_close_paren = countCloseParen(subResult);
							
							if(num_open_paren === num_close_paren){
								isEqualNum = true;
							}
						}
						
						if((isEqualNum === true) && (fatalErrorFlag === false)){
							subResult = subResult.slice(1, subResult.length - 1);
							return  subResult;
						}else if(fatalErrorFlag !== false){
							return "";
						}else{
							return rawString;
						}
					}
					
					return rawString;
				}
				
				function checkRecommendedPurposeofsharing(callback){
					$("#TREATMENT").iCheck('check');
//					$("#ETREAT").iCheck('check');
//					$("#CAREMGT").iCheck('check');
					if(typeof callback === 'function'){
						callback();
					}
				}
				
				function loadAllSharePreferences(){
					$(".inputform input").each(function(){
						if($(this).prop("id")!=null)
							lastInfoState[$(this).prop(	"id")]=$(this).prop("checked");
					});
				}
				
				
				
				function loadAllLastSpecificMedicalInfoState(){
					clearLastSpecificMedicalInfo();
					for (var i=0;i<specmedinfoary.length;i++){
						if($("#specmedinfo"+i).length==0)
							delete specmedinfoary[i];
						if(specmedinfoary[i]!=undefined){
							lastSpecificMedicalInfoState[specmedinfoary[i].code]=specmedinfoary[i].description.replace(/\^\^\^/g,",");
						}
					}
				}
				
				function loadAllPurposeofshareform(){
					$(".purposeofshareform input").each(function(){
						lastPurposeState[$(this).prop("id")]=$(this).prop("checked");
					});
				}
				
				function uncheckAllSharePreferences(callback){
					$("input.checkBoxClass1").iCheck('uncheck');
					if(typeof callback === 'function')
						callback();
				}
				
				function clearLastSpecificMedicalInfo(){
					for (var key in lastSpecificMedicalInfoState){
						delete lastSpecificMedicalInfoState[key];
					}
				}
				
				function clearAllSpecificMedicalInfo(callback){
					clearLastSpecificMedicalInfo();
					specmedinfoary.length=0;
					$(".removeEntry").each(function(){
						var entryId=$(this).attr("id").substr(11,$(this).attr("id").length-11);
						$("#entry"+entryId).remove();
					});
					
				}
				
				function uncheckAllMedicalInfo(callback){
					$("input.checkBoxClass1").iCheck('uncheck');
					if(typeof callback === 'function')
						callback();
				}
								
				function reAppendMediInfoBadges(){
					$("#notsharedmainpage").empty();
					for(var key in lastInfoState){
						if (lastInfoState.hasOwnProperty(key)) {
						    if (lastInfoState[key]==true){
						    	var description=$('label[for="' + key + '"]').text();
						    	var toAppendMain='<div id="TagMain'+ 
								key+ 
								'" class="badge">'+
								description+
								"</div>";
								$("#notsharedmainpage").append(toAppendMain);
						    }
						}
					}
					for(var key in lastSpecificMedicalInfoState){
						if (lastSpecificMedicalInfoState.hasOwnProperty(key)) {
						    if (lastSpecificMedicalInfoState[key]!=undefined){
						    	var description=lastSpecificMedicalInfoState[key];
						    	var toAppendMain='<div id="TagMain'+ 
								key+ 
								'" class="badge">'+
								description+
								"</div>";
								$("#notsharedmainpage").append(toAppendMain);
						    }
						}
					}
				}
				
				
				function reAppendPurposeOfUse(){
					$("#sharedpurpose").empty();
					for(var key in lastPurposeState){
						if (lastPurposeState.hasOwnProperty(key)) {
						    if (lastPurposeState[key]==true){
						    	var description=$('label[for="' + key + '"]').text();
						    	var toAppendMain='<div id="TagMain'+ 
								key+ 
								'" class="badge">'+
								description+
								"</div>";
								$("#sharedpurpose").append(toAppendMain);
						    }
						}
					}
				}
				
				function areDateCorrected(){
					if($('#date-picker-start').attr('value').trim()===""||$('#date-picker-end').attr('value').trim()==="")
						return false;
					dateToday2 = new Date();
					startDate2 = new Date($('#date-picker-start').attr('value'));
					todayDate2 = new Date(dateToday2.getFullYear(), dateToday2.getMonth(), dateToday2.getDate(), 0, 0, 0, 0);
					endDate2 = new Date($('#date-picker-end').attr('value'));
					
					if(startDate2.valueOf()<todayDate2.valueOf()||endDate2.valueOf()<todayDate2.valueOf()||endDate2.valueOf()<startDate2.valueOf()||startDate2.valueOf()===NaN||endDate2.valueOf()===NaN)
						
						return false;
					else  
						return true;
						
				}
				
				function revertAllStates(){
					for (var key in lastInfoState) {
						  if (lastInfoState.hasOwnProperty(key)) {
						    if (lastInfoState[key]==true)
						    	$("#"+key).iCheck('check');
						    else
						    	$("#"+key).iCheck('uncheck');
						  }
					}
					for (var key in lastPurposeState) {
						  if (lastPurposeState.hasOwnProperty(key)) {
						    if (lastPurposeState[key]==true)
						    	$("#"+key).iCheck('check');
						    else
						    	$("#"+key).iCheck('uncheck');
						  }
					}
					for (var key in lastProviderState) {
						  if (lastProviderState.hasOwnProperty(key)) {
						    if (lastProviderState[key]==true)
						    	$("#"+key).iCheck('check');
						    else
						    	$("#"+key).iCheck('uncheck');
						  }
					}
				}
				
				function handleLastStoredStates(callback){
					if (isSaveButtonClicked==true){
						loadAllProviders();
						loadAllSharePreferences();
						loadAllPurposeofshareform();
						loadAllLastSpecificMedicalInfoState();
						isSaveButtonClicked=false;
					}
					else{
						revertAllStates();
					}
					
					if(typeof callback === 'function')
						callback();
				}
				
				function areAllInfoUnSelected(){
					for (var key in lastInfoState){
						if (key!="")
							if (lastInfoState[key]==true){
								return false;
						}
					}
					
					for (var key in lastSpecificMedicalInfoState){
						if (key!="")
							if (lastSpecificMedicalInfoState[key]!=undefined){
								return false;
						}
					}
					return true;
				}
				
				function areAllPurposesUnselected(){
					for (var key in lastPurposeState){
						if (key!="")
							if (lastPurposeState[key]==true){
								return false;
						}
					}
					return true;
				}
				
				
				function updateSpecMedInfo() {
					if(specmedinfoary[specmedinfoid]!=undefined){
						$("#specmedinfo").append('<li class="spacing" id="'+'entry'+specmedinfoid+
								'"><div><span>'+
								specmedinfoary[specmedinfoary.length-1].displayName+
								'</span><span>'+
								'<button id="specmedinfo'+specmedinfoid +'" class="btn btn-danger btn-xs list-btn removeEntry">'+
								'<span class="fa fa-minus fa-white"></span></button></span></div></li>');
						$("#condition").val("");
						specmedinfoary[specmedinfoid].added=true;
						specmedinfoid=specmedinfoid+1;
					}
				}
				
				function addSpecMedInfoToArray(item){
					 newEntry=new Object();
		        	 newEntry.displayName=item.value;
		        	 newEntry.codeSystem=item.codeSystem;
		        	 newEntry.code=item.code;
		        	 newEntry.description=item.description;
                     specmedinfoary[specmedinfoid]=newEntry;
				}
				
				function initSpecMedInfoArray(item){
					newEntry=new Object();
		        	newEntry.displayName=item.displayName;
		        	newEntry.codeSystem=item.codeSystem;
		        	newEntry.code=item.code;
		        	newEntry.description=item.displayName;
                    specmedinfoary[specmedinfoid]=newEntry;
                    updateSpecMedInfo();
				}
}



/***********************************************************************************
 * GLOBAL SCOPE FUNCTIONS
***********************************************************************************/

function resetFormToBeSubmitted(){
	$("form#formToBeSubmitted > input:not(input[name='_csrf'], input#patientUsername, input#patientId, input#consentId)").remove();
}

function loadAllProviders(){
	$(".inputformPerson input.toDiscloseList").each(function(){
		if($(this).prop("id")!=null)
			lastProviderState[$(this).prop("id")]=$(this).prop("checked");
	});
	
	$(".inputformPerson input.isMadeToList").each(function(){
		if($(this).prop("id")!=null)
			lastProviderState[$(this).prop("id")]=$(this).prop("checked");
	});
}

//Toggle from provider state disabled/enabled
function toggleFromProviderDisabled(in_providerID){
	var providerId = in_providerID;
	
	if(isNaN(providerId)){
		throw new ReferenceError("providerId variable is not a valid numeric value");
	}
	
	if($("#from"+providerId).prop('disabled')==false){
		$("#from"+providerId).iCheck('disable');
		$("#from"+providerId).closest('label').addClass("joe");
	}
	else{
		$("#from"+providerId).iCheck('enable');
		$("#from"+providerId).closest('label').removeClass("joe");
	}
}

//Toggle to provider state disabled/enabled
function toggleToProviderDisabled(in_providerID){
	var providerId = in_providerID;
	
	if(isNaN(providerId)){
		throw new ReferenceError("providerId variable is not a valid numeric value");
	}

	if($("#to"+providerId).prop('disabled')==false){
		$("#to"+providerId).iCheck('disable');
		$("#to"+providerId).closest('label').addClass("joe");
	}
	else{
		$("#to"+providerId).iCheck('enable');
		$("#to"+providerId).closest('label').removeClass("joe");
	}
}


function createSpecMedInfoObj(str_code, str_codeSystem, str_displayName){
	newEntry = new Object();
	newEntry.displayName = str_displayName;
	newEntry.codeSystem = str_codeSystem;
	newEntry.code = str_code;	
	return newEntry;
}

function showShareSettingsModal(){
	$("#share-settings-modal").modal({
		  keyboard: false
	});
}

//Count number of opening parentheses '(' in string
function countOpenParen(in_str){
	//regular expression pattern to specify a global search for the open parenthesis '(' character
	var open_paren_regexp =/\(/g;
	var num_open_paren = 0;
	
	//calls countChar function to count number of occurances of open parentheses
	num_open_paren = countChar(open_paren_regexp, in_str);
	
	return num_open_paren;
}

//Count number of closing parentheses ')' in string
function countCloseParen(in_str){
	//regular expression pattern to specify a global search for the close parenthesis ')' character
	var close_paren_regexp =/\)/g;
	var num_close_paren = 0;
	
	//calls countChar function to count number of occurances of close parentheses
	num_close_paren = countChar(close_paren_regexp, in_str);
	
	return num_close_paren;
}

//Count number of occurances of the character specified by the regular expression pattern
function countChar(in_regexp_patt, in_str){
	var num_char = 0;	
	
	//counts the number of occurances of the character specified by the regular expression pattern
	/*     NOTE: the .match() function returns null if no match is found. Since that would result
	           in the .length() function throwing a TypeError if called on a null value, "||[]"
	           is included after the .match() function call. This causes .match() to return
	           a 0 length array instead of null if no match is found, which allows .length()
	           to return a count of '0' instead of throwing a TypeError. */
	try{
		num_char = (in_str.match(in_regexp_patt)||[]).length;
	}catch(e){
		switch(e.name){
		case "TypeError":
			num_char = 0;
			break;
		default:
			throw e;
			num_char = 0;
			break;
		}
	}
	
	return num_char;
}

//#ISSUE 138 Fix Start
//Resetting the Checked values to avoid saving duplicate consents 
function clearConsent(form){	
	$("form input").each(function(){
		if($(this).prop("checked")==true){
			$(this).prop('checked', false);
		}
	});		
	
}


//#ISSUE 138 Fix End
function populateModalData(jsonObj, isProviderAdminUser) {
	//TODO (MH): Add try/catch block for JSON.parse
	var validationDataObj = JSON.parse(jsonObj);
	
	var selected_authorized_providers_ary = validationDataObj.selectedAuthorizedProviders;
	var selected_discloseTo_providers_ary = validationDataObj.selectedDiscloseToProviders;
	var selected_purposeOf_use_ary = validationDataObj.selectedPurposeOfUse;
	
	var existing_authorized_providers_ary = validationDataObj.existingAuthorizedProviders;
	var existing_discloseTo_providers_ary = validationDataObj.existingDiscloseToProviders;
	var existing_purposeOf_use_ary = validationDataObj.existingPurposeOfUse;
	
	var selected_authorized_providers_string = "";
	var selected_discloseTo_providers_string = "";
	var selected_purposeOf_use_string = "";
	
	try{
		selected_authorized_providers_string = arrayToUlString(selected_authorized_providers_ary);
		selected_discloseTo_providers_string = arrayToUlString(selected_discloseTo_providers_ary);
		selected_purposeOf_use_string = arrayToUlString(selected_purposeOf_use_ary);
	}catch(err){
		if(err.name === "RangeError"){
			selected_authorized_providers_string = "<ul><li>ERROR DISPLAYING DATA</li></ul>";
			selected_discloseTo_providers_string = "<ul><li>ERROR DISPLAYING DATA</li></ul>";
			selected_purposeOf_use_string = "<ul><li>ERROR DISPLAYING DATA</li></ul>";
		}
	}
	
	var selected_consent_start_date_string = validationDataObj.selectedConsentStartDate;
	var selected_consent_end_date_string = validationDataObj.selectedConsentEndDate;
	
	var existing_authorized_providers_string = "";
	var existing_discloseTo_providers_string = "";
	var existing_purposeOf_use_string = "";
	
	try{
		existing_authorized_providers_string = arrayToUlString(existing_authorized_providers_ary);
		existing_discloseTo_providers_string = arrayToUlString(existing_discloseTo_providers_ary);
		existing_purposeOf_use_string = arrayToUlString(existing_purposeOf_use_ary);
	}catch(err){
		existing_authorized_providers_string = "<ul><li>ERROR DISPLAYING DATA</li></ul>";
		existing_discloseTo_providers_string = "<ul><li>ERROR DISPLAYING DATA</li></ul>";
		existing_purposeOf_use_string = "<ul><li>ERROR DISPLAYING DATA</li></ul>";
	}
	
	var existing_consent_start_date_string = validationDataObj.existingConsentStartDate;
	var existing_consent_end_date_string = validationDataObj.existingConsentEndDate;
	var existing_consent_status_string = validationDataObj.existingConsentStatus;
	
	var existing_consent_id = validationDataObj.existingConsentId;
	
	var selectedConsentPanelElement = $('div#consent_validation_modal form#frm_consent_validation_form > div#pnl_selected_consent');
	var existingConsentPanelElement = $('div#consent_validation_modal form#frm_consent_validation_form > div#pnl_existing_consent');
	
	selectedConsentPanelElement.find('div#selected_authorized_providers > span.selected-consent-field-data#selected_authorized_providers_data').empty().html(selected_authorized_providers_string);
	selectedConsentPanelElement.find('div#selected_discloseTo_providers > span.selected-consent-field-data#selected_discloseTo_providers_data').empty().html(selected_discloseTo_providers_string);
	selectedConsentPanelElement.find('div#selected_purposeOf_use > span.selected-consent-field-data#selected_purposeOf_use_data').empty().html(selected_purposeOf_use_string);
	selectedConsentPanelElement.find('div#selected_consent_start_date > span.selected-consent-field-data#selected_consent_start_date_data').text(selected_consent_start_date_string);
	selectedConsentPanelElement.find('div#selected_consent_end_date > span.selected-consent-field-data#selected_consent_end_date_data').text(selected_consent_end_date_string);
	
	existingConsentPanelElement.find('div#existing_authorized_providers > span.existing-consent-field-data#existing_authorized_providers_data').empty().html(existing_authorized_providers_string);
	existingConsentPanelElement.find('div#existing_discloseTo_providers > span.existing-consent-field-data#existing_discloseTo_providers_data').empty().html(existing_discloseTo_providers_string);
	existingConsentPanelElement.find('div#existing_purposeOf_use > span.existing-consent-field-data#existing_purposeOf_use_data').empty().html(existing_purposeOf_use_string);
	existingConsentPanelElement.find('div#existing_consent_start_date > span.existing-consent-field-data#existing_consent_start_date_data').text(existing_consent_start_date_string);
	existingConsentPanelElement.find('div#existing_consent_end_date > span.existing-consent-field-data#existing_consent_end_date_data').text(existing_consent_end_date_string);
	existingConsentPanelElement.find('div#existing_consent_status > span.existing-consent-field-data#existing_consent_status_data').text(existing_consent_status_string);
	
	//Bind new click event handler to "View Existing Consent" button
	$('div#pnl_existing_consent button#btn_view_existing_consent').on("click", function(e){
		e.preventDefault();
		
		if(isProviderAdminUser === true){
			var patientId = $('input#patientId').val();
			window.location.href="adminPatientView.html?duplicateconsent=" + existing_consent_id + "&id=" + patientId;
		}else{
			window.location.href="listConsents.html?duplicateconsent=" + existing_consent_id + "#jump_consent_" + existing_consent_id;
		}
	});
}
