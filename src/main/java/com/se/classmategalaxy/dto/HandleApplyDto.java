package com.se.classmategalaxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyx20
 * @version 1.0
 * @title HandleApplyDto
 * @description
 * @create 2024/1/11 22:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandleApplyDto {
    private Integer joinId;
    private Integer planetId;
    private Integer userId;
    private String userName;
    private String planetName;
    private Integer choice;
}
