package com.deliveryManPlus.dto.auth;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
public class JwtAuthResponseDto {

  private String tokenAuthScheme;

  private String accessToken;

  private String refreshToken;
}
