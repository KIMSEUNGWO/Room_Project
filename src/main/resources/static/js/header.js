const disabled = 'disabled';
window.addEventListener('load', () => {

    const modal = document.querySelector('.modal');
    const modal_content = document.querySelector('.modal-content');


    const loginBtn = document.querySelector('#btn-login');
    if (loginBtn != null)  {
        loginBtn.addEventListener('click', () => {
            insertModalSize('modal-login');
            modal_content.innerHTML  += createLoginModal();
            modal.classList.remove(disabled);
        })
    }

    const createRoomBtn = document.querySelector('#btn-create-room');
    if (createRoomBtn != null) {
        createRoomBtn.addEventListener('click', () => {
            insertModalSize('modal-create-room')
            modal_content.innerHTML += createCreateRoomModal();
            modal.classList.remove(disabled);
        })
    }


    const modalExitList = document.querySelector('.modal-exit');
    if (modalExitList != null) {
        modalExitList.addEventListener('click', () => {
            modal.classList.toggle(disabled);
            setTimeout(() => {
                modal_content.innerHTML = '';
                initModalSize();
            }, 200);
        })
    }


})

function insertModalSize(className) {
    initModalSize();
    const modal_wrap = document.querySelector('.modal-wrap');
    modal_wrap.classList.add(className);
}

function initModalSize() {
    const modal_wrap = document.querySelector('.modal-wrap');
    modal_wrap.classList.forEach(cl => {
        if (cl != 'modal-wrap') {
            modal_wrap.classList.remove(cl);
        }
    })
}

function createLoginModal() {
return  '<div class="modal-input-box">' +
            '<input type="text" name="account" id="account" placeholder="아이디">' +
            '<input type="password" name="password" id="password" placeholder="비밀번호" minlength="8">' +
        '</div>' +
        '<div class="error-box">' +
            '<span class="error">아이디/비밀번호를 확인해주세요.</span>' +
        '</div>' +
        '<button type="button" id="login">로그인</button>' +
        '<div class="modal-sub-menu">' +
            '<div class="text-align">' +
                '<a href="/signup" id="signup">아직 회원이 아니신가요?</a>' +
            '</div>' +
            '<div class="text-align justify-content">' +
                '<a href="/find/id" class="find">아이디 찾기</a>' +
                '<a href="/find/pw" class="find">비밀번호 찾기</a>' +
            '</div>' +
        '</div>' +
        '<div class="social-box">' +
            '<button type="button" class="social" id="kakao">' +
                '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="none">' +
                    '<path d="M9.98613 0.909058C4.47157 0.909058 0 4.28535 0 8.45987C0 11.0379 1.71984 13.314 4.34398 14.6731L3.29543 18.4485C3.29543 18.4485 3.12344 18.8207 3.39528 18.9879C3.47157 19.0542 3.57033 19.0909 3.67268 19.0909C3.77502 19.0909 3.87378 19.0542 3.95007 18.9879L8.74897 15.8974C9.16506 15.9352 9.58113 15.9621 10.0139 15.9621C15.5284 15.9621 20 12.5912 20 8.41131C20 4.2314 15.5007 0.909058 9.98613 0.909058Z" fill="#181602"/>' +
                '</svg>' +
                '<span>카카오톡으로 로그인하기</span>' +
            '</button>' +
        '</div>';
}