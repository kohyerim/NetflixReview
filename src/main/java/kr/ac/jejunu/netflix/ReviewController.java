package kr.ac.jejunu.netflix;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final UserDao userDao;
    private final ReviewDao reviewDao;

    @RequestMapping("/review")
    public ModelAndView review(@RequestParam(value = "user_id") Integer id) throws IOException {
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
    public ModelAndView register(@RequestParam("netflix_title") String netflix_title,
                                 @RequestParam("user_id") Integer user_id,
                                 @RequestParam("date") String date,
                                 @RequestParam("review_title") String review_title,
                                 @RequestParam("comment") String comment,
                                 @RequestParam("stars") Integer stars){
        ModelAndView modelAndView = new ModelAndView("upload");
        modelAndView.addObject("user_id", user_id);

        Review review = new Review();
        saveReview(user_id, netflix_title, date, review_title, comment, stars, review);

        return modelAndView;
    }

    @RequestMapping(value = "view")
    public ModelAndView view(@RequestParam("review_id") Integer review_id,
                             @RequestParam("user_id") Integer user_id){
        Review review = reviewDao.findById(review_id).get();
        ModelAndView modelAndView = new ModelAndView("view");
        return getModelAndView(review_id, review, user_id, review.getReview_content(), modelAndView);
    }

    @RequestMapping(value = "delete")
    public ModelAndView delete(@RequestParam("review_id") Integer review_id){
        Review review = reviewDao.findById(review_id).get();
        reviewDao.deleteById(review_id);

        ModelAndView modelAndView = new ModelAndView("/upload");
        modelAndView.addObject("user_id", review.getUser_id());

        return modelAndView;
    }

    @RequestMapping(value = "edit")
    public ModelAndView edit(@RequestParam("review_id") Integer review_id){
        Review review = reviewDao.findById(review_id).get();
        Integer user_id = review.getUser_id();
        ModelAndView modelAndView = new ModelAndView("edit");
        return getModelAndView(review_id, review, user_id, review.getReview_content().replaceAll("<br>", "\n"), modelAndView);
    }

    @RequestMapping(value = "update")
    public ModelAndView update(@RequestParam("review_id") Integer review_id,
                               @RequestParam("user_id") Integer user_id,
                               @RequestParam("netflix_title") String netflix_title,
                               @RequestParam("date") String date,
                               @RequestParam("review_title") String review_title,
                               @RequestParam("comment") String comment,
                               @RequestParam("stars") Integer stars){
        Review review = reviewDao.findById(review_id).get();
        saveReview(user_id, netflix_title, date, review_title, comment, stars, review);

        ModelAndView modelAndView = new ModelAndView("/upload");
        modelAndView.addObject("user_id", review.getUser_id());

        return modelAndView;
    }

    private ModelAndView getModelAndView(@RequestParam("review_id") Integer review_id, Review review, Integer user_id, String comment, ModelAndView modelAndView) {
        modelAndView.addObject("review_id", review_id);
        modelAndView.addObject("author_id", reviewDao.findById(review_id).get().getUser_id());
        modelAndView.addObject("user_id", user_id);
        modelAndView.addObject("title", review.getNetflix_title());
        modelAndView.addObject("date", review.getDate());
        modelAndView.addObject("name", userDao.findById(reviewDao.findById(review_id).get().getUser_id()).get().getName());
        modelAndView.addObject("review_title", review.getReview_title());
        modelAndView.addObject("comment", comment);
        modelAndView.addObject("stars", review.getStars());

        return modelAndView;
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
