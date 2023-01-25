package com.hristoforov.elective.constants;

/**
 * Contains validation regexes
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public class ValidationConstants {
    public static final String LOGIN_REGEX = "[A-Za-zА-Яа-я0-9]{4,16}";
    public static final String PASSWORD_REGEX = "(?=.*[a-zа-я])(?=.*[A-ZА-Я])(?=.*\\d)[a-zа-яA-ZА-Я\\d]{8,}";
    public static final String EMAIL_REGEX = "[a-z0-9]+@[a-z0-9.-]+\\.[a-z]{2,4}";
    public static final String NAME_REGEX = "[A-Za-zА-Яа-я]+";
    public static final String TITLE_REGEX = "[A-Za-zА-Яа-я]+[+-_#?!.]*";
}
