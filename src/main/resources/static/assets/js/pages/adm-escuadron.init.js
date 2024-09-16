renderizarLista();
renderizarCheckboxCheck();

// CONFIGURACION DE LISTADO
const perPage = 8;
let formEdit = false;
const settingsDatatable = {
	valueNames: [
		"id_escuadron",
		"id_unidad",
		"co_sigla",
		"tx_descripcion",
		"fl_bloqueado",
		"fl_estado",
		"tx_ruta_logo",
		"tx_informacion",
		"tx_descripcion_unidad",
		{ attr: "src", name: "image_src" }
	],
	page: perPage,
	pagination: true,
	plugins: [
		ListPagination({
			left: 2,
			right: 2
		})
	]
};

let listadoEscuadrones = new List("listadoEscuadrones", settingsDatatable).on("updated", function(list) {
	list.matchingItems.length == 0 ?
		(document.getElementsByClassName("noresult")[0].style.display = "block") :
		(document.getElementsByClassName("noresult")[0].style.display = "none");
	var isFirst = list.i == 1;
	var isLast = list.i > list.matchingItems.length - list.page;
	// make the Prev and Nex buttons disabled on first and last pages accordingly
	(document.querySelector(".pagination-prev.disabled")) ? document.querySelector(".pagination-prev.disabled").classList.remove("disabled") : '';
	(document.querySelector(".pagination-next.disabled")) ? document.querySelector(".pagination-next.disabled").classList.remove("disabled") : '';
	if (isFirst) {
		document.querySelector(".pagination-prev").classList.add("disabled");
	}
	if (isLast) {
		document.querySelector(".pagination-next").classList.add("disabled");
	}
	if (list.matchingItems.length <= perPage) {
		document.querySelector(".pagination-wrap").style.display = "none";
	} else {
		document.querySelector(".pagination-wrap").style.display = "flex";
	}

	if (list.matchingItems.length > 0) {
		document.getElementsByClassName("noresult")[0].style.display = "none";
	} else {
		document.getElementsByClassName("noresult")[0].style.display = "block";
	}
	refreshCallbacks();
});

async function renderizarLista() {
	try {
		const lstEscuadrones = await fetchListarEscuadronesPorUnidadRector();
		lstEscuadrones.forEach(escuadron => {
			listadoEscuadrones.add({
				id_escuadron: escuadron.idEscuadron,
				id_unidad: escuadron.idUnidad,
				co_sigla: escuadron.coSigla,
				tx_descripcion: escuadron.txDescripcion,
				fl_bloqueado: convertirBadgeBloqueado(escuadron.flBloqueado),
				tx_ruta_logo: escuadron.txRutaLogo,
				tx_informacion: escuadron.txInformacion,
				image_src: (escuadron.txRutaLogo != null && escuadron.txRutaLogo != 'null') ? 'uploads/escuadron/' + escuadron.txRutaLogo : 'assets/images/users/multi-user.jpg',
				fl_estado: convertirBadgeEstado(escuadron.flEstado),
				tx_descripcion_unidad: escuadron.txDescripcionUnidad

			});
			refreshCallbacks();
			filtrarInformacion();
			document.getElementById("loader").style.display = 'none';
			document.getElementById("contentTable").style.display = 'block';
		})
		listadoEscuadrones.remove("id_escuadron", '<a href="javascript:void(0);" class="fw-medium link-primary">#VZ2101</a>');
	} catch (error) {
		console.error('Error al mostrar los escuadrones:', error);
	}
}

const form = document.getElementById('frmEscuadron');
	  addBtn = document.getElementById("add-btn"),
	  editBtn = document.getElementById("edit-btn"),
	  removeBtns = document.getElementsByClassName("remove-item-btn"),
	  editBtns = document.getElementsByClassName("edit-item-btn"),
	  viewBtns = document.getElementsByClassName("view-item-btn");

function convertirBadgeEstado(estado) {
	return (estado === 1) ? `<span class="badge badge-soft-success text-uppercase">Activo</span>` : `<span class="badge badge-soft-danger text-uppercase">Inactivo</span>`;
}

function convertirBadgeBloqueado(estado) {
	return (estado === 1) ? `<span class="badge badge-soft-secondary text-uppercase">Permitido</span>` : `<span class="badge badge-soft-danger text-uppercase">Bloqueado</span>`;
}

let idEscuadronField = document.getElementById("idEscuadron-field"),
	escuadronLogoImg = document.getElementById("escuadronlogo-img"),
	escuadronLogoInput = document.getElementById("escuadron-logo-input"),
	idUnidadField = document.getElementById("idUnidad-field"),

	coSiglaField = document.getElementById("coSigla-field"),
	txDescripcionField = document.getElementById("txDescripcion-field"),
	flBloqueadoField = document.getElementById("flBloqueado-field"),
	txRutaLogoField = document.getElementById("txRutaLogo-field"),
	txInformacionField = document.getElementById("txInformacion-field"),

	cboBusquedaUnidad = document.getElementById("cboBusquedaUnidad");

let forms = document.querySelectorAll('.tablelist-form')
Array.prototype.slice.call(forms).forEach(function(form) {
	form.addEventListener('submit', async function(event) {
		if (idUnidadField.value == "" || idUnidadField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Unidad")
			return;
		}   
		if (!form.checkValidity()) {
			event.preventDefault();
			event.stopPropagation();
			form.classList.add('was-validated');
		} else { 
			event.preventDefault(); 
			if (coSiglaField.value !== "" &&
				txDescripcionField.value !== "" &&
				idUnidadField.value !== "" && !formEdit) {
 
 				let formData = new FormData();
				let archivoInput = document.getElementById('escuadron-logo-input');

				let escuadron = {
					idUnidad: idUnidadField.value,
					coSigla: coSiglaField.value,
					txDescripcion: txDescripcionField.value,
					txRutaLogo: "",
					txInformacion: txInformacionField.value
				};
				formData.append('escuadron', JSON.stringify(escuadron));
				formData.append('archivo', archivoInput.files[0]);
				 
				try {
					const response = await fetchGuardarEscuadron(formData);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al insertar el escuadron:', error);
				}


			} else if (
				coSiglaField.value !== "" &&
				txDescripcionField.value !== "" &&
				idUnidadField.value !== "" && formEdit) {

				if (flBloqueadoField.value == "" || flBloqueadoField.value == null) {
					event.preventDefault();
					event.stopPropagation();
					notification('info', "Seleccione Permiso")
					return;
				}
				let formData = new FormData();
				let archivoInput = document.getElementById('escuadron-logo-input');

				let idEscuadron = parseInt(idEscuadronField.value);

				let escuadron = {
					idEscuadron: idEscuadron,
					idUnidad: idUnidadField.value,
					coSigla: coSiglaField.value,
					txDescripcion: txDescripcionField.value,
					flBloqueado: flBloqueadoField.value == "Permitido" ? 1 : 0,
					txRutaLogo: txRutaLogoField.value,
					txInformacion: txInformacionField.value,
				};

				formData.append('escuadron', JSON.stringify(escuadron));

				if (archivoInput.files.length > 0) {
					formData.append('archivo', archivoInput.files[0]);
				} else {
					formData.append('archivo', null);
				}

				try {
					const response = await fetchActualizarEscuadron(formData);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al actualizar el escuadron:', error);
				}

			}
		}
	}, false)
})

let bloqueoVal = new Choices(flBloqueadoField);
let unidadVal = new Choices(idUnidadField);

let cboBusquedaUnidadVal = new Choices(cboBusquedaUnidad, {
	noResultsText: 'No se encontraron coincidencias',
});

function filtrarInformacion() {
	var isIdUnidad = cboBusquedaUnidad.value;

	listadoEscuadrones.filter(function(data) {
		matchDataIdUnidad = new DOMParser().parseFromString(data.values().id_unidad, 'text/html');
		var idUnidad = matchDataIdUnidad.body.textContent.trim();

		var unidadFilter = false;

		if (isIdUnidad == "" || isIdUnidad == null) {
			unidadFilter = true;
		} else {
			unidadFilter = idUnidad == isIdUnidad;
		}

		return unidadFilter;
	});
	listadoEscuadrones.update();
}

function renderizarCheckboxCheck() {
	Array.from(document.getElementsByName("chk_child")).forEach(function(x) {
		x.addEventListener("change", function(e) {
			if (x.checked == true) {
				e.target.closest("tr").classList.add("table-active");
			} else {
				e.target.closest("tr").classList.remove("table-active");
			}

			var checkedCount = document.querySelectorAll('[name="chk_child"]:checked').length;
			if (e.target.closest("tr").classList.contains("table-active")) {
				(checkedCount > 0) ? document.getElementById("remove-actions").style.display = 'block' : document.getElementById("remove-actions").style.display = 'none';
			} else {
				(checkedCount > 0) ? document.getElementById("remove-actions").style.display = 'block' : document.getElementById("remove-actions").style.display = 'none';
			}
		});
	});
}

function refreshCallbacks() {
	var removeBtns = document.getElementsByClassName("remove-item-btn");
	var editBtns = document.getElementsByClassName("edit-item-btn");

	if (removeBtns) {
		Array.from(removeBtns).forEach(function(btn) {
			btn.addEventListener("click", handleDeleteButtonClick);
		});
	}

	if (editBtns) {
		Array.from(editBtns).forEach(function(btn) {
			btn.addEventListener("click", handleEditButtonClick);
		});
	}

	Array.from(viewBtns).forEach(function(btn) {
		btn.addEventListener("click", function(e) {
			e.target.closest("tr").children[1].innerText;
			itemId = e.target.closest("tr").children[1].innerText;

			var itemValues = listadoEscuadrones.get({
				id_escuadron: itemId,
			});

			Array.from(itemValues).forEach(function(x) {

				var selectedid = x._values.id_escuadron;
				if (selectedid == itemId) {
					renderizarCardEscuadronDetalle(x._values);
				}
			});

		});
	});

	renderizarEscuadronDetalle();
}

function renderizarEscuadronDetalle() {
	var firstItem = listadoEscuadrones.visibleItems[0];
	if (firstItem) {
		var data = firstItem._values;
		renderizarCardEscuadronDetalle(data);
	}
}


function renderizarCardEscuadronDetalle(data) {
	var bloque = `
            <div class="card-body text-center">
                <div class="position-relative d-inline-block">
                    <div class="avatar-xl">
                        <div class="avatar-title bg-light rounded">
                            <img src="${data.image_src}" alt="" class="avatar-xl">
                        </div>
                    </div>
                </div>
                <h5 class="mt-3 mb-1">${data.co_sigla}</h5>
                <p class="text-muted">${data.tx_descripcion}</p>

                <ul class="list-inline mb-0">
                    <li class="list-inline-item avatar-xs">
                        <a href="javascript:void(0);"
                            class="avatar-title bg-soft-success text-success fs-15 rounded">
                            <i class="ri-global-line"></i>
                        </a>
                    </li>
                    <li class="list-inline-item avatar-xs">
                        <a href="javascript:void(0);"
                            class="avatar-title bg-soft-danger text-danger fs-15 rounded">
                            <i class="ri-mail-line"></i>
                        </a>
                    </li>
                    <li class="list-inline-item avatar-xs">
                        <a href="javascript:void(0);"
                            class="avatar-title bg-soft-warning text-warning fs-15 rounded">
                            <i class="ri-question-answer-line"></i>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="card-body">
                <h6 class="text-muted text-uppercase fw-semibold mb-3">Información</h6>
                <p class="text-muted mb-4">${data.tx_informacion}</p>
                <div class="table-responsive table-card">
                    <table class="table table-borderless mb-0">
                         <tbody> 
                               <tr>
                               		<td class="fw-medium" scope="row">Unidad</td>
                                    <td>${data.tx_descripcion_unidad}</td>
                                </tr> 
                                <tr>
                                     <td class="fw-medium" scope="row">Permiso</td>
                                     <td>${data.fl_bloqueado}</td>
                                </tr>
                                <tr>
                                     <td class="fw-medium" scope="row">Estado</td>
                                     <td>${data.fl_estado}</td>
                                </tr>
                           </tbody>
                    </table>
                </div>
            </div>
        `;
	document.getElementById('company-view-detail').innerHTML = bloque;
}

function handleEditButtonClick(e) {
	e.target.closest("tr").children[1].innerText;
	itemId = e.target.closest("tr").children[1].innerText;
	var itemValues = listadoEscuadrones.get({
		id_escuadron: itemId,
	});
	Array.from(itemValues).forEach(function(x) {
		var selectedid = x._values.id_escuadron;
		if (selectedid == itemId) {
			formEdit = true;
			idEscuadronField.value = selectedid;
			escuadronLogoImg.src = (x._values.tx_ruta_logo !== null && x._values.tx_ruta_logo !== 'null') ? 'uploads/escuadron/' + x._values.tx_ruta_logo : 'assets/images/users/multi-user.jpg';
			txRutaLogoField.value = (x._values.tx_ruta_logo !== null && x._values.tx_ruta_logo !== 'null') ? x._values.tx_ruta_logo : '';
			coSiglaField.value = x._values.co_sigla;
			txDescripcionField.value = x._values.tx_descripcion;


			if (bloqueoVal) bloqueoVal.destroy();
			bloqueoVal = new Choices(flBloqueadoField, {
				searchEnabled: false
			});

			valBloqueo = new DOMParser().parseFromString(x._values.fl_bloqueado, "text/html");
			var bloqueoSelec = valBloqueo.body.firstElementChild.innerHTML;
			bloqueoVal.setChoiceByValue(bloqueoSelec);


			if (unidadVal) unidadVal.destroy();
			unidadVal = new Choices(idUnidadField, {
				searchEnabled: true
			});

			unidadVal.setChoiceByValue(x._values.id_unidad.toString());

			txInformacionField.value = x._values.tx_informacion;

		}
	});
}

function handleDeleteButtonClick(e) {
	e.target.closest("tr").children[1].innerText;
	itemId = e.target.closest("tr").children[1].innerText;
	var itemValues = listadoEscuadrones.get({
		id_escuadron: itemId,
	});

	Array.from(itemValues).forEach(function(x) {
		var isdeleteid = x._values.id_escuadron;
		if (isdeleteid == itemId) {
			document.getElementById("eliminar-registro").addEventListener("click", async function() {
				document.getElementById("deleteRecord-close").click();
				try {
					const response = await fetchEliminarPorId(isdeleteid);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al eliminar el escuadron:', error);
				}
			});
		}
	});
}

function limpiarCampos() {
	idEscuadronField.value = "";
	txRutaLogoField.value = "";
	escuadronLogoImg.src = "assets/images/users/multi-user.jpg";

	txDescripcionField.value = "";
	coSiglaField.value = "";
	escuadronLogoInput.value = "";

	txInformacionField.value = "";

	bloqueoVal.destroy();
	bloqueoVal = new Choices(flBloqueadoField, {
		searchEnabled: false
	});


	unidadVal.destroy();
	unidadVal = new Choices(idUnidadField, {
		searchEnabled: true
	});

	formEdit = false;
}

function eliminarMultiple() {
	ids_array = [];
	var checkboxes = document.getElementsByName('chk_child');
	checkboxes.forEach(function(checkbox) {
		if (checkbox.checked) {
			var trNode = checkbox.closest("tr");
			var id = trNode.querySelector("td.id_escuadron").textContent;
			ids_array.push(parseInt(id));
		}
	});

	if (typeof ids_array !== 'undefined' && ids_array.length > 0) {
		Swal.fire({
			title: "¿Estas seguro?",
			text: "¡No podrás revertir esto!",
			icon: "warning",
			showCancelButton: true,
			confirmButtonClass: 'btn btn-primary w-xs me-2 mt-2',
			cancelButtonClass: 'btn btn-danger w-xs mt-2',
			confirmButtonText: "Si, eliminalo!",
			buttonsStyling: false,
			showCloseButton: true
		}).then(async function(result) {
			if (result.value) {
				try {
					const response = await fetchEliminarMultiple(ids_array);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("remove-actions").style.display = 'none';
						document.getElementById("checkAll").checked = false;
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al eliminar los escuadrones:', error);
				}

			}
		});
	} else {
		console.error('Error en el proceso de eliminar los escuadrones:', error);
	}
}

function handleNextPageClick(event) {
	event.preventDefault();
	const nextPageButton = document.querySelector(".pagination-next");
	const activePagination = document.querySelector(".pagination.listjs-pagination .active");

	if (activePagination && activePagination.nextElementSibling) {
		activePagination.nextElementSibling.children[0].click();

		nextPageButton.disabled = true;
	}
}

function handlePrevPageClick(event) {
	event.preventDefault();
	const prevPageButton = document.querySelector(".pagination-prev");
	const activePagination = document.querySelector(".pagination.listjs-pagination .active");

	if (activePagination && activePagination.previousElementSibling) {
		activePagination.previousElementSibling.children[0].click();

		prevPageButton.disabled = true;
	}
}

// Agregar un event listener para el botón "Siguiente"
document.querySelector(".pagination-next").addEventListener("click", handleNextPageClick);

// Agregar un event listener para el botón "Anterior"
document.querySelector(".pagination-prev").addEventListener("click", handlePrevPageClick);

document.getElementById("mdEscuadron").addEventListener("show.bs.modal", function(e) {
	if (e.relatedTarget.classList.contains("edit-item-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Editar Escuadrón";
		document.getElementById("content-flBloqueado-field").style.display = "block";
		document.getElementById("mdEscuadron").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Actualizar";
	} else if (e.relatedTarget.classList.contains("add-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Agregar Escuadrón";
		document.getElementById("content-flBloqueado-field").style.display = "none";
		document.getElementById("mdEscuadron").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Agregar Escuadron";
	} else {
		document.getElementById("mdTitulo").innerHTML = "Listar Escuadrón";
		document.getElementById("mdEscuadron").querySelector(".modal-footer").style.display = "none";
	}
});

document.getElementById("mdEscuadron").addEventListener("hidden.bs.modal", function() {
	limpiarCampos();
});

document.querySelector("#listadoEscuadrones").addEventListener("click", function() {
	renderizarCheckboxCheck();
});

// Cargar Imagen
document.querySelector("#escuadron-logo-input").addEventListener("change", function() {
	var preview = document.querySelector("#escuadronlogo-img");
	var file = document.querySelector("#escuadron-logo-input").files[0];
	var reader = new FileReader();
	reader.addEventListener("load", function() {
		preview.src = reader.result;
	}, false);
	if (file) {
		reader.readAsDataURL(file);
	}
});


const checkAll = document.getElementById("checkAll");
if (checkAll) {
	checkAll.onclick = function() {
		var checkboxes = document.querySelectorAll('.form-check-all input[type="checkbox"]');
		var checkedCount = document.querySelectorAll('.form-check-all input[type="checkbox"]:checked').length;
		for (var i = 0; i < checkboxes.length; i++) {
			checkboxes[i].checked = this.checked;
			if (checkboxes[i].checked) {
				checkboxes[i].closest("tr").classList.add("table-active");
			} else {
				checkboxes[i].closest("tr").classList.remove("table-active");
			}
		}

		(checkedCount > 0) ? document.getElementById("remove-actions").style.display = 'none' : document.getElementById("remove-actions").style.display = 'block';
	};
}

async function fetchListarEscuadronesPorUnidadRector() {
	try {
		const response = await fetch('/escuadrones/listarPorIdUnidadRector');
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de escuadrones: ${response.status}`);
		}
		listadoEscuadrones.clear();
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de escuadrones:', error);
		throw error;
	}
}

async function fetchGuardarEscuadron(formData) {
	try {
		const configHttp = {
			method: 'POST',
			body: formData
		}
		const response = await fetch(`/escuadrones/guardar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido insertar el escuadron: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al insertar el escuadron:', error);
		throw error;
	}
}

async function fetchActualizarEscuadron(formData) {
	try {
		const configHttp = {
			method: 'POST',
			body: formData
		}
		const response = await fetch(`/escuadrones/actualizar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido actualizar el escuadron: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al actualizar el escuadron:', error);
		throw error;
	}
}

async function fetchEliminarPorId(id) {
	try {
		const configHttp = {
			method: 'DELETE',
			headers: {
				'Content-Type': 'application/json'
			}
		}
		const response = await fetch(`/escuadrones/eliminar?id=${id}`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar el escuadron: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar el escuadron:', error);
		throw error;
	}
}


async function fetchEliminarMultiple(array) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(array)
		}
		const response = await fetch(`/escuadrones/eliminar-multiple`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar los escudrones: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar los escuadrones:', error);
		throw error;
	}
}