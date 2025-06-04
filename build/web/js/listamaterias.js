document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("solicitarA");
    const input = document.getElementById("dropdownInput");
    const list = document.getElementById("dropdownList");
    const items = list.querySelectorAll(".dropdown-list-item");
    const maestrosSelect = document.getElementById("maestrosSelect");

    const maestrosPorMateria = {
        "Aplicaciones Web": [
            { maestro: "Maestro A", nrc: "101" },
            { maestro: "Maestro B", nrc: "103" }
        ],
        "Modelos de Desarrollo Web": [
            { maestro: "Maestro D", nrc: "102" },
            { maestro: "Maestro E", nrc: "104" }
        ],
    };

    maestrosSelect.style.display = "block";

    form.addEventListener("submit", function(e) {
        e.preventDefault();
        const searchText = input.value.toLowerCase();
        let found = false;
        items.forEach(item => {
            const text = item.textContent.toLowerCase();
            if (text === searchText) {
                found = true;
            }
        });
        if (found) {
            // Enviar datos del formulario al servlet
            form.submit();
        } else {
            alert("Asignatura no válida. Por favor, seleccione una asignatura de la lista.");
        }
    });

    input.addEventListener("input", function() {
        const searchText = input.value.toLowerCase();
        items.forEach(item => {
            const text = item.textContent.toLowerCase();
            if (text.includes(searchText)) {
                item.style.display = "block";
            } else {
                item.style.display = "none";
            }
        });
        list.style.display = "block";
    });

    items.forEach(item => {
        item.addEventListener("click", function() {
            input.value = item.textContent;
            list.style.display = "none";
            const materiaSeleccionada = item.textContent;
            const maestros = maestrosPorMateria[materiaSeleccionada];
            actualizarMaestrosSelect(maestros);
        });
    });

    function actualizarMaestrosSelect(maestros) {
        maestrosSelect.innerHTML = ""; // Limpiar opciones anteriores
        const defaultOption = document.createElement("option");
        defaultOption.text = "Seleccionar maestro";
        defaultOption.value = "";
        defaultOption.disabled = true;
        defaultOption.selected = true;
        maestrosSelect.add(defaultOption);
        maestros.forEach(maestroObj => {
            const option = document.createElement("option");
            option.text = `${maestroObj.maestro} - NRC: ${maestroObj.nrc}`; // Mostrar maestro y NRC
            option.value = maestroObj.nrc; // Usar el NRC como valor
            maestrosSelect.add(option);
        });
        maestrosSelect.disabled = false; // Habilitar el select de maestros
    }

    document.addEventListener("click", function(e) {
        if (!list.contains(e.target) && e.target !== input) {
            list.style.display = "none";
        }
    });
});
