package com.leyunone.cloudcloud.service.report;


import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface ReportMessageReportShuntHandler {

    void messageShunt(String msg);

    void messageShunt(List<String> userIds);
}
