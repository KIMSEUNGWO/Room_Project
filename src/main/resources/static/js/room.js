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
    editRoomModalOpen();

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

function editRoomModalOpen() {
    let roomEditModal = document.querySelector('.setting-menu');
    const modal = document.querySelector('.modal');
    const modal_content = document.querySelector('.modal-content');

    roomEditModal.addEventListener('click', function(){
        insertModalSize('modal-edit-room');
        modal_content.innerHTML = editRoomModal();
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
                        '<h5>(2MB)</h5>' +
                    '</div>' +
                    '<input type="file" name="notifyImage" id="notifyImage" accept="image/*" multiple>' +
                '</div>' +
                '<div class="button-box">' +
                    '<button type="button" id="do-notify">신고하기</button>' +
                    '<button type="button" id="cancel-notify">취소</button>' +
                '</div>' +
            '</div>';
};

function editRoomModal() {
    return  '<div class="modal-wrapper">' +
                '<div class="create-room-image-box">' +
                    '<button type="button" id="img">' +
                        '<img src="/images/room_profile/basic-room-profile.jpg" width="100%" height="100%" id="roomProfile" name="roomProfile">' +
                        '<input type="file" name="roomImage" id="roomImage" accept="image/*">' +
                        '<svg width="36" height="36" viewBox="0 0 36 36" fill="none" xmlns="http://www.w3.org/2000/svg" class="g68VV5Ghc0ymGpbFWhEx"><circle cx="18" cy="18" r="18" fill="#000"></circle><path d="M11.375 22.658v2.969h2.969l8.756-8.756-2.97-2.969-8.755 8.756zm14.02-8.083a.788.788 0 000-1.116l-1.852-1.852a.788.788 0 00-1.116 0l-1.45 1.448 2.97 2.97 1.448-1.45z" fill="#fff"></path></svg>' +
                    '</button>' +
                '</div>' +
                '<div class="input-box">' +
                    '<h4>방 제목</h4>' +
                    '<input type="text" name="title" id="title" maxlength="10">' +
                    '<span class="msg disabled m-title"></span>' +
                '</div>' +
                '<div class="input-box">' +
                    '<h4>소개글</h4>' +
                    '<input type="text" name="intro" id="intro" maxlength="50">' +
                    '<span class="msg disabled m-intro"></span>' +
                '</div>' +
                '<div class="input-wrap">' +
                    '<h4>인원 수' +
                        '<input type="radio" name="max" id="m2" value="2">' +
                        '<input type="radio" name="max" id="m3" value="3">' +
                        '<input type="radio" name="max" id="m4" value="4" checked>' +
                        '<input type="radio" name="max" id="m5" value="5">' +
                        '<input type="radio" name="max" id="m6" value="6">' +
                    '</h4>' +
                    '<div class="radio-wrap max-wrap">' +
                        '<label for="m2" aria-selected="false">2명</label>' +
                        '<label for="m3" aria-selected="false">3명</label>' +
                        '<label for="m4" aria-selected="true">4명</label>' +
                        '<label for="m5" aria-selected="false">5명</label>' +
                        '<label for="m6" aria-selected="false">6명</label>' +
                    '</div>' +
                '</div>' +
                '<div class="input-wrap">' +
                    '<h4>공개여부' +
                        '<input type="radio" name="public" id="public" value="PUBLIC" checked>' +
                        '<input type="radio" name="public" id="private" value="PRIVATE">' +
                    '</h4>' +
                    '<div class="radio-wrap">' +
                        '<label for="public" aria-selected="true">공개방</label>' +
                        '<label for="private" aria-selected="false">비공개방</label>' +
                    '</div>' +
                '</div>' +
                '<div class="input-box password-box disabled">' +
                    '<h4>비밀번호 설정</h4>' +
                    '<input type="password" name="room-password" id="room-password" placeholder="비밀번호 4~6자리를 설정해주세요." minlength="4" maxlength="6">' +
                    '<span class="msg disabled m-private-password"></span>' +
                '</div>' +
                '<span class="msg disabled m-max"></span>' +
                '<div class="input-wrap tag-wrap">' +
                    '<h4>태그</h4>' +
                    '<div class="tag-list">' +
                        '<div class="tag-add-box">' +
                            '<span>#</span>' +
                            '<input type="text" name="tag-add" id="tag-add" placeholder="태그입력" maxlength="10">' +
                        '</div>' +
                    '</div>' +
                '</div>' +
            '</div>' +
            '<div class="buttons">' +
                '<button type="button" id="room-cancel">취소</button>' +
                '<button type="button" id="room-edit">저장</button>' +
            '</div>';
};