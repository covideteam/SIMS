$("#frmLogin").submit(function(e){
		if($.trim( $("[name='username']").val()).length === 0 || $.trim($("[name='password']").val()).length === 0 ){
			e.preventDefault();
			$.alert({
		         title: 'Alert!',
		         content: 'Enter username and password',
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
			return 
		}
		else if($.trim( $("[name='username']").val()).length === 0 ){
			e.preventDefault();
			$.alert({
		         title: 'Alert!',
		         content: 'Enter username',
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
			return 
		}
		else if($.trim($("[name='password']").val()).length === 0 ){
			e.preventDefault();
			$.alert({
		         title: 'Alert!',
		         content: 'Enter password',
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
			return 
		}
		
	});