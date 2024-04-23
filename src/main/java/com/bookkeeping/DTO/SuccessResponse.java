package com.bookkeeping.DTO;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SuccessResponse {

    private String status;
    private String message;

}
