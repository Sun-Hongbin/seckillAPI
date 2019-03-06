package org.seckill.exception;

/**
 * 秒杀关闭异常（时间到了或者是商品被秒光了）
 */
public class SeckillCloseException extends SeckillException{

    public SeckillCloseException(String message){
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }


}
