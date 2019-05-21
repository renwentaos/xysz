package com.xws.xysz.util;

import com.xws.xysz.config.SystemConfig;
import lombok.Getter;

/**
 * 分页相关工具
 * Created by yangg on 2018/11/21.
 */
public class PageUtil {
    private PageUtil(){}
    @Getter
    private int pageNo = 1;
    @Getter
    private int pageSize = SystemConfig.PAGE_SIZE;


    /**
     * 页码信息处理（检查是否符合系统设置的值，不符合时候修正）
     * @param no 页码
     * @param size 每页显示数量
     * @return 修正后的页码信息
     */
    public static PageUtil pageHandle(String no,String size){
        PageUtil pageUtil = new PageUtil();
        //判断页码与显示数量是否正确
        if (StringUtil.isPositiveInteger(no)) {
            pageUtil.pageNo = Integer.parseInt(no);
            if (pageUtil.pageNo < 1) {
                pageUtil.pageNo = 1;
            }
        }
        if (StringUtil.isPositiveInteger(size)) {
            pageUtil.pageSize = Integer.parseInt(size);
            if (pageUtil.pageSize < SystemConfig.PAGE_SIZE) {
                pageUtil.pageSize = SystemConfig.PAGE_SIZE;
            }
        }
        return pageUtil;
    }
}
