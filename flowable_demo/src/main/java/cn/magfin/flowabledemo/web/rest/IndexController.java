package cn.magfin.flowabledemo.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @created: 11/5/21
 * @package: cn.magfin.flowabledemo.web.rest
 * @version: v1.0
 * @author: xiaoboli
 * @date: 11/5/21
 * @IDETools: IDEA
 * @description:
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "redirect:/black-list-remove/";
    }
}
