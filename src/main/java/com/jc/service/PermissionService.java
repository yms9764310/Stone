package com.jc.service;


import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface PermissionService {
    Set<String> listPermissionName(String name);

}
