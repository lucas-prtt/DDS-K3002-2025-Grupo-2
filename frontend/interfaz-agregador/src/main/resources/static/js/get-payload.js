function getPayloadColeccion(inputsObligatorios) {
    let criterioDePertenencia = {}
    const fuentes = [];

    inputsObligatorios.tiposFuentes.forEach((tipo, index) => {
        const nombre = tipo.value === "dinamica" ? "e359660d-9459-4312-9db6-59e4f9c935d4" : inputsObligatorios.nombresFuentes[index].value;

        if ((nombre && (tipo.value === "estatica" || tipo.value === "proxy")) || tipo.value === "dinamica") {
            if(tipo.value) {
                fuentes.push({
                    tipo: tipo.value,
                    id: nombre
                });
            }
        }
    })

    if(inputsObligatorios.tipoCriterio.value === "DISTANCIA") {
        const lat = document.getElementById('criterio-latitud');
        const lon = document.getElementById('criterio-longitud');
        const dist = document.getElementById('criterio-distancia-minima');

        if(lat.value && lon.value && dist.value) {
            criterioDePertenencia = {
                tipo: 'distancia',
                ubicacionBase: { latitud: parseFloat(lat.value), longitud: parseFloat(lon.value) },
                distanciaMinima: parseFloat(dist.value)
            };
        }
    } else {
        if(inputsObligatorios.tipoCriterio.value === "FECHA") {
            const fechaIni = document.getElementById('criterio-fecha-inicial');
            const fechaFin = document.getElementById('criterio-fecha-final');

            if (fechaIni.value && fechaFin.value) {
                criterioDePertenencia = {
                    tipo: 'fecha',
                    fechaInicial: fechaIni.value + ":00.000",
                    fechaFinal: fechaFin.value + ":00.000"
                };
            }
        }
    }

    return {
        titulo: inputsObligatorios.titulo.value,
        descripcion: inputsObligatorios.descripcion.value,
        criteriosDePertenencia: [criterioDePertenencia],
        fuentes: fuentes,
        tipoAlgoritmoConsenso: inputsObligatorios.algoritmo.value
    };
}