
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



document.addEventListener("DOMContentLoaded", function () {
    const infoElements = document.querySelectorAll('.info-element');
    const tooltip = document.getElementById('info-tooltip');

    infoElements.forEach(element => {
        element.addEventListener('mouseenter', function (event) {
            tooltip.innerText = this.getAttribute('data-info');
            tooltip.style.display = 'block';

            const rect = element.getBoundingClientRect();
            const tooltipWidth = tooltip.offsetWidth;
            const tooltipHeight = tooltip.offsetHeight;

            const left = rect.left + (rect.width / 2) - (tooltipWidth / 2);
            const top = rect.top - tooltipHeight - 10; 

            if (left < 0) {
                tooltip.style.left = '10px'; 
            } else if (left + tooltipWidth > window.innerWidth) {
                tooltip.style.left = `${window.innerWidth - tooltipWidth - 10}px`; 
            } else {
                tooltip.style.left = `${left}px`;
            }

            if (top < 0) {
                tooltip.style.top = `${rect.bottom + 10}px`; 
            } else {
                tooltip.style.top = `${top}px`;
            }
        });

        element.addEventListener('mouseleave', function () {
            tooltip.style.display = 'none';
        });
    });
});


document.addEventListener('DOMContentLoaded', function() {
    const menu = document.getElementById("menu");
    const perfil = document.getElementById("perfil");
  
    perfil.addEventListener("click", function(event) {
      event.stopPropagation();
      menu.classList.toggle("hidden");
    });
  
    // Fecha o menu se clicar fora dele
    document.addEventListener("click", function(event) {
      if (!menu.contains(event.target) && event.target !== perfil) {
        menu.classList.add("hidden");
      }
    });
  });
  

  //confirmação janela
  function attachConfirmation(formId) {
    const form = document.getElementById(formId);
    const modal = document.getElementById('confirmation-modal');
    const confirmBtn = document.getElementById('confirm-btn');
    const cancelBtn = document.getElementById('cancel-btn');

    form.addEventListener('submit', function (event) {
        event.preventDefault();
        modal.style.display = 'flex';

        confirmBtn.onclick = function () {
            modal.style.display = 'none';
            form.submit();
        };

        cancelBtn.onclick = function () {
            modal.style.display = 'none';
        };
    });
}

// para links
function attachLinkConfirmation(linkClass) {
    const links = document.querySelectorAll(`.${linkClass}`);
    const modal = document.getElementById('confirmation-modal');
    if(modal == null) {
        return;
    }
    const confirmBtn = document.getElementById('confirm-btn');
    const cancelBtn = document.getElementById('cancel-btn');

    links.forEach(link => {
        link.addEventListener('click', function (event) {
            event.preventDefault();
            const href = this.href;
            modal.style.display = 'flex';

            confirmBtn.onclick = function () {
                modal.style.display = 'none';
                window.location.href = href;
            };

            cancelBtn.onclick = function () {
                modal.style.display = 'none';
            };
        });
    });
}

