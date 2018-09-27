package com.example.livedemo.service;

import com.example.livedemo.dao.VideoDemandDao;
import com.example.livedemo.entity.VideoDemand;
import com.example.livedemo.utils.FFmpegUtils;
import com.example.livedemo.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class DemandService {
    @Autowired
    private VideoDemandDao videoDemandDao;

    @Value("${ffmpeg.path}")
    private String ffmpegPath;

    @Value("${demand.video.path}")
    private String videoPath;

    @Value("${media.pic.path}")
    private String picPath;

    public Page<VideoDemand> getDemand(String type, Pageable pageable){
        if("0".equals(type)) {
            return videoDemandDao.findAll(pageable);
        }else{
            return videoDemandDao.findByType(type, pageable);
        }
    }

    public String cutPic(String videoName){
        String picName = videoName.substring(videoName.lastIndexOf("/") + 1) + ".jpg";
        FFmpegUtils.mediaPic(ffmpegPath, videoPath + videoName, picPath + picName);
        return picName;
    }

    public void save(MultipartFile file, VideoDemand videoDemand) throws IOException {
        if(file.getOriginalFilename().lastIndexOf(".") < 0){
            throw new IOException("文件类型无法识别");
        }

        String postfix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String savaName = UUID.randomUUID().toString() + postfix;

        //保存文件
        FileUtil.uploadFile((FileInputStream) file.getInputStream(), videoPath, savaName);

        //保存截图
        String picName = cutPic(savaName);

//        if(!".mp4".equals(postfix.toLowerCase()) & !".flv".equals(postfix.toLowerCase())){
//            String fileName = savaName.substring(0, savaName.lastIndexOf("."));
//            FFmpegUtils.toMp4(ffmpegPath, videoPath + savaName, videoPath + fileName + ".mp4");
//            savaName = fileName + ".mp4";
//        }

        videoDemand.setImage_path("http://192.168.1.137:8881/pic/" + picName);
        videoDemand.setName(file.getOriginalFilename());
        videoDemand.setPath("http://192.168.1.137:8080/" + savaName);
        videoDemandDao.saveAndFlush(videoDemand);
    }

    /**
     * 删除视频源
     * @param id
     */
    public void delete(long id){
        Optional<VideoDemand> videoDemandOptional = videoDemandDao.findById(id);
        VideoDemand videoDemand = videoDemandOptional.orElse(null);
        if(videoDemand == null)
            return;

        String videoUrl = videoDemand.getPath();
        String imageUrl = videoDemand.getImage_path();

        File videoFile = new File(videoPath + videoUrl.substring(videoUrl.lastIndexOf("/")));
        File imageFile = new File(picPath + imageUrl.substring(imageUrl.lastIndexOf("/")));
        if(videoFile.exists() && videoFile.isFile())
            videoFile.delete();
        if(imageFile.exists() && imageFile.isFile())
            imageFile.delete();

        videoDemandDao.deleteById(id);
    }
}
