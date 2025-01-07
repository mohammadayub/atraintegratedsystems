function setApplicationFees() {
    const licenseType = document.getElementById("licenseTypeId").value;
    const applicationFeesField = document.getElementById("applicationFees");
    const validity = document.getElementById("validity")
    const currencyType = document.getElementById("currencyType");
    //Currency Section
    if (licenseType === "6" || licenseType === "7" || licenseType === "8") {currencyType.value="USD";}
    else{currencyType.value="AFN";}
    // End Of Currency Section
    if (licenseType === "1") {
        applicationFeesField.value = 195000000;
        validity.value=15;


    } else if (licenseType === "2") {
        applicationFeesField.value = 0;
        validity.value=15;
    }
    else if (licenseType === "3") {
        applicationFeesField.value = 7800000;
        validity.value=15;
    }
    else if (licenseType === "4") {
        applicationFeesField.value = 1170000;
        validity.value=15;
    }
    else if (licenseType === "5") {
        applicationFeesField.value = 273000;
        validity.value=15;
    }
    else if (licenseType === "6") {
        applicationFeesField.value = 2000;
        validity.value=15;
    }
    else if (licenseType === "7") {
        applicationFeesField.value = 1500;
        validity.value=15;
    }
    else if (licenseType === "8") {
        applicationFeesField.value = 1000;
        validity.value=15;
    }
    else if (licenseType === "9") {
        applicationFeesField.value = 75000;
        validity.value=10;
    }
    else if (licenseType === "10") {
        applicationFeesField.value = 15000;
        validity.value=10;
    }
    else if (licenseType === "11") {
        applicationFeesField.value = 1014000;
        validity.value=15;
    }
    else if (licenseType === "12") {
        applicationFeesField.value = 10000;
        validity.value=15;
    }
    else if (licenseType === "13") {
        applicationFeesField.value = 30000;
        validity.value=10;
    }
    else if (licenseType === "14") {
        applicationFeesField.value = 10000;
        validity.value=10;
    }
    else if (licenseType === "15") {
        applicationFeesField.value = 10000;
        validity.value=10;
    }
    else if (licenseType === "16") {
        applicationFeesField.value = 10000;
        validity.value=10;
    }
    else if (licenseType === "17") {
        applicationFeesField.value = 250000;
        validity.value=5;
    }
    else if (licenseType === "18") {
        applicationFeesField.value = 100000;
        validity.value=5;
    }
    else if (licenseType === "19") {
        applicationFeesField.value = 50000;
        validity.value=5;
    }
    else if (licenseType === "20") {
        applicationFeesField.value = 20000;
        validity.value=5;
    }
    else if (licenseType === "21") {
        applicationFeesField.value = 10000;
        validity.value=5;
    }
    else if (licenseType === "22") {
        applicationFeesField.value = 50000;
        validity.value=5;
    }

    else {
        applicationFeesField.value = ""; // Clear if no valid option is selected
    }
}