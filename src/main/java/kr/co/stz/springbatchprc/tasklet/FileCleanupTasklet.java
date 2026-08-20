/**
 * ═════════════════════════════════════════════════════════════
 * 📄 FILE     : null.java
 * 📁 PACKAGE  : spring-batch-prc-kr.co.stz.springbatchprc
 * 👤 AUTHOR   : stz
 * 🕒 CREATED  : 26. 8. 19.
 * ═════════════════════════════════════════════════════════════
 * ═════════════════════════════════════════════════════════════
 * 📝 DESCRIPTION
 * -
 * ═════════════════════════════════════════════════════════════
 * ═════════════════════════════════════════════════════════════
 * 🔄 CHANGE LOG
 * - DATE : 2026/08/19 | Author : stz | 최초 생성
 * ═════════════════════════════════════════════════════════════
 */
package kr.co.stz.springbatchprc.tasklet;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;

import java.io.File;
import java.time.LocalDate;


@RequiredArgsConstructor
@Slf4j
public class FileCleanupTasklet implements Tasklet {
    
    private final String ROOT_PATH;
    private final int retentionDays;
    
    
    @Override
    public @Nullable RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LocalDate cutoffDate = LocalDate.now().minusDays(retentionDays);
        File folder = new File(ROOT_PATH);
        File[] files = folder.listFiles();
        
        if(files == null) return RepeatStatus.FINISHED;

        for (File file : files) {
            String name = file.getName();
            
            if(name.endsWith(".log") && name.length() >= 10) {
                // "access_2026-01-31.log" => "2026-01-31"
                String substring = name.substring(name.lastIndexOf("_") + 1, name.lastIndexOf("."));
                LocalDate fileDate = LocalDate.parse(substring);
                
                if(fileDate.isBefore(cutoffDate)) {
                    file.delete();
                    log.info("[삭제된 로그 파일] : {}", name);
                }
            }
        }
        return RepeatStatus.FINISHED;
    }
    
    
    
}
