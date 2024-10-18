renderizarLista();

async function renderizarLista() {
	document.getElementById('loader').style.display = 'block';

	const tablaContainer = document.getElementById('tbl-matriz');
	const loader = document.getElementById('loader');

	try {
		const response = await fetchObtenerMatrizACalificar();
		const { data } = response;
		console.log("RESPONSE_", response)
		if (data == null) {
			loader.style.display = 'none';
			return;
		}
		tablaContainer.innerHTML = generarMatriz(data.lstEjeX, data.lstEjeY, data.lstEjeInterseccion);
		ocultarFilas();
		renderizarSelectoresChoices();
		loader.style.display = 'none';

	} catch (error) {
		console.error('Error al mostrar la lista:', error);
	}
}

document.getElementById('btnRetornar').addEventListener("click", function() {
	window.history.back();
});

function obtenerTextoCaracter(texto) {
	const lastIndex = texto.lastIndexOf('>');

	if (lastIndex === -1) {
		return texto;
	}

	return texto.substring(lastIndex + 1).trim();
}

function generarMatriz(ejeX, ejeY, interseccion) {

	let tablaHTML = '<table id="tblMatrizGenerada" class="table table-sm align-middle mb-0" style="overflow-y:auto">';

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
						intersection.idManiobra === maniobra.idManiobra && intersection.idCalificacion === mision.idCalificacion
					);
					console.log("inter: ", intersection)

					const flHabilitado = mision.flHabilitado;
					const disabledAttr = flHabilitado === 0 ? 'disabled' : '';
					const tdStyle = flHabilitado === 0 ? 'border-left: 1px solid #efeaea; border-right: 1px solid #efeaea; background-color: #f8f8fb; cursor: no-drop;' : '';

					if (intersection && intersection.coCodigoEstandarRequerido !== "SE") {
						const divId = `divIdComplete-${intersection.idCalificacion}-${intersection.idManiobra}`;
						const idDivInput = `idDivInputComplete-${intersection.idCalificacion}-${intersection.idManiobra}`;

						tablaHTML += `<td style="${tdStyle} width: 14rem;">
										<div class="d-flex align-items-center justify-content-center">
											<button type="button" 
													class="p-0 
														   btn 
														   avatar-btn 
														   d-flex 
														   align-items-center 
														   justify-content-center" 
													data-id-maniobra="${intersection.idManiobra}" 
													data-id-calificacion="${intersection.idCalificacion}"
													data-id-detalle-calificacion="${intersection.idDetalleCalificacion}"
													data-co-codigo-estandar-obtenido="${intersection.coCodigoEstandarObtenido}">
												<div class="avatar-xs flex-shrink-0">
        											<div class="avatar-title 
																${renderizarColorBagdeEstandar(intersection.coCodigoEstandarRequerido)} 
																rounded-circle 
																shadow" style="width:90%; height:90%"> 
													${intersection.coCodigoEstandarRequerido}
													</div>
												</div>
    										</button>
											<span class="mx-1 text-muted">/</span>
										  	<div id="${divId}"  class="position-relative mb-0" style="width:6rem;">
											      <input id="${idDivInput}" 
											    	   type="text"  
											    	   aria-label="Sizing example input" 
											    	   aria-describedby="inputGroup-sizing-default" 
													   dir="ltr" 
													   ${disabledAttr}
													   spellcheck=false 
													   autocomplete="off" 
													   autocapitalize="off"
													   class="py-1 autocomplete-estandar form-control ${renderizarColorInputEstandar(intersection.coCodigoEstandarObtenido)} " 
													   placeholder="Estandar" 
													   value="${intersection.coCodigoEstandarObtenido == null ? '' : intersection.coCodigoEstandarObtenido}"
													   data-id-input=${idDivInput}
													   data-co-codigo-estandar-obtenido=${intersection.coCodigoEstandarObtenido}
													   data-id-calificacion=${intersection.idCalificacion}
													   data-id-maniobra=${intersection.idManiobra}
													   style="cursor: inherit;">
											</div>
											`
						if (intersection.coEstadoEjecucionManiobra !== "SIN_CALIFICAR" && intersection.coEstadoEjecucionManiobra !== "NORMAL") {
							tablaHTML += `<p class="${renderizarColorIconoEstadoManiobraEjecutada(intersection.coEstadoEjecucionManiobra)} fw-medium fs-12 mb-0">
						                      					<i class="${renderizarIconoEstadoManiobraEjecutada(intersection.coEstadoEjecucionManiobra)} fs-5 align-middle"></i> ${intersection.coEstadoEjecucionManiobra == 'SOBRE_ESTANDAR' ? '+' : '-'}0.5
						                  					  </p>`;
						} else if (intersection.coEstadoEjecucionManiobra === "NORMAL") {
							tablaHTML += `<p class="text-info fw-medium fs-12 mb-0">
																<i class="${renderizarIconoEstadoManiobraEjecutada(intersection.coEstadoEjecucionManiobra)} fs-5 align-middle"></i> &nbsp;0.0
						                  					 </p>`;
						} else {
							tablaHTML += `<p class="fw-medium fs-12 mb-0"></p>`;
						}

						`
					                    </div>
					                        
					                  </td>`;
					} else {
						tablaHTML += `<td style="${tdStyle} width: 14rem;"></td>`;
					}
				});
				tablaHTML += '</tr>';
			});
		}
	}

	tablaHTML += '</tbody></table>';
	return tablaHTML;
}

document.addEventListener("click", async function(event) {
	if (event.target.closest('.avatar-btn')) {
		const userProfileSidebar = document.querySelector(".layout-rightside-col");

		const coCodigoEstandarObtenido = event.target.closest('.avatar-btn').dataset.coCodigoEstandarObtenido;

		if (coCodigoEstandarObtenido == null || coCodigoEstandarObtenido.trim() == "" || coCodigoEstandarObtenido == "null") {
			notification('warning', 'Debe calificar la maniobra');
			return;
		}


		if (userProfileSidebar.classList.contains("d-block")) {
			userProfileSidebar.classList.remove("d-block");
			userProfileSidebar.classList.add("d-none");
		} else {
			userProfileSidebar.classList.remove("d-none");
			userProfileSidebar.classList.add("d-block");
		}

		const idManiobra = event.target.closest('.avatar-btn').dataset.idManiobra;
		const idCalificacion = event.target.closest('.avatar-btn').dataset.idCalificacion;
		const idDetalleCalificacion = event.target.closest('.avatar-btn').dataset.idDetalleCalificacion;
		// Puedes usar estas variables para realizar más acciones


		console.log("ID Maniobra:", idManiobra, "ID Calificación:", idCalificacion, "idDetalleCalificacion: ", idDetalleCalificacion);
		try {
			const lstRestricciones = await fetchListarRestriccionesPorIdDetalleCalificacion(idDetalleCalificacion);
			console.log("LISTA: ", lstRestricciones);

			// Limpiar el contenedor antes de añadir contenido
			const restriccionesContainer = document.getElementById('content-restricciones');
			restriccionesContainer.innerHTML = '';

			if (lstRestricciones && lstRestricciones.length > 0) {
				document.getElementById('alert-sin-restricciones').style.display = 'none'
				document.getElementById('content-card-restricciones').style.display = 'block';
				// Si hay restricciones, iterar y mostrarlas
				lstRestricciones.forEach(restriccion => {
					const button = document.createElement('div');
					button.classList.add('card', 'border', 'border-dashed', 'shadow-none');
					button.style.cursor = 'pointer';
					button.dataset.idRestriccionEstandar = restriccion.idRestriccionEstandar;
					button.dataset.txMensaje = restriccion.txMensaje;
					button.dataset.idDetalleCalificacion = idDetalleCalificacion;

					button.innerHTML = `
				        <div class="card-body">
				            <div class="d-flex">
				                <div class="flex-shrink-0">
				                    <div class="avatar-sm flex-shrink-0">
				                        <div class="avatar-title 
				                            ${renderizarColorBagdeEstandar(restriccion.txDescripcionEstandar)} 
				                            rounded 
				                            shadow" style="width:90%; height:90%"> 
				                            ${restriccion.txDescripcionEstandar}
				                        </div>
				                    </div>
				                </div>
				                <div class="flex-grow-1 ms-3">
				                    <div>
				                        <p class="text-muted mb-1 fst-italic">
				                            ${restriccion.txMensaje}
				                        </p> 
				                    </div>                                        
				                    <div class="text-end mb-0 text-muted">
				                        - <cite title="Estandar Pre Cargada">${renderizarDescripcionEstandar(restriccion.txDescripcionEstandar)}</cite>
				                    </div>
				                </div>
				            </div>
				        </div>
				    `;

					// Añadir evento de clic al botón
					button.addEventListener('click', () => {
						const idRestriccionEstandar = button.dataset.idRestriccionEstandar;
						const txMensaje = button.dataset.txMensaje;
						const idDetalleCalificacion = button.dataset.idDetalleCalificacion;
						console.log("ID Restricción Estandar:", idRestriccionEstandar);

						document.getElementById('txtarea-causa').innerText = txMensaje;
						// Aquí puedes agregar más lógica según lo que necesites hacer con el id
					});

					// Ahora añade el botón (no card) al contenedor de restricciones
					restriccionesContainer.appendChild(button);
				});
			} else {
				document.getElementById('alert-sin-restricciones').style.display = 'block'
				document.getElementById('content-card-restricciones').style.display = 'none';
				// Si no hay restricciones, mostrar el mensaje
				const noInfoCard = document.createElement('div');
				noInfoCard.classList.add('swiper-slide');
				noInfoCard.innerHTML = `
                    <div class="card border border-dashed shadow-none">
                        <div class="card-body">
                            <div class="d-flex">
                                <div class="flex-grow-1 ms-3">
                                    <p class="text-muted mb-1 fst-italic text-truncate-two-lines">No existe información</p>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
				restriccionesContainer.appendChild(noInfoCard);
			}

			// Inicializar Swiper y recalcular tamaño después de agregar el contenido
			const swiper = new Swiper(".vertical-swiper", {
				slidesPerView: 2,
				spaceBetween: 10,
				mousewheel: true,
				loop: true,
				direction: "vertical",
				autoplay: {
					delay: 2500,
					disableOnInteraction: false,
				},
			});


		} catch (error) {
			console.error('Error al listar restricciones :', error);
		}
	}
});

document.getElementById('btnGuardarCalificacion').addEventListener('click', async () => {
	const txtCausa = document.getElementById('txtarea-causa').value;
	const txtObservacion = document.getElementById('txtarea-observacion').value;
	const txtRecomendacion = document.getElementById('txtarea-recomendacion').value;

	if (txtCausa == null || txtCausa == '') {
		notification('warning', 'Ingrese el motivo de la calificación');
		return;
	}
	if (txtObservacion == null || txtObservacion == '') {
		notification('warning', 'Ingrese una observación sobre el motivo de calificacion');
		return;
	}
	if (txtRecomendacion == null || txtRecomendacion == '') {
		notification('warning', 'Ingrese la recomendación para la maniobra');
		return;
	}

	try {
		const response = await fetchRegistrarCor(24484, txtCausa, txtObservacion, txtRecomendacion);
		const { type, message } = response;
		notification(type, message);
		if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) { 
			document.getElementById('txtarea-causa').value = '';
			document.getElementById('txtarea-observacion').value = '';
			document.getElementById('txtarea-recomendacion').value = '';
		}
	} catch (error) {
		console.error('Error al registrar cor:', error);
	}

	console.log(txtCausa)
	console.log(txtObservacion)
	console.log(txtRecomendacion)
})

function renderizarIconoEstadoManiobraEjecutada(estado) {
	switch (estado) {
		case 'SOBRE_ESTANDAR':
			return 'ri-arrow-up-s-fill';
		case 'BAJO_ESTANDAR':
			return 'ri-arrow-down-s-fill';
		case 'NORMAL':
			return 'ri-arrow-right-s-fill';
		case 'SIN_CALIFICAR':
			return 'ri-arrow-drop-left-fill';
		default:
			return '';
	}
}

function renderizarColorIconoEstadoManiobraEjecutada(estado) {
	switch (estado) {
		case 'SOBRE_ESTANDAR':
			return 'text-success';
		case 'BAJO_ESTANDAR':
			return 'text-danger';
		case 'NORMAL':
		case 'SIN_CALIFICAR':
			return 'text-warning';
		default:
			return '';
	}
}

function renderizarColorBagdeEstandar(coCodigo) {
	let color = "";
	switch (coCodigo) {
		case 'D':
			color = 'bg-soft-info text-info';
			break;
		case 'I':
			color = 'bg-soft-warning text-warning';
			break;
		case 'R':
			color = 'bg-soft-primary text-primary';
			break;
		case 'B':
			color = 'bg-soft-secondary text-secondary';
			break;
		case 'E':
			color = 'bg-soft-success text-success';
			break;
		case 'SE':
			color = 'bg-soft-light text-light';
			break;
		case 'P':
			color = 'bg-soft-dark text-dark';
			break;
		case 'NR':
			color = 'bg-soft-danger text-danger';
			break;
		default:
			color = 'bg-soft-light text-dark';
			break;
	}
	return color;
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


function renderizarDescripcionEstandar(coCodigo) {
	let descripcion = "";
	switch (coCodigo) {
		case 'D':
			descripcion = 'Demostrativo';
			break;
		case 'I':
			descripcion = 'Insuficiente';
			break;
		case 'R':
			descripcion = 'Regular';
			break;
		case 'B':
			descripcion = 'Bueno';
			break;
		case 'E':
			descripcion = 'Excelente';
			break;
		default:
			descripcion = '';
			break;
	}
	return descripcion;
}

function renderizarSelectoresChoices() {
	document.querySelectorAll('.autocomplete-estandar').forEach(input => {
		const parentDiv = input.closest('div');
		const idInput = input.getAttribute('data-id-input');

		const idCalificacion = input.getAttribute('data-id-calificacion');
		const idManiobra = input.getAttribute('data-id-maniobra');
		const coCodigoEstandarObtenido = input.getAttribute('data-co-codigo-estandar-obtenido');
		const idDivInput = parentDiv ? parentDiv.id : null;

		if (idDivInput) {
			var autocompleteEstandar = new autoComplete({
				selector: `#${idDivInput} input`,
				placeHolder: "Estándar",
				data: {
					src: [
						{ "noNombre": "Insuficiente", "coCodigo": "I" },
						{ "noNombre": "Demostrativo", "coCodigo": "D" },
						{ "noNombre": "Regular", "coCodigo": "R" },
						{ "noNombre": "Bueno", "coCodigo": "B" },
						{ "noNombre": "Excelente", "coCodigo": "E" },
						{ "noNombre": "Sin Estandar", "coCodigo": "SE" },
						{ "noNombre": "Peligrosa", "coCodigo": "P" },
						{ "noNombre": "No Realizado", "coCodigo": "NR" }
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
							console.log("SELECCIONADO: ", seleccionado)

							autocompleteEstandar.input.value = seleccionado.coCodigo;
							console.log("SELECCIONADO codigo: ", seleccionado.coCodigo)
							console.log("idcalificaicon: ", idCalificacion)
							console.log("idManiobra: ", idManiobra)

							inputComplete.classList.remove(renderizarColorInputEstandar(coCodigoEstandarObtenido));
							inputComplete.classList.add(renderizarColorInputEstandar(seleccionado.coCodigo));

							const response = await fetchCalificarManiobra(idCalificacion, idManiobra, seleccionado.coCodigo);
							const { type, message, data } = response;


							notification(type, message);
							renderizarLista();
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
/*
// Vertical Swiper
var swiper = new Swiper(".vertical-swiper", {
   slidesPerView: 2,
   spaceBetween: 10,
   mousewheel: true,
   loop: true,
   direction: "vertical",
   autoplay: {
	   delay: 2500,
	   disableOnInteraction: false,
   },
});*/


window.addEventListener("resize", function() {
	var userProfileSidebar = document.querySelector(".layout-rightside-col");
	if (userProfileSidebar) {
		Array.from(document.querySelectorAll(".layout-rightside-btn")).forEach(function() {
			if (window.outerWidth < 1699 || window.outerWidth > 3440) {
				userProfileSidebar.classList.remove("d-block");
			} else if (window.outerWidth > 1699) {
				userProfileSidebar.classList.add("d-block");
			}
		});
	}

	var htmlAttr = document.documentElement;
	if (htmlAttr.getAttribute("data-layout") == "semibox") {
		userProfileSidebar.classList.remove("d-block");
		userProfileSidebar.classList.add("d-none");
	}
});

var overlay = document.querySelector('.overlay');
if (overlay) {
	document.querySelector(".overlay").addEventListener("click", function() {
		if (document.querySelector(".layout-rightside-col").classList.contains('d-block') == true) {
			document.querySelector(".layout-rightside-col").classList.remove("d-block");
		}
	});
}

window.addEventListener("load", function() {
	var userProfileSidebar = document.querySelector(".layout-rightside-col");
	if (userProfileSidebar) {
		Array.from(document.querySelectorAll(".layout-rightside-btn")).forEach(function() {
			if (window.outerWidth < 1699 || window.outerWidth > 3440) {
				userProfileSidebar.classList.remove("d-block");
			} else if (window.outerWidth > 1699) {
				userProfileSidebar.classList.add("d-block");
			}
		});
	}

	var htmlAttr = document.documentElement

	if (htmlAttr.getAttribute("data-layout") == "semibox") {
		if (window.outerWidth > 1699) {
			userProfileSidebar.classList.remove("d-block");
			userProfileSidebar.classList.add("d-none");
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

async function fetchCalificarManiobra(idCalificacion, idManiobra, coEstandarObtenido) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				idCalificacion: idCalificacion,
				idManiobra: idManiobra,
				coEstandarObtenido: coEstandarObtenido
			})
		}
		const response = await fetch(`/calificaciones/calificarManiobra`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido calificar la maniobra: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al calificar la maniobra:', error);
		throw error;
	}
}

async function fetchRegistrarCor(idDetalleCalificacion, txtCausa, txtObservacion, txtRecomendacion) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				idDetalleCalificacion: idDetalleCalificacion,
				txCausa: txtCausa,
				txObservacion: txtObservacion,
				txRecomendacion: txtRecomendacion
			})
		}
		const response = await fetch(`/cor/guardar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido calificar la maniobra: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al calificar la maniobra:', error);
		throw error;
	}
}

async function fetchObtenerMatrizACalificar() {
	try {
		const response = await fetch(`/calificaciones/obtenerMatriz`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la matriz a calificar: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la matriz a calificar:', error);
		throw error;
	}
}

async function fetchListarRestriccionesPorIdDetalleCalificacion(idDetalleCalificacion) {
	try {
		const response = await fetch(`/restriccionesEstandar/listarPorIdDetalleCalificacion?idDetalleCalificacion=${idDetalleCalificacion}`);
		if (!response.ok) {
			throw new Error(`No se ha podido obtener las restricciones: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener las restricciones:', error);
		throw error;
	}
}

