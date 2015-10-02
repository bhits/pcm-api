//Code to execute on document.ready event for pages with the footer fragment from common.html included
$(document).ready(function() {
	//var tempDiv = document.getElementById("sidenavMenu");
	if(document.getElementById("sidenavMenu")){
		var curURI = window.location.pathname;
		var endIndex = curURI.indexOf(".html");
		var startIndex = curURI.lastIndexOf("/", endIndex) + 1;
		var curPage = curURI.substring(startIndex, endIndex);

		switch(curPage){
		case "home":
			if(document.getElementById("sidenav_home"))
				document.getElementById("sidenav_home").className += " active";
			if(document.getElementById("sidenav_codeSystems"))
				document.getElementById("sidenav_codeSystems").className += " active";
			break;
		case "listConsents":
			document.getElementById("sidenav_consents").className += " active";
			break;
		case "connectionMain":
			document.getElementById("sidenav_providers").className += " active";
			break;
		case "profile":
			document.getElementById("sidenav_profile").className += " active";
			break;
		case "inboxMain":
			document.getElementById("sidenav_msgcenter").className += " active";
			break;
		case "activityHistory":
			document.getElementById("sidenav_activityhist").className += " active";
			break;
		case "codeSystemAdd":
		case "codeSystemEdit":
			document.getElementById("sidenav_codeSystems").className += " active";
			break;			
		case "codeSystemVersionList":
		case "codeSystemVersionAdd":
		case "codeSystemVersionEdit":
			document.getElementById("sidenav_codeSystemVersions").className += " active";
			break;
		case "valueSetList":
		case "valueSetAdd":
		case "valueSetEdit":
			document.getElementById("sidenav_valueSets").className += " active";
			break;
		case "conceptCodeList":
		case "conceptCodeAdd":
		case "conceptCodeEdit":
			document.getElementById("sidenav_conceptCodes").className += " active";
			break;
		case "valueSetCategoryList":
		case "valueSetCategoryAdd":
		case "valueSetCategoryEdit":
			document.getElementById("sidenav_valueSetCats").className += " active";
			break;
		case "lookup":
			document.getElementById("sidenav_lookup").className += " active";
			break;
		default:
			break;	
		}
	}
});