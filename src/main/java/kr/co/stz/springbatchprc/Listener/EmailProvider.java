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
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailProvider {

    public void send(String to , String subject , String message){
        // 실제로는 여기서 smtp로 메일 전송
        log.info("[메일 발솔 성공] 받는 사람 : {}" , to);
        log.info("[제목] : {}", subject);
        log.info("[내용] : {}" , message);
    }
}
