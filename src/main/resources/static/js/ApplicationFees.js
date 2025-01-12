
 function setApplicationFees() {
    const licenseType = document.getElementById("licenseTypeId").value;
    const guaranteeFees = document.getElementById("guaranteeFees");
    const guaranteeFeesPaymentOffice = document.getElementById("guaranteeFeesPaymentOffice");
    const validity = document.getElementById("validity");
    const currencyType = document.getElementById("currencyType");
    const adminFees = document.getElementById("adminFees");
    const licenseFees = document.getElementById("licenseFees");
    const databaseMaintenanceFees = document.getElementById("databaseMaintenanceFees");

    // Ensure these elements exist in your HTML
    const licensePaymentOffice = document.getElementById("licensePaymentOffice");
    const adminFeesPaymentOffice = document.getElementById("adminFeesPaymentOffice");
    const licenseApprovalDatabaseFeesPaymentOffice = document.getElementById("licenseApprovalDatabaseFeesPaymentOffice");
    const databaseMaintenanceFeesPaymentOffice = document.getElementById("databaseMaintenanceFeesPaymentOffice");


    // Currency Section
    if (licenseType === "6" || licenseType === "7" || licenseType === "8") {
        currencyType.value = "USD";
    } else {
        currencyType.value = "AFN";
    }
    // End Of Currency Section

    if (licenseType === "1") {
        licenseFees.value = 1950000000;
        adminFees.value = 37000000;
        databaseMaintenanceFees.value = 350000;
        guaranteeFees.value = 1000000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
        }
    else if (licenseType === "2"){

        licenseFees.value = 0;
        adminFees.value = 30000000;
        databaseMaintenanceFees.value = 350000;
        guaranteeFees.value = 0;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "3"){

        licenseFees.value = 78000000;
        adminFees.value = 10000000;
        databaseMaintenanceFees.value = 100000;
        guaranteeFees.value = 100000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "4"){

        licenseFees.value = 11700000;
        adminFees.value = 10500000;
        databaseMaintenanceFees.value = 100000;
        guaranteeFees.value = 200000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "5"){

        licenseFees.value = 2730000;
        adminFees.value = 1365000;
        databaseMaintenanceFees.value = 13650;
        guaranteeFees.value = 0;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "6"){

        licenseFees.value = 20000;
        adminFees.value = 10000;
        databaseMaintenanceFees.value = 200;
        guaranteeFees.value = 17000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "7"){

        licenseFees.value = 15000;
        adminFees.value = 7500;
        databaseMaintenanceFees.value = 150;
        guaranteeFees.value = 17000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "8"){

        licenseFees.value = 10000;
        adminFees.value = 5000;
        databaseMaintenanceFees.value = 100;
        guaranteeFees.value = 17000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "9"){

        licenseFees.value = 750000;
        adminFees.value = 720000;
        databaseMaintenanceFees.value = 7200;
        guaranteeFees.value = 2000000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "10"){

        licenseFees.value = 150000;
        adminFees.value = 240000;
        databaseMaintenanceFees.value = 2400;
        guaranteeFees.value = 750000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "11"){

        licenseFees.value = 10140000;
        adminFees.value = 3080000;
        databaseMaintenanceFees.value = 30800;
        guaranteeFees.value = 50000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "12"){

        licenseFees.value = 100000;
        adminFees.value = 3750000;
        databaseMaintenanceFees.value = 37500;
        guaranteeFees.value = 50000000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "13"){

        licenseFees.value = 300000;
        adminFees.value = 345000;
        databaseMaintenanceFees.value = 3450;
        guaranteeFees.value = 300000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "14"){

        licenseFees.value = 100000;
        adminFees.value = 115000;
        databaseMaintenanceFees.value = 1150;
        guaranteeFees.value = 116150;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "15"){

        licenseFees.value = 100000;
        adminFees.value = 115000;
        databaseMaintenanceFees.value = 1150;
        guaranteeFees.value = 116150;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "16"){

        licenseFees.value = 100000;
        adminFees.value = 115000;
        databaseMaintenanceFees.value = 1150;
        guaranteeFees.value = 116150;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "17"){

        licenseFees.value = 2500000;
        adminFees.value = 875000;
        databaseMaintenanceFees.value = 8750;
        guaranteeFees.value = 1000000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "18"){

        licenseFees.value = 1000000;
        adminFees.value = 575000;
        databaseMaintenanceFees.value = 5750;
        guaranteeFees.value = 500000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "19"){

        licenseFees.value = 500000;
        adminFees.value = 230000;
        databaseMaintenanceFees.value = 2300;
        guaranteeFees.value = 200000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "20"){

        licenseFees.value = 200000;
        adminFees.value = 135000;
        databaseMaintenanceFees.value = 1350;
        guaranteeFees.value = 200000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "21"){

        licenseFees.value = 200000;
        adminFees.value = 57500;
        databaseMaintenanceFees.value = 575;
        guaranteeFees.value = 100000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
    else if (licenseType === "22"){

        licenseFees.value = 800000;
        adminFees.value = 575000;
        databaseMaintenanceFees.value = 5750;
        guaranteeFees.value = 650000;

        licensePaymentOffice.value = "ATRA";
        adminFeesPaymentOffice.value = "ATRA";
        databaseMaintenanceFeesPaymentOffice.value = "ATRA";
        guaranteeFeesPaymentOffice.value = "ATRA";
    }
       else {
        // Clear fields if no valid license type
        licenseFees.value = "";
        guaranteeFees.value = "";
        guaranteeFeesPaymentOffice.value = "";
        adminFees.value = "";
        databaseMaintenanceFees.value = "";
        guaranteeFees.value = "";
        licensePaymentOffice.value = "";
        adminFeesPaymentOffice.value = "";
        databaseMaintenanceFeesPaymentOffice.value = "";
        licenseApprovalDatabaseFeesPaymentOffice.value = "";
    }











   function updateLicenseApplicantId() {
        var selectedValue = document.getElementById('licenseType').value;
        document.getElementById('applicantId').value = selectedValue;
    }

    // Ensure the value is set on page load
    window.onload = function() {
        updateLicenseApplicantId();
    };


}