package com.bidpages.utils;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtilUsingSecretString {

    private static final String secretKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9UIJHsN7GaHiN0BsHA"; //进行数字签名的私钥，一定要保管好

    private static final Long EXPIRE = 1 * 24 * 60 * 60 * 1000L; //token过期时间, 单位毫秒

    public static void main(String[] args) throws Exception {

        //获取系统的当前时间
    /*    long ttlMillis = System.currentTimeMillis();
        Date now = new Date(ttlMillis);*/

        //生成jwt令牌
       /* JwtBuilder jwtBuilder = Jwts.builder()
                .setId("66")//设置jwt编码
                .setSubject("程序员")//设置jwt主题
                .setIssuedAt(new Date())//设置jwt签发日期
                //.setExpiration(date)//设置jwt的过期时间
                .claim("t", "admin")
                .claim("company", "itheima")
                .signWith(SignatureAlgorithm.HS256, secretKey);
        //生成jwt
        String jwtToken = jwtBuilder.compact();
        System.out.println(jwtToken);*/
//        String s = acquireJWT("123456", "1", "123");
      /*  byte[] bytes = DatatypeConverter.parseBase64Binary(secretKey);
        //解析jwt,得到其内部的数据
        Claims claims = Jwts.parser().setSigningKey(bytes).parseClaimsJws(s).getBody();
        System.out.println(claims);
        Claims claims1 = parseJWT(s);
        System.out.println(claims1);*/
    }

    /**
     * 生成串
     *
     * @return
     * @throws Exception
     */
    public static String createJWT(String text) {
        //生成jwt令牌
        JwtBuilder jwtBuilder = null;
        try {
            jwtBuilder = Jwts.builder()
                    .setIssuedAt(new Date())//设置jwt签发日期
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                    .claim("sysUserJson", text)  //设置token主体部分 ，存储用户信息
//              .claim("company", "itheima")
                    .signWith(SignatureAlgorithm.HS256, secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return jwtBuilder.compact();
    }

    public static String parseJWT(String JwtToken) {
        Claims claims = null;
        try {
//            Jwts.parserBuilder().
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(JwtToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return (String) claims.get("sysUserJson");
    }

//    /**
//     * 解析JWT字符串
//     *
//     * @param jwt
//     * @return
//     * @throws Exception
//     */
//    public static Claims parseJWT(String jwt) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(jwt)
//                .getBody();
//    }

}
