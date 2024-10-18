const prevButton = document.getElementById('page-prev');
const nextButton = document.getElementById('page-next');

let currentPageMain = 1;
const itemsPerPageMain = 20;

renderizarLista();

async function renderizarLista() {
	document.getElementById('loader').style.display = 'block';
	document.getElementById('content-data').style.display = 'none';
	document.getElementById('noresult').style.display = 'none';
		 
	try { 
		const lstMisiones = await fetchListarMisionesACalificarPorPeriodo();
		allcandidateList = lstMisiones;
		if (lstMisiones.length > 0) {
			renderizarCardMisiones(lstMisiones, currentPageMain);
			paginationEvents();
			document.getElementById('content-data').style.display = 'block';
		} else {
			document.getElementById('noresult').style.display = 'block';
		}
		document.getElementById('loader').style.display = 'none';
	} catch (error) {
		console.error('Error al mostrar la lista:', error);
	}
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


async function renderizarCardMisiones(datas, page) {
	let pages = Math.ceil(datas.length / itemsPerPageMain);
	if (page < 1) page = 1;
	if (page > pages) page = pages;
	const candidateList = document.querySelector("#candidate-list");
	candidateList.innerHTML = '';

	for (let i = (page - 1) * itemsPerPageMain; i < (page * itemsPerPageMain) && i < datas.length; i++) {
		if (datas[i]) {
			let image_src = `/assets/images/users/user-dummy-img.jpg`;
			let txtNombreCalificador = "Sin Instructor asignado";
			if (datas[i].calificador != null){
				const defaultImage = datas[i].calificador.personal.sexo === 'MASCULINO' ? 'male-user.jpg' : 'female-user.jpg';
				image_src = (datas[i].calificador.personal.fotografia != null && datas[i].calificador.personal.fotografia != 'null') ? '/uploads/personal/' + datas[i].calificador.personal.fotografia : `/assets/images/users/${defaultImage}`;
				txtNombreCalificador = datas[i].calificador.personal.datosCompletos;
			} 
			
			const idCalificacion = datas[i].idCalificacion; 
			const card = document.createElement('div');
			card.dataset.idCalificacion = idCalificacion; 
			card.className = "col-xl-3 col-md-6";
			card.innerHTML = `
                <div class="card card-animate" style="cursor: pointer;">
                    <div class="card-body">
                        <div class="d-flex align-items-center">
                        	<div class="flex-grow-1 overflow-hidden">
								<p class="text-uppercase fw-medium text-muted text-truncate mb-0"> ${datas[i].txNombreTipoMision}</p>
							</div>
							
							<div class="flex-shrink-0">
								<h5 class="text-success fs-14 mb-0">
								<i class="ri-arrow-right-up-line fs-13 align-middle"></i> ${datas[i].flChequeo === 1 ? 'Vuelo chequeo' : 'Vuelo rutinario'  } 
							    </h5>
							</div>
						</div>
						
						<div class="d-flex align-items-end justify-content-between mt-4">
							<div>
								<h4 class="fs-22 fw-semibold ff-secondary mb-4"><span>${datas[i].coCodigo}</span></h4>
								<a href="#"  class="text-decoration-underline">${datas[i].calificador != null ? 'Instructor Asignado' : 'Asignar Instructor'}</a>
							</div>
							<div class="avatar-sm flex-shrink-0">
							  <div data-bs-toggle="tooltip" data-bs-trigger="hover" data-bs-placement="top" title="${txtNombreCalificador}" class="avatar-title bg-light rounded">
	                            <img src="${image_src}" alt="" class="img-thumbnail" width="200">
	                          </div>
							</div>
						</div>
							 
                          
                        </div>
                    </div>
                </div>`;
							
			card.addEventListener("click", async function() {
    			const idCalificacion = this.dataset.idCalificacion; 
    											
				const modalElement = document.getElementById('mdInstructor');
				const modal = new bootstrap.Modal(modalElement);
				modal.show();
				
				try {
					const nuPeriodo = 2024;
					const lstInstructores = await fetchListarInstructores(nuPeriodo);
					renderizarCardInstructores(lstInstructores, idCalificacion);
				} catch (error) {
					console.error('Error al mostrar los instructores:', error);
				} 
				
			});
			
			const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
			tooltipTriggerList.forEach(function(tooltipTriggerEl) {
				new bootstrap.Tooltip(tooltipTriggerEl);
			});
	
			candidateList.appendChild(card);
		}
	}
	paginaSeleccionada();
	currentPageMain == 1 ? prevButton.parentNode.classList.add('disabled') : prevButton.parentNode.classList.remove('disabled');
	currentPageMain == pages ? nextButton.parentNode.classList.add('disabled') : nextButton.parentNode.classList.remove('disabled');
}

function renderizarCardInstructores(lstInstructores, idCalificacion) {
	console.log("LISTA: ",lstInstructores)
	const contenedor = document.getElementById('content-item-instructor');
    contenedor.innerHTML = '';

    const listGroup = document.createElement('ul');
    listGroup.className = 'list-group';

    lstInstructores.forEach(miembro => {
        const colorClass = miembro.personal.sexo === 'MASCULINO' ? 'bg-danger-subtle text-danger' : 'bg-success-subtle text-success';

        const isUserProfile = (miembro.personal.fotografia != "null" && miembro.personal.fotografia != "" && miembro.personal.fotografia != null)
            ? `<img src="/uploads/personal/${miembro.personal.fotografia}" alt="fotografía" class="avatar-xs rounded" />`
            : `<div class="avatar-title ${colorClass} rounded shadow">${miembro.personal.inicialesDatos.substring(0, 2)}</div>`;

        const listItem = document.createElement('li');
        listItem.className = 'list-group-item';
        
        listItem.innerHTML = `
            <div class="d-flex align-items-center">
            	<div class="avatar-xs flex-shrink-0 me-3">
                    ${isUserProfile}
                </div>
                <div class="flex-grow-1">
                    <div class="d-flex"> 
                        <div class="flex-shrink-0 ms-2">
                            <h6 class="fs-14 mb-0">${miembro.personal.grado} FAP ${capitalizarIniciales(miembro.personal.datos)}</h6>
                        </div>
                    </div>
                </div>
                <div class="flex-shrink-0">
                    <button type="button" class="btn btn-light btn-sm" data-id-miembro="${miembro.idMiembro}">Seleccionar</button>
                </div>
            </div>
        `;

        listGroup.appendChild(listItem);
    });

    const scrollContainer = document.createElement('div');
    scrollContainer.setAttribute('data-simplebar', '');
    scrollContainer.style.maxHeight = '215px';
    scrollContainer.appendChild(listGroup);

    contenedor.appendChild(scrollContainer);
 
	const buttons = contenedor.querySelectorAll('button');
	buttons.forEach(button => {
       button.addEventListener('click', async function(event) {
        const idCalificador = event.target.getAttribute('data-id-miembro');
 
       		try { 
				const response = await fetchAsignarInstructor(idCalificacion,idCalificador); 
				const { type, message } = response;
				alert(type, message);
				if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
					document.getElementById("close-modal").click();
					renderizarLista();
				}
			} catch (error) {
				console.error('Error al asignar un instructor:', error);
			} 
       });
    });
}

function renderizarBagdePeriodo(periodo) {
	const currentYear = new Date().getFullYear();

	switch (periodo) {
		case currentYear:
			return `<span class="badge badge-soft-primary">Periodo: ${periodo}</span>`;
		case currentYear + 1:
			return `<span class="badge badge-soft-success">Periodo: ${periodo}</span>`;
		default:
			return `<span class="badge badge-soft-danger">Periodo: ${periodo}</span>`;
	}
}


function paginaSeleccionada() {
	var pagenumLink = document.getElementById('page-num').getElementsByClassName('clickPageNumber');
	for (var i = 0; i < pagenumLink.length; i++) {
		if (i == currentPageMain - 1) {
			pagenumLink[i].parentNode.classList.add("active");
		} else {
			pagenumLink[i].parentNode.classList.remove("active");
		}
	}
};

// paginationEvents
function paginationEvents() {
	var numPages = function numPages() {
		return Math.ceil(allcandidateList.length / itemsPerPageMain);
	};

	function clickPage() {
		document.addEventListener('click', function(e) {
			if (e.target.nodeName == "A" && e.target.classList.contains("clickPageNumber")) {
				currentPageMain = e.target.textContent;
				renderizarCardMisiones(allcandidateList, currentPageMain);
			}
		});
	};

	function pageNumbers() {
		var pageNumber = document.getElementById('page-num');
		pageNumber.innerHTML = "";
		// for each page
		for (var i = 1; i < numPages() + 1; i++) {
			pageNumber.innerHTML += "<div class='page-item'><a class='page-link clickPageNumber' href='javascript:void(0);'>" + i + "</a></div>";
		}
	}

	prevButton.addEventListener('click', function() {
		if (currentPageMain > 1) {
			currentPageMain--;
			renderizarCardMisiones(allcandidateList, currentPageMain);
		}
	});

	nextButton.addEventListener('click', function() {
		if (currentPageMain < numPages()) {
			currentPageMain++;
			renderizarCardMisiones(allcandidateList, currentPageMain);
		}
	});

	pageNumbers();
	clickPage();
	paginaSeleccionada();
}

function limpiarCampos() {
	const currentYear = new Date().getFullYear();
	if (coNsaVal) {
		coNsaVal.setChoiceByValue("");
	}

	if (nuPeriodoVal) {
		nuPeriodoVal.setChoiceByValue(currentYear.toString());
	}
}

document.getElementById('btnRetornar').addEventListener("click", function() {
	window.history.back();
});


const searchElementList = document.getElementById("buscarMision");
searchElementList.addEventListener("keyup", function() {
	const inputVal = searchElementList.value.toLowerCase();

	function filterItems(arr, query) {
		return arr.filter(function(el) {
			return el.personal.datosCompletos.toLowerCase().indexOf(query.toLowerCase()) !== -1;
		})
	}

	let filterData = filterItems(allcandidateList, inputVal);

	if (filterData.length == 0) {
		document.getElementById("pagination-element").style.display = "none";
		document.getElementById("noresult").style.display = "block";
	} else {
		document.getElementById("noresult").style.display = "none";
		document.getElementById("pagination-element").style.display = "flex";
	}

	let pageNumber = document.getElementById('page-num');
	pageNumber.innerHTML = "";
	let dataPageNum = Math.ceil(filterData.length / itemsPerPageMain)

	for (var i = 1; i < dataPageNum + 1; i++) {
		pageNumber.innerHTML += "<div class='page-item'><a class='page-link clickPageNumber' href='javascript:void(0);'>" + i + "</a></div>";
	}

	renderizarCardMisiones(filterData, currentPageMain);
});

async function fetchVerMisionACalificar(idMision) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				idMision: idMision
			})
		}
		const response = await fetch(`/redireccion/redirectCtrlMisionACalificar`, configHttp);
		
		if (!response.ok) {
			const message = `"No se ha podido obtener la url : ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.text();
		return mensaje;
	} catch (error) {
		console.error('Error al obtener la url:', error);
		throw error;
	}
}

async function fetchAsignarInstructor(idCalificacion, idCalificador) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				idCalificacion: idCalificacion,
				idCalificador: idCalificador
			})
		}
		const response = await fetch(`/calificaciones/asignarInstructor`, configHttp);
		
		if (!response.ok) {
			const message = `"No se ha podido obtener la url : ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al obtener la url:', error);
		throw error;
	}
}


async function fetchListarInstructores(nuPeriodo) {
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

async function fetchListarMisionesACalificarPorPeriodo(){
	try {
		const response = await fetch(`/misiones/listarMisionesACalificarPorPeriodo`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de misiones: ${response.status}`);
		} 
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de misiones:', error);
		throw error;
	}
}
