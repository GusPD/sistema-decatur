package com.gl05.bad.dao;

import com.gl05.bad.domain.RedSocial;
import org.springframework.data.jpa.repository.JpaRepository;
/*import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;*/

public interface RedSocialDao extends JpaRepository<RedSocial, Long>{
}
