window.addEventListener('load', function(){

    let cancelBtn = document.querySelector('.cancelBtn');

    cancelBtn.addEventListener('click', function(){
        close();
    });

    processComplete();
});

function processComplete(){
    let notifyId = document.querySelector('.notify-number').value;
    let completeBtn = document.querySelector('.completeBtn');

    completeBtn.addEventListener('click', function(){
        console.log('aaaa');
    });
};