function post_comment() {
    var parentId = $("#question_id").val();
    var comment = $("#comment_id").val();
    if (!comment) {
        alert("回复的内容不能为空！！！")
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": parentId,
            "comment": comment,
            "type": 1
        }),
        success: function () {
            alert("提交成功");
            location.reload();
            $("#comment_close").hide();
        },
        dataType: "text",
    });
}

function selectTag(value) {
    var previous = $("#tag").val()
    if (previous){
        $("#tag").val(previous+','+value)
    }else{
        $("#tag").val(value)
    }
}