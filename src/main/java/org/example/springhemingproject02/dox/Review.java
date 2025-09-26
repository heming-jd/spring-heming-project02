package org.example.springhemingproject02.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @CreatedBy
    private String id;
    private String applicationId;
    private String teacherId;
    private String status;
    private BigDecimal score;
    private String rejectReason;
    @ReadOnlyProperty
    private LocalDateTime reviewTime;
}
