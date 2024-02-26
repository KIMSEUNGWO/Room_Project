window.addEventListener('load', function(){

     let checkbox = document.querySelector('#withComplete');
     checkbox.addEventListener('change', () => {
        let form = document.querySelector('form.table-option-list');
        form.submit();
     
    });

    let reedMoreBtn = document.querySelectorAll('.reed-moreBtn');

    reedMoreBtn.forEach(function(reedMore){
        reedMore.addEventListener('click', function(){
            // var buttonId = reedMore.getAttribute('id');
            // console.log(buttonId);
            // sendData(buttonId);
            notifyReedMoreOpenPopUp();
        });
    });
    
    // reedMoreBtn.addEventListener('click', function(){
    //     notifyReedMoreOpenPopUp();
    // });

});

function notifyReedMoreOpenPopUp(){
    window.open('/admin/notify/reed_more', '신고 자세히 보기', 'width=500, height=700');
};

// function sendData(buttonId) {
    
//     var xhr =  new XMLHttpRequest();

//     xhr.open("GET", "/reciveData?buttonId=" + buttonId, true);

//     xhr.send();
// }
