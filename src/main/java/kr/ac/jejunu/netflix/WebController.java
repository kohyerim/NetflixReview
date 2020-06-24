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
    public ModelAndView fileupload(@RequestParam("id") Integer id, @RequestParam("pw") String pw,
                                   HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = null;
        if (pw.equals(userDao.findById(id).get().getPw())){
            String pathStr = request.getServletContext().getRealPath("/")+"WEB-INF/static/csv/" + id.toString() + "/";
            File file = new File(pathStr);
            if(!file.exists()){
                modelAndView = new ModelAndView("fileupload");
                modelAndView.addObject("name", userDao.findById(id).get().getName());
                modelAndView.addObject("id", userDao.findById(id).get().getId());
            }
            else{
                String fileName = "NetflixViewingHistory.csv";
                String path = pathStr + fileName;
                modelAndView = review(id, path);
            }
        }
        else{
            modelAndView = new ModelAndView(new RedirectView("/"));
        }
        return modelAndView;
    }

    @RequestMapping("/review")
    public ModelAndView review(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam(value = "path", required = false) String path) throws IOException {

        List<List<String>> list = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        BufferedReader br;
        br = Files.newBufferedReader(Paths.get(path));
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

        ModelAndView modelAndView = new ModelAndView("review");
        modelAndView.addObject("name", userDao.findById(id).get().getName());
        modelAndView.addObject("id", id);
        modelAndView.addObject("list", list);
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

    @RequestMapping("/register")
    public ModelAndView register(@RequestParam("netflix_title") String netflix_title,
                                 @RequestParam("user_id") String user_id,
                                 @RequestParam("date") String date,
                                 @RequestParam("review_title") String review_title,
                                 @RequestParam("comment") String comment,
                                 @RequestParam("stars") Integer stars,
                                 HttpServletRequest request){

        String pathStr = request.getServletContext().getRealPath("/")+"WEB-INF/static/csv/" + user_id + "/";
        String fileName = "NetflixViewingHistory.csv";
        String path = pathStr + fileName;
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("id", user_id);
        modelAndView.addObject("path", path);

        Review review = new Review();
        review.setNetflix_title(netflix_title);
        review.setUser_id(Integer.valueOf(user_id));
        review.setReview_title(review_title);
        review.setDate(date);
        review.setReview_content(comment);
        review.setStars(stars);

        reviewDao.save(review);

        return modelAndView;
    }
}
