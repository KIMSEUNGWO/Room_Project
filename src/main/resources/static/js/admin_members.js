//
//window.addEventListener('load', function(){
//
//    let checkBox = document.getElementById('onlyFreezeMembers');
//
//    // checkBox.addEventListener('change', function(){
//    //     if(this.checked){
//    //        location.href='/admin/FreezeMembers/get';
//    //     } else {
//    //         location.href='/admin/members/get';
//    //     }
//    // });
//
//    $('#onlyFreezeMembers').change(function(){
//        if($(this).is(":checked")) {
//            $.ajax({
//                url: "/admin/FreezeMembers/get",
//                method: "GET",
//                success: function(response){
//                    $('.entity').html(response);
//                }
//            });
//        };
//    });
//});

// function optionQuery(option){

//     let url = "/admin/members/get?query=" + option;

//     var xhr = new XMLHttpRequest();
//             xhr.open("GET", url, true);
//             xhr.onreadystatechange = function() {
//                 if(xhr.readyState == 4 && xhr.status == 200){
//                     console.log(xhr.responseText);
//                 }
//             };

//     xhr.send();
// }

// function sendXML(checkValue){
//     var checkValue = "unChecked";
//     var xhr = new XMLHttpRequest();
//             xhr.open("GET", checkValue, true);
//             xhr.onreadystatechange = function() {
//                 if(xhr.readyState == 4 && xhr.status == 200){
//                     console.log(xhr.responseText);
//                 }
//             };

//         xhr.send();
// }

// let isChecked = document.getElementById('onlyFreezeMembers').checked;

//     let url = "/admin/members/get?query=option1";

//     if(isChecked) {
//         url = "/admin/members/get?query=option2";
//     }
//     if(!isChecked){
//         url = "/admin/members/get?query=option1";
//     }

//     var xhr = new XMLHttpRequest();
//             xhr.open("GET", url, true);
//             xhr.onreadystatechange = function() {
//                 if(xhr.readyState == 4 && xhr.status == 200){
//                     console.log(xhr.responseText);
//                 }
//             };

//     xhr.send();