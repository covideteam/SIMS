<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags"%>

<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><tiles:insertAttribute name="title" /></title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href='<c:url value="/static/Tooltips/psw.css"></c:url>' rel="stylesheet">
  <link href='<c:url value='/static/Tooltips/Style.css'/>' rel="stylesheet" />
    <link href="cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css">
    <link href="<c:url value="/static/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/vendors/nprogress/nprogress.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/vendors/iCheck/skins/flat/green.css"/>" rel="stylesheet"/>
	<link href="<c:url value="/static/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/static/vendors/jqvmap/dist/jqvmap.min.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/vendors/bootstrap-daterangepicker/daterangepicker.css"/>" rel="stylesheet">
    <link href="<c:url value="/static/css/custom.min.css"/>" rel="stylesheet">
	<link href="<c:url value="/static/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css"/>" rel="stylesheet">
	<link href="<c:url value="/static/jquery-ui/jquery-ui.min.css"/>" rel="stylesheet">
	<link href="<c:url value="/static/confirmjs/jquery-confirm.min.css"/>" rel="stylesheet">
	<link href="<c:url value="/static/validation/validation.min.css"/>" rel="stylesheet">
	<link href="<c:url value="/static/sweetalert/sweetalert2.css"/>" rel="stylesheet">
	<script src="<c:url value='/static/sweetalert/sweetalert2.js'/>"></script>
	<link href="<c:url value="/static/growl/stylesheets/jquery.growl.css"/>" rel="stylesheet">
	<link href="<c:url value="/static/multiselect/jquery.multiselect.css"/>" rel="stylesheet">
	<link href="<c:url value="/static/css/main.css"/>" rel="stylesheet">
	<link href="<c:url value="/static/css/comments.css"/>" rel="stylesheet">
	<link href="<c:url value="/static/tostnotifications/css/bs4Toast.css"/>" rel="stylesheet" >
	<%-- <link href='<c:url value="/static/boostrapTable/datatables.min.css"/>'> --%>
	

	<!-- jQuery -->
    <script src="<c:url value='/static/vendors/jquery/dist/jquery.min.js'/>"></script>
    <script src="<c:url value="/static/tostnotifications/bs4-toast.js"/>"></script>
</head>
<input type="hidden" id="mainUrl"/>
<input type="hidden" id="ajaxUrl"/>
<input type="hidden" id="serverDate" name="serverDate">
<script>
	var mainUrl = window.location.origin + "/SIMS/";
	document.getElementById("mainUrl").value = mainUrl;
	document.getElementById("ajaxUrl").value = window.location.origin;
</script>


<input type="hidden" id="dateFormatJsp" value="${dateFormatJsp }"/>
<input type="hidden" id="pwd" value="${pwd}"/>
<body class="nav-md" oncontextmenu="return false;">
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" >
<%--               <a href="<c:url value="/administration/"/>" class="site_title"><img src='<c:url value='/static/images/SIMS.PNG'/>' style='width:100%; height: 100%; letter-spacing: -10%'/> <span></span></a> --%>
<a href="<c:url value="/administration/"/>" class="site_title"> <span>SIMS</span></a>
            </div>

            <div class="clearfix"></div>
            <!-- menu profile quick info -->
            <%-- <div class="profile clearfix">
              <div class="profile_pic">
                <img src="<c:url value='/static/images/user.png'/>" alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <span>Welcome,</span>
                <h2>${userFullName}</h2>
              </div>
            </div> --%>
            <!-- /menu profile quick info -->

            <br />

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
	            <tiles:insertAttribute name="menu" />
            </div>
            <!-- /sidebar menu -->

            <!-- /menu footer buttons -->
            <div class="sidebar-footer hidden-small">
              <a data-toggle="tooltip" data-placement="top" title="Settings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" id="logOut" title="Logout" href="/SIMS/logout">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
              </a>
            </div>
            <!-- /menu footer buttons -->
          </div>
        </div>
		
        <!-- top navigation -->
        <div class="top_nav">
          <div class="nav_menu">
              <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
              </div>
              <nav class="nav navbar-nav">
              <ul class=" navbar-right">
                <li class="nav-item dropdown open" style="padding-left: 15px;">
                  <a href="javascript:;" class="user-profile dropdown-toggle" aria-haspopup="true" id="navbarDropdown" data-toggle="dropdown" aria-expanded="false">
                   <%--  <img src="<c:url value='/static/images/user.png'/>" alt=""> --%>${userFullName}
                  </a>
                  <div class="dropdown-menu dropdown-usermenu pull-right" aria-labelledby="navbarDropdown">
                  <c:if test="${userRole ne 'SUPERADMIN'}">
                    <a class="dropdown-item"  href='<c:url value="/uad/changePassword"/>' id="changePassword"><label>Change Password</label></a>
                    </c:if>
                    <a class="dropdown-item"  href="/SIMS/logout"><i class="fa fa-sign-out pull-right" id="logOut"></i><spring:message code="label.logOut"></spring:message></a>
                  </div>
                </li>
                <li role="presentation" class="nav-item dropdown open">
                	 <spring:message code="label.serverTimeOut"></spring:message><p id="servertime" style="display:inline;color:black;">00:00:00</p>
                </li>
                <li></li>
                <%--  <li class="nav-item dropdown open" style="padding-right: 30px;">
                  <a href="javascript:;" class="dropdown-toggle" aria-haspopup="true" id="navbarDropdown" data-toggle="dropdown" aria-expanded="false">
                    	<c:if test="${languageCode eq 'en'}">
                    		<img alt="" src="<c:url value='/static/images/india-flag-icon.png'/>" width="30px;">
                    	</c:if>
                    	<c:if test="${languageCode eq 'es'}">
                    		<img alt="" src="<c:url value='/static/images/mexicoLangFlag.png'/>" width="30px;">
                    	</c:if>
                  </a>
                   <div class="dropdown-menu dropdown-usermenu pull-right" aria-labelledby="navbarDropdown" style='min-width:70px !important;max-width:70px !important;padding:5px;'>
                    <a style="display: block;"  href="#" onclick="chnageLocale('en')"><img alt="" src="<c:url value='/static/images/india-flag-icon.png'/>" width="30px;"></a>
                 	<a style="display: block;"  href="#" onclick="chnageLocale('es')"><img alt="" src="<c:url value='/static/images/mexicoLangFlag.png'/>" width="30px;" ></a>
                  </div>
                </li> --%>
               
              </ul>
            </nav>
          </div>
        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main" style='min-height:900px;padding-bottom:100px;'>
        	<div id="bodyDiv"><tiles:insertAttribute name="body" /></div>
        	<tiles:insertAttribute name="message"/>
        </div>
          <!-- /top tiles -->

        
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer class='footer_fixed'>
          <div class="pull-right">
             <a href="#" style='color:white;'><spring:message code="label.copyRight"></spring:message></a>
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
  
<div class="loader">
  <span class="loader__element"></span>
  <span class="loader__element"></span>
  <span class="loader__element"></span>
</div>
<!-- ./wrapper -->
 
    <!-- Bootstrap -->
    <script src="<c:url value='/static/vendors/bootstrap/dist/js/bootstrap.bundle.min.js'/>"></script>
    <!-- FastClick -->
    <script src="<c:url value='/static/vendors/fastclick/lib/fastclick.js'/>"></script>
    <!-- NProgress -->
    <script src="<c:url value='/static/vendors/nprogress/nprogress.js'/>"></script>
   <!-- gauge.js -->
    <script src="<c:url value='/static/vendors/gauge.js/dist/gauge.min.js'/>"></script>
    <!-- bootstrap-progressbar -->
    <script src="<c:url value='/static/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js'/>"></script>
    <!-- iCheck -->
    <script src="<c:url value='/static/vendors/iCheck/icheck.min.js'/>"></script>
    <!-- Skycons -->
    <script src="<c:url value='/static/vendors/skycons/skycons.js'/>"></script>
    <!-- Flot -->
    <script src="<c:url value='/static/vendors/Flot/jquery.flot.js'/>"></script>
    <script src="<c:url value='/static/vendors/Flot/jquery.flot.pie.js'/>"></script>
    <script src="<c:url value='/static/vendors/Flot/jquery.flot.time.js'/>"></script>
    <script src="<c:url value='/static/vendors/Flot/jquery.flot.stack.js'/>"></script>
    <script src="<c:url value='/static/vendors/Flot/jquery.flot.resize.js'/>"></script>
    <!-- Flot plugins -->
    <script src="<c:url value='/static/vendors/flot.orderbars/js/jquery.flot.orderBars.js'/>"></script>
    <script src="<c:url value='/static/vendors/flot-spline/js/jquery.flot.spline.min.js'/>"></script>
    <script src="<c:url value='/static/vendors/flot.curvedlines/curvedLines.js'/>"></script>
    <!-- DateJS -->
    <script src="<c:url value='/static/vendors/DateJS/build/date.js'/>"></script>
    <!-- JQVMap -->
    <script src="<c:url value='/static/vendors/jqvmap/dist/jquery.vmap.js'/>"></script>
    <script src="<c:url value='/static/vendors/jqvmap/dist/maps/jquery.vmap.world.js'/>"></script>
    <script src="<c:url value='/static/vendors/jqvmap/examples/js/jquery.vmap.sampledata.js'/>"></script>
    <!-- bootstrap-daterangepicker -->
    <script src="<c:url value='/static/vendors/moment/min/moment.min.js'/>"></script>
    <script src="<c:url value='/static/vendors/bootstrap-daterangepicker/daterangepicker.js'/>"></script>
    
    <script src="<c:url value='/static/vendors/datatables.net/js/jquery.dataTables.min.js'/>"></script>
    <script src="<c:url value='/static/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js'/>"></script>
    <script src="<c:url value='/static/vendors/datatables.net-buttons/js/dataTables.buttons.min.js'/>"></script>
    <script src="<c:url value='/static/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js'/>"></script>
    <script src="<c:url value='/static/vendors/datatables.net-buttons/js/buttons.flash.min.js'/>"></script>
    <script src="<c:url value='/static/vendors/datatables.net-buttons/js/buttons.html5.min.js'/>"></script>
    <script src="<c:url value='/static/vendors/datatables.net-buttons/js/buttons.print.min.js'/>"></script>
    <script src="<c:url value='/static/vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js'/>"></script>
    <script src="<c:url value='/static/vendors/datatables.net-keytable/js/dataTables.keyTable.min.js'/>"></script>
    <script src="<c:url value='/static/vendors/datatables.net-responsive/js/dataTables.responsive.min.js'/>"></script>
    <script src="<c:url value='/static/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js'/>"></script>
    <script src="<c:url value='/static/vendors/datatables.net-scroller/js/dataTables.scroller.min.js'/>"></script>
    <script src="<c:url value='/static/jquery-ui/jquery-ui.min.js'/>"></script>
    <script src="<c:url value='/static/validation/validation.min.js'/>"></script>
    <script src="<c:url value='/static/multiselect/jquery.multiselect.js'/>"></script>
    <script src="<c:url value='/static/js/main.js'/>"></script>
    <script src="<c:url value='/static/js/comments.js'/>"></script>
    <script src="<c:url value='/static/growl/javascripts/jquery.growl.js'/>"></script>
    <script src="<c:url value='/static/growl/notifications.js'/>"></script>
    <script src="<c:url value='/static/js/formbuilder.js'/>"></script>
    <script src="<c:url value='/static/confirmjs/jquery-confirm.min.js'/>"></script>
    <script src="<c:url value="/static/boostrapTable/datatables.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/boostrapTable/pdfmake.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/boostrapTable/vfs_fonts.js"/>" type="text/javascript"></script>
    <!-- Custom Theme Scripts -->
    <script src="<c:url value='/static/js/custom.min.js'/>"></script>
    <script src="/SIMS/static/validation/jquery.validate.min.js"></script>
    <script src="<c:url value='/static/html5qrcode/html5-qrcode.min.js'/>"></script>
    <script src='/SIMS/static/Tooltips/Tooltip.js'></script>
    <script type="text/javascript">
        var mainUrl = window.location.origin + "/SIMS/";
        var ajaxUrl ="";
		function synchronousAjaxCall(input){
			var result;
			$.ajax({
		        type: "GET",
		        url: input,
		        async: false,
		        success : function(data) {
		        	result = data;
		        }
		    });
			return result;
		}
		function asynchronousAjaxCall(input){
			var result;
			$.ajax({
		        type: "GET",
		        url: input,
		        async: true,
		        success : function(data) {
		        	result = data;
		        }
		    });
			return result;
		}
		function asynchronousAjaxCallBack(input,callBack){
			$.ajax({
		        type: "GET",
		        url: input,
		        async: true,
		        success : function(data) {
		        	if(callBack !== null && typeof callBack !== 'undefined'){
		        		callBack(data);
		        	}
		        }
		    });
		}
		function asynchronousPostAjaxCall(input){
			var result;
			$.ajax({
		        type: "POST",
		        url: input,
		        async: false,
		        success : function(data) {
		        	result = data;
		        }
		    });
			return result;
		}   
	</script>
	<script type="text/javascript">
	 function updateServerTime() {
        $.get(mainUrl+"/timeCtrl/serverTime", function(response) {
        	var date = new Date(response);
        	$("#servertime").html(date.getHours()+":"+date.getMinutes()+":"+date.getSeconds());
			$('#serverDate').val(response);
			setTimeout(updateServerTime, 1000); // Refresh every second
				/* $.growl.error({ message: "Error Message"});
				$.growl.notice({ message: "Success Message"});
				$.growl.warning({ message: "Warining Message"});
				$.growl.infoMsg({ message: "Notice Message"}); */
				
// 			bs4Toast.primary('', 'Primary Message');
				
        });
    }
	 
	 $(document).ready(function() {
 		debugger;
        var table = $('#example').DataTable({
     	
         searchBuilder: true,
         "language": {
         "lengthMenu": "${showLabel} _MENU_ ${entriesLabel}",
 		 "search": "${searchLabel}:",
 		 "zeroRecords": "${noDtaAvlLabel}",
 	     "info": "${showingPgsLabel} _PAGE_ ${ofLabel} _PAGES_ & _MAX_ ${entriesLabel}",
 	     "infoEmpty": "${noRcdsLabel}",
 	     "infoFiltered": "(${filterLabel} _MAX_ ${totRcdsLabel})",
 	     "paginate": {
 	         "previous": " ${prevPgLabel}",
 	        	 "next": "${nextLabel}"
 	       }
 		 }
     });
 });
	 
	 $(document).ready(function() {
        updateServerTime();
    }); 

		/* 	var currentDate = '${currentDate}';
			var ranningTime = '${currentTime}';
// 			var svrTAndDate = '${serverDateAndTime}';
			var runningTimeWithSeconds = '';
			var time1 = '${currentTime}';
			var tarray = time1.split(":")
			var th = parseInt(tarray[0]);
			var tt = parseInt(tarray[1]);
			var ts = parseInt(tarray[2]);
			var tht = th;
			var ttt = tt;
			var tst = ts;

			function oneSecondFunction() {
				ts++;
				if(ts == 60){
					tt++;
					ts = ts%60;
					if(tt == 60 ){
						th++;
						tt = tt%60;
					}
				}
				tst = ts; ttt = tt; tht = th;
				if(ts<10) tst = "0"+ts;
				if(tt<10) ttt = "0"+tt;
				if(th<10) tht = "0"+th;
				ranningTime = tht+":"+ttt;
				runningTimeWithSeconds = tht+":"+ttt+":"+tst;
				$("#servertime").html(tht+":"+ttt+":"+tst)	;
			}
			
			setInterval(oneSecondFunction, 1000); */
			/* var urlInput = mainUrl+"/timeCtrl/serverTime";
	   		svrTAndDate = synchronousAjaxCall(urlInput);
			$("#servertime").html(svrTAndDate)	; */
		</script>
<script type="text/javascript">
if (/mobile/i.test(navigator.userAgent)) {
	$('input').prop('readOnly', true);
}
</script>
<script type="text/javascript">
function chnageLocale(languageCode){
       var locationUrl = window.location.pathname;
       locationUrl = locationUrl.substring(5, locationUrl.length);
       locationUrl = locationUrl.replaceAll("/", "@@@");
	   if(locationUrl != null && locationUrl !="" && locationUrl != "undefined"){
		   $('#langCode').val(languageCode);
		   $('#locationUrl').val(locationUrl);
		   var input = mainUrl+"/inlag/changeLocale/"+languageCode+"/"+locationUrl;
		   var result = synchronousAjaxCall(input);
		   if(result == "success")
			   location.reload();
	   }
}
</script>

<!-- clock links -->
<div id="campion"></div>
</body>
</html>