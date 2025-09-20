package greeter;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CountryInfoServlet", urlPatterns = "/CountryInfoServlet")
public class CountryInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        
        String country = request.getParameter("country");
        String celsius = request.getParameter("celsius");
        PrintWriter out = response.getWriter();
        
        if(country == null || country.trim().isEmpty()){
            out.write("error: Please, provide a country name!");  
        } else {
            try {
                CountryInfo countryInfo = new CountryInfo();
                Result result = countryInfo.buildPanel(country, celsius);
                
                String responseText = formatResponse(result);
                out.write(responseText);
            } catch (Exception e) {
                out.write("error: " + e.getMessage());
            }
        }
        
        out.flush();
        out.close();
    }

    private String formatResponse(Result result) {
        if (result == null || result.info == null) {
            return "error: No country information found";
        }
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("Country: ").append(nullToEmpty(result.info.getSName())).append("\n");
        sb.append("ISO Code: ").append(nullToEmpty(result.info.getSISOCode())).append("\n");
        sb.append("Capital: ").append(nullToEmpty(result.info.getSCapitalCity())).append("\n");
        sb.append("Continent: ").append(nullToEmpty(result.info.getSContinentCode())).append("\n");
        sb.append("Currency: ").append(nullToEmpty(result.info.getSCurrencyISOCode())).append("\n");
        sb.append("Phone Code: ").append(nullToEmpty(result.info.getSPhoneCode())).append("\n");
        sb.append("Flag: ").append(nullToEmpty(result.info.getSCountryFlag())).append("\n");
        
        if (result.phoneWords != null && !result.phoneWords.isEmpty()) {
            sb.append("Phone Words: ").append(result.phoneWords).append("\n");
        }
        
        if (result.info.getLanguages() != null && result.info.getLanguages().getTLanguage() != null) {
            String languages = result.info.getLanguages().getTLanguage().stream()
                    .map(l -> nullToEmpty(l.getSName()))
                    .filter(s -> !s.isEmpty())
                    .distinct()
                    .collect(java.util.stream.Collectors.joining(", "));
            if (!languages.isEmpty()) {
                sb.append("Languages: ").append(languages).append("\n");
            }
        }
        
        if (result.celsius != null) {
            sb.append("Celsius: ").append(stripTrailingZeros(result.celsius)).append("°C\n");
            if (result.fahrenheit != null) {
                sb.append("Fahrenheit: ").append(stripTrailingZeros(result.fahrenheit)).append("°F\n");
            }
        }
        

        if (result.tip != null && !result.tip.isEmpty()) {
            sb.append("Travel Tip: ").append(result.tip).append("\n");
        }
        
        return sb.toString();
    }

    private static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    private static String stripTrailingZeros(Double d) {
        if (d == null) return "";
        String s = d.toString();
        if (s.contains(".")) s = s.replaceAll("0+$", "").replaceAll("\\.$", "");
        return s;
    }
}