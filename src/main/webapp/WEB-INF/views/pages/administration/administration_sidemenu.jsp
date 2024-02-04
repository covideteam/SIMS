<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags"%>

 <div class="menu_section" oncontextmenu="return false;">
	 <ul class="nav side-menu">
	 	<li style='display:none;'>
	 		<a><i class='fa fa-home'></i><spring:message code="label.projects"></spring:message><span class='fa fa-chevron-down'></span></a>
	 		<ul class='nav child_menu' id="liProjects">
	 		</ul>
	 	</li>
	 	<c:if test= "${pwscondition eq false}">
	 	<c:if test= "${pwsExp}">
	 	
	 	<c:choose>
	    	<c:when test="${Menu1}">
	    		<li><a id="siteAdmMenu"><i class="fa fa-home"></i><spring:message code="label.sa.menu"></spring:message><span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
			    	     <c:if test="${SMenu1}">
			      		 	<li><a href='<c:url value="/modulesAccess/getModuleAccessForm"/>' id="moduleAc"><spring:message code="label.roleSideMenu"></spring:message></a></li>
				           </c:if>
				        <c:if test="${userRole eq 'SUPERADMIN'}">
				        	<li><a href='<c:url value="/modulesAccess/addLinkNames"/>' id="addSideMenu"><spring:message code="label.addLinkForm"></spring:message></a></li>
				        	<li><a href='<c:url value="/userRole/addApprovalLevals"/>' id="addApprLevels"><spring:message code="label.workFlow"></spring:message></a></li>
				        	<li><a href='<c:url value="/triggerController/triggerForm"/>' id="trigger"><spring:message code="label.trigger"></spring:message></a></li>
				       </c:if>
			        </ul>
		        </li>
	    	</c:when>
	    	<c:otherwise>
	    	<c:if test="${userRole eq 'SUPERADMIN'}">
	    		<li><a id="siteAdmMenu"><i class="fa fa-home"></i><spring:message code="label.sa.menu"></spring:message><span class="fa fa-chevron-down"></span></a>
					<ul class="nav child_menu">
			    		
		    			<li><a href='<c:url value="/modulesAccess/getModuleAccessForm"/>' id="moduleAc"><spring:message code="label.roleSideMenu"></spring:message></a></li>
				        	<li><a href='<c:url value="/modulesAccess/addLinkNames"/>' id="addSideMenu"><spring:message code="label.addLinkForm"></spring:message></a></li>
				        	<li><a href='<c:url value="/userRole/addApprovalLevals"/>' id="addApprLevels"><spring:message code="label.workFlow"></spring:message></a></li>
				        	<li><a href='<c:url value="/triggerController/triggerForm"/>' id="trigger"><spring:message code="label.trigger"></spring:message></a></li>
				      </ul>
		        	</li>
		         </c:if>
	    	</c:otherwise>
	    </c:choose>
	    <c:choose>
	    	<c:when test="${userRole eq 'SUPERADMIN'}">
	    		<li><a id="configMenu"><i class="fa fa-users"></i> <spring:message code="label.mainLinkUserConfiguration"></spring:message> <span class="fa fa-chevron-down"></span></a>
			      <ul class="nav child_menu">
			      	 	<li><a href='<c:url value="/user/createUser"/>' id="userCreation"><spring:message code="label.userCrTitle"></spring:message></a></li>
			        	<li><a href='<c:url value="/userRole/createRole"/>' id="roleCreation"><spring:message code="label.roloCrTitle"></spring:message></a></li>
			      </ul>
		   		 </li>
	    	</c:when>
	    	<c:otherwise>
	    		<c:if test="${Menu2}">
				    <li><a id="configMenu"><i class="fa fa-users"></i> <spring:message code="label.mainLinkUserConfiguration"></spring:message> <span class="fa fa-chevron-down"></span></a>
				      <ul class="nav child_menu">
				      	<c:if test="${SMenu1 or SMenu2}">
				        	<li><a href='<c:url value="/user/createUser"/>' id="userCreation"><spring:message code="label.userCrTitle"></spring:message></a></li>
				        </c:if>
				        <c:if test="${SMenu2 or SMenu3}"> 
				        	<li><a href='<c:url value="/userRole/createRole"/>' id="roleCreation"><spring:message code="label.roloCrTitle"></spring:message></a></li>
				        </c:if>
				      </ul>
				    </li>
	   			 </c:if>
	    	</c:otherwise>
	    </c:choose>
	    <!-- Administration -->
	 	<c:if test="${Menu3}">
	    <li><a id="adminMenu"><i class="fa fa-cog"></i><spring:message code="label.sidmenu.administration"></spring:message>  <span class="fa fa-chevron-down"></span></a>
	      <ul class="nav child_menu">
	      	 <c:if test="${SMenu4}">
					<li><a href='<c:url value="/units/unitsForm"/>' id="unitsCreation"><spring:message code="label.unit.units"></spring:message></a></li>
			  </c:if>
	      	  <c:if test="${SMenu5}">
		      	<li><a href='<c:url value="/values/valuesPage"/>' id="valuesCreation"> <spring:message code="label.gvlink"></spring:message></a></li>
		      </c:if>
		      <c:if test="${SMenu6}">
		      	<li><a href='<c:url value="/groups/groupPage"/>' id="groupsCreation"><spring:message code="label.ggsLink"></spring:message></a></li>
		      </c:if>
		       <c:if test="${SMenu7}">
		      	<li><a href='<c:url value="/globalAtivity/activityPage"/>' id="activitiesCreation"><spring:message code="label.gasLink"></spring:message></a></li>
		      </c:if>
		      <c:if test="${SMenu8}">
		      	<li><a href='<c:url value="/parameters/parmetersPage"/>' id="parametersCreation"><spring:message code="label.gpsLink"></spring:message></a></li>
		      </c:if>
		      <c:if test="${SMenu9}">
		      	<li><a href='<c:url value="/defaultActivity/defaultActivityForm"/>' id="defalultActCreation"><spring:message code="label.defaultAct"></spring:message></a></li>
		      </c:if>
		       <c:if test="${SMenu10}">
		      	<li><a href='<c:url value="/customActParams/createCustomActivityParameters/"/>' id="customActParams"><spring:message code="label.configurationCustomActivityParameters"></spring:message></a></li>
		      </c:if>
		      <c:if test="${SMenu11}">
		      	<li><a href='<c:url value="/rules/rulesPage"/>' id="rulesCreation"><spring:message code="label.ruleSLink"></spring:message>  </a></li>
		      </c:if>
		      <c:if test="${SMenu12}">
			  	<li><a href='<c:url value="/condition/conditionForm"/>' id="conditionsCreation"><spring:message code="label.configurCondition"></spring:message></a></li>
			  </c:if>
			  <c:if test="${SMenu13}">
			  	<li><a href='<c:url value="/groups/groupDesignActivity"/>' id="groupDesingActs"><spring:message code="label.groupDesignActivity.LinkName"></spring:message></a></li>
			  </c:if>
			   <c:if test="${SMenu14}">
			  	<li><a href='<c:url value="/mealDiet/mealDietPlanList"/>' id="mealDietPlan"><spring:message code="label.mealDiet.dietPlan"></spring:message></a></li>
			  </c:if>
			</ul>
	    </li>
	    </c:if>
	    <!-- Instrument -->
	     <c:if test="${Menu4}">
		<li>
			<a id="instrumensMenu"><i class="fa fa-history"></i><spring:message code="label.instrument.Link"></spring:message> <span class="fa fa-chevron-down"></span></a>
			<ul class="nav child_menu">
				 <c:if test="${SMenu15}">
					<li><a href='<c:url value="/instrument/instrumentForm"/>' id="instrumentsCreation"><spring:message code="label.instrument.Link"></spring:message></a></li>
				</c:if>
				<%--  <c:if test="${SMenu16}">
					<li><a href='<c:url value="/instrument/deepfreezerForm"/>' id="storageTypes"><spring:message code="label.deepfreezer.sideLink"></spring:message></a></li>
				</c:if> --%>
			</ul>
		</li>
	    </c:if>
	    <!-- Pharma Module -->
	    <%--  <c:if test="${Menu5}">
	    <li>
	    <a><i class="fa fa-medkit"></i><spring:message code="label.pharmacy"></spring:message><span class="fa fa-chevron-down"></span></a>
	      <ul class="nav child_menu">
	       <c:if test="${SMenu16}">
	        	<li><a href='<c:url value="/drugReception/drugListForm"/>'><spring:message code="label.druglist"></spring:message></a></li>
	        </c:if>
	         <c:if test="${SMenu17}">
	        	<li><a href='<c:url value="/drugReception/drugApprovalList"/>'><spring:message code="label.drugreception.approvalTitel"></spring:message></a></li>
	        </c:if>
	      	  <c:if test="${SMenu18}">
	        	<li><a href='<c:url value="/drugReception/drugReceptionForm"/>'><spring:message code="label.drugReception"></spring:message></a></li>
	        </c:if> 
	        <c:if test="${SMenu19}">
	        	<li><a href='<c:url value="/documentryRequ/documentryRequirements"/>'><spring:message code="label.documentaryRequirements"></spring:message></a></li>
	        </c:if>
	         <c:if test="${SMenu20}">
	        	<li><a href='<c:url value="/studyDrugDispensing/studyDrugDispensingList"/>'><spring:message code="label.studyDrugDispensing.Title"></spring:message></a></li>
	        </c:if>
	        <c:if test="${SMenu21}">
	        	<li><a href='<c:url value="/pharmacyData/lineClearanceActivityList"/>'><spring:message code="label.lineClearanceActivity.titel"></spring:message></a></li>
	        </c:if>
	         <c:if test="${SMenu22}">
	        	<li><a href='<c:url value="/pharmacyData/dispensedIPHandoverList"/>'><spring:message code="label.dispensedIPHandover.titel"></spring:message></a></li>
	        </c:if>
	         <c:if test="${SMenu23}">
	        	<li><a href='<c:url value="/pharmacyData/ipRetentionList"/>'><spring:message code="label.iPRetention.title"></spring:message></a></li>
	        </c:if>
	         <c:if test="${SMenu24}">
	        	<li><a href='<c:url value="/pharmacyData/statusOfUnusedDispensedList"/>'><spring:message code="label.statusOfUnusedDispensed.titel"></spring:message></a></li>
	        </c:if>
	         <c:if test="${SMenu25}">
	        	<li><a href='<c:url value="/pharmacyData/ipDiscardList"/>'><spring:message code="label.ipDiscard.titel"></spring:message></a></li>
	        </c:if>
	        </ul>
	        </li>
	     </c:if> --%>
	     <!--  -->
	    <c:if test="${Menu6}">
	    <li>
	    <a id="studyMenu"><i class="fa fa-database"></i><spring:message code="label.studySideMenu"></spring:message><span class="fa fa-chevron-down"></span></a>
	      <ul class="nav child_menu">
	      	 <c:if test="${SMenu27}">
	        	<li><a href='<c:url value="/randomization/uploadRandomizationForm"/>' id="randomizationCreation"><spring:message code="label.randomizationTitle"></spring:message> </a></li>
	        </c:if>
	        <c:if test="${SMenu28}">
	        	<li><a href='<c:url value="/studydesign/studyDesignPage"/>' id="studyDesigning"> <spring:message code="label.projects"></spring:message></a></li>
	        </c:if>
	        <c:if test="${SMenu29}">
	        	<li><a href='<c:url value="/delegation/delegationPage"/>' id="delegations"><spring:message code="label.studyAssign"></spring:message></a></li>
	        </c:if>
	        <c:if test="${SMenu30}">
	        	<li><a href='<c:url value="/studyExe/studyExePage"/>' id="studyExecution"><spring:message code="label.seSLink"></spring:message> </a></li>
	        </c:if>
	         <c:if test="${SMenu31}"> 
	       		<li><a href='<c:url value="/deviations/deviationPage/Submit"/>' id="deviations"><spring:message code="label.deviationSideMenu"></spring:message> </a></li>
	     	  </c:if> 
	         <c:if test="${SMenu32}"> 
	       	 	<li><a href='<c:url value="/studyGroups/studyGroupsForm"/>' id="studyGroup"><spring:message code="label.crfData.studyGroup"></spring:message> </a></li>
	      	 </c:if> 
	      	 <c:if test="${SMenu33}"> 
	       	 	<li><a href='<c:url value="/stdMealDietConfig/studyMealDietConfiguration"/>' id="studyGroup"><spring:message code="label.study.meal.diet.config"></spring:message> </a></li>
	      	 </c:if> 
	      	  <c:if test="${SMenu34}">
			  	<li><a href='<c:url value="/subMealCon/studyMealConsumptionPage"/>' id="mealDietPlan"><spring:message code="label.study.meal.consumption.diet"></spring:message></a></li>
			  </c:if> 
	      	  <c:if test="${SMenu35}">
	       	 	<li><a href='<c:url value="/drugDispanse/drugDispanseEntry"/>' id="drugDispanseEntry"><spring:message code="label.drugDispance.sideMenu"></spring:message> </a></li>
	      	  </c:if>
	          <c:if test="${SMenu36}">
	       	 	<li><a href='<c:url value="/allowMeal/allowMealsPage"/>' id="drugDisPdf"><spring:message code="label.allowmeals.sideMenu"></spring:message> </a></li>
	      	 </c:if>
		 </ul>
	    </li>
	    </c:if>
	   
	    <c:if test="${Menu7}">
		    <li><a id="barcodesMenu"><i class="fa fa-barcode"></i><spring:message code="label.mainLinkBarcode"></spring:message><span class="fa fa-chevron-down"></span></a>
			    <ul class="nav child_menu">
			       <c:if test="${SMenu37}">
			       	 <li><a href='<c:url value="/barcodes/barcodelabelsPage"/>' id="barcodesLabeling"><spring:message code="label.mainLinkBarcode"></spring:message></a></li>
			      </c:if>
			     <%--  <c:if test="${SMenu38}">
						<li><a href='<c:url value="/barcodelabels/stdSubjectContainerPrint"/>' id="studySubjectsContainer"><spring:message code="label.barcodeSubjectContainer"></spring:message> </a></li>
					</c:if> --%>
					<%-- <c:if test="${SMenu39}">
						<li><a href='<c:url value="/barcodelabels/boxBarCodePrintPage"/>' id="boxBarcodes"><spring:message code="label.barcodeBox"></spring:message></a></li>
					</c:if> --%>
			      	<c:if test="${SMenu40}">
						<li><a href='<c:url value="/barcodelabels/instrumentBarCode"/>' id="instrumentBarcodes"><spring:message code="label.barcodeInstrument"></spring:message></a></li>
					</c:if>
					<%-- <c:if test="${SMenu41}">
						<li><a href='<c:url value="/barcodelabels/deepFreezerBarCode"/>' id="deepFreezerLabels"><spring:message code="label.deepfreezer.sideLink"></spring:message></a></li>
					</c:if> --%>
					<c:if test="${SMenu42}">
						<li><a href='<c:url value="/barcodelabels/deepFreezerShelfBarCode"/>' id="deepFreezerShelfLabels"><spring:message code="label.deepfreezershelf.sideLink"></spring:message></a></li>
					</c:if>
					<%-- <c:if test="${SMenu43}">
						<li><a href='<c:url value="/barcodelabels/centrifugation"/>' id="centrifuge">Centrifuge</a></li>
					</c:if> --%>
					
			   </ul>
		    </li>
	    </c:if>
	    <c:if test="${Menu8}">
    		<li>
    			<a id="reviewMenu"><i class="fa fa-search"></i><spring:message code="label.reviewed"></spring:message><span class="fa fa-chevron-down"></span></a>
	    		<ul class="nav child_menu">
					<c:if test="${SMenu43}">
					  	<li><a href='<c:url value="/studydesign/projectApprovalList"/>' id="studyDesingReview"><spring:message code="label.projectsApproval"></spring:message></a></li>
					</c:if>
					<c:if test="${SMenu44}">
					  	<li><a href='<c:url value="/randomization/randomizationApprovalList"/>' id="randomizationReview"><spring:message code="label.randomizationApprovalSide"></spring:message></a></li>
					</c:if>
					<c:if test="${SMenu45}">
						<li><a href='<c:url value="/studyExe/studyReview"/>' id="studyDataReview"><spring:message code="label.studyReview"></spring:message></a></li>
					</c:if>
					<c:if test="${SMenu46}">
						<li><a href='<c:url value="/studyExe/responseToStudyReview"/>' id="responseToStudyDataReview"><spring:message code="label.ResponseStudyReview"></spring:message></a></li>
					</c:if>
					<c:if test="${SMenu47}">
						<li><a href='<c:url value="/deviations/deviationPage/Review"/>' id="deviations"><spring:message code="label.deviationSideMenu"></spring:message> </a></li>
	       			</c:if>
	       		</ul>
			</li>
		</c:if>
	    <c:if test="${Menu9}">
			<li>
				<a id="reportMenu"><i class="fa fa-file"></i><spring:message code="label.report.title"></spring:message> <span class="fa fa-chevron-down"></span></a>
				<ul class="nav child_menu">
				 	<c:if test="${SMenu48}">
						 	<li><a href='<c:url value="/drugDispansePdf/generateDrugDispansePdf"/>' id="drugDisPdf"><spring:message code="label.drugDispanse.pdf"></spring:message> </a></li>
	      			</c:if>
					 <c:if test="${SMenu49}">
					 	<li><a href='<c:url value="/crfData/studyCrfData"/>' id="studyCrfData"><spring:message code="label.crfData"></spring:message> </a></li>
	      			</c:if>
					 <c:if test="${SMenu50}">
						<li><a href='<c:url value="/mealMenuReports/generateMealMenuReport"/>' id="mealMenuReport"><spring:message code="label.mealMenuReport"></spring:message> </a></li>
					</c:if>
					<c:if test="${SMenu51}">
						<li><a href='<c:url value="/missedtpsReports/generateMissedTimepointsReport"/>' id="mealMenuReport"><spring:message code="label.missedTpReport"></spring:message> </a></li>
					</c:if>
				</ul>
			</li>
	    </c:if>
	   <c:if test="${Menu10}">
			<li>
				<a id="auditMenu"><i class="fa fa-history"></i><spring:message code="label.log.logsTitel"></spring:message> <span class="fa fa-chevron-down"></span></a>
				<ul class="nav child_menu">
				 	<c:if test="${SMenu52}">
						<li><a href='<c:url value="/auditlog/auditLogTrigerDataSearch"/>' id="activityLog"><spring:message code="label.log.sideAuditLog"></spring:message></a></li>
					</c:if>
					 <c:if test="${SMenu53}">
						<li><a href='<c:url value="/auditlog/activityLoginDataSearch"/>' id="auditLog"><spring:message code="label.log.sideActivityLog"></spring:message></a></li>
					</c:if>
					 <c:if test="${SMenu54}">
						<li><a href='<c:url value="/auditlog/userLogInfoPage"/>' id="userActLog"><spring:message code="label.log.sideUserLog"></spring:message> </a></li>
					</c:if>
				</ul>
			</li>
	    </c:if>
	   </c:if>
	   </c:if>
	  </ul>
	</div>
</body>


