var valueSetCategoryListDataStore = (function() {
	var ary_valueSetCategoryList = new Array();
	
	return {
		pushValueSetCategory: function(in_valueSetCategory_id, in_valueSetCategory_code, in_valueSetCategory_name, in_valueSetCategory_desc){
			var tempValueSetCategory = new Array();
			tempValueSetCategory['valueSetCategory_id'] = in_valueSetCategory_id;
			tempValueSetCategory['valueSetCategory_code'] = in_valueSetCategory_code;
			tempValueSetCategory['valueSetCategory_name'] = in_valueSetCategory_name;
			tempValueSetCategory['valueSetCategory_desc'] = in_valueSetCategory_desc;
			ary_valueSetCategoryList.push(tempValueSetCategory);
		},
		getArySize: function(){
			var out_length = ary_valueSetCategoryList.length;
			return out_length;
		},
		getValueSetCategoryByIndex: function(in_index){
			var out_valset = {};
			if((in_index >= 0) && (in_index < ary_valueSetCategoryList.length)){
				var temp_valset = ary_valueSetCategoryList.slice(in_index, in_index + 1);
				out_valset = deepCopy(temp_valset[0]);
			}
			return out_valset;
		},
		removeValueSetCategoryByValueSetCategoryId: function(in_valueSetCategory_id){
			var ary_length = ary_valueSetCategoryList.length;
			var found_index = null;
			for (var i = 0; i < ary_length; i++){
				var tempVarSet = ary_valueSetCategoryList.slice(i, i + 1);
				tempVarSet = tempVarSet[0];
				if(tempVarSet['valueSetCategory_id'] == in_valueSetCategory_id){
					found_index = i;
					break;
				}
			}
			
			if(found_index == null){
				window.alert("ERROR: An unknown error occured. Please reload the page.");
			}else{
				ary_valueSetCategoryList.splice(found_index, 1);
			}
		},
		emptyValueSetCategoryArray: function(){
			ary_valueSetCategoryList = new Array();
		},
		getAllValueSetCategorys: function(){
			var tempAry = ary_valueSetCategoryList;
			return tempAry;
		}
	};
})();


$(document).ready(function(){
	initPopovers();
	
	$('tr.valueSetCategory-record').each(function(){
		var in_valueSetCategory_id = $(this).data("value-set-category-id");
		var in_valueSetCategory_code = $(this).data("value-set-category-code");
		var in_valueSetCategory_name = $(this).data("value-set-category-name");
		var in_valueSetCategory_desc = $(this).data("value-set-category-desc");
		
		valueSetCategoryListDataStore.pushValueSetCategory(in_valueSetCategory_id, in_valueSetCategory_code, in_valueSetCategory_name, in_valueSetCategory_desc);
	});
	
	$('table#current-valuesetcategories-table > tbody').on("click", "tr.valueSetCategory-record .delete-record-trigger", function(evt){
		var isConfirmed = confirm('Are you sure you want to delete this Value Set Category?');
		
		if(isConfirmed === true){
			var clickedElement = $(this);
			var clickedValueSetCategoryId = $(this).parents("tr").data("value-set-category-id");
			
			$.ajax({url: "valueSetCategory/delete/" + clickedValueSetCategoryId,
					type: "POST",
					success: function(response){
						var cur_valueSetCategory_id = clickedElement.parents("tr").data("value-set-category-id");
						valueSetCategoryListDataStore.removeValueSetCategoryByValueSetCategoryId(cur_valueSetCategory_id);
						clickedElement.parents("tr").remove();
					},
					error: function(e) {
						window.alert("ERROR: Unable to delete Value Set Category.");
					}
			});
		}
	});
	
	
});


/**
 * Clear HTML table rows and rebuild from stored array 
 */
function rebuildTableFromAry(){
	clearTable();
	
	var ary_len = valueSetCategoryListDataStore.getArySize();
	
	for(var i = 0; i < ary_len; i++){
		var curRecord = valueSetCategoryListDataStore.getValueSetCategoryByIndex(i);
		
		var temp_valueSetCategory_id = curRecord["valueSetCategory_id"];
		var temp_valueSetCategory_code = curRecord["valueSetCategory_code"];
		var temp_valueSetCategory_name = curRecord["valueSetCategory_name"];
		var temp_valueSetCategory_desc = curRecord["value-set-category-desc"];
		
		insertTableRow(temp_valueSetCategory_id, temp_valueSetCategory_code, temp_valueSetCategory_name, temp_valueSetCategory_desc);
	}
}

function insertTableRow(temp_valueSetCategory_id,temp_valueSetCategory_oid,  temp_valueSetCategory_code, temp_valueSetCategory_name, temp_valueSetCategory_display_name){
	$('table#current-valuesetcategories-table > tbody').append("<tr class='valueSetCategory-record' data-value-set-category-code='" + temp_valueSetCategory_code + "' data-value-set-category-id='" + temp_valueSetCategory_id + "' data-value-set-category-desc='" + temp_valueSetCategory_desc + "' data-value-set-category-name='" + temp_valueSetCategory_name + "'>" +
			"<td>" +
				"<span class='delete-record-trigger btn btn-danger btn-xs'>" +
					"<span class='fa fa-minus fa-white'></span>" +
				"</span>" +
			"</td>" +
			"<td>" +
				"<a href='valueSetCategory/edit/" + temp_valueSetCategory_id + "'>" +
					"<span>" + temp_valueSetCategory_code + "</span>" +
				"</a>" +
			"</td>" +
			"<td>" + temp_valueSetCategory_name + "</td>" +
			"<td>" + temp_valueSetCategory_desc + "</td>" +
		"</tr>");
}

function clearTable(){
	$('table#current-valuesetcategories-table > tbody > tr.valueSetCategory-record').remove();
}

