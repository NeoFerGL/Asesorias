function abreModal(Id) {
    document.getElementById('IdModalAceptar').value = Id;
    document.getElementById('comentarioAceptar').value = '';
}

function eliminar(Id) {
    document.getElementById('IdModalRechazar').value = Id;
    document.getElementById('comentarioRechazar').value = '';
}