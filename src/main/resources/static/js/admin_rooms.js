window.addEventListener('load', function(){

    roomOptionOpen();
    roomOptionClose();
    deleteRoom();

});

function roomOptionOpen(){
    let optionMore = document.querySelector('.option-more');
    let optionMenu = document.querySelector('.option-menu');

    optionMore.addEventListener('click', function(e){
        if (e.target.classList.contains('option-more')) {
            optionMenu.classList.remove('disabled');
        }
    });

    

};

function roomOptionClose(){
    let optionMenu = document.querySelector('.option-menu');
    let container = document.querySelector(':not(.option-more):not(.option-more *):first-of-type');

    container.addEventListener('click', function(event){
        let s = event.target;

        if(!s.classList.contains('option-more') && !s.classList.contains('option-exit')){
            optionMenu.classList.add('disabled');
        }
    })
    

    
};

function deleteRoom(){
    let optionMenu = document.querySelector('.option-menu');
    let optionExit = document.querySelector('.option-exit');

    optionExit.addEventListener('click', function(){
        optionMenu.classList.add('disabled');
    });
}