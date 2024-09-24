document.querySelectorAll('.place-bid-btn a').forEach(button => {
    button.addEventListener("click", async function(event) {
        event.preventDefault(); 
        const buttonId = this.id;
        const programa = buttonId === 'btnIrPDI' ? "PDI" : "PDE"; 
         
        try {
            const response = await fetchRedireccionarControlPersonas(programa);
            if (response) {
                window.location.href = response;
            }
        } catch (error) {
            console.error('Error al enlazar el uri:', error);
        }
    });
});

async function fetchRedireccionarControlPersonas(noTipoInstruccion) {
	try {
		const configHttp = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ 
				noTipoInstruccion: noTipoInstruccion
			})
		}
		const response = await fetch(`/redireccion/redirectCtrlPersonasInscritas`, configHttp);

		if (!response.ok) {
			const message = `"No se ha podido enlazar a esta url: ${response.status}`;
			throw new Error(message);
		} 
		const mensaje = await response.text();
		return mensaje;
	} catch (error) {
		console.error('Error al enlazar la url:', error);
		throw error;
	}
}
