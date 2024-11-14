//imgs

const caminhoImagens = "imgs/perfil/",
    nomeImagens = [
        'perfil1.png',
        'perfil2.png',
        'perfil4.png',
        'perfil5.png'
    ];

let botao = document.querySelector("#modifica");
let imagem = document.querySelector("#imgPerfil");

function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min) + min);
}

let numeroAleatorio = getRandomInt(0, 4);

botao.addEventListener("click", function () {
    let numeroAleatorio = getRandomInt(0, nomeImagens.length);
    imagem.src = caminhoImagens + nomeImagens[numeroAleatorio];
    console.log(caminhoImagens + nomeImagens[numeroAleatorio]);
});

let deletarConta = document.querySelector("#btn-deletar-conta")
deletarConta.addEventListener("click", function() {
    var formDeletarConta = document.getElementById("form-deletar-conta");
    formDeletarConta.style.display = "block";
    deletarConta.remove();
});