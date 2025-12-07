document.addEventListener('DOMContentLoaded', async function () {
    const editarBtns = Array.from(document.querySelectorAll(".coleccion-card .btn-editar-coleccion"));
    const modal = document.getElementById("modal-editar-coleccion");
    const agregarFuenteBtn = document.getElementById("agregar-fuente-editar-coleccion")
    const closeBtn = document.getElementById("salir-editar-coleccion");
    const confirmBtn = document.getElementById("editar-coleccion")

    if(allElementsFound([editarBtns, modal, closeBtn, confirmBtn, agregarFuenteBtn], "editar colecciones")) {
        colecciones.forEach((coleccion, index) => {
            listenOpenModal(modal, editarBtns[index], () => {
                window.coleccionActual = structuredClone(coleccion);

                document.getElementById('algoritmo-editar-coleccion').value = coleccion.tipoAlgoritmoConsenso;
                coleccion.fuentes.forEach(fuente => cargarFuenteColeccion(fuente))

                const containerFuenteCreada = crearContainerFuenteColeccion(0);
                document.getElementById("fuente-nueva-container-editar-coleccion").appendChild(containerFuenteCreada)
                document.getElementById('eliminar-fuente-0').remove();
                listenCambiosTipoFuenteColeccion(0)
            });

            confirmBtn.addEventListener('click', async function(){
                await actualizarColeccion(coleccion);
            });
        });

        listenCloseModal(modal, closeBtn, () => limpiarModalEditarColeccion());

        document.getElementById("agregar-fuente-editar-coleccion").addEventListener("click", function () {
            const fuenteIngresada = {
                id: document.getElementById("fuente-nombre-0").value,
                alias: document.getElementById("fuente-nombre-0").textContent,
                tipo: document.getElementById("fuente-tipo-0").value
            }
            cargarFuenteColeccion(fuenteIngresada)
        });
    }
});

async function actualizarColeccion(coleccion) {
    try {
        mostrarCargando("editar-coleccion");

        const algoritmoConsenso = document.getElementById("algoritmo-editar-coleccion").value
        if(algoritmoConsenso !== coleccion.tipoAlgoritmoConsenso) {
            const response = await fetch(`http://localhost:8086/apiAdministrativa/colecciones/${coleccion.id}/algoritmo`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + jwtToken
                },
                body: JSON.stringify({ algoritmoConsenso })
            });

            if (!response.ok) {
                throw new Error('Error al actualizar el algoritmo de consenso');
            }
        }

        if(window.coleccionActual.fuentes.all(fuenteActual => coleccion.fuentes.some(fuente => fuente.id === fuenteActual.id))) {
            if(window.coleccionActual.fuentes.length !== coleccion.fuentes.length) {
                const fuentesNuevas = window.coleccionActual.fuentes.filter(fuenteActual => !coleccion.fuentes.some(fuente => fuente.id === fuenteActual.id))

                for (const fuenteNueva of fuentesNuevas) {
                    const response = await fetch(`http://localhost:8086/apiAdministrativa/colecciones/${coleccion.id}/fuentes`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': 'Bearer ' + jwtToken
                        },
                        body: JSON.stringify({ tipo: fuenteNueva.tipo, id: fuenteNueva.id })
                    });

                    if (!response.ok) {
                        throw new Error(`Error al agregar la fuente ${fuenteNueva.id}`);
                    }
                }
            }
        }

        fuentesDivs.forEach(div => {
            const id = div.id.replace('fuente-editar-', '');
            const tipo = document.getElementById(`fuente-tipo-editar-${id}`)?.value;

            if (tipo) {
                let fuenteId;
                const selectNombre = document.getElementById(`fuente-nombre-editar-${id}`);

                if (tipo === 'dinamica') {
                    // Para dinámica, obtener el ID del atributo data
                    fuenteId = selectNombre?.getAttribute('data-fuente-dinamica-id');

                    if (!fuenteId) {
                        throw new Error('No se pudo obtener el ID de la fuente dinámica');
                    }
                } else if (tipo === 'estatica' || tipo === 'proxy') {
                    // Para estática o proxy, usar el ID del select
                    fuenteId = selectNombre?.value;

                    if (!fuenteId || fuenteId.trim() === '') {
                        throw new Error(`Debe seleccionar una fuente ${tipo}`);
                    }
                }

                // Agregar a operaciones pendientes
                window.operacionesPendientes.push({
                    tipo: 'AGREGAR',
                    fuenteId: fuenteId,
                    tipoFuente: tipo
                });
            }
        });

        // 1. Actualizar algoritmo de consenso si cambió
        if (window.algoritmoActual !== window.algoritmoOriginal) {
            console.log('Actualizando algoritmo de consenso...');
            const responseAlgoritmo = await fetch(`http://localhost:8086/apiAdministrativa/colecciones/${window.coleccionActual.id}/algoritmo`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + jwtToken
                },
                body: JSON.stringify({ algoritmoConsenso: window.algoritmoActual })
            });

            if (!responseAlgoritmo.ok) {
                throw new Error('Error al actualizar el algoritmo de consenso');
            }
            console.log('✓ Algoritmo actualizado');
        }

        // 2. Ejecutar operaciones de fuentes
        for (const operacion of window.operacionesPendientes) {
            if (operacion.tipo === 'AGREGAR') {
                console.log(`Agregando fuente ${operacion.fuenteId}...`);
                const responseAgregar = await fetch(`http://localhost:8086/apiAdministrativa/colecciones/${window.coleccionActual.id}/fuentes`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + jwtToken
                    },
                    body: JSON.stringify({ tipo: operacion.tipoFuente, id: operacion.fuenteId })
                });

                if (!responseAgregar.ok) {
                    throw new Error(`Error al agregar la fuente ${operacion.fuenteId}`);
                }
                console.log(`✓ Fuente ${operacion.fuenteId} agregada`);
            } else if (operacion.tipo === 'QUITAR') {
                console.log(`Quitando fuente ${operacion.fuenteId}...`);
                const responseQuitar = await fetch(`http://localhost:8086/apiAdministrativa/colecciones/${window.coleccionActual.id}/fuentes/${operacion.fuenteId}`, {
                    method: 'DELETE',
                    headers: {
                        'Authorization': 'Bearer ' + jwtToken
                    }
                });

                if (!responseQuitar.ok) {
                    throw new Error(`Error al quitar la fuente ${operacion.fuenteId}`);
                }
                console.log(`✓ Fuente ${operacion.fuenteId} quitada`);
            }
        }

        alert("Colección actualizada con éxito");
        window.location.reload();
    } catch (error) {
        console.error(error);
        alert(error.message);
    } finally {
        ocultarCargando("editar-coleccion");
    }
}