/**
 * 
 */

 function limitMinDate(value) {
   var input = document.getElementsByName("discontinuedDate");
   input[0].setAttribute("min", value);
}

 function limitMaxDate(value) {
   var input = document.getElementsByName("introducedDate");
   input[0].setAttribute("max", value);
}