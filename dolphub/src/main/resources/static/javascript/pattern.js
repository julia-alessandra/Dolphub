
// botão do menu
var btnToggleMenu = document.querySelector('#btn-toggle-menu');
btnToggleMenu.addEventListener('click', ()=> {
    const sidebar = document.getElementById("sidebar");
    sidebar.classList.toggle("minimized");
});


// controle deslizante da dificuldade
function atualizarDificuldade() {
    var slider = document.getElementById("dificuldade-slider");
    var output = document.getElementById("dificuldadeTexto");
    var dificuldade = ["Fácil", "Médio", "Difícil", "Especialista"];
    output.innerHTML = dificuldade[slider.value];
}