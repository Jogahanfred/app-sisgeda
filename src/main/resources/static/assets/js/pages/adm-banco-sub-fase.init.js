renderizarLista();
renderizarCheckboxCheck();

const perPage = 8;
let formEdit = false;

// CONFIGURACION DE LISTADO
const settingsDatatable = {
	valueNames: [
		"id_banco_sub_fase",
		"no_nombre",
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


let listadoBancoSubFases = new List("listadoBancoSubFases", settingsDatatable).on("updated", function(list) {
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
		const lstBancoSubFases = await fetchListarBancoSubFases();
		lstBancoSubFases.forEach(banco => {
			listadoBancoSubFases.add({
				id_banco_sub_fase: banco.idBancoSubFase,
				no_nombre: banco.noNombre,
				fl_estado: convertirBadgeEstado(banco.flEstado)
			});
			listadoBancoSubFases.sort('id_banco_sub_fase', { order: "desc" });
			refreshCallbacks();
			filtrarInformacion();
		})
		listadoBancoSubFases.remove("id_banco_sub_fase", '<a href="javascript:void(0);" class="fw-medium link-primary">#VZ2101</a>');
	} catch (error) {
		console.error('Error al mostrar el banco de sub fases:', error);
	}

}

function convertirBadgeEstado(estado) {
	return (estado === 1) ? `<span class="badge badge-soft-success text-uppercase">Activo</span>` : `<span class="badge badge-soft-danger text-uppercase">Inactivo</span>`;
}

let idBancoSubFaseField = document.getElementById("idBancoSubFase-field"),
	noNombreField = document.getElementById("noNombre-field");

let forms = document.querySelectorAll('.tablelist-form')
Array.prototype.slice.call(forms).forEach(function(form) {
	form.addEventListener('submit', async function(event) {
		if (!form.checkValidity()) {
			event.preventDefault();
			event.stopPropagation();
			form.classList.add('was-validated');
		} else {
			event.preventDefault();
			if (noNombreField.value !== "" && !formEdit) {

				let bancoSubFase = {
					noNombre: noNombreField.value,
				};

				try {
					const response = await fetchGuardarSubFase(bancoSubFase);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al insertar la sub fase:', error);
				}


			} else if (noNombreField.value !== "" && formEdit) {
				var idBancoSubFase = parseInt(idBancoSubFaseField.value);

				let bancoSubFase = {
					idBancoSubFase: idBancoSubFase,
					noNombre: noNombreField.value
				};

				try {
					const response = await fetchActualizarSubFase(bancoSubFase);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						limpiarCampos();
						refreshCallbacks();
						renderizarLista();
					}
				} catch (error) {
					console.error('Error al actualizar la sub fase:', error);
				}
			}
		}
	}, false)
})

function filtrarInformacion() {
	var isstatus = document.getElementById("cboEstado").value;

	listadoBancoSubFases.filter(function(data) {
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
	listadoBancoSubFases.update();
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
	noNombreField.value = "";
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
	var itemValues = listadoBancoSubFases.get({
		id_banco_sub_fase: itemId,
	});

	Array.from(itemValues).forEach(function(x) {
		var selectedid = x._values.id_banco_sub_fase;
		if (selectedid == itemId) {
			formEdit = true;
			idBancoSubFaseField.value = selectedid;
			noNombreField.value = x._values.no_nombre;
		}
	});
}

function handleDeleteButtonClick(e) {
	e.target.closest("tr").children[1].innerText;
	itemId = e.target.closest("tr").children[1].innerText;
	var itemValues = listadoBancoSubFases.get({
		id_banco_sub_fase: itemId,
	});

	Array.from(itemValues).forEach(function(x) {
		var isdeleteid = x._values.id_banco_sub_fase;

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
					console.error('Error al eliminar la sub fase:', error);
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
			var id = trNode.querySelector("td.id_banco_sub_fase").textContent;
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
					console.error('Error al eliminar las sub fases:', error);
				}

			}
		});
	} else {
		console.error('Error en el proceso de eliminar las sub fases:', error);
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

document.querySelector(".pagination-next").addEventListener("click", handleNextPageClick);

document.querySelector(".pagination-prev").addEventListener("click", handlePrevPageClick);


//Valida antes que se abra el modal de acuerdo a: Editar y Agregar, sino es una LISTA y oculta
document.getElementById("mdBancoSubFase").addEventListener("show.bs.modal", function(e) {
	if (e.relatedTarget.classList.contains("edit-item-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Editar Sub Fase";
		document.getElementById("mdBancoSubFase").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Actualizar";
	} else if (e.relatedTarget.classList.contains("add-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Agregar Sub Fase";
		document.getElementById("mdBancoSubFase").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Agregar Sub Fase";
	} else {
		document.getElementById("mdTitulo").innerHTML = "Listar Sub Fase";
		document.getElementById("mdBancoSubFase").querySelector(".modal-footer").style.display = "none";
	}
});

document.getElementById("mdBancoSubFase").addEventListener("hidden.bs.modal", function() {
	limpiarCampos(); 
});


document.querySelector("#listadoBancoSubFases").addEventListener("click", function() {
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

async function fetchListarBancoSubFases() {
	try {
		const response = await fetch('/bancoSubFases/listar');
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista del banco de sub fases: ${response.status}`);
		}
		listadoBancoSubFases.clear();
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista del banco de sub fases:', error);
		throw error;
	}
}


async function fetchGuardarSubFase(subFase) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(subFase)
		}
		const response = await fetch(`/bancoSubFases/guardar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido insertar la sub fase: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al insertar la sub fase:', error);
		throw error;
	}
}

async function fetchActualizarSubFase(subFase) {
	try {
		const configHttp = {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(subFase)
		}
		const response = await fetch(`/bancoSubFases/actualizar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido actualizar la sub fase: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al actualizar la sub fase:', error);
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
		const response = await fetch(`/bancoSubFases/eliminar?id=${id}`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar la sub fase: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar la sub fase:', error);
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
		const response = await fetch(`/bancoSubFases/eliminar-multiple`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar las sub fases: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar las sub fases:', error);
		throw error;
	}
}
