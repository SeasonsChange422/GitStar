package com.hx.entity.DTO.post;

import com.hx.entity.PO.Post;
import lombok.Data;

/**
 * @author dhx
 * @date 2025/5/9 18:59
 */
@Data
public class PostDTO extends Post {
    private int commentCount;
    public PostDTO(Post post,int commentCount){
        super(post);
        this.commentCount = commentCount;
    }
}
