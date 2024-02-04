function displayMessage(type, msg){
	if(type == "error")
		$.growl.error({ message: msg});
	if(type == "success")
		$.growl.notice({ message: msg});
	if(type == "warning")
		$.growl.warning({ message: msg});
	if(type == "info")
		$.growl.infoMsg({ message: msg});
}