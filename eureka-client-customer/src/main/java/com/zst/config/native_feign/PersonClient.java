package com.zst.config.native_feign;

import com.zst.entity.Person;
import feign.Feign;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

/**
 * @description: Feign 支持自定义编码器（encoder）、解码器（decoder）、
 * @author: Zhoust
 * @date: 2018/12/14 下午4:58
 * @version: V1.0
 */
public interface PersonClient {

    String LOCAL_8848 = "http://localhost:8848";
    String LOCAL_8849 = "http://localhost:8849";

    @RequestLine("GET /get/{id}")
    Person getPerson(@Param("id")Integer id);

    @RequestLine("POST /showPerson")
    @Headers("Content-Type: application/json")
    String showPerson(Person person);


    /**
     * 指定编码器，将要发送的对象转化为 json 字符串
     * @param p
     * @return
     */
    static String showPersonByEncoder(Person p) {
        PersonClient personClient = Feign.builder()
                .encoder(new GsonEncoder())
                .target(PersonClient.class,LOCAL_8849);
        return personClient.showPerson(p);
    }

    /**
     *  指定解码器 -> 将返回的 json 字符串转化为相应对象
     * @return
     */
    static Person getPersonByDecoder(Integer id) {
        PersonClient personClient = Feign.builder()
                                         .decoder(new GsonDecoder())
                                         .target(PersonClient.class,LOCAL_8848);
        return personClient.getPerson(id);
    }

    static void main(String[] args) {
        Person personByDecoder = getPersonByDecoder(1000);
        String s = showPersonByEncoder(personByDecoder);
        System.out.println(s);
    }


}