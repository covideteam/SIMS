<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href='<c:url value="/static/hyperlinkCSS/hyperlink.css"/>' />
<link rel="stylesheet"
	href='<c:url value="/static/headingScroolCss/headScroll.css" />' />
<title>Insert title here</title>
</head>
<body oncontextmenu="return false;">
	<!-- 		<div class="sitemessage"> -->
	<!-- 		<h1  style="width:930px; color: firebrick;" align="center"><b>Welcome to Administration</b></h1> -->
	<!-- 	</div> -->
	<!-- Link one	 -->
	<div class="content" style="display:none;">
		<div class="container">
			<div class="row">
				<div class="col-lg-6">
					<div class="card card-primary card-outline">
						<div class="card-cusColor">
							<h5 class="card-title m-0">User Configuration</h5>
						</div>
						<div class="card-body">
							<div class="section">
								<div class="section__item">
									<%-- <a href='<c:url value="/user/createUser"/>' --%>
									 <a href='#'
										class="sm-link sm-link_bg sm-link11"> <span
										class="sm-link__label">Create User</span>
									</a>
								</div>
							</div>
						</div>
					</div>

				</div>
				<div class="col-lg-6">
					<div class="card card-primary card-outline">
						<div class="card-cusColor">
							<h5 class="card-title m-0">Configuration</h5>
						</div>
						<div class="card-body">
							<div class="section">
								<div class="section__item">
									<%-- <a href='<c:url value="/department/createDept"/>' --%>
									<a href='#'
										class="sm-link sm-link_bg sm-link11"> <span
										class="sm-link__label">Department Master</span>
									</a> <%-- <a href='<c:url value="/dosageCon/dosageNew"/>' --%>
									<a href='#'
										class="sm-link sm-link_bg sm-link11"> <span
										class="sm-link__label">Dosage Master</span>
									</a>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<table>
				<thead>
					<tr>
						<th>Select Study</th>
						<th><select onchange="userStuyds(this.value)" name="studyid"
							id="studyid">
								<option value="-1">--Select--</option>
								<c:forEach items="${studys}" var="us">
									<option value="${us.id}">${us.projectNo}</option>
								</c:forEach>
						</select></th>
					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>

			<select id="testNo">
				<option value="1">1</option>
				<option value="2">2</option>
				<option vtalue="3">3</option>
				<option value="4">4</option>
			</select> <input type="button" class="btn btn-primary" value="Test Study"
				onclick="testStudy()"> <input type="text" name="filedName"
				id="filedName" /> <input type="button" class="btn btn-primary"
				value="Field Values" onclick="fieldValues()">

<input type="button" class="btn btn-primary" value="Test Dose"
				onclick="testDose()">
			<div id="json"></div>
		</div>
		<c:url value="/testController/generateCrf" var="saveactivity"></c:url>
<form:form action="${saveactivity}" method="POST" id="saveForm" modelAttribute="at">
	Project NO<input type="text" name="projectNo" id="projectNo"/>
				<input type="SUBMIT" class="btn btn-primary" value="Genereate CRF" >
	
</form:form>
	</div>




</body>
<script type="text/javascript">
	function userStuyds(studyId) {
		if (studyId != -1) {
			var url = mainUrl + '/administration/changeUserStudy/' + studyId;
			var result = synchronousAjaxCall(url).trim();
			var c = result.split('--');
			if (c[0] != 0) {
				$("#projectNo").val(c[0]);
				$("#userStudysAssing").html(result);
			} else {
				$("#projectNo").val("");
				$("#userStudysAssing").html("");
			}
		}

	}
	// 	function userperiod(periodId){
	// 		var url = mainUrl+'/administration/changeUserPeriod/'+periodId+'/'+$("#testNo").val();
	// 			var result = synchronousAjaxCall(url).trim();
	// 			if(result != 0){
	// 				$("#periodNo").val(result);
	// 			}else{
	// 				$("#periodNo").val("");
	// 			}

	// 	}
	function testStudy() {
		// // 		alert(mainUrl+'/testController/saveStudyInfo/'+$("#testNo").val());
		var url = mainUrl + '/testController/saveStudyInfo/'
				+ $("#testNo").val();
		var result = synchronousAjaxCall(url).trim();
	}
	function fieldValues() {
		// 		alert(mainUrl+'/testController/saveStudyInfo/'+$("#testNo").val());
		var url = mainUrl + '/studydesignStaticData/fieldValues/'
				+ $("#filedName").val();
		var result = synchronousAjaxCall(url);
		alert(result);
	}
	function testDose() {
		// // 		alert(mainUrl+'/testController/saveStudyInfo/'+$("#testNo").val());
		var url = mainUrl + '/testController/testDose/'
				+ $("#testNo").val();
		var result = synchronousAjaxCall(url).trim();
	}

	// 	function testStudy(){
	// 		var url = mainUrl+'/studydesignStaticData/studyStaticFieldValues';
	// // 		var result = synchronousAjaxCall(url);
	// // 		$("#json").html(result);

	// // 		var obj = 'name='+name+'&email'+email;
	// 		  $.ajax({
	// 		   url:url,
	// 		   type:"GET",
	// 		   contentType:"application/json",
	// 		   success:function(response){
	// // 		  alert(response);
	// 		  $("#json").html(response);
	// 		  const res = JSON.parse(response);
	// 		  console.log(res);
	// 		  },
	// 		  error:function(error){
	// // 		  alert(error);
	// 		  }
	// 		});
	// // // 		alert(mainUrl+'/testController/saveStudyInfo/'+$("#testNo").val());
	// // 		var url = mainUrl+'/testController/saveStudyInfo/'+$("#testNo").val();
	// // 		var result = synchronousAjaxCall(url).trim();
	// 	}
</script>

</html>