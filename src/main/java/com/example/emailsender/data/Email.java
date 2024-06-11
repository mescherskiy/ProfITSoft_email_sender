package com.example.emailsender.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Builder
@Document(indexName = "emails")
public class Email {
    @Id
    Long id;

    @Field(type = FieldType.Keyword)
    private String from;

    @Field(type = FieldType.Keyword)
    private String to;

    @Field(type = FieldType.Text)
    private String subject;

    @Field(type = FieldType.Text)
    private String body;

    @Field(type = FieldType.Keyword)
    private String status;

    @Field(type = FieldType.Text)
    private String error;

    @Field(type = FieldType.Integer)
    private int attemptCount = 0;

    @Field(type = FieldType.Long)
    private long lastAttemptTime;
}
