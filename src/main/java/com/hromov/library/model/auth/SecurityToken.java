package com.hromov.library.model.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecurityToken {
    private final String accessToken;
}
