package com.puxinxiaolin.mybatis.help;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 区分条件是 and 还是 or
 * @Author: YCcLin
 * @Date: 2025/4/9 15:18
 */
@Setter
@Getter
public class Criteria<T> extends GeneratedCriteria<T> {

    // true表示and false表示or
    private boolean andOrOr = true;

    protected Criteria() {
        super();
    }

}
