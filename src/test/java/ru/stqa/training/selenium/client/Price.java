package ru.stqa.training.selenium.client;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Victoria Kadubina
 */
public class Price {
    private int value;
    private String textDecoration;
    private Color color;
    private Double fontSize;
    private boolean isStrong;

    public Price(WebElement price) {
        this.value = Integer.parseInt(price.getText().replace("$", ""));
        this.textDecoration = price.getCssValue("text-decoration");
        this.color = getColor(price);
        this.fontSize = Double.parseDouble(price.getCssValue("font-size").replaceAll("[a-zA-Z]", ""));
        this.isStrong = "strong".equals(price.getTagName());
    }

    public boolean isStruckOut(){
        return getTextDecoration().contains("line-through");
    }

    public int getValue() {
        return value;
    }

    public String getTextDecoration() {
        return textDecoration;
    }

    public Color getColor() {
        return color;
    }

    public Double getFontSize() {
        return fontSize;
    }

    public boolean isStrong() {
        return isStrong;
    }

    private Color getColor(WebElement element) {
        Pattern pattern = Pattern.compile("\\d+");
        String color = element.getCssValue("color");

        Matcher matcher = pattern.matcher(color);
        List<Integer> colors = new ArrayList<>();

        while (matcher.find())
            colors.add(Integer.parseInt(matcher.group()));

        if (colors.size() < 3)
            throw new IllegalArgumentException("Illegal color style");

        return new Color(colors.get(0), colors.get(1), colors.get(2));
    }

    static class Color {
        private int r;
        private int g;
        private int b;

        public Color(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Price.Color color = (Price.Color) o;

            if (r != color.r) return false;
            if (g != color.g) return false;
            return b == color.b;

        }

        @Override
        public int hashCode() {
            int result = r;
            result = 31 * result + g;
            result = 31 * result + b;
            return result;
        }

        @Override
        public String toString() {
            return "rgb(" + r + ", " + g + ", " + b + ")";
        }
    }
}
