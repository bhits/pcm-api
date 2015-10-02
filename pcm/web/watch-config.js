var gruntWatch = module.exports = {};

gruntWatch.dev = {
	files: ['src/main/frontend/less/custom/*.less', 'src/main/frontend/less/modules/*.less'], // which files to watch
	tasks: ['newer:less:dev'],
	options: {
		nospawn: true
	}
};
