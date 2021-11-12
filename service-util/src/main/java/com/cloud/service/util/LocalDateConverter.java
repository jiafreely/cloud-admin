package com.cloud.service.util;


import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * @author xjh
 * @version 1.0
 * @ClassName: LocalDateConverter
 * @description: 针对JDK8的LocalDate类型，easyexcel默认的类型转换器是无法处理的，要自行编写转换器
 * @date 2021/11/8 16:58
 */

@Component
public class LocalDateConverter implements Converter<LocalDate> {
    @Override
    public Class<LocalDate> supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDate convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty
            , GlobalConfiguration globalConfiguration) throws Exception {
        return LocalDate.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public CellData convertToExcelData(LocalDate localDate, ExcelContentProperty excelContentProperty
            , GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData<>(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
