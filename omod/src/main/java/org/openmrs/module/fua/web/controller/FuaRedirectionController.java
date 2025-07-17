package org.openmrs.module.fua.web.controller;

import org.openmrs.module.fua.web.utils.MultipartInputStreamFileResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

@Controller
public class FuaRedirectionController {

    @RequestMapping(value = "/FUAFormat", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> redirectFuaRequest(
            @RequestParam("name") String name,
            @RequestParam("createdBy") String createdBy,
            @RequestParam("formatPayload") MultipartFile formatPayload
    ) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.set("token", "soyuntokenxd");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", name);
        body.add("createdBy", createdBy);
        body.add("formatPayload", new MultipartInputStreamFileResource(
                formatPayload.getInputStream(),
                formatPayload.getOriginalFilename(),
                formatPayload.getSize()
        ));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:3000/ws/FUAFormat", requestEntity, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
