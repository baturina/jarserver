package ru.netology;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderTest {
    @Test
    void shouldSubmitValidation() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=order-success]")
                .shouldHave(exactText("Ваша заявка успешно отправлена! " +
                        "Наш менеджер свяжется с вами в ближайшее время."));
    }


    @Test
    void shouldNotSubmitWithWrongName() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Ivanov Ivan");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=name] .input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                        "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitWithWrongPhone() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+799994");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }


//    @Test
//    void shouldNotSubmitWithWrongNameAndPhone() {
//        open("http://localhost:9999/");
//        $("[data-test-id=name] input").setValue("Ivanov Ivan");
//        $("[data-test-id=phone] input").setValue("+79999999999");
//        $("[data-test-id=agreement]").click();
//        $("button").click();
//        $("[data-test-id=name] .input__sub")
//                .shouldHave(exactText("Имя и Фамилия указаные неверно." +
//                        "Допустимы только русские буквы, пробелы и дефисы."));
//    }

    @Test
    void shouldNotSubmitWithoutAgreement() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("button").click();
        $("[data-test-id=agreement]").shouldHave(cssClass("input_invalid"));
    }

    @Test
    void shouldEmptyName() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=name] .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void shouldEmptyNumber() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=phone] .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }
}