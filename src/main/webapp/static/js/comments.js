var comments = [];
var isCommentsValid = true;
var autoSave = false;var isReview = false;
function getProjectComments(url,id) {
    $.ajax({
        url: $("#mainUrl").val() + '/' + url, //'/studydesign/discrepencies',
        data: { tabId: tabId, value: commentId },
        type: 'GET',
        success: function (coms) {
            if (typeof coms === "string") {
                coms = JSON.parse(coms);
            }
            comments = coms.Item1;
            for (var c = 0; c < comments.length; c++) {
                comments[c]["IsLocal"] = false;
                if (comments[c]["respondedBy"] !== undefined && comments[c]["respondedBy"] !== null) {
                    $(".comments-messages").append("<div class='comment-box-holder'><div class='comment-box message-comment'>Commented By:" + comments[c]["commentedBy"] + " , " + comments[c]["comments"] + "<br/>" + comments[c]["commentedOn"] + "</div></div>");
                    $(".comments-messages").append("<div class='comment-box-holder'><div class='comment-box message-comment response-box'>Responded By:" + comments[c]["respondedBy"] + " , " + comments[c]["Response"] + "<br/>" + comments[c]["RespondedOn"] + "</div></div>");
                }
                else {
                    isCommentsValid = false;
                    $(".comments-messages").append("<div class='comment-box-holder'><div class='comment-box message-comment'>Commented By:" + comments[c]["commentedBy"] + " , " + comments[c]["comments"] + "<br/>" + comments[c]["commentedOn"] + "</div><i data-id='" + comments[c]["id"] + "' class='fa fa-reply comments-reply-icon'></i></div>");
                }
            }
        }
    });
}


function loadComments(coms) {
	comments = coms;
	for (var c = 0; c < comments.length; c++) {
        comments[c]["IsLocal"] = false;
        if (comments[c]["respondedBy"] !== undefined && comments[c]["respondedBy"] !== null) {
            $(".comments-messages").append("<div class='comment-box-holder'><div class='comment-box message-comment'>Commented By:" + comments[c]["commentedBy"] + " , " + comments[c]["comment"] + "<br/>" + comments[c]["commentedOn"] + "</div></div>");
            $(".comments-messages").append("<div class='comment-box-holder'><div class='comment-box message-comment response-box'>Responded By:" + comments[c]["respondedBy"] + " , " + comments[c]["response"] + "<br/>" + comments[c]["respondedOn"] + "</div></div>");
        }
        else {
            isCommentsValid = false;
            if(isReview){
            	$(".comments-messages").append("<div class='comment-box-holder'><div class='comment-box message-comment'>Commented By:" + comments[c]["commentedBy"] + " , " + comments[c]["comment"] + "<br/>" + comments[c]["commentedOn"] + "</div><i data-id='" + comments[c]["commentId"] + "' class='fa fa-trash comments-trash-icon'></i></div>");            	
            }
            else{
            	$(".comments-messages").append("<div class='comment-box-holder'><div class='comment-box message-comment'>Commented By:" + comments[c]["commentedBy"] + " , " + comments[c]["comment"] + "<br/>" + comments[c]["commentedOn"] + "</div><i data-id='" + comments[c]["commentId"] + "' class='fa fa-reply comments-reply-icon'></i></div>");	
            }
        }
    }
}


/*$(document.body).append("<a class='chat-icon comments'><img src='/images/comments.png' style='width:80px;'/></a>");*/
function commentsDraggable(isreview) {
	isReview = isreview;
	$(document.body).append("<a class='comments-icon'><img src='/SIMS/static/images/comments.png' style='width:80px;'/></a>");
    $('.comments-holder').remove();
    $(document.body).append("<div class='comments-holder'><div class='comments'><div class='comments-top'><div class='comments-icons'><a href='javascript:void(0);'><i class='fa fa-minus'></i></a></div></div><div class='comments-messages'></div><div class='comments-input-holder'></div></div></div>")
    $(".comments-icon,.comments-holder").draggable();
}
$(document).on("click", ".comments-icon", function (e) {
	if ($(".comments-input-holder").find("#taCommentsInput").length === 0)
        $(".comments-input-holder").append("<textarea class='comments-input' id='taCommentsInput' placeholder='Enter comment'></textarea><input type='submit' value='ADD' id='addComments' class='message-send'/>");

    $(".comments-icon").hide('fast', function () {
        $('.comments').animate({
            opacity: 1,
            height: "400",
            width: "400"
        }, 500, function () {

        });
    });
});

$(document).on("click", ".view-comments-icon", function (e) {
    $(".view-comments-icon").hide('fast', function () {
        $('.comments').animate({
            opacity: 1,
            height: "400",
            width: "400"
        }, 500, function () {

        });
    });
});

$(document).on("click", ".comments-reply-icon", function (e) {
    $(this).hide();
    var commentId = $(this).data("id");
    $("<div class='comment-box-holder'><textarea class='comments-input' id='taCommentsInput' placeholder='Enter response'></textarea><i id='addResponse' class='fa fa-check comments-right' data-id='" + commentId + "'/><i id='cancelResponse' class='fa fa-times comments-cancel ds-ml-5' data-id='" + commentId + "'/></div>").insertAfter($(this).closest(".comment-box-holder"));
});

$(document).on("click", ".comments-right", function (e) {
    var commentsHolder = $(this).closest(".comment-box-holder");
    if ($.trim($("#taCommentsInput").val()).length === 0) {
        $.alert({
            title: 'Alert!',
            content: 'Add response',
        });
    }
    
    var id = $(this).data("id");
    var response = $("#taCommentsInput").val();
    var comment = find("id", id, comments);
    comment["response"] = response;
    var responseExists = true;
    for (var c = 0; c < comments.length; c++) {
        if ($.trim(comments[c]["response"]).length === 0) {
            responseExists = false;
        }
    }
    
    if(!comment.IsLocal){
    	if(typeof commentResponseCallback !== 'undefined'){
    		commentResponseCallback(id,response);
    	}
    }
    
    isCommentsValid = responseExists;
    $(commentsHolder).empty();
    $(commentsHolder).append("<div class='comment-holder'><div class='comment-box message-comment response-box'>" + response + "</div><i data-id='" + id + "' class='fa fa-pencil ds-ml-5 comments-edit-icon'></i></div>");
});

$(document).on("click", ".comments-cancel", function (e) {
    var id = $(this).data("id");
    var comment = find("commentId", id, comments);
    if (comment === null || comment === undefined) {
        $("[data-id='" + id + "']").show();
        $(this).closest(".comments-input-holder").remove();
    }
    else {
        var commentsHolder = $(this).closest(".comments-input-holder");
        $(commentsHolder).empty();
        $(commentsHolder).append("<div class='comment-holder'><div class='comment-box message-comment response-box'>" + comment["response"] + "</div><i data-id='" + id + "' class='fa fa-pencil comments-edit-icon'></i></div>");
    }
});

$(document).on("click", ".comments-edit-icon", function (e) {
    var commentsHolder = $(this).closest(".comment-box-holder");
    var id = $(this).data("id");
    var comment = find("commentId", id, comments);
    $(commentsHolder).empty();
    $(commentsHolder).append("<textarea class='comments-input' id='taCommentsInput' placeholder='Enter response'>" + comment["response"] + "</textarea><i id='addResponse' class='fa fa-check comments-right' data-id='" + id + "'/><i id='cancelResponse' class='fa fa-times comments-cancel ds-ml-5' data-id='" + id + "'/>");
});
var commentsCallBack = null;
function addComment(commentId,comment){
	 comments.push({ 'comment': comment, 'commentId': commentId, 'IsLocal': false });
     $(".comments-messages").append("<div class='comment-box-holder'><div class='comment-box message-comment'>" 
    		 + comment + "</div><i data-id='" + commentId + "' class='fa fa-trash comments-trash-icon'></i></div>");
}
$(document).on("click", "#addComments", function (e) {
	if(commentsCallBack !== null){
		commentsCallBack();
	}
	else if ($.trim($("#taCommentsInput").val()).length === 0 && commentId === "") {
        $.alert({
            title: 'Info',
            content: 'Enter comments',
        });
    }
    else {
        var randomNumber = getRandomNumber();
        comments.push({ 'comment': $("#taCommentsInput").val(), 'id': randomNumber, 'IsLocal': true });
        $(".comments-messages").append("<div class='comment-box-holder'><div class='comment-box message-comment'>" + $("#taCommentsInput").val() + "</div><i data-id='" + randomNumber + "' class='fa fa-trash comments-trash-icon'></i></div>");
        $("#taCommentsInput").val("");
    }
});

$(document).on("click", ".comments-trash-icon", function (e) {
    var index = 0;
    for (var c = 0; c < comments.length; c++) {
        if (comments[c]["id"] === $(this).data("id")) {
            index = c;
            break;
        }
    }
    
    if(!comments[index]["IsLocal"]){
    	if(typeof commentDeleteCallback !== "undefined"){
    		commentDeleteCallback(comments[index]["commentId"]);
    	}
    }
    comments.splice(index, 1);
    $(this).closest(".comment-box-holder").remove();
});

$(document).on('click', '.fa-minus', function () {
    /*$(this).closest('.chatbox').toggleClass('chatbox-min');*/
    $(this).closest('.comments').animate({
        opacity: 0,
        height: "0",
        width: "0"
    }, 500, function () {
        $(".comments-icon,.view-comments-icon").show();
    });
});

$(document).on('click', '.fa-close', function () {
    $(this).closest('.comments').hide();
});