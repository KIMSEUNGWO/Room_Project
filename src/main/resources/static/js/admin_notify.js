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
            notifyReedMoreOpenPopUp(notifyId);
        });
    });

    let memberInfoPop = document.querySelectorAll('.memberInfoPop');

    memberInfoPop.forEach(function(pop){
        pop.addEventListener('click', function(){
            var account = pop.getAttribute('id');
            memberInfoOpenPopUp(account);
        });
    });

});

function notifyReedMoreOpenPopUp(notifyId){
    window.open('/admin/notify/read_more?notifyId=' + notifyId, '신고 자세히 보기', 'width=500, height=750');
};

function memberInfoOpenPopUp(account){
    window.open('/admin/notify/member_info?account=' + account, '멤버 정보', 'width=600, height=750');
}