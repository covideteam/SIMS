<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
<script type="text/javascript">
var tfIds = [];
</script>
<script src='/SIMS/static/js/cpu/devidation.js'></script>
<script src='/SIMS/static/js/cpu/validation.js'></script>
<script src='/SIMS/static/js/cpu/mealCollection.js'></script>


<c:forEach items="${tinfList}" var="tinf">
	<script type="text/javascript">
		tfIds.push('${tinf.id}');
	</script>
</c:forEach>
<script type="text/javascript">
$(function(){
	$("#mealDetailsTab").css("display","none");
	$("#example_start").css("display","none");
	$("#example_end").css("display","none");
	$("#subStart").css("display","none");
	$("#subEnd").css("display","none");
	$('.startBtnTR').css("display","none");
})
</script>
<style type="text/css">
nav > .nav.nav-tabs{

  border: none;
    color:#fff;
    background:#272e38; 
/*     background:#17a2b8; */
    border-radius:0;

}
nav > div a.nav-item.nav-link,
nav > div a.nav-item.nav-link.active
{
  border: none;
/*     padding: 18px 25px; */
    color:#fff;
  background:#272e38; 
/*     background:#17a2b8; */
    border-radius:0;
}

nav > div a.nav-item.nav-link.active:after
 {
  content: "";
  position: relative;
  bottom: -45px;
  left: -20%;
  border: 15px solid transparent;
 /*  border-top-color: #e74c3c ; */
 border-top-color : #fa8e02;
}
.tab-content{
  background: #fdfdfd;
    line-height: 25px;
    border: 1px solid #ddd;
    border-top:5px solid #e74c3c;
    border-bottom:5px solid #e74c3c;
    padding:30px 25px;
}

nav > div a.nav-item.nav-link:hover,
nav > div a.nav-item.nav-link:focus
{
  border: none;
/*     background: #e74c3c; */
     background: #fa8e02;
    color:#fff;
    border-radius:0;
    transition:background 0.20s linear;
}
</style>
</head>
<body>
	<input type="hidden" id="Type" value="" />
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
					<br>
			<table id="example0" style="width: 100%; font-size: 15px;">
				<tr>
					 <td><label for="barcode"class=" col-form-label" style="color: #212529;"><spring:message code="label.vialRackScanBarcode"></spring:message></label></td>
					 <td>
					 	 <input type="text" name="barcode" id="barcode" class="form-control"  style="width:40%;display:inline;"/>
					 	 <a class="fa fa-camera neo-qrcodescanner" style="margin-left:5px;font-size: 20px;"></a>
					 	 <div style="color: red;" id="barcodeMsg"></div> 
					 	 <div style="color: red;" id="subjectMsg"></div>
					</td>
				</tr>
			</table>
			<br/>
			<table id="example0" style="width: 100%; font-size: 15px; margin-left: 10%;">
				<tr>
					<td><div id="mealTimePointsDiv" style="width: 100%;"></div></td>
				</tr>
			</table>
			<br>
			 <div class="row">
                <div class="col-sm-12 ">
                	<div class="container" id="mealDetailsTab">
		              <div class="row">
		                <div class="col-sm-12 ">
		                  <nav>
		                    <div class="nav nav-tabs nav-fill" id="nav-tab" role="tablist">
		                      <a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true">Start Meals</a>
		                      <a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">End Meals</a>
		                    </div>
		                  </nav>
		                  <div class="tab-content py-3 px-3 px-sm-0" id="nav-tabContent">
		                    <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
		                    	<table id="example_start" class="table table-bordered" style="width:100%; font-size: small;">
									<tr align="center" style="background-color:DodgerBlue; color:white;">
										<th><spring:message code="label.vitalColSubject"></spring:message></th>
										<th><spring:message code="label.vitalColMealType"></spring:message></th>
										<th><spring:message code="label.vitalColTimepoint"></spring:message></th>
										<th><spring:message code="label.action"></spring:message></th>
									</tr>
									<tbody id="example_start_body">
									</tbody>
									<tfoot>
										<tr class="startBtnTR" align="center">
											<td colspan="4">
												<div style="text-align: center; width: 10%;">
														<input type="button" value="Start" id="subStart_btn" class="btn btn-warning btn-sm" style="font-size:small;" onclick="submitForm('start', '${tinf.id}'), '0'">
												</div>
											</td>
										</tr>
									</tfoot>
							  	</table>
		                    </div>
		                    <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
		                    	<table id="example_end" class="table table-bordered" style="width:100%; font-size: small;">
									<thead>
										<tr align="center" style="background-color:DodgerBlue; color:white;">
											<td><spring:message code="label.vitalColSubject"></spring:message></td>
										<td><spring:message code="label.vitalColMealType"></spring:message></td>
										<td><spring:message code="label.vitalColTimepoint"></spring:message></td>
										<td><spring:message code="label.vitalColSartTime"></spring:message></td>
										<td><spring:message code="label.vitalColConsuption"></spring:message></td>
										<td><spring:message code="label.vitalColComment"></spring:message></td>
										<td><spring:message code="label.action"></spring:message></td>
										</tr>
									</thead>
									<tbody id="example_end_body">
										
									</tbody>
								</table>
		                   	 </div>
		                   </div>
		                </div>
		              </div>
		          </div>
              	</div>
             </div>
	</div>
</div>
	<c:url value="/study/clinical/measlCollection" var="measlCollectionurl2" />
	<sf:form action="${measlCollectionurl2}" method="GET" id="formsumit2">
	</sf:form>
</body>
<script type="text/javascript">

var table = $('#example1').DataTable({
	
    searchBuilder: true,
    "searching": false,
    paging: false,
    ordering: false,
    info: false,
   });
   
var table = $('#example3').DataTable({
	
    searchBuilder: true,
    "searching": false,
    paging: false,
    ordering: false,
    info: false,
   });
   
   $('#nav-home-tab').click(function() {         
	   $("#nav-home-tab").css("background","#fa8e02");
		$("#nav-profile-tab").css("background","#272e38");
	});
   
   $('#nav-profile-tab').click(function() {         
	   $("#nav-profile-tab").css("background","#fa8e02");
		$("#nav-home-tab").css("background","#272e38");
	});

</script>
</html>

