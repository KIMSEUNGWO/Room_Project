window.addEventListener('load', function(){

    let cancelBtn = document.querySelector('.cancelBtn');

    cancelBtn.addEventListener('click', function(){
        close();
    });

    processComplete();
});

function processComplete(){
    
    let completeBtn = document.querySelector('.completeBtn');

    completeBtn.addEventListener('click', function(){
        let notifyId = document.querySelector('.notify-number').value;
        let status = document.getElementById('process-status').value;

        $.ajax({
            url : '/admin/notify/status/change',
            type : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({notifyId : notifyId, status : status}),
            success : function(){
                console.log('요청성공');
            }
        });
    });
};


