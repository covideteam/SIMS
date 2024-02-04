const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
function sortByProperty(property){  
   return function(a,b){  
      if(a[property] > b[property])  
         return 1;  
      else if(a[property] < b[property])  
         return -1;  
  
      return 0;  
   }  
}
function getServerDate(){
	var dateVal = $('#serverDate').val();
	return dateVal;
}
function getDisplayServerDateAndTime(){
	var time = "";
	var appServerTime = $("#servertime").html();
	var tempTimeArr = appServerTime.split(":");
	var date = new Date(getServerDate());
	date.setHours(tempTimeArr[0]);
	date.setMinutes(tempTimeArr[1]);
	date.setSeconds(tempTimeArr[2]);
	var seconds = date.getSeconds();
	var minutes = date.getMinutes();
	var hour = date.getHours();
	if(hour < 10)
		hour = "0"+hour;
	if(minutes < 10)
		minutes = "0"+minutes;
	if(seconds < 10)
		seconds = "0"+seconds;
	var time = hour+":"+minutes+":"+seconds;
	const d = new Date(getServerDate());
	var currentDate = getServerDate();
	d.setFullYear(currentDate.split('-')[0], currentDate.split('-')[1], currentDate.split('-')[2]);
	return currentDate.split('-')[2] + " " + months[parseInt(currentDate.split('-')[1]) - 1] + " " + currentDate.split('-')[0] + " " + time;
}
function getDisplayServerDate(){
	var time = "";
	var appServerTime = $("#servertime").html();
	var tempTimeArr = appServerTime.split(":");
	var date = new Date(getServerDate());
	date.setHours(tempTimeArr[0]);
	date.setMinutes(tempTimeArr[1]);
	date.setSeconds(tempTimeArr[2]);
	var seconds = date.getSeconds();
	var minutes = date.getMinutes();
	var hour = date.getHours();
	if(hour < 10)
		hour = "0"+hour;
	if(minutes < 10)
		minutes = "0"+minutes;
	if(seconds < 10)
		seconds = "0"+seconds;
	var time = hour+":"+minutes+":"+seconds;
	const d = new Date(getServerDate());
	d.setFullYear(currentDate.split('-')[0], currentDate.split('-')[1], currentDate.split('-')[2]);
	return currentDate.split('-')[2] + " " + months[parseInt(currentDate.split('-')[1]) - 1] + " " + currentDate.split('-')[0];
}
function getServerDateAndTime(){
	var currentDate = "";
	var time = "";
	var appServerTime = $("#servertime").html();
	var tempTimeArr = appServerTime.split(":");
	var date = new Date(getServerDate());
	date.setHours(tempTimeArr[0]);
	date.setMinutes(tempTimeArr[1]);
	date.setSeconds(tempTimeArr[2]);
	var seconds = date.getSeconds();
	var minutes = date.getMinutes();
	var hour = date.getHours();
	if(hour < 10)
		hour = "0"+hour;
	if(minutes < 10)
		minutes = "0"+minutes;
	if(seconds < 10)
		seconds = "0"+seconds;
	var time = hour+":"+minutes+":"+seconds;
	return currentDate + " " + time;
}
function getServerTime(){
	var time = "";
	var appServerTime = $("#servertime").html();
	var tempTimeArr = appServerTime.split(":");
	var date = new Date(getServerDate());
	date.setHours(tempTimeArr[0]);
	date.setMinutes(tempTimeArr[1]);
	date.setSeconds(tempTimeArr[2]);
	var seconds = date.getSeconds();
	var minutes = date.getMinutes();
	var hour = date.getHours();
	if(hour < 10)
		hour = "0"+hour;
	if(minutes < 10)
		minutes = "0"+minutes;
	var time = hour+":"+minutes;
	return time;
}
function getServerEndTimeWithSeconds(){
	var time = "";
	var appServerTime = $("#servertime").html();
	var tempTimeArr = appServerTime.split(":");
	var date = new Date(getServerDate());
	date.setHours(tempTimeArr[0]);
	date.setMinutes(tempTimeArr[1]);
	date.setSeconds(tempTimeArr[2]);
	var seconds = date.getSeconds();
	var minutes = date.getMinutes();
	var hour = date.getHours();
	if(hour < 10)
		hour = "0"+hour;
	if(minutes < 10)
		minutes = "0"+minutes;
	if(seconds < 10)
		seconds = "0"+seconds;
	var time = hour+":"+minutes+":"+seconds;
	return time;
}
function convertHoursTime(diffVal){
	var hrs = parseInt(diffVal / 60);
	if(hrs < 10)
		hrs = "0"+hrs;
    var min = parseInt(diffVal % 60);
    if(min < 10)
    	min = "0"+min;
    return hrs+":"+min;
}
function getRandomNumber() {
    return Math.floor(Math.random() * (999999999 - 100000000) + 100000000);
}
function getCsrfToken(){
	var result;
	 $.ajax({
		url:$("#mainUrl").val() +'/administration/getCsrfToken',
		type:'GET',
		async: false,
		success:function(data){
			result = data;
		}
	 });
	 return result;
}
function getCsrfTokenWithCallback(callBack){
	 $.ajax({
		url:$("#mainUrl").val() +'/administration/getCsrfToken',
		type:'GET',
		success:function(data){
			callBack(data);
		}
	 });
}
function loadJS(file) {
    // DOM: Create the script element
    var jsElm = document.createElement("script");
    // set the type attribute
    jsElm.type = "application/javascript";
    // make the script element load file
    jsElm.src = file;
    // finally insert the element to the body element in order to load the script
    document.body.appendChild(jsElm);
}

$(document).ready(function(){
	if($("#mainUrl").length > 0){
		$.ajax({
			url: $("#mainUrl").val() +'/userProjects/getUserProjects',
			type:'GET',
			success:function(data){
				var projects = "";
				for(var d=0;d<data.length;d++){
					projects = projects + "<li><a data-id='" + data[d].studyId 
							+ "'>" + data[d].studyName + "<span class='fa fa-chevron-down'></span></a>";
					if(data[d].pwsactList.length > 0){
						projects = projects + "<ul class='nav child_menu'>";	
						for(var a=0;a<data[d].pwsactList.length;a++){
							projects = projects + "<li><a data-id='" 
									+ data[d].pwsactList[a].activityId 
									+ "' data-sid='" + data[d].pwsactList[a].studyActiviyId 
									+ "'>" + data[d].pwsactList[a].activityName +"</a></li>";
						}
						projects = projects + "</ul>";
					}
					projects = projects + "</li>";
				}
				$("#liProjects").append(projects);
				init_sidebar();
			},
			error:function(er){
				debugger;
			}
		 });	
	}
	
	
  $("*").dblclick(function(e){
    e.preventDefault();
  });
  
  $(document).on("blur","[type='radio'],[type='text'],select,textarea,[type='checkbox']",function(e){
	  if($("[name='" + $(this).attr('name') + "']").attr("data-valid") === undefined 
			  || $("[name='" + $(this).attr('name') + "']").attr("data-valid") === null
			  || $("[name='" + $(this).attr('name') + "']").attr("data-valid") === true)
	  $("[name='" + $(this).attr('name') + "']").removeClass('validate-error');
  });
  
  /*$('.nav-link').click(function(e){
	  e.preventDefault();
	 $(".nav-treeview").hide('slow');
	 $(".has-treeview").removeClass('menu-open');
	 $(this).next(".nav-treeview").show('slow');
	 $(this).closest(".has-treeview").addClass('menu-open');
  });*/
});

function onImagesLoaded(container, event) {
    var images = container.getElementsByTagName("img");
    var loaded = images.length;
    for (var i = 0; i < images.length; i++) {
        if (images[i].complete) {
            loaded--;
        }
        else {
            images[i].addEventListener("load", function() {
                loaded--;
                if (loaded == 0) {
                    event();
                }
            });
        }
        if (loaded == 0) {
            event();
        }
    }
}


var dialog;
function ValidateInput(type, event, ele) {
	var key = event.charCode || event.keyCode || 0;
    var pos = event.target.selectionStart;
    var min = $(ele).data("min");
    if(min === undefined || min === null){
    	min = "-0";
    }
    if (type === "NONE") {
        event.preventDefault();
    }
    else if (type === 'DECIMAL') {
        if (event.shiftKey && event.keyCode === 9) {
            return;
        }
        
        var decimalPoint = parseInt($(ele).data("deimalpoint"));
        if ((event.keyCode >= 48 && event.keyCode <= 57) ||
            (event.keyCode >= 96 && event.keyCode <= 105) ||
            event.keyCode === 8 || event.keyCode === 9 || event.keyCode === 37 ||
            event.keyCode === 39 || event.keyCode === 46 || event.keyCode === 190 || event.keyCode === 110) {
            console.log("");
        } else {
            event.preventDefault();
        }

        if ($(ele).val().indexOf('.') > pos) {
            console.log("");
        }
        else if ($(ele).val().indexOf('.') !== -1 && (event.keyCode === 190 || event.keyCode === 110))
            event.preventDefault();
        else if ($(ele).val().indexOf('.') !== -1 && document.getElementById($(ele).attr("id")).selectionStart > $(ele).val().split('.')[0].length && $(ele).val().split('.')[1].length === decimalPoint) {
            if (event.keyCode !== 9 && event.keyCode !== 8 && event.keyCode !== 37 && event.keyCode !== 39
                && event.keyCode !== 116) {
                event.preventDefault();
            }
        }
    }
    else if (type === 'ALPHA' || type === 'ALPHABET') {
        if ((key !== 16 && (
            key === 8 ||
            key === 9 ||
            key === 13 ||
            key === 46 ||
            key === 32 ||
            key === 37 ||
            key === 39 ||
            (key >= 65 && key <= 90)))) {
            return true;
        } else {
            event.preventDefault();
        }
    }
    else if (type === 'ALPHANUMERIC') {
        if (event.shiftKey && event.keyCode === 9) {
            return;
        }
        if (event.shiftKey && (key >= 65 && key <= 90)) {
            return true;
        }
        else if (event.shiftKey) {
            event.preventDefault(); return false;
        }
        else if ((key !== 16 && (
            key === 8 ||
            key === 9 ||
            key === 13 ||
            key === 46 ||
            ((key >= 65 && key <= 90) ||
                (key >= 35 && key <= 40) ||
                (key >= 48 && key <= 57) ||
                (key >= 96 && key <= 105))))) {
            return true;
        }
        else {
            event.preventDefault();
        }
    }
    else if (type === 'NUMERIC') {
    	if (event.shiftKey && event.keyCode === 9) {
            return;
        }
    	    	
    	if(min === 1 && $.trim($(ele).val()).length === 0){
        	if(key == 189 || key == 48 || key === 1){
        		event.preventDefault();
        		return false;
        	}
        }
        else if ((key !== 16 && (
            key === 8 ||
            key === 9 ||
            key === 13 ||
            key === 46 ||
            (key >= 35 && key <= 40) ||
            (key >= 48 && key <= 57) ||
            (key >= 96 && key <= 105)))) {
            console.log("");
        } else {
            event.preventDefault();
        }
    }
    else if (type === 'empty') {
        if (event.keyCode === 8 || event.keyCode === 9 || event.keyCode === 37 ||
            event.keyCode === 39 || event.keyCode === 46 || event.keyCode === 190) {
            console.log("");
        } else {
            event.preventDefault();
        }
    }
}

function showMessage(message){
	 $.alert({
         title: 'Alert!',
         content: message,
         escapeKey: 'confirm',
         buttons: {
        	 confirm: {
                 text: 'Ok',
                 btnClass: 'btn btn-primary alterbutton',
                 action: function () {
                     
                 }
             }
         },
         onOpen: function () {
             $(".alterbutton").focus();
         }
     });
}

function filterJsonArray(jsonArray, parameter1Name, parameter1Value, parameter2Name, parameter2Value, parameter3Name, parameter3Value){
	if(parameter1Name !== null && parameter2Name !== null && parameter3Name !== null && parameter1Name !== undefined && parameter2Name !== undefined && parameter3Name !== undefined){
		return jsonArray.filter(function (el) {
			  return el[parameter1Name].toString() === parameter1Value.toString() && el[parameter2Name].toString() === parameter2Value.toString() && el[parameter3Name].toString() === parameter3Value.toString();
		});
	}
	else if(parameter1Name !== null && parameter2Name !== null && parameter1Name !== undefined && parameter2Name !== undefined){
		return jsonArray.filter(function (el) {
			  return el[parameter1Name].toString() === parameter1Value.toString() && el[parameter2Name].toString() === parameter2Value.toString();
		});
	}
	else if(parameter2Name !== null && parameter3Name !== null && parameter2Name !== undefined && parameter3Name !== undefined){
		return jsonArray.filter(function (el) {
			  return el[parameter2Name].toString() === parameter2Value.toString() && el[parameter3Name].toString() === parameter3Value.toString();
		});
	}
	else if(parameter1Name !== null && parameter3Name !== null && parameter1Name !== undefined && parameter3Name !== undefined){
		return jsonArray.filter(function (el) {
			  return el[parameter1Name].toString() === parameter1Value.toString() && el[parameter3Name].toString() === parameter3Value.toString();
		});
	}
	else if(parameter1Name !== null){
		return jsonArray.filter(function (el) {
			  return el[parameter1Name].toString() === parameter1Value.toString();
		});
	}
	else if(parameter2Name !== null){
		return jsonArray.filter(function (el) {
			  return el[parameter2Name].toString() === parameter2Value.toString();
		});
	}
	else{
		return jsonArray.filter(function (el) {
			  return el[parameter3Name].toString() === parameter3Value.toString();
		});
	}
}

$(document).ready(function(e){
	if( window.location.href.toString().indexOf("login") <= 0){
		var loaderInterVal = setInterval(function(){
			if(loaderInterVal!==null){
				var scripts = document.getElementsByTagName("script");
				for (var i = 0; i < scripts.length; i++) {
				  if(scripts[i].src.toString().toLowerCase().indexOf("datatable") > 0){
					    $(".loader").hide();
						clearInterval(loaderInterVal);
						loaderInterVal = null;
				  }
				}
			}
		}, 100);
	}
	else{
		$(".loader").hide();
	}
	
	if($("#hidPageTitle").val() !== undefined && $("#hidPageTitle").val() !== null){
		document.title = $("#hidPageTitle").val();
	}
	
	$(document).on("keydown", "[data-texttype]", function (e) {
		e.stopPropagation();
	    if (typeof $(this).attr("maxlength") !== 'undefined' && $(this).attr("maxlength") !== null && $(this).attr("maxlength") !== undefined) {
	        if (parseInt($(this).val().toString().length) > parseInt($(this).attr("maxlength"))) {
	        	var key = event.charCode || event.keyCode || 0;
	        	if(key !== 8 && key !== 37 && key !== 39 && key !== 46){
	        		return false;	
	        	}
	            
	        }
	    }
	    var textType = 'ALL';
	    if ($(this).data("texttype") !== undefined && $(this).data("texttype") !== null)
	        textType = $(this).data("texttype").toUpperCase();

	    if (textType === "NUMERIC") {
	        ValidateInput(textType, e, this);
	    } else if (textType === "ALPHANUMERIC") {
	        ValidateInput(textType, e, $(this));
	    } else if (textType === "DECIMAL") {
	        ValidateInput(textType, e, $(this));
	    } else if (textType === "ALPHABET") {
	        ValidateInput(textType, e, $(this));
	    }
	    $(this).attr("data-isupdated","0");
	});

	$(document).on("blur", "[data-texttype]", function (e) {
		var textType = 'ALL';
	    if ($(this).data("texttype") !== undefined && $(this).data("texttype") !== null)
	        textType = $(this).data("texttype").toUpperCase();

	    if (textType === "NUMERIC" || textType === "DECIMAL") {
	    	if($.trim($(this).val()).length > 0){
		    	var value = parseInt($(this).val());
		    	var isValid = true;
		    	if(isNaN(value)){
		    		isValid = false;
		    		$(this).val('');
		    		displayMessage('info','Only numeric values are allowed');
		    	}
		    	else{
		    		var min = $(this).data("min");
		    		var max = $(this).data("max");
		    		
			    	if(min !== undefined && min > value){
			    		isValid = false; $(this).val(min);
			    		displayMessage('info','Minimum value of the field is ' + min);
			    	}	
			    	else if(max !== undefined && max < value){
			    		if(value > max){
			    			isValid = false; $(this).val(max);
				    		displayMessage('info','Maximum value of the field is ' + max);
				    	}	
			    	}
			    }
		    	$(this).attr("data-isupdated","1");
	    	}
	    }
	});

});

