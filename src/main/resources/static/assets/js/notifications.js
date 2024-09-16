function alert(type, message) {
	//success, warning, info, error
	Swal.fire({
		position: 'center',
		icon: type,
		title: message,
		showConfirmButton: false,
		timer: 2000,
		showCloseButton: true
	});
}


function notification(type, message) {
	//success, warning, info, danger
	let className = "bg-danger";

	if (type === 'success') {
		className = "bg-success";
	} else if (type === 'warning') {
		className = "bg-warning";
	} else if (type === 'info') {
		className = "bg-info";
	} else if (type === 'danger') {
		className = "bg-danger";
	}

	Toastify({
		text: message,
		duration: 3000,
		className: className,
		gravity: "top",
		position: "right",
		stopOnFocus: true,
	}).showToast();
}
