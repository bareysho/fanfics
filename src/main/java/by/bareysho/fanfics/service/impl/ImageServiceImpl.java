package by.bareysho.fanfics.service.impl;

import com.cloudinary.Cloudinary;
import by.bareysho.fanfics.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;


@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Value("${cloudinary.url.base}")
    private String cloudinaryUrlBase;

    @Override
    public String uploadPhoto(byte[] photoBytes) throws IOException {
        Map<String, String> map = null;
        try{
            File file = new File("img");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(photoBytes);
            fileOutputStream.flush();
            fileOutputStream.close();
            map = cloudinary.uploader().upload(file, Cloudinary.emptyMap());

        }catch (IOException ex){
            ex.printStackTrace();
        }
        return map.get("url");
    }
}
