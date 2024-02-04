
var flagArr =[];

function checkElementValidation(ele){
	var value = $(ele).val();
	var type = $(ele).attr('type');
	var currentele = $(ele).get(0).tagName.toLowerCase();
    var message = $(ele).attr("data-errormessage");
    var checkflag = $(ele).attr("data-isvalid");
    var length = $(ele).parent().find(".error").length;
    var name = $(ele).attr('name');
    var flag = true;
    if(message == "" || message == undefined){
    	message = "Required Field..!";
    }
    
    if(currentele == "input"){
    	if(type == "text" || type == "number" || type == "date" || type == "time"){
    		if($.trim(value) == "")
    		    flag = false;
    		else
    		    flag = true;
    	}else if (type === "radio" || type === "checkbox") {
    	 var check = $('[name="' + name + '"]:checked').length > 0;
    		  if (check) {
    		    flag = true;
    		  } else {
    		    flag = false;
    		  }
    	}
    }else if(currentele == "textarea"){
    		if($.trim(value) == "")
    		    flag = false;
    		else
    		    flag = true;
    }else if(currentele == "select"){
    	
    	if(value == "-1" || $.trim(value) == "" || $.trim(value) == "0")
    	    flag = false;
    	else
    	    flag = true;
    }
    var finalFlag = true;
    var val = $(ele).find(".top");
    $(ele).parent().find(".error").addClass("top");
    if(checkflag == "false" || flag == false){
    	var errorElement = $(ele).parent().find('.error');
    	if(length === 0){
        	$(ele).parent().append("<label class='error'>"+message+"</label>");
        }
        else{
        	$(errorElement).text(message);
        }
    	finalFlag = false;
    	
    	$(ele).parent().find('.error').fadeIn('slow');
    }else{
    	finalFlag = true;
    	$(ele).parent().find('.error').remove();
    }
    return finalFlag;
    
    
    
}


$(document).on('change','.validate', function() {
	checkElementValidation($(this));
});
 
$(document).on('click','.validate', function() {
	checkElementValidation($(this));
});

 function validateElements(parentElement){
	 flagArr.length = 0;
	 var flag = true;
	 var finalflag2 = true;
	 $(parentElement).find('.validate:visible').each(function() {
		 if(!checkElementValidation($(this))) flag = false;
		 else flag = true;
		 flagArr.push(flag);
	  });
	 for(var i=0;i<flagArr.length;i++){
		 if(flagArr[i] == false){
			 finalflag2 = false;
		    break;
		 }
	 }
  return finalflag2;
 }
 
 
 /*if(flag){

     if(validateEmail($('#email').val())){

      $('.error').fadeOut('slow');

     }else{
        $('.error').text('Invalid Email...!');
        $('.error').fadeIn('slow');
     }

  }else{
      $('.error').text('Email Required..!');
      $('.error').fadeIn("slow");
  }
 
 
function validateEmail(eVal){
    var val = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    
    if( val.test(eVal)){
        return true;
    }else{
        return false;
    }
}*/



