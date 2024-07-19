package com.example.finchiktelegrambot.jpa.repository;

import com.example.finchiktelegrambot.jpa.entity.DialogStateContext;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DialogStateContextRepository extends JpaRepository<DialogStateContext, Long> {

  Optional<DialogStateContext> findByIdTelegramUser(Long idTelegramUser);

}