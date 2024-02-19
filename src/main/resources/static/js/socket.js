let stompClient = null;
let token = getToken();
window.addEventListener('load', () => {
    connect();

})
function getToken() {
    fetch('/room/access')
    .then(res => res.json())
    .then(map => {
        console.log(map);
        token = map.message;
    });
}

function connect() {
    let socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);

}

function onConnected() {
    let roomId = getRoomId();
    // sub 할 url => /sub/chat/room/enter/roomId 로 구독한다
    stompClient.subscribe('/sub/chat/room/' + roomId, onMessageReceived);

    // 서버에 username 을 가진 유저가 들어왔다는 것을 알림
    // /pub/chat/enterUser 로 메시지를 보냄
    stompClient.send("/pub/chat/enterUser",
        {},
        JSON.stringify({
            roomId : Number(roomId),
            token : token,
            type: 'ENTER'
        })
    )
}
function getRoomId() {
    // 현재 페이지의 URL을 가져옴
    let currentURL = window.location.href;

    // "room/" 다음에 있는 숫자 값을 가져오기 위한 정규식 사용
    let match = currentURL.match(/\/room\/(\d+)/);

    // match에서 추출된 값 확인
    return match ? match[1] : null;
}

function sendMessage() {
    let messageInput = document.querySelector('textarea[name="message"]');

    if (messageInput.value.trim() && stompClient) {
        let chatMessage = {
            roomId : getRoomId(),
            message : messageInput.value.trim(),
            type : 'TALK'
        }

        stompClient.send('/pub/chat/sendMessage', {}, JSON.stringify(chatMessage));
        message.value = '';
    }
}

function onMessageReceived(payload) {
    console.log('payload : ' + payload);

    const history = document.querySelector('.chat-history');

    let chat = JSON.parse(payload.body);

    if (chat.type === 'ENTER') {
        history.innerHTML += centerMessage(payload.message);
        return;
    }

    if (chat.type === 'LEAVE') {
        history.innerHTML += centerMessage(payload.message);
        return;
    }

    if (chat.type === 'TALK') {

    }
    
}
function centerMessage(message) {
    return `<div class="date">
                <span>${message}</span>
            </div>`
}

function getUserList() {
    fetchGet('/chat/userlist', userList);
}
function userList(json) {
    let offlineMemberList = document.querySelector('.member-list[data-is-online="false"]');

    let memberList = json.memberList;
    for (let i=0;i<memberList.length;i++) {
        offlineMemberList += createMember(memberList[i]);
    }
}

function createMember(member) {
    return `<div class="member">
            <div class="member-data">
                <img src="/images/member_profile/${member.memberImage}" alt="">
                <span>${member.name}</span>` + 
                isManager(member) +   
            `</div>
            <button type="button" class="member-more">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><path d="M8 256a56 56 0 1 1 112 0A56 56 0 1 1 8 256zm160 0a56 56 0 1 1 112 0 56 56 0 1 1 -112 0zm216-56a56 56 0 1 1 0 112 56 56 0 1 1 0-112z"/></svg>
                <div class="member-option-menu disabled">
                    <div class="member-notify-box">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512"><path fill="#ff0000" d="M272 384c9.6-31.9 29.5-59.1 49.2-86.2l0 0c5.2-7.1 10.4-14.2 15.4-21.4c19.8-28.5 31.4-63 31.4-100.3C368 78.8 289.2 0 192 0S16 78.8 16 176c0 37.3 11.6 71.9 31.4 100.3c5 7.2 10.2 14.3 15.4 21.4l0 0c19.8 27.1 39.7 54.4 49.2 86.2H272zM192 512c44.2 0 80-35.8 80-80V416H112v16c0 44.2 35.8 80 80 80zM112 176c0 8.8-7.2 16-16 16s-16-7.2-16-16c0-61.9 50.1-112 112-112c8.8 0 16 7.2 16 16s-7.2 16-16 16c-44.2 0-80 35.8-80 80z"/></svg>
                        <span>신고하기</span>
                    </div>
                </div>
            </button>
        </div>`
}
function isManager(member) {
    if (member.manager) {
        return '<div class="manager">방장</div>';
    } else {
        return '';
    }
}

function onError() {
    al('error', '에러', '서버와 연결이 끊겼습니다. 다시 시도해주세요.');
}

function fetchGet(url, callback) {
    fetch(url)
    .then(res => res.json())
    .then(map => callback(map));
}