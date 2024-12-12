



document.addEventListener('DOMContentLoaded', () => {
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    console.log('CSRF Header:', csrfHeader);
    console.log('CSRF Token:', csrfToken);

    const enviarBtn = document.getElementById('enviar-respostas-btn'); // Botão para enviar todas as respostas.

    enviarBtn.addEventListener('click', event => {
        event.preventDefault();

        const questoesContainer = document.querySelectorAll('.questao-container');
        const respostas = []; // Array para acumular as respostas.

        let valid = true; // Verifica se todas as questões têm resposta.

        questoesContainer.forEach(questaoContainer => {
            const questaoId = Number(questaoContainer.dataset.id);
        
            // Atualiza a busca do input radio, incluindo o ID da questão no nome.
            const alternativaSelecionada = questaoContainer.querySelector(`input[name="resposta-${questaoId}"]:checked`);
        
            if (!alternativaSelecionada) {
                alert(`Por favor, selecione uma alternativa para a questão ${questaoId}.`);
                valid = false;
                return;
            }
        
            const alternativaId = Number(alternativaSelecionada.value);
        
            respostas.push({
                questaoId: questaoId,
                alternativaId: alternativaId
            });
        });
        

        if (!valid) return; // Se alguma questão não foi respondida, cancela o envio.

        const listaQuestoesContainer = document.getElementById('lista-questoes-container');
        const responderQuestoesUrl = listaQuestoesContainer.dataset.responderUrl;
        console.log('Respostas enviadas:', JSON.stringify({ respostas: respostas }));

        // Envia o array de respostas ao backend.
        fetch(responderQuestoesUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify({
                respostas: respostas
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Erro ao enviar respostas. Código: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                if (data.success) {
                    console.log('Respostas enviadas com sucesso!');

                    // Desabilita todas as questões após o envio bem-sucedido.
                    questoesContainer.forEach(questaoContainer => {
                        questaoContainer.style.opacity = '0.6';
                        questaoContainer.querySelectorAll('input').forEach(input => input.disabled = true);
                    });

                    // Exibe feedback visual para cada questão.
                    data.resultados.forEach(resultado => {
                        const questaoContainer = document.querySelector(`.questao-container[data-id="${resultado.questaoId}"]`);
                        if (resultado.ver === 'true') {
                            questaoContainer.style.border = '2px solid green';
                        } else if (resultado.ver === 'false') {
                            questaoContainer.style.border = '2px solid red';
                        }
                    });
                } else {
                    console.log('Erro ao processar respostas: ' + data.mensagem);
                    alert('Erro ao processar suas respostas. Tente novamente mais tarde.');
                }
            })
            .catch(error => {
                console.error('Erro:', error);
                alert('Erro ao enviar respostas. Tente novamente mais tarde.');
            });
    });
});
