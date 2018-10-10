package com.peanutbutter.register.service;

import com.peanutbutter.register.entity.User;

public interface RegisterService {

    User register(User user);
    User confirm(User user, String password);
}
