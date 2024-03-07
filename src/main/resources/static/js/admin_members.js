//  window.addEventListener('load', function(){
     
//      let checkbox = document.querySelector('#onlyFreezeMembers');
//      checkbox.addEventListener('change', () => {
//         let form = document.querySelector('form.table-option-list');
//         form.submit();
     
//     });
//  });

$(function() {

    var $onlyFreezeMembers = $('#onlyFreezeMembers').val;
    var $word = $('#word').val;


    $('#onlyFreezeMembers').on('click', function(){
        $.ajax({
            type: 'GET',
            url: '/admin/members/get',
            data: JSON.stringify({onlyFreezeMembers : $onlyFreezeMembers, word : $word}),
            success: function(response) {
                console.log('성공');
                memberList(response);
            }
        });
    });
    
});

function memberList (response){

    var members = response.content;

    var $list = $('.table-list');

    $list.empty();

    members.forEach(function(member){
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