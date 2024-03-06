//  window.addEventListener('load', function(){
     
//      let checkbox = document.querySelector('#onlyFreezeMembers');
//      checkbox.addEventListener('change', () => {
//         let form = document.querySelector('form.table-option-list');
//         form.submit();
     
//     });
//  });

$(function (){

    $.ajax({
        type : 'get',
        url : '/admin/members/get',
        data : formData,
        success : function(response){
            $('.table-form').html(response);
        }
    })

});