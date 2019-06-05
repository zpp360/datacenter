package com.shuheng.datacenter.controller;

import com.shuheng.datacenter.entity.PageData;
import com.shuheng.datacenter.server.VolunteerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/volunteer")
public class VolunteerController {

    @Resource(name = "volunteerService")
    private VolunteerService volunteerService;

    @GetMapping("/listVolunteer")
    public Object listVolunteer() throws Exception {
        PageData pd = new PageData();
        List<PageData> list = volunteerService.listAll(pd);
        return list;
    }

}
