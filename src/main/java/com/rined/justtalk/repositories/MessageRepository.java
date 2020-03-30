package com.rined.justtalk.repositories;

import com.rined.justtalk.model.Message;
import com.rined.justtalk.model.User;
import com.rined.justtalk.model.dto.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select new com.rined.justtalk.model.dto.MessageDto(" +
            "m, " +
            "count(ml), " +
            "(sum(case when ml =:user then 1 else 0 end) > 0)" +
            ") from message m left join m.likes ml " +
            "where m.tag = :tag " +
            "group by m")
    Page<MessageDto> findMessagesByTag(@Param("tag") String tag, @Param("user") User user, Pageable pageable);

    @Query("select new com.rined.justtalk.model.dto.MessageDto(" +
            "m, " +
            "count(ml), " +
            "(sum(case when ml =:user then 1 else 0 end) > 0)" +
            ") from message m left join m.likes ml " +
            "group by m")
    Page<MessageDto> findAll(@Param("user") User user, Pageable pageable);

    @Query("select new com.rined.justtalk.model.dto.MessageDto(" +
            "m, " +
            "count(ml), " +
            "(sum(case when ml =:user then 1 else 0 end) > 0)" +
            ") from message m left join m.likes ml " +
            "where m.author = :author " +
            "group by m")
    Page<MessageDto> findByUser(@Param("author") User messageAuthor, @Param("user") User user, Pageable pageable);
}
