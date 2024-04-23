package com.bookkeeping.Repository;

import com.bookkeeping.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookKeepingRepository extends JpaRepository<Book,Integer> {

}
