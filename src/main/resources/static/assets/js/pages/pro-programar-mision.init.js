const GLOBAL = {
	subFaseSeleccionada: {},
	lstGrupos: [],
	grupoSeleccionado: {},
	instructorSeleccionado: {},
	programaSeleccionado: {}
};

let cboBusquedaEscuadron = document.getElementById("cboBusquedaEscuadron"),
	cboBusquedaPrograma = document.getElementById("cboBusquedaPrograma"),
	cboGrupalPeriodo = document.getElementById("cboGrupalPeriodo"),
	cboIndividualPeriodo = document.getElementById("cboIndividualPeriodo"),
	cboGrupo = document.getElementById("cboGrupo"),
	//btnMatricularPrograma = document.getElementById('btnMatricularPrograma'),

	cboBusquedaProgramaVal = new Choices(cboBusquedaPrograma),
	cboGrupoVal = new Choices(cboGrupo);

renderizarListaGrupos();
renderizarListaInstructores();

cboBusquedaEscuadron.addEventListener("change", async function() {
	document.getElementById('alert-contenedor-programa').style.display = 'block';
	document.getElementById('contenedorPrograma').style.display = 'none';
	document.getElementById('noresult').style.display = 'none';

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

cboGrupalPeriodo.addEventListener("change", async function() {
	renderizarListaGrupos();
	const contenedor = document.getElementById('content-item-alumno')
	contenedor.innerHTML = '';
	document.getElementById('alert-contador-alumnos').style.display = 'block';
	document.getElementById('content-card-alumnos').style.display = 'none';
	document.getElementById('contador-alumnos').innerText = `0 Alumnos`;

	document.getElementById('btn-grupo-seleccionado').innerText = 'Selecciona Grupo';

	document.getElementById('contador-efectivos-alumnos').innerText = 0;
	document.getElementById('contador-descuentos-alumnos').innerText = 0;
	document.getElementById('contador-disponibles-alumnos').innerText = 0;
});

cboIndividualPeriodo.addEventListener("change", async function() {
	renderizarListaInstructores();
});

cboBusquedaPrograma.addEventListener("change", async function() {

	document.getElementById('loader').style.display = 'block';
	document.getElementById('contenedorPrograma').style.display = 'none';
	document.getElementById('noresult').style.display = 'none';

	let idPrograma = cboBusquedaPrograma.value;
	let programaText = cboBusquedaPrograma.options[cboBusquedaPrograma.selectedIndex].text;
	try {
		const lstFases = await fetchListarFasesPorIdPrograma(idPrograma);
		if (lstFases.length > 0) {
			GLOBAL.programaSeleccionado = {
				noNombrePrograma : programaText,
				lstFases: lstFases
			}
			renderizarMapa();
			document.getElementById('contenedorPrograma').style.display = 'block';
			document.getElementById('alert-contenedor-programa').style.display = 'none';
		} else {
			document.getElementById('noresult').style.display = 'block';
			document.getElementById('alert-contenedor-programa').style.display = 'none';
		}
		document.getElementById('loader').style.display = 'none';

	} catch (error) {
		console.error('Error al listar fases:', error);
	}
});

cboGrupo.addEventListener("change", async function() {
	let idGrupo = parseInt(cboGrupo.value);
	let grupoText = cboGrupo.options[cboGrupo.selectedIndex].text;
	try {
		const grupoEncontrado = GLOBAL.lstGrupos.find(grupo => grupo.idGrupo === idGrupo);
		if (grupoEncontrado) {

			GLOBAL.grupoSeleccionado = grupoEncontrado;

			renderizarCardAlumnos(grupoEncontrado.lstAlumnos);
			renderizarContadorSituacionAlumnos(grupoEncontrado.lstAlumnos);
			document.getElementById('btn-grupo-seleccionado').innerText = `Grupo Seleccionado: ${grupoText}`;
			document.getElementById('contador-alumnos').innerText = `${grupoEncontrado.lstAlumnos.length} Alumnos`;
			document.getElementById('alert-contador-alumnos').style.display = 'none';
			document.getElementById('content-card-alumnos').style.display = 'block';
			
			if(Object.keys(GLOBAL.programaSeleccionado).length > 0){
				renderizarMapa();
			}
				
		} else {

			GLOBAL.grupoSeleccionado = {};

			const contenedor = document.getElementById('content-item-alumno');
			contenedor.innerHTML = '';
			document.getElementById('btn-grupo-seleccionado').innerText = 'Selecciona Grupo';
			document.getElementById('alert-contador-alumnos').style.display = 'block';
			document.getElementById('content-card-alumnos').style.display = 'none';
			document.getElementById('contador-alumnos').innerText = `0 Alumnos`;

			document.getElementById('contador-efectivos-alumnos').innerText = 0;
			document.getElementById('contador-descuentos-alumnos').innerText = 0;
			document.getElementById('contador-disponibles-alumnos').innerText = 0;

		}
	} catch (error) {
		console.error('Error al listar miembros:', error);
	}
});

async function renderizarListaInstructores() {
	const nuPeriodo = cboIndividualPeriodo.value;
	try {
		const lstInstructores = await fetchListarInstructoresPorPeriodo(nuPeriodo);
		if (lstInstructores.length > 0) {
			renderizarCardInstructores(lstInstructores);
			document.getElementById('alert-contador-instructores').style.display = 'none';
			document.getElementById('content-card-instructores').style.display = 'block';

			document.getElementById('contador-instructores').innerText = `${lstInstructores.length} Instructores`;

		} else {

			GLOBAL.instructorSeleccionado = {};
			const contenedor = document.getElementById('content-item-instructor');
			contenedor.innerHTML = '';
			document.getElementById('alert-contador-instructores').style.display = 'block';
			document.getElementById('content-card-instructores').style.display = 'none';
			document.getElementById('contador-instructores').innerText = `0 Instructores`;
			document.getElementById('btn-instructor-seleccionado').innerText = 'Selecciona Instructor';
		}

	} catch (error) {
		console.error('Error al listar instructores:', error);
	}
}


async function renderizarListaGrupos() {
	const nuPeriodo = cboGrupalPeriodo.value;
	try {
		const lstGrupos = await fetchListarGruposPorPeriodo(nuPeriodo);
		GLOBAL.lstGrupos = lstGrupos;
		if (cboGrupoVal) cboGrupoVal.destroy();
		cboGrupoVal = new Choices(cboGrupo, {
			searchEnabled: false
		});
		const groupedChoices = llenarCboGrupos(lstGrupos);
		cboGrupoVal.setChoices(groupedChoices, 'value', 'label', false);
		document.getElementById('contador-grupos').innerText = `${lstGrupos.length} Grupos`;
	} catch (error) {
		console.error('Error al listar grupos:', error);
	}
}

function renderizarCardInstructores(lstInstructores) {
	const contenedor = document.getElementById('content-item-instructor');
	contenedor.innerHTML = '';

	lstInstructores.forEach(miembro => {
		const colorText = miembro.personal.sexo === 'MASCULINO' ? 'bg-soft-primary text-primary' : 'bg-soft-danger text-danger';

		const isUserProfile = (miembro.personal.fotografia != "null" && miembro.personal.fotografia != "" && miembro.personal.fotografia != null)
			? '<img src="/' + 'uploads/personal/' + miembro.personal.fotografia + '" alt="fotografía" class="img-fluid rounded-circle" />'
			: `<div class="avatar-title ${colorText} rounded-circle shadow">${miembro.personal.inicialesDatos.substring(0, 2)}</div>`;

		const cardHTML = `
                <div class="d-flex align-items-center">
                    <div class="avatar-xs flex-shrink-0 me-3">
                        ${isUserProfile}
                    </div>
                    <div class="flex-grow-1">
                        <h5 class="fs-13 mb-0">
                            <a href="#" class="text-body d-block">${miembro.personal.grado} FAP ${capitalizarIniciales(miembro.personal.datos)}</a>
                        </h5>
                    </div>
                    <div class="flex-shrink-0">
                        <div class="d-flex align-items-center gap-1">
                            <button type="button" class="btn btn-light btn-sm" data-id-miembro="${miembro.idMiembro}" data-datos="${miembro.personal.grado} FAP ${miembro.personal.datos}" >Seleccionar</button>
                        </div>
                    </div>
                </div>
            `;
		contenedor.innerHTML += cardHTML;
	});

	contenedor.addEventListener('click', function(event) {
		if (event.target && event.target.matches('button.btn.btn-light')) {
			const button = event.target;
			const idMiembro = button.getAttribute('data-id-miembro');
			const datosCompletos = button.getAttribute('data-datos');

			GLOBAL.instructorSeleccionado = {
				idMiembro: idMiembro,
				datosCompletos: datosCompletos
			}
			
			if(Object.keys(GLOBAL.programaSeleccionado).length > 0){
				renderizarMapa();
			}
			
			document.getElementById('btn-instructor-seleccionado').innerText = `Instructor Seleccionado: ${capitalizarIniciales(datosCompletos)}`;
		}
	});

}

function renderizarCardAlumnos(lstAlumnos) {
	const contenedor = document.getElementById('content-item-alumno');
	contenedor.innerHTML = '';

	lstAlumnos.forEach(miembro => {
		const colorText = miembro.personal.sexo === 'MASCULINO' ? 'bg-soft-primary text-primary' : 'bg-soft-danger text-danger';

		const isUserProfile = (miembro.personal.fotografia != "null" && miembro.personal.fotografia != "" && miembro.personal.fotografia != null)
			? '<img src="/' + 'uploads/personal/' + miembro.personal.fotografia + '" alt="fotografía" class="img-fluid rounded-circle" />'
			: `<div class="avatar-title ${colorText} rounded-circle shadow">${miembro.personal.inicialesDatos.substring(0, 2)}</div>`;

		const cardHTML = `
                <div class="d-flex align-items-center">
                    <div class="avatar-xs flex-shrink-0 me-3">
                        ${isUserProfile}
                    </div>
                    <div class="flex-grow-1">
                        <h5 class="fs-13 mb-0">
                            <a href="#" class="text-body d-block">${miembro.personal.grado} FAP ${capitalizarIniciales(miembro.personal.datos)}</a>
                        </h5>
                    </div>
                    <div class="flex-shrink-0">
                        <div class="d-flex align-items-center gap-1">
                            <button type="button" class="btn btn-light btn-sm">${miembro.noSituacion}</button>
                            
                        </div>
                    </div>
                </div>
            `;
		contenedor.innerHTML += cardHTML;
	});
}

function renderizarContadorSituacionAlumnos(lstAlumnos) {
	const efectivosAlumnos = document.getElementById('contador-efectivos-alumnos');
	const descuentosAlumnos = document.getElementById('contador-descuentos-alumnos');
	const disponiblesAlumnos = document.getElementById('contador-disponibles-alumnos');

	const cantidadBaja = lstAlumnos.filter(miembro => miembro.noSituacion === 'BAJA').length;

	efectivosAlumnos.innerText = lstAlumnos.length;
	descuentosAlumnos.innerText = cantidadBaja;
	disponiblesAlumnos.innerText = parseInt(lstAlumnos.length) - parseInt(cantidadBaja);
}

function capitalizarIniciales(nombreCompleto) {
	const palabras = nombreCompleto.split(' ');

	const nombreCapitalizado = palabras.map(palabra => {
		return palabra.charAt(0).toUpperCase() + palabra.slice(1).toLowerCase();
	}).join(' ');

	const maxLength = 26;
	if (nombreCapitalizado.length > maxLength) {
		return nombreCapitalizado.substring(0, maxLength) + '..';
	} else {
		return nombreCapitalizado;
	}
}

function llenarCboGrupos(lstGrupos) {
	const choices = [];

	lstGrupos.forEach(grupo => {
		choices.push({
			value: grupo.idGrupo.toString(),
			label: grupo.noNombre,
			selected: false,
			disabled: false
		});
	});
	return choices;
}

async function verificarInscripciones(lstIdsSubFases, lstIdMiembros) {
	const resultados = [];

	for (const idSubFase of lstIdsSubFases) {
		try {
			//const data = await fetchVerificarInscripcionMultiple(idSubFase, lstIdMiembros);
			const data = await fetchVerificarInscripcionSubFase(idSubFase, lstIdMiembros);
			const cantidadInscripcionVerificada = data.filter(mision => mision.flInscripcion === true).length
			
			resultados.push({
				idSubFase: idSubFase,
				nuInscripcionVerificada: cantidadInscripcionVerificada, 
				lstMisiones: data
			});
		} catch (error) {
			console.error(`Error al verificar inscripcion para el idSubFase ${idSubFase}:`, error);
			resultados.push({
				idSubFase: idSubFase,
				nuInscripcionVerificada: 0,  
			});
		}
	}
	return resultados;
}

async function renderizarMapa() {
	if(Object.keys(GLOBAL.programaSeleccionado).length == 0){
		notification('error', "Ha ocurrido un error al generar el mapa");
		return;
	}	
	
	const lstIdsSubFases = GLOBAL.programaSeleccionado.lstFases.flatMap(fase => fase.lstSubFases.map(subFase => subFase.idSubFase)); 
	let lstIdMiembros = "";
	if (Object.keys(GLOBAL.grupoSeleccionado).length > 0 && Object.keys(GLOBAL.instructorSeleccionado).length === 0) { 
		const idsMiembros = GLOBAL.grupoSeleccionado.lstAlumnos.map(alumno => alumno.idMiembro);
		lstIdMiembros = idsMiembros.join(',');  
	} else if (Object.keys(GLOBAL.instructorSeleccionado).length > 0 && Object.keys(GLOBAL.grupoSeleccionado).length === 0) { 
		lstIdMiembros = GLOBAL.instructorSeleccionado.idMiembro; 
	} 
	const lstVerificacion = await verificarInscripciones(lstIdsSubFases, lstIdMiembros);
	
	const lstValidacionInscripcion = GLOBAL.programaSeleccionado.lstFases.map(fase => ({
		...fase,
		lstSubFases: fase.lstSubFases.map(subfase => {
			const verificacion = lstVerificacion.find(v => v.idSubFase === subfase.idSubFase);

			return {
				...subfase,
				nuInscripcionVerificada: verificacion ? verificacion.nuInscripcionVerificada : 0,
				lstMisiones: verificacion.lstMisiones
			};
		})
	}));
 	console.log("VALICAION:",lstValidacionInscripcion)
	const contenedor = document.getElementById('contenedorPrograma');
	let html = ` 
                <div class="hori-sitemap"> 
                        <ul class="list-unstyled mb-0">
                            <li class="p-0 parent-title">
                            	<a href="javascript: void(0);" class="fw-semibold fs-14">
                            		${GLOBAL.programaSeleccionado.noNombrePrograma}
                            	</a>
                            </li>
							<li>
								<ul class="list-unstyled second-list row g-0 pt-0 justify-content-center">`;
									lstValidacionInscripcion.forEach(fase => {
									html += `<li class="col-sm-4">
												<a href="javascript: void(0);" class="fw-semibold">${fase.txDescripcionFase}</a>`;

									if (fase.lstSubFases && fase.lstSubFases.length > 0) {
										html += `<ul class="list-unstyled second-list pt-0">
													<li class="mx-2">`;
										fase.lstSubFases.forEach(subfase => {
											let iconStructure = subfase.lstMisiones.length > 0 ? ' <i class="ri-stack-line me-1 align-bottom"></i>' : '<span class="px-2"></span>';
											
											let iconClass = subfase.nuInscripcionVerificada > 0 ? 'ri-checkbox-circle-line text-success' : 'ri-indeterminate-circle-line text-danger';
											let icon = `<i class="${iconClass} me-1 align-bottom"></i>`;
											
											html += `<div>
							                        	<a href="javascript: void(0);" data-id-sub-fase="${subfase.idSubFase}" data-nombre-sub-fase="${subfase.txDescripcionSubFase}" style="border: 1px solid #e1e0e0;border-radius: .3rem;box-shadow: 0 1px 2px rgba(56, 65, 74, .15); margin: .3rem 0; padding-left: .5rem; text-align:left;">
							                            	${iconStructure}${icon}<span>${subfase.txDescripcionSubFase} (${subfase.nuInscripcionVerificada}/${subfase.nuTotalMision})</span>
							                        	</a>
							                     	</div>`;
										});

										html +=  `</li>
							            	  	</ul>`;
							          }
									html += '</li>';
									});

		html += `</ul>
	          </li>
	        </ul> 
	      </div>`;
	contenedor.innerHTML = html;

	contenedor.querySelectorAll('a').forEach(link => {
		link.addEventListener('click', function() {
			const idSubFase = this.getAttribute('data-id-sub-fase');
			GLOBAL.subFaseSeleccionada = {
				idSubFase: idSubFase
			};
			//const nombreSubFase = this.getAttribute('data-nombre-sub-fase');
						
			if (idSubFase) {
				if (
					(GLOBAL.grupoSeleccionado === null || Object.keys(GLOBAL.grupoSeleccionado).length === 0) &&
					(GLOBAL.instructorSeleccionado === null || Object.keys(GLOBAL.instructorSeleccionado).length === 0)
				) {
					notification('info', "Debe seleccionar al menos un Grupo o Instructor para su inscripción")
					return;
				}
				const subFase = lstValidacionInscripcion
								 .flatMap(fase => fase.lstSubFases)
		 						 .find(subfase => subfase.idSubFase === parseInt(idSubFase));
				 
				if (Array.isArray(subFase.lstMisiones) && subFase.lstMisiones.length === 0) {
					notification('info', "La sub fase no tiene misiones registradas")
					return;			
				}
				
				const lstMisiones = subFase.lstMisiones; 
				
				let mensaje = "";
				if (Object.keys(GLOBAL.grupoSeleccionado).length !== 0) {
					mensaje = `Ud. matriculará al Grupo: <br><strong>${GLOBAL.grupoSeleccionado.noNombre}</strong>`;
				} else if (Object.keys(GLOBAL.instructorSeleccionado).length !== 0) {
					mensaje = `Ud. matriculará al Instructor: <br><strong>${GLOBAL.instructorSeleccionado.datosCompletos}</strong>`;
				}
				const modalElement = document.getElementById('mdConfirmacion');
				renderizarMisiones(lstMisiones)
				document.getElementById('msgConfirmacion').innerHTML = `${mensaje} <br>En la misión que seleccione. <br>`;/*<strong> ${nombreSubFase} </strong>;*/
				const modal = new bootstrap.Modal(modalElement);
				modal.show();
			}
		});
	});
}

function renderizarMisiones(lstMisiones){
    const contenedor = document.getElementById('content-misiones'); 
    let html = `<div class="d-flex flex-wrap my-4 py-2 gap-2 justify-content-center">`;
    lstMisiones.forEach((mision) => {
        const { idMision, coCodigo, flInscripcion } = mision;
        const tooltipTitle = flInscripcion ? `${coCodigo} Inscrito` : "Diponible";
        const className = flInscripcion ? 'btn btn-primary disabled' : 'btn btn-soft-primary';


        html += `
            <div data-bs-toggle="tooltip" data-bs-trigger="hover" data-bs-placement="top" title="${tooltipTitle}">
                 <button class="${className} avatar-sm rounded-circle p-0 d-flex justify-content-center align-items-center" id="id-mision-${idMision}">
                    ${coCodigo}
                </button>
            </div>
        `;
    });

    html += `</div>`;
    contenedor.innerHTML = html;
    
	const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
	tooltipTriggerList.forEach(function(tooltipTriggerEl) {
		new bootstrap.Tooltip(tooltipTriggerEl);
	});
	
	const buttons = contenedor.querySelectorAll('button');
    buttons.forEach(button => {
        button.addEventListener('click', async function() {
            const buttonId = this.id;
            const idMision = buttonId.replace('id-mision-', ''); 
            matricularMision(idMision); 
        });
    });
	
}

async function matricularMision(idMision){
	const requestMatricula = {
		idMision: idMision,
		idInstructor: GLOBAL.instructorSeleccionado.idMiembro,
		idGrupo: GLOBAL.grupoSeleccionado.idGrupo
	}
	try {
		const response = await fetchMatricularPrograma(requestMatricula);
		const { type, message } = response;
		alert(type, message);
		if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
			const modal = bootstrap.Modal.getInstance(document.getElementById('mdConfirmacion'));
			modal.hide();
			renderizarMapa();
		}
	} catch (error) {
		console.error('Error al insertar la matricula:', error);
	}
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

const tabs = document.querySelectorAll('.nav-link');
tabs.forEach(tab => {
	tab.addEventListener('click', function(event) {
		event.preventDefault();
		const tabId = this.getAttribute('href');
					
		if (tabId === '#grupal-tab') {
			GLOBAL.instructorSeleccionado = {};
			document.getElementById('btn-instructor-seleccionado').innerText = 'Selecciona Instructor';
			if(Object.keys(GLOBAL.programaSeleccionado).length > 0){
				renderizarMapa();
			}	
		} else if (tabId === '#individual-tab') {
			if(Object.keys(GLOBAL.programaSeleccionado).length > 0){
				renderizarMapa();
			}	
			cboGrupoVal.setChoiceByValue("");

			GLOBAL.grupoSeleccionado = {};

			const contenedor = document.getElementById('content-item-alumno')
			contenedor.innerHTML = '';

			document.getElementById('alert-contador-alumnos').style.display = 'block';
			document.getElementById('content-card-alumnos').style.display = 'none';
			document.getElementById('contador-alumnos').innerText = `0 Alumnos`;

			document.getElementById('btn-grupo-seleccionado').innerText = 'Selecciona Grupo';

			document.getElementById('contador-efectivos-alumnos').innerText = 0;
			document.getElementById('contador-descuentos-alumnos').innerText = 0;
			document.getElementById('contador-disponibles-alumnos').innerText = 0;
		}
	});
});

document.getElementById("mdConfirmacion").addEventListener("hidden.bs.modal", function() {
	GLOBAL.subFaseSeleccionada = {};
});

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

async function fetchListarGruposPorPeriodo(nuPeriodo) {
	try {
		const response = await fetch(`/grupos/listar?nuPeriodo=${nuPeriodo}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de grupos: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de grupos:', error);
		throw error;
	}
}

async function fetchListarInstructoresPorPeriodo(nuPeriodo) {
	try {
		const response = await fetch(`/miembros/listarInstructores?nuPeriodo=${nuPeriodo}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de instructores: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de instructores:', error);
		throw error;
	}
}
 
async function fetchVerificarInscripcionSubFase(idSubFase, lstIdMiembros) {
	try {
		const response = await fetch(`/calificaciones/verificarInscripcionSubFase?idSubFase=${idSubFase}&lstIdMiembros=${lstIdMiembros}`);
		if (!response.ok) {
			throw new Error(`No se ha podido verificar la inscripcion sub fase: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la inscripcion sub fase:', error);
		throw error;
	}
}
async function fetchMatricularPrograma(request) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(request)
		}
		const response = await fetch(`/calificaciones/guardar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido matricular : ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al insertar la matricula:', error);
		throw error;
	}
}
