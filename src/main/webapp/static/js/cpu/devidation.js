function calculateDeviation(doseDate, timepoint,curdosewinPeriod, curdosewinSign , curdoseType){
	debugger;
	var doseActDate = new Date(doseDate);
	var ddate = new Date(doseDate);
	let doseTime = doseActDate.toLocaleTimeString('it-IT'); //en-US it can print Am also toLocaleTimeString('it-IT')
	var currentDate = new Date(getServerDate());
	currentDate = currentDate.setSeconds(0);
	currentDate = new Date(currentDate);
	var addWindowPeriod = false;
	if(doseDate > currentDate)
		addWindowPeriod = true;
	let preSentHrs = "0";
	let preMin ="0"; 
	var	deviationTime = "";
	if(ddate != null && ddate != undefined){
		var tpArr = timepoint.split(".");
		var tpMin = 0;
		if(tpArr.length > 0){
			tpMin = parseFloat("0."+tpArr[1]);
			tpMin = Math.floor(tpMin*60);
			ddate.setHours(ddate.getHours() + parseInt(tpArr[0]));
			ddate.setMinutes(ddate.getMinutes() + parseInt(tpMin));
			ddate.setSeconds(0);
			if(curdosewinSign == "" || curdosewinSign == "PLUS"){
				if(curdoseType == "HOURS"){
					preSentHrs = ddate.getHours() + parseInt(curdosewinPeriod);
					if(parseInt(preSentHrs) > 24){
						var days = hrsdiff / 24;
						var hrs = hrsdiff % 24;
						if(addWindowPeriod){
							ddate.setDate(ddate.getDate() + parseInt(days));
							ddate.setHours(ddate.getHours() + parseInt(hrs));
						}else{
							ddate.setDate(ddate.getDate() - parseInt(days));
							ddate.setHours(ddate.getHours() - parseInt(hrs));
						}
					}else{
						if(addWindowPeriod)
							ddate.setHours(ddate.getHours() + parseInt(curdosewinPeriod));
						else 
							ddate.setHours(ddate.getHours() - parseInt(curdosewinPeriod));
					}
						
				}else if(curdoseType == "MINUTES"){
					if(curdosewinPeriod > 59){
						var hrVal = Math.floor(curdosewinPeriod/60);
						var remainder = preMin%60;
						preMin = remainder;
						if(addWindowPeriod){
							ddate.setHours(ddate.getHours() + parseInt(hrVal));
							ddate.setMinutes(ddate.getMinutes() + parseInt(preMin));
						}else{
							ddate.setHours(ddate.getHours() - parseInt(hrVal));
							ddate.setMinutes(ddate.getMinutes() - parseInt(preMin));
						}
					}else{
						if(addWindowPeriod)
							ddate.setMinutes(ddate.getMinutes() + parseInt(curdosewinPeriod));
						else 
							ddate.setMinutes(ddate.getMinutes() - parseInt(curdosewinPeriod));
					}
						
				}else if(curdoseType == "DAYS"){
					if(addWindowPeriod)
						ddate.setDate(ddate.getDate() + curdosewinPeriod);
					else ddate.setDate(ddate.getDate() - curdosewinPeriod);
				}
					
				
			    var diff = ddate - currentDate;
			    var diffSeconds = diff/1000;
			    var diffHr = Math.floor(diffSeconds/3600);
			    var diffMin = Math.floor(diffSeconds%3600)/60;
			    if((diffHr+"".indexOf("-") != -1) || (diffMin+"".indexOf("-") != -1)){
			    	if(diffHr != 0)
			    		diffHr = diffHr +1;
			    	 var repHrtext = diffHr+"";
			    	 repHrtext = repHrtext.replace('-', '');
			    	 var repMintext = diffMin+"";
			    	 repMintext = repMintext.replace('-', '');
			    	 deviationTime = "-"+ Math.floor(repHrtext)+":"+Math.floor(repMintext); 
			    	
			    }else
			    	deviationTime = Math.floor(repHrtext)+":"+Math.floor(repMintext);
			}else if(curdosewinSign == "MINUS"){
				if(curdoseType == "HOURS"){
					preSentHrs = ddate.getHours() - parseInt(curdosewinPeriod);
					if(parseInt(preSentHrs) > 24){
						var days = hrsdiff / 24;
						var hrs = hrsdiff % 24;
						if(addWindowPeriod){
							ddate.setDate(ddate.getDate() + parseInt(days));
							ddate.setHours(ddate.getHours() + parseInt(hrs));
						}else{
							ddate.setDate(ddate.getDate() - parseInt(days));
							ddate.setHours(ddate.getHours() - parseInt(hrs));
						}
						
					}else{
						if(addWindowPeriod)
							ddate.setHours(ddate.getHours()+ parseInt(curdosewinPeriod));
						else ddate.setHours(ddate.getHours() - parseInt(curdosewinPeriod));
					}
						
				}else if(curdoseType == "MINUTES"){
					if(curdosewinPeriod > 59){
						var hrVal = Math.floor(curdosewinPeriod/60);
						var remainder = preMin%60;
						preMin = remainder;
						if(addWindowPeriod){
							ddate.setHours(ddate.getHours() + parseInt(hrVal));
							ddate.setMinutes(ddate.getMinutes() + parseInt(preMin));
						}else{
							ddate.setHours(ddate.getHours() - parseInt(hrVal));
							ddate.setMinutes(ddate.getMinutes() - parseInt(preMin));
						}
						
					}else{
						if(addWindowPeriod)
							ddate.setMinutes(ddate.getMinutes() + parseInt(curdosewinPeriod));
						else ddate.setMinutes(ddate.getMinutes() - parseInt(curdosewinPeriod));
					}
						
				}else if(curdoseType == "DAYS"){
					if(addWindowPeriod)
						ddate.setDate(ddate.getDate() + curdosewinPeriod);
					else 
						ddate.setDate(ddate.getDate() - curdosewinPeriod);
				}
					
				
				var diff = doseDate - currentDate;
			    var diffSeconds = diff/1000;
			    var diffHr = Math.floor(diffSeconds/3600);
			    var diffMin = Math.floor(diffSeconds%3600)/60;
			    if((diffHr+"".indexOf("-") != -1) || (diffMin+"".indexOf("-") != -1)){
			    	if(diffHr != 0)
			    		diffHr = diffHr +1;
			    	 var repHrtext = diffHr+"";
			    	 repHrtext = repHrtext.replace('-', '');
			    	 var repMintext = diffMin+"";
			    	 repMintext = repMintext.replace('-', '');
			    	 deviationTime = "-"+ Math.floor(repHrtext)+":"+Math.floor(repMintext); 
			    	
			    }else
			    	deviationTime = Math.floor(repHrtext)+":"+Math.floor(repMintext);
				
			}else{
				//Plus or Minus Condition
				var positiveDeviation = "";
				var negitivDeviation = "";
				//plus Condition
				if(curdoseType == "HOURS"){
					if(addWindowPeriod)
						preSentHrs = ddate.getHours() + parseInt(curdosewinPeriod);
					else
						preSentHrs = ddate.getHours() - parseInt(curdosewinPeriod);
					if(parseInt(preSentHrs) > 24){
						var days = hrsdiff / 24;
						var hrs = hrsdiff % 24;
						if(addWindowPeriod){
							ddate.setDate(ddate.getDate() + parseInt(days));
							ddate.setHours(ddate.getHours() + parseInt(hrs));
						}else{
							ddate.setDate(ddate.getDate() - parseInt(days));
							ddate.setHours(ddate.getHours() - parseInt(hrs));
						}
					}else{
						if(addWindowPeriod)
							ddate.setHours(ddate.getHours() + parseInt(curdosewinPeriod));
						else 
							ddate.setHours(ddate.getHours() - parseInt(curdosewinPeriod));
					}
						
				}else if(curdoseType == "MINUTES"){
					if(curdosewinPeriod > 59){
						var hrVal = Math.floor(curdosewinPeriod/60);
						var remainder = preMin%60;
						preMin = remainder;
						if(addWindowPeriod){
							ddate.setHours(ddate.getHours() + parseInt(hrVal));
							ddate.setMinutes(ddate.getMinutes() + parseInt(preMin));
						}else{
							ddate.setHours(ddate.getHours() - parseInt(hrVal));
							ddate.setMinutes(ddate.getMinutes() - parseInt(preMin));
						}
					}else{
						if(addWindowPeriod)
							ddate.setMinutes(ddate.getMinutes() + parseInt(curdosewinPeriod));
						else 
							ddate.setMinutes(ddate.getMinutes() - parseInt(curdosewinPeriod));
					}
				}else if(curdoseType == "DAYS"){
					if(addWindowPeriod)
						ddate.setDate(ddate.getDate() + curdosewinPeriod);
					else 
						ddate.setDate(ddate.getDate() - curdosewinPeriod);
				}
					
				
				
				var diff = ddate - currentDate;
			    var diffSeconds = diff/1000;
			    var diffHr = Math.floor(diffSeconds/3600);
			    var diffMin = Math.floor(diffSeconds%3600)/60;
			    var pattern = /-/;
			    if(pattern.test(diffHr) || pattern.test(diffMin)){
//			    if((diffHr+"".indexOf("-") != -1) || (diffMin+"".indexOf("-") != -1)){
			    	if(diffHr != 0)
			    		diffHr = diffHr +1;
			    	 var repHrtext = diffHr+"";
			    	 repHrtext = repHrtext.replace('-', '');
			    	 var repMintext = diffMin+"";
			    	 repMintext = repMintext.replace('-', '');
//			    	 negitivDeviation = "-"+ Math.floor(repHrtext)+":"+Math.floor(repMintext); 
			    	 //current date is higher so removing negitive sign
			    	 negitivDeviation = Math.floor(repHrtext)+":"+Math.floor(repMintext);
			    }else{
			    	positiveDeviation = Math.floor(diffHr)+":"+Math.floor(diffMin);
			    }
				if(positiveDeviation != "" && positiveDeviation != "-0:0" && positiveDeviation != "0:0"){
//					deviationTime = positiveDeviation;
					//Here dosed date is higher so adding negitive;
//					deviationTime = "-"+positiveDeviation;
					deviationTime = "-"+getHoursTimeInTwentyFourHoursFormat(positiveDeviation);
				}
				if(negitivDeviation != "" && negitivDeviation != "-0:0" && negitivDeviation != "0:0"){
//					deviationTime = negitivDeviation;
					deviationTime = getHoursTimeInTwentyFourHoursFormat(negitivDeviation)
				}
					
			}
				
		}
		return deviationTime;
	}
	
}

function getHoursTimeInTwentyFourHoursFormat (timeStr){
	var hours = "00";
	var minutes = "00";
	if(timeStr != null && timeStr != "" && timeStr != undefined){
		var arr = timeStr.split(":");
		if(arr != null && arr.length > 0){
			if(arr[0].length < 2)
				hours = "0"+arr[0];
			else hours = arr[0];
			if(arr[1].length < 2)
				minutes = "0"+arr[1];
			else minutes = arr[1];
		}
	}
	return hours +":"+minutes;
}