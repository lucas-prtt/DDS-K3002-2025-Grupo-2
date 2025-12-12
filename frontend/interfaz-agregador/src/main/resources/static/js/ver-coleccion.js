document.addEventListener('DOMContentLoaded', async function () {
    const verBtns = Array.from(document.querySelectorAll(".coleccion-card .btn-ver-coleccion"));
    const modal = document.getElementById("modal-ver-coleccion");
    const closeBtn = document.getElementById("salir-ver-coleccion")

    if(allElementsFound([verBtns, modal], "ver colección")) {
        colecciones.forEach((coleccion, index) => {
            listenOpenModal(modal, verBtns[index], async () => await autocompletarModalVerColeccion(coleccion))
        })
        listenCloseModal(modal, closeBtn, () => limpiarModalColeccion(modal, 'ver-coleccion'))
    }
});

async function autocompletarModalVerColeccion(coleccion) {
    const listaBotonesVerColeccion = Array.from(document.querySelectorAll(".btn-ver-coleccion button"))
    listaBotonesVerColeccion.forEach(boton => boton.disabled = true)

    const spinner = document.getElementById(`spinner-ver-coleccion-${coleccion.id}`);
    const verBtn = document.getElementById(`ver-coleccion-${coleccion.id}`);

    try {
        spinner.classList.remove("hidden");
        verBtn.classList.add("hidden");

        document.getElementById('titulo-ver-coleccion').textContent  = coleccion.titulo
        document.getElementById('descripcion-ver-coleccion').textContent  = coleccion.descripcion
        document.getElementById('algoritmo-ver-coleccion').textContent  = coleccion.tipoAlgoritmoConsenso;

        await autocompletarCriteriosColeccion(coleccion, 'ver-coleccion');

        autocompletarFuentesColeccion(coleccion, 'ver-coleccion');
    } catch (error) {
        console.error(error);
        alert(error.message);
    } finally {
        listaBotonesVerColeccion.forEach(boton => boton.disabled = false)

        spinner.classList.add("hidden");
        verBtn.classList.remove("hidden");
    }
}