renderizarLista();
renderizarCheckboxCheck();

// CONFIGURACION DE LISTADO
const perPage = 8;
let formEdit = false;
const settingsDatatable = {
	valueNames: [
		"id_sub_fase",
		"id_banco_sub_fase",
		"id_fase",
		"nu_total_hora",
		"nu_total_mision",
		"nu_total_maniobra",
		"co_codigo",

		"tx_finalidad",
		"fl_bloqueado",
		"fl_estado",
		"tx_descripcion_sub_fase",
		"tx_descripcion_fase",
		"id_programa",
		"tx_descripcion_programa",
		"id_escuadron",
		"id_banco_fase"
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


let listadoSubFases = new List("listadoSubFases", settingsDatatable).on("updated", function(list) {
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
		const lstSubFases = await fetchListarSubFasesPorIdUnidad();
		lstSubFases.forEach(subFase => {
			listadoSubFases.add({
				id_sub_fase: subFase.idSubFase,
				id_banco_sub_fase: subFase.idBancoSubFase,
				id_fase: subFase.idFase,
				nu_total_hora: subFase.nuTotalHora,
				nu_total_mision: subFase.nuTotalMision,
				nu_total_maniobra: subFase.nuTotalManiobra,
				co_codigo: subFase.coCodigo,
				tx_finalidad: subFase.txFinalidad,
				fl_bloqueado: convertirBadgeBloqueado(subFase.flBloqueado),
				fl_estado: convertirBadgeEstado(subFase.flEstado),
				tx_descripcion_sub_fase: subFase.txDescripcionSubFase,
				tx_descripcion_fase: subFase.txDescripcionFase,
				id_programa: subFase.idPrograma,
				tx_descripcion_programa: subFase.txDescripcionPrograma,
				id_escuadron: subFase.idEscuadron,
				id_banco_fase: subFase.idBancoFase
			});
			refreshCallbacks();
			filtrarInformacion();
		})
		listadoSubFases.remove("id_sub_fase", '<a href="javascript:void(0);" class="fw-medium link-primary">#VZ2101</a>');
	} catch (error) {
		console.error('Error al mostrar las sub fases:', error);
	}
}

function convertirBadgeEstado(estado) {
	return (estado === 1) ? `<span class="badge badge-soft-success text-uppercase">Activo</span>` : `<span class="badge badge-soft-danger text-uppercase">Inactivo</span>`;
}

function convertirBadgeBloqueado(estado) {
	return (estado === 1) ? `<span class="badge badge-soft-secondary text-uppercase">Permitido</span>` : `<span class="badge badge-soft-danger text-uppercase">Bloqueado</span>`;
}


let idSubFaseField = document.getElementById("idSubFase-field"),
	idBancoSubFaseField = document.getElementById("idBancoSubFase-field"),
	idBancoFaseField = document.getElementById("idBancoFase-field"),
	idFaseField = document.getElementById("idFase-field"),
	nuTotalHoraField = document.getElementById("nuTotalHora-field"),
	nuTotalMisionField = document.getElementById("nuTotalMision-field"),
	nuTotalManiobraField = document.getElementById("nuTotalManiobra-field"),
	coCodigoField = document.getElementById("coCodigo-field"),

	txFinalidadField = document.getElementById("txFinalidad-field"),

	flBloqueadoField = document.getElementById("flBloqueado-field"),


	idProgramaField = document.getElementById("idPrograma-field"),
	idEscuadronField = document.getElementById("idEscuadron-field"),

	cboBusquedaEscuadron = document.getElementById("cboBusquedaEscuadron"),
	cboBusquedaPrograma = document.getElementById("cboBusquedaPrograma");

let cboBusquedaProgramaVal = new Choices(cboBusquedaPrograma);
let forms = document.querySelectorAll('.tablelist-form')
Array.prototype.slice.call(forms).forEach(function(form) {
	form.addEventListener('submit', async function(event) {
		if (idEscuadronField.value == "" || idEscuadronField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Escuadrón")
			return;
		}
		if (idProgramaField.value == "" || idProgramaField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Programa")
			return;
		}
		if (idBancoFaseField.value == "" || idBancoFaseField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Fase")
			return;
		}
		if (idBancoSubFaseField.value == "" || idBancoSubFaseField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Banco Fase")
			return;
		}
		if (!form.checkValidity()) {
			event.preventDefault();
			event.stopPropagation();
			form.classList.add('was-validated');
		} else {
			event.preventDefault();

			if (idEscuadronField.value !== "" &&
				idProgramaField.value !== "" &&
				idBancoFaseField.value !== "" &&
				idBancoSubFaseField.value !== "" &&
				nuTotalHoraField.value !== "" &&
				nuTotalMisionField.value !== "" &&
				nuTotalManiobraField.value !== "" &&
				coCodigoField.value !== "" && !formEdit) {

				let subFase = {
					idEscuadron: idEscuadronField.value,
					idPrograma: idProgramaField.value,
					idBancoFase: idBancoFaseField.value,
					idBancoSubFase: idBancoSubFaseField.value,
					nuTotalHora: nuTotalHoraField.value,
					nuTotalMision: nuTotalMisionField.value,
					nuTotalManiobra: nuTotalManiobraField.value,
					coCodigo: coCodigoField.value,
					txFinalidad: txFinalidadField.value
				};

				try {
					const response = await fetchGuardarSubFase(subFase);
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

			} else if (idEscuadronField.value !== "" &&
				idProgramaField.value !== "" &&
				idFaseField.value !== "" &&
				idBancoFaseField.value !== "" &&
				idBancoSubFaseField.value !== "" &&
				nuTotalHoraField.value !== "" &&
				nuTotalMisionField.value !== "" &&
				nuTotalManiobraField.value !== "" &&
				coCodigoField.value !== "" && formEdit
			) {
				if (flBloqueadoField.value == "" || flBloqueadoField.value == null) {
					event.preventDefault();
					event.stopPropagation();
					notification('info', "Seleccione Permiso")
					return;
				}
				let idSubFase = parseInt(idSubFaseField.value);

				let subFase = {
					idSubFase: idSubFase,
					idFase: idFaseField.value,
					nuTotalHora: nuTotalHoraField.value,
					nuTotalMision: nuTotalMisionField.value,
					nuTotalManiobra: nuTotalManiobraField.value,
					coCodigo: coCodigoField.value,
					txFinalidad: txFinalidadField.value,
					flBloqueado: flBloqueadoField.value == "Permitido" ? 1 : 0
				};

				try {
					const response = await fetchActualizarSubFase(subFase);
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

cboBusquedaEscuadron.addEventListener("change", async function() {
	let idEscuadron = cboBusquedaEscuadron.value;
	try {
		const response = await fetchListarProgramasPorIdEscuadron(idEscuadron);
		if (cboBusquedaProgramaVal) cboBusquedaProgramaVal.destroy();
		cboBusquedaProgramaVal = new Choices(cboBusquedaPrograma, {
			searchEnabled: false
		});
		const groupedChoices = llenarCboIdPrograma(response);
		cboBusquedaProgramaVal.setChoices(groupedChoices, 'value', 'label', false);
	} catch (error) {
		console.error('Error al listar Programas:', error);
	}
});

function llenarCboIdPrograma(lstProgramas, idPrograma) {
	const groupedData = {};

	lstProgramas.forEach(programa => {
		const tipoInstruccion = programa.noTipoInstruccion;
		if (!groupedData[tipoInstruccion]) {
			groupedData[tipoInstruccion] = {
				label: tipoInstruccion,
				id: programa.idPrograma,
				disabled: false,
				choices: []
			};
		}
		groupedData[tipoInstruccion].choices.push({
			label: programa.noNombre,
			value: programa.idPrograma,
			selected: (idPrograma) ? programa.idPrograma == idPrograma : false,
			disabled: false
		});
	});

	const groupedChoices = Object.values(groupedData);
	return groupedChoices;
}

let bancoFaseVal = new Choices(idBancoFaseField);
let bancoSubFaseVal = new Choices(idBancoSubFaseField);
let programaVal = new Choices(idProgramaField);
let bloqueoVal = new Choices(flBloqueadoField);
let escuadronVal = new Choices(idEscuadronField);

idEscuadronField.addEventListener("change", async function() {
	let idEscuadron = idEscuadronField.value;
	try {
		const response = await fetchListarProgramasPorIdEscuadron(idEscuadron);
		if (programaVal) programaVal.destroy();
		programaVal = new Choices(idProgramaField, {
			searchEnabled: false
		});
		const groupedChoices = llenarCboIdPrograma(response);
		programaVal.setChoices(groupedChoices, 'value', 'label', false);
	} catch (error) {
		console.error('Error al listar Programas:', error);
	}
});

idProgramaField.addEventListener("change", async function() {
	var idPrograma = idProgramaField.value;
	try {
		const response = await fetchListarFasesPorIdPrograma(idPrograma);
		if (bancoFaseVal) bancoFaseVal.destroy();
		bancoFaseVal = new Choices(idBancoFaseField, {
			searchEnabled: false
		});
		const choices = llenarCboIdFase(response);
		bancoFaseVal.setChoices(choices, 'value', 'label', false);

	} catch (error) {
		console.error('Error al listar fases:', error);
	}
});


function llenarCboIdFase(lstFases, idFase) {
	const choices = [];

	lstFases.forEach(fase => { 
		choices.push({
			value: fase.idBancoFase.toString(),
			label: fase.txDescripcionFase,
			selected: (idFase) ? fase.idFase == idFase : false,
			disabled: false
		});
	});
	return choices;
}

function filtrarInformacion() {

	var isEscuadron = document.getElementById("cboBusquedaEscuadron").value;
	var isPrograma = document.getElementById("cboBusquedaPrograma").value;

	listadoSubFases.filter(function(data) {
		matchDataEscuadron = new DOMParser().parseFromString(data.values().id_escuadron, 'text/html');
		var escuadron = matchDataEscuadron.body.textContent.trim()

		matchDataPrograma = new DOMParser().parseFromString(data.values().id_programa, 'text/html');
		var programa = matchDataPrograma.body.textContent.trim();

		var escuadronFilter = false;
		var programaFilter = false;

		if (isEscuadron == "" || isEscuadron == null || isEscuadron == "") {
			escuadronFilter = true;
		} else {
			escuadronFilter = escuadron == isEscuadron;
		}

		if (isPrograma == "" || isPrograma == null || isPrograma == "") {
			programaFilter = true;
		} else {
			programaFilter = programa == isPrograma;
		}

		return programaFilter && escuadronFilter;
	});
	listadoSubFases.update();
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
	idSubFaseField.value = "";
	idFaseField.value = "";

	escuadronVal.destroy();
	escuadronVal.enable();
	escuadronVal = new Choices(idEscuadronField, {
		searchEnabled: false
	});

	programaVal.destroy();
	programaVal.enable();
	programaVal = new Choices(idProgramaField, {
		searchEnabled: false
	});

	bancoFaseVal.destroy();
	bancoFaseVal.enable();
	bancoFaseVal = new Choices(idBancoFaseField, {
		searchEnabled: false
	});

	bancoSubFaseVal.destroy();
	bancoSubFaseVal.enable();
	bancoSubFaseVal = new Choices(idBancoSubFaseField, {
		searchEnabled: true
	});

	nuTotalHoraField.value = "";
	nuTotalMisionField.value = "";
	nuTotalManiobraField.value = "";
	coCodigoField.value = "";
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

	var itemValues = listadoSubFases.get({
		id_sub_fase: itemId,
	});

	Array.from(itemValues).forEach(async function(x) {
		var selectedid = x._values.id_sub_fase;
		if (selectedid == itemId) {
			formEdit = true;
			idSubFaseField.value = selectedid;
			idFaseField.value = x._values.id_fase;

			if (escuadronVal) escuadronVal.destroy();
			escuadronVal = new Choices(idEscuadronField, {
				searchEnabled: false,
			});
			escuadronVal.setChoiceByValue(x._values.id_escuadron.toString());
			escuadronVal.disable();

			try {
				const response = await fetchListarFasesPorIdPrograma(x._values.id_programa.toString());
				if (bancoFaseVal) bancoFaseVal.destroy();
				bancoFaseVal = new Choices(idBancoFaseField, {
					searchEnabled: false
				});

				const choices = llenarCboIdFase(response, x._values.id_fase);
				bancoFaseVal.setChoices(choices, 'value', 'label', false);
				if (x._values.id_banco_fase) {
					bancoFaseVal.disable();
				} else {
					bancoFaseVal.enable();
				}
			} catch (error) {
				console.error('Error al cargar los fases:', error);
			}


			if (bancoSubFaseVal) bancoSubFaseVal.destroy();
			bancoSubFaseVal = new Choices(idBancoSubFaseField, {
				searchEnabled: true
			});

			bancoSubFaseVal.setChoiceByValue(x._values.id_banco_sub_fase.toString());
			bancoSubFaseVal.disable();

			try {
				const response = await fetchListarProgramasPorIdEscuadron(x._values.id_escuadron.toString());
				if (programaVal) programaVal.destroy();
				programaVal = new Choices(idProgramaField, {
					searchEnabled: false
				});

				const groupedChoices = llenarCboIdPrograma(response, x._values.id_programa);
				programaVal.setChoices(groupedChoices, 'value', 'label', false);

				if (x._values.id_programa) {
					programaVal.disable();
				} else {
					programaVal.enable();
				}

			} catch (error) {
				console.error('Error al cargar los programas:', error);
			}

			nuTotalHoraField.value = x._values.nu_total_hora;
			nuTotalMisionField.value = x._values.nu_total_mision;
			nuTotalManiobraField.value = x._values.nu_total_maniobra;
			coCodigoField.value = x._values.co_codigo;
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
	var itemValues = listadoSubFases.get({
		id_sub_fase: itemId,
	});

	Array.from(itemValues).forEach(function(x) {
		var isdeleteid = x._values.id_sub_fase;

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
			var id = trNode.querySelector("td.id_sub_fase").textContent;
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

// Agregar un event listener para el botón "Siguiente"
document.querySelector(".pagination-next").addEventListener("click", handleNextPageClick);

// Agregar un event listener para el botón "Anterior"
document.querySelector(".pagination-prev").addEventListener("click", handlePrevPageClick);

document.getElementById("mdSubFase").addEventListener("show.bs.modal", function(e) {
	if (e.relatedTarget.classList.contains("edit-item-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Editar Sub Fase";
		document.getElementById('content-flBloqueado-field').style.display = 'block';
		document.getElementById("mdSubFase").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Actualizar";
	} else if (e.relatedTarget.classList.contains("add-btn")) {
		document.getElementById("mdTitulo").innerHTML = "Agregar Sub Fase";
		document.getElementById('content-flBloqueado-field').style.display = 'none';
		document.getElementById("mdSubFase").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Agregar Sub Fase";
	} else {
		document.getElementById("mdTitulo").innerHTML = "Listar Sub Fase";
		document.getElementById("mdSubFase").querySelector(".modal-footer").style.display = "none";
	}
});

document.getElementById("mdSubFase").addEventListener("hidden.bs.modal", function() {
	limpiarCampos();
});

document.querySelector("#listadoSubFases").addEventListener("click", function() {
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

async function fetchListarSubFasesPorIdUnidad() {
	try {
		const response = await fetch(`/subFases/listarPorIdUnidad`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de sub fases: ${response.status}`);
		}
		listadoSubFases.clear();
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de sub fases:', error);
		throw error;
	}
}

async function fetchListarFasesPorIdPrograma(idPrograma) {
	try {
		const response = await fetch(`/fases/listarPorIdPrograma?idPrograma=${idPrograma}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de fases: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de fases:', error);
		throw error;
	}
}

async function fetchListarProgramasPorIdEscuadron(idEscuadron) {
	try {
		const response = await fetch(`/programas/listarPorIdEscuadron?idEscuadron=${idEscuadron}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de fases: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de fases:', error);
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
		const response = await fetch(`/subFases/guardar`, configHttp);

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
		const response = await fetch(`/subFases/actualizar`, configHttp);

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
		const response = await fetch(`/subFases/eliminar?id=${id}`, configHttp);

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
		const response = await fetch(`/subFases/eliminar-multiple`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido eliminar las sub fases: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al eliminar la sub fase:', error);
		throw error;
	}
}