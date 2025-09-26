package org.example.springhemingproject02.dox;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User{
    public static final String ROLE_USER = "hOl7U";;
    public static final String ROLE_ADMIN = "yxp4r";
    public static final String ROLE_COLLEGEAdmin = "aad";
    public static final String ROLE_Teacher = "efd";
    @Id
    @CreatedBy
    private String id;
    private String account;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String phone;
    private String email;
    private String collegeId;
    @JsonIgnore
    private String role;
    private String studentNumber;
    private String teacherNumber;
    private String categoryId;
    private String majorId;
    @ReadOnlyProperty
    private LocalDateTime createdTime;
    @ReadOnlyProperty
    private LocalDateTime updatedTime;

}
