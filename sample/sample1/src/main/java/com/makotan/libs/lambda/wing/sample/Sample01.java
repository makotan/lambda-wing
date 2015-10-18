package com.makotan.libs.lambda.wing.sample;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Created by makotan on 2015/10/17.
 */
public class Sample01 implements RequestHandler<Sample01.Request,Sample01.Response> {

    @Override
    public Response handleRequest(Request request, Context context) {
        Response res = new Response();
        res.setValue("" + request.getValue());
        return res;
    }

    public static class Request {
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class Response {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
