function limpiarModalHecho(multimediaCountObject) {
    document.getElementById('form-modal-hecho').reset();
    document.getElementById('crear-hecho-multimedia-container').innerHTML = '';
    multimediaCountObject.multimediaCount = 0;
}

function recopilarMultimediasEditarHecho() {
    const urls = [];
    const container = document.getElementById('crear-hecho-multimedia-container');
    const multimediaItems = container.querySelectorAll('.multimedia-item');

    multimediaItems.forEach(item => {
        const id = item.id.split('-')[1];
        const urlInput = document.getElementById(`url-${id}`);
        if (urlInput && urlInput.value.trim()) { // Solo añadir si no está vacío
            urls.push(urlInput.value.trim());
        }
    });

    return urls; // Devuelve un array de strings (URLs)
}