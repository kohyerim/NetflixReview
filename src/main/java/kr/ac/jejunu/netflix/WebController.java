package kr.ac.jejunu.netflix;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
public class WebController {
    private final UserDao userDao;

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");

        return modelAndView;
    }

    @GetMapping("/user/{id}")
    public User get(@PathVariable("id") Integer id){
        return userDao.findById(id).get();
    }

    @GetMapping("/review")
    public void review(){

    }

    @PostMapping("/review")
    public ModelAndView review(@RequestParam("id") Integer id, @RequestParam("pw") String pw){
        ModelAndView modelAndView;
        if (pw.equals(userDao.findById(id).get().getPw())){
            modelAndView = new ModelAndView("web");
            modelAndView.addObject("name", userDao.findById(id).get().getName());
            modelAndView.addObject("id", userDao.findById(id).get().getId());
        }
        else{
            modelAndView = new ModelAndView(new RedirectView("/"));
        }

        return modelAndView;
    }
}
