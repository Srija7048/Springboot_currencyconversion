package com.Exchange.Repository;

import com.Exchange.Entity.Conversiontable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Datarepository extends JpaRepository<Conversiontable,Long> {
}
