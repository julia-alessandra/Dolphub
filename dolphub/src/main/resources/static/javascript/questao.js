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



// responder questão
// document.addEventListener("DOMContentLoaded", () => {
//     const questaoForms = document.querySelectorAll(".questao-container");

//     questaoForms.forEach((form) => {
//         const responderBtn = form.querySelector(".responder-btn");
//         responderBtn.addEventListener("click", async () => {
//             const questaoId = form.dataset.questaoId; 
//             const selectedOption = form.querySelector("input[name='resposta']:checked");

//             if (!selectedOption) {
//                 alert("Por favor, selecione uma resposta.");
//                 return;
//             }

//             const alternativaId = selectedOption.value;

//             try {
//                 // Envia a requisição assíncrona
//                 const listaQuestoesContainer = document.getElementById('lista-questoes-container');
//                 const responderQuestaoUrl = listaQuestoesContainer.dataset.responderUrl;
//                 console.log(responderQuestaoUrl);
//                 const response = await fetch(responderQuestaoUrl, {
//                     method: "POST",
//                     headers: {
//                         "Content-Type": "application/json",
//                     },
//                     body: JSON.stringify({ questaoId, alternativaId }),
//                 });

//                 if (response.ok) {
//                     const result = await response.json();

//                     // Atualiza a interface da questão atual
//                     const statusMensagem = form.querySelector(".status-mensagem");
//                     if (!statusMensagem) {
//                         const mensagem = document.createElement("div");
//                         mensagem.classList.add("status-mensagem");
//                         mensagem.style.marginTop = "10px";
//                         mensagem.style.fontWeight = "bold";

//                         if (result.respostaCorreta) {
//                             mensagem.textContent = "✔ " + result.mensagem;
//                             mensagem.style.color = "green";
//                         } else {
//                             mensagem.textContent = "✘ " + result.mensagem;
//                             mensagem.style.color = "red";
//                         }
//                         form.appendChild(mensagem);
//                     } else {
//                         statusMensagem.textContent = result.respostaCorreta
//                             ? "✔ " + result.mensagem
//                             : "✘ " + result.mensagem;
//                         statusMensagem.style.color = result.respostaCorreta ? "green" : "red";
//                     }
//                 } else {
//                     alert("Erro ao processar a resposta. Tente novamente.");
//                 }
//             } catch (error) {
//                 console.error("Erro:", error);
//                 alert("Erro de conexão. Por favor, tente novamente.");
//             }
//         });
//     });
// });

document.addEventListener('DOMContentLoaded', () => {
    var csrfToken;
    const csrfTokenO = document.querySelector('meta[name="_csrf"]');
    if(csrfTokenO == null) return;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    console.log('CSRF Header:', csrfHeader); 
    console.log('CSRF Token:', csrfToken);

    document.querySelectorAll('.responder-btn').forEach(button => {
        button.addEventListener('click', event => {
            event.preventDefault(); 

            const questaoContainer = event.target.closest('.questao-container');
            console.log(questaoContainer);
            const questaoId = questaoContainer.dataset.id;
            console.log(questaoId);

            const alternativaSelecionada = questaoContainer.querySelector('input[name="resposta"]:checked');
            if (!alternativaSelecionada) {
                alert('Por favor, selecione uma alternativa antes de enviar.');
                return;
            }

            const alternativaId = alternativaSelecionada.value;

            const listaQuestoesContainer = document.getElementById('lista-questoes-container');
            const responderQuestaoUrl = listaQuestoesContainer.dataset.responderUrl;
            console.log(responderQuestaoUrl);
            fetch(responderQuestaoUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                },
                body: JSON.stringify({
                    questaoId: questaoId,
                    alternativaId: alternativaId
                })
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Erro ao responder questão. Código: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                if (data.success) {
                    console.log('Resposta enviada com sucesso!');
                    questaoContainer.style.opacity = '0.6'; 
                    questaoContainer.querySelectorAll('input').forEach(input => input.disabled = true); 
                    event.target.disabled = true; 
                    if (data.ver === 'true') {
                        questaoContainer.style.border = '2px solid green'; 
                    } else if (data.ver === 'false') {
                        questaoContainer.style.border = '2px solid red'; 
                    }
                } else {
                    console.log('Erro ao enviar a resposta: ' + data.mensagem);
                    questaoContainer.style.opacity = '0.6'; 
                    questaoContainer.querySelectorAll('input').forEach(input => input.disabled = true); 
                    event.target.disabled = true;

                    if (data.ver === 'true') {
                        questaoContainer.style.border = '2px solid green'; 
                    } else if (data.ver === 'false') {
                        questaoContainer.style.border = '2px solid red'; 
                    }
                }
            })
            .catch(error => {
                console.error('Erro:', error);
                alert('Erro ao processar sua resposta. Tente novamente mais tarde.');
            });
        });
    });
});


function validarAlternativas() {
    const alternativas = document.querySelectorAll('input[name="verificacaoAlternativa[]"]:checked');
    
    if (alternativas.length === 0) {
        alert("Por favor, selecione pelo menos uma alternativa.");
        return false;
    }
    
    return true;
}