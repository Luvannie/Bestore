package com.luvannie.springbootbookecommerce.service.token;

import com.luvannie.springbootbookecommerce.entity.Token;
import com.luvannie.springbootbookecommerce.entity.User;

public interface ITokenService {
    Token addToken(User user, String token, boolean isMobileDevice);
    Token refreshToken(String refreshToken, User user) throws Exception;
}
