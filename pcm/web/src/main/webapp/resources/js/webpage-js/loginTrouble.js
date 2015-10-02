//Code to execute on document.ready event for loginTrouble.html page
$(document).ready(function(){ 
	$("#mybutton").click(function(){
		var selectedRadioButton = $("#trouble input[name='optionRadio']:checked");
		window.location.href = selectedRadioButton.val();
	});			
});