package list.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ztang
 * @date 10:46 2018/3/27
 */
@RestController
public class TestController {



    @GetMapping("test")
    public String test() {
        return "helloworld";
    }
}
