/////////// public
function show_toolTip(event,msg) {
	var elt = findElement('toolTip');
	if (msg) elt.innerHTML = msg;
	switchDisplay('toolTip',true);
	positionSouris(event,'toolTip');
}
function hide_toolTip(){
	switchDisplay('toolTip',false);
}
function switchDisplay(ids,value) {
	if (id instanceof Array) {
		for (i in id) {
	    	switchDisplay(i,value);
		}
	} else switchDisplay(ids,value);
}
function positionSouris(event,id) {
	elt = findElement(id);
	var x;var y;
	if(document.all){//IE
		x = event.x + document.body.scrollLeft + 20;
		y = event.y + document.body.scrollLeft + 10;
	}else{
		x = event.clientX + document.body.scrollLeft + 20;
		y = event.clientY + document.body.scrollLeft + 10;
	}		
	position(elt,x,y,'px');
}
function findElement(id) {
	var elt;
	if (elt = document[id]){
		return elt;
	}
	if (document.all && (elt = document.all[id])){
		return elt;
	}
	if (document.getElementById){
		return document.getElementById(id);
	}
	return null;
}

/////////// private / do not use directly for development purpose unless advice / see public methods above
function switchDisplay(id,value) {
	elt = findElement(id);
	if (elt) {
      if (value!=null) {
    	 elt.style.display=value?'':'none';
    	 //elt.style.visibility=value?'visible':'hidden';
      } else {
    	 elt.style.display = elt.style.display=='none'?'':'none';
    	 //elt.style.visibility = elt.style.visibility=='hidden'?'visible':'hidden';
      }
	}
}
function displayOnly(others,only) {
  for (i in others) {
  	elt = findElement(others[i]);
  	if (elt) elt.style.display = 'none';
  }
 	elt = findElement(only);
  if (elt) elt.style.display = '';
}
function position(elt,x,y,unit) {
	if (elt) {
		var wi = document.body.clientWidth;
		var w=elt.clientWidth;
		if (x + w > wi)	{ x = x-w-40; }
		elt.style.left=x+unit;
		elt.style.top=y+unit;
	}
}
function setFormsElementsDisabled(b) {
	for (i=0; i<document.forms.length; i++) {
		for (j=0; j<document.forms[i].elements.length; j++) {
			document.forms[i].elements[j].disabled=b;
		}
	}
}

function switchCheckbox(id,value) {
	var elt = findElement(id);
	if (elt) {
        if (value!=null) elt.checked=value;
        else elt.checked=!elt.checked;
	}
}
function switchBoolean(id,value) {
	var elt = findElement(id);
	if (elt) {
        if (value!=null) elt.value = value;
        else elt.value = elt.value=='true'?'false':'true';
	}	
}
function isChecked(idCheckBox, idHidden){
	var chekckBox = findElement(idCheckBox);
	var hidden = findElement(idHidden);
	if(chekckBox && hidden) hidden.value = chekckBox.checked;
	else hidden.value = false;
}
