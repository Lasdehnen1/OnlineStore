package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Avatar;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AvatarService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
/**
 * <b>Сервис аватарок</b>
 * Хранит основную логику по работе с аватарами пользователей
 */
@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    public static String uploadDirectory = System.getProperty("user.dir") + "/images"; // user.dir - используемая директория
    private final AvatarRepository repository;
    private final UserRepository userRepository;

    /**
     * Метод удаления аватара из репозитория
     *
     * @param avatar аватар
     *
     */
    @Override
    public void removeAvatar(Avatar avatar) {
        repository.delete(avatar);

    }
    /**
     * Метод поиска аватара пользователя по id в репозитории
     *
     * @param id id аватара
     *
     */
    @Override
    public Avatar getAvatar(Long id) {
        return repository.findById(id).orElse(new Avatar());
    }

    /**
     * Метод загрузки аватара
     *
     * @param image изображение
     * @throws IOException
     */
    @Override
    public Avatar uploadAvatar(MultipartFile image) throws IOException {
        User user = userRepository.findUserByEmailIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        Path filePath = Path.of(uploadDirectory,"user_" + user.getId() + "." + getExtensions(image.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = new Avatar();
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(image.getSize());
        avatar.setMediaType(image.getContentType());
        avatar.setData(image.getBytes());
        repository.save(avatar);
        return avatar;
    }

    /**
     * Метод получения формата файла
     *
     * @param fileName имя файла
     *
     */
    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }



}
