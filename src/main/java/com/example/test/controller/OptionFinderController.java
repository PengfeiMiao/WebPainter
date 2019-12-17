package com.example.test.controller;

import com.example.test.finder.OptionFinder;
import com.example.test.service.impl.OptionFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mpf
 * @version 1.0
 * @date 2019/12/17 21:40
 */
@RestController
@RequestMapping("/finder")
public class OptionFinderController {
    @Autowired
    OptionFinderService optionFinderService;

    @GetMapping("/filesFinder")
    public List<OptionFinder> getFilesFinder(String creatorId){
        return optionFinderService.getFilesFinder(creatorId);
    }

}
