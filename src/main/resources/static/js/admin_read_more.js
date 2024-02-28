window.addEventListener('load', function(){

    let cancelBtn = document.querySelector('.cancelBtn');

    cancelBtn.addEventListener('click', function(){
        close();
    });

    processComplete();
});

function processComplete(){
    let notifyId = document.querySelector('.notify-number').value;
    let status = document.querySelector('.process-status').value;
    let completeBtn = document.querySelector('.completeBtn');

    completeBtn.addEventListener('click', function(){
        // $.ajax({
        //     url : '/admin/notify/status/change',
        //     type : 'POST',
        //     cotentType : 'application/json',
        //     data : JSON.stringify({notifyId : notifyId, change : status})
            
        // });

        console.log(notifyId);
        console.log(status);
    });
};