import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.ExactText;
import com.codeborne.selenide.conditions.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

class SelenideTest {

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @BeforeEach
    public void init() {
        open("http://localhost:9999/");
        clearBrowserCookies();
        clearBrowserLocalStorage();

    }

    @Test
    void shouldPass() {
        String dateOfDelivery = LocalDate.now().plusDays(3)////Указать сколько дней до доставки
                .format(format);

        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").setValue(dateOfDelivery);
        $("[name='name']").setValue("Василий - Петрович");
        $("[name='phone']").setValue("+71234567890");
        $(".checkbox__box").click();
        $(".button").click();
        $(new Selectors.WithText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition
                .text(dateOfDelivery));

    }

    /*
        Доп задача с городом
    */
    @Test
    void shouldChooseCity() {
        $("[placeholder='Город']").setValue("рр");
        $$(".menu-item__control").find(new Text("Нарьян-Мар")).click();
        $("[placeholder='Город']").shouldHave(Condition.value("Нарьян-Мар"));

    }

    /*
        Доп задача с календарем
     */
    @Test
    void shouldPassByCalendar() {
        LocalDate dateOfDelivery = LocalDate.now().plusDays(7);//Указать сколько дней до доставки
        SelenideElement calendar = $(".calendar__layout");

        $("[placeholder='Город']").setValue("Москва");
        $(".icon-button").click();
        calendar.$(new Selectors.WithText(String.valueOf(dateOfDelivery.getDayOfMonth()))).click();
        $("[name='name']").setValue("Василий - Петрович");
        $("[name='phone']").setValue("+71234567890");
        $(".checkbox__box").click();
        $(".button").click();
        $(new Selectors.WithText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition
                .text(dateOfDelivery.format(format)));
    }
}