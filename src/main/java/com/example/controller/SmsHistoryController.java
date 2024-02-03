package com.example.controller;

import com.example.service.SmsServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SmsHistoryController {

    @Autowired
    private SmsServerService smsServerService;

}
