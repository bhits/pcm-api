var codeSystemListDataStore = (function() {
	var ary_codeSystemList = new Array();
	
	return {
		pushCodeSystem: function(in_codeSystem_id, in_codeSystem_oid, in_codeSystem_code, in_codeSystem_name, in_codeSystem_display_name){
			var tempCodeSystem = new Array();
			tempCodeSystem['codeSystem_id'] = in_codeSystem_id;
			tempCodeSystem['codeSystem_oid'] = in_codeSystem_oid;
			tempCodeSystem['codeSystem_code'] = in_codeSystem_code;
			tempCodeSystem['codeSystem_name'] = in_codeSystem_name;
			tempCodeSystem['codeSystem_display_name'] = in_codeSystem_display_name;
			ary_codeSystemList.push(tempCodeSystem);
		},
		getArySize: function(){
			var out_length = ary_codeSystemList.length;
			return out_length;
		},
		getCodeSystemByIndex: function(in_index){
			var out_valset = {};
			if((in_index >= 0) && (in_index < ary_codeSystemList.length)){
				var temp_valset = ary_codeSystemList.slice(in_index, in_index + 1);
				out_valset = deepCopy(temp_valset[0]);
			}
			return out_valset;
		},
		removeCodeSystemByCodeSystemId: function(in_codeSystem_id){
			var ary_length = ary_codeSystemList.length;
			var found_index = null;
			for (var i = 0; i < ary_length; i++){
				var tempVarSet = ary_codeSystemList.slice(i, i + 1);
				tempVarSet = tempVarSet[0];
				if(tempVarSet['codeSystem_id'] == in_codeSystem_id){
					found_index = i;
					break;
				}
			}
			
			if(found_index == null){
				window.alert("ERROR: An unknown error occured. Please reload the page.");
			}else{
				ary_codeSystemList.splice(found_index, 1);
			}
		},
		emptyCodeSystemArray: function(){
			ary_codeSystemList = new Array();
		},
		getAllCodeSystems: function(){
			var tempAry = ary_codeSystemList;
			return tempAry;
		}
	};
})();


$(document).ready(function(){
	initPopovers();
	
	$('tr.codeSystem-record').each(function(){
		var in_codeSystem_id = $(this).data("code-system-id");
		var in_codeSystem_oid = $(this).data("code-system-oid");
		var in_codeSystem_code = $(this).data("code-system-code");
		var in_codeSystem_name = $(this).data("code-system-name");
		var in_codeSystem_display_name = $(this).data("code-system-display-name");
		
		codeSystemListDataStore.pushCodeSystem(in_codeSystem_id, in_codeSystem_oid, in_codeSystem_code, in_codeSystem_name, in_codeSystem_display_name);
	});
	
	$('table#current-codeSystems-table > tbody').on("click", "tr.codeSystem-record .delete-record-trigger", function(evt){
		var isConfirmed = confirm('Are you sure you want to delete this Code System?');
		
		if(isConfirmed === true){
			var clickedElement = $(this);
			var clickedCodeSystemId = $(this).parents("tr").data("code-system-id");
			
			$.ajax({url: "codeSystem/delete/" + clickedCodeSystemId,
					type: "POST",
					success: function(response){
						var cur_codeSystem_id = clickedElement.parents("tr").data("code-system-id");
						codeSystemListDataStore.removeCodeSystemByCodeSystemId(cur_codeSystem_id);
						clickedElement.parents("tr").remove();
					},
					error: function(e) {
						window.alert("ERROR: Unable to delete Code System.");
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
	
	var ary_len = codeSystemListDataStore.getArySize();
	
	for(var i = 0; i < ary_len; i++){
		var curRecord = codeSystemListDataStore.getCodeSystemByIndex(i);
		
		var temp_codeSystem_id = curRecord["codeSystem_id"];
		var temp_codeSystem_oid = curRecord["codeSystem_oid"];
		var temp_codeSystem_code = curRecord["codeSystem_code"];
		var temp_codeSystem_name = curRecord["codeSystem_name"];
		var temp_codeSystem_display_name = curRecord["codeSystem_display_name"];
		
		insertTableRow(temp_codeSystem_id,temp_codeSystem_oid,  temp_codeSystem_code, temp_codeSystem_name, temp_codeSystem_display_name);
	}
}

function insertTableRow(temp_codeSystem_id,temp_codeSystem_oid,  temp_codeSystem_code, temp_codeSystem_name, temp_codeSystem_display_name){
	$('table#current-codeSystems-table > tbody').append("<tr class='codeSystem-record' data-codeSystem-code='" + temp_codeSystem_code + "' data-codeSystem-id='" + temp_codeSystem_id + "' data-codeSystem-display-name='" + temp_codeSystem_display_name + "' data-codeSystem-oid='" + temp_codeSystem_oid + "' data-codeSystem-name='" + temp_codeSystem_name + "'>" +
			"<td>" +
				"<span class='delete-record-trigger btn btn-danger btn-xs'>" +
					"<span class='fa fa-minus fa-white'></span>" +
				"</span>" +
			"</td>" +
			"<td>" +
				"<a href='codeSystem/edit/" + temp_codeSystem_id + "'>" +
					"<span>" + temp_codeSystem_oid + "</span>" +
				"</a>" +
			"</td>" +
			"<td>" + temp_codeSystem_code + "</td>" +
			"<td>" + temp_codeSystem_name + "</td>" +
			"<td>" + temp_codeSystem_display_name + "</td>" +
		"</tr>");
}

function clearTable(){
	$('table#current_valuesets_table > tbody > tr.codeSystem-record').remove();
}

