var codeSystemsVersionsDataStore = (function() {
	var ary_codeSystemsVersionsList = new Array();
	
	return {
		pushVersion: function(in_cs_id, in_cs_version_key, in_cs_version_value){
			var tempValSet = new Array();
			tempValSet['cs_id'] = in_cs_id;
			tempValSet['cs_version_key'] = in_cs_version_key;
			tempValSet['cs_version_value'] = in_cs_version_value;
			ary_codeSystemsVersionsList.push(tempValSet);
		},
		getArySize: function(){
			var out_length = ary_codeSystemsVersionsList.length;
			return out_length;
		},
		getVersionByIndex: function(in_index){
			var out_valset = {};
			if((in_index >= 0) && (in_index < ary_codeSystemsVersionsList.length)){
				var temp_valset = ary_codeSystemsVersionsList.slice(in_index, in_index + 1);
				out_valset = deepCopy(temp_valset[0]);
			}else{
				window.alert("ERROR: An unknown error occured. Please reload the page.");
			}
			return out_valset;
		},
		getVersionsWithCsId: function(in_cs_id){
			var ary_length = ary_codeSystemsVersionsList.length;
			var tempAry = new Array();
			var isResults = false;
			for (var i = 0; i < ary_length; i++){
				var tempVarSet = ary_codeSystemsVersionsList.slice(i, i + 1);
				tempVarSet = tempVarSet[0];
				if(tempVarSet['cs_id'] == in_cs_id){
					tempAry.push(deepCopy(tempVarSet));
					isResults = true;
				}
			}
			
			if(isResults === true){
				return tempAry;
			}else{
				window.alert("ERROR: No matching versions with specified code system ID were found.");
				return null;
			}
		},
		emptyVersionsArray: function(){
			ary_codeSystemsVersionsList = new Array();
		},
		getAllVersions: function(){
			var tempAry = deepCopy(ary_codeSystemsVersionsList);
			return tempAry;
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
	$('input.cs_version_data').each(function(){
		var in_cs_id = $(this).data('cs-id');
		var in_cs_version_key = $(this).data('cs-version-key');
		var in_cs_version_value = $(this).data('cs-version-value');
		
		codeSystemsVersionsDataStore.pushVersion(in_cs_id, in_cs_version_key, in_cs_version_value);
	});
	
	$('select#select_codesys').change(function(evt){
		var sel_cs_id = $(this).val();
		
		if(sel_cs_id > 0){
			var resultAry = codeSystemsVersionsDataStore.getVersionsWithCsId(sel_cs_id);
			var resultAryLength = resultAry.length;
			
			$('select#select_version').empty();
			$('select#select_version').append("<option selected='selected' value=''>--- Please Select  ---</option>");
			
			for(var i = 0; i < resultAryLength; i++){
				$('select#select_version').append("<option value='" + resultAry[i]['cs_version_key'] + "'>" + resultAry[i]['cs_version_value'] + "</option>");
			}
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
	
	$('#create_conceptCodesystem_action').validate({
		ignore: [],  // <-- allows for validation of hidden fields
		rules: {
			valueSetIds: {
	              required: true
	          }
		},
		errorPlacement: function(error, element) {
			if (element.attr("name") == "valueSetIds") {
				error.insertAfter("#selected_valusets_name_display_holder");
			} else {
				error.insertAfter(element);
			}
		},
        errorClass: 'valueSetErrMsg'
	});

	
});

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