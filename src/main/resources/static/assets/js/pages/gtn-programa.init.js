renderizarLista();
renderizarCheckboxCheck();

// CONFIGURACION DE LISTADO
const perPage = 8;
let formEdit = false;
const settingsDatatable = {
	valueNames: [
		"id_programa",
		"id_escuadron",
		"nu_periodo",
		"no_tipo_instruccion",
		"no_nombre",
		"tx_descripcion",
		"tx_finalidad",
		"fl_bloqueado",
		"fl_estado",
		"tx_descripcion_escuadron",
		"id_unidad"
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


let listadoProgramas = new List("listadoProgramas", settingsDatatable).on("updated", function(list) {
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
		const lstProgramas = await fetchListarProgramasPorIdUnidad();
		lstProgramas.forEach(programa => {
			listadoProgramas.add({
				id_programa: programa.idPrograma,
				id_escuadron: programa.idEscuadron,
				nu_periodo: programa.nuPeriodo,
				no_tipo_instruccion: programa.noTipoInstruccion,
				no_nombre: programa.noNombre,
				tx_descripcion: programa.txDescripcion,
				tx_finalidad: programa.txFinalidad,
				fl_bloqueado: convertirBadgeBloqueado(programa.flBloqueado),
				fl_estado: convertirBadgeEstado(programa.flEstado),
				tx_descripcion_escuadron: programa.txDescripcionEscuadron,
				id_unidad: programa.idUnidad
			});
			refreshCallbacks();
			filtrarInformacion();
		})
		listadoProgramas.remove("id_programa", '<a href="javascript:void(0);" class="fw-medium link-primary">#VZ2101</a>');
	} catch (error) {
		console.error('Error al mostrar los programas:', error);
	}
}

function convertirBadgeEstado(estado) {
	return (estado === 1) ? `<span class="badge badge-soft-success text-uppercase">Activo</span>` : `<span class="badge badge-soft-danger text-uppercase">Inactivo</span>`;
}

function convertirBadgeBloqueado(estado) {
	return (estado === 1) ? `<span class="badge badge-soft-secondary text-uppercase">Permitido</span>` : `<span class="badge badge-soft-danger text-uppercase">Bloqueado</span>`;
}

let idProgramaField = document.getElementById("idPrograma-field"),
	idEscuadronField = document.getElementById("idEscuadron-field"),
	nuPeriodoField = document.getElementById("nuPeriodo-field"),
	noTipoInstruccionField = document.getElementById("noTipoInstruccion-field"),
	noNombreField = document.getElementById("noNombre-field"),

	txDescripcionField = document.getElementById("txDescripcion-field"),
	txFinalidadField = document.getElementById("txFinalidad-field"),

	flBloqueadoField = document.getElementById("flBloqueado-field"),

	idUnidadField = document.getElementById("idUnidad-field");


let forms = document.querySelectorAll('.tablelist-form')
Array.prototype.slice.call(forms).forEach(function(form) {
	form.addEventListener('submit', async function(event) {
		if (nuPeriodoField.value == "" || nuPeriodoField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Periodo")
			return;
		}
		if (idEscuadronField.value == "" || idEscuadronField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Escuadrón")
			return;
		}
		if (noTipoInstruccionField.value == "" || noTipoInstruccionField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Tipo de Instrucción")
			return;
		}
		if (!form.checkValidity()) {
			event.preventDefault();
			event.stopPropagation();
			form.classList.add('was-validated');
		} else {
			event.preventDefault();

			if (noTipoInstruccionField.value !== "" &&
				idEscuadronField.value !== "" &&
				nuPeriodoField.value !== "" &&
				noNombreField.value !== "" && !formEdit) {

				let programa = {
					idEscuadron: idEscuadronField.value,
					nuPeriodo: nuPeriodoField.value,
					noTipoInstruccion: noTipoInstruccionField.value,
					noNombre: noNombreField.value,
					txDescripcion: txDescripcionField.value,
					txFinalidad: txFinalidadField.value,
				};

				try {
					const response = await fetchGuardarPrograma(programa);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al insertar el programa:', error);
				}

			} else if (
				noTipoInstruccionField.value !== "" &&
				idEscuadronField.value !== "" &&
				nuPeriodoField.value !== "" &&
				noNombreField.value !== "" && formEdit
			) {
				if (flBloqueadoField.value == "" || flBloqueadoField.value == null) {
					event.preventDefault();
					event.stopPropagation();
					notification('info', "Seleccione Permiso")
					return;
				}
				const idPrograma = parseInt(idProgramaField.value);

				let programa = {
					idPrograma: idPrograma,
					noTipoInstruccion: noTipoInstruccionField.value,
					noNombre: noNombreField.value,
					txDescripcion: txDescripcionField.value,
					txFinalidad: txFinalidadField.value,
					flBloqueado: flBloqueadoField.value == "Permitido" ? 1 : 0
				};

				try {
					const response = await fetchActualizarPrograma(programa);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al actualizar el programa:', error);
				}
			}
		}
	}, false)
})

let bloqueoVal = new Choices(flBloqueadoField);
let tipoInstruccionVal = new Choices(noTipoInstruccionField);
let escuadronVal = new Choices(idEscuadronField);
let periodoVal = new Choices(nuPeriodoField);

function filtrarInformacion() {

	var isEscuadron = document.getElementById("cboBusquedaEscuadron").value;
	var isTipo = document.getElementById("cboTipoInstruccion").value;
	var isPeriodo = document.getElementById("cboBusquedaPeriodo").value;

	listadoProgramas.filter(function(data) {
		matchDataEscuadron = new DOMParser().parseFromString(data.values().id_escuadron, 'text/html');
		var escuadron = matchDataEscuadron.body.textContent.trim()

		matchDataTipo = new DOMParser().parseFromString(data.values().no_tipo_instruccion, 'text/html');
		var tipoInstruccion = matchDataTipo.body.textContent.trim();

		matchDataStatus = new DOMParser().parseFromString(data.values().nu_periodo, 'text/html');
		var periodo = matchDataStatus.body.textContent.trim()

		var escuadronFilter = false;
		var tipoFilter = false;
		var periodoFilter = false;

		if (isEscuadron == "" || isEscuadron == null || isEscuadron == '') {
			escuadronFilter = true;
		} else {
			escuadronFilter = escuadron == isEscuadron;
		}

		if (tipoInstruccion == 'all' || isTipo == 'all' || isTipo == '') {
			tipoFilter = true;
		} else {
			tipoFilter = tipoInstruccion == isTipo;
		}

		if (periodo == 'all' || isPeriodo == 'all') {
			periodoFilter = true;
		} else {
			periodoFilter = periodo == isPeriodo;
		}

		return escuadronFilter && tipoFilter && periodoFilter;
	});
	listadoProgramas.update();
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
	idProgramaField.value = "";

	escuadronVal.destroy();
	escuadronVal = new Choices(idEscuadronField, {
		searchEnabled: false
	});

	tipoInstruccionVal.destroy();
	tipoInstruccionVal = new Choices(noTipoInstruccionField, {
		searchEnabled: false
	});

	noNombreField.value = "";
	txDescripcionField.value = "";
	txFinalidadField.value = "";

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

	var itemValues = listadoProgramas.get({
		id_programa: itemId,
	});

	Array.from(itemValues).forEach(function(x) {
		var selectedid = x._values.id_programa;
		if (selectedid == itemId) {
			formEdit = true;
			idProgramaField.value = selectedid;

			var tipoInstruccion = x._values.no_tipo_instruccion;
			if (tipoInstruccionVal) tipoInstruccionVal.destroy();
			tipoInstruccionVal = new Choices(noTipoInstruccionField, {
				searchEnabled: false
			});
			tipoInstruccionVal.setChoiceByValue(tipoInstruccion);

			var escuadron = x._values.id_escuadron;
			if (escuadronVal) escuadronVal.destroy();
			escuadronVal = new Choices(idEscuadronField, {
				searchEnabled: false
			});
			escuadronVal.setChoiceByValue(escuadron.toString());

			var periodo = x._values.nu_periodo;
			if (periodoVal) periodoVal.destroy();
			periodoVal = new Choices(nuPeriodoField, {
				searchEnabled: false
			});
			periodoVal.setChoiceByValue(periodo.toString());

			noNombreField.value = x._values.no_nombre;
			txDescripcionField.value = x._values.tx_descripcion;
			txFinalidadField.value = x._values.tx_finalidad;

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
	var itemValues = listadoProgramas.get({
		id_programa: itemId,
	});

	Array.from(itemValues).forEach(function(x) {

		var isdeleteid = x._values.id_programa;

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
					console.error('Error al eliminar el programa:', error);
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
			var id = trNode.querySelector("td.id_programa").textContent;
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
					console.error('Error al eliminar los programas:', error);
				}
			}
		});
	} else {
		console.error('Error en el proceso de eliminar los programas:', error);
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

document.getElementById("mdPrograma").addEventListener("show.bs.modal", function(e) {
	if (e.relatedTarget.classList.contains("edit-item-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Editar Programa";
		document.getElementById('content-flBloqueado-field').style.display = 'block';
		document.getElementById("mdPrograma").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Actualizar";
	} else if (e.relatedTarget.classList.contains("add-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Agregar Programa";
		document.getElementById('content-flBloqueado-field').style.display = 'none';
		document.getElementById("mdPrograma").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Agregar Programa";
	} else {
		document.getElementById("mdTitulo").innerHTML = "Listar Programa";
		document.getElementById("mdPrograma").querySelector(".modal-footer").style.display = "none";
	}
});

document.getElementById("mdPrograma").addEventListener("hidden.bs.modal", function() {
	limpiarCampos();
});

document.querySelector("#listadoProgramas").addEventListener("click", function() {
	renderizarCheckboxCheck();
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

async function fetchListarProgramasPorIdUnidad() {
	try {
		const response = await fetch('/programas/listarPorIdUnidad');
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de programas: ${response.status}`);
		}
		listadoProgramas.clear();
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de programas:', error);
		throw error;
	}
}

async function fetchGuardarPrograma(programa) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(programa)
		}
		const response = await fetch(`/programas/guardar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido insertar el programa: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al insertar el programa:', error);
		throw error;
	}
}

async function fetchActualizarPrograma(programa) {
	try {
		const configHttp = {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(programa)
		}
		const response = await fetch(`/programas/actualizar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido actualizar el programa: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al actualizar el programa:', error);
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
		const response = await fetch(`/programas/eliminar?id=${id}`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar el programa: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar el programa:', error);
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
		const response = await fetch(`/programas/eliminar-multiple`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar los programas: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar el programa:', error);
		throw error;
	}
}