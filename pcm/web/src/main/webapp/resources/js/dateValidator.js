/**
 * DHTML date validation script. Courtesy of SmartWebby.com (http://www.smartwebby.com/dhtml/datevalidation.asp)
 */
function isDate(dtStr){
	// Declaring valid date character, minimum year and maximum year
	var dtCh= "/";
	var minYear=1900;
	var maxYear=2100;
	
	if (dtStr == "  /  /    "){
		return "Date of Birth is required";
	}
	
	var daysInMonth = DaysArray(12);
	var pos1=dtStr.indexOf(dtCh);
	var pos2=dtStr.indexOf(dtCh,pos1+1);
	var strMonth=dtStr.substring(0,pos1);
	var strDay=dtStr.substring(pos1+1,pos2);
	var strYear=dtStr.substring(pos2+1);
	strYr=strYear;
	
	if (strDay.charAt(0)=="0" && strDay.length>1){
		strDay=strDay.substring(1);
	}
	if (strMonth.charAt(0)=="0" && strMonth.length>1){
		strMonth=strMonth.substring(1);
	}
	
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1){
			strYr=strYr.substring(1);
		}
	}
	
	month=parseInt(strMonth);
	day=parseInt(strDay);
	year=parseInt(strYr);
	
	var strErr = "";
	
	if (strMonth == "  " || strMonth.length < 1 || month<1 || month>12){
		strErr = strErr + "Invalid month";
	}
	if (strDay == "  " || strDay.length < 1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		if(strErr.length > 0){
			strErr = strErr + "<br />";
		}
		strErr = strErr + "Invalid day";
	}
	if (strYear == "    " || strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		if(strErr.length > 0){
			strErr = strErr + "<br />";
		}
		strErr = strErr + "Invalid year";
	}
	
	if(strErr.length > 0){
		return strErr;
	}
	
	
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		return "Invalid date";
	}
	if (pos1==-1 || pos2==-1){
		return "The date format should be : mm/dd/yyyy";
	}
	
	return true;
}

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31;
		if (i==4 || i==6 || i==9 || i==11) {
			this[i] = 30;
		}
		if (i==2) {
			this[i] = 29;
		}
   } 
   return this;
}