package org.example.springhemingproject02.dox;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeClosure {
    @Id
    @CreatedBy
    private Long ancestorId;
    private Long descendantId;
    private Integer level;
    @ReadOnlyProperty
    private LocalDateTime createTime;
}
