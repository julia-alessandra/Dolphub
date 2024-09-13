

var btnToggleMenu = document.querySelector('#btn-toggle-menu');
btnToggleMenu.addEventListener('click', ()=> {
    const sidebar = document.getElementById("sidebar");
    sidebar.classList.toggle("minimized");
});
