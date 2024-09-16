renderizarLista();
renderizarCheckboxCheck();

// CONFIGURACION DE LISTADO
const perPage = 8;
let formEdit = false;
const settingsDatatable = {
	valueNames: [
		"id_unidad",
		"nu_codigo",
		"no_nombre",
		"tx_descripcion",
		"nu_nivel",
		"nu_codigo_rector",
		"fl_bloqueado",
		"fl_estado",
		"tx_ruta_logo",
		"tx_ubicacion",
		"tx_informacion",
		"tx_descripcion_organo_rector",
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

let listadoUnidades = new List("listadoUnidades", settingsDatatable).on("updated", function(list) {
	list.matchingItems.length == 0 ?
		(document.getElementsByClassName("noresult")[0].style.display = "block") :
		(document.getElementsByClassName("noresult")[0].style.display = "none");
	var isFirst = list.i == 1;
	var isLast = list.i > list.matchingItems.length - list.page;
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
		const lstUnidades = await fetchListarUnidades();
		lstUnidades.forEach(unidad => {
			listadoUnidades.add({
				id_unidad: unidad.idUnidad,
				nu_codigo: unidad.nuCodigo,
				no_nombre: unidad.noNombre,
				tx_descripcion: unidad.txDescripcion,
				nu_nivel: convertirBadgeNivel(unidad.nuNivel),
				nu_codigo_rector: unidad.nuCodigoRector,
				fl_bloqueado: convertirBadgeBloqueado(unidad.flBloqueado),
				tx_ruta_logo: unidad.txRutaLogo,
				tx_ubicacion: unidad.txUbicacion,
				tx_informacion: unidad.txInformacion,
				image_src: (unidad.txRutaLogo != null && unidad.txRutaLogo != 'null') ? 'uploads/unidad/' + unidad.txRutaLogo : 'assets/images/users/multi-user.jpg',
				fl_estado: unidad.flEstado,
				tx_descripcion_organo_rector: unidad.txDescripcionOrganoRector,
			});
			refreshCallbacks();
			document.getElementById("loader").style.display = 'none';
			document.getElementById("contentTable").style.display = 'block';
		})
		listadoUnidades.remove("id_unidad", '<a href="javascript:void(0);" class="fw-medium link-primary">#VZ2101</a>');
	} catch (error) {
		console.error('Error al mostrar las unidades:', error);
	}
}

const form = document.getElementById('frmUnidad'),
	  addBtn = document.getElementById("add-btn"),
	  editBtn = document.getElementById("edit-btn"),
	  removeBtns = document.getElementsByClassName("remove-item-btn"),
	  editBtns = document.getElementsByClassName("edit-item-btn"),
	  viewBtns = document.getElementsByClassName("view-item-btn");


function convertirBadgeBloqueado(estado) {
	return (estado === 1) ? `<span class="badge badge-soft-secondary text-uppercase">Permitido</span>` : `<span class="badge badge-soft-danger text-uppercase">Bloqueado</span>`;
}

function convertirBadgeNivel(estado) {
	switch (estado) {
		case 1:
			return (
				'<span class="badge badge-soft-primary text-uppercase">Nivel 1</span>'
			);
		case 2:
			return (
				'<span class="badge badge-soft-info text-uppercase">Nivel 2</span>'
			);
		case 3:
			return (
				'<span class="badge badge-soft-secondary text-uppercase">Nivel 3</span>'
			);
		case 4:
			return (
				'<span class="badge badge-soft-success text-uppercase">Nivel 4</span>'
			);
		default:
			return (
				'<span class="badge badge-secondary text-uppercase">Estado desconocido</span>'
			);
	}
}

let idUnidadField = document.getElementById("idUnidad-field"),
	unidadLogoImg = document.getElementById("unidadlogo-img"),
	nuCodigoField = document.getElementById("nuCodigo-field"),
	noNombreField = document.getElementById("noNombre-field"),
	txDescripcionField = document.getElementById("txDescripcion-field"),
	nuNivelField = document.getElementById("nuNivel-field"),
	flBloqueadoField = document.getElementById("flBloqueado-field"),
	flEstadoField = document.getElementById("flEstado-field"),
	txRutaLogoField = document.getElementById("txRutaLogo-field"),
	txUbicacionField = document.getElementById("txUbicacion-field"),
	txInformacionField = document.getElementById("txInformacion-field"),
	unidadLogoInput = document.getElementById("unidad-logo-input"),
	nuCodigoRectorField = document.getElementById("nuCodigoRector-field");

let nivelVal = new Choices(nuNivelField);
let bloqueoVal = new Choices(flBloqueadoField)
let codigoRectorVal = new Choices(nuCodigoRectorField)

let forms = document.querySelectorAll('.tablelist-form')
Array.prototype.slice.call(forms).forEach(function(form) {
	form.addEventListener('submit', async function(event) {
		if (nuNivelField.value == "" || nuNivelField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Nivel")
			return;
		}
		if (nuCodigoRectorField.value == "" || nuCodigoRectorField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Órgano Rector")
			return;
		}

		if (!form.checkValidity()) {
			event.preventDefault();
			event.stopPropagation();
			form.classList.add('was-validated');
		} else {
			event.preventDefault();
			if (nuCodigoField.value !== "" &&
				noNombreField.value !== "" &&
				txDescripcionField.value !== "" &&
				nuNivelField.value !== "" &&
				nuCodigoRectorField.value !== "" && !formEdit) {

				let formData = new FormData();
				let archivoInput = document.getElementById('unidad-logo-input');

				let unidad = {
					nuCodigo: nuCodigoField.value,
					noNombre: noNombreField.value,
					txDescripcion: txDescripcionField.value,
					nuNivel: nuNivelField.value === "Nivel 1" ? 1 : nuNivelField.value === "Nivel 2" ? 2 : nuNivelField.value === "Nivel 3" ? 3 : nuNivelField.value === "Nivel 4" ? 4 : nuNivelField.value === "Nivel 5" ? 5 : 0,
					nuCodigoRector: nuCodigoRectorField.value,
					txRutaLogo: "",
					txUbicacion: txUbicacionField.value,
					txInformacion: txInformacionField.value
				};
				formData.append('unidad', JSON.stringify(unidad));
				formData.append('archivo', archivoInput.files[0]);

				try {
					const response = await fetchGuardarUnidad(formData);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al insertar la unidad:', error);
				}

			} else if (
				nuCodigoField.value !== "" &&
				noNombreField.value !== "" &&
				txDescripcionField.value !== "" &&
				nuNivelField.value !== "" &&
				nuCodigoRectorField.value !== "" && formEdit) {
					
				if (flBloqueadoField.value == "" || flBloqueadoField.value == null) {
					event.preventDefault();
					event.stopPropagation();
					notification('info', "Seleccione Permiso")
					return;
				}

				var formData = new FormData();
				var archivoInput = document.getElementById('unidad-logo-input');

				var idUnidad = parseInt(idUnidadField.value);

				var unidad = {
					idUnidad: idUnidad,
					nuCodigo: nuCodigoField.value,
					noNombre: noNombreField.value,
					txDescripcion: txDescripcionField.value,
					nuNivel: nuNivelField.value === "Nivel 1" ? 1 : nuNivelField.value === "Nivel 2" ? 2 : nuNivelField.value === "Nivel 3" ? 3 : nuNivelField.value === "Nivel 4" ? 4 : nuNivelField.value === "Nivel 5" ? 5 : 0,
					nuCodigoRector: nuCodigoRectorField.value,
					txRutaLogo: txRutaLogoField.value,
					txUbicacion: txUbicacionField.value,
					txInformacion: txInformacionField.value,
					flBloqueado: flBloqueadoField.value == "Permitido" ? 1 : 0,
					flEstado: flEstadoField.value
				};

				formData.append('unidad', JSON.stringify(unidad));

				if (archivoInput.files.length > 0) {
					formData.append('archivo', archivoInput.files[0]);
				} else {
					formData.append('archivo', null);
				}

				try {
					const response = await fetchActualizarUnidad(formData);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al actualizar la unidad:', error);
				}
			}
		}
	}, false)
})

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

			var itemValues = listadoUnidades.get({
				id_unidad: itemId,
			});

			Array.from(itemValues).forEach(function(x) {

				var selectedid = x._values.id_unidad;
				if (selectedid == itemId) {
					renderizarCardUnidadDetalle(x._values);
				}
			});

		});
	});

	renderizarUnidadDetalle();
}

function renderizarUnidadDetalle() {
	let firstItem = listadoUnidades.get()[0];
	if (firstItem) {
		let data = firstItem._values;
		renderizarCardUnidadDetalle(data);
	}
}

function renderizarCardUnidadDetalle(data) {
	let bloque = `
            <div class="card-body text-center">
                <div class="position-relative d-inline-block">
                    <div class="avatar-xl">
                        <div class="avatar-title bg-light rounded">
                            <img src="${data.image_src}" alt="" class="avatar-xl">
                        </div>
                    </div>
                </div>
                <h5 class="mt-3 mb-1">${data.no_nombre}</h5>
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
                                <td class="fw-medium" scope="row">Nivel</td>
                                <td>${data.nu_nivel} <i class="ri-star-fill text-warning align-bottom"></i></td>
                            </tr>
                            <tr>
                                <td class="fw-medium" scope="row">Órgano Rector</td>
                                <td>${data.tx_descripcion_organo_rector}</td>
                            </tr>
                            <tr>
                                <td class="fw-medium" scope="row">Código</td>
                                <td>${data.nu_codigo}</td>
                            </tr>  
                            <tr>
                                <td class="fw-medium" scope="row">Ubicación</td>
                                <td>${data.tx_ubicacion}</td>
                            </tr>
                            <tr>
                                <td class="fw-medium" scope="row">Permiso</td>
                                <td>${data.fl_bloqueado}</td>
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
	var itemValues = listadoUnidades.get({
		id_unidad: itemId,
	});
	Array.from(itemValues).forEach(function(x) {
		var selectedid = x._values.id_unidad;
		if (selectedid == itemId) {
			formEdit = true;
			idUnidadField.value = selectedid;
			unidadLogoImg.src = (x._values.tx_ruta_logo !== null && x._values.tx_ruta_logo !== 'null') ? 'uploads/unidad/' + x._values.tx_ruta_logo : 'assets/images/users/multi-user.jpg';
			txRutaLogoField.value = (x._values.tx_ruta_logo !== null && x._values.tx_ruta_logo !== 'null') ? x._values.tx_ruta_logo : '';
			nuCodigoField.value = x._values.nu_codigo;
			noNombreField.value = x._values.no_nombre;
			txDescripcionField.value = x._values.tx_descripcion;

			if (nivelVal) nivelVal.destroy();
			nivelVal = new Choices(nuNivelField, {
				searchEnabled: false
			});

			valNivel = new DOMParser().parseFromString(x._values.nu_nivel, "text/html");
			var nivelSelec = valNivel.body.firstElementChild.innerHTML;
			nivelVal.setChoiceByValue(nivelSelec);


			if (bloqueoVal) bloqueoVal.destroy();
			bloqueoVal = new Choices(flBloqueadoField, {
				searchEnabled: false
			});

			valBloqueo = new DOMParser().parseFromString(x._values.fl_bloqueado, "text/html");
			var bloqueoSelec = valBloqueo.body.firstElementChild.innerHTML;
			bloqueoVal.setChoiceByValue(bloqueoSelec);

			if (codigoRectorVal) codigoRectorVal.destroy();
			codigoRectorVal = new Choices(nuCodigoRectorField, {
				searchEnabled: false
			});

			var indice = codigoRectorVal._store.choices.findIndex(function(choice) {
				return choice.value == x._values.nu_codigo.toString();
			});

			if (indice !== -1) {
				codigoRectorVal._store.choices[indice].disabled = true;
			}

			codigoRectorVal.setChoiceByValue(x._values.nu_codigo_rector.toString());

			txUbicacionField.value = x._values.tx_ubicacion;
			txInformacionField.value = x._values.tx_informacion;

		}
	});
}

function handleDeleteButtonClick(e) {
	e.target.closest("tr").children[1].innerText;
	itemId = e.target.closest("tr").children[1].innerText;
	var itemValues = listadoUnidades.get({
		id: itemId,
	});

	Array.from(itemValues).forEach(function(x) {
		deleteid = new DOMParser().parseFromString(x._values.id, "text/html");

		var isElem = deleteid.body.firstElementChild;
		var isdeleteid = deleteid.body.firstElementChild.innerHTML;

		if (isdeleteid == itemId) {
			document.getElementById("delete-record").addEventListener("click", function() {
				listadoUnidades.remove("id", isElem.outerHTML);
				document.getElementById("deleteRecord-close").click();
			});
		}
	});
}

function limpiarCampos() {
	idUnidadField.value = "";
	nuCodigoField.value = "";
	noNombreField.value = "";
	txDescripcionField.value = "";
	unidadLogoInput.value = "";

	nivelVal.destroy();
	nivelVal = new Choices(nuNivelField, {
		searchEnabled: false
	});

	codigoRectorVal.destroy();
	codigoRectorVal = new Choices(nuCodigoRectorField, {
		searchEnabled: false
	});

	txRutaLogoField.value = "";
	unidadLogoImg.src = "assets/images/users/multi-user.jpg";

	txUbicacionField.value = "";
	txInformacionField.value = "";

	bloqueoVal.destroy();
	bloqueoVal = new Choices(flBloqueadoField, {
		searchEnabled: false
	});

	flEstadoField.value = "";

	formEdit = false;
}

// Delete Multiple Records
function eliminarMultiple() {
	ids_array = [];
	var items = document.getElementsByName('chk_child');
	for (i = 0; i < items.length; i++) {
		if (items[i].checked == true) {
			var trNode = items[i].parentNode.parentNode.parentNode;
			var id = trNode.querySelector("td.id_unidad").textContent;
			ids_array.push(id);
		}
	}

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
					console.error('Error al eliminar las unidades:', error);
				}


			}
		});
	} else {
		console.error('Error en el proceso de eliminar las unidades:', error);
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

document.getElementById("mdUnidad").addEventListener("show.bs.modal", function(e) {
	if (e.relatedTarget.classList.contains("edit-item-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Editar Unidad";
		document.getElementById("content-flBloqueado-field").style.display = "block";
		document.getElementById("mdUnidad").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Actualizar";
	} else if (e.relatedTarget.classList.contains("add-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Agregar Unidad";
		document.getElementById("content-flBloqueado-field").style.display = "none";
		document.getElementById("mdUnidad").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Agregar Unidad";
	} else {
		document.getElementById("mdTitulo").innerHTML = "Listar Unidad";
		document.getElementById("mdUnidad").querySelector(".modal-footer").style.display = "none";
	}
});

document.getElementById("mdUnidad").addEventListener("hidden.bs.modal", function() {
	limpiarCampos(); 
});

document.querySelector("#listadoUnidades").addEventListener("click", function() {
	renderizarCheckboxCheck();
});

//Busqueda y seleccion del Checkbox
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

// Cargar Imagen
document.querySelector("#unidad-logo-input").addEventListener("change", function() {
	var preview = document.querySelector("#unidadlogo-img");
	var file = document.querySelector("#unidad-logo-input").files[0];
	var reader = new FileReader();
	reader.addEventListener("load", function() {
		preview.src = reader.result;
	}, false);
	if (file) {
		reader.readAsDataURL(file);
	}
});

async function fetchListarUnidades() {
	try {
		const response = await fetch('/unidades/listar');
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de unidades: ${response.status}`);
		}
		listadoUnidades.clear();
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de unidades:', error);
		throw error;
	}
}

async function fetchGuardarUnidad(formData) {
	try {
		const configHttp = {
			method: 'POST',
			body: formData
		}
		const response = await fetch(`/unidades/guardar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido insertar la unidad: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al insertar la unidad:', error);
		throw error;
	}
}

async function fetchActualizarUnidad(formData) {
	try {
		const configHttp = {
			method: 'POST',
			body: formData
		}
		const response = await fetch(`/unidades/actualizar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido actualizar la unidad: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al actualizar la unidad:', error);
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
		const response = await fetch(`/unidades/eliminar-multiple`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar las unidades: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar la unidades:', error);
		throw error;
	}
}


