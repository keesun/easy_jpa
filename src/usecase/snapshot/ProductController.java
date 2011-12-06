package usecase.snapshot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: keesun
 */
@Controller
public class ProductController {

    @Autowired ProductService service;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("prdList", service.getAll());
        return "/product";
    }

    @RequestMapping("/data")
    public String insertTestData() {
        service.insertTestData();
        return "redirect:/";
    }

    @RequestMapping("/evict")
    public String evictCache() {
        service.evictCacheData();
        return "redirect:/";
    }
}
