package greeter;

import java.math.BigInteger;
import java.util.Optional;

import com.soap.ws.client.countryinfo.CountryInfoService;
import com.soap.ws.client.countryinfo.CountryInfoServiceSoapType;
import com.soap.ws.client.countryinfo.TCountryInfo;
import com.soap.ws.client.numberconversion.NumberConversion;
import com.soap.ws.client.numberconversion.NumberConversionSoapType;
import com.soap.ws.client.tempconvert.TempConvert;
import com.soap.ws.client.tempconvert.TempConvertSoap;

public class CountryInfo {
	private final CountryInfoServiceSoapType countryPort;
    private final TempConvertSoap tempPort;
    private final NumberConversionSoapType numberPort;
    
    public CountryInfo() {
    	countryPort = new CountryInfoService().getCountryInfoServiceSoap();
        tempPort    = new TempConvert().getTempConvertSoap();
        numberPort  = new NumberConversion().getNumberConversionSoap();
    }
    
    public Result buildPanel(String countryInput, String celsiusStr) {
    	TCountryInfo info = null;
        String iso2 = null, phoneWords = null, tip = null;
        Double celsius = null, fahrenheit = null;
        
        try {
            iso2 = countryPort.countryISOCode(countryInput);
            if (iso2 == null || iso2.isBlank()) {
                iso2 = countryPort.countryISOCode(
                        Optional.ofNullable(countryInput).orElse("").trim().toUpperCase());
            }
            
            if (iso2 != null && !iso2.isBlank()) {
                info = countryPort.fullCountryInfo(iso2);
            }
        } catch (Exception ignore) {}

        if (celsiusStr != null && !celsiusStr.isBlank()) {
            try {
                celsius = Double.valueOf(celsiusStr.trim());
                String f = tempPort.celsiusToFahrenheit(String.valueOf(celsius));
                fahrenheit = (f != null && !f.isBlank()) ? Double.valueOf(f) : null;
            } catch (Exception ignore) {}
        }
        
        try {
            if (info != null && info.getSPhoneCode() != null) {
                String digits = info.getSPhoneCode().replaceAll("\\D", "");
                if (!digits.isEmpty()) {
                    phoneWords = numberPort.numberToWords(new BigInteger(digits));
                }
            }
        } catch (Exception ignore) {}
        

        if (celsius != null) {
            if (celsius < -10) {
                tip = "Extreme cold! Bundle up heavily 🧣🧤";
            } else if (celsius < 0) {
                tip = "Pack warm clothes ❄️";
            } else if (celsius < 10) {
                tip = "Chilly weather! wear a jacket 🧥";
            } else if (celsius < 20) {
                tip = "Cool day! avoid heavy dress 👕🧥";
            } else if (celsius < 30) {
                tip = "Mild and pleasant ☁️";
            } else if (celsius < 40) {
                tip = "Very warm! stay hydrated ☀️";
            } else {
                tip = "Extreme heat! Avoid staying outside 🔥";
            }
        }

        
        return new Result(info, celsius, fahrenheit, phoneWords, tip);
    }

}
