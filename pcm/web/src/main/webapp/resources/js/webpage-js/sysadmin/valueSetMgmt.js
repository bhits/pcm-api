//Append csrf token to ajax call
$(function(){
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

//Initialize popovers
function initPopovers(){
	
	$('[data-toggle=popover]').popover();
	
	$('html').on('mouseup', function(e) {
		if((!$(e.target).closest('.popover').length)) {
			if((!$(e.target).closest('[data-toggle=popover]').length)){
				$('.popover').each(function(){
					$(this.previousSibling).popover('hide');
				});
			}
		}
	});
	
}

//Append an option to a select element
function appendOptionToSelect(in_select_id, in_value, in_text){
	if(in_value == ""){
		$('select#' + in_select_id).append("<option selected='selected' value=''>- Please Select -</option>");
	}else{
		$('select#' + in_select_id).append("<option value='" + in_value + "'>" + in_text + "</option>");
	}
}
