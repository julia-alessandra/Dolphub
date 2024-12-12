function toggleForm(icon) {
    if (icon && icon.parentElement) {
        const container = icon.parentElement;
        if (container) {
            const form = container.querySelector('.formTitulo');
            if (form) {
                if (form.style.display === 'none') {
                    form.style.display = 'flex';
                    icon.classList.remove('fa-folder-plus');
                    icon.classList.add('fa-circle-xmark');
                } else {
                    form.style.display = 'none';
                    icon.classList.remove('fa-circle-xmark');
                    icon.classList.add('fa-folder-plus');
                }
            }
        }
    }
}
let deletarConta = document.querySelector("#btn-deletar-conta")
deletarConta.addEventListener("click", function() {
    var formDeletarConta = document.getElementById("form-deletar-conta");
    formDeletarConta.style.display = "block";
    deletarConta.remove();
});