package com.example.sec04.exceptionhandling;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentOrGradeErrorResponse {

    private int status;
    private String message;
    private long timeStamp ;

    public StudentOrGradeErrorResponse(){

    }

    public StudentOrGradeErrorResponse(int status, String message, long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

}
