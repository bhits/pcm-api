var _ = require('lodash');

var lessConfig = require('./less-config.js');
var watchConfig = require('./watch-config.js');

var gruntConf = {};

gruntConf.less = {};
gruntConf.watch = {};

_.extend(gruntConf.less, lessConfig);

_.extend(gruntConf.watch, watchConfig);

module.exports = function(grunt) {
	require('jit-grunt')(grunt);

	// Project configuration.
	grunt.initConfig(gruntConf);

	grunt.registerTask('default', ['newer:less', 'watch']);
};
