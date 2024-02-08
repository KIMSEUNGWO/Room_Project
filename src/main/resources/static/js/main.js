window.addEventListener('load', () => {

    const container = document.querySelector('.container');
    const search_wrapper = document.querySelector('.search-wrapper');
    container.addEventListener('click', (e) => {
        let target = e.target;
        if (notSearchOption(target)) {
            // searchOption.classList.remove('display');
            location.classList.remove('display');
            search_wrapper.classList.remove('display');
        }
    })


    const inputWord = document.querySelector('input[name="word"]');
    const searchOption = document.querySelector('.search-option');
    const location = document.querySelector('#location-option');
    inputWord.addEventListener('focus', () => {
        // searchOption.classList.add('display');
        location.classList.add('display');
        search_wrapper.classList.add('display');;
    })
})

function notSearchOption(target) {
    return !target.classList.contains('search-wrapper') &&
            !target.classList.contains('search-option') &&
            target.id != 'word' &&
            target.id != 'searchBtn';
}