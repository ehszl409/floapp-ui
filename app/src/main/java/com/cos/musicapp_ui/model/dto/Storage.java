package com.cos.musicapp_ui.model.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Storage {
    private Integer id;
    // 스프링 서버와 데이터 통신을 하기 위해서는
    // 변수명이 같아야 합니다.
    private String title;
    private List<Storage> storageSong;
    private Timestamp createDate;
}
