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
        return new ModelAndView("index");
    }

    @GetMapping("/user/{id}")
    public User get(@PathVariable("id") Integer id){
        return userDao.findById(id).get();
    }

    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    public Object fileupload(@RequestParam("user_id") Integer id, @RequestParam("pw") String pw){
        RedirectView redirectView;
        if (pw.equals(userDao.findById(id).get().getPw())){
            File file = new File(userDao.findById(id).get().getPath());
            if(!file.exists()){
                ModelAndView modelAndView;
                modelAndView = new ModelAndView("fileupload");
                modelAndView.addObject("name", userDao.findById(id).get().getName());
                modelAndView.addObject("user_id", userDao.findById(id).get().getId());
                return modelAndView;
            }
            else{
                redirectView = new RedirectView("review");
                redirectView.addStaticAttribute("user_id", id);
            }
        }
        else{
            redirectView = new RedirectView("/");
        }
        return redirectView;
    }

    @RequestMapping("/review")
    public ModelAndView review(@RequestParam(value = "user_id", required = false) Integer id) throws IOException {
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
        modelAndView.addObject("user_id", id);
        modelAndView.addObject("list", list);
        modelAndView.addObject("reviews", reviews);


        return modelAndView;
    }

    @RequestMapping("/write")
    public ModelAndView write(@RequestParam("select") String select,
                              @RequestParam("user_id") Integer id){
        String title = select.split(",")[0];
        String date = select.split(",")[1];
        ModelAndView modelAndView = new ModelAndView("write");
        modelAndView.addObject("title", title);
        modelAndView.addObject("date", date);
        modelAndView.addObject("name", userDao.findById(id).get().getName());
        modelAndView.addObject("user_id", id);

        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RedirectView register(@RequestParam("netflix_title") String netflix_title,
                                 @RequestParam("user_id") Integer user_id,
                                 @RequestParam("date") String date,
                                 @RequestParam("review_title") String review_title,
                                 @RequestParam("comment") String comment,
                                 @RequestParam("stars") Integer stars){

        RedirectView redirectView = new RedirectView("/review");
        redirectView.addStaticAttribute("user_id", user_id);

        Review review = new Review();
        saveReview(user_id, netflix_title, date, review_title, comment, stars, review);

        return redirectView;
    }

    @RequestMapping(value = "view")
    public ModelAndView view(@RequestParam("review_id") Integer review_id){
        Review review = reviewDao.findById(review_id).get();
        ModelAndView modelAndView = new ModelAndView("view");
        return getModelAndView(review_id, review, review.getReview_content(), modelAndView);
    }

    @RequestMapping(value = "delete")
    public RedirectView delete(@RequestParam("review_id") Integer review_id){
        Review review = reviewDao.findById(review_id).get();
        reviewDao.deleteById(review_id);

        RedirectView redirectView = new RedirectView("/review");
        redirectView.addStaticAttribute("id", review.getUser_id());

        return redirectView;
    }

    @RequestMapping(value = "edit")
    public ModelAndView edit(@RequestParam("review_id") Integer review_id){
        Review review = reviewDao.findById(review_id).get();
        ModelAndView modelAndView = new ModelAndView("edit");
        return getModelAndView(review_id, review, review.getReview_content().replaceAll("<br>", "\n"), modelAndView);
    }

    private ModelAndView getModelAndView(@RequestParam("review_id") Integer review_id, Review review, String comment, ModelAndView modelAndView) {
        modelAndView.addObject("review_id", review_id);
        modelAndView.addObject("user_id", reviewDao.findById(review_id).get().getUser_id());
        modelAndView.addObject("title", review.getNetflix_title());
        modelAndView.addObject("date", review.getDate());
        modelAndView.addObject("name", userDao.findById(reviewDao.findById(review_id).get().getUser_id()).get().getName());
        modelAndView.addObject("review_title", review.getReview_title());
        modelAndView.addObject("comment", comment);
        modelAndView.addObject("stars", review.getStars());

        return modelAndView;
    }

    @RequestMapping(value = "update")
    public RedirectView update(@RequestParam("review_id") Integer review_id,
                               @RequestParam("user_id") Integer user_id,
                               @RequestParam("netflix_title") String netflix_title,
                               @RequestParam("date") String date,
                               @RequestParam("review_title") String review_title,
                               @RequestParam("comment") String comment,
                               @RequestParam("stars") Integer stars){
        Review review = reviewDao.findById(review_id).get();
        saveReview(user_id, netflix_title, date, review_title, comment, stars, review);

        RedirectView redirectView = new RedirectView("/review");
        redirectView.addStaticAttribute("user_id", review.getUser_id());

        return redirectView;
    }

    private void saveReview(@RequestParam("user_id") Integer user_id, @RequestParam("title") String netflix_title, @RequestParam("date") String date, @RequestParam("review_title") String review_title, @RequestParam("comment") String comment, @RequestParam("stars") Integer stars, Review review) {
        review.setNetflix_title(netflix_title);
        review.setUser_id(user_id);
        review.setReview_title(review_title);
        review.setDate(date);
        review.setReview_content(comment.replaceAll("\r\n", "<br>"));
        review.setStars(stars);

        reviewDao.save(review);
    }
}
