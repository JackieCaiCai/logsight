package cn.fishmaple.logsight.service;

import cn.fishmaple.logsight.config.I18n;
import cn.fishmaple.logsight.dao.dto.LogFieldDTO;
import cn.fishmaple.logsight.dao.mapper.LogFieldMapper;
import cn.fishmaple.logsight.object.LogField;
import cn.fishmaple.logsight.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettingService {
    @Autowired
    private LogFieldMapper logFieldMapper;
    @Autowired
    private I18n i18n;
    public List<LogField> getPagesLogField(Integer page){
        List<LogFieldDTO> logFieldDTOList = logFieldMapper.selectAPage(page*10,10,"id");
        TimeUtil.initedFormatter();
        List<LogField> list = new ArrayList<>();
        logFieldDTOList.stream().forEach(logFieldDTO -> {
            list.add(new LogField()
                .setId(logFieldDTO.getId())
                .setCreateTime(TimeUtil.formatTimeUnchecked(logFieldDTO.getCreateTime()))
                .setPath(logFieldDTO.getPath())
                .setStatusStr(0==logFieldDTO.getStatus()?i18n.getMessage("i18n.setting_logfield_table_status_open")
                                :(1==logFieldDTO.getStatus()? i18n.getMessage("i18n.setting_logfield_table_status_invalid"):
                        i18n.getMessage("i18n.setting_logfield_table_status_closed")))
                .setStatus(logFieldDTO.getStatus())
                .setFileCount(logFieldDTO.getFileCount())
            );

        });
        return list;
    }
}
