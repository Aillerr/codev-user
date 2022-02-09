package com.codev.codevuser.controllers;

import com.codev.codevuser.jwtClasses.JwtTokenUtil;
import com.codev.codevuser.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RequestMapping("/microservices")
@RestController
@CrossOrigin
public class MicroservicesController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Value(value = "${micro.tmpURL}")
    private String tmpURL;
    @Value(value = "${micro.prodURL}")
    private String prodURL;
    @Value(value = "${micro.eco2mixURL}")
    private String eco2mixURL;


    @GetMapping(value = "/eco2mix/hour")
    private ResponseEntity<?> getLastHourEco2mix() {
        String url = eco2mixURL + "eco2mix";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/eco2mix/day")
    private ResponseEntity<?> getLastDayEco2mix() {
        String url = eco2mixURL + "eco2mix/24h";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/prod")
    private ResponseEntity<?> getProd() {
        String url = prodURL + "annualProd";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/prod/{year}")
    private ResponseEntity<?> getProdByYear(@PathVariable("year") int year) {
        String url = prodURL + "annualProd/" + year;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/temperature")
    private ResponseEntity<?> getTemperatures() {
        String url = tmpURL + "tmp";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/temperature/region/{region}")
    private ResponseEntity<?> getTempByRegion(@PathVariable("region") String region) {
        String url = tmpURL + "tmp/region/" + region;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/temperature/year/{year}")
    private ResponseEntity<?> getTempByYear(@PathVariable("year") String year) {
        String url = tmpURL + "tmp/year/" + year;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/temperature/yearRegion/{year}/{region}")
    private ResponseEntity<?> getProdByYear(@PathVariable("year") int year, @PathVariable("region") String region) {
        String url = tmpURL + "tmp/yearRegion/" + year + "/" + region;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(result);
    }
}
