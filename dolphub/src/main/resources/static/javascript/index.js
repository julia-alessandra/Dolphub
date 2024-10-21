// Função para alternar a visibilidade dos detalhes do motivo e girar a seta
function toggleMotivo(motivoId) {
    const detalhe = document.getElementById(`motivo-${motivoId}`);
    const seta = document.getElementById(`seta-${motivoId}`);
    if (detalhe.style.display === "none" || detalhe.style.display === "") {
        detalhe.style.display = "block";
        seta.style.transform = "rotate(180deg)"; // Gira a seta para cima
    } else {
        detalhe.style.display = "none";
        seta.style.transform = "rotate(0deg)"; // Retorna a seta para a posição original
    }
}
