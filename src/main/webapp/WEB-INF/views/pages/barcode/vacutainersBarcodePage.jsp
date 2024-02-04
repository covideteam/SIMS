<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<c:url value="/barcodelabels/stdVacutainerBarcodePrint" var="stdVacutainerBarcodePrint" />
<sf:form action="${stdVacutainerBarcodePrint}" method="POST"  id="formsumit" >
<input type="hidden" name="type" id="type"/>
<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2>Vacutainer Bar-codes</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-wrench"></i></a>
						</li>
						<!-- <li><a class="close-link"><i class="fa fa-close"></i></a>
						</li> -->
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
		<table id="example1" class="table table-bordered table-striped" style="width: 100%;">
			<tr>
				<td style="width: 20%;">Select Period :</td>
				<td style="width: 50%;">
					<select name="period" class="form-control" id="period1">
						<c:forEach items="${spmList}" var="p">
							<option value="${p.id}">Period${p.periodNo}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			  <tr align="center">
				<td ><input type="button" value="Print Bar-Code" class="btn btn-primary" onclick="periodVacutainers()"/></td>
 				<td><input type="button" value="Print all Vacutainers" class="btn btn-primary" onclick="periodVacutainersForAll()"/></td> 
			  </tr>
			
		    </table>
		  </div>
	    </div>
	 </div>
   </div>
</sf:form>
  <c:url value="/barcodelabels/stdVacutainerBarcodePrintTimePointWise" var="stdVacutainerBarcodePrintTimePointWise" />
<sf:form action="${stdVacutainerBarcodePrintTimePointWise}" method="POST"  id="formsumitTimePointWise" >
			<div class="row">
					<div class="col-md-12 col-sm-12 ">
						<div class="x_panel">
							<div class="x_title">
								<h2>TimePoint Wise Vacutainer Bar-codes</h2>
								<ul class="nav navbar-right panel_toolbox">
									<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
									</li>
									<li class="dropdown">
										<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-wrench"></i></a>
										<!-- <ul class="dropdown-menu" role="menu">
											<li><a class="dropdown-item" href="#">Settings 1</a>
											</li>
											<li><a class="dropdown-item" href="#">Settings 2</a>
											</li>
										</ul> -->
									</li>
									<!-- <li><a class="close-link"><i class="fa fa-close"></i></a>
									</li> -->
								</ul>
								<div class="clearfix"></div>
							</div>
							<div class="x_content">
								<br>
						<table id="example1" class="table table-bordered table-striped" style="width: 100%;">
							<tr>
								<td style="width: 20%;">Select Period :</td>
								<td style="width: 50%;">
									<select name="period" class="form-control" id="period2">
										<c:forEach items="${spmList}" var="p">
											<option value="${p.id}">Period${p.periodNo}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width: 20%;">Select Time Point :</td>
								<td style="width: 50%;">
									<select name="timePoint" id="timePoint2" class="form-control">
										<c:forEach items="${timePoints}" var="tp">
											<option value="${tp.id}">${tp.timePoint}(${tp.vacutainerNo}) (${tp.treatmentInfo.treatmentName})</option>
										</c:forEach>	
									</select>
								</td>
							</tr>
							
							<tr  align="center">
								<td colspan="2"><input type="button" value="Print Bar-Code" class="btn btn-primary" onclick="submitFormTimePointWise()"/></td>
							</tr>
							
						</table>
			         </div>
			      </div>
			  </div>
		</div>
</sf:form> 
<c:url value="/barcodelabels/stdVacutainerBarcodePrintSubjectWise" var="stdVacutainerBarcodePrintSubjectWise" />
<sf:form action="${stdVacutainerBarcodePrintSubjectWise}" method="POST"  id="formsumitSubjectWise" >
			<div class="row">
					<div class="col-md-12 col-sm-12 ">
						<div class="x_panel">
							<div class="x_title">
								<h2>Subject Wise Vacutainer Bar-codes</h2>
								<ul class="nav navbar-right panel_toolbox">
									<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
									</li>
									<li class="dropdown">
										<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-wrench"></i></a>
										<ul class="dropdown-menu" role="menu">
											<li><a class="dropdown-item" href="#">Settings 1</a>
											</li>
											<li><a class="dropdown-item" href="#">Settings 2</a>
											</li>
										</ul>
									</li>
									<li><a class="close-link"><i class="fa fa-close"></i></a>
									</li>
								</ul>
								<div class="clearfix"></div>
							</div>
							<div class="x_content">
								<br>
						<table id="example1" class="table table-bordered table-striped" style="width: 100%;">
							<tr>
								<td style="width: 20%;">Select Period :</td>
								<td style="width: 50%;">
									<select name="period" class="form-control" id="period3" onchange="requiredValidation('period3', 'period3Msg', -1)">
										<option value="-1">--Select--</option>
										<c:forEach items="${spmList}" var="p">
											<option value="${p.id}">Period${p.periodNo}</option>	
										</c:forEach>
										<option value="0">All Periods</option>
									</select>
									<font color='red' id="period3Msg"></font>
								</td>
							</tr>
							<tr>
							<td style="width: 20%;">Subject No :</td>
							<td style="width: 50%;">
									<select name="subject" id="subject3" class="form-control" onchange="">
										<option value="-1">--Select--</option>
										<c:forEach items="${subjectNosMap}" var="vol">
											<option value="${vol.value}">${vol.value}</option>
										</c:forEach>	
									</select>
									<font color='red' id="subject3Msg"></font>
							</tr>
							<tr  align="center">
								<td colspan="2"><input type="button" value="Print Bar-Code" class="btn btn-primary" onclick="submitFormSubjectWise()"/></td>
				<!-- 				<td><input type="button" value="Print all Subjects Bar-Codes" class="btn btn-primary" onclick="submitFormAll()"/></td> -->
							</tr>
							
						</table>
			</div>
		</div>						
	</div>
</div>
</sf:form>
<c:url value="/barcodelabels/stdVacutainerBarcodePrintSubjectWiseForTimePoint" var="stdVacutainerBarcodePrintSubjectWiseForTimePoint" />
<sf:form action="${stdVacutainerBarcodePrintSubjectWiseForTimePoint}" method="POST"  id="formsumitSubjectWiseForTimePoint" >
<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2>Subject Wise Vacutainer Bar-codes For Time Point</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-wrench"></i></a>
							<!-- <ul class="dropdown-menu" role="menu">
								<li><a class="dropdown-item" href="#">Settings 1</a>
								</li>
								<li><a class="dropdown-item" href="#">Settings 2</a>
								</li>
							</ul> -->
						</li>
						<!-- <li><a class="close-link"><i class="fa fa-close"></i></a>
						</li> -->
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
					<table id="example1" class="table table-bordered table-striped" style="width: 100%;">
						<tr>
							<td style="width: 20%;">Select Period :</td>
							<td style="width: 50%;">
								<select name="period" class="form-control" id="period4" 
								onchange="requiredValidation('period4', 'period4Msg', -1);resetSubjectAndTimePoitn('subject4', 'timePoint4')">
									<option value="-1">--Select--</option>
									<c:forEach items="${spmList}" var="p">
										<option value="${p.id}">Period${p.periodNo}</option>	
									</c:forEach>
								</select>
								<font color='red' id="period4Msg"></font></td>
							</td>
						</tr>
						<tr>
						<td style="width: 20%;">Subject No :</td>
						<td style="width: 50%;">
								<select name="subject" id="subject4" class="form-control" 
								onchange="subjectTimePoints('subject4', 'period4')">
									<option value="-1">--Select--</option>
									<c:forEach items="${subjectNosMap}" var="vol">
										<option value="${vol.key}">${vol.value}</option>
									</c:forEach>
									<option value="0">All Periods</option>	
								</select>
								<font color='red' id="subject4Msg"></font>
						</tr>
						<tr>
							<td>Time Point :</td>
							<td id="timePointsInfo">
								<select name="timePoint" id="timePoint4" class="form-control">
								<option value="-1">--Select--</option>
<%-- 									<c:forEach items="${timePoints}" var="tp"> --%>
<%-- 										<option value="${tp.id}">${tp.timePoint}(${tp.vacutainerNo}) (${tp.treatmentInfo.treatmentName})</option> --%>
<%-- 									</c:forEach>	 --%>
								</select>
								<font color='red' id="timePoint4Msg"></font>
							</td>
						</tr>
						<tr  align="center">
							<td colspan="2"><input type="button" value="Print Bar-Code" class="btn btn-primary" onclick="submitFormSubjectWiseForTimePoint()"/></td>
						</tr>
						
					</table>
		         </div>
	         </div>
	     </div>
     </div>
</sf:form>

</body>
<script type="text/javascript">


function getVacutainerTimePoints(){
	//$("#timePointsInfo").html("");
	$("#subjectmsg").html("");
	var volId = $("#subject3").val();
	var ordreNo = $("#period3").val();
	if(subject != '-1'){
		var result = synchronousAjaxCall(mainUrl+"/barcodelabels/getSubjectTimePoints/"+ordreNo+"/"+volId);
		alert(result);
		if(result != '' || result != 'undefined'){
			$('#timePointsInfo').html(result);
		}else{
			$('#timePointsInfo').html('');
		}
	}
}
function timePointIdVal(val){
	$("#timePointIdMsg").html("");
	if(val == '-1')
		$("#timePointIdMsg").html("Time Point Required");
}

</script>

</html>