package greeter;

import com.soap.ws.client.countryinfo.TCountryInfo;

public class Result {
	public final TCountryInfo info;
    public final Double celsius, fahrenheit;
    public final String phoneWords;
    public final String tip;
    
	public Result(TCountryInfo info, Double celsius, Double fahrenheit,
            String phoneWords, String tip) {
		this.info = info;
        this.celsius = celsius;
        this.fahrenheit = fahrenheit;
        this.phoneWords = phoneWords;
        this.tip = tip;
		
	}

}
