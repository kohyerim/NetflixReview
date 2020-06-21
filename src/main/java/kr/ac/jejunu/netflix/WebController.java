package kr.ac.jejunu.netflix;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class WebController {
    private final UserDao userDao;

    @GetMapping("/user/{id}")
    public User get(@PathVariable("id") Integer id){
        return userDao.findById(id).get();
    }

    @RequestMapping("/review")
    public ModelAndView test(){
        ModelAndView modelAndView = new ModelAndView("web");
        modelAndView.addObject("name", userDao.findById(1).get().getName());

        return modelAndView;
    }
}
