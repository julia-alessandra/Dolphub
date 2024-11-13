function filtrarCursos() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const cards = document.querySelectorAll('.card-item');
    cards.forEach(function(card) {
        const cursoNome = card.querySelector('.titulo-curso').innerText.toLowerCase();
        const professorNome = card.querySelector('.prof-curso').innerText.toLowerCase();
        if (cursoNome.includes(searchInput) || professorNome.includes(searchInput)) {
            card.style.display = ''; 
        } else {
            card.style.display = 'none'; 
        }
    });
}