document.addEventListener("DOMContentLoaded", function () {
    const parte2 = document.getElementById("parte2");
    const botaoMostrarParte2 = document.getElementById("mostrar-parte-2");

    // Função para exibir a parte 2 do formulário
    botaoMostrarParte2.addEventListener("click", function () {
        parte2.classList.remove("hidden");
    });
});
