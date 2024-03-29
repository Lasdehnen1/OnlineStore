package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id; //id пользователя

    private String email; //логин пользователя

    private String firstName; //имя пользователя

    private String lastName;	//фамилия пользователя

    private String phone;	//телефон пользователя

    private Role role; //роль пользователя

    private String image; //ссылка на аватар пользователя

}
