package com.example.test.service;

import com.example.test.finder.OptionFinder;

import java.util.List;

/**
 * @author mpf
 * @version 1.0
 * @date 2019/12/17 20:28
 */
public interface IOptionFinderService {

    List<OptionFinder> getFilesFinder(String creatorId);

}
