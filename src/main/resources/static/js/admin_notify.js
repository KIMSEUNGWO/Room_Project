window.addEventListener('load', function(){

     let checkbox = document.querySelector('#withComplete');
     checkbox.addEventListener('change', () => {
        let form = document.querySelector('form.table-option-list');
        form.submit();
     
    });

    let reedMoreBtn = document.querySelector('.reed-moreBtn');

    
    reedMoreBtn.addEventListener('click', function(){
        notifyReedMoreOpenPopUp();
    });

});

function notifyReedMoreOpenPopUp(){
    window.open('/admin/notify/reed_more', '신고 자세히 보기', 'width=500, height=700');
}