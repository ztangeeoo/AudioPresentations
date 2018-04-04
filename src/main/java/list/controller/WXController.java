package list.controller;

import com.thoughtworks.xstream.XStream;
import list.constant.Constant;
import list.dto.TestMessageDTO;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/30.
 */
@RestController
public class WXController {
    private static  final Logger logger = LoggerFactory.getLogger(WXController.class);

    @GetMapping(value = "ztang/tvlist")
    public Object listAudio(HttpServletRequest request){

            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
             String echostr = request.getParameter("echostr");
             return echostr;

    }
    @PostMapping(value = "ztang/tvlist")
    public Object listTv(HttpServletRequest request){
        Map<String, String> map = xmlToMap(request);
        logger.info("用户访问的参数：map={}",map);
        String toUserName = map.get("ToUserName");

        String fromUserName = map.get("FromUserName");
        String createTime = map.get("CreateTime");
        String msgType = map.get("MsgType");
        String content = map.get("Content");
        String msgId = map.get("MsgId");
        String str = null;
        TestMessageDTO message = new TestMessageDTO();
        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);
        message.setMsgType("text");
        message.setCreateTime(new Date().getTime()+"");
        if(msgType.equals("text")){
            message.setContent("傻逼吧你！");
        }else if(msgType.equals(Constant.REQ_MESSAGE_TYPE_EVENT)){
            String event = map.get("Event");
            if (event.equals(Constant.EVENT_TYPE_SUBSCRIBE)) {
                message.setContent("谢谢关注！");
                String eventKey = map.get("EventKey");
                if(eventKey.equals("qrscene_123")){
                  //  content = "点击打开与本书连接的音频！";
                    String s = eventKey.substring(8);
                    message.setMsgType("link");
                    message.setTitle("小学语文课外指导书");
                    message.setUrl("http://119.29.177.27:80/audio/getAudioList/"+s);
                    message.setDescription("你可以信赖的品牌");
                    message.setContent(null);
                }
            }   else if (msgType.equals(Constant.EVENT_TYPE_UNSUBSCRIBE)) {
            // 取消关注,用户接受不到我们发送的消息了，可以在这里记录用户取消关注的日志信息
        }  else if (event.equals(Constant.EVENT_TYPE_CLICK)) {
            // 事件KEY值，与创建自定义菜单时指定的KEY值对应  
            String eventKey = map.get("EventKey");
            // 自定义菜单点击事件  
            if (eventKey.equals("11")) {
                content = "天气预报菜单项被点击！";
                message.setContent(content);
            } else if (eventKey.equals("12")) {
                content = "公交查询菜单项被点击！";
                message.setContent(content);
            }
        }

        }
        str = objectToXml(message);
        logger.info("系统返回给用户的参数，str={}",str);
        return str;
    }



    public Map<String,String> xmlToMap(HttpServletRequest request)  {

        HashMap<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();

        Document doc = null;
        try {
            ServletInputStream is = request.getInputStream();
            doc = reader.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = doc.getRootElement();
        List<Element> list = root.elements();
        for(Element e: list){
            map.put(e.getName(),e.getText());
        }
        return map;
    }
    public static String objectToXml(TestMessageDTO message){
        XStream xs = new XStream();
        xs.alias("xml",message.getClass());
        return xs.toXML(message);
    }


}
