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


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@Slf4j
public class DatePrintChunk {
    
    @Bean
    @StepScope
    public ListItemReader<String> datePrintReader(@Value("#{jobParameters['requestDate']}") String requestDate) {
        return new ListItemReader<>(Arrays.asList(requestDate));
    }
    
    @Bean
    public ItemProcessor<String, LocalDate> datePrintProcessor() {
        return LocalDate::parse;
    }
    
    @Bean
    public ItemWriter<LocalDate> datePrintWriter() {
        return item -> {
            for (LocalDate localDate : item) {
                log.info("외부에서 받은 날짜 : {}", localDate);
            }
        };
    }
}
