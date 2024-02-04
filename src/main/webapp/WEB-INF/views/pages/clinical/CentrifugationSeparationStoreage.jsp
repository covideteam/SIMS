<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
</head>
<body>
	<div class="card">
		<div class="card-header">
			<h3 class="card-title">Centrifuge Process</h3>
		</div>
		<!-- /.card-header -->
		<div class="card-body">
			<table id="maintable" class="table table-bordered table-striped">
				<tr>
					<td>Scan Barcode</td>
					<td><input type="text" name="barcode" id="barcode" /> <font
						style="color: red" id="barcodeMsg"></font> <input type="hidden"
						id="timePintDataAndTime" value="" /></td>
					<td><input type="button" id="Reset" value="Reset"
						onclick="centrifugationCall('centrifugation')" /></td>
					<td colspan="4" />
				</tr>
				<tr>
					<td>Instrument :</td>
					<td id="insturmentMsg" colspan="5"></td>
				</tr>
				<tr>
					<td>Speed(rpm) :</td>
					<td><input type="text" id="speed" name="speed"
						readonly="readonly" /></td>
					<td>Process Time(min) :</td>
					<td><input type="text" id="processTime" name="processTime"
						readonly="readonly" /></td>
					<td>Temperature(°C) :</td>
					<td><input type="text" id="temperature" name="temperature"
						readonly="readonly" /></td>
				</tr>
				<tr>
					<th>Conditions</th>
					<td colspan="5">Buffer/Ice water bath storage <input
						type="radio" name="condition" value="Yes"
						onchange="requiredField( 'condition', 'centrifugeCondition', '', true, '')">Yes<input
						type="radio" name="condition" value="No"
						onchange="requiredField( 'condition', 'centrifugeCondition', '', true, '')">No
						<font color="red" id="centrifugeCondition"></font>
					</td>
				</tr>
			</table>
			<table id="vacutinersTable" border="1">
				<tr>
					<th>Project No</th>
					<th>Period</th>
					<th>Subject</th>
					<th>Time Point</th>
					<th />
				</tr>
			</table>
			<font color="red" id="vacutinersTableMsg"></font>
			<table border="1">
				<tr>
					<td>Start Time :</td>
					<td id="startTimeTd"><input type="button" id="startButton"
						value="Start" onclick="startTime()" /><font color="red"
						id="startMsg"></font></td>
				</tr>
				<tr>
					<td>End Time :</td>
					<td id="endTimeTd"><input type="button" id="endButton"
						disabled="disabled" value="End" onclick="endTime()" /><font
						color="red" id="endMsg"></font></td>
				</tr>
			</table>
			<!-- 			Sample Separation -->
			Continue With <a href="javascript:void(0);"
				onclick="centrifugationCall('separation')" class="nav-link">Sample
				Separation</a>
			<div id="sampleSeparationDiv">
				<table id="example0" class="table table-bordered table-striped">
					<tr>
						<td>Scan Barcode</td>
						<td><input type="text" name="separationbarcode"
							id="separationbarcode" /> <font style="color: red"
							id="barcodeMsg"></font></td>
					</tr>
				</table>
				<table id="example1" class="table table-bordered table-striped">
					<tr>
						<td>Vacutainer</td>
						<td id="vacutainerMsg"></td>
					</tr>
				</table>
				<table id="vacutinersOfSampleSeparionTable" border="1">
					<tr>
						<th>Study</th>
						<th>Period</th>
						<th>Time Point</th>
						<th>Subject</th>
						<th />
					</tr>
				</table>
				<font color='red' id="separationVacutinerMsg"></font>
				<table id="example2" class="table table-bordered table-striped">
					<tr>
						<td>Missing samples</td>
						<td><input type="radio" name="missingSampleSeparation"
							value="Yes">Yes<input type="radio"
							name="missingSampleSeparation" value="No">No</td>
					</tr>
					<tr>
						<td>Details(Subject numbers)</td>
						<td><input type="text" name="missingSubjectsSeparation"
							id="missingSubjectsSeparation" /></td>
					</tr>
					<tr>
						<td>Comments</td>
						<td><input type="radio" name="commentsSeparation" value="Yes">Yes<input
							type="radio" name="commentsSeparation" value="No">No</td>
					</tr>
					<tr>
						<td>Details(Subject numbers)</td>
						<td><input type="text" name="commentsSubjectsSeparation"
							id="commentsSubjectsSeparation" /></td>
					</tr>
					<tr>
						<td>Comments</td>
						<td><input type="text" name="commentSeparation"
							id="commentSeparation" /></td>
					</tr>
				</table>
				<table>
					<tr>
						<td>Start Time :</td>
						<td id="startTimeSeparationTd"><input type="button"
							disabled="disabled" id="startSeparationButton" value="Start"
							onclick="startTimeSeparation()" /><font color="red"
							id="startSeparationMsg"></font></td>
					</tr>
					<tr>
						<td>End Time :</td>
						<td id="endTimeSeparationTd"><input type="button"
							disabled="disabled" id="endSeparationButton" value="End"
							onclick="endSeparationTime()" /><font color="red"
							id="endSeparationMsg"></font></td>
					</tr>
				</table>
				<input type="hidden" name="projectNo" id="projectNo" /><input
					type="hidden" name="periodNo" id="periodNo" /> <input
					type="hidden" name="timePoint" id="timePoint" /><input
					type="hidden" name="subject" id="subject" /> <input type="hidden"
					name="vacutainer" id="vacutainer" /><br /> <input type="hidden"
					name="vacutainerScanTime" id="vacutainerScanTime" /><br /> <input
					type="hidden" name="noOfVials" id="noOfVials" value="0" />
				<div id="vialsDiv"></div>
				<div id="vialsScanTimeDiv"></div>
			</div>


			<!-- 			Storage -->
			Continue With <a href="javascript:void(0);"
				onclick="centrifugationCall('Storage')" class="nav-link">Sample
				Storage</a>
			<div id="sampleStorageDiv">
				<table id="example2" class="table table-bordered table-striped">
					<tr>
						<td>Scan Barcode</td>
						<td><input type="text" name="storagebarcode"
							id="storagebarcode" /> <font style="color: red"
							id="storagebarcodeMsg"></font></td>
					</tr>
				</table>
				<div id="storateDiv"></div>
				<table>
					<tr>
						<th>Shelf ID</th>
						<td id="shelfIDTd"></td>
						<th>Deepfreezer ID</th>
						<td id="DeepfreezerIDId"></td>
					</tr>
				</table>
				<table>
					<tr>
						<td>Missing samples</td>
						<td><input type="radio" name="missingSampleStorage"
							value="Yes">Yes<input type="radio"
							name="missingSampleStorage" value="No">No</td>
					</tr>
					<tr>
						<td>Details(Subject numbers)</td>
						<td><input type="text" name="missingSubjectsStorage"
							id="missingSubjectsStorage" /></td>
					</tr>
					<tr>
						<td>Comments</td>
						<td><input type="radio" name="commentsStorage" value="Yes">Yes<input
							type="radio" name="commentsStorage" value="No">No</td>
					</tr>
					<tr>
						<td>Details(Subject numbers)</td>
						<td><input type="text" name="commentsSubjectsStorage"
							id="commentsSubjectsStorage" /></td>
					</tr>
					<tr>
						<td>Comments</td>
						<td><input type="text" name="commentStorage"
							id="commentStorage" /></td>
					</tr>
					<tr>
						<td />
						<td><input type="button" id="endStorageButton"
							disabled="disabled" value="Conform"
							onclick="smapleStorageConform()" /></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$("#barcode").focus();
	var instrumentScanTime = '';
	var instrumentBarcode = '';
	var centrifugeId = "0"; // organization centrifuge machine detalis

	var studyId = 0;
	var periodId = 0;
	var sampleCentrifugationId = "0"; // study centrifuge details

	var vacutainers = [];
	var centrifugeDataMasterId = "0"; // current centrifuge Id
	var centrifugeDataEndId = "0";// current centrifuge Id
	var diffStudy = true;
	var diffAllow = true;
	$("#barcode")
			.on(
					'input',
					function(e) {
						var barcode = $("#barcode").val();
						var length = barcode.length;
						if (length == 23) {
							$("#barcode").val("");
							var prefix = barcode.substr(0, 2);
							if (prefix == "06") {//instrument
								console.log(barcode);
								instrumentScanTime = ranningTime;
								var result = synchronousAjaxCall(mainUrl
										+ "/study/clinical/centrifugeInstumentInfo/"
										+ barcode);
								if (result != '' || result != 'undefined') {
									var c = result.split('--');
									if (c.length > 1) {
										if (c[0] == '0') {
											instrumentBarcode = barcode;
											centrifugeId = c[1];
										}
										$("#insturmentMsg").html(c[2]);
									} else
										alert(c);
								}
							} else if (prefix == "04") {//vacutainer
								console.log(barcode);
								var vacutainerScanTime = ranningTime;
								if (studyId != '0' && diffAllow) {
									var subjectStudy = parseInt(barcode
											.substring(2, 8));
									// 									alert(studyId + "  " + subjectStudy)
									if (studyId != subjectStudy) {
										diffStudy = confirm("You have scanned diffrent study vacutiner. <br/> Do You want to continue?");
										if (diffStudy)
											diffAllow = false;
									}
								}

								if (diffStudy) {
									studyId = parseInt(barcode.substring(2, 8));
									var result = synchronousAjaxCall(mainUrl
											+ "/study/clinical/centrifugeVacutainerInfo/"
											+ barcode + "/" + studyId);
									if (result != '' || result != 'undefined') {
										var c = result.split('--');
										if (c.length > 1) {
											if (c[0] == '0') {
												$("#vacutinersTable  tbody")
														.append(c[1]);
												if ($("#speed").val() == '') {
													$("#speed").val(c[2]);
													$("#processTime").val(c[3]);
													$("#temperature").val(c[4]);
													sampleCentrifugationId = c[5];
													periodId = c[6];
												}
												vacutainers.push(barcode + '--'
														+ vacutainerScanTime);
												$("#vacutinersTableMsg").html(
														"");
											}
										} else
											alert(c);
									}
								}

							}
						}
					});
	function removeVacutiner(barcode) {
		$("#" + barcode).remove();
		var index = -1;
		for (var i = vacutainers.length - 1; i >= 0; i--) {
			var vv = vacutainers[i].split("--");
			if (vv[0] === barcode) {
				vacutainers.splice(i, 1);
				index = i;
			}
		}
		if (index != -1)
			delete vacutainers[i];
	}

	function startTime() {
		var conditioncheck = requiredField('condition', 'centrifugeCondition',
				'', true, '');
		var instrumentBarcodecheck = insturmentrequiredNonField(
				instrumentBarcode, 'insturmentMsg', '',
				'<font color="red">Please, Scan Centrifuge Barcode</font>');
		var vacutainerscheck = requiredNonField(vacutainers.toString(),
				'vacutinersTableMsg', '',
				'<font color="red">Please, Scan Vacutainer Barcode</font>');
		// 		alert(conditioncheck)
		// 		alert(instrumentBarcodecheck)
		// 		alert(vacutainerscheck)
		if (conditioncheck && instrumentBarcodecheck && vacutainerscheck) {
			var product = {
				runningTime : ranningTime,
				runningTimeWithSeconds : runningTimeWithSeconds,
				instrumentScanTime : instrumentScanTime,
				instrumentBarcode : instrumentBarcode,
				speed : $("#speed").val(),
				processTime : $("#processTime").val(),
				temperature : $("#temperature").val(),
				condition : $('input[name="condition"]:checked').val(),
				centrifugeId : centrifugeId,
				sampleCentrifugationId : sampleCentrifugationId,
				vacutainers : vacutainers.toString()
			};
			var conroller = mainUrl + "/study/clinical/centrifugationStartSave";
			// 		var conroller = mainUrl + "/sampleProcess/centrifugationStartSave";
			console.log(JSON.stringify(product));
			$.ajax({
				async : false,
				url : conroller,
				data : product,
				success : function(data) {
					console.log(data);
					centrifugeDataMasterId = data;
					$("#startTimeTd").html(runningTimeWithSeconds);
					$("#barcode").prop("disabled", true);
					$('input[name=centrifugationVacutinerButton]').prop(
							'disabled', true)
					$("#endButton").removeAttr('disabled');

				}
			});
		}

	}
	function endTime() {
		var product = {
			runningTimeWithSeconds : runningTimeWithSeconds,
			centrifugeDataId : centrifugeDataMasterId
		};
		var conroller = mainUrl + "/study/clinical/centrifugationEndSave";
		// 		var conroller = mainUrl + "/sampleProcess/centrifugationStartSave";
		console.log(JSON.stringify(product));
		$.ajax({
			async : false,
			url : conroller,
			data : product,
			success : function(data) {
				console.log(data);
				centrifugeDataEndId = data;
				$("#endTimeTd").html(runningTimeWithSeconds);
				$("#startSeparationButton").removeAttr('disabled');

			}
		});
	}
</script>
<script type="text/javascript">
	function requiredField(id, mesageId, compareValue, multiple, message) {
		$("#" + mesageId).html("");
		if (multiple) {
			var con = $('input[name="' + id + '"]:checked').val();
			if (con == undefined) {
				if (message != '')
					$("#" + mesageId).html(message);
				else
					/* $("#" + mesageId).html("Required Field"); */
					$('#'+mesageId).html('${validationText}');
				return false;
			} else
				return true;
		} else {
			if ($("#id").val() == compareValue) {
				if (message != '')
					$("#" + mesageId).html(message);
				else
					/* $("#" + mesageId).html("Required Field"); */
					$('#'+mesageId).html('${validationText}');
				return false;
			} else
				return true;
		}

	}
	function requiredNonField(value, mesageId, compareValue, message) {
		$("#" + mesageId).html("");
		if (value == compareValue) {
			// 		alert(message != '')
			if (message != '')
				$("#" + mesageId).html(message);
			else
				/* $("#" + mesageId).html("Required Field"); */
				$('#'+mesageId).html('${validationText}');
			return false;
		} else
			return true;
	}
	function insturmentrequiredNonField(value, mesageId, compareValue, message) {
		if (value == compareValue) {
			// 		alert(message != '')
			if (message != '')
				$("#" + mesageId).html(message);
			else
				/* $("#" + mesageId).html("Required Field"); */
				$('#'+mesageId).html('${validationText}');
			return false;
		} else
			return true;
	}
</script>
<script type="text/javascript">
	var separaitonData = {};
	var separationStartId = "0";
	var separationEndId = "0";
	function clearForm() {
		$("#vacutainer").val("");
		$("#vacutainerScanTime").val("");
		$("#example1")
				.html(
						'<tbody><tr><td>Vacutainer</td><td id="vacutainerMsg"></td></tr></tbody>');
		$("#noOfVials").val("");
		$("#vialsDiv").html("");
		$("#vialsScanTimeDiv").html("");
		$("#projectNo").val("");
		$("#periodNo").val("");
		$("#timePoint").val("");
		$("#subject").val("");
	}

	$("#separationbarcode")
			.on(
					'input',
					function(e) {
						var barcode = $("#separationbarcode").val();
						$("#barcodeMsg").html("");
						var length = barcode.length;
						if (length == 23) {
							$("#separationbarcode").val("");
							var prefix = barcode.substr(0, 2);
							if (prefix == "04") {//vacutainer
								clearForm();
								var scanTime = ranningTime;
								console
										.log(mainUrl
												+ "/study/clinical/vacutainerForSampleSeparation/"
												+ barcode);
								var result = synchronousAjaxCall(mainUrl
										+ "/study/clinical/vacutainerForSampleSeparation/"
										+ barcode);
								console.log(result);
								if (result != '' || result != 'undefined') {
									var c = result.split('--');
									if (c.length > 1) {
										$("#vacutainerMsg").html(c[2]);
										if (c[0] == '0') {
											$("#vacutainerMsg").css(
													'background-color', '');
											$("#vacutainer").val(barcode);
											$("#vacutainerScanTime").val(
													scanTime);

											var vials = c[1];
											var hiddenFields = "";
											var fields = "";
											var rows = "";
											$("#noOfVials").val(vials);
											var vialsScanTimes = "";
											for (var count = 1; count <= vials; count++) {
												hiddenFields += "<input type ='hidden' name='vial"+count+"' id='vial"+count+"'/><br/>";
												vialsScanTimes += "<input type ='hidden' name='vialScan"+count+"' id='vialScan"+count+"'/><br/>";
												rows += "<tr><td>Vial-"
														+ count
														+ "</td><td id='vial"+count+"Msg'></td></tr>";
											}
											// 											alert(rows)
											$("#example1  tbody").append(rows);
											$("#vialsDiv").html(hiddenFields);
											$("#vialsScanTimeDiv").html(
													vialsScanTimes);
											$("#projectNo").val(c[3]);
											$("#periodNo").val(c[4]);
											$("#timePoint").val(c[5]);
											$("#subject").val(c[6]);
										} else if (c[0] == '1') {
											$("#vacutainerMsg").css(
													'background-color', 'red');
										}
									} else {
										$("#barcodeMsg").html(result);
									}
								}
								$("#barcode").val("");
							} else if (prefix == "05") {
								var scanTime = ranningTime;
								// 			console.log(barcode);
								var v1 = barcode.substr(2);
								// 			console.log(v1.substr(0,20));
								var vial = "04" + v1.substr(0, 20) + "0";
								// 				console.log(vial)
								var endChar = barcode.substr(22);
								// 				console.log(endChar)
								var vacutainer = $("#vacutainer").val();
								if (vacutainer == "") {
									$("#vacutainerMsg")
											.html(
													"<font color='red'>Please, scan vacutainer required</font>");
								} else {
									console.log(vacutainer);
									console.log(vial)
									if (vacutainer == vial) {
										$("#vial" + endChar + "Msg").html(
												$("#vacutainerMsg").html()
														+ ", Vial-" + endChar);
										$("#vial" + endChar).val(barcode);
										$("#vialScan" + endChar).val(scanTime);
									} else {
										$("#vial" + endChar + "Msg").html("");
										$("#vial" + endChar).val("");
										$("#barcodeMsg")
												.html(
														"Vial-"
																+ endChar
																+ " is not matched With Vacutainer");
									}
									autoAppendForm();
								}
							}
						} else if (length > 23)
							$("#barcode").val("");
					});

	function autoAppendForm() {
		var separationData = [];
		if ($("#vacutainer").val() != '') {
			var flag = true;
			for (var i = 1; i <= $("#noOfVials").val(); i++) {
				if ($("#vial" + i).val() == '') {
					flag = false;
				}
			}
			if (flag) {
				var data = $("#vacutainer").val() + "##"
						+ $("#vacutainerScanTime").val() + "##"
				var vacutainer = $("#vacutainer").val();
				var vacutainerScanTime = $("#vacutainerScanTime").val();
				var noOfVials = $("#noOfVials").val();
				var vial = "";
				for (var i = 1; i <= noOfVials; i++) {
					if (i != 1) {
						vial += "#" + $("#vial" + i).val() + "--"
								+ $("#vialScan" + i).val();
					} else
						vial = $("#vial" + i).val() + "--"
								+ $("#vialScan" + i).val();
				}
				// 				alert(vial);
				data += vial;
				$("#vacutinersOfSampleSeparionTable  tbody")
						.append(
								"<tr id=\"sep"+vacutainer+"\"><td>"
										+ $("#projectNo").val()
										+ "</td><td>"
										+ $("#periodNo").val()
										+ "</td><td>"
										+ $("#timePoint").val()
										+ "</td><td>"
										+ $("#subject").val()
										+ "</td><td><input type='button'  value='Remove' onclick=\"removeVac('sep_"
										+ vacutainer
										+ "'')\" name='separationVacutinerButton'/></tr>");
				separaitonData["sep_" + vacutainer] = data;
				clearForm();
				$("#separationVacutinerMsg").html("");
			}
		}
	}
	function removeVac(barcode) {
		$("#sep_" + barcode).remove();
		$.each(separaitonData, function(k, v) {
			if (k == barcode) {
				separaitonData[barcode] = '';
			}
		});
	}
	function startTimeSeparation() {
		var separaitonDataArray = [];
		var flag = false;
		$.each(separaitonData, function(k, v) {
			if (v != '') {
				separaitonDataArray.push(v);
				flag = true;
			}
		});
		if (!flag)
			$("#separationVacutinerMsg").html("Vacutainer and Vails Match Required");
		else {
			var product = {
				centrifugeDataMasterId : centrifugeDataMasterId,
				runningTimeWithSeconds : runningTimeWithSeconds,
				missingSampleSeparation : $(
						'input[name="missingSampleSeparation"]:checked').val(),
				missingSubjectsSeparation : $('#missingSubjectsSeparation')
						.val(),
				commentsSeparation : $(
						'input[name="commentsSeparation"]:checked').val(),
				commentsSubjectsSeparation : $("#commentsSubjectsSeparation")
						.val(),
				commentSeparation : $("#commentSeparation").val(),
				separaitonData : separaitonDataArray.toString()
			};
			var conroller = mainUrl
					+ "/study/clinical/sampleSeparationStartSave";
			// 		var conroller = mainUrl + "/sampleProcess/centrifugationStartSave";
			console.log(JSON.stringify(product));
			$.ajax({
				async : false,
				url : conroller,
				data : product,
				success : function(data) {
					console.log(data);
					separationStartId = data;
					$("#startTimeSeparationTd").html(runningTimeWithSeconds);
					$("separationbarcode").attr('disabled', 'disabled');
					$('input[name="separationVacutinerButton"]').attr(
							'disabled', 'disabled');
					$("#endSeparationButton").removeAttr('disabled');
				}
			});
		}

	}
	function endSeparationTime() {
		var product = {
			centrifugeDataMasterId : centrifugeDataMasterId,
			runningTimeWithSeconds : runningTimeWithSeconds,
			separationStartId : separationStartId
		};
		var conroller = mainUrl + "/study/clinical/sampleSeparationEndSave";
		// 		var conroller = mainUrl + "/sampleProcess/centrifugationStartSave";
		console.log(JSON.stringify(product));
		$.ajax({
			async : false,
			url : conroller,
			data : product,
			success : function(data) {
				console.log(data);
				// 				separationEndId = data;
				$("#storateDiv").html(data);
				$("#endTimeSeparationTd").html(runningTimeWithSeconds);
				$("#endStorageButton").removeAttr('disabled');
			}
		});
	}
</script>
<script type="text/javascript">
	function storageData() {
		var centrifugeId = $("#centrifugeId").val();
		if (centrifugeId != '-1') {
			var result = synchronousAjaxCall(mainUrl
					+ "/study/clinical/centrifugeData/" + centrifugeId);
			//				alert(result)
			if (result != '' || result != 'undefined') {
				$("#centrifugationDataDiv").html(result)
			}
		}
	}
	var shelfbarcode = "";
	var deepfreezerBarcode = "";
	var submitedAloquotNo = [];
	$("#storagebarcode").on(
			'input',
			function(e) {
				var barcode = $("#storagebarcode").val();
				$("#storagebarcodeMsg").html("");
				var length = barcode.length;
				if (length == 23) {
					$("#storagebarcode").val("");
					var prefix = barcode.substr(0, 2);
					if (prefix == "07") {//vacutainer
						var scanTime = runningTimeWithSeconds;
						console.log(mainUrl + "/study/clinical/shelfDetails/"
								+ barcode);
						var result = synchronousAjaxCall(mainUrl
								+ "/study/clinical/shelfDetails/" + barcode);
						console.log(result);
						if (result != '' || result != 'undefined') {
							$("#shelfIDTd").html(result);
							shelfbarcode = barcode;
						}
					} else if (prefix == "08") {//vacutainer
						var scanTime = runningTimeWithSeconds;
						console.log(mainUrl
								+ "/study/clinical/deepfreezerDetails/"
								+ barcode);
						var result = synchronousAjaxCall(mainUrl
								+ "/study/clinical/deepfreezerDetails/"
								+ barcode);
						console.log(result);
						if (result != '' || result != 'undefined') {
							$("#DeepfreezerIDId").html(result);
							deepfreezerBarcode = barcode;
						}
					}
				}
			});

	function smapleStorageConform() {
		var no = $("#aloquotNo").val()
		var flag = true;
		$.each(submitedAloquotNo, function(k, v) {
			if (no == v) {
				alert("Data Stored Before For Aloquot : "
						+ $("#aloquotNo").val()
						+ "<br/> Please, Select another Aloquot");
				flag = false;
			}
		});
		
		if (flag) {
			if($("#aloquotNo").val() != -1){
				var product = {
						centrifugeDataMasterId : centrifugeDataMasterId,
						runningTimeWithSeconds : runningTimeWithSeconds,
						missingSampleStorage : $(
								'input[name="missingSampleStorage"]:checked').val(),
						missingSubjectsStorage : $('#missingSubjectsStorage').val(),
						commentsStorage : $('input[name="commentsStorage"]:checked')
								.val(),
						commentsSubjectsSeparation : $("#commentsSubjectsStorage")
								.val(),
						commentStorage : $("#commentStorage").val(),
						shelfbarcode : shelfbarcode,
						deepfreezerBarcode : deepfreezerBarcode,
						aloquotNo : $("#aloquotNo").val()
					};
					var conroller = mainUrl
							+ "/study/clinical/smapleStorageConformSave";
					// 		var conroller = mainUrl + "/sampleProcess/centrifugationStartSave";
					console.log(JSON.stringify(product));
					$.ajax({
						async : false,
						url : conroller,
						data : product,
						success : function(data) {
							console.log(data);
							submitedAloquotNo.push($("#aloquotNo").val());
							$("storagebarcode").attr('disabled', 'disabled');

						}
					});	
			}else{
				/* $("#aloquotNoMsg").html("Required Field"); */
				$('#aloquotNoMsg').html('${validationText}');
			}
			
		}

	}
</script>
</html>