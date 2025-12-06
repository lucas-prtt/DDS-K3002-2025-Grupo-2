function validarFormularioModalColeccion() {
    const titulo = document.getElementById('titulo-coleccion')
    const descripcion = document.getElementById('descripcion-coleccion')
    const algoritmo = document.getElementById('algoritmo-coleccion');

    // Obtener todos los criterios dinámicos
    const criteriosItems = Array.from(document.querySelectorAll('#criterios-container-crear-coleccion .criterio-item'));
    const tiposFuentes = Array.from(document.querySelectorAll('#fuentes-container-crear-coleccion .fuente-item select[id^="fuente-tipo-"]'));
    const nombresFuentes = Array.from(document.querySelectorAll('#fuentes-container-crear-coleccion .fuente-item select[id^="fuente-nombre-"]'));

    const inputsObligatoriosBasicos = [titulo, descripcion, algoritmo];

    // Validar cada criterio
    const criteriosParaValidar = [];
    criteriosItems.forEach((criterioItem) => {
        const criterioId = criterioItem.id.match(/criterio-(\d+)-/)[1];
        const tipoCriterio = document.getElementById(`criterio-tipo-${criterioId}-crear-coleccion`);
        criteriosParaValidar.push(tipoCriterio);

        if (tipoCriterio.value === 'DISTANCIA') {
            const lat = document.getElementById(`criterio-latitud-${criterioId}-crear-coleccion`);
            const lon = document.getElementById(`criterio-longitud-${criterioId}-crear-coleccion`);
            const dist = document.getElementById(`criterio-distancia-minima-${criterioId}-crear-coleccion`);
            criteriosParaValidar.push(lat, lon, dist);
        } else if (tipoCriterio.value === 'FECHA') {
            const fechaIni = document.getElementById(`criterio-fecha-inicial-${criterioId}-crear-coleccion`);
            const fechaFin = document.getElementById(`criterio-fecha-final-${criterioId}-crear-coleccion`);
            criteriosParaValidar.push(fechaIni, fechaFin);
        }
    });

    // Filtrar los nombres de fuentes para validar solo los que no son dinámicas o están visibles
    const nombresParaValidar = nombresFuentes.filter((select, index) => {
        const tipoFuente = tiposFuentes[index]?.value;
        return tipoFuente === "estatica" || tipoFuente === "proxy";
    });

    validarInputsObligatorios([...inputsObligatoriosBasicos, ...criteriosParaValidar, ...tiposFuentes, ...nombresParaValidar])

    return {
        titulo,
        descripcion,
        algoritmo,
        criteriosItems,
        tiposFuentes,
        nombresFuentes
    }
}

function limpiarModalColeccion(modal) {
    modal.querySelectorAll('input, select, textarea').forEach(campo => {
        campo.value = "";
        campo.classList.remove("form-not-completed");
        campo.classList.add("form-input");
    });

    document.getElementById('criterios-container-crear-coleccion').innerHTML = "";
    document.getElementById('fuentes-container-crear-coleccion').innerHTML = "";
}

function agregarCriterioColeccion(numeroCriterio, sufix) {
    const mensajeSinCriterios = document.getElementById(`sin-criterios-${sufix}`)
    if(!mensajeSinCriterios.classList.contains("hidden")) {
        mensajeSinCriterios.classList.add("hidden")
    }

    const criterioDiv = document.createElement('div');
    criterioDiv.className = 'criterio-item border border-gray-300 rounded-md py-2 px-3 bg-gray-50 mb-2';
    criterioDiv.id = `criterio-${numeroCriterio}-${sufix}`;

    criterioDiv.innerHTML = `
        <div class="flex justify-between items-center mb-1">
            <label for="criterio-tipo-${numeroCriterio}-${sufix}" class="block text-xs font-medium text-gray-600">Tipo de Criterio</label>
            <button id="eliminar-criterio-${numeroCriterio}-${sufix}" type="button" data-id="${numeroCriterio}" class="btn-eliminar-container">
                Eliminar
            </button>
        </div>

        <div class="space-y-2">
            <select id="criterio-tipo-${numeroCriterio}-${sufix}" class="form-input criterio-tipo-select" data-id="${numeroCriterio}">
                <option value="">Seleccione un criterio</option>
                <option value="DISTANCIA">Distancia</option>
                <option value="FECHA">Fecha</option>
            </select>

            <div id="campos-distancia-${numeroCriterio}-${sufix}" class="space-y-2 hidden">
                <div class="grid grid-cols-2 gap-2">
                    <div>
                        <label for="criterio-latitud-${numeroCriterio}-${sufix}" class="block text-xs font-medium text-gray-600 mb-1">
                            Latitud Base
                            <span class="form-obligatory-icon">*</span>
                        </label>
                        <input type="number" step="any" id="criterio-latitud-${numeroCriterio}-${sufix}" class="form-input" placeholder="-34.603722">
                    </div>
                    <div>
                        <label for="criterio-longitud-${numeroCriterio}-${sufix}" class="block text-xs font-medium text-gray-600 mb-1">
                            Longitud Base
                            <span class="form-obligatory-icon">*</span>
                        </label>
                        <input type="number" step="any" id="criterio-longitud-${numeroCriterio}-${sufix}" class="form-input" placeholder="-58.381592">
                    </div>
                </div>
                <div>
                    <label for="criterio-distancia-minima-${numeroCriterio}-${sufix}" class="block text-xs font-medium text-gray-600 mb-1">
                        Distancia Mínima (km)
                        <span class="form-obligatory-icon">*</span>
                    </label>
                    <input type="number" step="any" id="criterio-distancia-minima-${numeroCriterio}-${sufix}" class="form-input" placeholder="100">
                </div>
            </div>

            <div id="campos-fecha-${numeroCriterio}-${sufix}" class="space-y-2 hidden">
                <div>
                    <label for="criterio-fecha-inicial-${numeroCriterio}-${sufix}" class="block text-xs font-medium text-gray-600 mb-1">
                        Fecha Inicial
                        <span class="form-obligatory-icon">*</span>
                    </label>
                    <input type="datetime-local" id="criterio-fecha-inicial-${numeroCriterio}-${sufix}" class="form-input hover:cursor-pointer">
                </div>
                <div>
                    <label for="criterio-fecha-final-${numeroCriterio}-${sufix}" class="block text-xs font-medium text-gray-600 mb-1">
                        Fecha Final
                        <span class="form-obligatory-icon">*</span>
                    </label>
                    <input type="datetime-local" id="criterio-fecha-final-${numeroCriterio}-${sufix}" class="form-input hover:cursor-pointer">
                </div>
            </div>
        </div>
    `;

    document.getElementById(`criterios-container-${sufix}`).appendChild(criterioDiv);

    const removeBtn = document.getElementById(`eliminar-criterio-${numeroCriterio}-${sufix}`);
    const elemento = document.getElementById(`criterio-${numeroCriterio}-${sufix}`);

    removeBtn.addEventListener("click", () => {
        elemento.remove();
        // Mostrar mensaje si no quedan criterios
        const criteriosRestantes = document.querySelectorAll(`#criterios-container-${sufix} .criterio-item`);
        if (criteriosRestantes.length === 0) {
            mensajeSinCriterios.classList.remove("hidden");
        }
    });

    document.getElementById(`criterio-tipo-${numeroCriterio}-${sufix}`).addEventListener("change", () => {
        if(document.getElementById(`criterio-tipo-${numeroCriterio}-${sufix}`).value === "DISTANCIA") {
            document.getElementById(`campos-distancia-${numeroCriterio}-${sufix}`).classList.remove("hidden");
            document.getElementById(`campos-fecha-${numeroCriterio}-${sufix}`).classList.add("hidden");
        } else if(document.getElementById(`criterio-tipo-${numeroCriterio}-${sufix}`).value === "FECHA") {
            document.getElementById(`campos-fecha-${numeroCriterio}-${sufix}`).classList.remove("hidden");
            document.getElementById(`campos-distancia-${numeroCriterio}-${sufix}`).classList.add("hidden");
        } else {
            document.getElementById(`campos-distancia-${numeroCriterio}-${sufix}`).classList.add("hidden");
            document.getElementById(`campos-fecha-${numeroCriterio}-${sufix}`).classList.add("hidden");
        }
    })
}

function agregarFuenteColeccion(numeroFuente, sufix) {
    const mensajeSinFuentes = document.getElementById(`sin-fuentes-${sufix}`)
    if(!mensajeSinFuentes.classList.contains("hidden")) {
        mensajeSinFuentes.classList.add("hidden")
    }

    const fuenteDiv = document.createElement('div');
    fuenteDiv.className = 'fuente-item border border-gray-300 rounded-md py-2 px-3 bg-gray-50 mb-2';
    fuenteDiv.id = `fuente-${numeroFuente}-${sufix}`;

    fuenteDiv.innerHTML = `
        <div class="flex justify-between items-center mb-1">
            <label for="fuente-tipo-${numeroFuente}-${sufix}" class="block text-xs font-medium text-gray-600">Tipo</label>
            <button id="eliminar-fuente-${numeroFuente}-${sufix}" type="button" data-id="${numeroFuente}" class="btn-eliminar-container">
                Eliminar
            </button>
        </div>

        <div class="space-y-2">
            <select id="fuente-tipo-${numeroFuente}-${sufix}" class="form-input" data-id="${numeroFuente}">
                <option value="">Seleccione el tipo de fuente</option>
                <option value="estatica">Estática</option>
                <option value="dinamica">Dinámica</option>
                <option value="proxy">Proxy</option>
            </select>

            <div id="nombre-container-${numeroFuente}-${sufix}" class="hidden">
                <label for="fuente-nombre-${numeroFuente}-${sufix}" class="block text-xs font-medium text-gray-600 mb-1">
                    ID de la Fuente
                </label>
                <select id="fuente-nombre-${numeroFuente}-${sufix}" class="form-input">
                    <option value="">Cargando fuentes...</option>
                </select>
            </div>
        </div>
        `;
    document.getElementById(`fuentes-container-${sufix}`).appendChild(fuenteDiv);

    const removeBtn = document.getElementById(`eliminar-fuente-${numeroFuente}-${sufix}`);
    const elemento = document.getElementById(`fuente-${numeroFuente}-${sufix}`);

    removeBtn.addEventListener("click", () => {
        elemento.remove();
        const criteriosRestantes = document.querySelectorAll(`#fuentes-container-${sufix} .fuente-item`);
        if (criteriosRestantes.length === 0) {
            mensajeSinFuentes.classList.remove("hidden");
        }
    });
}

/*function agregarCriterioAColeccion(numeroCriterio) {
    const criterioDiv = document.createElement('div');
    criterioDiv.id = `criterio-${numeroCriterio}`;

    criterioDiv.innerHTML = `
        <div class="flex justify-between items-center mb-1">
            <span class="block text-xs font-medium text-gray-600">Tipo</span>
            <button id="eliminar-fuente-${numeroFuente}" type="button" data-id="${numeroFuente}" class="btn-eliminar">
                Eliminar
            </button>
        </div>

        <div class="space-y-2">
            <select id="fuente-tipo-${numeroFuente}" class="form-input" data-id="${numeroFuente}">
                <option value="">Seleccione el tipo de fuente</option>
                <option value="estatica">Estática</option>
                <option value="dinamica">Dinámica</option>
                <option value="proxy">Proxy</option>
            </select>

            <div id="nombre-container-${numeroFuente}" class="hidden">
                <label th:for="fuente-nombre-${numeroFuente}" class="block text-xs font-medium text-gray-600 mb-1">
                    Nombre
                    <span class="max-char-span">
						(Máx.255 caracteres)
					</span>
                </label>
                <input type="text" id="fuente-nombre-${numeroFuente}" maxlength="255"
                       class="form-input"
                       placeholder="Ingrese el nombre">
            </div>
        </div>
        `;
    document.getElementById('#fuentes-container-modal-coleccion').appendChild(fuenteDiv);

    const removeBtn = document.getElementById(`eliminar-fuente-${numeroFuente}`);
    const elemento = document.getElementById(`fuente-${numeroFuente}`);

    removeBtn.addEventListener("click", () => elemento.remove());
}*/

function autocompletarFuentesColeccion(coleccion, sufix) {
    coleccion.fuentes.forEach((fuente, index) => {
        agregarFuenteColeccion(index, sufix)

        document.getElementById(`eliminar-fuente-${index}-${sufix}`).classList.add("hidden")

        const tipoFuente = document.getElementById(`fuente-tipo-${index}-${sufix}`)
        tipoFuente.value = fuente.tipo

        if(fuente.tipo !== "dinamica") {
            const nombreFuente = document.getElementById(`fuente-nombre-${index}-${sufix}`)
            nombreFuente.value = fuente.id
            document.getElementById(`nombre-container-${index}-${sufix}`).classList.remove("hidden")
        }
    })
}

function autocompletarModalEditarColeccion(coleccion) {

    autocompletarFuentesColeccion(coleccion, 'editar-coleccion');

    window.coleccionActual = coleccion;
    window.fuentesOriginales = [...(coleccion.fuentes || [])];
    window.fuentesActuales = [...window.fuentesOriginales];
    window.operacionesPendientes = [];
    window.algoritmoOriginal = coleccion.tipoAlgoritmoConsenso;
    window.algoritmoActual = window.algoritmoOriginal;
    window.fuentesNuevas = []; // Para las fuentes agregadas con el nuevo sistema

    // Seleccionar algoritmo actual
    document.getElementById('algoritmo-editar-coleccion').value = coleccion.tipoAlgoritmoConsenso || '';

    // Limpiar container de nuevas fuentes
    document.getElementById('fuentes-container-editar-coleccion').innerHTML = '';

    // Renderizar fuentes actuales
    renderizarFuentesActuales();
    renderizarOperacionesPendientes();
}