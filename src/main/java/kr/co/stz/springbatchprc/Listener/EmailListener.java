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
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class EmailListener {
    
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final JobReportListener jobReportListener;
    
    @Bean
    public Job emailJob(Step emailStep) {
        return new JobBuilder("emailJob", jobRepository)
                .start(emailStep)
                .listener(jobReportListener)
                .build();
                
    }
    
    @Bean
    public Step emailStep() {
        return new StepBuilder("emailStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("3초 뒤 에러를 발생시킵니다. ... ");
                    Thread.sleep(3000);
                    
                    
                    throw new RuntimeException("테스트를 위해 에러 발생");
                }, transactionManager)
                .build();
    }
}
