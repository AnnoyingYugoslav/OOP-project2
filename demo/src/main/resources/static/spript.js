function Createa() {
    const LoginValue = document.getElementById("Login").value;
    const PasswordValue = document.getElementById("Password").value;
    const NicknameValue = document.getElementById("Nickname").value;
    const info = document.getElementById("info");
    if (LoginValue === '' || PasswordValue === '' || NicknameValue === '') {
        info.innerHTML = "Please fill all the fields"
    }
    else{
    const data = {
        1: LoginValue,
        2: PasswordValue,
        3: NicknameValue
    };
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
        const isSuccess = data[1];
        if (isSuccess === true) {
            console.log("Info: Creation true");
            document.location.href="index.html?success";
        } else if (isSuccess === false) {
            console.log("Info: Creation false");
            info.innerHTML="Such account already exists";
        } else {
            console.log("Info: Unknown condition");
        }
    })
    .catch(error => {
        console.error('Error:', error.message);
    });
}
}
function Logina() {
    const LoginValue = document.getElementById("Login").value;
    const PasswordValue = document.getElementById("Password").value;
    const info = document.getElementById("info");
    if (LoginValue === '' || PasswordValue === ''){
        info.innerHTML = "Please fill all the fields"
    }
    else{
    const data = {
        1: LoginValue,
        2: PasswordValue
    };
    fetch('/login', {
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
    const info = document.getElementById("info");
    console.log(LoginValue);
    console.log(PasswordValue);
    console.log(Password2Value);
    if (LoginValue === '' || PasswordValue === ''){
        info.innerHTML = "Please fill all the fields"
    }
    else{
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
            if (sessionStorage.getItem('Logo') == 'undefined'){
                document.location.href="mainpage.html?success2=true";
            }
            else{
                document.location.href="artistpage.html?success2=true";
            }
        }
        else{
            const infoElement = document.getElementById('info');
            document.getElementById("myForm").reset();
            infoElement.innerText = 'Wrong password';
        } 
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
}
function getrandomimages() {
    const imageContainer = document.getElementById('images');
    const info = document.getElementById('info');
    const Amount = 5;
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
        for (let i = 2; i <= Amount + 1; i++) {
            const Container = document.createElement('div');
            Container.className = 'image-container';
            console.log(i, responseData[i].imageData);
            const imageInfo = responseData[i].imageData;
            const imageID = responseData[i].id;
            getuserfromimage(imageID, function(artistInfo) {
                console.log('Artist: ' + artistInfo);
                const imgElement = document.createElement('img');
                const artistElement = document.createElement('p');
                artistElement.innerText = 'Artist: ' + artistInfo;
                imgElement.src = imageInfo;
                imgElement.alt = 'Random Image';
                imgElement.width = 200;
                imgElement.height = 200;
                Container.appendChild(imgElement);
                Container.appendChild(artistElement);
                imageContainer.appendChild(Container);
            });
            info.innerHTML="";
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
function getnewimages() {
    const imageContainer = document.getElementById('images');
    const info = document.getElementById('info');
    const Amount = 5;
    console.log(Amount);
    const data = {
        1: Amount
    };

    fetch('/getnewimages', {
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
        for (let i = 2; i <= Amount + 1; i++){
            const Container = document.createElement('div');
            Container.className = 'image-container';
            console.log(i, responseData[i].imageData);
            const imageInfo = responseData[i].imageData;
            const imageID = responseData[i].id;
            getuserfromimage(imageID, function(artistInfo) {
                console.log('Artist: ' + artistInfo);
                const imgElement = document.createElement('img');
                const artistElement = document.createElement('p');
                artistElement.innerText = 'Artist: ' + artistInfo;
                imgElement.src = imageInfo;
                imgElement.alt = 'Random Image';
                imgElement.width = 200;
                imgElement.height = 200;
                Container.appendChild(imgElement);
                Container.appendChild(artistElement);
                imageContainer.appendChild(Container);
            });
            info.innerHTML="";
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
function getuserimages() {
    const imageContainer = document.getElementById('images');
    const userid = document.getElementById('textInput').value;
    const info = document.getElementById('info');
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
        Amount = responseData[0];
        console.log(responseData[0]);
        if (Amount==0){
            const infoElement = document.getElementById('info');
            infoElement.innerText = 'Nothing has been found';
        }
        else{
            for (let i = 2; i <= Amount + 1; i++){
                const imageInfo = responseData[i].imageData;
                const imgElement = document.createElement('img');
                imgElement.src = imageInfo;
                imgElement.alt = 'Random Image';
                imgElement.width = 200;
                imgElement.height = 200; 
                imageContainer.appendChild(imgElement);
                info.innerHTML="";
            }
        } 
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
function gettagimages() {
    const imageContainer = document.getElementById('images');
    const tagid = document.getElementById('selecttag').value;
    const info = document.getElementById('info');
    const number = 5;
    const data = {
        1: number,
        2: tagid
    };
    fetch('/gettagimages', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(responseData => {
        imageContainer.innerHTML = '';
        Amount = responseData[0];
        console.log(responseData[0]);
        console.log(responseData[1]);
        console.log(responseData[2]);
        if (Amount==0){
            const infoElement = document.getElementById('info');
            infoElement.innerText = 'Nothing has been found';
        }
        else{
            for (let i = 2; i <= Amount + 1; i++){
                const Container = document.createElement('div');
                Container.className = 'image-container';
                const imageInfo = responseData[i].imageData;
                const imageID = responseData[i].id;
                getuserfromimage(imageID, function(artistInfo) {
                    console.log('Artist: ' + artistInfo);
                    const imgElement = document.createElement('img');
                    const artistElement = document.createElement('p');
                    artistElement.innerText = 'Artist: ' + artistInfo;
                    imgElement.src = imageInfo;
                    imgElement.alt = 'Random Image';
                    imgElement.width = 200;
                    imgElement.height = 200;
                    Container.appendChild(imgElement);
                    Container.appendChild(artistElement);
                    imageContainer.appendChild(Container);
                });
            }
        }
        info.innerHTML="";
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
function searchyou() {
    const imageContainer = document.getElementById('images');
    const Login = sessionStorage.getItem("Login");
    const Password = sessionStorage.getItem("Password");
    const info = document.getElementById('info');
    const data = {
        1: Login,
        2: Password
    };

    fetch('/getmyimages', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(responseData => {
        imageContainer.innerHTML = '';
        Amount = responseData[0];
        console.log(responseData[0]);

        if (Amount == 0) {
            const infoElement = document.getElementById('info');
            infoElement.innerText = 'Nothing has been found';
        } else {
            for (let i = 2; i <= Amount + 1; i++) {
                const Container = document.createElement('div');
                Container.className = 'image-container';
                const imageInfo = responseData[i].imageData;
                const publicInfo = responseData[i].isPublic;
                const imageId = responseData[i].id;
                console.log(imageId);

                const imgElement = document.createElement('img');
                imgElement.src = imageInfo;
                imgElement.alt = 'Random Image';
                imgElement.width = 200;
                imgElement.height = 200;
                Container.appendChild(imgElement);
                const publicElement = document.createElement('input');
                publicElement.type = 'checkbox';
                publicElement.checked = !publicInfo;
                publicElement.addEventListener('change', () => changeispublic(imageId));
                const publicLabel = document.createElement('label');
                publicLabel.innerText = 'Is Private';
                Container.appendChild(publicElement);
                Container.appendChild(publicLabel);
                imageContainer.appendChild(Container);
            }
        } 
        info.innerHTML="";
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
function changeispublic(imageInfopublic) {
    const imageInfo = imageInfopublic;
    const Login = sessionStorage.getItem("Login");
    const Password = sessionStorage.getItem("Password");
    const data = {
        1: Login,
        2: Password,
        3: imageInfo
    };
    console.log(data);
    fetch('/changeispublic', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(responseData => {
        console.log(responseData[0]);
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
if (urlParams.has('success3')) {
    const infoElement = document.getElementById('info');
    infoElement.innerText = 'Image has been uploaded';
}
let tagList = new Set();

function saveTag(checkbox) {
    if (checkbox.checked) {
        tagList.add(checkbox.value);
        console.log(tagList);
    } else {
        tagList.delete(checkbox.value);
        console.log(tagList);
    }
}
function UploadI() {
    const LoginValue = sessionStorage.getItem('Login');
    const PasswordValue = sessionStorage.getItem('Password');
    const ImageData = sessionStorage.getItem('temppic');
    const ImageName = document.getElementById("imgname").value;
    const IsPrivate = !document.getElementById('private').checked;
    const Amount = tagList.size;
    const data = {
        1: LoginValue,
        2: PasswordValue,
        3: ImageData,
        4: ImageName,
        5: Amount,
        6: IsPrivate
    };
    const tagArray = Array.from(tagList);
    tagArray.forEach((tag, index) => {
        data[index + 7] = tag;
    });
    console.log(data);
    fetch('/createimage', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(responseData => {
        console.log("Received data:");
        console.log("0:", responseData[0]);
        document.location.href="artistpage.html?success3=true";
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
function getuserfromimage(imageId, callback) {
    const data = {
        1: imageId
    };
    fetch('/getuserfromimage', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(responseData => {
        console.log(responseData[1]);
        callback(responseData[1]);
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
