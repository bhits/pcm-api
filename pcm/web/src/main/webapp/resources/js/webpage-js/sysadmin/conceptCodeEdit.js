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
	$('input.valset_list_record').each(function(){
		var curValsetListRecordElement = $(this);
		var list_Vs_id = curValsetListRecordElement.data('valset-id');	
		
		$('input.selected_valset_record').each(function(){
			var sel_vs_id = $(this).data('valset-id');
			if(sel_vs_id == list_Vs_id){
				lastValsetModalState.pushValsetId(sel_vs_id);
			}
		});
		
		handleLastValsetModalStates();
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
	
	$('#edit_valueset_action').validate({
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
				//error.insertAfter(element);
				error.insertAfter( element.parent("div"));
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


function showSelValSetNames(list_name){
	$('div#selected_valusets_name_display_holder').append("<div class='valset_display_div badge'>" +
			"<span>" + list_name + "</span>" +
		"</div>");
}

function clearDisplaySelectedValsets(){
	$('div#selected_valusets_name_display_holder').empty();
}