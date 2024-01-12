function Createa() {
    // Get the field values
    const LoginValue = document.getElementById("Login").value;
    const PasswordValue = document.getElementById("Password").value;
    const NicknameValue = document.getElementById("Nickname").value;

    // Create a JavaScript object with the field values
    const data = {
        1: LoginValue,
        2: PasswordValue,
        3: NicknameValue
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
            document.location.href="index.html?success";
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
function Logina() {
    // Get the field values
    const LoginValue = document.getElementById("Login").value;
    const PasswordValue = document.getElementById("Password").value;

    // Create a JavaScript object with the field values
    const data = {
        1: LoginValue,
        2: PasswordValue
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
        if (responseData[1]){
            sessionStorage.setItem("Login", LoginValue);
            sessionStorage.setItem("Nickname", responseData[2]);
            sessionStorage.setItem("Password", PasswordValue);
            sessionStorage.setItem("Logo", responseData[3].imageData);
            returnp();
        }
        else{
            const infoElement = document.getElementById('info');
            infoElement.innerText = 'There is no such account';
            document.getElementById("myForm").reset();
        } 
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
function returnp(){
    if (sessionStorage.getItem('Logo') == 'undefined'){
        document.location.href="mainpage.html";
    }
    else{
        document.location.href="artistpage.html";
    }
}
function logout() {
    sessionStorage.clear();
    document.location.href="index.html";
}
function chpswd() {
    const LoginValue = sessionStorage.getItem('Login');
    const PasswordValue = document.getElementById("Password").value;
    const Password2Value = document.getElementById("Password2").value;
    console.log(LoginValue);
    console.log(PasswordValue);
    console.log(Password2Value);
    const data = {
        1: LoginValue,
        2: PasswordValue,
        3: Password2Value
    };

    fetch('/changepassword', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(responseData => {
        console.log("Received data:");
        console.log("1:", responseData[1]);
        if (responseData[1]){
            sessionStorage.setItem("Password", Password2Value);
            document.location.href="mainpage.html?success2";
        }
        else{
            const infoElement = document.getElementById('info');
            document.getElementById("myForm").reset();
            infoElement.innerText = 'Podano zle haslo';
        } 
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
function getrandomimages() {
    const imageContainer = document.getElementById('images');
    const Amount = 3;
    console.log(Amount);
    const data = {
        1: Amount
    };

    fetch('/getrandomimages', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(responseData => {
        imageContainer.innerHTML = '';
        console.log("Received data:");
        console.log("0:", responseData[0]);
        // var amount = responseData[0];
        for (let i = 2; i <= Amount + 1; i++){
            console.log(i, responseData[i].imageData);
            const imageInfo = responseData[i].imageData;
            const imgElement = document.createElement('img');
            imgElement.src = imageInfo;
            console.log(imageInfo);
            imgElement.alt = 'Random Image';
            imgElement.width = 200;
            imgElement.height = 200; 
            imageContainer.appendChild(imgElement);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
function getuserimages() {
    const imageContainer = document.getElementById('images');
    const userid = 2;
    const data = {
        1: userid
    };

    fetch('/getuserimages', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(responseData => {
        imageContainer.innerHTML = '';
        console.log("Received data:");
        console.log("0:", responseData[0]);
        console.log("1:", responseData[0]);
        console.log("2:", responseData[0]);
        console.log("3:", responseData[0]);
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
const urlParams = new URLSearchParams(window.location.search);

if (urlParams.has('success')) {
    const infoElement = document.getElementById('info');
    infoElement.innerText = 'Account has been created';
}
if (urlParams.has('success2')) {
    const infoElement = document.getElementById('info');
    infoElement.innerText = 'Password has been changed';
}
const selectedTags = [];
        function saveTag(checkbox) {
            const tagValue = checkbox.value;
            if (checkbox.checked) {
                selectedTags.push(tagValue);
            } else {
                const index = selectedTags.indexOf(tagValue);
                if (index !== -1) {
                    selectedTags.splice(index, 1);
                }
            }
        }