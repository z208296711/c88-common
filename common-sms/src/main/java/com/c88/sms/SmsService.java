package com.c88.sms;

import com.c88.sms.impl.AbenlaProvider;
import com.c88.sms.impl.NuageProvider;
import com.c88.sms.impl.OnSmsVoiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
public class SmsService {

    @Autowired
    private OnSmsVoiceProvider onSmsVoiceProvider;
    @Autowired
    private AbenlaProvider abenlaProvider;
    @Autowired
    private NuageProvider nuageProvider;

    private List<SmsProvider> providers;

    @PostConstruct
    public void init() {
        providers = List.of(onSmsVoiceProvider, abenlaProvider, nuageProvider);
    }

    public SmsResultEnum sendSms(String phone, String random5digit) {
        try {
            return providers.get(RandomUtils.nextInt(0, providers.size())).sendSms(phone, random5digit);
        } catch (Exception e) {
            log.error("Send Sms Failed {}", e);
            throw e;
        }
    }

}
