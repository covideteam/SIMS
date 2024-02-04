<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${subjectList}" var="sl">
<option value="${sl.id}">${sl.subjectNo}</option>
</c:forEach>