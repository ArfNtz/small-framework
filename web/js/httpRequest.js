/////////// public

function httpRequestFormSubmit(id_form, id_target, url) {
	form = findElement(id_form);
	if (form) {
		query_string = doFormToQueryString(form);
		method = form.method;
		if (!url) url = form.action;
		httpRequest(method,url,query_string,id_target);
	}
}




//http://www.w3.org/TR/XMLHttpRequest/
function httpRequest(method, url, queryString, id_target, is_asynchrone, onReadyStateFunc, func, is_hide_target, id_wait) {
	var _hr = null, _content = null; 
	if(!is_asynchrone) is_asynchrone=true;
	if(window.XMLHttpRequest) _hr = new XMLHttpRequest(); 
	else if(window.ActiveXObject) _hr = new ActiveXObject('Microsoft.XMLHTTP');
	if (_hr!=null) _hr.open(method,url,is_asynchrone);
//	if (!onReadyStateFunc) onReadyStateFunc = getOnReadyStateFunc(id_target, func, is_hide_target, id_wait);
	_hr.onreadystatechange = function() {
		if (!func) func = majInnerHtml;
		if (!id_wait) id_wait = 'loading';
		elt_target = findElement(id_target);
		elt_wait = findElement(id_wait);
		if (_hr.readyState == 4) {
			if (elt_target&&!is_hide_target) elt_target.style.display='';
			if (elt_wait) elt_wait.style.display='none';
			func(id_target,_hr.responseText);
		} else {
			if (elt_target&&is_hide_target) elt_target.style.display='none';
			if (elt_wait) elt_wait.style.display='';
		}
	}
	if(method == 'POST') _hr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	//_hr.setRequestHeader('Content-Type', 'multipart/form-data');
	//_hr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	//_hr.setRequestHeader("Content-type", "text/html; charset=\"utf-8\"");
	_hr.send(queryString);
}

/////////// private / do not use directly for development purpose unless advice / see public methods above

function getOnReadyStateFunc(id_target, func, is_hide_target, id_wait) { 
	return function(id_target, func, is_hide_target, id_wait) {
	};
}

function majInnerHtml(id,_html) {
	elt = findElement(id);
	if (elt) elt.innerHTML = _html;
}

function doFormToQueryString(form) {
	var params='';
	if (form){
		for (j=0; j<form.elements.length; j++) {
			if (form.elements[j].disabled==false){
				if (form.elements[j].type == 'radio' ||	form.elements[j].type == 'checkbox') {
					if (form.elements[j].checked) params = params +'&'+form.elements[j].name+'='+form.elements[j].value;
				}else{
					params = params +'&'+form.elements[j].name+'='+form.elements[j].value;
				}
			}
		}
	}
	return params;
}


function submitFormWithoutSpecialKeys(event, id_form, id_target, url)
{
	if(!event)
	{
		var event = window.event;
	}
	
	if(!event.shiftKey && !event.altKey && !event.ctrlKey)
	{
		httpRequestFormSubmit(id_form, id_target,url);
	}
}

/////////// private / do not use directly for development purpose unless advice / see public methods above

function getOnReadyStateFunc(id_target, func, is_hide_target, id_wait) { 
	return function(id_target, func, is_hide_target, id_wait) {
	};
}
