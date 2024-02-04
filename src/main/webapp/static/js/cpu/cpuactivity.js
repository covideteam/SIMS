$("#studyName").change(function(e) {
	if ($(this).val() === "") {
		$("#barcodeType").val(-1);
	}
});

$("#cpuActivityType").change(
		function(e) {
			$("#activityDiv").html('');
			if ($(this).val() != -1) {
				$.ajax({
					url : $("#mainUrl").val()
							+ "/study/clinical/cpuActivity/"
							+ $("#studyName").val() + "/"
							+ $("#cpuActivityType").val(),
					type : 'GET',
					success : function(d) {
						$("#activityDiv").append(d);
					}
				});
			}
		});
$("#studyName").change(
		function(e) {
			$("#activityDiv").html('');
			if ($("#cpuActivityType").val() != -1) {
				$.ajax({
					url : $("#mainUrl").val()
							+ "/study/clinical/cpuActivity/"
							+ $("#studyName").val() + "/"
							+ $("#cpuActivityType").val(),
					type : 'GET',
					success : function(d) {
						$("#activityDiv").append(d);
					}
				});
			}
		});