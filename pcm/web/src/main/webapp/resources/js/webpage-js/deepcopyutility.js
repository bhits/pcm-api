function deepCopy(obj) {
	if (typeof obj == 'object') {
		if (isArray(obj)) {
			var r = new Array();
			for ( var i in obj) {
				r[i] = deepCopy(obj[i]);
			}
			return r;
		} else {
			var r = {};
			r.prototype = obj.prototype;
			for ( var k in obj) {
				r[k] = deepCopy(obj[k]);
			}
			return r;
		}
	}
	return obj;
}


var ARRAY_PROPS = {
	length : 'number',
	sort : 'function',
	slice : 'function',
	splice : 'function'
};


function isArray(obj) {
	if (obj instanceof Array)
		return true;
	// Otherwise, guess:
	for ( var k in ARRAY_PROPS) {
		if (!(k in obj && typeof obj[k] == ARRAY_PROPS[k]))
			return false;
	}
	return true;
}