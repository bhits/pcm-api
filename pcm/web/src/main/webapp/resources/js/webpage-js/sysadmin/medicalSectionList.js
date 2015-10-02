var medicalSectionListDataStore = (function() {
	var ary_medicalSectionList = new Array();
	
	return {
		pushMedicalSection: function(in_medicalSection_id, in_medicalSection_code, in_medicalSection_name, in_medicalSection_desc){
			var tempMedicalSection = new Array();
			tempMedicalSection['medicalSection_id'] = in_medicalSection_id;
			tempMedicalSection['medicalSection_code'] = in_medicalSection_code;
			tempMedicalSection['medicalSection_name'] = in_medicalSection_name;
			tempMedicalSection['medicalSection_desc'] = in_medicalSection_desc;
			ary_medicalSectionList.push(tempMedicalSection);
		},
		getArySize: function(){
			var out_length = ary_medicalSectionList.length;
			return out_length;
		},
		getMedicalSectionByIndex: function(in_index){
			var out_valset = {};
			if((in_index >= 0) && (in_index < ary_medicalSectionList.length)){
				var temp_valset = ary_medicalSectionList.slice(in_index, in_index + 1);
				out_valset = deepCopy(temp_valset[0]);
			}
			return out_valset;
		},
		removeMedicalSectionByMedicalSectionId: function(in_medicalSection_id){
			var ary_length = ary_medicalSectionList.length;
			var found_index = null;
			for (var i = 0; i < ary_length; i++){
				var tempVarSet = ary_medicalSectionList.slice(i, i + 1);
				tempVarSet = tempVarSet[0];
				if(tempVarSet['medicalSection_id'] == in_medicalSection_id){
					found_index = i;
					break;
				}
			}
			
			if(found_index == null){
				window.alert("ERROR: An unknown error occured. Please reload the page.");
			}else{
				ary_medicalSectionList.splice(found_index, 1);
			}
		},
		emptyMedicalSectionArray: function(){
			ary_medicalSectionList = new Array();
		},
		getAllMedicalSections: function(){
			var tempAry = ary_medicalSectionList;
			return tempAry;
		}
	};
})();


$(document).ready(function(){
	initPopovers();
	
	$('tr.medicalSection-record').each(function(){
		var in_medicalSection_id = $(this).data("medical-section-id");
		var in_medicalSection_code = $(this).data("medical-section-code");
		var in_medicalSection_name = $(this).data("medical-section-name");
		var in_medicalSection_desc = $(this).data("medical-section-desc");
		
		medicalSectionListDataStore.pushMedicalSection(in_medicalSection_id, in_medicalSection_code, in_medicalSection_name, in_medicalSection_desc);
	});
	
	$('table#current-medicalsections-table > tbody').on("click", "tr.medicalSection-record .delete-record-trigger", function(evt){
		var isConfirmed = confirm('Are you sure you want to delete this Medical Information Category?');
		
		if(isConfirmed === true){
			var clickedElement = $(this);
			var clickedMedicalSectionId = $(this).parents("tr").data("medical-section-id");
			
			$.ajax({url: "medicalSection/delete/" + clickedMedicalSectionId,
					type: "POST",
					success: function(response){
						var cur_medicalSection_id = clickedElement.parents("tr").data("medical-section-id");
						medicalSectionListDataStore.removeMedicalSectionByMedicalSectionId(cur_medicalSection_id);
						clickedElement.parents("tr").remove();
					},
					error: function(e) {
						window.alert("ERROR: Unable to delete Medical Information Category.");
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
	
	var ary_len = medicalSectionListDataStore.getArySize();
	
	for(var i = 0; i < ary_len; i++){
		var curRecord = medicalSectionListDataStore.getMedicalSectionByIndex(i);
		
		var temp_medicalSection_id = curRecord["medicalSection_id"];
		var temp_medicalSection_code = curRecord["medicalSection_code"];
		var temp_medicalSection_name = curRecord["medicalSection_name"];
		var temp_medicalSection_desc = curRecord["medical-section-desc"];
		
		insertTableRow(temp_medicalSection_id, temp_medicalSection_code, temp_medicalSection_name, temp_medicalSection_desc);
	}
}

function insertTableRow(temp_medicalSection_id,temp_medicalSection_oid,  temp_medicalSection_code, temp_medicalSection_name, temp_medicalSection_display_name){
	$('table#current-medicalsections-table > tbody').append("<tr class='medicalSection-record' data-medical-section-code='" + temp_medicalSection_code + "' data-medical-section-id='" + temp_medicalSection_id + "' data-medical-section-desc='" + temp_medicalSection_desc + "' data-medical-section-name='" + temp_medicalSection_name + "'>" +
			"<td>" +
				"<span class='delete-record-trigger btn btn-danger btn-xs'>" +
					"<span class='fa fa-minus fa-white'></span>" +
				"</span>" +
			"</td>" +
			"<td>" +
				"<a href='medicalSection/edit/" + temp_medicalSection_id + "'>" +
					"<span>" + temp_medicalSection_code + "</span>" +
				"</a>" +
			"</td>" +
			"<td>" + temp_medicalSection_name + "</td>" +
			"<td>" + temp_medicalSection_desc + "</td>" +
		"</tr>");
}

function clearTable(){
	$('table#current-medicalsections-table > tbody > tr.medicalSection-record').remove();
}

