package org.epoch.generator.app.generator;

import org.epoch.generator.api.dto.DbTable;
import org.epoch.generator.api.dto.GeneratorConfig;
import org.epoch.generator.infra.enums.FileType;
import org.epoch.generator.infra.util.TemplateUtil;
import org.springframework.stereotype.Component;

/**
 * @author Marshal
 * @date 2020/1/20
 */
@Component
public class ApiGenerator implements AbstractGenerator {

    @Override
    public FileType getFileType() {
        return FileType.Api;
    }

    @Override
    public byte[] generate(DbTable table, GeneratorConfig generatorConfig) throws Exception {
        return TemplateUtil.createFtlInfoByType(FileType.Api, table, generatorConfig);
    }

}
