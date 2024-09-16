renderizarLista();
let formEdit = false;

function renderizarLista() {
	let table = $('#tblAeronave').DataTable({
		aLengthMenu: [
			[5, 10, 25, 50, 100],
			[5, 10, 25, 50, 100]
		],
		iDisplayLength: parseInt(document.getElementById('cboLength').value, 10) || 5, // Longitud inicial
		bDestroy: true,
		bRetrieve: true,
		bServerSide: true,
		bFiltered: false,
		bSort: false,
		sAjaxSource: '/aeronaves/paginar',
		initComplete: function() {
			document.querySelectorAll('.dataTables_wrapper .row').forEach(function(row) {
				row.querySelectorAll('.col-sm-12.col-md-6').forEach(function(div) {
					div.classList.remove('col-sm-12', 'col-md-6');
					div.classList.add('col-sm-6');
				});
			});
			$('.dataTables_filter').hide();
			$('.dataTables_length').hide();
		},
		fnServerData: function(sSource, aoData, fnCallback, oSettings) {
			oSettings.jqXHR = $.ajax({
				type: 'post',
				url: sSource,
				data: aoData,
				success: function(response) {
					document.getElementById('loader').style.display = 'none';
					document.getElementById('content-tbl-aeronave').style.display = 'block';
					fnCallback(response);
					const celdas = document.querySelectorAll('#tblAeronave tbody tr');
					celdas.forEach(celda => {
						celda.style.verticalAlign = 'middle';
					});
				}
			});
		},
		createdRow: function(row, data, dataIndex) {
			$(row).find('td').eq(0).addClass('text-center');
			$(row).find('td').eq(1).addClass('text-center');
			$(row).find('td').eq(2).addClass('text-center');
			$(row).find('td').eq(3).addClass('text-center');
			$(row).find('td').eq(4).addClass('text-center');
			$(row).find('td').eq(5).addClass('text-center');
		},
		fnDrawCallback: function(oSettings) {
			var table = this.api();
			var data = table.rows().data().toArray();
			if (data.length > 0) {
				var firstObject = data[0];
				renderizarCardAeronaveDetalle(firstObject)
			}

			var startIndex = table.page() * table.page.len();
			table.column(0, { search: 'applied', order: 'applied' })
				.nodes()
				.each(function(cell, i) {
					cell.innerHTML = startIndex + i + 1;
				});

			if (data.length === 0) {
				$('#content-tbl-aeronave').hide();
				$('#noresult').show();
			} else {
				$('#content-tbl-aeronave').show();
				$('#noresult').hide();
			}

		},
		aoColumns: [
			{ mData: null, sWidth: '5%' },
			{ mData: 'txDescripcionUnidad', sWidth: '15%' },

			{ mData: 'txDescripcionFlota', sWidth: '15%' },
			{ mData: 'coNroCola', sWidth: '15%' },
			{
				mData: 'flEstado',
				sWidth: '10%',
				mRender: function(data, type, full) {
					return (data === 1) ? `<span class="badge badge-soft-success text-uppercase">Activo</span>` : `<span class="badge badge-soft-danger text-uppercase">Inactivo</span>`;
				}
			},
			{
				mData: 'idAeronave',
				mRender: function(data, type, full) {
					return '<button type="button" data-bs-toggle="tooltip" data-bs-placement="top" title="Visualizar Información" class="rounded-pill mx-1 btn btn-soft-info waves-effect waves-light material-shadow-none"><i class="ri-eye-line label-icon align-middle fs-19"></i></button>' +
						   '<button type="button" data-bs-toggle="modal" data-bs-toggle="tooltip" data-bs-placement="top" title="Editar Información"  data-bs-target="#mdAeronave" class="rounded-pill mx-1 btn btn-soft-primary waves-effect waves-light material-shadow-none"><i class="ri-pencil-line label-icon align-middle fs-19"></i></button>'+
						   '<button type="button" data-bs-toggle="modal" data-bs-toggle="tooltip" data-bs-placement="top" title="Eliminar Aeronave" href="#mdEliminarRegistro" class="rounded-pill mx-1 btn btn-soft-danger waves-effect waves-light material-shadow-none"><i class="ri-delete-bin-6-line label-icon align-middle fs-19"></i></button>';
				}, sWidth: '25%'
			}

		],
		columnDefs: [{
			targets: 1,
			orderable: false
		}],
		language: {
			emptyTable: "¡No hay datos disponibles!",
			infoFiltered: "(Filtrado de _MAX_ registros en total)",
			lengthMenu: "Mostrar _MENU_ registros",
			info: "Mostrando _START_ - _END_ de _TOTAL_ registros",
			infoEmpty: "Mostrando 0 de 0 registros",
			zeroRecords: "No se encontraron registros coincidentes",
			search: "Buscar:",
			paginate: {
				first: "Primero",
				last: "Último",
				next: "Siguiente",
				previous: "Anterior"
			}
		},
	});
	//Espejo
	$('#input-search').on('keyup', function() {
		table.search(this.value).draw();
	});

	table.on('search.dt', function() {
		$('#input-search').val(table.search());
	});
}

let idAeronaveField = document.getElementById("idAeronave-field"),
	coNroColaField = document.getElementById("coNroCola-field"),
	idFlotaField = document.getElementById("idFlota-field"),
	fotografiaImg = document.getElementById("fotografia-img"),

	fotografiaField = document.getElementById("fotografia-field"),


	fotografiaInput = document.getElementById("fotografia-input");

let idFlotaVal = new Choices(idFlotaField);

function llenarObjetoModal(obj) {
	formEdit = true;

	idAeronaveField.value = obj.idAeronave;
	coNroColaField.value = obj.coNroCola;
	idFlotaVal.setChoiceByValue(obj.idFlota.toString());

	fotografiaImg.src = (obj.txRutaImagen !== null && obj.txRutaImagen !== 'null') ? 'uploads/aeronave/' + obj.txRutaImagen : `assets/images/no_photography.svg`;
	fotografiaField.value = (obj.txRutaImagen !== null && obj.txRutaImagen !== 'null') ? obj.txRutaImagen : '';
}

let forms = document.querySelectorAll('.tablelist-form')
Array.prototype.slice.call(forms).forEach(function(form) {
	form.addEventListener('submit', async function(event) {
		if (idFlotaField.value == "" || idFlotaField.value == null) {
			event.preventDefault();
			event.stopPropagation();
			notification('info', "Seleccione Flota")
			return;
		}
		if (!form.checkValidity()) {
			event.preventDefault();
			event.stopPropagation();
			form.classList.add('was-validated');
		} else {
			event.preventDefault();
			if (idFlotaField.value !== "" &&
			    coNroColaField.value !== "" && !formEdit) {
				
				let formData = new FormData();
				let archivoInput = document.getElementById('fotografia-input');
				
				let aeronave = {
					idFlota: idFlotaField.value,
					coNroCola: coNroColaField.value
				}
				formData.append('aeronave', JSON.stringify(aeronave));
				formData.append('archivo', archivoInput.files[0]);
				
				
				try {
					const response = await fetchGuardarAeronave(formData);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						$('#tblAeronave').DataTable().draw();
						limpiarCampos();
					}
				} catch (error) {
					console.error('Error al insertar la aeronave:', error);
				}
				
			} else if (
				idFlotaField.value !== "" &&
			    coNroColaField.value !== "" && formEdit) {

				let formData = new FormData();
				let archivoInput = document.getElementById('fotografia-input');

				var idAeronave = parseInt(idAeronaveField.value);
				
				let aeronave = {
					idAeronave: idAeronave,
					idFlota: idFlotaField.value,
					coNroCola: coNroColaField.value
				}

				formData.append('aeronave', JSON.stringify(aeronave));

				if (archivoInput.files.length > 0) {
					formData.append('archivo', archivoInput.files[0]);
				} else {
					formData.append('archivo', null);
				}

				try {
					const response = await fetchActualizarAeronave(formData);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						$('#tblAeronave').DataTable().draw();
						limpiarCampos();

					}
				} catch (error) {
					console.error('Error al actualizar el aeronave:', error);
				}

			}
		}
	}, false)
})


function limpiarCampos() {
	idAeronaveField.value = "";
	
	idFlotaVal.destroy();
	idFlotaVal = new Choices(idFlotaField, {
		searchEnabled: false
	});

	coNroColaField.value = ""; 
	
	fotografiaField.value = "";
	fotografiaImg.src = "assets/images/no_photography.svg";

	fotografiaInput.value = "";
	formEdit = false;
}


function renderizarCardAeronaveDetalle(data) {
	const image_src = (data.txRutaImagen != null && data.txRutaImagen != 'null') ? 'uploads/aeronave/' + data.txRutaImagen : `assets/images/no_photography.svg`;

	var bloque = `
            <div class="card-body text-center">
                <div class="position-relative d-inline-block">
                    <div class="">
                        <div class="avatar-title bg-light rounded">	
                            <img src="${image_src}" alt="" class="img-thumbnail" width="300">
                        </div>
                    </div>
                </div>
                <h5 class="mt-3 mb-1">${data.coNroCola}</h5>
                <p class="text-muted">${data.txDescripcionFlota}</p>

            </div> 
        `;
	document.getElementById('view-aeronave').innerHTML = bloque;
}



document.getElementById("mdAeronave").addEventListener("hidden.bs.modal", function() {
	limpiarCampos();
});
 
document.getElementById('cboLength').addEventListener('change', function() {
	const length = parseInt(this.value, 10);
	const table = $('#tblAeronave').DataTable();
	table.page.len(length).draw();
});

document.getElementById("tblAeronave").addEventListener('click', async function(event) {
	const btnVista = event.target.closest('button.btn-soft-info');
	if (btnVista) {
		const row = btnVista.closest('tr');
		const table = $('#tblAeronave').DataTable();
		const rowData = table.row(row).data();
		const dataIdAeronave = rowData['idAeronave'];
		const response = await fetchBuscarAeronavePorId(dataIdAeronave);
		renderizarCardAeronaveDetalle(response)
	} 
});

document.getElementById("mdAeronave").addEventListener("show.bs.modal", async function(e) {
	if (e.relatedTarget.classList.contains('btn-soft-primary')) {
		document.getElementById("mdTitulo").innerHTML = "Editar Aeronave";
		document.getElementById("mdAeronave").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Actualizar";

		const btnEditar = e.relatedTarget;
		const row = btnEditar.closest('tr');
		const table = $('#tblAeronave').DataTable();
		const rowData = table.row(row).data();
		const dataIdAeronave = rowData['idAeronave'];
		const response = await fetchBuscarAeronavePorId(dataIdAeronave);
		llenarObjetoModal(response);
		
	} else if (e.relatedTarget.classList.contains('add-btn')) {
		document.getElementById("mdTitulo").innerHTML = "Agregar Aeronave";
		document.getElementById("mdAeronave").querySelector(".modal-footer").style.display = "block";
		document.getElementById("add-btn").innerHTML = "Agregar"; 
	} else {
		document.getElementById("mdTitulo").innerHTML = "Lista Aeronave";
	}
}); 

// Cargar Imagen
document.querySelector("#fotografia-input").addEventListener("change", function() {
	var preview = document.querySelector("#fotografia-img");
	var file = document.querySelector("#fotografia-input").files[0];
	var reader = new FileReader();
	reader.addEventListener("load", function() {
		preview.src = reader.result;
	}, false);
	if (file) {
		reader.readAsDataURL(file);
	}
});
 
async function fetchBuscarAeronavePorId(idAeronave) {
	try {
		const response = await fetch(`/aeronaves/buscarPorId?idAeronave=${idAeronave}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la aeronave: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la aeronave:', error);
		throw error;
	}
}

async function fetchGuardarAeronave(formData) {
	try {
		const configHttp = {
			method: 'POST',
			body: formData
		}
		const response = await fetch(`/aeronaves/guardar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido insertar la aeronave: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al insertar la aeronave:', error);
		throw error;
	}
}


async function fetchActualizarAeronave(formData) {
	try {
		const configHttp = {
			method: 'POST',
			body: formData
		}
		const response = await fetch(`/aeronaves/actualizar`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido actualizar la aeronave: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al actualizar la aeronave:', error);
		throw error;
	}
}
