const disabled = 'disabled';
window.addEventListener('load', () => {

    const modal = document.querySelector('.modal');
    const modal_content = document.querySelector('.modal-content');

    // 테스트 코드
    insertModalSize('modal-login');

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


    // 부모에게 이벤트 위임
    modal_content.addEventListener('click', (e) => {
        let target = e.target;

        if (target.id == 'signup') {
            let modalContent = document.querySelector('.modal-content');
            modalContent.classList.add('change');
            setTimeout(() => {
                modalContent.classList.remove('change');
            }, 800);
        }
    })


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
                '<a id="signup">아직 회원이 아니신가요?</a>' +
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

function createCreateRoomModal() {
    return  '<div class="modal-wrapper">' +
                '<div class="create-room-image-box">' +
                    '<button type="button" id="img">' +
                        '<img src="/static/image/IMG_8012.jpeg" width="100%" height="100%" id="myProfile">' +
                        '<input type="file" name="roomImage" id="roomImage" accept="image/*">' +
                        '<svg width="36" height="36" viewBox="0 0 36 36" fill="none" xmlns="http://www.w3.org/2000/svg" class="g68VV5Ghc0ymGpbFWhEx"><circle cx="18" cy="18" r="18" fill="#000"></circle><path d="M11.375 22.658v2.969h2.969l8.756-8.756-2.97-2.969-8.755 8.756zm14.02-8.083a.788.788 0 000-1.116l-1.852-1.852a.788.788 0 00-1.116 0l-1.45 1.448 2.97 2.97 1.448-1.45z" fill="#fff"></path></svg>' +
                    '</button>' +
                '</div>' +
                '<div class="input-box">' +
                    '<h4>방 제목</h4>' +
                    '<input type="text" name="title" id="title" maxlength="10">' +
                '</div>' +
                '<div class="input-box">' +
                    '<h4>소개글</h4>' +
                    '<input type="text" name="intro" id="intro" maxlength="50">' +
                '</div>' +
                '<div class="input-wrap">' +
                    '<h4>인원 수' +
                        '<input type="radio" name="max" id="m2">' +
                        '<input type="radio" name="max" id="m3">' +
                        '<input type="radio" name="max" id="m4" checked>' +
                        '<input type="radio" name="max" id="m5">' +
                        '<input type="radio" name="max" id="m6">' +
                    '</h4>' +
                    '<div class="radio-wrap">' +
                        '<label for="m2" aria-selected="false">2명</label>' +
                        '<label for="m3" aria-selected="false">3명</label>' +
                        '<label for="m4" aria-selected="true">4명</label>' +
                        '<label for="m5" aria-selected="false">5명</label>' +
                        '<label for="m6" aria-selected="false">6명</label>' +
                    '</div>' +
                '</div>' +
                '<div class="input-wrap tag-wrap">' +
                    '<h4>태그</h4>' +
                    '<div class="tag-list">' +
                        '<div class="tag-box">' +
                            '<svg xmlns="http://www.w3.org/2000/svg" class="tagSvg" viewBox="0 0 448 512"><path d="M0 80V229.5c0 17 6.7 33.3 18.7 45.3l176 176c25 25 65.5 25 90.5 0L418.7 317.3c25-25 25-65.5 0-90.5l-176-176c-12-12-28.3-18.7-45.3-18.7H48C21.5 32 0 53.5 0 80zm112 32a32 32 0 1 1 0 64 32 32 0 1 1 0-64z"/></svg>' +
                            '<span type="text" name="tag" id="" value="백엔드">백엔드</span>' +
                            '<svg xmlns="http://www.w3.org/2000/svg" class="xSvg" viewBox="0 0 384 512"><path d="M376.6 84.5c11.3-13.6 9.5-33.8-4.1-45.1s-33.8-9.5-45.1 4.1L192 206 56.6 43.5C45.3 29.9 25.1 28.1 11.5 39.4S-3.9 70.9 7.4 84.5L150.3 256 7.4 427.5c-11.3 13.6-9.5 33.8 4.1 45.1s33.8 9.5 45.1-4.1L192 306 327.4 468.5c11.3 13.6 31.5 15.4 45.1 4.1s15.4-31.5 4.1-45.1L233.7 256 376.6 84.5z"/></svg>' +
                        '</div>' +
                        '<div class="tag-box">' +
                            '<svg xmlns="http://www.w3.org/2000/svg" class="tagSvg" viewBox="0 0 448 512"><path d="M0 80V229.5c0 17 6.7 33.3 18.7 45.3l176 176c25 25 65.5 25 90.5 0L418.7 317.3c25-25 25-65.5 0-90.5l-176-176c-12-12-28.3-18.7-45.3-18.7H48C21.5 32 0 53.5 0 80zm112 32a32 32 0 1 1 0 64 32 32 0 1 1 0-64z"/></svg>' +
                            '<span type="text" name="tag" id="" value="백엔드">가나다라마바사아차</span>' +
                            '<svg xmlns="http://www.w3.org/2000/svg" class="xSvg" viewBox="0 0 384 512"><path d="M376.6 84.5c11.3-13.6 9.5-33.8-4.1-45.1s-33.8-9.5-45.1 4.1L192 206 56.6 43.5C45.3 29.9 25.1 28.1 11.5 39.4S-3.9 70.9 7.4 84.5L150.3 256 7.4 427.5c-11.3 13.6-9.5 33.8 4.1 45.1s33.8 9.5 45.1-4.1L192 306 327.4 468.5c11.3 13.6 31.5 15.4 45.1 4.1s15.4-31.5 4.1-45.1L233.7 256 376.6 84.5z"/></svg>' +
                        '</div>' +
                        '<div class="tag-box">' +
                            '<svg xmlns="http://www.w3.org/2000/svg" class="tagSvg" viewBox="0 0 448 512"><path d="M0 80V229.5c0 17 6.7 33.3 18.7 45.3l176 176c25 25 65.5 25 90.5 0L418.7 317.3c25-25 25-65.5 0-90.5l-176-176c-12-12-28.3-18.7-45.3-18.7H48C21.5 32 0 53.5 0 80zm112 32a32 32 0 1 1 0 64 32 32 0 1 1 0-64z"/></svg>' +
                            '<span type="text" name="tag" id="" value="백엔드">가나다라마바</span>' +
                            '<svg xmlns="http://www.w3.org/2000/svg" class="xSvg" viewBox="0 0 384 512"><path d="M376.6 84.5c11.3-13.6 9.5-33.8-4.1-45.1s-33.8-9.5-45.1 4.1L192 206 56.6 43.5C45.3 29.9 25.1 28.1 11.5 39.4S-3.9 70.9 7.4 84.5L150.3 256 7.4 427.5c-11.3 13.6-9.5 33.8 4.1 45.1s33.8 9.5 45.1-4.1L192 306 327.4 468.5c11.3 13.6 31.5 15.4 45.1 4.1s15.4-31.5 4.1-45.1L233.7 256 376.6 84.5z"/></svg>' +
                        '</div>' +
                        '<div class="tag-box">' +
                            '<svg xmlns="http://www.w3.org/2000/svg" class="tagSvg" viewBox="0 0 448 512"><path d="M0 80V229.5c0 17 6.7 33.3 18.7 45.3l176 176c25 25 65.5 25 90.5 0L418.7 317.3c25-25 25-65.5 0-90.5l-176-176c-12-12-28.3-18.7-45.3-18.7H48C21.5 32 0 53.5 0 80zm112 32a32 32 0 1 1 0 64 32 32 0 1 1 0-64z"/></svg>' +
                            '<span type="text" name="tag" id="" value="백엔드">아아아아아아아아아아아</span>' +
                            '<svg xmlns="http://www.w3.org/2000/svg" class="xSvg" viewBox="0 0 384 512"><path d="M376.6 84.5c11.3-13.6 9.5-33.8-4.1-45.1s-33.8-9.5-45.1 4.1L192 206 56.6 43.5C45.3 29.9 25.1 28.1 11.5 39.4S-3.9 70.9 7.4 84.5L150.3 256 7.4 427.5c-11.3 13.6-9.5 33.8 4.1 45.1s33.8 9.5 45.1-4.1L192 306 327.4 468.5c11.3 13.6 31.5 15.4 45.1 4.1s15.4-31.5 4.1-45.1L233.7 256 376.6 84.5z"/></svg>' +
                        '</div>' +
                        '<div class="tag-add-box">' +
                            '<span>#</span>' +
                            '<input type="text" name="tag-add" id="tag-add" placeholder="태그입력" maxlength="10">' +
                        '</div>' +
                    '</div>' +
                '</div>' +
            '</div>' +
            '<div class="buttons">' +
                '<button type="button" id="room-cancel">취소</button>' +
                '<button type="button" id="room-create">생성</button>' +
            '</div>';
}