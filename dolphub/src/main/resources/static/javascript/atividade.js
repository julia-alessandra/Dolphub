
let botaoConfig = document.querySelector("#botaoConfigAtv")

botaoConfig.addEventListener("click", function () {
    var formConfig = document.getElementById("formConfig");
    if (formConfig.style.display = 'none')
        formConfig.style.display = "block";
    else
        formConfig.style.display = "none";
});