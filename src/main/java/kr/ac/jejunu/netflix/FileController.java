package kr.ac.jejunu.netflix;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Controller
@RequiredArgsConstructor
public class FileController {
    private final UserDao userDao;
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
        modelAndView.addObject("path", pathStr+file.getOriginalFilename());

        return modelAndView;
    }
}
