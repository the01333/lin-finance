package com.puxinxiaolin.mybatis.help;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Criteria<T> extends GeneratedCriteria<T> {

    // true 表示and false表示or
    private boolean andOrOr = true;

    protected Criteria() {
        super();
    }

}
