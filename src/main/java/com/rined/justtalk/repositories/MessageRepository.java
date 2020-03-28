package com.rined.justtalk.repositories;

import com.rined.justtalk.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findMessagesByTag(String tag, Pageable pageable);

    Page<Message> findAll(Pageable pageable);

}
