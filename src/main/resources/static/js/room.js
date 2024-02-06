window.addEventListener('load', () => {

    const message = document.querySelector('#message');

    message.addEventListener('keyup', () => textareaResize(message));
    message.addEventListener('paste', () => textareaResize(message));
})

function textareaResize(message) {
    message.style.height = 'auto';
    message.style.height = (message.scrollHeight) + 'px';
}