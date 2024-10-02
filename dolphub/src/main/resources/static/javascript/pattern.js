
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


// minimizar tópicos
const topicosClicaveis = document.querySelectorAll('.title-topico');
topicosClicaveis.forEach((element) => {
    element.addEventListener('click', () => {
        element.classList.toggle('btn-minimized');
        const closestLiParent = element.closest('li');
        if (closestLiParent) {
            closestLiParent.classList.toggle('li-minimized');
        }
    });
});


//notificação
document.addEventListener('DOMContentLoaded', function () {
    const notification = document.getElementById('notification');
    if(notification) {
        const closeBtn = document.getElementById('close-btn');
        const borderAnimation = document.getElementById('border-animation');

        function closeNotification() {
            notification.classList.add('hidden');
        }
        
        setTimeout(() => {
            borderAnimation.style.width = '0'; 
        }, 0);

        setTimeout(() => {
            closeNotification();
        }, 5000); 

        closeBtn.addEventListener('click', closeNotification);
    }
});


// info texto mouse evt
// document.addEventListener("DOMContentLoaded", function () {
//     const infoElements = document.querySelectorAll('.info-element');
//     const tooltip = document.getElementById('info-tooltip');
//     if(tooltip) {

//         infoElements.forEach(element => {
//             element.addEventListener('mouseenter', function (event) {
//                 tooltip.innerText = this.getAttribute('data-info');
//                 tooltip.style.display = 'block';
//                 tooltip.style.left = `${event.pageX + 10}px`;
//                 tooltip.style.top = `${event.pageY + 10}px`;
//             });

//             element.addEventListener('mouseleave', function () {
//                 tooltip.style.display = 'none';
//             });
//         });
//     }
// });
document.addEventListener("DOMContentLoaded", function () {
    const infoElements = document.querySelectorAll('.info-element');
    const tooltip = document.getElementById('info-tooltip');

    infoElements.forEach(element => {
        element.addEventListener('mouseenter', function (event) {
            tooltip.innerText = this.getAttribute('data-info');
            tooltip.style.display = 'block';

            // Calcula a posição do elemento
            const rect = element.getBoundingClientRect();
            const tooltipWidth = tooltip.offsetWidth;
            const tooltipHeight = tooltip.offsetHeight;

            // Define a posição do tooltip
            const left = rect.left + (rect.width / 2) - (tooltipWidth / 2);
            const top = rect.top - tooltipHeight - 10; // Para que fique acima do elemento

            // Ajusta a posição se o tooltip sair da tela
            if (left < 0) {
                tooltip.style.left = '10px'; // Ajuste se estiver muito à esquerda
            } else if (left + tooltipWidth > window.innerWidth) {
                tooltip.style.left = `${window.innerWidth - tooltipWidth - 10}px`; // Ajuste se estiver muito à direita
            } else {
                tooltip.style.left = `${left}px`;
            }

            if (top < 0) {
                tooltip.style.top = `${rect.bottom + 10}px`; // Posiciona abaixo do elemento se sair da tela
            } else {
                tooltip.style.top = `${top}px`;
            }
        });

        element.addEventListener('mouseleave', function () {
            tooltip.style.display = 'none';
        });
    });
});