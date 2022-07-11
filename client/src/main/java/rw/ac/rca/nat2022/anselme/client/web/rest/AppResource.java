package rw.ac.rca.nat2022.anselme.client.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.InternetDomainName;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import rw.ac.rca.nat2022.anselme.client.dao.Link;
import rw.ac.rca.nat2022.anselme.client.utils.ApiResponse;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static rw.ac.rca.nat2022.anselme.client.utils.Utility.formatURL;

@Controller
@RequestMapping
public class AppResource {

    @GetMapping
    public String index() {
        return "index";
    }

    public static void DownloadWebPage(String webpage)
    {
        try {
            String urlString = webpage;
            URI uri = new URI(urlString);
            String host = uri.getHost();
            InternetDomainName internetDomainName = InternetDomainName.from(host).topPrivateDomain();
            String domainName = internetDomainName.toString();
            // Create URL object
            System.out.println(webpage);
            URL url = new URL(webpage);
            BufferedReader readr =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            // Enter filename in which you want to download
            File theDir = new File("C:/Users/USER/Documents/nat2022-java-starter-final/client/"+domainName+"/");
            if (!theDir.exists()){
                theDir.mkdirs();
            }
            BufferedWriter writer =
                    new BufferedWriter(new FileWriter(theDir+"/index.html"));

            // read each line from stream till end
            String line;
            while ((line = readr.readLine()) != null) {
                writer.write(line);
            }

            readr.close();
            writer.close();
            System.out.println("Successfully Downloaded.");
        }

        // Exceptions
        catch (MalformedURLException mue) {
            System.out.println("Malformed URL Exception raised");
        }
        catch (IOException ie) {
            System.out.println("IOException raised");
            System.out.println(ie.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("register")
    public String storeUser(HttpServletRequest request, Model model) throws JsonProcessingException {
        System.out.println("here we are");
        try {
            RestTemplate restTemplate = new RestTemplate();

            Map<String, String> requestBody = new HashMap<>();

            for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
                requestBody.put(entry.getKey(), entry.getValue()[0]);
            }

            ResponseEntity<ApiResponse> res = restTemplate.postForEntity(formatURL("/api/links"), requestBody, ApiResponse.class);
            ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
            Link link = mapper.convertValue(res.getBody().getData(), Link.class);
            DownloadWebPage(link.getUrl());
            return "redirect:/?registered=true";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ApiResponse response = new ObjectMapper().readValue(e.getMessage().substring(7, e.getMessage().length() - 1), ApiResponse.class);
            System.out.println(response.getMessage());
            model.addAttribute("error", response.getMessage());

            return "index";
        }
    }
}
