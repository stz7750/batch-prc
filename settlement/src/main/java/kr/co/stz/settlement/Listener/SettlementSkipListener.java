/**
 * ═════════════════════════════════════════════════════════════
 * 📄 FILE     : null.java
 * 📁 PACKAGE  : spring-batch-prc-kr.co.stz.settlement.Listener
 * 👤 AUTHOR   : stz
 * 🕒 CREATED  : 26. 8. 21.
 * ═════════════════════════════════════════════════════════════
 * ═════════════════════════════════════════════════════════════
 * 📝 DESCRIPTION
 * -
 * ═════════════════════════════════════════════════════════════
 * ═════════════════════════════════════════════════════════════
 * 🔄 CHANGE LOG
 * - DATE : 2026/08/21 | Author : stz | 최초 생성
 * ═════════════════════════════════════════════════════════════
 */
package kr.co.stz.settlement.Listener;

import kr.co.stz.settlement.domain.Orders;
import kr.co.stz.settlement.domain.Settlement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SettlementSkipListener {
    
    @OnSkipInRead
    public void onSkipInRead(Throwable throwable) {
        log.warn("[SKIP][READ] 주문 데이터 읽기 중 skip 발생", throwable);
    }
    
    @OnSkipInProcess
    public void onSkipInProcess(Orders item, Throwable throwable) {
        log.warn("[SKIP][PROCESS] 주문 정산 처리 중 skip 발생. orderId={}", item.getId(), throwable);
    }
    
    @OnSkipInWrite
    public void onSkipInWrite(Settlement item, Throwable throwable) {
        log.warn("[SKIP][WRITE] 정산 데이터 저장 중 skip 발생. orderId={}", item.getOrderId(), throwable);
    }
}
