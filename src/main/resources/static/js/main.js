let page = 0;
let size = 10;
let lastPage = false;

window.addEventListener('load', () => {
    const modal = document.querySelector('.modal');
    const modal_content = document.querySelector('.modal-content');

    scrollToEvent();
    const searchInput = document.querySelector('input[name="word"]');
    searchInput.addEventListener('keydown', (e) => (e.keyCode == 13) ? searchBtn.click() : '');

    const searchBtn = document.querySelector('#searchBtn');
    searchBtn.addEventListener('click', () => search(searchInput.value));

    const roomWrapper = document.querySelector('.room-wrapper');
    roomWrapper.addEventListener('click', (e) => {
        
        if (e.target.classList.contains('tag')) {
            search(e.target.value);
            return;
        }

        if (e.target.classList.contains('more')) {
            let moreMenu = e.target.children.namedItem('more-menu');
            moreMenu.classList.toggle('disabled');
            clearMoreMenu(moreMenu);
        }

        if (e.target.classList.contains('enterBtn')) {
            insertModalSize('enter-room-confirm')
            modal_content.innerHTML = createEnterRoomModal(e.target.value);
            modal.classList.remove(disabled);
        }
    })

    modal_content.addEventListener('click', (e) => {
        let target = e.target;

        if (target.id == 'room-enter') { // 로그인페이지에서 회원가입 버튼클릭
            fetchGet('/login/check', hasLogin);

            function hasLogin(json) {
                if (json.result == 'ok') {
                    modalExit();
                    window.location.href='/room/' + target.value;
                } else {
                    al('error', '경고', '로그인이 필요합니다.')
                    changeToLogin();
                }
            }
            return;
        }
    })
})

function search(searchWord) {
    let searchTemplate = document.querySelector('.search-result');
    let searchResultTag = document.querySelector('.search-result .room-list')
    searchTemplate.classList.remove('disabled');
    searchTemplate.classList.add('change');
    setTimeout(() => {
       searchResultTag.innerHTML = ''; 
    }, 300);

    pageInit();

    fetchGet(`/search?word=${searchWord}&page=${page++}&size=${size}`, searchResult);
}
function clearMoreMenu(nowMoreMenu) {
    let moreMenus = document.querySelectorAll('.more-menu');
    moreMenus.forEach(moreMenu => {
        if (moreMenu != nowMoreMenu) {
            moreMenu.classList.add('disabled')
        }
    });
}
function pageInit() {
    page = 0;
    size = 10;
    lastPage = false;
}
function scrollToEvent() {

    function scrollEvent() {
        if (!isScrollOver() || lastPage) {
            return;
        }
        window.removeEventListener('scroll', scrollEvent); // 이벤트 제거

        let searchTemplate = document.querySelector('.search-result');
        searchTemplate.classList.remove('disabled');
        const searchInput = document.querySelector('input[name="word"]');
        fetchGet(`/search?word=${searchInput.value}&page=${page++}&size=${size}`, searchResult);

    }
    
    window.addEventListener('scroll', scrollEvent);
}
function isScrollOver() {
    // 현재 스크롤 위치
    var scrollPosition = window.scrollY || window.pageYOffset || document.documentElement.scrollTop;

    // 문서의 높이
    var documentHeight = document.documentElement.scrollHeight;

    // 브라우저 창의 높이
    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;

    // 스크롤이 가장 아래에서 90% 이상 되었을 때 실행할 코드
    if (scrollPosition > (documentHeight - windowHeight) * 0.999) {
        return true;
    }
    return false;
}
/**
 * 예상 JSON 결과값
 * {
 *      result : 'ok',
 *      message : '검색성공',
 *      word : '검색어',
 *      roomList :  [
 *                      {
 *                          roonId : 1,
 *                          roomImage : $sfln4.jpg,
 *                          roomTitle : '방제목방제목',
 *                          roomIntro : '소개글소개글소개글',
 *                          roomPublic : 공개방 : true, 비공개방 : false,
 *                          roomJoin : 내가 참여중인방 : true, 참여중이 아닌 방 false;
 *                          roomMaxPerson : 1/5 (현재참겨자/최대참여자)
 *                          tagList :   ['백엔드','자바','어쩌구','저쩌구']
 *                      }
 *                      {
 *                          ...
 *                      }
 *                  ]
 * }
 */
function searchResult(json) {
    let searchResultTag = document.querySelector('.search-result .room-list')
    let searchTemplate = document.querySelector('.search-result');
    if (json.result == 'ok') {

        if (json.roomList.length == 0) {
            al('error', '검색실패', '검색 결과가 없어요.');
            searchTemplate.classList.remove('change');
            return;
        }
        if (json.roomList.length < size) {
            lastPage = true;
        }
        setTimeout(() => {
            searchResultTag.innerHTML += createRoomTemplate(json.roomList);
            highlight(json.word);
            searchTemplate.classList.remove('change');
        }, 500);

    } else if (json.result == 'error') {
        al('error', '검색 실패', '방 정보를 가져오지 못했습니다.');
        searchTemplate.classList.remove('change');
    }
    scrollToEvent();
}

function createRoomTemplate(roomList) {
    let temp = '';
    for (let i=0;i<roomList.length;i++) {
        temp += createRoom(roomList[i]);
    }
    return temp;
}

function createRoom(room) {
    return  '<div class="room ' + alreadyJoinRoom(room.roomJoin) + '">' +
                '<dlv class="room-wrap">' +
                    '<div class="room-top">' +
                        '<button type="button" class="more" value="' + room.roomId + '">' +
                            '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><path d="M8 256a56 56 0 1 1 112 0A56 56 0 1 1 8 256zm160 0a56 56 0 1 1 112 0 56 56 0 1 1 -112 0zm216-56a56 56 0 1 1 0 112 56 56 0 1 1 0-112z"/></svg>' +
                            '<ul class="more-menu disabled" name="more-menu">' +
                                '<li>' +
                                    '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M495.9 166.6c3.2 8.7 .5 18.4-6.4 24.6l-43.3 39.4c1.1 8.3 1.7 16.8 1.7 25.4s-.6 17.1-1.7 25.4l43.3 39.4c6.9 6.2 9.6 15.9 6.4 24.6c-4.4 11.9-9.7 23.3-15.8 34.3l-4.7 8.1c-6.6 11-14 21.4-22.1 31.2c-5.9 7.2-15.7 9.6-24.5 6.8l-55.7-17.7c-13.4 10.3-28.2 18.9-44 25.4l-12.5 57.1c-2 9.1-9 16.3-18.2 17.8c-13.8 2.3-28 3.5-42.5 3.5s-28.7-1.2-42.5-3.5c-9.2-1.5-16.2-8.7-18.2-17.8l-12.5-57.1c-15.8-6.5-30.6-15.1-44-25.4L83.1 425.9c-8.8 2.8-18.6 .3-24.5-6.8c-8.1-9.8-15.5-20.2-22.1-31.2l-4.7-8.1c-6.1-11-11.4-22.4-15.8-34.3c-3.2-8.7-.5-18.4 6.4-24.6l43.3-39.4C64.6 273.1 64 264.6 64 256s.6-17.1 1.7-25.4L22.4 191.2c-6.9-6.2-9.6-15.9-6.4-24.6c4.4-11.9 9.7-23.3 15.8-34.3l4.7-8.1c6.6-11 14-21.4 22.1-31.2c5.9-7.2 15.7-9.6 24.5-6.8l55.7 17.7c13.4-10.3 28.2-18.9 44-25.4l12.5-57.1c2-9.1 9-16.3 18.2-17.8C227.3 1.2 241.5 0 256 0s28.7 1.2 42.5 3.5c9.2 1.5 16.2 8.7 18.2 17.8l12.5 57.1c15.8 6.5 30.6 15.1 44 25.4l55.7-17.7c8.8-2.8 18.6-.3 24.5 6.8c8.1 9.8 15.5 20.2 22.1 31.2l4.7 8.1c6.1 11 11.4 22.4 15.8 34.3zM256 336a80 80 0 1 0 0-160 80 80 0 1 0 0 160z"/></svg>' +
                                    '<span>다른 설정</span>' +
                                '</li>' +
                                '<li class="room-exit">' +
                                    '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M377.9 105.9L500.7 228.7c7.2 7.2 11.3 17.1 11.3 27.3s-4.1 20.1-11.3 27.3L377.9 406.1c-6.4 6.4-15 9.9-24 9.9c-18.7 0-33.9-15.2-33.9-33.9l0-62.1-128 0c-17.7 0-32-14.3-32-32l0-64c0-17.7 14.3-32 32-32l128 0 0-62.1c0-18.7 15.2-33.9 33.9-33.9c9 0 17.6 3.6 24 9.9zM160 96L96 96c-17.7 0-32 14.3-32 32l0 256c0 17.7 14.3 32 32 32l64 0c17.7 0 32 14.3 32 32s-14.3 32-32 32l-64 0c-53 0-96-43-96-96L0 128C0 75 43 32 96 32l64 0c17.7 0 32 14.3 32 32s-14.3 32-32 32z"/></svg>' +
                                    '<span>방 나가기</span>' +
                                '</li>' +
                            '</ul>' +
                        '</button>' +
                    '</div>' +
                    '<div class="room-image-box">' +
                        '<img src="/images/room_profile/' + room.roomImage + '" alt="">' +
                    '</div>' +
                    '<span class="room-title">' + room.roomTitle +'</span>' +
                    '<span class="room-content">' + room.roomIntro + '</span>' +
                '</dlv>' +
                '<div class="room-wrap2">' +
                    '<div class="tag-list">' +
                            createTagList(room.tagList) +
                    '</div>' +
                    '<div class="button-box">' +
                        '<button type="button" class="enterBtn ' + checkFull(room.roomJoin, room.nowPerson, room.maxPerson) + '" ' + checkFull(room.roomJoin, room.nowPerson, room.maxPerson) + ' value="' + room.roomId + '">' +
                            createPublic(room.roomPublic) +                            
                            '<span>입장하기 <span class="person">' + room.nowPerson + '/' + room.maxPerson + '</span></span>' +
                            '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M217.9 105.9L340.7 228.7c7.2 7.2 11.3 17.1 11.3 27.3s-4.1 20.1-11.3 27.3L217.9 406.1c-6.4 6.4-15 9.9-24 9.9c-18.7 0-33.9-15.2-33.9-33.9l0-62.1L32 320c-17.7 0-32-14.3-32-32l0-64c0-17.7 14.3-32 32-32l128 0 0-62.1c0-18.7 15.2-33.9 33.9-33.9c9 0 17.6 3.6 24 9.9zM352 416l64 0c17.7 0 32-14.3 32-32l0-256c0-17.7-14.3-32-32-32l-64 0c-17.7 0-32-14.3-32-32s14.3-32 32-32l64 0c53 0 96 43 96 96l0 256c0 53-43 96-96 96l-64 0c-17.7 0-32-14.3-32-32s14.3-32 32-32z"/></svg>' +
                        '</button>' +
                    '</div>' +
                '</div>' +
            '</div>';
}
function checkFull(roomJoin, nowPerson, maxPerson) {
    if (roomJoin) return '';
    if (nowPerson == maxPerson) return 'disabled';
    return '';
}
function alreadyJoinRoom(roomJoin) {
    if (roomJoin) return 'join';
    return '';
}
function createPublic(roomPublic) {
    if (!roomPublic) {
        return '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><path d="M144 144v48H304V144c0-44.2-35.8-80-80-80s-80 35.8-80 80zM80 192V144C80 64.5 144.5 0 224 0s144 64.5 144 144v48h16c35.3 0 64 28.7 64 64V448c0 35.3-28.7 64-64 64H64c-35.3 0-64-28.7-64-64V256c0-35.3 28.7-64 64-64H80z"/></svg>'
    }
    return '';
}
function createTagList(tagList) {
    let temp = '';

    for (let i=0;i<tagList.length;i++) {
        temp += '<button type="button" class="tag" value="' + tagList[i] + '">' +
                    '<svg xmlns="http://www.w3.org/2000/svg" class="tagSvg" viewBox="0 0 448 512"><path d="M0 80V229.5c0 17 6.7 33.3 18.7 45.3l176 176c25 25 65.5 25 90.5 0L418.7 317.3c25-25 25-65.5 0-90.5l-176-176c-12-12-28.3-18.7-45.3-18.7H48C21.5 32 0 53.5 0 80zm112 32a32 32 0 1 1 0 64 32 32 0 1 1 0-64z"/></svg>' +
                    '<span>' + tagList[i] + '</span>' +
                '</button>';
    }

    return temp;
}

// 검색된 키워드 하이라이트 효과
function highlight(pattern) {
    const tagList = document.querySelectorAll('.room-title, .room-content, .tag span');

    pattern = pattern.split('').join(' ');
    const patternWithoutSpaces = pattern.replace(/\s/g, '\\s*');
    const regex = new RegExp(patternWithoutSpaces, 'gi');
    
    tagList.forEach(element => {
        let text = element.textContent;
        if (regex.test(text)) {
            element.innerHTML = text.replace(regex, '<span class="find">$&</span>');
        };
    });

}

function fetchGet(url, callback) {
    fetch(url)
    .then(res => res.json())
    .then(map => callback(map));
}

function createEnterRoomModal(value) {
    return  `<div class="confirm-message">
                <span>방에 입장하시겠습니까?</span>
            </div>
            <div class="buttons">
                <button type="button" id="room-cancel">이전</button>
                <button type="button" id="room-enter" value="${value}">입장</button>
            </div>`
}