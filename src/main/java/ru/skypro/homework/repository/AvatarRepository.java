package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

}
