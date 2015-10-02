var gruntLint = module.exports = {};
var scanfiles = require('./consent2share-pg-scanfiles.js');

gruntLint.consent2share_pg_dev = {
	src: scanfiles.files, // which files to lint
    options: {
      relaxerror: [], //string of warnings or errors to be ignored (e.g. relaxerror: ["W001"])
      showallerrors: false, //gives report of all errors and warnings, OVERRIDES stoponwarning and stoponerrors
      stoponwarning: false, //break from grunt task when warnings are found
      stoponerrors: false //break from grunt task when errors are found
    },
};
