package com.luvannie.springbootbookecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComplainDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("complain")
    private String complain;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("is_finish")
    private boolean isFinish;
}
