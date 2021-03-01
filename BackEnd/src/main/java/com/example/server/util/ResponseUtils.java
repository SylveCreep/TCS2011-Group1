package com.example.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.server.model.request.PagingRequest;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseUtils {


    //Get sort object for sort
    public Sort getSortObj(PagingRequest pagingRequest){
        Sort sort;
        switch (pagingRequest.sort){
            case "desc":
                sort = Sort.by(Sort.Direction.DESC,pagingRequest.getColumn());
                break;
            default:
                sort = Sort.by(Sort.Direction.ASC,pagingRequest.getColumn());
        }
        return sort;
    }

    //Reponse format
    public ResponseEntity<?> getResponseEntity(Object data, int code, String mess, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("data",data);
        response.put("code",code);
        response.put("messenger",mess);
        return new ResponseEntity<>(response, status);
    }

    public ResponseEntity<?> getResponseEntity(Object data, int code, String mess, Object total,  HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("data",data);
        response.put("code",code);
        response.put("lastPage",total);
        response.put("messenger",mess);
        return new ResponseEntity<>(response, status);
    }

    public static String dateFormat(Date date){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }
}
