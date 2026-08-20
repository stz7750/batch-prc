/**
 * ═════════════════════════════════════════════════════════════
 * 📄 FILE     : null.java
 * 📁 PACKAGE  : spring-batch-prc-kr.co.stz.springbatchprc.JobParameters
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
package kr.co.stz.springbatchprc.JobParameters;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class DatePrintJobConfig {
    
    private final JobRepository jobRepository;
    
    @Bean
    public Job datePrintJob(Step datePrintStep) {
        return new JobBuilder("datePrintJob", jobRepository)
                .start(datePrintStep)
                .build();
    } 
    
    @Bean
    public Step datePrintStep(
            ItemReader<String> datePrintReader,
            ItemProcessor<String, LocalDate> datePrintProcessor,
            ItemWriter<LocalDate> datePrintWriter
    ) {
        return new StepBuilder( "datePrintStep", jobRepository)
                .<String, LocalDate>chunk(2)
                .reader(datePrintReader)
                .processor(datePrintProcessor)
                .writer(datePrintWriter)
                .build();
    }
}
