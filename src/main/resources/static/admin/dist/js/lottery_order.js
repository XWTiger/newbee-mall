/*$(
    function () {
        var content = $(this).text();
        switch (content) {
            case 'ww':
                $(this).replaceWith("胜胜");
                break;
            case 'wd':
                $(this).replaceWith("胜平");
                break;
            case 'wl':
                $(this).replaceWith("胜负");
                break;
            case 'dw':
                $(this).replaceWith("平胜");
                break;
            case 'dd':
                $(this).replaceWith("平平");
                break;
            case 'dl':
                $(this).replaceWith("平负");
                break;
            case 'lw':
                $(this).replaceWith("负胜");
                break;
            case 'ld':
                $(this).replaceWith("负平");
                break;
            case 'll':
                $(this).replaceWith("负负");
                break;
        }
    }
);*/
$(document).ready(function(){
     $("[id=harlf-court-detail]").each(function () {
         console.log("=============>");
        switch (this.innerText) {
            case 'ww':
                this.replaceWith("胜胜");
                break;
            case 'wd':
                this.replaceWith("胜平");
                break;
            case 'wl':
                this.replaceWith("胜负");
                break;
            case 'dw':
                this.replaceWith("平胜");
                break;
            case 'dd':
                this.replaceWith("平平");
                break;
            case 'dl':
                this.replaceWith("平负");
                break;
            case 'lw':
                this.replaceWith("负胜");
                break;
            case 'ld':
                this.replaceWith("负平");
                break;
            case 'll':
                this.replaceWith("负负");
                break;
        }
    });



});

$('#fileupload-new-button').click(function () {
    $('#fileupload').click();
});
$(function () {
    var lotteryNo = $('#lottery-order-no').text();
    $('#fileupload').fileupload({
        url: "/admin/upload/files?lotteryOrderId=" + lotteryNo +"&token=" + sessionStorage.getItem("token"),
        dataType: 'json',

// 上传完成后的执行逻辑
        done: function (e, data) {
            window.location.reload();
        },
// 上传过程中的回调函数
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('.bar').text(progress + '%');
            $('#progress.bar').css(
                'width',
                progress + '%'
            );
        }
    });
});

function noticeUser() {
    swal({
        title: "推送打票成功",
        text: "确认要执行通知操作吗?",
        icon: "success",
        buttons: true,
        dangerMode: true
    })
}
