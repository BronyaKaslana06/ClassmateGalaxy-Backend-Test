package com.se.classmategalaxy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyx20
 * @version 1.0
 * @title Evaluate
 * @description
 * @create 2024/1/12 2:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evaluate {
    private Integer evaluate_id;
    private Float score;
    private String reason;
    private Integer resourceId;
    private Integer userId;
}
