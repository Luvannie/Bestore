package com.luvannie.springbootbookecommerce.responses.complain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luvannie.springbootbookecommerce.entity.Complain;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplainResponse {
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

    private int totalPages;
    public static ComplainResponse fromComplain(Complain complain) {
        return ComplainResponse.builder()
                .id(complain.getId())
                .complain(complain.getComplain())
                .userId(complain.getUserId())
                .orderId(complain.getOrderId())
                .isFinish(complain.isFinish())
                .totalPages(0)
                .build();
    }
}
