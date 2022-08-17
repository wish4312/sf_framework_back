package com.lsitc.core.dao;

import java.util.List;

public interface ICommonDao {
    <T> List<T> selectList(String queryId, Object param);
    <T> T selectOne(String queryId, Object param);
    int insert(String queryId, Object param);
    int update(String queryId, Object param);
    int delete(String queryId, Object param);
//    <T> void batchUpdate(String queryId, List<T> list);
}