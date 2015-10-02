/** Parses a 10 digit phone number and formats it to include
 * parenthesis around the area code and a hyphen between the
 * 3 digit exchange code and the four digit subscriber number.
 * 
 * Example of formated number: (410) 555-1234 
 * 
 * @param number
 * @returns number (formated)
 */
function phoneNumberParser (number){
	if (number.length==10){
		number="("+number.substr(0,3)+") "+number.substr(3,3)+"-"+number.substr(6,4);
	}
	
	return number;
		
}

/** Parses a 9 digit zip code to include a hyphen between the first
 * five digits and the last four digits. If zip code passed in is
 * not nine digits, then the input is returned as output unchanged.
 * 
 * @param zipcode
 * @returns zipcode (formated)
 */
function zipCodeParser(zipcode){
	if (zipcode.length==9){
		zipcode=zipcode.substr(0,5)+"-"+zipcode.substr(5,4);
	}
	return zipcode;
}

/** Function takes address, city, state, and zip code, removes the +4 digits
 *  from the extended zipcode if neccesary to form a 5 digit zip, then
 *  returns a single string with the concatenated full address.
 * 
 * @param address
 * @param city
 * @param state
 * @param zip
 * @returns {String} str_address
 */
function addressParserZip5(address, city, state, zip){
	var zip5 = zip.substr(0,5);
	var str_address = address + ", " + city + ", " + state + ", " + zip5;
	
	return str_address;
}


/**
 * Convert array into a string of HTML code defining an unordered list
 * 
 * @param {array} in_array - array to convert
 * @returns {String} out_string - returned string
 */
function arrayToUlString(in_array){
	var ary_len = in_array.length;
	var out_string = "<ul>";
	
	for(var i = 0; i < ary_len; i++){
		out_string = out_string.concat("<li>" + in_array[i] + "</li>");
	}
	
	out_string = out_string.concat("</ul>");
	
	return out_string;
}