const loader = document.querySelector("#elmLoader");
const contentSubFase = document.querySelector("#content-sub-fase");
const contentDetalleMision = document.querySelector("#content-detalle-mision");

//Guardar
let DOCUMENTO_GLOBAL = {
	subFase: {},
	misiones: [],
	operaciones: [],
	lstTipoMisiones: []
}

let cboBusquedaEscuadron = document.getElementById("cboBusquedaEscuadron"),
	cboBusquedaPrograma = document.getElementById("cboBusquedaPrograma"),
	cboBusquedaFase = document.getElementById("cboBusquedaFase"),
	cboBusquedaSubFase = document.getElementById("cboBusquedaSubFase"),

	cboFiltroOperacion = document.getElementById("cboFiltroOperacion"),

	objSFtxDescripcionSubFase = document.getElementById("txtSubFase-txDescripcionSubFase"),
	objSFnuTotalHora = document.getElementById("txtSubFase-nuTotalHora"),
	objSFnuTotalManiobra = document.getElementById("txtSubFase-nuTotalManiobra"),
	objSFnuTotalMision = document.getElementById("txtSubFase-nuTotalMision"),
	objSFtxDescripcionPrograma = document.getElementById("txtSubFase-txDescripcionPrograma"),
	objSFtxDescripcionFase = document.getElementById("txtSubFase-txDescripcionFase"),
	btnGenerarOperacion = document.getElementById("btnGenerarOperacion"),
	btnGenerarCruce = document.getElementById("btnGenerarCruce"),
	btnCompletarMision = document.getElementById("btnCompletarMision");

let cboBusquedaEscuadronVal = new Choices(cboBusquedaEscuadron),
	cboBusquedaProgramaVal = new Choices(cboBusquedaPrograma),
	cboBusquedaFaseVal = new Choices(cboBusquedaFase),
	cboBusquedaSubFaseVal = new Choices(cboBusquedaSubFase),

	cboFiltroOperacionVal = new Choices(cboFiltroOperacion);

async function llenarOperacionPorEscuadron(idEscuadron) {
	if (idEscuadron == null || idEscuadron == "") {
		if (cboFiltroOperacionVal)
			cboFiltroOperacionVal.destroy();
		cboFiltroOperacionVal = new Choices(cboFiltroOperacion, {
			searchEnabled: true
		});
		return;
	}

	try {
		const response = await fetchListarOperacionesPorIdEscuadron(idEscuadron);

		if (cboFiltroOperacionVal)
			cboFiltroOperacionVal.destroy();
		cboFiltroOperacionVal = new Choices(cboFiltroOperacion, {
			searchEnabled: true
		});

		const choices = [];
		response.forEach(operacion => {
			choices.push({
				value: operacion.idOperacion.toString(),
				label: operacion.noNombre,
				selected: false,
				disabled: false
			});
		});

		cboFiltroOperacionVal.setChoices(choices, 'value', 'label', false);

	} catch (error) {
		console.error('Error al listar Operaciones:', error);
	}
}

function isEmptyObject(obj) {
	return Object.keys(obj).length === 0 && obj.constructor === Object;
}

btnCompletarMision.addEventListener("click", async function(e) {
	if (isEmptyObject(DOCUMENTO_GLOBAL.subFase) || DOCUMENTO_GLOBAL.subFase == "" || DOCUMENTO_GLOBAL.subFase == null) {
		e.preventDefault();
		e.stopPropagation();
		notification('warning', "Debe seleccionar una Sub Fase")
		return;
	}
	if (DOCUMENTO_GLOBAL.misiones == [] || DOCUMENTO_GLOBAL.misiones == "" || DOCUMENTO_GLOBAL.misiones == null) {
		e.preventDefault();
		e.stopPropagation();
		notification('warning', "Debe generar las misiones")
		return;
	}
	if (DOCUMENTO_GLOBAL.operaciones == [] || DOCUMENTO_GLOBAL.operaciones == "" || DOCUMENTO_GLOBAL.operaciones == null) {
		e.preventDefault();
		e.stopPropagation();
		notification('warning', "Debe seleccionar operaciones")
		return;
	}

	let mision = {
		"subfase": DOCUMENTO_GLOBAL.subFase,
		"lstMisiones": DOCUMENTO_GLOBAL.misiones,
		"lstOperaciones": DOCUMENTO_GLOBAL.operaciones
	};

	try {
		const response = await fetchMatricularMision(mision);
		const { type, message } = response;
		alert(type, message);
		if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
			interfazBloqueo();
		}
	} catch (error) {
		console.error('Error al matricular la misión:', error);
	}

});

function interfazBloqueo() {
	const btnCompletarMision = document.getElementById('btnCompletarMision');
	const btnGenerarCruce = document.getElementById('btnGenerarCruce');
	const btnGenerarOperacion = document.getElementById('btnGenerarOperacion');

	const btnBuscarSubFase = document.getElementById('btnBuscarSubFase');
	const successIcon = document.getElementById('successIcon');
	const msgEmergente = document.getElementById('msgEmergente');
	const contentProgress = document.getElementById('contentProgress');
	const barraProgreso = document.getElementById('barraProgreso');

	let contadorIntervalo = document.getElementById('contadorIntervalo');
	let txtTituloCompletado = document.getElementById('txtTituloCompletado');
	let txtSubTituloCompletado = document.getElementById('txtSubTituloCompletado');
	let idEnlace = document.getElementById('idEnlace');

	btnCompletarMision.style.display = 'none';
	btnGenerarCruce.style.display = 'none';
	btnGenerarOperacion.style.display = 'none';

	txtTituloCompletado.innerText = "¡Misión registrada con éxito! Ahora, definamos los estándares.";
	txtSubTituloCompletado.innerText = "Puede empezar a brindar información sobre los estándares necesarios para cada misión.";
	successIcon.setAttribute('src', 'https://cdn.lordicon.com/lupuorrc.json');

	cboBusquedaEscuadronVal.disable();
	cboBusquedaProgramaVal.disable();
	cboBusquedaFaseVal.disable();
	cboBusquedaSubFaseVal.disable();
	cboFiltroOperacionVal.disable();

	if (DOCUMENTO_GLOBAL.subFase.idSubfase != null || DOCUMENTO_GLOBAL.subFase.idSubfase != "") {
		btnBuscarSubFase.style.display = 'block';

		idEnlace.innerText = 'SF-' + DOCUMENTO_GLOBAL.subFase.idSubFase;
		contentProgress.style.display = 'block';
		msgEmergente.style.display = 'block';

		let contador = 20;

		const interval = setInterval(() => {
			contador--;
			contadorIntervalo.textContent = contador;
			const progreso = (contador / 20) * 100;
			barraProgreso.style.width = `${progreso}%`;
			barraProgreso.setAttribute('aria-valuenow', progreso);

			if (contador <= 0) {
				clearInterval(interval);

				txtTituloCompletado.innerText = "¡Completa, registra y establece las misiones para cada sub fase académica!";
				txtSubTituloCompletado.innerText = "Cada misión implica una serie de maniobras que se evaluarán según un estándar mínimo establecido.";
				successIcon.setAttribute('src', 'https://cdn.lordicon.com/wzwygmng.json');

				btnBuscarSubFase.style.display = 'none';
				contentProgress.style.display = 'none';
				msgEmergente.style.display = 'none';
				idEnlace.innerText = '';

				resetearRMSubFase();

				renderizarRMMisiones(null);
				renderizarCardMisiones(null);

				renderizarAcordeonOperacion(null);
				renderizarRMOperaciones(null);

				btnCompletarMision.style.display = 'block';
				btnGenerarCruce.style.display = 'block';
				btnGenerarOperacion.style.display = 'block';

				cboBusquedaEscuadronVal.enable();
				cboBusquedaEscuadronVal.destroy();
				cboBusquedaEscuadronVal = new Choices(cboBusquedaEscuadron, {
					searchEnabled: true,
					shouldSort: false,
				});


				cboBusquedaProgramaVal.enable();
				cboBusquedaProgramaVal.destroy();
				cboBusquedaProgramaVal = new Choices(cboBusquedaPrograma, {
					searchEnabled: true,
					shouldSort: false,
				});

				cboBusquedaFaseVal.enable();
				cboBusquedaFaseVal.destroy();
				cboBusquedaFaseVal = new Choices(cboBusquedaFase, {
					searchEnabled: true
				});

				cboBusquedaSubFaseVal.enable();
				cboBusquedaSubFaseVal.destroy();
				cboBusquedaSubFaseVal = new Choices(cboBusquedaSubFase, {
					searchEnabled: true
				});


				cboFiltroOperacionVal.enable();
				cboFiltroOperacionVal.destroy();
				cboFiltroOperacionVal = new Choices(cboFiltroOperacion, {
					searchEnabled: true
				});

				contentSubFase.style.display = 'none';
				DOCUMENTO_GLOBAL.subFase = {};

			}
		}, 2000);
	}

}

cboBusquedaEscuadron.addEventListener("change", async function() {

	resetearRMSubFase();

	renderizarRMMisiones(null);
	renderizarCardMisiones(null);

	renderizarAcordeonOperacion(null);
	renderizarRMOperaciones(null);

	let idEscuadron = cboBusquedaEscuadron.value;

	try {
		const response = await fetchListarProgramasPorIdEscuadron(idEscuadron);
		if (cboBusquedaProgramaVal)

			cboBusquedaProgramaVal.destroy();
		cboBusquedaProgramaVal = new Choices(cboBusquedaPrograma, {
			searchEnabled: true,
			shouldSort: false,
		});

		cboBusquedaFaseVal.destroy();
		cboBusquedaFaseVal = new Choices(cboBusquedaFase, {
			searchEnabled: true
		});

		cboBusquedaSubFaseVal.destroy();
		cboBusquedaSubFaseVal = new Choices(cboBusquedaSubFase, {
			searchEnabled: true
		});
		contentSubFase.style.display = 'none';

		const groupedData = {};

		response.forEach(programa => {
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
				selected: false,
				disabled: false
			});
		});

		const groupedChoices = Object.values(groupedData);
		cboBusquedaProgramaVal.setChoices(groupedChoices, 'value', 'label', false);

	} catch (error) {
		console.error('Error al listar programas:', error);
	}
	await llenarOperacionPorEscuadron(idEscuadron);
});

cboBusquedaPrograma.addEventListener("change", async function() {
	resetearRMSubFase();

	renderizarRMMisiones(null);
	renderizarCardMisiones(null);

	renderizarAcordeonOperacion(null);
	renderizarRMOperaciones(null);

	let idPrograma = cboBusquedaPrograma.value;

	try {
		const response = await fetchListarFasesPorIdPrograma(idPrograma);
		if (cboBusquedaFaseVal)

			cboBusquedaFaseVal.destroy();
		cboBusquedaFaseVal = new Choices(cboBusquedaFase, {
			searchEnabled: true
		});

		cboBusquedaSubFaseVal.destroy();
		cboBusquedaSubFaseVal = new Choices(cboBusquedaSubFase, {
			searchEnabled: true
		});
		contentSubFase.style.display = 'none';

		const choices = [];

		response.forEach(fase => {
			choices.push({
				value: fase.idFase.toString(),
				label: fase.txDescripcionFase,
				selected: false,
				disabled: false
			});
		});

		cboBusquedaFaseVal.setChoices(choices, 'value', 'label', false);


	} catch (error) {
		console.error('Error al listar fases:', error);
	}
});

cboBusquedaFase.addEventListener("change", async function() {
	resetearRMSubFase();

	renderizarRMMisiones(null);
	renderizarCardMisiones(null);

	renderizarAcordeonOperacion(null);
	renderizarRMOperaciones(null);

	let idFase = cboBusquedaFase.value;

	try {
		const response = await fetchListarSubFasesPorIdFase(idFase);
		if (cboBusquedaSubFaseVal) cboBusquedaSubFaseVal.destroy();
		cboBusquedaSubFaseVal = new Choices(cboBusquedaSubFase, {
			searchEnabled: true
		});
		const choices = [];

		response.forEach(subFase => {
			choices.push({
				value: subFase.idSubFase.toString(),
				label: subFase.txDescripcionSubFase,
				selected: false,
				disabled: false
			});
		});

		cboBusquedaSubFaseVal.setChoices(choices, 'value', 'label', false);


	} catch (error) {
		console.error('Error al listar sub fases:', error);
	}
});

cboBusquedaSubFase.addEventListener("change", async function() {
	loader.style.display = 'block';
	contentSubFase.style.display = 'none';

	renderizarRMMisiones(null);
	renderizarCardMisiones(null);

	renderizarAcordeonOperacion(null);
	renderizarRMOperaciones(null);

	var idSubFase = cboBusquedaSubFase.value;

	if (idSubFase == null || idSubFase == "") {
		resetearRMSubFase();
		loader.style.display = 'none';
		contentSubFase.style.display = 'none';
		DOCUMENTO_GLOBAL.subFase = null;
		return;
	}

	try {
		const response = await fetchBuscarSubFasePorId(idSubFase);
		DOCUMENTO_GLOBAL.subFase = response;
		renderizarRMSubFase(DOCUMENTO_GLOBAL.subFase);

		setTimeout(function() {

			loader.style.display = 'none';
			contentSubFase.style.display = 'block';

			objSFtxDescripcionSubFase.innerText = DOCUMENTO_GLOBAL.subFase.txDescripcionSubFase + " (" + DOCUMENTO_GLOBAL.subFase.coCodigo + ")";

			objSFnuTotalHora.innerHTML = "<strong>Horas Programadas: </strong>" + DOCUMENTO_GLOBAL.subFase.nuTotalHora + ((DOCUMENTO_GLOBAL.subFase.nuTotalHora > 1) ? " hrs" : " hora");
			objSFnuTotalManiobra.innerHTML = "<strong>Total de Maniobras: </strong>" + DOCUMENTO_GLOBAL.subFase.nuTotalManiobra + ((DOCUMENTO_GLOBAL.subFase.nuTotalManiobra > 1) ? " maniobras" : " maniobra");
			objSFnuTotalMision.innerHTML = "<strong>Total de Misiones: </strong>" + DOCUMENTO_GLOBAL.subFase.nuTotalMision + ((DOCUMENTO_GLOBAL.subFase.nuTotalMision > 1) ? " misiones" : " misión");

		}, 1000)
	} catch (error) {
		console.error('Error al buscar sub fase:', error);
	}
});

btnGenerarCruce.addEventListener("click", async function(e) {
	if (DOCUMENTO_GLOBAL.subFase == null || (DOCUMENTO_GLOBAL.subFase.coCodigo == "" || DOCUMENTO_GLOBAL.subFase.coCodigo == null)) {
		e.preventDefault();
		e.stopPropagation();
		notification('warning', "No tiene seleccionado el código")
		return;
	}

	if (DOCUMENTO_GLOBAL.subFase == null || (DOCUMENTO_GLOBAL.subFase.nuTotalMision == "" || DOCUMENTO_GLOBAL.subFase.nuTotalMision == null)) {
		e.preventDefault();
		e.stopPropagation();
		notification('warning', "No tiene cantidad la misión")
		return;
	}

	const lstDetalleMision = crearListaDetalleMision(DOCUMENTO_GLOBAL.subFase);
	renderizarRMMisiones(lstDetalleMision);
	await renderizarCardMisiones(lstDetalleMision);
})

btnGenerarOperacion.addEventListener("click", async function(e) {
	const arrayOperacion = cboFiltroOperacionVal.getValue(true);
	if (!(Array.isArray(arrayOperacion) && arrayOperacion.length > 0)) {
		e.preventDefault();
		e.stopPropagation();
		notification('warning', "No tiene seleccionada ninguna operación")
		return;
	}

	try {
		const response = await fetchListarDetalleManiobras(arrayOperacion);
		const lstDetalleOperacion = crearListaOperacion(response);
		renderizarAcordeonOperacion(lstDetalleOperacion);
		renderizarRMOperaciones(lstDetalleOperacion);
	} catch (error) {
		console.error('Error al insertar la fase:', error);
	}
})

function crearListaDetalleMision(data) {
	const lstDetalleMision = [];
	for (let i = 1; i <= data.nuTotalMision; i++) {
		lstDetalleMision.push({
			idSubFase: data.idSubFase,
			idTipoMision: null,
			txDescripcionTipoMision: "-",
			coCodigo: data.coCodigo.replace(/\//g, "-") + "-" + i,
			flChequeo: 0
		});
	}
	return lstDetalleMision;
}

function crearListaOperacion(data) {
	const lstDetalleOperacion = [];
	data.forEach(obj => {
		lstDetalleOperacion.push({
			idOperacion: obj.idOperacion,
			noNombre: obj.noNombre,
			lstManiobras: crearListaManiobras(obj.lstManiobras)
		});
	})
	return lstDetalleOperacion;
}

function crearListaManiobras(lstManiobras) {
	const nuevaListaManiobras = lstManiobras.map(maniobra => {
		return {
			idManiobra: maniobra.idManiobra,
			txDescripcionBancoManiobra: maniobra.txDescripcionBancoManiobra,
			nuOrden: 0,
			flTransaccion: 1
		};
	});

	return nuevaListaManiobras;
}

async function renderizarCardMisiones(lstDetalleMision) {
	if (lstDetalleMision == null) {
		contentDetalleMision.innerHTML = ''; 
		const msgMision = document.getElementById('msg-mision');
		msgMision.style.display = 'block';
		return;
	}

	contentDetalleMision.innerHTML = '';

	await llenarCboTipoMision();

	lstDetalleMision.forEach(detalle => {
		const div = document.createElement('div');
		div.className = 'col-lg-3 col-sm-6';


		const opcionesCboMision = `
    		<option value="" selected>Tipo Misión</option>`+
			DOCUMENTO_GLOBAL.lstTipoMisiones.map(tipoMision => {
				return `<option value="${tipoMision.idTipoMision}">
                   ${tipoMision.coCodigo} 
             </option>`;
			}).join('');

		div.innerHTML = `
          	
            <div class="form-check card-radio">
            	<div class="mb-1">
		            <select class="form-control mb-0" 
		                	data-choices 
		                	data-choices-search-false 
		                	id="cbo-${detalle.coCodigo}">
				        ${opcionesCboMision}
				    </select>
				</div>
                <input id="radio-${detalle.coCodigo}" type="radio" class="form-check-input" name="mision-radio"   ${detalle.flChequeo == 1 ? 'checked' : ''}>
                <label class="form-check-label text-center" for="radio-${detalle.coCodigo}"> 
					<span class="text-muted fs-24 mb-2 text-wrap d-block">${detalle.coCodigo}</span>
                </label>
            </div>
            <div class="d-flex flex-wrap p-2 py-1 bg-light rounded-bottom border mt-n1">
                <div>
                    <a href="#" class="d-block text-body p-1 px-2" data-bs-toggle="modal" data-bs-target="#addAddressModal">
                        <i class="ri-pencil-fill text-muted align-bottom me-1"></i>Edit
                    </a>
                </div>
                <div>
                    <a href="#" class="d-block text-body p-1 px-2" data-bs-toggle="modal" data-bs-target="#removeItemModal">
                        <i class="ri-delete-bin-fill text-muted align-bottom me-1"></i>Remove
                    </a>
                </div>
            </div>
            
        `;

		contentDetalleMision.appendChild(div);


		const selectElement = div.querySelector(`#cbo-${detalle.coCodigo}`);
		const radioElement = div.querySelector(`#radio-${detalle.coCodigo}`);
		if (selectElement) {
			selectElement.addEventListener('change', function() {
				const selectedTipoMisionId = selectElement.value;
				const selectedTipoMisionTexto = this.options[this.selectedIndex].textContent;

				actualizarListaMisiones(detalle.coCodigo, selectedTipoMisionId, selectedTipoMisionTexto);
			});

			new Choices(selectElement, {
				searchEnabled: false
			});
		}

		if (radioElement) {
			radioElement.addEventListener('change', function() {
				const radioElementChecked = radioElement.checked;
				const radioElementId = radioElement.id.replace('radio-', '');

				lstDetalleMision.find(m => {
					if (m.coCodigo === radioElementId) {
						m.flChequeo = radioElementChecked ? 1 : 0;
					} else {
						m.flChequeo = 0;
					}
				});

				renderizarRMMisiones(lstDetalleMision)
			});
		}


	});
}

function actualizarListaMisiones(coCodigoDetalle, idTipoMisionSeleccionado, textoTipoMisionSeleccionado) {
	const detalleModificado = DOCUMENTO_GLOBAL.misiones.map(mision => {
		if (mision.coCodigo === coCodigoDetalle) {
			mision.idTipoMision = idTipoMisionSeleccionado;
			mision.txDescripcionTipoMision = (textoTipoMisionSeleccionado == "null" || textoTipoMisionSeleccionado == "" || textoTipoMisionSeleccionado == null) ? "-" : textoTipoMisionSeleccionado;
		}
		return mision;
	});
	renderizarRMMisiones(detalleModificado)
}

function renderizarAcordeonOperacion(data) {
	const accordionContainer = document.getElementById('accordionOperacion');
	accordionContainer.innerHTML = '';
	DOCUMENTO_GLOBAL.operaciones = data;

	if (data == null) {
		const simpleBarOperacion = document.getElementById('simple-bar-operacion');
		const estadoBtnManiobra = document.getElementById('estado-btn-maniobra');
		const rmLstOperacion = document.getElementById('rm-lst-operacion');
		const msgRmOperacion = document.getElementById('msg-rm-operacion');

		if (cboFiltroOperacionVal) {
			cboFiltroOperacionVal.removeActiveItems();
		}

		rmLstOperacion.innerHTML = '';
		simpleBarOperacion.style.height = '0px';
		msgRmOperacion.style.display = 'block';

		estadoBtnManiobra.classList.remove('btn-soft-success');
		estadoBtnManiobra.classList.add('btn-soft-danger');
		estadoBtnManiobra.innerHTML = '<i class="ri-share-line me-1 align-bottom"></i> No Verificada';

		return;
	}

	data.forEach((item, index) => {
		const idHeader = `accordionHeader${item.idOperacion}`;
		const idCollapse = `accordionCollapse${item.idOperacion}`;

		const accordionItem = document.createElement('div');
		accordionItem.className = 'accordion-item material-shadow';

		accordionItem.innerHTML = `
            <h2 class="accordion-header" id="${idHeader}">
                <button class="accordion-button ${index === 0 ? '' : 'collapsed'}" type="button" data-bs-toggle="collapse" data-bs-target="#${idCollapse}" aria-expanded="${index === 0 ? 'true' : 'false'}" aria-controls="${idCollapse}">
                    ${item.noNombre}
                </button>
            </h2>
            <div id="${idCollapse}" class="accordion-collapse collapse ${index === 0 ? 'show' : ''}" aria-labelledby="${idHeader}" data-bs-parent="#accordionOperacion">
                <div class="accordion-body">
                    ${item.lstManiobras.map(maniobra => ` 
                    	<div class="my-2 row align-items-center"> 
	                    	<div class="col-6 col-md-2">
							    <div class="form-icon right">
							        <input type="number" class="form-control form-control-icon" id="orden-${maniobra.idManiobra}" placeholder="Orden">
							        <i class="ri-line-height"></i>
							    </div>
							</div>                 	
	                        <div class="col-12 col-md-9">
		                        <p class="m-0"><strong>Maniobra ${maniobra.idManiobra}:</strong> ${maniobra.txDescripcionBancoManiobra}</p>
	                        </div>  
							<div class="col-6 col-md-1">
		                        <div class="mr-1 form-check form-switch form-switch-custom form-switch-success">
		                            <input class="form-check-input switch-checkbox" type="checkbox" role="switch" id="switch-check-${maniobra.idManiobra}"  ${maniobra.flTransaccion == 1 ? 'checked' : ''}> 
		                        </div>
							</div>
                    	</div>
                    `).join('')} 
                </div>
            </div>
        `;

		accordionContainer.appendChild(accordionItem);

		const checkboxes = accordionItem.querySelectorAll('.switch-checkbox');
		checkboxes.forEach(checkbox => {
			checkbox.addEventListener('change', function() {
				const maniobraId = parseInt(checkbox.id.replace('switch-check-', ''));
				const isChecked = checkbox.checked;
				const maniobra = item.lstManiobras.find(m => m.idManiobra === maniobraId);
				if (maniobra) {
					maniobra.flTransaccion = isChecked ? 1 : 0;
				}
				renderizarRMOperaciones(data);
			});
		});

		const numberInputs = accordionItem.querySelectorAll('input[type="number"]');
		numberInputs.forEach(input => {
			input.addEventListener('change', function() {
				const maniobraId = parseInt(input.id.replace('orden-', ''));
				const isValue = input.value;
				const maniobra = item.lstManiobras.find(m => m.idManiobra === maniobraId);
				if (maniobra) {
					maniobra.nuOrden = isValue;
				}
				renderizarRMOperaciones(data);
			});
		});
	});
}

document.querySelectorAll('.card-radio input[type="radio"]').forEach(radio => {
	radio.addEventListener('change', function() {
		document.querySelectorAll('.card-radio input[type="radio"]').forEach(otherRadio => {
			if (otherRadio !== this) {
				otherRadio.checked = false;
			}
		});
	});
});

async function llenarCboTipoMision() {
	try {
		const response = await fetchListarTipoMisiones();

		DOCUMENTO_GLOBAL.lstTipoMisiones = response.map(detalle => ({
			idTipoMision: detalle.idTipoMision,
			noNombre: detalle.noNombre,
			txDescripcion: detalle.txDescripcion,
			coCodigo: detalle.coCodigo,
			flEstado: detalle.flEstado
		}));
	} catch (error) {
		console.error('Error al llenar el combo tipo mision:', error);
	}
}

function renderizarRMSubFase(data) {
	const rmCoCodigo = document.getElementById("rm-co-codigo");
	const rmNoNombre = document.getElementById("rm-no-nombre");
	const rmCardSubFase = document.getElementById("rm-card-sub-fase");
	rmCardSubFase.checked = true;

	rmCoCodigo.innerText = data.coCodigo;
	rmNoNombre.innerText = data.txDescripcionSubFase;
}

function renderizarRMMisiones(data) {

	DOCUMENTO_GLOBAL.misiones = data;

	const rmLstMision = document.getElementById('rm-lst-mision');
	const estadoBtnMision = document.getElementById('estado-btn-mision');
	const msgRmMision = document.getElementById('msg-rm-mision');

	if (data == null) {
		msgRmMision.style.display = 'block';
		rmLstMision.innerHTML = '';
		estadoBtnMision.classList.remove('btn-soft-success');
		estadoBtnMision.classList.add('btn-soft-danger');
		estadoBtnMision.innerHTML = '<i class="ri-share-line me-1 align-bottom"></i> No Verificada';
		return;
	}

	msgRmMision.style.display = 'none';
	const htmlArray = data.map(mision => {
		const btnChequeo = mision.flChequeo == 1 ? 'btn-warning' : 'btn-primary';
		return `
            <button type="button" 
                    class="m-2 btn ${btnChequeo} position-relative">
                ${mision.coCodigo}
                <span class="position-absolute top-0 start-50 translate-middle badge rounded-pill bg-success">
                    ${mision.idTipoMision !== null && mision.idTipoMision !== "" ? mision.txDescripcionTipoMision : "-"}
                </span>
            </button>
        `;
	});
	rmLstMision.innerHTML = htmlArray.join('');

	const validarTipoMision = (mision) => mision.idTipoMision !== null && mision.idTipoMision !== "";
	const validarMisionChequeo = data.some(m => m.flChequeo === 1);

	const listaCompletada = data.every(validarTipoMision);
	const msgMision = document.getElementById('msg-mision');

	if (listaCompletada && validarMisionChequeo) {
		msgMision.style.display = 'none';
		estadoBtnMision.classList.remove('btn-soft-danger');
		estadoBtnMision.classList.add('btn-soft-success');
		estadoBtnMision.innerHTML = '<i class="ri-share-line me-1 align-bottom"></i> Verificada';
	} else {
		msgMision.style.display = 'block';
		estadoBtnMision.classList.remove('btn-soft-success');
		estadoBtnMision.classList.add('btn-soft-danger');
		estadoBtnMision.innerHTML = '<i class="ri-share-line me-1 align-bottom"></i> No Verificada';
	}

}

function renderizarRMOperaciones(data) {
	DOCUMENTO_GLOBAL.operaciones = data;

	const estadoBtnManiobra = document.getElementById('estado-btn-maniobra');
	const rmLstOperacion = document.getElementById('rm-lst-operacion');
	const msgRmOperacion = document.getElementById('msg-rm-operacion');
	const simpleBarOperacion = document.getElementById('simple-bar-operacion');

	rmLstOperacion.innerHTML = '';

	if (data == null) {
		const accordionContainer = document.getElementById('accordionOperacion');
		accordionContainer.innerHTML = '';

		if (cboFiltroOperacionVal) {
			cboFiltroOperacionVal.removeActiveItems();
		}

		simpleBarOperacion.style.height = '0px';
		msgRmOperacion.style.display = 'block';

		estadoBtnManiobra.classList.remove('btn-soft-success');
		estadoBtnManiobra.classList.add('btn-soft-danger');
		estadoBtnManiobra.innerHTML = '<i class="ri-share-line me-1 align-bottom"></i> No Verificada';
		return;
	}

	msgRmOperacion.style.display = 'none';
	simpleBarOperacion.style.height = '235px';

	const combinedList = [];

	data.forEach(operacion => {
		if (Array.isArray(operacion.lstManiobras) && operacion.lstManiobras.length > 0) {
			operacion.lstManiobras.sort((a, b) => {
				if (a.nuOrden !== 0 && b.nuOrden !== 0) {
					return a.nuOrden - b.nuOrden;
				} else if (a.nuOrden === 0 && b.nuOrden === 0) {
					return 0;
				} else {
					return a.nuOrden === 0 ? 1 : -1;
				}
			});

			operacion.lstManiobras.forEach(maniobra => {
				if (maniobra.flTransaccion == 1) {
					const combinedItem = {
						idOperacion: operacion.idOperacion,
						noNombreOperacion: operacion.noNombre,
						idManiobra: maniobra.idManiobra,
						txDescripcionBancoManiobra: maniobra.txDescripcionBancoManiobra,
						nuOrden: maniobra.nuOrden
					};
					combinedList.push(combinedItem);
				}
			});
		}
	});

	combinedList.sort((a, b) => {
		const indexA = data.findIndex(op => op.idOperacion === a.idOperacion);
		const indexB = data.findIndex(op => op.idOperacion === b.idOperacion);
		return indexA - indexB;
	});

	if (combinedList.length === 0) {
		const msgRmOperacion = document.getElementById('msg-rm-operacion');
		msgRmOperacion.style.display = 'block';
		estadoBtnManiobra.classList.remove('btn-soft-success');
		estadoBtnManiobra.classList.add('btn-soft-danger');
		estadoBtnManiobra.innerHTML = '<i class="ri-share-line me-1 align-bottom"></i> No Verificada';
		simpleBarOperacion.style.height = '0px';
		return;
	}

	const htmlArray = combinedList.map(item => `
        <li class="list-group-item">
            <div class="d-flex align-items-center">
                <div class="flex-grow-1">
                    <div class="d-flex">
                        <div class="flex-shrink-0">
                            <h6 class="fs-14 mb-0">${item.txDescripcionBancoManiobra}</h6>
                            <small class="text-muted"><strong>${item.noNombreOperacion}</strong></small>
                        </div>
                    </div>
                </div>
                <div class="flex-shrink-0">
                    <span class="text-muted">Orden: ${item.nuOrden}</span>
                </div>
            </div>
        </li>
    `);

	const ulElement = document.createElement('ul');
	ulElement.classList.add('list-group');
	ulElement.innerHTML = htmlArray.join('');
	rmLstOperacion.appendChild(ulElement);

	if (data) {
		estadoBtnManiobra.classList.remove('btn-soft-danger');
		estadoBtnManiobra.classList.add('btn-soft-success');
		estadoBtnManiobra.innerHTML = '<i class="ri-share-line me-1 align-bottom"></i> Verificada';
	} else {
		estadoBtnManiobra.classList.remove('btn-soft-success');
		estadoBtnManiobra.classList.add('btn-soft-danger');
		estadoBtnManiobra.innerHTML = '<i class="ri-share-line me-1 align-bottom"></i> No Verificada';
	}
}

function resetearRMSubFase() {
	const rmCoCodigo = document.getElementById('rm-co-codigo');
	const rmNoNombre = document.getElementById('rm-no-nombre');
	const rmCardSubFase = document.getElementById("rm-card-sub-fase");

	rmCoCodigo.innerText = "Código";
	rmNoNombre.innerText = "Sin selección";
	rmCardSubFase.checked = false;
}

const checkoutTab = document.querySelectorAll(".checkout-tab");
if (checkoutTab) {
	Array.from(document.querySelectorAll(".checkout-tab")).forEach(function(form) {

		// next tab
		const NextTab = form.querySelectorAll(".nexttab");
		if (NextTab) {
			Array.from(form.querySelectorAll(".nexttab")).forEach(function(nextButton) {
				var tabEl = form.querySelectorAll('button[data-bs-toggle="pill"]');
				if (tabEl) {
					Array.from(tabEl).forEach(function(item) {
						item.addEventListener('show.bs.tab', function(event) {
							event.target.classList.add('done');
						});
					});
					nextButton.addEventListener("click", function() {
						var nextTab = nextButton.getAttribute('data-nexttab');
						if (nextTab) {
							document.getElementById(nextTab).click();
						}
					});
				}

			});
		}

		//Pervies tab
		const previesTab = document.querySelectorAll(".previestab");
		if (previesTab) {
			Array.from(form.querySelectorAll(".previestab")).forEach(function(prevButton) {

				prevButton.addEventListener("click", function() {
					var prevTab = prevButton.getAttribute('data-previous');
					if (prevTab) {
						var totalDone = prevButton.closest("form").querySelectorAll(".custom-nav .done").length;
						if (totalDone) {
							for (var i = totalDone - 1; i < totalDone; i++) {
								(prevButton.closest("form").querySelectorAll(".custom-nav .done")[i]) ? prevButton.closest("form").querySelectorAll(".custom-nav .done")[i].classList.remove('done') : '';
							}
							document.getElementById(prevTab).click();
						}
					}
				});
			}); 
		}

		// Step number click
		const tabButtons = form.querySelectorAll('button[data-bs-toggle="pill"]');
		if (tabButtons) {
			Array.from(tabButtons).forEach(function(button, i) {
				button.setAttribute("data-position", i);
				button.addEventListener("click", function() {
					(form.querySelectorAll(".custom-nav .done").length > 0) ?
						Array.from(form.querySelectorAll(".custom-nav .done")).forEach(function(doneTab) {
							doneTab.classList.remove('done');
						}) : '';
					for (var j = 0; j <= i; j++) {
						tabButtons[j].classList.contains('active') ? tabButtons[j].classList.remove('done') : tabButtons[j].classList.add('done');
					}
				});
			});
		}
	});
}

async function fetchListarTipoMisiones() {
	try {
		const response = await fetch(`/tipoMisiones/listar`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de tipos de misiones: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de tipos de misiones:', error);
		throw error;
	}
}

async function fetchListarDetalleManiobras(array) {
	try {
		const response = await fetch(`/operaciones/listarDetalleManiobra?lstIds=${array}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de maniobras: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de maniobras:', error);
		throw error;
	}
}

async function fetchListarOperacionesPorIdEscuadron(idEscuadron) {
	try {
		const response = await fetch(`/operaciones/listarPorIdEscuadron?idEscuadron=${idEscuadron}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de operaciones: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de operaciones:', error);
		throw error;
	}
}

async function fetchListarProgramasPorIdEscuadron(idEscuadron) {
	try {
		const response = await fetch(`/programas/listarPorIdEscuadron?idEscuadron=${idEscuadron}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de programas: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de programas:', error);
		throw error;
	}
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

async function fetchListarSubFasesPorIdFase(idFase) {
	try {
		const response = await fetch(`/subFases/listarPorIdFase?idFase=${idFase}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de sub fases: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de sub fases:', error);
		throw error;
	}
}

async function fetchBuscarSubFasePorId(idSubFase) {
	try {
		const response = await fetch(`/subFases/buscarPorId?id=${idSubFase}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la busqueda de sub fase: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la busqueda de sub fase:', error);
		throw error;
	}
}

async function fetchMatricularMision(mision) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(mision)
		}
		const response = await fetch(`/misiones/guardar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido matricular la misión: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al matricular la misión', error);
		throw error;
	}
}
