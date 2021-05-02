import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.conditions.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

class SelenideTest {
    public String DatePlusCount(int count) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, count);
        return dateFormat.format(calendar.getTime());
    }


    @BeforeEach
    void init() {
        open("http://localhost:9999/");


    }

    @Test
    void ShouldPass() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").setValue(DatePlusCount(3));
        $("[name='name']").setValue("Василий - Петрович");
        $("[name='phone']").setValue("+71234567890");
//        $(".checkbox__box").click();
        $(".button").click();
        $(new Selectors.WithText("Успешно!")).waitUntil(Condition.visible,15000);

    }
    @Test
    void ShouldChooseCity() {
        $("[placeholder='Город']").setValue("рр");
        $$(".menu-item__control").find(new Text("Нарьян-Мар")).click();
        $("[placeholder='Город']").getValue().equalsIgnoreCase("Нарьян-Мар");

    }

}