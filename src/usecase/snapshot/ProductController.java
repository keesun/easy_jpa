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

    @RequestMapping("/evictall")
    public String evictAllCache(){
        service.evictAllCache();
        return "redirect:/";
    }

    @RequestMapping("/product/with/all")
    public String productWithAll(Model model){
        model.addAttribute("prdList", service.getAll());
        return "/productWithAll";
    }

    @RequestMapping("/product/with/detail")
    public String productWithDetail(Model model){
        model.addAttribute("prdList", service.getAll());
        return "/productAndDetail";
    }

    @RequestMapping("/category")
    public String category(Model model){
        model.addAttribute("categoryList", service.getAllCategory());
        return "redirect:/";
    }

    @RequestMapping("/product/with/category")
    public String productWithCategory(Model model){
        model.addAttribute("prdList", service.getAll());
        return "/productWithCategory";
    }



}
