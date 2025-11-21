package com.schedule.app.repository;

import com.schedule.app.security.AuthUser;

public interface AuthUserMapper {
    AuthUser readAuthUser(String userId);
}
