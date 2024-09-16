renderizarLista();
renderizarCheckboxCheck();

// CONFIGURACION DE LISTADO
const perPage = 8;
let formEdit = false;
const settingsDatatable = {
	valueNames: [
		"id_operacion",
		"id_escuadron",
		"no_nombre",
		"fl_estado",
		"fl_bloqueado",
		"tx_descripcion_escuadron"
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


let listadoOperaciones = new List("listadoOperaciones", settingsDatatable).on("updated", function(list) {
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

	if (list.matchingItems.length == perPage) {
		document.querySelector(".pagination.listjs-pagination").firstElementChild.children[0].click()
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
		const lstOperaciones = await fetchListarOperaciones();
		lstOperaciones.forEach(operacion => {
			listadoOperaciones.add({
				id_operacion: operacion.idOperacion,
				id_escuadron: operacion.idEscuadron,
				no_nombre: operacion.noNombre,
				fl_bloqueado: convertirBadgeBloqueado(operacion.flBloqueado),
				fl_estado: convertirBadgeEstado(operacion.flEstado),
				tx_descripcion_escuadron: operacion.txDescripcionEscuadron
			});
			listadoOperaciones.sort('id_operacion', { order: "desc" });
			refreshCallbacks();
			filtrarInformacion();
		})
		listadoOperaciones.remove("id_operacion", '<a href="javascript:void(0);" class="fw-medium link-primary">#VZ2101</a>');
	} catch (error) {
		console.error('Error al mostrar las operaciones:', error);
	}
}

function convertirBadgeEstado(estado) {
	return (estado === 1) ? `<span class="badge badge-soft-success text-uppercase">Activo</span>` : `<span class="badge badge-soft-danger text-uppercase">Inactivo</span>`;
}

function convertirBadgeBloqueado(estado) {
	return (estado === 1) ? `<span class="badge badge-soft-secondary text-uppercase">Permitido</span>` : `<span class="badge badge-soft-danger text-uppercase">Bloqueado</span>`;
}


let idOperacionField = document.getElementById("idOperacion-field"),
	idEscuadronField = document.getElementById("idEscuadron-field"),
	noNombreField = document.getElementById("noNombre-field"),
	flBloqueadoField = document.getElementById("flBloqueado-field");

let forms = document.querySelectorAll('.tablelist-form')
Array.prototype.slice.call(forms).forEach(function(form) {
	form.addEventListener('submit', async function(event) {
		if (idEscuadronField.value == "" || idEscuadronField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Escuadrón")
			return;
		}
		if (!form.checkValidity()) {
			event.preventDefault();
			event.stopPropagation();
			form.classList.add('was-validated');
		} else {
			event.preventDefault();
			if (idEscuadronField.value !== "" &&
				noNombreField.value !== "" && !formEdit) {

				let operacion = {
					idEscuadron: idEscuadronField.value,
					noNombre: noNombreField.value,
				};

				try {
					const response = await fetchGuardarOperacion(operacion);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al insertar la operacion:', error);
				}

			} else if (
				idEscuadronField.value !== "" &&
				noNombreField.value !== "" && formEdit
			) {
				if (flBloqueadoField.value == "" || flBloqueadoField.value == null) {
					event.preventDefault();
					event.stopPropagation();
					notification('info', "Seleccione Permiso")
					return;
				}
				let idOperacion = parseInt(idOperacionField.value);

				let operacion = {
					idOperacion: idOperacion,
					idEscuadron: idEscuadronField.value,
					noNombre: noNombreField.value,
					flBloqueado: flBloqueadoField.value == "Permitido" ? 1 : 0
				};

				try {
					const response = await fetchActualizarOperacion(operacion);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al actualizar la operacion:', error);
				}
			}
		}
	}, false)
})

let bloqueoVal = new Choices(flBloqueadoField);
let escuadronVal = new Choices(idEscuadronField);

function filtrarInformacion() {

	var isstatus = document.getElementById("cboEstado").value;
	var isescuadron = document.getElementById("cboBusquedaEscuadron").value;


	listadoOperaciones.filter(function(data) {
		matchDataStatus = new DOMParser().parseFromString(data.values().fl_estado, 'text/html');
		var status = matchDataStatus.body.textContent.trim()

		matchDataEscuadron = new DOMParser().parseFromString(data.values().id_escuadron, 'text/html');
		var escuadron = matchDataEscuadron.body.textContent.trim()

		var statusFilter = false;
		var escuadronFilter = false;

		if (status == 'all' || isstatus == 'all' || isstatus == "") {
			statusFilter = true;
		} else {
			statusFilter = status == isstatus;
		}

		if (escuadron == '' || isescuadron == '' || isescuadron == "") {
			escuadronFilter = true;
		} else {
			escuadronFilter = escuadron == isescuadron;
		}

		return statusFilter && escuadronFilter;
	});
	listadoOperaciones.update();
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

function limpiarCampos() {
	idOperacionField.value = "";
	noNombreField.value = "";

	escuadronVal.destroy();
	escuadronVal = new Choices(idEscuadronField, {
		searchEnabled: false
	});

	bloqueoVal.destroy();
	bloqueoVal = new Choices(flBloqueadoField, {
		searchEnabled: false
	});

	formEdit = false;
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
}

function handleEditButtonClick(e) {
	e.target.closest("tr").children[1].innerText;
	itemId = e.target.closest("tr").children[1].innerText;
	var itemValues = listadoOperaciones.get({
		id_operacion: itemId,
	});

	Array.from(itemValues).forEach(function(x) {
		var selectedid = x._values.id_operacion;
		if (selectedid == itemId) {
			formEdit = true;
			idOperacionField.value = selectedid;

			noNombreField.value = x._values.no_nombre;

			if (escuadronVal) escuadronVal.destroy();
			escuadronVal = new Choices(idEscuadronField, {
				searchEnabled: false
			});
			escuadronVal.setChoiceByValue(x._values.id_escuadron.toString());

			if (bloqueoVal) bloqueoVal.destroy();
			bloqueoVal = new Choices(flBloqueadoField, {
				searchEnabled: false
			});

			valBloqueo = new DOMParser().parseFromString(x._values.fl_bloqueado, "text/html");
			var bloqueoSelec = valBloqueo.body.firstElementChild.innerHTML;
			bloqueoVal.setChoiceByValue(bloqueoSelec);

		}
	});
}

function handleDeleteButtonClick(e) {
	e.target.closest("tr").children[1].innerText;
	itemId = e.target.closest("tr").children[1].innerText;
	var itemValues = listadoOperaciones.get({
		id_operacion: itemId,
	});

	Array.from(itemValues).forEach(function(x) {
		let isdeleteid = x._values.id_operacion;

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
					console.error('Error al eliminar la operacion:', error);
				}

			});
		}
	});
}


function eliminarMultiple() {
	ids_array = [];
	var checkboxes = document.getElementsByName('chk_child');
	checkboxes.forEach(function(checkbox) {
		if (checkbox.checked) {
			var trNode = checkbox.closest("tr");
			var id = trNode.querySelector("td.id_operacion").textContent;
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
					console.error('Error al eliminar las operaciones:', error);
				}

			}
		});
	} else {
		console.error('Error en el proceso de eliminar las operaciones:', error);
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

document.getElementById("mdOperacion").addEventListener("show.bs.modal", function(e) {
	if (e.relatedTarget.classList.contains("edit-item-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Editar Operación";
		document.getElementById('content-flBloqueado-field').style.display = 'block';
		document.getElementById("mdOperacion").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Actualizar";
	} else if (e.relatedTarget.classList.contains("add-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Agregar Operación";
		document.getElementById('content-flBloqueado-field').style.display = 'none';
		document.getElementById("mdOperacion").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Agregar Operacion";
	} else {
		document.getElementById("mdTitulo").innerHTML = "Listar Operacion";
		document.getElementById("mdOperacion").querySelector(".modal-footer").style.display = "none";
	}
});

//Se oculta modal y limpia los campos 
document.getElementById("mdOperacion").addEventListener("hidden.bs.modal", function() {
	limpiarCampos();
});

//Obtiene el click en FlotaList
document.querySelector("#listadoOperaciones").addEventListener("click", function() {
	renderizarCheckboxCheck();
});

const checkAll = document.getElementById("checkAll");
if (checkAll) {
	checkAll.onclick = function() {
		var checkboxes = document.querySelectorAll('.form-check-all input[type="checkbox"]');
		var checkedCount = document.querySelectorAll('.form-check-all input[type="checkbox"]:checked').length;
		//Agrega la clase de seleccionado cuando esta chequeado
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

async function fetchListarOperaciones() {
	try {
		const response = await fetch('/operaciones/listar');
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de operaciones: ${response.status}`);
		}
		listadoOperaciones.clear();
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de operaciones:', error);
		throw error;
	}
}

async function fetchGuardarOperacion(operacion) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(operacion)
		}
		const response = await fetch(`/operaciones/guardar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido insertar la operacion: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al insertar la operacion:', error);
		throw error;
	}
}

async function fetchActualizarOperacion(operacion) {
	try {
		const configHttp = {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(operacion)
		}
		const response = await fetch(`/operaciones/actualizar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido actualizar la operacion: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al actualizar la operacion:', error);
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
		const response = await fetch(`/operaciones/eliminar?id=${id}`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar la operacion: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar la operacion:', error);
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
		const response = await fetch(`/operaciones/eliminar-multiple`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar las operaciones: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar la operaciones:', error);
		throw error;
	}
}