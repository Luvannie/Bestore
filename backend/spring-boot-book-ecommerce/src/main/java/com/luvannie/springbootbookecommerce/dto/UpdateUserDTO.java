package com.luvannie.springbootbookecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {


    @JsonProperty("username")
    private String username;

    @JsonProperty("account")
    private String account;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;

    @JsonProperty("retype_password")
    private String retypePassword;

    @JsonProperty("phone_number")
    private String phoneNumber;
}
