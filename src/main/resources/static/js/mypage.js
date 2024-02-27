let timerInterval = null;
let clickLimit = null;

window.addEventListener('load', () => {
    let x = document.querySelector('#pwdPopCancel');
    let cancelBtn = document.querySelector('#pwdPopCancelBtn');

    let pwdPop = document.querySelector('.passwordPop').parentElement;
    let deletePop = document.querySelector('.deletePop').parentElement;

    x.addEventListener('click', () => {
        pwdPop.classList.add('disabled');
    })
    cancelBtn.addEventListener('click', () => {
        pwdPop.classList.add('disabled');
    })

    let pwdBtn = document.querySelector('#pwdBtn');
    let deleteBtn = document.querySelector('#deleteBtn');
    if (pwdBtn != null){
        pwdBtn.addEventListener('click', () => {
            pwdPop.classList.remove('disabled');
        })
    }
    deleteBtn.addEventListener('click', () => {
        deletePop.classList.remove('disabled');
    })

    const findPassword = document.querySelector('#findPassword');
    findPassword.addEventListener('click', () => {
        console.log('?');
        modalFindPassword();
    })

    

    let profileImgBtn = document.querySelector('#img');
    let imgInput = document.querySelector('#img input[type="file"]');

    profileImgBtn.addEventListener('click', () => {
        imgInput.click();
    })

    let profile = document.querySelector('#myProfile');
    imgInput.addEventListener('change', () => {
        let file = imgInput.files[0];
        profile.src = URL.createObjectURL(file);
    })


    //

    const deletePopBtn = document.querySelector('#deletePopDeleteBtn');

    let checkBtn = document.querySelector('#check');
    let checkbox = document.querySelector('input[name="policy"]');
    checkBtn.addEventListener('click', () => {
        checkbox.click();
    })

    checkbox.addEventListener('change', () => {
        let boolean = checkbox.checked;
        if (boolean) {
            checkBtn.classList.add('confirm');
            deletePopBtn.setAttribute('data-is-allowed', 'true');
        } else {
            checkBtn.classList.remove('confirm');
            deletePopBtn.setAttribute('data-is-allowed', 'false');
        }
    })

    

    let deleteCancelBtn = document.querySelector('#deletePopCancel');
    let deleteCancelBtn2 = document.querySelector('#deletePopCancelBtn');
    deleteCancelBtn.addEventListener('click', () => {
        deletePop.classList.add('disabled');
    });
    deleteCancelBtn2.addEventListener('click', () => {
        deletePop.classList.add('disabled');
    });

    // password
    let pwSaveBtn = document.querySelector('.saveBtn');
    pwSaveBtn.addEventListener('click', () => {
        let currPw = document.querySelector('input[name="currPassword"]').value;
        let changePw = document.querySelector('input[name="changePassword"]').value;
        let checkPw = document.querySelector('input[name="checkPassword"]').value;

        let json = {nowPassword : currPw, changePassword : changePw, checkPassword : checkPw};
        fetchPost('/change/password', json, result);
    })

    // 휴대폰 변경 로직

    const changePassword = document.querySelector('.formEditBtn');
    let phonePop = document.querySelector('.phonePop').parentElement;
    let phoneX = document.querySelector('#phonePopCancel');
    let phonePopCancelBtn = document.querySelector('#phonePopCancelBtn');

    changePassword.addEventListener('click', () => {
        phonePop.classList.remove('disabled');
    })
    phoneX.addEventListener('click', () => phonePop.classList.add('disabled'))
    phonePopCancelBtn.addEventListener('click', () => phonePop.classList.add('disabled'))


    const phoneBtn = document.querySelector('#phoneBtn');
    phoneBtn.addEventListener('click', () => {
        if (validPhone() != null) return;

        let phone = document.querySelector('input[name="phones"]').value.replaceAll('-', '');
        let json = {phone : phone};

        fetchPost('/sms/send/find', json, sendSMS);
    })

    const changePhone = document.querySelector('#changePhone');
    changePhone.addEventListener('click', () => {

        let phone = document.querySelector('input[name="phones"]');
        let certification = document.querySelector('input[name="phoneCheck"]');

        
        if (phone == null || phone.value.length == '') {
            let cPhone = document.querySelector('.confirmPhone');
            printFalse(cPhone, '휴대폰 번호를 입력해주세요');
            return;
        }
        if (certification == null || certification.value.length < 5) {
            let cPhoneCheck = document.querySelector('.confirmPhoneCheck');
            printFalse(cPhoneCheck, '인증번호를 입력해주세요');
            return;
        }

        let json = {phone : phone.value.replaceAll('-', ''), certificationNumber : certification.value};
        fetchPost('/changePhone/confirm', json, resultPhone);
    }) 
    const phone = document.querySelector('input[name="phones"]');
    phone.addEventListener('keyup',function(e){

        if (phone.isEqualNode(e.target)) {
            if (e.key === 'Backspace') {
                phone.value = removePhone(phone.value);
            } else {
                var str = removeNotNumber(phone.value);
                phone.value = addPhone(str);
            }

        }
    })


})

function modalFindPassword() {
    let modalList = document.querySelectorAll('.pop');
    modalList.forEach(el => el.classList.add('disabled'));
    
    const modal = document.querySelector('.modal');
    const modal_content = document.querySelector('.modal-content');
    
    insertModalSize('modal-find');
    modal_content.innerHTML = createFindPassword();
    modal.classList.remove(disabled);
}
function resultPhone(result) {
    if (result.result == 'ok') {
        alert(result.message);
        location.reload();
    }
    
}
function removePhone(str) {
    if (str.endsWith('-')) {
        return str.slice(0, -1);
    }
    return str;
}
function addPhone(str) {
    if (str.endsWith('-')) {
        return str;
    }
    if (str.length == 3) {
        return str.slice(0, 3) + '-' + str.slice(3);
    }
    if (str.length == 4) {
        return str.slice(0,3) + '-' + str.slice(3,4);
    }
    if (str.length == 8) {
        return str.slice(0, 8) + '-' + str.slice(8);
    }
    if (str.length == 9) {
        return str.slice(0, 8) + '-' + str.slice(8,9);
    }
    return str;
}
function removeNotNumber(text) {
    var newString = text.replace(/[^0-9\-]/, "");
    var distinctString = newString.replace(/-+/g, "-");
    if (distinctString.endsWith('-')) {
        return distinctString.slice(0, -1).replace(/-+/g, "-");
    }
    return distinctString;
}

function sendSMS(result) {
    console.log(result);
    if (result.result == 'ok') {
        if (clickLimit) {
            alert("잠시 후에 시도해주세요.");
            return;
        }
        var cPhone = document.querySelector('.confirmPhone');
        printTrue(cPhone, result.message);
        clearInterval(timerInterval);

        timerInterval = limitTimer();
        clickLimit = limitClick();
    }
}

function validPhone() {
    var cPhone = document.querySelector('.confirmPhone');
    if (checkPhone()){
        printTrue(cPhone, '');
        return null;
    } else {
        printFalse(cPhone, '입력정보가 잘못되었습니다');
        return document.querySelector('input[name="phones"]');
    }
}

function result(map) {
    if (map.result == 'fail') {
        if (map.message == null) {
            let currPwError = document.querySelector('#bfpw');
            let changePwError = document.querySelector('#cpw');
            let checkPwError = document.querySelector('#cpwc');
            
            currPwError.innerHTML = (map.nowPwError != null) ? map.nowPwError : '';
            changePwError.innerHTML = (map.changePwError != null) ? map.changePwError : '';
            checkPwError.innerHTML = (map.checkPwError != null) ? map.checkPwError : '';
        } else {
            alert(map.message);
            location.href = '/';
        }
    }
    if (map.result == 'ok') {
        alert(map.message);
        location.href = '/mypage';
    }
}

function printTrue(cTag, message) {
    if (cTag != null ){
        cTag.innerHTML = message;
        cTag.classList.remove(disabled)
        cTag.classList.remove("error")
    }
}


function printFalse(cTag, message) {
    if (cTag != null) {
        cTag.innerHTML = message;
        cTag.classList.add("error");
        cTag.classList.remove(disabled);
    }
}

function checkPhone() {
    var validPhobe = document.querySelector('input[name="phones"]');
    var phoneRegex = /^(010)-[0-9]{3,4}-[0-9]{4}$/;
    return phoneRegex.test(validPhobe.value);
}

function limitClick() {
    var seconds = 10;
    var timerId = setInterval(function(){
        // 시간 감소
        seconds--;
        // 시간이 0이면 타이머 중지
        if (seconds === 0) {
            clearInterval(timerId);
            clickLimit = undefined; // 타이머 중지 후 초기화
            return true;
        }
    }, 1000);

    return timerId; // 새로운 타이머 ID 반환
}

function limitTimer() {
    let timer = document.querySelector('.limitTime');
    var minutes = 5;
    var seconds = 0;
    var timerId = setInterval(function(){
        // 시간 감소
        seconds--;
    
        // 시간이 음수가 되면 분 감소
        if (seconds < 0) {
            minutes--;
            seconds = 59;
        }
        if (minutes == 0) {
            timer.style.color = 'red';
        }
    
        // 분과 초를 2자리 숫자로 표시
        var formattedMinutes = ('0' + minutes).slice(-2);
        var formattedSeconds = ('0' + seconds).slice(-2);
    
        // 타이머 업데이트
        timer.textContent = formattedMinutes + ':' + formattedSeconds;
    
        // 시간이 0이면 타이머 중지
        if (minutes === 0 && seconds === 0) {
            clearInterval(timerId);
            alert('타이머 종료!');
            return true;
        }
    }, 1000);

    return timerId; // 새로운 타이머 ID 반환
}