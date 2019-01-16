package com.fin.security;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Credentials implements Serializable {

    private String username;
    private String password;
}