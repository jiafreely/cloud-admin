package com.cloud.service.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: Jdk8Entity
 * @description: TODO
 * @date 2021/11/30 16:16
 */
@Data
@Accessors(chain = true)
public class Jdk8Entity implements Serializable {
    private String id;
    private String name;
    private String dateTime;

}
