 $(document).ready(function () {
    // Initialize DataTable
    $('#profileTable').DataTable({
      dom: 'Bfrtip',
      buttons: [
        {
          extend: 'excelHtml5',
          text: 'Export to Excel',
          title: 'License Approvals Report'
        }
      ]
    });

    // Function to parse the date and calculate days
    function parseDate(dateString) {
      const dateParts = dateString.split('-');
      return new Date(dateParts[0], dateParts[1] - 1, dateParts[2]); // Month is 0-indexed
    }

    // Function to calculate days between system date and the expiry date
    function calculateDays(startDate, endDate) {
      const now = new Date(); // Current system date
      const start = parseDate(startDate); // Approval Date (start date)
      const end = parseDate(endDate); // License Fee Expiry Date (end date)

      // Calculate days passed from start (Approval Date) to now
      const daysPassed = Math.max(0, Math.ceil((now - start) / (1000 * 60 * 60 * 24))); // in days

      // Calculate remaining days to the expiry date
      const daysRemaining = Math.max(0, Math.ceil((end - now) / (1000 * 60 * 60 * 24))); // in days

      return { daysPassed, daysRemaining };
    }

    // Render 3D Charts for License Fees
    $('[id^="chart-licenseFees-"]').each(function () {
      const chartId = $(this).attr('id');
      const ctx = document.getElementById(chartId).getContext('2d');

      const approvalDate = $(this).data('approval-date'); // Approval Date
      const expiryDate = $(this).data('license-fee-expiry-date'); // License Fee Expiry Date

      const { daysPassed, daysRemaining } = calculateDays(approvalDate, expiryDate);

      // Create the chart with a 3D effect
      new Chart(ctx, {
        type: 'pie',
        data: {
          labels: ['Days Passed', 'Days Remaining'],
          datasets: [{
            data: [daysPassed, daysRemaining],
            backgroundColor: ['#4caf50', '#f44336'],
            borderWidth: 0, // Remove border to make it smoother
          }]
        },
        options: {
          responsive: true,
          plugins: {
            legend: {
              position: 'top',
            },
            tooltip: {
              enabled: true,
            }
          },
          rotation: Math.PI / 4, // Rotate the chart for a 3D effect
          cutout: 50, // Make the center hollow
          animation: {
            animateRotate: true, // Add rotation animation
            animateScale: true, // Add scaling effect
          },
          elements: {
            arc: {
              borderWidth: 0, // No border for a smoother look
            }
          },
        }
      });
    });
  });