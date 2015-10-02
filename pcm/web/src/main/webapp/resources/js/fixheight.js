$.fn.max = function(selector) { 
    return Math.max.apply(null, this.map(function(index, el) { return selector.apply(el); }).get() ); 
}

$(document).ready(function(){
	fixSize();
	
	$(window).resize(function(){
		fixSize();
	});
	
});

function fixSize(){
	$('div.div-cell').attr("style", "");
	$('div.div-cell').height(function () {
	  var maxHeight = $(this).closest('div.row').find('div.div-cell')
					  .max( function () {
						return $(this).height();
					  });
	  return maxHeight;
	});
}