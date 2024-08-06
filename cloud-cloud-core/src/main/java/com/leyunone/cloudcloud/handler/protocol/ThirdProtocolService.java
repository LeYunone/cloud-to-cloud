package com.leyunone.cloudcloud.handler.protocol;

import com.leyunone.cloudcloud.api.ThirdHouseVO;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/18 13:45
 */
public interface ThirdProtocolService {

    List<ThirdHouseVO> getThirdHouse(Integer appId, String clientId, String myComHomeId);
}
