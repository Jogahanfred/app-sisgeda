renderizarLista();
let currentPage = 1;
const itemsPerPage = 20;
const rol = "INSTRUCTOR";

const prevButton = document.getElementById('page-prev');
const nextButton = document.getElementById('page-next');

async function renderizarLista(nuPeriodo) {
	document.getElementById('loader').style.display = 'block';
	document.getElementById('content-data').style.display = 'none';
	document.getElementById('noresult').style.display = 'none';

	if (nuPeriodo == null) {
		nuPeriodo = document.getElementById('cboPeriodo-field').value;
	}
	try {
		const lstGrupos = await fetchListarGrupos(nuPeriodo);
		console.log(lstGrupos)
		allgroupList = lstGrupos;
		if (lstGrupos.length > 0) {
			renderizarCardGrupos(lstGrupos, currentPage);
			paginationEvents();
			document.getElementById('content-data').style.display = 'block';
		} else {
			document.getElementById('noresult').style.display = 'block';
		}
		document.getElementById('loader').style.display = 'none';
	} catch (error) {
		console.error('Error al mostrar los grupos:', error);
	}
}

document.getElementById('cboPeriodo-field').addEventListener('change', function() {
	let cboPeriodo = document.getElementById('cboPeriodo-field').value;
	renderizarLista(cboPeriodo);
});

function capitalizarTexto(cadena) {
	const palabras = cadena.split(' ');

	const nombreCapitalizado = palabras.map(palabra => {
		return palabra.charAt(0).toUpperCase() + palabra.slice(1).toLowerCase();
	}).join(' ');

	return nombreCapitalizado;
}

function renderizarCardGrupos(datas, page) {

	let pages = Math.ceil(datas.length / itemsPerPage)
	if (page < 1) page = 1
	if (page > pages) page = pages
	document.querySelector("#candidate-list").innerHTML = '';
	for (var i = (page - 1) * itemsPerPage; i < (page * itemsPerPage) && i < datas.length; i++) {
		if (datas[i]) {
			let grupoAlumnos = '';
			if (datas[i].lstAlumnos && datas[i].lstAlumnos.length > 0) {
				const elementosMostrados = 4;
                let alumnosVisibles = datas[i].lstAlumnos.slice(0, elementosMostrados);

				grupoAlumnos = alumnosVisibles.map(alumno => {
					
					const defaultImage = alumno.personal.sexo === 'MASCULINO' ? 'male-user.jpg' : 'female-user.jpg';
					const image_src = (alumno.personal.fotografia != null && alumno.personal.fotografia != 'null') ? '/uploads/personal/' + alumno.personal.fotografia : `/assets/images/users/${defaultImage}`;

					return `<a href="javascript: void(0);" class="avatar-group-item shadow" data-bs-toggle="tooltip" data-bs-trigger="hover" data-bs-placement="top" title="${alumno.personal.datosCompletos}">
		                        <img src="${image_src}" alt="" class="rounded-circle avatar-xxs">
		                    </a>`
				}).join('');
				
				 if (datas[i].lstAlumnos.length > elementosMostrados) {
                    const cantidadAlumnos = datas[i].lstAlumnos.length - elementosMostrados;
                    const tooltipAlumnos = cantidadAlumnos === 1 ? `${cantidadAlumnos} Alumno` : `${cantidadAlumnos} Alumnos`;
                    grupoAlumnos += `
                        <a href="javascript: void(0);" class="avatar-group-item shadow" data-bs-toggle="tooltip" data-bs-trigger="hover" data-bs-placement="top" title="${tooltipAlumnos}">
                            <div class="avatar-xxs">
                                <div class="avatar-title rounded-circle">${cantidadAlumnos}+</div>
                            </div>
                        </a>
                    `;
                }
			}

			let isUserProfile = `<div class="avatar-title border bg-light text-primary rounded text-uppercase fs-24">${datas[i].coCodigo.slice(0, 1)}${datas[i].coCodigo.slice(-1)}</div>`;
			document.querySelector("#candidate-list").innerHTML += `<div class="col-xxl-3 col-md-6">
			    <div class="card">
			        <div class="card-body">
			            <div class="d-flex align-items-center">
			                <div class="flex-shrink-0">
			                    <div class="avatar-lg rounded">${isUserProfile}</div>
			                </div>
			                <div class="flex-grow-1 ms-3">
			                    <a href="/pages-profile">
			                        <h5 class="fs-16 mb-1">${datas[i].coCodigo}</h5>
			                    </a>
			                    <p class="text-muted mb-2">${datas[i].noNombre}</p>
			                    <div class="d-flex flex-wrap gap-2 align-items-center">
			                        <div class="text-muted"><strong>Estado: </strong></div>
			                    	<div class="badge text-bg-success">
			                    		${capitalizarTexto(datas[i].noSituacion)}
			                    	</div>
			                    </div>
			                    <div class="d-flex align-items-center mt-2">
									<div class="flex-grow-1">
										<div class="avatar-group">
                                            ${grupoAlumnos}
                                        </div>
									</div>
									<div class="flex-shrink-0">
										<button type="button" 
												class="btn btn-soft-info waves-effect waves-light material-shadow-none btn-sm">
										   <i class="ri-eye-line align-bottom me-1"></i> 
											Ver
										</button>
									</div>
								</div>
			                </div>
			            </div>
			        </div>
			    </div>
			</div>`;
		}
	}

	const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
	tooltipTriggerList.forEach(function(tooltipTriggerEl) {
		new bootstrap.Tooltip(tooltipTriggerEl);
	});

	paginaSeleccionada();
	currentPage == 1 ? prevButton.parentNode.classList.add('disabled') : prevButton.parentNode.classList.remove('disabled');
	currentPage == pages ? nextButton.parentNode.classList.add('disabled') : nextButton.parentNode.classList.remove('disabled');
}

function renderizarBagdePeriodo(periodo) {
	const currentYear = new Date().getFullYear();

	switch (periodo) {
		case currentYear:
			return '<span class="badge badge-soft-primary">Periodo: ' + periodo + '</span>';
		case currentYear + 1:
			return '<span class="badge badge-soft-success">Periodo: ' + periodo + '</span>';
		default:
			return '<span class="badge badge-soft-danger">Periodo: ' + periodo + '</span>';
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
		return Math.ceil(allgroupList.length / itemsPerPage);
	};

	function clickPage() {
		document.addEventListener('click', function(e) {
			if (e.target.nodeName == "A" && e.target.classList.contains("clickPageNumber")) {
				currentPage = e.target.textContent;
				renderizarCardGrupos(allgroupList, currentPage);
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
			renderizarCardGrupos(allgroupList, currentPage);
		}
	});

	nextButton.addEventListener('click', function() {
		if (currentPage < numPages()) {
			currentPage++;
			renderizarCardGrupos(allgroupList, currentPage);
		}
	});

	pageNumbers();
	clickPage();
	paginaSeleccionada();
}

document.getElementById("addBtn").addEventListener('click', async function(event) {
	document.getElementById("mdTitulo").innerHTML = "Matricular Instructor";
	document.getElementById("mdInstructor").querySelector(".modal-footer").style.display = "block";
	document.getElementById("add-btn").innerHTML = "Matricular";

	var modal = new bootstrap.Modal(document.getElementById('mdInstructor'));
	modal.show();

});

let nuPeriodoField = document.getElementById("nuPeriodo-field"),
	coNsaField = document.getElementById("coNsa-field");

var coNsaVal = new Choices(coNsaField)
var nuPeriodoVal = new Choices(nuPeriodoField)

let forms = document.querySelectorAll('.tablelist-form')
Array.prototype.slice.call(forms).forEach(function(form) {
	form.addEventListener('submit', async function(event) {
		if (nuPeriodoField.value == "" || nuPeriodoField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Periodo")
			return;
		}
		if (coNsaField.value == "" || coNsaField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Personal")
			return;
		}
		if (!form.checkValidity()) {
			event.preventDefault();
			event.stopPropagation();
		} else {
			event.preventDefault();

			let instructor = {
				nuPeriodo: nuPeriodoField.value,
				noRol: rol,
				coNsa: coNsaField.value
			};
			console.log(instructor)

			try {
				const response = await fetchMatricularInstructor(instructor);
				const { type, message } = response;
				alert(type, message);
				if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
					const cboPeriodo = document.getElementById('cboPeriodo-field').value;
					document.getElementById("close-modal").click();
					limpiarCampos();
					renderizarLista(cboPeriodo);
				}
			} catch (error) {
				console.error('Error al instructor la instructor:', error);
			}
		}
	}, false)
})

function limpiarCampos() {
	const currentYear = new Date().getFullYear();
	if (coNsaVal) {
		coNsaVal.setChoiceByValue("");
	}

	if (nuPeriodoVal) {
		nuPeriodoVal.setChoiceByValue(currentYear.toString());
	}
}


const searchElementList = document.getElementById("buscarInstructor");
searchElementList.addEventListener("keyup", function() {
	const inputVal = searchElementList.value.toLowerCase();

	function filterItems(arr, query) {
		return arr.filter(function(el) {
			return el.personal.datosCompletos.toLowerCase().indexOf(query.toLowerCase()) !== -1;
		})
	}

	let filterData = filterItems(allgroupList, inputVal);

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

	renderizarCardGrupos(filterData, currentPage);
});

document.getElementById("mdInstructor").addEventListener("hidden.bs.modal", function() {
	limpiarCampos();
});

async function fetchMatricularInstructor(instructor) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(instructor)
		}
		const response = await fetch(`/miembros/guardar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido insertar el instructor: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al insertar el instructor:', error);
		throw error;
	}
}

async function fetchListarGrupos(nuPeriodo) {
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
