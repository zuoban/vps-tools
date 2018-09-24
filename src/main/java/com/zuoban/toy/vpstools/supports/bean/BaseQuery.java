package com.zuoban.toy.vpstools.supports.bean;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基础查询对象，包含分页条件
 *
 * @author wangjinqiang
 * @date 2018-09-19
 */
@Data
public class BaseQuery {
    /**
     * 第几页
     */
    private Integer page = 0;

    /**
     * 每页显示几条
     */
    private Integer size = 10;

    /**
     * 搜索关键词
     */
    private String keywords;

    /**
     * 其它条件
     */
    private String ext;

    private String order;

    public Pageable toPageable() {
        // 若 size 小于0 则取最大值
        int size = this.size > 0 ? this.size : Integer.MAX_VALUE;

        if (StrUtil.isBlank(order)) {
            return PageRequest.of(page, size);
        }

        String[] split = order.split(",");
        List<Sort.Order> orderList = Arrays.stream(split).filter(StrUtil::isNotBlank).map(it -> {
            String[] sortItems = it.trim().split("\\s+");
            Sort.Order order;
            String column = sortItems[0];

            boolean startsWithMinus = column.startsWith("-");

            if (startsWithMinus) {
                column = column.substring(1);
            }

            if (sortItems.length == 1) {
                order = new Sort.Order(Sort.DEFAULT_DIRECTION, column);
            } else {
                order = new Sort.Order("desc".equalsIgnoreCase(sortItems[1]) ? Sort.Direction.DESC : Sort.Direction.ASC, column);
            }

            return startsWithMinus ? order.nullsLast() : order;
        }).collect(Collectors.toList());
        return PageRequest.of(page, size, Sort.by(orderList));
    }

    public Map ext2Map() {
        if (JSONUtil.isJsonObj(ext)) {
            return JSONUtil.parseObj(ext);
        }
        return null;
    }

}
