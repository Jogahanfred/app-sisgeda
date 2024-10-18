let DOCUMENTO_GLOBAL = {
	idSubFaseSeleccionada: null,
	matriz: null
}

const btnLimpiar = document.getElementById('btnLimpiar'),
	btnDesglozarMisiones = document.getElementById('btnDesglozarMisiones')


btnLimpiar.addEventListener("click", function() {
	autoCompleteSubFase.input.value = '';
	const contentDesglozarMisiones = document.getElementById('contentDesglozarMisiones');
	const tblMatriz = document.getElementById('tbl-matriz');
	let txtSubFaseEncontrada = document.getElementById('txtSubFaseEncontrada'),
		txtSubFaseSeleccionada = document.getElementById('txtSubFaseSeleccionada');
	contentDesglozarMisiones.style.display = 'none';
	txtSubFaseEncontrada.innerText = 'Sin selección';
	txtSubFaseSeleccionada.innerText = '-';
	DOCUMENTO_GLOBAL.idSubFaseSeleccionada = null;
	tblMatriz.innerHTML = '';

});

btnDesglozarMisiones.addEventListener("click", async function () {
	try {
		const tablaContainer = document.getElementById('tbl-matriz');
		const loader = document.getElementById('loader');

		tablaContainer.innerHTML = '';
		loader.style.display = 'block';

		const idSubFase = DOCUMENTO_GLOBAL.idSubFaseSeleccionada;

		const response = await fetchObtenerMatriz(idSubFase);
		const { type, message, data } = response;
		console.log("RESPONSE MATRIZ: ", data)
		notification(type, message);
		DOCUMENTO_GLOBAL.matriz = data;

		if (data == null) {
			loader.style.display = 'none';
			return;
		}

		setTimeout(() => {
			loader.style.display = 'none';
			tablaContainer.innerHTML = generarMatriz(data.lstEjeX, data.lstEjeY, data.lstEjeInterseccion);
			ocultarFilas();
			renderizarSelectoresChoices();
		}, 1500);

	} catch (err) {
		console.error(`Ha ocurrido un error al obtener la matriz: ${err.message}`);
	}


});

function obtenerTextoCaracter(texto) {
	const lastIndex = texto.lastIndexOf('>');

	if (lastIndex === -1) {
		return texto;
	}

	return texto.substring(lastIndex + 1).trim();
}

function generarMatriz(ejeX, ejeY, interseccion) {

	let tablaHTML = '<table id="tblMatrizGenerada" class="table align-middle mb-0" style="overflow-y:auto">';

	tablaHTML += '<tr id="filaTipoMision" class="text-center" style="background-color: #5f7ba0; color: white;"><th>Matriz de Misiones</th>';
	ejeX.forEach(mision => {
		tablaHTML += `<th colspan="1">${mision.coCodigo}</th>`;
	});
	tablaHTML += '</tr>';

	tablaHTML += '<tr id="filaCoCodigoTipoMision" class="text-center" style="background-color:beige"><th></th>';
	ejeX.forEach(mision => {
		tablaHTML += `<th>${mision.coCodigoTipoMision}</th>`;
	});
	tablaHTML += '</tr>';

	tablaHTML += '<tbody>';

	const operaciones = [];
	const operacionesMap = {};

	ejeY.forEach(maniobra => {
		const operacionId = maniobra.idOperacion;

		if (!operacionesMap[operacionId]) {
			operacionesMap[operacionId] = { nombre: maniobra.noNombreOperacion, maniobras: [] };
			operaciones.push(operacionesMap[operacionId]);
		}

		operacionesMap[operacionId].maniobras.push(maniobra);
	});

	for (const operacionId in operaciones) {
		if (operaciones.hasOwnProperty(operacionId)) {
			const operacion = operaciones[operacionId];
			tablaHTML += `<tr class="text-center" style="background-color: #eaf1fd; color: #5f5f5f;"><th colspan="${ejeX.length + 1}">${operacion.nombre}</th></tr>`;
			operacion.maniobras.forEach(maniobra => {
				tablaHTML += '<tr>';
				tablaHTML += `<th class="text-muted">${maniobra.noNombreManiobra}</th>`;
				ejeX.forEach(mision => {
					const intersection = interseccion.find(intersection =>
						intersection.idManiobra === maniobra.idManiobra && intersection.idMision === mision.idMision
					);
					if (intersection) {
						const divId = `divIdComplete-${intersection.idMision}-${intersection.idManiobra}`;
						const idDivInput = `idDivInputComplete-${intersection.idMision}-${intersection.idManiobra}`;
						tablaHTML += `<td>
										<div class="d-flex justify-content-center">
											<div id="${divId}" 
											     class="position-relative mb-0" style="width:3rem;"> 
												<input id="${idDivInput}"
												       type="text" 
													   dir="ltr" 
													   spellcheck=false 
													   autocomplete="off" 
													   autocapitalize="off"
													   class="autocomplete-estandar form-control form-control-xs ${renderizarColorInputEstandar(intersection.coCodigoEstandar)} border-light" 
													   placeholder="Estandar" 
													   value="${intersection.coCodigoEstandar}"
													   data-id-input=${idDivInput}
													   data-co-codigo-estandar-default=${intersection.coCodigoEstandar}
													   data-id-mision=${intersection.idMision}
													   data-id-maniobra=${intersection.idManiobra}>
					                        </div>
					                    </div>
					                        
					                  </td>`;
					} else {
						tablaHTML += '<td></td>';
					}
				});
				tablaHTML += '</tr>';
			});
		}
	}

	tablaHTML += '</tbody></table>';
	return tablaHTML;
}

function renderizarColorInputEstandar(coCodigo) {
	let color = "";
	switch (coCodigo) {
		case 'D':
			color = 'bg-soft-info';
			break;
		case 'I':
			color = 'bg-soft-warning';
			break;
		case 'R':
			color = 'bg-soft-primary';
			break;
		case 'B':
			color = 'bg-soft-secondary';
			break;
		case 'E':
			color = 'bg-soft-success';
			break;
		case 'SE':
			color = 'bg-soft-light';
			break;
		case 'P':
			color = 'bg-soft-dark';
			break;
		case 'NR':
			color = 'bg-soft-danger';
			break;
		default:
			color = 'bg-soft-light';
			break;
	}
	return color;
}

function renderizarSelectoresChoices() {
	document.querySelectorAll('.autocomplete-estandar').forEach(input => {
		const parentDiv = input.closest('div');
		const idInput = input.getAttribute('data-id-input');

		const idMision = input.getAttribute('data-id-mision');
		const idManiobra = input.getAttribute('data-id-maniobra');
		const coCodigoEstandarDefault = input.getAttribute('data-co-codigo-estandar-default');
		const idDivInput = parentDiv ? parentDiv.id : null;

		if (idDivInput) {
			var autocompleteEstandar = new autoComplete({
				selector: `#${idDivInput} input`,
				placeHolder: "Estándar",
				data: {
					src: [
						{ "idEstandarRequerido": 1, "noNombre": "Demostrativo", "coCodigo": "D" },
						{ "idEstandarRequerido": 3, "noNombre": "Insuficiente", "coCodigo": "I" },
						{ "idEstandarRequerido": 4, "noNombre": "Regular", "coCodigo": "R" },
						{ "idEstandarRequerido": 5, "noNombre": "Bueno", "coCodigo": "B" },
						{ "idEstandarRequerido": 6, "noNombre": "Excelente", "coCodigo": "E" },
						{ "idEstandarRequerido": 21, "noNombre": "Sin Estandar", "coCodigo": "SE" },
						{ "idEstandarRequerido": 7, "noNombre": "Peligrosa", "coCodigo": "P" },
						{ "idEstandarRequerido": 8, "noNombre": "No Realizado", "coCodigo": "NR" }
					],
					cache: true,
					keys: ["noNombre"]
				},
				resultsList: {
					element: (list, data) => {
						if (!data.results.length) {
							var message = document.createElement("div");
							message.setAttribute("class", "no_result");
							message.innerHTML = "<span>- \"" + data.query + "\"</span>";
							list.prepend(message);
						}
					},
					noResults: true,
				},
				resultItem: {
					highlight: true,
				},
				events: {
					input: {
						selection: async function selection(event) {
							const seleccionado = event.detail.selection.value;
							const inputComplete = document.getElementById(`${idInput}`);

							autocompleteEstandar.input.value = seleccionado.coCodigo;

							inputComplete.classList.remove(renderizarColorInputEstandar(coCodigoEstandarDefault));
							inputComplete.classList.add(renderizarColorInputEstandar(seleccionado.coCodigo));

							const response = await fetchActualizarEstandar(idMision, idManiobra, seleccionado.idEstandarRequerido);
							const { type, message, data } = response;

							notification(type, message);
						}
					}
				}
			});
		}

	});
}

function ocultarFilas() {
	const tabla = document.getElementById('tblMatrizGenerada');
	if (tabla) {
		const primeraCelda = tabla.querySelector('tr#filaTipoMision th:first-child');
		const segundaCelda = tabla.querySelector('tr#filaCoCodigoTipoMision th:first-child');
		if (primeraCelda && segundaCelda) {
			primeraCelda.rowSpan = '2';
			segundaCelda.style.display = 'none';
		}
	}
}

var autoCompleteSubFase = new autoComplete({
	selector: "#autoCompleteSubFase",
	placeHolder: "Buscar Sub Fase...",
	data: {
		src: async () => {
			try {
				const dataFiltros = await fetchListarFiltroPeriodo(2024);
				return dataFiltros;
			} catch (err) {
				console.error(`Ha ocurrido un error al obtener el filtro por periodos: ${err.message}`);
				return [];
			}
		},
		cache: true,
		keys: ["text"],
	},
	resultsList: {
		element: function element(list, data) {
			if (!data.results.length) {
				var message = document.createElement("div");
				message.setAttribute("class", "no_result");
				message.innerHTML = "<span>Resultados no encontrado para \"" + data.query + "\"</span>";
				list.prepend(message);
			}
		},
		noResults: true
	},
	resultItem: {
		highlight: true
	},
	events: {
		input: {
			selection: function selection(event) {
				const seleccionado = event.detail.selection.value;
				autoCompleteSubFase.input.value = seleccionado.text;

				const contentDesglozarMisiones = document.getElementById('contentDesglozarMisiones');
				let txtSubFaseEncontrada = document.getElementById('txtSubFaseEncontrada');
				let txtSubFaseSeleccionada = document.getElementById('txtSubFaseSeleccionada');
				let nombreSubFase = obtenerTextoCaracter(seleccionado.text);

				contentDesglozarMisiones.style.display = 'block';
				txtSubFaseEncontrada.innerText = seleccionado.text;
				txtSubFaseSeleccionada.innerText = nombreSubFase;
				DOCUMENTO_GLOBAL.idSubFaseSeleccionada = seleccionado.id;

			}
		}
	}
});

async function fetchListarFiltroPeriodo(nuPeriodo) {
	try {
		const response = await fetch(`/subFases/listarFiltroPeriodo?nuPeriodo=${nuPeriodo}`);
		if (!response.ok) {
			const message = `"No se ha podido resolver la lista de filtros: ${response.status}`;
			throw new Error(message);
		}

		const data = await response.json();
		return data.map(item => ({ id: item.value, text: item.text }));
	} catch (error) {
		console.error('Error al listar el filtro por periodo:', error);
		throw error;
	}
}

async function fetchObtenerMatriz(idSubFase) {
	try {
		const response = await fetch(`/subFases/obtenerMatriz?idSubFase=${idSubFase}`);

		if (!response.ok) {
			const message = `"No se ha podido resolver la obtención de la matriz: ${response.status}`;
			throw new Error(message);
		}

		const dataMatriz = await response.json();
		return dataMatriz;
	} catch (error) {
		console.error('Error al obtener la matriz:', error);
		throw error;
	}
}

async function fetchActualizarEstandar(idMision, idManiobra, idEstandar) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				idMision: idMision,
				idManiobra: idManiobra,
				idEstandarRequerido: idEstandar
			})
		}
		const response = await fetch(`/misiones/actualizarEstandar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido actualizar el estandar: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al actualizar el estandar:', error);
		throw error;
	}
}