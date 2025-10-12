var exec = require('cordova/exec');

function isArrayOfInts(a){ return Array.isArray(a) && a.every(n => Number.isInteger(n) && n >= 0); }

exports.vibratePattern = function(pattern, success, error){
  if (!isArrayOfInts(pattern)) return error && error('Pattern must be array of non-negative integers');
  exec(success, error, 'AdvVibration', 'vibratePattern', [pattern]);
};

// Petits helpers utiles dans ton quiz
exports.once   = (ms=200)=> exports.vibratePattern([ms]);
exports.twice  = (ms=200, pause=100)=> exports.vibratePattern([ms, pause, ms]);
exports.thrice = (ms=200, pause=100)=> exports.vibratePattern([ms, pause, ms, pause, ms]);
