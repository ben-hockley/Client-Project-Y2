function openModal() {
    const modal = document.getElementById("modal");
    if (modal) {
        modal.style.display = "flex";
    } else {
        console.error("Modal element not found");
    }
}

function closeModal() {
    const modal = document.getElementById("modal");
    if (modal) {
        modal.style.display = "none";
    } else {
        console.error("Modal element not found");
    }
}