function allElementsFound(elementsList, desiredAction) {
    if(elementsList.some(e => e === null)) {
        console.log("No se pudo encontrar algún elemento para " + desiredAction)
    }

    return !elementsList.some(e => e === null);
}

function validarInputsObligatorios(inputs) {
    inputs.forEach(input => {
        let invalid = false;

        if(!input.classList.contains("hidden") && !input.closest(".hidden")) {
            if (input.type === "checkbox" || input.type === "radio") {
                invalid = !input.checked;
            } else if (input.type === "file") {
                invalid = input.files.length === 0;
            } else {
                invalid = input.value.trim() === "";
            }
        }

        if (invalid) {
            input.classList.add("form-not-completed");
            input.classList.remove("form-input");
        } else {
            input.classList.remove("form-not-completed");
            input.classList.add("form-input");
        }
    });
}