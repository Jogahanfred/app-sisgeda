renderizarLista();
renderizarCheckboxCheck();

// CONFIGURACION DE LISTADO
const perPage = 8;
let formEdit = false;
const settingsDatatable = {
	valueNames: [
		"id_flota",
		"no_tipo_flota",
		"co_codigo",
		"no_nombre",
		"fl_estado",
		"fl_bloqueado",
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


let listadoFlotas = new List("listadoFlotas", settingsDatatable).on("updated", function(list) {
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
	console.log(perPage)
	console.log(list.matchingItems.length)
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
		const lstFlotas = await fetchListarFlotas();
		lstFlotas.forEach(flota => {
			listadoFlotas.add({
				id_flota: flota.idFlota,
				no_tipo_flota: flota.noTipoFlota,
				co_codigo: flota.coCodigo,
				no_nombre: flota.noNombre,
				fl_bloqueado: convertirBadgeBloqueado(flota.flBloqueado),
				fl_estado: convertirBadgeEstado(flota.flEstado)
			});
			listadoFlotas.sort('id_flota', { order: "desc" });
			refreshCallbacks();
			filtrarInformacion();
		})
		listadoFlotas.remove("id_flota", '<a href="javascript:void(0);" class="fw-medium link-primary">#VZ2101</a>');
	} catch (error) {
		console.error('Error al mostrar las flotas:', error);
	}
}

function convertirBadgeEstado(estado) {
	return (estado === 1) ? `<span class="badge badge-soft-success text-uppercase">Activo</span>` : `<span class="badge badge-soft-danger text-uppercase">Inactivo</span>`;
}

function convertirBadgeBloqueado(estado) {
	return (estado === 1) ? `<span class="badge badge-soft-secondary text-uppercase">Permitido</span>` : `<span class="badge badge-soft-danger text-uppercase">Bloqueado</span>`;
}


let idFlotaField = document.getElementById("idFlota-field"),
	noTipoFlotaField = document.getElementById("noTipoFlota-field"),
	coCodigoField = document.getElementById("coCodigo-field"),
	noNombreField = document.getElementById("noNombre-field"),
	flBloqueadoField = document.getElementById("flBloqueado-field"),
	flEstadoField = document.getElementById("flEstado-field");


let bloqueoVal = new Choices(flBloqueadoField);
let tipoFlotaVal = new Choices(noTipoFlotaField);

let forms = document.querySelectorAll('.tablelist-form')
Array.prototype.slice.call(forms).forEach(function(form) {
	form.addEventListener('submit', async function(event) {
		if (noTipoFlotaField.value == "" || noTipoFlotaField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Tipo de Flota")
			return;
		}
		if (!form.checkValidity()) {
			event.preventDefault();
			event.stopPropagation();
			form.classList.add('was-validated');
		} else { 
			event.preventDefault();
			if (noTipoFlotaField.value !== "" &&
				coCodigoField.value !== "" &&
				noNombreField.value !== "" && !formEdit) {

				let flota = {
					noTipoFlota: noTipoFlotaField.value,
					coCodigo: coCodigoField.value,
					noNombre: noNombreField.value,
				};

				try {
					const response = await fetchGuardarFlota(flota);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al insertar la flota:', error);
				}

			} else if (
				noTipoFlotaField.value !== "" &&
				coCodigoField.value !== "" &&
				noNombreField.value !== "" && formEdit
			) {
				if (flBloqueadoField.value == "" || flBloqueadoField.value == null) {
					event.preventDefault();
					event.stopPropagation();
					notification('info', "Seleccione Permiso")
					return;
				}
				let idFlota = parseInt(idFlotaField.value);

				let flota = {
					idFlota: idFlota,
					noTipoFlota: noTipoFlotaField.value,
					coCodigo: coCodigoField.value,
					noNombre: noNombreField.value,
					flBloqueado: flBloqueadoField.value == "Permitido" ? 1 : 0
				};


				try {
					const response = await fetchActualizarFlota(flota);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al actualizar la flota:', error);
				}
			}
		}
	}, false)
})



function filtrarInformacion() {

	var isstatus = document.getElementById("cboEstado").value;
	var isTipo = document.getElementById("cboTipoFlota").value;


	listadoFlotas.filter(function(data) {
		matchDataStatus = new DOMParser().parseFromString(data.values().fl_estado, 'text/html');
		var status = matchDataStatus.body.textContent.trim()

		matchDataTipo = new DOMParser().parseFromString(data.values().no_tipo_flota, 'text/html');
		var tipoFlota = matchDataTipo.body.textContent.trim();

		var statusFilter = false;
		var tipoFilter = false;

		if (status == 'all' || isstatus == 'all' || isstatus == '') {
			statusFilter = true;
		} else {
			statusFilter = status == isstatus;
		}

		if (tipoFlota == 'all' || isTipo == 'all' || isTipo == '') {
			tipoFilter = true;
		} else {
			tipoFilter = tipoFlota == isTipo;
		}

		return statusFilter && tipoFilter;
	});
	listadoFlotas.update();
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
	idFlotaField.value = "";
	noTipoFlotaField.value = "";
	coCodigoField.value = "";
	noNombreField.value = "";

	tipoFlotaVal.destroy();
	tipoFlotaVal = new Choices(noTipoFlotaField, {
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
	var itemValues = listadoFlotas.get({
		id_flota: itemId,
	});

	Array.from(itemValues).forEach(function(x) {
		var selectedid = x._values.id_flota;
		if (selectedid == itemId) {
			formEdit = true;
			idFlotaField.value = selectedid;

			var tipoFlota = x._values.no_tipo_flota;
			if (tipoFlotaVal) tipoFlotaVal.destroy();
			tipoFlotaVal = new Choices(noTipoFlotaField, {
				searchEnabled: false
			});
			tipoFlotaVal.setChoiceByValue(tipoFlota);

			coCodigoField.value = x._values.co_codigo;
			noNombreField.value = x._values.no_nombre;

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
	var itemValues = listadoFlotas.get({
		id_flota: itemId,
	});

	Array.from(itemValues).forEach(function(x) {
		var isdeleteid = x._values.id_flota;

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
					console.error('Error al eliminar la flota:', error);
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
			var id = trNode.querySelector("td.id_flota").textContent;
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
					console.error('Error al eliminar las flotas:', error);
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

//Valida antes que se abra el modal de acuerdo a: Editar y Agregar, sino es una LISTA y oculta
document.getElementById("mdFlota").addEventListener("show.bs.modal", function(e) {
	if (e.relatedTarget.classList.contains("edit-item-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Editar Flota";
		document.getElementById('content-flBloqueado-field').style.display = 'block';
		document.getElementById("mdFlota").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Actualizar";
	} else if (e.relatedTarget.classList.contains("add-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Agregar Flota";
		document.getElementById('content-flBloqueado-field').style.display = 'none';
		document.getElementById("mdFlota").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Agregar Flota";
	} else {
		document.getElementById("mdTitulo").innerHTML = "Listar Flota";
		document.getElementById('content-flBloqueado-field').style.display = 'none';
		document.getElementById("mdFlota").querySelector(".modal-footer").style.display = "none";
	}
});

//Se oculta modal y limpia los campos 
document.getElementById("mdFlota").addEventListener("hidden.bs.modal", function() {
	limpiarCampos(); 
});

//Obtiene el click en FlotaList
document.querySelector("#listadoFlotas").addEventListener("click", function() {
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

async function fetchGuardarFlota(flota) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(flota)
		}
		const response = await fetch(`/flotas/guardar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido insertar la flota: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al insertar la flota:', error);
		throw error;
	}
}

async function fetchActualizarFlota(flota) {
	try {
		const configHttp = {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(flota)
		}
		const response = await fetch(`/flotas/actualizar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido actualizar la flota: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al actualizar la flota:', error);
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
		const response = await fetch(`/flotas/eliminar?id=${id}`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar la flota: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar la flota:', error);
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
		const response = await fetch(`/flotas/eliminar-multiple`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar las flotas: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar la flota:', error);
		throw error;
	}
}

async function fetchListarFlotas() {
	try {
		const response = await fetch('/flotas/listar');
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de flotas: ${response.status}`);
		}
		listadoFlotas.clear();
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de flotas:', error);
		throw error;
	}
}
