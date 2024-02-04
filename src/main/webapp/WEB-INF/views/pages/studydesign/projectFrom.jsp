<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 
<script src='/SIMS/static/js/studydesign.js'></script>     

<input type="hidden" class="form-control autosave" name="projectId" value="${psid}"  />
<input type="hidden" class="form-control autosave" name="projectReview" value="${review}"  />

<c:forEach items="${globalActivities}" var="globalActivity">
	<input type="hidden" class="form-control autosave" name="${globalActivity.getActivityCode()}" value="${globalActivity.getId()}"  />	
</c:forEach>

<c:if test="${review == 'review'}"></c:if>

<form:form> 
	<input type="hidden" name="projectId" id="desProjectId"/>
	<input type="hidden" name="rowNumber" id="desRowNumber"/>
	<input type="hidden" name="subRowNumber" id="desSubRowNumber"/>
	<input type="hidden" name="fieldName" id="desFieldName"/>
	<input type="hidden" name="type" id="desType"/>
	<div id="discripencyModal" class="modal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document" style="width:80%;max-width:80%;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">Discrepancies</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      	<c:if test="${review == 'review'}">
		      	<table style="width:100%;">
		      		<tr><td style="width:80px;">Comment</td><td><input type="text" class="form-control" id="txtComments" name="comment"/></td></tr>
		      		<tr>
		      			<td colspan="2" style='text-align:center;'>
		      				<button type="button" class="btn btn-primary" id="btnSaveDescrepancy">Save</button>
		      			</td>
		      		</tr>
		      	</table>
	      	</c:if>
	      	
	      	<c:if test="${review != 'review'}">
		      	<table id="formResponse" style="width:100%;display:none;">
		      		<tr><td style="width:80px;">Comment</td><td><label id="lblComment"></label><input type='hidden' name='studyDiscrepancyId' id="hidDiscrepancyId"/></td></tr>
		      		<tr><td style="width:80px;">Response</td><td><input type="text" class="form-control" id="txtResponse" name="comments"/></td></tr>
		      	</table>
	      	</c:if>
	       	
	       	<table id="tblComments" class="ds-table ds-table-align-header-center ds-table-border mt-5">
	       		<thead><tr><td>Comment</td><td>Commented By</td><td>Commented Date</td><td>Response</td><td>Response Given By</td><td>Response Given Date</td><td></td></tr></thead>
	       		<tbody>
	       				
	       		</tbody>
	       	</table>
	      </div>
	      <div class="modal-footer">
	      	<button type="button" id="btnBack" class="btn btn-secondary" style='display:none;'>Back</button>
	        <button type="button" class="btn btn-primary" id="btnSaveDiscrepancyResponse" style='display:none;'>Save</button>
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
</form:form>




<form id="frmDesign">        
	<div class='main'>
        <div id="studyInformation" class='parent' data-name="StudyInformation">
            <h1 class='page-subheading'>Study Information</h1>
            <table style="width:100%" class="ds-form-table reviewFormTable">
                <tr>
                    <td>Study Number</td>
                    <td><input type="text" class="form-control autosave reviewElement" required name="projectNumber" /></td>
                    <td>Sponsor Project Code</td>
                    <td><input type="text" class="form-control autosave reviewElement" required name="sponsorCode" id="projNo" /></td>
                    <td>Project Design</td>
                    <td><select id="studyDesign" name="studyDesign" class='form-control reviewElement validate' data-validate="required"><option value=''>Select</option><option value='Fast'>Fast</option><option value='FED'>Fed</option><option value='FF'>Fast&Fed</option></select></td>
                </tr>
                <tr>
                    <td>Masking</td>
                    <td><select id="masking" name="Masking" class="form-control autosave  reviewElement validate" data-validate="required"><option value=''>Select</option><option value='Open Label'>Open Label</option></select></td>
                    <td>No. of Periods</td>
                    <td><input type="text" name="noOfPeriods" data-min="1" class="form-control autosave reviewElement validate" data-validate="required" id="noOfPeriods" data-texttype="NUMERIC" maxlength="1"/></td>
                    <td>Washout Period(Days)</td>
                    <td><input type="text" name="washoutPeriod" data-min="0" class="form-control autosave reviewElement validate" data-validate="required" id="washoutPer" data-texttype="NUMERIC" maxlength="2" /></td>
                </tr>
                <tr>
                    <td>Dosage Form</td>
                    <td><select id="dosageForm" name="dosageForm" class="form-control autosave reviewElement validate" data-validate="required"><option value=''>Select</option><option value='Injection'>Injection</option><option value='Tablet'>Tablet</option></select></td>
                    <td>Treatment Code on Sachet</td>
                    <td><select id="trtmntcdonscht" name="treatmentCode" class="form-control autosave reviewElement validate" data-validate="required"><option value=''>Select</option><option value='Show'>Show</option><option value='Hide'>Hide</option></select></td>

                    <td>Pre Dose Housing(Hr)</td>
                    <td><input type="text" name="preDoseHousing" class="form-control autosave reviewElement validate" data-validate="required" id="preDoseHousing" data-texttype="NUMERIC" maxlength="3"/></td>
                </tr>
                <tr>
                    <td>Post Dose Housing(Hr)</td>
                    <td><input type="text"  data-min="1" name="postDoseHousing" class="form-control autosave reviewElement validate" data-validate="required" id="postDoseHousing" data-texttype="NUMERIC" maxlength="3"/></td>
                    <td>No. of Subjects</td>
                    <td><input type="text" data-min="1" name="noOfSubjects" class="form-control autosave reviewElement validate" data-validate="required" id="noOfSubjects" data-texttype="NUMERIC" maxlength="3"/></td>
                    <td>No. of Stand By's</td>
                    <td><input type="text" data-min="0" name="noOfStandBys" class="form-control autosave reviewElement validate" data-validate="required" id="noofStandBy_s" data-texttype="NUMERIC" maxlength="2"/></td>
                </tr>
                <tr>
                    <td>Dose Type</td>
                    <td><select id="doseType" name="doseType" class="form-control reviewElement validate" data-validate="required"><option value=''>Select</option><option value='Single'>Single</option><option value='Multiple'>Multiple</option></select></td>
                    <td>No. of Treatments</td>
                    <td><input type="text" name="noOfTreatments" class="form-control reviewElement validate" data-validate="required" id="nooftreatments" data-old='0' data-min="1" data-texttype="NUMERIC" maxlength="1"/></td>
                </tr>
                <tr>
                    <td>Project Title</td>
                    <td colspan="5"><textarea id=studyTitle class="form-control autosave reviewElement validate" data-validate="required" name="studyTitle" rows="6"></textarea></td>
                </tr>
            </table>

            <div style="display:none;" class="parent" data-name="TreatmentWiseInformation" id="treatmentWiseInformationMain">
                <h1 class='page-subheading'>Treatment Wise Information</h1>
                <table id="treatmentsinfo" class="ds-table ds-table-align-header-center reviewFormTable">
                	<thead></thead>
                	<tbody></tbody>
                </table>
            </div>

            <div id="sachetLabelCode">

            </div>
        </div>
        
        <div id="dosingTimepoints" style="display:none;" class='pad-t-60 parent' data-name="DosingTimepoints">
            <h1 class='page-subheading'>Dose Timepoints</h1>
            <table class="ds-form-table reviewFormTable" id="isThereanyDifferenceInTheNoOfDosings"><tr><td>Is there any difference in the no. of dosings</td><td style='width:150px'><select id='differenceinDosings' name='IsthereAnyDifferenceinDosings' class='form-control autosave reviewElement'></select></td></tr></table>
            <div id="doseTreatmentTimepoints">

            </div>
        </div>

        <div class='pad-t-60 parent' id="dosingParameters" data-name="DosingParameters">
            <h1 class='page-subheading'>Dosing Parameters</h1>
            <div>
                <table class="ds-form-table reviewFormTable">
                    <tr>
                        <td style="width:250px">Treatment specific Parameters</td>
                        <td style="width:150px"><select id="treatmentwisedose" name="treatmentWiseDoseParameters" class='form-control reviewElement validate' data-validate='required'><option value=''>Select</option></select></td>
                        <td style="width:150px;display:none;">Timepoint specific Parameters</td>
                        <td style="width:120px;display:none;"><select id="timepointSpecificParameterDose" name="TimepointSpecificParameterDose" class='form-control autosave reviewElement'><option value=''>Select</option></select></td>
                        <td></td>
                    </tr>
                </table>
                <table class="mgn-t10 ds-form-table col-lg-12 removeReviewElement">
                    <tr>
                        <td style="width:250px">Treatment</td>
                        <td style="width:150px"><select id="dosingTreatmentvalue" name="DosingTreatment" class='form-control'><option value='Select'>Select</option></select></td>
                        <td style="width:150px;display:none;">Timepoint</td>
                        <td style="width:120px;display:none;"><select id="dosingTimepointParameters" name="DosingTimepointParameters" class='form-control'><option value='Select'>Select</option></select></td>
                        <td style="width:170px">Parameter</td>
                        <td><select id="parameterdesc" class="form-control" name="ParameterDesc"></select></td>
                        <td style="width:80px"><button class='fa fa-plus btn btn-info autosaveclick' style='align-content:center;width:100px;' id="btnDosingParametersSubmit" title='Add'></button></td>

                    </tr>
                </table>

                <table id="dosingParameterList" data-name="DosingParameters" class="ds-table ds-table-align-header-center ds-table-border" style="margin-top:10px" data-defaultrow="1">
                    <thead style="text-align:center">
                        <tr><td style='width:12%;'><label>Treatment</label></td><td style='width:70%;'><label>Parameter Description</label></td><td style='width:5%;'><label>Action</label></td></tr>
                    </thead>
                    <tbody>
                        <tr style="text-align:center"><td colspan="4">No Data Found</td></tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div id="sampleTimePoints" data-name="sampleTimePoins" class='pad-t-60 parent'>
            <h1 class='page-subheading'>Sample Timepoints</h1>
            <table class="ds-form-table reviewFormTable">
                <tr>
                    <td style="width:230px">Treatment specific Timepoints</td>
                    <td style="width:170px"><select id="samptmpdp" name="treatmentSpecificTimepoint" class='form-control autosave reviewElement validate' data-validate='required'><option value=''>Select</option></select></td>
                </tr>
            </table>

            <table class="ds-form-table removeReviewElement">
                <tr>
                    <td style="width:230px">Treatment</td>
                    <td style="width:170px"><select id="sampleTreatmentValue_1" name="treatmentSpecificSampleTimepoint" class='form-control autosave reviewElement'><option value=''>Select</option></select></td>
                </tr>
            </table>

            <div id="sampleTimePoints">
                <table class="ds-form-table multi-entry-table removeReviewElement" style="margin-top:20px;">
                    <tr>
                        <td style='width:150px;'>Special Condition</td>
                        <td style='width:190px;'>Blood Volume(mL)</td>
                        <td style='width:170px;'>Type of Vacutainer Used</td>
                        <td style='width:100px;'>Buffer</td>
                        <td style='width:110px;'></td>
                    </tr>
                </table>
                <table class="ds-form-table removeReviewElement" style="margin-bottom:20px;">
                    <tr>
                        <td style='width:150px;'>
                            <select id='splcondition' name="splCondition" class='form-control'>
                                <option value='Select'>Select</option>
                                <option value='Normal'>Normal</option>
                                <option value='Monochromatic'>Monochromatic</option>
                            </select>
                        </td>
                        <td style='width:190px;'><input type='text' data-texttype='NUMERIC' data-min='1' maxlength='2' name="bloodVolume" class='form-control' id='bloodvolume' /></td>
                        <td style='width:170px;'><input type='text' name="typeofvacutainerused" class='form-control' id='typeofvacutainerused' /></td>
                        <td style='width:100px;'><select id='buffer' name="buffer" class='form-control'><option value='Select'>Select</option><option value='Yes'>Yes</option><option value='No'>No</option></select></td>
                        <td style='width:110px;'><button class='fa fa-plus btn btn-info' style='align-content:center;width:100px;line-height:1;' id="btnSampleInformation" title='Add'></button></td>
                    </tr>
                </table>

                <table data-srnumber="0" id='tsplconditionwisedata' data-name="sampleInformation" class='ds-table ds-table-align-header-center ds-table-border' style='margin-top:10px' data-defaultrow="1">
                    <thead style='text-align:center'>
                        <tr><td style='width: 12.4%;'><label>Treatment</label></td><td style='width:12.4%'><label>Special Condition</label></td><td style='width:12.4%;'><label>Blood Volume(mL)</label></td><td style='width:12.4%;'><label>Type of Vacutainer Used</label></td><td style='width:12.4%;'><label>Buffer</label></td><td class="deleteColumn" style='width:12.4%;'><label>Action</label></td></tr>
                    </thead>
                    <tbody style='text-align:center'>
                        <tr id="tsplConditionWisedataTabletr"><td colspan="7">No Data Found</td></tr>
                    </tbody>
                </table>


                <table class="ds-form-table removeReviewElement" style='margin-top:10px'>
                    <tr>
                        <td style="width:200px">Treatment</td>
                        <td style="width:170px"><select id="sampleTreatmentValue_2" name="sampleTreatmentValue_2" class='form-control autosave'><option value=''>Select</option></select></td>
                    </tr>
                </table>


                <table class="ds-form-table multi-entry-table removeReviewElement" style="margin-top:20px;">
                    <tr>
                        <td style='width:500px;'>Timepoints</td>
                        <td style='width:145px;'>No. of Vacutainers</td>
                        <td style="width:240px;">Window Period</td>
                        <td style="width:240px;">No. of Vials</td>
                        <td style='width:105px;'></td>
                    </tr>
                </table>
                <table class="ds-form-table removeReviewElement" style="margin-bottom:20px;">
                    <tr>
                        <td style='width:500px;padding-left:5px;'><input type=text id='timepoints' name="timepoints_STP" class='form-control timepoint-format' /></td>
                        <td style='width:145px;padding-left:5px;'><input data-texttype='NUMERIC' data-min='1' maxlength='1' id='noofvacutainers' name="noofvacutainers_STP" class='form-control' min='0' /></td>
                        <td style='width:80px;padding-left:5px;'><select class='form-control' name="windowperiod_STP" id='windowperiod'><option value='Select'>Select</option><option value='+'>+</option><option value='-'>-</option><option value='+/-'>+/-</option></select></td>
                        <td style='width:80px;padding-left:5px;'><input data-texttype='NUMERIC' data-min='1' maxlength='4' name="windowperiodValue_STP" id='windowperiod_value' class='form-control' min='0' /></td>
                        <td style='width:80px;padding-left:5px;'><select class='form-control' id="windowPeriodTime" name="period_STP"><option value='Select'>Select</option><option value='Months'>Minutes</option><option value='Hours'>Hours</option><option value='Days'>Days</option></select></td>
                        <td style='width:250px;'><input type="text" data-min='0' data-texttype="NUMERIC" maxlength="1" id='noofvialsss' name="noofvialsss" class='form-control'/></td>
                        <td style='width:100px;padding-left:5px;'><button class='fa fa-plus btn btn-info' id='sampletimepointsbtn' style='align-content:center;width:100px;line-height:1;' title='Add'></button></td>
                    </tr>
                </table>

                <table data-srnumber="0" data-name="sampleTimePoins" id='sampletimepointTreatmentslisttable' class='ds-table ds-table-align-header-center ds-table-border ' style='margin-top:10px' data-defaultrow="1">
                    <thead style='text-align:center'>
                        <tr><td style='width:12.4%;'><label>Treatment</label></td><td style='width:12.4%;'><label>Timepoints</label></td><td style='width:12.4%;'><label>No. of Vacutainers</label></td><td style='width:12.4%;'><label>Window Period</label></td><td style='width:12.4%;'><label>No. of Vials</label></td><td class="deleteColumn" style='width:12.4%;'><label>Action</label></td></tr>
                    </thead>
                    <tbody style='text-align:center'>
                        <tr id="sampletimepointlistTabletr"><td colspan="6">No Data Found</td></tr>
                    </tbody>
                </table>
            </div>

        </div>

        <div id="vitalTimePoint" data-name="vitalTimePoins" class='pad-t-60 parent'>
            <h1 class='page-subheading'>Vital Timepoints Information</h1>
            
            <table class="ds-form-table reviewFormTable">
                <tr>
                	<td style='width:250px'>Treatment Specific Timepoints</td>
                    <td style='width:150px;padding-left:5px'><select id="tmntspctmpt" name="teatmentSpecificVitalTimepoints" class="form-control autosave validate reviewElement" data-validate='required'><option value=''>Select</option><option value='Yes'>Yes</option><option value='No'>No</option></select></td>
                </tr>
            </table>
            
             <table class="ds-form-table removeReviewElement">
                <tr>
                	<td style='width:250px'>Treatments</td>
                    <td style='width:150px;padding-left:5px'><select id="vitalTreatment" class='form-control autosave'><option value='Select'>Select</option></select></td>
                </tr>
            </table>
            
            <table class="ds-form-table removeReviewElement" style="margin-bottom:20px;">
            	<thead class="multi-entry-table">
	            	<tr>
	                    <td style='width:120px;'>Position</td>
	                    <td style='width:160px;'>Timepoints</td>
	                    <td style='width:200px;'>Parameters</td>
	                    <td style='width:190px' colspan="3">Window Period</td>
	                    <td style='width:210px;text-align:center;'>Is Orthostatic</td>
	                    <td style='width:205px;'>Position</td>
	                    <td></td>
					</tr>
				</thead>
				<tbody>
					<tr>
	                    <td style='width:120px;'><select id='vitalsPosition' name="vitalsPosition" class='form-control'><option value='Select'>Select</option><option value='Prone'>Prone</option></select></td>
	                    <td style='width:160px;'><input type='text' name="vitalsTimepoints" class='form-control timepoint-format' id='vitalsTimepoints' /></td>
	                    <td style='width:200px;'><select id='vitalsParameters' name="vitalsParameters" class='form-control' multiple style="height: 80px; overflow: auto;"></select></td>
	                    <td style='width:80px;'><select id="vitalsWindowPeriodSign" name="vitalsWindowPeriodSign" class='form-control'><option value='Select'>Select</option><option value='+'>+</option><option value='-'>-</option><option value='+/-'>+/-</option></select></td>
	                    <td style='width:60px;'><input type='text' data-texttype='NUMERIC' data-min='1' maxlength='4' id="vitalsWindowPeriod" name="vitalsWindowPeriod" class='form-control' /></td>
	                    <td style='width:80px;padding-left:5px;'><select class='form-control' id="vitalsWindowPeriodTime" name="vitalsWindowPeriodTime"><option value='Select'>Select</option><option value='Months'>Minutes</option><option value='Hours'>Hours</option><option value='Days'>Days</option></select></td>
	                    <td style='width: 170px; padding-left: 30px' id='tdOrthostaticPosition'></td>
	                    <td style='width:110px;'><select name="othostaticPosition" id="othostaticPosition" class='form-control' disabled><option value='Select'>Select</option><option value='Prone'>Prone</option></select></td>
	                    <td style='width:170px;'>
	                    	<button class='fa fa-plus btn btn-info' id='vitalsPreAndPostBtn' style='align-content:center;width:100px;line-height:1;' title='Add'></button>
	                    </td>
                	</tr>
				</tbody>
			</table>

            <table data-name="vitalTimepointInformation" data-srnumber="0" data-defaultrow="1" class='ds-table ds-table-align-header-center ds-table-border' id="preAndPostTable" style='margin-top:10px'>
                <thead style='text-align:center'>
                    <tr><td><label>Treatment</label></td>
                    <td><label>Position</label></td>
                    <td><label>Timepoints</label></td>
                    <td><label>Parameters</label></td>
                    <td><label>Window Period(min)</label></td>
                    <td><label>Is Orthostatic</label></td>
                    <td><label>Position</label></td>
                    <td><label>Action</label></td></tr>
                </thead>
                <tbody>
                    <tr><td colspan="8" style='text-align:center'>No Data Found</td></tr>
				</tbody>
            </table>

            <div><h4 class="page-section-subheading" style='margin-top:10px;display:none;'>Test Parameter Ranges</h4></div>
            <table class="ds-form-table removeReviewElement" style='display:none;'>
                <tr>
                    <td>Test Name</td>
                    <td class='text-center' style="padding-left: 10px;"><div style='width:120px;'><select id="testName" name="testName" class='form-control autosave' required><option value='Select'>Select</option><option value='Respiratory Rate'>Respiratory Rate</option></select></div></td>
                    <td class='text-center' style="padding-left: 10px;">Range From</td>
                    <td class='text-center' style="padding-left: 10px;"><div style='width:100px;'><input  data-texttype='NUMERIC' data-min='2' maxlength='4' name="rangeFrom" id="rangeFrom" min='0' class='form-control autosave' required /></div></td>
                    <td class='text-center' style="padding-left: 10px;">To</td>
                    <td class='text-center' style="padding-left: 10px;"><div style='width:100px;'><input  data-texttype='NUMERIC' data-min='2' maxlength='4' name="toRange" id="toRange" min='0' class='form-control autosave' required /></div></td>
                    <td style='width:170px;padding-left:10px;'><button class='glyphicon  glyphicon-plus btn btn-info autosaveclick' style='align-content:center;width:100px;' id="testparBtn" title='Add'></button></td>
                </tr>
            </table>

            <table class='ds-table ds-table-align-header-center ds-table-border' id="testParameterRangesTable" style='margin-top:10px;display:none;'>
                <thead style='text-align:center'>
                    <tr><td><label>Test Name</label></td><td><label>Range</label></td><td class="deleteColumn"><label>Action</label></td></tr>
                </thead>
                <tbody style='text-align:center'>
                    <tr id="testParameterRangesTabletr"><td colspan="3">No Data Found</td></tr>
                </tbody>
            </table>
        </div>

        <div id="mealsTimePoints" data-name="mealsTimePoints" class='pad-t-60 parent'>
            <h1 class='page-subheading'>Meals Timepoints Information</h1>
            <table class="ds-form-table removeReviewElement">
                <tr>
                	<td style='width:250px'>Treatment Specific Timepoints</td>
                    <td style='width:150px;padding-left:5px'><select id="teatmentSpecificMealTimepoints" name="teatmentSpecificMealTimepoints" class="form-control autosave validate" data-validate='required'><option value=''>Select</option><option value='Yes'>Yes</option><option value='No'>No</option></select></td>
                </tr>
            </table>
            
             <table class="ds-form-table removeReviewElement">
                <tr>
                	<td style='width:250px'>Treatments</td>
                    <td style='width:150px;padding-left:5px'><select id="mTPTreatmentvalue" class='form-control autosave'><option value='Select'>Select</option></select></td>
                </tr>
            </table>

            <table class="ds-form-table multi-entry-table removeReviewElement" style="margin-top:20px;">
                <tr>
                    <td style='width:180px;'>Timepoints</td>
                    <td style='width:160px;'>Meals Type</td>
                    <td style="text-align:center; width:170px;">Window Period(min)</td>
                    <td style="text-align:center; width:250px;">Completion Time</td>
                    <td style="width:90px"></td>
                </tr>
            </table>

            <table class="ds-form-table removeReviewElement" style="margin-bottom:20px;">
                <tr>
                    <td><div style='width:170px;'><input type="text" class="form-control timepoint-format" id="mealTimePoint" /></div></td>
                    <td>
                        <div style='width:170px;'>
                            <select id="MealsType" name="mealType" class="form-control">
                                <option value='Select'>Select</option>
                                <option value='NormalBreakfast'>NormalBreakfast</option>
                                <option value='StandardBreakfast'>Standard Breakfast</option>
                                <option value='High Fat Breakfast'>High Fat Breakfast</option>
                                <option value='Lunch'>Lunch</option>
                                <option value='Snacks'>Snacks</option>
                                <option value='Dinner'>Dinner</option>
                            </select>
                        </div>
                    </td>
                    <td><div style='width:80px;'><select class="form-control" id="mealWindowPeriodSign"><option value='+/-'>+/-</option><option value='+'>+</option><option value='-'>-</option></select></div></td>
                    <td><div style='width:80px;'><input type="text" class="form-control" data-texttype="NUMERIC" maxlength="3" name="mealWindowPeriodValue" id="mealWindowPeriodValue" /></div></td>
                    <td><div style='width:100px;'><select id="mealCompletionTimeApplicable" class="form-control"><option value='yes'>Yes</option><option value='No'>No</option></select></div></td>
                    <td><div style='width:100px;'><input type="text" class="form-control" data-texttype="NUMERIC" maxlength="3" id="mealCompletionTimeValue" disabled/></div></td>
                    <td style='width:170px;'>
                    	<button class="fa fa-plus btn btn-info" id="mealsTimepointbtnadd" style="align-content:center;width:100px;line-height:1;" title="Add"></button>
                    </td>
				</tr>
            </table>

            <table data-defaultrow="1" data-srnumber="0" id="Mealsinfo" class="ds-table ds-table-align-header-center ds-table-border" style="margin-top:20px;" data-name="mealsTimePointInformation">
                <thead>
                    <tr>
                        <td style="text-align:center">Treatment Number</td>
                        <td style="text-align:center">Timepoints</td>
                        <td style="text-align:center">Meals Type</td>
                        <td style="text-align:center">Window Period(min)</td>
                        <td style="text-align:center">Completion Applicable</td>
                        <td style="text-align:center">Completion Time</td>
                        <td class="deleteColumn">Action</td>
                    </tr>
                </thead>
                <tbody style="text-align:center">
                    <tr id="mealsTabletr"><td colspan="7">No Data Found</td></tr>
                </tbody>
            </table>
        </div>



        <div id="sampleProcessingAndStorage" data-name="sampleProcessing" class='pad-t-60 parent' >
            <h1 class='page-subheading'>Sample Processing and Storage</h1>
            <div><h4 class="page-section-subheading">Centrifugation</h4></div>
            <table class="ds-form-table reviewFormTable">
				<tr>
					<td style='width:200px;'>Is Centrifugation Applicable</td>
                    <td style='width:150px;padding-left:5px' id="tdCentrifugationApplicable"></td>
                </tr>
            </table>
            <div id="divCentrifugationApplicable" style="display:none;">
	            <div style="margin-top:10px;"><h4 class="page-section-subheading">Parameters</h4></div>
	            <table class="ds-form-table reviewFormTable">
	             	<tr>
	                	<td style='width:130px;'>Applicable To</td>
	                    <td style='width:150px;padding-left:5px'>
	                    	<select id='centrifugationApplicableTo' data-rownumber="0" data-subrownumber="1" name="centrifugationApplicableTo" class='form-control autosave reviewElement'>
	                             <option value=''>Select</option>
	                             <option value='All'>All</option>
	                             <option value='Vacutainer1'>Vacutainer1</option>
	                             <option value='Vacutainer2'>Vacutainer2</option>
	                         </select>
	                    </td>
	                    <td></td>
	                </tr>
					<tr>
						<td style='width:130px;'>Speed(rpm)</td>
	                    <td style='width:150px;padding-left:5px'><input data-rownumber="0" data-subrownumber="1" type="text" class="form-control autosave reviewElement" data-texttype="NUMERIC" maxlength="4" name="centrifugationSpeed" id="speed" /></td>
	                    <td style='width:150px;padding-left:5px'>Process Time(min)</td>
	                    <td style='width:170px;padding-left:5px'><input data-rownumber="0" data-subrownumber="1" type="text" class="form-control autosave reviewElement" data-texttype="NUMERIC" maxlength="3" name="centrifugationProcessTime" id="ProcessTime" /></td>
	                    <td style='width:120px;padding-left:5px'>Temperature(°C)</td>
	                    <td style='width:170px;padding-left:5px'><input data-rownumber="0" data-subrownumber="1" type="text" class="form-control autosave reviewElement" data-texttype="NUMERIC" maxlength="3" name="centrifugationTemparature" id="temparaturealiquote" /></td>
	                    <td style='width:140px;padding-left:5px'>Allowed Time(min)</td>
	                    <td style='width:170px;padding-left:5px'><input data-rownumber="0" data-subrownumber="1" type="text" class="form-control autosave reviewElement" name="centrifugationAllowedTime" id="allowedtime" /></td>
	                </tr>
	            </table>
	            <table class="ds-form-table reviewFormTable">
	                <tr>
	                	<td style='width:125px;'>Condition</td>
	                    <td style='width:430px;' id="divCentrifugationConditions"></td>
	                </tr>
	            </table>
            </div>
            

            <div style="margin-top:10px;"><h4 class="page-subheading">Processing</h4></div>
            <table class="ds-form-table reviewFormTable">
            	<tr>
                	<td style='width:150px;'>Allowed Time(min)</td>
                    <td style='width:150px;'><input type="text" class="form-control autosave reviewElement" name="processingAllowedTime" id="allowedtimealiquote" /></td>
                    <td style='width:150px;'>Allowed Time From</td>
                    <td style='width:150px;'><select name="ProcessingAllowedTimeFrom" id="processingAllowedTimeFrom" class="form-control autosave reviewElement"><option value="">Select</option><option value="STARTTIME">Start Time</option><option value="ENDTIME">EndTime</option></select></td>
                </tr>
                <tr>
                	<td style='width:150px;padding-top:5px;'>Condition</td>
                    <td style='width:150px;padding-top:5px;' id="divProcessingConditions"></td>
                </tr>
            </table>
                       
            <table style="margin-top:5px;" class="ds-form-table reviewFormTable">
            	<tr>
                	<td style='width:150px;'>Is Matrix different</td>
                    <td style='wdith:350px;' id="divIsMatrixDifferent"></td>
                </tr>
            </table>
           
            <div id="divAliquotMatrix" style="display:none;margin-top:10px;">
            	<h4 class="page-section-subheading">Aliquots</h4>
	            <table class="ds-form-table reviewFormTable" id="aliquotes">
	            	<thead class="multi-entry-table">
	            		<tr>
	            			<td>Aliquot</td>
	            			<td>Volume(mL)</td>
	            			<td>Matrix</td>
	            		</tr>
	            	</thead>
	            	<tbody>
	            	
	            	</tbody>
	            </table>
			</div>

            <div style="margin-top:10px;"><h4 class="page-subheading">Storage</h4></div>
			<table class="ds-form-table reviewFormTable">
            	<tr>
                	<td style='width:150px;'>Is storage different</td>
                    <td id="divIsStorageDifferent"></td>
                </tr>
            </table>

            <table class="ds-form-table reviewFormTable" id="tblStorageDifferent" style="display:none;margin-top:10px;">
            	<thead class="multi-entry-table">
            		<tr>
            			<td></td>
            			<td>Condition</td>
            			<td>Allowed Time(min)</td>
            			<td>Temperature(°C)</td>
            			<td>Storage</td>
            		</tr>
            	</thead>
            	<tbody>
            	
            	</tbody>
            </table>
        </div>

        <div id="ecgTimePointInfo" class='pad-t-60 parent' data-name="ecgTimePoins">
			<h3 class='page-subheading'>ECG</h3>
			<div>
		    	<table class="reviewFormTable">
		        	<tr>
		            	<td style="width:250px;">ECG Applicable</td>
						<td style="width:150px;" id="tdEcgApplicable"></td>
		    		</tr>
				</table>
				<div id="divEcgApplicable" style="display:none;margin-top:10px;">
					<div style="margin-bottom:10px;">
						<table class="reviewFormTable">
			     			<tr>
			         			<td style="width:250px;">Treatment Specific Timepoints</td>
								<td style="width:150px;" id="tdEcgTreatmentSpecificTimepoints"></td>
			      			</tr>
			  			</table>
			 		</div>
			    
					<table class="ds-form-table multi-entry-table removeReviewElement">
						<tbody>
							<tr>
			                    <td style='width:180px;'>Treatment</td>
			                    <td style='width:160px;'>Timepoints</td>
			                    <td colspan="3" style="text-align:center; width:250px;">Window Period</td>
			                    <td style="width:110px"></td>
			                </tr>
		                </tbody>
		            </table>
	            	<table class="ds-form-table removeReviewElement" style="margin-bottom:10px;">
						<tr>
			    			<td style='width:180px;'><select id="EcgTreatment" name="EcgTreatment" class="form-control"><option value='Select'>Select</option></select></td>
			    			<td style='width:160px;'><input type="text" class="form-control timepoint-format" name="EcgTimePoint" id="EcgTimePoint" /></td>
			    			<td style='width:80px;padding-left:5px;'><select class='form-control' name="ecgSign" id='ecgSign'><option value='Select'>Select</option><option value='+'>+</option><option value='-'>-</option><option value='+/-'>+/-</option></select></td>
							<td style='width:80px;padding-left:5px;'><input type='text' name="ecgWindowperiodValue"  data-texttype="NUMERIC" maxlength="3" id='ecgWindowperiodValue' class='form-control'/></td>
							<td style='width:80px;padding-left:5px;'><select class='form-control' id="ecgWindowPeriodTime" name="ecgWindowPeriodTime"><option value='Select'>Select</option><option value='Months'>Minutes</option><option value='Hours'>Hours</option><option value='Days'>Days</option></select></td>
			        		<td><button class='fa fa-plus btn btn-info' id='btnEcg' style='align-content:center;width:100px;line-height:1;' title='Add'></button></td>
			    		</tr>
					</table>
			
					<table data-srnumber="0" data-defaultrow="1" id="tblEcg" class="ds-table ds-table-align-header-center ds-table-border" style="margin-top:20px;">
						<thead>
			    			<tr>
			        			<td style="text-align:center">Treatment No</td>
			        			<td style="text-align:center">Timepoints</td>
								<td style="text-align:center">Window Period</td>
			        			<td class="deleteColumn">Action</td>
			    			</tr>
						</thead>
						<tbody>
			            	<tr style="text-align:center"><td colspan="4">No Data Found</td></tr>
			        	</tbody>
			    	</table>
				</div>
		    </div>
		</div>
        
		<div id="skinAdhesionTimePointInfo" class='pad-t-60 parent' data-name="SkinAdhesion" style="display:none;">
			<h3 class='page-subheading'>Skin Adhesion</h3>
			<div>
		    	<table class="reviewFormTable">
		        	<tr>
		            	<td style="width:250px;">Skin Adhesion Applicable</td>
						<td style="width:150px;" id="tdSkinAdhesionApplicable"></td>
		    		</tr>
				</table>
				<div id="divSkinAdhesionData" style="display:none;margin-top:10px;">
					<div style="margin-bottom:10px;">
						<table class="reviewFormTable">
			     			<tr>
			         			<td style="width:250px;">Treatment Specific Timepoints</td>
								<td style="width:150px;" id="tdSkinAdhesionTreatmentSpecificTimepoints"></td>
			      			</tr>
			  			</table>
			 		</div>
			    
					<table class="ds-form-table multi-entry-table removeReviewElement">
						<tbody>
							<tr>
			                    <td style='width:180px;'>Treatment</td>
			                    <td style='width:280px;'>Parameters</td>
			                    <td style='width:160px;'>Timepoints</td>
			                    <td colspan="3" style="text-align:center; width:250px;">Window Period</td>
			                    <td style="width:110px"></td>
			                </tr>
		                </tbody>
		            </table>
	            	<table class="ds-form-table removeReviewElement" style="margin-bottom:10px;">
						<tr>
			    			<td style='width:180px;'><select id="SkinAdhesionTreatment" name="SkinAdhesionTreatment" class="form-control"><option value='Select'>Select</option></select></td>
			    			<td style='width:280px;'><select id="SkinAdhesionParameters" name="SkinAdhesionParameters" class="form-control" multiple><option value='Select'>Select</option></select></td>
			    			<td style='width:160px;'><input type="text" class="form-control timepoint-format" name="SkinAdhesionTimePoint" id="SkinAdhesionTimePoint" /></td>
			    			<td style='width:80px;padding-left:5px;'><select class='form-control' name="saWindowperiodSign" id='saWindowperiodSign'><option value='Select'>Select</option><option value='+'>+</option><option value='-'>-</option><option value='+/-'>+/-</option></select></td>
							<td style='width:80px;padding-left:5px;'><input type='text' name="saWindowperiodValue"  data-texttype="NUMERIC" maxlength="3" id='saWindowperiodValue' class='form-control'/></td>
							<td style='width:80px;padding-left:5px;'><select class='form-control' id="saWindowPeriodTime" name="saWindowPeriodTime"><option value='Select'>Select</option><option value='Months'>Minutes</option><option value='Hours'>Hours</option><option value='Days'>Days</option></select></td>
			        		<td><button class='fa fa-plus btn btn-info' id='btnSkinAdhesion' style='align-content:center;width:100px;line-height:1;' title='Add'></button></td>
			    		</tr>
					</table>
			
					<table data-srnumber="0" data-defaultrow="1" id="tblSkinAdhesion" class="ds-table ds-table-align-header-center ds-table-border" style="margin-top:20px;">
						<thead>
			    			<tr>
			        			<td style="text-align:center">Treatment</td>
			        			<td style="text-align:center">Parameters</td>
								<td style="text-align:center">Timepoints</td>
								<td style="text-align:center">Window Period</td>
			        			<td class="deleteColumn">Action</td>
			    			</tr>
						</thead>
						<tbody style="text-align:center">
			            	<tr id="mealsTabletr"><td colspan="5">No Data Found</td></tr>
			        	</tbody>
			    	</table>
				</div>
		    </div>
		</div>
        
        <div id="skinSensivityTimePointInfo" class='pad-t-60 parent' data-name="SkinSensivity" style="display:none;">
            <h3 class='page-subheading'>Skin Sensitivity</h3>
            <div>
                <table class="reviewFormTable">
                    <tr>
                        <td style="width:250px;">Skin Sensitivity Applicable</td>
                        <td style="width:150px;" id="tdSkinSensitivityApplicable"></td>
                    </tr>
                </table>
            </div>
            <div id="SkinSensitivityData" style="display:none;margin-top:10px;">
            	<div id="divSkinAdhesionTimePoint" style="margin-bottom:10px;">
					<table class="reviewFormTable">
		     			<tr>
		         			<td style="width:250px;">Treatment Specific Timepoints</td>
							<td style="width:150px;" id="tdSkinSensivityTreatmentSpecificTimepoints"></td>
		      			</tr>
		  			</table>
		 		</div>
		    
				<table class="ds-form-table multi-entry-table removeReviewElement">
					<tbody>
					 	<tr>
		                    <td style='width:180px;'>Treatment</td>
		                    <td style='width:280px;'>Parameters</td>
		                    <td style='width:160px;'>Timepoints</td>
		                    <td colspan="3" style="text-align:center; width:250px;">Window Period</td>
		                    <td style="width:110px"></td>
		                </tr>
	                </tbody>
	            </table>
	            <table class="ds-form-table removeReviewElement" style="margin-bottom:10px;">
					<tr>
		    			<td style='width:180px;'><select id="SkinSensivityTreatment" name="SkinSensivityTreatment" class="form-control"><option value='Select'>Select</option></select></td>
		    			<td style='width:280px;'><select id="SkinSensivityParameters" name="SkinSensivityParameters" class="form-control" multiple><option value='Select'>Select</option></select></td>
		    			<td style='width:160px;'><input type="text" class="form-control timepoint-format" id="SkinSensivityTimePoint" name="SkinSensivityTimePoint"/></td>
		    			<td style='width:80px;padding-left:5px;'><select class='form-control' name="ssWindowperiodSign" id='ssWindowperiodSign'><option value='Select'>Select</option><option value='+'>+</option><option value='-'>-</option><option value='+/-'>+/-</option></select></td>
						<td style='width:80px;padding-left:5px;'><input type='text' name="ssWindowperiodValue" data-texttype="NUMERIC" maxlength="3" id='ssWindowperiodValue' class='form-control' /></td>
						<td style='width:80px;padding-left:5px;'><select class='form-control' id="ssWindowPeriodTime" name="ssWindowPeriodTime"><option value='Select'>Select</option><option value='Months'>Minutes</option><option value='Hours'>Hours</option><option value='Days'>Days</option></select></td>
		        		<td><button class='fa fa-plus btn btn-info' id='btnSkinSensivity' style='align-content:center;width:100px;line-height:1;' title='Add'></button></td>
		    		</tr>
				</table>
		
				<table data-srnumber="0" data-defaultrow="1" id="tblSkinSensivity" class="ds-table ds-table-align-header-center ds-table-border" style="margin-top:10px;">
					<thead>
		    			<tr>
		        			<td style="text-align:center">Treatment</td>
		        			<td style="text-align:center">Parameters</td>
							<td style="text-align:center">Timepoints</td>
							<td style="text-align:center">Window Period</td>
		        			<td class="deleteColumn">Action</td>
		    			</tr>
					</thead>
					<tbody style="text-align:center">
		            	<tr><td colspan="5">No Data Found</td></tr>
		        	</tbody>
		    	</table>
            </div>
        </div>

        <!-- ECG Time Points Information End -->
        <div id="restrictionsComplains" class='pad-t-60 parent' data-name="restrictionsComplainceMonitoring">
            <h1 class='page-subheading'>Restrictions</h1>
            <div id="divTreatmentSpecificRestrictionsComplaince" style="margin-bottom:10px;">
				<table class="reviewFormTable">
	     			<tr>
	         			<td style="width:250px;">Treatment Specific Restrictions</td>
						<td style="width:150px;" id="tdTreatmentSpecificRestrictionsComplaince"></td>
	      			</tr>
	  			</table>
	 		</div>
            
            <div id="divRestrictionsComplaince" style='display:none;'>
	            <table class="ds-form-table col-lg-12 removeReviewElement" style="margin-bottom:10px;">
	                <tr>
		                <td style='width:50px;'>Treatment</td>
	                    <td style='width:200px;padding-left:5px'><select class="form-control" name="resTreatment" id="resTreatment"><option value=''>Select</option></select></td>
		                <td style='width:50px;'>Activity</td>
	                    <td style='width:200px;padding-left:5px'><select class="form-control" name="resActivity" id="resActivity"><option value=''>Select</option></select></td>
	                	<td style='width:50px;'>Parameter</td>
	                    <td style='width:200px;padding-left:5px'><select class="form-control" name="resParameter" id="resParameter"><option value=''>Select</option></select></td>
	                    <td style='width:100px;'>
	                         <button class='fa fa-plus btn btn-info' id='restrictionsbtn' style='align-content:center;width:100px;line-height:1;' title='Add'></button>
	                    </td>
	                </tr>
	            </table>
	
	            <table data-srnumber="0" id="restrictionstable" data-name="restrictionsComplainceMonitoring" class="ds-table ds-table-align-header-center ds-table-border table-first-column-center" style="margin-top:10px" data-defaultrow="1">
	                <thead style="text-align:center">
	                    <tr><td style="width:80px;"><label>Treatment</label></td><td><label>Activity</label></td><td><label>Description</label></td><td class="deleteColumn" style='width:50px;'><label>Action</label></td></tr>
	                </thead>
	                <tbody>
	                    <tr style="text-align:center;"><td colspan="5">No Data Found</td></tr>
	                </tbody>
	            </table>
            </div>
        </div>
        
        <!--Inclusion criteria  -->
        <div id="#inclusionCriteria" class='pad-t-60'>
            <h3 class='page-subheading'>Inclusion Criteria</h3>
            <table class="col-lg-12 removeReviewElement">
                <tbody>
                    <tr>
                    	<td style='width:50px;'>Parameter</td>
                        <td style='padding-left:5px'><select class="form-control" name="icParameter" id="icParameter"><option value=''>Select</option></select></td>
                        <td style='width:50px;'>Gender</td>
                        <td style='padding-left:5px;width:100px;'><select class="form-control" name="icGender" id="icGender"><option value=''>Select</option></select></td>
                        <td style='width:100px;padding-left:5px'>
                            <button class='fa fa-plus btn btn-info' id='inclusioncriteriabtn' style='align-content:center;width:100px;line-height:1;' title='Add'></button>
                        </td>
                    </tr>
                </tbody>
            </table>

            <table data-srnumber="0" class="ds-table ds-table-align-header-center ds-table-border" id="inclusionTable" data-name="inclusionCriteria" style="margin-top:10px" data-defaultrow="1">
                <thead>
                    <tr><td><label>Description</label></td><td style='width:100px;'><label>Gender</label></td>
                    <td class="deleteColumn" style='width:50px;'><label>Action </label></td></tr>
                </thead>
                <tbody>
                    <tr style="text-align:center;"><td colspan="5">No Data Found</td></tr>
                </tbody>
            </table>
        </div>

        <!-- INCLUSION CRITERIA END -->
        <!--Exclusion criteria  -->

        <div id="#exclusionCriteria" class='pad-t-60'>

            <h3 class='page-subheading'>Exclusion Criteria</h3>
            <table class="col-lg-12 removeReviewElement">
                <tbody>
                    <tr>
                    	<td style='width:50px;'>Parameter</td>
                        <td style='padding-left:5px'><select class="form-control" name="ecParameter" id="ecParameter"><option value=''>Select</option></select></td>
                        <td style='width:50px;'>Gender</td>
                        <td style='width:100px;padding-left:5px'><select class="form-control" name="ecGender" id="ecGender"><option value=''>Select</option></select></td>
                        <td style='width:100px;padding-left:5px'>
                            <button class='fa fa-plus btn btn-info' id='exclusionCriteriaBtn' style='align-content:center;width:100px;line-height:1;' title='Add'></button>
                        </td>
                    </tr>
                </tbody>
            </table>

            <!-- EXCLUSION CRITERIA IMPORT POPUP END -->
            <table data-srnumber="0" class="ds-table ds-table-align-header-center ds-table-border" id="exclusionTable" data-name="exclusionCriteria" style="margin-top:10px" data-defaultrow="1">
                <thead>
                    <tr><td><label>Description</label></td><td style='width:100px;'><label>Gender</label></td>
                    <td class="deleteColumn" style='width:50px;'><label>Action </label></td></tr>
                </thead>
                <tbody>
                    <tr style="text-align:center;"><td colspan="5">No Data Found</td></tr>
                </tbody>
			</table>
		</div>
        <!-- EXCLUSION CRITERIA END -->
        
        <!--DEFAULT ACTIVITIES-->
        <div id="#defaultActivities" class='pad-t-60'>
			<h3 class='page-subheading'>Default Activities</h3>
            <table class="col-lg-12 removeReviewElement">
                <tbody>
                    <tr>
                    	<td style='width:50px;'>Activities</td>
                        <td style='padding-left:5px;width:200px;'><select class="form-control" name="DefaultActivities" id="ddlDefaultActivities"><option value=''>Select</option></select></td>
                    	<td style='width:50px;'>Parameter</td>
                        <td style='padding-left:5px'><select class="form-control" name="DefaultActivityParameter" id="ddlDefaultActivityParameter"><option value=''>Select</option></select></td>
                        <td style='width:100px;padding-left:5px'>
                            <button class='fa fa-plus btn btn-info' id='btnDefaultActivities' style='align-content:center;width:100px;line-height:1;' title='Add'></button>
                        </td>
                    </tr>
                </tbody>
            </table>

            <table data-srnumber="0" class="ds-table ds-table-align-header-center ds-table-border" id="tblDefaultActivities" data-name="DefaultActivity" style="margin-top:10px" data-defaultrow="1">
                <thead>
                    <tr><td style='width:250px;'><label>Activity</label></td><td><label>Parameter</label></td><td class="deleteColumn" style='width:50px;'><label>Action </label></td></tr>
                </thead>
                <tbody>
                    <tr style="text-align:center;"><td colspan="3">No Data Found</td></tr>
                </tbody>
			</table>
		</div>
        <!-- DEFAULT ACTIVITIES END -->
        
        
         <!--OTHER ACTIVITIES START-->

        <div id="otherActivities" class='pad-t-60 parent' data-name="OtherActivities">
			<h3 class='page-subheading'>Other Activities</h3>
            <table class="col-lg-12 removeReviewElement">
                <tbody>
                    <tr>
                    	<td style='width:100px;'>Activity</td>
                        <td style='width:150px;'><select class="form-control" name="otherActivity" id="otherActivity"><option value=''>Select</option></select></td>
                        <td style='width:150px;'>Treatment Specific</td>
                        <td style='width:150px;'><select class="form-control" name="otherTreatmentSpecific" id="otherTreatmentSpecific"><option value=''>Select</option></select></td>
                        <td style='width:150px;'>Timepoint Specific</td>
                        <td style='width:150px;'><select class="form-control" name="otherTimepointSpecific" id="otherTimepointSpecific"><option value=''>Select</option></select></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
            
            <table class="col-lg-12 removeReviewElement" style="margin-bottom:10px;">
                <tr>
	                <td style='width:100px;'>Treatment</td>
                    <td style='width:150px;'><select class="form-control" name="otherTreatment" id="otherTreatment"><option value=''>Select</option></select></td>
	                <td style='width:150px;'>Timepoint</td>
                    <td style='width:150px;'><input type="text" name="otherTimepoint" id="otherTimepoint" class="form-control timepoint-format" data-notp="4"></td>
                	<td style='width:150px;'>Parameter</td>
                    <td style='width:150px;'><select class="form-control" name="otherParameter" id="otherParameter" multiselect><option value=''>Select</option></select></td>
                    <td style='width:100px;'>
                         <button class='fa fa-plus btn btn-info' id='btnOtherActivity' style='align-content:center;width:100px;line-height:1;' title='Add'></button>
                    </td>
                    <td></td>
                </tr>
            </table>
            
            <table data-srnumber="0" class="ds-table ds-table-align-header-center ds-table-border" id="tblOtherActivity" data-name="OtherActivity" style="margin-top:10px" data-defaultrow="1">
                <thead>
                    <tr><td><label>Activity</label></td><td><label>Treatment</label></td><td><label>Timepoint</label></td><td><label>Parameter</label></td>
                    <td class="deleteColumn" style='width:50px;'><label>Action</label></td></tr>
                </thead>
                <tbody>
                    <tr style="text-align:center;"><td colspan="5">No Data Found</td></tr>
                </tbody>
			</table>

        </div>
        <!-- OTHER ACTIVITIES END -->

    </div>
</form>
            
    <c:choose>
        <c:when test="${review == 'review'}">
            <div class="col-lg-12 text-center">
				<input id="btnSendComments" class="btn btn-primary" type='submit' value="Send Comments"/>
				<input id="btnAcceptComments" class="btn btn-primary" type='submit' value="Accept"/>
			</div>
        </c:when>
        <c:otherwise>
			<div class="col-lg-12 text-center">
				<input id="btnSubmit" class="btn btn-primary" type='submit' value="Submit"/>
				<input id="btnProjectDesignBack" class="btn btn-primary" type='submit' value="Back"/>
			</div>
        </c:otherwise>
    </c:choose>
      
	<form:form id="frmData" method="POST">
			
	</form:form>


            