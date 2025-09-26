package org.example.springhemingproject02.dox;

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
public class Application {
    @Id
    @CreatedBy
    private String id;
    private String studentId;
    private String leafNodeId;
    private String projectDescription;
    private String status;
    @ReadOnlyProperty
    private LocalDateTime applyTime;
    @ReadOnlyProperty
    private LocalDateTime updateTime;

}
