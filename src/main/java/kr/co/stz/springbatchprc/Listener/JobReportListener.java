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


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobReportListener {
    
    private final EmailProvider emailProvider;
    
    @BeforeJob
    public void before(JobExecution jobExecution) {
        log.info("배치를 시작합니다. id : {}", jobExecution.getJobInstanceId());
    }
    
    @AfterJob
    public void after(JobExecution jobExecution) {
        if(jobExecution.getStatus() != BatchStatus.FAILED){
            log.info("[배치가 성공적으로 실행되었습니다.]");
        } else {
            log.info("배치가 실패되었습니다 이메일을 보냅니다.");
            emailProvider.send("me", "BATCH-FAIL", "대실패!\n"+jobExecution.getJobInstanceId()+"번이 실패");
        }
    }
}
