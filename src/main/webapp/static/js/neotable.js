var neodiv = $("#neodiv");
var neoTable = $(".neotable");
var totalRecords = $(".neotable tbody tr").length; 
$(".neotable tbody tr").hide();
var pagesize = 10;
var rem = totalRecords / 10;
var totalPages = 1;
var currentPage = 1;
var footer = document.createElement("tfoot");
if (rem === 0) {
    rem = totalRecords;
    currentObject.rem = 1;
}
else {
	totalPages = Math.ceil(rem);
}

var tableFooter = document.createElement("table");
tableFooter.style = "width:100%;"
var trFooter = document.createElement("tr");

var totalRecordsInfoText = document.createElement("td");
if (totalRecords === undefined) {
    totalRecordsInfoText.innerHTML = "Total Record : <b>0<b>";
}
else {
    totalRecordsInfoText.innerHTML = "Total Record : <b>" + totalRecords + "<b>";
}
totalRecordsInfoText.style = "width:25%;"
trFooter.append(totalRecordsInfoText);

var tdPagingFooter = document.createElement("td");
tdPagingFooter.style = "width:50%;text-align:center;"
var prev = document.createElement("a");
prev.className = "fa fa-angle-double-left paging";
prev.addEventListener("click", function next() {
    if (currentPage > 1) {
    	currentPage--;
        
    }
});
tdPagingFooter.append(prev);

var pageInfoText = document.createElement("a");
if (totalRecords === undefined) {
    pageInfoText.innerHTML = "Page <b>0</b> of <b>0<b>";
}
else {
    if (totalRecords === 0) {
        pageInfoText.innerHTML = "Page <b>0</b> of <b>0<b>";
    }
    else {
        pageInfoText.innerHTML = "Page <b>" + currentPage + "</b> of <b>" + totalPages + "<b>";
    }
}
pageInfoText.className = "paging-info";
tdPagingFooter.append(pageInfoText);

var next = document.createElement("a");
next.className = "fa fa-angle-double-right paging";
next.addEventListener("click", function next() {
	var noofrecord = currentPage * pagesize;
	if(noofrecord > totalRecords){
		noofrecord = totalRecords;
	}
    if (totalPages * pagesize < totalRecords) {
    	for(var n = (currentPage * pagesize); n <= noofrecord; n++){
    		
    	}
    	currentPage++;
    }
});
tdPagingFooter.append(next);
trFooter.append(tdPagingFooter);
tableFooter.append(trFooter);

/*PARENT TABLE FOOTER*/
var row = document.createElement("tr");
var column = document.createElement("td");
column.setAttribute("colspan", $("#" + tableName + " thead tr td").length);
column.style = "text-align:center;";
column.append(tableFooter);

row.append(column);
footer.append(row);
$(".neotable tfoot").remove();
$(".neotable").append(footer);







