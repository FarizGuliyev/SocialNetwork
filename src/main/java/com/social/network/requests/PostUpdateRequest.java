package com.social.network.requests;

import lombok.Data;

@Data
public class PostUpdateRequest {
    private String text;
    private String title;
}
