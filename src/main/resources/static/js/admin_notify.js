window.addEventListener('load', function(){
    let reedMoreBtn = document.querySelector('.reed-moreBtn');

    
    reedMoreBtn.addEventListener('click', function(){
        notifyReedMoreOpenPopUp();
    });

});

function notifyReedMoreOpenPopUp(){
    window.open('notify_reed_more.html', '신고 자세히 보기', 'width=500, height=700');
}