package com.social.network.requests;

import lombok.Data;

@Data
public class PostCreateRequest {

    private String title;
    private String text;
    private Long userId;
}
