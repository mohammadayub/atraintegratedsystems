  function doPrint() {
    document.getElementById("pagnav").style.display = "none";
    document.getElementById("pagfooter").style.display = "none";
    document.getElementById("prn").style.display = "none";
    document.getElementById("backtopage").style.display = "none";


    window.print();

    document.getElementById("pagnav").style.display = "block";
    document.getElementById("pagfooter").style.display = "inline-block";
    document.getElementById("prn").style.display = "inline-block";
    document.getElementById("backtopage").style.display = "inline-block";

  }