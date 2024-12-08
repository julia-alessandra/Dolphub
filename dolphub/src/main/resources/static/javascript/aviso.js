const avisoForm = document.getElementById("avisoForm");
const avisosContainer = document.getElementById("avisos");

avisoForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const titulo = document.getElementById("titulo_aviso").value;
    const mensagem = document.getElementById("mensagem_aviso").value;
    const url = avisoForm.getAttribute("action");

    try {
        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ titulo, mensagem })
        });

        if (response.ok) {
            const aviso = await response.json(); // O backend retorna o aviso salvo
            adicionarAviso(aviso);
            avisoForm.reset(); // Limpar o formulário após o envio
        } else {
            console.error("Erro ao enviar o aviso.");
        }
    } catch (error) {
        console.error("Erro:", error);
    }
});

function adicionarAviso(aviso) {
    const avisoCard = document.createElement("div");
    avisoCard.className = "aviso-card";

    const titulo = document.createElement("div");
    titulo.className = "aviso-titulo";
    titulo.textContent = aviso.titulo;

    const mensagem = document.createElement("div");
    mensagem.className = "aviso-mensagem";
    mensagem.textContent = aviso.mensagem;

    avisoCard.appendChild(titulo);
    avisoCard.appendChild(mensagem);

    avisosContainer.prepend(avisoCard); // Adicionar no topo da lista
}