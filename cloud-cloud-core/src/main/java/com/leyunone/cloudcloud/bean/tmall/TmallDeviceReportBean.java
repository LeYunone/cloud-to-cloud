package com.leyunone.cloudcloud.bean.tmall;

import lombok.*;

/**
 * :)
 *
 * @Author Leyunone
 * @Date 2024/1/18 14:33
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TmallDeviceReportBean {

    /**
     * 技能id,厂商在天猫精灵IoT平台创建技能的id(必填)
     */
    private String skillId;

    /**
     * 每次请求的消息id,由厂商云在发起每次请求时生成全局唯一消息id,方便问题定位排查 (必填)
     */
    private String messageId;

    /**
     * 设备id,表示当前同步的的该设备的状态 (必填)
     */
    private String deviceId;

    /**
     * 上报类型，1：属性上报 2：在离线上报 3：事件上报。这里是属性状态同步，因此固定为1。(必填)
     */
    private Integer reportType;

    /**
     * 设备所有属性的当前状态值组成的Map对象的Json字符串，参照设备对应的产品功能定义。(必填)
     */
    private String payload;
    
    private Integer payloadVersion;
    
    private Integer accountType;

    /**
     * Oauth授权颁发给天猫精灵云的token。当account_type=1时，user_access_token必填。
     */
    private String userAccessToken;

    /**
     *  Oauth授权颁发给天猫精灵云的open_id。当account_type=2时，open_user_id必填。
     */
    private String openUserId;
    
    private String extension;

    /**
     * 时间戳 毫秒
     */
    private Long timeStamp;
}
