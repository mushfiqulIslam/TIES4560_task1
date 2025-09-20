window.onload = function() {
    // Initialize if needed
};

function getCountryInfo() {
    var country = document.getElementById('countryInput').value;
    var celsius = document.getElementById('celsiusInput').value;
    
    var q_str = 'country=' + encodeURIComponent(country);
    if (celsius && celsius.trim() !== '') {
        q_str += '&celsius=' + encodeURIComponent(celsius);
    }
    
    doAjax('CountryInfoServlet', q_str, 'getCountryInfoBack', 'post', 0);
}

function getCountryInfoBack(result) {
    var resultDiv = document.getElementById('result');
    
    if (result.substring(0, 5) == 'error') {
        resultDiv.innerHTML = "<span class='error'>" + result.substring(6) + "</span>";
    } else {
        resultDiv.innerHTML = "<pre>" + result + "</pre>";
    }
}