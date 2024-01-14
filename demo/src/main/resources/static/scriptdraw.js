const container = document.querySelector('.container')
const color = document.querySelector('.color')
const resetBtn = document.getElementById('reset')
const eraserBtn = document.getElementById('eraser')
let draw = false
let eraserMode = false;

function createcanvas() {
  for (let i = 0; i < 81; i++) {
    const div = document.createElement('div')
    div.classList.add('pixel')
    div.addEventListener('mouseover', function(){
        if(!draw) return
        div.style.backgroundColor = eraserMode ? 'white' : color.value;
    })
    div.addEventListener('mousedown', function(){
        div.style.backgroundColor = eraserMode ? 'white' : color.value;
    })
    container.appendChild(div)
  }
}

window.addEventListener("mousedown", function(){
    draw = true
})
window.addEventListener("mouseup", function(){
    draw = false
})

resetBtn.addEventListener('click', function(){
    container.innerHTML = ''
    createcanvas()
});

eraserBtn.addEventListener('click', function () {
    eraserMode = true;
    eraserBtn.classList.toggle('active', eraserMode);
});

color.addEventListener('click', function () {
    eraserMode = false; 
    eraserBtn.classList.remove('active');
});

createcanvas()

function convert() {
    const LoginValue = sessionStorage.getItem('Login');
    const PasswordValue = sessionStorage.getItem('Password');
    const LogoValue = sessionStorage.getItem('Logo');
    const data = {
        1: LoginValue,
        2: PasswordValue,
        3: LogoValue
    };
    fetch('/convert', {
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
            const infoElement = document.getElementById('info');
            infoElement.innerText = 'Logo zostało utworzone!';
        }
        else{
            const infoElement = document.getElementById('info');
            infoElement.innerText = 'Wystąpił błąd';
        } 
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
function changelogo(){
const LoginValue = sessionStorage.getItem('Login');
const PasswordValue = sessionStorage.getItem('Password');
const NicknameValue = sessionStorage.getItem("Nickname");
const LogoValue = sessionStorage.getItem('Logo');
const data = {
    1: LoginValue,
    2: PasswordValue,
    3: NicknameValue,
    4: LogoValue
};

fetch('/changdetail', {
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
        const infoElement = document.getElementById('info');
        infoElement.innerText = 'Logo was updated!';
    }
    else{
        const infoElement = document.getElementById('info');
        infoElement.innerText = 'Error';
    } 
})
.catch(error => {
    console.error('Error:', error);
});
}
function downloadImage(data, filename) {
    const a = document.createElement('a');
    a.href = data;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
}
function returnp(){
    if (sessionStorage.getItem('Logo') == 'undefined'){
        document.location.href="mainpage.html";
    }
    else{
        document.location.href="artistpage.html";
    }
}