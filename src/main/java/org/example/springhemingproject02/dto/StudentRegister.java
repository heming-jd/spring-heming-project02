package org.example.springhemingproject02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springhemingproject02.dox.StudentScore;
import org.example.springhemingproject02.dox.User;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRegister {
    private User user;
    private StudentScore studentScore;
}
