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
    console.log('token = ', token);
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
            token : token,
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
        history.innerHTML += centerMessage(chat.message);
        moveToOnline(chat.sender);
        return;
    }

    if (chat.type === 'LEAVE') {
        history.innerHTML += centerMessage(chat.message);
        moveToOffline(chat.sender);
        return;
    }

    if (chat.type === 'TALK') {
        printMessage(chat);
    }
    
}
function moveToOnline(sender) {
    let member = document.querySelector('.member-list[data-is-online="false"] span[name="' + sender+ '"]').parentElement.parentElement;
    let online = document.querySelector('.member-list[data-is-online="true"]');
    online.appendChild(member);
}
function moveToOffline(sender) {
    let member = document.querySelector('.member-list[data-is-online="true"] #' + sender).parentElement.parentElement;
    let offline = document.querySelector('.member-list[data-is-online="false"]');
    offline.appendChild(member);
}
function centerMessage(message) {
    return `<div class="date">
                <span>${message}</span>
            </div>`
}

function onError() {
    al('error', '에러', '서버와 연결이 끊겼습니다. 다시 시도해주세요.');
}

function printMessage(chat) {
    const chatHistory = document.querySelector('.chat-history');
    let lastElement = chatHistory.lastElementChild;

    nextDate(chat, lastElement);

    if (chat.token == token) { // 내 메세지
        if (lastElement.classList.contains('me')) { // 마지막 채팅내역이 나일 경우
            lastElement.innerHTML += createMeMessageBox(chat); 
        } else { // 마지막 채팅내역이 내가 아닌경우
            chatHistory.innerHTML += createMe(chat);
        }
    } else { // 상대방 메세지
        let name = lastElement.querySelector('.name');
        if (name != null && name.textContent == chat.sender) { // 마지막 채팅내역이 보낸사람과 일치
            lastElement.querySelector('.message-wrap').innerHTML += createYouMessageBox(chat);
        } else {
            chatHistory.innerHTML += createYou(chat);
        }
    }
}

function nextDate(chat, lastElement) {
    const chatHistory = document.querySelector('.chat-history');
    let day = formatDay(chat.time);
    
    if (lastElement == null || lastElement.classList.contains('date')) return;
    console.log('이전 날짜 값 : ' , lastElement.querySelector('.message-box:last-child .day').textContent);
    console.log('이후 날짜 값 : ' , day);

    if (lastElement.querySelector('.message-box:last-child .day').textContent != day) {
        chatHistory.innerHTML += printMessage(formatDay(chat.time)); // 2021년 1월 1일 월요일 출력
    }
}

function formatDay(time) {
    let dateObject = new Date(time);
    return new Intl.DateTimeFormat('ko-KR', {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
                weekday: 'long'
            }).format(dateObject);
}
function foramtTime(time) {
    let dateObject = new Date(time);
    return ('0' + dateObject.getHours()).slice(-2) + ':' + ('0' + dateObject.getMinutes()).slice(-2);
}
function createMeMessageBox(chat) {
    return `<div class="message-box">
                <span class="day" style="display: none;">` + formatDay(chat.time) +`</span>
                <span class="time">` + foramtTime(chat.time) + `</span>
                <pre class="message-content">
                    ${chat.message}
                </pre>
            </div>`
}
function createYouMessageBox(chat) {
    return `<div class="message-box">
                <pre class="message-content">
                    ${chat.message}
                </pre>
                <span class="time">` + foramtTime(chat.time) + `</span>
                <span class="day" style="display: none;">` + formatDay(chat.time) +`</span>
            </div>`
}

function createMe(chat) {
    return `<div class="me">
                ` + createMeMessageBox(chat) + `
            </div>`
}
function createYou(chat) {
    return `<div class="you">
                <div class="profile-box">
                    <img src="/images/member_profile/${chat.senderImage}" alt="">
                </div>
                <div class="message-wrap">
                    <div class="name-box">
                        <span class="name">${chat.sender}</span>
                    </div>
                    ` + createYouMessageBox(chat) + `
                </div>
            </div>`
    
}
