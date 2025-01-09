$(document).ready(function () {
        // Initialize DataTable with individual column search
        let table = $('#profileTable').DataTable({
            initComplete: function () {
                this.api().columns().every(function () {
                    var column = this;
                    var header = $(column.header());
                    var columnText = header.text(); // Get the existing header text
                    header.empty().append('<div>' + columnText + '</div>'); // Keep the header text and add a wrapper

                    // Append the search input below the header text
                    $('<input type="text" placeholder="Search" style="width: 100%; margin-top: 5px;"/>')
                        .appendTo(header)
                        .on('keyup change clear', function () {
                            if (column.search() !== this.value) {
                                column
                                    .search(this.value)
                                    .draw();
                            }
                        });
                });
            }
        });
    });