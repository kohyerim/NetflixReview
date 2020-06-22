package kr.ac.jejunu.netflix;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Controller
@RequiredArgsConstructor
public class FileController {
    @GetMapping("/upload")
    public void upload(){

    }

    @PostMapping("/upload")
    public ModelAndView upload(@RequestParam("id") Integer id, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        String pathStr = request.getServletContext().getRealPath("/")+"/WEB-INF/static/csv/" + id.toString();
        File folderMaker = new File(pathStr);
        if(!folderMaker.exists()){
            folderMaker.mkdir();
        }
        File path = new File(pathStr + "/" + file.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(file.getBytes());
        bufferedOutputStream.close();

        ModelAndView modelAndView = new ModelAndView("upload");
        modelAndView.addObject("url", "/csv/" + id.toString() +"/"+ file.getOriginalFilename());
        return modelAndView;
    }
}
