const form = document.forms[0];	
const text = form.querySelectorAll(`input`);	

let textLogin = text[0].querySelector(`label`);	
let textPass = text[1].querySelector(`label`);	

form.addEventListener(`submit`, (el) => {	
  el.preventDefault();	
   if (form.elements.login.value === '') {	
    textLogin.textContent = `Заполните поле`;	
   } else if (form.elements.password.value === '' ) {	
    textPass.textContent = `Заполните поле`;	
   } else {	
    document.location.href = "lab1.html";	
   }	
});