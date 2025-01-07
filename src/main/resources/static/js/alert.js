//<!--popup for submit-->

  document.addEventListener("DOMContentLoaded", function () {
      // Get references to the buttons and the popup
      const submitButton = document.getElementById("submitButton");
      const confirmationPopup = document.getElementById("confirmationPopup");
      const confirmYesButton = document.getElementById("confirmYesButton");
      const confirmNoButton = document.getElementById("confirmNoButton");
      const form = document.querySelector("form");

      // Show the popup only if the form is valid
      submitButton.addEventListener("click", function (event) {
          // Check if the form is valid using browser's built-in validation
          if (form.checkValidity()) {
              event.preventDefault(); // Prevent immediate form submission
              confirmationPopup.style.display = "flex"; // Show the confirmation popup
          } else {
              // If form is invalid, show validation messages
              form.reportValidity();
          }
      });

      // If the user clicks "Yes, Submit"
      confirmYesButton.addEventListener("click", function () {
          form.submit(); // Submit the form explicitly
      });

      // If the user clicks "No, Stay"
      confirmNoButton.addEventListener("click", function () {
          event.preventDefault();
          confirmationPopup.style.display = "none"; // Hide the confirmation popup
          // No form submission happens here
      });
  });