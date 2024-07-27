package com.pastebin.service.api.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.pastebin.service.api.converter.JodaDateTimeConverter;
import jakarta.persistence.*;
import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.joda.time.DateTime;

@Data
@Entity
@Table(name = "pastes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paste {
    @Id
    private String hash;

    @Column(columnDefinition = "TEXT")
    private String data;

    @Convert(converter = JodaDateTimeConverter.class)
    @JsonSerialize(using = DateTimeSerializer.class)
    @Builder.Default
    private DateTime createdAt = DateTime.now();

    private String language;

    private String title;

    @Builder.Default
    private boolean isPublic = true;

    public Paste(String hash, String data) {
        this.hash = hash;
        this.data = data;
        this.createdAt = DateTime.now();
        this.isPublic = true;
    }
}