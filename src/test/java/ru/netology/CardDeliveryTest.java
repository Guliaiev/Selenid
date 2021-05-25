package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardDeliveryTest {
    public String generateDate(int shift) {
        String date;
        LocalDate localDate = LocalDate.now().plusDays(shift);
        date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(localDate);
        return date;
    }

    @BeforeEach
    void openUrl() {
        open("http://localhost:9999");
    }

    @AfterEach
   void tearDown() {
        closeWindow();
    }


    @Test
    void shouldInputPositive() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("Ульяновск");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] input").setValue("Евгений Петров");
        $("[data-test-id='phone'] input").setValue("+79780116541");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(withText("Успешно!"))
                .shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__content").shouldHave(exactText("Встреча успешно" +
                " забронирована на " + date));


    }

    @Test
    void shouldInputNameWhithSpaceAndHyphen() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("Ульяновск");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] input").setValue("Евгений-Петров  Ландыш-серебристый");
        $("[data-test-id='phone'] input").setValue("+79780116541");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Встреча успешно забронирована"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__content").shouldHave(exactText("Встреча успешно" +
                " забронирована на " + date));
    }

    @Test
    void shouldNotInputName() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("Ульяновск");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='phone'] input").setValue("+79780116541");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNotInputCity() {
        String date = generateDate(3);
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] input").setValue("Евгений Петров");
        $("[data-test-id='phone'] input").setValue("+79780116541");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNotInputTelNumber() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("Ульяновск");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] input").setValue("Евгений Петров");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNotInputDate() {
        $("[data-test-id='city'] input").setValue("Ульяновск");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(BACK_SPACE));
        $("[data-test-id='name'] input").setValue("Евгений Петров");
        $("[data-test-id='phone'] input").setValue("+79780116541");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Неверно введена дата"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldInputCityNotFromList() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("Тамань");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] input").setValue("Евгений Петров");
        $("[data-test-id='phone'] input").setValue("+79780116541");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Доставка в выбранный город недоступна"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldInputNameEnglish() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] input").setValue("Evgeniy Petrov");
        $("[data-test-id='phone'] input").setValue("+79780116541");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("только русские буквы, пробелы и дефисы"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNotInputPlusTelNumber() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] input").setValue("Евгений Петров");
        $("[data-test-id='phone'] input").setValue("89780116541");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Телефон указан неверно"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldInputNotFormatTelNumber() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] input").setValue("Евгений Петров");
        $("[data-test-id='phone'] input").setValue("+7980116541");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Телефон указан неверно"))
                .shouldBe(Condition.visible);
    }
}