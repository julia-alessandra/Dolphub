$(document).ready(function() {
    $('#tags').select2({
        placeholder: "Selecione os assuntos",
        allowClear: true,
        tags: true
    });
});

function adicionarNovaTag() {
    const novaTag = $('#novaTag').val().trim();
    if (novaTag) {
        const $tags = $('#tags');
        const optionExists = $tags.find('option').filter(function() {
            return $(this).text() === novaTag;
        }).length;

        if (!optionExists) {
            const newOption = new Option(novaTag, novaTag, true, true);
            $tags.append(newOption).trigger('change');
        }

        $('#novaTag').val(''); 
    }
}