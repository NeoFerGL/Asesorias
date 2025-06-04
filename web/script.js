function desplegar(seccion) {
    tabs = document.getElementsByClassName("despliege");
    for (i = 0; i < tabs.length; i++) {
        tabs[i].style.display = "none";
    }
    document.getElementById(seccion).style.display = "block";
}