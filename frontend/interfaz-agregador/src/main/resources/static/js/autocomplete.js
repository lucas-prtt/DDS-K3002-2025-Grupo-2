/**
 * Inicializa la funcionalidad de autocompletado para un input
 * @param {string} inputId - ID del input
 * @param {string} endpoint - URL del endpoint de autocompletado
 * @param {number} debounceDelay - Retraso del debounce en ms (por defecto 300)
 */
function initializeAutocomplete(inputId, endpoint, debounceDelay = 300) {
    const input = document.getElementById(inputId);
    if (!input) {
        console.warn(`No se encontró el input con id: ${inputId}`);
        return;
    }

    // Crear contenedor de sugerencias con posicionamiento fixed
    const suggestionsContainer = document.createElement("ul");
    const containerId = `${inputId}-suggestions`;
    suggestionsContainer.id = containerId;
    suggestionsContainer.className = "fixed bg-white border border-gray-300 rounded-md shadow-lg text-black max-h-48 overflow-y-auto";
    suggestionsContainer.style.minWidth = "200px";
    suggestionsContainer.style.display = "none";
    suggestionsContainer.style.zIndex = "10000"; // Mayor que el modal (9999)

    // Agregar al body para evitar problemas con overflow: hidden
    document.body.appendChild(suggestionsContainer);

    // Función para actualizar posición y ancho del dropdown
    const updateDropdownPosition = () => {
        // Solo actualizar si el input es visible
        if (input.offsetParent === null) {
            return false;
        }

        const rect = input.getBoundingClientRect();
        // Asegurar que el rectángulo tenga dimensiones válidas
        if (rect.width === 0 || rect.height === 0) {
            return false;
        }

        suggestionsContainer.style.width = `${rect.width}px`;
        suggestionsContainer.style.left = `${rect.left}px`;
        suggestionsContainer.style.top = `${rect.bottom + 4}px`;
        return true;
    };

    // Actualizar posición cuando la ventana cambia o hay scroll
    window.addEventListener('resize', updateDropdownPosition);
    window.addEventListener('scroll', updateDropdownPosition, true);

    let debounceTimer;

    input.addEventListener("input", () => {
        clearTimeout(debounceTimer);
        const query = input.value.trim();

        if (!query) {
            suggestionsContainer.innerHTML = "";
            suggestionsContainer.style.display = "none";
            return;
        }

        debounceTimer = setTimeout(async () => {
            try {
                const response = await fetch(`${endpoint}?search=${encodeURIComponent(query)}&limit=5`);
                if (!response.ok) {
                    throw new Error(`Error en la respuesta: ${response.status}`);
                }
                const data = await response.json();

                suggestionsContainer.innerHTML = "";
                if (data.length === 0) {
                    suggestionsContainer.style.display = "none";
                    return;
                }

                // Actualizar posición antes de mostrar
                const positionUpdated = updateDropdownPosition();
                if (!positionUpdated) {
                    console.warn(`[Autocomplete ${inputId}] No se pudo actualizar la posición, input no visible`);
                    return;
                }

                suggestionsContainer.style.display = "block";

                data.forEach(item => {
                    const li = document.createElement("li");
                    li.textContent = item;
                    li.className = "px-4 py-2 hover:bg-gray-100 cursor-pointer";
                    li.onclick = () => {
                        input.value = item;
                        suggestionsContainer.innerHTML = "";
                        suggestionsContainer.style.display = "none";
                        // Disparar evento change para que se valide el input si es necesario
                        input.dispatchEvent(new Event('change', { bubbles: true }));
                    };
                    suggestionsContainer.appendChild(li);
                });
            } catch (err) {
                console.error("Error obteniendo sugerencias:", err);
                suggestionsContainer.style.display = "none";
            }
        }, debounceDelay);
    });

    // Cerrar sugerencias al hacer clic fuera
    document.addEventListener("click", (e) => {
        if (!e.target.closest(`#${inputId}`) && !e.target.closest(`#${containerId}`)) {
            suggestionsContainer.innerHTML = "";
            suggestionsContainer.style.display = "none";
        }
    });

    // Cerrar sugerencias al presionar ESC
    input.addEventListener("keydown", (e) => {
        if (e.key === "Escape") {
            suggestionsContainer.innerHTML = "";
            suggestionsContainer.style.display = "none";
        }
    });
}

