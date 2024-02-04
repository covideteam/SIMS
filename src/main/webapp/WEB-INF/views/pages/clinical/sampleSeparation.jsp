<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<script src='/SIMS/static/js/cpu/validation.js'></script>
<script src='/SIMS/static/js/cpu/sampleSeparation.js'></script>
<style type="text/css">
#sparationDiv{
  display: none;
}
</style>
</head>
<body>
			<table class="table table-bordered" style="border: none;">
				<c:choose>
					<c:when test="${studySelection}">
						<tr>
							<td>Select Centrifuge Data :</td>
							<td id="centifugeData"></td>
						</tr>
						<tr>
							<td>Subjects :</td>
							<td id="centrufugeSubjects"></td>
						</tr>
					</c:when>	
					<c:otherwise>
						<tr>
							<td>Project No : ${centrifugationDataMaster.study.projectNo}<input
								type="hidden" name="centrifugationDataMasterId"
								id="centrifugationDataMasterId"
								value="${centrifugationDataMaster.id}"></td>
						</tr>
						<tr>
							<td>Centrifuge ID
								:${centrifugationDataMaster.instrument.instrumentNo}<font
								color="red" id="centrifugationDataMasterIdMsg"></font>
							</td>
							<td>Time Point : ${centrifugationDataMaster.timePoitns}</td>
						</tr>
						<tr>
							<td>Subjects :</td>
							<td id="centrufugeSubjects">${centrifugationDataMaster.subjects}</td>
						</tr>
					</c:otherwise>
				</c:choose>

			</table>
			<div style="width: 55%; margin-left: 20%;">
				<table id="maintable" class="table table-bordered" style="border: none;">
					<tr>
						<td>Scan Barcode</td>
						<td>
							<input type="text" name="barcode" class="form-control" id="barcode" />
							<input type="hidden" id="timePintDataAndTime" value="" />
							<div style="color: red" id="barcodeMsg"></div> 
						</td> 
	<!-- 					<td><input type="button" id="reset" value="Reset" class="btn btn-primary btn-md" onclick="storage()" /></td> -->
					</tr>
				</table>
			</div>
			<div id="sparationDiv">
			<table class="table table-bordered" style="width: 100%;">
				<thead>
					<tr>
						<th>Project No</th>
						<th>Period</th>
						<th>Subject</th>
						<th>Time Point</th>
						<th>Aliquot volume</th>
						<th>Vials</th>
					</tr>
				</thead>
				<tbody id="vacutinersTable">

				</tbody>
			</table>
			<table class="table table-bordered" style="width: 100%; border: none;">
				<tr>
					<td>Conditions</td>
					<td>
						Buffer/Ice water bath storage 
						<input type="radio" name="bufferStorage" value="Yes">Yes
						<input type="radio" name="bufferStorage" value="No">No
						<div style="color: red;" id="bufferStorageMsg"></div>
					</td>
					<td>Missed Subjects</td>
					<td colspan="3"><input type="text" name="separationMissedSubjects" class="form-control" id="separationMissedSubjects" /></td>
				</tr>
			</table>
			
			<table class="table table-bordered table-striped" style="width: 100%;">
				<tr>
					<td>Start Time :</td>
					<td id="startTimeTd">
						<input type="button" id="startButton" value="Start" class="btn btn-warning btn-sm" onclick="separationStartTimeAdd()" />
						<div style="color: red;" id="startMsg"></div>
					</td>
					<td>End Time :</td>
					<td id="endTimeTd">
						<input type="button" id="endButton" disabled="disabled" value="End" class="btn btn-warning btn-sm" onclick="separationEndTimeAdd()" />
						<div style="color: red;" id="endMsg"></div>
					</td>
				</tr>
			</table>
		</div>
</body>


</html>