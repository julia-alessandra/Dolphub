function adicionarAlternativa() {
            const alternativasDiv = document.getElementById("alternativas");
            const alternativaCount = alternativasDiv.children.length;

            if (alternativaCount < 5) {
                const alternativaDiv = document.createElement("div");
                alternativaDiv.classList.add('alternativa');
                alternativaDiv.innerHTML = `
                    <input type="checkbox" class="check" name="verificacaoAlternativa[]" value="${alternativaCount}" />
                    <input type="text" name="descricaoAlternativa[]" required />
                `;
                alternativasDiv.appendChild(alternativaDiv);
                gerenciarAlternativas();
            } else {
                alert("Só é permitido adicionar até 5 alternativas.");
            }
        }

        function removerAlternativa() {
            const alternativasDiv = document.getElementById("alternativas");
            if (alternativasDiv.children.length > 2) {
                alternativasDiv.lastChild.remove();
            } else {
                alert("Uma questão deve ter pelo menos 2 alternativas.");
            }
        }

function gerenciarAlternativas() {
    var checkboxes = document.querySelectorAll('input[class="check"]');
    checkboxes.forEach((checkbox) => {
        checkbox.addEventListener('click', () => {
            checkboxes.forEach((otherCheckbox) => {
                if (otherCheckbox !== checkbox) {
                    otherCheckbox.checked = false;
                }
            });
        });
    });
}
document.addEventListener('DOMContentLoaded', gerenciarAlternativas);