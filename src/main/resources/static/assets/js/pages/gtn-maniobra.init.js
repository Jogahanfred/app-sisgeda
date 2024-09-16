renderizarLista();
renderizarCheckboxCheck();

// CONFIGURACION DE LISTADO
const perPage = 8;
let formEdit = false;
const settingsDatatable = {
	valueNames: [
		"id_maniobra",
		"id_banco_maniobra",
		"id_operacion",
		"fl_estado",
		"fl_bloqueado",
		"tx_descripcion_operacion",
		"tx_descripcion_banco_maniobra",
		"id_escuadron",
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

let listadoManiobras = new List("listadoManiobras", settingsDatatable).on("updated", function(list) {
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
		const lstManiobras = await fetchListarManiobras();
		lstManiobras.forEach(maniobra => {
			listadoManiobras.add({
				id_maniobra: maniobra.idManiobra,
				id_banco_maniobra: maniobra.idBancoManiobra,
				id_operacion: maniobra.idOperacion,
				fl_bloqueado: convertirBadgeBloqueado(maniobra.flBloqueado),
				fl_estado: convertirBadgeEstado(maniobra.flEstado),
				tx_descripcion_operacion: maniobra.txDescripcionOperacion,
				tx_descripcion_banco_maniobra: maniobra.txDescripcionBancoManiobra,
				id_escuadron: maniobra.idEscuadron,
				tx_descripcion_escuadron: maniobra.txDescripcionEscuadron
			});
			listadoManiobras.sort('id_maniobra', { order: "desc" });
			refreshCallbacks();
			filtrarInformacion();
		})
		listadoManiobras.remove("id_maniobra", '<a href="javascript:void(0);" class="fw-medium link-primary">#VZ2101</a>');
	} catch (error) {
		console.error('Error al mostrar las maniobras:', error);
	}
}

function convertirBadgeEstado(estado) {
	return (estado === 1) ? `<span class="badge badge-soft-success text-uppercase">Activo</span>` : `<span class="badge badge-soft-danger text-uppercase">Inactivo</span>`;
}

function convertirBadgeBloqueado(estado) {
	return (estado === 1) ? `<span class="badge badge-soft-secondary text-uppercase">Permitido</span>` : `<span class="badge badge-soft-danger text-uppercase">Bloqueado</span>`;
}

let idManiobraField = document.getElementById("idManiobra-field"),
	idBancoManiobraField = document.getElementById("idBancoManiobra-field"),
	idOperacionField = document.getElementById("idOperacion-field"),
	flBloqueadoField = document.getElementById("flBloqueado-field"),

	idEscuadronField = document.getElementById("idEscuadron-field"),

	cboBusquedaEscuadron = document.getElementById("cboBusquedaEscuadron"),
	cboBusquedaOperacion = document.getElementById("cboBusquedaOperacion"); 

let forms = document.querySelectorAll('.tablelist-form')
Array.prototype.slice.call(forms).forEach(function(form) {
	form.addEventListener('submit', async function(event) {
		if (idEscuadronField.value == "" || idEscuadronField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Escuadrón")
			return;
		}
		if (idOperacionField.value == "" || idOperacionField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Operación")
			return;
		}
		if (idBancoManiobraField.value == "" || idBancoManiobraField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Maniobra del Banco")
			return;
		}
		if (!form.checkValidity()) {
			event.preventDefault();
			event.stopPropagation();
		} else {
			event.preventDefault();
			if (idBancoManiobraField.value !== "" &&
				idOperacionField.value !== "" && !formEdit) {

				let maniobra = {
					idBancoManiobra: idBancoManiobraField.value,
					idOperacion: idOperacionField.value,
				};

				try {
					const response = await fetchGuardarManiobra(maniobra);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al insertar la maniobra:', error);
				}

			} else if (
				idBancoManiobraField.value !== "" &&
				idOperacionField.value !== "" && formEdit
			) {
				if (flBloqueadoField.value == "" || flBloqueadoField.value == null) {
					event.preventDefault();
					event.stopPropagation();
					notification('info', "Seleccione Permiso")
					return;
				}
				let idManiobra = parseInt(idManiobraField.value);

				let maniobra = {
					idManiobra: idManiobra,
					idBancoManiobra: idBancoManiobraField.value,
					idOperacion: idOperacionField.value,
					flBloqueado: flBloqueadoField.value == "Permitido" ? 1 : 0
				};

				try {
					const response = await fetchActualizarManiobra(maniobra);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al actualizar la maniobra:', error);
				}
			}
		}
	}, false)
})
 
let bloqueoVal = new Choices(flBloqueadoField);
let bancoManiobraVal = new Choices(idBancoManiobraField);
let operacionVal = new Choices(idOperacionField);
let escuadronVal = new Choices(idEscuadronField);

let cboBusquedaEscuadronVal = new Choices(cboBusquedaEscuadron);
let cboBusquedaOperacionVal = new Choices(cboBusquedaOperacion);

idEscuadronField.addEventListener("change", async function() {
	let idEscuadron = idEscuadronField.value;
	try {
		const response = await fetchListarOperacionesPorIdEscuadron(idEscuadron);
		llenarCboIdOperacion(response);
	} catch (error) {
		console.error('Error al listar Operaciones:', error);
	}
});

function llenarCboIdOperacion(lstOperaciones, idOperacion) {
	if (operacionVal) operacionVal.destroy();
	operacionVal = new Choices(idOperacionField, {
		searchEnabled: false
	});
	const choices = [];

	lstOperaciones.forEach(operacion => {
		choices.push({
			value: operacion.idOperacion.toString(),
			label: operacion.noNombre,
			selected: (idOperacion) ? operacion.idOperacion == idOperacion : false,
			disabled: false
		});
	});

	operacionVal.setChoices(choices, 'value', 'label', false);
}

cboBusquedaEscuadron.addEventListener("change", async function() {
	var idEscuadron = cboBusquedaEscuadron.value;
	try {
		const response = await fetchListarOperacionesPorIdEscuadron(idEscuadron);
		llenarCboBusquedaOperacion(response);
	} catch (error) {
		console.error('Error al listar Operaciones:', error);
	}
});

function llenarCboBusquedaOperacion(lstOperaciones) {
	if (cboBusquedaOperacionVal) cboBusquedaOperacionVal.destroy();
	cboBusquedaOperacionVal = new Choices(cboBusquedaOperacion, {
		searchEnabled: false
	});
	const choices = [];

	lstOperaciones.forEach(operacion => {
		choices.push({
			value: operacion.idOperacion.toString(),
			label: operacion.noNombre,
			selected: false,
			disabled: false
		});
	});

	cboBusquedaOperacionVal.setChoices(choices, 'value', 'label', false);

} 

function filtrarInformacion() {

	var isstatus = document.getElementById("cboEstado").value;
	var isescuadron = document.getElementById("cboBusquedaEscuadron").value;
	var isoperacion = document.getElementById("cboBusquedaOperacion").value;


	listadoManiobras.filter(function(data) {
		matchDataStatus = new DOMParser().parseFromString(data.values().fl_estado, 'text/html');
		var status = matchDataStatus.body.textContent.trim()

		matchDataEscuadron = new DOMParser().parseFromString(data.values().id_escuadron, 'text/html');
		var escuadron = matchDataEscuadron.body.textContent.trim()

		matchDataOperacion = new DOMParser().parseFromString(data.values().id_operacion, 'text/html');
		var operacion = matchDataOperacion.body.textContent.trim()

		var statusFilter = false;
		var escuadronFilter = false;
		var operacionFilter = false;

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

		if (operacion == '' || isoperacion == '' || isoperacion == "") {
			operacionFilter = true;
		} else {
			operacionFilter = operacion == isoperacion;
		}

		return statusFilter && escuadronFilter && operacionFilter;
	});
	listadoManiobras.update();
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
	idManiobraField.value = "";

	bancoManiobraVal.destroy();
	bancoManiobraVal = new Choices(idBancoManiobraField, {
		searchEnabled: true
	});

	escuadronVal.destroy();
	escuadronVal = new Choices(idEscuadronField, {
		searchEnabled: true
	});

	operacionVal.destroy();
	operacionVal = new Choices(idOperacionField, {
		searchEnabled: true
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
	var itemValues = listadoManiobras.get({
		id_maniobra: itemId,
	});

	Array.from(itemValues).forEach(async function(x) {
		let selectedid = x._values.id_maniobra;
		if (selectedid == itemId) {
			formEdit = true;
			idManiobraField.value = selectedid;

			if (escuadronVal) escuadronVal.destroy();
			escuadronVal = new Choices(idEscuadronField, {
				searchEnabled: true
			});
			escuadronVal.setChoiceByValue(x._values.id_escuadron.toString());


			try {
				const response = await fetchListarOperacionesPorIdEscuadron(x._values.id_escuadron.toString());
				llenarCboIdOperacion(response, x._values.id_operacion);
			} catch (error) {
				console.error('Error al cargar las Operaciones:', error);
			} 

			if (bancoManiobraVal) bancoManiobraVal.destroy();
			bancoManiobraVal = new Choices(idBancoManiobraField, {
				searchEnabled: true
			});
			bancoManiobraVal.setChoiceByValue(x._values.id_banco_maniobra.toString());
 
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
	var itemValues = listadoManiobras.get({
		id_maniobra: itemId,
	});

	Array.from(itemValues).forEach(function(x) {
		let isdeleteid = x._values.id_maniobra;

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
					console.error('Error al eliminar la maniobra:', error);
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
			var id = trNode.querySelector("td.id_maniobra").textContent;
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
					console.error('Error al eliminar las maniobras:', error);
				}
			}
		});
	} else {
		console.error('Error en el proceso de eliminar las maniobras:', error);
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

document.getElementById("mdManiobra").addEventListener("show.bs.modal", function(e) {
	if (e.relatedTarget.classList.contains("edit-item-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Editar Maniobra";
		document.getElementById('content-flBloqueado-field').style.display = 'block';
		document.getElementById("mdManiobra").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Actualizar";
	} else if (e.relatedTarget.classList.contains("add-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Agregar Maniobra";
		document.getElementById('content-flBloqueado-field').style.display = 'none';
		document.getElementById("mdManiobra").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Agregar Maniobra";
	} else {
		document.getElementById("mdTitulo").innerHTML = "Listar Maniobra";
		document.getElementById("mdManiobra").querySelector(".modal-footer").style.display = "none";
	}
});

document.getElementById("mdManiobra").addEventListener("hidden.bs.modal", function() {
	limpiarCampos();
});

document.querySelector("#listadoManiobras").addEventListener("click", function() {
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

async function fetchListarManiobras() {
	try {
		const response = await fetch('/maniobras/listar');
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de maniobras: ${response.status}`);
		}
		listadoManiobras.clear();
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de maniobras:', error);
		throw error;
	}
}

async function fetchGuardarManiobra(maniobra) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(maniobra)
		}
		const response = await fetch(`/maniobras/guardar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido insertar la maniobra: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al insertar la maniobra:', error);
		throw error;
	}
}

async function fetchActualizarManiobra(maniobra) {
	try {
		const configHttp = {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(maniobra)
		}
		const response = await fetch(`/maniobras/actualizar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido actualizar la maniobra: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al actualizar la maniobra:', error);
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
		const response = await fetch(`/maniobras/eliminar?id=${id}`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar la maniobra: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar la maniobra:', error);
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
		const response = await fetch(`/maniobras/eliminar-multiple`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar las maniobras: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar las maniobras:', error);
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
		console.error('Error al obtener la lista de Operaciones:', error);
		throw error;
	}
}
