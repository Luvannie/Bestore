package com.luvannie.springbootbookecommerce.responses.user;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserListResponse {
    private List<UserResponse> users;
    private int totalPages;
}
