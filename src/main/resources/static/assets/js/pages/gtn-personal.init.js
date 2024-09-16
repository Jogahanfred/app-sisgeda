renderizarLista();
let formEdit = false;

function renderizarLista() { 
	let table = $('#tblPersonal').DataTable({
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
		sAjaxSource: '/personal/paginar',
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
					document.getElementById('content-tbl-personal').style.display = 'block';
					fnCallback(response);
					const celdas = document.querySelectorAll('#tblPersonal tbody tr');
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
			$(row).find('td').eq(3).addClass('text-left');
			$(row).find('td').eq(4).addClass('text-center');
			$(row).find('td').eq(5).addClass('text-center');
			$(row).find('td').eq(6).addClass('text-center');
			$(row).find('td').eq(7).addClass('text-center');
		},
		fnDrawCallback: function(oSettings) { 
			var table = this.api();
			var data = table.rows().data().toArray();
			if (data.length > 0) {
				var firstObject = data[0];
				renderizarCardPersonalDetalle(firstObject)
			}

			var startIndex = table.page() * table.page.len();
			table.column(0, { search: 'applied', order: 'applied' })
				.nodes()
				.each(function(cell, i) {
					cell.innerHTML = startIndex + i + 1;
				});

			if (data.length === 0) {
				$('#content-tbl-personal').hide();
				$('#noresult').show();
			} else {
				$('#content-tbl-personal').show();
				$('#noresult').hide();
			}

		},
		aoColumns: [
			{ mData: null, sWidth: '5%' },
			{ mData: 'nsa', sWidth: '7%' },

			{ mData: 'grado', sWidth: '7%' },
			{ mData: 'datos', sWidth: '30%' },
			{ mData: 'dni', sWidth: '10%' },
			{ mData: 'especialidad', sWidth: '15%' },
			{ mData: 'sexo', sWidth: '10%' },
			{
				mData: 'nsa',
				mRender: function(data, type, full) {
					return '<button type="button" data-bs-toggle="tooltip" data-bs-placement="top" title="Visualizar Información" class="rounded-pill mx-1 btn btn-soft-info waves-effect waves-light material-shadow-none"><i class="ri-eye-line label-icon align-middle fs-19"></i></button>' +
						'<button type="button"  data-bs-toggle="tooltip" data-bs-placement="top" title="Subir Fotografía" class="rounded-pill mx-1 btn btn-soft-primary waves-effect waves-light material-shadow-none"><i class="ri-upload-2-fill label-icon align-middle fs-19"></i></button>';
				}, sWidth: '20%'
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

let nsaField = document.getElementById("nsa-field"),
	fotografiaImg = document.getElementById("fotografia-img"),

	fotografiaField = document.getElementById("fotografia-field"),

	fotografiaInput = document.getElementById("fotografia-input"),
	txtDatosCompletos = document.getElementById("txt-datos-completos"),
	txtEspecialidad = document.getElementById("txt-especialidad");

function llenarObjetoModal(obj) {
	formEdit = true;
	const defaultImage = obj.sexo === 'MASCULINO' ? 'male-user.jpg' : 'female-user.jpg';

	nsaField.value = obj.nsa;
	fotografiaImg.src = (obj.fotografia !== null && obj.fotografia !== 'null') ? 'uploads/personal/' + obj.fotografia : `assets/images/users/${defaultImage}`;
	fotografiaField.value = (obj.fotografia !== null && obj.fotografia !== 'null') ? obj.fotografia : '';

	txtDatosCompletos.innerText = obj.datosCompletos;
	txtEspecialidad.innerText = obj.especialidad;

	document.getElementById("mdTitulo").innerHTML = "Editar Personal";
	document.getElementById("mdPersonal").querySelector(".modal-footer").style.display = "block";
	document.getElementById("add-btn").innerHTML = "Actualizar";
}

let forms = document.querySelectorAll('.tablelist-form')
Array.prototype.slice.call(forms).forEach(function(form) {
	form.addEventListener('submit', async function(event) {
		if (!form.checkValidity()) {
			event.preventDefault();
			event.stopPropagation();
			form.classList.add('was-validated');
		} else {
			event.preventDefault();
			if (nsaField.value !== "" && !formEdit) {
				event.preventDefault();
				event.stopPropagation();
				notification('info', "No tiene privilegios para registrar un personal")
				return;

			} else if (
				nsaField.value !== "" && formEdit) {

				let formData = new FormData();
				let archivoInput = document.getElementById('fotografia-input');

				let personal = {
					nsa: nsaField.value
				};

				formData.append('personal', JSON.stringify(personal));

				if (archivoInput.files.length > 0) {
					formData.append('archivo', archivoInput.files[0]);
				} else {
					formData.append('archivo', null);
				}

				try {
					const response = await fetchActualizarPersonal(formData);
					const { type, message } = response;
					alert(type, message);
					if (SYSTEM.Constants.SweetAlert.TypeMessage.Success == type) {
						document.getElementById("close-modal").click();
						$('#tblPersonal').DataTable().draw();
						limpiarCampos(); 
						
					}
				} catch (error) {
					console.error('Error al actualizar el personal:', error);
				}

			}
		}
	}, false)
})


function limpiarCampos() {
	nsaField.value = "";
	fotografiaField.value = "";
	fotografiaImg.src = "assets/images/users/male-user.jpg";

	fotografiaInput.value = "";
	formEdit = false;
}


function renderizarCardPersonalDetalle(data) { 
	const defaultImage = data.sexo === 'MASCULINO' ? 'male-user.jpg' : 'female-user.jpg';
	const image_src = (data.fotografia != null && data.fotografia != 'null') ? 'uploads/personal/' + data.fotografia : `assets/images/users/${defaultImage}`;

	var bloque = `
            <div class="card-body text-center">
                <div class="position-relative d-inline-block">
                    <div class="">
                        <div class="avatar-title bg-light rounded">
                            <img src="${image_src}" alt="" class="img-thumbnail" width="200">
                        </div>
                    </div>
                </div>
                <h5 class="mt-3 mb-1">${data.datos}</h5>
                <p class="text-muted">${data.grado}</p>

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
                <h6 class="text-muted text-uppercase fw-semibold mb-3">Especialidad</h6>
                <p class="text-muted mb-4">${data.especialidad}</p>
                <div class="table-responsive table-card">
                    <table class="table table-borderless mb-0">
                         <tbody> 
                               <tr>
                                    <td class="fw-medium" scope="row">Nro Documento</td>
                                    <td>${data.dni}</td>
                               </tr>
                               <tr>
                               		<td class="fw-medium" scope="row">Unidad</td>
                                    <td>${data.unidadOrigen}</td>
                                </tr> 
                                <tr>
                                     <td class="fw-medium" scope="row">Nacimiento</td>
                                     <td>${data.nacimiento}</td>
                                </tr>
                           </tbody>
                    </table>
                </div>
            </div>
        `;
	document.getElementById('view-person').innerHTML = bloque;
}



document.getElementById("mdPersonal").addEventListener("hidden.bs.modal", function() {
	limpiarCampos();
});

document.querySelector("#listadoPersonal").addEventListener("click", function() {
	//renderizarCheckboxCheck();
});

document.getElementById('cboLength').addEventListener('change', function() {
	const length = parseInt(this.value, 10);
	const table = $('#tblPersonal').DataTable();
	table.page.len(length).draw();
});

document.getElementById("tblPersonal").addEventListener('click', async function(event) {
	const btnVista = event.target.closest('button.btn-soft-info');
	const btnEditar = event.target.closest('button.btn-soft-primary');
	if (btnVista) {
		const row = btnVista.closest('tr');
		const table = $('#tblPersonal').DataTable();
		const rowData = table.row(row).data();
		const dataNsa = rowData['nsa'];
		const response = await fetchBuscarPersonalPorNsa(dataNsa);
		renderizarCardPersonalDetalle(response)

	} else if (btnEditar) {
		const row = btnEditar.closest('tr');
		const table = $('#tblPersonal').DataTable();
		const rowData = table.row(row).data();
		const dataNsa = rowData['nsa'];
		const response = await fetchBuscarPersonalPorNsa(dataNsa);
		llenarObjetoModal(response);

		var modal = new bootstrap.Modal(document.getElementById('mdPersonal'));
		modal.show();
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


async function fetchPaginarPersonal(page, size) {
	try {
		const response = await fetch(`/personal/paginar?page=${page}&size=${size}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver la lista de personal: ${response.status}`);
		}
		listadoPersonal.clear();
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener la lista de personal:', error);
		throw error;
	}
}

async function fetchBuscarPersonalPorNsa(nsa) {
	try {
		const response = await fetch(`/personal/buscarPorNsa?nsa=${nsa}`);
		if (!response.ok) {
			throw new Error(`No se ha podido resolver el personal: ${response.status}`);
		}
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error al obtener el personal:', error);
		throw error;
	}
}

async function fetchActualizarPersonal(formData) {
	try {
		const configHttp = {
			method: 'POST',
			body: formData
		}
		const response = await fetch(`/personal/actualizarFotografia`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido actualizar el personal: ${response.status}`;
			throw new Error(message);
		}

		const mensaje = await response.json();
		return mensaje;
	} catch (error) {
		console.error('Error al actualizar el personal:', error);
		throw error;
	}
}
