var cc_paginationInfo = {
};

var pagedUrl;

var conceptCodeListDataStore = (function() {
	var ary_conceptCodeList = new Array();
	
	return {
		pushConceptCode: function(in_conceptcode_id, in_conceptcode_code, in_conceptcode_name, in_conceptcode_cs_name, in_conceptcode_cs_vs_name){
			var tempConceptCode = new Array();
			tempConceptCode['conceptcode_id'] = in_conceptcode_id;
			tempConceptCode['conceptcode_code'] = in_conceptcode_code;
			tempConceptCode['conceptcode_name'] = in_conceptcode_name;
			tempConceptCode['conceptcode_cs_name'] = in_conceptcode_cs_name;
			tempConceptCode['conceptcode_cs_vs_name'] = in_conceptcode_cs_vs_name;
			tempConceptCode['conceptcode_valsets_ary'] = new Array();
			
			ary_conceptCodeList.push(tempConceptCode);
			return true;
		},
		pushConceptCodeValSetByConceptCodeId: function(in_conceptcode_id, in_valset_key, in_valset_name){
			var ary_length = ary_conceptCodeList.length;
			var found_index = null;
			for (var i = 0; i < ary_length; i++){
				var tempConceptCode = ary_conceptCodeList.slice(i, i + 1);
				tempConceptCode = tempConceptCode[0];
				if(tempConceptCode['conceptcode_id'] == in_conceptcode_id){
					found_index = i;
					break;
				}
			}
			
			if(found_index === null){
				//TODO (MH): Catch this error in code that calls this function
				throw new TypeError("found_index variable was null in pushConceptCodeValSetByConceptCodeId method.");
			}else{
				if((in_valset_key === null) || (in_valset_key === undefined)){
					//TODO (MH): Catch this error in code that calls this function
					throw new TypeError("in_valset_key was either null or undefined in pushConceptCodeValSetByConceptCodeId method.");
				}
				
				if((in_valset_name === null) || (in_valset_name === undefined)){
					//TODO (MH): Catch this error in code that calls this function
					throw new TypeError("in_valset_name was either null or undefined in pushConceptCodeValSetByConceptCodeId method.");
				}
				
				var tempAry = new Array();
				tempAry['valset_key'] = in_valset_key;
				tempAry['valset_name'] = in_valset_name;
				
				try{
					ary_conceptCodeList[found_index]['conceptcode_valsets_ary'].push(tempAry);
				}catch(e){
					console.log("ERROR: An unknown error occured in pushConceptCodeValSetByConceptCodeId method.");
					//TODO (MH): Catch this error in code that calls this function
					throw e;
				}
				return true;
			}
		},
		getArySize: function(){
			var out_length = ary_conceptCodeList.length;
			return out_length;
		},
		getConceptCodeByIndex: function(in_index){
			var out_conceptcode = {};
			if((in_index >= 0) && (in_index < ary_conceptCodeList.length)){
				var temp_conceptcode = ary_conceptCodeList.slice(in_index, in_index + 1);
				out_conceptcode = deepCopy(temp_conceptcode[0]);
			}
			return out_conceptcode;
		},
		getConceptCodeValSetsAryByConceptCodeId: function(in_conceptcode_id){
			var ary_length = ary_conceptCodeList.length;
			var found_index = null;
			for (var i = 0; i < ary_length; i++){
				var tempConceptCode = ary_conceptCodeList.slice(i, i + 1);
				tempConceptCode = tempConceptCode[0];
				if(tempConceptCode['conceptcode_id'] == in_conceptcode_id){
					found_index = i;
					break;
				}
			}
			
			if(found_index === null){
				//TODO (MH): Catch this error in code that calls this function
				throw new TypeError("found_index variable was null in getConceptCodeValSetsAryByConceptCodeId method.");
			}else{
				var concept_code_record = ary_conceptCodeList.slice(found_index, found_index + 1);
				var out_ary = new Array();
				out_ary = deepCopy(concept_code_record[0]['conceptcode_valsets_ary']);
				return out_ary;
			}
		},
		removeConceptCodeByIndex: function(in_index){
			if((in_index >= 0) && (in_index < ary_conceptCodeList.length)){
				ary_conceptCodeList.splice(in_index, 1);
			}
		},
		removeConceptCodeByConceptCodeId: function(in_conceptcode_id){
			var ary_length = ary_conceptCodeList.length;
			var found_index = null;
			for (var i = 0; i < ary_length; i++){
				var tempConceptCode = ary_conceptCodeList.slice(i, i + 1);
				tempConceptCode = tempConceptCode[0];
				if(tempConceptCode['conceptcode_id'] == in_conceptcode_id){
					found_index = i;
					break;
				}
			}
			
			if(found_index === null){
				//TODO (MH): Catch this error in code that calls this function
				throw new TypeError("found_index variable was null in removeConceptCodeByConceptCodeId method.");
			}else{
				ary_conceptCodeList.splice(found_index, 1);
				return found_index;
			}
		},
		emptyConceptCodeArray: function(){
			ary_conceptCodeList = new Array();
		},
		getAllConceptCodes: function(){
			var tempAry = deepCopy(ary_conceptCodeList);
			return tempAry;
		}
	};
})();

var filteredConceptCodeListIndex = (function(){
	var ary_filteredConceptCodeList = new Array();
	
	return {
		pushCodeIndex: function(in_index){
			ary_filteredConceptCodeList.push(in_index);
		},
		getCodeIndex: function(in_pos){
			var out_code = {};
			if((in_pos >= 0) && (in_pos < ary_filteredConceptCodeList.length)){
				var temp_code = ary_filteredConceptCodeList.slice(in_pos, in_pos + 1);
				out_code = deepCopy(temp_code[0]);
			}
			return out_code;
		},
		getAllCodeIndexes: function(){
			var tempAry = deepCopy(ary_filteredConceptCodeList);
			return tempAry;
		},
		getArySize: function(){
			var out_length = ary_filteredConceptCodeList.length;
			return out_length;
		},
		removeCodeIndex: function(in_pos){
			if((in_pos >= 0) && (in_pos < ary_filteredConceptCodeList.length)){
				ary_filteredConceptCodeList.splice(in_pos, 1);
			}
		},
		removeCodeIndexByCodeIndex: function(in_index){
			var ary_len = ary_filteredConceptCodeList.length;
			var found_pos = null;
			
			for(var i = 0; i < ary_len; i++){
				var tempCodeIndex = ary_filteredConceptCodeList[i];
				if(tempCodeIndex == in_index){
					found_pos = i;
					break;
				}
			}
			
			if(found_pos === null){
				//TODO (MH): Catch this error in code that calls this function
				throw new TypeError("found_pos variable was null in removeCodeIndexByCodeIndex method.");
			}else{
				ary_filteredConceptCodeList.splice(found_pos, 1);
				return found_pos;
			}
		},
		emptyCodeIndexArray: function(){
			ary_filteredConceptCodeList = new Array();
		}
	};
	
})();

var lastValsetModalState = (function(){
	var ary_lastModalState = new Array();
	
	return {
		pushValsetId: function(in_valsetId){
			ary_lastModalState.push(in_valsetId);
		},
		isValsetIdInAry: function(in_valsetId){
			var arylen = ary_lastModalState.length;
			var isFound = false;
			
			for(var i = 0; i < arylen; i++){
				if(ary_lastModalState[i] == in_valsetId){
					isFound = true;
					break;
				}
			}
			
			return isFound;
		},
		emptyValsetModalStateAry: function(){
			ary_lastModalState = new Array();
		},
		getAllValsetIdsInAry: function(){
			var tempAry = deepCopy(ary_lastModalState);
			return tempAry;
		}
	};
	
})();

$(document).ready(function(){
	var pageLength = 20;
	var divPanelState = $('div#div_panelStateInfo').data('panel-state');
	
	if(divPanelState == "resetoptions"){
		$('div#collapseTwo').addClass("in");
	}else if(divPanelState == "addnew"){
		$('div#collapseOne').addClass("in");
	}
	
	initPopovers();
	
	$('form').on("click", "button", function(e){
		e.preventDefault();
	});
	
	//loadAllConceptCodes();
	loadInitialConceptCodes(0);
	
	showTable();
	
	/**
	 * Bind event handlers:
	 */
	
	//delegated event handler for click events on pagination icons
	$('div#conceptcode_pagination_holder > ul.pagination').on("click", "li.pagination-li > span.pagination-icon", function(e){
		e.stopPropagation();
		
		var clickedIcon = $(this);
		var clickedParent = clickedIcon.parent("li.pagination-li");

		if(clickedParent.hasClass("pagination-page")){
			var target_pagenum = clickedIcon.data("pagenum"); 
			loadPageConceptCodes(target_pagenum - 1);
			gotoTargetPage(target_pagenum);
		}else if((clickedParent.is("#pagination_next")) && (clickedParent.hasClass("disabled") === false)){
			loadPageConceptCodes(getCurrentPagenum());
			gotoNextPage();
		}else if((clickedParent.is("#pagination_prev")) && (clickedParent.hasClass("disabled") === false)){
			loadPageConceptCodes(getCurrentPagenum() - 2);
			gotoPrevPage();
		}else if((clickedParent.is("#pagination_first")) && (clickedParent.hasClass("disabled") === false)){
			loadPageConceptCodes(0);
			jumpToFirstPagegroup();
			gotoFirstPage();
		}else if((clickedParent.is("#pagination_last")) && (clickedParent.hasClass("disabled") === false)){
			var numResults = $("#pagination_numresults").text();
			loadPageConceptCodes(Math.ceil(numResults/pageLength) - 1);
			jumpToLastPagegroup();
			gotoLastPage();
		}
	});
	
	//delegated event handler for click events on delete buttons to delete concept codes
	$('table#current_conceptcodes_table > tbody').on("click", "tr.conceptcode-record .delete-record-trigger", function(evt){
		var isConfirmed = confirm('Are you sure you want to delete this concept code?');
		
		if(isConfirmed === true){
			var clickedElement = $(this);
			var clickedConceptCodeId = $(this).parents("tr").data("conceptcode-id");
			
			$.ajax({url: "conceptCode/delete/" + clickedConceptCodeId,
					type: "DELETE",
					success: function(response){
						rebuildPagination(cc_paginationInfo.totalPages);
						loadPageConceptCodes(getPageToLoadAfterDelete());
						setNumResultsDisplay(cc_paginationInfo.totalNumberOfConceptCodes - 1);
					},
					error: function(jqXHRobj, err, errThrown) {
						console.log("ERROR: Unable to delete concept code.");
						console.log("     Server returned HTTP Status Code: " + errThrown);
						console.log("     Error details: " + jqXHRobj.responseText);
						window.alert("ERROR: Unable to delete concept code.");
					}
			});

			
		}
	});
	
	
//	$('table#current_conceptcodes_table').on("recordDeleted.c2s_ui.data_display", function(evt){
//		try{
//			//rebuildTableFromAry();
//		}catch(err){
//			if(err.name == "RangeError"){
//				if(err.message.search("Sanity Check Failed:") > -1){
//					var new_cur_page = cur_page - 1;
//					
//					//Sanity Check
//					if(checkIsPage(new_cur_page) !== true){
//						//rethrow original error
//						throw err;
//					}else{
//						try{
//							setPaginationActiveClass(new_cur_page);
//							rebuildTableFromAry();
//						}catch(err2){
//							//set pagination active class back to original value
//							setPaginationActiveClass(cur_page);
//							//rethrow original error
//							throw err;
//						}
//					}
//				}
//			}
//		}
//		var cur_page = getCurrentPagenum();
//		try{
//			rebuildPagination(null, cur_page);
//		}catch(err){
//			if(err.name == "TypeError"){
//				
//				if(err.message.search("Invalid pagenum_to_set") > -1){
//					rebuildPagination(1, 1);
//				}else{
//					//rethrow error
//					throw err;
//				}
//			}
//		}
//	});
	
	//event handler for change events on BATCH UPLOAD batch_select_codesys dropdown
	$('select#batch_select_codesys').change(function(evt){
		var selCodeSysName = $(this).val();
		
		resetBatchSelectVersionDropdown();
		
		if(selCodeSysName != ""){
			setSelectBatchCsVsIsEnabled(true);
			
			$('input.batch_cs_version_data').each(function(){
				if($(this).data('cs-name') == selCodeSysName){
					var tempVal = $(this).data('cs-version-value');
					var tempKey = $(this).data('cs-version-key');
					appendOptionToSelect("batch_select_version", tempKey, tempVal);
				}
			});
		}else{
			setSelectBatchCsVsIsEnabled(false);
		}
	});
	
	//event handler for change events on select_filter_on_cs dropdown
	$('select#select_filter_on_cs').change(function(evt){
		
		var selCodeSysName = $(this).val();
	
		resetSelectFilterOnCsVs();
		resetSelectFilterOnValSetName();
	
		if(selCodeSysName != ""){
			setSelectCsVsIsEnabled(true);
			
			$('input.cs_version_data').each(function(){
				if($(this).data('cs-name') == selCodeSysName){
					var tempVal = $(this).data('cs-version-value');
					appendOptionToSelect("select_filter_on_cs_vs", tempVal, tempVal);
				}
			});
			
			populateValueSetsByCodeSystem();
			
			//filterCodeSysNameFromSelect();
			
			//Change table header text
			setHeaderForFilter();
			
			filterWithPagination();
		}else{
			setSelectCsVsIsEnabled(false);
			
			//Change table header text
			setHeaderForNoFilter();
		}
		
		
//		
//		//Hide table while rebuilding
//		hideTable();
//		
//		resetSelectFilterOnCsVs();
//		resetSelectFilterOnValSetName();
//		
//		//Filter array
//	//	resetFilterArray();
//		
//		if(selCodeSysName != ""){
//			setSelectCsVsIsEnabled(true);
//			
//			$('input.cs_version_data').each(function(){
//				if($(this).data('cs-name') == selCodeSysName){
//					var tempVal = $(this).data('cs-version-value');
//					appendOptionToSelect("select_filter_on_cs_vs", tempVal, tempVal);
//				}
//			});
//			
//			filterCodeSysNameFromSelect();
//			
//			//Change table header text
//			setHeaderForFilter();
//		}else{
//			setSelectCsVsIsEnabled(false);
//			
//			//Change table header text
//			setHeaderForNoFilter();
//		}
//		
//		//Clear and rebuild table
//		//initTableFromAry();
//		loadInitialConceptCodes(0);
//		
//		//Show table once rebuild complete
//		showTable();
	});
	
	//event handler for change events on select_filter_on_cs_vs dropdown
	$('select#select_filter_on_cs_vs').change(function(evt){
		
		resetSelectFilterOnValSetName();
		
		populateValueSetsByCodeSystem();
		
		filterWithPagination();
//		
//		var selCodeSysVersionName = $(this).val();
//		var selCodeSysName = $('select#select_filter_on_cs').val();
//		
//		//Hide table while rebuilding
//		hideTable();
//		
//		resetSelectFilterOnValSetName();
//		
//		//Filter array
//		resetFilterArray();
//		
//		//Reapply previous filter
//		filterCodeSysNameFromSelect();
//		
//		if(selCodeSysVersionName != ""){
//			filterCodeSysVersionNameFromSelect();
//			
//			//Change table header text
//			setHeaderForFilter();
//		}else{
//			if(selCodeSysName == ""){
//				setHeaderForNoFilter();
//			}
//		}
//		
//		//Clear and rebuild table
//		initTableFromAry();
//		
//		//Show table once rebuild complete
//		showTable();
	});
	
	//event handler for change events on select_filter_on_valset_name dropdown
	$('select#select_filter_on_valset_name').change(function(evt){
		
		filterCodeSysNameFromSelect();
		filterCodeSysVersionNameFromSelect();
		
		filterWithPagination();
		
//		var selValSetNameId = $(this).val();
//		var selCodeSysName = $('select#select_filter_on_cs').val();
//		
//		//Hide table while rebuilding
//		hideTable();
//		
//		//Filter array
//		resetFilterArray();
//		
//		//Reapply previous filters
//		filterCodeSysNameFromSelect();
//		filterCodeSysVersionNameFromSelect();
//		
//		if(selValSetNameId != ""){
//			filterValueSetNameFromSelect();
//			
//			//Change table header text
//			setHeaderForFilter();
//		}else{
//			if(selCodeSysName == ""){
//				setHeaderForNoFilter();
//			}
//		}
//		
//		//Clear and rebuild table
//		initTableFromAry();
//		
//		//Show table once rebuild complete
//		showTable();
	});

	//event handler for click event on reset button
	$('#btn_search_conceptcode_reset').click(function(e){
		window.location.href = "conceptCodeList.html?panelState=resetoptions";
	});
	
	//event handler for click event on search by name search button
	$('#btn_search_conceptcode_name_submit').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var in_searchParam = $('input#txt_search_conceptcode_name').val();
		
		if(in_searchParam.length > 0){
			var codeSystemSelected = ($('select#select_filter_on_cs').val() != "" ? $('select#select_filter_on_cs').val() : "empty");
			var codeSystemVersionSelected = ($('select#select_filter_on_cs_vs').val() != "" ? $('select#select_filter_on_cs_vs').val() : "empty");
			var valueSetNameSelected = ($('select#select_filter_on_valset_name  :selected').text().indexOf("Value Set Name") >= 0 ? "empty" : $('select#select_filter_on_valset_name  :selected').text());

			pagedUrl = "conceptCode/ajaxSearchConceptCode/pageNumber/pageNumberPlaceHolder/searchCategory/name/searchTerm/"+in_searchParam+"/codeSystem/"+codeSystemSelected+"/codeSystemVersion/"+codeSystemVersionSelected+"/valueSetName/"+valueSetNameSelected;
			
			$.ajax({url: "conceptCode/ajaxSearchConceptCode/pageNumber/0/searchCategory/name/searchTerm/"+in_searchParam+"/codeSystem/"+codeSystemSelected+"/codeSystemVersion/"+codeSystemVersionSelected+"/valueSetName/"+valueSetNameSelected,

				method: "GET",
				success: function(response){

					var totalNumberOfConceptCodes = response.totalNumberOfConceptCodes;
					var totalPages = response.totalPages;
					var conceptCodes = response.conceptCodes;
					var numberOfElements = response.numberOfElements;
					
					setPaginationInfo(totalNumberOfConceptCodes, totalPages, conceptCodes, numberOfElements);
					
					clearTable();
					
					if(totalNumberOfConceptCodes > 0){
						
						for(var i = 0; i < numberOfElements; i++){
							
							var temp_conceptcode_id = conceptCodes[i].id;
							var temp_conceptcode_code = conceptCodes[i].code;
							var temp_conceptcode_name = conceptCodes[i].name;
							var temp_conceptcode_cs_name = conceptCodes[i].codeSystemName;
							var temp_conceptcode_cs_vs_name = conceptCodes[i].codeSystemVersionName;
							var temp_conceptcode_valsets_ary = new Array();

							temp_conceptcode_valsets_ary = conceptCodes[i].valueSetMap;
							insertTableRow(temp_conceptcode_id, temp_conceptcode_code, temp_conceptcode_name, temp_conceptcode_cs_name, temp_conceptcode_cs_vs_name, temp_conceptcode_valsets_ary);
						}
						
						clearSearchConceptCodeCodeInput();
						
						//Change table header text
						setHeaderForSearchName();
						
						rebuildPagination(totalPages);
						setNumResultsDisplay(totalNumberOfConceptCodes);
					}else{
						window.alert("No results found");
						setNumResultsDisplay(0);
						rebuildPagination(1);
					}
				},
				error: function(e){
					window.alert("ERROR - An error occured while search for concept code by code: " + e.responseText);
				}
		});
		}
	});
	
	//event handler for click event on search by code search button
	$('#btn_search_conceptcode_code_submit').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var in_searchParam = $('input#txt_search_conceptcode_code').val();
		
		if(in_searchParam.length > 0){
			var codeSystemSelected = ($('select#select_filter_on_cs').val() != "" ? $('select#select_filter_on_cs').val() : "empty");
			var codeSystemVersionSelected = ($('select#select_filter_on_cs_vs').val() != "" ? $('select#select_filter_on_cs_vs').val() : "empty");
			var valueSetNameSelected = ($('select#select_filter_on_valset_name  :selected').text().indexOf("Value Set Name") >= 0 ? "empty" : $('select#select_filter_on_valset_name  :selected').text());

			pagedUrl = "conceptCode/ajaxSearchConceptCode/pageNumber/pageNumberPlaceHolder/searchCategory/code/searchTerm/"+in_searchParam+"/codeSystem/"+codeSystemSelected+"/codeSystemVersion/"+codeSystemVersionSelected+"/valueSetName/"+valueSetNameSelected;
			
			$.ajax({url: "conceptCode/ajaxSearchConceptCode/pageNumber/0/searchCategory/code/searchTerm/"+in_searchParam+"/codeSystem/"+codeSystemSelected+"/codeSystemVersion/"+codeSystemVersionSelected+"/valueSetName/"+valueSetNameSelected,
					method: "GET",
					success: function(response){

						var totalNumberOfConceptCodes = response.totalNumberOfConceptCodes;
						var totalPages = response.totalPages;
						var conceptCodes = response.conceptCodes;
						var numberOfElements = response.numberOfElements;
						
						setPaginationInfo(totalNumberOfConceptCodes, totalPages, conceptCodes, numberOfElements);
						
						clearTable();
						
						if(totalNumberOfConceptCodes > 0){
							
							for(var i = 0; i < numberOfElements; i++){
								
								var temp_conceptcode_id = conceptCodes[i].id;
								var temp_conceptcode_code = conceptCodes[i].code;
								var temp_conceptcode_name = conceptCodes[i].name;
								var temp_conceptcode_cs_name = conceptCodes[i].codeSystemName;
								var temp_conceptcode_cs_vs_name = conceptCodes[i].codeSystemVersionName;
								var temp_conceptcode_valsets_ary = new Array();

								temp_conceptcode_valsets_ary = conceptCodes[i].valueSetMap;
								insertTableRow(temp_conceptcode_id, temp_conceptcode_code, temp_conceptcode_name, temp_conceptcode_cs_name, temp_conceptcode_cs_vs_name, temp_conceptcode_valsets_ary);
							}
						
							clearSearchConceptCodeNameInput();
							
							//Change table header text
							setHeaderForSearchCode();
							
							rebuildPagination(totalPages);
							setNumResultsDisplay(totalNumberOfConceptCodes);
						}else{
							window.alert("No results found");
							setNumResultsDisplay(0);
							rebuildPagination(1);							
						}
					},
					error: function(e){
						window.alert("ERROR - An error occured while search for concept code by code: " + e.responseText);
					}
			});
		}
	});
	
	/* event handler for hide.bs.modal event on valueSetName-modal to
	   display selected value set names when the modal is closed */
	$('div#valueSetName-modal').on("hide.bs.modal", function(){
		handleLastValsetModalStates();
	});
	
	/* event handler for show.bs.modal event on valueSetName-modal to
	   display selected value set names when the modal is loaded */
	$('div#valueSetName-modal').on("show.bs.modal", function(){
		handleLastValsetModalStates();
	});
	
	/* event handler for when btn_save_valuesetname is clicked
	   to save selected value set names from modal */
	$('div#valueSetName-modal button#btn_save_valuesetname').click(function(){
		storeValsetModalState();
	});
	
	/**
	 * Initialize jquery validate plugin to validate batch concept code upload form
	 */
	$("#batch_conceptcode_upload_form").validate({
	  ignore: [],  // <-- allows for validation of hidden fields
      rules: {
          valueSetIds: {
              required: true
          },
          batch_file: { 
        	  required: true,
        	  extension: "xlsx"
          }
      },
      errorPlacement: function(error, element) {
	     if (element.attr("name") == "valueSetIds") {
	       error.insertAfter("#selected_valusets_name_display_holder");
	     } else if (element.attr("name") == "batch_file") {
	       error.insertAfter("#file_err_msg");
	     } else {
	       error.insertAfter(element);
	     }
	  },
	  errorClass: 'valueSetErrMsg'
	});
	
	resetFilterArray();
	initTableFromAry();
	
}); //End of document.ready function

/**
 * Load all concept code records from sever via AJAX
 */
function loadAllConceptCodes(){
	var responseObj = {};
	
	$.ajax({url: "conceptCode/ajaxGetAllConceptCodes",
		    success: function(response){
		    	responseObj = response;
		    	
				var responseLength = responseObj.length;
				
				if(responseLength > 0){
					conceptCodeListDataStore.emptyConceptCodeArray();
					
					for(var i = 0; i < responseLength; i++){
						try{
							storeConceptCodeRecord(responseObj[i]);
						}catch(e){
							console.log("ERROR: An unknown error has occured while attempting to call storeConceptCodeRecord() inside AJAX call to ajaxSearchConceptCode");
							console.log("      Error details: " + e);
							window.alert("ERROR: An unknown error has occured.");
						}
					}
					clearSearchConceptCodeNameInput();
					
					resetSelectFilterOnCs();
					resetSelectFilterOnCsVs();
					resetSelectFilterOnValSetName();
					
					//Reset Filter
					resetFilterArray();
					
					initTableFromAry();
				}
		    },
		    error: function(err){
		    	window.alert("ERROR: " + err.responseText);}
		    }
	);
}


function loadInitialConceptCodes(pageNumber){
	
	$.ajax({url: "conceptCode/ajaxGetPagedConceptCodes/pageNumber/" + pageNumber,
		    success: function(response){

				var totalNumberOfConceptCodes = response.totalNumberOfConceptCodes;
				var totalPages = response.totalPages;
				var conceptCodes = response.conceptCodes;
				var numberOfElements = response.numberOfElements;
				
				setPaginationInfo(totalNumberOfConceptCodes, totalPages, conceptCodes, numberOfElements);
				
				if(totalNumberOfConceptCodes > 0){
					pagedUrl = "conceptCode/ajaxGetPagedConceptCodes/pageNumber/pageNumberPlaceHolder";
					
					for(var i = 0; i < numberOfElements; i++){
						
						var temp_conceptcode_id = conceptCodes[i].id;
						var temp_conceptcode_code = conceptCodes[i].code;
						var temp_conceptcode_name = conceptCodes[i].name;
						var temp_conceptcode_cs_name = conceptCodes[i].codeSystemName;
						var temp_conceptcode_cs_vs_name = conceptCodes[i].codeSystemVersionName;
						var temp_conceptcode_valsets_ary = new Array();

						temp_conceptcode_valsets_ary = conceptCodes[i].valueSetMap;
						insertTableRow(temp_conceptcode_id, temp_conceptcode_code, temp_conceptcode_name, temp_conceptcode_cs_name, temp_conceptcode_cs_vs_name, temp_conceptcode_valsets_ary);
					}
					
//					
//
//					clearSearchConceptCodeNameInput();
//					
//					resetSelectFilterOnCs();
//					resetSelectFilterOnCsVs();
//					resetSelectFilterOnValSetName();
//					
//					//Reset Filter
//					resetFilterArray();
//					
//					initTableFromAry();
					
					rebuildPagination(totalPages);
					setNumResultsDisplay(totalNumberOfConceptCodes);
				}
		    },
		    error: function(err){
		    	window.alert("ERROR: " + err.responseText);}
		    }
	);
}


function loadPageConceptCodes(pageNumber){
	// replace placeholder with page number
	pagedUrl = pagedUrl.replace("pageNumberPlaceHolder", pageNumber);

	$.ajax({url: pagedUrl,
		    success: function(response){

				var totalNumberOfConceptCodes = response.totalNumberOfConceptCodes;
				var totalPages = response.totalPages;
				var conceptCodes = response.conceptCodes;
				var numberOfElements = response.numberOfElements;
				
				setPaginationInfo(totalNumberOfConceptCodes, totalPages, conceptCodes, numberOfElements);
				
				clearTable();
				
				if(totalNumberOfConceptCodes > 0){
					
					for(var i = 0; i < numberOfElements; i++){
						
						var temp_conceptcode_id = conceptCodes[i].id;
						var temp_conceptcode_code = conceptCodes[i].code;
						var temp_conceptcode_name = conceptCodes[i].name;
						var temp_conceptcode_cs_name = conceptCodes[i].codeSystemName;
						var temp_conceptcode_cs_vs_name = conceptCodes[i].codeSystemVersionName;
						var temp_conceptcode_valsets_ary = new Array();

						temp_conceptcode_valsets_ary = conceptCodes[i].valueSetMap;
						insertTableRow(temp_conceptcode_id, temp_conceptcode_code, temp_conceptcode_name, temp_conceptcode_cs_name, temp_conceptcode_cs_vs_name, temp_conceptcode_valsets_ary);
					}
				}
				else{
					window.alert("No results found");
					setNumResultsDisplay(0);
					rebuildPagination(1);
				}
				// set back page number placeholder for next call 
				pagedUrl = pagedUrl.replace("pageNumber/"+pageNumber, "pageNumber/pageNumberPlaceHolder");
		    },
		    error: function(err){
		    	window.alert("ERROR: " + err.responseText);}
		    }
	);
}

function getPageToLoadAfterDelete() {
	if (getCurrentPagenum() === 1) {
		return 0;
	} else if (cc_paginationInfo.numberOfElements === 1) {
		return getCurrentPagenum() - 2;
	} else {
		return getCurrentPagenum() - 1;
	}

}

function setPaginationInfo(totalNumberOfConceptCodes, totalPages, conceptCodes, numberOfElements) {
	cc_paginationInfo.totalNumberOfConceptCodes = totalNumberOfConceptCodes;
	cc_paginationInfo.totalPages = totalPages;
	cc_paginationInfo.conceptCodes = conceptCodes;
	cc_paginationInfo.numberOfElements = numberOfElements;
}

/**
 * Initialize HTML table rows on initial page load & when filter/search changes
 */
function initTableFromAry(){
	//setPaginationActiveClass(1);
	//var num_pages = rebuildTableFromAry();
	//rebuildPagination(num_pages);
}

function storeConceptCodeRecord(in_conceptCodeRecord){
	var in_conceptcode_id = in_conceptCodeRecord.id;
	var in_conceptcode_code = in_conceptCodeRecord.code;
	var in_conceptcode_name = in_conceptCodeRecord.name;
	var in_conceptcode_cs_name = in_conceptCodeRecord.codeSystemName;
	var in_conceptcode_cs_vs_name = in_conceptCodeRecord.codeSystemVersionName;
	
	conceptCodeListDataStore.pushConceptCode(in_conceptcode_id, in_conceptcode_code, in_conceptcode_name, in_conceptcode_cs_name, in_conceptcode_cs_vs_name);
	
	var in_conceptcode_valsets_ary = new Array();
	in_conceptcode_valsets_ary = in_conceptCodeRecord.valueSetMap;
	
	for (curValSet in in_conceptcode_valsets_ary){
		var in_valset_key = curValSet;
		var in_valset_name = in_conceptcode_valsets_ary[curValSet];
	
		try{
			conceptCodeListDataStore.pushConceptCodeValSetByConceptCodeId(in_conceptcode_id, in_valset_key, in_valset_name);
		}catch(e){
			console.log("ERROR: An unknown error has occured while attempting to call conceptCodeListDataStore.pushConceptCodeValSetByConceptCodeId() inisde storeConceptCodeRecord() method");
			console.log("      Error details: " + e);
			window.alert("ERROR: An unknown error has occured.");
		}
	}
}

/**
 * Clear HTML table rows and rebuild page from stored arrays
 * 
 * @returns {integer} num_pages - the number of pages to show in the pagination control
 */
function rebuildTableFromAry(){
	//Set page_len to equal number of results to show per page
	//TODO (MH): Change this number to use a value read from a properties file
	var page_len = 20;
	
	var ary_len = filteredConceptCodeListIndex.getArySize();
	
	var num_pages = null;
	
	clearTable();
	
	if(ary_len > 0){
		var current_pagenum = getCurrentPagenum();
		
		var current_page_first_record_index = page_len * (current_pagenum - 1);
		var current_page_last_record_index = current_page_first_record_index + (page_len - 1);
		
		//Handle case where number of records on current page is less than the default page length
		if(current_page_last_record_index >= ary_len){
			current_page_last_record_index = ary_len - 1;
		}
		
		try{
			//Sanity Check
			if(current_page_first_record_index > current_page_last_record_index){
				throw new RangeError("Sanity Check Failed: " + current_page_first_record_index + " > " + current_page_last_record_index);
			}
		
			//Sanity Check
			if(current_page_first_record_index < 0){
				throw new RangeError();
			}
		
			//Sanity Check
			if(current_page_last_record_index < 0){
				throw new RangeError();
			}
		
			for(var i = current_page_first_record_index; i <= current_page_last_record_index; i++){
				var curRecIndex = filteredConceptCodeListIndex.getCodeIndex(i);
				
				var curRecord = conceptCodeListDataStore.getConceptCodeByIndex(curRecIndex);
				
				var temp_conceptcode_id = curRecord["conceptcode_id"];
				var temp_conceptcode_code = curRecord["conceptcode_code"];
				var temp_conceptcode_name = curRecord["conceptcode_name"];
				var temp_conceptcode_cs_name = curRecord["conceptcode_cs_name"];
				var temp_conceptcode_cs_vs_name = curRecord["conceptcode_cs_vs_name"];
				
				var temp_conceptcode_valsets_ary = new Array();
				
				try{
					temp_conceptcode_valsets_ary = conceptCodeListDataStore.getConceptCodeValSetsAryByConceptCodeId(temp_conceptcode_id);
				}catch(err){
					if(err.name == "TypeError"){
						console.log("ERROR: A TypeError was caught inside of for loop within the rebuildTableFromAry() method.");
						
						//FIXME (MH): Remove the following console.log lines of code when done testing
						console.log("Error message: " + err.message);
						console.log("Error stack trace: " + err.stack);
					}
					
					throw err;
				}
				
				insertTableRow(temp_conceptcode_id, temp_conceptcode_code, temp_conceptcode_name, temp_conceptcode_cs_name, temp_conceptcode_cs_vs_name, temp_conceptcode_valsets_ary);
			}
		}catch(err){
			if(err.name = "RangeError"){
				if(err.message.search("Sanity Check Failed:")){
					//rethrow error
					throw err;
				}
			}else{
				console.log("ERROR: Error caught in rebuildTableFromAry() method. Clearing out table and data...");
			
				console.log("    Clearing table...");
				clearTable();
				
				console.log("    Emptying conceptCodeListDataStore array...");
				conceptCodeListDataStore.emptyConceptCodeArray();
				
				console.log("    Emptying filteredConceptCodeListIndex array...");
				filteredConceptCodeListIndex.emptyCodeIndexArray();
				
				//FIXME (MH): Remove the following console.log lines of code when done with testing
				console.log("");
				console.log("  Error Type: " + err.name);
				console.log("  Error Message: " + err.message);
				console.log("  Error Stack Trace: " + err.stack);
				
				window.alert("ERROR: An unknown error has occured. Please reload the page.");
			}
		}
		
		setNumResultsDisplay(ary_len);
		
		//Calculate the number of pages for pagination to return from function
		num_pages = Math.ceil(ary_len / page_len);
	}else{
		setNumResultsDisplay(0);
		//Set num_pages to 1
		num_pages = 1;
	}
	
	//Trigger tableRebuilt event
	$("table#current_conceptcodes_table > tbody").trigger("tableRebuilt.c2s_ui.data_display");
	
	//Return the calculated number of pages for pagination
	return num_pages;
}

/**
 * Insert table row into HTML DOM
 * 
 * @param temp_conceptcode_id
 * @param temp_conceptcode_code
 * @param temp_conceptcode_name
 * @param temp_conceptcode_cs_name
 * @param temp_conceptcode_cs_vs_name
 * @param temp_conceptcode_valsets_ary
 */
function insertTableRow(temp_conceptcode_id, temp_conceptcode_code, temp_conceptcode_name, temp_conceptcode_cs_name, temp_conceptcode_cs_vs_name, temp_conceptcode_valsets_ary){
	var valset_string = "", temp_valset_key, temp_valset_name;
	
	for(i in temp_conceptcode_valsets_ary){
		if (temp_conceptcode_valsets_ary.hasOwnProperty(i)) {
			temp_valset_key = i;
			temp_valset_name = temp_conceptcode_valsets_ary[i];
			
			valset_string = valset_string + "<div class='conceptcode-valset-record' data-conceptcode-valset-key='" + i + "' data-conceptcode-valsetcat-code='" + temp_valset_name + "' id='conceptcode_valset_data_" + temp_conceptcode_id + "_" + temp_valset_key + "'>" +
			"<span>" + temp_valset_name + "</span>" + "</div>";
			
		}
	}

	$('table#current_conceptcodes_table > tbody').append("<tr class='conceptcode-record' data-conceptcode-cs-name='" + temp_conceptcode_cs_name + "' data-conceptcode-id='" + temp_conceptcode_id + "' data-conceptcode-cs-vs-name='" + temp_conceptcode_cs_vs_name + "' data-conceptcode-name='" + temp_conceptcode_name + "' data-conceptcode-code='" + temp_conceptcode_code + "'>" +
			"<td>" +
				"<span>" +
					"<span class='delete-record-trigger btn btn-danger btn-xs'>" +
						"<span class='fa fa-minus fa-white'></span>" +
					"</span>" +
				"</span>" +
			"</td>" +
			"<td>" +
				"<a href='conceptCode/edit/" + temp_conceptcode_id + "' >" +
					"<span>" + temp_conceptcode_code + "</span>" +
				"</a>" +
			"</td>" +
			"<td>" + temp_conceptcode_name + "</td>" +
			"<td>" + valset_string + "</td>" +
			"<td>" + temp_conceptcode_cs_name + "    :   " + temp_conceptcode_cs_vs_name + "</td>" +
		"</tr>");
	
}


function deleteConceptCode(clickedElementParentTr){
	var cur_conceptcode_id = clickedElementParentTr.data("conceptcode-id");
	
	try{
		clickedElementParentTr.trigger("recordDeleting.c2s_ui.data_display", {concept_code_id: cur_conceptcode_id});
		
//		conceptCodeListDataStore.removeConceptCodeByConceptCodeId(cur_conceptcode_id);
		//filteredConceptCodeListIndex.removeCodeIndexByCodeIndex(conceptCodeIndex);
		resetFilterArray();
		filterCodeSysNameFromSelect();
		filterCodeSysVersionNameFromSelect();
		filterValueSetNameFromSelect();
		
		$("table#current_conceptcodes_table > tbody").trigger("recordDeleted.c2s_ui.data_display", {concept_code_id: cur_conceptcode_id});
	}catch(err){
		//FIXME (MH): Catch & handle certain types of specific errors here; rethrow errors when appropriate
		throw err;
	}
}


function filterWithPagination() {
	var in_searchParam = "empty";
	var searchCategory = "name";
	if ($('input#txt_search_conceptcode_code').val() != "") {
		in_searchParam = $('input#txt_search_conceptcode_code').val();
		searchCategory = "code";
	} else if ($('input#txt_search_conceptcode_name').val()) {
		in_searchParam = $('input#txt_search_conceptcode_name').val();
	}
	
	if(in_searchParam.length > 0){
		var codeSystemSelected = ($('select#select_filter_on_cs').val() != "" ? $('select#select_filter_on_cs').val() : "empty");
		var codeSystemVersionSelected = ($('select#select_filter_on_cs_vs').val() != "" ? $('select#select_filter_on_cs_vs').val() : "empty");
		var valueSetNameSelected = ($('select#select_filter_on_valset_name  :selected').text().indexOf("Value Set Name") >= 0 ? "empty" : $('select#select_filter_on_valset_name  :selected').text());

		pagedUrl = "conceptCode/ajaxSearchConceptCode/pageNumber/pageNumberPlaceHolder/searchCategory/"+searchCategory+"/searchTerm/"+in_searchParam+"/codeSystem/"+codeSystemSelected+"/codeSystemVersion/"+codeSystemVersionSelected+"/valueSetName/"+valueSetNameSelected;
		
		$.ajax({url: "conceptCode/ajaxSearchConceptCode/pageNumber/0/searchCategory/"+searchCategory+"/searchTerm/"+in_searchParam+"/codeSystem/"+codeSystemSelected+"/codeSystemVersion/"+codeSystemVersionSelected+"/valueSetName/"+valueSetNameSelected,

			method: "GET",
			success: function(response){

				var totalNumberOfConceptCodes = response.totalNumberOfConceptCodes;
				var totalPages = response.totalPages;
				var conceptCodes = response.conceptCodes;
				var numberOfElements = response.numberOfElements;
				
				clearTable();
				
				if(totalNumberOfConceptCodes > 0){
					
					for(var i = 0; i < numberOfElements; i++){
						
						var temp_conceptcode_id = conceptCodes[i].id;
						var temp_conceptcode_code = conceptCodes[i].code;
						var temp_conceptcode_name = conceptCodes[i].name;
						var temp_conceptcode_cs_name = conceptCodes[i].codeSystemName;
						var temp_conceptcode_cs_vs_name = conceptCodes[i].codeSystemVersionName;
						var temp_conceptcode_valsets_ary = new Array();

						temp_conceptcode_valsets_ary = conceptCodes[i].valueSetMap;
						insertTableRow(temp_conceptcode_id, temp_conceptcode_code, temp_conceptcode_name, temp_conceptcode_cs_name, temp_conceptcode_cs_vs_name, temp_conceptcode_valsets_ary);
					}
					
					//Change table header text
					setHeaderForFilter();
					
					rebuildPagination(totalPages);
					setNumResultsDisplay(totalNumberOfConceptCodes);
				}else{
					window.alert("No results found");
					setNumResultsDisplay(0);
					rebuildPagination(1);
				}
			},
			error: function(e){
				window.alert("ERROR - An error occured while search for concept code by code: " + e.responseText);
			}
	});
	}
}

function populateValueSetsByCodeSystem() {
	var codeSystemSelected = ($('select#select_filter_on_cs').val() != "" ? $('select#select_filter_on_cs').val() : "empty");
	var codeSystemVersionSelected = ($('select#select_filter_on_cs_vs').val() != "" ? $('select#select_filter_on_cs_vs').val() : "empty");
	
	$.ajax({
		url:"conceptCode/populateValueSetNames/codeSystem/"+codeSystemSelected+"/codeSystemVersion/"+codeSystemVersionSelected,
		method: "GET",
		success: function(response) {
			resetSelectFilterOnValSetName();
			
			$('#select_filter_on_valset_name')
			.empty()
			.append('<option selected="selected" value="">- Select Value Set Name -</option>');
			
			for (var key in response) {;
			  
			  $('#select_filter_on_valset_name')
			    .append('<option value=' + key + '>' + response[key] + '</option>');
			}
		},
		error: function(e) {
			window.alert("ERROR - An error occured while populating value set names: " + e.responseText);
		}
		
	});
}

/**
 * Clear HTML table display
 */
function clearTable(){
	$('table#current_conceptcodes_table > tbody > tr.conceptcode-record').remove();
}

/**
 * Reset filter array
 */
function resetFilterArray(){
	filteredConceptCodeListIndex.emptyCodeIndexArray();
	var arylen = conceptCodeListDataStore.getArySize();
	
	for(var i = 0; i < arylen; i++){
		filteredConceptCodeListIndex.pushCodeIndex(i);
	}
}

/**
 * Filter concept codes by code system name selected in select input element
 */
function filterCodeSysNameFromSelect(){
	var selVal = $("select#select_filter_on_cs").val();
	filterCodeSysName(selVal);
}

/**
 * Remove concept code references that do not match selected
 * code system name from filteredConceptCodeListIndex array
 * 
 * @param {string} selCodeSysName
 */
function filterCodeSysName(selCodeSysName){
	if(selCodeSysName != ""){
		applyFilter(selCodeSysName, 'conceptcode_cs_name');
	}
}

/**
 * Filter concept codes by code system version name selected in select input element
 */
function filterCodeSysVersionNameFromSelect(){
	var selVal = $("select#select_filter_on_cs_vs").val();
	filterCodeSysVersionName(selVal);
}

/**
 * Remove concept code references that do not match selected
 * code system version name from filteredConceptCodeListIndex array
 * 
 * @param {string} selCodeSysVersionName
 */
function filterCodeSysVersionName(selCodeSysVersionName){
	if(selCodeSysVersionName != ""){
		applyFilter(selCodeSysVersionName, 'conceptcode_cs_vs_name');
	}
}

/**
 * Filter concept codes by value set name selected in select input element
 */
function filterValueSetNameFromSelect(){
	var selVal = $("select#select_filter_on_valset_name").val();
	filterValueSetName(selVal);
}

/**
 * Remove concept code references that do not match selected
 * value set name from filteredConceptCodeListIndex array
 * 
 * @param {string} selValSetNameId
 */
function filterValueSetName(selValSetNameId){
	if(selValSetNameId != ""){
		var arylen = filteredConceptCodeListIndex.getArySize();
		
		for(var i = 0; i < arylen; i++){
			var curIndex = filteredConceptCodeListIndex.getCodeIndex(i);
			var curCode = conceptCodeListDataStore.getConceptCodeByIndex(curIndex);
			
			var curValSetAry = curCode['conceptcode_valsets_ary'];
			var valset_arylen = curValSetAry.length;
			
			var hasMatchValSet = false;
			
			for(var j = 0; j < valset_arylen; j++){
				if(curValSetAry[j]['valset_key'] == selValSetNameId){
					hasMatchValSet = true;
					break;
				}
			}
			
			if(hasMatchValSet === false){
				filteredConceptCodeListIndex.removeCodeIndex(i);
				i--;
				arylen--;
			}
		}
	}
}

/**
 * Apply specified filter to filtered array
 * 
 * @param in_filterCriteria
 * @param in_arrayFieldName
 */
function applyFilter(in_filterCriteria, in_arrayFieldName){
	var arylen = filteredConceptCodeListIndex.getArySize();
	
	for(var i = 0; i < arylen; i++){
		var curIndex = filteredConceptCodeListIndex.getCodeIndex(i);
		var curCode = conceptCodeListDataStore.getConceptCodeByIndex(curIndex);
		if(curCode[in_arrayFieldName] != in_filterCriteria){
			filteredConceptCodeListIndex.removeCodeIndex(i);
			i--;
			arylen--;
		}
	}
}

/**
 * Change table header to indicate filtered results
 */
function setHeaderForFilter(){
	//Change table header text
	$('tr#conceptcodes_table_header').addClass("hidden");
	$('tr#conceptcodes_filter_table_header').removeClass("hidden");
}

/**
 * Change table header to indicate search by code results
 */
function setHeaderForSearchCode(){
	//Change table header text
	$('tr#conceptcodes_table_header').addClass("hidden");
	$('tr#conceptcodes_filter_table_header').addClass("hidden");
	$('tr#conceptcodes_namesearch_table_header').addClass("hidden");
	$('tr#conceptcodes_codesearch_table_header').removeClass("hidden");
}

/**
 * Change table header to indicate search by name results
 */
function setHeaderForSearchName(){
	//Change table header text
	$('tr#conceptcodes_table_header').addClass("hidden");
	$('tr#conceptcodes_filter_table_header').addClass("hidden");
	$('tr#conceptcodes_codesearch_table_header').addClass("hidden");
	$('tr#conceptcodes_namesearch_table_header').removeClass("hidden");
}

/**
 * Change table header to remove indication that table contains filtered results
 */
function setHeaderForNoFilter(){
	//Change table header text
	$('tr#conceptcodes_filter_table_header').addClass("hidden");
	$('tr#conceptcodes_table_header').removeClass("hidden");
}

/**
 * Change table header to remove indication that table contains search results
 */
function setHeaderForNoSearch(){
	//Change table header text
	$('tr#conceptcodes_codesearch_table_header').addClass("hidden");
	$('tr#conceptcodes_namesearch_table_header').addClass("hidden");
	setHeaderForNoFilter();
}

/**
 * Reset BATCH UPLOAD select version dropdown
 */
function resetBatchSelectVersionDropdown(){
	$('select#batch_select_version').empty();
	appendOptionToSelect("batch_select_version", "", "- Please Select -", true);
}

/**
 * Reset select filter on code system dropdown
 */
function resetSelectFilterOnCs(){
	$('select#select_filter_on_cs').val("");
}

/**
 * Reset select filter on code system version dropdown
 */
function resetSelectFilterOnCsVs(){
	$('select#select_filter_on_cs_vs').empty();
	appendOptionToSelect("select_filter_on_cs_vs", "", "- Please Select -");
}

/**
 * Reset select filter on value set name dropdown
 */
function resetSelectFilterOnValSetName(){
	$('select#select_filter_on_valset_name').val("");
}

/**
 * Clear search by concept code code input box
 */
function clearSearchConceptCodeCodeInput(){
	$('input#txt_search_conceptcode_code').val("");
}

/**
 * Clear search by concept code name input box
 */
function clearSearchConceptCodeNameInput(){
	$('input#txt_search_conceptcode_name').val("");
}

/**
 * Enable or disable select code system version dropdown
 * 
 * @param {boolean} setEnabledDisabled - the state to set (true: enable; false: disable)
 */
function setSelectCsVsIsEnabled(setEnabledDisabled){
	if(setEnabledDisabled === false){
		$('select#select_filter_on_cs_vs').attr("disabled", "disabled");
	}else if(setEnabledDisabled === true){
		$('select#select_filter_on_cs_vs').removeAttr("disabled");
	}
}

/**
 * Enable or disable BATCH UPLOAD select code system version dropdown
 * 
 * @param setEnabledDisabled - the state to set (true: enable; false: disable)
 */
function setSelectBatchCsVsIsEnabled(setEnabledDisabled){
	if(setEnabledDisabled === false){
		$('select#batch_select_version').attr("disabled", "disabled");
	}else if(setEnabledDisabled === true){
		$('select#batch_select_version').removeAttr("disabled");
	}
}

function handleLastValsetModalStates(){
	uncheckAllValsetModalInputs();
	clearDisplaySelectedValsets();
	
	$('input.valset_list_record').each(function(){
		var curElement = $(this);
		var curValsetName = curElement.data('valset-name');
		var curValsetId = curElement.data('valset-id');
		
		if(lastValsetModalState.isValsetIdInAry(curValsetId) === true){
			curElement.prop("checked", true);
			showSelValSetNames(curValsetName);
		}
	});
}

function storeValsetModalState(){
	lastValsetModalState.emptyValsetModalStateAry();
	
	$('input.valset_list_record').each(function(){
		var curElement = $(this);
		var list_vs_id = curElement.data('valset-id');
		
		if(curElement.prop("checked")){
			lastValsetModalState.pushValsetId(list_vs_id);
		}
	});
}

function uncheckAllValsetModalInputs(){
	$('input.valset_list_record').prop("checked", false);
}

function showSelValSetNames(list_vs_name){
	$('div#selected_valusets_name_display_holder').append(
		"<div class='badge' > <span>" + list_vs_name +
					" </span> </div>");
}

function clearDisplaySelectedValsets(){
	$('div#selected_valusets_name_display_holder').empty();
}

/**
 * Show current concept codes table
 */
function showTable(){
	$('table#current_conceptcodes_table > tbody').removeClass("hidden");
}

/**
 * Hide current concept codes table
 */
function hideTable(){
	$('table#current_conceptcodes_table > tbody').addClass("hidden");
}





/*********************************************************************
 * PAGINATION RELATED FUNCTIONS:
 *********************************************************************/

/**
 * Set pagination number of results display to specified value
 * 
 * @param {integer} num_results - the number of results to be displayed
 */
function setNumResultsDisplay(num_results){
	//Check to make sure the num_results variable is a valid numeric value
	if($.isNumeric(num_results) !== true){
		//TODO (MH): Catch this error in code that calls this function
		throw new TypeError("Type Error in setNumResultsDisplay(): num_results value (" + num_results + ") not numeric.");
	}else{
		$('div#conceptcode_pagination_holder > div#pagination_numresults_holder span#pagination_numresults').text(num_results);
	}
}

/**
 * Rebuild the pagination icons with the specified number of pages
 * 
 * @param {integer} num_pages (optional) - the number of pages to show in the pagination bar
 * @param {integer} pagenum_to_set (optional) - the page number to set as the current page
 */
function rebuildPagination(num_pages, pagenum_to_set){
	//Select pagination ul element
	var pagination_ul_element = $('div#conceptcode_pagination_holder > ul.pagination');
	
	pagination_ul_element.trigger("paginationRebuilding.c2s_ui.pagination");
	
	hidePaginationHolder();
	
	/* If value of num_pages passed to function is undefined or null, then
	 * calcualte the number of pages to show in the pagination bar based on
	 * the length of the filteredConceptCodeListIndex data structure array
	 * and the number of results to show per page (page_len)
	 */
	if((num_pages === undefined) || (num_pages === null)){
		//Set page_len to equal number of results to show per page
		//TODO (MH): Change this number to use a value read from a properties file
		var page_len = 20;
		
		var ary_len = filteredConceptCodeListIndex.getArySize();
		
		//Calculate the number of pages to show in pagination bar
		num_pages = Math.ceil(ary_len / page_len);
	}
	
	//Number of page icons to show in pagination bar at a time
	var num_page_icons_at_once = 5;
	
	//Initialize variables
	var isEllipsisOn = false;
	var num_pages_on_screen = 0;
	
	if(num_pages > num_page_icons_at_once){
		num_pages_on_screen = num_page_icons_at_once;
		isEllipsisOn = true;
	}else{
		num_pages_on_screen = num_pages;
		isEllipsisOn = false;
	}
	
	pagination_ul_element.empty();
	
	pagination_ul_element.append("<li id='pagination_first' class='pagination-li disabled'>" +
								 		"<span class='pagination-icon pagination-control'>&laquo;</span>" +
								 "</li>" +
								 "<li id='pagination_prev' class='pagination-li disabled'>" +
								 		"<span class='pagination-icon pagination-control'>&lsaquo;</span>" +
								 "</li>");
	
	pagination_ul_element.append("<li id='pagination_ellipsis_prev' class='pagination-li pagination_ellipsis disabled hidden'>" +
								 		"<span class='pagination-icon pagination-control'>...</span>" +
								 "</li>");
	
	for(var i = 1; i <= num_pages; i++){
		var pagenum_icon_string = "<li id='pagination_" + i +"' class='pagination-li pagination-page";
		
		if(i > num_pages_on_screen){
			pagenum_icon_string = pagenum_icon_string.concat(" pagination-offscreen pagination-trailing hidden");
		}
		
		pagenum_icon_string = pagenum_icon_string.concat("'><span class='pagination-icon pagination-pagenum' data-pagenum='" + i + "'>" + i + "</span></li>");
		
		pagination_ul_element.append(pagenum_icon_string);
	}
	
	//Start building string of HTML code to append for next ellipsis icon
	var ellipsis_icon_string = "<li id='pagination_ellipsis_next' class='pagination-li pagination_ellipsis";
	
	//If ellipsis is off, then append disabled & hidden classes to the ellipsis_icon_string variable
	if(isEllipsisOn === false){
		ellipsis_icon_string = ellipsis_icon_string.concat(" disabled hidden");
	}
	
	//Finish building string of HTML code to append for next ellipsis icon
	ellipsis_icon_string = ellipsis_icon_string.concat("'><span class='pagination-icon pagination-control'>...</span></li>");
	
	//Append HTML code for next ellipsis icon to pagination ul element using ellipsis_icon_string varibale value
	pagination_ul_element.append(ellipsis_icon_string);
	
	pagination_ul_element.append("<li id='pagination_next' class='pagination-li'><span class='pagination-icon pagination-control'>&rsaquo;</span></li>" +
			"<li id='pagination_last' class='pagination-li'><span class='pagination-icon pagination-control'>&raquo;</span></li>");
	
	if(num_pages <= 1){
		disableNextPageIcon();
	}
	
	var isValidPage = false;
	
	try{
		isValidPage = checkIsPage(pagenum_to_set);
	}catch(err){
		pagenum_to_set = 1;
		if(err.name == "TypeError"){
			isValidPage = true;
		}else{
			isValidPage = null;
			//rethrow error
			throw err;
		}
	}
	
	if(isValidPage === false){
		pagenum_to_set = getLastPagenum();
		
		//Check for empty page and throw error to be handled by calling code
		if((pagenum_to_set === undefined) || (pagenum_to_set === null)){
			throw new TypeError("Invalid pagenum_to_set");
		}
	}
	
	changePagegroup("jumpToTargetPagegroup", null, null, null, pagenum_to_set);
	gotoTargetPage(pagenum_to_set);
	
	showPaginationHolder();
	
	pagination_ul_element.trigger("paginationRebuilt.c2s_ui.pagination");
}

/**
 * Change current active paginiation page to specified target page
 * 
 * @param {integer} in_targetPage - the target page to go to
 */
function gotoTargetPage(in_targetPage){
	//Check to make sure the page number variable is a valid numeric value
	if($.isNumeric(in_targetPage) !== true){
		//TODO (MH): Catch this error in code that calls this function
		throw new TypeError("Type Error in gotoTargetPage(): in_targetPage value (" + in_targetPage + ") not numeric.");
	}else if(checkIsPage(in_targetPage) === false){
		//TODO (MH): Catch this error in code that calls this function
		throw new RangeError("Range Error in gotoTargetPage(): in_targetPage value (" + in_targetPage + ") out of valid range");
	}else{
		setPaginationActiveClass(in_targetPage);
		
		var num_pages = getLastPagenum();
		
		if(checkNextPages(in_targetPage) === true){
			var last_pagegroup_pagenum = getLastPagegroupPagenum();
			if(last_pagegroup_pagenum < num_pages){
				enableNextEllipsisIcon();
			}else{
				disableNextEllipsisIcon();
			}
			enableNextPageIcon();
		}else{
			disableNextEllipsisIcon();
			disableNextPageIcon();
		}
		
		if(checkPrevPages(in_targetPage) === true){
			var first_pagegroup_pagenum = getFirstPagegroupPagenum();
			if(first_pagegroup_pagenum > 1){
				enablePrevEllipsisIcon();
			}else{
				disablePrevEllipsisIcon();
			}
			enablePrevPageIcon();
		}else{
			disablePrevEllipsisIcon();
			disablePrevPageIcon();
		}
		
		//rebuildTableFromAry();
		$('div#conceptcode_pagination_holder > ul.pagination').trigger("gotoTargetPageComplete.c2s_ui.pagination", {newPagenum: in_targetPage});
	}
}

/**
 * Change current active paginiation page to next page
 */
function gotoNextPage(){
	var current_pagenum = getCurrentPagenum();
	
	if(checkNextPages(current_pagenum) === true){
		var isEndOfPagegroup = checkIsEndOfPagegroup(current_pagenum);
		var next_pagenum = current_pagenum + 1;
		
		if(isEndOfPagegroup === true){
			incrementPagegroup();
		}
		
		gotoTargetPage(next_pagenum);
		
		$('div#conceptcode_pagination_holder > ul.pagination').trigger("gotoNextPageComplete.c2s_ui.pagination", {oldPagenum: current_pagenum});
	}
}

/**
 * Change current active paginiation page to previous page
 */
function gotoPrevPage(){
	var current_pagenum = getCurrentPagenum();
	
	if(checkPrevPages(current_pagenum) === true){
		var isStartOfPagegroup = checkIsStartOfPagegroup(current_pagenum);
		var prev_pagenum = current_pagenum - 1;
		
		if(isStartOfPagegroup === true){
			decrementPagegroup();
		}
		
		gotoTargetPage(prev_pagenum);
		
		$('div#conceptcode_pagination_holder > ul.pagination').trigger("gotoPrevPageComplete.c2s_ui.pagination", {oldPagenum: current_pagenum});
	}
}

/**
 * Change current active paginiation page to first page
 */
function gotoFirstPage(){
	gotoTargetPage(1);
	$('div#conceptcode_pagination_holder > ul.pagination').trigger("gotoFirstPageComplete.c2s_ui.pagination");
}

/**
 * Change current active paginiation page to last page
 */
function gotoLastPage(){
	var last_pagenum = getLastPagenum(); 
	gotoTargetPage(last_pagenum);
	$('div#conceptcode_pagination_holder > ul.pagination').trigger("gotoLastPageComplete.c2s_ui.pagination");
}

/**
 * Increment visible pages in page group by one
 */
function incrementPagegroup(){
	var cur_last_pagegroup_pagenum = getLastPagegroupPagenum();
	var cur_first_pagegroup_pagenum = getFirstPagegroupPagenum();
	
	var new_last_pagegroup_pagenum = cur_last_pagegroup_pagenum + 1;
	
	changePagegroup("incrementPagegroup", new_last_pagegroup_pagenum, null, cur_first_pagegroup_pagenum, new_last_pagegroup_pagenum);
}

/**
 * Decrement visible pages in page group by one
 */
function decrementPagegroup(){
	var cur_last_pagegroup_pagenum = getLastPagegroupPagenum();
	var cur_first_pagegroup_pagenum = getFirstPagegroupPagenum();
	
	var new_first_pagegroup_pagenum = (cur_first_pagegroup_pagenum > 1) ? cur_first_pagegroup_pagenum - 1 : 1;
	
	changePagegroup("decrementPagegroup", new_first_pagegroup_pagenum, cur_last_pagegroup_pagenum, null, new_first_pagegroup_pagenum);
}

/**
 * Jump to the last pagination page group and goto the last page
 */
function jumpToLastPagegroup(){
	//TODO (MH): Change this number to use a value read from a properties file
	var page_len = 5;
	
	var last_pagenum = getLastPagenum();
	
	var new_first_pagegroup_pagenum = ((last_pagenum - page_len + 1) >= 1) ? (last_pagenum - page_len + 1) : 1;
	
	changePagegroup("jumpToLastPagegroup", null, last_pagenum, new_first_pagegroup_pagenum, null);
}

/**
 * Jump to the first pagination page group and goto the first page
 */
function jumpToFirstPagegroup(){
	//TODO (MH): Change this number to use a value read from a properties file
	var page_len = 5;
	
	var first_pagenum = 1;
	var last_pagenum = getLastPagenum();
	
	var new_last_pagegroup_pagenum = ((first_pagenum + page_len - 1) <= last_pagenum) ? (first_pagenum + page_len - 1) : last_pagenum;
	
	changePagegroup("jumpToFirstPagegroup", null, new_last_pagegroup_pagenum, first_pagenum, null);
}

/**
 * Change Pagegroup
 * 
 * @param {String} mode
 * @param {integer} ellipsisNonePage
 * @param {integer} ellipsisTrailingPage
 * @param {integer} ellipsisLeadingPage
 * @param {integer} targetPage
 */
function changePagegroup(mode, ellipsisNonePage, ellipsisTrailingPage, ellipsisLeadingPage, targetPage){
	var isSuccess = false;
	
	//TODO (MH): Replace with value from a properties file
	var num_pages = 5;
	
	if(mode == "jumpToLastPagegroup"){
		try{
			setAllPagesEllipsisLeading();
			for(var i = ellipsisTrailingPage; i >= ellipsisLeadingPage; i--){
				setPageEllipsisNone(i);
			}
			isSuccess = true;
		}catch(err){
			isSuccess = false;
			//rethrow error
			throw err;
		}
	}else if(mode == "jumpToFirstPagegroup"){
		try{
			setAllPagesEllipsisTrailing();
			for(var i = ellipsisLeadingPage; i <= ellipsisTrailingPage; i++){
				setPageEllipsisNone(i);
			}
			isSuccess = true;
		}catch(err){
			isSuccess = false;
			//rethrow error
			throw err;
		}
	}else if(mode == "jumpToTargetPagegroup"){
		try{
			var last_pagenum = getLastPagenum();
			
			if(num_pages > last_pagenum){
				num_pages = last_pagenum;
			}
			
			var last_pagegroup_pagenum = targetPage;
			var first_pagegroup_pagenum = targetPage - num_pages + 1;
			
			if(first_pagegroup_pagenum < 1){
				first_pagegroup_pagenum = 1;
				last_pagegroup_pagenum = num_pages;
			}
			
			//Set Ellipis Leading For Loop
			for(var i = 1; i < first_pagegroup_pagenum; i++){
				try{
					setPageEllipsisLeading(i);
				}catch(err){
					if(err.name == "RangeError"){
						console.log("ERROR: A RangeError was caught in the changePagegroup() method, within the jumpToTargetPagegroup elseif code block, inside of the Set Ellipsis Leading for loop.");
					}
					//rethrow error
					throw err;
				}
			}
			
			//Set Ellipis None For Loop
			for(var i = first_pagegroup_pagenum; i <= last_pagegroup_pagenum; i++){
				try{
					setPageEllipsisNone(i);
				}catch(err){
					if(err.name == "RangeError"){
						console.log("ERROR: A RangeError was caught in the changePagegroup() method, within the jumpToTargetPagegroup elseif code block, inside of the Set Ellipsis None for loop.");
					}
					//rethrow error
					throw err;
				}
			}
			
			//Set Ellipis Trailing For Loop
			for(var i = last_pagegroup_pagenum + 1; i <= last_pagenum; i++){
				try{
					setPageEllipsisTrailing(i);
				}catch(err){
					if(err.name == "RangeError"){
						console.log("ERROR: A RangeError was caught in the changePagegroup() method, within the jumpToTargetPagegroup elseif code block, inside of the Set Ellipsis Trailing for loop.");
					}
					//rethrow error
					throw err;
				}
			}
			
			isSuccess= true;
		}catch(err){
			isSuccess = false;
			//rethrow error
			throw err;
		}
	}else{
		if ((ellipsisNonePage !== undefined) && (ellipsisNonePage !== null)){
			//TODO (MH): Add try/catch block here
			setPageEllipsisNone(ellipsisNonePage);
			isSuccess = true;
		}else{
			isSuccess = false;
		}
		
		if ((ellipsisTrailingPage !== undefined) && (ellipsisTrailingPage !== null)){
			//TODO (MH): Add try/catch block here
			setPageEllipsisTrailing(ellipsisTrailingPage);
		}
		
		if ((ellipsisLeadingPage !== undefined) && (ellipsisLeadingPage !== null)){
			//TODO (MH): Add try/catch block here
			setPageEllipsisLeading(ellipsisLeadingPage);
		}
	}
	
	if(isSuccess === true){
		//Trigger changePagegroupComplete event
		$('div#conceptcode_pagination_holder > ul.pagination').trigger("changePagegroupComplete.c2s_ui.pagination", {mode: mode});
	}else{
		throw new Error("ERROR: Unable to change pagegroup.");
	}
}


/**
 * Set all pagination pages to be trailing ellipsis pages
 */
function setAllPagesEllipsisTrailing(){
	var pagination_li_elements = $("div#conceptcode_pagination_holder > ul.pagination > li.pagination-li.pagination-page"); 
	
	//Make sure no page gets both pagination-leading & pagination_trailing at the same time
	pagination_li_elements.removeClass("pagination-leading pagination-trailing");
	
	pagination_li_elements.addClass("pagination-offscreen pagination-trailing hidden");
}

/**
 * Set all pagination pages to be leading ellipsis pages
 */
function setAllPagesEllipsisLeading(){
	var pagination_li_elements = $("div#conceptcode_pagination_holder > ul.pagination > li.pagination-li.pagination-page"); 
	
	//Make sure no page gets both pagination-leading & pagination_trailing at the same time
	pagination_li_elements.removeClass("pagination-leading pagination-trailing");
	
	pagination_li_elements.addClass("pagination-offscreen pagination-leading hidden");
}


/**
 * Set specified pagination page to be a trailing ellipsis page
 * 
 * @param {integer} in_pagenum - the page number to be set
 */
function setPageEllipsisTrailing(in_pagenum){
	setPageEllipsis(in_pagenum, "trailing");
}

/**
 * Set specified pagination page to be a leading ellipsis page
 * 
 * @param {integer} in_pagenum - the page number to be set
 */
function setPageEllipsisLeading(in_pagenum){
	setPageEllipsis(in_pagenum, "leading");
}

/**
 * Set specified pagination page to be a regular pagination page (not an ellipsis page)
 * 
 * @param {integer} in_pagenum - the page number to be set
 */
function setPageEllipsisNone(in_pagenum){
	setPageEllipsis(in_pagenum, "none");
}

/**
 * Set specified pagination page to be either a leading or trailing ellipsis page, or not an ellipsis page at all
 * 
 * @param {integer} in_pagenum - the page number to be set
 * @param {string} in_type - the type of ellipsis to set ('trailing', 'leading', or 'none')
 */
function setPageEllipsis(in_pagenum, in_type){
	//Check to make sure the page number variable is a valid numeric value
	if($.isNumeric(in_pagenum) !== true){
		//TODO (MH): Catch this error in code that calls this function
		throw new TypeError("Type Error in setPageEllipsis(): in_pagenum value (" + in_pagenum + ") not numeric.");
	}else if(checkIsPage(in_pagenum) === false){
		//TODO (MH): Catch this error in code that calls this function
		throw new RangeError("Range Error in setPageEllipsis(): in_pagenum value (" + in_pagenum + ") out of valid range.");
	}else{
		var pagination_li_element = $("div#conceptcode_pagination_holder > ul.pagination > li.pagination-li.pagination-page#pagination_" + in_pagenum);
		var class_names_string = "";
		
		if(in_type == "none"){
			pagination_li_element.removeClass("pagination-offscreen pagination-leading pagination-trailing hidden");
		}else{
			if(in_type == "leading"){
				class_names_string = "pagination-offscreen pagination-leading hidden";
			}else if(in_type == "trailing"){
				class_names_string = "pagination-offscreen pagination-trailing hidden";
			}else{
				//TODO (MH): Catch this error in code that calls this function
				throw new Error("Error in setPageEllipsis(): in_type value (" + in_type + ") is invalid.");
			}
			
			//Make sure no page gets both pagination-leading & pagination_trailing at the same time
			pagination_li_element.removeClass("pagination-leading pagination-trailing");
			
			pagination_li_element.addClass(class_names_string);
		}
	}
}

/**
 * Get the page number of the last pagination page
 * 
 * @returns {integer} last_pagenum - the last page's page number
 */
function getLastPagenum(){
	var last_pagenum = $('div#conceptcode_pagination_holder > ul.pagination > li.pagination-li.pagination-page').last().children('span.pagination-pagenum').first().data('pagenum');
	return last_pagenum;
}

/**
 * Get the page number of the last pagination page in the current pagination page group on screen
 * 
 * @returns {integer} last_pagegroup_pagenum - the last page's page number in the current pagination page group
 */
function getLastPagegroupPagenum(){
	var last_pagegroup_pagenum = $('div#conceptcode_pagination_holder > ul.pagination > li.pagination-li.pagination-page:not(.pagination-offscreen)').last().children('span.pagination-pagenum').first().data('pagenum');
	return last_pagegroup_pagenum;
}

/**
 * Get the page number of the first pagination page in the current pagination page group on screen
 * 
 * @returns {integer} first_pagegroup_pagenum - the first page's page number in the current pagination page group
 */
function getFirstPagegroupPagenum(){
	var first_pagegroup_pagenum = $('div#conceptcode_pagination_holder > ul.pagination > li.pagination-li.pagination-page:not(.pagination-offscreen)').first().children('span.pagination-pagenum').first().data('pagenum');
	return first_pagegroup_pagenum;
}

/**
 * Check if specified pagination page currently exists in pagination bar 
 * 
 * @param {integer} in_pagenum - the pagination page number to check
 * @returns {boolean} isPage - true if page exists; false if page doesn't exist
 */
function checkIsPage(in_pagenum){
	//Check to make sure the page number variable is a valid numeric value
	if($.isNumeric(in_pagenum) !== true){
		//TODO (MH): Catch this error in code that calls this function
		throw new TypeError("Type Error in checkIsPage(): in_pagenum value (" + in_pagenum + ") not numeric.");
	}else{
		var isPage = $("div#conceptcode_pagination_holder > ul.pagination > li.pagination-page#pagination_" + in_pagenum).is("li");
		if((isPage !== true) && (isPage !== false)){
			isPage = false;
		}
		return isPage;
	}
}

/**
 * Check if there are any other pagination pages after the specified page in pagination bar
 * 
 * @param {integer} in_pagenum - the pagination page number to check
 * @returns {boolean} isNextPages - true if more pages exists; false if more pages don't exist
 */
function checkNextPages(in_pagenum){
	//Check to make sure the page number variable is a valid numeric value
	if($.isNumeric(in_pagenum) !== true){
		//TODO (MH): Catch this error in code that calls this function
		throw new TypeError("Type Error in checkNextPages(): in_pagenum value (" + in_pagenum + ") not numeric.");
	}else if(checkIsPage(in_pagenum) === false){
		//TODO (MH): Catch this error in code that calls this function
		throw new RangeError("Range Error in checkNextPages(): in_pagenum value (" + in_pagenum + ") out of valid range.");
	}else{
		var next_pagenum = in_pagenum + 1;
		var isNextPages = checkIsPage(next_pagenum);
		return isNextPages;
	}
}

/**
 * Check if there are any other pagination pages before the specified page in pagination bar
 * 
 * @param {integer} in_pagenum - the pagination page number to check
 * @returns {boolean} isPrevPages - true if previous pages exists; false if previous pages don't exist
 */
function checkPrevPages(in_pagenum){
	//Check to make sure the page number variable is a valid numeric value
	if($.isNumeric(in_pagenum) !== true){
		//TODO (MH): Catch this error in code that calls this function
		throw new TypeError("Type Error in checkPrevPages(): in_pagenum value (" + in_pagenum + ") not numeric.");
	}else if(checkIsPage(in_pagenum) === false){
		//TODO (MH): Catch this error in code that calls this function
		throw new RangeError("Range Error in checkPrevPages(): in_pagenum value (" + in_pagenum + ") out of valid range.");
	}else{
		var prev_pagenum = in_pagenum - 1;
		var isPrevPages = checkIsPage(prev_pagenum);
		return isPrevPages;
	}
}

/**
 * Check if the specified pagination page is the last page in the currently displayed page group
 * 
 * @param {integer} in_pagenum - the pagination page number to check
 * @returns {boolean} isNextPagegroup - true if end of page group; false if not end of page group
 */
function checkIsEndOfPagegroup(in_pagenum){
	var isEndPagegroup = false;
	
	var pagination_li_element = $("div#conceptcode_pagination_holder > ul.pagination > li.pagination-page#pagination_" + in_pagenum);
	
	if(pagination_li_element.is("li.pagination-offscreen") === true){
		isEndPagegroup = false;
	}else{
		var isMorePages = checkNextPages(in_pagenum);
		
		if(isMorePages === false){
			isEndPagegroup = true;
		}else{
			isEndPagegroup = pagination_li_element.next("li.pagination-page").is("li.pagination-offscreen.pagination-trailing");
		}
	}
	
	return isEndPagegroup;
}

/**
 * Check if the specified pagination page is the first page in the currently displayed page group
 * 
 * @param {integer} in_pagenum - the pagination page number to check
 * @returns {boolean} isPrevPagegroup - true if start of page group; false if not start of page group
 */
function checkIsStartOfPagegroup(in_pagenum){
	var isStartPagegroup = false;
	
	var pagination_li_element = $("div#conceptcode_pagination_holder > ul.pagination > li.pagination-page#pagination_" + in_pagenum);
	
	if(pagination_li_element.is("li.pagination-offscreen") === true){
		isStartPagegroup = false;
	}else{
		var isPrevPages = checkPrevPages(in_pagenum);
		
		if(isPrevPages === false){
			isStartPagegroup = true;
		}else{
			isStartPagegroup = pagination_li_element.prev("li.pagination-page").is("li.pagination-offscreen.pagination-leading");
		}
	}
	
	return isStartPagegroup;
}

/**
 * Hide pagination holder div element
 */
function hidePaginationHolder(){
	$('div.sysAdmin_pagination#conceptcode_pagination_holder').addClass("hidden");
}

/**
 * Show pagination holder div element
 */
function showPaginationHolder(){
	$('div.sysAdmin_pagination#conceptcode_pagination_holder').removeClass("hidden");
}

/**
 * Enable & show next ellipsis icon in the pagination bar
 */
function enableNextEllipsisIcon(){
	$("div#conceptcode_pagination_holder > ul.pagination > li.pagination-li.pagination_ellipsis#pagination_ellipsis_next").removeClass("disabled hidden");
}

/**
 * Disable & hide next ellipsis icon in the pagination bar
 */
function disableNextEllipsisIcon(){
	$("div#conceptcode_pagination_holder > ul.pagination > li.pagination-li.pagination_ellipsis#pagination_ellipsis_next").addClass("disabled hidden");
}

/**
 * Enable & show prev ellipsis icon in the pagination bar
 */
function enablePrevEllipsisIcon(){
	$("div#conceptcode_pagination_holder > ul.pagination > li.pagination-li.pagination_ellipsis#pagination_ellipsis_prev").removeClass("disabled hidden");
}

/**
 * Disable & hide prev ellipsis icon in the pagination bar
 */
function disablePrevEllipsisIcon(){
	$("div#conceptcode_pagination_holder > ul.pagination > li.pagination-li.pagination_ellipsis#pagination_ellipsis_prev").addClass("disabled hidden");
}

/**
 * Disable next page icon in the pagination bar
 */
function disableNextPageIcon(){
	$("div#conceptcode_pagination_holder > ul.pagination > li.pagination-li#pagination_next").addClass("disabled");
	$("div#conceptcode_pagination_holder > ul.pagination > li.pagination-li#pagination_last").addClass("disabled");
}

/**
 * Enable next page icon in the pagination bar
 */
function enableNextPageIcon(){
	$("div#conceptcode_pagination_holder > ul.pagination > li.pagination-li#pagination_next").removeClass("disabled");
	$("div#conceptcode_pagination_holder > ul.pagination > li.pagination-li#pagination_last").removeClass("disabled");
}

/**
 * Disable previous page icon in the pagination bar
 */
function disablePrevPageIcon(){
	$("div#conceptcode_pagination_holder > ul.pagination > li.pagination-li#pagination_prev").addClass("disabled");
	$("div#conceptcode_pagination_holder > ul.pagination > li.pagination-li#pagination_first").addClass("disabled");
}

/**
 * Enable previous page icon in the pagination bar
 */
function enablePrevPageIcon(){
	$("div#conceptcode_pagination_holder > ul.pagination > li.pagination-li#pagination_prev").removeClass("disabled");
	$("div#conceptcode_pagination_holder > ul.pagination > li.pagination-li#pagination_first").removeClass("disabled");
}

/**
 * Get pagination current page number
 * 
 * @returns {integer} current_pagenum
 */
function getCurrentPagenum(){
	var current_pagenum = $('div#conceptcode_pagination_holder > ul.pagination > li.pagination-li.active > span.pagination-icon').data("pagenum");
	
	//Check to make sure the page number variable is a valid numeric value
	if($.isNumeric(current_pagenum) !== true){
		//TODO (MH): Catch this error in code that calls this function
		throw new TypeError("Type Error in getCurrentPagenum(): current_pagenum value (" + current_pagenum + ") not numeric.");
	}else if(current_pagenum < 1){
		//TODO (MH): Catch this error in code that calls this function
		throw new RangeError("Range Error in getCurrentPagenum(): current_pagenum value (" + current_pagenum + ") out of valid range");
	}
	
	return current_pagenum;
}

/**
 * Remove 'active' class from all pagination icon li elements
 */
function removePaginationActiveClass(){
	$('div#conceptcode_pagination_holder > ul.pagination > li.pagination-li.pagination-page').removeClass("active");
	$('div#conceptcode_pagination_holder > ul.pagination').trigger("activeClassRemoved.c2s_ui.pagination");
}

/**
 * Set 'active' class on specified pagination icon li element
 * 
 * @param {integer} current_pagenum - the page number to set as the current active page
 */
function setPaginationActiveClass(current_pagenum){
	//Check to make sure the page number variable is a valid numeric value
	if($.isNumeric(current_pagenum) !== true){
		//TODO (MH): Catch this error in code that calls this function
		throw new TypeError("Type Error in setPaginationActiveClass(): current_pagenum value (" + current_pagenum + ") not numeric.");
	}else if(current_pagenum < 1){
		//TODO (MH): Catch this error in code that calls this function
		throw new RangeError("Range Error in setPaginationActiveClass(): current_pagenum value (" + current_pagenum + ") out of valid range");
	}else{
		removePaginationActiveClass();
		var currentPageElement = $("div#conceptcode_pagination_holder > ul.pagination > li#pagination_" + current_pagenum);
		currentPageElement.addClass("active");
		currentPageElement.trigger("activeClassAdded.c2s_ui.pagination");
	}
}