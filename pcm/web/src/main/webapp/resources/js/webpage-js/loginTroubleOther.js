//Code to execute on document.ready event for loginTroubleOther.html page
$(document).ready(function(){
	$("#mybutton").click(function(){
		var selectedRadioButton = $("#other-troubles input[name='optionRadio']:checked");
		window.location.href = selectedRadioButton.val();
	});
});