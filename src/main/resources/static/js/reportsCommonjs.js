
    $(document).ready(function () {
        const table = $('#profileTable').DataTable({
            scrollX: true,
            dom: 'Bfrtip',
            pageLength: 25,
            buttons: [
                {
                    extend: 'excelHtml5',
                    text: 'Export to Excel',
                    footer: true,
                    exportOptions: {
                        columns: ':visible'
                    },
                    customize: function (xlsx) {
                        let sheet = xlsx.xl.worksheets['sheet1.xml'];
                        let row = $('row', sheet).last();
                        $('c', row).each(function () {
                            $(this).attr('s', '52');
                        });

                        // Remove the grand total rows for the export to excel
                        $('row', sheet).filter(function () {
                            return $(this).find('c').text() === 'Grand Totals:';
                        }).remove();

                        // Set column widths
                        $('col', sheet).each(function () {
                            $(this).attr('width', '30');
                        });
                    }
                }
            ],
            footerCallback: function (row, data, start, end, display) {
                let api = this.api();

                let intVal = function (i) {
                    return typeof i === 'string'
                        ? parseFloat(i.replace(/[,]/g, '')) || 0
                        : typeof i === 'number'
                            ? i
                            : 0;
                };

                let formatNumber = function (num) {
                    return num.toLocaleString(undefined, {
                        minimumFractionDigits: 2,
                        maximumFractionDigits: 2
                    });
                };

                // Grand totals
                let applicationFeesTotal = api.column(6).data().reduce((a, b) => intVal(a) + intVal(b), 0);
                let licenseFeesTotal = api.column(11).data().reduce((a, b) => intVal(a) + intVal(b), 0);
                let adminFeesTotal = api.column(15).data().reduce((a, b) => intVal(a) + intVal(b), 0);
                let dbFeesTotal = api.column(19).data().reduce((a, b) => intVal(a) + intVal(b), 0);
                let guaranteeFeesTotal = api.column(23).data().reduce((a, b) => intVal(a) + intVal(b), 0);

                // Set totals in footer with names
                $(api.column(6).footer()).html('Application Fees: ' + formatNumber(applicationFeesTotal));
                $(api.column(11).footer()).html('License Fees: ' + formatNumber(licenseFeesTotal));
                $(api.column(15).footer()).html('Admin Fees: ' + formatNumber(adminFeesTotal));
                $(api.column(19).footer()).html('DB Fees: ' + formatNumber(dbFeesTotal));
                $(api.column(23).footer()).html('Guarantee Fees: ' + formatNumber(guaranteeFeesTotal));
            }
        });
    });