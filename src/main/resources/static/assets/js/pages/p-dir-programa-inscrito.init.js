const prevButton = document.getElementById('page-prev');
const nextButton = document.getElementById('page-next');

let currentPage = 1;
const itemsPerPage = 20;

renderizarLista();

async function renderizarLista() {
	document.getElementById('loader').style.display = 'block';
	document.getElementById('content-data').style.display = 'none';
	document.getElementById('noresult').style.display = 'none';

	try { 
		const lstProgramas = await fetchListarProgramasACalificarPorPeriodo();
		allcandidateList = lstProgramas;
		if (lstProgramas.length > 0) {
			renderizarCardProgramas(lstProgramas, currentPage);
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


async function renderizarCardProgramas(datas, page) {
	let pages = Math.ceil(datas.length / itemsPerPage);
	if (page < 1) page = 1;
	if (page > pages) page = pages;
	const candidateList = document.querySelector("#candidate-list");
	candidateList.innerHTML = '';

	for (let i = (page - 1) * itemsPerPage; i < (page * itemsPerPage) && i < datas.length; i++) {
		if (datas[i]) {
			const colors = ['bg-primary', 'bg-danger', 'bg-warning', 'bg-info'];
			const colorText = colors[Math.floor(Math.random() * colors.length)];
		
			const card = document.createElement('div');
			card.className = "col-xl-3 col-md-6";
			card.innerHTML = `
                <div class="card card-animate" style="cursor: pointer;">
                    <div class="card-body">
                        <div class="d-flex align-items-center">
                        	<div class="flex-grow-1 overflow-hidden">
								<p class="text-uppercase fw-medium text-muted text-truncate mb-0"> Escuadron: ${datas[i].txDescripcionEscuadron}</p>
							</div>
							
							<div class="flex-shrink-0">
								<h5 class="text-success fs-14 mb-0">
								<i class="ri-arrow-right-up-line fs-13 align-middle"></i> Periodo: ${datas[i].nuPeriodo}
							    </h5>
							</div>
						</div>
						
						<div class="d-flex align-items-end justify-content-between mt-4">
							<div>
								<h4 class="fs-16 fw-semibold ff-secondary mb-4"><span>${datas[i].noNombre}</span></h4>
								<a href="" class="text-decoration-underline">Ir al programa</a>
							</div>
							<div class="avatar-sm flex-shrink-0">
								<span class="avatar-title ${colorText} rounded fs-3">
									${datas[i].noNombre.slice(-2).toUpperCase()}
								</span>
							</div>
						</div>
							 
                          
                        </div>
                    </div>
                </div>`;

			card.addEventListener("click", async function() {
				const idPrograma = datas[i].idPrograma;
				try {
					const response = await fetchVerFasesInscritos(idPrograma);
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
				renderizarCardProgramas(allcandidateList, currentPage);
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
			renderizarCardProgramas(allcandidateList, currentPage);
		}
	});

	nextButton.addEventListener('click', function() {
		if (currentPage < numPages()) {
			currentPage++;
			renderizarCardProgramas(allcandidateList, currentPage);
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


const searchElementList = document.getElementById("buscarPrograma");
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

	renderizarCardProgramas(filterData, currentPage);
});

async function fetchVerFasesInscritos(idPrograma) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				idPrograma: idPrograma
			})
		}
		const response = await fetch(`/redireccion/redirectCtrlFasesInscritas`, configHttp);

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

async function fetchListarProgramasACalificarPorPeriodo(){
	try {
		const response = await fetch(`/programas/listarProgramasACalificarPorPeriodo`);
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
