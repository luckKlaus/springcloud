package com.luck.cloud;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 特别注意，该接口不能被扫描到，否则会出错
 * Created by luck on 2017/09/28.
 *
 * @param <T>
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
