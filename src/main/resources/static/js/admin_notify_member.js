$(function(){

    $('.cancelBtn').on('click', function(){
        close();
    });

    memberFreeze();
});

function memberFreeze(){

    $('.freezeBtn').on('click', function(){

        var memberId = parseInt($('#memberId').val());
        var freezePeriod = $('#freezePeriod').val();
        var freezeReason = $('#freezeReason').val();

        $.ajax({
            url : '/admin/notify/member/freeze',
            type : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({memberId : memberId, freezePeriod : freezePeriod, freezeReason : freezeReason}),
            success : function(){
                close();
                window.opener.location.reload();
            }
        });
    });
};