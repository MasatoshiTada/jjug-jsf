package com.example.web.exception.handler;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * 発生した例外をハンドリングするクラスです。 下記の上妻さんのブログを参考に作っています。
 *
 * @see http://n-agetsuma.hatenablog.com/entry/2013/02/11/134531
 * @author tada
 */
public class MyExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory parent;

    public MyExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler handler = new MyExceptionHandler(parent.getExceptionHandler());
        return handler;
    }
}
