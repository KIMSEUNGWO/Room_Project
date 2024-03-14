$(function(){
    var onlyFreezeMembers = $('#onlyFreezeMembers');
    var word = $('#word');
    var searchBtn = $('#searchBtn');

    onlyFreezeMembers.on('change', function(){
        sendRequest();
    });

    word.on('keydown', function(e){
        if(e.key == "Enter"){
            sendRequest();
        }
    });

    searchBtn.on('click', function(){
        sendRequest();
    });
});

function sendRequest(page) {

    var onlyFreezeMembers = $('#onlyFreezeMembers').is(':checked');
    var word = $('#word').val();

    $.ajax({
        type: 'GET',
        url: '/admin/members/get',
        data: {
            word : word,
            onlyFreezeMembers : onlyFreezeMembers,
            page : page
        },
        success: function(response) {
            console.log(response.number + "ㅎ");
            console.log(response.totalPages)
            console.log('word=' + word);
            memberList(response);
            paging(response.totalPages, response.number);

            // History API를 사용하여 URL 변경
            if(page==null){
                var newUrl = '/admin/members/get?page=1';
            } else {
                var newUrl = '/admin/members/get?page=' + page;
            }
            
            if(onlyFreezeMembers==true){
                newUrl += '&onlyFreezeMembers=' + onlyFreezeMembers;
            }

            if(word!=""){
                newUrl += '&word=' + word;
            }
            history.pushState({ path: newUrl }, '', newUrl);
        }
    });
}

function memberList(response) {
    var members = response.content;

    var $list = $('.table-list');
    $list.empty();
    members.forEach(function(member) {
        var $entity = $(`<div class="entity">
            <div class="email">${member.memberAccount}</div>
            <div class="name">${member.memberName}</div>
            <div class="nickname">${member.memberNickname}</div>
            <div class="phone">${member.phone}</div>
            <div class="registerdate">${member.memberCreateDate}</div>
            <div class="notifycount">${member.memberNotifyCount}</div>
            <div class="social">${member.socialType}</div>
            <div class="status">${member.memberStatusEnum}</div>
        </div>`);
        $list.append($entity);
    });
};

function paging(totalPages, currentPage) {
    var $pageBox = $('.page-box');
    $pageBox.empty();

    var pageBtnStart = $(`<div class="pagination">`);

    var pageBtnMid = $(`<div id="page">`);

    if (currentPage > 1) {
        var pageBtnLastLeft = $(`<div class="buttons">
                                    <button type="button" id="last-left" class="page-num" value="1">
                                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M41.4 233.4c-12.5 12.5-12.5 32.8 0 45.3l160 160c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L109.3 256 246.6 118.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0l-160 160zm352-160l-160 160c-12.5 12.5-12.5 32.8 0 45.3l160 160c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L301.3 256 438.6 118.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0z"/></svg>
                                    </button>
                                </div>`);
        pageBtnStart.append(pageBtnLastLeft);
    }
    if(currentPage > 0){
        var pageBtnLeft = $(`<div class="buttons">
                                <button type="button" id="left" class="page-num" value="${currentPage}">
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 320 512"><path d="M41.4 233.4c-12.5 12.5-12.5 32.8 0 45.3l160 160c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L109.3 256 246.6 118.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0l-160 160z"/></svg>
                                </button>
                            </div>`);
        pageBtnStart.append(pageBtnLeft);
    }

    for (var i = 1; i <= totalPages; i++) {
        var button = $('<button/>', {
            type: 'button',
            class: 'page-num',
            val: i,
            text: i
        });
        pageBtnMid.append(button);
    }

    if (currentPage+1 < totalPages) {
        var pageRightBtn = $(`<div class="buttons">
                                <button type="button" id="right" class="page-num" value="${currentPage+2}">
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 320 512"><path d="M278.6 233.4c12.5 12.5 12.5 32.8 0 45.3l-160 160c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3L210.7 256 73.4 118.6c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0l160 160z"/></svg>
                                </button>
                            </div>`);
        pageBtnMid.append(pageRightBtn);
    }
    
    if(currentPage+2 < totalPages){
        var pageLastRight = $(`<div class="buttons">
                                <button type="button" id="last-right" class="page-num" value="${totalPages}">
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M470.6 278.6c12.5-12.5 12.5-32.8 0-45.3l-160-160c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L402.7 256 265.4 393.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0l160-160zm-352 160l160-160c12.5-12.5 12.5-32.8 0-45.3l-160-160c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L210.7 256 73.4 393.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0z"/></svg>
                                </button>
                            </div>`);
        pageBtnMid.append(pageLastRight);
    }

    
    $pageBox.append(pageBtnStart.append(pageBtnMid));

    $('.page-num').on('click', function(){
        var page =  parseInt($(this).val());
        sendRequest(page);
    });


};


