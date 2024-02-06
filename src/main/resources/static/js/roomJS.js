
window.addEventListener('load', () => {

    const mores = document.querySelectorAll('.more');
    mores.forEach(more => {
        more.addEventListener('click', () => {
            let moreMenu = more.children.namedItem('more-menu');
            moreMenu.classList.toggle('disabled');
            clearMoreMenu(moreMenu);
        })
    })
    
})

function clearMoreMenu(nowMoreMenu) {
    let moreMenus = document.querySelectorAll('.more-menu');
    moreMenus.forEach(moreMenu => {
        if (moreMenu != nowMoreMenu) {
            moreMenu.classList.add('disabled')
        }
    });
}