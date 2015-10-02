var path = require('path');
var gruntLess = module.exports = {};

gruntLess.dev = {
	files: [{
		expand: true,
		cwd: 'src/main/frontend/less/',
		src: ['custom/*.less', 'modules/*.less'],
		dest: 'src/main/webapp/resources/css/',
		rename: function(dest, src) {
			return path.join(dest, src.replace(/\/less\//g, '/css/'));
		},
		ext: '.css'
	}]
};
