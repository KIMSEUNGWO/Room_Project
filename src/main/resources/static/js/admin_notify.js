window.addEventListener('load', function(){

     let checkbox = document.querySelector('#withComplete');
     checkbox.addEventListener('change', () => {
        let form = document.querySelector('form.table-option-list');
        form.submit();
     
    });

    let reedMoreBtn = document.querySelectorAll('.reed-moreBtn');

    reedMoreBtn.forEach(function(reedMore){
        reedMore.addEventListener('click', function(){
            var notifyId = reedMore.getAttribute('id');
            // console.log(buttonId);
            // sendData(buttonId);
            notifyReedMoreOpenPopUp(notifyId);
        });
    });
    
    // reedMoreBtn.addEventListener('click', function(){
    //     notifyReedMoreOpenPopUp();
    // });

});

function notifyReedMoreOpenPopUp(notifyId){
    window.open('/admin/notify/reed_more?notifyId=' + notifyId, '신고 자세히 보기', 'width=500, height=700');
};

// function sendData(buttonId) {
    
//     var xhr =  new XMLHttpRequest();

//     xhr.open("GET", "/reciveData?buttonId=" + buttonId, true);

//     xhr.send();
// }
