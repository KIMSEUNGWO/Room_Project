window.addEventListener('load', () => {
    const trigger = document.querySelector('#btn-mypage');
    trigger.addEventListener('click', () => {
        al('테스트', '테스트 중입니다.');
    })
    
})

function al(title, message) {
    const alert = document.querySelector('.alert');
    const alertTitle = document.querySelector('.alert-title');
    const alertContent = document.querySelector('.alert-content');

    alertTitle.innerText = title;;
    alertContent.innerText = message;

    alert.classList.add('display');
    setTimeout(() => {
        alert.classList.remove('display');
    }, 3000);
}