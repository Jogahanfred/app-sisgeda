let DOCUMENTO_GLOBAL = {
	idSubFaseSeleccionada: null,
	matriz: null,
	idDetalleMisionSeleccionada: null,
	coEstandarRequeridoSeleccionado: null
}

const btnLimpiar = document.getElementById('btnLimpiar'),
	btnDesglozarMisiones = document.getElementById('btnDesglozarMisiones'),
	btnRegistrarMensajePredefinido = document.getElementById('btnRegistrarMensajePredefinido'),
	cboEstandar = document.getElementById('cboEstandar-field');

var cboEstandarVal = new Choices(cboEstandar)


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
	DOCUMENTO_GLOBAL.idDetalleMisionSeleccionada = null,
		DOCUMENTO_GLOBAL.coEstandarRequeridoSeleccionado = null,
		tblMatriz.innerHTML = '';

});

btnDesglozarMisiones.addEventListener("click", async () => {
	try {
		const tablaContainer = document.getElementById('tbl-matriz');
		const loader = document.getElementById('loader');

		tablaContainer.innerHTML = '';
		loader.style.display = 'block';

		const idSubFase = DOCUMENTO_GLOBAL.idSubFaseSeleccionada;

		const response = await fetchObtenerMatriz(idSubFase);
		const { type, message, data } = response;
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
		}, 1500);

	} catch (err) {
		console.error(`Ha ocurrido un error al obtener la matriz: ${err.message}`);
	}


});

btnRegistrarMensajePredefinido.addEventListener("click", async () => {
	const idEstandar = document.getElementById('cboEstandar-field');
	const txMensajePredefinidoField = document.getElementById('txMensajePredefinido-field');

	if (idEstandar.value == null || idEstandar.value == "") {
		notification('warning', 'Debe seleccionar un estándar');
		return;
	}

	if (txMensajePredefinidoField.value == null || txMensajePredefinidoField.value.trim() == "") {
		notification('warning', 'Debe colocar un mensaje a registrar');
		return;
	}

	const txtBtnResolve = document.getElementById('txt-btn-resolve');
	txtBtnResolve.style.display = 'none';

	const txtBtnLoader = document.getElementById('txt-btn-loader');
	txtBtnLoader.style.display = 'block';

	const restriccion = {
		idDetalleMision: DOCUMENTO_GLOBAL.idDetalleMisionSeleccionada,
		idEstandar: idEstandar.value,
		txMensaje: txMensajePredefinidoField.value
	}

	const response = await fetchRegistrarRestriccion(restriccion);

	const { type, message } = response;
	notification(type, message);

	if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
		txtBtnResolve.style.display = 'block';
		txtBtnLoader.style.display = 'none';

		const txMensajePredefinidoField = document.getElementById('txMensajePredefinido-field');

		if (cboEstandarVal) {
			cboEstandarVal.removeActiveItems();
			cboEstandarVal.setChoiceByValue("");
		}
		txMensajePredefinidoField.value = "";
		try {
			const responseRestricciones = await fetchListarRestriccionesPorIdDetalleMision(DOCUMENTO_GLOBAL.idDetalleMisionSeleccionada);
			renderizarTabMensajesPredefinidos(responseRestricciones, DOCUMENTO_GLOBAL.coEstandarRequeridoSeleccionado);
		} catch (error) {
			console.error('Error al listar restricciones:', error);
		}
	}
})

function obtenerTextoCaracter(texto) {
	const lastIndex = texto.lastIndexOf('>');

	if (lastIndex === -1) {
		return texto;
	}

	return texto.substring(lastIndex + 1).trim();
}

function generarMatriz(ejeX, ejeY, interseccion) {

	let tablaHTML = '<table id="tblMatrizGenerada" class="table table-bordered align-middle mb-0" style="overflow-y:auto">';

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
						const maniobraSeleccionada = {
							idDetalleMision: intersection.idDetalleMision,
							coCodigoMision: mision.coCodigo,
							idEstandarRequerido: intersection.idEstandarRequerido,
							coEstandarRequerido: intersection.coCodigoEstandar,
							noNombreOperacion: maniobra.noNombreOperacion,
							noNombreManiobra: maniobra.noNombreManiobra
						};

						tablaHTML += `<td class="col-sm-auto">
						                <div class="avatar-group align-items-baseline justify-content-center">
						                  <div class="avatar-group-item">
						                    <a class="d-inline-block"
						                       data-bs-toggle="tooltip" data-bs-placement="top" title="${maniobraSeleccionada.coEstandarRequerido}"
						                       data-id-detalle-mision="${maniobraSeleccionada.idDetalleMision}" 
						                       data-co-codigo-mision="${maniobraSeleccionada.coCodigoMision}"
						                       data-id-estandar-requerido="${maniobraSeleccionada.idEstandarRequerido}"
						                       data-co-estandar-requerido="${maniobraSeleccionada.coEstandarRequerido}"
						                       data-no-nombre-operacion="${maniobraSeleccionada.noNombreOperacion}"
						                       data-no-nombre-maniobra="${maniobraSeleccionada.noNombreManiobra}"
						                       onclick="abrirModal(this)">
						                      <div class="avatar-xxs">
						                        <span class="avatar-title rounded-circle ${renderizarColorAvatarEstandar(maniobraSeleccionada.coEstandarRequerido)} text-white">
						                          ${maniobraSeleccionada.coEstandarRequerido}
						                        </span>
						                      </div>
						                    </a>
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


async function abrirModal(element) {
	const targetElement = element.closest('a');
	const idDetalleMision = targetElement.getAttribute('data-id-detalle-mision');
	const coCodigoMision = targetElement.getAttribute('data-co-codigo-mision');
	const idEstandarRequerido = targetElement.getAttribute('data-id-estandar-requerido');
	const coEstandarRequerido = targetElement.getAttribute('data-co-estandar-requerido');
	const noNombreOperacion = targetElement.getAttribute('data-no-nombre-operacion');
	const noNombreManiobra = targetElement.getAttribute('data-no-nombre-maniobra');

	const maniobraObtenida = {
		idDetalleMision: idDetalleMision,
		coCodigoMision: coCodigoMision,
		idEstandarRequerido: idEstandarRequerido,
		coEstandarRequerido: coEstandarRequerido,
		noNombreOperacion: noNombreOperacion,
		noNombreManiobra: noNombreManiobra
	}

	DOCUMENTO_GLOBAL.idDetalleMisionSeleccionada = maniobraObtenida.idDetalleMision;
	DOCUMENTO_GLOBAL.coEstandarRequeridoSeleccionado = maniobraObtenida.coEstandarRequerido;

	try {
		const responseRestricciones = await fetchListarRestriccionesPorIdDetalleMision(maniobraObtenida.idDetalleMision);

		renderizarTabMensajesPredefinidos(responseRestricciones, maniobraObtenida.coEstandarRequerido);

		var modalElement = document.getElementById('mdRestriccion');
		var modal = new bootstrap.Modal(modalElement);
		modal.show();

		document.getElementById('mdTitulo').innerText = `Matricular Restricciones`;

		document.getElementById('txtNoNombreOperacionModal').innerText = `${maniobraObtenida.noNombreOperacion}`;
		document.getElementById('txtCoCodigoMisionModal').innerText = maniobraObtenida.coCodigoMision;
		document.getElementById('txtNoNombreManiobraModal').innerText = `${maniobraObtenida.noNombreManiobra}`;

	} catch (error) {
		console.error('Error al listar restricciones:', error);
	}


}

document.getElementById("mdRestriccion").addEventListener("hidden.bs.modal", function() {
	DOCUMENTO_GLOBAL.idDetalleMisionSeleccionada = null;
	DOCUMENTO_GLOBAL.coEstandarRequeridoSeleccionado = null;

	const txMensajePredefinidoField = document.getElementById('txMensajePredefinido-field');

	if (cboEstandarVal) {
		cboEstandarVal.removeActiveItems();
		cboEstandarVal.setChoiceByValue("");
	}
	txMensajePredefinidoField.value = "";

});

function renderizarTabMensajesPredefinidos(datos, coEstandarRequerido) {
	const tabMap = {
		1: 'nav-badge-d',  // D
		2: 'nav-badge-i',  // I
		3: 'nav-badge-r',  // R
		4: 'nav-badge-b',  // B
		5: 'nav-badge-e'   // E
	};

	Object.values(tabMap).forEach(tabId => {
		const tabContent = document.querySelector(`#${tabId}`);
		tabContent.innerHTML = '';
		tabContent.innerHTML = '<p class="m-0">No hay datos disponibles</p>';
		const tabLink = document.querySelector(`a[href="#${tabId}"]`);
		const badge = tabLink.querySelector('.badge-soft-info');
		if (badge) {
			badge.remove();
		}
	});

	Object.entries(tabMap).forEach(([key, tabId]) => {
		const letter = tabId.split('-').pop();
		if (letter.toUpperCase() === coEstandarRequerido) {
			const tabLinkWithFire = document.querySelector(`a[href="#${tabId}"]`);

			if (tabLinkWithFire && !tabLinkWithFire.querySelector('.badge-soft-info')) {
				const badge = document.createElement('span');
				badge.className = 'badge badge-soft-info fs-12';
				badge.innerHTML = '<i class="ri-fire-fill me-1 align-bottom"></i>';
				tabLinkWithFire.appendChild(badge);
			}

		}
	});

	datos.forEach(dato => {
		const tabId = tabMap[dato.nuNivelEstandar];
		if (tabId) {
			const tabContent = document.querySelector(`#${tabId}`);
			const message = `<div class="d-flex mt-2">
                                <div class="flex-shrink-0">
                                    <i class="ri-checkbox-circle-fill text-success"></i>
                                </div>
                                <div class="flex-grow-1 ms-2">
                                    ${dato.txMensaje}
                                </div>
                             </div>`;

			if (tabContent.querySelector('p')) {
				tabContent.innerHTML = '';
			}

			tabContent.innerHTML += message;

			if (dato.nuNivelEstandar == coEstandarRequerido) {
				const tabLink = document.querySelector(`a[href="#${tabId}"]`);
				if (!tabLink.querySelector('.badge-soft-info')) {
					const badge = document.createElement('span');
					badge.className = 'badge badge-soft-info fs-12';
					badge.innerHTML = '<i class="ri-fire-fill me-1 align-bottom"></i>';
					tabLink.appendChild(badge);
				}
			}

		}
	});
}

function renderizarColorAvatarEstandar(coCodigo) {
	let color = "";
	switch (coCodigo) {
		case 'D':
			color = 'bg-info';
			break;
		case 'I':
			color = 'bg-warning';
			break;
		case 'R':
			color = 'bg-primary';
			break;
		case 'B':
			color = 'bg-secondary';
			break;
		case 'E':
			color = 'bg-success';
			break;
		case 'SE':
			color = 'bg-light';
			break;
		case 'P':
			color = 'bg-dark';
			break;
		case 'NR':
			color = 'bg-danger';
			break;
		default:
			color = 'bg-light';
			break;
	}
	return color;
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

async function fetchRegistrarRestriccion(restriccion) {
	try {
		const response = await fetch('/restriccionesEstandar/guardar', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(restriccion)
		});

		if (!response.ok) {
			const message = `"No se ha podido resolver el registro de restricciones: ${response.status}`;
			throw new Error(message);
		}

		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al registrar la restriccion:', error);
		throw error;
	}
}

async function fetchListarRestriccionesPorIdDetalleMision(idDetalleMision) {
	try {
		const response = await fetch(`/restriccionesEstandar/listarPorIdDetalleMision?idDetalleMision=${idDetalleMision}`);
		if (!response.ok) {
			const message = `"No se ha podido resolver la lista de restricciones: ${response.status}`;
			throw new Error(message);
		}

		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al listar las restricciones por detalle mision:', error);
		throw error;
	}
}

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
		const response = await fetch(`/subFases/obtenerMatrizRestriccion?idSubFase=${idSubFase}`);

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
