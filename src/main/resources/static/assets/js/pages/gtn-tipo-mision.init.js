renderizarLista();
renderizarCheckboxCheck();

// CONFIGURACION DE LISTADO
const perPage = 8;
let formEdit = false;
const settingsDatatable = {
	valueNames: [
		"id_tipo_mision",
		"co_codigo",
		"no_nombre",
		"tx_descripcion",
		"fl_estado",
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


let listadoTipoMisiones = new List("listadoTipoMisiones", settingsDatatable).on("updated", function(list) {
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
		const lstTipos = await fetchListarTipoMisiones();
		lstTipos.forEach(tipo => {
			listadoTipoMisiones.add({
				id_tipo_mision: tipo.idTipoMision,
				co_codigo: tipo.coCodigo,
				no_nombre: tipo.noNombre,
				tx_descripcion: tipo.txDescripcion,
				fl_estado: convertirBadgeEstado(tipo.flEstado)
			});
			listadoTipoMisiones.sort('id_tipo_mision', { order: "desc" });
			refreshCallbacks();
			filtrarInformacion();
		})
		listadoTipoMisiones.remove("id_tipo_mision", '<a href="javascript:void(0);" class="fw-medium link-primary">#VZ2101</a>');
	} catch (error) {
		console.error('Error al mostrar los tipos de misiones:', error);
	}
}

function convertirBadgeEstado(estado) {
	return (estado === 1) ? `<span class="badge badge-soft-success text-uppercase">Activo</span>` : `<span class="badge badge-soft-danger text-uppercase">Inactivo</span>`;
}

let idTipoMisionField = document.getElementById("idTipoMision-field"),
	coCodigoField = document.getElementById("coCodigo-field"),
	noNombreField = document.getElementById("noNombre-field"),
	txDescripcionField = document.getElementById("txDescripcion-field");

let forms = document.querySelectorAll('.tablelist-form')
Array.prototype.slice.call(forms).forEach(function(form) {
	form.addEventListener('submit', async function(event) {

		if (!form.checkValidity()) {
			event.preventDefault();
			event.stopPropagation();
			form.classList.add('was-validated');
		} else {
			event.preventDefault();
			if (coCodigoField.value !== "" &&
				noNombreField.value !== "" && !formEdit) {

				let tipoMision = {
					coCodigo: coCodigoField.value,
					noNombre: noNombreField.value,
					txDescripcion: txDescripcionField.value,
				};

				try {
					const response = await fetchGuardarTipoMision(tipoMision);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al insertar el tipo de mision:', error);
				}

			} else if (
				coCodigoField.value !== "" &&
				noNombreField.value !== "" && formEdit
			) {
				let idTipoMision = parseInt(idTipoMisionField.value);

				let tipoMision = {
					idTipoMision: idTipoMision,
					coCodigo: coCodigoField.value,
					noNombre: noNombreField.value,
					txDescripcion: txDescripcionField.value
				};

				try {
					const response = await fetchActualizarTipoMision(tipoMision);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al actualizar el tipo de mision:', error);
				}
			}
		}
	}, false)
})

function filtrarInformacion() {
	var isstatus = document.getElementById("cboEstado").value;

	listadoTipoMisiones.filter(function(data) {
		matchDataStatus = new DOMParser().parseFromString(data.values().fl_estado, 'text/html');
		var status = matchDataStatus.body.textContent.trim()

		var statusFilter = false;

		if (status == 'all' || isstatus == 'all' || isstatus == "") {
			statusFilter = true;
		} else {
			statusFilter = status == isstatus;
		}

		return statusFilter;
	});
	listadoTipoMisiones.update();
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
	idTipoMisionField.value = "";
	coCodigoField.value = "";
	noNombreField.value = "";
	txDescripcionField.value = "";
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
	var itemValues = listadoTipoMisiones.get({
		id_tipo_mision: itemId,
	});

	Array.from(itemValues).forEach(function(x) {
		let selectedid = x._values.id_tipo_mision;
		if (selectedid == itemId) {
			formEdit = true;
			idTipoMisionField.value = selectedid;

			coCodigoField.value = x._values.co_codigo;
			noNombreField.value = x._values.no_nombre;
			txDescripcionField.value = x._values.tx_descripcion;
		}
	});
}

function handleDeleteButtonClick(e) {
	e.target.closest("tr").children[1].innerText;
	itemId = e.target.closest("tr").children[1].innerText;
	var itemValues = listadoTipoMisiones.get({
		id_tipo_mision: itemId,
	});

	Array.from(itemValues).forEach(function(x) {
		let isdeleteid = x._values.id_tipo_mision;

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
					console.error('Error al eliminar el tipo de mision:', error);
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
			var id = trNode.querySelector("td.id_tipo_mision").textContent;
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
					console.error('Error al eliminar los tipos de misiones:', error);
				}

			}
		});
	} else {
		console.error('Error en el proceso de eliminar las flotas:', error);
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

document.getElementById("mdTipoMision").addEventListener("show.bs.modal", function(e) {
	if (e.relatedTarget.classList.contains("edit-item-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Editar Tipo";
		document.getElementById("mdTipoMision").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Actualizar";
	} else if (e.relatedTarget.classList.contains("add-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Agregar Tipo";
		document.getElementById("mdTipoMision").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Agregar Tipo";
	} else {
		document.getElementById("mdTitulo").innerHTML = "Listar Tipo";
		document.getElementById("mdTipoMision").querySelector(".modal-footer").style.display = "none";
	}
});

document.getElementById("mdTipoMision").addEventListener("hidden.bs.modal", function() {
	limpiarCampos();
});

document.querySelector("#listadoTipoMisiones").addEventListener("click", function() {
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

async function fetchListarTipoMisiones() {
	try {
		const response = await fetch('/tipoMisiones/listar');
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de tipos de misiones: ${response.status}`);
		}
		listadoTipoMisiones.clear();
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de tipos de misiones:', error);
		throw error;
	}
}

async function fetchGuardarTipoMision(tipo) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(tipo)
		}
		const response = await fetch(`/tipoMisiones/guardar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido insertar el tipo: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al insertar el tipo:', error);
		throw error;
	}
}

async function fetchActualizarTipoMision(tipo) {
	try {
		const configHttp = {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(tipo)
		}
		const response = await fetch(`/tipoMisiones/actualizar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido actualizar el tipo: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al actualizar el tipo:', error);
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
		const response = await fetch(`/tipoMisiones/eliminar?id=${id}`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar el tipo: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar el tipo:', error);
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
		const response = await fetch(`/tipoMisiones/eliminar-multiple`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar los tipos: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar el tipo:', error);
		throw error;
	}
}
