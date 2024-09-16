let cboBusquedaEscuadron = document.getElementById("cboBusquedaEscuadron"),
	cboBusquedaPrograma = document.getElementById("cboBusquedaPrograma"),
	cboBusquedaProgramaVal = new Choices(cboBusquedaPrograma);

cboBusquedaEscuadron.addEventListener("change", async function() {
	let idEscuadron = cboBusquedaEscuadron.value;
	try {
		const response = await fetchListarProgramasPorIdEscuadron(idEscuadron);
		if (cboBusquedaProgramaVal) cboBusquedaProgramaVal.destroy();
		cboBusquedaProgramaVal = new Choices(cboBusquedaPrograma, {
			searchEnabled: false
		});
		const groupedChoices = llenarCboIdPrograma(response);
		cboBusquedaProgramaVal.setChoices(groupedChoices, 'value', 'label', false);
	} catch (error) {
		console.error('Error al listar Programas:', error);
	}
});

cboBusquedaPrograma.addEventListener("change", async function() {
	
	document.getElementById('loader').style.display = 'block';
	document.getElementById('contenedorPrograma').style.display = 'none';
	document.getElementById('noresult').style.display = 'none';
	
	let idPrograma = cboBusquedaPrograma.value;
	try {
		const lstFases = await fetchListarFasesPorIdPrograma(idPrograma); 
		if (lstFases.length > 0) {
			renderizarMapa(lstFases);
			document.getElementById('contenedorPrograma').style.display = 'block';
		} else {
			document.getElementById('noresult').style.display = 'block';
		}
		document.getElementById('loader').style.display = 'none';

	} catch (error) {
		console.error('Error al listar fases:', error);
	}
});

function renderizarMapa(response) {
	const contenedor = document.getElementById('contenedorPrograma');
	let html = '<ul class="list-unstyled mb-0">';

	html += `
        <li class="p-0 parent-title">
            <a href="javascript: void(0);" class="fw-medium fs-14" style="cursor: default;">Nancy Martino - Project Director</a>
        </li>
    `;

	response.forEach(fase => {
		html += `
            <li>
                <div class="first-list">
                    <div class="list-wrap">
                        <a href="javascript: void(0);" class="fw-medium text-primary"style="cursor: default;">${fase.txDescripcionFase}</a>
                    </div>
        `;

		if (fase.lstSubFases && fase.lstSubFases.length > 0) {
			html += '<ul class="second-list list-unstyled">';

			fase.lstSubFases.forEach(subfase => {
				html += `
                    <li>
                        <a href="javascript: void(0);"  data-id-sub-fase="${subfase.idSubFase}">${subfase.txDescripcionSubFase}</a>
                    </li>
                `;
			});

			html += '</ul>'; 
		}

		html += '</div></li>'; 
	});

	html += '</ul>'; 
	contenedor.innerHTML = html;

 	contenedor.querySelectorAll('a').forEach(link => {
        link.addEventListener('click', function() {
            const id = this.getAttribute('data-id-sub-fase');
            if (id) {
                console.log('ID del item clickeado:', id);

            }
        });
    });
}

function llenarCboIdPrograma(lstProgramas, idPrograma) {
	const groupedData = {};

	lstProgramas.forEach(programa => {
		const tipoInstruccion = programa.noTipoInstruccion;
		if (!groupedData[tipoInstruccion]) {
			groupedData[tipoInstruccion] = {
				label: tipoInstruccion,
				id: programa.idPrograma,
				disabled: false,
				choices: []
			};
		}
		groupedData[tipoInstruccion].choices.push({
			label: programa.noNombre,
			value: programa.idPrograma,
			selected: (idPrograma) ? programa.idPrograma == idPrograma : false,
			disabled: false
		});
	});

	const groupedChoices = Object.values(groupedData);
	return groupedChoices;
}

async function fetchListarFasesPorIdPrograma(idPrograma) {
	try {
		const response = await fetch(`/fases/listarPorIdPrograma?idPrograma=${idPrograma}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de fases: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de fases:', error);
		throw error;
	}
}

async function fetchListarProgramasPorIdEscuadron(idEscuadron) {
	try {
		const response = await fetch(`/programas/listarPorIdEscuadron?idEscuadron=${idEscuadron}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de fases: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de fases:', error);
		throw error;
	}
}

