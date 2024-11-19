async function addTag() {
    const input = document.getElementById("tagInput");
    const tagName = input.value.trim();

    if (tagName === "") {
        alert("O nome da tag não pode estar vazio!");
        return;
    }

    const response = await fetch("/api/tags", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ nome: tagName })
    });

    if (response.ok) {
        input.value = "";
        loadTags();
    } else {
        alert("Erro ao adicionar a tag!");
    }
}

async function loadTags() {
    const container = document.getElementById("tagsContainer");
    const response = await fetch("/api/tags");

    if (response.ok) {
        const tags = await response.json();
        container.innerHTML = ""; 

        tags.forEach(tag => {
            const tagElement = document.createElement("div");
            tagElement.textContent = tag.nome;
            tagElement.className = "tag";
            container.appendChild(tagElement);
        });
    } else {
        alert("Erro ao carregar as tags!");
    }
}

document.addEventListener("DOMContentLoaded", loadTags);
