function isValidDouble(value) {
    const cleaned = value.replace(",", ".");
    const num = parseFloat(cleaned);
    return !isNaN(num) && isFinite(num);
}

function parseToFloat(value) {
    return parseFloat(value.replace(",", "."));
}

function normalizeInput(input, errorElement, min, max) {
    input.addEventListener("input", () => {
        if (input.value.includes(",")) {
            input.value = input.value.replace(",", ".");
        }
        const value = input.value.trim();
        const num = parseToFloat(value);
        errorElement.classList.toggle("d-none", isValidDouble(value) && num >= min && num <= max);
    });
}

export function initCoordinateValidation() {
    const latitudeInput = document.getElementById("latitude");
    const latitudeError = document.getElementById("latitude-error");
    const longitudeInput = document.getElementById("longitude");
    const longitudeError = document.getElementById("longitude-error");

    if (latitudeInput && latitudeError)
        normalizeInput(latitudeInput, latitudeError, -90, 90);
    if (longitudeInput && longitudeError)
        normalizeInput(longitudeInput, longitudeError, -180, 180);
}
