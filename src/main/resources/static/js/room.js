window.addEventListener('load', () => {

    const message = document.querySelector('#message');
    
    message.addEventListener('keydown', (e) => {
        if (e.key === 'Enter' && !e.shiftKey) {
            sendMessage();
            message.value = '';
        }
        textareaResize(message);
    })
    message.addEventListener('keyup', (e) => {
        if (e.key === 'Enter' && !e.shiftKey) {
            message.value = '';
        }
        textareaResize(message);
        
    });

    const sendBtn = document.querySelector('#send');
    sendBtn.addEventListener('click', () => sendMessage());

    message.addEventListener('paste', () => textareaResize(message));

    const modalExitList = document.querySelector('.modal-exit');
    if (modalExitList != null) {
        modalExitList.addEventListener('click', () => {
            modalExit();
        })
    }

    const room_notice = document.querySelector('.room-notice'); // 공지사항 접기 펼치기
    room_notice.addEventListener('click', (e) => {
        if (e.target.classList.contains('folder')) {
            const folder = e.target;
            let notice = document.querySelector('.room-notice-content');
            if (!notice.classList.contains('display')) {
                folder.querySelector('svg').style.rotate = '180deg';
            } else {
                folder.querySelector('svg').style.rotate = '0deg';
            }
            notice.classList.toggle('display');
        }
    })

    memberOptionMenuOpen();
    memberOptionMenuClose();
    notifyModalOpen();

    const modal = document.querySelector('.modal');
    const modal_content = document.querySelector('.modal-content');



    modal_content.addEventListener('click', (e) => {
        let target = e.target;
        if (target.id == 'room-notice') {
            // 공지사항 업데이트 로직
            let notice = document.querySelector('#notice');
            if (notice == null) {
                al('error', '공지사항 변경 실패', '다시 시도해주세요.');
                modalExit();
                return;
            }
            let json = {notice : notice.value};
            fetchPost('/room/' + getRoomId() + '/notice', json, updateNoticeResult);
            modalExit();
            return;
        }
        if (target.id == 'room-notice-delete') {
            // 공지사항 삭제 로직
            let notice = document.querySelector('#notice');
            if (notice == null) {
                al('error', '공지사항 변경 실패', '다시 시도해주세요.');
                modalExit();
                return;
            }
            let json = null;
            fetchDelete('/room/' + getRoomId() + '/notice/delete', json, deleteNoticeResult);
            modalExit();
            return;
        }
    })

    modal_content.addEventListener('keydown', (e) => {

        // 엔터를 눌렀을 때
        if (e.keyCode === 13 && e.target.id == 'tag-add') {

        }
    })

    const roomTitleWrap = document.querySelector('.room-title-wrap');
    const settingMenu = document.querySelector('.setting-menu-list');

    roomTitleWrap.addEventListener('click', (e) =>{
        if(e.target.id == 'setting'){
            settingMenu.classList.toggle('disabled');
        };
        if (e.target.classList.contains('setting-menu')) {
            settingMenu.classList.add('disabled');
        }
        if (e.target.id == 'default-setting') { // 설정버튼
            fetchGet('/room/' + getRoomId() + '/edit', editResult);
            return;
        }
        if (e.target.id == 'notice-setting') {
            insertModalSize('upload-notice');
            modal_content.innerHTML = createUploadNotice();
            modal.classList.remove('disabled');
        }
    });

    

});
function deleteNoticeResult(json) {
    if (json.result == 'error') {
        al(json.result, '공지사항 삭제 실패', json.message);
    }
}
function updateNoticeResult(json) {

}
function scrollToBottom() {
    const chatHistory = document.querySelector('.chat-history');
    chatHistory.scrollTop = chatHistory.scrollHeight;
}

function textareaResize(message) {
    message.style.height = 'auto';
    message.style.height = (message.scrollHeight) + 'px';
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

function editResult(json) {
    const modal = document.querySelector('.modal');
    const modal_content = document.querySelector('.modal-content');

    if (json.result == 'ok') {
        insertModalSize('modal-edit-room');
        modal_content.innerHTML = editRoomModal(json.editRoom);
        modal.classList.remove('disabled');
    }
    if(json.result == 'error') {

    }
    if (json.result == 'notLogin') {
        al('error', '로그인 필요', json.message);
        setTimeout(() => {
            window.location.href = '/';
        }, 1000);
    }
}

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

function editRoomModal(editRoom) {
    return  `<div class="modal-wrapper">
                <div class="create-room-image-box">
                    <button type="button" id="img">
                        <img src="/images/room_profile/${editRoom.image}" width="100%" height="100%" id="roomProfile" name="roomProfile">
                        <input type="file" name="roomImage" id="roomImage" accept="image/*">
                        <svg width="36" height="36" viewBox="0 0 36 36" fill="none" xmlns="http://www.w3.org/2000/svg" class="g68VV5Ghc0ymGpbFWhEx"><circle cx="18" cy="18" r="18" fill="#000"></circle><path d="M11.375 22.658v2.969h2.969l8.756-8.756-2.97-2.969-8.755 8.756zm14.02-8.083a.788.788 0 000-1.116l-1.852-1.852a.788.788 0 00-1.116 0l-1.45 1.448 2.97 2.97 1.448-1.45z" fill="#fff"></path></svg>
                    </button>
                    <div class="checkLabel">
                        <input type="radio" name="defaultImage" id="defaultImage">
                        <label for="defaultImage">기본이미지로 설정</label>
                    </div>
                </div>
                <div class="input-box">
                    <h4>방 제목</h4>
                    <input type="text" name="title" id="title" maxlength="10" value="${editRoom.title}">
                    <span class="msg disabled m-title"></span>
                </div>
                <div class="input-box">
                    <h4>소개글</h4>
                    <input type="text" name="intro" id="intro" maxlength="50" value="${editRoom.intro}">
                    <span class="msg disabled m-intro"></span>
                </div>
                <div class="input-wrap">
                    <h4>인원 수
                        <input type="radio" name="max" id="m2" value="2" ${editRoom.max == 2 ? 'checked' : ''}>
                        <input type="radio" name="max" id="m3" value="3" ${editRoom.max == 3 ? 'checked' : ''}>
                        <input type="radio" name="max" id="m4" value="4" ${editRoom.max == 4 ? 'checked' : ''}>
                        <input type="radio" name="max" id="m5" value="5" ${editRoom.max == 5 ? 'checked' : ''}>
                        <input type="radio" name="max" id="m6" value="6" ${editRoom.max == 6 ? 'checked' : ''}>
                    </h4>
                    <div class="radio-wrap max-wrap">
                        <label for="m2" aria-selected="${editRoom.max == 2 ? 'true' : 'false '}">2명</label>
                        <label for="m3" aria-selected="${editRoom.max == 3 ? 'true' : 'false '}">3명</label>
                        <label for="m4" aria-selected="${editRoom.max == 4 ? 'true' : 'false '}">4명</label>
                        <label for="m5" aria-selected="${editRoom.max == 5 ? 'true' : 'false '}">5명</label>
                        <label for="m6" aria-selected="${editRoom.max == 6 ? 'true' : 'false '}">6명</label>
                    </div>
                </div>
                <span class="msg disabled m-max"></span>
                <div class="input-wrap">
                    <h4>공개여부
                        <input type="radio" name="public" id="public" value="PUBLIC" ${editRoom.roomPublic == 'PUBLIC' ? 'checked' : ''}>
                        <input type="radio" name="public" id="private" value="PRIVATE" ${editRoom.roomPublic == 'PRIVATE' ? 'checked' : ''}>
                    </h4>
                    <div class="radio-wrap">
                        <label for="public" aria-selected="${editRoom.roomPublic == 'PUBLIC'}">공개방</label>
                        <label for="private" aria-selected="${editRoom.roomPublic == 'PRIVATE'}">비공개방</label>
                    </div>
                </div>
                <span class="msg disabled m-public"></span>
                <div class="input-box password-box ${editRoom.roomPublic == 'PUBLIC' ? 'disabled' : ''}">
                    <h4>비밀번호 설정</h4>
                    <input type="password" name="room-password" id="room-password" placeholder="비밀번호 4~6자리를 설정해주세요." minlength="4" maxlength="6" value="${editRoom.password != null ? editRoom.password : ''}">
                    <span class="msg disabled m-private-password"></span>
                </div>
                <span class="msg disabled m-max"></span>
                <div class="input-wrap tag-wrap">
                    <h4>태그</h4>
                    <div class="tag-list">
                        ${createEditTagList(editRoom.tagList)}
                    </div>
                </div>
                <span class="msg disabled m-tag"></span>
            </div>
            <div class="buttons">
                <button type="button" id="room-cancel">취소</button>
                <button type="button" id="room-edit">수정</button>
            </div>`;
};
function createEditTagList(tagList) {
    if (tagList == null) return '';

    let temp = '';
    for (let i=0;i<tagList.length;i++) {
        temp += createEditTag(tagList[i]);
    }
    if (tagList.length < 5) {
        temp += createTagAdd();
    }
    return temp;
}

function createEditTag(tag) {
    return `<div class="tag-box">
                <svg class="tagSvg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><path d="M0 80V229.5c0 17 6.7 33.3 18.7 45.3l176 176c25 25 65.5 25 90.5 0L418.7 317.3c25-25 25-65.5 0-90.5l-176-176c-12-12-28.3-18.7-45.3-18.7H48C21.5 32 0 53.5 0 80zm112 32a32 32 0 1 1 0 64 32 32 0 1 1 0-64z"></path></svg>
                <span type="text" name="tag" value="${tag}">${tag}</span>
                <svg class="xSvg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512"><path d="M376.6 84.5c11.3-13.6 9.5-33.8-4.1-45.1s-33.8-9.5-45.1 4.1L192 206 56.6 43.5C45.3 29.9 25.1 28.1 11.5 39.4S-3.9 70.9 7.4 84.5L150.3 256 7.4 427.5c-11.3 13.6-9.5 33.8 4.1 45.1s33.8 9.5 45.1-4.1L192 306 327.4 468.5c11.3 13.6 31.5 15.4 45.1 4.1s15.4-31.5 4.1-45.1L233.7 256 376.6 84.5z"></path></svg>
            </div>`
}
function createTagAdd() {
    return  '<div class="tag-add-box">' +
                '<span>#</span>' +
                '<input type="text" name="tag-add" id="tag-add" placeholder="태그입력" maxlength="10">' +
            '</div>';
}

function createUploadNotice() {
    return `<h3>공지사항</h3>
            <textarea id="notice"></textarea>
            <div class="text-lengths">
                (<span id="currentLength">0</span>/100)
            </div>
            <div class="buttons">
                <button type="button" id="room-notice-delete">삭제</button>
                <button type="button" id="room-cancel">이전</button>
                <button type="button" id="room-notice">등록</button>
            </div>`;
}
