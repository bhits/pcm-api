$(document).ready(function() {
			$('#normal-toggle-button').toggleButtons();
			$('#info-toggle-button').toggleButtons();
			
			$('body').removeClass('fouc_loading');

			$(".submit_buttons").click(function() {
				$(this).attr("disabled", "disabled");
			});
			
			var sign_revoke_arys = {
					consentPreSignList: [],
					consentPreRevokeList: []
			};

			initListConsent(sign_revoke_arys);
			
			var duplicate_consent_id = $('input#duplicate_consent_id').val();
			
			if((typeof duplicate_consent_id != 'undefined') && (typeof duplicate_consent_id != 'null')){
				$("section.consent-current-active#consent_current_active_" + duplicate_consent_id).children("div.summary-border").first().addClass("duplicate-consent");
			}
			
			resize_objects();
	 		$(window).resize(resize_objects);

	 		function resize_objects(){
		 		var $a = $('.column-one').height(), $b = $('.column-two').height();
				var childA = $('.column-one').children().height();
				var childB = $('.column-two').children().height();
				
				if( $a > childA && $b > childA && childA >= childB){
		 		    $(".column-one").css("min-height",childA);
		 		    $(".column-two").css("min-height",childA);
				}
				else if($a > childB && $b > childB && childB >= childA){
		 		    $(".column-one").css("min-height",childB);
		 		    $(".column-two").css("min-height",childB);
				}
				else if($a > $b) {
		 		    //#one is higher than #two
		 		    $(".column-two").css("min-height",$a);
		 		}
		 		else {
		 		    //#two is higher than #one
		 		    $(".column-one").css("min-height",$b);
	 		    }
	 		}

	 		$('#applyMyPolicyForm').validate({
	 			rules: {
	 				c32Id: "required",
	 				purposeOfUse: "required"
	 			},
	 			messages: {
	 				c32Id: "This field is required. Please upload a c32 Document.",
	 				purposeOfUse: "Purpose of Use is required."
	 			},
	 	        errorClass: 'valueSetErrMsg'
	 		});		
	 		
});



function openSignConsentPage(id){
	
	
	$.ajax({
		  type: "POST",
		  url: 'signConsent.html',
		  consentId:id,
		  success:function(data){
			  if(data == 'OK') {
			        window.open(url);
			  }
		  }
		  
		});
}

//check status of the consent status
function initConsentPresignStatusChecker (sign_revoke_arys) {
	if (sign_revoke_arys.consentPreSignList.length!=0||sign_revoke_arys.consentPreRevokeList!=0) {
			valueInterval=0;
			
			setInterval(function() {						
				$.ajax({
				url: "listConsents.html/checkConsentStatus",
				type:"GET",
				traditional: true,
				async:true, 
				data: {consentPreSignList:sign_revoke_arys.consentPreSignList,
					consentPreRevokeList:sign_revoke_arys.consentPreRevokeList},
						
				success: function (result) { 
				
					if(result.trim()=="true")
						 window.location.replace("listConsents.html");
				}
				});
			}, 1000 * 60 *0.25);
	}
}

function initRevokeModalListeners() {
	var consentRevokationId=null;
				
	$(".revokation-button").live("click",function(){
		consentRevokationId=$(this).attr("id").substr(6,$(this).attr("id").length-6);
	});
	
	$("#signRevokation").click(function(){
//		if(!($("#optionsRadio1").is(':checked')) && !($("#optionsRadio2").is(':checked'))){
//			window.alert("Please select one option.");
//		}
//		else{
			$("#consentRevokationForm").append('<input name="consentId" style="display: none;" hidden="true" value="'+consentRevokationId+'"/>');
			$("#consentRevokationForm").submit();
		//}
		
	});
	
	//Bind hidden.bs.modal event handler to clear modal fields
	$("#revoke-modal").on('hidden.bs.modal', function(){
		document.getElementById("consentRevokationForm").reset();
	});
}


function initListConsent(sign_revoke_arys) {
	
	$('.consent-entry-input').each(function(){
		if($(this).data('consentstage')=="CONSENT_SAVED"){
			sign_revoke_arys.consentPreSignList.push($(this).val());
		}
		
		if($(this).data('consentstage')=="CONSENT_SIGNED"&&($(this).data('revokestage')!="REVOCATION_REVOKED")){
			sign_revoke_arys.consentPreRevokeList.push($(this).val());
		}
	});
	
	initConsentPresignStatusChecker(sign_revoke_arys);
	initRevokeModalListeners();
};


function loadTryMyPolicy(consentId) {
	var tryMyPocilyurl = "tryMyPolicyLookupC32Documents/"+consentId; 
	$.ajax({
		url: tryMyPocilyurl,
		type:"GET",
		traditional: true,
		async:true, 
		data: {consentId: consentId},
		success: function (data) { 
			$("#tryMyPolicy_c32Docs").empty();
			$('label[for="tryMyPolicy_c32Docs"][generated="true"]').hide();
			$('label[for="tryMyPolicy_purposeOfUse"][generated="true"]').hide();
			$("#consentId").remove();
			$("#applyMyPolicyForm").append("<input id='consentId' type='hidden' name='consentId' value='" + consentId + "' />");
			
			// populate c32 documents
			$.each(data.c32Documents, function(index, element) {
				$("#tryMyPolicy_c32Docs").append('<option value='+element.id+' name="c32Id">'+element.filename+'</option>');

	        });
			
			$("#tryMyPolicy_purposeOfUse").empty();
			
			// populate share for purpose of use codes
			$.each(data.shareForPurposeOfUseCodesAndValues, function(index, element){
				// Only add Treatment option
				if (element === "Healthcare Treatment") {
					$("#tryMyPolicy_purposeOfUse").append("<option value='"+index+"'>"+element+"</option>");
				}
			});
		}
	});
}

$('#tryMyPolicyApplyButton').click(function(){
    var consentId = $('#consentId').val();
    var c32Id = $("#tryMyPolicy_c32Docs").val();
    var purposeOfUse = $("#tryMyPolicy_purposeOfUse").val();
    
    // do not submit if c32 doc or purpose of use is missing
    if (!c32Id || !purposeOfUse) {
    	return;
    }
	var url = "tryMyPolicyApply/consentId/" + consentId + "/c32Id/" + c32Id + "/purposeOfUse/" + purposeOfUse;
	window.open(url);
});

$("#applyMyPolicyForm").submit(function() {

    var url = "tryMyPolicyApply"; // the script where you handle the form input.
    
//    var jqxhr = $.ajax({
//           type: "POST",
//           url: url,
//           data: $("#applyMyPolicyForm").serialize(), // serializes the form's elements.
//           success: function(data)
//           {
//        	   window.alert("SUCCESS");
//               //window.alert("PASS: " + data);
//               
//               //var win=window.open('about:blank');
//               //with(win.document)
//               //{
//                 //open();
//                 //write(data);
//                 //close();
//              // }
//               
//               //window.open('data:text/xml,' + encodeURIComponent(data) );
//               
//              // var win=window.open('Try My Policy');
//             // $(win.document.body).html(data);
//           },
//           error: function(e){
//        	   window.alert("ERROR: " + e.responseText);
//           }
//         });
    
//    jqxhr.always(function(){
//    	window.alert("JQXHR TRIGGER");
//    	
//    	window.alert(jqxhr.responseXML);
//    	
//    });

    return false; // avoid to execute the actual submit of the form.
});
