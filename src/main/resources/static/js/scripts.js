document.getElementById("startButton").addEventListener("click", function () {
    // Fetch the JSON file
    fetch("questionnaire.json")
        .then(response => response.json())
        .then(data => {
            // Redirect to the next page with JSON data as query parameter
            const queryParams = new URLSearchParams(data).toString();
            window.location.href = "next_page.html?" + queryParams;
        })
        .catch(error => console.error("Error loading data:", error));
});

// Function to retrieve the query parameters from the URL
function getQueryParams() {
    const queryParams = new URLSearchParams(window.location.search);
    const data = {};
    for (const [key, value] of queryParams.entries()) {
        data[key] = value;
    }
    return data;
}

document.addEventListener("DOMContentLoaded", function () {
    // Get the query parameters and populate the form fields
    const queryParamsData = getQueryParams();
    document.getElementById("name").value = queryParamsData.name || '';
    document.getElementById("email").value = queryParamsData.email || '';
    // Add more lines to populate other fields as needed
});
