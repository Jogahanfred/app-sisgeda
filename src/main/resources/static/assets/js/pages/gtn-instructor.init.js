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
		const lstInstructores = await fetchListarInstructores(nuPeriodo);
		allcandidateList = lstInstructores;
		if (lstInstructores.length > 0) {
			renderizarCardInstructores(lstInstructores, currentPage);
			paginationEvents();
			document.getElementById('content-data').style.display = 'block';
		} else {
			document.getElementById('noresult').style.display = 'block';
		}
		document.getElementById('loader').style.display = 'none';
	} catch (error) {
		console.error('Error al mostrar los instructores:', error);
	}
}

document.getElementById('cboPeriodo-field').addEventListener('change', function() {
	let cboPeriodo = document.getElementById('cboPeriodo-field').value;
	renderizarLista(cboPeriodo);
});

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

function renderizarCardInstructores(datas, page) {

	let pages = Math.ceil(datas.length / itemsPerPage)
	if (page < 1) page = 1
	if (page > pages) page = pages
	document.querySelector("#candidate-list").innerHTML = '';
	for (var i = (page - 1) * itemsPerPage; i < (page * itemsPerPage) && i < datas.length; i++) {
		if (datas[i]) {
			const colorText = datas[i].personal.sexo === 'MASCULINO' ? 'text-primary' : 'text-danger';

			var isUserProfile = (datas[i].personal.fotografia != "null" && datas[i].personal.fotografia != "" && datas[i].personal.fotografia != null)
				? '<img src="/' + 'uploads/personal/' + datas[i].personal.fotografia + '" alt="fotografía" class="member-img img-fluid d-block rounded" />'
				: `<div class="avatar-title border bg-light ${colorText} rounded text-uppercase fs-24">${datas[i].personal.inicialesDatos.substring(0, 2)}</div>`;
			document.querySelector("#candidate-list").innerHTML += '<div class="col-xxl-3 col-md-6">\
			    <div class="card">\
			        <div class="card-body">\
			            <div class="d-flex align-items-center">\
			                <div class="flex-shrink-0">\
			                    <div class="avatar-lg rounded">'+ isUserProfile + '</div>\
			                </div>\
			                <div class="flex-grow-1 ms-3">\
			                    <a href="/pages-profile">\
			                        <h5 class="fs-16 mb-1">'+ capitalizarIniciales(datas[i].personal.datos) + '</h5>\
			                    </a>\
			                    <p class="text-muted mb-2">'+ datas[i].personal.descriGrado + '</p>\
			                    <div class="d-flex flex-wrap gap-2 align-items-center">\
			                    <div class="badge text-bg-success"><i class="mdi mdi-folder-account-outline me-1"></i>NSA: '+ datas[i].coNsa + '</div>\
			                        <div class="text-muted"><strong>DNI: </strong>'+ datas[i].personal.dni + '</div>\
			                    </div>\
			                    <div class="d-flex gap-4 mt-2 text-muted">\
			                        <div>\
			                            <i class="ri-user-heart-line text-danger me-1 align-bottom"></i><strong>Grupo: </strong>'+ datas[i].personal.tipoSangre + '</div>\
			                        <div>\
			                            '+ renderizarBagdePeriodo(datas[i].nuPeriodo) + '\
			                        </div>\
			                    </div>\
			                </div>\
			            </div>\
			        </div>\
			    </div>\
			</div>';
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
		return Math.ceil(allcandidateList.length / itemsPerPage);
	};

	function clickPage() {
		document.addEventListener('click', function(e) {
			if (e.target.nodeName == "A" && e.target.classList.contains("clickPageNumber")) {
				currentPage = e.target.textContent;
				renderizarCardInstructores(allcandidateList, currentPage);
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
			renderizarCardInstructores(allcandidateList, currentPage);
		}
	});

	nextButton.addEventListener('click', function() {
		if (currentPage < numPages()) {
			currentPage++;
			renderizarCardInstructores(allcandidateList, currentPage);
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

	renderizarCardInstructores(filterData, currentPage);
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
