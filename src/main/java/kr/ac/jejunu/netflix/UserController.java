package kr.ac.jejunu.netflix;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserDao userDao;

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");

        return modelAndView;
    }

    @RequestMapping("/signup")
    public ModelAndView signup(){
        return new ModelAndView("signup");
    }

    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    public ModelAndView userRegiter(@RequestParam("email") String email,
                                    @RequestParam("name") String name,
                                    @RequestParam("pw") String pw){
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPw(pw);
        userDao.save(user);

        ModelAndView modelAndView = new ModelAndView("userRegister");
        modelAndView.addObject("user_id", user.getId());

        return modelAndView;
    }

    @RequestMapping("/login")
    public Object login(@RequestParam("email") String email,
                              @RequestParam("pw") String pw){
        List<User> users = userDao.findAll();
        Integer user_id = null;
        for(int i = 0; i<users.size(); i++){
            if(users.get(i).getEmail().equals(email) && users.get(i).getPw().equals(pw)){
                user_id = i+1;
                break;
            }
        }

        if(user_id==null){
            return new RedirectView("/");
        }
        else {
            String path = userDao.findById(user_id).get().getPath();
            System.out.println(path);
            if(path != null){
                ModelAndView modelAndView = new ModelAndView("upload");
                modelAndView.addObject("user_id", user_id);
                return modelAndView;
            }
            else {
                ModelAndView modelAndView = new ModelAndView("fileupload");
                modelAndView.addObject("name", userDao.findById(user_id).get().getName());
                modelAndView.addObject("user_id", user_id);
                return modelAndView;
            }
        }
    }

    @RequestMapping(value = "/fileupload")
    public ModelAndView fileupload(@RequestParam("user_id") Integer id){
        ModelAndView modelAndView;

        modelAndView = new ModelAndView("fileupload");
        modelAndView.addObject("name", userDao.findById(id).get().getName());
        modelAndView.addObject("user_id", userDao.findById(id).get().getId());

        return modelAndView;
    }

    @RequestMapping("/upload")
    public ModelAndView upload(@RequestParam("user_id") Integer id,
                               @RequestParam("file") MultipartFile file,
                               HttpServletRequest request) throws IOException {
        String pathStr = request.getServletContext().getRealPath("/")+"WEB-INF/static/csv/" + id.toString() + "/";
        File folderMaker = new File(pathStr);
        if(!folderMaker.exists()){
            folderMaker.mkdir();
        }
        File path = new File(pathStr + file.getOriginalFilename());

        FileOutputStream fileOutputStream = new FileOutputStream(path);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(file.getBytes());
        bufferedOutputStream.close();

        User user = userDao.findById(id).get();
        user.setPath(path.getPath());
        userDao.save(user);

        ModelAndView modelAndView = new ModelAndView("upload");
        modelAndView.addObject("user_id", id);

        return modelAndView;
    }
}
