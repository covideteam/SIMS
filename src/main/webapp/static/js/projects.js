$(document).on("click",".edit,.review",function(e){
	e.preventDefault();
	$(".loader").show();
	var id = $(this).data("id");
	var url = $(this).data("url");
	$.ajax({
		url:$("#mainUrl").val() +'/administration/getCsrfToken',
		type:'GET',
		success:function(data){
			var f = document.createElement("form");
			f.setAttribute('method',"post");
			f.setAttribute('action', url);
			
			var i = document.createElement("input");
			i.type = "hidden";
			i.name = "id";
			i.value = id;
			f.appendChild(i);
			
			var ct = document.createElement("input");
			ct.type = "hidden";
			ct.name = data.parameterName;
			ct.value = data.token;
			f.appendChild(ct);
			
			$("body").append(f);
			f.submit();
		},
		error:function(er){
			debugger;
		}
	});
});

$(document).on("click",".newProject",function(e){
	e.preventDefault();
	$(".loader").show();
	var id = $(this).data("id");
	var url = $("#mainUrl").val() + $(this).data("url");
	var f = document.createElement("form");
	f.setAttribute('method',"get");
	f.setAttribute('action', url);
	$("body").append(f);
	f.submit();
});


