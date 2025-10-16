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
public class Major {
    @Id
    @CreatedBy
    private Long id;
    private String majorName;
    private Long collegeId;
    private Long categoryId;
    @ReadOnlyProperty
    private LocalDateTime createTime;
}
