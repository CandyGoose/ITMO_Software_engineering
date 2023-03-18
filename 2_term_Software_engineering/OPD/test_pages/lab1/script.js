document.querySelectorAll('input').forEach(el => {	
    el.addEventListener('blur', () => {	
      if (el.value.length == 0){	
        showErr(el);	
      }	
    });	
});	

function showErr(field, errText) {	
    if (field.nextElementSibling && field.nextElementSibling.textContent == errText) {	
        return;	
    }	

    field.classList.add('loginerr');	

    const err = document.createElement('span');	
    field.after(err);	
    err.textContent = errText;	
    hideErr(field, err);	
}	

function hideErr(field, err) {	
    field.addEventListener('input', () => {	
        field.classList.add('loginerr');	
        err.remove();	
      });	
}