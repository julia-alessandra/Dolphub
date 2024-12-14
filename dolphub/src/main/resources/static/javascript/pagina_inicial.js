

const searchInput = document.getElementById('searchInput');
const cursosList = document.getElementById('listaSeusCursos');
const cursoItems = cursosList.getElementsByTagName('li');

let numCriados = document.getElementById("numCriados");
if(numCriados != null) {
    numCriados.innerHTML = cursoItems.length
}

function filterCourses() {
    const query = searchInput.value.toLowerCase(); 
    for (let i = 0; i < cursoItems.length; i++) {
        const tituloCurso = cursoItems[i].querySelector('.titulo-curso').textContent.toLowerCase();
        
        if (tituloCurso.includes(query)) {
            cursoItems[i].style.display = ''; 
        } else {
            cursoItems[i].style.display = 'none'; 
        }
    }
}

searchInput.addEventListener('input', filterCourses);