window.addEventListener('load', () => {

    const message = document.querySelector('#message');

    message.addEventListener('keyup', () => textareaResize(message));
    message.addEventListener('paste', () => textareaResize(message));

    memberOptionMenuOpen();
    memberOptionMenuClose();
    memberNotify();
})

function textareaResize(message) {
    message.style.height = 'auto';
    message.style.height = (message.scrollHeight) + 'px';
}

function memberOptionMenuOpen(){
    let memberMoreBtn = document.querySelector('.member-more');
    let memberOptionMenu = document.querySelector('.member-option-menu');

    memberMoreBtn.addEventListener('click', function(){
        console.log('remove');
       memberOptionMenu.classList.remove('disabled'); 
    });
}

function memberOptionMenuClose() {
    let memberOptionMenu = document.querySelector('.member-option-menu');
    let memberNotifyBox = document.querySelectorAll('.member-notify-box, .member-notify-box *');

    memberNotifyBox.forEach(function(element){
        element.addEventListener('mouseout', function(){
            console.log('add11111')
            memberOptionMenu.classList.add('disabled');
        });
    });
}

function memberNotify() {
    let memberOptionMenu = document.querySelector('.member-option-menu');
    let memberNotifybtn = document.querySelector('.member-notify-box');

    memberNotifybtn.addEventListener('click', function(){
        console.log('add22222')
        memberOptionMenu.classList.add('disabled');
    });
}