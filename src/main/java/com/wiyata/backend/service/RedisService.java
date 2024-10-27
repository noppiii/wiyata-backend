package com.wiyata.backend.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    void setData(String key, Object value, Long time, TimeUnit timeUnit);

    Object getData(String key);

    void deleteData(String key);

    void increaseData(String key);
}
