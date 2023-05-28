package az.code.travelTechdemo.util;

import org.springframework.stereotype.Service;

@Service
public class EmailTexts {
    private static EmailTexts instance;

    private String PASSWORD_RESET_SUBJECT_TEXT;
    private String PASSWORD_RESET_BODY_TEXT;

    private EmailTexts(){
        PASSWORD_RESET_SUBJECT_TEXT = "Travel tech Customer support";
        PASSWORD_RESET_BODY_TEXT = "Please use the OTP below in order \n" +
                " to change your password: ";
    }


    //double locking
    public static EmailTexts getInstance() {
        if (instance == null) {
            synchronized (EmailTexts.class) {
                if (instance == null) {
                    instance = new EmailTexts();
                }
            }
        }
        return instance;
    }

    public String getEmailSubject() {
        return PASSWORD_RESET_SUBJECT_TEXT;
    }

    public String getEmailBody() {
        return PASSWORD_RESET_BODY_TEXT;
    }

}
