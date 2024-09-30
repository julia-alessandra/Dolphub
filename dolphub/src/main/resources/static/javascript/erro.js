function toggleErrorDetails() {
    const detalheErro = document.getElementById('detalhe-erro');
    const toggleButton = document.getElementById('toggleButton');

    if (detalheErro.classList.contains('minimized')) {
        detalheErro.classList.remove('minimized');
        toggleButton.classList.add('inv');
    } else {
        detalheErro.classList.add('minimized');
        toggleButton.classList.remove('inv');
    }
}