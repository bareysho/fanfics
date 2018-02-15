package by.bareysho.fanfics.service;

import java.io.IOException;

public interface ImageService {
    String uploadPhoto(byte[] photoBytes) throws IOException;
}
