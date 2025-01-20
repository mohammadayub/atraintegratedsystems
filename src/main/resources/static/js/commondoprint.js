function doPrint() {
    const hideElements = ["pagnav", "pagfooter", "prn", "backtopage"];

    // Hide elements if they exist
    hideElements.forEach(id => {
        const element = document.getElementById(id);
        if (element) {
            element.style.display = "none";
        }
    });

    // Print the page
    window.print();

    // Restore elements if they exist
    hideElements.forEach(id => {
        const element = document.getElementById(id);
        if (element) {
            element.style.display = id === "pagfooter" ? "inline-block" : "block";
        }
    });
}
