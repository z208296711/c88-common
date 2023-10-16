package com.c88.sms;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * SEND MAIL IN THREAD POOL
 */
@Slf4j
@Component
public class MailService {

    @Value(value = "${mailuser}")
    private String mailServiceUser;
    @Value(value = "${mailpsw}")
    private String mailServicePsw;
    @Value(value = "${mailurl}")
    private String mailUrl;

    private RestTemplate restTemplate;
    private HttpHeaders headers;

    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        this.headers.setBasicAuth(mailServiceUser, mailServicePsw);
        this.headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    }

    @Async
    public CompletableFuture<Boolean> sendMail(MailUnit mailUnit) {
        log.info(mailUnit.toString());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(mailUnit.genReq(), headers);

        try {
            ResponseEntity<MailGunRes> responseEntity = restTemplate.postForEntity(mailUrl, requestEntity, MailGunRes.class);
            if (responseEntity.getStatusCode().isError()) return CompletableFuture.completedFuture(false);

            return CompletableFuture.completedFuture(true);
        } catch (RestClientException e) {
            log.error("MailGun error:", e);
            return CompletableFuture.completedFuture(false);
        }

    }

    @Getter
    @Builder
    public static class MailTrunk {
        private String mailSubject;
        private String mailBody;
        // must end with "@mail.cmb68.vip" ex: support@mail.cmb68.vip
        private String sender = "support@mail.cmb68.vip";
        @Singular
        private List<String> targets;

        public MailTrunk addTargets(String target) {
            if (targets == null) targets = new ArrayList<>();
            targets.add(target);
            return this;
        }

        public List<MailUnit> genMailUnits() {
            return targets.stream()
                    .map(target -> new MailUnit().setFrom(this.sender)
                            .setTo(target)
                            .setSubject(this.mailSubject)
                            .setText(this.mailBody)
                    )
                    .collect(Collectors.toList());
        }
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    public static class MailUnit {
        // must end with "@mail.cmb68.vip" ex: support@mail.cmb68.vip
        private String from = "support@mail.cmb68.vip";
        private String to;
        private String subject;
        private String text;

        public MultiValueMap<String, String> genReq() {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("from", from);
            map.add("to", to);
            map.add("subject", subject);
            map.add("text", text);
            return map;
        }
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    public static class MailGunRes {
        private String id;
        private String message;
    }
}
