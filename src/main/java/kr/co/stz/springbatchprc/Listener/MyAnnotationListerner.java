/**
 * ═════════════════════════════════════════════════════════════
 * 📄 FILE     : null.java
 * 📁 PACKAGE  : spring-batch-prc-kr.co.stz.springbatchprc.Listener
 * 👤 AUTHOR   : stz
 * 🕒 CREATED  : 26. 8. 20.
 * ═════════════════════════════════════════════════════════════
 * ═════════════════════════════════════════════════════════════
 * 📝 DESCRIPTION
 * -
 * ═════════════════════════════════════════════════════════════
 * ═════════════════════════════════════════════════════════════
 * 🔄 CHANGE LOG
 * - DATE : 2026/08/20 | Author : stz | 최초 생성
 * ═════════════════════════════════════════════════════════════
 */
package kr.co.stz.springbatchprc.Listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyAnnotationListerner {
    
    @BeforeJob
    public void announceStart(JobExecution jobExecution) {
        log.info("[Job 시작]");    
    }
    
    @AfterJob
    public void announceEnd(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.FAILED) {
            log.warn("[잡 실패]");
        }else{
            log.info("[Job 종료]");
        }
        
    }
}
