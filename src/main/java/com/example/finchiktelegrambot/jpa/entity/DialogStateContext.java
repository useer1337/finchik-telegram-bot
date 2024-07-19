package com.example.finchiktelegrambot.jpa.entity;

import com.example.finchiktelegrambot.enums.DialogState;
import com.example.finchiktelegrambot.model.context.Context;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dialog_state_context")
public class DialogStateContext {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "id_telegram_user")
  private Long idTelegramUser;

  @Column(name = "state")
  private String state;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "context")
  private Context context;

  public DialogState getState() {
    return DialogState.valueOf(this.state);
  }

}