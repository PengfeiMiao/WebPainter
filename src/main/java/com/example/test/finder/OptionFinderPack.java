package com.example.test.finder;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * optionFinder的包装类
 * 可以用作一个finder里面有两级列表的情况
 */
@Getter
@Setter
public class OptionFinderPack {
    /**
     * 当前一级菜单名字
     */
    private String packName;
    private String packId;
    private String packCode;
    /**
     * 二级菜单
     */
    private List<OptionFinder> optionFinders;
}
