package com.galaxy.portal.integral.analysis;

import com.anscen.analysis.reader.analysis.listener.ReaderFileListener;
import com.anscen.analysis.reader.analysis.template.FileAnalysisTemplate;
import com.anscen.analysis.reader.config.AnalysisProperties;
import com.anscen.analysis.reader.config.ScanProperties;
import com.anscen.analysis.reader.util.FileUtil;
import com.anscen.analysis.reader.util.ReadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;

/**
 * @Author: wuhaifeng
 * @DateTime: 2021/7/2 21:26
 * @Description: TODO
 */
@Slf4j
public class IntegralFileAnalysisTemplate extends FileAnalysisTemplate {

    public IntegralFileAnalysisTemplate(ThreadPoolTaskExecutor analysisThreadPool, AnalysisProperties analysisProperties, ReaderFileListener readerFileListener) {
        super(analysisThreadPool, analysisProperties, readerFileListener);
    }

    public IntegralFileAnalysisTemplate(ReaderFileListener readerFileListener) {
        super(readerFileListener);
    }

    @Override
    public void beforeAnalysis(String fileName) throws Exception {

        File file = new File(fileName);
        log.info("===== {}文件解析前操作 ========", fileName);
        log.info("//------解析本次要解析的文件路径，获取本次要操作的信息----//");
        String[] fileInfos = file.getName().split("-");
        if (fileInfos.length != 5) {
            throw new Exception("文件名不符合格式");
        }
        //测试文件-append-20210701-2.txt
        String tableName = fileInfos[0];//表英文名称
        String tableChineseName = fileInfos[1];//表中文名称
        String importType = fileInfos[2];//表导入类型
        String fileDate = fileInfos[3];//表时间日期

        log.info("//------解析文件表头信息-----//");
        if ("new".equals(importType)) {
            ReadFile readFile = new ReadFile();
            String titleFields = readFile.getRow(file, this.getAnalysisProperties().getTitleRowNum());
            //生成表头和字段的映射关系

            log.info("//-------将本次要字段映射关系存储到数据库表中----//");

        }


        log.info("//------创建本次数据要插入表-----//");


    }

    @Override
    public void afterAnalysis(String filePath) throws Exception {
        log.info("====={} 文件解析成功后操作 ========", filePath);

        log.info("===更新当前表的数据量===");

        log.info("====更新表的状态=====");

        log.info("文件：" + filePath + "解析完成，等待导入es");
        File file = new File(filePath);
        FileUtil.moveFiles(filePath, this.getAnalysisProperties().getAnalysisedMovePath() +File.separator +  file.getName());
    }
}
