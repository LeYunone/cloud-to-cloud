package com.leyunone.cloudcloud.api.protocol;

import com.leyunone.cloudcloud.bean.enums.ProtocolCommandEnum;
import com.sun.istack.internal.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/24 11:47
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MyCompanyProtocolDTO implements Serializable {

    private ProtocolCommandEnum command;

    private String clientId;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ControlCommand extends MyCompanyProtocolDTO implements Serializable {
        private List<MyCompanyCommand> commands;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class QueryCommand extends MyCompanyProtocolDTO implements Serializable {
        private List<Long> deviceIds;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DiscoveryCommand extends MyCompanyProtocolDTO implements Serializable {
        //第三方家庭
        private String thirdHomeId;
        //关联第三方协议的业务id
        @NotNull
        private String businessId;
        private String myComUserId;
        private Integer appId;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class UnbindAuthCommand extends MyCompanyProtocolDTO implements Serializable {
        private List<Long> deviceIds;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class SyncCommand extends MyCompanyProtocolDTO implements Serializable {
        private List<Long> deviceIds;
    }
}
