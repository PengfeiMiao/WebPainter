package com.example.test.service.impl;

import com.example.test.entity.CommonFile;
import com.example.test.finder.OptionFinder;
import com.example.test.finder.OptionFinderPack;
import com.example.test.mapper.FileMapper;
import com.example.test.service.IOptionFinderService;
import com.example.test.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mpf
 * @version 1.0
 * @date 2019/12/17 20:31
 */
@Service
public class OptionFinderService implements IOptionFinderService {

    @Autowired
    FileMapper fileMapper;

    @Override
    public List<OptionFinder> getFilesFinder(String creatorId) {

        List<CommonFile> list = fileMapper.selectFileByCreator(creatorId);
        List<OptionFinder> result = addBlankOption(true);
        for(CommonFile item : list){
            result.add(new OptionFinder(item.getId(), item.getFilename()+"_"
                    + TimeUtil.dateToString(item.getUploadtime(), TimeUtil.getYearMonthDayHourMinPattern())));
        }
        return result;
    }

    /**
     * 添加空白选项（当为二级菜单时）
     *
     * @param blankOption
     * @return
     */
    private List<OptionFinderPack> addBlankOptionInPack(Boolean blankOption) {
        List<OptionFinderPack> resultList = new ArrayList<>();
        if (blankOption != null && blankOption) {
            OptionFinderPack optionFinderPack = new OptionFinderPack();
            List<OptionFinder> optionFinderList = addBlankOption(true);

            optionFinderPack.setOptionFinders(optionFinderList);
            optionFinderPack.setPackId("");
            optionFinderPack.setPackName("");

            resultList.add(optionFinderPack);
        }
        return resultList;
    }

    /**
     * 添加空白选项
     *
     * @param blankOption
     * @return
     */
    private List<OptionFinder> addBlankOption(Boolean blankOption) {
        List<OptionFinder> optionFinders = new ArrayList<>();
        if (blankOption != null && blankOption) {
            optionFinders.add(new OptionFinder("", ""));
        }
        return optionFinders;
    }

}
