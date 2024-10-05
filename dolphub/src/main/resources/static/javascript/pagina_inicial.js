let randomColor = "#"+((1<<24)*Math.random()|0).toString(16); 
let cards = document.querySelectorAll(".card-curso");

cards.forEach(c => {
    c.style.borderTop = randomColor;
});



