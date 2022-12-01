package com.stars.common.redis;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.stars.common.util.JsonUtils;

import com.stars.pojo.news.LoginParams;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * study: token
 */
public class JWTUtils {
    private static final String ISSUER = "Chat-API";
    private static final String HMAC256_PWD = ".,sd125@#zs1a3";

    /*------------------------------Using RS256---------------------------------*/
    /*获取签发的token，返回给前端*/
    public static String generTokenByRS256(Object user) throws Exception {

//        RSA256Key rsa256Key = SecretKeyUtils.getRSA256Key(); // 获取公钥/私钥
//        Algorithm algorithm = Algorithm.RSA256(
//                rsa256Key.getPublicKey(), rsa256Key.getPrivateKey());
        Algorithm algorithm = Algorithm.HMAC256(HMAC256_PWD);
        return createToken(algorithm, user);

    }

    /*签发token*/
    public static String createToken(Algorithm algorithm, Object data) throws Exception {
        String[] audience = {"app"};
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.WEEK_OF_MONTH, 1);

        return JWT.create()
                .withIssuer(ISSUER)        //发布者
                .withAudience(audience)     //观众，相当于接受者
                .withIssuedAt(new Date())   // 生成签名的时间
                .withExpiresAt(instance.getTime())    // 生成签名的有效期
                .withClaim("AnalogData", JsonUtils.obj2string(data)) //存数据
                .withNotBefore(new Date())  //生效时间
                .withJWTId(UUID.randomUUID().toString())    //编号
                .sign(algorithm);                            //签入
    }

    /*验证token*/
//    public static DecodedJWT verifierToken(String token) throws Exception {
//        RSA256Key rsa256Key = SecretKeyUtils.getRSA256Key(); // 获取公钥/私钥
//
//        //其实按照规定只需要传递 publicKey 来校验即可，这可能是auth0 的缺点
//        Algorithm algorithm = Algorithm.RSA256(rsa256Key.getPublicKey(), rsa256Key.getPrivateKey());
//        JWTVerifier verifier = JWT.require(algorithm)
//                .withIssuer(ISSUER)
//                .build(); //Reusable verifier instance 可复用的验证实例
//
//        return verifier.verify(token);
//    }

//    public static User verifierTokenByUser(String token) throws Exception {
//        //其实按照规定只需要传递 publicKey 来校验即可，这可能是auth0 的缺点
//        Algorithm algorithm = Algorithm.HMAC256(HMAC256_PWD);
//        JWTVerifier verifier = JWT.require(algorithm)
//                .withIssuer(ISSUER)
//                .build(); //Reusable verifier instance 可复用的验证实例
//        DecodedJWT decodedJWT = verifier.verify(token);
//        Claim data = decodedJWT.getClaim("data");
//        return JSON.toJavaObject(JSONObject.parseObject(data.asString()), User.class);
//    }
//
//    public static User getAccount(String token){
//        Claim data = JWT.decode(token).getClaim("data");
//        return JSON.toJavaObject(JSONObject.parseObject(data.asString()), User.class);
//    }

    // 在http拦截器里token校验
    public static LoginParams verifierTokenBySysUser(String token) throws Exception {
        //其实按照规定只需要传递 publicKey 来校验即可，这可能是auth0 的缺点
        Algorithm algorithm = Algorithm.HMAC256(HMAC256_PWD);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build(); //Reusable verifier instance 可复用的验证实例
        DecodedJWT decodedJWT = verifier.verify(token);
        Claim data = decodedJWT.getClaim("AnalogData");
        System.out.println("--verifierTokenBySysUser-1-"+data);
        System.out.println("--verifierTokenBySysUser-2-"+data.asString());
        return JsonUtils.string2Obj(data.asString(), LoginParams.class);
    }
    // 从token里获取user实体类
    public static LoginParams getSysUser(String token){
        Claim data = JWT.decode(token).getClaim("AnalogData");
        return JsonUtils.string2Obj(data.asString(), LoginParams.class);
    }
}