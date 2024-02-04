<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-30">
          <div class="" style="width:100%;">
          		<c:if test="${pageMessage != null && pageMessge != ''}">
          			<script type="text/javascript">
          			    $(function(){
          			    	bs4Toast.success('${success}', '${pageMessage}');
          			    });
          			</script>
          		</c:if>
          		<c:if test="${pageError != null && pageError != ''}">
         		     <script type="text/javascript">
          			    $(function(){
          			    	bs4Toast.error('${error}', '${pageError}');
          			    });
          			</script>
          		</c:if>
        		<c:if test="${pageWarning != null && pageWarning != '' }">
       		      	<script type="text/javascript">
        			    $(function(){
        			    	bs4Toast.warning('warning', '${pageWarning}');
        			    });
        			</script>
        		</c:if>
        		<c:if test="${pageInfo != null && pageInfo != '' }">
       		      	<script type="text/javascript">
        			    $(function(){
        			    	bs4Toast.primary('warning', '${pageInfo}');
        			    });
        			</script>
        		</c:if>
             </div>
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->
