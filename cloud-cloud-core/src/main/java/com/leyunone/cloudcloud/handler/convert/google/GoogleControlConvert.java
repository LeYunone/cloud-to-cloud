package com.leyunone.cloudcloud.handler.convert.google;

import cn.hutool.core.util.ObjectUtil;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.third.google.GoogleControlRequest;
import com.leyunone.cloudcloud.bean.third.google.GoogleDevice;
import com.leyunone.cloudcloud.bean.mapping.ActionMapping;
import com.leyunone.cloudcloud.bean.mapping.GoogleProductMapping;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/28 9:45
 */
@Service
public class GoogleControlConvert extends AbstractGoogleDataConverterTemplate<List<DeviceFunctionDTO>, GoogleControlRequest> {

    protected GoogleControlConvert(ProductMappingService productMappingService) {
        super(productMappingService);
    }

    /**
     * 四层循环
     *
     * @param googleControlRequest
     * @return
     */
    @Override
    public List<DeviceFunctionDTO> convert(GoogleControlRequest googleControlRequest) {
        GoogleControlRequest.Input input = googleControlRequest.getInputs().get(0);
        List<GoogleControlRequest.Command> commands = input.getPayload().getCommands();
        List<DeviceFunctionDTO> functionCodeCommands = new ArrayList<>();
        //一次发出不同的操作指令
        commands.forEach(command -> {
            List<GoogleDevice> devices = command.getDevices();
            List<String> pids = devices.stream().map(t -> t.getCustomData().getProductId()).collect(Collectors.toList());
            List<ProductMapping> mapping = productMappingService.getMapping(pids, ThirdPartyCloudEnum.GOOGLE);
            Map<String, GoogleProductMapping> productMappingMap = super.convertToMapByProductId(mapping);
            List<GoogleControlRequest.Execution> execution = command.getExecution();
            //一次操作多个设备
            devices.stream().filter(d -> productMappingMap.containsKey(d.getCustomData().getProductId())).forEach(d -> {
                GoogleProductMapping googleProductMapping = productMappingMap.get(d.getCustomData().getProductId());
                List<ActionMapping> actionMappings = googleProductMapping.getActionMappings();
                /**
                 * 谷歌字段:
                 * thirdSignCode_command
                 *  存在一个指令控制多个属性
                 * colorSetting:  action.devices.commands.ColorAbsolute 
                 *                  "color": {
                 *                        "name": "Warm White",
                 *                        "temperature": 3000,
                 *                        "spectrumRGB": 16711935,
                 *                        "spectrumHSV": {
                 *                             "hue": 300,
                 *                             "saturation": 1,
                 *                             "value": 1
                 *                           }
                 *                        }
                 */

                Map<String, List<ActionMapping>> actionMaps = CollectionFunctionUtils.groupTo(actionMappings, ActionMapping::getThirdActionCode);
                //一次发出多个动作
                execution.forEach(action -> {
                    List<ActionMapping> actionMap = actionMaps.get(action.getCommand());
                    //一个动作映射我方云多个操作
                    actionMap.forEach(actionMapping -> {
                        Object value = super.getControlValue(action.getParams(), actionMapping);
                        if (ObjectUtil.isNull(value)) return;
                        DeviceFunctionDTO codeCommand = new DeviceFunctionDTO();
                        codeCommand.setSignCode(actionMapping.getSignCode());
                        codeCommand.setValue(String.valueOf(value));
                        codeCommand.setFunctionId(actionMapping.getFunctionId());
                        codeCommand.setOperation(actionMapping.getOperation());
                        codeCommand.setDeviceId(d.getId());
                        functionCodeCommands.add(codeCommand);
                    });

                });
            });

        });
        return functionCodeCommands;
    }


}
