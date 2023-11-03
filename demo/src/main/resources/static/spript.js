function button1Clicked() {
    // Get the field values
    const field1Value = document.getElementById("field1").value;
    const field2Value = document.getElementById("field2").value;
    const field3Value = document.getElementById("field3").value;

    // Create a JavaScript object with the field values
    const data = {
        field1: field1Value,
        field2: field2Value,
        field3: field3Value
    };

    // Send the data to the backend
    fetch('/createaccount', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(data => {
        if (data === true) {
            console.log("Info: Creation true");
        } else if (data === false) {
            console.log("Info: Creation false");
        } else {
            console.log("Info: Unknown condition");
        }
    })
    .catch(error => {
        console.error('Error:', error);
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
    fetch('/con', {
        method: 'GET'
    })
    .then(response => response.text())
    .then(data => {
        console.log('Received string:', data);
        // You can do something with the received string here
    })
    .catch(error => {
        console.error('Error:', error);
    });
}