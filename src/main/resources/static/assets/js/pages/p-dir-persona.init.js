const prevButton = document.getElementById('page-prev');
const nextButton = document.getElementById('page-next');

let currentPage = 1;
const itemsPerPage = 20;

const GLOBAL = {
	idEscuadron: 0
}

renderizarLista();
const cboBusquedaUnidad = document.getElementById("cboBusquedaUnidad");
const cboBusquedaUnidadVal = new Choices(cboBusquedaUnidad); 

const cboBusquedaEscuadron = document.getElementById("cboBusquedaEscuadron");
let cboBusquedaEscuadronVal = new Choices(cboBusquedaEscuadron); 

cboBusquedaUnidad.addEventListener("change", async function(){
	document.getElementById('content-data').style.display = 'none';
	document.getElementById('noresult').style.display = 'block';
	let idUnidad = cboBusquedaUnidad.value;
	try {
		const response = await fetchListarEscuadronPorUnidad(idUnidad);
		if (cboBusquedaEscuadronVal) cboBusquedaEscuadronVal.destroy();
		cboBusquedaEscuadronVal = new Choices(cboBusquedaEscuadron, {
			searchEnabled: false
		});
		const groupedChoices = llenarCboEscuadron(response);
		cboBusquedaEscuadronVal.setChoices(groupedChoices, 'value', 'label', false);
	} catch (error) {
		console.error('Error al listar escuadron:', error);
	}
});

function llenarCboEscuadron(lstEscuadron) {
	const choices = [];

	lstEscuadron.forEach(escuadron => { 
		choices.push({
			value: escuadron.idEscuadron.toString(),
			label: escuadron.coSigla,
			selected: false,
			disabled: false
		});
	});
	return choices;
}

cboBusquedaEscuadron.addEventListener("change", function (){
	const idEscuadron = cboBusquedaEscuadron.value;
	GLOBAL.idEscuadron = idEscuadron;
	renderizarLista();
	
});

async function renderizarLista() {
	document.getElementById('loader').style.display = 'block';
	document.getElementById('content-data').style.display = 'none';
	document.getElementById('noresult').style.display = 'none';
 
	try {
		const lstMiembros = await fetchListarMiembrosACalificarPorPeriodo(GLOBAL.idEscuadron);
		console.log("LSITA: ",lstMiembros); 
		allcandidateList = lstMiembros;
		if (lstMiembros.length > 0) {
			renderizarCardMiembros(lstMiembros, currentPage);
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


async function renderizarCardMiembros(datas, page) {
	let pages = Math.ceil(datas.length / itemsPerPage);
	if (page < 1) page = 1;
	if (page > pages) page = pages;
	const candidateList = document.querySelector("#candidate-list");
	candidateList.innerHTML = '';

	for (let i = (page - 1) * itemsPerPage; i < (page * itemsPerPage) && i < datas.length; i++) {
		if (datas[i]) {
			const colorText = datas[i].personal.sexo === 'MASCULINO' ? 'text-primary' : 'text-danger';

			const isUserProfile = (datas[i].personal.fotografia && datas[i].personal.fotografia !== "null")
				? `<img src="/uploads/personal/${datas[i].personal.fotografia}" alt="fotografía" class="member-img img-fluid d-block rounded" />`
				: `<div class="avatar-title border bg-light ${colorText} rounded text-uppercase fs-24">${datas[i].personal.inicialesDatos.substring(0, 2)}</div>`;

			const card = document.createElement('div');
			card.className = "col-xxl-3 col-md-6";
			card.innerHTML = `
                <div class="card card-animate" style="cursor: pointer;">
                    <div class="card-body">
                        <div class="d-flex align-items-center">
                            <div class="flex-shrink-0">
                                <div class="avatar-lg rounded">${isUserProfile}</div>
                            </div>
                            <div class="flex-grow-1 ms-3">
                                <a href="#">
                                    <h5 class="fs-16 mb-1">${capitalizarIniciales(datas[i].personal.datos)}</h5>
                                </a>
                                <p class="text-muted mb-2">${datas[i].personal.descriGrado}</p>
                                <div class="d-flex flex-wrap gap-2 align-items-center">
                                    <div class="badge text-bg-success"><i class="mdi mdi-folder-account-outline me-1"></i>NSA: ${datas[i].coNsa}</div>
                                    <div class="text-muted"><strong>DNI: </strong>${datas[i].personal.dni}</div>
                                </div>
                                <div class="d-flex gap-4 mt-2 text-muted">
                                    <div>
                                        <i class="ri-user-heart-line text-danger me-1 align-bottom"></i><strong>Grupo: </strong>${datas[i].personal.tipoSangre}</div>
                                    <div>
                                        ${renderizarBagdePeriodo(datas[i].nuPeriodo)}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>`;

			card.addEventListener("click", async function() {
				const idMiembro = datas[i].idMiembro;
				try {
					const response = await fetchVerProgramasInscritos(idMiembro);
					if (response) {
						window.location.href = response;
					}
				} catch (error) {
					console.error('Error al enlazar el uri:', error);
				}
			});

			candidateList.appendChild(card);
		}
	}
	paginaSeleccionada();
	currentPage == 1 ? prevButton.parentNode.classList.add('disabled') : prevButton.parentNode.classList.remove('disabled');
	currentPage == pages ? nextButton.parentNode.classList.add('disabled') : nextButton.parentNode.classList.remove('disabled');
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
		if (i == currentPage - 1) {
			pagenumLink[i].parentNode.classList.add("active");
		} else {
			pagenumLink[i].parentNode.classList.remove("active");
		}
	}
};

// paginationEvents
function paginationEvents() {
	var numPages = function numPages() {
		return Math.ceil(allcandidateList.length / itemsPerPage);
	};

	function clickPage() {
		document.addEventListener('click', function(e) {
			if (e.target.nodeName == "A" && e.target.classList.contains("clickPageNumber")) {
				currentPage = e.target.textContent;
				renderizarCardMiembros(allcandidateList, currentPage);
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
		if (currentPage > 1) {
			currentPage--;
			renderizarCardMiembros(allcandidateList, currentPage);
		}
	});

	nextButton.addEventListener('click', function() {
		if (currentPage < numPages()) {
			currentPage++;
			renderizarCardMiembros(allcandidateList, currentPage);
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


const searchElementList = document.getElementById("buscarAlumno");
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
	let dataPageNum = Math.ceil(filterData.length / itemsPerPage)

	for (var i = 1; i < dataPageNum + 1; i++) {
		pageNumber.innerHTML += "<div class='page-item'><a class='page-link clickPageNumber' href='javascript:void(0);'>" + i + "</a></div>";
	}

	renderizarCardMiembros(filterData, currentPage);
});

async function fetchVerProgramasInscritos(idMiembro) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				idMiembro: idMiembro
			})
		}
		const response = await fetch(`/redireccion/redirectCtrlProgramasInscritos`, configHttp);

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

async function fetchListarEscuadronPorUnidad(idUnidad) {
	try {
		const response = await fetch(`/escuadrones/listarPorIdUnidad?idUnidad=${idUnidad}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de operaciones: ${response.status}`);
		} 
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de escuadrones:', error);
		throw error;
	}
}

async function fetchListarMiembrosACalificarPorPeriodo(idEscuadron){
	try {
		const response = await fetch(`/miembros/listarMiembrosACalificarPorPeriodo?idEscuadron=${idEscuadron}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de miembros: ${response.status}`);
		} 
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de miembros:', error);
		throw error;
	}
}