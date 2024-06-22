package com.luvannie.springbootbookecommerce.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", "dkfmy6ufc",
                "api_key", "292535229338629",
                "api_secret", "fcjjE_Pn3ygwODRA5MOI7-UUySI",
                "CLOUDINARY_URL", "cloudinary://292535229338629:fcjjE_Pn3ygwODRA5MOI7-UUySI@dkfmy6ufc"
        );
        return new Cloudinary(config);
    }
}
