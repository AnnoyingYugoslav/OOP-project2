function button1Clicked() {
    // Get the field values
    const field1Value = document.getElementById("field1").value;
    const field2Value = document.getElementById("field2").value;
    const field3Value = document.getElementById("field3").value;

    // Create a JavaScript object with the field values
    const data = {
        1: field1Value,
        2: field2Value,
        3: field3Value
    };

    // Send the data to the backend
    fetch('/createaccount', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        // Check the value associated with key 1
        const isSuccess = data[1];

        if (isSuccess === true) {
            console.log("Info: Creation true");
        } else if (isSuccess === false) {
            console.log("Info: Creation false");
        } else {
            console.log("Info: Unknown condition");
        }
    })
    .catch(error => {
        console.error('Error:', error.message);
    });
}
// script.js
function button2Clicked() {
    // Get the field values
    const field1Value = document.getElementById("field1").value;
    const field2Value = document.getElementById("field2").value;

    // Create a JavaScript object with the field values
    const data = {
        1: field1Value,
        2: field2Value
    };

    // Send the data to the backend
    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(responseData => {
        // Log the received data to the console
        console.log("Received data:");
        console.log("1:", responseData[1]);
        console.log("2:", responseData[2]);
        console.log("3:", responseData[3]);
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
function button3Clicked() {
    // Get the field values
    const field1Value = document.getElementById("field1").value;
    const field2Value = document.getElementById("field2").value;
    const field3Value = document.getElementById("field3").value;
    const field4Value = document.getElementById("field4").value;

    // Create a JavaScript object with the field values
    const data = {
        1: field1Value,
        2: field2Value,
        3: field3Value,
        4: field4Value
    };

    // Send the data to the backend
    fetch('/convert', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(responseData => {
        // Log the received data to the console
        console.log("Received data:");
        console.log("1:", responseData[1]);
        console.log("2:", responseData[2]);
        console.log("3:", responseData[3]);
    })
    .catch(error => {
        console.error('Error:', error);
    });
}