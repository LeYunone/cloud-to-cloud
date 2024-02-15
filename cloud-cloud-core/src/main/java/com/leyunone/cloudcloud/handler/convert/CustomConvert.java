package com.leyunone.cloudcloud.handler.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.CustomConvertModel;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.handler.factory.ConvertHandlerFactory;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import com.leyunone.cloudcloud.util.UniqueShortStringUtils;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2023/12/14 14:48
 */
@Service
public class CustomConvert extends AbstractDataConvertHandler<JSONObject, CustomConvertModel> {

    /**
     * 属性：值 分隔符
     */
    public static final String delimiter = "&";

    /**
     * 代码占位符
     */
    public static final String placeholder = "@";

    @Autowired
    private CacheManager cacheManager;

    private final Logger logger = LoggerFactory.getLogger(CustomConvert.class);

    protected CustomConvert(ConvertHandlerFactory factory, ProductMappingService productMappingService) {
        super(factory, productMappingService);
    }

    /**
     * 根据值映射公式构建定制体
     *
     * @param customModel
     * @return
     */
    @Override
    public JSONObject convert(CustomConvertModel customModel) {
        if (StrUtil.isBlank(customModel.getMappingTemplate())) return new JSONObject();
        List<DeviceFunctionDTO> deviceFunctions = customModel.getDeviceInfo().getDeviceFunctions();
        Map<String, Map<String, Object>> statusMap = deviceFunctions.stream().collect(Collectors.toMap(DeviceFunctionDTO::getSignCode, t -> BeanUtil.beanToMap(t), (k1, k2) -> k1));
        Object o = iteratorMapping(customModel.getMappingTemplate(), statusMap);
        return (JSONObject) o;
    }

    private Object iteratorMapping(String json, Map<String, Map<String, Object>> statusMap) {
        JSONObject result = new JSONObject();
        String keyValue = "";
        try {
            keyValue = json;
            JSONObject jsonObject = JSONObject.parseObject(json);
            for (String key : jsonObject.keySet()) {
                result.put(key, iteratorMapping(jsonObject.getString(key), statusMap));
            }
        } catch (JSONException e) {
            /**
             * 非json字符串 终止符
             * 判断：如果存在占位符号$ 则执行代码级三目运算;
             *      否则映射实际值
             * 详细见 {@link MappingTest}
             */
            Object value = null;
            if (keyValue.contains(placeholder)) {
                value = this.compilerMappingCode(keyValue.split("@")[1], this.slicingStatus(keyValue, statusMap));
            } else {
                if (keyValue.contains(delimiter)) {
                    //如果是字符串 并且包含属性赋值符 填充对应属性值
                    String[] split = keyValue.split(delimiter);
                    Map<String, Object> codeValue = statusMap.get(split[0]);
                    value = ObjectUtil.isNotNull(codeValue) ? codeValue.get(split[1]) : null;
                }else{
                    //字符串值
                    value = keyValue;
                }
            }

            return value;
        }
        return result;
    }

    /**
     * 切割属性值
     *
     * @param keyValue
     * @param statusMap
     */
    private Map<String, Object> slicingStatus(String keyValue, Map<String, Map<String, Object>> statusMap) {
        Map<String, Object> result = new HashMap<>();

        try {
            String[] split = keyValue.split(placeholder);
            String keyMapString = split[0];
            String[] keyMap = keyMapString.replaceAll(" ", "").split(",");

            for (String key : keyMap) {
                String[] codeMapping = key.split(delimiter);
                Map<String, Object> status = statusMap.get(codeMapping[0]);
                String[] valueMapping = codeMapping[1].split("=");
                Object statusValue = status.get(valueMapping[0]);
                if (ObjectUtil.isNotNull(statusValue)) {
                    result.put(valueMapping[1], statusValue);
                }
            }
        } catch (Exception e) {
        }
        result.put("date", LocalDateTime.now());
        return result;
    }

    /**
     * 定义:
     * 根据自定义映射规则生产 Jexl表达式：
     * 采用自带的脚本ScriptEngine 语言进行虚拟机级别的字符串编译；
     * FIXME 缓存Jexl表达式，减少虚拟机开销
     *
     * @param mappingValue
     * @param map          值映射
     * @return
     */
    private Object compilerMappingCode(String mappingValue, Map<String, Object> map) {
        // 创建编译参数
        MapContext context = new MapContext();
        map.keySet().forEach(key -> {
            context.set(key, map.get(key));
        });
        // 创建运行环境
        Engine engine = new Engine();
        // 执行代码
        JexlExpression expression = engine.createExpression(mappingValue);
        return expression.evaluate(context);
    }

    /**
     * 定义：
     * 根据自定义映射规则 动态生产不可删除的class文件，并且进行缓存
     *
     * @param mappingValue
     * @param value
     * @return
     */
    @Deprecated
    private Object compilerMappingCodeDeprecated(String mappingValue, Integer value) {
        //唯一短串设置编辑文件缓存
        String shortString = UniqueShortStringUtils.generate(mappingValue);
        String cache = cacheManager.getData(shortString, String.class);
        String runPath = "";
        Object result = null;
        if (StrUtil.isBlank(cache)) {
            StringBuilder sb = new StringBuilder();
            sb.append("public class CustomMappingCompiler { public Object mapping(Integer value) { return ");
            sb.append(mappingValue);
            sb.append(";}}");
            File tempFile = new File("CustomMappingCompiler.java");
            try {
                FileWriter writer = new FileWriter(tempFile);
                writer.write(sb.toString());
                writer.close();
                runPath = tempFile.getPath();
            } catch (Exception e) {
            }
            cacheManager.addDate(shortString, runPath);
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, runPath);

        Class<?> customMappingCompiler = null;
        try {
            customMappingCompiler = Class.forName("CustomMappingCompiler");
            Object o = customMappingCompiler.getDeclaredConstructor().newInstance();
            result = customMappingCompiler.getMethod("mapping", Integer.class).invoke(o, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
