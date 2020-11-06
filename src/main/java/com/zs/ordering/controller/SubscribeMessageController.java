package com.zs.ordering.controller;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zs.ordering.config.ConfigAttibutes;
import com.zs.ordering.config.WechatAccountConfig;
import com.zs.ordering.enums.MesgEnum;
import com.zs.ordering.enums.UniformMesgEnum;
import com.zs.ordering.utils.HttpUtil;
import com.zs.ordering.utils.HttpUtils;
import com.alibaba.fastjson.JSONObject;
import com.zs.ordering.vo.TemplateData;
import com.zs.ordering.vo.WxMssVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信订阅消息发送接口
 * created by zhangyf on 2020-04-08 16:32
 */
@RestController
@RequestMapping(value = "subscribeMessage")
@Slf4j
public class SubscribeMessageController {

//    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private ConfigAttibutes configAttibutes;

    @Resource
    private WxMssVo wxMssVo;
    @Resource
    private WechatAccountConfig wechatAccountConfig;


    /**
     * 发送小程序订阅消息
     * 参数
     * access_token
     * touser
     * template_id
     * page
     * data
     * miniprogram_state
     * lang
     * @author Steven
     * @since 2020-10-30
     */
    @GetMapping(value = "/send")
    public Object sendSubscribeMessage(@RequestParam Map<String, Object> params,
                              @RequestParam(value = "openid",required = false) String openid) {

        openid = "oOpbO4gnHx-oou52xv7ugysGSVLw";
        //这里简单起见我们每次都获取最新的access_token（时间开发中，应该在access_token快过期时再重新获取）
        String Token =  getAccessToken("wx07f164cb9f41c6da","fa909e00808af1f97121414f172f1302");
        //用户的openid（要发送给那个用户，通常这里应该动态传进来的）
        params.put("touser", openid);
        params.put("template_id",wechatAccountConfig.getWeapp_templateId().get("test"));
        params.put("page", "pages/index/index");
        params.put("miniprogram_state", "develope");
        params.put("lang", "zh_CN");
        //TemplateData类型对象
        Map<String,TemplateData> weapp_data = new HashMap<>();
//        Map<String,Object> _data = new HashMap<>();
//        Map<String,Object> _data1 = new HashMap<>();
//        _data.put("value","奔驰E300L");
//        _data1.put("value","进入了解4S店的最新报价信息");
//        weapp_data.put("thing1",_data);
//        weapp_data.put("thing2",_data1);
        weapp_data.put("thing1", new TemplateData("奔驰E300L"));
        weapp_data.put("thing2", new TemplateData("进入了解4S店的最新报价信息"));
        wxMssVo.setData(weapp_data);
        params.put("data", weapp_data);
        String url = (String) (configAttibutes.getSubscribeMessage()+Token);
        String jsonData = JSONObject.toJSONString(params);
        log.info("发送的模板数据{}", jsonData);

//        JSONObject jsonData = new JSONObject(params);
//        JSONObject result  = new JSONObject(HttpUtils.sendPost(url,jsonData));
//        HttpUtil.sendPost(url,params);

        String str = HttpUtils.sendPost(url, jsonData);
        log.info("发送订阅消息的返回值{}", str);
//        JSONObject result =new JSONObject(str);
        JSONObject result = JSON.parseObject(str);
        //判断发送订阅消息请求的返回值错误码
        if ((MesgEnum.ERR_OPENID.getCode()).toString().equals(String.valueOf(result.get("errcode")))) {

            log.info("错误信息{}", MesgEnum.ERR_OPENID.getName());
            return MesgEnum.ERR_OPENID.getName();

        } else if ((MesgEnum.ERR_TPLT.getCode()).toString().equals(String.valueOf(result.get("errcode")))) {

            log.info("错误信息{}", MesgEnum.ERR_TPLT.getName());
            return MesgEnum.ERR_TPLT.getName();

        } else if ((MesgEnum.ERR_REFUSE.getCode()).toString().equals(String.valueOf(result.get("errcode")))) {

            log.info("错误信息{}", MesgEnum.ERR_REFUSE.getName());
            return MesgEnum.ERR_REFUSE.getName();

        } else if ((MesgEnum.ERR_PAGE.getCode()).toString().equals(String.valueOf(result.get("errcode")))) {

            log.info("错误信息{}", MesgEnum.ERR_PAGE.getName());
            return MesgEnum.ERR_PAGE.getName();

        } else if ((MesgEnum.ERR_TPLT_PARA.getCode()).toString().equals(String.valueOf(result.get("errcode")))) {
            log.info("错误信息{}", MesgEnum.ERR_TPLT_PARA.getName());
            return MesgEnum.ERR_TPLT_PARA.getName();
        }else {
            return "发送成功啦~";
        }
    }


    /**
     * 发送统一服务通知消息
     * @author Steven
     * @since 2020-10-30
     */
    @GetMapping(value = "/send2")
    public Object senduniformMessage(@RequestParam Map<String, Object> params,
                              @RequestParam(value = "openid",required = false) String openid) {
            //公众号token
//        String Token =  getAccessToken(wechatAccountConfig.getMpAppId(), wechatAccountConfig.getMpAppSecret());
        //小程序token
//        String  Token = getAccessToken(wechatAccountConfig.getWeappAppId(), wechatAccountConfig.getWeappAppSecret());
        //幸福车生活小程序的
      String Token =  getAccessToken("wx07f164cb9f41c6da","fa909e00808af1f97121414f172f1302");

        openid = "oOpbO4gnHx-oou52xv7ugysGSVLw";
        params.put("touser",openid);
        Map<String,Object>  weapp_template_msg =new HashMap<>();
        Map<String,Object>  mp_template_msg =new HashMap<>();
        mp_template_msg.put("appid",wechatAccountConfig.getMpAppId());
        mp_template_msg.put("template_id","9SgExl6tCofW6ZQ-2l-RKB27hAJtFI5PxspVFRroakI");
        mp_template_msg.put("url","https://scrm.bzmaster.cn/Weixin/index");
        Map<String,Object> miniprogram_data=new HashMap<>();
        miniprogram_data.put("appid","wx07f164cb9f41c6da");
        miniprogram_data.put("pagepath","pages/general/homePage/rescue/index");
//        miniprogram_data.put("setUsePath",true);
        mp_template_msg.put("miniprogram",miniprogram_data);
        Map<String,TemplateData> mp_template_data=new HashMap<>();

        mp_template_data.put("first",new TemplateData("您好，您的卡卷已办理成功","#173177"));
        mp_template_data.put("keyword1",new TemplateData("XXX美容院","#173177"));
        mp_template_data.put("keyword2",new TemplateData("3折卡","#173177"));
        mp_template_data.put("keyword3",new TemplateData("2017/1/1到2018/1/1","#173177"));
        mp_template_data.put("keyword4",new TemplateData("2017/2/1感谢您的支持，谢谢","#173177"));
        mp_template_data.put("remark",new TemplateData("如有疑问，请咨询13912345678","#173177"));


//        mp_template_data.put("first",new TemplateData("详细内容详细内容详细内容","#173177"));
//        mp_template_data.put("productType",new TemplateData("小轿车车辆普通维护","#173177"));
//        mp_template_data.put("time",new TemplateData("2013年11月6日 14:00","#173177"));
//        mp_template_data.put("result",new TemplateData("已预约","#173177"));
//        mp_template_data.put("remark",new TemplateData("如有疑问，请咨询13912345678","#173177"));


//公众号data可以勇多个Map嵌套
//        Map<String,Object> mp_template_data=new HashMap<>();
//        Map<String,Object> data_ =new HashMap<>();
//        data_.put("value","详细内容详细内容详细内容");
//        data_.put("color","#173177");
//        Map<String,Object> data_1 =new HashMap<>();
//        data_1.put("value","小轿车车辆普通维护");
//        data_1.put("color","#173177");
//        Map<String,Object> data_2 =new HashMap<>();
//        data_2.put("value","2013年11月6日 14:00");
//        data_2.put("color","#173177");
//        Map<String,Object> data_3 =new HashMap<>();
//        data_3.put("value","已预约");
//        data_3.put("color","#173177");
//        Map<String,Object> data_4 =new HashMap<>();
//        data_4.put("value","如有疑问，请咨询13912345678");
//        data_4.put("color","#173177");
//        mp_template_data.put("first",data_);
//        mp_template_data.put("productType",data_1);
//        mp_template_data.put("time",data_2);
//        mp_template_data.put("result",data_3);
//        mp_template_data.put("remark",data_4);



//        mp_template_data.put("productType",new TemplateData("小轿车车辆普通维护","#173177"));
//        mp_template_data.put("time",new TemplateData("2013年11月6日 14:00","#173177"));
//        mp_template_data.put("result",new TemplateData("已预约","#173177"));
//        mp_template_data.put("remark",new TemplateData("如有疑问，请咨询13912345678","#173177"));

        mp_template_msg.put("data",mp_template_data);
        params.put("mp_template_msg",mp_template_msg);
        String url = (String) (configAttibutes.getUniformMessage()+Token);
        String jsonData = JSONObject.toJSONString(params);
        log.info("发送统一服务通知消息模板数据{}", jsonData);

        String str= HttpUtils.sendPost(url,jsonData);
        log.info("发送统一服务通知消息的返回值{}", str);

        JSONObject result = JSON.parseObject(str);

        //判断发送订阅消息请求的返回值错误码
        if ((UniformMesgEnum.ERR_28.getCode()).toString().equals(String.valueOf(result.get("errcode")))) {

            log.info("错误信息{}", UniformMesgEnum.ERR_28.getCode());
            return UniformMesgEnum.ERR_28.getName();

        } else if ((UniformMesgEnum.ERR_29.getCode()).toString().equals(String.valueOf(result.get("errcode")))) {

            log.info("错误信息{}", UniformMesgEnum.ERR_29.getCode());
            return UniformMesgEnum.ERR_29.getName();

        } else if (( UniformMesgEnum.ERR_30.getCode()).toString().equals(String.valueOf(result.get("errcode")))) {

            log.info("错误信息{}", UniformMesgEnum.ERR_30.getCode());
            return UniformMesgEnum.ERR_30.getName();

        } else if ((UniformMesgEnum.ERR_Temp.getCode()).toString().equals(String.valueOf(result.get("errcode")))) {

            log.info("错误信息{}", UniformMesgEnum.ERR_Temp.getCode());
            return UniformMesgEnum.ERR_Temp.getName();

        } else if ((UniformMesgEnum.ERR_OutNmber.getCode()).toString().equals(String.valueOf(result.get("errcode")))) {
            log.info("错误信息{}", UniformMesgEnum.ERR_OutNmber.getName());
            return UniformMesgEnum.ERR_OutNmber.getName(); }

        else if ((UniformMesgEnum.ERR_OPENID.getCode()).toString().equals(String.valueOf(result.get("errcode")))) {
            log.info("错误信息{}", UniformMesgEnum.ERR_OPENID.getName());
            return UniformMesgEnum.ERR_OPENID.getName();
        }
        else if ((UniformMesgEnum.ERR_APPID.getCode()).toString().equals(String.valueOf(result.get("errcode")))) {
            log.info("错误信息{}", UniformMesgEnum.ERR_APPID.getName());
            return UniformMesgEnum.ERR_APPID.getName();
        }
        else if ((UniformMesgEnum.OK.getCode()).toString().equals(String.valueOf(result.get("errcode")))) {
            log.info("错误信息{}", UniformMesgEnum.OK.getName());
            return UniformMesgEnum.OK.getName();
        }
        else {
            return "出现错误啦===>"+str;
        }
    }

    /**
     * <p>
     * 获取普通AccessToken（小程序公众号是同一个接口同参数）
     * </p>
     *
     * @author Steven
     * @since 2020-10-30
     */
    @GetMapping("/getAccessToken")
    public String getAccessToken(String appId,String appSecret) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("APPID",appId );
        params.put("APPSECRET",appSecret);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}", String.class, params);
        String body = responseEntity.getBody();
        JSONObject object = JSON.parseObject(body);
        String Access_Token = object.getString("access_token");
        String expires_in = object.getString("expires_in");
        log.info("有效时长expires_in:{}", expires_in);
        log.info("获取到的token{}",Access_Token);
        return Access_Token;
    }
//
//    /**
//     *
//     * 获取患者测评结果（中间程序调用国医慧联PC端API）
//     *  http://118.178.137.95:9000/api/v1/evas/bef5a4bcf88346419924ff23b8ef2352
//     *  返回值
//     *  {
//     * "status": 1,
//     * "info": "OK",
//     * "data": {
//     * "uid": "bef5a4bcf88346419924ff23b8ef2352",
//     * "puid": "7edecaced4e44f2695fc246b7665942c",
//     * "doctorid": 12345,
//     * "eva": 4,
//     * "title": "孕期女性健康状态测评",
//     * "result": "痰热",
//     * "report1": "痰热",
//     * "report2": "气滞:3;痰湿:60;痰热:60;肝火:15",
//     * "report3": "痰热",
//     * "recbrief": "",
//     * "recdetail": "痰热...（下略）",
//     * "intbreif": "",
//     * "intdetail": "",
//     * "createtime": "2018-08-15T15:31:20",
//     * "updatetime": "2018-08-15T15:31:20"
//     * }
//     * }
//     */
//
//    @RequestMapping(value = "evasResult")
//    public Object getEvasResult(@RequestParam Map<String, Object> params) {
//        //根据身份证获取患者id
//        //JSONObject patienInfo ;
//        String patientUrl = (String) (configAttibutes.getApiUrl().get("patientsUrl"));
//
//		/*if (null == params.get("idnumber") || "".equals(params.get("idnumber"))) {
//
//			patienInfo  = new JSONObject(HttpUtils.get(patientUrl, null, 3000, 3000, "UTF-8"));
//		}else{
//			patienInfo  = new JSONObject(HttpUtils.get(patientUrl, params, 3000, 3000, "UTF-8"));
//		}*/
//
//        JSONObject patientInfo  = new JSONObject(HttpUtils.get(patientUrl, params, 3000, 3000, "UTF-8"));
//        if (!"1".equals(String.valueOf(patientInfo.get("status")))) {
//            System.out.println("获取患者信息失败：" + patientInfo.get("info"));
//        } else{
//            JSONArray patientInfoArray = (JSONArray) patientInfo.get("data");
//            patientInfoArray.getJSONObject(0).get("uid");
//            params.put("puid", patientInfoArray.getJSONObject(0).get("uid"));//患者id
//        }
//        //根据患者id获取测评列表
//        //params.put("eva", 4);
//        String evasUrl = (String) (configAttibutes.getApiUrl().get("evasUrl"));
//        JSONObject evaResult  = new JSONObject(HttpUtils.get(evasUrl, params, 3000, 3000, "UTF-8"));
//        List evaList =null;
//        if (!"1".equals(String.valueOf(evaResult.get("status")))) {
//            logger.info("获取测评列表失败：" + evaResult.get("info"));
//        } else {
//
//            JSONArray evaArray = (JSONArray) evaResult.get("data");
//            evaList =evaArray.toList();//这里面的uid则为测评id
//
//        }
//
//
//        //根据返回的测评id再获取测评结果
//        if (null != evaList && evaList.size() > 0) {
//            for (int i = 0; i < evaList.size(); i++) {
//                String uid = (String) ((HashMap) evaList.get(i)).get("uid");
//                String url = (String) (configAttibutes.getApiUrl().get("evasResultUrl"))+"/"+uid;
//                JSONObject result  = new JSONObject(HttpUtils.get(url, params, 3000, 3000, "UTF-8"));
//                if (!"1".equals(String.valueOf(result.get("status")))) {
//                    System.out.println("获取测评结果失败："+uid+"\t\n" + result.get("info"));
//                }else{
//                    JSONObject evaResults = (JSONObject) result.get("data");
//                    logger.info(" title:"+evaResults.get("title")
//                            +" uid:"+evaResults.get("uid")+" "
//                            +" puid:"+evaResults.get("puid")
//                            +" eva:"+evaResults.get("eva")
//                            +" doctorid:"+evaResults.get("doctorid")
//                            +" result:"+evaResults.get("result")
//                            +" createtime:"+evaResults.get("createtime")
//                    );
//                }
//            }
//        }
//
//
//        return params;
//    }
//
//    /**
//     * 获取患者信息
//     * http://118.178.137.95:9000/api/v1/patients/?idnumber=123456198707311234
//     *
//     * 返回值{
//     * "status": 1,  0-请求失败，1-请求成功
//     * "info": "OK", 当 status 为 0 时，info 会返回具体错误原因，否则返回“OK”。
//     * "count": 1, 返回结果的个数
//     * "data": [
//     * {
//     * "uid": "7edecaced4e44f2695fc246b7665942c",
//     * "name": "包霜",
//     * "gender": 2,
//     * "birthday": "1987-07-31T00:00:00",
//     * "idnumber": "123456198707311234",
//     * "call": "",
//     * "createtime": "2018-07-23T14:55:12",
//     * "updatetime": "2018-08-15T15:18:17"
//     * }
//     * ]
//     * }
//     */
//    @RequestMapping(value = "patients")
//    public Object getPatients(@RequestParam Map<String, Object> params) {
//        JSONObject result ;
//        params.put("idnumber", "123456198707311234");//身份证号
//        String url = (String) (configAttibutes.getApiUrl().get("patientsUrl"));
//        if (null == params.get("idnumber") || "".equals(params.get("idnumber"))) {
//
//            result  = new JSONObject(HttpUtils.get(url, null, 3000, 3000, "UTF-8"));
//        }else{
//            result  = new JSONObject(HttpUtils.get(url, params, 3000, 3000, "UTF-8"));
//        }
//
//        return params;
//    }
//
//    /**
//     * 获取患者测评列表（中间程序调用国医慧联PC端API）
//     *
//     * http://118.178.137.95:9000/api/v1/evas/?puid=7edecaced4e44f2695fc246b7665942c&eva=4&limit=2
//     * 返回值
//     * {
//     * "status": 1,
//     * "info": "OK",
//     * "count": 5,
//     * "data": [
//     * {
//     * "uid": "bef5a4bcf88346419924ff23b8ef2352",
//     * "puid": "7edecaced4e44f2695fc246b7665942c",
//     * "doctorid": 12345,
//     * "eva": 4,
//     * "title": "孕期女性健康状态测评",
//     * "result": "痰热",
//     * "createtime": "2018-08-15T15:31:20",
//     * "updatetime": "2018-08-15T15:31:20"
//     * },
//     * {
//     * "uid": "57118c37fb1c4b62a2597a32f7811d08",
//     * "puid": "7edecaced4e44f2695fc246b7665942c",
//     * "doctorid": 12345,
//     * "eva": 4,
//     * "title": "孕期女性健康状态测评",
//     * "result": "阴虚血热;痰热;肝肾阴虚",
//     * "createtime": "2018-08-15T15:18:42",
//     * "updatetime": "2018-08-15T15:18:42"
//     * }
//     * ]
//     * }
//     */
//
//    @RequestMapping(value = "evas")
//    public Object getEvas(@RequestParam Map<String, Object> params) {
//        params.put("puid", "7edecaced4e44f2695fc246b7665942c");
//        String url = (String) (configAttibutes.getApiUrl().get("evasUrl"));
//        JSONObject result  = new JSONObject(HttpUtils.get(url, params, 3000, 3000, "UTF-8"));
//        return params;
//    }
//
//    @RequestMapping(value = "test")
//    public Object test(@RequestParam Map<String, Object> params) {
//        List list = this.sxyService.test();
//        System.out.println("********");
//        return null;
//    }
//
//


}