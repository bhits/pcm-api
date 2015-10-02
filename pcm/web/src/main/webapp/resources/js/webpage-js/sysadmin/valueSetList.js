var vs_paginationInfo = {
};

var pagedUrl;

var valueSetListDataStore = (function() {
	var ary_valueSetList = new Array();
	
	return {
		pushValSet: function(in_valset_id, in_valset_code, in_valset_name, in_valset_cat_code, in_valset_cat_name){
			var tempValSet = new Array();
			tempValSet['valset_id'] = in_valset_id;
			tempValSet['valset_code'] = in_valset_code;
			tempValSet['valset_name'] = in_valset_name;
			tempValSet['valset_cat_code'] = in_valset_cat_code;
			tempValSet['valset_cat_name'] = in_valset_cat_name;
			ary_valueSetList.push(tempValSet);
		},
		getArySize: function(){
			var out_length = ary_valueSetList.length;
			return out_length;
		},
		getValSetByIndex: function(in_index){
			var out_valset = {};
			if((in_index >= 0) && (in_index < ary_valueSetList.length)){
				var temp_valset = ary_valueSetList.slice(in_index, in_index + 1);
				out_valset = deepCopy(temp_valset[0]);
			}
			return out_valset;
		},
		removeValSetByIndex: function(in_index){
			if((in_index >= 0) && (in_index < ary_valueSetList.length)){
				ary_valueSetList.splice(in_index, 1);
			}
		},
		removeValSetByValsetId: function(in_valset_id){
			var ary_length = ary_valueSetList.length;
			var found_index = null;
			for (var i = 0; i < ary_length; i++){
				var tempVarSet = ary_valueSetList.slice(i, i + 1);
				tempVarSet = tempVarSet[0];
				if(tempVarSet['valset_id'] == in_valset_id){
					found_index = i;
					break;
				}
			}
			
			if(found_index == null){
				window.alert("ERROR: An unknown error occured. Please reload the page.");
			}else{
				ary_valueSetList.splice(found_index, 1);
			}
		},
		emptyValSetArray: function(){
			ary_valueSetList = new Array();
		},
		getAllValSets: function(){
			var tempAry = deepCopy(ary_valueSetList);
			return tempAry;
		}
	};
})();

var filteredValueSetListIndex = (function(){
	var ary_filteredValueSetList = new Array();
	
	return {
		pushValSetIndex: function(in_index){
			ary_filteredValueSetList.push(in_index);
		},
		getValSetIndex: function(in_pos){
			var out_valset = {};
			if((in_pos >= 0) && (in_pos < ary_filteredValueSetList.length)){
				var temp_valset = ary_filteredValueSetList.slice(in_pos, in_pos + 1);
				out_valset = deepCopy(temp_valset[0]);
			}
			return out_valset;
		},
		getAllValSetIndexes: function(){
			var tempAry = deepCopy(ary_filteredValueSetList);
			return tempAry;
		},
		getArySize: function(){
			var out_length = ary_filteredValueSetList.length;
			return out_length;
		},
		removeValSetIndex: function(in_pos){
			if((in_pos >= 0) && (in_pos < ary_filteredValueSetList.length)){
				ary_filteredValueSetList.splice(in_pos, 1);
			}
		},
		emptyValSetIndexArray: function(){
			ary_filteredValueSetList = new Array();
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
	
	$('tr.valset-record').each(function(){
		var in_valset_id = $(this).data("valset-id");
		var in_valset_code = $(this).data("valset-code");
		var in_valset_name = $(this).data("valset-name");
		var in_valset_cat_code = $(this).data("valset-cat-code");
		var in_valset_cat_name = $(this).data("valset-cat-name");
		
		valueSetListDataStore.pushValSet(in_valset_id, in_valset_code, in_valset_name, in_valset_cat_code, in_valset_cat_name);
	});
	
	showTable();
	
	/**
	 * Bind event handlers:
	 */
	
	$('form').on("click", "button", function(e){
		e.preventDefault();
	});
	
	//delegated event handler for click events on pagination icons
	$('div#valueset_pagination_holder > ul.pagination').on("click", "li.pagination-li > span.pagination-icon", function(e){
		e.stopPropagation();
		
		var clickedIcon = $(this);
		var clickedParent = clickedIcon.parent("li.pagination-li");
		
		if(clickedParent.hasClass("pagination-page")){
			var target_pagenum = clickedIcon.data("pagenum"); 
			loadPageValueSets(target_pagenum - 1);
			gotoTargetPage(target_pagenum);
		}else if((clickedParent.is("#pagination_next")) && (clickedParent.hasClass("disabled") === false)){
			loadPageValueSets(getCurrentPagenum());
			gotoNextPage();
		}else if((clickedParent.is("#pagination_prev")) && (clickedParent.hasClass("disabled") === false)){
			loadPageValueSets(getCurrentPagenum() - 2);
			gotoPrevPage();
		}else if((clickedParent.is("#pagination_first")) && (clickedParent.hasClass("disabled") === false)){
			loadPageValueSets(0);
			jumpToFirstPagegroup();
			gotoFirstPage();
		}else if((clickedParent.is("#pagination_last")) && (clickedParent.hasClass("disabled") === false)){
			var numResults = $("#pagination_numresults").text();
			loadPageValueSets(Math.ceil(numResults/pageLength) - 1);
			jumpToLastPagegroup();
			gotoLastPage();
		}
	});
	
	$('table#current_valuesets_table > tbody').on("click", "tr.valset-record .delete-record-trigger", function(evt){
		var isConfirmed = confirm('Are you sure you want to delete this value set?');
		
		if(isConfirmed === true){
			var clickedElement = $(this);
			var clickedValsetId = $(this).parents("tr").data("valset-id");
			
			$.ajax({url: "valueSet/delete/" + clickedValsetId,
					type: "DELETE",
					success: function(response){
						var cur_valset_id = clickedElement.parents("tr").data("valset-id");
						valueSetListDataStore.removeValSetByValsetId(cur_valset_id);
						clickedElement.parents("tr").remove();
					},
					error: function(e) {
						window.alert("ERROR: Unable to delete value-set - " + e.responseText);
					}
			});
			
			// TODO:
			rebuildPagination(vs_paginationInfo.totalPages);
			loadPageValueSets(getPageToLoadAfterDelete());
			setNumResultsDisplay(vs_paginationInfo.totalNumberOfValueSets - 1);
		}
	});
	
	$('select#select_filter_on_category').change(function(evt){
		filterWithPagination();
		setHeaderForFilter();
		
//		var selCatCode = $(this).val();
//		
//		//Hide table while rebuilding
//		hideTable();
//		
//		//Reset filter array
//		resetFilterArray();
//		
//		if(selCatCode != ""){
//			//Filter array
//			filterCatCode(selCatCode);
//			
//			//Change table header text
//			setHeaderForFilter();
//		}else{
//			//Change table header text
//			setHeaderForNoFilter();
//		}
//		
//		//Clear and rebuild table
//		initTableFromAry();
//		
//		//Show table once rebuild complete
//		showTable();
	});
	
	$('#btn_search_valset_reset').click(function(e){
		window.location.href = "valueSetList.html?panelState=resetoptions";
	});
	
	$('#btn_search_valset_name_submit').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var in_searchParam = $('input#txt_search_valset_name').val();
		
		if(in_searchParam.length > 0){
			var valueSetCategorySelected = ($('select#select_filter_on_category').val() != "" ? $('select#select_filter_on_category').val() : "empty");

			pagedUrl = "valueSet/ajaxSearchValueSet/pageNumber/pageNumberPlaceHolder/searchCategory/name/searchTerm/"+in_searchParam+"/valueSetCategory/"+valueSetCategorySelected;
			
			$.ajax({url: "valueSet/ajaxSearchValueSet/pageNumber/0/searchCategory/name/searchTerm/"+in_searchParam+"/valueSetCategory/"+valueSetCategorySelected,
					method: "GET",
					data: {searchCategory: "name",
						   searchTerm: in_searchParam},
					success: function(response){
						var totalNumberOfValueSets = response.totalNumberOfValueSets;
						var totalPages = response.totalPages;
						var valueSets = response.valueSets;
						var numberOfElements = response.numberOfElements;
						
						setPaginationInfo(totalNumberOfValueSets, totalPages, valueSets, numberOfElements);
						
						clearTable();
						
						if(totalNumberOfValueSets > 0){
							
							for(var i = 0; i < numberOfElements; i++){
								
								var temp_valset_id = valueSets[i].id;
								var temp_valset_code = valueSets[i].code;
								var temp_valset_name = valueSets[i].name;
								var temp_valset_cat_code = valueSets[i].valueSetCatCode;
								var temp_valset_cat_name = valueSets[i].valueSetCatName;
								
								insertTableRow(temp_valset_id, temp_valset_code, temp_valset_name, temp_valset_cat_code, temp_valset_cat_name);
							}
						
							clearSearchValsetCodeInput();
							
							//Change table header text
							setHeaderForSearchName();
							
							rebuildPagination(totalPages);
							setNumResultsDisplay(totalNumberOfValueSets);
						}else{
							window.alert("No results found");
							hidePaginationHolder();
						}
					},
					error: function(e){
						window.alert("ERROR - An error occured while search for value set code: " + e.responseText);
					}
			});
		}
	});
	
	$('#btn_search_valset_code_submit').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var in_searchParam = $('input#txt_search_valset_code').val();
		
		if(in_searchParam.length > 0){
			var valueSetCategorySelected = ($('select#select_filter_on_category').val() != "" ? $('select#select_filter_on_category').val() : "empty");

			pagedUrl = "valueSet/ajaxSearchValueSet/pageNumber/pageNumberPlaceHolder/searchCategory/code/searchTerm/"+in_searchParam+"/valueSetCategory/"+valueSetCategorySelected;
			
			$.ajax({url: "valueSet/ajaxSearchValueSet/pageNumber/0/searchCategory/code/searchTerm/"+in_searchParam+"/valueSetCategory/"+valueSetCategorySelected,
					method: "GET",
					data: {searchCategory: "code",
						   searchTerm: in_searchParam},
					success: function(response){
						var totalNumberOfValueSets = response.totalNumberOfValueSets;
						var totalPages = response.totalPages;
						var valueSets = response.valueSets;
						var numberOfElements = response.numberOfElements;
						
						setPaginationInfo(totalNumberOfValueSets, totalPages, valueSets, numberOfElements);
						
						clearTable();
						
						if(totalNumberOfValueSets > 0){
							
							for(var i = 0; i < numberOfElements; i++){
								
								var temp_valset_id = valueSets[i].id;
								var temp_valset_code = valueSets[i].code;
								var temp_valset_name = valueSets[i].name;
								var temp_valset_cat_code = valueSets[i].valueSetCatCode;
								var temp_valset_cat_name = valueSets[i].valueSetCatName;
								
								insertTableRow(temp_valset_id, temp_valset_code, temp_valset_name, temp_valset_cat_code, temp_valset_cat_name);
							}
						
							clearSearchValsetNameInput();
							
							//Change table header text
							setHeaderForSearchCode();
							
							rebuildPagination(totalPages);
							setNumResultsDisplay(totalNumberOfValueSets);
						}else{
							window.alert("No results found");
							hidePaginationHolder();
						}
					},
					error: function(e){
						window.alert("ERROR - An error occured while search for value set code: " + e.responseText);
					}
			});
		}
	});
	
	$("#valueset_batchuplad_form").validate({
	      rules: {
	          batch_file: { 
	        	  required: true,
	        	  extension: "xlsx"
	          }
	      },
	      errorPlacement: function(error, element) {
		     if (element.attr("name") == "batch_file") {
		       error.insertAfter("#file_err_msg");
		     } else {
		       error.insertAfter(element);
		     }
		  },
		  errorClass: 'valueSetErrMsg'
		});
	
	//resetFilterArray();
	//initTableFromAry();

	rebuildPagination($('#paginationMapInfo').attr('data-totalpages'));
	setNumResultsDisplay($('#paginationMapInfo').attr('data-totalnumberofvaluesets'));
	pagedUrl = "valueSet/ajaxGetPagedValueSets/pageNumber/pageNumberPlaceHolder";
	
	setPaginationInfo($('#paginationMapInfo').attr('data-totalnumberofvaluesets'), $('#paginationMapInfo').attr('data-totalpages'),
			"", $('#paginationMapInfo').attr('data-numberofelements'));
	
});

function getPageToLoadAfterDelete() {
	if (getCurrentPagenum() === 1) {
		return 0;
	} else if (vs_paginationInfo.numberOfElements === 1) {
		return getCurrentPagenum() - 2;
	} else {
		return getCurrentPagenum() - 1;
	}

}

/**
 * Initialize HTML table rows on initial page load & when filter/search changes  valueSetsPagedMap
 */
function initTableFromAry(){
//	setPaginationActiveClass(1);
//	//rebuildTableFromAry();
}

/**
 * Clear HTML table rows and rebuild page from stored arrays
 * 
 * @returns {integer} num_pages - the number of pages to show in the pagination control
 */
//function rebuildTableFromAry(){
//	//Set page_len to equal number of results to show per page
//	//TODO (MH): Change this number to use a value read from a properties file
//	var page_len = 20;
//	
//	//var ary_len = filteredValueSetListIndex.getArySize();
//	var ary_len = $('#paginationMapInfo').attr('data-totalNumberOfValueSets');
//	
//	clearTable();
//	
//	if(ary_len > 0){
//		var current_pagenum = getCurrentPagenum();
//		
//		var current_page_first_record_index = page_len * (current_pagenum - 1);
//		var current_page_last_record_index = current_page_first_record_index + (page_len - 1);
//		
//		//Handle case where number of records on current page is less than the default page length
//		if(current_page_last_record_index >= ary_len){
//			current_page_last_record_index = ary_len - 1;
//		}
//		
//		//Sanity Check
//		if(current_page_first_record_index > current_page_last_record_index){
//			//TODO (MH): Catch this error in code that calls this function
//			throw new RangeError(current_page_first_record_index + " > " + current_page_last_record_index);
//		}
//		
//		//Sanity Check
//		if(current_page_first_record_index < 0){
//			//TODO (MH): Catch this error in code that calls this function
//			throw new RangeError();
//		}
//		
//		//Sanity Check
//		if(current_page_last_record_index < 0){
//			//TODO (MH): Catch this error in code that calls this function
//			throw new RangeError();
//		}
//		
//		//Calculate the number of pages for pagination to return from function
//		var num_pages = Math.ceil(ary_len / page_len);
//		
//		setNumResultsDisplay(ary_len);
//		
//		//Return the calculated number of pages for pagination
//		return num_pages;
//	}else{
//		setNumResultsDisplay(0);
//		//Return the number of pages for pagination as 1
//		return 1;
//	}
//}

/**
 * Insert table row into HTML DOM
 * 
 * @param temp_valset_id
 * @param temp_valset_code
 * @param temp_valset_name
 * @param temp_valset_cat_code
 * @param temp_valset_cat_name
 */
function insertTableRow(temp_valset_id, temp_valset_code, temp_valset_name, temp_valset_cat_code, temp_valset_cat_name){
	$('table#current_valuesets_table > tbody').append("<tr class='valset-record' data-valset-code='" + temp_valset_code + "' data-valset-id='" + temp_valset_id + "' data-valset-cat-name='" + temp_valset_cat_name + "' data-valset-cat-code='" + temp_valset_cat_code + "' data-valset-name='" + temp_valset_name + "'>" +
			"<td>" +
				"<span class='delete-record-trigger btn btn-danger btn-xs'>" +
					"<span class='fa fa-minus fa-white'></span>" +
				"</span>" +
			"</td>" +
			"<td>" +
				"<a href='valueSet/edit/" + temp_valset_id + "'>" +
					"<span>" + temp_valset_code + "</span>" +
				"</a>" +
			"</td>" +
			"<td>" + temp_valset_name + "</td>" +
			"<td>" + temp_valset_cat_name + "</td>" +
		"</tr>");
}

// load paged value sets
function loadPageValueSets(pageNumber){
	// replace placeholder with page number
	pagedUrl = pagedUrl.replace("pageNumberPlaceHolder", pageNumber);

	$.ajax({url: pagedUrl,
		    success: function(response){

				var totalNumberOfValueSets = response.totalNumberOfValueSets;
				var totalPages = response.totalPages;
				var valueSets = response.valueSets;
				var numberOfElements = response.numberOfElements;
				
				setPaginationInfo(totalNumberOfValueSets, totalPages, valueSets, numberOfElements);
				
				clearTable();
				
				if(totalNumberOfValueSets > 0){
					
					for(var i = 0; i < numberOfElements; i++){
						
						var temp_valset_id = valueSets[i].id;
						var temp_valset_code = valueSets[i].code;
						var temp_valset_name = valueSets[i].name;
						var temp_valset_cat_code = valueSets[i].valueSetCatCode;
						var temp_valset_cat_name = valueSets[i].valueSetCatName;

						insertTableRow(temp_valset_id, temp_valset_code, temp_valset_name, temp_valset_cat_code, temp_valset_cat_name);
					}
				}
				else{
					window.alert("No results found");
				}
				// set back page number placeholder for next call 
				pagedUrl = pagedUrl.replace("pageNumber/"+pageNumber, "pageNumber/pageNumberPlaceHolder");
		    },
		    error: function(err){
		    	window.alert("ERROR: " + err.responseText);}
		    }
	);
}

/**
 * Holds pagination information
 */
function setPaginationInfo(totalNumberOfValueSets, totalPages, valueSets, numberOfElements) {
	vs_paginationInfo.totalNumberOfValueSets = totalNumberOfValueSets;
	vs_paginationInfo.totalPages = totalPages;
	vs_paginationInfo.valueSets = valueSets;
	vs_paginationInfo.numberOfElements = numberOfElements;
}

/**
 * Reset filter array
 */
function resetFilterArray(){
	filteredValueSetListIndex.emptyValSetIndexArray();
	var arylen = valueSetListDataStore.getArySize();
	
	for(var i = 0; i < arylen; i++){
		filteredValueSetListIndex.pushValSetIndex(i);
	}
}

/**
 * Clear HTML table display
 */
function clearTable(){
	$('table#current_valuesets_table > tbody > tr.valset-record').remove();
}

/**
 * Show current value sets table
 */
function showTable(){
	$('table#current_valuesets_table > tbody').removeClass("hidden");
}

/**
 * Hide current value sets table
 */
function hideTable(){
	$('table#current_valuesets_table > tbody').addClass("hidden");
}

function resetSelectFilterOnCategory(){
	$('select#select_filter_on_category').val("");
	$('select#select_filter_on_category').trigger("change");
}

function clearSearchValsetCodeInput(){
	$('input#txt_search_valset_code').val("");
}

function clearSearchValsetNameInput(){
	$('input#txt_search_valset_name').val("");
}

/**
 * Remove value set references that do not match selected
 * category code from filteredValueSetListIndex array
 * 
 * @param {string} selCatCode
 */
function filterCatCode(selCatCode){
	if(selCatCode != ""){
		applyFilter(selCatCode, 'valset_cat_code');
	}
}

/**
 * Apply specified filter to filtered array
 * 
 * @param in_filterCriteria
 * @param in_arrayFieldName
 */
function applyFilter(in_filterCriteria, in_arrayFieldName){
	var arylen = filteredValueSetListIndex.getArySize();
	
	for(var i = 0; i < arylen; i++){
		var curIndex = filteredValueSetListIndex.getValSetIndex(i);
		var curValset = valueSetListDataStore.getValSetByIndex(curIndex);
		if(curValset[in_arrayFieldName] != in_filterCriteria){
			filteredValueSetListIndex.removeValSetIndex(i);
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
	$('tr#valueset_table_header').addClass("hidden");
	$('tr#valueset_categoryfilter_table_header').removeClass("hidden");
}

/**
 * Change table header to indicate search by code results
 */
function setHeaderForSearchCode(){
	//Change table header text
	$('tr#valueset_table_header').addClass("hidden");
	$('tr#valueset_categoryfilter_table_header').addClass("hidden");
	$('tr#valueset_namesearch_table_header').addClass("hidden");
	$('tr#valueset_codesearch_table_header').removeClass("hidden");
}

/**
 * Change table header to indicate search by name results
 */
function setHeaderForSearchName(){
	//Change table header text
	$('tr#valueset_table_header').addClass("hidden");
	$('tr#valueset_categoryfilter_table_header').addClass("hidden");
	$('tr#valueset_codesearch_table_header').addClass("hidden");
	$('tr#valueset_namesearch_table_header').removeClass("hidden");
}

/**
 * Change table header to remove indication that table contains filtered results
 */
function setHeaderForNoFilter(){
	//Change table header text
	$('tr#valueset_categoryfilter_table_header').addClass("hidden");
	$('tr#valueset_table_header').removeClass("hidden");
}

/**
 * Change table header to remove indication that table contains search results
 */
function setHeaderForNoSearch(){
	//Change table header text
	$('tr#valueset_codesearch_table_header').addClass("hidden");
	$('tr#valueset_namesearch_table_header').addClass("hidden");
	setHeaderForNoFilter();
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
		$('div#valueset_pagination_holder > div#pagination_numresults_holder span#pagination_numresults').text(num_results);
	}
}

function filterWithPagination() {
	var in_searchParam = "empty";
	var searchCategory = "name";
	if ($('input#txt_search_valset_code').val() != "") {
		in_searchParam = $('input#txt_search_valset_code').val();
		searchCategory = "code";
	} else if ($('input#txt_search_valset_name').val()) {
		in_searchParam = $('input#txt_search_valset_name').val();
	}
	
	if(in_searchParam.length > 0){
		var valueSetSelected = ($('select#select_filter_on_category').val() != "" ? $('select#select_filter_on_category').val() : "empty");
		pagedUrl = "valueSet/ajaxSearchValueSet/pageNumber/pageNumberPlaceHolder/searchCategory/"+searchCategory+"/searchTerm/"+in_searchParam+"/valueSetCategory/"+valueSetSelected;
		
		$.ajax({url: "valueSet/ajaxSearchValueSet/pageNumber/0/searchCategory/"+searchCategory+"/searchTerm/"+in_searchParam+"/valueSetCategory/"+valueSetSelected,

			method: "GET",
			success: function(response){

				var totalNumberOfValueSets = response.totalNumberOfValueSets;
				var totalPages = response.totalPages;
				var valueSets = response.valueSets;
				var numberOfElements = response.numberOfElements;
				
				clearTable();
				
				if(totalNumberOfValueSets > 0){
					
					for(var i = 0; i < numberOfElements; i++){
						
						var temp_valset_id = valueSets[i].id;
						var temp_valset_code = valueSets[i].code;
						var temp_valset_name = valueSets[i].name;
						var temp_valset_cat_code = valueSets[i].valueSetCatCode;
						var temp_valset_cat_name = valueSets[i].valueSetCatName;

						insertTableRow(temp_valset_id, temp_valset_code, temp_valset_name, temp_valset_cat_code, temp_valset_cat_name);
					}
					
					//Change table header text
					setHeaderForFilter();
					
					rebuildPagination(totalPages);
					setNumResultsDisplay(totalNumberOfValueSets);
				}else{
					setNumResultsDisplay(totalNumberOfValueSets);
					window.alert("No results found");
				}
			},
			error: function(e){
				window.alert("ERROR - An error occured while search for value set by code: " + e.responseText);
			}
	});
	}
}

/**
 * Rebuild the pagination icons with the specified number of pages
 * 
 * @param {integer} num_pages
 */
function rebuildPagination(num_pages){
	hidePaginationHolder();
	
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
	
	//Select pagination ul element
	var pagination_ul_element = $('div#valueset_pagination_holder > ul.pagination');
	
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
	
	setPaginationActiveClass(1);
	
	showPaginationHolder();
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
		removePaginationActiveClass();
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
	}
}

/**
 * Change current active paginiation page to next page
 */
function gotoNextPage(){
	var current_pagenum = getCurrentPagenum();
	
	if(checkNextPages(current_pagenum) === true){
		var isEndOfPagegroup = checkIsEndOfPagegroup(current_pagenum);
		if(isEndOfPagegroup === true){
			incrementPagegroup();
		}else{
			var next_pagenum = current_pagenum + 1;
			gotoTargetPage(next_pagenum);
		}
	}
}

/**
 * Change current active paginiation page to previous page
 */
function gotoPrevPage(){
	var current_pagenum = getCurrentPagenum();
	
	if(checkPrevPages(current_pagenum) === true){
		var isStartOfPagegroup = checkIsStartOfPagegroup(current_pagenum);
		if(isStartOfPagegroup === true){
			decrementPagegroup();
		}else{
			var prev_pagenum = current_pagenum - 1;
			gotoTargetPage(prev_pagenum);
		}
	}
}

/**
 * Change current active paginiation page to first page
 */
function gotoFirstPage(){
	gotoTargetPage(1);
}

/**
 * Change current active paginiation page to last page
 */
function gotoLastPage(){
	var last_pagenum = getLastPagenum(); 
	gotoTargetPage(last_pagenum);
}

/**
 * Increment visible pages in page group by one
 */
function incrementPagegroup(){
	var cur_last_pagegroup_pagenum = getLastPagegroupPagenum();
	var cur_first_pagegroup_pagenum = getFirstPagegroupPagenum();
	
	var new_last_pagegroup_pagenum = cur_last_pagegroup_pagenum + 1;
	
	try{
		setPageEllipsisNone(new_last_pagegroup_pagenum);
		setPageEllipsisLeading(cur_first_pagegroup_pagenum);
		gotoTargetPage(new_last_pagegroup_pagenum);
	}catch(err){
		if(err.name === 'RangeError'){
			//FIXME (MH): Remove this console.log line when done testing
			console.log("Unable to increment pagegroup");
		}else{
			throw err;
		}
	}
}

/**
 * Decrement visible pages in page group by one
 */
function decrementPagegroup(){
	var cur_last_pagegroup_pagenum = getLastPagegroupPagenum();
	var cur_first_pagegroup_pagenum = getFirstPagegroupPagenum();
	
	var new_first_pagegroup_pagenum = (cur_first_pagegroup_pagenum > 1) ? cur_first_pagegroup_pagenum - 1 : 1;
	
	try{
		setPageEllipsisNone(new_first_pagegroup_pagenum);
		setPageEllipsisTrailing(cur_last_pagegroup_pagenum);
		gotoTargetPage(new_first_pagegroup_pagenum);
	}catch(err){
		if(err.name === 'RangeError'){
			//FIXME (MH): Remove this console.log line when done testing
			console.log("Unable to decrement pagegroup");
		}else{
			throw err;
		}
	}
}

/**
 * Jump to the last pagination page group and goto the last page
 */
function jumpToLastPagegroup(){
	//TODO (MH): Change this number to use a value read from a properties file
	var page_len = 5;
	
	var last_pagenum = getLastPagenum();
	
	var new_first_pagegroup_pagenum = ((last_pagenum - page_len + 1) >= 1) ? (last_pagenum - page_len + 1) : 1;
	
	try{
		setAllPagesEllipsisLeading();
		for(var i = last_pagenum; i >= new_first_pagegroup_pagenum; i--){
			setPageEllipsisNone(i);
		}
		gotoLastPage();
	}catch(err){
		if(err.name === 'RangeError'){
			//FIXME (MH): Remove this console.log line when done testing
			console.log("Unable to jump to last pagegroup");
		}else{
			throw err;
		}
	}
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
	
	try{
		setAllPagesEllipsisTrailing();
		for(var i = first_pagenum; i <= new_last_pagegroup_pagenum; i++){
			setPageEllipsisNone(i);
		}
		gotoFirstPage();
	}catch(err){
		if(err.name === 'RangeError'){
			//FIXME (MH): Remove this console.log line when done testing
			console.log("Unable to jump to first pagegroup");
		}else{
			throw err;
		}
	}
}


/**
 * Set all pagination pages to be trailing ellipsis pages
 */
function setAllPagesEllipsisTrailing(){
	var pagination_li_elements = $("div#valueset_pagination_holder > ul.pagination > li.pagination-li.pagination-page"); 
	
	//Make sure no page gets both pagination-leading & pagination_trailing at the same time
	pagination_li_elements.removeClass("pagination-leading pagination-trailing");
	
	pagination_li_elements.addClass("pagination-offscreen pagination-trailing hidden");
}

/**
 * Set all pagination pages to be leading ellipsis pages
 */
function setAllPagesEllipsisLeading(){
	var pagination_li_elements = $("div#valueset_pagination_holder > ul.pagination > li.pagination-li.pagination-page"); 
	
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
		var pagination_li_element = $("div#valueset_pagination_holder > ul.pagination > li.pagination-li.pagination-page#pagination_" + in_pagenum);
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
	var last_pagenum = $('div#valueset_pagination_holder > ul.pagination > li.pagination-li.pagination-page').last().children('span.pagination-pagenum').first().data('pagenum');
	return last_pagenum;
}

/**
 * Get the page number of the last pagination page in the current pagination page group on screen
 * 
 * @returns {integer} last_pagegroup_pagenum - the last page's page number in the current pagination page group
 */
function getLastPagegroupPagenum(){
	var last_pagegroup_pagenum = $('div#valueset_pagination_holder > ul.pagination > li.pagination-li.pagination-page:not(.pagination-offscreen)').last().children('span.pagination-pagenum').first().data('pagenum');
	return last_pagegroup_pagenum;
}

/**
 * Get the page number of the first pagination page in the current pagination page group on screen
 * 
 * @returns {integer} first_pagegroup_pagenum - the first page's page number in the current pagination page group
 */
function getFirstPagegroupPagenum(){
	var first_pagegroup_pagenum = $('div#valueset_pagination_holder > ul.pagination > li.pagination-li.pagination-page:not(.pagination-offscreen)').first().children('span.pagination-pagenum').first().data('pagenum');
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
		var isPage = $("div#valueset_pagination_holder > ul.pagination > li.pagination-page#pagination_" + in_pagenum).is("li");
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
	
	var pagination_li_element = $("div#valueset_pagination_holder > ul.pagination > li.pagination-page#pagination_" + in_pagenum);
	
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
	
	var pagination_li_element = $("div#valueset_pagination_holder > ul.pagination > li.pagination-page#pagination_" + in_pagenum);
	
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
	$('div.sysAdmin_pagination#valueset_pagination_holder').addClass("hidden");
}

/**
 * Show pagination holder div element
 */
function showPaginationHolder(){
	$('div.sysAdmin_pagination#valueset_pagination_holder').removeClass("hidden");
}

/**
 * Enable & show next ellipsis icon in the pagination bar
 */
function enableNextEllipsisIcon(){
	$("div#valueset_pagination_holder > ul.pagination > li.pagination-li.pagination_ellipsis#pagination_ellipsis_next").removeClass("disabled hidden");
}

/**
 * Disable & hide next ellipsis icon in the pagination bar
 */
function disableNextEllipsisIcon(){
	$("div#valueset_pagination_holder > ul.pagination > li.pagination-li.pagination_ellipsis#pagination_ellipsis_next").addClass("disabled hidden");
}

/**
 * Enable & show prev ellipsis icon in the pagination bar
 */
function enablePrevEllipsisIcon(){
	$("div#valueset_pagination_holder > ul.pagination > li.pagination-li.pagination_ellipsis#pagination_ellipsis_prev").removeClass("disabled hidden");
}

/**
 * Disable & hide prev ellipsis icon in the pagination bar
 */
function disablePrevEllipsisIcon(){
	$("div#valueset_pagination_holder > ul.pagination > li.pagination-li.pagination_ellipsis#pagination_ellipsis_prev").addClass("disabled hidden");
}

/**
 * Disable next page icon in the pagination bar
 */
function disableNextPageIcon(){
	$("div#valueset_pagination_holder > ul.pagination > li.pagination-li#pagination_next").addClass("disabled");
	$("div#valueset_pagination_holder > ul.pagination > li.pagination-li#pagination_last").addClass("disabled");
}

/**
 * Enable next page icon in the pagination bar
 */
function enableNextPageIcon(){
	$("div#valueset_pagination_holder > ul.pagination > li.pagination-li#pagination_next").removeClass("disabled");
	$("div#valueset_pagination_holder > ul.pagination > li.pagination-li#pagination_last").removeClass("disabled");
}

/**
 * Disable previous page icon in the pagination bar
 */
function disablePrevPageIcon(){
	$("div#valueset_pagination_holder > ul.pagination > li.pagination-li#pagination_prev").addClass("disabled");
	$("div#valueset_pagination_holder > ul.pagination > li.pagination-li#pagination_first").addClass("disabled");
}

/**
 * Enable previous page icon in the pagination bar
 */
function enablePrevPageIcon(){
	$("div#valueset_pagination_holder > ul.pagination > li.pagination-li#pagination_prev").removeClass("disabled");
	$("div#valueset_pagination_holder > ul.pagination > li.pagination-li#pagination_first").removeClass("disabled");
}

/**
 * Get pagination current page number
 * 
 * @returns {integer} current_pagenum
 */
function getCurrentPagenum(){
	var current_pagenum = $('div#valueset_pagination_holder > ul.pagination > li.pagination-li.active > span.pagination-icon').data("pagenum");
	
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
	$('div#valueset_pagination_holder > ul.pagination > li.pagination-li.pagination-page').removeClass("active");
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
		$("div#valueset_pagination_holder > ul.pagination > li#pagination_" + current_pagenum).addClass("active");
	}
}