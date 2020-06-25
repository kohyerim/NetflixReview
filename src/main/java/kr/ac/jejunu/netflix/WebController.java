package kr.ac.jejunu.netflix;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WebController {
    private final UserDao userDao;
    private final ReviewDao reviewDao;

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");

        return modelAndView;
    }

    @GetMapping("/user/{id}")
    public User get(@PathVariable("id") Integer id){
        return userDao.findById(id).get();
    }

    @GetMapping("/fileupload")
    public void fileupload(){

    }

    @PostMapping("/fileupload")
    public Object fileupload(@RequestParam("id") Integer id, @RequestParam("pw") String pw,
                             HttpServletRequest request){
        RedirectView redirectView;
        if (pw.equals(userDao.findById(id).get().getPw())){
            File file = new File(userDao.findById(id).get().getPath());
            if(!file.exists()){
                ModelAndView modelAndView;
                modelAndView = new ModelAndView("fileupload");
                modelAndView.addObject("name", userDao.findById(id).get().getName());
                modelAndView.addObject("id", userDao.findById(id).get().getId());
                return modelAndView;
            }
            else{
                redirectView = new RedirectView("review");
                redirectView.addStaticAttribute("id", id);
            }
        }
        else{
            redirectView = new RedirectView("review");
        }
        return redirectView;
    }

    @RequestMapping("/review")
    public ModelAndView review(@RequestParam(value = "id", required = false) Integer id) throws IOException {

        List<List<String>> list = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        BufferedReader br;
        br = Files.newBufferedReader(Paths.get(userDao.findById(id).get().getPath()));
        String line;
        while ((line = br.readLine()) != null){
            List<String> tmpList;
            String[] array = line.split(",");
            tmpList = Arrays.asList(array);
            for(int i=0; i<tmpList.size(); i++){
                 tmpList.set(i, tmpList.get(i).replaceAll("\"", ""));
            }
            List<String> tmpTitle = Arrays.asList(tmpList.get(0).split(":"));
            String title = "";
            String date = tmpList.get(tmpList.size()-1);
            if(tmpTitle.size() == 1){
                title = tmpTitle.get(0);
            }
            else{
                title = tmpTitle.get(0) + tmpTitle.get(1);
            }

            if(!titleList.contains(title)){
                titleList.add(title);
                list.add(Arrays.asList(title, date));
            }
        }

        List<Review> reviews = reviewDao.findAll();

        ModelAndView modelAndView = new ModelAndView("review");
        modelAndView.addObject("name", userDao.findById(id).get().getName());
        modelAndView.addObject("id", id);
        modelAndView.addObject("list", list);
        modelAndView.addObject("reviews", reviews);


        return modelAndView;
    }

    @RequestMapping("/write")
    public ModelAndView write(@RequestParam("select") String select,
                              @RequestParam("id") Integer id){
        String title = select.split(",")[0];
        String date = select.split(",")[1];
        ModelAndView modelAndView = new ModelAndView("write");
        modelAndView.addObject("title", title);
        modelAndView.addObject("date", date);
        modelAndView.addObject("name", userDao.findById(id).get().getName());
        modelAndView.addObject("id", id);

        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RedirectView register(@RequestParam("netflix_title") String netflix_title,
                                 @RequestParam("user_id") String user_id,
                                 @RequestParam("date") String date,
                                 @RequestParam("review_title") String review_title,
                                 @RequestParam("comment") String comment,
                                 @RequestParam("stars") Integer stars,
                                 HttpServletRequest request){

        RedirectView redirectView = new RedirectView("/review");
        redirectView.addStaticAttribute("id", user_id);

        Review review = new Review();
        review.setNetflix_title(netflix_title);
        review.setUser_id(Integer.valueOf(user_id));
        review.setReview_title(review_title);
        review.setDate(date);
        review.setReview_content(comment.replaceAll("\r\n", "<br>"));
        review.setStars(stars);

        reviewDao.save(review);

        return redirectView;
    }

    @RequestMapping(value = "view")
    public ModelAndView view(@RequestParam("review_id") Integer review_id){
        Review review = reviewDao.findById(review_id).get();
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("title", review.getNetflix_title());
        modelAndView.addObject("date", review.getDate());
        modelAndView.addObject("name", userDao.findById(reviewDao.findById(review_id).get().getUser_id()).get().getName());
        modelAndView.addObject("review_title", review.getReview_title());
        modelAndView.addObject("comment", review.getReview_content());
        modelAndView.addObject("stars", review.getStars());

        return modelAndView;
    }
}
