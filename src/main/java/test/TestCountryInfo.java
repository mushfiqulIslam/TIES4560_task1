package test;

import greeter.CountryInfo;

public class TestCountryInfo {
	public static void main(String[] args) {
		CountryInfo countryInfo = new  CountryInfo();
		var result = countryInfo.buildPanel("Bangladesh", "33");
		if (result.info != null) {
            System.out.println("Country: " + result.info.getSName());
            System.out.println("Capital: " + result.info.getSCapitalCity());
            System.out.println("Currency: " + result.info.getSCurrencyISOCode());
            var languages = result.info.getLanguages().getTLanguage();
            
            if (languages != null && !languages.isEmpty()) {
                for (com.soap.ws.client.countryinfo.TLanguage lang : languages) {
                    System.out.println("Language: " + lang.getSName()
                                       + " (" + lang.getSISOCode() + ")");
                }
            } else {
                System.out.println("No languages found.");
            }
        }
		
		if (result.celsius != null) {
            System.out.println(result.celsius + "°C = " + result.fahrenheit + "°F");
        }
        if (result.phoneWords != null) {
            System.out.println("Phone code in words: " + result.phoneWords);
        }
        if (result.tip != null) {
            System.out.println("Tip: " + result.tip);
        }
	}
}
