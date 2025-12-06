document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById("modal-coleccion")
    const modalTitleHeader = document.getElementById("modal-header-crear-coleccion")
    const openBtn = document.getElementById("menu-crear-coleccion")
    const agregarCriterioBtn = document.getElementById("agregar-criterio-crear-coleccion")
    const agregarFuenteBtn = document.getElementById("agregar-fuente-crear-coleccion")
    const confirmBtn = document.getElementById("crear-coleccion");
    const closeBtn = document.getElementById("salir-crear-coleccion");

    if(allElementsFound([modal, modalTitleHeader, openBtn, agregarCriterioBtn, agregarFuenteBtn, confirmBtn, closeBtn], "crear colección")) {
        listenOpenModal(modal, openBtn, () => {
            document.getElementById("dropdown-menu").classList.add("hidden")

            const mensajeSinCriterios = document.getElementById("sin-criterios-crear-coleccion")
            if(mensajeSinCriterios.classList.contains("hidden")) {
                mensajeSinCriterios.classList.remove("hidden")
            }

            const mensajeSinFuentes = document.getElementById("sin-fuentes-crear-coleccion")
            if(mensajeSinFuentes.classList.contains("hidden")) {
                mensajeSinFuentes.classList.remove("hidden")
            }

            modalTitleHeader.classList.remove("hidden")
            agregarCriterioBtn.classList.remove("hidden")
            agregarFuenteBtn.classList.remove("hidden")
            confirmBtn.parentElement.classList.remove("hidden")
            closeBtn.parentElement.classList.remove("hidden")
        })
        listenCloseModal(modal, closeBtn, () => {
            limpiarModalColeccion(modal)

            const mensajeSinCriterios = document.getElementById("sin-criterios-crear-coleccion")
            if(!mensajeSinCriterios.classList.contains("hidden")) {
                mensajeSinCriterios.classList.add("hidden")
            }

            const mensajeSinFuentes = document.getElementById("sin-fuentes-crear-coleccion")
            if(!mensajeSinFuentes.classList.contains("hidden")) {
                mensajeSinFuentes.classList.add("hidden")
            }

            modalTitleHeader.classList.add("hidden")
            agregarCriterioBtn.classList.add("hidden")
            agregarFuenteBtn.classList.add("hidden")
            confirmBtn.parentElement.classList.add("hidden")
            closeBtn.parentElement.classList.add("hidden")
        })
        listenAgregarCriterioModalColeccion(agregarCriterioBtn);
        listenAgregarFuenteModalColeccion(agregarFuenteBtn);
        listenCamposFuenteModalColeccion();

        confirmBtn.addEventListener("click", async function() {
            const inputsObligatorios = validarFormularioModalColeccion()

            if(!document.querySelector('#form-modal-coleccion .form-not-completed') && document.getElementById("sin-criterios-crear-coleccion").classList.contains("hidden")) {
                await crearColeccion(inputsObligatorios)
            }
        })
    }
});

async function crearColeccion(inputsObligatorios) {
    try {
        mostrarCargando("crear-coleccion");

        const payload = getPayloadColeccion(inputsObligatorios)
        console.log("Enviando Payload:", JSON.stringify(payload, null, 2));

        const response = await fetch('http://localhost:8086/apiAdministrativa/colecciones', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', 'Authorization' : 'Bearer ' + jwtToken },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const errorText = await response.text()
            throw new Error(errorText ? `Error al crear la colección:\n${errorText}` : "Error al crear la colección")
        }

        alert("Colección creada con éxito");
        document.getElementById("salir-crear-coleccion").click();
        window.location.reload()
    } catch (error) {
        console.error(error);
        alert(error.message);
    } finally {
        ocultarCargando("crear-coleccion");
    }
}