var codeSystemVersionListDataStore = (function() {
	var ary_codeSystemVersionList = new Array();
	
	return {
		pushCodeSystemVersion: function(in_codeSystemVersion_id, in_codeSystemVersion_name, in_codeSystem_name, in_codeSystemVersion_desc){
			var tempCodeSystemVersion = new Array();
			tempCodeSystemVersion['codeSystemVersion_id'] = in_codeSystemVersion_id;
			tempCodeSystemVersion['codeSystemVersion_name'] = in_codeSystemVersion_name;
			tempCodeSystemVersion['codeSystem_name'] = in_codeSystem_name;
			tempCodeSystemVersion['codeSystemVersion_desc'] = in_codeSystemVersion_desc;
			ary_codeSystemVersionList.push(tempCodeSystemVersion);
		},
		getArySize: function(){
			var out_length = ary_codeSystemVersionList.length;
			return out_length;
		},
		getCodeSystemVersionByIndex: function(in_index){
			var out_valset = {};
			if((in_index >= 0) && (in_index < ary_codeSystemVersionList.length)){
				var temp_valset = ary_codeSystemVersionList.slice(in_index, in_index + 1);
				out_valset = deepCopy(temp_valset[0]);
			}
			return out_valset;
		},
		removeCodeSystemVersionByCodeSystemVersionId: function(in_codeSystem_id){
			var ary_length = ary_codeSystemVersionList.length;
			var found_index = null;
			for (var i = 0; i < ary_length; i++){
				var tempVarSet = ary_codeSystemVersionList.slice(i, i + 1);
				tempVarSet = tempVarSet[0];
				if(tempVarSet['codeSystemVersion_id'] == in_codeSystem_id){
					found_index = i;
					break;
				}
			}
			
			if(found_index == null){
				window.alert("ERROR: An unknown error occured. Please reload the page.");
			}else{
				ary_codeSystemVersionList.splice(found_index, 1);
			}
		},
		emptyCodeSystemVersionArray: function(){
			ary_codeSystemVersionList = new Array();
		},
		getAllCodeSystemVersions: function(){
			var tempAry = ary_codeSystemVersionList;
			return tempAry;
		}
	};
})();

$(document).ready(function(){
	
	initPopovers();
	
	$('tr.codeSystemVersion-record').each(function(){
		var in_codeSystemVersion_id = $(this).data("code-system-version-id");
		var in_codeSystemVersion_name = $(this).data("code-system-version-name");
		var in_codeSystem_name = $(this).data("codeSystem-name");
		var in_codeSystemVersion_desc = $(this).data("code-system-version-desc");
		
		codeSystemVersionListDataStore.pushCodeSystemVersion(in_codeSystemVersion_id, in_codeSystemVersion_name, in_codeSystem_name, in_codeSystemVersion_desc);
	});
	
	$('table#current-codeSystemsversion-table > tbody').on("click", "tr.codeSystemVersion-record .delete-record-trigger", function(evt){
		var isConfirmed = confirm('Are you sure you want to delete this Code System Version?');
		
		if(isConfirmed === true){
			var clickedElement = $(this);
			var clickedCodeSystemVersionId = $(this).parents("tr").data("code-system-version-id");
			
			$.ajax({url: "codeSystemVersion/delete/" + clickedCodeSystemVersionId,
					type: "POST",
					success: function(response){
						var cur_codeSystemVersion_id = clickedElement.parents("tr").data("code-system-version-id");
						codeSystemVersionListDataStore.removeCodeSystemVersionByCodeSystemVersionId(cur_codeSystemVersion_id);
						clickedElement.parents("tr").remove();
					},
					error: function(e) {
						window.alert("ERROR: Unable to delete Code System Version.");
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
		
		var temp_codeSystemVersion_id = curRecord["codeSystemVersion_id"];
		var temp_codeSystemVersion_name = curRecord["codeSystemVersion_name"];
		var temp_codeSystem_name = curRecord["codeSystem_name"];
		var temp_codeSystemVersion_desc  = curRecord["codeSystemVersion_desc"];
		
		insertTableRow(temp_codeSystem_id,temp_codeSystem_oid,  temp_codeSystem_code, temp_codeSystem_name, temp_codeSystem_display_name);
	}
}

function insertTableRow(temp_codeSystem_id,temp_codeSystem_oid,  temp_codeSystem_code, temp_codeSystem_name, temp_codeSystem_display_name){
	$('table#current-codeSystems-table > tbody').append("<tr class='codeSystemVersion-record' data-code-system-version-id='" + temp_codeSystemVersion_id + "' data-code-system-version-name='" + temp_codeSystemVersion_name + "' data-codeSystem-name='" + temp_codeSystem_name + "' data-code-system-version-desc='" + temp_codeSystemVersion_desc + "'>" +
			"<td>" +
				"<span class='delete-record-trigger btn btn-danger btn-xs'>" +
					"<span class='fa fa-minus fa-white'></span>" +
				"</span>" +
			"</td>" +
			"<td>" +
				"<a href='codeSystemVersion/edit/" + temp_codeSystemVersion_id + "'>" +
					"<span>" + temp_codeSystemVersion_name + "</span>" +
				"</a>" +
			"</td>" +
			"<td>" + temp_codeSystem_name + "</td>" +
			"<td>" + temp_codeSystem_name + "</td>" +
			"<td>" + temp_codeSystemVersion_desc + "</td>" +
		"</tr>");
}

function clearTable(){
	$('table#current-codeSystemsversion-table > tbody > tr.codeSystemVersion-record').remove();
}

