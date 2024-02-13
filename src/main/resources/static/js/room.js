window.addEventListener('load', () => {

    const message = document.querySelector('#message');
    
    message.addEventListener('keyup', () => textareaResize(message));
    message.addEventListener('paste', () => textareaResize(message));

    const modalExitList = document.querySelector('.modal-exit');
    if (modalExitList != null) {
        modalExitList.addEventListener('click', () => {
            modalExit();
        })
    }

    memberOptionMenuOpen();
    memberOptionMenuClose();
    notifyModalOpen();

    roomSettingMenuOpen();
    roomSettingMenuClose();

});

function textareaResize(message) {
    message.style.height = 'auto';
    message.style.height = (message.scrollHeight) + 'px';
};

function roomSettingMenuOpen() {
    let roomSettingBtn = document.querySelector('.room-setting');
    let settingMenu = document.querySelector('.setting-menu');

    roomSettingBtn.addEventListener('click', function(e){
        if(e.target.classList.contains('room-setting')){
            settingMenu.classList.remove('disabled');
        };
    });
};

function roomSettingMenuClose() {
    let settingMenu = document.querySelector('.setting-menu');
    let element = document.querySelector(':not(.room-setting):not(.room-setting *):first-of-type');

    element.addEventListener('click', function(e){
        let s = e.target;

        if(!s.classList.contains('room-setting') 
            && !s.classList.contains('setting-menu')
            && !s.classList.contains('default-setting')
            && !settingMenu.classList.contains('disabled')){
                settingMenu.classList.add('disabled');
        }
    });
};

function memberOptionMenuOpen(){
    let memberMoreBtn = document.querySelector('.member-more');
    let memberOptionMenu = document.querySelector('.member-option-menu');

    memberMoreBtn.addEventListener('click', function(e){
        if(e.target.classList.contains('member-more')) {
            memberOptionMenu.classList.remove('disabled');
        }
    });
};

function memberOptionMenuClose() {
    let memberOptionMenu = document.querySelector('.member-option-menu');
    let element = document.querySelector(':not(.member-more):not(.member-more *):first-of-type');

    element.addEventListener('click', function(e){
        let s = e.target;

        if(!s.classList.contains('member-more') 
            && !s.classList.contains('member-notify-box')
            && !memberOptionMenu.classList.contains('disabled')){
            memberOptionMenu.classList.add('disabled');
        }
    })
};

function notifyModalOpen(){
    let notifyBox = document.querySelector('.member-notify-box');
    const modal = document.querySelector('.modal');
    const modal_content = document.querySelector('.modal-content');

    notifyBox.addEventListener('click', function(){
        insertModalSize('modal-notify');
        modal_content.innerHTML = createNotify();
        modal.classList.remove('disabled');
    });
};

function modalExit() {
    const modal = document.querySelector('.modal');
    const modal_content = document.querySelector('.modal-content');

    modal.classList.toggle('disabled');
    setTimeout(() => {
        modal_content.innerHTML = '';
        initModalSize();
    }, 200);
};

function insertModalSize(className) {
    initModalSize();
    const modal_wrap = document.querySelector('.modal-wrap');
    modal_wrap.classList.add(className);
};

function initModalSize() {
    const modal_wrap = document.querySelector('.modal-wrap');
    modal_wrap.classList.forEach(cl => {
        if (cl != 'modal-wrap') {
            modal_wrap.classList.remove(cl);
        }
    })
};

function counter() {
    let content = document.querySelector('.notify-content');

    if (content.value.length > 1000) {
        content.value = content.value.substring(0, 1000);
    }
    let count = document.querySelector('.count');
    count.textContent = content.value.length;

}

function createNotify() {
    return '<div class="notify">' +
                '<div class="benotifiedmember-box">' +
                    '<h4>신고 대상</h4>' +
                    '<input type="text" class="benotifiedmember-name" value="오승찬"></input>' +
                '</div>' +
                '<div class="notify-reason-box">' +
                    '<h4>신고 사유</h4>' +
                    '<select class="notify-reason">' +
                        '<option value="" selected="selected">사유를 선택해주세요</option>' +
                        '<option value="홍보/상업적 광고 등 (스팸 메세지 등)">홍보/상업적 광고 등 (스팸 메세지 등)</option>' +
                        '<option value="고의적인 대화방해 (텍스트 도배 등)">고의적인 대화방해 (텍스트 도배 등)</option>' +
                        '<option value="미풍양속을 해치는 행위 (음란/욕설 등)">미풍양속을 해치는 행위 (음란/욕설 등)</option>' +
                        '<option value="운영자 사칭">운영자 사칭</option>' +
                        '<option value="개인정보 침해, 아이디 도용">개인정보 침해, 아이디 도용</option>' +
                        '<option value="기타">기타</option>' +
                    '</select>' +
                '</div>' +
                '<div class="notify-content-box">' +
                    '<h4>신고 내용</h4>' +
                    '<span class="count-box">(<span class="count">0</span>/1000)</span>' +
                    '<textarea class="notify-content" onkeydown="counter();" name="notify-content" id="notify-content" cols="30" rows="10">sdfasdf</textarea>' +
                '</div>' +
                '<div class="notify-image-box">' +
                    '<div class="notify-image-text">' +
                        '<h4>파일 첨부</h4>' +
                        '<h5>(1MB)</h5>' +
                    '</div>' +
                    '<input type="file" name="notifyImage" id="notifyImage" accept="image/*" multiple>' +
                '</div>' +
                '<div class="button-box">' +
                    '<button type="button" id="do-notify">신고하기</button>' +
                    '<button type="button" id="cancel-notify">취소</button>' +
                '</div>' +
            '</div>';
};