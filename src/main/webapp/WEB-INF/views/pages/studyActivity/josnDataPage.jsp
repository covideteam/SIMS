<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
${str}
<%-- ${jsonStr} --%>
<%-- <c:out value="${jsonStr}"></c:out> --%>
<script type="text/javascript">
var str = '${jsonStr}';
var d = JSON.parse(str);
var mainUrl = '${mainUrl}';
mainUrl = mainUrl+"/studyActivity/tesingUrl";
alert("Main url is :"+mainUrl);
var stVal = '${str}';
alert("StVal is :"+stVal);
// var result = synchronousAjaxCall(mainUrl+"/studyActivity/saveStudyActivityFromsData/"+'${str}');
$.ajax({
type: "POST",
 url: mainUrl,
    async: true,
    data:stVal,
    success :function(result)
    {
    }
 });
alert('ddddd');

alert("ativity Name : "+d.activity.name);
alert("created By :"+d.activity.createdBy); 

var gList = d.gpList;
if(gList.length > 0){
	for(var i=0; i<gList.length; i++){
		var list = gList[i];
		alert("list value is : "+list.parameterName);
	}
}
</script>
</body>
</html>