package com.luvannie.springbootbookecommerce.responses.user;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luvannie.springbootbookecommerce.entity.Role;
import com.luvannie.springbootbookecommerce.entity.User;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String userName;

    @JsonProperty("phone_number")
    private String phoneNumber;



    @JsonProperty("is_active")
    private boolean active;

    @JsonProperty("email")
    private String email;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;

    @JsonProperty("role")
    private Role role;

    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .active(user.isActive())
                .facebookAccountId(user.getFacebookAccountId())
                .googleAccountId(user.getGoogleAccountId())
                .role(user.getRole())
                .build();
    }
}
