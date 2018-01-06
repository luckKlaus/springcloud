package com.luck.cloud.gateway.filters.pre;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.nimbusds.jose.JOSEObject;
import com.nimbusds.jose.util.Base64URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by luck on 2017/09/28.
 * 该过滤器覆盖范围为整个微服务.
 */
@Component
public class PreFilter extends ZuulFilter {
    private static Logger logger = LoggerFactory.getLogger(PreFilter.class);
    private static String[] headerKeys = {"Service-Name","Authorization"};
//    private final CheckTokenService checkTokenService;
//    private final ExemptionService exemptionService;
//    private final RedisServiceImpl redisService;

//    @Autowired
//    public PreFilter(CheckTokenService checkTokenService, RedisServiceImpl redisService,
//                     ExemptionService exemptionService) {
//        this.checkTokenService = checkTokenService;
//        this.redisService = redisService;
//        this.exemptionService = exemptionService;
//    }

    static {
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        logger.info("进入gateway过滤器!");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

//        //连连支付回调接口
//        String reqUrl = request.getRequestURL().toString();
//        if(reqUrl.contains("public/llcp")) {
//            return null;
//        }
//        if(reqUrl.contains("/userPage")) {
//            return null;
//        }
//        if(reqUrl.contains("/roulette")) {
//            return null;
//        }
//        if(reqUrl.contains("/baitiao")) {
//            return null;
//        }
//
//        Map<String,String> heads = getHeader(request,headerKeys);
//        //此处预留 验签 鉴权 取出jwt token验证(从redis取出该user对应秘钥) 取出token后验证通过,设置相应的user_id到httpRequestHeader里面,方便feign层取出进行调用,注意user_id不从外部传入
//        String serviceName = heads.get("Service-Name");
//        Response resp = null;
//        if(StringUtil.isBlank(serviceName)){
//            sendResponse(ctx,ResponseCodeEnum.SERVICENAME_NOTFOUND);
//            return null;
//        }
//        String userId = "0";
//        Map exemptionMap = exemptionService.getExemptionMap();
//        if(!exemptionMap.containsKey(serviceName)){
//            //String secret = redisService.get("jwt_token_secret");
//            String secret = null;
//            if(request.getRequestURL().toString().contains("api/web/service")){
//                secret = redisService.get("manage_jwt_token_secret");
//            }else{
//                secret = redisService.get("jwt_token_secret");
//            }
//            resp = checkTokenService.checkToken(heads.get("Authorization"), secret);
//            if(resp != null){
//                ctx.setSendZuulResponse(false);
//                ctx.setResponseStatusCode(200);
//                Util.sendResponse(JSONObject.toJSONString(resp),ctx.getResponse());
//                return null;
//            }
//            userId = getUserId(heads.get("Authorization"));
//            if(userId == null || userId.equals("0")){
//                sendResponse(ctx,ResponseCodeEnum.SIGNATURE_ERROR);
//                return null;
//            }
//            if(request.getRequestURL().toString().contains("api/user/service")
//                    || request.getRequestURL().toString().contains("api/order/service")){
//                String redisToken = redisService.get("user:tokenStore:userId_" + userId);
//                if(!StringUtil.isBlank(redisToken) && !heads.get("Authorization").equals(redisToken)){
//                    sendResponse(ctx,ResponseCodeEnum.LOGIN_REPEAT_ERROR);
//                    return null;
//                }
//            }
//        }
//
//
//        RequestContext requestContext = RequestContext.getCurrentContext();
//        requestContext.addZuulRequestHeader("authorization", heads.get("Authorization"));
//        requestContext.addZuulRequestHeader("serviceName", heads.get("Service-Name"));
//        requestContext.addZuulRequestHeader("userId", userId);//设置header示例
        return null;
    }


    /**
     * 获取header信息
     *
     * @param req request请求
     * @param keys 键集合
     * @return value
     */
    private Map<String, String> getHeader(HttpServletRequest req, String[] keys) {
        Map<String, String> headerMap = new HashMap<>();
        for (String key : keys) {
            if (key.equals("Authorization")) {
                String tokenStr = req.getHeader(key);
                if(tokenStr != null && tokenStr.length() > 7){
                    tokenStr = tokenStr.substring(7, tokenStr.length());
                }
                headerMap.put(key,tokenStr);
            } else {
                headerMap.put(key,req.getHeader(key));
            }
        }
        return headerMap;
    }

    private String getUserId(String token) {
        String result = "0";
        try{
           Base64URL second = JOSEObject.split(token)[1];
           String payload = new String(Base64.getDecoder().decode(second.toString()),"utf-8");
           result = JSONObject.parseObject(payload).getString("userId");
       }catch (Exception e){
        logger.error("获取userId异常：" + e.getMessage());
       }
        return result;
    }

//    private void sendResponse(RequestContext ctx,ResponseCodeEnum status){
//        Response resp = new Response();
//        ctx.setSendZuulResponse(false);
//        ctx.setResponseStatusCode(200);
//        resp.setResponseCode(status);
//        Util.sendResponse(JSONObject.toJSONString(resp),ctx.getResponse());
//    }

}
