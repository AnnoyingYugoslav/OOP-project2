const container = document.querySelector('.container')
const color = document.querySelector('.color')
const resetBtn = document.getElementById('reset')
const eraserBtn = document.getElementById('eraser')
const saveButton = document.getElementById('save-button');
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

saveButton.addEventListener('click', function () {
    html2canvas(container, { scale: 5 }).then(canvas => {
        const base64Image = canvas.toDataURL('image/png');
        // downloadImage(base64Image, 'pixel_art.png');
        // Save the base64 image to sessionStorage
        sessionStorage.setItem('Logo', base64Image);
        convert();
        // Optionally, you can also display the image or perform other actions
        // For example, you can set the src attribute of an image element with the saved image
        // const savedImageElement = document.getElementById('saved-image');
        // savedImageElement.src = base64Image;
    });
});
function convert() {
    // Get the field values
    const LoginValue = sessionStorage.getItem('Nickname');
    const PasswordValue = sessionStorage.getItem('Password');
    const LogoValue = sessionStorage.getItem('Logo');

    // Create a JavaScript object with the field values
    const data = {
        1: LoginValue,
        2: PasswordValue,
        3: LogoValue
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