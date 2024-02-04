var onQrCodeScanned = null; var html5QrcodeScanner = null;
var qrCodeModal = "<div class='qrCodeModal'><div class='overlay'></div><div class='qrCode-body'><div id='qr-reader' style='width:100%;'></div><div style='width:100px;margin:auto;'><button id='btnQrClose' class='btn btn-primary'>Close</button></div></div></div>";
$("body").append(qrCodeModal);
var html5QrcodeScanner = null;
const qrCodeSuccessCallback = (decodedText, decodedResult) => {
	if(typeof onQrCodeScanned !== undefined && onQrCodeScanned !== null && onQrCodeScanned !== undefined){
		onQrCodeScanned(decodedText);
    }
	$(".qrCodeModal").hide();
	html5QrcodeScanner.pause(true);
};

function startQrCodeScanner(){
	var resultContainer = document.getElementById('qr-reader-results');
    var lastResult, countResults = 0;
    if(html5QrcodeScanner === null){
    	const width  = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    	const height = window.innerHeight|| document.documentElement.clientHeight|| document.body.clientHeight;
    	
    	$(".qrCode-body").css("left", ((width- 300)/2) + "px");
    	
    	if(width < 500){
    		html5QrcodeScanner = new Html5QrcodeScanner("qr-reader", { fps: 10, qrbox: 100 });	
    	}
    	else{
    		html5QrcodeScanner = new Html5QrcodeScanner("qr-reader", { fps: 10, qrbox: 100 });
    	}
    	html5QrcodeScanner.render(qrCodeSuccessCallback);
    }
    else{
    	$(".qrCodeModal").show();
    	html5QrcodeScanner.resume();
    }
}

$(document).on("click","#btnQrClose",function(e){
	if(html5QrcodeScanner !== null){
		if(html5QrcodeScanner.html5Qrcode.isScanning){
			html5QrcodeScanner.pause(true);	
		}
	}
	$(".qrCodeModal").hide();
});
