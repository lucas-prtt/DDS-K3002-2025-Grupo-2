document.addEventListener('DOMContentLoaded', function () {
    solicitudes.forEach(solicitud => {
        if(solicitud.estado.estado.toLowerCase() === 'pendiente') {
            document.getElementById(`nombre-aprobar-solicitud-${solicitud.id}`).addEventListener("click", async function() {
                await aprobarSolicitud(solicitud.id);
            })

            document.getElementById(`nombre-rechazar-solicitud-${solicitud.id}`).addEventListener("click", async function() {
                await rechazarSolicitud(solicitud.id);
            })
        }
    })
})

async function aprobarSolicitud(solicitudId) {
    if (!confirm('¿Estás seguro de que deseas aprobar esta solicitud?')) {
        return;
    }

    try {
        mostrarCargando(`aprobar-solicitud-${solicitudId}`);

        const response = await fetch(apiAdministrativaUrl + `/solicitudes/${solicitudId}/estado`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json', 'Authorization' : 'Bearer ' + jwtToken },
            body: JSON.stringify({ nuevoEstado: 'ACEPTADA', adminId: userId }),
        })

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText ? `Error al aprobar la solicitud:\n${errorText}` : "Error al aprobar la solicitud")
        }

        alert("Se ha aceptado la solicitud correctamente");
        window.location.reload();
    } catch (error) {
        console.error(error);
        alert(error.message);
    } finally {
        ocultarCargando(`aprobar-solicitud-${solicitudId}`);
    }
}

async function rechazarSolicitud(solicitudId) {
    if (!confirm('¿Estás seguro de que deseas rechazar esta solicitud?')) {
        return;
    }

    try {
        mostrarCargando(`rechazar-solicitud-${solicitudId}`);

        const response = await fetch(apiAdministrativaUrl + `/solicitudes/${solicitudId}/estado`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json', 'Authorization' : 'Bearer ' + jwtToken },
            body: JSON.stringify({ nuevoEstado: 'RECHAZADA', adminId: userId }),
        })

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText ? `Error al rechazar la solicitud:\n${errorText}` : "Error al rechazar la solicitud")
        }

        alert("Se ha rechazado la solicitud correctamente");
        window.location.reload();
    } catch (error) {
        console.error(error);
        alert(error.message);
    } finally {
        ocultarCargando(`rechazar-solicitud-${solicitudId}`);
    }
}