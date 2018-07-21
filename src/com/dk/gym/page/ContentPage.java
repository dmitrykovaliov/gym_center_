package com.dk.gym.page;

public class ContentPage {
    private PageType pageType;
    private String pageURL;

    public ContentPage() {
    }

    public ContentPage(PageType pageType, String pageURL) {
        this.pageType = pageType;
        this.pageURL = pageURL;
    }

    public PageType getPageType() {
        return pageType;
    }

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentPage that = (ContentPage) o;

        if (pageType != that.pageType) return false;
        return pageURL != null ? pageURL.equals(that.pageURL) : that.pageURL == null;
    }

    @Override
    public int hashCode() {
        int result = pageType != null ? pageType.hashCode() : 0;
        result = 31 * result + (pageURL != null ? pageURL.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContentPage{" +
                "pageType=" + pageType +
                ", pageURL='" + pageURL + '\'' +
                '}';
    }
}
