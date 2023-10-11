package com.gl05.bad.dao;

import com.gl05.bad.domain.ResetPassword;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface ResetPasswordDao extends DataTablesRepository<ResetPassword, Long> {
    ResetPassword findByToken(String token);
}