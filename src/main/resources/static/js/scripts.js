//document.getElementById("startButton").addEventListener("click", function () {
//	alert("button click!");
//    // Fetch the JSON file
//    fetch("questionnaire.json")
//        .then(response => response.json())
//        .then(data => {
//            // Redirect to the next page with JSON data as query parameter
//            const queryParams = new URLSearchParams(data).toString();
//            window.location.href = "assessment.html?" + queryParams;
//        })
//        .catch(error => console.error("Error loading data:", error));
//});




// Function to retrieve the query parameters from the URL
//    function getQueryParams() {
//        const queryString = window.location.search;
//        const params = new URLSearchParams(queryString);
//        const queryParamsData = {};
//        for (const [key, value] of params.entries()) {
//            queryParamsData[key] = value;
//        }
//        return queryParamsData;
//    }
//
//function validateForm(){
//	 // Get the query parameters and populate the form fields
//        const queryParamsData = getQueryParams();
//        if (queryParamsData.jsonData) {
//            try {
//                const jsonDataArray = JSON.parse(queryParamsData.jsonData);
//                if (Array.isArray(jsonDataArray) && jsonDataArray.length > 0) {
//                    // Assuming the first object in the array has the data you want to populate
//                    const firstObject = jsonDataArray[0];
//                    alert(firstObject.SectionName);
//                    document.getElementById("name").value = firstObject.SectionName || '';
//                    document.getElementById("email").value = firstObject.email || '';
//                    // Add more lines to populate other fields as needed
//                }
//            } catch (error) {
//                console.error("Error parsing JSON data:", error);
//            }
//        }
//	alert("started!");
//	
//}
