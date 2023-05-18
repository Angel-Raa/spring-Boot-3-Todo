package com.caja.ideal.event;

import com.caja.ideal.user.UserModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;
@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private UserModel user;
    private String appUrl;

    public RegistrationCompleteEvent( UserModel user, String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
    }
}
